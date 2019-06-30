package com.yuiwai.kasumi.core.implementation

import com.yuiwai.kasumi.core.concept._

import scala.util.chaining._

final case class TypedBoard[V](edges: Set[TypedEdge[V]]) extends TypedBoardOps[V] {
  override type This = TypedBoard[V]
  override type E = TypedEdge[V]
  override type R = TypedRoute[V]
  override def nodes: Set[NodeOps[V]] = edges.flatMap(_.nodes)
  override def remapFilter(f: TypedEdge[V] => Option[TypedEdge[V]]): TypedBoard[V] = ???
  override def +(edge: TypedEdge[V]): TypedBoard[V] = copy(edges + edge)
  override def splice(node: NodeOps[V]): TypedBoard[V] = {
    edges.foldLeft(Set.empty[Node[V]] -> Set.empty[Node[V]]) { case (acc@(fs, ts), e) =>
      e match {
        case TypedEdge(from, `node`) => (fs + from, ts)
        case TypedEdge(`node`, to) => (fs, ts + to)
        case _ => acc
      }
    }.pipe { case (ns1, ns2) =>
      edges.filterNot(e => e.from == node || e.to == node) ++ ns1.flatMap(f => ns2.collect {
        case t if f != t => TypedEdge(f, t)
      })
    }.pipe(es => TypedBoard(es))
  }
  override def route(
    searcher: SearchOps[NodeOps[V]],
    from: NodeOps[V],
    to: NodeOps[V],
    condition: Condition): Option[TypedRoute[V]] =
    searcher.search(this.asInstanceOf[searcher.B], from, to, condition.asInstanceOf[searcher.Condition])
      .map(_.asInstanceOf[R])
  def routeV(searcher: SearchOps[NodeOps[V]], from: V, to: V, condition: Condition = _ => true): Option[TypedRouteOps[V]] =
    route(searcher, Node(from), Node(to), condition)
}
object TypedBoard {
  def empty[V] = TypedBoard(Set.empty[TypedEdge[V]])
}

final case class TypedEdge[V](from: Node[V], to: Node[V]) extends TypedEdgeOps[V] {
  override type This = TypedEdge[V]
  override def flipped: TypedEdge[V] = copy(to, from)
}
object TypedEdge {
  implicit def gen[V](from: V, to: V): TypedEdge[V] = apply(from, to)
  def apply[V](from: V, to: V): TypedEdge[V] = apply(Node(from), Node(to))
}

final case class TypedRoute[V](head: TypedEdge[V], rest: Seq[TypedEdge[V]]) extends TypedRouteOps[V] {
  override type This = TypedRoute[V]
  override type E = TypedEdge[V]
  override def tail: This = {
    require(rest.nonEmpty)
    copy(rest.head, rest.tail)
  }
  override def last: Option[NodeOps[V]] = nodes.lastOption
  override def length: Int = rest.length + 1
  override def edges: Seq[E] = head +: rest
  def +(edge: E, edges: E*): Option[This] = (edge +: edges).foldLeft[Option[This]](Some(this)) { (acc, e) =>
    acc match {
      case Some(r) => r + e
      case None => None
    }
  }
  override def -->(node: NodeOps[V]): Option[This] = last.flatMap { n =>
    this + TypedEdge(n.asInstanceOf[Node[V]], node.asInstanceOf[Node[V]])
  }
  override def ++(that: This): Option[This] = ???
}
object TypedRoute {
  def apply[V](e1: (V, V), es: (V, V)*): TypedRoute[V] =
    apply(TypedEdge[V](e1._1, e1._2), es.map(e => TypedEdge[V](e._1, e._2)))
}
