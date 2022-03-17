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

      dut.clockDomain.assertReset()
      sleep(1000)
      dut.clockDomain.deassertReset()
      dut.io.out1.ack #= false

      assert(dut.io.in1.dual.ones.getBitsWidth==width)

      fork {
        val in = dut.io.in1
        for (x <- data) {
          writeChannel(in, x)
        }
      }


      val readed: Seq[Int] = (1 to testSize).map(_ => readChannel(dut.io.out1).toInt).toSeq
      println(readed)

      assert(data == readed)
    }
  }
}
