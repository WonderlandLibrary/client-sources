package com.client.glowclient.sponge.mixin;

import net.minecraft.network.play.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.network.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import com.client.glowclient.sponge.mixinutils.*;
import net.minecraft.nbt.*;
import net.minecraft.util.math.*;
import net.minecraft.world.chunk.*;
import java.util.*;
import net.minecraft.tileentity.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.network.play.server.*;

@SideOnly(Side.CLIENT)
@Mixin({ NetHandlerPlayClient.class })
public abstract class MixinNetHandlerPlayClient implements INetHandlerPlayClient
{
    private Minecraft mc;
    @Shadow
    private Minecraft field_147299_f;
    @Shadow
    private WorldClient field_147300_g;
    
    public MixinNetHandlerPlayClient() {
        super();
    }
    
    @Overwrite
    public void handleChunkData(final SPacketChunkData sPacketChunkData) {
        PacketThreadUtil.checkThreadAndEnqueue((Packet)sPacketChunkData, (INetHandler)NetHandlerPlayClient.class.cast(this), (IThreadListener)this.client);
        if (sPacketChunkData.isFullChunk()) {
            this.world.doPreChunk(sPacketChunkData.getChunkX(), sPacketChunkData.getChunkZ(), true);
        }
        this.world.invalidateBlockReceiveRegion(sPacketChunkData.getChunkX() << 4, 0, sPacketChunkData.getChunkZ() << 4, (sPacketChunkData.getChunkX() << 4) + 15, 256, (sPacketChunkData.getChunkZ() << 4) + 15);
        final Chunk chunk = this.world.getChunk(sPacketChunkData.getChunkX(), sPacketChunkData.getChunkZ());
        chunk.read(sPacketChunkData.getReadBuffer(), sPacketChunkData.getExtractedSize(), sPacketChunkData.isFullChunk());
        this.world.markBlockRangeForRenderUpdate(sPacketChunkData.getChunkX() << 4, 0, sPacketChunkData.getChunkZ() << 4, (sPacketChunkData.getChunkX() << 4) + 15, 256, (sPacketChunkData.getChunkZ() << 4) + 15);
        if (!sPacketChunkData.isFullChunk() || this.world.provider.shouldClientCheckLighting()) {
            chunk.resetRelightChecks();
        }
        if (!sPacketChunkData.isFullChunk()) {
            HookTranslator.m36(chunk);
        }
        for (final NBTTagCompound nbtTagCompound : sPacketChunkData.getTileEntityTags()) {
            final TileEntity tileEntity = this.world.getTileEntity(new BlockPos(nbtTagCompound.getInteger("x"), nbtTagCompound.getInteger("y"), nbtTagCompound.getInteger("z")));
            if (tileEntity != null) {
                tileEntity.handleUpdateTag(nbtTagCompound);
            }
        }
    }
    
    @Inject(method = { "handleChat" }, at = { @At("RETURN") }, cancellable = true)
    public void postHandleChat(final SPacketChat sPacketChat, final CallbackInfo callbackInfo) {
        HookTranslator.m37(NetHandlerPlayClient.class.cast(this), sPacketChat);
    }
    
    @Inject(method = { "handleBlockAction" }, at = { @At("RETURN") }, cancellable = true)
    public void postHandleBlockAction(final SPacketBlockAction sPacketBlockAction, final CallbackInfo callbackInfo) {
        HookTranslator.m38(NetHandlerPlayClient.class.cast(this), sPacketBlockAction);
    }
    
    @Inject(method = { "handleMaps" }, at = { @At("RETURN") }, cancellable = true)
    public void postHandleMaps(final SPacketMaps sPacketMaps, final CallbackInfo callbackInfo) {
        HookTranslator.m39(NetHandlerPlayClient.class.cast(this), sPacketMaps);
    }
    
    @Inject(method = { "handleCustomPayload" }, at = { @At("RETURN") }, cancellable = true)
    public void postHandleCustomPayload(final SPacketCustomPayload sPacketCustomPayload, final CallbackInfo callbackInfo) {
        HookTranslator.m40(NetHandlerPlayClient.class.cast(this), sPacketCustomPayload);
    }
}
