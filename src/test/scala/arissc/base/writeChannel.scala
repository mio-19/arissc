package arissc.base

import spinal.core._
import spinal.core.sim._
import arissc.base.simutils._

object writeChannel {

  def flipBigInt(width: Int, x:BigInt):BigInt = {
    if(x<0) throw new IllegalArgumentException("Negative input")
    if(width<x.bitLength) throw new IllegalArgumentException("width too short")
    var result = BigInt(0)
    for(i <- 0 until width) {
      if(!x.testBit(i)) result = result.setBit(i)
    }
    result
  }

  def apply[T <: Data](ch: ChannelIn[T], data: BigInt): Unit = {
    if (!ch.isStatusWaitingOrReturningSim) throw new IllegalStateException("not empty"+simutils.getBigInt(ch.ack))
    ch.dual.ones ##= data
    ch.dual.flipped ##= flipBigInt(ch.data.getBitsWidth, data)
    println(ch.dual.ones.asBigInt)
    println(ch.dual.flipped.asBigInt)
    assert(ch.isStatusValidSim)
    waitUntil(ch.isStatusAckedSim)
    ch.dual ##= 0
    waitUntil(ch.isStatusEmptySim)
  }
}