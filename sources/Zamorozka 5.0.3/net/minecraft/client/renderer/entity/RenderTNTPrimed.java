package net.minecraft.client.renderer.entity;

import java.awt.Color;
import java.text.DecimalFormat;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import zamorozka.main.Zamorozka;
import zamorozka.module.ModuleManager;
import zamorozka.modules.VISUALLY.HUD;

public class RenderTNTPrimed extends Render<EntityTNTPrimed>
{
	Minecraft mc = Minecraft.getMinecraft();
    public RenderTNTPrimed(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
        this.shadowSize = 0.5F;
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityTNTPrimed entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        if(ModuleManager.getModule(HUD.class).getState() && Zamorozka.settingsManager.getSettingByName("RenderTNTTag").getValBoolean()) {
            try {
                this.renderTag(RenderTNTPrimed.class.cast(Minecraft.getMinecraft().getRenderManager().getEntityClassRenderObject(EntityTNTPrimed.class)), entity, x, y, z, partialTicks);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
   
        BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y + 0.5F, (float)z);

        if ((float)entity.getFuse() - partialTicks + 1.0F < 10.0F)
        {
            float f = 1.0F - ((float)entity.getFuse() - partialTicks + 1.0F) / 10.0F;
            f = MathHelper.clamp(f, 0.0F, 1.0F);
            f = f * f;
            f = f * f;
            float f1 = 1.0F + f * 0.3F;
            GlStateManager.scale(f1, f1, f1);
        }

        float f2 = (1.0F - ((float)entity.getFuse() - partialTicks + 1.0F) / 100.0F) * 0.8F;
        this.bindEntityTexture(entity);
        GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.5F, -0.5F, 0.5F);
        blockrendererdispatcher.renderBlockBrightness(Blocks.TNT.getDefaultState(), entity.getBrightness());
        GlStateManager.translate(0.0F, 0.0F, 1.0F);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
            blockrendererdispatcher.renderBlockBrightness(Blocks.TNT.getDefaultState(), 1.0F);
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        else if (entity.getFuse() / 5 % 2 == 0)
        {
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
            GlStateManager.color(1.0F, 1.0F, 1.0F, f2);
            GlStateManager.doPolygonOffset(-3.0F, -3.0F);
            GlStateManager.enablePolygonOffset();
            blockrendererdispatcher.renderBlockBrightness(Blocks.TNT.getDefaultState(), 1.0F);
            GlStateManager.doPolygonOffset(0.0F, 0.0F);
            GlStateManager.disablePolygonOffset();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
        }

        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
    public void renderTag(final RenderTNTPrimed tntRenderer, final EntityTNTPrimed tntPrimed, final double x, final double y, final double z, final float partialTicks) {
        if (tntPrimed.fuse < 1 && Zamorozka.settingsManager.getSettingByName("RenderTNTTag").getValBoolean() && ModuleManager.getModule(HUD.class).getState()) {
            return;
        }
        final double d0 = tntPrimed.getDistanceSqToEntity(tntRenderer.getRenderManager().renderViewEntity);
        if (d0 <= 4096.0) {
            final float number = (tntPrimed.fuse - partialTicks) / 20.0f;
            final String str = new DecimalFormat("0.00").format(number);
            final FontRenderer fontrenderer = tntRenderer.getFontRendererFromRenderManager();
            final float f = 1.6f;
            final float f2 = 0.016666668f * f;
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x + 0.0f, (float)y + tntPrimed.height + 0.5f, (float)z);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-tntRenderer.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
            int xMultiplier = 1;
            if (this.mc != null && this.mc.gameSettings != null && this.mc.gameSettings.thirdPersonView == 2) {
                xMultiplier = -1;
            }
            GlStateManager.rotate(tntRenderer.getRenderManager().playerViewX * (float)xMultiplier, 1.0f, 0.0f, 0.0f);
            GlStateManager.scale(-f2, -f2, f2);
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            final Tessellator tessellator = Tessellator.getInstance();
            final BufferBuilder worldrenderer = tessellator.getBuffer();
            final int i = 0;
            final int j = fontrenderer.getStringWidth(str) / 2;
            final float green = Math.min((float)tntPrimed.fuse / 80.0f, 1.0f);
            final Color color = new Color(1.0f - green, green, 0.0f);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.disableTexture2D();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos((double)(-j - 1), (double)(-1 + i), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldrenderer.pos((double)(-j - 1), (double)(8 + i), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldrenderer.pos((double)(j + 1), (double)(8 + i), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            worldrenderer.pos((double)(j + 1), (double)(-1 + i), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, color.getRGB());
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityTNTPrimed entity)
    {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
