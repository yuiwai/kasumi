package com.yuiwai.kasumi.core.concept

trait TypedBoardOps[V] extends BoardOps[NodeOps[V]]
trait TypedEdgeOps[V] extends EdgeOps[NodeOps[V]]
trait TypedRouteOps[V] extends RouteOps[NodeOps[V]]
