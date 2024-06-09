/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.render;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.thekirkayt.client.friend.FriendManager;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.NametagRenderEvent;
import me.thekirkayt.event.events.Render3DEvent;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.IChatComponent;
import org.lwjgl.opengl.GL11;

@Module.Mod(shown=false)
public class Tags
extends Module {
    @Option.Op(min=1.0, max=20.0, increment=1.0)
    private double distance = 8.0;
    @Option.Op(min=0.0, max=2.0, increment=0.1)
    private double scale = 0.1;
    @Option.Op
    private boolean armor = true;
    private Character formatChar = new Character('\u00a7');
    public static Map<EntityLivingBase, double[]> entityPositions = new HashMap<EntityLivingBase, double[]>();

    @EventTarget(value=3)
    private void onRender3DEvent(Render3DEvent event) {
        ClientUtils.mc();
        for (Object o : Minecraft.theWorld.loadedEntityList) {
            Entity ent = (Entity)o;
            ClientUtils.mc();
            if (ent == Minecraft.thePlayer) continue;
            if (ent instanceof EntityPlayer) {
                double posX = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)event.getPartialTicks() - RenderManager.renderPosX;
                double posY = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double)event.getPartialTicks() - RenderManager.renderPosY + (double)ent.height + 0.5;
                double posZ = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)event.getPartialTicks() - RenderManager.renderPosZ;
                String str = ent.getDisplayName().getFormattedText();
                if (FriendManager.isFriend(ent.getName())) {
                    str = str.replace(ent.getName(), this.formatChar + "b" + FriendManager.getAliasName(ent.getName()));
                }
                String colorString = this.formatChar.toString();
                double health = MathUtils.roundToPlace(((EntityPlayer)ent).getHealth(), 2);
                colorString = health >= 12.0 ? String.valueOf(colorString) + "2" : (health >= 4.0 ? String.valueOf(colorString) + "6" : String.valueOf(colorString) + "4");
                str = String.valueOf(str) + " | " + colorString + health;
                ClientUtils.mc();
                float dist = Minecraft.thePlayer.getDistanceToEntity(ent);
                float scale = 0.02672f;
                float factor = (float)((double)dist <= this.distance ? this.distance * this.scale : (double)dist * this.scale);
                GlStateManager.pushMatrix();
                GlStateManager.disableDepth();
                GlStateManager.translate(posX, posY, posZ);
                GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
                ClientUtils.mc();
                GlStateManager.rotate(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                ClientUtils.mc();
                GL11.glRotatef((float)RenderManager.playerViewX, (float)1.0f, (float)0.0f, (float)0.0f);
                GlStateManager.scale(-(scale *= factor), -scale, scale);
                GlStateManager.disableLighting();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                Tessellator tessellator = Tessellator.getInstance();
                WorldRenderer worldRenderer = tessellator.getWorldRenderer();
                GlStateManager.disableTexture2D();
                worldRenderer.startDrawingQuads();
                int stringWidth = ClientUtils.clientFont().getStringWidth(str) / 2;
                GL11.glColor3f((float)0.0f, (float)0.0f, (float)0.0f);
                GL11.glLineWidth((float)1.0E-6f);
                GL11.glBegin((int)3);
                GL11.glVertex2d((double)(-stringWidth - 2), (double)-0.8);
                GL11.glVertex2d((double)(-stringWidth - 2), (double)8.8);
                GL11.glVertex2d((double)(-stringWidth - 2), (double)8.8);
                GL11.glVertex2d((double)(stringWidth + 2), (double)8.8);
                GL11.glVertex2d((double)(stringWidth + 2), (double)8.8);
                GL11.glVertex2d((double)(stringWidth + 2), (double)-0.8);
                GL11.glVertex2d((double)(stringWidth + 2), (double)-0.8);
                GL11.glVertex2d((double)(-stringWidth - 2), (double)-0.8);
                GL11.glEnd();
                worldRenderer.setColorRGBA_I(0, 100);
                worldRenderer.addVertex(-stringWidth - 2, -0.8, 0.0);
                worldRenderer.addVertex(-stringWidth - 2, 8.8, 0.0);
                worldRenderer.addVertex(stringWidth + 2, 8.8, 0.0);
                worldRenderer.addVertex(stringWidth + 2, -0.8, 0.0);
                tessellator.draw();
                GlStateManager.enableTexture2D();
                ClientUtils.clientFont().drawString(str, -ClientUtils.clientFont().getStringWidth(str) / 2, 0.0, -1);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
                if (this.armor && ClientUtils.player().getDistanceSqToEntity(ent) <= 900.0) {
                    int xOffset = 0;
                    ItemStack[] arrayOfItemStack1 = ((EntityPlayer)ent).inventory.armorInventory;
                    int j = arrayOfItemStack1.length;
                    for (int i = 0; i < j; ++i) {
                        ItemStack armourStack = arrayOfItemStack1[i];
                        if (armourStack == null) continue;
                        xOffset -= 8;
                    }
                    if (((EntityPlayer)ent).getHeldItem() != null) {
                        xOffset -= 8;
                        ItemStack renderStack = ((EntityPlayer)ent).getHeldItem().copy();
                        if (renderStack.hasEffect() && (renderStack.getItem() instanceof ItemTool || renderStack.getItem() instanceof ItemArmor)) {
                            renderStack.stackSize = 1;
                        }
                        this.renderItemStack(renderStack, xOffset, -20);
                        xOffset += 16;
                    }
                    for (ItemStack armourStack : ((EntityPlayer)ent).inventory.armorInventory) {
                        if (armourStack == null) continue;
                        ItemStack renderStack1 = armourStack.copy();
                        if (renderStack1.hasEffect() && (renderStack1.getItem() instanceof ItemTool || renderStack1.getItem() instanceof ItemArmor)) {
                            renderStack1.stackSize = 1;
                        }
                        this.renderItemStack(renderStack1, xOffset, -20);
                        xOffset += 16;
                    }
                }
                GlStateManager.popMatrix();
            }
            GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();
    }

    private static void drawEnchantTag(String text, int x, int y) {
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        x = (int)((double)x * 1.75);
        GL11.glScalef((float)0.57f, (float)0.57f, (float)0.57f);
        ClientUtils.clientFont().drawStringWithShadow(text, x, -36 - (y -= 4), 64250);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    @EventTarget
    private void onNametagRender(NametagRenderEvent event) {
        event.setCancelled(true);
    }

    public void renderItemStack(ItemStack stack, int x, int y) {
        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.clear(256);
        ClientUtils.mc().getRenderItem().zLevel = -150.0f;
        ClientUtils.mc().getRenderItem().func_180450_b(stack, x, y);
        ClientUtils.mc().getRenderItem().func_175030_a(ClientUtils.mc().fontRendererObj, stack, x, y);
        ClientUtils.mc().getRenderItem().zLevel = 0.0f;
        GlStateManager.disableBlend();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        this.renderEnchantText(stack, x, y);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();
    }

    public void renderEnchantText(ItemStack stack, int x, int y) {
        int uLevel;
        int fLevel;
        int kLevel;
        int sLevel;
        int encY = y - 24;
        if (stack.getItem() instanceof ItemArmor) {
            int pLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
            int tLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack);
            int uLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            if (pLevel > 0) {
                ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "e" + "p" + pLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (tLevel > 0) {
                ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "e" + "t" + tLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (uLevel2 > 0) {
                ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "e" + "u" + uLevel2, x * 2, encY, 16777215);
                encY += 7;
            }
        }
        if (stack.getItem() instanceof ItemBow) {
            sLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
            kLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
            fLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack);
            uLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            if (sLevel > 0) {
                ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "e" + "d" + sLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (kLevel > 0) {
                ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "e" + "k" + kLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (fLevel > 0) {
                ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "e" + "f" + fLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (uLevel > 0) {
                ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "e" + "u" + uLevel, x * 2, encY, 16777215);
                encY += 7;
            }
        }
        if (stack.getItem() instanceof ItemSword) {
            sLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
            kLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack);
            fLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
            uLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            if (sLevel > 0) {
                ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "e" + "s" + sLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (kLevel > 0) {
                ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "e" + "k" + kLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (fLevel > 0) {
                ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "e" + "f" + fLevel, x * 2, encY, 16777215);
                encY += 7;
            }
            if (uLevel > 0) {
                ClientUtils.clientFont().drawStringWithShadow(this.formatChar + "e" + "u" + uLevel, x * 2, encY, 16777215);
            }
        }
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

