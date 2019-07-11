package com.yuiwai.kasumi.core.implementation

import com.yuiwai.kasumi.core.concept.Expression
import utest._

object CircuitSpec extends TestSuite {
  val tests = Tests {
    val c0 = Circuit.empty(InMemoryNodeLayer.empty, InMemoryEdgeLayer.empty)
    val board = Board.empty + (1, 2) + (2, 3) + (3, 4)
    val c1 = Circuit(board, Broker.empty, InMemoryNodeLayer(Map.empty), InMemoryEdgeLayer(Map.empty))
    "properties" - {
      "empty Circuit" - {
        c0.nodes.size ==> 0
        c0.edges.size ==> 0
      }
      "non empty Circuit" - {
        c1.nodes.size ==> 4
        c1.edges.size ==> 3
      }
    }
    "build and eval" - {
      val c = c1
        .putExpr(Node(1), (i: Int) => i + 1)
        .putCond(Edge(1, 2), (_: Int) % 2 == 0)

      c.eval(Node(1), 10) ==> Some(11)
      c.eval(Node(1), "foo") ==> None
      c.eval(Node(2), 10) ==> None

      c.eval(Edge(1, 2), 10) ==> Some(true)
      c.eval(Edge(1, 2), 11) ==> Some(false)
      c.eval(Edge(1, 2), "foo") ==> Some(false)
      c.eval(Edge(0, 1), 11) ==> None
    }
    "components" - {
      "generator" - {
      }
      "emitter" - {

      }
    }
    "update" - {
      "empty Circuit" - {
        val c = c1.update()
      }
    }
  }
}
