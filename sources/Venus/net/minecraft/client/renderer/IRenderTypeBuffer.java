/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ResourceLocation;
import net.optifine.render.VertexBuilderDummy;
import net.optifine.util.TextureUtils;

public interface IRenderTypeBuffer {
    public static Impl getImpl(BufferBuilder bufferBuilder) {
        return IRenderTypeBuffer.getImpl(ImmutableMap.of(), bufferBuilder);
    }

    public static Impl getImpl(Map<RenderType, BufferBuilder> map, BufferBuilder bufferBuilder) {
        return new Impl(bufferBuilder, map);
    }

    public IVertexBuilder getBuffer(RenderType var1);

    default public void flushRenderBuffers() {
    }

    public static class Impl
    implements IRenderTypeBuffer {
        protected final BufferBuilder buffer;
        protected final Map<RenderType, BufferBuilder> fixedBuffers;
        protected RenderType lastRenderType = null;
        protected final Set<BufferBuilder> startedBuffers = Sets.newIdentityHashSet();
        private final IVertexBuilder DUMMY_BUFFER = new VertexBuilderDummy(this);

        protected Impl(BufferBuilder bufferBuilder, Map<RenderType, BufferBuilder> map) {
            this.buffer = bufferBuilder;
            this.fixedBuffers = map;
            this.buffer.setRenderTypeBuffer(this);
            for (BufferBuilder bufferBuilder2 : map.values()) {
                bufferBuilder2.setRenderTypeBuffer(this);
            }
        }

        @Override
        public IVertexBuilder getBuffer(RenderType renderType) {
            BufferBuilder bufferBuilder = this.getBufferRaw(renderType);
            if (!Objects.equals(this.lastRenderType, renderType)) {
                RenderType renderType2;
                if (this.lastRenderType != null && !this.fixedBuffers.containsKey(renderType2 = this.lastRenderType)) {
                    this.finish(renderType2);
                }
                if (this.startedBuffers.add(bufferBuilder)) {
                    bufferBuilder.setRenderType(renderType);
                    bufferBuilder.begin(renderType.getDrawMode(), renderType.getVertexFormat());
                }
                this.lastRenderType = renderType;
            }
            return renderType.getTextureLocation() == TextureUtils.LOCATION_TEXTURE_EMPTY ? this.DUMMY_BUFFER : bufferBuilder;
        }

        private BufferBuilder getBufferRaw(RenderType renderType) {
            return this.fixedBuffers.getOrDefault(renderType, this.buffer);
        }

        public void finish() {
            if (!this.startedBuffers.isEmpty()) {
                Object object;
                if (this.lastRenderType != null && (object = this.getBuffer(this.lastRenderType)) == this.buffer) {
                    this.finish(this.lastRenderType);
                }
                if (!this.startedBuffers.isEmpty()) {
                    for (RenderType renderType : this.fixedBuffers.keySet()) {
                        this.finish(renderType);
                        if (!this.startedBuffers.isEmpty()) continue;
                        break;
                    }
                }
            }
        }

        public void finish(RenderType renderType) {
            BufferBuilder bufferBuilder = this.getBufferRaw(renderType);
            boolean bl = Objects.equals(this.lastRenderType, renderType);
            if ((bl || bufferBuilder != this.buffer) && this.startedBuffers.remove(bufferBuilder)) {
                renderType.finish(bufferBuilder, 0, 0, 0);
                if (bl) {
                    this.lastRenderType = null;
                }
            }
        }

        public IVertexBuilder getBuffer(ResourceLocation resourceLocation, IVertexBuilder iVertexBuilder) {
            if (!(this.lastRenderType instanceof RenderType.Type)) {
                return iVertexBuilder;
            }
            resourceLocation = RenderType.getCustomTexture(resourceLocation);
            RenderType.Type type = (RenderType.Type)this.lastRenderType;
            RenderType.Type type2 = type.getTextured(resourceLocation);
            return this.getBuffer(type2);
        }

        public RenderType getLastRenderType() {
            return this.lastRenderType;
        }

        @Override
        public void flushRenderBuffers() {
            RenderType renderType = this.lastRenderType;
            this.finish();
            if (renderType != null) {
                this.getBuffer(renderType);
            }
        }

        public IVertexBuilder getDummyBuffer() {
            return this.DUMMY_BUFFER;
        }
    }
}

