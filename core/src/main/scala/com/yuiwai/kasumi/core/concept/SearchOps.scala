package com.yuiwai.kasumi.core.concept

trait SearchOps[Node <: NodeOps[_]] {
  type Condition = E => Boolean
  type N = Node
  type B <: BoardOps[N]
  type E = B#E
  type R = B#R
  def search(board: B, from: N, to: N, condition: Condition = _ => true): Option[R]
}
trait TypedSearchOps[N <: NodeOps[V], V] extends SearchOps[N]
