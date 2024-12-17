package org.infovars
import org.ejml.simple.SimpleMatrix
import java.io.File
import kotlin.math.roundToLong

data class MachineConfiguration(val buttonA: Vec2,
                           val buttonB: Vec2,
                           val prizeLocation: Vec2,
                           val tokenA: Int = 3,
                           val tokenB: Int = 1 ) {
    fun findPossibleButtonCounts(): List<Pair<Long, Long>> {
        val result = mutableListOf<Pair<Long, Long>>()

        val maxButtonACount = listOf(prizeLocation.x / buttonA.x, prizeLocation.y / buttonA.y).min().toLong()
        val maxButtonBCount = listOf(prizeLocation.x / buttonB.x, prizeLocation.y / buttonB.y).min().toLong()

        for (buttonACount in 0..maxButtonACount) {
            for (buttonBCount in 0..maxButtonBCount) {
                val currLoc = buttonA*buttonACount + buttonB*buttonBCount
                if (currLoc == prizeLocation) {
                    result.add(Pair(buttonACount, buttonBCount))
                }
            }
        }
        return result
    }
    fun findButtonCount(): Vec2 {
        val a = SimpleMatrix(2,2)
        a.set(0, 0, buttonA.x)
        a.set(0, 1, buttonB.x)
        a.set(1, 0, buttonA.y)
        a.set(1, 1, buttonB.y)
        val c = SimpleMatrix(2,1)
        c.set(0, 0, prizeLocation.x + 10000000000000.toDouble())
        c.set(1, 0, prizeLocation.y + 10000000000000.toDouble())
//        c.set(0, 0, prizeLocation.x.toDouble() )
//        c.set(1, 0, prizeLocation.y.toDouble())
        if (a.invert() == null) {
            return Vec2(-1.0, -1.0)
        }
        val tokenCounts = a.invert().mult(c)
        val result = Vec2(tokenCounts.get(0, 0).roundToLong().toDouble(), tokenCounts.get(1, 0).roundToLong().toDouble())
        if (result.x * buttonA.x + result.y * buttonB.x != prizeLocation.x + 10000000000000) {
            return Vec2(-1.0, -1.0)
        }
        if (result.x * buttonA.y + result.y * buttonB.y != prizeLocation.y + 10000000000000) {
            return Vec2(-1.0, -1.0)
        }
        return result
    }
    fun findMinimumTokenCount(possibleCombos: List<Pair<Long, Long>>): Long {
        if (possibleCombos.isEmpty()) {
            return -1
        }
        return possibleCombos.minOf { it.first*tokenA + it.second*tokenB }
    }
    companion object {
        fun loadConfigurations(fname: String): List<MachineConfiguration> {
            val lines = File(fname).readLines().filter { it.length > 0 }
            val configs = mutableListOf<MachineConfiguration>()
            for (chunk in lines.chunked(3)) {
                configs.add(parseConfiguration(chunk))
            }
            return configs
        }
        private fun extractButtonCoord(str: String): Vec2 {
            val patternX = """X\+([0-9]+)""".toRegex()
            val patternY = """Y\+([0-9]+)""".toRegex()
            val resX = patternX.find(str)
            val resY = patternY.find(str)
            val x = resX?.groupValues?.drop(1)?.first().toString().toDouble()
            val y = resY?.groupValues?.drop(1)?.first().toString().toDouble()
            return Vec2(x, y)
        }
        private fun extractPrizeCoord(str: String): Vec2 {
            val patternX = """X=([0-9]+)""".toRegex()
            val patternY = """Y=([0-9]+)""".toRegex()
            val resX = patternX.find(str)
            val resY = patternY.find(str)
            val x = resX?.groupValues?.drop(1)?.first().toString().toDouble()
            val y = resY?.groupValues?.drop(1)?.first().toString().toDouble()
            return Vec2(x, y)
        }
        private fun parseConfiguration(chunk: List<String>): MachineConfiguration {
            val buttonAStr = chunk[0]
            val buttonBStr = chunk[1]
            val PrizeStr = chunk[2]
            val buttonA = extractButtonCoord(buttonAStr)
            val buttonB = extractButtonCoord(buttonBStr)
            val prizeLoc = extractPrizeCoord(PrizeStr)
            val newPrizeLoc = Vec2(prizeLoc.x, prizeLoc.y)
            return MachineConfiguration(buttonA, buttonB, newPrizeLoc)
        }
    }
}
class AoC13 {
    companion object {
        fun solutionPart1(): Long {
            val machines = MachineConfiguration.loadConfigurations("src/main/resources/aoc_13_input.txt")
            val res = machines.map { it.findMinimumTokenCount(it.findPossibleButtonCounts()) }
                .filter{ it > 0 }
                .sum()
            return res
        }

        fun solutionPart2(): Long {
            val machines = MachineConfiguration.loadConfigurations("src/main/resources/aoc_13_input.txt")
            return machines.map{ it.findButtonCount()}
                .filter { it.x > -1 }
                .sumOf { it.x * 3 + it.y}.toLong()
        }

    }
}