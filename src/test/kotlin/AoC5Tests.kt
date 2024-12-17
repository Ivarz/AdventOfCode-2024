import org.infovars.AoC5
import kotlin.test.Test
import kotlin.test.assertEquals

class AoC5Tests {
    @Test
    fun testLoadData() {
        val (pageRules, pages) = AoC5.loadData("src/test/resources/aoc_5_test_input1.txt")
        assert(pages.size == 6)
        assert(pageRules.size == 21)
    }

    @Test
    fun testIsValidManual() {
        val (pageRules, pages) = AoC5.loadData("src/test/resources/aoc_5_test_input1.txt")
        val aggPageRules = AoC5.aggregatePageRules(pageRules)
        assert(AoC5.isValidManual(aggPageRules, pages[0]))
        assert(AoC5.isValidManual(aggPageRules, pages[1]))
        assert(AoC5.isValidManual(aggPageRules, pages[2]))
        assert(!AoC5.isValidManual(aggPageRules, pages[3]))
        assert(!AoC5.isValidManual(aggPageRules, pages[4]))
        assert(!AoC5.isValidManual(aggPageRules, pages[5]))
    }

    @Test
    fun testValidManuals() {
        val (pageRules, pages) = AoC5.loadData("src/test/resources/aoc_5_test_input1.txt")
        assert(AoC5.filterValidManuals(pageRules, pages).size == 3)
    }

    @Test
    fun testSolutionPart1() {
        assert(AoC5.solutionPart1Runner("src/test/resources/aoc_5_test_input1.txt") == 143)
    }

    @Test
    fun testManualSorting() {
        val (pageRules, _) = AoC5.loadData("src/test/resources/aoc_5_test_input1.txt")
        assertEquals("97,75,47,61,53", AoC5.sortManual(pageRules, "75,97,47,61,53"))
        assertEquals("61,29,13", AoC5.sortManual(pageRules, "61,13,29"))
        assertEquals("97,75,47,29,13", AoC5.sortManual(pageRules, "97,13,75,29,47"))

    }

    @Test
    fun testSolutionPart2() {
        assertEquals(123, AoC5.solutionPart2Runner("src/test/resources/aoc_5_test_input1.txt"))
    }
}