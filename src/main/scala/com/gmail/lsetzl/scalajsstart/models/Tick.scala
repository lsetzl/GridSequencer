package com.gmail.lsetzl.scalajsstart.models

case class Tick(value: Int) extends Ordered[Tick] {
  def toDuration: Duration = Duration(value)

  def +(a: Duration): Tick = Tick(value + a.value)

  def -(a: Duration): Tick = Tick(value - a.value)

  def -(a: Tick): Duration = Duration(value - a.value)

  def zeroCut: Tick = Tick(List(value, 0).max)

  override def compare(that: Tick): Int = value - that.value
}
