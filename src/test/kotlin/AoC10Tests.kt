import org.infovars.AoC10
import org.infovars.AoC10.Companion.dfsTraverse
import org.infovars.AoC10.Companion.isNeighbour
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class AoC10Tests {
    @Test
    fun testLoadTopomap() {
        val topomap = AoC10.loadTopoMap("src/test/resources/aoc_10_test_input1.txt")
        assertEquals(8, topomap.size)
        assertEquals(8, topomap[0].size)
    }
    @Test
    fun testIsNeighbour() {
        assert(isNeighbour(0, 1))
        assert(isNeighbour(1, 2))
        assertFalse(isNeighbour(0, 2))
    }

    @Test
    fun testDfsTraversal() {
        val topomap = AoC10.loadTopoMap("src/test/resources/aoc_10_test_input1.txt")
        val visitedPeaks = MutableList(topomap.size) {
            MutableList(topomap[0].size) { 0 }
        }
        dfsTraverse(topomap, Pair(0,2), visitedPeaks)
        assertEquals(5, (AoC10.countFoundPeaks(visitedPeaks)))
    }

    @Test
    fun testSolutionPart1() {
        assertEquals(36, AoC10.solutionPart1Runner("src/test/resources/aoc_10_test_input1.txt"))
    }

    @Test
    fun testSolutionPart2() {
        assertEquals(81, AoC10.solutionPart2Runner("src/test/resources/aoc_10_test_input1.txt"))
    }
}