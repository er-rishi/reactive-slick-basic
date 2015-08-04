name := "reactive-slick-basic"

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
    "com.typesafe.slick" %% "slick" % "3.0.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "postgresql"          % "postgresql" % "9.1-901.jdbc4"
)
