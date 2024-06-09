package com.leafclient.leaf.utils.math

import net.minecraft.util.math.Vec2f
import net.minecraft.util.math.Vec3d
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sqrt

/**
 * Returns the rotation to [other] starting from [Vec3d]
 */
fun Vec3d.rotationTo(other: Vec3d): Vec2f {
    val delta = Vec3d(
        other.x - x,
        other.y - y,
        other.z - z
    )

    val distance = sqrt((delta.x * delta.x + delta.z * delta.z))

    val yawAtan = atan2(delta.z, delta.x)
    val pitchAtan = atan2(delta.y, distance)

    val deg = 180.0 / PI

    val yaw = yawAtan.toFloat() * deg.toFloat() - 90F
    val pitch = -pitchAtan.toFloat() * deg.toFloat()

    return Vec2f(
        yaw % 360F, pitch
    )
}

/**
 * Returns the angle difference
 */
fun Vec2f.angleDifference(other: Vec2f): Vec2f {
    var xDiff = (other.x - x) % 360F
    var yDiff = (other.y - y) % 360F

    if(xDiff > 180F)
        xDiff = 360F - xDiff
    if(yDiff > 180F)
        yDiff = 360F - yDiff

    if(xDiff < -180F)
        xDiff += 360F
    if(yDiff < -180F)
        yDiff += 360F

    return Vec2f(xDiff, yDiff)
}

/**
 * Returns the length of this vec
 */
val Vec2f.length: Float
    get() = sqrt(x * x + y * y)