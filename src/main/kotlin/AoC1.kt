package org.infovars

import java.io.File
import kotlin.math.absoluteValue

class AoC1 {
    companion object {
        fun loadData(): Pair<MutableList<Int>, MutableList<Int>> {
            var fstColumn = mutableListOf<Int>()
            var sndColumn = mutableListOf<Int>()
            File("src/main/resources/aoc_1_input.txt")
                .readLines()
                .map { it.split("\t") }
                .map {
                    fstColumn.add(it[0].toInt())
                    sndColumn.add(it[1].toInt())
                }
            return Pair(fstColumn, sndColumn)
        }
        fun solutionPart1(): Int {
            var (fstColumn, sndColumn) = loadData()
            fstColumn.sort()
            sndColumn.sort()
            var totalDifference = 0
            for ((i, fstValue) in fstColumn.withIndex()) {
                totalDifference += (fstValue - sndColumn[i]).absoluteValue
            }
            return totalDifference
        }

        fun solutionPart2(): Int {
            var (fstColumn, sndColumn) = loadData()
            var rightCount = sndColumn.groupingBy { it }.eachCount()
            var totalDifference = fstColumn.sumOf {
                fstValue -> fstValue * rightCount.getOrDefault(fstValue, 0)
            }
            return totalDifference
        }
    }
}