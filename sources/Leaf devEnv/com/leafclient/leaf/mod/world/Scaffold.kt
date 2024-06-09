package com.leafclient.leaf.mod.world

import com.leafclient.leaf.event.game.entity.PlayerMotionEvent
import com.leafclient.leaf.event.game.entity.PlayerMoveEvent
import com.leafclient.leaf.extension.timer
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.asSetting
import com.leafclient.leaf.management.setting.contraint.bound
import com.leafclient.leaf.management.setting.contraint.choice
import com.leafclient.leaf.management.setting.contraint.increment
import com.leafclient.leaf.management.setting.setting
import com.leafclient.leaf.mod.world.scaffold.AirTower
import com.leafclient.leaf.mod.world.scaffold.PerfectJumpTower
import com.leafclient.leaf.utils.client.keyboard.Key
import com.leafclient.leaf.utils.eyePosition
import com.leafclient.leaf.utils.math.BlockInteraction
import com.leafclient.leaf.utils.math.getClosestFacings
import com.leafclient.leaf.utils.math.isInsideBlocks
import com.leafclient.leaf.utils.math.rotationTo
import com.leafclient.leaf.utils.client.setting.Mode
import com.leafclient.leaf.utils.doWhenSwappingTo
import com.leafclient.leaf.utils.safeWalk
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.client.settings.KeyBinding
import net.minecraft.entity.projectile.EntitySnowball
import net.minecraft.init.Blocks
import net.minecraft.item.ItemBlock
import net.minecraft.network.play.client.CPacketAnimation
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.RayTraceResult
import net.minecraft.util.math.Vec2f
import net.minecraft.util.math.Vec3d
import org.lwjgl.input.Keyboard
import kotlin.random.Random

class Scaffold: ToggleableMod("Scaffold", Category.WORLD, defaultKey = Keyboard.KEY_X) {

    private var tower by setting<Mode>("Tower", PerfectJumpTower(this)) {
        choice(AirTower())
    }
    private var reach by setting("Reach", 5) {
        bound(1, 6)
        increment(1)
    }
    private var keepRotation by setting("Keep Rotation", "Forces the scaffold to keep the rotations", true)
    private var sneak by setting("Sneak", false)
    private var rayTracing by setting("Ray tracing", true)
    private var reverse by setting("Reverse", false)
    private var reverseKey by ::reverse.asSetting.setting("Keybind", Key(Keyboard.KEY_LSHIFT))
    private var speedFactor by setting("Speed factor", 1.0) {
        bound(0.0, 1.0)
        increment(0.05)
    }

    private var target: BlockInteraction? = null
    private var previousRotation: Vec2f? = null

    private val blackListedBlocks = arrayOf(
        Blocks.CHEST,
        Blocks.ENDER_CHEST,
        Blocks.TRAPPED_CHEST,
        Blocks.FURNACE,
        Blocks.LADDER,
        Blocks.TALLGRASS,
        Blocks.ENCHANTING_TABLE,
        Blocks.ANVIL,
        Blocks.ACTIVATOR_RAIL,
        Blocks.BEACON,
        Blocks.BREWING_STAND,
        Blocks.CACTUS,
        Blocks.CAULDRON,
        Blocks.CRAFTING_TABLE,
        Blocks.DEADBUSH,
        Blocks.DISPENSER,
        Blocks.DROPPER,
        Blocks.SAND,
        Blocks.GRAVEL
    //Not sure if I should do all bad blocks or not
    )

    @Subscribe
    val onPreMotion = Listener<PlayerMotionEvent.Pre> { e ->
        // We don't have any block
        val randomBlockSlot = randomBlockSlot
        if(randomBlockSlot == -1)
            return@Listener

        val box = mc.player.entityBoundingBox
        val reversing = reverse && reverseKey.isKeyDown
        val scaffoldPosition = if(reversing) {
            KeyBinding.setKeyBindState(reverseKey.keyCode, false)
            Vec3d(e.posX, e.posY - 1.5, e.posZ)
        } else {
            Vec3d(e.posX, e.posY - 0.5, e.posZ)
        }

        mc.player.motionX *= speedFactor
        mc.player.motionZ *= speedFactor

        if(!box.contract(0.1, 0.0, 0.1).offset(0.0, -0.1, 0.0).isInsideBlocks(mc.player, mc.world) && sneak) {
            mc.player.isSneaking = true
            e.isSneaking = true
            mc.player.motionX *= 0.2
            mc.player.motionZ *= 0.2
        }

        if(!box.contract(0.3, 0.0, 0.3).offset(0.15, if(reversing) -1.1 else -0.1, 0.15).isInsideBlocks(mc.player, mc.world)) {
            val vec = scaffoldPosition
                .blocksAround
                .firstOrNull() ?: return@Listener

            val pos = BlockPos(vec)
            val bb = mc.world.getBlockState(pos).getBoundingBox(mc.world, pos)

            // I was sorting them two times, i'm a poopihead
            target = bb.getClosestFacings(scaffoldPosition, pos)
                .first()
        }

        if (target != null) {
            val target = target!!
            previousRotation = mc.player.eyePosition.rotationTo(target.interactionBox.center)
            previousRotation!!.apply {
                e.rotationYaw = x + Random.nextDouble(-2.5, 2.5).toFloat()
                e.rotationPitch = y
            }
            e.isCancelled = true
        } else if (previousRotation != null && keepRotation) {
            e.rotationYaw = previousRotation!!.x
            e.rotationPitch = previousRotation!!.y
            e.isCancelled = true
        }
    }

