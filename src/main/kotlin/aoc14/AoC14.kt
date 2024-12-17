package aoc14

import org.infovars.Vec2Long
import java.io.File

enum class Quadrant {
    I, II, III, IV
}

data class Grid(val values: List<Int>, val width: Int, val height: Int) {
    fun print() {
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (values[(y * width + x)] == 0) print(".")
                else print("X")
//                print(values[(y * width + x)])
            }
            println()
        }
        println()
    }
    fun robotsWithAllNeighbours(): Int {
        var roboCount = 0
        for (y in 1 until height-1) {
            for (x in 1 until width-1) {
                val currCell = values[(y * width + x)]
                val up = values[((y-1) * width + x)]
                val down = values[((y+1) * width + x)]
                val left = values[(y * width + (x-1))]
                val right = values[(y * width + (x+1))]
                if (currCell > 0 && up > 0 && down > 0 && left > 0 && right > 0) {
                    roboCount++
                }
            }
        }
        return roboCount
    }
    companion object {
        fun fromRobots(robots: List<Robot>, width: Int = 101, height: Int = 103): Grid {
            val gridValues = MutableList(width*height) { 0 }
            for (r in robots) {
                gridValues[(r.position.y * width + r.position.x).toInt()]++
            }
            return Grid(gridValues, width, height)
        }
    }
}

data class Robot(var position: Vec2Long, var velocity: Vec2Long) {
    fun moveInGrid(steps: Long = 100, gridWidth: Long = 101, gridHeight: Long = 103 ): Robot {
        var newPosX = (position.x + ((steps % gridWidth)*(velocity.x % gridWidth) % gridWidth)) % gridWidth
        if (newPosX < 0) {
            newPosX += gridWidth
        }
        var newPosY = (position.y + ((steps % gridHeight)*(velocity.y % gridHeight) % gridHeight)) % gridHeight
        if (newPosY < 0) {
            newPosY += gridHeight
        }
        return Robot(Vec2Long(newPosX, newPosY), velocity)
    }
    fun quadrant(gridWidth: Long = 101, gridHeight: Long = 103): Quadrant {
        return when {
            position.x < gridWidth / 2 -> when {
                position.y < gridHeight / 2 -> Quadrant.I
                else -> Quadrant.II
            }
            else -> when {
                position.y < gridHeight / 2 -> Quadrant.III
                else -> Quadrant.IV
            }
        }
    }

    companion object {
        fun fromString(str: String): Robot {
            val pattern = """p=([-0-9]+),([-0-9]+) v=([-0-9]+),([-0-9]+)""".toRegex()
            pattern.find(str)?.destructured?.let { (x, y, vx, vy) ->
                return Robot(Vec2Long(x.toLong(), y.toLong()), Vec2Long(vx.toLong(), vy.toLong()))
            }
            return Robot(Vec2Long(0, 0), Vec2Long(0, 0))
        }
    }
}
class AoC14 {
    companion object {
        fun loadData(fname: String): List<Robot> {
            val robots = mutableListOf<Robot>()
            File(fname).forEachLine { line ->
                robots.add(Robot.fromString(line))
            }
            return robots
        }
        fun List<Robot>.getQuadrantResult(gridWidth: Long = 101, gridHeight: Long = 103): Long {
            return this.map { it.quadrant(gridWidth = gridWidth, gridHeight = gridHeight) }
                .groupingBy { it }
                .eachCount()
                .map { it.value }
                .reduce { acc, i -> acc * i }
                .toLong()
        }

        fun getResult(robots: List<Robot>, gridWidth: Long = 101, gridHeight: Long = 103): Long {
            val result = robots
                .map { it.moveInGrid(gridWidth = gridWidth, gridHeight = gridHeight) }
                .filter { it.position.x != (gridWidth / 2) && it.position.y != (gridHeight / 2) }
                .getQuadrantResult(gridWidth, gridHeight)
            return result
        }
        fun solutionPart1(): Long {
            val robots = loadData("src/main/resources/aoc_14_input.txt")
            return getResult(robots)
        }

        fun solutionPart2(): Int {
            val robots = loadData("src/main/resources/aoc_14_input.txt")
            val threshold = 100
            for (step in 0 .. 1000000) {
                val grid = Grid.fromRobots(robots.map { it.moveInGrid(steps = step.toLong()) })
                if (grid.robotsWithAllNeighbours() > threshold) {
                    return step
                }
            }
            return -1
        }
    }
}