package com.cout970.fira.modules

import com.cout970.fira.Config
import com.cout970.fira.util.Utils
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.resources.I18n
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.network.play.client.CPacketPlayerTryUseItem
import net.minecraft.util.EnumHand
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.RayTraceResult
import net.minecraft.util.math.Vec3d
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import kotlin.math.absoluteValue
import kotlin.math.atan2
import kotlin.math.sign
import kotlin.math.sqrt

object AutoPilot {

    private var cooldown = 0
    private var lastTick = 0L

    fun hud(): String = "AutoPilot ${Config.AutoPilot.autoPilotHeight}"

    @SubscribeEvent
    fun onTick(e: TickEvent.ClientTickEvent) {
        if (e.phase != TickEvent.Phase.START) return

        if (!Config.AutoPilot.enable) return

        val player = Utils.mc.player ?: return
        val chestplate = player.inventory.armorInventory[2]

        // Ignorar si el jugador no tiene elytra
        if (chestplate.isEmpty || chestplate.item != Items.ELYTRA) return
        // o no esta volando
        if (!player.isElytraFlying) return

        // Deshabilita el piloto automatico cuando se acaba la durabilidad
        if (chestplate.itemDamage > chestplate.maxDamage - Config.ElytraTweaks.elytraMinDurability) return

        val velocity = Vec3d(player.motionX, player.motionY, player.motionZ).lengthVector()
        val ms = velocity.toFloat() * 20f

        if (Config.AutoPilot.autoPilotCollisionDetection > 0) {
            if (checkForFutureCollisions(player)) {
                return
            }
        }

        setPlayerPitch(player)

        if (Config.AutoPilot.autoUseRockets) {
            sendRocketIfNeed(player, ms)
        }
    }

    private fun checkForFutureCollisions(player: EntityPlayerSP): Boolean {
        val tmp = player.rotationPitch
        player.rotationPitch = 0f
        val pos: Vec3d = player.getPositionEyes(1f)
        val dir: Vec3d = Vec3d(player.motionX, 0.0, player.motionZ).scale(Config.AutoPilot.autoPilotCollisionDetection.toDouble())
        player.rotationPitch = tmp

        val res = player.world.rayTraceBlocks(pos, pos.add(dir), false, true, true)

        if (res != null && res.typeOfHit == RayTraceResult.Type.BLOCK && player.world.getBlockState(res.blockPos).block != Blocks.PORTAL) {
            player.motionX = 0.0
            player.motionY = 0.0
            player.motionZ = 0.0

            Utils.runLater(1) {
                player.motionX = 0.0
                player.motionZ = 0.0
            }

            Utils.runLater(5) {
                player.motionX = 0.0
                player.motionZ = 0.0
            }

            Utils.chatMsg("${TextFormatting.RED}${I18n.format("text.fira_client.autopilot.disable_msg")}")
            Utils.ping()

            Config.AutoFly.enable = false
            Config.AutoPilot.enable = false
            return true
        }
        return false
    }

    private fun sendRocketIfNeed(player: EntityPlayerSP, ms: Float) {
        if (player.world.totalWorldTime != lastTick) {
            lastTick = player.world.totalWorldTime
            when {
                cooldown < 0 -> cooldown = 0
                cooldown > 0 -> cooldown--
                else -> {
                    val diff = Config.AutoPilot.autoPilotHeight - player.posY
                    val should = if (ElytraFly.canFly()) diff > 0.25 && diff < 1.0 else ms < Config.AutoPilot.autoPilotMinSpeed

                    // Ignorar si el jugador no tiene cohetes en la mano o ya viaja rapido
                    if (player.heldItemMainhand.item == Items.FIREWORKS && should) {
                        // Lanzar cohete
                        player.connection.sendPacket(CPacketPlayerTryUseItem(EnumHand.MAIN_HAND))
                        cooldown = Config.AutoPilot.autoUseRocketsDelay

                        if (Config.ElytraFly.enable) ElytraFly.autoPilotAscending = 20
                    }
                }
            }
        }
    }

    private fun setPlayerPitch(player: EntityPlayerSP) {

        // Keep height
        val horizSpeed = sqrt(player.motionX * player.motionX + player.motionZ * player.motionZ)
        val sp = MathHelper.clamp(horizSpeed / 1.7, 0.0, 1.0)
        val percent0 = 1 - sqrt(sp)
        val minAngle = 0.6

        if (sp < 0.5) {
            return
        }

        val pitch = -((45 - minAngle) * percent0 + minAngle)

        // Move to correct height
        val diff = (Config.AutoPilot.autoPilotHeight + 1 - player.posY) * 2
        val percent1 = MathHelper.clamp(diff.absoluteValue, 0.0, 1.0)
        val dst = -Math.toDegrees(atan2(diff.absoluteValue, horizSpeed * 30.0)) * sign(diff)

        // Combine and apply
        val adjust = (dst - pitch) * percent1

        player.rotationPitch = pitch.toFloat()
        player.rotationPitch += adjust.toFloat()
        player.prevRotationPitch = player.rotationPitch
    }
}