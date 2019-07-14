package com.yuiwai.kasumi.core.implementation

import utest._

object CircuitSpec extends TestSuite {
  val tests = Tests {
    val c0 = Circuit.empty(InMemoryNodeLayer.empty, InMemoryEdgeLayer.empty, InMemoryNodeLayer.empty)
    val board = Board.empty + (1, 2) + (2, 3) + (3, 4)
    val c1 = Circuit(board, Current.empty, InMemoryNodeLayer.empty, InMemoryEdgeLayer.empty, InMemoryNodeLayer.empty)
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
        .putCalc(Node(1), (i: Int) => i + 1)
        .putCond(Edge(1, 2), (_: Int) % 2 == 0)

      c.eval(Node(1), 10) ==> Some(11)
      c.eval(Node(1), "foo") ==> None
      c.eval(Node(2), 10) ==> None

      c.eval(Edge(1, 2), 10) ==> Some(true)
      c.eval(Edge(1, 2), 11) ==> Some(false)
      c.eval(Edge(1, 2), "foo") ==> Some(false)
      c.eval(Edge(0, 1), 11) ==> None
    }
    "current/particle" - {
      "no calculation" - {
        val c = c1
          .putCond(Edge(1, 2), (_: Int) % 2 == 0)

        c.putData(Node(1), 1).current.size ==> 0
        c.putData(Node(1), 2).current.size ==> 1
      }

      "no condition" - {
        val c = c1
          .putCalc(Node(1), (i: Int) => i + 1)

        c.putData(Node(1), 1).current.size ==> 1
        c.putData(Node(1), 2).current.size ==> 1
      }

      "calculation and condition" - {
        val c = c1
          .putCalc(Node(1), (i: Int) => i + 1)
          .putCond(Edge(1, 2), (_: Int) % 2 == 0)

        c.putData(Node(1), 1).current.size ==> 1
        c.putData(Node(1), 2).current.size ==> 0
      }
    }
    "components" - {
      "generator" - {
      }
      "emitter" - {

      }
    }
    "update" - {
      "empty Circuit" - {
        c1.update() ==> c1
        // c1.putGen()
      }
    }
  }
}
