/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.optifine.entity.model.anim.ModelUpdater;

public class CustomModelRenderer {
    private String modelPart;
    private boolean attach;
    private ModelRenderer modelRenderer;
    private ModelUpdater modelUpdater;

    public CustomModelRenderer(String string, boolean bl, ModelRenderer modelRenderer, ModelUpdater modelUpdater) {
        this.modelPart = string;
        this.attach = bl;
        this.modelRenderer = modelRenderer;
        this.modelUpdater = modelUpdater;
    }

    public ModelRenderer getModelRenderer() {
        return this.modelRenderer;
    }

    public String getModelPart() {
        return this.modelPart;
    }

    public boolean isAttach() {
        return this.attach;
    }

    public ModelUpdater getModelUpdater() {
        return this.modelUpdater;
    }
}

