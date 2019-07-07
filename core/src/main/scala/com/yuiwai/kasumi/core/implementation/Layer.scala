package com.yuiwai.kasumi.core.implementation

import com.yuiwai.kasumi.core.concept.{EdgeLayerOps, NodeLayerOps}

import scala.language.higherKinds

object Layer {
  type Id[T] = T
  def byNode[F[_]](nodeLayer: NodeLayerOps[F])(node: Node[_]): F[Option[nodeLayer.Data]] = nodeLayer.find(node.asInstanceOf[nodeLayer.K])
  def byEdge[F[_]](edgeLayer: EdgeLayerOps[F])(edge: Edge): F[Option[edgeLayer.Data]] = edgeLayer.find(edge.asInstanceOf[edgeLayer.K])
  def byRouteN[F[_] : Applicative](nodeLayer: NodeLayerOps[F])(route: Route)
    (implicit T: Traverse[Seq]): F[Seq[Option[nodeLayer.Data]]] =
    T.traverse(route.nodes)(byNode(nodeLayer)(_))
  def byRouteE[F[_] : Applicative](edgeLayer: EdgeLayerOps[F])(route: Route)
    (implicit T: Traverse[Seq]): F[Seq[Option[edgeLayer.Data]]] =
    T.traverse(route.edges)(byEdge(edgeLayer)(_))
}
final case class InMemoryNodeLayer[T](data: Map[Node[_], T]) extends NodeLayerOps[Layer.Id] {
  override type Data = T
  override type K = Node[_]
  override def find(node: Node[_]): Option[T] = data.get(node)
}
final case class InMemoryEdgeLayer[T](data: Map[Edge, T]) extends EdgeLayerOps[Layer.Id] {
  override type Data = T
  override type K = Edge
  override def find(edge: Edge): Option[T] = data.get(edge)
}

trait Apply[F[_]] {
  def ap[A, B](f: F[A => B])(fa: F[A]): F[B]
  def map[A, B](fa: F[A])(f: A => B): F[B]
  def map2[A, B, Z](fa: F[A], fb: F[B])(f: (A, B) => Z): F[Z] = map(product(fa, fb))(f.tupled)
  def product[A, B](fa: F[A], fb: F[B]): F[(A, B)] = ap(map(fa)(a => (b: B) => a -> b))(fb)
}
trait Applicative[F[_]] extends Apply[F] {
  def pure[T](value: T): F[T]
  override def map[A, B](fa: F[A])(f: A => B): F[B] = ap(pure(f))(fa)
}
object Applicative {
  // TODO Idの置き場
  import Layer.Id
  implicit val idApplicative: Applicative[Id] = new Applicative[Id] {
    override def pure[T](value: T): Id[T] = value
    override def ap[A, B](f: Id[A => B])(fa: Id[A]): Id[B] = f(fa)
  }
}
trait Traverse[F[_]] {
  def traverse[G[_] : Applicative, A, B](fa: F[A])(f: A => G[B]): G[F[B]]
}
object Traverse {
  implicit val seqTraverse: Traverse[Seq] = new Traverse[Seq] {
    override def traverse[G[_] : Applicative, A, B](fa: Seq[A])(f: A => G[B]): G[Seq[B]] = {
      val applicative = implicitly[Applicative[G]]
      fa.foldLeft(applicative.pure(Seq.empty[B])) {
        (acc, a) =>
          applicative.map2(acc, f(a))(_ :+ _)
      }
    }
  }
}