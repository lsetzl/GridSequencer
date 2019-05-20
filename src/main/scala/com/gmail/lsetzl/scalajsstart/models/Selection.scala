package com.gmail.lsetzl.scalajsstart.models

import com.gmail.lsetzl.scalajsstart.models
import com.gmail.lsetzl.scalajsstart.models.tick.{Duration, Tick, TickRange}

case class Selection(a: Point, b: Point, resolution: Duration) {
  val points: List[Point] = List(a, b)
  val ticks: List[Tick] = points.map(_.tick)
  val indexes: List[TrackIndex] = points.map(_.index)

  def tickRange: TickRange = models.TickRange(ticks.min, ticks.max - ticks.min + resolution)

  def trackIndexRange: TrackIndexRange = TrackIndexRange(indexes.min, indexes.max - indexes.min + Length(1))
}
