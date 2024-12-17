package org.infovars

import java.io.File
import kotlin.math.absoluteValue

class AoC2 {
    companion object {
        fun loadData(): List<List<Int>> {
            return File("src/main/resources/aoc_2_input.txt")
                .readLines()
                .map { line -> line.split(" ")
                    .map { it.toInt() }
                }
        }
        fun isSafe(list: List<Int>): Boolean {
            val monotonicity = list[0].compareTo(list[1])
            for (window in list.windowed(2, 1)) {
                if (window[0].compareTo(window[1]) != monotonicity) {
                    return false
                }
                val diff = (window[1] - window[0]).absoluteValue
                if (diff < 1 || diff > 3) {
                    return false
                }
            }
            return true
        }
        fun isSafeDampened(list: List<Int>): Boolean {
            var safetyLevels = mutableListOf(isSafe(list))
            for ((indexToRm, _) in list.withIndex()) {
                var modList = list.toMutableList()
                modList.removeAt(indexToRm)
                safetyLevels.add(isSafe(modList))
            }
            return safetyLevels.any { it }
        }

        fun solutionPart1(): Int {
            val data = loadData()
            return data
                .map { isSafe(it) }
                .filter { it }
                .size
        }

        fun solutionPart2(): Int {
            val data = loadData()
            return data
                .map { isSafeDampened(it) }
                .filter { it }
                .size
        }
    }
}