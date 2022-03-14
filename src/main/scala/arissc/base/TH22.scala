package arissc.base

import spinal.core._

class TH22 extends Component {
  val io = new Bundle {
    val in1 = in Bool()
    val in2 = in Bool()
    val out1 = (out Bool()).noCombLoopCheck
  }

  io.out1 := Mux(io.in1 && io.in2, True, Mux(io.in1 ## io.in2 === False ## False, False, io.out1))
}
