package me.superskidder.lune.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import me.superskidder.lune.font.CFontRenderer;
import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.modules.combat.AntiBot;
import me.superskidder.lune.modules.combat.Teams;
import me.superskidder.lune.events.EventRender3D;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.utils.render.ColorUtils;
import me.superskidder.lune.values.type.Num;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class Nametag
        extends Mod {
    private Num<Number> scale = new Num("Scale", 3.0, 1.0, 5.0);
    private ArrayList<Entity> entities = new ArrayList();

    public Nametag() {
        super("Nametags", ModCategory.Render, "See a tag on player's head");
        this.addValues(this.scale);
    }

    @EventTarget
    public void onRender(EventRender3D e) {
        for (Object o : this.mc.theWorld.playerEntities) {
            EntityPlayer p = (EntityPlayer) o;
            if (p != mc.thePlayer) {
                double pX = p.lastTickPosX + (p.posX - p.lastTickPosX) * mc.timer.renderPartialTicks
                        - mc.getRenderManager().renderPosX;
                double pY = p.lastTickPosY + (p.posY - p.lastTickPosY) * mc.timer.renderPartialTicks
                        - mc.getRenderManager().renderPosY;
                double pZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * mc.timer.renderPartialTicks
                        - mc.getRenderManager().renderPosZ;
                renderNameTag(p, String.valueOf(p.getDisplayName()), pX, pY, pZ);
            }
        }
    }

    public void renderNameTag(EntityPlayer entity, String tag, double pX, double pY, double pZ) {
        CFontRenderer fr = FontLoaders.C16;
        float var10 = mc.thePlayer.getDistanceToEntity(entity) / 6.0F;
        if (var10 < 0.8F) {
            var10 = 0.8F;
        }
        pY += entity.isSneaking() ? 0.5D : 0.7D;
        float var11 = (float) (var10 * this.scale.getValue().floatValue());
        var11 /= 100.0F;
        tag = entity.getName();
        String var12 = "";
        if (AntiBot.bot.contains(entity)) {
            var12 = "\u00a79[BOT]";
        } else {
            var12 = "";
        }
        String var13 = "";
        if (!Teams.isOnSameTeam(entity)) {
            var13 = "";
        } else {
            var13 = "\u00a7b[TEAM]";
        }

        if ((var13 + var12).equals("")) {
            var13 = "\u00a7a";
        }

        String var14 = var13 + var12 + tag;
        String var15 = "\u00a77HP:" + (int) entity.getHealth();
        GL11.glPushMatrix();
        GL11.glTranslatef((float) pX, (float) pY + 1.4F, (float) pZ);
        GL11.glRotatef(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(RenderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-var11, -var11, var11);
        setGLCap(2896, false);
        setGLCap(2929, false);
        int var16 = fr.getStringWidth(var14) / 2;
        setGLCap(3042, true);
        GL11.glBlendFunc(770, 771);
        this.drawBorderedRectNameTag((float) (-var16 - 2), (float) (-(fr.getStringHeight("") + 9)), (float) (var16 + 2), 2.0F, 1.0F, ColorUtils.reAlpha(Color.BLACK.getRGB(), 0.3F), ColorUtils.reAlpha(Color.BLACK.getRGB(), 0.3F));
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        fr.drawString(var14, -var16, -(fr.getStringHeight("") + 8), -1);
        fr.drawString(var15, -fr.getStringWidth(var15) / 2, -(fr.getStringHeight("") - 2), -1);
        int var17 = (new Color(255, 255, 255)).getRGB();
        if (entity.getHealth() > 20.0F) {
            var17 = -65292;
        }

        float var18 = (float) Math.ceil((double) (entity.getHealth() + entity.getAbsorptionAmount()));
        float var19 = var18 / (entity.getMaxHealth() + entity.getAbsorptionAmount());
        Gui.drawRect((float) var16 + var19 * 40.0F - 40.0F + 2.0F, 2.0F, (float) (-var16) - 1.98F, 0.9F, var17);
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        revertAllCaps();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
        GL11.glClear(0);
    }

    public static void revertAllCaps() {
        for (Iterator localIterator = glCapMap.keySet().iterator(); localIterator.hasNext(); ) {
            int cap = ((Integer) localIterator.next()).intValue();
            revertGLCap(cap);
        }
    }

    public static void revertGLCap(int cap) {
        Boolean origCap = (Boolean) glCapMap.get(Integer.valueOf(cap));
        if (origCap != null) {
            if (origCap.booleanValue()) {
                GL11.glEnable(cap);
            } else {
                GL11.glDisable(cap);
            }
        }
    }

    public void renderItemStack(ItemStack var1, int var2, int var3) {

    }

    public void whatTheFuckOpenGLThisFixesItemGlint() {
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
    }

    private static Map<Integer, Boolean> glCapMap = new HashMap();

    public static void setGLCap(int cap, boolean flag) {
        glCapMap.put(Integer.valueOf(cap), Boolean.valueOf(GL11.glGetBoolean(cap)));
        if (flag) {
            GL11.glEnable(cap);
        } else {
            GL11.glDisable(cap);
        }
    }

    public void drawBorderedRectNameTag(float var1, float var2, float var3, float var4, float var5, int var6, int var7) {

    }
}

