package kafka



import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}


object streamPipeline {
  def main(args:Array[String]):Unit={
    //System.setProperty("hadoop.home.dir","$HADOOP_HOME")
    val spark = SparkSession.builder().master("local").appName("Application").getOrCreate()
    val broker_id = ""
    val group_id = ""
    val topics = ""
    val kafkParams = Map[String,Object](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG->broker_id,
      ConsumerConfig.GROUP_ID_CONFIG->group_id,
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG->classOf[StringDeserializer],
      "auto.offset.reset" -> "earliest",
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG->classOf[StringDeserializer]
    )
    import org.apache.kafka.common.TopicPartition
    val offsets = Map(new TopicPartition("kafka", 0) -> 2L)
    val sparkconf = new SparkConf().setMaster("local").setAppName("kafka demo");
    val ssc = new StreamingContext(sparkconf,Seconds(5))
    val sc = ssc.sparkContext
    sc.setLogLevel("OFF")
//    val message = KafkaUtils.createDirectStream[String,String](
//      ssc,
//      LocationStrategies.PreferConsistent,
//      ConsumerStrategies.Subscribe[String, String](kafkParams, topics.,offsets)
//    )
  }
}
