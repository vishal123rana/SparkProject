package StructuredStreaming

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.dsl.expressions.StringToAttributeConversionHelper
import org.apache.spark.sql.functions.{col, column, desc, window}
object StreamingDemo {
    def main(arg:Array[String]):Unit= {
      val spark = SparkSession.builder().master("local").appName("Application").getOrCreate()
      val staticDataFrame = spark.read.format("csv")
        .option("header", "true")
        .option("inferSchema", "true")
        .load("/home/vishal/Desktop/data/retail-data/by-day/*.csv")
      // staticDataFrame.show(false);
      staticDataFrame.createOrReplaceTempView("retail_data")
      val staticSchema = staticDataFrame.schema
      staticDataFrame
        .selectExpr(
          "CustomerId",
          "(UnitPrice * Quantity) as total_cost",
          "InvoiceDate")
        .groupBy(
          col("CustomerId"), window(col("InvoiceDate"), "1 day"))
        .sum("total_cost")
        .show(false)
      spark.conf.set("spark.sql.shuffle.partitions", "5")
      val streamingDataFrame = spark.readStream // streaming DataFrame
        .schema(staticSchema)
        .option("maxFilesPerTrigger", 1)
        .format("csv")
        .option("header", "true")
        .load("/data/retail-data/by-day/*.csv")
      val purchaseByCustomerPerHour = streamingDataFrame
        .selectExpr(
          "CustomerId",
          "(UnitPrice * Quantity) as total_cost",
          "InvoiceDate")
        .groupBy(
          col("CustomerId"), window(col("InvoiceDate"), "1 day"))
        .sum("total_cost")
      purchaseByCustomerPerHour.writeStream
        .format("memory") // memory = store in-memory table
        .queryName("customer_purchases") // the name of the in-memory table
        .outputMode("complete") // complete = all the counts should be in the table
        .start()
    }
}

