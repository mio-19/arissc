package spinal.core.internals

import spinal.core._

// This file disables PhaseCheckCombinationalLoops in a dirty way
class PhaseCheckCombinationalLoops() extends PhaseCheck {
  override def impl(pc: PhaseContext): Unit = {
  }
}
