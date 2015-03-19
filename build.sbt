name := "hc_monads"

version := "0.2"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.0",
  "org.specs2" %% "specs2-core" % "3.1" % "test"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

scalacOptions in Test ++= Seq("-Yrangepos")

testOptions in Test += Tests.Argument("html")
