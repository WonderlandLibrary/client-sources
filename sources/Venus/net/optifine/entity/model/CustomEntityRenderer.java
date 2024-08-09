/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.util.ResourceLocation;
import net.optifine.entity.model.CustomModelRenderer;

public class CustomEntityRenderer {
    private String name = null;
    private String basePath = null;
    private ResourceLocation textureLocation = null;
    private CustomModelRenderer[] customModelRenderers = null;
    private float shadowSize = 0.0f;

    public CustomEntityRenderer(String string, String string2, ResourceLocation resourceLocation, CustomModelRenderer[] customModelRendererArray, float f) {
        this.name = string;
        this.basePath = string2;
        this.textureLocation = resourceLocation;
        this.customModelRenderers = customModelRendererArray;
        this.shadowSize = f;
    }

    public String getName() {
        return this.name;
    }

    public String getBasePath() {
        return this.basePath;
    }

    public ResourceLocation getTextureLocation() {
        return this.textureLocation;
    }

    public CustomModelRenderer[] getCustomModelRenderers() {
        return this.customModelRenderers;
    }

    public float getShadowSize() {
        return this.shadowSize;
    }
}

