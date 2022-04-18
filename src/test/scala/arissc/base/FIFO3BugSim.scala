package arissc.base

import arissc.SimConf.SimConf
import arissc.base.simutils._
import spinal.core._
import spinal.core.sim._

import scala.util.Random

object FIFO3BugSim {
  def main(args: Array[String]) {
    val width = 64
    SimConf.doSim(new FIFO3(UInt(width bits))) { dut =>
      dut.io.out1.ack #= false
      dut.io.in1.dual ##= 0
      dut.clockDomain.assertReset()
      sleep(1000)
      dut.clockDomain.deassertReset()
      sleep(1000)

      {
        val data = Random.nextInt().abs

        fork {
          writeChannel(dut.io.in1, data)
        }

        sleep(6432)
        val got = readChannel(dut.io.out1).toInt
        assert(got == data)
      }
      sleep(6432)
      // here

      {
        val data = Random.nextInt().abs

        fork {
          sleep(1234)
          writeChannel(dut.io.in1, data)
          print(dut.io.in1.toString)
        }

        val got = readChannel(dut.io.out1).toInt
        assert(got == data)
      }
    }
  }

}
