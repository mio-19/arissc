package arissc.base

import spinal.core.{Bundle, Component, Data}

class FIFO[T <: Data](n: Int, data: T) extends Component {
  val io = new Bundle {
    val in1 = ChannelIn(data)
    val out1 = ChannelOut(data)
  }
  if (n < 1) {
    throw new IllegalArgumentException("n<1")
  }

  val fifoArray = Array.fill(n)(new Regi(data))
  io.in1 <> fifoArray(0).io.in1
  for (i <- 0 until n - 1) {
    fifoArray(i).io.out1 <> fifoArray(i + 1).io.in1
  }
  io.out1 <> fifoArray(n - 1).io.out1
}
