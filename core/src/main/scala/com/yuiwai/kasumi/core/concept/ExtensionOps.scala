package com.yuiwai.kasumi.core.concept

trait ExtensionOps {
  def |>[A](f: this.type => A): A = f(this)
}

sealed trait ExtensionPlace {
  self: ExtensionOps =>
}

trait NodeExtension[N <: NodeOps[_]] extends ExtensionOps with ExtensionPlace {
  def node: N
}

trait EdgeExtension[E <: EdgeOps[_]] extends ExtensionOps with ExtensionPlace {
  def edge: E
}

trait Trigger {
  self: ExtensionOps =>
  type This <: Trigger
  def updated: This
  def isFired: Boolean
}

trait TimerOps extends ExtensionOps with Trigger {
  def interval: Int
  def current: Int
  def isFired: Boolean = current % interval == 0
}

trait GeneratorOps[V] extends ExtensionOps {
  def trigger: Trigger
  def creator: () => Seq[V]
  def update: (this.type, Seq[V])
}
