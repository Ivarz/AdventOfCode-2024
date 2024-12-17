import org.infovars.AoC7
import org.infovars.Op
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.math.pow

class AoC7Tests {
    @Test
    fun testNumberSetIndices0() {
        val idxs = AoC7.indicesToChange(0)
        assertEquals(listOf(), idxs)
    }
    @Test
    fun testNumberSetIndices1() {
        val idxs = AoC7.indicesToChange(7)
        assertEquals(listOf(2, 1, 0), idxs)
    }
    @Test
    fun testNumberSetIndices2() {
        val idxs = AoC7.indicesToChange(5)
        assertEquals(listOf(2, 0), idxs)
    }

    @Test
    fun testPatterns() {
        val opCount = 5
        val baseOp: Op = Long::plus
        val opToAdd: Op = Long::times
        val basePattern = List(opCount) { baseOp }
        val patterns = AoC7.generateOperatorCombos(opCount, basePattern, opToAdd)
        assertEquals(2.0.pow(5).toInt(), patterns.size)
    }

    @Test
    fun opApplication() {
        val nums = listOf<Long>(11, 6, 16, 20)
        val intPlus: Op = Long::plus
        val intTimes: Op = Long::times
        val ops = listOf(intPlus, intTimes, intPlus)
        assertEquals(292, AoC7.applyOps(nums, ops))
    }

    @Test
    fun TestSolutionPart1Runner() {
        assertEquals(3749, AoC7.solutionPart1Runner("src/test/resources/aoc_7_test_input1.txt"))
    }
    @Test
    fun TestSolutionPart2Runner() {
        assertEquals(11387, AoC7.solutionPart2Runner("src/test/resources/aoc_7_test_input1.txt"))
    }
}