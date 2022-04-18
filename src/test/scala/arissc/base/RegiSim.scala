package arissc.base

import arissc.SimConf.SimConf
import spinal.core._
import spinal.core.sim._
import arissc.base.simutils._

import scala.util.Random

object RegiSim {

  def main(args: Array[String]) {
    val width = 64
    SimConf.doSim(new Regi(UInt(width bits))) { dut =>
      dut.io.out1.ack #= false
      dut.io.in1.dual ##= 0
      dut.clockDomain.assertReset()
      sleep(1000)
      dut.clockDomain.deassertReset()

      {
        val data = Random.nextInt().abs

        fork {
          writeChannel(dut.io.in1, data)
        }

        sleep(6432)
        val got = readChannel(dut.io.out1).toInt
        assert(got == data)
      }
      {
        val data = Random.nextInt().abs

        fork {
          sleep(6432)
          writeChannel(dut.io.in1, data)
        }

        val got = readChannel(dut.io.out1).toInt
        assert(got == data)
      }
    }
  }
}
