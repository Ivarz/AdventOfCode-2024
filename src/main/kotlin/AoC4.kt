package org.infovars

import java.io.File

class AoC4 {
    companion object {
        fun loadData(inputFname: String): List<String> {
            return File(inputFname).readLines()
        }
        fun isNeighbour(src: Char, dst: Char): Boolean {
            val neighbourMap = mapOf<Char, Char>(
                'X' to 'M',
                'M' to 'A',
                'A' to 'S',
            )
            return neighbourMap.getOrDefault(src, ' ') == dst
        }

        fun countXmasFromPosStraightLine(data: List<String>, coord: Pair<Int, Int>): Int {
            val (row, col) = coord
            val modifiers = listOf(
                Pair(-1, -1),
                Pair(0, -1),
                Pair(1, -1),
                Pair(-1, 0),
                Pair(1, 0),
                Pair(-1, 1),
                Pair(0, 1),
                Pair(1, 1),
            )
            val src = data[row][col]
            var xmasCount = 0
            for ((rowMod, colMod) in modifiers) {
                var prevChar = src
                var prevRow = row
                var prevCol = col
                while (true) {
                    val nextRow = prevRow + rowMod
                    val nextCol = prevCol + colMod
                    if (nextRow > data.lastIndex
                        || nextCol > data[0].lastIndex
                        || nextRow < 0
                        || nextCol < 0
                    ) { break }
                    val nextChar = data[nextRow][nextCol]
                    if (isNeighbour(prevChar, nextChar)) {
                        if (nextChar == 'S') {
                            xmasCount++
                            break
                        }
                        prevChar = nextChar
                        prevRow = nextRow
                        prevCol = nextCol
                    } else {
                        break
                    }
                }
            }
            return xmasCount
        }

        fun solutionPart1Runner(inputFname: String): Int {
            val data = loadData(inputFname)
            var count = 0
            for (rowIdx in data.indices) {
                for (colIdx in data[rowIdx].indices) {
                    if (data[rowIdx][colIdx] != 'X') continue
                    count += countXmasFromPosStraightLine(data, Pair(rowIdx, colIdx))
                }
            }
            return count
        }
        fun solutionPart1(): Int {
            return solutionPart1Runner("src/main/resources/aoc_4_input.txt")
        }
        fun solutionPart2(): Int {
            return solutionPart2Runner("src/main/resources/aoc_4_input.txt")
        }
        fun solutionPart2Runner(inputFname: String): Int {
            val data = loadData(inputFname)
            var count = 0
            for (i in 1 until data.lastIndex) {
                for (j in 1 until data[0].lastIndex) {
                    if (data[i][j] != 'A') continue
                    val diagonal1 = "${data[i-1][j-1]}${data[i][j]}${data[i+1][j+1]}"
                    val diagonal2 = "${data[i-1][j+1]}${data[i][j]}${data[i+1][j-1]}"
                    if (diagonal1.toCharArray().sorted().joinToString("") == "AMS"
                        && diagonal2.toCharArray().sorted().joinToString("") == "AMS") {
                        count++
                    }
                }
            }
            return count
        }
    }
}