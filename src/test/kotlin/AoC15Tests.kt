import kotlin.test.Test
import aoc15.AoC15
import aoc15.Direction
import kotlin.test.assertEquals

class AoC15Tests {
    @Test
    fun testGpsCoordSum1() {
        val (grid, moves) = AoC15.loadData("src/test/resources/aoc_15_test_input1.txt")
        grid.moveRobotSequence(moves)
        assertEquals(2028, grid.gpsCoordSum())
    }
    @Test
    fun testGpsCoordSum3() {
        val (grid, moves) = AoC15.loadData("src/test/resources/aoc_15_test_input3.txt")
        grid.moveRobotSequence(moves)
        assertEquals(10092, grid.gpsCoordSum())
    }

    @Test
    fun testGpsCoordWSum3() {
        val (grid, moves) = AoC15.loadData2("src/test/resources/aoc_15_test_input3.txt")
        grid.moveRobotSequence(moves)
        assertEquals(9021, grid.gpsCoordSum())
    }
}
