package arissc.base

import spinal.core._
import spinal.core.sim._
import arissc.base.simutils._

object writeChannel {
  def apply[T <: Data](ch: ChannelIn[T], data: BigInt): Unit = {
    if (!ch.isStatusWaitingOrReturningSim) throw new IllegalStateException("not empty"+simutils.getBigInt(ch.ack))
    ch.dual.ones ##= data
    ch.dual.flipped ##= ~data
    println(ch.dual.ones.asBigInt)
    println(ch.dual.flipped.asBigInt)
    assert(ch.isStatusValidSim)
    waitUntil(ch.isStatusAckedSim)
    ch.dual ##= 0
    waitUntil(ch.isStatusEmptySim)
  }
}