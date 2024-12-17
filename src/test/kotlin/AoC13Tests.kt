import org.infovars.MachineConfiguration
import kotlin.test.Test

class AoC13Tests {
    @Test
    fun testRegex() {
        val str = "Button A: X+94, Y+34"
        val pattern = """X\+([0-9]+)""".toRegex()

        val res = pattern.find(str)
        println(res?.groupValues?.drop(1)?.first())
    }

//    @Test
//    fun testConfigLoading() {
//        val machines = MachineConfiguration.loadConfigurations("src/test/resources/aoc_13_test_input1.txt")
//        val res = machines.map { it.findMinimumTokenCount(it.findPossibleButtonCounts()) }
//            .filter{ it > 0 }
//            .sum()
//        assertEquals(480, res)
//    }
    @Test
    fun answer1() {
        val machines = MachineConfiguration.loadConfigurations("src/main/resources/aoc_13_input.txt")
        val res = machines.map { it.findMinimumTokenCount(it.findPossibleButtonCounts()) }
            .filter{ it > 0 }
            .sum()
    }
    @Test
    fun answer2() {
        val machines = MachineConfiguration.loadConfigurations("src/main/resources/aoc_13_input.txt")
//        val machines = MachineConfiguration.loadConfigurations("src/test/resources/aoc_13_test_input1.txt")
        println("answer2")
        println(machines.map{ it.findButtonCount()}
            .filter { it.x > -1 }
            .sumOf { it.x * 3 + it.y}
            .toLong()
        )

    }
}