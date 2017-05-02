// Generic counter object to be used as a component of the Program Counter.

package pipeline

import chisel3._

object Counter {
	def wrap(num: UInt, max: UInt) =
		Mux(num > max, 0.U, num)

	def counter(maxVal: UInt, clk: Bool, rst: Bool, set: Bool, setVal: UInt): UInt = {
		val count = Reg(init=0.asUInt(maxVal.getWidth.W))
		when (clk) {
			when (rst) {
				count := 0.U
			} .elsewhen (set) {
				count := wrap(setVal, maxVal)
			} .otherwise {
				count := wrap(count + 1.U, maxVal)
			}
		}

		count
	}
}
