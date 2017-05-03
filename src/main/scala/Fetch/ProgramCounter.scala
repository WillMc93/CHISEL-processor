// Main program counter that will point to the next instruction.

package pipeline

import chisel3._

class ProgCounter(val width: Int) extends Module {
	def wrap(count: UInt) =
    	Mux(Bool(count.getWidth > width), 0.U, count)

    def counter(clk: Bool, rst: Bool, set: Bool, setVal: UInt): UInt = {
    //	val count = Reg(init=0.asUInt(width.W))
		val count = Reg(init=0.U) // the vagueness seems problematic
    	when (clk) {
			when (rst) {
				count := 0.U
			} .elsewhen (set) {
				count := wrap(setVal) // sure to cause some trouble down the line
			} .otherwise {
				count := wrap(count + 1.U)
			}
		}
      	count
    }

	val io = IO(new Bundle {
		val clk = Input(Bool())
		val rst = Input(Bool())
		val set = Input(Bool())
		val setVal = Input(UInt(width.W))

		val count = Output(UInt(width.W))
	})

	io.count := counter(io.clk, io.rst, io.set, io.setVal)

}
