import base._
import spinal.core._

object TopLevelVerilog {
  def main(args: Array[String]) {
    SpinalVerilog(new C)
    SpinalVerilog(new TH12)
    SpinalVerilog(new TH22)
    SpinalVerilog(new TH23)
    SpinalVerilog(new Regi(Bool()))
  }
}
