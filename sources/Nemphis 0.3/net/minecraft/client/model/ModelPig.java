/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;

public class ModelPig
extends ModelQuadruped {
    private static final String __OBFID = "CL_00000849";

    public ModelPig() {
        this(0.0f);
    }

    public ModelPig(float p_i1151_1_) {
        super(6, p_i1151_1_);
        this.head.setTextureOffset(16, 16).addBox(-2.0f, 0.0f, -9.0f, 4, 3, 1, p_i1151_1_);
        this.childYOffset = 4.0f;
    }
}

