package com.gmail.lsetzl.scalajsstart.models

case class TrackIndexRange(index: TrackIndex, length: Length) {
  val end: TrackIndex = index + length - Length(1)

  def +(a: Length): TrackIndexRange = copy(index = index + a)

  def -(a: Length): TrackIndexRange = copy(index = index - a)

  def extend(a: Length): TrackIndexRange = copy(length = length + a)

  def shrink(a: Length): TrackIndexRange = copy(length = length - a)

  def next: TrackIndexRange = copy(index = index + length)

  def set(a: Length): TrackIndexRange = copy(length = a)
}
