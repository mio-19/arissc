import base.C
import spinal.core.SpinalVerilog

object TopLevelVerilog {
  def main(args: Array[String]) {
    SpinalVerilog(new C)
  }
}
