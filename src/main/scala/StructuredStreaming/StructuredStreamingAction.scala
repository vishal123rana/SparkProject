package StructuredStreaming

import org.apache.spark.sql.SparkSession

object StructuredStreamingAction {
  def main(args:Array[String]):Unit={
    val spark = SparkSession.builder().master("local").appName("Application").getOrCreate()
    val static = spark.read.json("/home/vishal/Desktop/data/activity-data/")
    val dataSchema = static.schema
    println(dataSchema)
    val streaming = spark.readStream.schema(dataSchema).option("maxFilesPerTrigger",1).json("/home/vishal/Desktop/data/activity-data")
    val activityCounts = streaming.groupBy("gt").count()
    spark.conf.set("spark.sql.shuffle.partitions",5)
    val activityQuery = activityCounts.writeStream.queryName("activity_counts")
      .format("memory").outputMode("complete").start()
    activityQuery.awaitTermination()
  }
}
