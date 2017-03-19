import sbt.Keys._
import sbt._
import play.sbt.PlayImport._


scalaVersion in ThisBuild := "2.11.8"

name := "postgresql-async-app"

version := "2.5.13-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
	"com.github.mauricio" %% "postgresql-async" % "0.2.20"
)

