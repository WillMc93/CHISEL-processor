// Main ALU

package pipeline

import chisel3._

class ALU(width: Int) extends Module {
	val opWidth = 3

	var overflow = Bool(false)
	var zero = Bool(false)

	def flag(num: UInt, num1: UInt, num2: UInt): UInt = {
		val overflowTest = num1 +& num2
		overflow = Bool(overflowTest.getWidth > width)
		zero = num === 0.U
		num
	}

//	def signFlag(num: SInt, )

	def add(num1: UInt, num2: UInt) = flag(num1 + num2, num1, num2)
	def sub(num1: UInt, num2: UInt) = flag(num1 - num2, num1, num2)

//	def signAdd(num1: SInt, num2: SInt) = signFlag(num1 + num2)

	val io = IO(new Bundle {
		val num1 = Input(UInt(width.W))
		val num2 = Input(UInt(width.W))
		val op = Input(UInt(3.W))

		val out = Output(UInt(width.W))
		val overflow = Output(Bool())
		val zero = Output(Bool())
	})

	io.overflow := overflow
	io.zero := zero
	io.out := 0.U // Change soon.
}
