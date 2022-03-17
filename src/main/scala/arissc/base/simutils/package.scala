package arissc.base

import spinal.core
import spinal.core._
import spinal.core.internals.BaseNode
import spinal.sim._
import spinal.core._
import spinal.core.sim._

import scala.collection.mutable.ArrayBuffer

package object simutils {
  def getBigInt(data: Data): BigInt = {
    var result = BigInt(0)
    for (x <- data.flatten) {
      result = result << x.getBitsWidth
      val current = sim.SimBaseTypePimper(x).toBigInt
      result += current
    }
    result
  }

  def setBigInt(data: Data, value: BigInt): Unit = {
    if (value < 0) throw new IllegalArgumentException("The BigInt is negative")
    var i = value
    for (x <- data.flatten.reverse) {
      val item = i & ((BigInt(2) << x.getBitsWidth) - 1)
      sim.SimBaseTypePimper(x).assignBigInt(item)
      if (sim.SimBaseTypePimper(x).toBigInt != item) {
        sleep(1)
        assert(sim.SimBaseTypePimper(x).toBigInt == item)
      }
      i = i >> x.getBitsWidth
    }
  }

  implicit class DataPimper[T <: Data](x: T) {
    def asBigInt: BigInt = getBigInt(x)

    def ##=(value: BigInt): Unit = setBigInt(x, value)
  }
}
