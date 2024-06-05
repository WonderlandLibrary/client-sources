package me.mees.remix.modules.render;

import me.satisfactory.base.module.*;
import me.mees.remix.font.*;
import optifine.*;
import net.minecraft.scoreboard.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import me.satisfactory.base.events.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import pw.stamina.causam.scan.method.model.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.enchantment.*;
import me.satisfactory.base.*;
import me.satisfactory.base.relations.*;
import java.text.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.entity.*;

public class Nametags extends Module
{
    int[] colorCode;
    public CFontRenderer fontRenderer3;
    
    public Nametags() {
        super("Nametags", 0, Category.RENDER);
        this.colorCode = new int[32];
        this.fontRenderer3 = FontLoaders.consolas18;
    }
    
    public int getColorCode(final char p_175064_1_) {
        final int index = "0123456789abcdef".indexOf(p_175064_1_);
        if (index >= 0 && index < this.colorCode.length) {
            int color = this.colorCode[index];
            if (Config.isCustomColors()) {
                color = CustomColors.getTextColor(index, color);
            }
            return color;
        }
        return 16777215;
    }
    
    public int getTeamColor(final EntityLivingBase entity) {
        int i2 = 16777215;
        final ScorePlayerTeam scoreplayerteam = (ScorePlayerTeam)entity.getTeam();
        final String s2;
        if (scoreplayerteam != null && (s2 = FontRenderer.getFormatFromString(scoreplayerteam.getColorPrefix())).length() >= 2) {
            i2 = this.getColorCode(s2.charAt(1));
        }
        return i2;
    }
    
    public void setColor(final Color c) {
        GL11.glColor4d((double)(c.getRed() / 255.0f), (double)(c.getGreen() / 255.0f), (double)(c.getBlue() / 255.0f), (double)(c.getAlpha() / 255.0f));
    }
    
    public long getPing(final String name) {
        if (Nametags.mc.getNetHandler().func_175104_a(name) != null) {
            return Nametags.mc.getNetHandler().func_175104_a(name).getResponseTime();
        }
        return -3L;
    }
    
    public void drawRect(float left, float top, float right, float bottom, final Color color) {
        if (left < right) {
            final float var5 = left;
            left = right;
            right = var5;
        }
        if (top < bottom) {
            final float var5 = top;
            top = bottom;
            bottom = var5;
        }
        final Tessellator var6 = Tessellator.getInstance();
        final WorldRenderer var7 = var6.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.func_179090_x();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        this.setColor(color);
        var7.startDrawingQuads();
        var7.addVertex(left, bottom, 0.0);
        var7.addVertex(right, bottom, 0.0);
        var7.addVertex(right, top, 0.0);
        var7.addVertex(left, top, 0.0);
        var6.draw();
        GlStateManager.func_179098_w();
        GlStateManager.disableBlend();
        this.setColor(Color.WHITE);
    }
    
