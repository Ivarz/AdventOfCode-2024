package aoc15

import aoc14.Robot
import com.sun.management.GcInfo
import org.infovars.Vec2Int
import org.infovars.Vec2Long
import java.io.File
import java.util.Collections

enum class GridElement {
    ROBOT, BOX, WALL, SPACE;
    override fun toString(): String {
        return when (this) {
            ROBOT -> "@"
            BOX -> "O"
            WALL -> "#"
            SPACE -> "."
        }
    }
    fun toChar(): Char {
        return when (this) {
            ROBOT -> '@'
            BOX -> 'O'
            WALL -> '#'
            SPACE -> '.'
        }
    }
}

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

enum class GridWElement {
    ROBOT, BOXL, BOXR, WALL, SPACE;

    override fun toString(): String {
        return when (this) {
            ROBOT -> "@"
            BOXL -> "["
            BOXR -> "]"
            WALL -> "#"
            SPACE -> "."
        }
    }
}

data class GridW(val elements: MutableList<GridWElement>, val width: Int, val height: Int) {
    var robotLocation: Vec2Int = Vec2Int(0, 0)

    fun getElement(pos: Vec2Int): GridWElement {
        return elements[pos.y*width+pos.x]
    }
    fun setElement(pos: Vec2Int, elem: GridWElement) {
        elements[pos.y*width+pos.x] = elem
    }
    fun getElement(x: Int, y: Int): GridWElement {
        return elements[y*width+x]
    }
    init {
        findRobot()
    }
    fun findRobot() {
        for (row in 0 until height) {
            for (col in 0 until width) {
                if (elements[row*width+col] == GridWElement.ROBOT) {
                    robotLocation = Vec2Int(col, row)
                }
            }
        }
    }

    fun canMove(src: Vec2Int, dir: Direction): Boolean {
        val elemValue = getElement(src)
        if (elemValue == GridWElement.WALL) {
            return false
        }
        if (elemValue == GridWElement.SPACE) {
            return true
        }
        if (elemValue == GridWElement.BOXL) {
            return when (dir) {
                Direction.UP -> canMove(Vec2Int(src.x, src.y-1), dir) && canMove(Vec2Int(src.x+1, src.y-1), dir)
                Direction.DOWN -> canMove(Vec2Int(src.x, src.y+1), dir) && canMove(Vec2Int(src.x+1, src.y+1), dir)
                Direction.LEFT -> canMove(Vec2Int(src.x-1, src.y), dir)
                Direction.RIGHT -> canMove(Vec2Int(src.x+2, src.y), dir)
            }
        }
        if (elemValue == GridWElement.BOXR) {
            return when (dir) {
                Direction.UP -> canMove(Vec2Int(src.x, src.y-1), dir) && canMove(Vec2Int(src.x-1, src.y-1), dir)
                Direction.DOWN -> canMove(Vec2Int(src.x, src.y+1), dir) && canMove(Vec2Int(src.x-1, src.y+1), dir)
                Direction.LEFT -> canMove(Vec2Int(src.x-2, src.y), dir)
                Direction.RIGHT -> canMove(Vec2Int(src.x+1, src.y), dir)
            }
        }
        if (elemValue == GridWElement.ROBOT) {
            return when (dir) {
                Direction.UP -> canMove(Vec2Int(src.x, src.y-1), dir)
                Direction.DOWN -> canMove(Vec2Int(src.x, src.y+1), dir)
                Direction.LEFT -> canMove(Vec2Int(src.x-1, src.y), dir)
                Direction.RIGHT -> canMove(Vec2Int(src.x+1, src.y), dir)
            }
        }
        return true
    }

