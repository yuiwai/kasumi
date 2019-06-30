package com.yuiwai.kasumi.core.concept

trait BrokerOps[This, N <: NodeOps[_], E <: EdgeOps[N], P <: PayloadOps[P, _, N, E, _]] {
  def payloads: Seq[P]
  def size: Int = payloads.size
  def +(payload: P): This
  def update(modifier: Seq[P] => Seq[P]): This
  def update(): This = update(p => p)
}

trait PayloadOps[This, V, N <: NodeOps[_], E <: EdgeOps[N], R <: RouteOps[N]] {
  def isReached: Boolean = pos match {
    case Left(n) => n == route.head.to
    case _ => false
  }
  def value: V
  def pos: Either[N, E]
  def route: R
  def update: This
}
