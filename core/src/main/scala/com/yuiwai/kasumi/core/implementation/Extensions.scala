package com.yuiwai.kasumi.core.implementation

import com.yuiwai.kasumi.core.concept.{GeneratorOps, TimerOps, Trigger}

trait Extensions {
  def generator[V](trigger: Trigger, creator: () => Seq[V]) = Generator(trigger, creator)
}
object Extensions extends Extensions

final case class Generator[V](trigger: Trigger, creator: () => Seq[V]) extends GeneratorOps[V] {
  override def update: (Generator.this.type, Seq[V]) = copy(trigger.update, creator) |> { g =>
    (g.asInstanceOf[this.type], if (g.trigger.isFired) creator() else Seq.empty)
  }
}

final case class Timer(interval: Int, current: Int) extends TimerOps {
  def update: this.type = copy(current = current + 1).asInstanceOf[this.type]
}
object Timer {
  def apply(interval: Int): Timer = apply(interval, 0)
  def apply(): Timer = apply(1)
}
