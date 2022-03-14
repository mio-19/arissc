package arissc.base

import spinal.core._

object Zeros {
  def apply[T<: Data](x:T): T = U(0, x.getBitsWidth bits).as(x)
}
