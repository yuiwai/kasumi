package com.yuiwai.kasumi.demo

import com.yuiwai.kasumi.core.implementation._

object Main {
  def main(args: Array[String]): Unit = {
    val board = Board.empty ~
      (1, 2) ~
      ("foo", "bar") ~
      ("bar", "baz") ~
      ("foo", 1) ~
      ("bar", 2) ~
      ("foo", 2) + (2, 3) + ("foo", 3) + (3, 2)
    board
      .routes("foo", 2)
      .map(_.show)
      .foreach(println)

    val broker = Broker.empty +
      Payload(1, Node("foo"), board.route("foo", 3).get) +
      Payload(2, Edge(1, 2), board.route(2, "foo").get)
    broker
      .payloads
      .map(_.show)
      .foreach(println)
  }
}
