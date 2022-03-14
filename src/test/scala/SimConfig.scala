import spinal.core._
import spinal.sim._

object SimConfig {
  val spinalConfig = SpinalConfig(mode = VHDL, defaultClockDomainFrequency = FixedFrequency(10 MHz))
}
