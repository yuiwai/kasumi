package com.yuiwai.kasumi.core.implementation

import utest._

object LayerSpec extends TestSuite {
  val tests = Tests {
    val board = Board.empty ~ (1, 2) ~ (2, 3) ~ (2, 4) ~ (1, 3)
    val nodeLayer = InMemoryNodeLayer(Map(Node(1) -> "Foo", Node(2) -> "Bar", Node(3) -> "Baz"))
    val edgeLayer = InMemoryEdgeLayer(Map(Edge(1, 2) -> 10, Edge(2, 3) -> 5, Edge(2, 4) -> 8, Edge(1, 3) -> 9))
    "node layer" - {
      val byNode: Node[_] => Option[String] = Layer.byNode(nodeLayer)
      byNode(Node(1)) ==> Some("Foo")
      byNode(Node(2)) ==> Some("Bar")
      byNode(Node(3)) ==> Some("Baz")
      byNode(Node(4)) ==> None
    }
    "edge layer" - {
      val byEdge: Edge => Option[Int] = Layer.byEdge(edgeLayer)
      byEdge(Edge(1, 2)) ==> Some(10)
      byEdge(Edge(2, 3)) ==> Some(5)
      byEdge(Edge(2, 4)) ==> Some(8)
      byEdge(Edge(1, 3)) ==> Some(9)
    }
  }
}
