package com.cout970.fira.modules

import com.cout970.fira.Config
import com.cout970.fira.util.Utils
import net.minecraft.block.Block
import net.minecraft.client.model.ModelBase
import net.minecraft.client.model.ModelEnderCrystal
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityEnderCrystal
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.network.play.client.CPacketHeldItemChange
import net.minecraft.network.play.client.CPacketPlayer
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
import net.minecraft.util.*
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraftforge.common.ISpecialArmor
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.floor
import kotlin.math.min

object CrystalPvP {
    private val ENDER_CRYSTAL_TEXTURES = ResourceLocation("textures/entity/endercrystal/endercrystal.png")
    private val modelEnderCrystal: ModelBase = ModelEnderCrystal(0.0f, false)

    fun hud(): String {
        val count = Utils.mc.player?.inventory?.let { inv ->
            var count = 0
            for (i in listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, inv.sizeInventory - 1)) {
                val stack = inv.getStackInSlot(i)
                if (Blocks.OBSIDIAN == Block.getBlockFromItem(stack.item)) {
                    count += stack.count
                }
            }
            count
        } ?: 0

        return "Surround $count"
    }

    fun renderEntity(entity: EntityEnderCrystal, x: Float, y: Float, z: Float): Boolean {
        if (!Config.CrystalPvP.colorDamage) return false
        GlStateManager.pushMatrix()
        GlStateManager.translate(x, y, z)
        Utils.mc.textureManager.bindTexture(ENDER_CRYSTAL_TEXTURES)
        val s = min(1f, calculateProtection(playerDamage(entity, Utils.mc.player), Utils.mc.player) / 10f)
        GlStateManager.color(1f - (1 - s), 0f, 1f - s)
        modelEnderCrystal.render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f)
        GlStateManager.color(1f, 1f, 1f)
        GlStateManager.popMatrix()
        return true
    }

    fun calculateProtection(amount: Float, player: EntityPlayer): Float {
        val damageSrc = DamageSource("explosion").setDifficultyScaled().setExplosion()
        val inv = NonNullList.withSize(player.inventory.armorInventory.size, ItemStack.EMPTY)

        player.inventory.armorInventory.forEachIndexed { index, itemStack -> inv[index] = itemStack.copy() }

        var damageAmount = ISpecialArmor.ArmorProperties.applyArmor(
                player, inv, damageSrc, amount.toDouble()
        )
        if (damageAmount <= 0) return 0f
        damageAmount = (damageAmount - player.absorptionAmount).coerceAtLeast(0.0f)
        return damageAmount
    }

    fun playerDamage(crystal: EntityEnderCrystal, entity: Entity): Float {
        val f3 = 6.0 * 2.0
        val vec3d = Vec3d(crystal.posX, crystal.posY, crystal.posZ)

        val d12 = entity.getDistance(crystal.posX, crystal.posY, crystal.posZ) / f3
        if (d12 <= 1.0) {
            var d5: Double = entity.posX - crystal.posX
            var d7: Double = entity.posY + entity.eyeHeight.toDouble() - crystal.posY
            var d9: Double = entity.posZ - crystal.posZ
            val d13 = MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9).toDouble()

            if (d13 != 0.0) {
                d5 /= d13
                d7 /= d13
                d9 /= d13
                val d14 = entity.world.getBlockDensity(vec3d, entity.entityBoundingBox)
                val d10 = (1.0 - d12) * d14
                return ((d10 * d10 + d10) / 2.0 * 7.0 * f3 + 1.0).toInt().toFloat()
            }
        }
        return 0f
    }

    @SubscribeEvent
    fun onTick(e: TickEvent.ClientTickEvent) {
        if (!Config.CrystalPvP.surround) return
        if (e.phase != TickEvent.Phase.END) return
        placeObsidian(false)
    }

    fun placeObsidian(tp: Boolean) {
        val p = Utils.mc.player ?: return

        // Buscar slot con obsidiana
        var index: Int? = null
        for (i in listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, p.inventory.sizeInventory - 1)) {
            val stack = p.inventory.getStackInSlot(i)
            if (!stack.isEmpty && Block.getBlockFromItem(stack.item) == Blocks.OBSIDIAN) {
                index = i
            }
        }

        // No hay obsidiana en el inventario
        if (index == null) return

        // Bloque actual
        val current = BlockPos(floor(p.posX).toInt(), floor(p.posY + 0.5).toInt(), floor(p.posZ).toInt())

        // Comprobar que bloques se pueden poner
        val posList = listOf(current.north(), current.south(), current.east(), current.west()).mapNotNull { target ->
            val pos = target.down()
            val replace = p.world.getBlockState(pos).block.isReplaceable(p.world, pos)

            if (replace) {
                EnumFacing.values().forEach { side ->
                    p.world.getBlockState(pos.offset(side.opposite)).isSideSolid(p.world, pos, side)
                    return@mapNotNull pos to side
                }
            }

            val facing = EnumFacing.UP

            if (p.world.getBlockState(pos.offset(facing)).block.isReplaceable(p.world, pos.offset(facing))) {
                pos to facing
            } else {
                null
            }
        }

        // No hacer nada si no se pueden colocar bloques
        if (posList.isEmpty()) return

        // TP al centro del bloque
        if (tp) {
            p.posX = current.x + 0.5
            p.posZ = current.z + 0.5

            p.connection.sendPacket(CPacketPlayer.Position(
                    p.posX, p.posY, p.posZ, true
            ))
        }

        // Slot seleccionado con prioridad
        val prev = p.inventory.currentItem

        // Cambir slot seleccionado
        if (index != p.inventory.currentItem && index < 9) {
            p.connection.sendPacket(CPacketHeldItemChange(index))
            p.inventory.currentItem = index
        }

        // Seleccionar mano
        val hand = if (index < 9) EnumHand.MAIN_HAND else EnumHand.OFF_HAND

        // Guardar la orientación actual
        val pair = p.pitchYaw

        // Colocar bloques
        posList.forEach { (pos, side) ->

            val center = Vec3d(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5)
            val head = p.getPositionEyes(0f)

            val dir = center.subtract(head).normalize()

            val yaw = Math.toDegrees(atan2(dir.x, dir.z))
            val pitch = Math.toDegrees(-asin(dir.y))

            p.connection.sendPacket(CPacketPlayer.Rotation(
                    yaw.toFloat(), pitch.toFloat(), true
            ))
            p.connection.sendPacket(CPacketPlayerTryUseItemOnBlock(
                    pos, side, hand,
                    0f, 1f, 0f
            ))
        }

        // Restore yaw/pitch
        p.connection.sendPacket(CPacketPlayer.Rotation(
                pair.y, pair.x, true
        ))

        // Restore selected slot
        if (prev != p.inventory.currentItem) {
            p.connection.sendPacket(CPacketHeldItemChange(prev))
            p.inventory.currentItem = prev
        }
    }

    fun suppressExplosions(): Boolean {
        return Config.CrystalPvP.suppressExplosions
    }
}