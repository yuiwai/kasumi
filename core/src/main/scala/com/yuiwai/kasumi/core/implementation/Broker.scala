package com.yuiwai.kasumi.core.implementation

import Payload.Pos
import com.yuiwai.kasumi.core.concept._

final case class Broker(payloads: Seq[Payload[_]])
  extends BrokerOps[Broker, Node[_], Edge, Payload[_]] {
  override def +(payload: Payload[_]): Broker = copy(payloads = payloads :+ payload)
  override def update(modifier: Seq[Payload[_]] => Seq[Payload[_]]): Broker = {
    val xs = payloads.map(_.update)
        .groupBy(_.pos)
        .values
        .flatMap(modifier)
        .toSeq

    copy(payloads = xs.filter(!_.isReached))
  }
}
object Broker {
  def empty: Broker = Broker(Seq.empty)
}

final case class Payload[V](value: V, pos: Pos, route: Route) extends PayloadOps[Payload[_], V, Node[_], Edge, Route] {
  require {
    pos match {
      case Left(n) => route.head.from == n
      case Right(e) => route.head.from == e.to
    }
  }
  def show: String = s"$value @ ${pos.map(_.show).getOrElse(pos.left.get.show)} <${route.show}>"
  override def update: Payload[_] = {
    pos match {
      case Left(n) =>
        if (n == route.head.to) copy(pos = Left(route.head.to))
        else if (route.rest.isEmpty) copy(pos = Right(route.head))
        else copy(pos = Right(route.head), route = route.tail)
      case Right(e) => copy(pos = Left(e.to))
    }
  }
}
object Payload {
  type Pos = Either[Node[_], Edge]
  def apply[V](value: V, pos: Node[_], route: Route): Payload[V] = apply(value, Left(pos), route)
  def apply[V](value: V, pos: Edge, route: Route): Payload[V] = apply(value, Right(pos), route)
  def apply(route: Route): Payload[Unit] = apply((), route.head.from, route)
  def reached[V](value: V): Payload[V] = apply(value, EmptyNode, Route(Edge.empty))
}
