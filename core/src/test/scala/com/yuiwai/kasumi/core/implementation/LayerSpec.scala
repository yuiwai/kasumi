package com.yuiwai.kasumi.core.implementation

import com.yuiwai.kasumi.core.implementation.Layer.Id
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
    "route" - {
      val byRouteN: Route => Id[Seq[Option[String]]] = Layer.byRouteN(nodeLayer)
      byRouteN(board.route(BFS, Node(1), Node(2)).get) ==> Seq(Some("Foo"), Some("Bar"))
      byRouteN(board.route(BFS, Node(1), Node(4)).get) ==> Seq(Some("Foo"), Some("Bar"), None)

      val byRouteE: Route => Id[Seq[Option[Int]]] = Layer.byRouteE(edgeLayer)
      byRouteE(board.route(BFS, Node(1), Node(2)).get) ==> Seq(Some(10))
      byRouteE(board.route(BFS, Node(1), Node(4)).get) ==> Seq(Some(10), Some(8))
    }
    "find" - {
      nodeLayer.find(Node(0)) ==> None
      nodeLayer.find(Node(1)) ==> Some("Foo")
      nodeLayer.findMap(Node(1))(i => Some(i + 1)) ==> Some("Foo1")

      edgeLayer.find(Edge(0, 1)) ==> None
      edgeLayer.find(Edge(1, 2)) ==> Some(10)
      edgeLayer.findMap(Edge(1, 2))(i => Some(i + 1)) ==> Some(11)
    }
    "put" - {
      nodeLayer.put(Node(0), "Baz")
        .find(Node(0)) ==> Some("Baz")

      edgeLayer.put(Edge(0, 1), 100)
        .find(Edge(0, 1)) ==> Some(100)
    }
  }
}
