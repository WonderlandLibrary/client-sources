/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelBed
extends ModelBase {
    public ModelRenderer field_193772_a;
    public ModelRenderer field_193773_b;
    public ModelRenderer[] field_193774_c = new ModelRenderer[4];

    public ModelBed() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.field_193772_a = new ModelRenderer(this, 0, 0);
        this.field_193772_a.addBox(0.0f, 0.0f, 0.0f, 16, 16, 6, 0.0f);
        this.field_193773_b = new ModelRenderer(this, 0, 22);
        this.field_193773_b.addBox(0.0f, 0.0f, 0.0f, 16, 16, 6, 0.0f);
        this.field_193774_c[0] = new ModelRenderer(this, 50, 0);
        this.field_193774_c[1] = new ModelRenderer(this, 50, 6);
        this.field_193774_c[2] = new ModelRenderer(this, 50, 12);
        this.field_193774_c[3] = new ModelRenderer(this, 50, 18);
        this.field_193774_c[0].addBox(0.0f, 6.0f, -16.0f, 3, 3, 3);
        this.field_193774_c[1].addBox(0.0f, 6.0f, 0.0f, 3, 3, 3);
        this.field_193774_c[2].addBox(-16.0f, 6.0f, -16.0f, 3, 3, 3);
        this.field_193774_c[3].addBox(-16.0f, 6.0f, 0.0f, 3, 3, 3);
        this.field_193774_c[0].rotateAngleX = 1.5707964f;
        this.field_193774_c[1].rotateAngleX = 1.5707964f;
        this.field_193774_c[2].rotateAngleX = 1.5707964f;
        this.field_193774_c[3].rotateAngleX = 1.5707964f;
        this.field_193774_c[0].rotateAngleZ = 0.0f;
        this.field_193774_c[1].rotateAngleZ = 1.5707964f;
        this.field_193774_c[2].rotateAngleZ = 4.712389f;
        this.field_193774_c[3].rotateAngleZ = (float)Math.PI;
    }

    public int func_193770_a() {
        return 51;
    }

    public void func_193771_b() {
        this.field_193772_a.render(0.0625f);
        this.field_193773_b.render(0.0625f);
        this.field_193774_c[0].render(0.0625f);
        this.field_193774_c[1].render(0.0625f);
        this.field_193774_c[2].render(0.0625f);
        this.field_193774_c[3].render(0.0625f);
    }

    public void func_193769_a(boolean p_193769_1_) {
        this.field_193772_a.showModel = p_193769_1_;
        this.field_193773_b.showModel = !p_193769_1_;
        this.field_193774_c[0].showModel = !p_193769_1_;
        this.field_193774_c[1].showModel = p_193769_1_;
        this.field_193774_c[2].showModel = !p_193769_1_;
        this.field_193774_c[3].showModel = p_193769_1_;
    }
}

