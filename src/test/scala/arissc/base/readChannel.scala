package arissc.base

import spinal.core._
import spinal.core.sim._

object readChannel {
  def apply[T <: Data](ch: ChannelIn[T]): Data = {
    waitUntil(ch.isStatusValidSim)
    val data = ch.unsafeExtract
    ch.ack #= true
    waitUntil(ch.isStatusClearedSim)
    ch.ack #= false
    data
  }
}
