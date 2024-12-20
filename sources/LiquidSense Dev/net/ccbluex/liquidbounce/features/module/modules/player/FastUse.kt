/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.liquidbounce.features.module.modules.player

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.MoveEvent
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.timer.MSTimer
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue
import net.minecraft.client.settings.KeyBinding
import net.minecraft.item.ItemBucketMilk
import net.minecraft.item.ItemFood
import net.minecraft.item.ItemPotion
import net.minecraft.network.play.client.C03PacketPlayer
import net.minecraft.network.play.client.C07PacketPlayerDigging
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
import net.minecraft.network.play.client.C09PacketHeldItemChange
import net.minecraft.util.BlockPos
import net.minecraft.util.EnumFacing

@ModuleInfo(name = "FastUse", description = "Allows you to use items faster.", category = ModuleCategory.PLAYER)
class FastUse : Module() {

    private val modeValue = ListValue("Mode", arrayOf("Instant","Hypixel","NCP", "AAC", "CustomDelay"), "NCP")
    private val noMoveValue = BoolValue("NoMove", false)

    private val delayValue = IntegerValue("CustomDelay", 0, 0, 300)
    private val customSpeedValue = IntegerValue("CustomSpeed", 2, 1, 35)
    private val customTimer = FloatValue("CustomTimer", 1.1f, 0.5f, 2f)

    private val msTimer = MSTimer()
    private var usedTimer = false
    var aacDelay = false

    fun getModeValue(): ListValue? {
        return modeValue
    }

    fun shabi(){
        aacDelay = true;
    }

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        msTimer
        if (usedTimer) {
            mc.timer.timerSpeed = 1F
            usedTimer = false
        }

        if (!mc.thePlayer.isUsingItem) {
            msTimer.reset()
            return
        }

        if (modeValue.get().equals("AAC")){
            if (aacDelay) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true)
                if (msTimer.hasTimePassed(1000)){
                    aacDelay = false
                }
            } else {
                msTimer.reset()
            }
        }

        val usingItem = mc.thePlayer.itemInUse.item

        if (usingItem is ItemFood || usingItem is ItemBucketMilk || usingItem is ItemPotion) {
            when (modeValue.get().toLowerCase()) {
                "instant" -> {
                    repeat(35) {
                        mc.netHandler.addToSendQueue(C03PacketPlayer())
                    }

                    mc.playerController.onStoppedUsingItem(mc.thePlayer)
                }

                "hypixel" -> {
                    mc.thePlayer.sendQueue.getNetworkManager().sendPacket(C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem))
                    mc.thePlayer.sendQueue.getNetworkManager().sendPacket(C08PacketPlayerBlockPlacement(mc.thePlayer.itemInUse))
                    for (i in 0..40) {
                        mc.thePlayer.sendQueue.getNetworkManager().sendPacket(C03PacketPlayer(mc.thePlayer.onGround))
                    }
                    mc.thePlayer.sendQueue.getNetworkManager().sendPacket(C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN))
                    mc.thePlayer.sendQueue.getNetworkManager().sendPacket(C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem))
                }

                "ncp" -> if (mc.thePlayer.itemInUseDuration > 14) {
                    repeat(20) {
                        mc.netHandler.addToSendQueue(C03PacketPlayer())
                    }

                    mc.playerController.onStoppedUsingItem(mc.thePlayer)
                }

                "aac" -> {
                    if(msTimer.hasTimePassed(1000)){
                        mc.timer.timerSpeed = 1.22F
                        usedTimer = true
                    }
                }

                "customdelay" -> {
                    mc.timer.timerSpeed = customTimer.get()
                    usedTimer = true

                    if (!msTimer.hasTimePassed(delayValue.get().toLong()))
                        return

                    repeat(customSpeedValue.get()) {
                        mc.netHandler.addToSendQueue(C03PacketPlayer(mc.thePlayer.onGround))
                    }

                    msTimer.reset()
                }
            }
        }
    }

    @EventTarget
    fun onMove(event: MoveEvent?) {
        if (event == null) return

        if (!state || !mc.thePlayer.isUsingItem || !noMoveValue.get()) return
        val usingItem = mc.thePlayer.itemInUse.item
        if ((usingItem is ItemFood || usingItem is ItemBucketMilk || usingItem is ItemPotion))
            event.zero()
    }

    fun resetTimer(){
        if(msTimer.hasTimePassed(1000)){
            msTimer.reset()
        }
    }

    override fun onDisable() {
        if (usedTimer) {
            mc.timer.timerSpeed = 1F
            usedTimer = false
        }
        aacDelay = false
    }

    override val tag: String?
        get() = modeValue.get()
}