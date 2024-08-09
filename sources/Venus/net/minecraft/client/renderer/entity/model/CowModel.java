/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class CowModel<T extends Entity>
extends QuadrupedModel<T> {
    public CowModel() {
        super(12, 0.0f, false, 10.0f, 4.0f, 2.0f, 2.0f, 24);
        this.headModel = new ModelRenderer(this, 0, 0);
        this.headModel.addBox(-4.0f, -4.0f, -6.0f, 8.0f, 8.0f, 6.0f, 0.0f);
        this.headModel.setRotationPoint(0.0f, 4.0f, -8.0f);
        this.headModel.setTextureOffset(22, 0).addBox(-5.0f, -5.0f, -4.0f, 1.0f, 3.0f, 1.0f, 0.0f);
        this.headModel.setTextureOffset(22, 0).addBox(4.0f, -5.0f, -4.0f, 1.0f, 3.0f, 1.0f, 0.0f);
        this.body = new ModelRenderer(this, 18, 4);
        this.body.addBox(-6.0f, -10.0f, -7.0f, 12.0f, 18.0f, 10.0f, 0.0f);
        this.body.setRotationPoint(0.0f, 5.0f, 2.0f);
        this.body.setTextureOffset(52, 0).addBox(-2.0f, 2.0f, -8.0f, 4.0f, 6.0f, 1.0f);
        this.legBackRight.rotationPointX -= 1.0f;
        this.legBackLeft.rotationPointX += 1.0f;
        this.legBackRight.rotationPointZ += 0.0f;
        this.legBackLeft.rotationPointZ += 0.0f;
        this.legFrontRight.rotationPointX -= 1.0f;
        this.legFrontLeft.rotationPointX += 1.0f;
        this.legFrontRight.rotationPointZ -= 1.0f;
        this.legFrontLeft.rotationPointZ -= 1.0f;
    }

    public ModelRenderer getHead() {
        return this.headModel;
    }
}

