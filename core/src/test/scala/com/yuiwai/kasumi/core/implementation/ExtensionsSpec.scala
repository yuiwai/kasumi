package com.yuiwai.kasumi.core.implementation

import utest._

object ExtensionsSpec extends TestSuite {
  import com.yuiwai.kasumi.core.implementation.{Extensions => Ex}
  val tests = Tests {
    "Timer" - {
      "every times" - {
        val t = Timer()
        t.isFired ==> true
        t.update.isFired ==> true
      }
      "interval = 2" - {
        val t = Timer(2)
        t.isFired ==> true
        t.update.isFired ==> false
        t.update.update.isFired ==> true
      }
    }
    "Generator" - {
      val g = Ex.generator(Timer(1), () => Seq(1))
      g.update._2 ==> Seq(1)
    }
  }
}
