package com.leafclient.leaf.mod.world

import com.leafclient.leaf.event.game.entity.PlayerMotionEvent
import com.leafclient.leaf.event.game.entity.PlayerMoveEvent
import com.leafclient.leaf.event.game.entity.PlayerUpdateEvent
import com.leafclient.leaf.event.game.world.CollisionBoxEvent
import com.leafclient.leaf.extension.isInWeb
import com.leafclient.leaf.extension.timer
import com.leafclient.leaf.extension.timerSpeed
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.setting
import com.leafclient.leaf.utils.*
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.block.Block
import net.minecraft.block.BlockStairs
import net.minecraft.init.Blocks
import net.minecraft.util.math.AxisAlignedBB
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author auto on 4/17/2020
 */
class Terrain: ToggleableMod("Terrain", Category.WORLD) {

    var ice by setting("Ice", true)

    var cactus by setting("Cactus", true)

    var ladders by setting("Ladders", true)

    var stairs by setting("Stairs", false)

    var webs by setting("Webs", true)

    private val delayer = Delayer()

    //All ice blocks
    private var iceBlocks = arrayOf(Blocks.ICE, Blocks.PACKED_ICE, Blocks.FROSTED_ICE)

    @Subscribe
    val onPlayerMove = Listener<PlayerMoveEvent> { e ->
        if(!mc.player.isMoving)
            return@Listener

        if (ice) {
            if (iceBlocks.contains(mc.world.getBlockState(mc.player.position.add(-0.5, -1.0, -0.5)).block))
                    e.strafe(mc.player.baseSpeed + 0.4)
        }

        if (ladders) {
            //Needs more testing, only done with short distances
            if (mc.player.isOnLadder && mc.player.motionY > 0) {
                if (delayer.hasReached(1000L))
                    mc.timer.timerSpeed = 2f
            }
            if (delayer.hasReached(1150L) && mc.timer.timerSpeed == 2f) {
                mc.timer.timerSpeed = 1f
                delayer.reset()
            }
        }

        if (webs && mc.player.isInWeb) {
            //doesn't work yet
            if (mc.player.ticksExisted % 15 == 0) {
                e.strafe(0.8)
            } else {
                mc.timer.timerSpeed = 1f
            }
        }
    }

    @Subscribe
    val onPlayerUpdate = Listener<PlayerMotionEvent.Pre> { e ->
        if(!mc.player.isMoving)
            return@Listener

        if (stairs) {
            //needs improvement
            val pos = mc.player.position.add(-0.5, -1.0, -0.5)
            val yaw = mc.player.movementYaw
            if (mc.world.getBlockState(pos).block is BlockStairs
                && mc.world.getBlockState(pos.add(-sin(yaw) * 1, 1.0, cos(yaw) * 1)).block is BlockStairs
                && mc.player.onGround) {
                mc.player.jump()
            }
        }
    }

    @Subscribe
    val onPlayerCollision = Listener<CollisionBoxEvent> { e ->
        if(cactus && mc.world.getBlockState(e.position).block == Blocks.CACTUS) {
            e.boundingBox = Block.FULL_BLOCK_AABB
        }
    }

}