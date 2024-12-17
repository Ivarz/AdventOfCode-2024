import org.infovars.AoC6
import org.infovars.Direction
import kotlin.test.Test
import kotlin.test.assertEquals

class AoC6Tests {
    @Test
    fun testLocateGuard() {
        val grid = AoC6.loadData("src/test/resources/aoc_6_test_input1.txt")
        val (i, j) = AoC6.locateGuard(grid)
        assertEquals(6, i)
        assertEquals(4, j)
    }

    @Test
    fun testMoveUp() {
        val grid = AoC6.loadData("src/test/resources/aoc_6_test_input1.txt")
        val loc = AoC6.locateGuard(grid)
        val newLoc = AoC6.walk(grid, loc, Direction.UP)
        assertEquals(Pair(1,4), newLoc)
    }
    @Test
    fun testMoveDown() {
        val grid = AoC6.loadData("src/test/resources/aoc_6_test_input1.txt")
        val loc = AoC6.locateGuard(grid)
        val newLoc = AoC6.walk(grid, loc, Direction.DOWN)
        assertEquals(Pair(9,4), newLoc)
    }
    @Test
    fun testMoveLeft() {
        val grid = AoC6.loadData("src/test/resources/aoc_6_test_input1.txt")
        val loc = AoC6.locateGuard(grid)
        val newLoc = AoC6.walk(grid, loc, Direction.LEFT)
        assertEquals(Pair(6,2), newLoc)
    }
    @Test
    fun testMoveRight() {
        val grid = AoC6.loadData("src/test/resources/aoc_6_test_input1.txt")
        val loc = AoC6.locateGuard(grid)
        val newLoc = AoC6.walk(grid, loc, Direction.RIGHT)
        assertEquals(Pair(6,9), newLoc)
    }

    @Test
    fun walkGrid() {
        val grid = AoC6.loadData("src/test/resources/aoc_6_test_input1.txt")
        val (loc, _) = AoC6.walkGuard(grid)
        assertEquals(Pair(9,7), loc)
    }

    @Test
    fun solutionPart1RunnerTest() {
        val res = AoC6.solutionPart1Runner("src/test/resources/aoc_6_test_input1.txt")
        assertEquals(41, res)
    }
    @Test
    fun solutionPart2RunnerTest() {
        val res = AoC6.solutionPart2Runner("src/test/resources/aoc_6_test_input1.txt")
        assertEquals(6, res)

    }
}