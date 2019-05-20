package com.gmail.lsetzl.scalajsstart.models

case class Song(cursor: Point, selectionStart: Option[Point], resolution: Duration, view: Selection,
                channels: Seq[Channel], tracks: Tracks, events: Seq[Event]) {
  def isSelecting: Boolean = selectionStart.isDefined

  def startSelect: Song = copy(selectionStart = Some(cursor))

  def clearSelect: Song = copy(selectionStart = None)

  def selection: Selection = {
    val tickRange: TickRange = selectionStart match {
      case None => TickRange(cursor.tick, resolution)
      case Some(s) => TickRange(cursor.tick, s.tick, resolution)
    }
    val trackIndexRange: TrackIndexRange = selectionStart match {
      case None => TrackIndexRange(cursor.trackIndex, Length(1))
      case Some(s) => TrackIndexRange(cursor.trackIndex, s.trackIndex)
    }
    Selection(tickRange, trackIndexRange)
  }

  def selectedTracks: Seq[Track] = selection.trackIndexRange.indexes.map(tracks(_))

  def cursorToLeft: Song = copy(cursor = (cursor - resolution).zeroCut)

  def cursorToRight: Song = copy(cursor = cursor + resolution)

  def cursorToUp: Song = copy(cursor = (cursor - Length(1)).zeroCut)

  def cursorToDown: Song = copy(cursor = cursor + Length(1))

  def addEvent(value: Value): Song = {
    val trackId: Int = tracks(cursor.trackIndex.value).id
    copy(events = events :+ Event(trackId, selection.tickRange, ValueRange(value, value)))
  }
}

object Song {
  def apply(): Song = {
    Song(Point(Tick(0), TrackIndex(0)), None, Duration(16), Selection(),
      List(Channel(0)), Tracks(Track(0, 0, TrackType.Tempo)), Nil)
  }
}