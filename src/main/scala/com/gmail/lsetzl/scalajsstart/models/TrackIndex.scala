package com.gmail.lsetzl.scalajsstart.models

case class TrackIndex(value: Int) extends Ordered[TrackIndex] {
  def toLength: Length = Length(value)

  def +(a: Length): TrackIndex = TrackIndex(value + a.value)

  def -(a: Length): TrackIndex = TrackIndex(value - a.value)

  def -(a: TrackIndex): Length = Length(value - a.value)

  def zeroCut: TrackIndex = TrackIndex(List(value, 0).max)

  override def compare(that: TrackIndex): Int = value - that.value
}
