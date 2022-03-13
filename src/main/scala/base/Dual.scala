package base

import spinal.core._

case class Dual[T <: Data](data: T) extends Bundle {
  val ones = data
  val flipped = data

  def isCleared: Bool = (ones ## flipped).asBits.asUInt === U(0)

  def isValid: Bool = ~ones.asBits === flipped.asBits

  def unsafeExtract = ones
}

object Dual {
  def from[T <: Data](x: T): Dual[T] = (x.asBits ## ~x.asBits).as(new Dual(x))
}
