import org.infovars.AoC9
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AoC9Tests {

    @Test
    fun testDiskBuilding() {
        val diskMap = AoC9.loadData("src/test/resources/aoc_9_test_input1.txt")
        val disk = AoC9.buildDisk(diskMap)
        assertEquals(42, disk.size)
    }

    @Test
    fun testSolutionPart1() {
        assertEquals(1928, AoC9.solutionPart1Runner("src/test/resources/aoc_9_test_input1.txt"))
    }

    @Test
    fun testIntervalBuilding() {
        val diskMap = AoC9.loadData("src/test/resources/aoc_9_test_input1.txt")
        val disk = AoC9.buildDisk(diskMap)
        val intervals = AoC9.buildIntervals(disk)
        assertEquals(18, intervals.size)
    }

    @Test
    fun diskCompression2() {
        val diskMap = AoC9.loadData("src/test/resources/aoc_9_test_input1.txt")
        val disk = AoC9.buildDisk(diskMap)
        AoC9.compressDisk2(disk)
        assertEquals(
            expected = mutableListOf<Long>(0, 0, 9, 9, 2, 1, 1, 1, 7, 7, 7, -1, 4, 4, -1, 3, 3, 3, -1, -1, -1, -1, 5, 5, 5, 5, -1, 6, 6, 6, 6, -1, -1, -1, -1, -1, 8, 8, 8, 8, -1, -1),
            actual = disk
        )
    }

    @Test
    fun solutionPart2() {
        assertEquals(2858, AoC9.solutionPart2Runner("src/test/resources/aoc_9_test_input1.txt"))
    }
}