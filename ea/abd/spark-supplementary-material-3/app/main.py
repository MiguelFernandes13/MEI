from pyspark.sql import SparkSession
from pyspark.sql.functions import count

#docker exec spark-spark-1 python3 main.py

# the spark session
spark = SparkSession.builder \
    .master("spark://spark:7077") \
    .config("spark.jars", "/app/gcs-connector-hadoop3-2.2.12.jar") \
    .config("spark.driver.extraClassPath", "/app/gcs-connector-hadoop3-2.2.12.jar") \
    .config("spark.eventLog.enabled", "true") \
    .config("spark.eventLog.dir", "/tmp/spark-events") \
    .getOrCreate()

# google cloud service account credentials file
spark._jsc.hadoopConfiguration().set(
    "google.cloud.auth.service.account.json.keyfile",
    "/app/credentials.json")

# bucket name
BUCKET_NAME = 'abd_example'

# data frames
titles = spark.read.parquet(f"gs://{BUCKET_NAME}/title.basics.parquet")
principals = spark.read.parquet(f"gs://{BUCKET_NAME}/title.principals.parquet")
names = spark.read.parquet(f"gs://{BUCKET_NAME}/name.basics.parquet")

#titles.show()
#principals.show()
#names.show()

#The main list of people involved in the title with tconst = "tt3896198", returning nconst,
#primaryName, category, and characters (place the where operator after the join);
principals \
    .join(names, names["nconst"] == principals["nconst"]) \
    .where(principals["tconst"] == "tt3896198") \
    .select(principals["nconst"], names["primaryName"], principals["category"], principals["characters"]) \
    .explain(mode='extended')
    #.show()
#write the result in Google Cloud Storage
#titles.join(principals, titles["tconst"] == principals["tconst"]).where(titles["tconst"] == "tt3896198").join(names, principals["nconst"] == names["nconst"]).select(principals["nconst"], names["primaryName"], principals["category"], principals["characters"]).write.json(f"gs://{BUCKET_NAME}/tt3896198.json")


#The top 10 people with more titles, returning nconst, primaryName, count
principals \
    .join(names, names["nconst"] == principals["nconst"]) \
    .groupBy(names["nconst"], names["primaryName"]) \
    .agg(count("*").alias("count")) \
    .sort("count", ascending=False) \
    .limit(10) \
    .explain(mode='extended')
    #.show()
#write the result in Google Cloud Storage
#names.join(principals, names["nconst"] == principals["nconst"]).join(titles, principals["tconst"] == titles["tconst"]).groupBy(names["nconst"], names["primaryName"]).agg(count(titles["tconst"]).alias("count")).sort("count", ascending=False).limit(10).write.json(f"gs://{BUCKET_NAME}/top10.json")