    fun moveElement(src: Vec2Int, dir: Direction) {
        val elemValue = elements[src.y*width+src.x]
        if (elemValue == GridWElement.SPACE || elemValue == GridWElement.WALL) {
            return
        }
        if (elemValue == GridWElement.ROBOT) {
            val neigh = when (dir) {
                Direction.UP -> Vec2Int(src.x, src.y-1)
                Direction.DOWN -> Vec2Int(src.x, src.y+1)
                Direction.LEFT -> Vec2Int(src.x-1, src.y)
                Direction.RIGHT -> Vec2Int(src.x+1, src.y)
            }
            moveElement(neigh, dir)
            Collections.swap(elements, src.y*width+src.x, neigh.y*width+neigh.x)
        }
        if (elemValue == GridWElement.BOXL) {
            when (dir) {
                Direction.UP -> {
                    val neigh1 = Vec2Int(src.x, src.y-1)
                    val neigh2 = Vec2Int(src.x+1, src.y-1)
                    moveElement(neigh1, dir)
                    moveElement(neigh2, dir)
                    Collections.swap(elements, src.y*width+src.x, neigh1.y*width+neigh1.x)
                    Collections.swap(elements, src.y*width+src.x+1, neigh2.y*width+neigh2.x)
                }
                Direction.DOWN -> {
                    val neigh1 = Vec2Int(src.x, src.y+1)
                    val neigh2 = Vec2Int(src.x+1, src.y+1)
                    moveElement(neigh1, dir)
                    moveElement(neigh2, dir)
                    Collections.swap(elements, src.y*width+src.x, neigh1.y*width+neigh1.x)
                    Collections.swap(elements, src.y*width+src.x+1, neigh2.y*width+neigh2.x)
                }
                Direction.LEFT -> {
                    val neigh = Vec2Int(src.x-1, src.y)
                    moveElement(neigh, dir)
                    Collections.swap(elements, src.y*width+src.x, neigh.y*width+neigh.x)
                }
                Direction.RIGHT -> {
                    val neigh = Vec2Int(src.x+1, src.y)
                    moveElement(neigh, dir)
                    Collections.swap(elements, src.y*width+src.x, neigh.y*width+neigh.x)
                }
            }
        }
        if (elemValue == GridWElement.BOXR) {
            when (dir) {
                Direction.UP -> {
                    val neigh1 = Vec2Int(src.x, src.y-1)
                    val neigh2 = Vec2Int(src.x-1, src.y-1)
                    moveElement(neigh1, dir)
                    moveElement(neigh2, dir)
                    Collections.swap(elements, src.y*width+src.x, neigh1.y*width+neigh1.x)
                    Collections.swap(elements, src.y*width+src.x-1, neigh2.y*width+neigh2.x)
                }
                Direction.DOWN -> {
                    val neigh1 = Vec2Int(src.x, src.y+1)
                    val neigh2 = Vec2Int(src.x-1, src.y+1)
                    moveElement(neigh1, dir)
                    moveElement(neigh2, dir)
                    Collections.swap(elements, src.y*width+src.x, neigh1.y*width+neigh1.x)
                    Collections.swap(elements, src.y*width+src.x-1, neigh2.y*width+neigh2.x)
                }
                Direction.LEFT -> {
                    val neigh = Vec2Int(src.x-1, src.y)
                    moveElement(neigh, dir)
                    Collections.swap(elements, src.y*width+src.x, neigh.y*width+neigh.x)
                }
                Direction.RIGHT -> {
                    val neigh = Vec2Int(src.x+1, src.y)
                    moveElement(neigh, dir)
                    Collections.swap(elements, src.y*width+src.x, neigh.y*width+neigh.x)
                }
            }
        }
    }
    fun moveRobot(dir: Direction) {
        val src = robotLocation
        if (canMove(src, dir)) {
            moveElement(src, dir)
            when (dir) {
                Direction.UP -> robotLocation = Vec2Int(src.x, src.y-1)
                Direction.DOWN -> robotLocation = Vec2Int(src.x, src.y+1)
                Direction.LEFT -> robotLocation = Vec2Int(src.x-1, src.y)
                Direction.RIGHT -> robotLocation = Vec2Int(src.x+1, src.y)
            }
        }
    }
    fun moveRobotSequence(seq: String) {
        goBack()
        for (c in seq) {
            when (c) {
                '<' -> moveRobot(Direction.LEFT)
                '>' -> moveRobot(Direction.RIGHT)
                '^' -> moveRobot(Direction.UP)
                'v' -> moveRobot(Direction.DOWN)
            }
            print()
            goBack()
        }
        print()
    }
    fun goBack() {
        print("\u001b[H")
    }
    fun clearScreen() {
        println("\u001b[2J")
    }
    fun print() {
        for (y in 0 until height) {
            for (x in 0 until width) {
                print(elements[y*width + x])
            }
            println()
        }
    }
    fun gpsCoordSum(): Long {
        var sum = 0L
        for (row in 0 until height) {
            for (col in 0 until width) {
                if (elements[row*width+col] == GridWElement.BOXL) {
                    sum += row*100L + col
                }
            }
        }
        return sum
    }
    companion object {
        fun fromStrings(strs: List<String>): GridW {
            val width = 2*strs[0].length
            val height = strs.size
            val elems = MutableList<GridWElement>(height*width) { GridWElement.SPACE }
            for ((rowIdx, row) in strs.withIndex()) {
                for ((colIdx, char) in row.withIndex()) {
                    var elem1 = GridWElement.SPACE
                    var elem2 = GridWElement.SPACE
                    when (char) {
                        '#' -> { elem1 = GridWElement.WALL; elem2 = GridWElement.WALL }
                        '@' ->{ elem1 = GridWElement.ROBOT; elem2 = GridWElement.SPACE }
                        'O' -> { elem1 = GridWElement.BOXL; elem2 = GridWElement.BOXR }
                    }
                    elems[rowIdx*width+(2*colIdx)] = elem1
                    elems[rowIdx*width+(2*colIdx+1)] = elem2
                }
            }
            return GridW(elems, width, height)
        }
    }

}

data class Grid(val elements: MutableList<GridElement>, val width: Int, val height: Int) {
    var robotLocation: Vec2Int = Vec2Int(0, 0)

