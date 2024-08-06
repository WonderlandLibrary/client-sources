package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.events.Bus;
import com.shroomclient.shroomclientnextgen.events.impl.ChunkLoadEvent;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.UpgradeData;
import net.minecraft.world.gen.chunk.BlendingData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Chunk.class)
public class ChunkMixin {

    @Inject(method = { "<init>" }, at = @At("TAIL"))
    private void init(
        ChunkPos pos,
        UpgradeData upgradeData,
        HeightLimitView heightLimitView,
        Registry biomeRegistry,
        long inhabitedTime,
        ChunkSection[] sectionArray,
        BlendingData blendingData,
        CallbackInfo ci
    ) {
        Bus.post(new ChunkLoadEvent(pos));
    }
}
