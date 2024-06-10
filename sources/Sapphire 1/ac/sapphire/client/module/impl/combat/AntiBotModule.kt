package ac.sapphire.client.module.impl.combat

import ac.sapphire.client.event.annotation.Subscribe
import ac.sapphire.client.event.events.network.PacketReceiveEvent
import ac.sapphire.client.ext.mc
import ac.sapphire.client.ext.player
import ac.sapphire.client.ext.requireMixin
import ac.sapphire.client.mixin.gui.IPlayerTabOverlayAccessor
import ac.sapphire.client.mixin.network.INetHandlerPlayClientAccessor
import ac.sapphire.client.module.AbstractModule
import ac.sapphire.client.module.ModuleCategory
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.network.play.server.S18PacketEntityTeleport
import kotlin.contracts.ExperimentalContracts

class AntiBotModule : AbstractModule("antibot", "AntiBot", "", ModuleCategory.COMBAT) {
    @Subscribe
    @ExperimentalContracts
    fun onPacketRecv(event: PacketReceiveEvent) {
        if (event.packet is S18PacketEntityTeleport) {
            val entity = mc.theWorld.getEntityByID(event.packet.entityId)
            if (entity is EntityPlayer && entity.isInvisible && entity.ticksExisted > 3 && mc.theWorld.playerEntities.contains(
                    entity
                ) && !isInTabList(entity)
            ) {
                mc.theWorld.removeEntity(entity)
            }
        }
    }

    @ExperimentalContracts
    private fun isInTabList(entityPlayer: EntityPlayer): Boolean {
        val tab = mc.ingameGUI.tabList
        requireMixin<IPlayerTabOverlayAccessor>(tab)

        val orderedInfos = tab.field_175252_a ?: return false
        val playerInfos = orderedInfos.sortedCopy(player.sendQueue.playerInfoMap)

        val netHandler = player.sendQueue
        requireMixin<INetHandlerPlayClientAccessor>(netHandler)

        return playerInfos.contains(netHandler.playerInfoMap()[entityPlayer.gameProfile.id])
    }
}