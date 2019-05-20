package com.gmail.lsetzl.scalajsstart.models

import com.gmail.lsetzl.scalajsstart.models

case class Selection(tickRange: TickRange, trackIndexRange: TrackIndexRange) {

}

object Selection {
  def apply(): Selection = {
    Selection(TickRange(Tick(0), Duration(48 * 4)), TrackIndexRange(TrackIndex(0), Length(16)))
  }

  def apply(a: Point, b: Point, resolution: Duration): Selection = {
    val points: List[Point] = List(a, b)
    val ticks: List[Tick] = points.map(_.tick)
    val indexes: List[TrackIndex] = points.map(_.trackIndex)
    def tickRange: TickRange = models.TickRange(ticks.min, ticks.max - ticks.min + resolution)
    def trackIndexRange: TrackIndexRange = TrackIndexRange(indexes.min, indexes.max - indexes.min + Length(1))
    Selection(tickRange, trackIndexRange)
  }
}
