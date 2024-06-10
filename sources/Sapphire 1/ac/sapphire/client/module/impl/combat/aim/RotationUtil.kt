package ac.sapphire.client.module.impl.combat.aim

import ac.sapphire.client.ext.mc
import ac.sapphire.client.ext.player
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.MathHelper
import java.security.SecureRandom
import kotlin.math.abs
import kotlin.math.atan2

object RotationUtil {
    private val secureRng = SecureRandom()

    private var lastYaw = 999F
    private var lastPitch = 999F

    fun getRandomizedRotations(box: AxisAlignedBB, target: EntityPlayer, speed: Float): FloatArray {
        var xDiff: Double = target.posX - player.posX
        var zDiff: Double = target.posZ - player.posZ

        var yPosToAim = (target.posY) - (player.posY)

        val oxd = xDiff
        val oxz = zDiff

        xDiff += box.minX + box.maxX
        zDiff += box.minZ + box.maxZ

        val xzsqrt: Double
        if (target.posY < player.posY) {
            xzsqrt = MathHelper.sqrt_double(oxd * oxd + oxz * oxz).toDouble()
            yPosToAim += box.minY - box.maxY
        } else {
            xzsqrt = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff).toDouble()
        }

        var intendedYaw = (Math.toDegrees(atan2(zDiff, xDiff)) - 90f).toFloat()
        var intendedPitch = (-Math.toDegrees(atan2(yPosToAim, xzsqrt))).toFloat()

        var yawChange: Float = randomFloat(0.2F, 1F)
        var pitchChange: Float = randomFloat(0.2F, 1F)
        if (lastYaw == yawChange) {
            yawChange *= -1
        }

        if (lastPitch == pitchChange) {
            pitchChange *= -1
        }

        if (randomFloat(1F, 20F) < 15) {
            intendedYaw += yawChange / 3
            if (abs((intendedPitch + pitchChange)) <= 90) {
                intendedPitch += pitchChange / 3
            }
        } else {
            intendedYaw += yawChange
            if (abs((intendedPitch + pitchChange)) <= 90) {
                intendedPitch += pitchChange
            }
        }

        lastYaw = yawChange
        lastPitch = pitchChange

        intendedYaw = wrapRotation(player.rotationYaw, intendedYaw, speed)
        intendedPitch = wrapRotation(player.rotationPitch, intendedPitch, speed)

        return roundRotation(intendedYaw, intendedPitch)
    }

    private fun randomFloat(min: Float, max: Float): Float {
        return secureRng.nextFloat() * (max - min) + min
    }

    private fun roundRotation(yaw: Float, pitch: Float): FloatArray {
        val f = mc.gameSettings.mouseSensitivity * 0.6f + 0.2f
        val f1 = f * f * f * 1.2f

        return floatArrayOf(yaw - (yaw % (f1 / 4)), pitch - (pitch % (f1 / 4)))
    }

    private fun wrapRotation(angle: Float, targetAngle: Float, maxIncrease: Float): Float {
        var f = MathHelper.wrapAngleTo180_float(targetAngle - angle)
        if (f > maxIncrease) {
            f = maxIncrease
        }
        if (f < -maxIncrease) {
            f = -maxIncrease
        }
        return angle + f
    }
}