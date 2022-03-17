package arissc.base

import spinal.core._
import spinal.core.sim._

// Valid / Waiting - dual valid; ack = False
// Acked - dual ???; ack = True
// Sent / Acked - dual valid; ack = True
// Cleared / Acked - dual cleared; ack = True
// Empty / Waiting - data cleared; ack = False
// Waiting - data ???; ack = False

case class ChannelIn[T <: Data](data: T) extends Bundle {
  val dual = in(Dual(data)) simPublic()
  val ack = out Bool() simPublic()

  // following api can be unsafe
  // for in
  def isStatusValid = dual.isValid && ~ack

  def isStatusValidSim = dual.isValidSim && !ack.toBoolean

  def isStatusCleared = dual.isEmpty && ack

  def isStatusClearedSim = dual.isEmptySim && ack.toBoolean

  // for out
  def isStatusEmpty = dual.isEmpty && ~ack

  def isStatusEmptySim = dual.isEmptySim && !ack.toBoolean

  def isStatusWaitingOrReturning = ~ack

  def isStatusWaitingOrReturningSim = !ack.toBoolean

  def isStatusAcked = ack

  def isStatusSent = dual.isValid && ack

  def unsafeExtract = dual.unsafeExtract

  def unsafeVerify[U <: Data](x: U): Bool = dual.unsafeVerify(x)
}

object ChannelOut {
  def apply[T <: Data](data: T) = ChannelIn(data).flip()
}
