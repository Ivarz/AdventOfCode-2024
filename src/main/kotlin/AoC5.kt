package org.infovars

import java.io.File

class AoC5 {
    companion object {
        fun loadData(fname: String): Pair<List<String>, List<String>> {
            val pageRules = mutableListOf<String>()
            val pages = mutableListOf<String>()
            for (line in File(fname).readLines()) {
                if (line.contains('|'))
                    pageRules.add(line)
                if (line.contains(','))
                    pages.add(line)
            }
            return Pair(pageRules, pages)
        }

        fun aggregatePageRules(pageRules: List<String>): Map<String, Set<String>> {
            val aggRules = mutableMapOf<String, MutableSet<String>>()
            for (pageRule in pageRules) {
                val (fst, snd) = pageRule.split("|")
                aggRules.computeIfAbsent(fst) { mutableSetOf() }.add(snd)
            }
            return aggRules
        }
        fun isValidManual(rules: Map<String,Set<String>>, manual: String): Boolean {
            val pageNums = manual.split(",")
            for (i in 0 until pageNums.lastIndex) {
                val pageNum = pageNums[i]
                val rightNums = rules.getOrDefault(pageNum, setOf())
                for (j in i + 1 until pageNums.size) {
                    val rightPageNum = pageNums[j]
                    if (rightPageNum !in rightNums) {
                        return false
                    }
                }
            }
            return true
        }
        fun sortManual(rules: List<String>, manual: String): String {
            val rulesAgg = aggregatePageRules(rules)
            val pageNums = manual.split(",")
            val rightNumCount = mutableMapOf<String, Int>()

            for (pageNum1 in pageNums) {
                val initCount = rightNumCount.getOrDefault(pageNum1, 0)
                rightNumCount[pageNum1] = initCount
                for (pageNum2 in pageNums) {
                    if (pageNum1 == pageNum2) {continue}
                    val rightNums = rulesAgg.getOrDefault(pageNum1, setOf())
                    if (pageNum2 in rightNums) {
                        val newCount = rightNumCount.getOrDefault(pageNum1, 0)+1
                        rightNumCount[pageNum1] = newCount
                    }
                }
            }
            val result = rightNumCount.toList().sortedBy {
                (_, value) -> value
            }.map {it.first }
                .reversed()
                .joinToString(",")
            return result
        }

        fun filterValidManuals(pageRules: List<String>, pages: List<String>): List<String> {
            val aggPageRules = aggregatePageRules(pageRules)
            return pages.filter { isValidManual(aggPageRules, it) }
        }

        fun filterInvalidManuals(pageRules: List<String>, pages: List<String>): List<String> {
            val aggPageRules = aggregatePageRules(pageRules)
            return pages.filter { !isValidManual(aggPageRules, it) }
        }

        fun solutionPart1Runner(fname: String): Int {
            val (pageRules, pages) = loadData(fname)
            return filterValidManuals(pageRules, pages).sumOf {
                val pageNums = it.split(",")
                pageNums[pageNums.size / 2].toInt()
            }
        }

        fun solutionPart1(): Int {
            return solutionPart1Runner("src/main/resources/aoc_5_input.txt")
        }

        fun solutionPart2Runner(fname: String): Int {
            val (pageRules, pages) = loadData(fname)
            return filterInvalidManuals(pageRules, pages)
                .map { sortManual(pageRules, it) }
                .sumOf {
                val pageNums = it.split(",")
                pageNums[pageNums.size / 2].toInt()
            }
        }

        fun solutionPart2(): Int {
            return solutionPart2Runner("src/main/resources/aoc_5_input.txt")
        }
    }
}