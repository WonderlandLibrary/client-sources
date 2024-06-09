package com.cout970.fira.modules

import com.cout970.fira.Config
import com.cout970.fira.util.Utils
import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.gui.GuiIngame
import net.minecraft.client.resources.I18n
import net.minecraft.init.Items
import net.minecraft.inventory.ClickType
import net.minecraft.util.math.MathHelper
import net.minecraft.util.text.TextFormatting
import org.lwjgl.input.Keyboard
import kotlin.math.min
import kotlin.math.sqrt

object ElytraFly {

    // Número de ticks a dejar caer para que el piloto automatico suba a la posicion adecuada
    var autoPilotAscending = 0

    fun hud(): String {
        val player = Minecraft.getMinecraft().player ?: return ""
        val elytra = player.inventory.armorInventory[2]

        return "ElytraFly ${elytra.maxDamage - elytra.itemDamage}"
    }

    fun preElytraTravelHook(entity: Any?): Boolean {
        return if (entity is EntityPlayerSP) {
            if (Config.ElytraFly.enable) Config.ElytraFly.moveFacing else true
        } else {
            true
        }
    }

    fun elytraTravelHook(entity: Any?) {
        if (entity is EntityPlayerSP) {
            onPlayerPreElytraTravel(entity)
        }
    }

