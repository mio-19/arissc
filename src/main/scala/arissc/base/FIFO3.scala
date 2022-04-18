package arissc.base

import spinal.core.{Bundle, Component, Data}

class FIFO3[T <: Data]( data: T) extends Component {
  val io = new Bundle {
    val in1 = ChannelIn(data)
    val out1 = ChannelOut(data)
  }

  val regi0 = new Regi(data)
  val regi1 = new Regi(data)
  val regi2 = new Regi(data)
  io.in1 <> regi0.io.in1
  regi0.io.out1 <> regi1.io.in1
  regi1.io.out1 <> regi2.io.in1
  regi2.io.out1 <> io.out1
}
