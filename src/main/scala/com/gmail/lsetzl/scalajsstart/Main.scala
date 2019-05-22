package com.gmail.lsetzl.scalajsstart

import com.gmail.lsetzl.scalajsstart.models._
import scalatags.JsDom.all._
import org.scalajs.dom

object Main extends App {
  var song: Song = Song()

  val mainCanvas: dom.html.Canvas = canvas().render
  mainCanvas.width = 1000
  mainCanvas.height = 500
  val context: dom.CanvasRenderingContext2D = mainCanvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  val mainScreen: dom.html.Div = div(mainCanvas).render

  dom.document.onkeydown = (e: dom.KeyboardEvent) => {
    if (e.shiftKey && !song.isSelecting) song = song.startSelect
    if (e.keyCode == 243 || !e.shiftKey && List(37, 39).contains(e.keyCode)) song = song.clearSelect

    e.keyCode match {
      case 37 => song = song.cursorToLeft
      case 38 => song = song.cursorToUp
      case 39 => song = song.cursorToRight
      case 40 => song = song.cursorToDown
      case 67 => song = song.addEvent(Value(0))
    }

    drawGrid(context, song.view)
    drawSelection(context, song.selection)
  }

  def toX(view: Selection, resolution: Duration, tick: Tick): Int = {
    (tick - view.tickRange.tick) * 16 / resolution
  }

  def drawGrid(c: dom.CanvasRenderingContext2D, view: Selection): Unit = {
    c.fillStyle = "black"
    c.fillRect(0, 0, 1000, 500)

    c.lineWidth = 1
    c.strokeStyle = "gray"
    c.beginPath()
    for (x <- 0 to 63; y <- 0 to 15) {
      context.strokeRect(x * 16, y * 16, 16, 16)
    }
  }

  def draw(c: dom.CanvasRenderingContext2D, view: Selection,
           resolution: Duration, channels: Seq[Channel], tracks: Seq[Track], events: Seq[Event]): Unit = {

  }

  def drawChannel(c: dom.CanvasRenderingContext2D, y: Int,
                  view: Selection, resolution: Duration,
                  channel: Channel, tracks: Seq[Track], events: Seq[Event]): Unit = {

  }

  def drawTrack(c: dom.CanvasRenderingContext2D, y: Int,
                view: Selection, resolution: Duration,
                track: Track, events: Seq[Event]): Unit = {

  }

  def drawEvent(c: dom.CanvasRenderingContext2D, y: Int,
                view: Selection, resolution: Duration, trackType: TrackType, event: Event): Unit = {
    def valueString(value: Value): String = trackType match {
      case TrackType.Note => List("c", "c+", "d", "e", "f", "f+", "g", "g+", "a", "b-", "b")(value.value)
      case _ => value.value.toString
    }

    val x1 = toX(view, resolution, event.tickRange.tick)
    val x2 = toX(view, resolution, event.tickRange.end)
    c.strokeStyle = "white"
    c.fillStyle = "white"
    c.globalAlpha = 0.5
    c.beginPath()
    context.fillRect(x1, y, x2, y + 16)
    c.globalAlpha = 1.0
    val text: String = valueString(event.valueRange.start) + event.valueRange.end.map("-" + valueString(_)).getOrElse("")
    c.fillText(text, x1, y + 16, x2 - x1)
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

  drawGrid(context, song.view)
  drawSelection(context, song.selection)

  dom.document.body.appendChild(mainScreen)
}
