package de.resourcepacks24.utils;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class DrawUtils extends Gui
{
    private Minecraft mc = Minecraft.getMinecraft();
    private final RenderItem itemRenderer;
    public FontRenderer fontRenderer;
    private static int mouseX = 0;
    private static int mouseY = 0;

    public DrawUtils()
    {
        this.itemRenderer = this.mc.getRenderItem();
        this.fontRenderer = this.mc.fontRendererObj;
    }

    public RenderItem getItemRenderer()
    {
        return this.itemRenderer;
    }

    public FontRenderer getFontRenderer()
    {
        return this.fontRenderer;
    }

    public List<String> listFormattedStringToWidth(String str, int wrapWidth)
    {
        return this.fontRenderer.listFormattedStringToWidth(str, wrapWidth);
    }

    public static void updateMouse(int x, int y)
    {
        if (x != 0 || y != 0)
        {
            mouseX = x;
            mouseY = y;
        }
    }

    public static int getMouseX()
    {
        return mouseX;
    }

    public static int getMouseY()
    {
        return mouseY;
    }

    public void drawRightString(String text, double x, double y)
    {
        this.drawString(text, x - (double)this.getStringWidth(text), y);
    }

    public void drawString(String text, double x, double y)
    {
        this.drawString(this.fontRenderer, text, (int)x, (int)y, 16777215);
    }

    public void drawString(String text, double x, double y, double size)
    {
        GL11.glPushMatrix();
        GL11.glScaled(size, size, size);
        this.drawString(this.fontRenderer, text, (int)(x / size), (int)(y / size), 16777215);
        GL11.glPopMatrix();
    }

    public void drawCenteredString(String text, int x, int y)
    {
        this.drawCenteredString(this.fontRenderer, text, x, y, 16777215);
    }

    public void drawCenteredString(String text, double x, double y, double size)
    {
        GL11.glPushMatrix();
        GL11.glScaled(size, size, size);
        this.drawCenteredString(this.fontRenderer, text, (int)(x / size), (int)(y / size), 16777215);
        GL11.glPopMatrix();
    }

    public void drawRightString(String text, double x, double y, double size)
    {
        GL11.glPushMatrix();
        GL11.glScaled(size, size, size);
        this.drawString(this.fontRenderer, text, (int)(x / size - (double)this.getStringWidth(text)), (int)(y / size), 16777215);
        GL11.glPopMatrix();
    }

    public void drawCenteredStringWithoutShadow(String text, double x, double y, double size)
    {
        GL11.glPushMatrix();
        GL11.glScaled(size, size, size);
        this.fontRenderer.drawString(text, (int)(x / size - (double)(this.fontRenderer.getStringWidth(text) / 2)), (int)(y / size), 16777215);
        GL11.glPopMatrix();
    }

    public void drawItem(ItemStack item, int x, int y)
    {
        RenderHelper.enableGUIStandardItemLighting();
        this.itemRenderer.renderItemAndEffectIntoGUI(item, x, y);
        this.itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, item, x, y, "");
        this.itemRenderer.renderItemOverlays(this.fontRenderer, item, x, y);
        GlStateManager.disableLighting();
    }

    public void drawRightItem(ItemStack item, int x, int y)
    {
        RenderHelper.enableGUIStandardItemLighting();
        this.itemRenderer.renderItemAndEffectIntoGUI(item, x, y);
        this.itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, item, x, y, "");
        this.itemRenderer.renderItemOverlays(this.fontRenderer, item, x, y);
        GlStateManager.disableLighting();
    }

    public void drawItem(ItemStack item, double x, double y, String s)
    {
        RenderHelper.enableGUIStandardItemLighting();
        this.itemRenderer.renderItemAndEffectIntoGUI(item, (int)x, (int)y);
        this.itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, item, (int)x, (int)y, s);
        GlStateManager.disableLighting();
    }

    public void bindRightString(String text, int y)
    {
        this.drawString(text, (double)(this.getWidth() - this.getStringWidth(text) - 2), (double)y);
    }

    public int getHeight()
    {
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        return scaledresolution.getScaledHeight();
    }

    public int getWidth()
    {
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        return scaledresolution.getScaledWidth();
    }

    public int getRight(String text)
    {
        return this.fontRenderer.getStringWidth(text) - 2;
    }

    public List<String> splitEqually(String text, int size)
    {
        List<String> list = new ArrayList((text.length() + size - 1) / size);

        for (int i = 0; i < text.length(); i += size)
        {
            list.add(text.substring(i, Math.min(text.length(), i + size)));
        }

        return list;
    }

    public int getRightScreen(int settings)
    {
        String s = "";

        for (int i = 0; i <= settings; ++i)
        {
            s = s + " ";
        }

        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        return scaledresolution.getScaledWidth() - this.fontRenderer.getStringWidth(s) - 5;
    }

    public int getMiddleScreen(int settings)
    {
        String s = "";

        for (int i = 0; i <= settings; ++i)
        {
            s = s + " ";
        }

        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        return scaledresolution.getScaledWidth() / 2 - this.fontRenderer.getStringWidth(s);
    }

    public int getStringWidth(String text)
    {
        return this.fontRenderer.getStringWidth(text);
    }

    public double getScale(int scale)
    {
        int i = scale;

        if (scale < 50)
        {
            i = 25 + scale / 2;
        }

        return (double)i * 0.02D;
    }

    public void drawRect(double left, double top, double right, double bottom, int color)
    {
        drawRect((int)left, (int)top, (int)right, (int)bottom, color);
    }

    public void drawBox(int left, int top, int right, int bottom)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.9F);
        drawRect(left, top, right, bottom, java.awt.Color.WHITE.getRGB());
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
        drawRect(left, top, right, top + 1, java.awt.Color.WHITE.getRGB());
        drawRect(left, top, left + 1, bottom, java.awt.Color.WHITE.getRGB());
        drawRect(right - 1, top, right, bottom, java.awt.Color.WHITE.getRGB());
        drawRect(left, bottom - 1, right, bottom, java.awt.Color.WHITE.getRGB());
    }

    public void drawBackground(int tint)
    {
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        this.mc.getTextureManager().bindTexture(optionsBackground);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos(0.0D, (double)this.getHeight(), 0.0D).tex(0.0D, (double)((float)this.getHeight() / 32.0F + (float)tint)).color(64, 64, 64, 255).endVertex();
        worldrenderer.pos((double)this.getWidth(), (double)this.getHeight(), 0.0D).tex((double)((float)this.getWidth() / 32.0F), (double)((float)this.getHeight() / 32.0F + (float)tint)).color(64, 64, 64, 255).endVertex();
        worldrenderer.pos((double)this.getWidth(), 0.0D, 0.0D).tex((double)((float)this.getWidth() / 32.0F), (double)tint).color(64, 64, 64, 255).endVertex();
        worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, (double)tint).color(64, 64, 64, 255).endVertex();
        tessellator.draw();
    }

    public void drawChatBackground(int left, int top, int right, int bottom, int amountScrolled)
    {
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos((double)left, (double)bottom, 0.0D).tex((double)((float)left / f), (double)((float)(bottom + amountScrolled) / f)).color(32, 32, 32, 255).endVertex();
        worldrenderer.pos((double)right, (double)bottom, 0.0D).tex((double)((float)right / f), (double)((float)(bottom + amountScrolled) / f)).color(32, 32, 32, 255).endVertex();
        worldrenderer.pos((double)right, (double)top, 0.0D).tex((double)((float)right / f), (double)((float)(top + amountScrolled) / f)).color(32, 32, 32, 255).endVertex();
        worldrenderer.pos((double)left, (double)top, 0.0D).tex((double)((float)left / f), (double)((float)(top + amountScrolled) / f)).color(32, 32, 32, 255).endVertex();
        tessellator.draw();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableAlpha();
        GlStateManager.shadeModel(7425);
        GlStateManager.disableDepth();
        int i = 4;
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableAlpha();
        GlStateManager.shadeModel(7425);
        GlStateManager.enableTexture2D();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos((double)left, (double)(top + i), 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 0).endVertex();
        worldrenderer.pos((double)right, (double)(top + i), 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 0).endVertex();
        worldrenderer.pos((double)right, (double)top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
        worldrenderer.pos((double)left, (double)top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
        tessellator.draw();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos((double)left, (double)bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
        worldrenderer.pos((double)right, (double)bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
        worldrenderer.pos((double)right, (double)(bottom - i), 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 0).endVertex();
        worldrenderer.pos((double)left, (double)(bottom - i), 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 0).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
    }

    public void drawTransparentBackground(int left, int top, int right, int bottom)
    {
        try
        {
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
            float f = 32.0F;
            int i = 0;
            drawRect(left, top, right, bottom, Integer.MIN_VALUE);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableAlpha();
            GlStateManager.shadeModel(7425);
            GlStateManager.disableDepth();
            int j = 4;
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableAlpha();
            GlStateManager.shadeModel(7425);
            GlStateManager.enableTexture2D();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldrenderer.pos((double)left, (double)(top + j), 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 0).endVertex();
            worldrenderer.pos((double)right, (double)(top + j), 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 0).endVertex();
            worldrenderer.pos((double)right, (double)top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
            worldrenderer.pos((double)left, (double)top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
            tessellator.draw();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldrenderer.pos((double)left, (double)bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            worldrenderer.pos((double)right, (double)bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            worldrenderer.pos((double)right, (double)(bottom - j), 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 0).endVertex();
            worldrenderer.pos((double)left, (double)(bottom - j), 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 0).endVertex();
            tessellator.draw();
            GlStateManager.shadeModel(7424);
            GlStateManager.enableAlpha();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void overlayBackground(int startY, int endY)
    {
        int i = 255;
        int j = 255;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos(0.0D, (double)endY, 0.0D).tex(0.0D, (double)((float)endY / 32.0F)).color(64, 64, 64, i).endVertex();
        worldrenderer.pos((double)(0 + this.getWidth()), (double)endY, 0.0D).tex((double)((float)this.getWidth() / 32.0F), (double)((float)endY / 32.0F)).color(64, 64, 64, i).endVertex();
        worldrenderer.pos((double)(0 + this.getWidth()), (double)startY, 0.0D).tex((double)((float)this.getWidth() / 32.0F), (double)((float)startY / 32.0F)).color(64, 64, 64, j).endVertex();
        worldrenderer.pos(0.0D, (double)startY, 0.0D).tex(0.0D, (double)((float)startY / 32.0F)).color(64, 64, 64, j).endVertex();
        tessellator.draw();
    }

    public void overlayBackground(int startX, int startY, int endX, int endY)
    {
        int i = 255;
        int j = 255;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos((double)startX, (double)endY, 0.0D).tex(0.0D, (double)((float)endY / 32.0F)).color(64, 64, 64, i).endVertex();
        worldrenderer.pos((double)(startX + endX), (double)endY, 0.0D).tex((double)((float)endX / 32.0F), (double)((float)endY / 32.0F)).color(64, 64, 64, i).endVertex();
        worldrenderer.pos((double)(startX + endX), (double)startY, 0.0D).tex((double)((float)endX / 32.0F), (double)((float)startY / 32.0F)).color(64, 64, 64, j).endVertex();
        worldrenderer.pos((double)startX, (double)startY, 0.0D).tex(0.0D, (double)((float)startY / 32.0F)).color(64, 64, 64, j).endVertex();
        tessellator.draw();
    }

    public void drawTexturedModalRect(double x, double y, double textureX, double textureY, double width, double height)
    {
        this.drawTexturedModalRect((int)x, (int)y, (int)textureX, (int)textureY, (int)width, (int)height);
    }

    public void drawTexturedModalRect(double left, double top, double right, double bottom)
    {
        double d0 = 0.0D;
        double d1 = 0.0D;
        double d2 = right - left;
        double d3 = bottom - top;
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(left + 0.0D, top + d3, (double)this.zLevel).tex((double)((float)(d0 + 0.0D) * f), (double)((float)(d1 + d3) * f1)).endVertex();
        worldrenderer.pos(left + d2, top + d3, (double)this.zLevel).tex((double)((float)(d0 + d2) * f), (double)((float)(d1 + d3) * f1)).endVertex();
        worldrenderer.pos(left + d2, top + 0.0D, (double)this.zLevel).tex((double)((float)(d0 + d2) * f), (double)((float)(d1 + 0.0D) * f1)).endVertex();
        worldrenderer.pos(left + 0.0D, top + 0.0D, (double)this.zLevel).tex((double)((float)(d0 + 0.0D) * f), (double)((float)(d1 + 0.0D) * f1)).endVertex();
        tessellator.draw();
    }
}