    init {
        findRobot()
    }
    fun findRobot() {
        for (row in 0 until height) {
            for (col in 0 until width) {
                if (elements[row*width+col] == GridElement.ROBOT) {
                    robotLocation = Vec2Int(col, row)
                }
            }
        }
    }

    fun moveRobotRight() {
        var groupEndX = robotLocation.x + 1
        while (elements[robotLocation.y*width + groupEndX] == GridElement.BOX) {
            groupEndX++
        }
        if (elements[robotLocation.y*width + groupEndX] != GridElement.SPACE) {
            return
        }
        while (groupEndX > robotLocation.x) {
            val srcIdx = robotLocation.y*width + (groupEndX - 1)
            val tgtIdx = robotLocation.y*width + groupEndX
            Collections.swap(elements, srcIdx, tgtIdx)
            groupEndX--
        }
        robotLocation.x++
    }
    fun moveRobotLeft() {
        var groupEndX = robotLocation.x - 1
        while (elements[robotLocation.y*width + groupEndX] == GridElement.BOX) {
            groupEndX--
        }
        if (elements[robotLocation.y*width + groupEndX] != GridElement.SPACE) {
            return
        }
        while (groupEndX < robotLocation.x) {
            val srcIdx = robotLocation.y*width + (groupEndX + 1)
            val tgtIdx = robotLocation.y*width + groupEndX
            Collections.swap(elements, srcIdx, tgtIdx)
            groupEndX++
        }
        robotLocation.x--
    }

    fun moveRobotDown() {
        var groupEndY = robotLocation.y + 1
        while (elements[groupEndY*width + robotLocation.x] == GridElement.BOX) {
            groupEndY++
        }
        if (elements[groupEndY*width + robotLocation.x] != GridElement.SPACE) {
            return
        }
        while (groupEndY > robotLocation.y) {
            val srcIdx = (groupEndY - 1)*width + robotLocation.x
            val tgtIdx = groupEndY*width + robotLocation.x
            Collections.swap(elements, srcIdx, tgtIdx)
            groupEndY--
        }
        robotLocation.y++
    }

    fun moveRobotUp() {
        var groupEndY = robotLocation.y - 1
        while (elements[groupEndY*width + robotLocation.x] == GridElement.BOX) {
            groupEndY--
        }
        if (elements[groupEndY*width + robotLocation.x] != GridElement.SPACE) {
            return
        }
        while (groupEndY < robotLocation.y) {
            val srcIdx = (groupEndY + 1)*width + robotLocation.x
            val tgtIdx = groupEndY*width + robotLocation.x
            Collections.swap(elements, srcIdx, tgtIdx)
            groupEndY++
        }
        robotLocation.y--
    }

    fun moveRobotSequence(seq: String) {
        for (c in seq) {
            when (c) {
                '<' -> moveRobotLeft()
                '>' -> moveRobotRight()
                '^' -> moveRobotUp()
                'v' -> moveRobotDown()
            }
        }
    }

    fun gpsCoordSum(): Long {
        var sum = 0L
        for (row in 0 until height) {
            for (col in 0 until width) {
                if (elements[row*width+col] == GridElement.BOX) {
                    sum += row*100L + col
                }
            }
        }
        return sum
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
    companion object {
        fun fromStrings(strs: List<String>): Grid {
            val width = strs[0].length
            val height = strs.size
            val elems = MutableList<GridElement>(height*width) { GridElement.SPACE }
            for ((rowIdx, row) in strs.withIndex()) {
                for ((colIdx, char) in row.withIndex()) {
                    val elem = when (char) {
                        '#' -> GridElement.WALL
                        '@' ->GridElement.ROBOT
                        'O' -> GridElement.BOX
                        else -> GridElement.SPACE
                    }
                    elems[rowIdx*width+colIdx] = elem
                }
            }
            return Grid(elems, width, height)
        }
    }
}

class AoC15 {
    companion object {
        fun loadData(fname: String): Pair<Grid, String> {
            val lines = File(fname).readLines()
            val gridLines = lines.takeWhile { it.isNotEmpty() }
            val robotStr = lines
                .dropWhile { it.isNotEmpty() }
                .dropWhile { it.isEmpty() }
            return Pair(Grid.fromStrings(gridLines), robotStr.joinToString(""))
        }
        fun loadData2(fname: String): Pair<GridW, String> {
            val lines = File(fname).readLines()
            val gridLines = lines.takeWhile { it.isNotEmpty() }
            val robotStr = lines
                .dropWhile { it.isNotEmpty() }
                .dropWhile { it.isEmpty() }
            return Pair(GridW.fromStrings(gridLines), robotStr.joinToString(""))
        }
        fun solutionPart1() : Long {
            val (grid, moves) = loadData("src/main/resources/aoc_15_input.txt")
            grid.moveRobotSequence(moves)
            return grid.gpsCoordSum()
        }
        fun solutionPart2() : Long {
            val (grid, moves) = loadData2("src/main/resources/aoc_15_input.txt")
            grid.moveRobotSequence(moves)
            return grid.gpsCoordSum()
        }
    }
}