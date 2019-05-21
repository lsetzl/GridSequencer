package com.gmail.lsetzl.scalajsstart.models

case class Selection(tickRange: TickRange, trackIndexRange: TrackIndexRange) {
  def contains(a: Tick): Boolean = tickRange.contains(a)
}

object Selection {
  def apply(): Selection = {
    Selection(TickRange(Tick(0), Duration(48 * 4)), TrackIndexRange(TrackIndex(0), Length(16)))
  }

  def apply(a: Point, b: Point, resolution: Duration): Selection = {
    val points: List[Point] = List(a, b)
    val ticks: List[Tick] = points.map(_.tick)
    val indexes: List[TrackIndex] = points.map(_.trackIndex)
    def tickRange: TickRange = TickRange(ticks.min, ticks.max, resolution)
    def trackIndexRange: TrackIndexRange = TrackIndexRange(indexes.min, indexes.max)
    Selection(tickRange, trackIndexRange)
  }
}
