import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

version in ThisBuild := "0.2.0-SNAPSHOT"
scalaVersion in ThisBuild := "2.13.0"
scalacOptions ++= Seq("-feature")
organization in ThisBuild := "com.yuiwai"
scalacOptions in ThisBuild ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xlint",
)


lazy val root = project
  .in(file("."))
  .aggregate(coreJVM, coreJS)
  .settings(
    name := "kasumi",
    crossScalaVersions := Nil,
    publish / skip := true
  )

lazy val core = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "kasumi-core",
    crossScalaVersions := Seq(scalaVersion.value, "2.12.8"),
    testFrameworks += new TestFramework("utest.runner.Framework"),
    libraryDependencies += "com.lihaoyi" %% "utest" % "0.6.9" % "test",
    publishTo := Some(Resolver.file("file", file("release")))
  )
lazy val coreJS = core.js
lazy val coreJVM = core.jvm

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