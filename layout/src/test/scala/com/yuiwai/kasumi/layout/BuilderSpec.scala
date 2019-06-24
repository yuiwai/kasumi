package com.yuiwai.kasumi.layout

import com.yuiwai.kasumi.core.implementation.Board
import utest._

object BuilderSpec extends TestSuite {
  val tests = Tests {
    "build" - {
      Builder.build(Board.empty) ==> Map.empty

      val board = Board.empty ~ (1, 2) ~ (2, 3) ~ (1, 4) ~ (3, 4)
      Builder.build(board)
    }
  }
}
