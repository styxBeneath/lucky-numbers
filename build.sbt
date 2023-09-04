ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.0.1"

lazy val root = (project in file("."))
  .settings(
    name := "lucky-numbers",
    idePackagePrefix := Some("io.luckynumbers")
  )
libraryDependencies += "org.typelevel" %% "cats-effect" % "3.1.1"
