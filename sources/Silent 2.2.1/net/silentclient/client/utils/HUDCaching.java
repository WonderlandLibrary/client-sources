package net.silentclient.client.utils;

import net.minecraft.client.gui.GuiIngame;
import net.silentclient.client.mixin.accessors.GuiInGameAccessor;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.ClientTickEvent;
import net.silentclient.client.gui.lite.clickgui.utils.GlUtils;
import net.silentclient.client.mods.render.crosshair.CrosshairMod;
import net.silentclient.client.mods.settings.FPSBoostMod;
import net.silentclient.client.mods.settings.RenderMod;

public class HUDCaching {
	private static final Minecraft mc = Minecraft.getMinecraft();
    public static Framebuffer framebuffer;
    private static boolean dirty = true;
    public static boolean renderingCacheOverride;

    @EventTarget
    public void tick(ClientTickEvent event) {
        if (FPSBoostMod.hudOptimizationEnabled()) {
            if (!OpenGlHelper.isFramebufferEnabled() && mc.thePlayer != null) {
        		Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Hud Optimization").setValBoolean(false);
            } else {
                dirty = true;
            }
        }
    }

    public static void renderCachedHud(EntityRenderer renderer, GuiIngame ingame, float partialTicks) {
        if (!OpenGlHelper.isFramebufferEnabled() || !FPSBoostMod.hudOptimizationEnabled()) {
            ingame.renderGameOverlay(partialTicks);
        } else {
            GlStateManager.enableDepth();
            ScaledResolution resolution = new ScaledResolution(mc);
            int width = resolution.getScaledWidth();
            int height = resolution.getScaledHeight();
            renderer.setupOverlayRendering();
            GlStateManager.enableBlend();

            if (framebuffer != null) {
                Tessellator tessellator = Tessellator.getInstance();
                WorldRenderer worldRenderer = tessellator.getWorldRenderer();
                if (((GuiInGameAccessor) ingame).silent$showCrosshair() && (Client.getInstance().getSettingsManager().getSettingByClass(RenderMod.class, "Crosshair in F5").getValBoolean() || mc.gameSettings.thirdPersonView < 1)) {
                    if(!Client.getInstance().getModInstances().getModByClass(CrosshairMod.class).isEnabled()) {
                    	mc.getTextureManager().bindTexture(Gui.icons);
                        GlStateManager.enableBlend();
                        GlStateManager.tryBlendFuncSeparate(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR, GL11.GL_ONE, GL11.GL_ZERO);
                        GlStateManager.enableAlpha();
                        drawTexturedModalRect(tessellator, worldRenderer, (width >> 1) - 7, (height >> 1) - 7);
                        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
                    } else if(Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").getValBoolean()) {
                    	String selected = Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset ID").getValString();
                        if(Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Vanilla Blendering").getValBoolean()) {
                            GlStateManager.enableBlend();
                            GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
                            GlStateManager.enableAlpha();
                            GlStateManager.enableDepth();
                        } else {
                            ColorUtils.setColor(Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Crosshair Color").getValColor().getRGB());
                        }

                        mc.getTextureManager().bindTexture(new ResourceLocation("silentclient/mods/crosshair/crosshair" + selected + ".png"));
                        GlUtils.startScale((width >> 1) - 7, (height >> 1) - 7, 16, 16, (float) Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Scale").getValDouble());
                        Gui.drawModalRectWithCustomSizedTexture((width >> 1) - 7, (height >> 1) - 7, 0, 0, 16, 16, 16, 16);
                        GlUtils.stopScale();
                    }
                }

                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GlStateManager.color(1, 1, 1, 1);
                framebuffer.bindFramebufferTexture();
                drawTexturedRect(tessellator, worldRenderer, (float) resolution.getScaledWidth_double(), (float) resolution.getScaledHeight_double());
                GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
            }

            if (framebuffer == null || dirty) {
                dirty = false;
                (framebuffer = checkFramebufferSizes(framebuffer, mc.displayWidth, mc.displayHeight)).framebufferClear();
                framebuffer.bindFramebuffer(false);
                GlStateManager.disableBlend();
                GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GlStateManager.color(1, 1, 1, 1);
                GlStateManager.disableLighting();
                GlStateManager.disableFog();
                renderingCacheOverride = true;
                ingame.renderGameOverlay(partialTicks);
                renderingCacheOverride = false;
                mc.getFramebuffer().bindFramebuffer(false);
                GlStateManager.enableBlend();
            }

            GlStateManager.enableDepth();
        }
    }

    private static Framebuffer checkFramebufferSizes(Framebuffer framebuffer, int width, int height) {
        if (framebuffer == null || framebuffer.framebufferWidth != width || framebuffer.framebufferHeight != height) {
            if (framebuffer == null) {
                framebuffer = new Framebuffer(width, height, true);
                framebuffer.framebufferColor[0] = 0.0f;
                framebuffer.framebufferColor[1] = 0.0f;
                framebuffer.framebufferColor[2] = 0.0f;
            } else {
                framebuffer.createBindFramebuffer(width, height);
            }

            framebuffer.setFramebufferFilter(GL11.GL_NEAREST);
        }

        return framebuffer;
    }

    private static void drawTexturedRect(Tessellator tessellator, WorldRenderer worldrenderer, float width, float height) {
        GlStateManager.enableTexture2D();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(0, height, 0.0).tex(0, 0).endVertex();
        worldrenderer.pos(width, height, 0.0).tex(1, 0).endVertex();
        worldrenderer.pos(width, 0, 0.0).tex(1, 1).endVertex();
        worldrenderer.pos(0, 0, 0.0).tex(0, 1).endVertex();
        tessellator.draw();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
    }

    private static void drawTexturedModalRect(Tessellator tessellator, WorldRenderer worldrenderer, int x, int y) {
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + 16, 100.0).tex(0.0f, 0.0625f).endVertex();
        worldrenderer.pos(x + 16, y + 16, 100.0).tex(0.0625f, 0.0625f).endVertex();
        worldrenderer.pos(x + 16, y, 100.0).tex(0.0625f, 0.0f).endVertex();
        worldrenderer.pos(x, y, 100.0).tex(0.0f, 0.0f).endVertex();
        tessellator.draw();
    }
}
