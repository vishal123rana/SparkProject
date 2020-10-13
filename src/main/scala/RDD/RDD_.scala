package RDD

import org.apache.spark.sql.SparkSession

object RDD_ {
  def individualStartWithS(word:String)={
    word.startsWith("S")
  }
   def main(args:Array[String]):Unit={
     val spark = SparkSession.builder().master("local").appName("Application").getOrCreate()
     val myCollection = "Spark The Definitive Guide : Big Data Processing Made Simple".split(" ")
     val words = spark.sparkContext.parallelize(myCollection,2);
     words.setName("myWords")
     println(words.distinct.count())
     words.filter(word=>individualStartWithS(word)).collect().foreach(println)
   }
}
