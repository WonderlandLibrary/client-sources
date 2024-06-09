// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import java.util.Iterator;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import events.listeners.EventRenderNameTags;
import events.Event;
import intent.AquaDev.aqua.modules.Category;
import java.awt.Color;
import intent.AquaDev.aqua.modules.Module;

public class Nametags extends Module
{
    final Color back;
    final Color orange;
    private String name;
    
    public Nametags() {
        super("Nametags", Type.Visual, "Nametags", 0, Category.Visual);
        this.back = new Color(28, 25, 24, 255);
        this.orange = new Color(255, 99, 0, 255);
        this.name = "";
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventRenderNameTags && Nametags.mc.thePlayer != null && Nametags.mc.theWorld != null) {
            for (final EntityPlayer e : Nametags.mc.theWorld.playerEntities) {
                if (Nametags.mc.thePlayer.getDistanceToEntity(e) < 250.0f && e != Nametags.mc.thePlayer) {
                    final String health = new StringBuilder(String.valueOf(Math.round(e.getHealth()))).toString();
                    this.name = e.getDisplayName().getFormattedText();
                    this.name = this.name.replaceAll("", "");
                    GlStateManager.pushMatrix();
                    GlStateManager.disableTexture2D();
                    final float pT = Nametags.mc.timer.renderPartialTicks;
                    final double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * pT - RenderManager.renderPosX;
                    final double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * pT - RenderManager.renderPosY;
                    final double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * pT - RenderManager.renderPosZ;
                    final float d = Nametags.mc.thePlayer.getDistanceToEntity(e);
                    final float s = Math.min(Math.max(1.21f * d * 0.1f, 1.25f), 6.0f) * 2.0f / 100.0f;
                    GlStateManager.translate((float)x, (float)y + e.height + 1.8f - e.height / 2.0f, (float)z);
                    GlStateManager.rotate(-Nametags.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(Nametags.mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
                    GlStateManager.scale(-s, -s, s);
                    final float string_width = Nametags.mc.fontRendererObj.getStringWidth(this.name) / 2.0f - 2.0f;
                    GlStateManager.enableTexture2D();
                    GlStateManager.resetColor();
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    Shadow.drawGlow(() -> Gui.drawRect(-Nametags.mc.fontRendererObj.getStringWidth(this.name) / 2 - 3, -2, (int)string_width + 8, 13, new Color(30, 30, 30, 255).getRGB()), false);
                    Gui.drawRect(-Nametags.mc.fontRendererObj.getStringWidth(this.name) / 2 - 3, -2, (int)string_width + 8, 13, new Color(30, 30, 30, 100).getRGB());
                    GlStateManager.resetColor();
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    Nametags.mc.fontRendererObj.drawString(this.name, (int)(-string_width), 2, Color.white.getRGB());
                    GlStateManager.disableBlend();
                    GlStateManager.enableDepth();
                    GlStateManager.resetColor();
                    GlStateManager.disableLighting();
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    GlStateManager.popMatrix();
                }
            }
        }
    }
    
    public static String removeColorCode(final String text) {
        String finalText = text;
        for (int i = 0; i < finalText.length(); ++i) {
            if (Character.toString(finalText.charAt(i)).equals("")) {
                try {
                    final String part1 = finalText.substring(0, i);
                    final String part2 = finalText.substring(Math.min(i + 2, finalText.length()));
                    finalText = String.valueOf(part1) + part2;
                }
                catch (Exception ex) {}
            }
        }
        return finalText;
    }
    
    public void renderItem(final ItemStack item, final int xPos, final int yPos, final int zPos) {
        GL11.glPushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.enableBlend();
        GL11.glBlendFunc(770, 771);
        GlStateManager.disableLighting();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final IBakedModel ibakedmodel = Nametags.mc.getRenderItem().getItemModelMesher().getItemModel(item);
        Nametags.mc.getRenderItem().textureManager.bindTexture(TextureMap.locationBlocksTexture);
        GlStateManager.scale(16.0f, 16.0f, 0.0f);
        GL11.glTranslated((xPos - 7.85f) / 16.0f, (-5 + yPos) / 16.0f, zPos / 16.0f);
        GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        if (ibakedmodel.isBuiltInRenderer()) {
            GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(-0.5f, -0.5f, -0.5f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            TileEntityItemStackRenderer.instance.renderByItem(item);
        }
        else {
            Nametags.mc.getRenderItem().renderModel(ibakedmodel, -1, item);
        }
        GlStateManager.enableAlpha();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
    }
    
    public String getHealthColor(final int hp) {
        if (hp > 15) {
            return "a";
        }
        if (hp > 10) {
            return "e";
        }
        if (hp > 5) {
            return "6";
        }
        if (hp < 2) {
            return "4";
        }
        return "c";
    }
}
