package arissc.base

package object utils {

  def flipBigInt(width: Int, x: BigInt): BigInt = {
    if (x < 0) throw new IllegalArgumentException("Negative input")
    if (width < x.bitLength) throw new IllegalArgumentException("width too short")
    var result = BigInt(0)
    for (i <- 0 until width) {
      if (!x.testBit(i)) result = result.setBit(i)
    }
    result
  }

}
