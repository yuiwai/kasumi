package com.yuiwai.kasumi.core.implementation

import utest._

object CircuitSpec extends TestSuite {
  val tests = Tests {
    val circuit = Circuit(Board.empty, Broker.empty, InMemoryNodeLayer(Map.empty), InMemoryEdgeLayer(Map.empty))
    "properties" - {
      "empty Circuit" - {
        circuit.nodes.size ==> 0
        circuit.edges.size ==> 0
      }
    }
    "update" - {
      "empty Circuit" - {
        val c = circuit.update()
      }
    }
  }
}
