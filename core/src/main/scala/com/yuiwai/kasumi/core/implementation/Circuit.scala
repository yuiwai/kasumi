package com.yuiwai.kasumi.core.implementation

import com.yuiwai.kasumi.core.concept._

import scala.language.higherKinds
import scala.util.Try
import scala.util.chaining._

case class Circuit[F[_]](
  board: Board,
  current: Current,
  nodeLayer: NodeLayerOps[F, Calculation[_, _]],
  edgeLayer: EdgeLayerOps[F, Condition[_]],
  generatorLayer: NodeLayerOps[F, GeneratorOps[_]])
  (implicit F: Monad[F], T: Traverse[Set])
  extends CircuitOps[F, Circuit[F], Board] {
  override def putCalc[P, R](node: NodeOps[_], expr: Calculation[P, R]): Circuit[F] =
    copy(nodeLayer = nodeLayer.put(node.asInstanceOf[nodeLayer.K], expr).asInstanceOf[NodeLayerOps[F, Calculation[_, _]]])
  override def putCond[P](edge: EdgeOps[_], cond: Condition[P]): Circuit[F] =
    copy(edgeLayer = edgeLayer.put(edge.asInstanceOf[edgeLayer.K], cond).asInstanceOf[EdgeLayerOps[F, Condition[_]]])
  override def putData[V, R](node: NodeOps[_], data: V): F[Circuit[F]] = F.flatMap(eval[V, R](node, data)) {
    case Some(r) => putCalculatedData(node, r)
    case None => putCalculatedData(node, data)
  }
  private def putCalculatedData[V](node: NodeOps[_], data: V): F[Circuit[F]] =
    F.map(
      T.traverse(
        // FIXME これだと動かない
        // board.edgesFrom(node)
        board.edges.filter(_.from == node)
          .map { e =>
            e -> eval(e, data)
          }) { x =>
        F.map(x._2)(_.fold(x._1 -> true)(x._1 -> _))
      }) { xs =>
      xs.foldLeft(current) { case (acc, (e, b)) =>
        if (b) acc.copy(current.particles :+ Particle(data, e))
        else acc
      }.pipe(crr => copy(current = crr))
    }
  override def eval[P, R](node: NodeOps[_], param: P): F[Option[R]] = Try {
    nodeLayer.findMap(node.asInstanceOf[nodeLayer.K]) {
      case expr: Calculation[P, R] => Some(expr.eval(param))
      case _ => None
    }
  }.getOrElse(F.pure(None))
  override def eval[P](edge: EdgeOps[_], param: P): F[Option[Boolean]] = Try {
    edgeLayer.findMap(edge.asInstanceOf[edgeLayer.K]) {
      case cond: Condition[P] => Some(cond.eval(param))
      case _ => None
    }
  }.getOrElse(implicitly[Applicative[F]].pure(Some(false)))
  override def update(): Circuit[F] = this
}

final case class Current(particles: Seq[Particle]) extends CurrentOps
object Current {
  def empty: Current = apply(Seq.empty)
}

object Particle {
  def apply[V](data: V, edge: EdgeOps[_]): Particle = new Particle {
    override type Data = V
    override def value: V = data
    override def pos: EdgeOps[_] = edge
  }
}

object Circuit {
  def empty[F[_] : Monad](
    emptyNodeLayer: NodeLayerOps[F, Calculation[_, _]],
    emptyEdgeLayer: EdgeLayerOps[F, Condition[_]],
    generatorNodeLayer: NodeLayerOps[F, GeneratorOps[_]]) =
    apply(Board.empty, Current.empty, emptyNodeLayer, emptyEdgeLayer, generatorNodeLayer)
}

