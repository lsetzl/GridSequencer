package com.gmail.lsetzl.scalajsstart.models

case class Tracks(values: Track*) {
  def apply(index: TrackIndex): Track = values(index.value)
}

