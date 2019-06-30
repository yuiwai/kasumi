package com.yuiwai.kasumi.core.concept

sealed trait LayerOps {
  type Data
}
trait NodeLayerOps extends LayerOps {
  type N <: NodeOps[_]
  def find(node: N): Option[Data]
}
trait EdgeLayerOps extends LayerOps {
  type E <: EdgeOps[_]
  def find(edge: E): Option[Data]
}
