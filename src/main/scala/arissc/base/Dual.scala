package arissc.base

import spinal.core._
import spinal.core.sim._
import arissc.base.simutils._

case class Dual[T <: Data](data: T) extends Bundle {
  val ones = data.clone()
  val flipped = data.clone()

  def isEmpty: Bool = (ones ## flipped).asBits.asUInt === U(0)

  def isEmptySim: Boolean = ones.asBigInt == 0 && flipped.asBigInt == 0

  def isValid: Bool = ~ones.asBits === flipped.asBits

  def unsafeExtract = ones

  def unsafeVerify[U <: Data](x: U): Bool = this.asBits === Dual.from(x).asBits
}

object Dual {
  def from[T <: Data](x: T): Dual[T] = (x.asBits ## ~x.asBits).as(new Dual(x))

  def empty[T <: Data](x: T): Dual[T] = U(0).as(new Dual(x))

  def emptyFor[T <: Data](x: Dual[T]): Dual[T] = Zeros(x)
}
