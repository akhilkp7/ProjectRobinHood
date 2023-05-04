package BatchWordCount
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
object WordCountStreaming {
  def main(args: Array[String]) = {
    val conf = new SparkConf()
    conf.setMaster("local[2]").setAppName("Word Count example").set("spark.driver.bindAddress", "127.0.0.1")
    val sc = new StreamingContext(conf, Seconds(1))
    val lines = sc.socketTextStream("localhost", 9999)
    val wordDstream = lines.flatMap(line => line.split(" ")).map(word => (word, 1))
    val mappingFunc = (word: String, one: Option[Int], state: State[Int]) => {
      val sum = one.getOrElse(0) + state.getOption.getOrElse(0)
      val output = (word, sum)
      state.update(sum)
      output
    }
    {
      val stateDstream = wordDstream.mapWithState(StateSpec.function(mappingFunc))
      stateDstream.print()
      sc.checkpoint(".")
      sc.start()
      sc.awaitTermination()
    }
  }
}