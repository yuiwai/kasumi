package com.yuiwai.kasumi.core.implementation

import utest._

object BoardSpec extends TestSuite {
  val tests = Tests {
    val board = Board.empty
    "add edge" - {
      val s = board + (0, 1)
      s.edges.size ==> 1
      s.nodes.size ==> 2
      val b = board ~ (0, 1)
      b.edges.size ==> 2
      b.nodes.size ==> 2
    }
    "splice" - {
      (board + (0, 1))
        .splice(1)
        .edges ==> Set.empty
      (board + (0, 1) + (1, 2))
        .splice(1)
        .edges ==> Set(Edge(Node(0), Node(2)))
    }
    "routes" - {
      "empty board" - {
        board.routes(0, 1) ==> Set.empty
      }
      "single edge" - {
        (board + (0, 1))
          .routes(0, 1) ==> Set(Route(0 -> 1))
      }
      "straight edges" - {
        (board + (0, 1) + (1, 2))
          .routes(0, 2) ==> Set(Route(0 -> 1, 1 -> 2))
      }
      "fan-in/fan-out" - {
        (board + (0, 1) + (1, 2) + (0, 2))
          .routes(0, 2) ==> Set(Route(0 -> 1, 1 -> 2), Route(0 -> 2))
        (board + (0, 1) + (1, 2) + (2, 3) + (1, 4))
          .routes(1, 3) ==> Set(Route(1 -> 2, 2 -> 3))
      }
      "bidirectional" - {
        (board ~ (0, 1) ~ (1, 2))
          .routes(0, 2) ==> Set(Route(0 -> 1, 1 -> 2))
      }
      "complex" - {
        (board ~ (0, 1) ~ (1, 2) ~ (2, 3) ~ (1, 4) ~ (4, 3))
          .routes(0, 3) ==> Set(Route(0 -> 1, 1 -> 2, 2 -> 3), Route(0 -> 1, 1 -> 4, 4 -> 3))
      }
    }
  }
}

object RouteSpec extends TestSuite {
  val tests = Tests {
    val empty = Route.empty
    "empty route" - {
      empty.length ==> 0
    }
    "add edge" - {
      (empty + Edge(1, 2)).get.length ==> 1
      (empty + (Edge(1, 2), Edge(2, 3))).get.length ==> 2
      ((empty + Edge(1, 2)).get + Edge(2, 3)).get.length ==> 2
      (empty + (Edge(1, 2), Edge(4, 3))) ==> None
    }
    "add node" - {
      (empty --> 1) ==> None
      ((empty + Edge(1, 2)).get --> 3).get.length ==> 2
    }
    "concat" - {
      val r1_2 = (empty + Edge(1, 2)).get
      val r2_3 = (empty + Edge(2, 3)).get
      (empty ++ empty).get ==> empty
      (empty ++ r1_2).get ==> r1_2
      (r1_2 ++ empty).get ==> r1_2
      (r1_2 ++ r2_3).get.length ==> 2
      (r2_3 ++ r1_2) ==> None
    }
  }
}