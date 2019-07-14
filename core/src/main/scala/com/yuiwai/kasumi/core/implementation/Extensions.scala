package com.yuiwai.kasumi.core.implementation

import com.yuiwai.kasumi.core.concept.{ExtensionOps, GeneratorOps, TimerOps, Trigger}

trait Extensions {
  def generator[V](trigger: Trigger, creator: () => Seq[V]) = Generator(trigger, creator)
}
object Extensions extends Extensions

final case class Generator[V](trigger: Trigger, creator: () => Seq[V]) extends GeneratorOps[V] {
  override def updated(): (Generator.this.type, Seq[V]) = copy(trigger.updated, creator) |> { g =>
    (g.asInstanceOf[this.type], if (g.trigger.isFired) creator() else Seq.empty)
  }
}

sealed trait OnceSwitch extends ExtensionOps with Trigger {
  override type This = OnceSwitch
  def activated: OnceSwitch
  def deactivated: OnceSwitch
}
object OnceSwitch {
  def apply(): OnceSwitch = PassiveOnceSwitch
}
case object BurnedOnceSwitch extends OnceSwitch {
  override def isFired: Boolean = false
  override def activated: OnceSwitch = this
  override def deactivated: OnceSwitch = PassiveOnceSwitch
  override def updated: OnceSwitch = this
}
case object PassiveOnceSwitch extends OnceSwitch {
  override def isFired: Boolean = false
  override def activated: OnceSwitch = ReadyOnceSwitch
  override def deactivated: OnceSwitch = this
  override def updated: OnceSwitch = this
}
case object ReadyOnceSwitch extends OnceSwitch {
  override def isFired: Boolean = false
  override def activated: OnceSwitch = this
  override def deactivated: OnceSwitch = this
  override def updated: OnceSwitch = ActiveOnceSwitch
}
case object ActiveOnceSwitch extends OnceSwitch {
  override def isFired: Boolean = true
  override def activated: OnceSwitch = this
  override def deactivated: OnceSwitch = this
  override def updated: OnceSwitch = BurnedOnceSwitch
}

final case class Timer(interval: Int, current: Int) extends TimerOps {
  override type This = Timer
  def updated: Timer = copy(current = current + 1)
}
object Timer {
  def apply(interval: Int): Timer = apply(interval, 0)
  def apply(): Timer = apply(1)
}
