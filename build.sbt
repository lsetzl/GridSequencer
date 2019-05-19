enablePlugins(ScalaJSPlugin)

name := "scalajsstart"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.7"

libraryDependencies += "com.lihaoyi" %%% "scalatags" % "0.6.7"

scalaJSUseMainModuleInitializer := true
