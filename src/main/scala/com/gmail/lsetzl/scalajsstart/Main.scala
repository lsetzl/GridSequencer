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
    drawSelection(context, song.se)
  }

  def drawGrid(c: dom.CanvasRenderingContext2D, v: View): Unit = {
    c.fillStyle = "black"
    c.fillRect(0, 0, 1000, 500)

    c.lineWidth = 1
    c.strokeStyle = "gray"
    c.beginPath()
    for (x <- 0 to 63; y <- 0 to 15) {
      context.strokeRect(x * 16, y * 16, 16, 16)
    }
  }

  def draw(viewRange: View, tracks: Tracks): Unit = {

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

  drawGrid(context, View(TickRange(Tick(0), Duration(48 * 8)), TrackIndexRange(TrackIndex(0), Length(16))))
  drawSelection(context, selection)

  dom.document.body.appendChild(mainScreen)
}
