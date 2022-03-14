package base

import spinal.core._

// Drop data that weren't handled in time
// fifo can be useful

// en True out1 Empty / Waiting
// en True out1 ??? / Waiting
// en True out1 Valid
// en ?? out1 Sent / Acked
// en ?? out1 Cleared
// en ?? out1 Empty
class SyncInToChannelDrop[T <: Data](data: T) extends Component {
  val io = new Bundle {
    val in1 = SyncIn(data)

    val out1 = ChannelOut(data).noCombLoopCheck
  }

  val in1 = RegNext(io.in1.x).as(data)
  val en = RegNext(io.in1.en)

  io.out1.dual := Mux(ClockDomain.current.readResetWire, Dual.emptyFor(io.out1.dual), Mux(en && io.out1.isStatusWaitingOrReturning, Dual.from(in1), Mux(io.out1.isStatusAcked, Dual.emptyFor(io.out1.dual), io.out1.dual)))
}
