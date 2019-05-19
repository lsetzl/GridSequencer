package com.gmail.lsetzl.scalajsstart

import scalatags.JsDom.all._
import org.scalajs.dom

case class Tick(value: Int) extends Ordered[Tick] {
  def toDuration: Duration = Duration(value)

  def +(a: Duration): Tick = Tick(value + a.value)

  def -(a: Duration): Tick = Tick(value - a.value)

  def -(a: Tick): Duration = Duration(value - a.value)

  override def compare(that: Tick): Int = value - that.value
}

case class Duration(value: Int) extends Ordered[Duration] {
  def toTick: Tick = Tick(value)

  def +(a: Duration): Duration = Duration(value + a.value)

  def -(a: Duration): Duration = Duration(value - a.value)

  def abs: Duration = Duration(value.abs)

  override def compare(that: Duration): Int = value - that.value
}

case class TickRange(tick: Tick, duration: Duration) {
  def +(a: Duration): TickRange = copy(tick = tick + a)

  def -(a: Duration): TickRange = copy(tick = tick - a)

  def extend(a: Duration): TickRange = copy(duration = duration + a)

  def shrink(a: Duration): TickRange = copy(duration = duration - a)

  def next: TickRange = copy(tick = tick + duration)

  def set(a: Duration): TickRange = copy(duration = a)
}

case class Index(value: Int) extends Ordered[Index] {
  def toLength: Length = Length(value)

  def +(a: Length): Index = Index(value + a.value)

  def -(a: Length): Index = Index(value - a.value)

  def -(a: Index): Length = Length(value - a.value)

  override def compare(that: Index): Int = value - that.value
}

case class Length(value: Int) extends Ordered[Length] {
  override def compare(that: Length): Int = value - that.value

  def +(a: Length): Length = Length(value + a.value)

  def -(a: Length): Length = Length(value - a.value)
}

case class TrackIndexRange(index: Index, length: Length) {
  val end: Index = index + length - Length(1)

  def +(a: Length): TrackIndexRange = copy(index = index + a)

  def -(a: Length): TrackIndexRange = copy(index = index - a)

  def extend(a: Length): TrackIndexRange = copy(length = length + a)

  def shrink(a: Length): TrackIndexRange = copy(length = length - a)

  def next: TrackIndexRange = copy(index = index + length)

  def set(a: Length): TrackIndexRange = copy(length = a)
}

case class Point(tick: Tick, index: Index) {
  def +(a: Duration): Point = copy(tick = tick + a)
  def -(a: Duration): Point = copy(tick = List(tick - a, Tick(0)).max)
  def +(a: Length): Point = copy(index = index + a)
  def -(a: Length): Point = copy(index = List(index - a, Index(0)).max)
}

case class Selection(a: Point, b: Point, resolution: Duration) {
  val points: List[Point] = List(a, b)
  val ticks: List[Tick] = points.map(_.tick)
  val indexes: List[Index] = points.map(_.index)

  def tickRange: TickRange = TickRange(ticks.min, ticks.max - ticks.min + resolution)

  def trackIndexRange: TrackIndexRange = TrackIndexRange(indexes.min, indexes.max - indexes.min + Length(1))
}

case class ViewRange(tickRange: TickRange, trackIndexRange: TrackIndexRange)

case class Value(value: Int)

case class ValueRange(start: Value, end: Value)

case class Event(tickRange: TickRange, valueRange: ValueRange)

case class Events(list: Seq[Event]) {
  def +(a: Event): Events = Events(list = list :+ a)
}

trait EventType

object EventType {
  type T = EventType

  case object Note extends T

  case object Velocity extends T

  case object Volume extends T

  case object Tempo extends T
}

case class Track(channel: Int, eventType: EventType, events: Seq[Event])

case class Tracks(tracks: Seq[Track])


object Main extends App {
  var cursor: Point = Point(Tick(0), Index(0))
  var selectionStart: Option[Point] = None
  var resolution: Duration = Duration(16)
  var events: Events = Events(Nil)

  def selection: Selection = Selection(cursor, selectionStart.getOrElse(cursor), resolution)

  val mainCanvas: dom.html.Canvas = canvas().render
  mainCanvas.width = 1000
  mainCanvas.height = 500
  val context: dom.CanvasRenderingContext2D = mainCanvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  val mainScreen: dom.html.Div = div(mainCanvas).render

  dom.document.onkeydown = (e: dom.KeyboardEvent) => {
    if (e.shiftKey && selectionStart.isEmpty) selectionStart = Some(cursor)
    if (!e.shiftKey && List(37, 38, 39, 40).contains(e.keyCode)) selectionStart = None

    e.keyCode match {
      case 37 => cursor = cursor - resolution
      case 38 => cursor = cursor - Length(1)
      case 39 => cursor = cursor + resolution
      case 40 => cursor = cursor + Length(1)
      case 67 => events = events + Event(selection.tickRange, ValueRange(Value(0), Value(0)))
    }
    drawGrid(context, ViewRange(TickRange(Tick(0), Duration(48 * 8)), TrackIndexRange(Index(0), Length(16))))
    drawSelection(context, selection)
  }

  def drawGrid(c: dom.CanvasRenderingContext2D, vr: ViewRange): Unit = {
    c.fillStyle = "black"
    c.fillRect(0, 0, 1000, 500)

    c.lineWidth = 1
    c.strokeStyle = "gray"
    c.beginPath()
    for (x <- 0 to 63; y <- 0 to 15) {
      context.strokeRect(x * 16, y * 16, 16, 16)
    }
  }

  def draw(viewRange: ViewRange, tracks: Tracks): Unit = {

  }

  def drawSelection(c: dom.CanvasRenderingContext2D, selection: Selection): Unit = {
    c.lineWidth = 3
    c.strokeStyle = "yellow"
    c.beginPath()
    context.strokeRect(
      selection.tickRange.tick.value,
      selection.trackIndexRange.index.value * 16,
      selection.tickRange.duration.value,
      selection.trackIndexRange.length.value * 16
    )
  }

  drawGrid(context, ViewRange(TickRange(Tick(0), Duration(48 * 8)), TrackIndexRange(Index(0), Length(16))))
  drawSelection(context, selection)

  dom.document.body.appendChild(mainScreen)
}
