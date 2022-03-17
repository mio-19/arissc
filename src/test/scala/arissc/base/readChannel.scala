package arissc.base

import arissc.base.simutils.DataPimper
import spinal.core._
import spinal.core.sim._

object readChannel {
  def apply[T <: Data](ch: ChannelIn[T]): BigInt = {
    waitUntil(ch.isStatusValidSim)
    val data = ch.unsafeExtract.asBigInt
    ch.ack #= true
    waitUntil(ch.isStatusClearedSim)
    ch.ack #= false
    println("Got "+data)
    data
  }
}
