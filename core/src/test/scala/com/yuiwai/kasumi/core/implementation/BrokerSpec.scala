package com.yuiwai.kasumi.core.implementation

import utest._

object BrokerSpec extends TestSuite {
  val tests = Tests {
    val broker = Broker.empty
    "add payload" - {
      "once" - {
        (broker + Payload.reached(1))
          .size ==> 1
      }
      "twice" - {
        (broker + Payload.reached(1) + Payload.reached(1))
          .size ==> 2
      }
    }
    "update" - {
      "empty broker" - {
        broker.update().size ==> 0
      }
      "reached" - {
        (broker + Payload.reached(1))
          .update()
          .size ==> 0
      }
      "unreached" - {
        (broker + Payload(Route(1 -> 2, 2 -> 3)))
          .update()
          .size ==> 1
      }
    }
    "modifier" - {
      "reached" - {
        var received = false
        val b = (broker + Payload.reached(1))
          .update { _ => received = true; Seq.empty }
        received ==> true
        b.size ==> 0
      }
      "unreached" - {
        var received = false
        val b = (broker + Payload(Route(1 -> 2, 2 -> 3)))
          .update { _ => received = true; Seq.empty }
        received ==> true
        b.size ==> 0
      }
    }
  }
}
