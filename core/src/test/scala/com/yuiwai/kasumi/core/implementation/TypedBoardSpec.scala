package com.yuiwai.kasumi.core.implementation

import com.yuiwai.kasumi.core.concept.SearchOps
import utest._

import scala.util.chaining._

object TypedBoardSpec extends TestSuite {
  val tests = Tests {
    val board = TypedBoard.empty[Int]
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
        .splice(Node(1))
        .edges ==> Set.empty
      (board + (0, 1) + (1, 2))
        .splice(Node(1))
        .edges ==> Set(TypedEdge(Node(0), Node(2)))
    }
    "route" - {
      val bfs = new TypedBFSImpl[Int] {
      }
      "empty board" - {
        board.route(bfs, Node(0), Node(1)) ==> None
      }
      "single edge" - {
        (board + (0, 1))
          .route(bfs, Node(0), Node(1)) ==> Some(TypedRoute(0 -> 1))
      }
      "straight edges" - {
        (board + (0, 1) + (1, 2))
          .routeV(bfs, 0, 2) ==> Some(TypedRoute(0 -> 1, 1 -> 2))
      }
      "fan-in/fan-out" - {
        (board + (0, 1) + (1, 2) + (0, 2))
          .routeV(bfs, 0, 2) ==> Some(TypedRoute(0 -> 2))
        (board + (0, 1) + (1, 2) + (2, 3) + (1, 4))
          .routeV(bfs, 1, 3) ==> Some(TypedRoute(1 -> 2, 2 -> 3))
      }
      "bidirectional" - {
        (board ~ (0, 1) ~ (1, 2))
          .routeV(bfs, 0, 2) ==> Some(TypedRoute(0 -> 1, 1 -> 2))
      }
      "complex" - {
        (board ~ (0, 1) ~ (1, 2) ~ (2, 3) ~ (1, 4) ~ (4, 3))
          .routeV(bfs, 0, 3).get.length ==> 3
      }
      "with condition" - {
        (board ~ (2, 3) ~ (3, 4) ~ (2, 6) ~ (6, 8) ~ (8, 4)).tap { b =>
          b.routeV(bfs, 2, 4, e => e.from.value % 2 == 0) ==>
            Some(TypedRoute(2 -> 6, 6 -> 8, 8 -> 4))
        }
      }
    }
  }
}
