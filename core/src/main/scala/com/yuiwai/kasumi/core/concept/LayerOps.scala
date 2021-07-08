package com.yuiwai.kasumi.core.concept

import scala.language.higherKinds

sealed trait LayerOps[F[_], T] {
  type This
  type K
  def all: F[Seq[(K, T)]]
  def find(key: K): F[Option[T]]
  def findMap[R](key: K)(f: T => Option[R]): F[Option[R]]
  def put(key: K, data: T): This
}
trait NodeLayerOps[F[_], T] extends LayerOps[F, T] {
  type K <: NodeOps[_]
  def find(node: K): F[Option[T]]
  def findMap[R](node: K)(f: T => Option[R]): F[Option[R]]
  def put(node: K, data: T): This
}
trait EdgeLayerOps[F[_], T] extends LayerOps[F, T] {
  type K <: EdgeOps[_]
  def find(edge: K): F[Option[T]]
  def findMap[R](edge: K)(f: T => Option[R]): F[Option[R]]
  def put(edge: K, data: T): This
}
