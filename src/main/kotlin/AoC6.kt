package org.infovars

import java.io.File

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}
class AoC6 {
    companion object {
        fun loadData(fname: String): List<CharArray> {
            return File(fname).readLines().map {it.toCharArray()}
        }
        fun locateGuard(grid: List<CharArray>): Pair<Int, Int> {
            for ((i, row) in grid.withIndex()) {
                for ((j, c) in row.withIndex()) {
                    if (c == '^') {
                        return Pair(i, j)
                    }
                }
            }
            return Pair(0, 0)
        }

        fun walk(grid: List<CharArray>, startLoc: Pair<Int, Int>, direction: Direction): Pair<Int,Int> {
            var (i, j) = startLoc
            var newI = i
            var newJ = j
            grid[i][j] = 'X'
            when (direction) {
                Direction.UP -> newI--
                Direction.DOWN -> newI++
                Direction.LEFT -> newJ--
                Direction.RIGHT -> newJ++
            }
            while (newI >= 0 && newI < grid.size && newJ >= 0 && newJ < grid[0].size && grid[newI][newJ] != '#') {
                i = newI
                j = newJ
                grid[i][j] = 'X'
                when (direction) {
                    Direction.UP -> newI--
                    Direction.DOWN -> newI++
                    Direction.LEFT -> newJ--
                    Direction.RIGHT -> newJ++
                }
            }
            return Pair(i, j)
        }

        fun walkGuard(grid: List<CharArray>): Pair<Pair<Int, Int>, Int> {
            var currPos = locateGuard(grid)
            val walkDirectionOrder = listOf(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT)
            var orderIdx = 0
            var iterations = 0
            do {
                iterations++
                val walkDir = walkDirectionOrder[orderIdx]
                currPos = walk(grid, currPos, walkDir)
                val (i, j) =  currPos
                orderIdx = (orderIdx + 1) % walkDirectionOrder.size
                if (iterations >= 10000) {
                    return Pair(currPos, iterations)
                }
            } while (i > 0 && i < grid.lastIndex && j > 0 && j < grid[0].lastIndex)
            return Pair(currPos, iterations)
        }

        fun countX(grid: List<CharArray>): Int {
            var count = 0
            for (charr in grid) {
                count += charr.filter { it == 'X' }.count()
            }
            return count
        }

        fun solutionPart1Runner(fname: String): Int {
            var data = loadData(fname)
            val pos = walkGuard(data)
            return countX(data)
        }

        fun solutionPart1(): Int {
            return solutionPart1Runner("src/main/resources/aoc_6_input.txt")
        }

        fun solutionPart2Runner(fname: String): Int {
            val data = loadData(fname)
            val (startI, startJ) = locateGuard(data)
            var loopModifications = 0
            for ((i, row) in data.withIndex()) {
                for ((j, c) in row.withIndex()) {
                    if (c != '.' && c != 'X')
                        continue
                    data[i][j] = '#'
                    val (pos, iterations) = walkGuard(data)
//                    println("${i}, ${j} ${iterations} ${pos}")
                    if (iterations >= 10000) {
                        loopModifications++
                    }
                    data[i][j] = '.'
                    data[startI][startJ] = '^'
                }
            }
            return loopModifications
        }
        fun solutionPart2(): Int {
            return solutionPart2Runner("src/main/resources/aoc_6_input.txt")
        }
    }
}