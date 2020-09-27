package DataSet

import org.apache.spark.{SparkConf, SparkContext}


object Test {
  def main(args:Array[String]):Unit={
      val conf = new SparkConf().setMaster("local").setAppName("Application")
      val sc = new SparkContext(conf)
      val dataRdd = sc.parallelize(Array(("Broke",20)))
      dataRdd.foreach(println)
  }
}
