// Main ALU
// Assumes all data is signed.

package pipeline

import chisel3._

class ALU(width: Int) extends Module {
	val opWidth = 4

	val noOP = "b0000".U
	val addOP = "b0001".U
	val subOP = "b0010".U
	val mulOP = "b0011".U
	val sllOP = "b".U

	var overflow = Bool(false)
	var zero = Bool(false)

/*
	def flag(num: UInt, num1: UInt, num2: UInt): UInt = {
		val addOFlow = Bool((num1 +& num2).getWidth > width)
		val mulOFlow = Bool((num1 * num2).getWidth > width)
		overflow = addOFlow || mulOFlow
		zero = num === 0.U
		num
	}
*/

	def clearFlags() = {
		overflow = Bool(false)
		zero = Bool(false)
	}

	// ARITHMETIC OPERATIONS


	def add(num1: SInt, num2: SInt): SInt = {
		overflow = Bool((num1 +& num2).getWidth > width)
		zero = (num1 + num2) === 0.S
		num1 + num2
	}

	def sub(num1: SInt, num2: SInt): SInt = {
		val ret = num1 - num2
		zero = ret === 0.S
		ret
	}

	def mul(num1: SInt, num2: SInt): SInt = {
		overflow = Bool((num1 * num2).getWidth > width)
		num1 * num2
	}

	// LOGICAL SHIFTS
	def sll(num: SInt, shift: UInt) = num << shift

	def srl(num: SInt, shift: UInt): SInt = {
		val x = Seq(0.asSInt(1.W), num)
		val y = x >> shift
		y(num.getWidth, 0)
	}

	// BITWISE LOGIC
	def and(num1: SInt, num2: SInt) = num1 & num2
	def or(num1: SInt, num2: SInt) = num1 | num2
	def not(num: SInt) = ~num


	// EQUALTIY CHECKING
	// Set-less-than
	def slt(num: SInt, comp: SInt) = Mux(num < comp, 1.S, 0.S)
	// Set-greater-than
	def sgt(num: SInt, comp: SInt) = Mux(num > comp, 1.S, 0.S)
	// Set-equal
	def seq(num: SInt, comp: SInt) = Mux(num === comp, 1.S, 0.S)
	// Set-not-equal
	def sne(num: SInt, comp: SInt) = Mux(num =/= comp, 1.S, 0.S)


//	def signAdd(num1: SInt, num2: SInt) = signFlag(num1 + num2)

	val io = IO(new Bundle {
		val num1 = Input(UInt(width.W))
		val num2 = Input(UInt(width.W))
		val op = Input(UInt(4.W))

		val out = Output(UInt(width.W))
		val overflow = Output(Bool())
		val zero = Output(Bool())
	})

	io.overflow := overflow
	io.zero := zero
	io.out := 0.U // Change soon.
}
