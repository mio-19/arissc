package arissc.base.sim

// extracted from spinal.core.sim, made public

import spinal.core.{BaseType, Data}
import spinal.core.internals.BaseNode
import spinal.sim.{Signal, SimError, SimManager, SimManagerContext}

import scala.collection.mutable.ArrayBuffer

object simutils {
  /** Get a BigInt value from a BaseType */
  def getBigInt(bt: BaseType): BigInt = {
    if (bt.getBitsWidth == 0) return BigInt(0)
    val manager = SimManagerContext.current.manager
    val signal = btToSignal(manager, bt)
    manager.getBigInt(signal)
  }

  def btToSignal(manager: SimManager, bt: BaseNode) = {
    if (bt.algoIncrementale != -1) {
      SimError(s"UNACCESSIBLE SIGNAL : $bt isn't accessible during the simulation.\n- To fix it, call simPublic() on it during the elaboration.")
    }

    manager.raw.userData.asInstanceOf[ArrayBuffer[Signal]](bt.algoInt)
  }

  def getBigInt(data: Data): BigInt = {
    var result = BigInt(0)
    for (x <- data.flatten) {
      result = result << x.getBitsWidth
      result += getBigInt(x)
    }
    result
  }
}
