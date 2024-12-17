import org.infovars.AoC11
import kotlin.test.Test
import kotlin.test.assertEquals

class AoC11Tests {
    @Test
    fun testStrToInt() {
        assertEquals(123, "0123".toLong())
    }

    @Test
    fun testBlinking() {
        val stones = AoC11.loadData("src/test/resources/aoc_11_test_input1.txt")
        AoC11.blink(stones, 1)
        assertEquals(listOf<Long>(1, 2024, 1, 0, 9, 9, 2021976), stones)
    }
    @Test
    fun testBlinking6() {
        val stones = mutableListOf<Long>(125, 17)
        AoC11.blink(stones, 6)
        assertEquals(22, stones.size)
    }
    @Test
    fun testBlinking22() {
        val stones = mutableListOf<Long>(125, 17)
        AoC11.blink(stones, 25)
        assertEquals(55312, stones.size)
    }
}