/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EnderCrystalRenderer;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class EnderCrystalModel
extends Model {
    public ModelRenderer cube;
    public ModelRenderer glass;
    public ModelRenderer base;

    public EnderCrystalModel() {
        super(RenderType::getEntityCutoutNoCull);
        EnderCrystalRenderer enderCrystalRenderer = new EnderCrystalRenderer(Minecraft.getInstance().getRenderManager());
        this.cube = (ModelRenderer)Reflector.RenderEnderCrystal_modelRenderers.getValue(enderCrystalRenderer, 0);
        this.glass = (ModelRenderer)Reflector.RenderEnderCrystal_modelRenderers.getValue(enderCrystalRenderer, 1);
        this.base = (ModelRenderer)Reflector.RenderEnderCrystal_modelRenderers.getValue(enderCrystalRenderer, 2);
    }

    public EnderCrystalRenderer updateRenderer(EnderCrystalRenderer enderCrystalRenderer) {
        if (!Reflector.RenderEnderCrystal_modelRenderers.exists()) {
            Config.warn("Field not found: RenderEnderCrystal.modelEnderCrystal");
            return null;
        }
        Reflector.RenderEnderCrystal_modelRenderers.setValue(enderCrystalRenderer, 0, this.cube);
        Reflector.RenderEnderCrystal_modelRenderers.setValue(enderCrystalRenderer, 1, this.glass);
        Reflector.RenderEnderCrystal_modelRenderers.setValue(enderCrystalRenderer, 2, this.base);
        return enderCrystalRenderer;
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
    }
}

