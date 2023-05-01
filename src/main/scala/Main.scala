import java.io._
import scala.collection.immutable.List
import scala.meta._
object Main {
  def main(args: Array[String]): Unit = {

    val path = java.nio.file.Paths.get("src/main/scala/BatchWordCount/WordCount.scala")
    val bytes = java.nio.file.Files.readAllBytes(path)
    val text = new String(bytes, "UTF-8")
    val input = Input.VirtualFile(path.toString, text)
    val exampleTree = input.parse[Source].get
    /*val outputFile = new File("src/main/scala/WordCount_Transformed.scala")
    val writer = new PrintWriter(outputFile)*/
//    print(exampleTree.syntax)
    print(exampleTree.structure)
    println()

    val newTree = exampleTree.transform {
      case Import(List(Importer(Term.Select(Term.Select(Term.Name("org"), Term.Name("apache")), Term.Name("spark")), List(Importee.Name(Name("SparkContext"))))))
      => Import(List(Importer(Term.Select(Term.Select(Term.Select(Term.Name("org"), Term.Name("apache")), Term.Name("spark")), Term.Name("streaming")), List(Importee.Wildcard()))))
      case Pat.Var(Term.Name("textFile")) => Pat.Var(Term.Name("lines"))
      case Type.Name("SparkContext") => Type.Name("StreamingContext")
      case Term.ArgClause(List(Term.Name("conf")), None) => Term.ArgClause(List(Term.Name("conf"),
        Term.Apply(Term.Name("Seconds"), Term.ArgClause(List(Lit.Int(1)), None))), None)
      case Term.Select(Term.Name("sc"), Term.Name("textFile")) => Term.Select(Term.Name("sc"), Term.Name("socketTextStream"))
      case Term.ArgClause(List(Lit.String("src/main/scala/scalaDemo.txt")), None) => Term.ArgClause(List(Lit.String("localhost"), Lit.Int(9999)), None)
      case Term.Select(Term.Name("textFile"), Term.Name("flatMap")) => Term.Select(Term.Name("lines"), Term.Name("flatMap"))
      case Pat.Var(Term.Name("counts")) => Pat.Var(Term.Name("wordDstream"))




      case other => other

    }
    print(newTree.show[Syntax])




    val extraLine =
      q"""
      val stateDstream = wordDstream.mapWithState(
          StateSpec.function(mappingFunc).initialState(initialRDD))
    """

    val extraLine2 =
      q"""
ssc.start()
      ssc.awaitTermination()
          """
    object ImportTransformer extends Transformer {
      override def apply(tree: Tree): Tree = tree match {

        case q"import org.apache.spark.SparkContext" => q"import org.apache.spark.streaming._"

        case q"val $name = new SparkContext($conf)" =>
          q"val sparkConf = new SparkConf().setAppName(${Lit.String("StatefulNetworkWordCount")})"

        case q"""  counts.foreach(println) """ =>
          Defn.Val(Nil, List(Pat.Var(Term.Name("mappingFunc"))), None,
            Term.Function(Term.ParamClause(List(Term.Param(Nil, Term.Name("word"), Some(Type.Name("String")), None),
              Term.Param(Nil, Term.Name("one"), Some(Type.Apply(Type.Name("Option"), Type.ArgClause(List(Type.Name("Int"))))), None),
              Term.Param(Nil, Term.Name("state"), Some(Type.Apply(Type.Name("State"),
                Type.ArgClause(List(Type.Name("Int"))))), None)), None),
              Term.Block(List(Defn.Val(Nil, List(Pat.Var(Term.Name("sum"))), None,
                Term.ApplyInfix(Term.Apply(Term.Select(Term.Name("one"), Term.Name("getOrElse")), Term.ArgClause(List(Lit.Int(0)), None)),
                  Term.Name("+"), Type.ArgClause(Nil), Term.ArgClause(List(Term.Apply(Term.Select(Term.Select(Term.Name("state"),
                    Term.Name("getOption")), Term.Name("getOrElse")), Term.ArgClause(List(Lit.Int(0)), None))), None))),
                Defn.Val(Nil, List(Pat.Var(Term.Name("output"))), None, Term.Tuple(List(Term.Name("word"), Term.Name("sum")))),
                Term.Apply(Term.Select(Term.Name("state"), Term.Name("update")), Term.ArgClause(List(Term.Name("sum")), None)),
                Term.Name("output")))))
        case q"val wordDstream = $dstreamExpr.reduceByKey(_ + _)" =>
          q"val wordDstream = $dstreamExpr"


        case other => super.apply(other)
      }
    }

  println("\n")

    println(ImportTransformer(newTree))
       val transformedTree = ImportTransformer(exampleTree)

       /* writer.write(transformedTree.syntax)
        writer.close()
        println(s"Transformed code written to: ${outputFile.getAbsolutePath}")*/

  }
}





//import scala.meta._
//
//// parse the existing Scala code file
//val code = """
//  object MyClass {
//    def main(args: Array[String]): Unit = {
//      // existing code here
//    }
//  }
//"""
//val parsedCode = code.parse[Source].get
//
//// create the mapping function as a Term.Apply value
//val mappingFunc = q"""
//  val mappingFunc = (word: String, one: Option[Int], state: State[Int]) => {
//    val sum = one.getOrElse(0) + state.getOption.getOrElse(0)
//    val output = (word, sum)
//    state.update(sum)
//    output
//  }
//"""
//
//// find the line to insert the mapping function after
//val targetLine = 5 // for example
//
//// insert the mapping function after the target line
//val newCode = parsedCode.transform {
//  case Term.Block(stats) =>
//    val (before, after) = stats.splitAt(targetLine)
//    q"""
//      ${before :+ mappingFunc} ++ ${after}
//    """
//}.syntax

