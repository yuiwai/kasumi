package com.yuiwai.kasumi.core.concept

trait SearchOps [B <: BoardOps[B, N, E, R], N <: NodeOps[_], E <: EdgeOps[E, N], R <: RouteOps[R, N, E]]{
  type Condition = E => Boolean
  def search(board: B, from: N, to: N, condition: Condition = _ => true): Option[R]
}
