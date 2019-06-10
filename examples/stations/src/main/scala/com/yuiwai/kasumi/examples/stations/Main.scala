package com.yuiwai.kasumi.examples.stations

import com.yuiwai.kasumi.core.implementation._
import scala.util.chaining._

object Main {
  def main(args: Array[String]): Unit = {
    val board = Data.stations
    // println(board.nodes)
    println(board.route(Station("東葉高速鉄道", "八千代緑が丘"), Station("新京成線", "初富")))
  }
}

final case class Station(line: String, name: String)

object Data {
  val lines: Map[String, List[String]] = Map(
    "東葉高速鉄道" ->
      List("西船橋", "東海神", "飯山満", "北習志野", "船橋日大前", "八千代緑が丘", "八千代中央", "村上", "東葉勝田台"),
    "新京成線" ->
      List("松戸", "上本郷", "新松戸", "みのり台", "八柱", "常磐平", "五香", "元山", "くぬぎ山", "北初富", "新鎌ヶ谷", "初富",
        "鎌ヶ谷大仏", "二和向台", "三咲", "滝不動", "高根公団", "高根木戸", "北習志野", "習志野", "薬園台", "前原", "新津田沼", "京成津田沼")
  )
  val connections = List(("東葉高速鉄道", "北習志野") -> ("新京成線", "北習志野"))
  def stations: Board = lines.foldLeft(Board.empty) { case (board, (lineName, stations)) =>
    stations.sliding(2).foldLeft(board) { (acc, xs) =>
      xs match {
        case h :: t :: Nil => acc ~ (Station(lineName, h), Station(lineName, t))
        case _ => acc
      }
    }.pipe { board =>
      connections.foldLeft(board) {
        case (acc, ((l1, s1), (l2, s2))) => acc ~ (Station(l1, s1), Station(l2, s2))
      }
    }
  }
}
