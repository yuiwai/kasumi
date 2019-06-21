package com.yuiwai.kasumi.cli

import com.yuiwai.kasumi.examples.stations._
import scala.util.chaining._

object Cli {
  def main(args: Array[String]): Unit = {
    val xs = Data.stations
    xs.nodes
      .filter(n => xs.edges.count(e => e.from == n) == 2)
      .pipe(_.foldLeft(xs)((acc, x) => acc.splice(x)))
      .pipe(_.nodes.map(_.value.asInstanceOf[Station].name))
      .tap(println)
  }
}