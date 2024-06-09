package com.client.glowclient.sponge.mixin;

import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.client.glowclient.sponge.mixinutils.*;
import org.spongepowered.asm.mixin.injection.*;

@SideOnly(Side.CLIENT)
@Mixin({ ChunkRenderContainer.class })
public abstract class MixinChunkRenderContainer implements IBlockAccess
{
    public MixinChunkRenderContainer() {
        super();
    }
    
    @Inject(method = { "addRenderChunk" }, at = { @At("HEAD") }, cancellable = true)
    public void preAddRenderChunk(final RenderChunk renderChunk, final BlockRenderLayer blockRenderLayer, final CallbackInfo callbackInfo) {
        HookTranslator.m2(renderChunk, blockRenderLayer);
    }
}
