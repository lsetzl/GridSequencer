package com.gmail.lsetzl.scalajsstart.models

import com.gmail.lsetzl.scalajsstart.models.tick.Tick

case class Point(tick: Tick, index: TrackIndex) {
  def +(a: Duration): Point = copy(tick = tick + a)
  def -(a: Duration): Point = copy(tick = List(tick - a, Tick(0)).max)
  def +(a: Length): Point = copy(index = index + a)
  def -(a: Length): Point = copy(index = List(index - a, TrackIndex(0)).max)
}