    @Subscribe
    val onPostMotion = Listener<PlayerMotionEvent.Post> { e ->
        if(target != null) {
            val target = target ?: return@Listener
            val center = target.interactionBox.center
            //<editor-fold desc="Raytracing">
            var hitVec = Vec3d(center.x, center.y, center.z)
            if(rayTracing) {
                val reversing = reverse && reverseKey.isKeyDown
                val result = mc.player.rayTrace(reach.toDouble(), mc.timer.renderPartialTicks)
                if(result == null || result.typeOfHit != RayTraceResult.Type.BLOCK) {
                    this.target = null
                    return@Listener
                }

                val fakeEntity = EntitySnowball(mc.world, center.x, center.y, center.z)
                if(!reversing && !mc.player.canEntityBeSeen(fakeEntity)) {
                    return@Listener
                }

                hitVec = result.hitVec
            }
            //</editor-fold>
            val randomBlockSlot = randomBlockSlot
            if(randomBlockSlot != -1) {
                mc.player.doWhenSwappingTo(randomBlockSlot) {
                    if (rayTracing && mc.playerController.processRightClickBlock(mc.player, mc.world, BlockPos(target.position), target.facing, hitVec, EnumHand.MAIN_HAND) == EnumActionResult.SUCCESS) {
                        mc.player.connection.sendPacket(CPacketAnimation(EnumHand.MAIN_HAND))
                    }
                }
            }

            e.isCancelled = true
        }
    }

    @Subscribe
    val onMove = Listener<PlayerMoveEvent> { e ->
        if(randomBlockSlot == -1)
            e.safeWalk(-0.5)
    }

    /**
     * Returns the quantity of blocks available in the inventory
     */
    val blockQuantity: Int
        get() = mc.player.inventoryContainer.inventorySlots
            .filter { it.hasStack && !it.stack.isEmpty && it.stack.item is ItemBlock
                    && !blackListedBlocks.contains((it.stack.item as ItemBlock).block) }
            .map { it.stack.count }
            .sum()

    /**
     * Returns a random block slot.
     * If not blocks are found, returns -1
     */
    private val randomBlockSlot: Int
        get() {
            val slots = mc.player.inventoryContainer.inventorySlots
                .filter { it.hasStack && !it.stack.isEmpty && it.stack.item is ItemBlock
                        && !blackListedBlocks.contains((it.stack.item as ItemBlock).block) }

            return if(slots.isEmpty())
                -1
            else {
                val hotBarSlot = slots
                    .minBy { it.slotIndex }
                    ?.slotIndex ?: 555

                if(hotBarSlot > 8) {
                    slots.random()
                        .slotIndex
                } else {
                    hotBarSlot
                }
            }
        }

    /**
     * Returns each interactable blocks around
     */
    private val Vec3d.blocksAround: List<Vec3d>
        get() {
            val blockList = mutableListOf<Vec3d>()
            for(x in -reach..reach) {
                for(z in -reach..reach) {
                    for(y in -reach..1) {
                        val vec = Vec3d(this.x + x, this.y + y, this.z + z)
                        val pos = BlockPos(vec)

                        if(mc.world.getBlockState(pos).getCollisionBoundingBox(mc.world, pos) != null
                            && this.distanceTo(vec) <= reach)
                            blockList.add(vec)
                    }
                }
            }
            return blockList
                .sortedBy { this.distanceTo(it) }
        }

    override fun onDisable() {
        target = null
        previousRotation = null
    }

}