    public int getMiddle(final int i, final int i1) {
        return (i + i1) / 2;
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Subscriber
    public void eventRender3D(final Event3DRender e) {
        for (final Object i : Nametags.mc.theWorld.loadedEntityList) {
            if (i instanceof EntityPlayer) {
                final EntityPlayer entity = (EntityPlayer)i;
                if (entity.getName() == Nametags.mc.thePlayer.getName() && entity.isInvisible()) {
                    continue;
                }
                final double n = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * Nametags.mc.timer.renderPartialTicks;
                final double pX = n - Nametags.mc.getRenderManager().renderPosX;
                final double n2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * Nametags.mc.timer.renderPartialTicks;
                final double pY = n2 - Nametags.mc.getRenderManager().renderPosY;
                final double n3 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * Nametags.mc.timer.renderPartialTicks;
                final double pZ = n3 - Nametags.mc.getRenderManager().renderPosZ;
                this.renderNameTag(entity, entity.getDisplayName().getFormattedText(), pX, pY, pZ);
            }
        }
    }
    
    public void renderArmor(final EntityPlayer player, int x, final int y) {
        final ItemStack[] items = player.getInventory();
        final ItemStack inHand = player.getCurrentEquippedItem();
        final ItemStack boots = items[0];
        final ItemStack leggings = items[1];
        final ItemStack body = items[2];
        final ItemStack helm = items[3];
        ItemStack[] stuff = null;
        if (inHand != null) {
            stuff = new ItemStack[] { inHand, helm, body, leggings, boots };
        }
        else {
            stuff = new ItemStack[] { helm, body, leggings, boots };
        }
        final ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
        ItemStack[] array;
        for (int length = (array = stuff).length, j = 0; j < length; ++j) {
            final ItemStack i = array[j];
            if (i != null && i.getItem() != null) {
                stacks.add(i);
            }
        }
        final int width = 16 * stacks.size() / 2;
        x -= width;
        GlStateManager.disableDepth();
        for (final ItemStack stack : stacks) {
            this.renderItem(stack, x, y);
            if (stack.getMaxDamage() > 0) {
                GlStateManager.translate((float)(x + 2), (float)(y + 1), 0.0f);
                GlStateManager.scale(0.32f, 0.32f, 0.32f);
                GlStateManager.disableDepth();
                GlStateManager.disableLighting();
                this.setColor(Color.white);
                Nametags.mc.fontRendererObj.drawString("§f" + String.valueOf(stack.getMaxDamage() - stack.getItemDamage()), (float)(10 - Nametags.mc.fontRendererObj.getStringWidth("§f" + String.valueOf(stack.getMaxDamage() - stack.getItemDamage())) / 2), 30.0f, Color.WHITE.getRGB());
                this.setColor(Color.white);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
                GlStateManager.scale(3.125f, 3.125f, 3.125f);
                GlStateManager.translate((float)(-x - 1), (float)(-y - 1), 0.0f);
            }
            x += 16;
        }
        GlStateManager.enableDepth();
    }
    
    public void renderItem(final ItemStack stack, final int x, int y) {
        final EnchantEntry[] enchants = { new EnchantEntry(Enchantment.field_180310_c, "Prot"), new EnchantEntry(Enchantment.thorns, "Th"), new EnchantEntry(Enchantment.field_180314_l, "Sharp"), new EnchantEntry(Enchantment.fireAspect, "Fire"), new EnchantEntry(Enchantment.field_180313_o, "Kb"), new EnchantEntry(Enchantment.unbreaking, "Unb") };
        GlStateManager.pushMatrix();
        GlStateManager.pushMatrix();
        final float scale1 = 0.3f;
        GlStateManager.translate((float)(x - 3), (float)(y + 10), 0.0f);
        GlStateManager.scale(0.3f, 0.3f, 0.3f);
        GlStateManager.popMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        Nametags.mc.getRenderItem().zLevel = -100.0f;
        GlStateManager.disableDepth();
        Nametags.mc.getRenderItem().renderItemAboveHead(stack, x, y);
        Nametags.mc.getRenderItem().renderItemOverlayIntoGUI(Nametags.mc.fontRendererObj, stack, x, y, null);
        GlStateManager.enableDepth();
        EnchantEntry[] array;
        for (int length = (array = enchants).length, i = 0; i < length; ++i) {
            final EnchantEntry enchant = array[i];
            final int level = EnchantmentHelper.getEnchantmentLevel(enchant.getEnchant().effectId, stack);
            String levelDisplay = "" + level;
            if (level > 10) {
                levelDisplay = "10+";
            }
            if (level > 0) {
                final float scale2 = 0.32f;
                GlStateManager.translate((float)(x + 2), (float)(y + 1), 0.0f);
                GlStateManager.scale(0.32f, 0.32f, 0.32f);
                GlStateManager.disableDepth();
                GlStateManager.disableLighting();
                this.setColor(Color.white);
                Nametags.mc.fontRendererObj.drawString("§f" + enchant.getName() + " " + levelDisplay, (float)(20 - Nametags.mc.fontRendererObj.getStringWidth("§f" + enchant.getName() + " " + levelDisplay) / 2), 0.0f, Color.WHITE.getRGB());
                this.setColor(Color.white);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
                GlStateManager.scale(3.125f, 3.125f, 3.125f);
                GlStateManager.translate((float)(-x - 1), (float)(-y - 1), 0.0f);
                y += (int)((Nametags.mc.fontRendererObj.FONT_HEIGHT + 3) * 0.32f);
            }
        }
        Nametags.mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
    }
    
    public void renderNameTag(final EntityPlayer entity, String tag, final double pX, double pY, final double pZ) {
        if (entity.getName().equals(Nametags.mc.thePlayer.getName())) {
            return;
        }
        pY += (entity.isSneaking() ? 0.5 : 0.7);
        float var13 = Nametags.mc.thePlayer.getDistanceToEntity(entity) / 4.0f;
        if (var13 < 1.6f) {
            var13 = 1.6f;
        }
        final int color = Base.INSTANCE.GetMainColor();
        Color friendColor = new Color(0, 255, 0);
        if (FriendManager.isFriend(entity.getName())) {
            tag = "" + entity.getDisplayName().getFormattedText();
            friendColor = new Color(0, 255, 0);
        }
        final DecimalFormat format = new DecimalFormat("0.##");
        String healthSt = "";
        final float health = entity.getHealth() / 1.0f;
        if (health <= entity.getMaxHealth() * 0.25) {
            healthSt = String.valueOf(healthSt) + "";
        }
        else if (health <= entity.getMaxHealth() * 0.5) {
            healthSt = String.valueOf(healthSt) + "";
        }
        else if (health <= entity.getMaxHealth() * 0.75) {
            healthSt = String.valueOf(healthSt) + "";
        }
        else if (health <= entity.getMaxHealth()) {
            healthSt = String.valueOf(healthSt) + "";
        }
        final DecimalFormat decimalFormat = new DecimalFormat("0.##");
        tag = String.valueOf(String.valueOf(tag)) + " §4\u2764" + healthSt + "§f " + decimalFormat.format(health) + "";
        final RenderManager renderManager = Nametags.mc.getRenderManager();
        float scale = var13;
        scale /= 30.0f;
        scale *= (float)0.3;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)pX, (float)pY + 1.4f, (float)pZ);
        GL11.glNormal3f(1.0f, 1.0f, 1.0f);
        GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(-scale, -scale, scale);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        final Tessellator var14 = Tessellator.getInstance();
        final WorldRenderer var15 = var14.getWorldRenderer();
        final int width = Nametags.mc.fontRendererObj.getStringWidth(tag) / 2;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        final ItemStack[] items = entity.getInventory();
        if (entity.getCurrentEquippedItem() != null || items[0] != null || items[1] != null || items[2] != null || items[3] != null) {
            if (entity.getCurrentEquippedItem() != null && items[0] != null && items[1] != null && items[2] != null && items[3] != null) {
                if (width >= 41) {
                    Gui.drawRect(-width - 4, -(Nametags.mc.fontRendererObj.FONT_HEIGHT + 1) - 21, width + 6, 2, new Color(22, 22, 22, 150).getRGB());
                }
                else {
                    Gui.drawRect(-45, -(Nametags.mc.fontRendererObj.FONT_HEIGHT + 1) - 21, 47, 2, new Color(22, 22, 22, 150).getRGB());
                }
            }
            else if (width >= 35) {
                Gui.drawRect(-width - 1, -(Nametags.mc.fontRendererObj.FONT_HEIGHT + 1) - 21, width + 6, 2, new Color(22, 22, 22, 150).getRGB());
            }
            else {
                Gui.drawRect(-36, -(Nametags.mc.fontRendererObj.FONT_HEIGHT + 1) - 21, 35, 2, new Color(22, 22, 22, 150).getRGB());
            }
        }
        else {
            Gui.drawRect(-width - 1, -(Nametags.mc.fontRendererObj.FONT_HEIGHT + 1) - 21, width + 3, -(this.fontRenderer3.getStringHeight(tag) + 1) - 7, new Color(22, 22, 22, 150).getRGB());
        }
        Nametags.mc.fontRendererObj.drawString(tag, this.getMiddle(-width - 2, width + 2) - width + 1, -(this.fontRenderer3.getStringHeight(tag) - 1) - 20, Color.WHITE.getRGB());
        GlStateManager.translate(0.0f, 1.0f, 0.0f);
        this.renderArmor(entity, 0, -(Nametags.mc.fontRendererObj.FONT_HEIGHT + 1) - 8);
        GlStateManager.translate(0.0f, -1.0f, 0.0f);
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    public class EnchantEntry
    {
        private Enchantment enchant;
        private String name;
        
        public EnchantEntry(final Enchantment enchant, final String name) {
            this.enchant = enchant;
            this.name = name;
        }
        
        public Enchantment getEnchant() {
            return this.enchant;
        }
        
        public String getName() {
            return this.name;
        }
    }
}
