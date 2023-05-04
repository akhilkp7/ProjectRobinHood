ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "ProjectRobinHood"
  )

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "3.3.2"

// https://mvnrepository.com/artifact/org.apache.spark/spark-sql
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.3.2"

// https://mvnrepository.com/artifact/org.apache.spark/spark-hive
libraryDependencies += "org.apache.spark" %% "spark-hive" % "3.3.2"
libraryDependencies += "com.storm-enroute" %% "scalameter" % "0.19"

// build.sbt
libraryDependencies += "org.scalameta" %% "scalameta" % "4.7.6"

libraryDependencies += "org.apache.spark" %% "spark-streaming" % "3.3.2"
