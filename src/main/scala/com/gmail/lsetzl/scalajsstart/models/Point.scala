package com.gmail.lsetzl.scalajsstart.models

case class Point(tick: Tick, trackIndex: TrackIndex) {
  def +(a: Duration): Point = copy(tick = tick + a)
  def -(a: Duration): Point = copy(tick = List(tick - a, Tick(0)).max)
  def +(a: Length): Point = copy(trackIndex = trackIndex + a)
  def -(a: Length): Point = copy(trackIndex = List(trackIndex - a, TrackIndex(0)).max)
  def zeroCut: Point = Point(tick.zeroCut, trackIndex.zeroCut)
}
