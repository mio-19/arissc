package arissc.base

import arissc.SimConf.SimConf
import arissc.base.simutils._
import spinal.core._
import spinal.core.sim._

import scala.util.Random

object FIFO3Sim {
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

      {
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

      {
        val data0 = Random.nextInt().abs
        val data1 = Random.nextInt().abs
        val data2 = Random.nextInt().abs

        fork {
          sleep(6432)
          writeChannel(dut.io.in1, data0)
          writeChannel(dut.io.in1, data1)
          writeChannel(dut.io.in1, data2)
        }

        assert(readChannel(dut.io.out1).toInt == data0)
        assert(readChannel(dut.io.out1).toInt == data1)
        assert(readChannel(dut.io.out1).toInt == data2)
      }

      {
        val data0 = Random.nextInt().abs
        val data1 = Random.nextInt().abs
        val data2 = Random.nextInt().abs

        fork {
          writeChannel(dut.io.in1, data0)
          writeChannel(dut.io.in1, data1)
          writeChannel(dut.io.in1, data2)
        }

        sleep(6432)
        assert(readChannel(dut.io.out1).toInt == data0)
        assert(readChannel(dut.io.out1).toInt == data1)
        assert(readChannel(dut.io.out1).toInt == data2)
      }
    }
  }

}
