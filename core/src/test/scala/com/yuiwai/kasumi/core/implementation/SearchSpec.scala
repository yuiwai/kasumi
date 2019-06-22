package com.yuiwai.kasumi.core.implementation

import utest._

object SearchSpec extends TestSuite {
  val tests = Tests {
    val board = Board.empty ~ (1, 2) ~ (1, 3) ~ (2, 3) ~ (2, 4) ~ (3, 4) ~ (3, 6) ~ (4, 5) ~ (5, 6)
    "bfs" - {
      BFS.search(board, Node(1), Node(2)) ==> Some(Route((1, 2)))
      BFS.search(board, Node(1), Node(6)) ==> Some(Route((1, 3), (3, 6)))
      BFS.search(board, Node(3), Node(5)) ==> Some(Route((3, 6), (6, 5)))
      BFS.search(board, Node(5), Node(1)).nonEmpty ==> true
      BFS.search(board, Node(1), Node(7)) ==> None
      BFS.search(board, Node(7), Node(1)) ==> None
    }
  }
}
