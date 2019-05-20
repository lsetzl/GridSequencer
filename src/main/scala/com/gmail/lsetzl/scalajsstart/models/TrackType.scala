package com.gmail.lsetzl.scalajsstart.models

trait TrackType

object TrackType {
  type T = TrackType

  case object Note extends T

  case object Velocity extends T

  case object Volume extends T

  case object Tempo extends T
}