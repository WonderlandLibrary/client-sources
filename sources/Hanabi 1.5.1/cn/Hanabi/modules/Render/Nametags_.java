package cn.Hanabi.modules.Render;

import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.events.*;
import net.minecraft.entity.player.*;
import cn.Hanabi.injection.interfaces.*;
import java.util.*;
import com.darkmagician6.eventapi.*;
import net.minecraft.entity.*;
import cn.Hanabi.modules.Combat.*;
import cn.Hanabi.modules.Player.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import ClassSub.*;
import net.minecraft.item.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;

public class Nametags_ extends Mod
{
    private Value<Boolean> invisible;
    
    
    public Nametags_() {
        super("Nametags", Category.RENDER);
        this.invisible = new Value<Boolean>("Nametags_Invisible", false);
    }
    
    @EventTarget
    public void onRender(final EventRender eventRender) {
        for (final EntityPlayer entityPlayer : Nametags_.mc.theWorld.playerEntities) {
            if (entityPlayer != Nametags_.mc.thePlayer) {
                this.renderNameTag(entityPlayer, entityPlayer.getName(), entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * Class211.getTimer().renderPartialTicks - ((IRenderManager)Nametags_.mc.getRenderManager()).getRenderPosX(), entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * Class211.getTimer().renderPartialTicks - ((IRenderManager)Nametags_.mc.getRenderManager()).getRenderPosY(), entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * Class211.getTimer().renderPartialTicks - ((IRenderManager)Nametags_.mc.getRenderManager()).getRenderPosZ());
            }
        }
    }
    
    private void renderNameTag(final EntityPlayer entityPlayer, String getUnformattedText, final double n, double n2, final double n3) {
        if (entityPlayer.isInvisible() && !this.invisible.getValueState()) {
            return;
        }
        final FontRenderer fontRendererObj = Nametags_.mc.fontRendererObj;
        float n4 = Nametags_.mc.thePlayer.getDistanceToEntity((Entity)entityPlayer) / 6.0f;
        if (n4 < 0.8f) {
            n4 = 0.8f;
        }
        n2 += (entityPlayer.isSneaking() ? 0.5 : 0.7);
        final float n5 = n4 * 2.0f / 100.0f;
        getUnformattedText = entityPlayer.getDisplayName().getUnformattedText();
        String s;
        if (AntiBot.isBot((Entity)entityPlayer)) {
            s = "§9[BOT]";
        }
        else {
            s = "";
        }
        String s2;
        if (Teams.isOnSameTeam((Entity)entityPlayer)) {
            s2 = "§b[TEAM]";
        }
        else {
            s2 = "";
        }
        if (Teams.isClientFriend((Entity)entityPlayer)) {
            s2 = "§e[Friend]";
        }
        final String s3 = "";
        if ((s2 + s).equals("")) {
            s2 = "§a";
        }
        final String string = s2 + s + getUnformattedText + s3;
        final String string2 = "§7HP:" + (int)entityPlayer.getHealth();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)n, (float)n2 + 1.4f, (float)n3);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-Nametags_.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(Nametags_.mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(-n5, -n5, n5);
        Class145.setGLCap(2896, false);
        Class145.setGLCap(2929, false);
        final int n6 = Nametags_.mc.fontRendererObj.getStringWidth(string) / 2;
        Class145.setGLCap(3042, true);
        GL11.glBlendFunc(770, 771);
        this.drawBorderedRectNameTag(-n6 - 2, -(Nametags_.mc.fontRendererObj.FONT_HEIGHT + 9), n6 + 2, 2.0f, 1.0f, Class120.reAlpha(Color.BLACK.getRGB(), 0.3f), Class120.reAlpha(Color.BLACK.getRGB(), 0.3f));
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        fontRendererObj.drawString(string, -n6, -(Nametags_.mc.fontRendererObj.FONT_HEIGHT + 8), -1);
        fontRendererObj.drawString(string2, -Nametags_.mc.fontRendererObj.getStringWidth(string2) / 2, -(Nametags_.mc.fontRendererObj.FONT_HEIGHT - 2), -1);
        int rgb = new Color(188, 0, 0).getRGB();
        if (entityPlayer.getHealth() > 20.0f) {
            rgb = -65292;
        }
        Class246.drawRect(n6 + (float)Math.ceil(entityPlayer.getHealth() + entityPlayer.getAbsorptionAmount()) / (entityPlayer.getMaxHealth() + entityPlayer.getAbsorptionAmount()) * n6 * 2.0f - n6 * 2 + 2.0f, 2.0f, -n6 - 2, 0.9f, rgb);
        GL11.glPushMatrix();
        int n7 = 0;
        final ItemStack[] armorInventory = entityPlayer.inventory.armorInventory;
        for (int length = armorInventory.length, i = 0; i < length; ++i) {
            if (armorInventory[i] != null) {
                n7 -= 11;
            }
        }
        if (entityPlayer.getHeldItem() != null) {
            n7 -= 8;
            final ItemStack copy = entityPlayer.getHeldItem().copy();
            if (copy.hasEffect() && (copy.getItem() instanceof ItemTool || copy.getItem() instanceof ItemArmor)) {
                copy.stackSize = 1;
            }
            this.renderItemStack(copy, n7, -35);
            n7 += 20;
        }
        for (final ItemStack itemStack : entityPlayer.inventory.armorInventory) {
            if (itemStack != null) {
                final ItemStack copy2 = itemStack.copy();
                if (copy2.hasEffect() && (copy2.getItem() instanceof ItemTool || copy2.getItem() instanceof ItemArmor)) {
                    copy2.stackSize = 1;
                }
                this.renderItemStack(copy2, n7, -35);
                n7 += 20;
            }
        }
        GL11.glPopMatrix();
        Class145.revertAllCaps();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    public void renderItemStack(final ItemStack itemStack, final int n, final int n2) {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        Nametags_.mc.getRenderItem().zLevel = -150.0f;
        this.whatTheFuckOpenGLThisFixesItemGlint();
        Nametags_.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, n, n2);
        Nametags_.mc.getRenderItem().renderItemOverlays(Nametags_.mc.fontRendererObj, itemStack, n, n2);
        Nametags_.mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }
    
    private void whatTheFuckOpenGLThisFixesItemGlint() {
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
    
    public void drawBorderedRectNameTag(final float n, final float n2, final float n3, final float n4, final float n5, final int n6, final int n7) {
        Class246.drawRect(n, n2, n3, n4, n7);
        final float n8 = (n6 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n6 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n6 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n6 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(n9, n10, n11, n8);
        GL11.glLineWidth(n5);
        GL11.glBegin(1);
        GL11.glVertex2d((double)n, (double)n2);
        GL11.glVertex2d((double)n, (double)n4);
        GL11.glVertex2d((double)n3, (double)n4);
        GL11.glVertex2d((double)n3, (double)n2);
        GL11.glVertex2d((double)n, (double)n2);
        GL11.glVertex2d((double)n3, (double)n2);
        GL11.glVertex2d((double)n, (double)n4);
        GL11.glVertex2d((double)n3, (double)n4);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
}
