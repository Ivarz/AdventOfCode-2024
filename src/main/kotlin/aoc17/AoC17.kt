package aoc17

import kotlin.math.pow

enum class Opcode(code: Int) {
    OP_ADV(0),
    OP_BXL(1),
    OP_BST(2),
    OP_JNZ(3),
    OP_BXC(4),
    OP_OUT(5),
    OP_BDV(6),
    OP_CDV(7);
    companion object {
        fun parse(value: Int): Opcode {
            return when (value) {
                0 -> OP_ADV
                1 -> OP_BXL
                2 -> OP_BST
                3 -> OP_JNZ
                4 -> OP_BXC
                5 -> OP_OUT
                6 -> OP_BDV
                7 -> OP_CDV
                else -> throw Exception("Unknown opcode: $value")
            }
        }
    }
}

typealias Operand = Int
data class Memory(var regA: Long, var regB: Long, var regC: Long)

data class ChronospatialComputer(val program: List<Int>, var memory: Memory) {
    var ipointer = 0
    var output = mutableListOf<Long>()

    fun returnOutput(): String {
        return output.joinToString(",")
    }

    fun run(): String {
        while (ipointer < program.size) {
            val opcode = Opcode.parse(program[ipointer])
            val operand = program[ipointer + 1]
            when (opcode) {
                Opcode.OP_ADV -> {
                    val result = when (operand) {
                        0, 1, 2, 3 -> memory.regA / Math.pow(2.0, operand.toDouble()).toLong()
                        4 -> memory.regA / Math.pow(2.0, memory.regA.toDouble()).toLong()
                        5 -> memory.regA / Math.pow(2.0, memory.regB.toDouble()).toLong()
                        6 -> memory.regA / Math.pow(2.0, memory.regC.toDouble()).toLong()
                        7 ->  throw Exception(" Invalid combo operand: $operand")
                        else -> throw Exception("Unknown operand: $operand")
                    }
                    memory.regA = result
                    ipointer += 2
                }
                Opcode.OP_BXL -> {
                    memory.regB = memory.regB xor operand.toLong()
                    ipointer += 2
                }

                Opcode.OP_BST -> {
                    val result = when (operand) {
                        0, 1, 2, 3 -> operand % 8
                        4 -> memory.regA % 8
                        5 -> memory.regB % 8
                        6 -> memory.regC % 8
                        7 ->  throw Exception(" Invalid combo operand: $operand")
                        else -> throw Exception("Unknown operand: $operand")
                    }
                    memory.regB = result.toLong()
                    ipointer += 2
                }
                Opcode.OP_JNZ -> {
                    if (memory.regA != 0L) {
                        ipointer = operand
                    } else {
                        ipointer += 2
                    }

                }
                Opcode.OP_BXC -> {
                    memory.regB = memory.regB xor memory.regC
                    ipointer += 2

                }
                Opcode.OP_OUT -> {
                    val result = when (operand) {
                        0, 1, 2, 3 -> operand % 8
                        4 -> memory.regA % 8
                        5 -> memory.regB % 8
                        6 -> memory.regC % 8
                        7 ->  throw Exception(" Invalid combo operand: $operand")
                        else -> throw Exception("Unknown operand: $operand")
                    }
                    output.add(result.toLong())
                    ipointer += 2

                }
                Opcode.OP_BDV -> {
                    val result = when (operand) {
                        0, 1, 2, 3 -> memory.regA / Math.pow(2.0, operand.toDouble()).toLong()
                        4 -> memory.regA / Math.pow(2.0, memory.regA.toDouble()).toLong()
                        5 -> memory.regA / Math.pow(2.0, memory.regB.toDouble()).toLong()
                        6 -> memory.regA / Math.pow(2.0, memory.regC.toDouble()).toLong()
                        7 -> throw Exception(" Invalid combo operand: $operand")
                        else -> throw Exception("Unknown operand: $operand")
                    }
                    memory.regB = result
                    ipointer += 2
                }
                Opcode.OP_CDV -> {
                    val result = when (operand) {
                        0, 1, 2, 3 -> memory.regA / Math.pow(2.0, operand.toDouble()).toLong()
                        4 -> memory.regA / Math.pow(2.0, memory.regA.toDouble()).toLong()
                        5 -> memory.regA / Math.pow(2.0, memory.regB.toDouble()).toLong()
                        6 -> memory.regA / Math.pow(2.0, memory.regC.toDouble()).toLong()
                        7 -> throw Exception(" Invalid combo operand: $operand")
                        else -> throw Exception("Unknown operand: $operand")
                    }
                    memory.regC = result
                    ipointer += 2
                }
                else -> throw Exception("Unknown opcode: $opcode")
            }
        }
        return returnOutput()
    }
}
class AoC17 {
}