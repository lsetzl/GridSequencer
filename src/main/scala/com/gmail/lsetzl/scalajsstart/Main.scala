package com.gmail.lsetzl.scalajsstart

import com.gmail.lsetzl.scalajsstart.models._
import scalatags.JsDom.all._
import org.scalajs.dom

object Main extends App {
  var cursor: Point = Point(Tick(0), TrackIndex(0))
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
    drawGrid(context, ViewRange(TickRange(Tick(0), Duration(48 * 8)), TrackIndexRange(TrackIndex(0), Length(16))))
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

  drawGrid(context, ViewRange(TickRange(Tick(0), Duration(48 * 8)), TrackIndexRange(TrackIndex(0), Length(16))))
  drawSelection(context, selection)

  dom.document.body.appendChild(mainScreen)
}
