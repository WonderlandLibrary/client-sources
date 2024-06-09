package com.leafclient.leaf.utils

import net.minecraft.client.Minecraft
import net.minecraft.client.network.NetworkPlayerInfo
import net.minecraft.entity.Entity
import net.minecraft.entity.boss.EntityDragon
import net.minecraft.entity.boss.EntityWither
import net.minecraft.entity.monster.*
import net.minecraft.entity.passive.*
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.MobEffects
import net.minecraft.scoreboard.ScorePlayerTeam
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.Vec3d
import java.util.regex.Pattern

/**
 * Returns whether the entity is friendly (tamed or friend player)
 */
val Entity.isFriendly: Boolean
    get() = this is EntityTameable && this.ownerId == Minecraft.getMinecraft().player.uniqueID

/**
 * Returns whether the entity will ignore you or not
 */
val Entity.isPassive: Boolean
    get() = this is EntityBat
            || this is EntityChicken
            || this is EntityCow
            || this is EntityDonkey
            || this is EntityHorse
            || this is EntityLlama
            || this is EntityMooshroom
            || this is EntityMule
            || (this is EntityOcelot && this.ownerId != null && this.ownerId != Minecraft.getMinecraft().player.uniqueID)
            || this is EntityParrot
            || this is EntityPig
            || (this is EntityRabbit && this.rabbitType != 99)
            || this is EntitySheep
            || this is EntitySkeletonHorse
            || this is EntitySquid
            || this is EntityVillager
            || (this is EntityWolf && !this.isAngry)
            // These are mobs yet passive
            || (this is EntityIronGolem && this.rotationPitch == 0F)
            || (this is EntityEnderman && !this.isScreaming)
            || (this is EntityPigZombie && !this.isAngry)
            || (this is EntityPolarBear && this.isChild)

/**
 * Returns whether the entity is hostile or not
 */
val Entity.isHostile: Boolean
    get() = this is EntityBlaze
            || this is EntitySpider
            || this is EntityCreeper
            || this is EntityElderGuardian
            || this is EntityEndermite
            || this is EntityEvoker
            || this is EntityGhast
            || this is EntityGiantZombie
            || this is EntityGuardian
            || this is EntityHusk
            || this is EntityIllusionIllager
            || this is EntityMagmaCube
            || (this is EntityPolarBear && !this.isChild)
            || this is EntityShulker
            || this is EntitySilverfish
            || this is EntitySlime
            || this is EntitySnowman
            || this is EntitySkeleton
            || this is EntityStray
            || this is EntityVex
            || this is EntityWitherSkeleton
            || this is EntityWitch
            || this is EntityWither
            || this is EntityDragon
            || (this is EntityZombie && this !is EntityPigZombie)
            // Specific cases
            || (this is EntityIronGolem && this.rotationPitch != 0F)
            || (this is EntityEnderman && this.isScreaming)
            || (this is EntityWolf && this.isAngry)
            || (this is EntityPigZombie && this.isAngry)
            || (this is EntityRabbit && this.rabbitType == 99)

/**
 * Returns the eye position in a [Vec3d]
 */
val Entity.eyePosition: Vec3d
    get() = Vec3d(posX, posY + eyeHeight, posZ)

/**
 * Returns the motion at jumping of this entity
 */
val EntityPlayer.jumpMotion: Double
    get() = 0.42 + (this.getActivePotionEffect(MobEffects.JUMP_BOOST)?.let {
        return@let (it.amplifier + 1) * 0.1
    } ?: 0.0)

/**
 * Returns the player's base horizontal speed.
 */
val EntityPlayer.baseSpeed: Double
    get() = 0.2873 * (this.getActivePotionEffect(MobEffects.SPEED)?.let {
        return@let 1.0 + (it.amplifier + 1) * 0.2
    } ?: 1.0)

/**
 * Returns the nearest point on the [box]
 */
fun EntityPlayer.nearestPointBB(box: AxisAlignedBB): Vec3d {
    val eye = eyePosition
    val origin = doubleArrayOf(eye.x, eye.y, eye.z)
    val destMins = doubleArrayOf(box.minX, box.minY, box.minZ)
    val destMaxs = doubleArrayOf(box.maxX, box.maxY, box.maxZ)
    for (i in 0..2) {
        if (origin[i] > destMaxs[i]) origin[i] = destMaxs[i] else if (origin[i] < destMins[i]) origin[i] = destMins[i]
    }
    return Vec3d(origin[0], origin[1], origin[2])
}

/**
 * Returns the direction of the movement based on its yaw.
 */
val EntityPlayer.movementYaw: Double
    get() {
        var rotationYaw = rotationYaw

        if (this.moveForward < 0f) rotationYaw += 180f

        var forward = 1f
        if (this.moveForward < 0f) forward = -0.5f else if (this.moveForward > 0f) forward = 0.5f
        if (this.moveStrafing > 0f) rotationYaw -= 90f * forward
        if (this.moveStrafing < 0f) rotationYaw += 90f * forward

        return rotationYaw.toDouble() * 0.017453292
    }

/**
 * Checks whether an [Entity] is inside of a block
 * TODO: Do it better, this one is inaccurate, see scaffold which uses pretty much the same one.
 */
val Entity.isInsideBlock: Boolean
    get() {
        return world.getCollisionBoxes(this, this.entityBoundingBox).isNotEmpty()
    }

private val COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]")

val NetworkPlayerInfo.name: String
    get() = displayName?.formattedText ?: ScorePlayerTeam.formatPlayerName(playerTeam, gameProfile.name)

val String.stripColor: String
    get() = COLOR_PATTERN.matcher(this).replaceAll("")