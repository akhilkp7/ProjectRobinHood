package BatchWordCount

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.scalameter._

object SampleSparkAppBenchmark extends Bench.LocalTime {

  val conf = new SparkConf()
    .setMaster("local[2]")
    .setAppName("Word Count example")
    .set("spark.driver.bindAddress", "127.0.0.1")
  val sc = new SparkContext(conf)

  val textFile = sc.textFile("src/main/scala/scalaDemo.txt")
  val words = textFile.flatMap(_.split(" "))

  val ranges = Gen.range("range")(5, 50, 5)

  performance of "flatMap" in {
    measure method "flatMap" in {
      using(ranges) in { r =>
        words.flatMap(_.split(" "))
      }
    }
  }

  performance of "map" in {
    measure method "map" in {
      using(ranges) in { r =>
        words.map(x => (x, 1))
      }
    }
  }

  performance of "reduceByKey" in {
    measure method "reduceByKey" in {
      using(ranges) in { r =>
        val pairs = words.map(x => (x, 1))
        pairs.reduceByKey(_ + _)
      }
    }
  }
}
