package StructuredStreaming

import org.apache.spark.sql.SparkSession
import org.apache.kafka.clients.consumer.ConsumerRecord;
object WordCountStraming {
   def main(args:Array[String]):Unit={
     val spark = SparkSession.builder().master("local").appName("Application").getOrCreate()
     val lines = spark.readStream
       .format("socket")
       .option("host", "localhost")
       .option("port", 9999)
       .load()
     import spark.implicits._
     val words = lines.as[String].flatMap(_.split(" "))
     val wordCounts = words.groupBy("value").count()
     val query = wordCounts.writeStream
       .outputMode("complete")
       .format("console")
       .start()
     query.awaitTermination()
   }
}
