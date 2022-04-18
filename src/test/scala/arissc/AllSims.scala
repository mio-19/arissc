package arissc

import arissc.base._

object AllSims {
  def main(args: Array[String]): Unit = {
    RegiSim.main(args)
    FIFO2Sim.main(args)
    // todo: more sims
  }
}
