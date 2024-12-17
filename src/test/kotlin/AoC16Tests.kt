import kotlin.test.Test

import aoc16.AoC16
import aoc16.GridElement
import org.infovars.Vec2Int
import kotlin.test.assertEquals

class AoC16Tests {
    @Test
    fun testloaddata() {
        val grid = AoC16.loadData("src/test/resources/aoc_16_test_input1.txt")
        val (dist, _) = grid.dijkstra(Vec2Int(1, 13), Vec2Int(13, 1), 2)
        assertEquals(7036, dist)
    }
    @Test
    fun testDijkstra2() {
        val grid = AoC16.loadData("src/test/resources/aoc_16_test_input2.txt")
        val (dist, _) = grid.dijkstra(grid.startLocation, grid.endLocation, 2)
        assertEquals(11048, dist)
    }

    @Test
    fun testDijkstraReal() {
        val grid = AoC16.loadData("src/main/resources/aoc_16_input.txt")
        val (dist, _) = grid.dijkstra(grid.startLocation, grid.endLocation, 2)
        println("Dist: $dist")
//        assertEquals(11048, dist)
    }

    @Test
    fun testDijkstraAndDfs() {
//        val grid = AoC16.loadData("src/test/resources/aoc_16_test_input1.txt")
        val grid = AoC16.loadData("src/main/resources/aoc_16_input.txt")
        val start = grid.startLocation
        val end = grid.endLocation
        val (distToEnd, distsStartEnd) = grid.dijkstra(start, end, 2)
        val (distToStart, distsEndStart) = grid.dijkstra(end, start, 0)

        for (y in 0 until grid.height) {
            for (x in 0 until grid.width) {
                val dst = distsStartEnd[y*grid.width+x]
                if (dst == Int.MAX_VALUE-10000) {
                    print("####\t")
                } else {
                    print("${dst.toString().padStart(4,'0')}\t")
                }
            }
            println()
        }
        println()
        for (y in 0 until grid.height) {
            for (x in 0 until grid.width) {
                val dst = distsEndStart[y*grid.width+x]
                if (dst == Int.MAX_VALUE-10000) {
                    print("####\t")
                } else {
                    print("${dst.toString().padStart(4,'0')}\t")
                }
            }
            println()
        }
        println()
        println()

        var tiles = mutableSetOf<Vec2Int>()
        for (y in 0 until grid.height) {
            for (x in 0 until grid.width) {
                val dst = distsStartEnd[y*grid.width+x]
                val dst2 = distsEndStart[y*grid.width+x]
                if (dst == Int.MAX_VALUE-10000) {
                    print("####\t")
                } else {
                    print("${(dst+dst2).toString().padStart(4,'0')}\t")
                    if (dst+dst2 == distToEnd || dst+dst2+1000 == distToEnd) {
                        tiles.add(Vec2Int(x, y))
                    }
                }
            }
            println()
        }
        grid.drawPath(tiles.toList())
        println(tiles.size)
    }
}
