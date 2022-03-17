package arissc.base

import spinal.core._
import spinal.core.sim._
import arissc.base.simutils._
import arissc.base.utils.flipBigInt

object writeChannel {

  def apply[T <: Data](ch: ChannelIn[T], data: BigInt): Unit = {
    if (!ch.isStatusWaitingOrReturningSim) throw new IllegalStateException("not empty" + simutils.getBigInt(ch.ack))
    assert(!ch.ack.toBoolean)
    ch.dual.ones ##= data
    ch.dual.flipped ##= flipBigInt(ch.dual.flipped.getBitsWidth, data)
    //assert(ch.dual.isValidSim)
    //assert(!ch.ack.toBoolean)
    //assert(ch.isStatusValidSim)
    waitUntil(ch.isStatusAckedSim)
    ch.dual ##= 0
    waitUntil(ch.isStatusEmptySim)
  }
}