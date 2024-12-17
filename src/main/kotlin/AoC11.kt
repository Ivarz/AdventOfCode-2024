package org.infovars

import java.io.File

class AoC11 {
    companion object {
        fun loadData(fname: String): MutableList<Long> {
            return File(fname).readLines()[0].split(" ").map { it.toLong() }.toMutableList()
        }
        fun blink(stones: MutableList<Long>, times: Int) {
            repeat(times) {
                for (i in stones.lastIndex downTo 0) {
                    val currStone = stones[i]
                    val currStoneStr = currStone.toString()
                    if (currStone == 0L) {
                        stones[i] = 1
                    } else if (currStoneStr.length % 2 == 0) {
                        val midpoint = currStoneStr.length / 2
                        val leftStone = currStoneStr.substring(0, midpoint).toLong()
                        val rightStone = currStoneStr.substring(midpoint, currStoneStr.length).toLong()
                        stones[i] = rightStone
                        stones.add(i, leftStone)
                    } else {
                        stones[i] *= 2024L
                    }
                }
            }
        }
        fun blink2(stoneCounts: Map<Long, Long>, times: Int): Map<Long, Long> {
            var currMap = stoneCounts
            repeat(times) {
                val newMap = currMap.toMutableMap()
                for ((stone, stoneCount) in currMap) {
                    val stoneStr = stone.toString()
                    if (stone == 0L) {
                        val currValue = newMap.getOrDefault(1, 0)
                        newMap[1] = currValue + stoneCount
                        newMap[stone] = newMap.getOrDefault(stone, 0) - stoneCount
                    } else if (stoneStr.length % 2 == 0) {
                        val midpoint = stoneStr.length / 2
                        val leftStone = stoneStr.substring(0, midpoint).toLong()
                        val rightStone = stoneStr.substring(midpoint, stoneStr.length).toLong()

                        val currRightValue = newMap.getOrDefault(rightStone, 0)
                        newMap[rightStone] = currRightValue + stoneCount
                        val currLeftValue = newMap.getOrDefault(leftStone, 0)
                        newMap[leftStone] = currLeftValue + stoneCount
                        newMap[stone] = newMap.getOrDefault(stone, 0) - stoneCount
                    } else {
                        val currValue = newMap.getOrDefault(stone*2024, 0)
                        newMap[stone*2024] = currValue + stoneCount
                        newMap[stone] = newMap.getOrDefault(stone, 0) - stoneCount
                    }
                }
                currMap = newMap
            }
            return currMap
        }

        fun blinkMemo(stones: MutableList<Long>, times: Int, lookupTable: Map<Long, List<Long>>): Long {
            var result = 0L
            for (iteration in 0 until times) {
                val iterationsLeft = times - iteration
                for ((i, currStone) in stones.withIndex()) {
                    val memoizedValue = lookupTable.getOrDefault(currStone, List(times) { 0 })[iterationsLeft]
                    if ( memoizedValue > 0L ) {
                        result += memoizedValue
                        stones.removeAt(i)
                        continue
                    }
                }
            }
            return result
        }
        fun stonesAfterNIterations(stone: Long, iterations: Int): Int {
            val stones = mutableListOf<Long>(stone)
            blink(stones, iterations)
            return stones.size
        }

        fun solutionPart1Runner(fname: String): Int {
            val stones = loadData(fname)
            blink(stones, 25)
            return stones.size
        }
        fun solutionPart2Runner(fname: String): Int {
            val stones = loadData(fname)
            blink2(stones.groupingBy { it }.eachCount().mapValues { it.value.toLong() }, 25)
            return stones.size
        }

        fun solutionPart1(): Int {
            return solutionPart1Runner("src/main/resources/aoc_11_input.txt")
        }
        fun solutionPart2(): Long {
            val stones = loadData("src/main/resources/aoc_11_input.txt")
            val counts = blink2(stones.groupingBy { it }.eachCount().mapValues { it.value.toLong() }, 75)
            var res = 0L
            for ((_, v) in counts) {
                res += v
            }
            return res
        }
    }
}