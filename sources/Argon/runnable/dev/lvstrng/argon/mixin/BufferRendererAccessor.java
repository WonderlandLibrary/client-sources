// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.mixin;

import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BufferRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({BufferRenderer.class})
public interface BufferRendererAccessor {
    /*@Accessor("currentVertexBuffer")
    default void setCurrentVertexBuffer(final VertexBuffer vertexBuffer) {
    }*/
}
