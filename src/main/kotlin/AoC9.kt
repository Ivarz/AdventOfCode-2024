package org.infovars

import java.io.File
import java.util.*

class AoC9 {
    companion object {
        fun loadData(fname: String): String {
           return File(fname).readLines()[0]
        }

        fun buildDisk(diskMap: String): MutableList<Long> {
            val result = mutableListOf<Long>()
            for ((idx, char) in diskMap.withIndex()) {
                val num = char.toString().toInt()
                val fileId = if (idx % 2 == 0) { idx / 2 } else { -1 }
                for (i in 1..num) {
                    result.add(fileId.toLong())
                }
            }
            return result
        }

        fun buildIntervals(disk: List<Long>): MutableList<Pair<Int, Int>> {
            var i = 0;
            var j = 0;
            val result = mutableListOf<Pair<Int, Int>>()
            while (j < disk.size) {
                while (j < disk.size && disk[i] == disk[j]) {
                    j++
                }
                result.add(Pair(i, j))
                i = j
            }
            return result
        }

        fun compressDisk(disk: List<Long>) {
            var i = 0
            var j = disk.lastIndex
            while (i < j) {
                if (disk[i] >= 0) {
                    i++
                    continue
                }
                if (disk[j] < 0) {
                    j--
                    continue
                }
                Collections.swap(disk, i, j)
            }
        }

        fun setValuesInInterval(disk: MutableList<Long>, interval: Pair<Int, Int>, value: Long) {
            for (i in interval.first until interval.second) {
                disk[i] = value
            }
        }

        fun compressDisk2(disk: MutableList<Long>) {
            var intervals: MutableList<Pair<Int, Int>> = buildIntervals(disk)
            var j = intervals.lastIndex
            while (intervals.size > 1) {
                val targetInterval = intervals.removeLast()
                val currNum: Long = disk[targetInterval.first]
                if (currNum < 0) {
                    continue
                }
                val tgtIntervalSize = targetInterval.second - targetInterval.first
                for ((idx, currInterval) in intervals.withIndex()) {
                    if (disk[currInterval.first] >= 0) continue
                    val currIntervalSize = currInterval.second - currInterval.first
                    if (currIntervalSize >= tgtIntervalSize) {
                        val distance = targetInterval.first - currInterval.first
                        val newTargetInterval = Pair(targetInterval.first - distance, targetInterval.second - distance)
                        val residueInterval = Pair(newTargetInterval.second, newTargetInterval.second + (currIntervalSize - tgtIntervalSize) )
                        if (residueInterval.second > residueInterval.first) {
                            intervals.add(idx, residueInterval)
                        }
                        intervals.add(idx, newTargetInterval)
                        setValuesInInterval(disk, targetInterval, -1)
                        setValuesInInterval(disk, newTargetInterval, currNum)
                        break
                    }

                }

            }

        }
        fun checksum(disk: List<Long>): Long {
            var sum: Long = 0
            for ((idx, value) in disk.withIndex()) {
                if (value < 0) {
                    continue
                }
                sum += value*idx
            }
            return sum
        }

        fun solutionPart1Runner(fname: String): Long {
            val diskMap = loadData(fname)
            val disk = buildDisk(diskMap)
            compressDisk(disk)
            return checksum(disk)
        }
        fun solutionPart1(): Long {
            return solutionPart1Runner("src/main/resources/aoc_9_input.txt")
        }
        fun solutionPart2Runner(fname: String): Long {
            val diskMap = loadData(fname)
            val disk = buildDisk(diskMap)
            compressDisk2(disk)
            return checksum(disk)
        }
        fun solutionPart2(): Long {
            return solutionPart2Runner("src/main/resources/aoc_9_input.txt")
        }
    }
}