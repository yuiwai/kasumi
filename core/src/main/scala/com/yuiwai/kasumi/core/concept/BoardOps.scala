package com.yuiwai.kasumi.core.concept

trait BoardOps[N <: NodeOps[_]] {
  type This <: BoardOps[N]
  type E <: EdgeOps[N]
  type R <: RouteOps[N]
  type GenEdge[F, T] = (F, T) => E
  type Condition = E => Boolean
  def nodes: Set[N]
  def edges: Set[E]
  def remapFilter(f: E => Option[E]): This
  def filter(f: E => Boolean): This = remapFilter(e => Some(e).filter(f))
  def +(edge: E): This
  def +[F, T](from: F, to: T)(implicit genEdge: GenEdge[F, T]): This = this + genEdge(from, to)
  def ~(edge: E): This = (this + edge).asInstanceOf[this.type] + edge.flipped.asInstanceOf[E]
  def ~[F, T](from: F, to: T)(implicit genEdge: GenEdge[F, T]): This = this ~ genEdge(from, to)
  def splice(node: N): This
  def route(searcher: SearchOps[N], from: N, to: N, condition: Condition = _ => true): Option[R]
}
trait NodeOps[+V] {
  def value: V
  def show: String = value.toString
}
trait EdgeOps[N <: NodeOps[_]] {
  type This <: EdgeOps[N]
  def from: N
  def to: N
  def nodes: Set[N] = Set(from, to)
  def flipped: This
  def show: String = s"""(${from.show}, ${to.show})"""
}
trait RouteOps[N <: NodeOps[_]] {
  type This <: RouteOps[N]
  type E <: EdgeOps[N]
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

