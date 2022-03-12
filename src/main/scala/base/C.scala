package base

import spinal.core._

class C extends Component {
  val io = new Bundle {
    val in1 = in Bool()
    val in2 = in Bool()
    val out1 = (out Bool()).noCombLoopCheck
  }

  io.out1 := io.in1 && io.out1 || io.in2 && io.out1 || io.in1 && io.in2
}
