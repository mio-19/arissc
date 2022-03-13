package base

import spinal.core._

// @posedge clk; in1 Valid
// ACK
// Thread 1
// ???
// in1 Cleared
// in1 Empty

class ChannelToSyncOut[T <: Data](data: T) extends Component {
  val io = new Bundle {
    val in1 = ChannelIn(data).noCombLoopCheck

    val out1 = SyncOut(data)
  }

  val x = RegNextWhen(io.in1.unsafeExtract, io.in1.isStatusValid)
  val in1Locked = RegNext(io.in1)
  io.out1.x := x
  io.out1.en := in1Locked.unsafeVerify(x)

  io.in1.ack := Mux(io.in1.isStatusCleared, False, Mux(io.in1.isStatusWaitingOrReturning && io.in1.unsafeVerify(x), True, io.in1.ack))
}
