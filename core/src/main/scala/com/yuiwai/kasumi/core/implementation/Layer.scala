package com.yuiwai.kasumi.core.implementation

import com.yuiwai.kasumi.core.concept.{EdgeLayerOps, NodeLayerOps}
import com.yuiwai.kasumi.core.implementation.Layer.Id

import scala.language.higherKinds

object Layer {
  type Id[T] = T
  def byNode[F[_], T](nodeLayer: NodeLayerOps[F, T])(node: Node[_]): F[Option[T]] = nodeLayer.find(node.asInstanceOf[nodeLayer.K])
  def byEdge[F[_], T](edgeLayer: EdgeLayerOps[F, T])(edge: Edge): F[Option[T]] = edgeLayer.find(edge.asInstanceOf[edgeLayer.K])
  def byRouteN[F[_] : Applicative, T](nodeLayer: NodeLayerOps[F, T])(route: Route)
    (implicit T: Traverse[Seq]): F[Seq[Option[T]]] =
    T.traverse(route.nodes)(byNode(nodeLayer)(_))
  def byRouteE[F[_] : Applicative, T](edgeLayer: EdgeLayerOps[F, T])(route: Route)
    (implicit T: Traverse[Seq]): F[Seq[Option[T]]] =
    T.traverse(route.edges)(byEdge(edgeLayer)(_))
}
final case class InMemoryNodeLayer[T](dataMap: Map[Node[_], T]) extends NodeLayerOps[Layer.Id, T] {
  override type This = InMemoryNodeLayer[T]
  override type K = Node[_]
  override def find(node: Node[_]): Id[Option[T]] = dataMap.get(node)
  override def findMap[R](node: Node[_])(f: T => Option[R]): Id[Option[R]] = dataMap.get(node).flatMap(f)
  override def put(node: Node[_], data: T): InMemoryNodeLayer[T] = copy(dataMap.updated(node, data))
}
object InMemoryNodeLayer {
  def empty[T] = apply(Map.empty[Node[_], T])
}
final case class InMemoryEdgeLayer[T](dataMap: Map[Edge, T]) extends EdgeLayerOps[Layer.Id, T] {
  override type This = InMemoryEdgeLayer[T]
  override type K = Edge
  override def find(edge: Edge): Id[Option[T]] = dataMap.get(edge)
  override def findMap[R](edge: Edge)(f: T => Option[R]): Id[Option[R]] = dataMap.get(edge).flatMap(f)
  override def put(edge: Edge, data: T): InMemoryEdgeLayer[T] = copy(dataMap.updated(edge, data))
}
object InMemoryEdgeLayer {
  def empty[T] = apply(Map.empty[Edge, T])
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
trait Monad[F[_]] extends Applicative[F] {
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
}
object Monad {
  import Layer.Id
  implicit val idMonad = new Monad[Id] {
    override def flatMap[A, B](fa: Id[A])(f: A => Id[B]): Id[B] = f(fa)
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
  implicit val setTraverse: Traverse[Set] = new Traverse[Set] {
    override def traverse[G[_] : Applicative, A, B](fa: Set[A])(f: A => G[B]): G[Set[B]] = {
      val applicative = implicitly[Applicative[G]]
      fa.foldLeft(applicative.pure(Set.empty[B])) {
        (acc, a) =>
          applicative.map2(acc, f(a))(_ + _)
      }
    }
  }
}