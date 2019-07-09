package com.yuiwai.kasumi.core.concept

import scala.language.higherKinds

trait CircuitOps[F[_], This, Bo <: BoardOps[_], Br <: BrokerOps[Br, _, _, _]] {
  val board: Bo
  val broker: Br
  val nodeLayer: NodeLayerOps[F]
  val edgeLayer: EdgeLayerOps[F]
  def nodes: Set[_] = board.nodes
  def edges: Set[_] = board.edges
  def update(): This
}
