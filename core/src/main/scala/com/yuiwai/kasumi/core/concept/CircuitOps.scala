package com.yuiwai.kasumi.core.concept

trait CircuitOps[This, Bo <: BoardOps[Bo, _, _, _], Br <: BrokerOps[Br, _, _, _]] {
  val board: Bo
  val broker: Br
  def nodes: Set[_] = board.nodes
  def edges: Set[_] = board.edges
  def update(): This
}
