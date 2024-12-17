package org.infovars.AoC8

import java.io.File

data class Vec2(val x: Int, val y: Int) {
    fun inBounds(width: Int, height: Int) : Boolean {
        return x >= 0 && y >= 0 && x < width && y < height
    }
    fun distVec(other: Vec2): Vec2 {
        return Vec2(x - other.x, y - other.y)
    }
    operator fun plus(other: Vec2): Vec2 {
        return Vec2(x + other.x, y + other.y)
    }
    operator fun minus(other: Vec2): Vec2 {
        return Vec2(x - other.x, y - other.y)
    }
    operator fun times(scalar: Int): Vec2 {
        return Vec2(x * scalar, y * scalar)
    }
}

data class Antenna(val frequency: Char, val location: Vec2) {
    fun antinode(other: Antenna): Vec2 {
        val dist = this.location - other.location
        return this.location + dist
    }
    fun updatedAntinodeModel(other: Antenna, gridWidth: Int, gridHeight: Int ): List<Vec2> {
        val result = mutableListOf<Vec2>()
        var currPos = this.location
        val dist = this.location - other.location
        while (currPos.inBounds(gridWidth, gridHeight)) {
            result.add(currPos)
            currPos += dist
        }
        return result
    }

    companion object {
        fun findAntinodes(antennas: List<Antenna>): Set<Vec2> {
            val result = mutableSetOf<Vec2>()
            for (i in 0 until antennas.lastIndex) {
                for (j in i+1 until antennas.size) {
                    val a1 = antennas[i]
                    val a2 = antennas[j]
                    result.add(a1.antinode(a2))
                    result.add(a2.antinode(a1))
                }
            }
            return result
        }
        fun findAntinodesUpdated(antennas: List<Antenna>, maxWidth: Int, maxHeight: Int): Set<Vec2> {
            val result = mutableSetOf<Vec2>()
            for (i in 0 until antennas.lastIndex) {
                for (j in i+1 until antennas.size) {
                    val a1 = antennas[i]
                    val a2 = antennas[j]
                    result.addAll(a1.updatedAntinodeModel(a2, maxWidth, maxHeight))
                    result.addAll(a2.updatedAntinodeModel(a1, maxWidth, maxHeight))
                }
            }
            return result
        }
    }

}

class Grid(val cells: List<CharArray>) {
    val height: Int get() = cells.size
    val width: Int get() = cells[0].size

    fun getAt(coord: Vec2): Char {
        return cells[coord.y][coord.x]
    }
    fun listAntennas(): List<Antenna> {
        val result = mutableListOf<Antenna>()
        for (y in 0 until height) {
            for (x in 0 until width) {
                val char = getAt(Vec2(x, y))
                if (char != '.') {
                    result.add(Antenna(char, Vec2(x, y)))
                }
            }
        }
        return result
    }

    fun groupAntennas(): Map<Char, List<Antenna>> {
        return listAntennas().groupBy { it.frequency }
    }

    fun findAntinodes(): List<Vec2> {
        val allAntinodes = mutableSetOf<Vec2>()
        for ((_, antennas) in groupAntennas()) {
            allAntinodes.addAll(Antenna.findAntinodes(antennas))
        }
        val result = allAntinodes.filter { it.inBounds(width, height) }
        return result
    }
    fun findUpdatedAntinodes(): List<Vec2> {
        val allAntinodes = mutableSetOf<Vec2>()
        for ((_, antennas) in groupAntennas()) {
            allAntinodes.addAll(Antenna.findAntinodesUpdated(antennas, width, height))
        }
        val result = allAntinodes.filter { it.inBounds(width, height) }
        return result
    }

    companion object {
        fun load(fname: String): Grid {
            val cells = File(fname).readLines().map { line -> line.toCharArray() }
            return Grid(cells)
        }
    }
}

class AoC8 {
    companion object {
        fun loadData(fname: String): Grid {
            return Grid.load(fname)
        }
        fun solutionPart1Runner(fname: String): Int {
            val grid = loadData(fname)
            return grid.findAntinodes().size
        }
        fun solutionPart1(): Int {
            return solutionPart1Runner("src/main/resources/aoc_8_input.txt")
        }
        fun solutionPart2Runner(fname: String): Int {
            val grid = loadData(fname)
            return grid.findUpdatedAntinodes().size
        }
        fun solutionPart2(): Int {
            return solutionPart2Runner("src/main/resources/aoc_8_input.txt")
        }
    }
}