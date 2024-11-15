// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.mixin;

import net.minecraft.client.render.BuiltChunkStorage;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({WorldRenderer.class})
public interface WorldRendererAccessor {
    @Accessor("chunks")
    BuiltChunkStorage getChunks();

    @Accessor
    Frustum getFrustum();

    @Accessor
    void setFrustum(final Frustum p0);
}
