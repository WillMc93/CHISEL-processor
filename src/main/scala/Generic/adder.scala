// Generic adder object that will be used as a component in several places.
package pipeline

import chisel3._

object adder {
	def wrap(num: UInt, max: UInt) =
		Mux(num > max, 0.U, num)
}
