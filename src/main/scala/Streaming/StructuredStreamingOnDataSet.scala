package Streaming

import org.apache.spark.sql.SparkSession

case class Flight(DEST_COUNTRY_NAME: String, ORIGIN_COUNTRY_NAME: String,count: BigInt)
object StructuredStreamingOnDataSet {
  def originIsDestination(flight_row: Flight): Boolean = {
    return flight_row.ORIGIN_COUNTRY_NAME == flight_row.DEST_COUNTRY_NAME
  }
    def main(args:Array[String]):Unit= {
      val spark = SparkSession.builder().master("local").appName("Application").getOrCreate()
      val dataSchema = spark.read
        .parquet("/home/vishal/Desktop/data/flight-data/parquet/2010-summary.parquet/")
        .schema
      val flightsDF = spark.readStream.schema(dataSchema)
        .parquet("/home/vishal/Desktop/data/flight-data/parquet/2010-summary.parquet/")
      import spark.implicits._
      val flights = flightsDF.as[Flight]
      flights.filter(flight_row => originIsDestination(flight_row))
        .groupByKey(x => x.DEST_COUNTRY_NAME).count()
        .writeStream.queryName("device_counts").format("memory").outputMode("complete")
        .start()
      spark.sql("select * from device_counts").show(false)
    }
}
