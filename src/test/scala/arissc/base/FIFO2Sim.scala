package arissc.base

import arissc.SimConf.SimConf
import spinal.core._
import spinal.core.sim._
import arissc.base.simutils._

import scala.util.Random

object FIFO2Sim {
  def main(args: Array[String]) {
    val width = 64
    SimConf.doSim(new FIFO(2, UInt(width bits))) { dut =>
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
      def test1 = {
        val data0 = Random.nextInt().abs
        val data1 = Random.nextInt().abs

        fork {
          writeChannel(dut.io.in1, data0)
          writeChannel(dut.io.in1, data1)
        }

        sleep(6432)
        assert(readChannel(dut.io.out1).toInt == data0)
        assert(readChannel(dut.io.out1).toInt == data1)
      }
      test1

      def test2 = {
        val data0 = Random.nextInt().abs
        val data1 = Random.nextInt().abs

        fork {
          sleep(6432)
          writeChannel(dut.io.in1, data0)
          writeChannel(dut.io.in1, data1)
        }

        assert(readChannel(dut.io.out1).toInt == data0)
        assert(readChannel(dut.io.out1).toInt == data1)
      }
      test2
    }
  }
}
