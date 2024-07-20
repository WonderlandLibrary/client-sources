/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderBiped<T extends EntityLiving>
extends RenderLiving<T> {
    private static final ResourceLocation DEFAULT_RES_LOC = new ResourceLocation("textures/entity/steve.png");

    public RenderBiped(RenderManager renderManagerIn, ModelBiped modelBipedIn, float shadowSize) {
        super(renderManagerIn, modelBipedIn, shadowSize);
        this.addLayer(new LayerCustomHead(modelBipedIn.bipedHead));
        this.addLayer(new LayerElytra(this));
        this.addLayer(new LayerHeldItem(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return DEFAULT_RES_LOC;
    }

    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0f, 0.1875f, 0.0f);
    }
}

