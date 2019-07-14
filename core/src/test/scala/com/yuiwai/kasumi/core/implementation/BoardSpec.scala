package com.yuiwai.kasumi.core.implementation

import utest._

import scala.util.chaining._

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
    "edgesFrom" - {
      board ~ (0, 1) ~ (1, 2) tap {
        _.edgesFrom(Node(1)) ==> Set(Edge(1, 2), Edge(1, 0))
      }
    }
    "filter" - {
      board ~ (0, 1) + (1, 2) + (1, 3) tap {
        _.filter(_.from.value != 1).edges ==> Set(Edge(0, 1))
      }
    }
    "splice" - {
      (board + (0, 1))
        .splice(1)
        .edges ==> Set.empty
      (board + (0, 1) + (1, 2))
        .splice(1)
        .edges ==> Set(Edge(Node(0), Node(2)))
    }
    "route" - {
      "empty board" - {
        board.route(BFS, 0, 1) ==> None
      }

      "single edge" - {
        (board + (0, 1))
          .route(BFS, 0, 1) ==> Some(Route(0 -> 1))
      }
      "straight edges" - {
        (board + (0, 1) + (1, 2))
          .route(BFS, 0, 2) ==> Some(Route(0 -> 1, 1 -> 2))
      }
      "fan-in/fan-out" - {
        (board + (0, 1) + (1, 2) + (0, 2))
          .route(BFS, 0, 2) ==> Some(Route(0 -> 2))
        (board + (0, 1) + (1, 2) + (2, 3) + (1, 4))
          .route(BFS, 1, 3) ==> Some(Route(1 -> 2, 2 -> 3))
      }
      "bidirectional" - {
        (board ~ (0, 1) ~ (1, 2))
          .route(BFS, 0, 2) ==> Some(Route(0 -> 1, 1 -> 2))
      }
      "complex" - {
        (board ~ (0, 1) ~ (1, 2) ~ (2, 3) ~ (1, 4) ~ (4, 3))
          .route(BFS, 0, 3).get.length ==> 3
      }
      "with condition" - {
        (board ~ (2, 3) ~ (3, 4) ~ (2, 6) ~ (6, 8) ~ (8, 4)).tap { b =>
          b.route(BFS, 2, 4, e => e.from.value.asInstanceOf[Int] % 2 == 0) ==>
            Some(Route(2 -> 6, 6 -> 8, 8 -> 4))
        }
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