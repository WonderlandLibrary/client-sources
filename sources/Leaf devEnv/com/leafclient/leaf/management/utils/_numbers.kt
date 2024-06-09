package com.leafclient.leaf.management.utils

import java.lang.RuntimeException
import kotlin.math.round
import kotlin.math.roundToInt

/**
 * Implements the [plus] operation on generic number types.
 */
@Suppress("unchecked_cast")
operator fun <T: Number> T.plus(other: Number): T {
    return when(this) {
        is Byte -> this.toByte() + other.toByte()
        is Short -> this.toShort() + other.toShort()
        is Int -> this.toInt() + other.toInt()
        is Long -> this.toLong() + other.toLong()
        is Float -> this.toFloat() + other.toFloat()
        is Double -> this.toDouble() + other.toDouble()
        else -> throw RuntimeException("Cannot apply math operation on " + this::class.java.simpleName)
    } as T
}

/**
 * Implements the [minus] operation on generic number types.
 */
@Suppress("unchecked_cast")
operator fun <T: Number> T.minus(other: Number): T {
    return when(this) {
        is Byte -> this.toByte() - other.toByte()
        is Short -> this.toShort() - other.toShort()
        is Int -> this.toInt() - other.toInt()
        is Long -> this.toLong() - other.toLong()
        is Float -> this.toFloat() - other.toFloat()
        is Double -> this.toDouble() - other.toDouble()
        else -> throw RuntimeException("Cannot apply math operation on " + this::class.java.simpleName)
    } as T
}

/**
 * Implements the [times] operation on generic number types.
 */
@Suppress("unchecked_cast")
operator fun <T: Number> T.times(other: Number): T {
    return when(this) {
        is Byte -> this.toByte() * other.toByte()
        is Short -> this.toShort() * other.toShort()
        is Int -> this.toInt() * other.toInt()
        is Long -> this.toLong() * other.toLong()
        is Float -> this.toFloat() * other.toFloat()
        is Double -> this.toDouble() * other.toDouble()
        else -> throw RuntimeException("Cannot apply math operation on " + this::class.java.simpleName)
    } as T
}

/**
 * Implements the [div] operation on generic number types.
 */
@Suppress("unchecked_cast")
operator fun <T: Number> T.div(other: Number): T {
    return when(this) {
        is Byte -> this.toByte() / other.toByte()
        is Short -> this.toShort() / other.toShort()
        is Int -> this.toInt() / other.toInt()
        is Long -> this.toLong() / other.toLong()
        is Float -> this.toFloat() / other.toFloat()
        is Double -> this.toDouble() / other.toDouble()
        else -> throw RuntimeException("Cannot apply math operation on " + this::class.java.simpleName)
    } as T
}

/**
 * Implements the [rem] operation on generic number types.
 */
@Suppress("unchecked_cast")
operator fun <T: Number> T.rem(other: Number): T {
    return when(this) {
        is Byte -> this.toByte() % other.toByte()
        is Short -> this.toShort() % other.toShort()
        is Int -> this.toInt() % other.toInt()
        is Long -> this.toLong() % other.toLong()
        is Float -> this.toFloat() % other.toFloat()
        is Double -> this.toDouble() % other.toDouble()
        else -> throw RuntimeException("Cannot apply math operation on " + this::class.java.simpleName)
    } as T
}

/**
 * Implements the [compareTo] operation on generic number types.
 */
@Suppress("unchecked_cast")
operator fun <T: Number> T.compareTo(other: Number): Int {
    return when(this) {
        is Byte -> this.toByte().compareTo(other.toByte())
        is Short -> this.toShort().compareTo(other.toShort())
        is Int -> this.toInt().compareTo(other.toInt())
        is Long -> this.toLong().compareTo(other.toLong())
        is Float -> this.toFloat().compareTo(other.toFloat())
        is Double -> this.toDouble().compareTo(other.toDouble())
        else -> throw RuntimeException("Cannot apply math operation on " + this::class.java.simpleName)
    }
}

/**
 * Allows rounding a number to another one
 */
@Suppress("unchecked_cast")
fun <T: Number> T.round(other: Number): T {
    return when(this) {
        is Byte -> ((this.toDouble() / other.toDouble()).roundToInt() * other.toDouble()).toInt().toByte()
        is Short -> ((this.toDouble() / other.toDouble()).roundToInt() * other.toDouble()).toInt().toShort()
        is Int -> ((this.toDouble() / other.toDouble()).roundToInt() * other.toDouble()).toInt()
        is Long -> ((this.toDouble() / other.toDouble()).roundToInt() * other.toDouble()).toLong()
        is Float -> ((this.toDouble() / other.toDouble()).roundToInt() * other.toDouble()).toFloat()
        is Double -> ((this.toDouble() / other.toDouble()).roundToInt() * other.toDouble())
        else -> throw RuntimeException("Cannot apply math operation on " + this::class.java.simpleName)
    } as T
}