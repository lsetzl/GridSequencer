package com.gmail.lsetzl.scalajsstart.models

case class ValueRange(start: Value, end: Option[Value]) {

}

object ValueRange {
  def apply(value: Value): ValueRange = ValueRange(value, None)
}
