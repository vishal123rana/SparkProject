package StructuredStreaming

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}

object kafkaPlusSpark {
    def main(args:Array[String]):Unit= {
        val brokers = "localhost:9092";
        val groupid = "grp1";
        val topic = "test";
        val kafkaParams = Map[String,Object](
           ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG->brokers,
            ConsumerConfig.GROUP_ID_CONFIG-> groupid,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG->classOf[StringDeserializer],
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG->classOf[StringDeserializer]
        )

      val spark = SparkSession.builder().master("local").appName("Application").getOrCreate()

    //  val streaming = spark.readStream.format("kafka").option("kafka.bootstrap.servers","localhost:9092").option("subscribe","test").load();
      //streaming.foreach(x=>println(x))
//      val query = streaming.writeStream
//        .outputMode("update").format("console").start()
//      spark.streams.awaitAnyTermination()
  //    streaming.printSchema()
      val df = spark.read.option("header",true).option("inferSchema",true).csv("C:\\Users\\vishal rana\\Desktop\\data\\creditcard.csv")
      df.show()
      val rd = df.rdd
      println(rd.partitioner)
      println(rd.count())
      spark.readStream.option("header",true).option("inferSchema",true).csv("C:\\Users\\vishal rana\\Desktop\\data\\creditcard.csv").show()
    }
}
