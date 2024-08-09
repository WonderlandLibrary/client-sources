/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;

public class HorseArmorChestsModel<T extends AbstractChestedHorseEntity>
extends HorseModel<T> {
    private final ModelRenderer field_199057_c = new ModelRenderer(this, 26, 21);
    private final ModelRenderer field_199058_d;

    public HorseArmorChestsModel(float f) {
        super(f);
        this.field_199057_c.addBox(-4.0f, 0.0f, -2.0f, 8.0f, 8.0f, 3.0f);
        this.field_199058_d = new ModelRenderer(this, 26, 21);
        this.field_199058_d.addBox(-4.0f, 0.0f, -2.0f, 8.0f, 8.0f, 3.0f);
        this.field_199057_c.rotateAngleY = -1.5707964f;
        this.field_199058_d.rotateAngleY = 1.5707964f;
        this.field_199057_c.setRotationPoint(6.0f, -8.0f, 0.0f);
        this.field_199058_d.setRotationPoint(-6.0f, -8.0f, 0.0f);
        this.body.addChild(this.field_199057_c);
        this.body.addChild(this.field_199058_d);
    }

    @Override
    protected void func_199047_a(ModelRenderer modelRenderer) {
        ModelRenderer modelRenderer2 = new ModelRenderer(this, 0, 12);
        modelRenderer2.addBox(-1.0f, -7.0f, 0.0f, 2.0f, 7.0f, 1.0f);
        modelRenderer2.setRotationPoint(1.25f, -10.0f, 4.0f);
        ModelRenderer modelRenderer3 = new ModelRenderer(this, 0, 12);
        modelRenderer3.addBox(-1.0f, -7.0f, 0.0f, 2.0f, 7.0f, 1.0f);
        modelRenderer3.setRotationPoint(-1.25f, -10.0f, 4.0f);
        modelRenderer2.rotateAngleX = 0.2617994f;
        modelRenderer2.rotateAngleZ = 0.2617994f;
        modelRenderer3.rotateAngleX = 0.2617994f;
        modelRenderer3.rotateAngleZ = -0.2617994f;
        modelRenderer.addChild(modelRenderer2);
        modelRenderer.addChild(modelRenderer3);
    }

    @Override
    public void setRotationAngles(T t, float f, float f2, float f3, float f4, float f5) {
        super.setRotationAngles(t, f, f2, f3, f4, f5);
        if (((AbstractChestedHorseEntity)t).hasChest()) {
            this.field_199057_c.showModel = true;
            this.field_199058_d.showModel = true;
        } else {
            this.field_199057_c.showModel = false;
            this.field_199058_d.showModel = false;
        }
    }

    @Override
    public void setRotationAngles(AbstractHorseEntity abstractHorseEntity, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((AbstractChestedHorseEntity)abstractHorseEntity), f, f2, f3, f4, f5);
    }

    @Override
    public void setRotationAngles(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        this.setRotationAngles((T)((AbstractChestedHorseEntity)entity2), f, f2, f3, f4, f5);
    }
}

