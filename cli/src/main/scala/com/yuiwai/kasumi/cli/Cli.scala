package com.yuiwai.kasumi.cli

import com.yuiwai.kasumi.core.implementation.{BFS, Node}
import com.yuiwai.kasumi.examples.stations._

import scala.util.chaining._

object Cli {
  def main(args: Array[String]): Unit = {
    stations2()
    // noTyped()
    // typed()
  }

  def stations2(): Unit = {
    println(Data.stations2.route(BFS.typed, Node(Stations.JY01), Node(Stations.JY30)))
  }

  def noTyped(): Unit = {
    val xs = Data.stations

    // 乗り換え or 始発/終点
    val hubStations = xs.nodes
      .filter(n => xs.edges.count(e => e.from == n) == 2)
      .pipe(_.foldLeft(xs)((acc, x) => acc.splice(x)))

    val lineGraph = hubStations.remapFilter(e => Some(e.copy(e.from.value.asInstanceOf[Station].lines, e.to.value.asInstanceOf[Station].lines)))
      .filter(e => e.from != e.to)

    lineGraph
      .route(BFS, "JR山手線", "新京成線")
      .tap(println)

    xs.route(BFS, Station("東京メトロ銀座線", "渋谷"), Station("千葉都市モノレール2号線", "動物公園"))
      .map(r => r.headFromValue[Station].name :: r.headToValue[Station].name :: r.rest.map(_.to.value.asInstanceOf[Station].name).toList)
      .tap(println)
  }

  def typed(): Unit = {
    val xs = Data.stationsTyped

    // 乗り換え or 始発/終点
    val hubStations = xs.nodes
      .filter(n => xs.edges.count(e => e.from == n) == 2)
      .pipe(_.foldLeft(xs)((acc, x) => acc.splice(x)))

    val lineGraph = hubStations
      .map(_.map(n => Node(n.value.line)))
      .filter(e => e.from != e.to)

    lineGraph
      .routeV(BFS.typed, "JR山手線", "新京成線")
      .tap(println)

    xs.routeV(BFS.typed, Station("東京メトロ銀座線", "渋谷"), Station("千葉都市モノレール2号線", "動物公園"))
      .map(r => r.head.fromV.name :: r.head.toV.name :: r.rest.map(_.toV.name).toList)
      .tap(println)
  }
}