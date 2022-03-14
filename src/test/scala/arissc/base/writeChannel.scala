package arissc.base

import spinal.core._
import spinal.core.sim._

object writeChannel {
  def apply[T <: Data](ch: ChannelIn[T], data: T): Unit = {
    if (!ch.isStatusEmpty.toBoolean) throw new IllegalStateException("not empty")
    ch.dual.asBits #= Dual.from(data).asBits.toBigInt
    waitUntil(ch.isStatusAcked.toBoolean)
    ch.dual.asBits #= Dual.empty(data).asBits.toBigInt
    waitUntil(ch.isStatusEmpty.toBoolean)
  }
}