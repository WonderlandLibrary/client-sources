/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LlamaSpitModel<T extends Entity>
extends SegmentedModel<T> {
    private final ModelRenderer main = new ModelRenderer(this);

    public LlamaSpitModel() {
        this(0.0f);
    }

    public LlamaSpitModel(float f) {
        int n = 2;
        this.main.setTextureOffset(0, 0).addBox(-4.0f, 0.0f, 0.0f, 2.0f, 2.0f, 2.0f, f);
        this.main.setTextureOffset(0, 0).addBox(0.0f, -4.0f, 0.0f, 2.0f, 2.0f, 2.0f, f);
        this.main.setTextureOffset(0, 0).addBox(0.0f, 0.0f, -4.0f, 2.0f, 2.0f, 2.0f, f);
        this.main.setTextureOffset(0, 0).addBox(0.0f, 0.0f, 0.0f, 2.0f, 2.0f, 2.0f, f);
        this.main.setTextureOffset(0, 0).addBox(2.0f, 0.0f, 0.0f, 2.0f, 2.0f, 2.0f, f);
        this.main.setTextureOffset(0, 0).addBox(0.0f, 2.0f, 0.0f, 2.0f, 2.0f, 2.0f, f);
        this.main.setTextureOffset(0, 0).addBox(0.0f, 0.0f, 2.0f, 2.0f, 2.0f, 2.0f, f);
        this.main.setRotationPoint(0.0f, 0.0f, 0.0f);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.main);
    }
}

