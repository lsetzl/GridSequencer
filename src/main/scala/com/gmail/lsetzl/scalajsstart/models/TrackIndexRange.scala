package com.gmail.lsetzl.scalajsstart.models

case class TrackIndexRange(index: TrackIndex, length: Length) {
  val end: TrackIndex = index + length - Length(1)

  def +(a: Length): TrackIndexRange = copy(index = index + a)

  def -(a: Length): TrackIndexRange = copy(index = index - a)

  def extend(a: Length): TrackIndexRange = copy(length = length + a)

  def shrink(a: Length): TrackIndexRange = copy(length = length - a)

  def next: TrackIndexRange = copy(index = index + length)

  def set(a: Length): TrackIndexRange = copy(length = a)

  def indexes: Seq[TrackIndex] = (1 to length.value).map(index + Length(_))
}

object TrackIndexRange {
  def apply(a: TrackIndex, b: TrackIndex): TrackIndexRange = {
    val trackIndexes: Seq[TrackIndex] = List(a, b)
    TrackIndexRange(trackIndexes.min, trackIndexes.max - trackIndexes.min + Length(1))
  }
}
