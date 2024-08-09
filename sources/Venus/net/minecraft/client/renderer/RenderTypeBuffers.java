/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.SortedMap;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.OutlineLayerBuffer;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.util.Util;

public class RenderTypeBuffers {
    private final RegionRenderCacheBuilder fixedBuilder = new RegionRenderCacheBuilder();
    private final SortedMap<RenderType, BufferBuilder> fixedBuffers = Util.make(new Object2ObjectLinkedOpenHashMap(), this::lambda$new$1);
    private final IRenderTypeBuffer.Impl bufferSource = IRenderTypeBuffer.getImpl(this.fixedBuffers, new BufferBuilder(256));
    private final IRenderTypeBuffer.Impl crumblingBufferSource = IRenderTypeBuffer.getImpl(new BufferBuilder(256));
    private final OutlineLayerBuffer outlineBufferSource = new OutlineLayerBuffer(this.bufferSource);

    private static void put(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> object2ObjectLinkedOpenHashMap, RenderType renderType) {
        object2ObjectLinkedOpenHashMap.put(renderType, new BufferBuilder(renderType.getBufferSize()));
    }

    public RegionRenderCacheBuilder getFixedBuilder() {
        return this.fixedBuilder;
    }

    public IRenderTypeBuffer.Impl getBufferSource() {
        return this.bufferSource;
    }

    public IRenderTypeBuffer.Impl getCrumblingBufferSource() {
        return this.crumblingBufferSource;
    }

    public OutlineLayerBuffer getOutlineBufferSource() {
        return this.outlineBufferSource;
    }

    private void lambda$new$1(Object2ObjectLinkedOpenHashMap object2ObjectLinkedOpenHashMap) {
        object2ObjectLinkedOpenHashMap.put(Atlases.getSolidBlockType(), this.fixedBuilder.getBuilder(RenderType.getSolid()));
        object2ObjectLinkedOpenHashMap.put(Atlases.getCutoutBlockType(), this.fixedBuilder.getBuilder(RenderType.getCutout()));
        object2ObjectLinkedOpenHashMap.put(Atlases.getBannerType(), this.fixedBuilder.getBuilder(RenderType.getCutoutMipped()));
        object2ObjectLinkedOpenHashMap.put(Atlases.getTranslucentCullBlockType(), this.fixedBuilder.getBuilder(RenderType.getTranslucent()));
        RenderTypeBuffers.put(object2ObjectLinkedOpenHashMap, Atlases.getShieldType());
        RenderTypeBuffers.put(object2ObjectLinkedOpenHashMap, Atlases.getBedType());
        RenderTypeBuffers.put(object2ObjectLinkedOpenHashMap, Atlases.getShulkerBoxType());
        RenderTypeBuffers.put(object2ObjectLinkedOpenHashMap, Atlases.getSignType());
        RenderTypeBuffers.put(object2ObjectLinkedOpenHashMap, Atlases.getChestType());
        RenderTypeBuffers.put(object2ObjectLinkedOpenHashMap, RenderType.getTranslucentNoCrumbling());
        RenderTypeBuffers.put(object2ObjectLinkedOpenHashMap, RenderType.getArmorGlint());
        RenderTypeBuffers.put(object2ObjectLinkedOpenHashMap, RenderType.getArmorEntityGlint());
        RenderTypeBuffers.put(object2ObjectLinkedOpenHashMap, RenderType.getGlint());
        RenderTypeBuffers.put(object2ObjectLinkedOpenHashMap, RenderType.getGlintDirect());
        RenderTypeBuffers.put(object2ObjectLinkedOpenHashMap, RenderType.getGlintTranslucent());
        RenderTypeBuffers.put(object2ObjectLinkedOpenHashMap, RenderType.getEntityGlint());
        RenderTypeBuffers.put(object2ObjectLinkedOpenHashMap, RenderType.getEntityGlintDirect());
        RenderTypeBuffers.put(object2ObjectLinkedOpenHashMap, RenderType.getWaterMask());
        ModelBakery.DESTROY_RENDER_TYPES.forEach(arg_0 -> RenderTypeBuffers.lambda$new$0(object2ObjectLinkedOpenHashMap, arg_0));
    }

    private static void lambda$new$0(Object2ObjectLinkedOpenHashMap object2ObjectLinkedOpenHashMap, RenderType renderType) {
        RenderTypeBuffers.put(object2ObjectLinkedOpenHashMap, renderType);
    }
}

