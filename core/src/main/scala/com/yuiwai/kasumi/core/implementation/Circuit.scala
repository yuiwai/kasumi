package com.yuiwai.kasumi.core.implementation

import com.yuiwai.kasumi.core.concept._

import scala.language.higherKinds
import scala.util.Try

case class Circuit[F[_] : Applicative](
  board: Board, broker: Broker, nodeLayer: NodeLayerOps[F, Expression[_, _]], edgeLayer: EdgeLayerOps[F, Condition[_]])
  extends CircuitOps[F, Circuit[F], Board, Broker] {
  override def putExpr[P, R](node: NodeOps[_], expr: Expression[P, R]): Circuit[F] =
    copy(nodeLayer = nodeLayer.put(node.asInstanceOf[nodeLayer.K], expr).asInstanceOf[NodeLayerOps[F, Expression[_, _]]])
  override def putCond[P](edge: EdgeOps[_], cond: Condition[P]): Circuit[F] =
    copy(edgeLayer = edgeLayer.put(edge.asInstanceOf[edgeLayer.K], cond).asInstanceOf[EdgeLayerOps[F, Condition[_]]])
  override def eval[P, R](node: NodeOps[_], param: P): F[Option[R]] = Try {
    nodeLayer.findMap(node.asInstanceOf[nodeLayer.K]) {
      case expr: Expression[P, R] => Some(expr.eval(param))
      case _ => None
    }
  }.getOrElse(implicitly[Applicative[F]].pure(None))
  override def eval[P](edge: EdgeOps[_], param: P): F[Option[Boolean]] = Try {
    edgeLayer.findMap(edge.asInstanceOf[edgeLayer.K]) {
      case cond: Condition[P] => Some(cond.eval(param))
      case _ => None
    }
  }.getOrElse(implicitly[Applicative[F]].pure(Some(false)))
  override def update(): Circuit[F] = this
}
object Circuit {
  def empty[F[_] : Applicative](emptyNodeLayer: NodeLayerOps[F, Expression[_, _]], emptyEdgeLayer: EdgeLayerOps[F, Condition[_]]) =
    apply(Board.empty, Broker.empty, emptyNodeLayer, emptyEdgeLayer)
}

