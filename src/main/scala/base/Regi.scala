package base

import spinal.core._

// in1 : dual valid, ack = False; out1 : dual cleared, ack = False ;;; in1 Valid out1 Empty
// in1 : dual valid, ack = False; out1 : ???, ack = False
// in1 : dual valid, ack = False; out1 : dual valid, ack = False ;;; in1 Valid out1 Valid

//Thread 1
// in1 : dual valid, ack = True;
// in1 : dual ???, ack = True;
// in1 : dual empty, ack = True; ;;; in1 Cleared
// in1 : dual empty, ack = False;

// Thread 2
// out1 : dual valid, ack = True; ;;; out1 Acked
// out1 : dual ???, ack = ???
// out1 : dual empty, ack = ???


class Regi[T <: Data](data: T) extends Component {
  val io = new Bundle {
    val in1 = ChannelIn(data).noCombLoopCheck
    val out1 = ChannelOut(data).noCombLoopCheck
  }

  io.in1.ack := Mux(io.in1.isStatusValid && io.out1.isStatusValid, True, Mux(io.in1.isStatusCleared, False, io.in1.ack))
  io.out1.dual := Mux(io.in1.isStatusValid && io.out1.isStatusWaiting, io.in1.dual, Mux(io.out1.isStatusAcked, Dual.emptyFor(io.out1.dual), io.out1.dual))
}
