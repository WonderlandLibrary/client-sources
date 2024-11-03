package net.silentclient.client.mixin.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S48PacketResourcePackSend;
import net.minecraft.util.IChatComponent;
import net.silentclient.client.event.impl.EntityDamageEvent;
import net.silentclient.client.hooks.NetHandlerPlayClientHook;
import net.silentclient.client.utils.Players;
import net.silentclient.client.utils.WorldListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(NetHandlerPlayClient.class)
public abstract class NetHandlerPlayClientMixin {
    @Shadow private WorldClient clientWorldController;
    @Shadow
    public abstract NetworkPlayerInfo getPlayerInfo(UUID p_175102_1_);

    @Inject(
            method = "handleSpawnPlayer",
            cancellable = true,
            at =
            @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V",
                    shift = At.Shift.AFTER
            ))
    private void patcher$fixHandleSpawnPlayerNPE(S0CPacketSpawnPlayer packetIn, CallbackInfo ci) {
        if (this.getPlayerInfo(packetIn.getPlayer()) == null) {
            ci.cancel();
        }
    }

    @Inject(method = "handleEntityStatus", at = @At("RETURN"))
    public void callEntityDamageEvent(S19PacketEntityStatus packetIn, CallbackInfo ci) {
        if(packetIn.getOpCode() == 2) {
            EntityDamageEvent event = new EntityDamageEvent(packetIn.getEntity(clientWorldController));
            event.call();
        }
    }

    @ModifyArg(
            method = {"handleJoinGame", "handleRespawn"},
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V")
    )
    private GuiScreen skipTerrainScreen(GuiScreen original) {
        return null;
    }

    @Inject(method = "handleJoinGame", at = @At(value = "RETURN"))
    public void resetPlayers(S01PacketJoinGame packetIn, CallbackInfo ci) {
        Players.reload();
        WorldListener.onWorldSwitch();
    }

    //#if MC==10809
    @Inject(method = "handleResourcePack", at = @At("HEAD"), cancellable = true)
    private void silent$resourceExploitFix(S48PacketResourcePackSend packetIn, CallbackInfo ci) {
        if (!NetHandlerPlayClientHook.validateResourcePackUrl((NetHandlerPlayClient) (Object) this, packetIn)) {
            ci.cancel();
        }
    }
    @Redirect(
            method = "handleUpdateSign",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=Unable to locate sign at ", ordinal = 0)),
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;addChatMessage(Lnet/minecraft/util/IChatComponent;)V", ordinal = 0)
    )
    private void silent$removeDebugMessage(EntityPlayerSP instance, IChatComponent component) {
        // No-op
    }
    //#endif
}
