package base

import spinal.core._

case class Dual[T <: Data](data: T) extends Bundle {
  val ones = data.clone()
  val flipped = data.clone()

  def isEmpty: Bool = (ones ## flipped).asBits.asUInt === U(0)

  def isValid: Bool = ~ones.asBits === flipped.asBits

  def unsafeExtract = ones
}

object Dual {
  def from[T <: Data](x: T): Dual[T] = (x.asBits ## ~x.asBits).as(new Dual(x))
  def empty[T <: Data](x: T): Dual[T] = U(0).as(new Dual(x))
  def emptyFor[T <: Data](x: Dual[T]): Dual[T] = U(0, x.getBitsWidth bits).as(x)
}
