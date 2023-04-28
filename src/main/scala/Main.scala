//import java.io._
//import scala.meta._
//object Main {
//  def main(args: Array[String]): Unit = {
//
//    val count = new WordCount();
//    count.countWords();
//
//    val path = java.nio.file.Paths.get("C:\\Users\\Akhil\\Desktop\\scalaWordCount.scala")
//    val bytes = java.nio.file.Files.readAllBytes(path)
//    val text = new String(bytes, "UTF-8")
//    val input = Input.VirtualFile(path.toString, text)
//    val exampleTree = input.parse[Source].get
//    val outputFile = new File("src/main/scala/WordCount_Transformed.scala")
//    val writer = new PrintWriter(outputFile)
////    print(exampleTree.syntax)
//    print(exampleTree.structure)
//    println()
//
//    val one= q"val a=10"
//    val two =q"val b=20"
//
//    val extraLine =
//      q"""
//      val stateDstream = wordDstream.mapWithState(
//          StateSpec.function(mappingFunc).initialState(initialRDD))
//    """
//
//    val extraLine2 =
//      q"""
//ssc.start()
//      ssc.awaitTermination()
//          """
//    object ImportTransformer extends Transformer {
//      override def apply(tree: Tree): Tree = tree match {
//
//        case q"import org.apache.spark.SparkContext" => q"import org.apache.spark.streaming._"
//
//        case q"val $name = new SparkContext($conf)" =>
//          q"val sparkConf = new SparkConf().setAppName(${Lit.String("StatefulNetworkWordCount")})"
//
//        case q"val $pat = $expr" if pat.syntax == "textFile" && expr.syntax.contains(".textFile(") =>
//          q"""
//                      val ssc = new StreamingContext(sparkConf, Seconds(1))
//                      ssc.checkpoint(".")
//                      val lines = ssc.socketTextStream(args(0), args(1).toInt)
//
//                      val mappingFunc = (word: String, one: Option[Int], state: State[Int]) => {
//            val sum = one.getOrElse(0) + state.getOption.getOrElse(0)
//            val output = (word, sum)
//            state.update(sum)
//            output
//          }
//
//                    """
//
//        case q"""val counts = textFile.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey(_ + _)""" =>
//          q"""val wordDstream = lines.flatMap(_.split(" ")).map(x => (x, 1))"""
//
//
//        case q"""  counts.foreach(println) """ =>
//          q"""
//            val stateDstream = wordDstream.mapWithState(
//            StateSpec.function(mappingFunc).initialState(initialRDD))
//          stateDstream.print()
//          ssc.start()
//          ssc.awaitTermination()
//           """
//
//
//
//        case other => super.apply(other)
//      }
//    }
//
//
//
//    println(ImportTransformer(exampleTree))
//       val transformedTree = ImportTransformer(exampleTree)
//
//        writer.write(transformedTree.syntax)
//        writer.close()
//        println(s"Transformed code written to: ${outputFile.getAbsolutePath}")
//
//  }
//}
//
//
//
//
//
////import scala.meta._
////
////// parse the existing Scala code file
////val code = """
////  object MyClass {
////    def main(args: Array[String]): Unit = {
////      // existing code here
////    }
////  }
////"""
////val parsedCode = code.parse[Source].get
////
////// create the mapping function as a Term.Apply value
////val mappingFunc = q"""
////  val mappingFunc = (word: String, one: Option[Int], state: State[Int]) => {
////    val sum = one.getOrElse(0) + state.getOption.getOrElse(0)
////    val output = (word, sum)
////    state.update(sum)
////    output
////  }
////"""
////
////// find the line to insert the mapping function after
////val targetLine = 5 // for example
////
////// insert the mapping function after the target line
////val newCode = parsedCode.transform {
////  case Term.Block(stats) =>
////    val (before, after) = stats.splitAt(targetLine)
////    q"""
////      ${before :+ mappingFunc} ++ ${after}
////    """
////}.syntax
//
