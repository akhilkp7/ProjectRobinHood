package BatchWordCount

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
object SampleSparkApp {



  def main(args: Array[String]) = {

    //Create a SparkContext to initialize Spark
    val conf = new SparkConf()
    conf.setMaster("local[2]").setAppName("Word Count example").set("spark.driver.bindAddress", "127.0.0.1")
    val sc = new SparkContext(conf)

    // Load the text into a Spark RDD, which is a distributed representation of each line of text
    val textFile = sc.textFile("src/main/scala/scalaDemo.txt")

    //word count
    val counts = textFile.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)

    counts.foreach(println)
    System.out.println("Total words: " + counts.count())
  }
}
