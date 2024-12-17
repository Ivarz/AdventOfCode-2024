package org.infovars

import java.io.File

class AoC3 {
    companion object {
        fun loadData(): String {
            return File("src/main/resources/aoc_3_input.txt")
                .readLines()
                .joinToString(separator = "")
        }
        fun mulResult(data: String): Int {
            val regex = """mul\([0-9]+,[0-9]+\)""".toRegex()
            var result = 0
            for (match in regex.findAll(data)) {
                val numStr = match.value
                    .replace("mul(", "")
                    .replace(")", "")
                val (fst, snd) = numStr
                    .split(",")
                    .map { it.toInt() }
                result += fst * snd
            }
            return result
        }
        fun solutionPart1(): Int {
            val data = loadData()
            return mulResult(data)
        }

        fun solutionPart2(): Int {
            val data = loadData()
            val dataSplitByDo = data.split("do()")
            var result = 0
            for (chunk in dataSplitByDo) {
                val mulOiString = chunk.split("don't()")[0]
                result += mulResult(mulOiString)
            }
            return result
        }
    }
}