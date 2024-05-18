package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.utils.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.network.play.server.*;
import wwe.*;

@Mixin(value = { NetHandlerPlayClient.class }, priority = 9999)
public class MixinNetHandlerPlayClient
{
    public MixinNetHandlerPlayClient() {
        super();
    }
    
    @Inject(method = { "handleChunkData" }, at = { @At("RETURN") }, cancellable = true)
    public void handleChunkData(final SPacketChunkData sPacketChunkData, final CallbackInfo callbackInfo) {
        hSNE.Seqo(new FCbU(sPacketChunkData.getChunkX(), sPacketChunkData.getChunkZ(), Wrapper.mc.world.getChunk(sPacketChunkData.getChunkX(), sPacketChunkData.getChunkZ()), sPacketChunkData));
    }
    
    @Inject(method = { "handleSpawnObject" }, at = { @At("RETURN") }, cancellable = true)
    public void handleSpawnObject(final SPacketSpawnObject sPacketSpawnObject, final CallbackInfo callbackInfo) {
        hSNE.Seqo(new QRuA(sPacketSpawnObject));
    }
}
