package arissc.base

import arissc.base.sim.simutils
import spinal.core._
import spinal.core.sim._

object writeChannel {
  def apply[T <: Data](ch: ChannelIn[T], data: BigInt): Unit = {
    if (!ch.isStatusWaitingOrReturningSim) throw new IllegalStateException("not empty"+simutils.getBigInt(ch.ack))
    ch.dual.asBits #= (data << ch.data.getBitsWidth) & (~data)
    waitUntil(ch.isStatusAcked.toBoolean)
    ch.dual.asBits #= 0
    waitUntil(ch.isStatusEmpty.toBoolean)
  }
}