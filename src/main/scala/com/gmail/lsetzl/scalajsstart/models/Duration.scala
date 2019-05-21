package com.gmail.lsetzl.scalajsstart.models

case class Duration(value: Int) extends Ordered[Duration] {
  def toTick: Tick = Tick(value)

  def +(a: Duration): Duration = Duration(value + a.value)

  def -(a: Duration): Duration = Duration(value - a.value)

  def *(a: Int): Duration = Duration(value * a)

  def /(a: Int): Duration = Duration(value / a)

  def /(a: Duration): Int = value / a.value

  def abs: Duration = Duration(value.abs)

  override def compare(that: Duration): Int = value - that.value
}
