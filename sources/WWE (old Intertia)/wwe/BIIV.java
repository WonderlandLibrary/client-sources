package wwe;

import net.minecraft.client.*;
import java.awt.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.math.*;
import wwe.utils.*;
import org.lwjgl.util.glu.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.entity.*;
import me.THEREALWWEFAN231.wwefan.mixin.mixins.interfaces.*;
import net.minecraft.client.shader.*;
import org.lwjgl.opengl.*;

public class BIIV
{
    public static int pVYu;
    public static int ZcYV;
    public static int OXSx;
    public static int lkKu;
    public static int bfRW;
    private static Minecraft loUE;
    
    public BIIV() {
        super();
    }
    
    public static void tUrc(double n, double n2, double n3, double n4, double n5, double n6, final float n7, final float n8, final float n9, final float n10) {
        n -= yYGD.onRX();
        n2 -= yYGD.UuJK();
        n3 -= yYGD.LkVR();
        n4 -= yYGD.onRX();
        n5 -= yYGD.UuJK();
        n6 -= yYGD.LkVR();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glDepthMask(false);
        GL11.glColor4f(n7, n8, n9, n10);
        Igao(new AxisAlignedBB(n, n2, n3, n4, n5, n6), n7, n8, n9, n10);
        GL11.glColor4d(0.0, 0.0, 0.0, 0.5);
        Wupg(new AxisAlignedBB(n, n2, n3, n4, n5, n6));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }
    
