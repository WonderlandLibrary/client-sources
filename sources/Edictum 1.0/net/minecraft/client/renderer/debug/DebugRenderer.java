package net.minecraft.client.renderer.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;

public class DebugRenderer
{
    public final DebugRenderer.IDebugRenderer debugRendererPathfinding;
    public final DebugRenderer.IDebugRenderer debugRendererWater;
    public final DebugRenderer.IDebugRenderer field_190077_c;
    public final DebugRenderer.IDebugRenderer field_190078_d;
    private boolean field_190079_e;
    private boolean field_190080_f;
    private boolean field_190081_g;
    private boolean field_190082_h;

    public DebugRenderer(Minecraft clientIn)
    {
        this.debugRendererPathfinding = new DebugRendererPathfinding(clientIn);
        this.debugRendererWater = new DebugRendererWater(clientIn);
        this.field_190077_c = new DebugRendererChunkBorder(clientIn);
        this.field_190078_d = new DebugRendererHeightMap(clientIn);
    }

    public boolean func_190074_a()
    {
        return this.field_190079_e || this.field_190080_f || this.field_190081_g;
    }

    public boolean func_190075_b()
    {
        this.field_190079_e = !this.field_190079_e;
        return this.field_190079_e;
    }

    public void func_190073_a(float p_190073_1_, long p_190073_2_)
    {
        if (this.field_190080_f)
        {
            this.debugRendererPathfinding.func_190060_a(p_190073_1_, p_190073_2_);
        }

        if (this.field_190079_e && !Minecraft.getMinecraft().func_189648_am())
        {
            this.field_190077_c.func_190060_a(p_190073_1_, p_190073_2_);
        }

        if (this.field_190081_g)
        {
            this.debugRendererWater.func_190060_a(p_190073_1_, p_190073_2_);
        }

        if (this.field_190082_h)
        {
            this.field_190078_d.func_190060_a(p_190073_1_, p_190073_2_);
        }
    }

    public static void func_190076_a(String p_190076_0_, double p_190076_1_, double p_190076_3_, double p_190076_5_, float p_190076_7_, int p_190076_8_)
    {
        Minecraft minecraft = Minecraft.getMinecraft();

        if (minecraft.thePlayer != null && minecraft.getRenderManager() != null && minecraft.getRenderManager().options != null)
        {
            FontRenderer fontrenderer = minecraft.fontRendererObj;
            EntityPlayer entityplayer = minecraft.thePlayer;
            double d0 = entityplayer.lastTickPosX + (entityplayer.posX - entityplayer.lastTickPosX) * (double)p_190076_7_;
            double d1 = entityplayer.lastTickPosY + (entityplayer.posY - entityplayer.lastTickPosY) * (double)p_190076_7_;
            double d2 = entityplayer.lastTickPosZ + (entityplayer.posZ - entityplayer.lastTickPosZ) * (double)p_190076_7_;
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)(p_190076_1_ - d0), (float)(p_190076_3_ - d1) + 0.07F, (float)(p_190076_5_ - d2));
            GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.scale(0.02F, -0.02F, 0.02F);
            RenderManager rendermanager = minecraft.getRenderManager();
            GlStateManager.rotate(-rendermanager.playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate((float)(rendermanager.options.thirdPersonView == 2 ? 1 : -1) * rendermanager.playerViewX, 1.0F, 0.0F, 0.0F);
            GlStateManager.disableLighting();
            GlStateManager.enableTexture2D();
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.scale(-1.0F, 1.0F, 1.0F);
            fontrenderer.drawString(p_190076_0_, -fontrenderer.getStringWidth(p_190076_0_) / 2, 0, p_190076_8_);
            GlStateManager.enableLighting();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
        }
    }

    public interface IDebugRenderer
    {
        void func_190060_a(float p_190060_1_, long p_190060_2_);
    }
}
