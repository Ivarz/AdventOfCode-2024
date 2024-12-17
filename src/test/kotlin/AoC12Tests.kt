import org.infovars.AoC12
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AoC12Tests {
    @Test
    fun testLoadData() {
        val data = AoC12.loadData("src/test/resources/aoc_12_test_input1.txt")
        assertEquals(4, data.size)
    }

    @Test
    fun TestFindAreaPerimeterFromSrc1() {
        val data = AoC12.loadData("src/test/resources/aoc_12_test_input1.txt")
        val accountedFields = MutableList(data.size) { MutableList(data.size) { false } }
        val src = Pair(0, 1)
        val (area, perimeter) = AoC12.findAreaPerimeterFromSrc(data, src, accountedFields)
        assertEquals(4, area)
        assertEquals(10, perimeter.size)

        val srcB = Pair(2, 1)
        val (areaB, perimeterB) = AoC12.findAreaPerimeterFromSrc(data, srcB, accountedFields)
        assertEquals(4, areaB)
        assertEquals(8, perimeterB.size)
    }
    @Test
    fun TestFindAreaPerimeterFromSrc2() {
        val data = AoC12.loadData("src/test/resources/aoc_12_test_input2.txt")
        val accountedFields = MutableList(data.size) { MutableList(data.size) { false } }
        val src = Pair(0, 1)
        val (area, perimeter) = AoC12.findAreaPerimeterFromSrc(data, src, accountedFields)
        assertEquals(21, area)
        assertEquals(36, perimeter.size)

        val srcX = Pair(1, 1)
        val (areaX, perimeterX) = AoC12.findAreaPerimeterFromSrc(data, srcX, accountedFields)
        assertEquals(1, areaX)
        assertEquals(4, perimeterX.size)
    }

    @Test
    fun TestFindAreaPerimeterFromSrc3() {
        val data = AoC12.loadData("src/test/resources/aoc_12_test_input3.txt")
        val accountedFields = MutableList(data.size) { MutableList(data.size) { false } }
        val src = Pair(0, 0)
        val (area, perimeter) = AoC12.findAreaPerimeterFromSrc(data, src, accountedFields)
        assertEquals(12, area)
        assertEquals(18, perimeter.size)
    }

    @Test
    fun testFindValue1() {
        val data = AoC12.loadData("src/test/resources/aoc_12_test_input1.txt")
        val value = AoC12.findValue(data)
        assertEquals(140, value)
    }

    @Test
    fun testFindValue2() {
        val data = AoC12.loadData("src/test/resources/aoc_12_test_input2.txt")
        val value = AoC12.findValue(data)
        assertEquals(772, value)
    }
    @Test
    fun testFindValue3() {
        val data = AoC12.loadData("src/test/resources/aoc_12_test_input3.txt")
        val value = AoC12.findValue(data)
        assertEquals(1930, value)
    }

    @Test
    fun testCountSides() {
        val data = AoC12.loadData("src/test/resources/aoc_12_test_input1.txt")
        val accountedFields = MutableList(data.size) { MutableList(data.size) { false } }
        val src = Pair(1, 2)
        val (_, perimeter) = AoC12.findAreaPerimeterFromSrc(data, src, accountedFields)
        assertEquals(8, AoC12.countFaces(perimeter))
    }

    @Test
    fun testCountSides4() {
        val data = AoC12.loadData("src/test/resources/aoc_12_test_input4.txt")
        val accountedFields = MutableList(data.size) { MutableList(data.size) { false } }
        val src = Pair(0, 0)
        val (area, perimeter) = AoC12.findAreaPerimeterFromSrc(data, src, accountedFields)
        assertEquals(17, area)
        assertEquals(12, AoC12.countFaces(perimeter))
    }
    @Test
    fun testNewValue4() {
        val data = AoC12.loadData("src/test/resources/aoc_12_test_input4.txt")
        assertEquals(236, AoC12.findNewValue(data))
    }
    @Test
    fun testNewValue5() {
        val data = AoC12.loadData("src/test/resources/aoc_12_test_input5.txt")
        assertEquals(368, AoC12.findNewValue(data))
    }

}