    public static void yvCF(double n, double n2, double n3, double n4, double n5, double n6, final Color color) {
        n -= yYGD.onRX();
        n2 -= yYGD.UuJK();
        n3 -= yYGD.LkVR();
        n4 -= yYGD.onRX();
        n5 -= yYGD.UuJK();
        n6 -= yYGD.LkVR();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glDepthMask(false);
        VQeC(color);
        Wupg(new AxisAlignedBB(n, n2, n3, n4, n5, n6));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void hHdz(double n, double n2, double n3, double n4, double n5, double n6, final Color color) {
        n -= yYGD.onRX();
        n2 -= yYGD.UuJK();
        n3 -= yYGD.LkVR();
        n4 -= yYGD.onRX();
        n5 -= yYGD.UuJK();
        n6 -= yYGD.LkVR();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        VQeC(color);
        Wupg(new AxisAlignedBB(n, n2, n3, n4, n5, n6));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }
    
    public static void zIBn(final BlockPos blockPos, final Color color, final double n, final double n2) {
        TGTO(blockPos, color, n, n2);
    }
    
    public static void gDGd(final BlockPos blockPos, final BlockPos blockPos2, final Color color) {
        final double n = blockPos.getX() - yYGD.onRX();
        final double n2 = blockPos.getY() - yYGD.UuJK();
        final double n3 = blockPos.getZ() - yYGD.LkVR();
        GL11.glPushMatrix();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d((double)(color.getRed() / 255.0f), (double)(color.getGreen() / 255.0f), (double)(color.getBlue() / 255.0f), 0.25);
        Igao(new AxisAlignedBB(n, n2, n3, blockPos2.getX() - yYGD.onRX(), blockPos2.getY() - yYGD.UuJK(), blockPos2.getZ() - yYGD.LkVR()), 0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glColor4d(0.0, 0.0, 0.0, 0.5);
        Wupg(new AxisAlignedBB(n, n2, n3, blockPos2.getX() - yYGD.onRX(), blockPos2.getY() - yYGD.UuJK(), blockPos2.getZ() - yYGD.LkVR()));
        GL11.glLineWidth(2.0f);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void TGTO(final BlockPos blockPos, final Color color, final double n, final double n2) {
        final double n3 = blockPos.getX() - yYGD.onRX();
        final double n4 = blockPos.getY() - yYGD.UuJK();
        final double n5 = blockPos.getZ() - yYGD.LkVR();
        GL11.glPushMatrix();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d((double)(color.getRed() / 255.0f), (double)(color.getGreen() / 255.0f), (double)(color.getBlue() / 255.0f), 0.25);
        Igao(new AxisAlignedBB(n3, n4, n5, n3 + n2, n4 + 1.0, n5 + n), 0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glColor4d(0.0, 0.0, 0.0, 0.5);
        Wupg(new AxisAlignedBB(n3, n4, n5, n3 + n2, n4 + 1.0, n5 + n));
        GL11.glLineWidth(2.0f);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void KZGQ(final Entity entity, final Color color) {
        GL11.glPushMatrix();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d((double)(color.getRed() / 255.0f), (double)(color.getGreen() / 255.0f), (double)(color.getBlue() / 255.0f), 0.15000000596046448);
        Minecraft.getMinecraft().getRenderManager();
        Igao(new AxisAlignedBB(entity.getEntityBoundingBox().minX - entity.posX + (entity.posX - yYGD.onRX()), entity.getEntityBoundingBox().minY - entity.posY + (entity.posY - yYGD.UuJK()), entity.getEntityBoundingBox().minZ - entity.posZ + (entity.posZ - yYGD.LkVR()), entity.getEntityBoundingBox().maxX - entity.posX + (entity.posX - yYGD.onRX()), entity.getEntityBoundingBox().maxY - entity.posY + (entity.posY - yYGD.UuJK()), entity.getEntityBoundingBox().maxZ - entity.posZ + (entity.posZ - yYGD.LkVR())), 0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glColor4d(0.0, 0.0, 0.0, 0.5);
        Wupg(new AxisAlignedBB(entity.getEntityBoundingBox().minX - entity.posX + (entity.posX - yYGD.onRX()), entity.getEntityBoundingBox().minY - entity.posY + (entity.posY - yYGD.UuJK()), entity.getEntityBoundingBox().minZ - entity.posZ + (entity.posZ - yYGD.LkVR()), entity.getEntityBoundingBox().maxX - entity.posX + (entity.posX - yYGD.onRX()), entity.getEntityBoundingBox().maxY - entity.posY + (entity.posY - yYGD.UuJK()), entity.getEntityBoundingBox().maxZ - entity.posZ + (entity.posZ - yYGD.LkVR())));
        GL11.glLineWidth(2.0f);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void XrBQ(final BlockPos blockPos, final double n, final double n2, final double n3) {
        final double n4 = blockPos.getX() - yYGD.onRX();
        final double n5 = blockPos.getY() - yYGD.UuJK();
        final double n6 = blockPos.getZ() - yYGD.LkVR();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d(n, n2, n3, 0.15000000596046448);
        Igao(new AxisAlignedBB(n4, n5, n6, n4 + 1.0, n5 + 1.0, n6 + 1.0), 0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }
    
    public static void wAYb(final BlockPos blockPos) {
        final double n = blockPos.getX() - yYGD.onRX();
        final double n2 = blockPos.getY() - yYGD.UuJK();
        final double n3 = blockPos.getZ() - yYGD.LkVR();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d(0.0, 0.0, 0.0, 0.5);
        Wupg(new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 1.0, n3 + 1.0));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }
    
    public static void Lszk(final Entity entity, final Color color) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        Minecraft.getMinecraft().getRenderManager();
        Wupg(new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + (entity.posX - yYGD.onRX()), entity.getEntityBoundingBox().minY - entity.posY + (entity.posY - yYGD.UuJK()), entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + (entity.posZ - yYGD.LkVR()), entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + (entity.posX - yYGD.onRX()), entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + (entity.posY - yYGD.UuJK()), entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + (entity.posZ - yYGD.LkVR())));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void nzVr(final BlockPos blockPos, final float n) {
        final double n2 = blockPos.getX() - yYGD.onRX();
        final double n3 = blockPos.getY() - yYGD.UuJK();
        final double n4 = blockPos.getZ() - yYGD.LkVR();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(n, 1.0f - n, 0.0f, 0.15f);
        Igao(new AxisAlignedBB(n2 + 0.5 - n / 2.0f, n3 + 0.5 - n / 2.0f, n4 + 0.5 - n / 2.0f, n2 + 0.5 + n / 2.0f, n3 + 0.5 + n / 2.0f, n4 + 0.5 + n / 2.0f), n, 1.0f - n, 0.0f, 0.15f);
        GL11.glColor4d(0.0, 0.0, 0.0, 0.5);
        Wupg(new AxisAlignedBB(n2 + 0.5 - n / 2.0f, n3 + 0.5 - n / 2.0f, n4 + 0.5 - n / 2.0f, n2 + 0.5 + n / 2.0f, n3 + 0.5 + n / 2.0f, n4 + 0.5 + n / 2.0f));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }
    
    public static void htka(final BlockPos blockPos) {
        final double n = blockPos.getX() - yYGD.onRX();
        final double n2 = blockPos.getY() - yYGD.UuJK();
        final double n3 = blockPos.getZ() - yYGD.LkVR();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.0f);
        final float n4 = 1.0f - MathHelper.abs(MathHelper.sin(Minecraft.getSystemTime() % (0x4A18DD7F3CA4BDEBL ^ 0x4A18DD7F3CA49AFBL) / 10000.0f * 3.1415927f * 4.0f) * 1.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(1.0f - n4, n4, 0.0f, 0.15f);
        Igao(new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 1.0, n3 + 1.0), 1.0f - n4, n4, 0.0f, 0.15f);
        GL11.glColor4d(0.0, 0.0, 0.0, 0.5);
        Wupg(new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 1.0, n3 + 1.0));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }
    
    public static void Igao(final AxisAlignedBB axisAlignedBB, final float n, final float n2, final float n3, final float n4) {
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer = instance.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        instance.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        instance.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        instance.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        instance.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        instance.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(n, n2, n3, n4).endVertex();
        instance.draw();
    }
    
    public static void DluU(final AxisAlignedBB axisAlignedBB, final Color color) {
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer = instance.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        instance.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        instance.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        instance.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        instance.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        instance.draw();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        instance.draw();
    }
    
    public static void SGOp(final AxisAlignedBB axisAlignedBB, final Color color) {
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer = instance.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        instance.draw();
    }
    
    public static void ihUK(int n, int n2, int n3, final Color color) {
        n += (int)(-yYGD.onRX());
        n2 += (int)(-yYGD.UuJK());
        n3 += (int)(-yYGD.LkVR());
        GL11.glPushMatrix();
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.5f);
        GL11.glColor3f(0.0f, 230.0f, 255.0f);
        final Vec3d rotateYaw = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(Wrapper.mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(Wrapper.mc.player.rotationYaw));
        GL11.glBegin(2);
        GL11.glVertex3d(rotateYaw.x, Wrapper.mc.player.getEyeHeight() + rotateYaw.y, rotateYaw.z);
        GL11.glVertex3d((double)n, (double)n2, (double)n3);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void Wupg(final AxisAlignedBB axisAlignedBB) {
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer = instance.getBuffer();
        buffer.begin(3, DefaultVertexFormats.POSITION);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        instance.draw();
        buffer.begin(3, DefaultVertexFormats.POSITION);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        instance.draw();
        buffer.begin(1, DefaultVertexFormats.POSITION);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        instance.draw();
    }
    
    public static void CCJM(final AxisAlignedBB axisAlignedBB, final Color color) {
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer = instance.getBuffer();
        buffer.begin(3, DefaultVertexFormats.POSITION);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        instance.draw();
        buffer.begin(3, DefaultVertexFormats.POSITION);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        instance.draw();
        buffer.begin(1, DefaultVertexFormats.POSITION);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        instance.draw();
    }
    
    public static void VQeC(final Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    public static float[] sWOr(final Color color) {
        return new float[] { color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getGreen() / 255.0f };
    }
    
    public static void VHBo(final double n, final double n2, final double n3, final float n4, final int n5, final int n6) {
        final Sphere sphere = new Sphere();
        GL11.glPushMatrix();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.2f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        sphere.setDrawStyle(100013);
        GL11.glTranslated(n - yYGD.onRX(), n2 - yYGD.UuJK(), n3 - yYGD.LkVR());
        sphere.draw(n4, n5, n6);
        GL11.glLineWidth(2.0f);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void sfEx(float n, float n2, float n3, float n4, final int n5, final int n6) {
        n *= 2.0f;
        n2 *= 2.0f;
        n3 *= 2.0f;
        n4 *= 2.0f;
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        ChKL(n, n2 + 1.0f, n4 - 2.0f, n5);
        ChKL(n3 - 1.0f, n2 + 1.0f, n4 - 2.0f, n5);
        RhSQ(n + 2.0f, n3 - 3.0f, n2, n5);
        RhSQ(n + 2.0f, n3 - 3.0f, n4 - 1.0f, n5);
        RhSQ(n + 1.0f, n + 1.0f, n2 + 1.0f, n5);
        RhSQ(n3 - 2.0f, n3 - 2.0f, n2 + 1.0f, n5);
        RhSQ(n3 - 2.0f, n3 - 2.0f, n4 - 2.0f, n5);
        RhSQ(n + 1.0f, n + 1.0f, n4 - 2.0f, n5);
        pLEV(n + 1.0f, n2 + 1.0f, n3 - 1.0f, n4 - 1.0f, n6);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public static void KOGI(final int n, final float n2, final int n3, final int n4, final float n5, final int n6, final int n7) {
        pLEV((float)n, n2, (float)n3, (float)n4, n7);
        pLEV((float)n, n2, (float)n3, n2 + n5, n6);
        pLEV((float)n, (float)n4, (float)n3, n4 + n5, n6);
        pLEV((float)n3, n2, n3 + n5, n4 + n5, n6);
        pLEV((float)n, n2, n + n5, n4 + n5, n6);
    }
    
    public static void DBeu(final double n, final double n2, final double n3, final double n4, final float n5, final int n6, final int n7) {
        pLEV((float)n, (float)n2, (float)n3, (float)n4, n7);
        final float n8 = (n6 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n6 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n6 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n6 & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glColor4f(n9, n10, n11, n8);
        GL11.glLineWidth(n5);
        GL11.glBegin(1);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static void RhSQ(float n, float n2, final float n3, final int n4) {
        if (n2 < n) {
            final float n5 = n;
            n = n2;
            n2 = n5;
        }
        pLEV(n, n3, n2 + 1.0f, n3 + 1.0f, n4);
    }
    
    public static void ChKL(final float n, float n2, float n3, final int n4) {
        if (n3 < n2) {
            final float n5 = n2;
            n2 = n3;
            n3 = n5;
        }
        pLEV(n, n2 + 1.0f, n + 1.0f, n3, n4);
    }
    
    public static void pLEV(final float n, final float n2, final float n3, final float n4, final int n5) {
        final float n6 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n7 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n8 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n9 = (n5 & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glColor4f(n7, n8, n9, n6);
        GL11.glBegin(7);
        GL11.glVertex2d((double)n3, (double)n2);
        GL11.glVertex2d((double)n, (double)n2);
        GL11.glVertex2d((double)n, (double)n4);
        GL11.glVertex2d((double)n3, (double)n4);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static void ukDn(final double n, final double n2, final double n3, final double n4, final int n5, final int n6) {
        final float n7 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n8 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n9 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n10 = (n5 & 0xFF) / 255.0f;
        final float n11 = (n6 >> 24 & 0xFF) / 255.0f;
        final float n12 = (n6 >> 16 & 0xFF) / 255.0f;
        final float n13 = (n6 >> 8 & 0xFF) / 255.0f;
        final float n14 = (n6 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(n8, n9, n10, n7);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glColor4f(n12, n13, n14, n11);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }
    
    public static void qYdM(final double n, final double n2, final double n3, final double n4, final float n5, final int n6, final int n7, final int n8) {
        final float n9 = (n6 >> 24 & 0xFF) / 255.0f;
        final float n10 = (n6 >> 16 & 0xFF) / 255.0f;
        final float n11 = (n6 >> 8 & 0xFF) / 255.0f;
        final float n12 = (n6 & 0xFF) / 255.0f;
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(3042);
        GL11.glPushMatrix();
        GL11.glColor4f(n10, n11, n12, n9);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(1);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glEnd();
        GL11.glPopMatrix();
        ukDn(n, n2, n3, n4, n7, n8);
        GL11.glEnable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
    }
    
    public static void cijA(final int n, final int n2, final float n3, final double n4, final float n5, final float n6, final int n7) {
        GL11.glPushMatrix();
        final float n8 = (n7 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n7 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n7 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n7 & 0xFF) / 255.0f;
        GL11.glTranslatef((float)n, (float)n2, 0.0f);
        GL11.glColor4f(n9, n10, n11, n8);
        GL11.glLineWidth(n3);
        GL11.glEnable(3042);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glDisable(3008);
        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glEnable(32925);
        if (n4 > 0.0) {
            GL11.glBegin(3);
            int n12 = 0;
            while (n12 < n4) {
                final float n13 = (float)(n12 * (n4 * 3.141592653589793 / n5));
                GL11.glVertex2f((float)(Math.cos(n13) * n6), (float)(Math.sin(n13) * n6));
                ++n12;
                if (BIIV.\u2008) {
                    throw null;
                }
            }
            GL11.glEnd();
        }
        if (n4 < 0.0) {
            GL11.glBegin(3);
            int n14 = 0;
            while (n14 > n4) {
                final float n15 = (float)(n14 * (n4 * 3.141592653589793 / n5));
                GL11.glVertex2f((float)(Math.cos(n15) * -n6), (float)(Math.sin(n15) * -n6));
                --n14;
                if (BIIV.\u2008) {
                    throw null;
                }
            }
            GL11.glEnd();
        }
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(3008);
        GL11.glEnable(2929);
        GL11.glDisable(32925);
        GL11.glDisable(3479);
        GL11.glPopMatrix();
    }
    
    public static void oqnK(float n, float n2, float n3, final int n4, final int n5) {
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        n3 *= 2.0f;
        n *= 2.0f;
        n2 *= 2.0f;
        final float n6 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n7 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n8 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n9 = (n5 & 0xFF) / 255.0f;
        final float n10 = (float)(6.2831852 / n4);
        final float n11 = (float)Math.cos(n10);
        final float n12 = (float)Math.sin(n10);
        GL11.glColor4f(n7, n8, n9, n6);
        float n13 = n3;
        float n14 = 0.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(2);
        int i = 0;
        while (i < n4) {
            GL11.glVertex2f(n13 + n, n14 + n2);
            final float n15 = n13;
            n13 = n11 * n13 - n12 * n14;
            n14 = n12 * n15 + n11 * n14;
            ++i;
            if (BIIV.\u2008) {
                throw null;
            }
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public static void HBET(int n, int n2, double n3, final int n4) {
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        n3 *= 2.0;
        n *= 2;
        n2 *= 2;
        final float n5 = (n4 >> 24 & 0xFF) / 255.0f;
        final float n6 = (n4 >> 16 & 0xFF) / 255.0f;
        final float n7 = (n4 >> 8 & 0xFF) / 255.0f;
        final float n8 = (n4 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(n6, n7, n8, n5);
        GL11.glBegin(6);
        int i = 0;
        while (i <= 360) {
            GL11.glVertex2d(n + Math.sin(i * 3.141592653589793 / 180.0) * n3, n2 + Math.cos(i * 3.141592653589793 / 180.0) * n3);
            ++i;
            if (BIIV.\u2008) {
                throw null;
            }
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public static void imZq(final AxisAlignedBB axisAlignedBB) {
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer = instance.getBuffer();
        GlStateManager.color(0.0f, 0.0f, 255.0f, 255.0f);
        GlStateManager.disableTexture2D();
        GlStateManager.enableColorLogic();
        GlStateManager.colorLogicOp(GlStateManager.LogicOp.OR_REVERSE);
        buffer.begin(3, DefaultVertexFormats.POSITION);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        instance.draw();
        buffer.begin(3, DefaultVertexFormats.POSITION);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        instance.draw();
        buffer.begin(1, DefaultVertexFormats.POSITION);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        instance.draw();
        GlStateManager.disableColorLogic();
        GlStateManager.enableTexture2D();
    }
    
    public static void EvDD(final AxisAlignedBB axisAlignedBB) {
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer = instance.getBuffer();
        GlStateManager.color(0.0f, 0.0f, 255.0f, 255.0f);
        GlStateManager.disableTexture2D();
        GlStateManager.enableColorLogic();
        GlStateManager.colorLogicOp(GlStateManager.LogicOp.OR_REVERSE);
        buffer.begin(3, DefaultVertexFormats.POSITION);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        instance.draw();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        instance.draw();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        instance.draw();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        instance.draw();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        instance.draw();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
        instance.draw();
        GlStateManager.disableColorLogic();
        GlStateManager.enableTexture2D();
    }
    
    public static void azcZ(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.5f);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glLineWidth(1.0f);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d(n4, n6, n5, 0.18250000476837158);
        Igao(new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 1.0, n3 + 1.0), 0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glColor4d(0.0, 0.0, 0.0, 0.5);
        Wupg(new AxisAlignedBB(n, n2, n3, n + 1.0, n2 + 1.0, n3 + 1.0));
        GL11.glLineWidth(2.0f);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void fWdo(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final double n8) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.5f);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glLineWidth(1.0f);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d(n4, n6, n5, 0.15);
        Igao(new AxisAlignedBB(n, n2, n3, n + n8, n2 + 1.0, n3 + n7), 0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glColor4d(0.0, 0.0, 0.0, 0.5);
        Wupg(new AxisAlignedBB(n, n2, n3, n + n8, n2 + 1.0, n3 + n7));
        GL11.glLineWidth(2.0f);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void QzNo(final FontRenderer fontRenderer, final String s, final float n, final float n2, final float n3, final int n4, final float n5, final float n6, final boolean b) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(n, n2, n3);
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-n5, 0.0f, 1.0f, 0.0f);
        int n7;
        if (b) {
            n7 = -1;
            if (BIIV.\u2008) {
                throw null;
            }
        }
        else {
            n7 = 1;
        }
        GlStateManager.rotate(n7 * n6, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-0.025f, -0.025f, 0.025f);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        final int n8 = fontRenderer.getStringWidth(s) / 2;
        GlStateManager.disableTexture2D();
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer = instance.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos((double)(-n8 - 1), (double)(-1 + n4), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        buffer.pos((double)(-n8 - 1), (double)(8 + n4), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        buffer.pos((double)(n8 + 1), (double)(8 + n4), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        buffer.pos((double)(n8 + 1), (double)(-1 + n4), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        instance.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        fontRenderer.drawString(s, -fontRenderer.getStringWidth(s) / 2, n4, -1);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
    }
    
    public static void ITDu(final Entity entity, final Color color) {
        GL11.glPushMatrix();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d((double)(color.getRed() / 255.0f), (double)(color.getGreen() / 255.0f), (double)(color.getBlue() / 255.0f), 0.15000000596046448);
        Minecraft.getMinecraft().getRenderManager();
        Igao(new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + (entity.posX - yYGD.onRX()), entity.getEntityBoundingBox().minY - entity.posY + (entity.posY - yYGD.UuJK()), entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + (entity.posZ - yYGD.LkVR()), entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + (entity.posX - yYGD.onRX()), entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + (entity.posY - yYGD.UuJK()), entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + (entity.posZ - yYGD.LkVR())), 0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glColor4d(0.0, 0.0, 0.0, 0.5);
        Wupg(new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + (entity.posX - yYGD.onRX()), entity.getEntityBoundingBox().minY - entity.posY + (entity.posY - yYGD.UuJK()), entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + (entity.posZ - yYGD.LkVR()), entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + (entity.posX - yYGD.onRX()), entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + (entity.posY - yYGD.UuJK()), entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + (entity.posZ - yYGD.LkVR())));
        GL11.glLineWidth(2.0f);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void Slzx(final double n, final double n2, final double n3, final EntityPlayer entityPlayer, final double n4, final double n5) {
        if (!(entityPlayer instanceof EntityPlayerSP)) {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glColor4f(0.7f, 0.0f, 0.0f, 0.15f);
            GL11.glPushMatrix();
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glLineWidth(1.0f);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2848);
            EvDD(new AxisAlignedBB(n - n5, n2 + 0.1, n3 - n5, n + n5, n2 + n4 + 0.25, n3 + n5));
            GL11.glColor4f(0.7f, 0.0f, 0.0f, 1.0f);
            imZq(new AxisAlignedBB(n - n5, n2 + 0.1, n3 - n5, n + n5, n2 + n4 + 0.25, n3 + n5));
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glEnable(3553);
            GL11.glEnable(2896);
            GL11.glDisable(2848);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
    }
    
    public static void njYD(final BlockPos blockPos, final double n, final double n2, final double n3) {
        final double n4 = blockPos.getX() - yYGD.onRX();
        final double n5 = blockPos.getY() - yYGD.UuJK();
        final double n6 = blockPos.getZ() - yYGD.LkVR();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d(n, n2, n3, 0.15000000596046448);
        Igao(new AxisAlignedBB(n4, n5, n6, n4 + 1.0, n5 + 2.0, n6 + 1.0), 0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }
    
    public static void kSPT(final BlockPos blockPos, final Color color, final double n, final double n2) {
        final double n3 = blockPos.getX() - yYGD.onRX();
        final double n4 = blockPos.getY() - yYGD.UuJK();
        final double n5 = blockPos.getZ() - yYGD.LkVR();
        FuEJ();
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(3.0f);
        GL11.glEnable(2848);
        GL11.glEnable(2960);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6913);
        GL11.glStencilFunc(512, 0, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6914);
        GL11.glStencilFunc(514, 1, 15);
        GL11.glStencilOp(7680, 7680, 7680);
        GL11.glPolygonMode(1032, 6913);
        GL11.glPolygonOffset(1.0f, 2000000.0f);
        GL11.glDisable(10754);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(2960);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glEnable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glPopAttrib();
    }
    
    public static void tDuv(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final int n7, final int n8, final float[] array) {
        JkIr(n, n2, n3, n4, n5, n6, n7, n8, array, 0.2, 0.25);
    }
    
    public static void JkIr(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final int n7, final int n8, final float[] array, final double n9, final double n10) {
        final int n11 = n7 + n8;
        GlStateManager.glTexParameteri(3553, 10242, 10497);
        GlStateManager.glTexParameteri(3553, 10243, 10497);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        final Tessellator instance = Tessellator.getInstance();
        final BufferBuilder buffer = instance.getBuffer();
        final double n12 = n6 + n4;
        double n13;
        if (n8 < 0) {
            n13 = n12;
            if (BIIV.\u2008) {
                throw null;
            }
        }
        else {
            n13 = -n12;
        }
        final double n14 = n13;
        final double frac = MathHelper.frac(n14 * 0.2 - MathHelper.floor(n14 * 0.1));
        final float n15 = array[0];
        final float n16 = array[1];
        final float n17 = array[2];
        final double n18 = n12 * 0.025 * -1.5;
        final double n19 = 0.5 + Math.cos(n18 + 2.356194490192345) * n9;
        final double n20 = 0.5 + Math.sin(n18 + 2.356194490192345) * n9;
        final double n21 = 0.5 + Math.cos(n18 + 0.7853981633974483) * n9;
        final double n22 = 0.5 + Math.sin(n18 + 0.7853981633974483) * n9;
        final double n23 = 0.5 + Math.cos(n18 + 3.9269908169872414) * n9;
        final double n24 = 0.5 + Math.sin(n18 + 3.9269908169872414) * n9;
        final double n25 = 0.5 + Math.cos(n18 + 5.497787143782138) * n9;
        final double n26 = 0.5 + Math.sin(n18 + 5.497787143782138) * n9;
        final double n27 = -1.0 + frac;
        final double n28 = n8 * n5 * (0.5 / n9) + n27;
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(n + n19, n2 + n11, n3 + n20).tex(1.0, n28).color(n15, n16, n17, 1.0f).endVertex();
        buffer.pos(n + n19, n2 + n7, n3 + n20).tex(1.0, n27).color(n15, n16, n17, 1.0f).endVertex();
        buffer.pos(n + n21, n2 + n7, n3 + n22).tex(0.0, n27).color(n15, n16, n17, 1.0f).endVertex();
        buffer.pos(n + n21, n2 + n11, n3 + n22).tex(0.0, n28).color(n15, n16, n17, 1.0f).endVertex();
        buffer.pos(n + n25, n2 + n11, n3 + n26).tex(1.0, n28).color(n15, n16, n17, 1.0f).endVertex();
        buffer.pos(n + n25, n2 + n7, n3 + n26).tex(1.0, n27).color(n15, n16, n17, 1.0f).endVertex();
        buffer.pos(n + n23, n2 + n7, n3 + n24).tex(0.0, n27).color(n15, n16, n17, 1.0f).endVertex();
        buffer.pos(n + n23, n2 + n11, n3 + n24).tex(0.0, n28).color(n15, n16, n17, 1.0f).endVertex();
        buffer.pos(n + n21, n2 + n11, n3 + n22).tex(1.0, n28).color(n15, n16, n17, 1.0f).endVertex();
        buffer.pos(n + n21, n2 + n7, n3 + n22).tex(1.0, n27).color(n15, n16, n17, 1.0f).endVertex();
        buffer.pos(n + n25, n2 + n7, n3 + n26).tex(0.0, n27).color(n15, n16, n17, 1.0f).endVertex();
        buffer.pos(n + n25, n2 + n11, n3 + n26).tex(0.0, n28).color(n15, n16, n17, 1.0f).endVertex();
        buffer.pos(n + n23, n2 + n11, n3 + n24).tex(1.0, n28).color(n15, n16, n17, 1.0f).endVertex();
        buffer.pos(n + n23, n2 + n7, n3 + n24).tex(1.0, n27).color(n15, n16, n17, 1.0f).endVertex();
        buffer.pos(n + n19, n2 + n7, n3 + n20).tex(0.0, n27).color(n15, n16, n17, 1.0f).endVertex();
        buffer.pos(n + n19, n2 + n11, n3 + n20).tex(0.0, n28).color(n15, n16, n17, 1.0f).endVertex();
        instance.draw();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.depthMask(false);
        final double n29 = 0.5 - n10;
        final double n30 = 0.5 - n10;
        final double n31 = 0.5 + n10;
        final double n32 = 0.5 - n10;
        final double n33 = 0.5 - n10;
        final double n34 = 0.5 + n10;
        final double n35 = 0.5 + n10;
        final double n36 = 0.5 + n10;
        final double n37 = -1.0 + frac;
        final double n38 = n8 * n5 + n37;
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(n + n29, n2 + n11, n3 + n30).tex(1.0, n38).color(n15, n16, n17, 0.125f).endVertex();
        buffer.pos(n + n29, n2 + n7, n3 + n30).tex(1.0, n37).color(n15, n16, n17, 0.125f).endVertex();
        buffer.pos(n + n31, n2 + n7, n3 + n32).tex(0.0, n37).color(n15, n16, n17, 0.125f).endVertex();
        buffer.pos(n + n31, n2 + n11, n3 + n32).tex(0.0, n38).color(n15, n16, n17, 0.125f).endVertex();
        buffer.pos(n + n35, n2 + n11, n3 + n36).tex(1.0, n38).color(n15, n16, n17, 0.125f).endVertex();
        buffer.pos(n + n35, n2 + n7, n3 + n36).tex(1.0, n37).color(n15, n16, n17, 0.125f).endVertex();
        buffer.pos(n + n33, n2 + n7, n3 + n34).tex(0.0, n37).color(n15, n16, n17, 0.125f).endVertex();
        buffer.pos(n + n33, n2 + n11, n3 + n34).tex(0.0, n38).color(n15, n16, n17, 0.125f).endVertex();
        buffer.pos(n + n31, n2 + n11, n3 + n32).tex(1.0, n38).color(n15, n16, n17, 0.125f).endVertex();
        buffer.pos(n + n31, n2 + n7, n3 + n32).tex(1.0, n37).color(n15, n16, n17, 0.125f).endVertex();
        buffer.pos(n + n35, n2 + n7, n3 + n36).tex(0.0, n37).color(n15, n16, n17, 0.125f).endVertex();
        buffer.pos(n + n35, n2 + n11, n3 + n36).tex(0.0, n38).color(n15, n16, n17, 0.125f).endVertex();
        buffer.pos(n + n33, n2 + n11, n3 + n34).tex(1.0, n38).color(n15, n16, n17, 0.125f).endVertex();
        buffer.pos(n + n33, n2 + n7, n3 + n34).tex(1.0, n37).color(n15, n16, n17, 0.125f).endVertex();
        buffer.pos(n + n29, n2 + n7, n3 + n30).tex(0.0, n37).color(n15, n16, n17, 0.125f).endVertex();
        buffer.pos(n + n29, n2 + n11, n3 + n30).tex(0.0, n38).color(n15, n16, n17, 0.125f).endVertex();
        instance.draw();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
    }
    
    public static void nieu(final BlockPos blockPos, final float n, final float n2, final float n3) {
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        ((InterfaceMixinEntityRenderer)Wrapper.mc.entityRenderer).orientCameraVoid(yYGD.XgOL().renderPartialTicks);
        GL11.glPushMatrix();
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.5f);
        GL11.glColor3f(n, n2, n3);
        final Vec3d rotateYaw = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(Wrapper.mc.player.rotationPitch)).rotateYaw(-(float)Math.toRadians(Wrapper.mc.player.rotationYaw));
        GL11.glBegin(2);
        GL11.glVertex3d(rotateYaw.x, Wrapper.mc.player.getEyeHeight() + rotateYaw.y, rotateYaw.z);
        GL11.glVertex3d(blockPos.getX() + 0.5 - yYGD.onRX(), blockPos.getY() - yYGD.UuJK(), blockPos.getZ() - yYGD.LkVR() + 0.5);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    public static void FuEJ() {
        final Framebuffer framebuffer = Minecraft.getMinecraft().getFramebuffer();
        if (framebuffer != null && framebuffer.depthBuffer > -1) {
            RzzU(framebuffer);
            framebuffer.depthBuffer = -1;
        }
    }
    
    public static void RzzU(final Framebuffer framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(framebuffer.depthBuffer);
        final int glGenRenderbuffersEXT = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, glGenRenderbuffersEXT);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, glGenRenderbuffersEXT);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, glGenRenderbuffersEXT);
    }
    
    public static void Xfgz(final double n, final double n2, final double n3) {
        final double n4 = n - yYGD.onRX();
        final double n5 = n2 - yYGD.UuJK();
        final double n6 = n3 - yYGD.LkVR();
        GL11.glPushMatrix();
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.0f);
        GL11.glColor3f(189.0f, 0.0f, 0.0f);
        GL11.glBegin(2);
        GL11.glVertex3d(n4, n5, n6);
        GL11.glVertex3d(n4 + 16.0, n5, n6);
        GL11.glVertex3d(n4 + 16.0, n5, n6);
        GL11.glVertex3d(n4, n5, n6);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(n4, n5, n6);
        GL11.glVertex3d(n4, n5, n6 + 16.0);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(n4, n5, n6 + 16.0);
        GL11.glVertex3d(n4 + 16.0, n5, n6 + 16.0);
        GL11.glVertex3d(n4 + 16.0, n5, n6 + 16.0);
        GL11.glVertex3d(n4, n5, n6 + 16.0);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(n4 + 16.0, n5, n6 + 16.0);
        GL11.glVertex3d(n4 + 16.0, n5, n6);
        GL11.glVertex3d(n4 + 16.0, n5, n6);
        GL11.glVertex3d(n4 + 16.0, n5, n6 + 16.0);
        GL11.glColor3f(189.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    static {
        BIIV.pVYu = 0;
        BIIV.ZcYV = 1;
        BIIV.OXSx = 2;
        BIIV.lkKu = 3;
        BIIV.bfRW = 4;
        BIIV.loUE = Wrapper.mc;
    }
}
