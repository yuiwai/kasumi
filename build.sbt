version in ThisBuild := "0.1"

scalaVersion in ThisBuild := "2.13.0-RC1"

scalacOptions ++= Seq("-feature")

lazy val core = project
  .in(file("core"))
  .settings(
    name := "kasumi-core",
    testFrameworks += new TestFramework("utest.runner.Framework"),
    libraryDependencies += "com.lihaoyi" %% "utest" % "0.6.7" % "test"
  )

lazy val demo = project
  .in(file("demo"))
  .dependsOn(core)

lazy val stations = project
  .in(file("examples/stations"))
  .dependsOn(core)