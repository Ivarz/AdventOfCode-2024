package aoc18

import java.io.File

enum class GridElement {
    SPACE, BLOCK;
    override fun toString(): String {
        return when (this) {
            SPACE -> return "."
            BLOCK -> return "#"
        }
    }
}
data class Grid(val elements: List<GridElement>, val width: Int, val height: Int) {
    fun shortestPath(): Int {
        val (srcX, srcY) = Pair(0, 0)
        val (dstX, dstY) = Pair(width-1, height-1)
        val visited = MutableList(elements.size) { false }
        val dist = MutableList(elements.size) { Int.MAX_VALUE }
        val q = ArrayDeque<Pair<Int, Int>>()
        q.add(Pair(srcX, srcY))
        visited[srcX + srcY * width] = true
        dist[srcX + srcY * width] = 0
        val neighMod = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))
        while (!q.isEmpty()) {
            val (currX, currY) = q.removeFirst()
            for (mod in neighMod) {
                val (dx, dy) = mod
                val neighX = currX + dx
                val neighY = currY + dy
                if (neighX < 0 || neighX >= width || neighY < 0 || neighY >= height) {
                    continue
                }
                val neighIdx = neighX + neighY * width
                if (visited[neighIdx]) {
                    continue
                }
                if (elements[neighIdx] == GridElement.BLOCK) {
                    continue
                }
                q.add(Pair(neighX, neighY))
                visited[neighIdx] = true
                dist[neighIdx] = dist[currX + currY * width] + 1
                if (neighX == dstX && neighY == dstY) {
                    return dist[neighIdx]
                }
            }
        }
        return -1
    }
    fun print() {
        for (y in 0 until height) {
            for (x in 0 until width) {
                print(elements[x + y * width])
            }
            println()
        }
    }
    companion object {
        fun fromFile(fname: String, nlines: Int, width: Int, height: Int): Grid {
            val blocks = File(fname).readLines()
                .take(nlines)
                .map { val (x, y) = it.split(","); Pair(x.toInt(), y.toInt())}
            val gridElements = MutableList(width * height) { GridElement.SPACE }
            for ((x, y) in blocks) {
                gridElements[x + y * width] = GridElement.BLOCK
            }
            return Grid(gridElements, width, height)
        }
    }
}
class AoC18 {
}