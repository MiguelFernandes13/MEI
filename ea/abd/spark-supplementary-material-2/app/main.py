from pyspark.sql import SparkSession, DataFrame
import time


#implement a function that counts the number of titles per
#decade, sorted by most to least.
def titlesByDecade(titles: DataFrame) -> DataFrame:
    titles.createOrReplaceTempView("titles")
    return spark.sql("select (FLOOR(startYear/10)*10) as decade, count(*) as count from titles group by FLOOR(startYear/10) order by count desc")
    


# both yearStart and yearEnd refer to the startYear column
def averageRuntime(titles: DataFrame, titleType: str, yearStart: int, yearEnd: int) -> DataFrame:
    titles.createOrReplaceTempView("titles")
    return spark.sql(f"select avg(runtimeMinutes) as average from titles where titleType = '{titleType}' and startYear >= {yearStart} and startYear <= {yearEnd} group by titleType")
    

def run(titles: DataFrame):
    for _ in range(3):
        titlesByDecade(titles).count()
        averageRuntime(titles, 'tvEpisode', 2005, 2010).count()
        averageRuntime(titles, 'movie', 2015, 2020).count()


# the spark session
spark = SparkSession.builder.master("spark://spark:7077").getOrCreate()

# load the title.basics dataset
# TSV
titlesT = spark.read.csv("/app/imdb/title.basics.tsv", header=True, inferSchema=True, sep="\t")
# Parquet
#titlesP = spark.read.parquet("/app/imdb/title.basics.parquet")
titlesP = spark.read.parquet("/app/imdb/title.basics.parquet", partitionBy='startYear')
print(f"Number of titles: {titlesP.count()}")

# run the queries
start_run = time.time()
run(titlesP)
end_run = time.time()
print(f"Time for run all queries {end_run - start_run}")
