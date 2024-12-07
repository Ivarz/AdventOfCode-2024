package org.infovars

import java.io.File
import kotlin.math.log
import kotlin.math.pow

typealias Op = (Long, Long) -> Long

class AoC7 {
    companion object {
        fun concatLong(a: Long, b: Long): Long {
            return (a.toString() + b.toString()).toLong()
        }
        fun loadData(fname: String): List<Pair<Long, List<Long>>> {
            val lines = File(fname).readLines()
            val result = mutableListOf<Pair<Long, List<Long>>>()
            for (line in lines) {
                val fields = line.split(" ")
                val fst = fields[0].split(":")[0].toLong()
                val rest = fields.slice(1 until fields.size).map { it.toLong() }
                result.add(Pair(fst, rest))
            }
            return result
        }
        fun indicesToChange(number: Int): List<Int> {
            val indices = mutableListOf<Int>()
            var currentNumber = number
            while (currentNumber > 0) {
                val largesBitIdx = log(currentNumber.toDouble(), 2.0).toInt()
                val toSubtract = 2.0.pow(largesBitIdx.toDouble()).toInt()
                currentNumber -= toSubtract
                indices.add(largesBitIdx)
            }
            return indices
        }

        fun generateOperatorCombos(size: Int, basePattern: List<Op>, opToAdd: Op): List<List<Op>> {
            val possibleCombos = 2.0.pow(size.toDouble()).toInt()
            val patterns = mutableListOf<List<Op>>()
            for (i in 0 until possibleCombos) {
                val indices = indicesToChange(i)
                val basePatternTmp = basePattern.toMutableList()
                for (idx in indices) {
                    basePatternTmp[idx] = opToAdd
                }
                patterns.add(basePatternTmp)
            }
            return patterns
        }

        fun applyOps(nums: List<Long>, ops: List<Op>): Long {
            var result = nums[0]
            for (i in 1 until nums.size) {
                result = ops[i-1](result, nums[i])
            }
            return result
        }
        fun hasValidCombos(nums: List<Long>, opsList: List<List<Op>>, result: Long): Boolean {
            for (ops in opsList) {
                val currRes = applyOps(nums, ops)
                if (currRes == result) return true
            }
            return false
        }
        fun solutionPart1Runner(fname: String): Long {
           val data = loadData(fname)
           val validData = data.filter {
               val (res, nums) = it
               val opCount = nums.size - 1
               val baseOp: Op = Long::plus
               val opToAdd: Op = Long::times
               val basePattern = List(opCount) { baseOp }
               val ops = generateOperatorCombos(opCount, basePattern, opToAdd)
               hasValidCombos(nums, ops, res)
           }
           return validData.sumOf {
               val (res, _) = it
               res
           }
        }

        fun solutionPart1(): Long {
            return solutionPart1Runner("src/main/resources/aoc_7_input.txt")
        }

        fun solutionPart2Runner(fname: String): Long {
            val data = loadData(fname)
            val validData = data.filter {
                val (res, nums) = it
                val opCount = nums.size - 1
                val baseOp: Op = Long::plus
                val opToAdd: Op = Long::times
                val basePattern = List(opCount) { baseOp }
                val allOps = mutableListOf<List<Op>>()
                val opsTmp = generateOperatorCombos(opCount, basePattern, opToAdd)
                for (newBasePattern in opsTmp) {
                    val newOpToAdd: Op = ::concatLong
                    val newOps = generateOperatorCombos(opCount, newBasePattern, newOpToAdd)
                    allOps += newOps
                }
                hasValidCombos(nums, allOps, res)
            }
            return validData.sumOf {
                val (res, _) = it
                res
            }
        }

        fun solutionPart2(): Long {
            return solutionPart2Runner("src/main/resources/aoc_7_input.txt")
        }
    }
}