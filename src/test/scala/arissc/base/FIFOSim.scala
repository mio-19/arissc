package arissc.base

import arissc.SimConf.SimConf
import arissc.base.simutils._
import spinal.core._
import spinal.core.sim._

import scala.util.Random

//MyTopLevel's testbench
object FIFOSim {
  val testSize = 5

  def main(args: Array[String]) {
    val width = 64
    SimConf.doSim(new FIFO(512, UInt(width bits))) { dut =>
      val data: Seq[Int] = (1 to testSize).map(_ => Random.nextInt().abs)

      dut.io.out1.ack #= false
      dut.io.in1.dual ##= 0
      dut.clockDomain.assertReset()
      sleep(1000)
      dut.clockDomain.deassertReset()

      assert(dut.io.in1.dual.ones.getBitsWidth == width)
      assert(dut.io.out1.isStatusEmptySim)

      for (x <- data) {
        writeChannel(dut.io.in1, x)
      }

      for (expect <- data) {
        val got = readChannel(dut.io.out1).toInt
        assert(expect equals got)
        sleep(1234)
      }
    }
  }
}
