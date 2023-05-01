//
//Source(List(Pkg(Term.Name("BatchWordCount"), List(Defn.Object(Nil, Term.Name("BatchWordCounts"),
//Template(Nil, Nil, Self(Name(""), None), List(Import(List(Importer(Term.Select(Term.Select(Term.Name("org"),
//Term.Name("apache")), Term.Name("spark")), List(Importee.Name(Name("SparkConf")))))),
//Import(List(Importer(Term.Select(Term.Select(Term.Name("org"), Term.Name("apache")), Term.Name("spark")),
//List(Importee.Name(Name("SparkContext")))))), Defn.Def(Nil, Term.Name("main"),
//List(Member.ParamClauseGroup(Type.ParamClause(Nil), List(Term.ParamClause(List(Term.Param(Nil, Term.Name("args"),
//Some(Type.Apply(Type.Name("Array"), Type.ArgClause(List(Type.Name("String"))))), None)), None)))), None,
//Term.Block(List(Defn.Val(Nil, List(Pat.Var(Term.Name("conf"))), None, Term.New(Init(Type.Name("SparkConf"), Name(""),
//List(Term.ArgClause(Nil, None))))), Term.Apply(Term.Select(Term.Name("conf"), Term.Name("setMaster")),
//Term.ArgClause(List(Lit.String("local")), None)), Term.Apply(Term.Select(Term.Name("conf"), Term.Name("setAppName")),
//Term.ArgClause(List(Lit.String("Word Count example")), None)), Defn.Val(Nil, List(Pat.Var(Term.Name("sc"))), None,
//Term.New(Init(Type.Name("SparkContext"), Name(""), List(Term.ArgClause(List(Term.Name("conf")), None))))),
//Defn.Val(Nil, List(Pat.Var(Term.Name("textFile"))), None, Term.Apply(Term.Select(Term.Name("sc"),
//Term.Name("textFile")), Term.ArgClause(List(Lit.String("src/main/scala/scalaDemo.txt")), None))),
//Defn.Val(Nil, List(Pat.Var(Term.Name("counts"))), None,
//Term.Apply(Term.Select(Term.Apply(Term.Select(Term.Apply(Term.Select(Term.Name("textFile"), Term.Name("flatMap")),
//Term.ArgClause(List(Term.Function(Term.ParamClause(List(Term.Param(Nil, Term.Name("line"), None, None)), None),
//Term.Apply(Term.Select(Term.Name("line"), Term.Name("split")), Term.ArgClause(List(Lit.String(" ")), None)))), None)),
//Term.Name("map")),
//Term.ArgClause(List(Term.Function(Term.ParamClause(List(Term.Param(Nil, Term.Name("word"), None, None)), None),
//Term.Tuple(List(Term.Name("word"), Lit.Int(1))))), None)), Term.Name("reduceByKey")),
//Term.ArgClause(List(Term.AnonymousFunction(Term.ApplyInfix(Term.Placeholder(), Term.Name("+"), Type.ArgClause(Nil),
//Term.ArgClause(List(Term.Placeholder()), None)))), None))), Term.Apply(Term.Select(Term.Name("counts"),
//Term.Name("foreach")), Term.ArgClause(List(Term.Name("println")), None)),
//Term.Apply(Term.Select(Term.Select(Term.Name("System"), Term.Name("out")), Term.Name("println")),
//Term.ArgClause(List(Term.ApplyInfix(Lit.String("Total words: "), Term.Name("+"), Type.ArgClause(Nil),
//Term.ArgClause(List(Term.Apply(Term.Select(Term.Name("counts"), Term.Name("count")),
//Term.ArgClause(Nil, None))), None))), None)), Term.Apply(Term.Select(Term.Name("counts"), Term.Name("saveAsTextFile")),
//Term.ArgClause(List(Lit.String("/home/ubuntu/stdatalabs/code/datasets/output/WordCount.txt")), None)))))), Nil))))))
