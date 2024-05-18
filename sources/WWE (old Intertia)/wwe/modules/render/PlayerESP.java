package wwe.modules.render;

import wwe.modules.*;
import net.minecraft.util.text.*;
import wwe.utils.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.awt.*;
import java.util.*;
import wwe.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;

public class PlayerESP extends qMIe
{
    public static float CsyR;
    public ModeValue mode;
    
    public PlayerESP() {
        super("Player ESP", "Makes players glow", 0, Category.RENDER);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Box");
        list.add("2D");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Box", list);
    }
    
    @oqrr
    public void CRUW(final RTIo rtIo) {
        if (this.mode.OnGi().equalsIgnoreCase("2D")) {
            this.owgW("Player ESP" + TextFormatting.DARK_GRAY + " 2D");
            for (final Entity entity : Wrapper.mc.world.loadedEntityList) {
                if (entity != Wrapper.mc.player && entity != null && entity.getName() != Wrapper.mc.player.getName() && entity instanceof EntityPlayer) {
                    final float n = (float)((float)entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * yYGD.XgOL().renderPartialTicks);
                    final float n2 = (float)((float)entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * yYGD.XgOL().renderPartialTicks);
                    final float n3 = (float)((float)entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * yYGD.XgOL().renderPartialTicks);
                    Wrapper.mc.player.getDistance(entity);
                    ((EntityLivingBase)entity).getHealth();
                    if (!vlwv.Zqnd().UsVJ(entity.getName())) {
                        this.oMUp(entity, n - yYGD.onRX(), n2 - yYGD.UuJK(), n3 - yYGD.LkVR(), 0.0f, 255.0f, 0.0f, 0.0f, -65536);
                        if (PlayerESP.\u2009\u200d) {
                            throw null;
                        }
                    }
                    else {
                        this.oMUp(entity, n - yYGD.onRX(), n2 - yYGD.UuJK(), n3 - yYGD.LkVR(), 0.0f, 500.0f, 0.0f, 0.0f, KrtY.DnMA(0x7310BF81BB5B0BDCL ^ 0x7310BF81B0B0C9DCL, 1.0f).getRGB());
                    }
                }
                if (PlayerESP.\u2009\u200d) {
                    throw null;
                }
            }
        }
        if (this.mode.OnGi().equalsIgnoreCase("Box")) {
            this.owgW("Player ESP" + TextFormatting.DARK_GRAY + " Box");
            for (final Entity entity2 : Wrapper.mc.world.loadedEntityList) {
                if (entity2 != Wrapper.mc.player && entity2 != null && entity2 instanceof EntityPlayer) {
                    if (vlwv.Zqnd().UsVJ(entity2.getName())) {
                        BIIV.ITDu(entity2, JIfG.JzFV(0x91BFCEC00E7F7E0BL ^ 0x91BFCEC00E7F7E0AL, 1.0f));
                        if (PlayerESP.\u2009\u200d) {
                            throw null;
                        }
                    }
                    else {
                        BIIV.ITDu(entity2, Color.red);
                    }
                }
                if (PlayerESP.\u2009\u200d) {
                    throw null;
                }
            }
        }
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        super.qBmy();
    }
    
    public void oMUp(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5, final float n6, final float n7, final int n8) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(n, n2, n3);
        GL11.glNormal3f(0.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-yYGD.BqWL(), 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(-0.1, -0.1, 0.1);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        kzJj(4.0, -20.0, 7.0, -19.5, n4, n5, n6, n7);
        WIuR(-7.0, -20.0, -4.0, -19.5, n4, n5, n6, n7);
        WIuR(6.5, -20.0, 7.0, -17.5, n4, n5, n6, n7);
        WIuR(-7.0, 2.5, -4.0, 3.0, n4, n5, n6, n7);
        WIuR(4.0, 2.5, 7.0, 3.0, n4, n5, n6, n7);
        WIuR(-7.0, 0.5, -6.5, 3.0, n4, n5, n6, n7);
        WIuR(6.5, 0.5, 7.0, 3.0, n4, n5, n6, n7);
        kpll(7.0, -20.0, 7.300000190734863, -17.5, n8);
        kpll(-7.300000190734863, -20.0, -7.0, -17.5, n8);
        kpll(4.0, -20.299999237060547, 7.300000190734863, -20.0, n8);
        kpll(-7.300000190734863, -20.299999237060547, -4.0, -20.0, n8);
        kpll(-7.0, 3.0, -4.0, 3.299999952316284, n8);
        kpll(4.0, 3.0, 7.0, 3.299999952316284, n8);
        kpll(-7.300000190734863, 0.5, -7.0, 3.299999952316284, n8);
        kpll(7.0, 0.5, 7.300000190734863, 3.299999952316284, n8);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GlStateManager.popMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void kpll(final double n, final double n2, final double n3, final double n4, final int n5) {
        final float n6 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n7 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n8 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n9 = (n5 & 0xFF) / 255.0f;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glPushMatrix();
        GL11.glColor4f(n7, n8, n9, n6);
        GL11.glBegin(7);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void WIuR(final double n, final double n2, final double n3, final double n4, final float n5, final float n6, final float n7, final float n8) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glPushMatrix();
        GL11.glColor4f(n5, n6, n7, n8);
        GL11.glBegin(7);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void kzJj(final double n, final double n2, final double n3, final double n4, final float n5, final float n6, final float n7, final float n8) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glPushMatrix();
        GL11.glColor4f(n5, n6, n7, n8);
        GL11.glBegin(7);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
