package com.yuiwai.kasumi.core.concept

trait BoardOps[This, N <: NodeOps[_], E <: EdgeOps[E, N], R <: RouteOps[R, N, E]] {
  type GenEdge[F, T] = (F, T) => E
  type Condition = E => Boolean
  def nodes: Set[N]
  def edges: Set[E]
  def +(edge: E): This
  def +[F, T](from: F, to: T)(implicit genEdge: GenEdge[F, T]): This = this + genEdge(from, to)
  def ~(edge: E): This = (this + edge).asInstanceOf[this.type] + edge.flipped
  def ~[F, T](from: F, to: T)(implicit genEdge: GenEdge[F, T]): This = this ~ genEdge(from, to)
  private def shortestRoute[F, T](from: F, to: T, condition: Condition)
    (implicit genRoute: Seq[E] => R, ord: Ordering[R]): Option[R] = {
    routes(from, to, condition) match {
      case xs if xs.isEmpty => None
      case xs => Some(xs.min)
    }
  }
  private def longestRoute[F, T](from: F, to: T, condition: Condition)
    (implicit genRoute: Seq[E] => R, ord: Ordering[R]): Option[R] = {
    routes(from, to, condition) match {
      case xs if xs.isEmpty => None
      case xs => Some(xs.max)
    }
  }
  def route[F, T](from: F, to: T, condition: Condition = _ => true)
    (implicit genRoute: Seq[E] => R, ord: Ordering[R]): Option[R] = shortestRoute(from, to, condition)
  def routes[F, T](from: F, to: T, condition: Condition = _ => true)(implicit genRoute: Seq[E] => R): Set[R] = {
    edges
      .filter(_.from.value == from)
      .flatMap(e => routes(e :: Nil, Set.empty, Set.empty, to, condition))
  }
  private def routes[T](stack: List[E], reached: Set[R], visited: Set[E], to: T, condition: Condition)
    (implicit genRoute: Seq[E] => R): Set[R] = {
    if (stack.isEmpty) reached
    else if (stack.head.to.value == to) routes(stack.tail, reached ++ Set(genRoute(stack.reverse)), visited, to, condition)
    else edges.filter(e => e.from == stack.head.to && !visited.contains(e) && e.flipped != stack.head && condition(e)) match {
      case es if es.nonEmpty => routes(es.head :: stack, reached, visited + es.head, to, condition)
      case _ => routes(stack.tail, reached, visited, to, condition)
    }
  }
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
