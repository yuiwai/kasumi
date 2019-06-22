package com.yuiwai.kasumi.core.concept

trait BoardOps[This <: BoardOps[This, N, E, R], N <: NodeOps[_], E <: EdgeOps[E, N], R <: RouteOps[R, N, E]] {
  type GenEdge[F, T] = (F, T) => E
  type Condition = E => Boolean
  def nodes: Set[N]
  def edges: Set[E]
  def +(edge: E): This
  def +[F, T](from: F, to: T)(implicit genEdge: GenEdge[F, T]): This = this + genEdge(from, to)
  def ~(edge: E): This = (this + edge).asInstanceOf[this.type] + edge.flipped
  def ~[F, T](from: F, to: T)(implicit genEdge: GenEdge[F, T]): This = this ~ genEdge(from, to)
  def route[F, T](from: F, to: T, condition: Condition = _ => true)
    (implicit searcher: SearchOps[This, N, E, R], genNodeF: F => N, genNodeT: T => N): Option[R] =
    searcher.search(this.asInstanceOf[This], from, to, condition)
}
trait NodeOps[+V] {
  def value: V
  def show: String = value.toString
}
trait EdgeOps[This, N <: NodeOps[_]] {
  def from: N
  def to: N
  def nodes: Set[N] = Set(from, to)
  def flipped: This
  def show: String = s"""(${from.show}, ${to.show})"""
}
trait RouteOps[This, N <: NodeOps[_], E <: EdgeOps[E, N]] {
  def head: E
  def tail: This
  def last: Option[N]
  def length: Int
  def nodes: Seq[N] = head.from :: edges.map(e => e.to).toList
  def edges: Seq[E]
  def from: N = edges.head.from
  def visited(node: N): Boolean = edges.exists(_.from == node)
  def -->(node: N): Option[This]
  def ++(that: This): Option[This]
  def show: String = edges.map(_.show).mkString(" --> ")
}

