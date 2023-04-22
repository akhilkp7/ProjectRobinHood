import org.apache.spark.sql.catalyst.ScalaReflection.universe.EmptyTree

import scala.meta._
import java.nio.file._
import java.nio.charset.StandardCharsets
import java.io._
import java._
object Main {
  def main(args: Array[String]): Unit = {

    val count = new WordCount();
    count.countWords();

    val path = java.nio.file.Paths.get("src/main/scala/WordCount.scala")
    val bytes = java.nio.file.Files.readAllBytes(path)
    val text = new String(bytes, "UTF-8")
    val input = Input.VirtualFile(path.toString, text)
    val exampleTree = input.parse[Source].get
    val outputFile = new File("src/main/scala/WordCount_Transformed.scala")
    val writer = new PrintWriter(outputFile)
//    print(exampleTree.syntax)
    print(exampleTree.structure)
    println()

    val one= q"val a=10"
    val two =q"val b=20"
    object ImportTransformer extends Transformer {
      override def apply(tree: Tree): Tree = tree match {
        // Match on Import nodes
        case i@Importer(q"org.apache.spark", importees) =>
          // Replace the SparkContext importee with SparkStreaming
          val newImportees = importees.map {
            case i@Importee.Name(Name("SparkContext")) =>
              i.copy(name = Name("SparkContext"))
            case other => other
          }
          i.copy(importees = newImportees)
        case q"val $name = new SparkContext($conf)" =>
          q"val sparkConf = new SparkConf().setAppName(${Lit.String("StatefulNetworkWordCount")})"
        case q"val $pat = $expr" if pat.syntax == "textFile" && expr.syntax.contains(".textFile(") =>
          q"""
                      val ssc = new StreamingContext(sparkConf, Seconds(1))
                      ssc.checkpoint(".")
                    """
        case other => super.apply(other)
      }
    }



    println(ImportTransformer(exampleTree))
    /*    val transformedTree = ImportTransformer(exampleTree)
        writer.write(transformedTree.syntax)
        writer.close()

        println(s"Transformed code written to: ${outputFile.getAbsolutePath}")*/

  }
}