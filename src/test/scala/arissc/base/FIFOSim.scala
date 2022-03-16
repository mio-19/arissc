package arissc.base

import arissc.SimConf.SimConf
import spinal.core._
import spinal.core.sim._

import scala.util.Random

//MyTopLevel's testbench
object FIFOSim {
  val testSize = 5

  def main(args: Array[String]) {
    SimConf.doSim(new FIFO(512, UInt(64 bits))) { dut =>
      val data: Seq[Int] = (1 to testSize).map(_ => Random.nextInt().abs).toSeq

      fork {
        data.foreach(x => writeChannel(dut.io.in1, U(x)))
        False
      }

      val readed: Seq[Int] = (1 to testSize).map(_ => readChannel(dut.io.out1).asBits.asUInt.toInt).toSeq

      assert(data == readed)
    }
  }
}
