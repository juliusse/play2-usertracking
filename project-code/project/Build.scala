import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appOrganization = "info.seltenheim"
  val appName         = "play2-usertracking"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    organization := appOrganization
    , javacOptions ++= Seq("-source", "1.6", "-target", "1.6")//for compatibility with Debian Squeeze 
  )

}
