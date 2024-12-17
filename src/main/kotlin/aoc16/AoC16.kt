package aoc16

import org.infovars.Vec2Int
import java.io.File
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.math.absoluteValue


enum class GridElement {
    START, END, SPACE, WALL, PATH;
    override fun toString(): String {
        when (this) {
            START -> return "S"
            END -> return "E"
            SPACE -> return "."
            WALL -> return "#"
            PATH -> return "O"
        }
    }
}

data class Grid(val elements: MutableList<GridElement>, val width: Int, val height: Int) {
    var startLocation: Vec2Int = Vec2Int(0, 0)
    var endLocation: Vec2Int = Vec2Int(0, 0)

    init {
        findStartEndLocation()
    }
    fun findStartEndLocation() {
        for (row in 0 until height) {
            for (col in 0 until width) {
                if (elements[row*width+col] == GridElement.START) {
                    startLocation = Vec2Int(col, row)
                }
                if (elements[row*width+col] == GridElement.END) {
                    endLocation = Vec2Int(col, row)
                }
            }
        }
    }
    fun getElement(pos: Vec2Int): GridElement {
        return elements[pos.y*width+pos.x]
    }
    fun getElement(x: Int, y: Int): GridElement {
        return elements[y*width+x]
    }

    fun print() {
        for (y in 0 until height) {
            for (x in 0 until width) {
                print(elements[y*width + x])
            }
            println()
        }
        println()
    }

    fun drawPath(path: List<Vec2Int>) {
        val pathElems = elements.toMutableList()
        for ((x, y) in path) {
            pathElems[y*width+x] = GridElement.PATH
        }
        for (y in 0 until height) {
            for (x in 0 until width) {
                print(pathElems[y*width + x])
            }
            println()
        }
        println()
    }
    fun findBestPathNodes(start: Vec2Int, end: Vec2Int, distances: List<Int>): Set<Vec2Int> {
        val visited = MutableList(elements.size) { false }
        val q = ArrayDeque<Pair<Vec2Int, Int>>()
        val result = mutableSetOf<Vec2Int>()

        val directions = listOf(
            Vec2Int(-1, 0), // left
            Vec2Int(0, -1), // up
            Vec2Int(1, 0), // right
            Vec2Int(0, 1), // down
        )
        val startingDirIndices = listOf(0, 1, 2, 3)
        for (i in startingDirIndices) {
            q.add(Pair(start, i))
        }
        visited[start.y*width+start.x] = true
        while (!q.isEmpty()) {
            val (curr, currDirIdx) = q.removeFirst()
            result.add(curr)
            val (currX, currY) = curr
            val currDirection = directions[currDirIdx]
            val currLocValue = distances[currY*width+currX]
//            val neigh = Vec2Int(currX + currDirection.x, currY + currDirection.y)
//            val neighLocValue = distances[neigh.y*width+neigh.x]
//
//            if (neighLocValue == currLocValue-1) {
//                q.add(Pair(neigh, currDirIdx))
//            }
//            if (neighLocValue == currLocValue-1001) {
//                q.add(Pair(neigh, (currDirIdx+1) % directions.size))
//                q.add(Pair(neigh, (currDirIdx+3) % directions.size))
//            }
            for (neighMod in directions ) {
                val neigh = Vec2Int(currX + neighMod.x, currY + neighMod.y)
                val neighLocValue = distances[neigh.y*width+neigh.x]
                if (neighLocValue == currLocValue-1) {
                    q.add(Pair(neigh, currDirIdx))
                }
                if (neighLocValue == currLocValue-1001) {
                    q.add(Pair(neigh, (currDirIdx+1) % directions.size))
                    q.add(Pair(neigh, (currDirIdx+3) % directions.size))
                }
            }
        }
        return result
    }
    fun dijkstra(start: Vec2Int, end: Vec2Int, dirIdx: Int): Pair<Int, List<Int>> {
        val dist = MutableList(elements.size) { Int.MAX_VALUE-10000 }
        val fromDirIdx = MutableList(elements.size) { dirIdx }
        val visited = MutableList(elements.size) { false }
        dist[start.y*width+start.x] = 0
        val q = PriorityQueue<Vec2Int> { a, b -> dist[a.y*width+a.x] - dist[b.y*width+b.x] }
        q.add(start)

        val direction = listOf(
            Vec2Int(-1, 0), // left
            Vec2Int(0, -1), // up
            Vec2Int(1, 0), // right
            Vec2Int(0, 1), // down
            )

        while (!q.isEmpty()) {
            val curr = q.remove()
            val (currX, currY) = curr
            val currDirectionIdx = fromDirIdx[currY*width+currX]
            var neighDirIdx = currDirectionIdx
            val currElem = getElement(curr)
            if (currElem == GridElement.WALL || visited[currY*width+currX]) {
                continue
            }
            visited[currY*width+currX] = true
            repeat(direction.size) {
                val cost = when ((neighDirIdx - currDirectionIdx).absoluteValue) {
                    1 -> 1001
                    2 -> 2001
                    3 -> 1001
                    else -> 1
                }
                val neigh = Vec2Int(currX + direction[neighDirIdx].x, currY + direction[neighDirIdx].y)
                val elem = getElement(neigh)
//                println("  $neigh $cost $elem ${dist[neigh.y*width+neigh.x]}")
                if (elem != GridElement.WALL && !visited[neigh.y*width+neigh.x] && dist[neigh.y*width+neigh.x] > dist[currY*width+currX] + cost) {
                    dist[neigh.y*width+neigh.x] = dist[currY*width+currX] + cost
                    fromDirIdx[neigh.y*width+neigh.x] = neighDirIdx
                    q.add(neigh)
//                    println("    update ${dist[neigh.y*width+neigh.x]} ${neighDirIdx} ${currDirectionIdx}")
                }
                neighDirIdx = (neighDirIdx + 1) % direction.size
            }
        }
        return Pair(dist[end.y*width+end.x], dist)
    }

    companion object {
        fun fromStrings(strs: List<String>): Grid {
            val width = strs[0].length
            val height = strs.size
            val elems = MutableList<GridElement>(height*width) { GridElement.SPACE }
            for ((rowIdx, row) in strs.withIndex()) {
                for ((colIdx, char) in row.withIndex()) {
                    val elem = when (char) {
                        '#' -> GridElement.WALL
                        'S' -> GridElement.START
                        'E' -> GridElement.END
                        else -> GridElement.SPACE
                    }
                    elems[rowIdx*width+colIdx] = elem
                }
            }
            return Grid(elems, width, height)
        }
    }
}

class AoC16 {
    companion object {
        fun loadData(fname: String): Grid {
            return Grid.fromStrings(File(fname).readLines())
        }
    }
}