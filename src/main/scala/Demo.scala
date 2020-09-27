import org.apache.spark.sql.SparkSession
case class Flight(DEST_COUNTRY_NAME:String,ORIGIN_COUNTRY_NAME:String,count:BigInt)
object Demo {
   def main(args:Array[String]):Unit={
        val spark = SparkSession.builder().master("local").appName("Application").getOrCreate()
       import spark.implicits._
      val df = spark.read.option("inferSchema","true").option("header","true").json("/home/vishal/Desktop/data/flight-data/json/")
     println(df.getClass)
     df.show(false)
     val dataset = df.as[Flight]
     println(dataset.getClass)
     dataset.show(false)
  //  df.show(false);
   }
}