    fun onPlayerPreElytraTravel(player: EntityPlayerSP) {

        if (!canFly()) return

        val screen = Minecraft.getMinecraft().currentScreen
        val inMenu = screen != null && screen.doesGuiPauseGame()
        val allowDescend = !Config.ElytraFly.lockControls && (screen == null || screen is GuiIngame) && Utils.mc.player == Utils.mc.renderViewEntity
        val vanillaFly = Config.ElytraFly.elytraFlyControl.isActive()

        if (Config.ElytraTweaks.swapElytras && Config.ElytraTweaks.swapElytraByTime > 0 && player.world.totalWorldTime % Config.ElytraTweaks.swapElytraByTime == 0L) {
            swapElytras()
        }

        val forward = when {
            inMenu -> 0.0
            !vanillaFly && Config.AutoFly.enable -> 1.0
            !vanillaFly && player.movementInput.forwardKeyDown -> 1.0
            !vanillaFly && player.movementInput.backKeyDown -> -1.0
            else -> 0.0
        }

        val side = when {
            inMenu -> 0.0
            player.movementInput.leftKeyDown -> 1.0
            player.movementInput.rightKeyDown -> -1.0
            else -> 0.0
        }

        if (Config.Debug.debugMode) {
            val speed = when (player.world.totalWorldTime % 9) {
                0L, 1L, 2L -> 0.0
                3L, 4L, 5L -> 36 / 20.0
                else -> -36 / 20.0
            }

            val f1 = MathHelper.sin(player.rotationYaw * 0.017453292f)
            val f2 = MathHelper.cos(player.rotationYaw * 0.017453292f)
            player.motionX = (speed * f2 - 0 * f1)
            player.motionZ = (0 * f2 + speed * f1)

            val newMotion = sqrt(player.motionX * player.motionX + player.motionZ * player.motionZ)
            val max = Config.ElytraFly.movementSpeed / 20.0
            // Mantener el limite en diagonales
            if (newMotion > max) {
                player.motionX *= max / newMotion
                player.motionZ *= max / newMotion
            }
        } else {
            if (forward != 0.0 || side != 0.0) {
                val max = Config.ElytraFly.movementSpeed / 20.0
                val mult = (Config.ElytraFly.accelerationSpeed / 20.0).coerceIn(0.0, max)

                val f1 = MathHelper.sin(player.rotationYaw * 0.017453292f)
                val f2 = MathHelper.cos(player.rotationYaw * 0.017453292f)
                val dirX = (side * f2 - forward * f1)
                val dirZ = (forward * f2 + side * f1)

                player.motionX = min(max, player.motionX + mult * dirX)
                player.motionZ = min(max, player.motionZ + mult * dirZ)

                val newMotion = sqrt(player.motionX * player.motionX + player.motionZ * player.motionZ)

                // Mantener el limite en diagonales
                if (newMotion > max) {
                    player.motionX *= max / newMotion
                    player.motionZ *= max / newMotion
                }
            } else {
                if (!Config.ElytraFly.keepInertia || Config.ElytraFly.elytraFlyStop.isActive()) {
                    player.motionX = 0.0
                    player.motionZ = 0.0
                } else if (!vanillaFly) {
                    player.motionX -= player.motionX * Config.ElytraFly.airDrag
                    player.motionZ -= player.motionZ * Config.ElytraFly.airDrag
                }
            }
        }

        player.motionY = when {
            // Debug mode
            Config.Debug.debugMode -> if (player.world.totalWorldTime % 9 == 1L) 1.0 else -0.0002
            // En un menu
            inMenu -> -Config.ElytraFly.elytraFlyFall.toDouble()
            // En chat
            !allowDescend -> -Config.ElytraFly.elytraFlyFall.toDouble()
            // Slow fall
            Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) -> -0.025
            // Faster fall
            Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) -> -0.5
            // Normal fly
            vanillaFly || autoPilotAscending > 0 -> player.motionY
            // Base fall speed
            else -> -Config.ElytraFly.elytraFlyFall.toDouble()
        }

        if (autoPilotAscending > 0) {
            autoPilotAscending--
        }
    }

    fun canFly(): Boolean {
        if (!Config.ElytraFly.enable) return false

        val player = Minecraft.getMinecraft().player ?: return false

        // Ignorar si el jugador no tiene elytra
        if (!hasElytra(player)) return false

        // Deshabilita cuando se acaba la durabilidad de las elytras
        if (!hasChestplateDurability(player, Config.ElytraTweaks.elytraMinDurability)) {
            if (Config.ElytraTweaks.swapElytras) swapElytras()

            Utils.withCooldown("swapElytrasMsg", 20) {
                Utils.runLater(20) {
                    if (!hasChestplateDurability(player, Config.ElytraTweaks.elytraMinDurability)) {
                        Utils.chatMsg("${TextFormatting.RED}${I18n.format("text.fira_client.elytra_fly.warning1")}")
                        Utils.ping()

                        Config.ElytraFly.enable = false
                    }
                }
            }

            return false
        }

        return true
    }

    fun hasElytra(player: EntityPlayerSP): Boolean {
        val chestplate = player.inventory.armorInventory[2]
        return !chestplate.isEmpty && chestplate.item == Items.ELYTRA
    }

    private fun hasChestplateDurability(player: EntityPlayerSP, amount: Int): Boolean {
        val chestplate = player.inventory.armorInventory[2]
        return !chestplate.isEmpty && chestplate.itemDamage < chestplate.maxDamage - amount
    }

    private fun swapElytras() {
        val p = Utils.mc.player ?: return
        val options = mutableListOf<Pair<Int, Int>>()

        repeat(9 * 3) { slot ->
            val stack = p.inventory.mainInventory[9 + slot]
            if (!stack.isEmpty && stack.item == Items.ELYTRA && (stack.maxDamage - stack.itemDamage) > Config.ElytraTweaks.elytraMinDurability) {
                options += (9 + slot) to (stack.maxDamage - stack.itemDamage)
            }
        }

        repeat(9) { slot ->
            val stack = p.inventory.mainInventory[slot]
            if (!stack.isEmpty && stack.item == Items.ELYTRA && (stack.maxDamage - stack.itemDamage) > Config.ElytraTweaks.elytraMinDurability) {
                options += (36 + slot) to (stack.maxDamage - stack.itemDamage)
            }
        }

        if (options.isEmpty()) return
        val slot = options.maxBy { it.second }?.first ?: return

        Utils.mc.playerController
                .windowClick(p.inventoryContainer.windowId, 6, 0, ClickType.QUICK_MOVE, p)

        Utils.mc.playerController
                .windowClick(p.inventoryContainer.windowId, slot, 0, ClickType.QUICK_MOVE, p)
    }
}