package kafka

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, from_json}
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.types.{IntegerType, LongType, StringType, StructType}

object ReadDataFromKafka {
      def main(args:Array[String]):Unit={
        //System.setProperty("hadoop.home.dir","$HADOOP_HOME")
        val spark = SparkSession.builder().master("local").appName("Application").getOrCreate()
        val df = spark.readStream.format("kafka").option("kafka.bootstrap.servers","localhost:9092").
          option("subscribe","kafka").load()

//        lines.writeStream.format("console").
//          outputMode("append").
//          trigger(Trigger.ProcessingTime("20 seconds")).
//          start()
        df.printSchema();
        val personStringDF = df.selectExpr("CAST(value AS STRING)")
        val schema = new StructType().add("firstname",StringType).add("dept",StringType).add("Salary",LongType)

        val personDF = personStringDF.select(from_json(col("value"), schema).as("data")).select("data.*")
        personDF.writeStream.format("console").outputMode("append").start().awaitTermination()
      }
}
