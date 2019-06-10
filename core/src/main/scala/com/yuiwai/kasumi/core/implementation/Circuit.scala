package com.yuiwai.kasumi.core.implementation

import com.yuiwai.kasumi.core.concept.CircuitOps

case class Circuit(board: Board, broker: Broker, layers: Seq[Layer])
  extends CircuitOps[Circuit, Board, Broker] {
  override def update(): Circuit = this
}
