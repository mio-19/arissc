package arissc.base

import spinal.core._

// in1 : dual valid, ack = False; out1 : dual cleared, ack = False ;;; in1 Valid out1 Empty
// in1 : dual valid, ack = False; out1 : ???, ack = False
// in1 : dual valid, ack = False; out1 : dual valid, ack = False ;;; in1 Valid out1 Valid
// in1 : dual ???, ack = True; out1 : dual valid, ack = False ;; data completeness
// in1 : dual ???, ack = True; out1 : dual valid, ack = True
// in1 : dual cleared, ack = True; out1 : dual ???, ack = True
// in1 : dual cleared, ack = True; out1 : dual cleared, ack = True
// in1 : dual cleared, ack = False; out1 : dual cleared, ack = ??? ;; null completeness


class Regi[T <: Data](data: T) extends Component {
  val io = new Bundle {
    val in1 = ChannelIn(data).noCombLoopCheck
    val out1 = ChannelOut(data).noCombLoopCheck
  }

  io.in1.ack := Mux(ClockDomain.current.readResetWire, False, Mux(io.in1.isStatusValid && io.in1 === io.out1, True, Mux(io.in1.isStatusCleared && io.out1.dual.isEmpty, False, io.in1.ack)))
  io.out1.dual := Mux(ClockDomain.current.readResetWire, Dual.emptyFor(io.out1.dual), Mux(io.out1.isStatusWaitingOrReturning, (io.in1.dual.asBits | io.out1.dual.asBits).as(Dual(data)), Mux(io.out1.isStatusAcked, Dual.emptyFor(io.out1.dual), io.out1.dual)))
}
