package arissc.base

import spinal.core._

class ChannelOne2N[T <: Data](data: T, size : Int) extends Component {
  val io = new Bundle {
    val in1 = ChannelIn(data)

    val out1 = Vec(ChannelOut(data), size)
  }

  //todo
}
