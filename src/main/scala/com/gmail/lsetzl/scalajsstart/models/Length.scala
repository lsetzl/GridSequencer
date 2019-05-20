package com.gmail.lsetzl.scalajsstart.models

case class Length(value: Int) extends Ordered[Length] {
  override def compare(that: Length): Int = value - that.value

  def +(a: Length): Length = Length(value + a.value)

  def -(a: Length): Length = Length(value - a.value)
}
