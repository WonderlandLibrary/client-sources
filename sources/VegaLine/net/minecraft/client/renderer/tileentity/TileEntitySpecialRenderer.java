/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.optifine.entity.model.IEntityRenderer;

public abstract class TileEntitySpecialRenderer<T extends TileEntity>
implements IEntityRenderer {
    protected static final ResourceLocation[] DESTROY_STAGES = new ResourceLocation[]{new ResourceLocation("textures/blocks/destroy_stage_0.png"), new ResourceLocation("textures/blocks/destroy_stage_1.png"), new ResourceLocation("textures/blocks/destroy_stage_2.png"), new ResourceLocation("textures/blocks/destroy_stage_3.png"), new ResourceLocation("textures/blocks/destroy_stage_4.png"), new ResourceLocation("textures/blocks/destroy_stage_5.png"), new ResourceLocation("textures/blocks/destroy_stage_6.png"), new ResourceLocation("textures/blocks/destroy_stage_7.png"), new ResourceLocation("textures/blocks/destroy_stage_8.png"), new ResourceLocation("textures/blocks/destroy_stage_9.png")};
    protected TileEntityRendererDispatcher rendererDispatcher;
    private Class tileEntityClass = null;
    private ResourceLocation locationTextureCustom = null;

    public void func_192841_a(T p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
        ITextComponent itextcomponent = ((TileEntity)p_192841_1_).getDisplayName();
        if (itextcomponent != null && this.rendererDispatcher.cameraHitResult != null && ((TileEntity)p_192841_1_).getPos().equals(this.rendererDispatcher.cameraHitResult.getBlockPos())) {
            this.setLightmapDisabled(true);
            this.drawNameplate(p_192841_1_, itextcomponent.getFormattedText(), p_192841_2_, p_192841_4_, p_192841_6_, 12);
            this.setLightmapDisabled(false);
        }
    }

    protected void setLightmapDisabled(boolean disabled) {
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        if (disabled) {
            GlStateManager.disableTexture2D();
        } else {
            GlStateManager.enableTexture2D();
        }
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    protected void bindTexture(ResourceLocation location) {
        TextureManager texturemanager = this.rendererDispatcher.renderEngine;
        if (texturemanager != null) {
            texturemanager.bindTexture(location);
        }
    }

    protected World getWorld() {
        return this.rendererDispatcher.worldObj;
    }

    public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcherIn) {
        this.rendererDispatcher = rendererDispatcherIn;
    }

    public FontRenderer getFontRenderer() {
        return this.rendererDispatcher.getFontRenderer();
    }

    public boolean isGlobalRenderer(T te) {
        return false;
    }

    protected void drawNameplate(T te, String str, double x, double y, double z, int maxDistance) {
        Entity entity = this.rendererDispatcher.entity;
        double d0 = ((TileEntity)te).getDistanceSq(entity.posX, entity.posY, entity.posZ);
        if (d0 <= (double)(maxDistance * maxDistance)) {
            float f = this.rendererDispatcher.entityYaw;
            float f1 = this.rendererDispatcher.entityPitch;
            boolean flag = false;
            EntityRenderer.drawNameplate(this.getFontRenderer(), str, (float)x + 0.5f, (float)y + 1.5f, (float)z + 0.5f, 0, f, f1, false, false);
        }
    }

    public void renderTileEntityFast(T p_renderTileEntityFast_1_, double p_renderTileEntityFast_2_, double p_renderTileEntityFast_4_, double p_renderTileEntityFast_6_, float p_renderTileEntityFast_8_, int p_renderTileEntityFast_9_, float p_renderTileEntityFast_10_, BufferBuilder p_renderTileEntityFast_11_) {
    }

    @Override
    public Class getEntityClass() {
        return this.tileEntityClass;
    }

    @Override
    public void setEntityClass(Class p_setEntityClass_1_) {
        this.tileEntityClass = p_setEntityClass_1_;
    }

    @Override
    public ResourceLocation getLocationTextureCustom() {
        return this.locationTextureCustom;
    }

    @Override
    public void setLocationTextureCustom(ResourceLocation p_setLocationTextureCustom_1_) {
        this.locationTextureCustom = p_setLocationTextureCustom_1_;
    }
}

