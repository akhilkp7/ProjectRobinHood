package org.apache.spark.examples.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.scalameter._

object StatefulNetworkWordCountBenchmark extends Bench.LocalTime {

  val sparkConf = new SparkConf().setMaster("local[2]").setAppName("StatefulNetworkWordCountBenchmark").set("spark.driver.bindAddress", "127.0.0.1")
  val ssc = new StreamingContext(sparkConf, Seconds(1))

  val lines = ssc.socketTextStream("localhost", 9000)
  val words = lines.flatMap(_.split(" "))
  val wordDstream = words.map(x => (x, 1))

  // Define the mapping function for mapWithState
  val mappingFunc = (word: String, one: Option[Int], state: State[Int]) => {
    val sum = one.getOrElse(0) + state.getOption.getOrElse(0)
    val output = (word, sum)
    state.update(sum)
    output
  }

  // Define the initial state RDD for mapWithState
  val initialRDD = ssc.sparkContext.parallelize(List(("hello", 1), ("world", 1)))

  // Define the ranges to be used for performance evaluation
  val ranges = Gen.range("range")(1, 10, 1)

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

  performance of "mapWithState" in {
    measure method "mapWithState" in {
      using(ranges) in { r =>
        wordDstream.mapWithState(StateSpec.function(mappingFunc).initialState(initialRDD))
      }
    }
  }
}
