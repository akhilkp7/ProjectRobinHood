object SampleSparkApp {
  import org.apache.spark.SparkConf
  import org.apache.spark.streaming._

  def main(args: Array[String]) = {
    val conf = new SparkConf()
    conf.setMaster("local")
    conf.setAppName("Word Count example")
    val sparkConf = new SparkConf().setAppName("StatefulNetworkWordCount")

      val ssc = new StreamingContext(sparkConf, Seconds(1))
      ssc.checkpoint(".")
      val lines = ssc.socketTextStream(args(0), args(1).toInt)
      val mappingFunc = (word: String, one: Option[Int], state: State[Int]) => {
        val sum = one.getOrElse(0) + state.getOption.getOrElse(0)
        val output = (word, sum)
        state.update(sum)
        output
      }

    val wordDstream = lines.flatMap(_.split(" ")).map(x => (x, 1))

      val stateDstream = wordDstream.mapWithState(StateSpec.function(mappingFunc))
      stateDstream.print()
      ssc.start()
      ssc.awaitTermination()

  }
}