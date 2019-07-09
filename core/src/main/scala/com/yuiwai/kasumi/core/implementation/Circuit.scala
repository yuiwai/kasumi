package com.yuiwai.kasumi.core.implementation

import com.yuiwai.kasumi.core.concept.{CircuitOps, EdgeLayerOps, NodeLayerOps}

import scala.language.higherKinds

case class Circuit[F[_]](board: Board, broker: Broker, nodeLayer: NodeLayerOps[F], edgeLayer: EdgeLayerOps[F])
  extends CircuitOps[F, Circuit[F], Board, Broker] {
  override def update(): Circuit[F] = this
}
