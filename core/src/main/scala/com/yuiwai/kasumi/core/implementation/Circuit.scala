package com.yuiwai.kasumi.core.implementation

import com.yuiwai.kasumi.core.concept.{CircuitOps, LayerOps}

import scala.language.higherKinds

case class Circuit[F[_]](board: Board, broker: Broker, layers: Seq[LayerOps[F]])
  extends CircuitOps[Circuit[F], Board, Broker] {
  override def update(): Circuit[F] = this
}
