package com.yuiwai.kasumi.core.concept

import scala.language.higherKinds

trait CircuitOps[F[_], This, Bo <: BoardOps[_], Br <: BrokerOps[Br, _, _, _]] {
  val board: Bo
  val broker: Br
  val nodeLayer: NodeLayerOps[F, Expression[_, _]]
  val edgeLayer: EdgeLayerOps[F, Condition[_]]
  def nodes: Set[_] = board.nodes
  def edges: Set[_] = board.edges
  def putExpr[P, R](node: NodeOps[_], expr: Expression[P, R]): This
  def putCond[P](edge: EdgeOps[_], cond: Condition[P]): This
  def eval[P, R](node: NodeOps[_], param: P): F[Option[R]]
  def eval[P](edge: EdgeOps[_], param: P): F[Option[Boolean]]
  def update(): This
}

trait Expression[P, +R] {
  def eval(param: P): R
}

trait Condition[P] {
  def eval(param: P): Boolean
}
