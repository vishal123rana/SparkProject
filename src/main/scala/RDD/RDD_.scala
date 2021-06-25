package RDD

import org.apache.spark.sql.SparkSession

object RDD_ {
  def individualStartWithS(word:String): Boolean ={
    word.startsWith("S")
  }
   def main(args:Array[String]):Unit={
     val spark = SparkSession.builder().master("local").config("spark.ui.port","7077").appName("Application").getOrCreate()
     val myCollection = "Spark The Definitive Guide : Big Data Processing Made Simple".split(" ")
    val words = spark.sparkContext.textFile("C:\\Spark\\README.md")
     //  val df = spark.read.option("inferSchema",false).option("header",false).csv("C:\\Users\\vishal rana\\Desktop\\one.csv")
    // df.printSchema();
    words.collect();
   }
}
