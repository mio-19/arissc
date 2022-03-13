package base

import spinal.core._

case class SyncIn[T <: Data](data: T) extends Bundle {
  val x = in(data.clone())
  val en = in Bool()
}

object SyncOut {
  def apply[T <: Data](data: T) = SyncIn(data).flip()
}
