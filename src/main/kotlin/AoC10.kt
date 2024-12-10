package org.infovars

import java.io.File

class AoC10 {
    companion object {

        fun isNeighbour(src: Int, dst: Int): Boolean {
            return (dst - src) == 1
        }

        fun countFoundPeaks(foundPeaks: List<List<Int>>): Int {
            var count = 0
            for (row in foundPeaks) {
                for (col in row) {
                    count += col
                }
            }
            return count
        }

        fun dfsTraverse(topomap: List<List<Int>>, src: Pair<Int, Int>, foundPeaks: MutableList<MutableList<Int>>) {
            val (row, col) = src
            val mod = listOf( Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))
            val currHeight = topomap[row][col]
            if (currHeight == 9) {
                foundPeaks[row][col] = 1
                return
            }
            for ((rowMod, colMod) in mod) {
                val neighRow = row + rowMod
                val neighCol = col + colMod
                if (neighRow < 0 ||
                    neighCol < 0 ||
                    neighRow >= topomap.size ||
                    neighCol >= topomap[neighRow].size ||
                    foundPeaks[neighRow][neighCol] > 0 ) {
                    continue
                }
                val neighbourValue = topomap[neighRow][neighCol]
                if (isNeighbour(currHeight, neighbourValue)) {
                    dfsTraverse(topomap, Pair(neighRow, neighCol), foundPeaks)
                }
            }
        }
        fun countPeaksFromSource(topomap: List<List<Int>>, src: Pair<Int, Int>): Int {
            val visitedPeaks = MutableList(topomap.size) {
                MutableList(topomap[0].size) { 0 }
            }
            dfsTraverse(topomap, src, visitedPeaks)
            return countFoundPeaks(visitedPeaks)
        }

        fun dfsTraverseV2(topomap: List<List<Int>>, src: Pair<Int, Int>, foundPeaks: MutableList<MutableList<Int>>) {
            val (row, col) = src
            val mod = listOf( Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))
            val currHeight = topomap[row][col]
            if (currHeight == 9) {
                foundPeaks[row][col] += 1
                return
            }
            for ((rowMod, colMod) in mod) {
                val neighRow = row + rowMod
                val neighCol = col + colMod
                if (neighRow < 0 ||
                    neighCol < 0 ||
                    neighRow >= topomap.size ||
                    neighCol >= topomap[neighRow].size) {
                    continue
                }
                val neighbourValue = topomap[neighRow][neighCol]
                if (isNeighbour(currHeight, neighbourValue)) {
                    dfsTraverseV2(topomap, Pair(neighRow, neighCol), foundPeaks)
                }
            }
        }
        fun countPathsFromSourceToPeaks(topomap: List<List<Int>>, src: Pair<Int, Int>): Int {
            val visitedPeaks = MutableList(topomap.size) {
                MutableList(topomap[0].size) { 0 }
            }
            dfsTraverseV2(topomap, src, visitedPeaks)
            return countFoundPeaks(visitedPeaks)
        }

        fun solutionPart1Runner(fname: String): Int {
            var result = 0
            val topomap = loadTopoMap(fname)
            for ((rowIdx, row) in topomap.withIndex()) {
                for ((colIdx, col) in row.withIndex()) {
                    if (col == 0) {
                        result += countPeaksFromSource(topomap, Pair(rowIdx, colIdx))
                    }
                }
            }
            return result
        }

        fun solutionPart2Runner(fname: String): Int {
            var result = 0
            val topomap = loadTopoMap(fname)
            for ((rowIdx, row) in topomap.withIndex()) {
                for ((colIdx, col) in row.withIndex()) {
                    if (col == 0) {
                        result += countPathsFromSourceToPeaks(topomap, Pair(rowIdx, colIdx))
                    }
                }
            }
            return result
        }

        fun loadTopoMap(fname: String): List<List<Int>> {
            val result = mutableListOf<MutableList<Int>>()
            for (line in File(fname).readLines()) {
                result.add(line.toCharArray().map { it.toString().toInt() }.toMutableList())
            }
            return result
        }

        fun solutionPart1(): Int {
            return solutionPart1Runner("src/main/resources/aoc_10_input.txt")
        }
        fun solutionPart2(): Int {
            return solutionPart2Runner("src/main/resources/aoc_10_input.txt")
        }
    }
}