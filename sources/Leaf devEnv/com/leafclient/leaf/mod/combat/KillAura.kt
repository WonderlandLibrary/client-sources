package com.leafclient.leaf.mod.combat

import com.leafclient.leaf.event.game.entity.PlayerMotionEvent
import com.leafclient.leaf.event.game.entity.PlayerUpdateEvent
import com.leafclient.leaf.extension.isBlocking
import com.leafclient.leaf.extension.isBot
import com.leafclient.leaf.extension.lastReportedPitch
import com.leafclient.leaf.extension.lastReportedYaw
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.asSetting
import com.leafclient.leaf.management.setting.contraint.bound
import com.leafclient.leaf.management.setting.contraint.choice
import com.leafclient.leaf.management.setting.contraint.increment
import com.leafclient.leaf.management.setting.setting
import com.leafclient.leaf.management.setting.settingContainer
import com.leafclient.leaf.management.utils.Labelable
import com.leafclient.leaf.utils.client.setting.Mode
import com.leafclient.leaf.mod.combat.killaura.SingleMode
import com.leafclient.leaf.mod.combat.killaura.SwitchMode
import com.leafclient.leaf.mod.movement.NoSlow
import com.leafclient.leaf.utils.*
import com.leafclient.leaf.utils.client.registerTask
import com.leafclient.leaf.utils.math.angleDifference
import com.leafclient.leaf.utils.math.length
import com.leafclient.leaf.utils.math.rotationTo
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import fr.shyrogan.publisher4k.subscription.subscription
import net.minecraft.client.Minecraft
import net.minecraft.util.math.Vec3d
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.network.play.client.CPacketPlayer
import net.minecraft.util.EnumHand
import net.minecraft.util.math.Vec2f
import org.lwjgl.input.Keyboard
import kotlin.random.Random

class KillAura: ToggleableMod("KillAura", Category.FIGHT, defaultKey = Keyboard.KEY_R) {

    private var mode by setting<Mode>("Mode", SwitchMode()) {
        choice(SingleMode())
    }
    private var attackStrength by setting("Attack strength", false)
    private var aps by setting("APS", 10) {
        bound(0, 100)
        increment(1)
    }
    private var autoblock by setting("Autoblock", true)
    var switchHits by ::mode.asSetting.setting("Switch hits", 1) {
            bound(0, 4)
            increment(1)
    }
    private var reach by setting("Reach", 4.0) {
        bound(0.0, 7.0)
        increment(0.1)
    }
    private var rangeCalculation by ::reach.asSetting.setting("Range calculation", RangeCalculations.BOUNDING_BOX) {
            choice(*RangeCalculations.values())
    }
    private var rotationSpeed by setting("Rotation speed", 180.0F) {
        bound(0.0F, 180.0F)
        increment(0.25F)
    }
    private var priority by setting("Priority", Priority.FOV) {
        choice(*Priority.values())
    }

    private var targets by settingContainer("Targets")
    private var players by ::targets.asSetting.setting("Players", true)
    private var hostiles by ::targets.asSetting.setting("Hostiles", true)
    private var passives by ::targets.asSetting.setting("Passives", true)

    private var rayTracing by setting("Ray tracing", false)
    var lockView by setting("Lock view", false)
    private var superKnockback by setting("Super knockback", false)
    private var superKnockbackPackets by ::superKnockback.asSetting
        .setting("Packets", 100) {
            bound(20, 1000)
            increment(10)
        }
    private var reverseKnockback by setting("Reverse knockback", false)


    val delayer = Delayer()
    val superKnockbackDelayer = Delayer()
    var rotationTarget: Entity? = null

    @Subscribe(-11)
    val onPostMotion = Listener { e: PlayerMotionEvent.Post ->
        if(rotationTarget != null && autoblock) {
            // This should be done before the NoSlow and overrides NoSlow's method by cancelling
            // the upcoming events
            mc.player.isBlocking = true
            NoSlow.startBlocking()
            e.isCancelled = true
        }
        rotationTarget = null
    }

    override val suffix: String
        get() = mode.label

