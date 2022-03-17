package arissc.base

import spinal.core._
import spinal.core.sim._
import arissc.base.simutils._

object writeChannel {
  def apply[T <: Data](ch: ChannelIn[T], data: BigInt): Unit = {
    if (!ch.isStatusWaitingOrReturningSim) throw new IllegalStateException("not empty"+simutils.getBigInt(ch.ack))
    ch.dual ##= (data << ch.data.getBitsWidth) & (~data)
    assert(ch.dual.isValidSim)
    waitUntil(ch.isStatusAckedSim)
    ch.dual ##= 0
    waitUntil(ch.isStatusEmptySim)
  }
}