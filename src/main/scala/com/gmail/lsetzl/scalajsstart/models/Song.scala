package com.gmail.lsetzl.scalajsstart.models

case class Song(cursor: Tick, selectionEnd: Option[Tick], unitDuration: Duration, view: TickRange, channels: Seq[Channel], tracks: Seq[Track], events: Seq[Event]) {

}