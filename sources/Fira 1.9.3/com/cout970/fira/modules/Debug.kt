package com.cout970.fira.modules

import com.cout970.fira.Config
import com.cout970.fira.Log
import com.cout970.fira.Manager
import com.cout970.fira.util.Utils
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.util.concurrent.Future
import io.netty.util.concurrent.GenericFutureListener
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.network.NetHandlerPlayClient
import net.minecraft.entity.passive.EntityDonkey
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.network.EnumConnectionState
import net.minecraft.network.INetHandler
import net.minecraft.network.NetworkManager
import net.minecraft.network.Packet
import net.minecraft.network.play.client.CPacketClickWindow
import net.minecraft.network.play.client.CPacketCreativeInventoryAction
import net.minecraft.network.play.client.CPacketEntityAction
import net.minecraft.network.play.client.CPacketPlayer
import net.minecraft.util.text.ITextComponent
import net.minecraftforge.client.event.InputUpdateEvent
import net.minecraftforge.client.event.RenderPlayerEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import java.net.SocketAddress
import javax.crypto.SecretKey

object Debug {

    var counter = 0

    @SubscribeEvent
    fun onTick(e: TickEvent.PlayerTickEvent) {
        if (!Config.Debug.packetFlood) return
        if (e.phase != TickEvent.Phase.START) return
    }

    @SubscribeEvent
    fun playerRender(e: RenderPlayerEvent.Post) {
        if (Config.Debug.logNetworkPackets) {
            bindPlayerNetworkManager(e.entityPlayer)
        }
    }

    private fun bindPlayerNetworkManager(entityPlayer: EntityPlayer) {
        val player = entityPlayer as? EntityPlayerSP ?: return

        if (player.connection.networkManager !is FixerNetworkManager) {
            val wrapper = FixerNetworkManager(player.connection.networkManager)
            val fields = NetHandlerPlayClient::class.java.declaredFields

            fields.forEach { field ->
                if (field.type == NetworkManager::class.java) {
                    field.isAccessible = true
                    field.set(player.connection, wrapper)
                }
            }
        }
    }

    class FixerNetworkManager(private val parent: NetworkManager) : NetworkManager(parent.direction) {

        override fun sendPacket(packetIn: Packet<*>) {
            if (Config.Debug.logNetworkPackets) {
                if (!Config.Debug.filterMotionPackets || packetIn !is CPacketPlayer) {
                    val clazz = packetIn.javaClass.simpleName

                    try {
                        val p = packetIn
                        val json = when (p) {
                            is CPacketPlayer.Position -> "{x: ${p.getX(0.0)}, y: ${p.getY(0.0)}, z: ${p.getZ(0.0)}, onGround: ${p.isOnGround}}"
                            is CPacketPlayer.PositionRotation -> "{x: ${p.getX(0.0)}, y: ${p.getY(0.0)}, z: ${p.getZ(0.0)}, yaw: ${p.getYaw(0f)}, pitch: ${p.getPitch(0f)}, onGround: ${p.isOnGround}}"
                            is CPacketPlayer.Rotation -> "{yaw: ${p.getYaw(0f)}, pitch: ${p.getPitch(0f)}, onGround: ${p.isOnGround}}"
                            is CPacketEntityAction -> "{action: ${p.action}, auxData: ${p.auxData}}"
                            is CPacketClickWindow -> "{windowId: ${p.windowId}, slotId: ${p.slotId}, actionNumber: ${p.actionNumber}, clickedItem: ${p.clickedItem}, mode: ${p.clickType}, usedButton: ${p.usedButton}}"
                            is CPacketCreativeInventoryAction -> ""
                            else -> Manager.GSON.toJson(packetIn)
                        }

                        Log.info("$clazz $json")
                    } catch (e: Throwable) {
                        Log.error("Unable to stringify $clazz")
                    }
                }
            }
            parent.sendPacket(packetIn)
        }

        override fun channelActive(p_channelActive_1_: ChannelHandlerContext) {
            parent.channelActive(p_channelActive_1_)
        }

        override fun setConnectionState(newState: EnumConnectionState) {
            parent.setConnectionState(newState)
        }

        override fun channelInactive(p_channelInactive_1_: ChannelHandlerContext) {
            parent.channelInactive(p_channelInactive_1_)
        }

        @Suppress("DEPRECATION")
        override fun exceptionCaught(p_exceptionCaught_1_: ChannelHandlerContext, p_exceptionCaught_2_: Throwable) {
            parent.exceptionCaught(p_exceptionCaught_1_, p_exceptionCaught_2_)
        }

        override fun setNetHandler(handler: INetHandler) {
            parent.netHandler = handler
        }

        override fun sendPacket(packetIn: Packet<*>, listener: GenericFutureListener<out Future<in Void>>, vararg listeners: GenericFutureListener<out Future<in Void>>?) {
            parent.sendPacket(packetIn, listener, *listeners)
        }

        override fun processReceivedPackets() {
            parent.processReceivedPackets()
        }

        override fun getRemoteAddress(): SocketAddress {
            return parent.remoteAddress
        }

        override fun closeChannel(message: ITextComponent) {
            parent.closeChannel(message)
        }

        override fun isLocalChannel(): Boolean {
            return parent.isLocalChannel
        }

        override fun enableEncryption(key: SecretKey) {
            parent.enableEncryption(key)
        }

        override fun isEncrypted(): Boolean {
            return parent.isEncrypted
        }


        override fun isChannelOpen(): Boolean {
            return parent.isChannelOpen
        }

        override fun hasNoChannel(): Boolean {
            return parent.hasNoChannel()
        }

        override fun getNetHandler(): INetHandler {
            return parent.netHandler
        }

        override fun getExitMessage(): ITextComponent {
            return parent.exitMessage
        }

        override fun disableAutoRead() {
            parent.disableAutoRead()
        }

        override fun setCompressionThreshold(threshold: Int) {
            parent.setCompressionThreshold(threshold)
        }

        override fun checkDisconnected() {
            parent.checkDisconnected()
        }

        override fun channel(): Channel {
            return parent.channel()
        }
    }
}


