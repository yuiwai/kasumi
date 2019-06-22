package com.yuiwai.kasumi.core.implementation

import com.yuiwai.kasumi.core.concept.{BoardOps, EdgeOps, NodeOps, RouteOps}

import scala.util.chaining._

final case class Board(edges: Set[Edge]) extends BoardOps[Board, Node[_], Edge, Route] {
  override def nodes: Set[Node[_]] = edges.flatMap(_.nodes)
  override def +(edge: Edge): Board = copy(edges + edge)
  def splice(value: Node[_]): Board = {
    edges.foldLeft(Set.empty[Node[_]] -> Set.empty[Node[_]]) { case (acc@(fs, ts), e) =>
      e match {
        case Edge(from, `value`) => (fs + from, ts)
        case Edge(`value`, to) => (fs, ts + to)
        case _ => acc
      }
    }.pipe { case (ns1, ns2) =>
      edges.filterNot(e => e.from == value || e.to == value) ++ ns1.flatMap(f => ns2.collect {
        case t if f != t => Edge(f, t)
      })
    }.pipe(es => Board(es))
  }

}
object Board {
  def empty: Board = apply(Set.empty)
}
final case class Node[V](value: V) extends NodeOps[V]
object EmptyNode extends NodeOps[Nothing] {
  override def value: Nothing = sys.error("cannot get value from EmptyNode.")
}
object Node {
  implicit val genNode: Any => Node[_] = v => apply(v)
  def empty: NodeOps[Nothing] = EmptyNode
}
sealed case class Edge(from: Node[_], to: Node[_]) extends EdgeOps[Edge, Node[_]] {
  override def flipped: Edge = Edge(to, from)
}
object EmptyEdge extends Edge(EmptyNode, EmptyNode)
object Edge {
  implicit def gen[F, T](from: F, to: T): Edge = apply(from, to)
  def empty: EmptyEdge.type = EmptyEdge
  def apply[F, T](from: F, to: T): Edge = apply(Node(from), Node(to))
}

sealed case class Route(head: Edge, rest: Seq[Edge]) extends RouteOps[Route, Node[_], Edge] {
  def length: Int = rest.length + 1
  def headFromValue[T]: T = head.from.value.asInstanceOf[T]
  def headToValue[T]: T = head.to.value.asInstanceOf[T]
  override def tail: Route = {
    require(rest.nonEmpty)
    copy(rest.head, rest.tail)
  }
  def last: Option[Node[_]] = nodes.lastOption
  override def edges: Seq[Edge] = head +: rest
  def +(edge: Edge): Option[Route] = head match {
    case _ if last.get == edge.from => Some(copy(rest = rest :+ edge))
    case _ => None
  }
  def +(edge: Edge, edges: Edge*): Option[Route] = (edge +: edges).foldLeft[Option[Route]](Some(this)) { (acc, e) =>
    acc match {
      case Some(r) => r + e
      case None => None
    }
  }
  def -->(node: Node[_]): Option[Route] = last.flatMap { n =>
    this + Edge(n, node)
  }
  override def ++(that: Route): Option[Route] =
    that match {
      case EmptyRoute => Some(this)
      case _ => last
        .filter(_ == that.from)
        .map(_ => copy(rest = rest ++ that.edges))
    }
}
object EmptyRoute extends Route(EmptyEdge, Seq.empty) {
  override def length: Int = 0
  override def nodes: Seq[Node[_]] = Seq.empty
  override def last: Option[Node[_]] = None
  override def +(edge: Edge): Option[Route] = Some(Route(edge))
  override def -->(node: Node[_]): Option[Route] = None
  override def ++(that: Route): Option[Route] = Some(that)
}

object Route {
  implicit val gen: Edge => Route = (edge: Edge) => apply(edge, Seq.empty)
  implicit val genFromSeq: Seq[Edge] => Route = (edges: Seq[Edge]) => apply(edges.head, edges.tail)
  implicit val ord: Ordering[Route] = (x: Route, y: Route) => x.edges.size.compare(y.edges.size)
  def empty: EmptyRoute.type = EmptyRoute
  def apply(edge: Edge): Route = apply(edge, Seq.empty)
  def apply(e1: (Any, Any), es: (Any, Any)*): Route = apply(Edge(e1._1, e1._2), es.map(e => Edge(e._1, e._2)))
}
