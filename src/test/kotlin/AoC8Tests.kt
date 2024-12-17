import org.infovars.AoC8.Antenna
import org.infovars.AoC8.AoC8
import org.infovars.AoC8.Vec2
import kotlin.test.Test
import kotlin.test.assertEquals

class AoC8Tests {
    @Test
    fun testDataLoad() {
        val data = AoC8.loadData("src/test/resources/aoc_8_test_input1.txt")
        assertEquals('a', data.getAt(Vec2(4, 3)))
        assertEquals('a', data.getAt(Vec2(5,5)))
    }

    @Test
    fun testVecOpOverload() {
        val v1 = Vec2(1, 2)
        val v2 = Vec2(-3, 5)
        val v3 = v1 + v2
        val v4 = v1 - v2
        assertEquals(Vec2(-2, 7), v3)
        assertEquals(Vec2(4, -3), v4)
    }

    @Test
    fun testCoordInGrid() {
        val data = AoC8.loadData("src/test/resources/aoc_8_test_input1.txt")
        val coord = Vec2(0, 0)
        assert(coord.inBounds(data.width, data.height))
    }

    @Test
    fun testAntennaListing() {
        val grid = AoC8.loadData("src/test/resources/aoc_8_test_input1.txt")
        val antennas = grid.listAntennas()
        println(antennas.groupBy { it.frequency } )
        assertEquals(2, antennas.size)
    }
    @Test
    fun testAntinodeCalc() {
        val a1 = Antenna('x', Vec2(8,8))
        val a2 = Antenna('x', Vec2(9,9))
        assertEquals(Vec2(7,7), a1.antinode(a2))
        assertEquals(Vec2(10,10), a2.antinode(a1))
    }

    @Test
    fun testFindAntinodes() {
        val grid = AoC8.loadData("src/test/resources/aoc_8_test_input1.txt")
        val antinodes = grid.findAntinodes()
        assertEquals(2, antinodes.size)

    }

    @Test
    fun testFindAntinodes2() {
        val grid = AoC8.loadData("src/test/resources/aoc_8_test_input2.txt")
        val antinodes = grid.findAntinodes()
        assertEquals(14, antinodes.size)
    }

    @Test
    fun testSolutionPart1() {
        val res = AoC8.solutionPart1Runner("src/test/resources/aoc_8_test_input2.txt")
        assertEquals(14, res)
    }

    @Test
    fun testSolutionPart2() {
        val res = AoC8.solutionPart2Runner("src/test/resources/aoc_8_test_input2.txt")
        assertEquals(34, res)
    }

}