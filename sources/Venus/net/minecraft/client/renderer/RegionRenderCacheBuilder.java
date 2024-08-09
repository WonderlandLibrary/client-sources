/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderType;

public class RegionRenderCacheBuilder {
    private final Map<RenderType, BufferBuilder> builders = RenderType.getBlockRenderTypes().stream().collect(Collectors.toMap(RegionRenderCacheBuilder::lambda$new$0, RegionRenderCacheBuilder::lambda$new$1));

    public BufferBuilder getBuilder(RenderType renderType) {
        return this.builders.get(renderType);
    }

    public void resetBuilders() {
        this.builders.values().forEach(BufferBuilder::reset);
    }

    public void discardBuilders() {
        this.builders.values().forEach(BufferBuilder::discard);
    }

    private static BufferBuilder lambda$new$1(RenderType renderType) {
        return new BufferBuilder(renderType.getBufferSize());
    }

    private static RenderType lambda$new$0(RenderType renderType) {
        return renderType;
    }
}

