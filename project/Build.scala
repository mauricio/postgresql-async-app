import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "postgresql-async-app"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here
    "com.github.mauricio" %% "postgresql-async" % "0.1.1"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
