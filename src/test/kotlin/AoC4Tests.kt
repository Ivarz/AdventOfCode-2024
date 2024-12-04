import org.infovars.AoC4
import kotlin.test.Test
import kotlin.test.assertEquals

class AoC4Tests {
    @Test
    fun testCountingInStraightLine() {
        val testCount = AoC4.countXmasFromPosStraightLine(
            AoC4.loadData("src/test/resources/aoc_4_test_input.txt")
            , Pair(0, 4))
        assertEquals(1, testCount)
    }

    @Test
    fun testSolutionPart1() {
        assertEquals(18, AoC4.solutionPart1Runner("src/test/resources/aoc_4_test_input.txt"))
    }

    @Test
    fun testSolutionPart2() {
        assertEquals(9, AoC4.solutionPart2Runner("src/test/resources/aoc_4_test_input2.txt"))
    }
}