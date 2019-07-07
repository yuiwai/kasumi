package com.yuiwai.kasumi.core.concept

import scala.language.higherKinds

sealed trait LayerOps[F[_]] {
  type Data
  type K
  def find(key: K): F[Option[Data]]
}
trait NodeLayerOps[F[_]] extends LayerOps[F] {
  type K <: NodeOps[_]
  def find(node: K): F[Option[Data]]
}
trait EdgeLayerOps[F[_]] extends LayerOps[F] {
  type K <: EdgeOps[_]
  def find(edge: K): F[Option[Data]]
}