    /**
     * Returns the available targets for the KillAura
     */
    val availableTargets: List<Entity>
        get() = mc.world.loadedEntityList
            .filter { it != mc.player && (it !is EntityLivingBase || it.isEntityAlive)
                    && ((it is EntityPlayer && players) || (it.isHostile && hostiles) || (it.isPassive && passives))
                    && (it !is EntityPlayer || !it.isBot)
                    && !it.isFriendly
                    && rangeCalculation.calculator.invoke(mc.player, it) < reach
                    && (!rayTracing || mc.player.canEntityBeSeen(it))
            }
            .sortedWith(priority.comparator)

    /**
     * Checks whether the player can actually attack or needs to wait
     */
    val isAbleToAttack: Boolean
        get() = delayer.hasReached(1000L / aps) && (!attackStrength || mc.player.getCooledAttackStrength(0.0F) == 1F)

    /**
     * Returns the instant rotation to the target
     */
    val rotationToTarget: Vec2f
        get() = if(rotationTarget == null) {
            Vec2f(mc.player.rotationYaw, mc.player.rotationPitch)
        } else {
            val target = rotationTarget!!
            val rotation = mc.player.eyePosition.rotationTo(Vec3d(target.posX, target.posY + target.eyeHeight / 1.5, target.posZ))

            if(!reverseKnockback)
                rotation
            else
                Vec2f(rotation.x - 180F, rotation.y * -1F + 180F)
        }

    /**
     * Returns the rotation towards the target
     */
    val smoothRotationToTarget: Vec2f
        get() = if(rotationTarget == null) {
            Vec2f(mc.player.rotationYaw, mc.player.rotationPitch)
        } else {
            val rotationSpeed = rotationSpeed + Random.nextDouble(-10.0, 10.0).toFloat()
            val difference = Vec2f(mc.player.lastReportedYaw, mc.player.lastReportedPitch)
                .angleDifference(rotationToTarget)

            val yawMovement = when {
                difference.x > rotationSpeed  -> rotationSpeed
                difference.x < -rotationSpeed -> -rotationSpeed
                else                          -> difference.x
            }
            val pitchMovement = when {
                difference.y > rotationSpeed  -> rotationSpeed
                difference.y < -rotationSpeed -> -rotationSpeed
                else                          -> difference.y
            }

            Vec2f(mc.player.lastReportedYaw + yawMovement, mc.player.lastReportedPitch + pitchMovement)
        }

    /**
     * Attacks specified [entities]
     */
    fun attack(entities: List<Entity>) {
        subscription(Listener<PlayerUpdateEvent.Pre> {
            NoSlow.stopBlocking()
            if(superKnockback) {
                if(superKnockbackDelayer.hasReached(1000L) && entities.any { it.entityBoundingBox.intersects(mc.player.entityBoundingBox) }) {
                    for(i in 0..superKnockbackPackets) {
                        mc.player.connection.sendPacket(CPacketPlayer(false))
                    }
                    superKnockbackDelayer.reset()
                }
            }
            entities.forEach {
                mc.playerController.attackEntity(mc.player, it)
                mc.player.swingArm(EnumHand.MAIN_HAND)
            }
        }).registerTask()
    }

    /**
     * Different ways to calculate the distance between two entities
     */
    enum class RangeCalculations(override val label: String, val calculator: EntityPlayer.(Entity) -> Double): Labelable {
        BOUNDING_BOX("Bounding box", {
            this.nearestPointBB(it.entityBoundingBox)
                .distanceTo(eyePosition)
        }),
        DISTANCE("Position", {
            this.getDistance(it).toDouble()
        })
    }

    /**
     * Different priorities
     */
    enum class Priority(override val label: String, val comparator: Comparator<Entity>): Labelable {
        FOV("FOV", compareBy {
            val mc = Minecraft.getMinecraft()

            Vec2f(mc.player.lastReportedYaw, mc.player.lastReportedPitch)
                .angleDifference(mc.player.eyePosition.rotationTo(Vec3d(it.posX, it.posY + it.eyeHeight / 1.5, it.posZ)))
                .length
        }),
        DISTANCE("Distance", compareBy {
            val mc = Minecraft.getMinecraft()

            mc.player.getDistance(it)
        });
    }

}