import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

version in ThisBuild := "0.1"
scalaVersion in ThisBuild := "2.13.0"
scalacOptions ++= Seq("-feature")

lazy val core = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "kasumi-core",
    testFrameworks += new TestFramework("utest.runner.Framework"),
    libraryDependencies += "com.lihaoyi" %% "utest" % "0.6.9" % "test"
  )
lazy val coreJS = core.js
lazy val coreJVM = core.jvm

lazy val demo = project
  .in(file("demo"))
  .dependsOn(coreJVM)

lazy val stations = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("examples/stations"))
  .dependsOn(core)

lazy val stationsJS = stations.js
lazy val stationsJVM = stations.jvm

lazy val visualize = project
  .in(file("visualize"))
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.7"
    ),
    scalaJSUseMainModuleInitializer := true
  )
  .dependsOn(stationsJS)
  .enablePlugins(ScalaJSPlugin)

lazy val cli = project
  .in(file("cli"))
  .dependsOn(stationsJVM)