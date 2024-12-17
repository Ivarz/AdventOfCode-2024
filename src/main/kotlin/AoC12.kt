package org.infovars

import java.io.File
import kotlin.collections.ArrayDeque
import kotlin.math.absoluteValue
import kotlin.math.sign

typealias Edge = Pair<Pair<Int, Int>, Pair<Int, Int>>
class AoC12 {
    companion object {
        fun loadData(fname: String): List<CharArray> {
            return File(fname).readLines().map { it.toCharArray()}
        }

        fun findAreaPerimeterFromSrc(data: List<CharArray>, src: Pair<Int, Int>, accountedFields: MutableList<MutableList<Boolean>>): Pair<Int, MutableSet<Edge>> {
            var area = 0
            val (row, col) = src
            val visited = MutableList(data.size) { MutableList(data[0].size) { false } }
            visited[row][col] = true
            val queue = ArrayDeque<Pair<Int, Int>>().apply { add(Pair(row, col)) }
            val modifiers = listOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))
            val srcValue = data[row][col]
            val perimeterEdges = mutableSetOf<Edge>()
            while (queue.isNotEmpty()) {
                val (currRow, currCol) = queue.removeFirst()
                accountedFields[currRow][currCol] = true
                area++
                for ((rowMod, colMod) in modifiers) {
                    val neighRow = currRow + rowMod
                    val neighCol = currCol + colMod
                    val currEdgeOut = Pair(Pair(currRow, currCol), Pair(neighRow, neighCol))
                    if (neighRow < 0 || neighCol < 0 || neighRow >= data.size || neighCol >= data[currRow].size) {
                        perimeterEdges.add(currEdgeOut)
                        continue
                    }

                    val neighbour = data[neighRow][neighCol]
                    if (visited[neighRow][neighCol]) {
                        if (neighbour != srcValue && currEdgeOut !in perimeterEdges) {
                            perimeterEdges.add(currEdgeOut)
                        }
                        continue
                    }
                    visited[neighRow][neighCol] = true
                    if (neighbour == srcValue) {
                        queue.add(Pair(neighRow, neighCol))
                    } else {
                        if (currEdgeOut !in perimeterEdges) {
                            perimeterEdges.add(currEdgeOut)
                        }
                    }
                }
            }
            return Pair(area, perimeterEdges)
        }
        fun findValue(grid: List<CharArray>): Int {
            val accountedFields = MutableList(grid.size) { MutableList(grid.size) { false } }
            var value = 0
            for (rowIdx in grid.indices) {
                for (colIdx in grid[rowIdx].indices) {
                    if (accountedFields[rowIdx][colIdx]) continue
                    val src = Pair(rowIdx, colIdx)
                    val (area, perimeter) = findAreaPerimeterFromSrc(grid, src, accountedFields)
                    value += area * perimeter.size
                }
            }
            return value
        }
        fun countAdjacentEdgeGroupsInCol(edges: List<Pair<Int, Int>>): Int {
            if (edges.size == 1) {
                return 1
            }
            var groups = 1
            val sortedSrc = edges.sortedWith(compareBy { it.first})
            var (prevRow, _) = sortedSrc.first()
            for (idx in 1 .. sortedSrc.lastIndex) {
                val (currRow, _) = sortedSrc[idx]
                if ((prevRow - currRow).absoluteValue == 1) {
                    prevRow = currRow
                    continue
                }
                prevRow = currRow
                groups++
            }

            return groups

        }
        fun countAdjacentEdgeGroupsInRow(edges: List<Pair<Int, Int>>): Int {
            if (edges.size == 1) {
                return 1
            }
            var groups = 1
            val sortedSrc = edges.sortedWith(compareBy { it.second})
            var (_, prevCol) = sortedSrc.first()
            for (idx in 1 .. sortedSrc.lastIndex) {
                val (_, currCol) = sortedSrc[idx]
                if ((prevCol - currCol).absoluteValue == 1) {
                    prevCol = currCol
                    continue
                }
                prevCol = currCol
                groups++
            }

            return groups

        }
        fun countFaces(perimeter: Set<Edge>): Int {
            val perimeterGroupedByDirection = perimeter.groupBy {
                Pair(
                    (it.second.first - it.first.first).sign,
                    (it.second.second - it.first.second).sign
                )
            }
            var totals = 0
            for ((k, v) in perimeterGroupedByDirection) {
                val srcEdges = v.map { it.first }
                //horizontal edges
                if (k == Pair(0, -1) || k == Pair(0, 1)) {
                    val groupedEdgeStack = srcEdges.groupBy { it.second }
                    for ((_, edgeStack) in groupedEdgeStack) {
                        totals += countAdjacentEdgeGroupsInCol(edgeStack)
                    }
                }

                //vertical edges
                if (k == Pair(1, 0) || k == Pair(-1, 0)) {
                    val groupedEdgeStack = srcEdges.groupBy { it.first }
                    for ((_, edgeStack) in groupedEdgeStack) {
                        totals += countAdjacentEdgeGroupsInRow(edgeStack)
                    }
                }
            }
            return totals
        }
        fun findNewValue(grid: List<CharArray>): Int {
            val accountedFields = MutableList(grid.size) { MutableList(grid.size) { false } }
            var value = 0
            for (rowIdx in grid.indices) {
                for (colIdx in grid[rowIdx].indices) {
                    if (accountedFields[rowIdx][colIdx]) continue
                    val src = Pair(rowIdx, colIdx)
                    val (area, perimeter) = findAreaPerimeterFromSrc(grid, src, accountedFields)
                    value += area * countFaces(perimeter)
                }
            }
            return value
        }
        fun solutionPart1(): Int {
            val data = loadData("src/main/resources/aoc_12_input.txt")
            return findValue(data)
        }
        fun solutionPart2(): Int {
            val data = loadData("src/main/resources/aoc_12_input.txt")
            return findNewValue(data)
        }
    }
}