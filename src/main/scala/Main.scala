import scala.meta._
object Main {
  def main(args: Array[String]): Unit = {

    val count = new WordCount();
    count.countWords();

    val path = java.nio.file.Paths.get("src/main/scala/WordCount.scala")
    val bytes = java.nio.file.Files.readAllBytes(path)
    val text = new String(bytes, "UTF-8")
    val input = Input.VirtualFile(path.toString, text)
    val exampleTree = input.parse[Source].get

//    print(exampleTree.syntax)
    print(exampleTree.structure)
    println()
    object ImportTransformer extends Transformer {
      override def apply(tree: Tree): Tree = tree match {
        // Match on Import nodes
        case i @ Importer(q"org.apache.spark", importees) =>
          // Replace the SparkContext importee with SparkStreaming
          val newImportees = importees.map {
            case i @ Importee.Name(Name("SparkContext")) =>
              i.copy(name = Name("SparkStreaming"))
            case other => other
          }
          i.copy(importees = newImportees)

        // Recursively traverse the AST
        case other => super.apply(other)
      }
    }

    println(ImportTransformer(exampleTree))

  }
}