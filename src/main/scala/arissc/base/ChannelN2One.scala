package arissc.base

import spinal.core._

// gettingI true ...
// gettingI true in1[i] Valid out1 Empty
// gettingI false ...
// gettingI false in1[i] Valid out1 Empty
// gettingI false in1[i] Valid out1 ...
// gettingI false in1[i] Valid out1 Sent
// gettingI true in1[i] ACKed out1 ...
// gettingI true out1 Empty

class ChannelN2One[T <: Data](data: T, size : Int) extends Component {
  val io = new Bundle {
    val in1 = Vec(ChannelIn(data), size)

    val out1 = ChannelOut(data).noCombLoopCheck
  }

  //todo
}
