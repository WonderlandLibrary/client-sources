// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Render;

import net.minecraft.client.network.badlion.memes.EventTarget;
import java.util.List;
import net.minecraft.client.renderer.WorldRenderer;
import java.util.Iterator;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemArmor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import net.minecraft.client.network.badlion.Wrapper;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.network.badlion.Utils.MathUtils;
import net.minecraft.Badlion;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.client.network.badlion.Utils.ClientUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.network.badlion.Events.EventRender3D;
import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.network.badlion.Mod.Mod;

public class NameTags extends Mod
{
    public NameTags() {
        super("NameTags", Category.RENDER);
    }
    
    @Override
    public void onEnable() {
        EventManager.register(this);
    }
    
    @Override
    public void onDisable() {
        EventManager.unregister(this);
    }
    
    @EventTarget
    public void onRender3D(final EventRender3D event) {
        GlStateManager.pushMatrix();
        for (final Object ent1 : ClientUtils.mc().theWorld.loadedEntityList) {
            final Entity ent2 = (Entity)ent1;
            if (ent2 == ClientUtils.mc().thePlayer) {
                continue;
            }
            if (ent2 instanceof EntityPlayer && !ent2.isInvisible()) {
                final double posX = ent2.lastTickPosX + (ent2.posX - ent2.lastTickPosX) * event.getPartialTicks() - RenderManager.renderPosX;
                final double posY = ent2.lastTickPosY + (ent2.posY - ent2.lastTickPosY) * event.getPartialTicks() - RenderManager.renderPosY + ent2.height + 0.4;
                final double posZ = ent2.lastTickPosZ + (ent2.posZ - ent2.lastTickPosZ) * event.getPartialTicks() - RenderManager.renderPosZ;
                String str = ent2.getDisplayName().getFormattedText();
                if (ent2.isSneaking()) {
                    str = "§c" + ent2.getDisplayName().getFormattedText();
                }
                if (Badlion.getWinter().friendUtils.isFriend(ent2.getName())) {
                    str = str.replace(ent2.getDisplayName().getFormattedText(), "§b" + ent2.getName());
                }
                String colorString = "§";
                final double health = MathUtils.roundToPlace(((EntityPlayer)ent2).getHealth(), 2);
                if (health >= 12.0) {
                    colorString = String.valueOf(String.valueOf(colorString)) + "2";
                }
                else if (health >= 4.0) {
                    colorString = String.valueOf(String.valueOf(colorString)) + "6";
                }
                else {
                    colorString = String.valueOf(String.valueOf(colorString)) + "4";
                }
                str = "§8[" + colorString + health + "§8]" + " §f" + String.valueOf(str);
                final float dist = ClientUtils.mc().thePlayer.getDistanceToEntity(ent2);
                float scale = 0.025f;
                final float factor = (float)((dist <= 8.0f) ? 0.8 : (dist * 0.1));
                scale *= factor;
                GlStateManager.pushMatrix();
                GlStateManager.disableDepth();
                GlStateManager.translate(posX, posY, posZ);
                GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(-ClientUtils.mc().renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(ClientUtils.mc().renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
                GlStateManager.scale(-scale, -scale, scale);
                GlStateManager.disableLighting();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                final Tessellator tessellator = Tessellator.getInstance();
                final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
                GlStateManager.func_179090_x();
                worldRenderer.startDrawingQuads();
                final int stringWidth = Wrapper.fr.getStringWidth(str) / 2;
                GL11.glColor3f(0.0f, 0.5f, 0.5f);
                GL11.glLineWidth(1.0E-6f);
                GL11.glBegin(3);
                GL11.glEnd();
                worldRenderer.func_178974_a(0, 140);
                worldRenderer.addVertex(-stringWidth - 2, 0.0, 0.0);
                worldRenderer.addVertex(-stringWidth - 2, 10.0, 0.0);
                worldRenderer.addVertex(stringWidth + 2, 10.0, 0.0);
                worldRenderer.addVertex(stringWidth + 2, 0.0, 0.0);
                tessellator.draw();
                GlStateManager.func_179098_w();
                Wrapper.fr.drawString(str, -Wrapper.fr.getStringWidth(str) / 2, 1, -1);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
                if (ent2 instanceof EntityPlayer) {
                    final List<ItemStack> itemsToRender = new ArrayList<ItemStack>();
                    final ItemStack hand = ((EntityPlayer)ent2).getEquipmentInSlot(0);
                    if (hand != null) {
                        itemsToRender.add(hand);
                    }
                    for (int i = 4; i > 0; --i) {
                        final ItemStack stack = ((EntityPlayer)ent2).getEquipmentInSlot(i);
                        if (stack != null) {
                            itemsToRender.add(stack);
                        }
                    }
                    int x = -(itemsToRender.size() * 8);
                    for (final ItemStack stack2 : itemsToRender) {
                        GlStateManager.disableDepth();
                        ClientUtils.mc().getRenderItem().zLevel = -100.0f;
                        ClientUtils.mc().getRenderItem().renderItemIntoGUI(stack2, x, -18, false);
                        ClientUtils.mc().getRenderItem().func_175030_a(Minecraft.getMinecraft().fontRendererObj, stack2, x, -18);
                        ClientUtils.mc().getRenderItem().zLevel = 0.0f;
                        this.whatTheFuckOpenGLThisFixesItemGlint();
                        GlStateManager.enableDepth();
                        final String text = "";
                        if (stack2 != null) {
                            int y = 0;
                            final int sLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180314_l.effectId, stack2);
                            final int fLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack2);
                            final int kLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180313_o.effectId, stack2);
                            if (sLevel > 0) {
                                GL11.glDisable(2896);
                                drawEnchantTag("§fSh" + sLevel, x, y);
                                y -= 9;
                            }
                            if (fLevel > 0) {
                                GL11.glDisable(2896);
                                drawEnchantTag("§fFir" + fLevel, x, y);
                                y -= 9;
                            }
                            if (kLevel > 0) {
                                GL11.glDisable(2896);
                                drawEnchantTag("§fKb" + kLevel, x, y);
                            }
                            else if (stack2.getItem() instanceof ItemArmor) {
                                final int pLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180310_c.effectId, stack2);
                                final int tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack2);
                                final int uLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack2);
                                if (pLevel > 0) {
                                    GL11.glDisable(2896);
                                    drawEnchantTag("§fP" + pLevel, x, y);
                                    y -= 9;
                                }
                                if (tLevel > 0) {
                                    GL11.glDisable(2896);
                                    drawEnchantTag("§fTh" + tLevel, x, y);
                                    y -= 9;
                                }
                                if (uLevel > 0) {
                                    GL11.glDisable(2896);
                                    drawEnchantTag("§fUnb" + uLevel, x, y);
                                }
                            }
                            else if (stack2.getItem() instanceof ItemBow) {
                                final int powLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack2);
                                final int punLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack2);
                                final int fireLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack2);
                                if (powLevel > 0) {
                                    GL11.glDisable(2896);
                                    drawEnchantTag("§fPow" + powLevel, x, y);
                                    y -= 9;
                                }
                                if (punLevel > 0) {
                                    GL11.glDisable(2896);
                                    drawEnchantTag("§fPun" + punLevel, x, y);
                                    y -= 9;
                                }
                                if (fireLevel > 0) {
                                    GL11.glDisable(2896);
                                    drawEnchantTag("§fFir" + fireLevel, x, y);
                                }
                            }
                            else if (stack2.getRarity() == EnumRarity.EPIC) {
                                drawEnchantTag("§fGod", x, y);
                            }
                            x += 16;
                        }
                    }
                }
                GlStateManager.disableLighting();
                GlStateManager.popMatrix();
            }
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }
    
    private static void drawEnchantTag(final String text, int x, int y) {
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        x *= (int)2.0;
        y -= 5;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Wrapper.fr.drawStringWithShadow(text, x, -38 - y, -1286);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }
    
    public void whatTheFuckOpenGLThisFixesItemGlint() {
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.func_179090_x();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.func_179098_w();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
    }
}
