package com.gmail.lsetzl.scalajsstart.models

case class TrackIndex(value: Int) extends Ordered[TrackIndex] {
  def toLength: Length = Length(value)

  def +(a: Length): TrackIndex = TrackIndex(value + a.value)

  def -(a: Length): TrackIndex = TrackIndex(value - a.value)

  def -(a: TrackIndex): Length = Length(value - a.value)

  override def compare(that: TrackIndex): Int = value - that.value
}
