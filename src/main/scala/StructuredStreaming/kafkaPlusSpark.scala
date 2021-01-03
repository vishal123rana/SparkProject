package StructuredStreaming

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable

object kafkaPlusSpark {
    def main(args:Array[String]):Unit= {
        val kafkaParams = Map[String,Object](
            "bootstrap.servers"->"localhost:9092",
            "key.deserializer"->classOf[StringDeserializer],
            "value.deserializer"->classOf[StringDeserializer],
            "auto.offset.reset" -> "latest",
            "enable.auto.commit" -> (false:java.lang.Boolean)
        )
        //val conf = new SparkConf().setAppName("Streaming").setMaster("local");
        val spark = SparkSession.builder().master("local").appName("Application").getOrCreate()
       // val streaming = spark.readStream.format("kafka").option("kafka.bootstrap.servers","localhost:9092").option("subscribe","topic")
        val topics = Array("test")

    }
}
