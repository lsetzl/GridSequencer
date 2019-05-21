package com.gmail.lsetzl.scalajsstart.models

case class TickRange(tick: Tick, duration: Duration) {
  def end: Tick = tick + duration

  def +(a: Duration): TickRange = copy(tick = tick + a)

  def -(a: Duration): TickRange = copy(tick = tick - a)

  def extend(a: Duration): TickRange = copy(duration = duration + a)

  def shrink(a: Duration): TickRange = copy(duration = duration - a)

  def next: TickRange = copy(tick = end)

  def set(a: Duration): TickRange = copy(duration = a)

  def contains(a: Tick): Boolean = (tick.value to end.value).contains(a.value)
}

object TickRange {
  def apply(a: Tick, b: Tick, resolution: Duration): TickRange = {
    val ticks: Seq[Tick] = List(a, b)
    TickRange(ticks.min, ticks.max - ticks.min + resolution)
  }
}