import aoc17.AoC17
import aoc17.ChronospatialComputer
import aoc17.Memory
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AoC17Tests {
    @Test
    fun testComputerSetB1() {
        val mem = Memory(0, 0, 9)
        val program = listOf(2,6)
        val comp = ChronospatialComputer(program, mem)
        comp.run()
        assertEquals(1, comp.memory.regB)
    }

    @Test
    fun testComputerOutput() {
        val mem = Memory(10, 0, 0)
        val program = listOf(5,0,5,1,5,4)
        val comp = ChronospatialComputer(program, mem)
        assertEquals("0,1,2", comp.run())
    }

    @Test
    fun testComputerExample() {
        val mem = Memory(729, 0, 0)
        val program = listOf(0,1,5,4,3,0)
        val comp = ChronospatialComputer(program, mem)
        assertEquals("4,6,3,5,6,3,5,2,1,0", comp.run())
    }
    @Test
    fun puzzleInput() {
        val mem = Memory(64012472, 0, 0)
        val program = listOf(2,4,1,7,7,5,0,3,1,7,4,1,5,5,3,0)
        val comp = ChronospatialComputer(program, mem)
        val result = comp.run()
    }
    @Test
    fun puzzlePart2() {
        fun findBestEnd(program: List<Int>, end: Long, currPow: Int = 0) {
            if (currPow < 0) {
                return
            }
            var currEnd = end
            for (coef in 0 until 8) {
                val nextEnd = currEnd - coef*Math.pow(8.0, currPow.toDouble()).toLong()
                val mem = Memory(nextEnd, 0, 0)
                val comp = ChronospatialComputer(program, mem)
                comp.run()
                if (comp.output[currPow].toInt() == program[currPow]) {
                    println("$currPow $coef $nextEnd ${comp.output}")
                    val nextPow = currPow - 1
                    findBestEnd(program, nextEnd, nextPow)
                }
            }
        }
        val program = listOf(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)
        val targetOutput = program.joinToString(",")
        // interval starts at
//        var beg = 258385232517360 // beg of 5,5,3,0)
        var beg = Math.pow(8.0, 15.0).toLong()
        println("beg: $beg")
        val end = Math.pow(8.0, 16.0).toLong() - 1
        val newend = (end
                - 3L*Math.pow(8.0, 14.0).toLong()
                - 5L*Math.pow(8.0, 13.0).toLong()
                - 1L*Math.pow(8.0, 12.0).toLong()
                - 7L*Math.pow(8.0, 11.0).toLong()
                - 3L*Math.pow(8.0, 10.0).toLong()
                - 7L*Math.pow(8.0, 9.0).toLong() // 7
                )
        val newbeg = end - 4L*Math.pow(8.0, 14.0).toLong()
        var prevLast = 0L
        var counter: Long = 0
        findBestEnd(program, end, 14)
        val answer = 265652340990875L
        for (regAValue in answer+10 downTo (answer - 10) step 1) {
            val mem = Memory(regAValue.toLong(), 0, 0)
            val comp = ChronospatialComputer(program, mem)
            val result = comp.run()
            println("$regAValue ${comp.output.size} $result ${targetOutput == result}")
        }
    }
}