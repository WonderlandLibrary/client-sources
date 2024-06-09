package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public abstract class TileEntitySpecialRenderer<T extends TileEntity>
{
    protected static final ResourceLocation[] DESTROY_STAGES = new ResourceLocation[] {new ResourceLocation("textures/blocks/destroy_stage_0.png"), new ResourceLocation("textures/blocks/destroy_stage_1.png"), new ResourceLocation("textures/blocks/destroy_stage_2.png"), new ResourceLocation("textures/blocks/destroy_stage_3.png"), new ResourceLocation("textures/blocks/destroy_stage_4.png"), new ResourceLocation("textures/blocks/destroy_stage_5.png"), new ResourceLocation("textures/blocks/destroy_stage_6.png"), new ResourceLocation("textures/blocks/destroy_stage_7.png"), new ResourceLocation("textures/blocks/destroy_stage_8.png"), new ResourceLocation("textures/blocks/destroy_stage_9.png")};
    protected TileEntityRendererDispatcher rendererDispatcher;

    public void renderTileEntityAt(T te, double x, double y, double z, float partialTicks, int destroyStage)
    {
        ITextComponent itextcomponent = te.getDisplayName();

        if (itextcomponent != null && this.rendererDispatcher.field_190057_j != null && te.getPos().equals(this.rendererDispatcher.field_190057_j.getBlockPos()))
        {
            this.func_190053_a(true);
            this.func_190052_a(te, itextcomponent.getFormattedText(), x, y, z, 12);
            this.func_190053_a(false);
        }
    }

    protected void func_190053_a(boolean p_190053_1_)
    {
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);

        if (p_190053_1_)
        {
            GlStateManager.disableTexture2D();
        }
        else
        {
            GlStateManager.enableTexture2D();
        }

        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    protected void bindTexture(ResourceLocation location)
    {
        TextureManager texturemanager = this.rendererDispatcher.renderEngine;

        if (texturemanager != null)
        {
            texturemanager.bindTexture(location);
        }
    }

    protected World getWorld()
    {
        return this.rendererDispatcher.worldObj;
    }

    public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        this.rendererDispatcher = rendererDispatcherIn;
    }

    public FontRenderer getFontRenderer()
    {
        return this.rendererDispatcher.getFontRenderer();
    }

    public boolean isGlobalRenderer(T te)
    {
        return false;
    }

    protected void func_190052_a(T p_190052_1_, String p_190052_2_, double p_190052_3_, double p_190052_5_, double p_190052_7_, int p_190052_9_)
    {
        Entity entity = this.rendererDispatcher.entity;
        double d0 = p_190052_1_.getDistanceSq(entity.posX, entity.posY, entity.posZ);

        if (d0 <= (double)(p_190052_9_ * p_190052_9_))
        {
            float f = this.rendererDispatcher.entityYaw;
            float f1 = this.rendererDispatcher.entityPitch;
            boolean flag = false;
            EntityRenderer.func_189692_a(this.getFontRenderer(), p_190052_2_, (float)p_190052_3_ + 0.5F, (float)p_190052_5_ + 1.5F, (float)p_190052_7_ + 0.5F, 0, f, f1, false, false);
        }
    }
}
