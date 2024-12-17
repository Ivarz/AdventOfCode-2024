package org.infovars

import java.math.BigDecimal
import java.math.MathContext
import kotlin.math.*

data class Vec2Long(val x: Long, val y: Long) {
    fun distVec(other: Vec2Long): Vec2Long {
        return Vec2Long(x - other.x, y - other.y)
    }
    operator fun plus(other: Vec2Long): Vec2Long {
        return Vec2Long(x + other.x, y + other.y)
    }
    operator fun minus(other: Vec2Long): Vec2Long {
        return Vec2Long(x - other.x, y - other.y)
    }
    operator fun times(scalar: Long): Vec2Long {
        return Vec2Long(x * scalar, y * scalar)
    }
}
data class Vec2Int(var x: Int, var y: Int) {
    fun distVec(other: Vec2Int): Vec2Int {
        return Vec2Int(x - other.x, y - other.y)
    }
    operator fun plus(other: Vec2Int): Vec2Int {
        return Vec2Int(x + other.x, y + other.y)
    }
    operator fun minus(other: Vec2Int): Vec2Int {
        return Vec2Int(x - other.x, y - other.y)
    }
    operator fun times(scalar: Int): Vec2Int {
        return Vec2Int(x * scalar, y * scalar)
    }
}
data class Vec2(val x: Double, val y: Double) {
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
    operator fun times(scalar: Long): Vec2 {
        return Vec2(x * scalar, y * scalar)
    }
}

fun <T: Number> stirling(n: T): BigDecimal {
    val num = BigDecimal(n.toString())
    val mc = MathContext(5)
    val sqrtPart = BigDecimal((2*PI).toString()).times(num).sqrt(mc)
    val rest = num.divide( BigDecimal(E), mc ).pow(num.toInt())
    return sqrtPart.times(rest)
}