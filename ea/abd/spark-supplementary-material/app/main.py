#!/usr/bin/env python3

from pyspark.sql import SparkSession

# the spark session
spark = SparkSession.builder.master("spark://spark:7077").getOrCreate()

# test that spark is working
df = spark.range(5).toDF("number")
df.show()

# load the title.basics dataset
titles = spark.read.csv("/app/imdb/title.basics.tsv", header=True, inferSchema=True, sep="\t")

# get a random sample and print the first 5
titles.sample(0.01).show(5)

# get the number of entries in the dataframe
print(titles.count())

# create a view to use with SQL
titles.createOrReplaceTempView("titles")

# query: get the release year of iron man
spark.sql("select startYear from titles where primaryTitle = 'Iron Man'").show()

# query Compute the number of titles per titleType
spark.sql("select titleType, count(*) as count from titles group by titleType order by count desc").show()


#titles.write.parquet("/app/titles.parquet")
#titles.write.parquet("/app/titles2.parquet", compression='gzip')
#titles.write.parquet("/app/titles.startyear.parquet", partitionBy='startYear')

parquet_Partition = spark.read.parquet("/app/titles.startyear.parquet")
parquet_Partition.createOrReplaceTempView("parquet_Partition")
#obtain the numberof titles released in 2022
number_titles = spark.sql("select count(*) from parquet_Partition where startYear = 2022").show()



