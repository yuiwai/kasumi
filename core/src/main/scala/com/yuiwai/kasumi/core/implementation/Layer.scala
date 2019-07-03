package com.yuiwai.kasumi.core.implementation

import com.yuiwai.kasumi.core.concept.{EdgeLayerOps, NodeLayerOps}

import scala.language.higherKinds

object Layer {
  type Id[T] = T
  def byNode[F[_]](nodeLayer: NodeLayerOps[F])(node: Node[_]): F[Option[nodeLayer.Data]] = nodeLayer.find(node.asInstanceOf[nodeLayer.K])
  def byEdge[F[_]](edgeLayer: EdgeLayerOps[F])(edge: Edge): F[Option[edgeLayer.Data]] = edgeLayer.find(edge.asInstanceOf[edgeLayer.K])
  def byRouteN[F[_]](nodeLayer: NodeLayerOps[F])(route: Route)(implicit T: Traverse[Seq]): F[Seq[Option[nodeLayer.Data]]] = {
    val f: Node[_] => F[Option[nodeLayer.Data]] = byNode(nodeLayer)
    T.traverse(route.nodes)(f)
  }
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

trait Traverse[F[_]] {
  def traverse[G[_], A, B](fa: F[A])(f: A => G[B]): G[F[B]]
}