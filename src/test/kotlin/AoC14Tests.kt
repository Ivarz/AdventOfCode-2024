import aoc14.AoC14
import aoc14.AoC14.Companion.getQuadrantResult
import aoc14.Grid
import aoc14.Robot
import kotlin.test.Test
import kotlin.test.assertEquals

class AoC14Tests {

    @Test
    fun testRobotParse() {
        val inputStr = "p=0,4 v=3,-3"
        val robot = Robot.fromString(inputStr)
        assertEquals(0, robot.position.x)
        assertEquals(4, robot.position.y)
        assertEquals(3, robot.velocity.x)
        assertEquals(-3, robot.velocity.y)
    }

    @Test
    fun testRobotMove() {
        val robots = AoC14.loadData("src/test/resources/aoc_14_test_input.txt")
        val width = 11L
        val height = 7L
        val result = robots.map { it.moveInGrid(gridWidth = width, gridHeight = height) }
            .also { it.forEach{r -> println(r)} }
            .filter { it.position.x != (width / 2) && it.position.y != (height / 2) }
            .getQuadrantResult(width, height)
        assertEquals(12, result)
    }

    @Test
    fun testGetResult() {
        val robots = AoC14.loadData("src/test/resources/aoc_14_test_input.txt")
        val width = 11L
        val height = 7L
        val result = AoC14.getResult(robots, width, height)
        assertEquals(12, result)
    }

    @Test
    fun testGrid() {
        val robots = AoC14.loadData("src/main/resources/aoc_14_input.txt")
        val threshold = 100
        for (step in 0 .. 1000000) {
            val grid = Grid.fromRobots(robots.map { it.moveInGrid(steps = step.toLong()) })
            if (grid.robotsWithAllNeighbours() > threshold) {
                println("steps: $step")
                grid.print()
                println()
                break
            }
        }
    }
}