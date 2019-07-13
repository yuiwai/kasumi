package com.yuiwai.kasumi.core.implementation

import utest._

object ExtensionsSpec extends TestSuite {
  import com.yuiwai.kasumi.core.implementation.{Extensions => Ex}
  val tests = Tests {
    "Timer" - {
      "every times" - {
        val t = Timer()
        t.isFired ==> true
        t.updated.isFired ==> true
      }
      "interval = 2" - {
        val t = Timer(2)
        t.isFired ==> true
        t.updated.isFired ==> false
        t.updated.updated.isFired ==> true
      }
    }
    "OnceSwitch" - {
      val s = OnceSwitch()
      s.isFired ==> false
      s.activated.isFired ==> false
      s.activated.updated.isFired ==> true
      s.activated.updated.updated.isFired ==> false
    }
    "Generator" - {
      val g = Ex.generator(Timer(1), () => Seq(1))
      g.update._2 ==> Seq(1)
    }
  }
}
