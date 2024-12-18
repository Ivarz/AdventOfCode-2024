import aoc18.AoC18
import kotlin.test.Test

class AoC18Tests {
    @Test
    fun testLoadGrid() {
        for (lines in 1 .. 24) {
            val grid = aoc18.Grid.fromFile("src/test/resources/aoc_18_test_input1.txt", lines, 7, 7)
            println("${lines} ${grid.shortestPath()}")
        }
    }
    @Test
    fun testLoadGridReal() {
        for (lines in 1024 .. 3450) {
            val grid = aoc18.Grid.fromFile("src/test/resources/aoc_18_input.txt", lines, 71, 71)
            println("${lines} ${grid.shortestPath()}")
        }
    }
}