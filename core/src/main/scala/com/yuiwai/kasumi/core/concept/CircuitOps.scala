package com.yuiwai.kasumi.core.concept

import scala.language.higherKinds

trait CircuitOps[F[_], This, Bo <: BoardOps[_]] {
  val board: Bo
  val current: CurrentOps
  val nodeLayer: NodeLayerOps[F, Calculation[_, _]]
  val edgeLayer: EdgeLayerOps[F, Condition[_]]
  def nodes: Set[_] = board.nodes
  def edges: Set[_] = board.edges
  def putGen(node: NodeOps[_], generator: GeneratorOps[_]): This
  def putCalc[P, R](node: NodeOps[_], expr: Calculation[P, R]): This
  def putCond[P](edge: EdgeOps[_], cond: Condition[P]): This
  def putData[V, R](node: NodeOps[_], data: V): F[This]
  def eval[P, R](node: NodeOps[_], param: P): F[Option[R]]
  def eval[P](edge: EdgeOps[_], param: P): F[Option[Boolean]]
  def generated(): F[This]
  def updated(): F[This]
}

trait CurrentOps {
  def particles: Seq[Particle]
  def size: Int = particles.size
}

trait Particle {
  type Data
  def value: Data
  def pos: EdgeOps[_]
}

trait Calculation[P, +R] {
  def eval(param: P): R
}

trait Condition[P] {
  def eval(param: P): Boolean
}
