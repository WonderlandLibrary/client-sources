package wwe;

import java.text.*;
import wwe.utils.*;
import net.minecraft.client.gui.*;
import java.util.concurrent.*;
import org.lwjgl.opengl.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.text.*;
import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.entity.*;
import java.io.*;
import wwe.modules.combat.*;
import net.minecraft.entity.*;

public class sSoG extends GuiScreen
{
    public YEmw Zytq;
    public DateFormat KDAj;
    
    public sSoG(final YEmw zytq) {
        super();
        this.KDAj = new SimpleDateFormat("MM/dd/yy hh:mm:ss");
        this.Zytq = zytq;
    }
    
    public void func_73866_w_() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - Wrapper.fontRenderer.getStringWidth("Remove log out spot") / 2, this.height / 2 + this.height / 4 - 22, Wrapper.fontRenderer.getStringWidth("Remove log out spot") + 4, 20, "Remove log out spot"));
        super.initGui();
    }
    
    public void func_73863_a(final int n, final int n2, final float n3) {
        final long n4 = (this.Zytq.AepW.getTime() - new Date().getTime()) / (0x92C373ED0F6E1CDBL ^ 0x92C373ED0F6E1F33L);
        final int abs = Math.abs((int)TimeUnit.SECONDS.toDays(n4));
        final long abs2 = Math.abs(TimeUnit.SECONDS.toHours(n4) - abs * 24);
        final long abs3 = Math.abs(TimeUnit.SECONDS.toMinutes(n4) - TimeUnit.SECONDS.toHours(n4) * (0xF51C8035E2238DBEL ^ 0xF51C8035E2238D82L));
        final long abs4 = Math.abs(TimeUnit.SECONDS.toSeconds(n4) - TimeUnit.SECONDS.toMinutes(n4) * (0xD143143B504765B1L ^ 0xD143143B5047658DL));
        gnNa.wQYt((float)(this.width / 2 - this.width / 4), (float)(this.height / 2 - this.height / 4), (float)(this.width / 2 + this.width / 4), (float)(this.height / 2 + this.height / 4), -1);
        XybC.dWXO("Log out spot viewer", (float)(this.width / 2 - Wrapper.fontRenderer.getStringWidth("Log out spot viewer") / 2), (float)(this.height / 2 - this.height / 4 + this.height / 36), -16777216);
        XybC.dWXO("Name : " + this.Zytq.AAtC, (float)(this.width / 2 - Wrapper.fontRenderer.getStringWidth("Name : " + this.Zytq.AAtC) / 2), (float)(this.height / 2 - this.height / 4 + this.height / 36 + 10), -16777216);
        XybC.dWXO("Date logged : " + this.KDAj.format(this.Zytq.AepW) + ",", (float)(this.width / 2 - Wrapper.fontRenderer.getStringWidth("Date logged : " + this.KDAj.format(this.Zytq.AepW) + ",") / 2), (float)(this.height / 2 - this.height / 4 + this.height / 36 + 20), -16777216);
        XybC.dWXO("Days " + abs + " Hours " + abs2 + " Minutes " + abs3 + " Seconds " + abs4 + " ago", (float)(this.width / 2 - Wrapper.fontRenderer.getStringWidth("Days " + abs + " Hours " + abs2 + " Minutes " + abs3 + " Seconds " + abs4 + " ago") / 2), (float)(this.height / 2 - this.height / 4 + this.height / 36 + 30), -16777216);
        if (this.Zytq.egIB != null) {
            GlStateManager.disableBlend();
            GL11.glPushMatrix();
            final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
            int n5 = -3;
            int i = 3;
            while (i >= 0) {
                final ItemStack itemStack = (ItemStack)this.Zytq.egIB.inventory.armorInventory.get(i);
                if (itemStack == null) {
                    if (sSoG.\u2006\u200c\u200a\u2002\u200c) {
                        throw null;
                    }
                }
                else {
                    list.add(itemStack);
                }
                --i;
                if (sSoG.\u2006\u200c\u200a\u2002\u200c) {
                    throw null;
                }
            }
            if (this.Zytq.egIB.getHeldItemMainhand() != null) {
                list.add(this.Zytq.egIB.getHeldItemMainhand());
            }
            if (this.Zytq.egIB.getHeldItemOffhand() != null) {
                list.add(this.Zytq.egIB.getHeldItemOffhand());
            }
            for (final ItemStack itemStack2 : list) {
                if (Wrapper.mc.world != null) {
                    RenderHelper.enableGUIStandardItemLighting();
                    n5 += 16;
                }
                GlStateManager.pushMatrix();
                GlStateManager.disableAlpha();
                GlStateManager.clear(256);
                GlStateManager.enableBlend();
                Wrapper.mc.getRenderItem().zLevel = -150.0f;
                Wrapper.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack2, n5 + this.width / 2 - 4, this.height / 2 + this.height / 4 - this.height / 8);
                Wrapper.mc.getRenderItem().renderItemOverlays(Wrapper.fontRenderer, itemStack2, n5 + this.width / 2 - 4, this.height / 2 + this.height / 4 - this.height / 8);
                Wrapper.mc.getRenderItem().zLevel = 0.0f;
                GlStateManager.disableBlend();
                GlStateManager.scale(0.5, 0.5, 0.5);
                GlStateManager.disableDepth();
                GlStateManager.disableLighting();
                GlStateManager.enableDepth();
                GlStateManager.scale(2.0f, 2.0f, 2.0f);
                GlStateManager.enableAlpha();
                GlStateManager.popMatrix();
                int n6 = this.height / 2 + this.height / 4 - this.height / 8 - 12;
                int n7 = n6 + 5;
                final NBTTagList enchantmentTagList = itemStack2.getEnchantmentTagList();
                if (enchantmentTagList != null) {
                    int j = 0;
                    while (j < enchantmentTagList.tagCount()) {
                        final short short1 = enchantmentTagList.getCompoundTagAt(j).getShort("id");
                        final short short2 = enchantmentTagList.getCompoundTagAt(j).getShort("lvl");
                        final Enchantment enchantmentByID = Enchantment.getEnchantmentByID((int)short1);
                        if (enchantmentByID != null) {
                            String s;
                            if (enchantmentByID.isCurse()) {
                                s = TextFormatting.RED + enchantmentByID.getTranslatedName((int)short2).substring(11).substring(0, 1).toLowerCase();
                                if (sSoG.\u2006\u200c\u200a\u2002\u200c) {
                                    throw null;
                                }
                            }
                            else {
                                s = enchantmentByID.getTranslatedName((int)short2).substring(0, 1).toLowerCase();
                            }
                            final String string = s + short2;
                            GL11.glPushMatrix();
                            Wrapper.fontRenderer.drawStringWithShadow(string, (float)(n5 + this.width / 2 - 8 + 6), (float)n7, Color.white.darker().getRGB());
                            GL11.glPopMatrix();
                            n6 += 8;
                            n7 -= 10;
                        }
                        ++j;
                        if (sSoG.\u2006\u200c\u200a\u2002\u200c) {
                            throw null;
                        }
                    }
                }
                if (sSoG.\u2006\u200c\u200a\u2002\u200c) {
                    throw null;
                }
            }
            GL11.glPopMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableAlpha();
            final int n8 = this.width / 2 - this.width / 8;
            final int n9 = this.height / 2 + this.height / 4 - this.height / 8 + 14;
            final int n10 = 20;
            final EntityPlayer egIB = this.Zytq.egIB;
            GlStateManager.enableColorMaterial();
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)n8, (float)n9, 50.0f);
            GlStateManager.scale((float)(-n10), (float)n10, (float)n10);
            GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
            final float renderYawOffset = ((EntityLivingBase)egIB).renderYawOffset;
            final float rotationYaw = ((EntityLivingBase)egIB).rotationYaw;
            final float rotationPitch = ((EntityLivingBase)egIB).rotationPitch;
            final float prevRotationYawHead = ((EntityLivingBase)egIB).prevRotationYawHead;
            final float rotationYawHead = ((EntityLivingBase)egIB).rotationYawHead;
            GlStateManager.rotate(135.0f, 0.0f, 1.0f, 0.0f);
            RenderHelper.enableStandardItemLighting();
            GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(0.0f, 1.0f, 0.0f, 0.0f);
            ((EntityLivingBase)egIB).renderYawOffset = 0.0f;
            ((EntityLivingBase)egIB).rotationYaw = 0.0f;
            ((EntityLivingBase)egIB).rotationPitch = 0.0f;
            ((EntityLivingBase)egIB).rotationYawHead = ((EntityLivingBase)egIB).rotationYaw;
            ((EntityLivingBase)egIB).prevRotationYawHead = ((EntityLivingBase)egIB).rotationYaw;
            GlStateManager.translate(0.0f, 0.0f, 0.0f);
            final RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
            renderManager.setPlayerViewY(180.0f);
            renderManager.setRenderShadow(false);
            renderManager.renderEntity((Entity)egIB, 0.0, 0.0, 0.0, 0.0f, 1.0f, false);
            renderManager.setRenderShadow(true);
            ((EntityLivingBase)egIB).renderYawOffset = renderYawOffset;
            ((EntityLivingBase)egIB).rotationYaw = rotationYaw;
            ((EntityLivingBase)egIB).rotationPitch = rotationPitch;
            ((EntityLivingBase)egIB).prevRotationYawHead = prevRotationYawHead;
            ((EntityLivingBase)egIB).rotationYawHead = rotationYawHead;
            GlStateManager.popMatrix();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.disableTexture2D();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        }
        super.drawScreen(n, n2, n3);
    }
    
    public void func_146281_b() {
        super.onGuiClosed();
    }
    
    public void func_73876_c() {
        super.updateScreen();
    }
    
    public void func_73864_a(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
    }
    
    public void func_146286_b(final int n, final int n2, final int n3) {
        super.mouseReleased(n, n2, n3);
    }
    
    protected void func_73869_a(final char c, final int n) {
        if (n == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    public void func_146284_a(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0 && LogOutSpot.zEsy.contains(this.Zytq)) {
            LogOutSpot.zEsy.remove(this.Zytq);
            Wrapper.mc.displayGuiScreen((GuiScreen)null);
        }
    }
}
