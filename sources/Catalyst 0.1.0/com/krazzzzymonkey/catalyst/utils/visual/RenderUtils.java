// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils.visual;

import java.util.Iterator;
import com.krazzzzymonkey.catalyst.module.modules.gui.ClickGui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.Vec3d;
import com.krazzzzymonkey.catalyst.module.modules.player.Scaffold;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import net.minecraft.block.state.IBlockState;
import com.krazzzzymonkey.catalyst.xray.XRayData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.World;
import java.awt.Color;
import com.krazzzzymonkey.catalyst.xray.XRayBlock;
import java.util.LinkedList;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import com.krazzzzymonkey.catalyst.utils.TimerUtils;

public class RenderUtils
{
    public static /* synthetic */ int splashTickPos;
    public static /* synthetic */ TimerUtils splashTimer;
    public static /* synthetic */ boolean isSplash;
    private static final /* synthetic */ int[] llIIlIIl;
    private static final /* synthetic */ String[] llIIIlll;
    private static final /* synthetic */ AxisAlignedBB DEFAULT_AABB;
    
    public static void drawESP(final Entity lIlllIlllllIlll, final float lIlllIlllllIllI, final float lIlllIllllllIll, final float lIlllIllllllIlI, final float lIlllIlllllIIll, final float lIlllIllllllIII) {
        try {
            final double lIllllIIIIIIlll = Wrapper.INSTANCE.mc().getRenderManager().viewerPosX;
            final double lIllllIIIIIIllI = Wrapper.INSTANCE.mc().getRenderManager().viewerPosY;
            final double lIllllIIIIIIlIl = Wrapper.INSTANCE.mc().getRenderManager().viewerPosZ;
            final double lIllllIIIIIIlII = lIlllIlllllIlll.lastTickPosX + (lIlllIlllllIlll.posX - lIlllIlllllIlll.lastTickPosX) * lIlllIllllllIII - lIllllIIIIIIlll;
            final double lIllllIIIIIIIll = lIlllIlllllIlll.lastTickPosY + (lIlllIlllllIlll.posY - lIlllIlllllIlll.lastTickPosY) * lIlllIllllllIII + lIlllIlllllIlll.height / 2.0f - lIllllIIIIIIllI;
            final double lIllllIIIIIIIlI = lIlllIlllllIlll.lastTickPosZ + (lIlllIlllllIlll.posZ - lIlllIlllllIlll.lastTickPosZ) * lIlllIllllllIII - lIllllIIIIIIlIl;
            final float lIllllIIIIIIIIl = Wrapper.INSTANCE.mc().getRenderManager().playerViewY;
            final float lIllllIIIIIIIII = Wrapper.INSTANCE.mc().getRenderManager().playerViewX;
            int n;
            if (lIIlIlllIl(Wrapper.INSTANCE.mc().getRenderManager().options.thirdPersonView, RenderUtils.llIIlIIl[1])) {
                n = RenderUtils.llIIlIIl[3];
                "".length();
                if (-" ".length() == "   ".length()) {
                    return;
                }
            }
            else {
                n = RenderUtils.llIIlIIl[0];
            }
            final boolean lIlllIlllllllll = n != 0;
            GL11.glPushMatrix();
            GlStateManager.translate(lIllllIIIIIIlII, lIllllIIIIIIIll, lIllllIIIIIIIlI);
            GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-lIllllIIIIIIIIl, 0.0f, 1.0f, 0.0f);
            int n2;
            if (lIIlIlllII(lIlllIlllllllll ? 1 : 0)) {
                n2 = RenderUtils.llIIlIIl[15];
                "".length();
                if (null != null) {
                    return;
                }
            }
            else {
                n2 = RenderUtils.llIIlIIl[3];
            }
            GlStateManager.rotate(n2 * lIllllIIIIIIIII, 1.0f, 0.0f, 0.0f);
            GL11.glEnable(RenderUtils.llIIlIIl[12]);
            GL11.glDisable(RenderUtils.llIIlIIl[8]);
            GL11.glDisable(RenderUtils.llIIlIIl[13]);
            GL11.glDisable(RenderUtils.llIIlIIl[14]);
            GL11.glDepthMask((boolean)(RenderUtils.llIIlIIl[0] != 0));
            GL11.glLineWidth(1.0f);
            GL11.glBlendFunc(RenderUtils.llIIlIIl[9], RenderUtils.llIIlIIl[10]);
            GL11.glEnable(RenderUtils.llIIlIIl[11]);
            GL11.glColor4f(lIlllIlllllIllI, lIlllIllllllIll, lIlllIllllllIlI, lIlllIlllllIIll);
            GL11.glBegin(RenderUtils.llIIlIIl[3]);
            GL11.glVertex3d(0.0, 1.0, 0.0);
            GL11.glVertex3d(-0.5, 0.5, 0.0);
            GL11.glVertex3d(0.0, 1.0, 0.0);
            GL11.glVertex3d(0.5, 0.5, 0.0);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(-0.5, 0.5, 0.0);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.5, 0.5, 0.0);
            GL11.glEnd();
            GL11.glDepthMask((boolean)(RenderUtils.llIIlIIl[3] != 0));
            GL11.glEnable(RenderUtils.llIIlIIl[14]);
            GL11.glEnable(RenderUtils.llIIlIIl[8]);
            GL11.glEnable(RenderUtils.llIIlIIl[13]);
            GL11.glDisable(RenderUtils.llIIlIIl[11]);
            GL11.glDisable(RenderUtils.llIIlIIl[12]);
            GL11.glPopMatrix();
            "".length();
            if ("   ".length() == (0x45 ^ 0x41)) {
                return;
            }
        }
        catch (Exception lIlllIllllllllI) {
            lIlllIllllllllI.printStackTrace();
        }
    }
    
    private static int lIIllIIlII(final float n, final float n2) {
        return fcmpg(n, n2);
    }
    
    public static void drawHLine(float lIlllIlIIlIlIIl, float lIlllIlIIlIlIII, final float lIlllIlIIlIIlll, final int lIlllIlIIlIlIlI) {
        if (lIIllIIIlI(lIIllIIIII(lIlllIlIIlIlIII, lIlllIlIIlIlIIl))) {
            final float lIlllIlIIlIlllI = lIlllIlIIlIlIIl;
            lIlllIlIIlIlIIl = lIlllIlIIlIlIII;
            lIlllIlIIlIlIII = lIlllIlIIlIlllI;
        }
        drawRect(lIlllIlIIlIlIIl, lIlllIlIIlIIlll, lIlllIlIIlIlIII + 1.0f, lIlllIlIIlIIlll + 1.0f, lIlllIlIIlIlIlI);
    }
    
    private static String lIIlIlIlIl(final String lIlllIIlllllIll, final String lIlllIIlllllIII) {
        try {
            final SecretKeySpec lIlllIIlllllllI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(lIlllIIlllllIII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher lIlllIIllllllIl = Cipher.getInstance("Blowfish");
            lIlllIIllllllIl.init(RenderUtils.llIIlIIl[1], lIlllIIlllllllI);
            return new String(lIlllIIllllllIl.doFinal(Base64.getDecoder().decode(lIlllIIlllllIll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception lIlllIIllllllII) {
            lIlllIIllllllII.printStackTrace();
            return null;
        }
    }
    
    public static void drawSelectionBoundingBox(final AxisAlignedBB lIlllIlIllllllI) {
        final Tessellator lIlllIlIlllllIl = Tessellator.getInstance();
        final BufferBuilder lIlllIlIlllllII = lIlllIlIlllllIl.getBuffer();
        lIlllIlIlllllII.begin(RenderUtils.llIIlIIl[17], DefaultVertexFormats.POSITION);
        lIlllIlIlllllII.pos(lIlllIlIllllllI.minX, lIlllIlIllllllI.minY, lIlllIlIllllllI.minZ).endVertex();
        lIlllIlIlllllII.pos(lIlllIlIllllllI.maxX, lIlllIlIllllllI.minY, lIlllIlIllllllI.minZ).endVertex();
        lIlllIlIlllllII.pos(lIlllIlIllllllI.maxX, lIlllIlIllllllI.minY, lIlllIlIllllllI.maxZ).endVertex();
        lIlllIlIlllllII.pos(lIlllIlIllllllI.minX, lIlllIlIllllllI.minY, lIlllIlIllllllI.maxZ).endVertex();
        lIlllIlIlllllII.pos(lIlllIlIllllllI.minX, lIlllIlIllllllI.minY, lIlllIlIllllllI.minZ).endVertex();
        lIlllIlIlllllIl.draw();
        lIlllIlIlllllII.begin(RenderUtils.llIIlIIl[17], DefaultVertexFormats.POSITION);
        lIlllIlIlllllII.pos(lIlllIlIllllllI.minX, lIlllIlIllllllI.maxY, lIlllIlIllllllI.minZ).endVertex();
        lIlllIlIlllllII.pos(lIlllIlIllllllI.maxX, lIlllIlIllllllI.maxY, lIlllIlIllllllI.minZ).endVertex();
        lIlllIlIlllllII.pos(lIlllIlIllllllI.maxX, lIlllIlIllllllI.maxY, lIlllIlIllllllI.maxZ).endVertex();
        lIlllIlIlllllII.pos(lIlllIlIllllllI.minX, lIlllIlIllllllI.maxY, lIlllIlIllllllI.maxZ).endVertex();
        lIlllIlIlllllII.pos(lIlllIlIllllllI.minX, lIlllIlIllllllI.maxY, lIlllIlIllllllI.minZ).endVertex();
        lIlllIlIlllllIl.draw();
        lIlllIlIlllllII.begin(RenderUtils.llIIlIIl[3], DefaultVertexFormats.POSITION);
        lIlllIlIlllllII.pos(lIlllIlIllllllI.minX, lIlllIlIllllllI.minY, lIlllIlIllllllI.minZ).endVertex();
        lIlllIlIlllllII.pos(lIlllIlIllllllI.minX, lIlllIlIllllllI.maxY, lIlllIlIllllllI.minZ).endVertex();
        lIlllIlIlllllII.pos(lIlllIlIllllllI.maxX, lIlllIlIllllllI.minY, lIlllIlIllllllI.minZ).endVertex();
        lIlllIlIlllllII.pos(lIlllIlIllllllI.maxX, lIlllIlIllllllI.maxY, lIlllIlIllllllI.minZ).endVertex();
        lIlllIlIlllllII.pos(lIlllIlIllllllI.maxX, lIlllIlIllllllI.minY, lIlllIlIllllllI.maxZ).endVertex();
        lIlllIlIlllllII.pos(lIlllIlIllllllI.maxX, lIlllIlIllllllI.maxY, lIlllIlIllllllI.maxZ).endVertex();
        lIlllIlIlllllII.pos(lIlllIlIllllllI.minX, lIlllIlIllllllI.minY, lIlllIlIllllllI.maxZ).endVertex();
        lIlllIlIlllllII.pos(lIlllIlIllllllI.minX, lIlllIlIllllllI.maxY, lIlllIlIllllllI.maxZ).endVertex();
        lIlllIlIlllllIl.draw();
    }
    
    public static void drawXRayBlocks(final LinkedList<XRayBlock> lIlllIllIlIIlII, final float lIlllIllIlIIlll) {
        GL11.glPushMatrix();
        GL11.glEnable(RenderUtils.llIIlIIl[12]);
        GL11.glBlendFunc(RenderUtils.llIIlIIl[9], RenderUtils.llIIlIIl[10]);
        GL11.glEnable(RenderUtils.llIIlIIl[11]);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(RenderUtils.llIIlIIl[8]);
        GL11.glEnable(RenderUtils.llIIlIIl[16]);
        GL11.glDisable(RenderUtils.llIIlIIl[14]);
        GL11.glDisable(RenderUtils.llIIlIIl[13]);
        final WorldClient lIlllIllIlIIllI = Wrapper.INSTANCE.world();
        final EntityPlayerSP lIlllIllIlIIlIl = Wrapper.INSTANCE.player();
        final byte lIlllIllIlIIIII = (byte)lIlllIllIlIIlII.iterator();
        while (lIIlIlllII(((Iterator)lIlllIllIlIIIII).hasNext() ? 1 : 0)) {
            final XRayBlock lIlllIllIlIlIIl = ((Iterator<XRayBlock>)lIlllIllIlIIIII).next();
            final BlockPos lIlllIllIllIIIl = lIlllIllIlIlIIl.getBlockPos();
            final XRayData lIlllIllIllIIII = lIlllIllIlIlIIl.getxRayData();
            final IBlockState lIlllIllIlIllll = lIlllIllIlIIllI.getBlockState(lIlllIllIllIIIl);
            final double lIlllIllIlIlllI = lIlllIllIlIIlIl.lastTickPosX + (lIlllIllIlIIlIl.posX - lIlllIllIlIIlIl.lastTickPosX) * lIlllIllIlIIlll;
            final double lIlllIllIlIllIl = lIlllIllIlIIlIl.lastTickPosY + (lIlllIllIlIIlIl.posY - lIlllIllIlIIlIl.lastTickPosY) * lIlllIllIlIIlll;
            final double lIlllIllIlIllII = lIlllIllIlIIlIl.lastTickPosZ + (lIlllIllIlIIlIl.posZ - lIlllIllIlIIlIl.lastTickPosZ) * lIlllIllIlIIlll;
            final int lIlllIllIlIlIll = new Color(lIlllIllIllIIII.getRed(), lIlllIllIllIIII.getGreen(), lIlllIllIllIIII.getBlue(), RenderUtils.llIIlIIl[5]).getRGB();
            GLUtils.glColor(lIlllIllIlIlIll);
            final AxisAlignedBB lIlllIllIlIlIlI = lIlllIllIlIllll.getSelectedBoundingBox((World)lIlllIllIlIIllI, lIlllIllIllIIIl).grow(0.0020000000949949026).offset(-lIlllIllIlIlllI, -lIlllIllIlIllIl, -lIlllIllIlIllII);
            drawSelectionBoundingBox(lIlllIllIlIlIlI);
            "".length();
            if ((0xB ^ 0xF) != (0x58 ^ 0x5C)) {
                return;
            }
        }
        GL11.glEnable(RenderUtils.llIIlIIl[13]);
        GL11.glEnable(RenderUtils.llIIlIIl[14]);
        GL11.glEnable(RenderUtils.llIIlIIl[8]);
        GL11.glDisable(RenderUtils.llIIlIIl[12]);
        GL11.glDisable(RenderUtils.llIIlIIl[11]);
        GL11.glPopMatrix();
    }
    
    public static void drawStringWithRect(final String lIllllIlIIlllIl, final int lIllllIlIlIIIlI, final int lIllllIlIlIIIIl, final int lIllllIlIIllIlI, final int lIllllIlIIllIIl, final int lIllllIlIIllIII) {
        drawBorderedRect(lIllllIlIlIIIlI - RenderUtils.llIIlIIl[1], lIllllIlIlIIIIl - RenderUtils.llIIlIIl[1], lIllllIlIlIIIlI + Wrapper.INSTANCE.fontRenderer().getStringWidth(lIllllIlIIlllIl) + RenderUtils.llIIlIIl[1], lIllllIlIlIIIIl + RenderUtils.llIIlIIl[2], 1.0f, lIllllIlIIllIIl, lIllllIlIIllIII);
        Wrapper.INSTANCE.fontRenderer().drawString(lIllllIlIIlllIl, lIllllIlIlIIIlI, lIllllIlIlIIIIl, lIllllIlIIllIlI);
        "".length();
    }
    
    public static void drawOutlinedBox(final AxisAlignedBB lIlllIlIlIllllI) {
        GL11.glBegin(RenderUtils.llIIlIIl[3]);
        GL11.glVertex3d(lIlllIlIlIllllI.minX, lIlllIlIlIllllI.minY, lIlllIlIlIllllI.minZ);
        GL11.glVertex3d(lIlllIlIlIllllI.maxX, lIlllIlIlIllllI.minY, lIlllIlIlIllllI.minZ);
        GL11.glVertex3d(lIlllIlIlIllllI.maxX, lIlllIlIlIllllI.minY, lIlllIlIlIllllI.minZ);
        GL11.glVertex3d(lIlllIlIlIllllI.maxX, lIlllIlIlIllllI.minY, lIlllIlIlIllllI.maxZ);
        GL11.glVertex3d(lIlllIlIlIllllI.maxX, lIlllIlIlIllllI.minY, lIlllIlIlIllllI.maxZ);
        GL11.glVertex3d(lIlllIlIlIllllI.minX, lIlllIlIlIllllI.minY, lIlllIlIlIllllI.maxZ);
        GL11.glVertex3d(lIlllIlIlIllllI.minX, lIlllIlIlIllllI.minY, lIlllIlIlIllllI.maxZ);
        GL11.glVertex3d(lIlllIlIlIllllI.minX, lIlllIlIlIllllI.minY, lIlllIlIlIllllI.minZ);
        GL11.glVertex3d(lIlllIlIlIllllI.minX, lIlllIlIlIllllI.minY, lIlllIlIlIllllI.minZ);
        GL11.glVertex3d(lIlllIlIlIllllI.minX, lIlllIlIlIllllI.maxY, lIlllIlIlIllllI.minZ);
        GL11.glVertex3d(lIlllIlIlIllllI.maxX, lIlllIlIlIllllI.minY, lIlllIlIlIllllI.minZ);
        GL11.glVertex3d(lIlllIlIlIllllI.maxX, lIlllIlIlIllllI.maxY, lIlllIlIlIllllI.minZ);
        GL11.glVertex3d(lIlllIlIlIllllI.maxX, lIlllIlIlIllllI.minY, lIlllIlIlIllllI.maxZ);
        GL11.glVertex3d(lIlllIlIlIllllI.maxX, lIlllIlIlIllllI.maxY, lIlllIlIlIllllI.maxZ);
        GL11.glVertex3d(lIlllIlIlIllllI.minX, lIlllIlIlIllllI.minY, lIlllIlIlIllllI.maxZ);
        GL11.glVertex3d(lIlllIlIlIllllI.minX, lIlllIlIlIllllI.maxY, lIlllIlIlIllllI.maxZ);
        GL11.glVertex3d(lIlllIlIlIllllI.minX, lIlllIlIlIllllI.maxY, lIlllIlIlIllllI.minZ);
        GL11.glVertex3d(lIlllIlIlIllllI.maxX, lIlllIlIlIllllI.maxY, lIlllIlIlIllllI.minZ);
        GL11.glVertex3d(lIlllIlIlIllllI.maxX, lIlllIlIlIllllI.maxY, lIlllIlIlIllllI.minZ);
        GL11.glVertex3d(lIlllIlIlIllllI.maxX, lIlllIlIlIllllI.maxY, lIlllIlIlIllllI.maxZ);
        GL11.glVertex3d(lIlllIlIlIllllI.maxX, lIlllIlIlIllllI.maxY, lIlllIlIlIllllI.maxZ);
        GL11.glVertex3d(lIlllIlIlIllllI.minX, lIlllIlIlIllllI.maxY, lIlllIlIlIllllI.maxZ);
        GL11.glVertex3d(lIlllIlIlIllllI.minX, lIlllIlIlIllllI.maxY, lIlllIlIlIllllI.maxZ);
        GL11.glVertex3d(lIlllIlIlIllllI.minX, lIlllIlIlIllllI.maxY, lIlllIlIlIllllI.minZ);
        GL11.glEnd();
    }
    
    public static String DF(final float lIllllIlIlIllll, final int lIllllIlIllIIIl) {
        final DecimalFormat lIllllIlIllIIII = new DecimalFormat(RenderUtils.llIIIlll[RenderUtils.llIIlIIl[0]], DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        lIllllIlIllIIII.setMaximumFractionDigits(lIllllIlIllIIIl);
        return lIllllIlIllIIII.format(lIllllIlIlIllll);
    }
    
    private static boolean lIIllIIIlI(final int lIlllIIlllIllII) {
        return lIlllIIlllIllII < 0;
    }
    
    public static void drawOutlinedBox() {
        drawOutlinedBox(RenderUtils.DEFAULT_AABB);
    }
    
    private static void lIIlIllIll() {
        (llIIlIIl = new int[19])[0] = ((171 + 36 - 186 + 189 ^ 121 + 78 - 72 + 9) & (0x47 ^ 0x5F ^ (0xF4 ^ 0xB6) ^ -" ".length()));
        RenderUtils.llIIlIIl[1] = "  ".length();
        RenderUtils.llIIlIIl[2] = (0xCD ^ 0x9E ^ (0x4D ^ 0x14));
        RenderUtils.llIIlIIl[3] = " ".length();
        RenderUtils.llIIlIIl[4] = (0x5F ^ 0x47);
        RenderUtils.llIIlIIl[5] = 113 + 111 - 118 + 30 + (0xDD ^ 0x97) - (23 + 42 + 96 + 33) + (124 + 90 + 8 + 17);
        RenderUtils.llIIlIIl[6] = (43 + 98 - 55 + 61 ^ 76 + 38 - 11 + 28);
        RenderUtils.llIIlIIl[7] = (0x61 ^ 0x69);
        RenderUtils.llIIlIIl[8] = (-(0xFFFFE9FF & 0x760F) & (0xFFFFFDEF & 0x6FFF));
        RenderUtils.llIIlIIl[9] = (0xFFFFD393 & 0x2F6E);
        RenderUtils.llIIlIIl[10] = (0xFFFFAF57 & 0x53AB);
        RenderUtils.llIIlIIl[11] = (-(112 + 7 - 56 + 67) & (0xFFFF9FEB & 0x6BB5));
        RenderUtils.llIIlIIl[12] = (0xFFFF9BEB & 0x6FF6);
        RenderUtils.llIIlIIl[13] = (0xFFFFABDC & 0x5F73);
        RenderUtils.llIIlIIl[14] = (0xFFFFDB71 & 0x2FFF);
        RenderUtils.llIIlIIl[15] = -" ".length();
        RenderUtils.llIIlIIl[16] = (0xFFFFFF66 & 0xBDD);
        RenderUtils.llIIlIIl[17] = "   ".length();
        RenderUtils.llIIlIIl[18] = (14 + 75 - 73 + 118 ^ 85 + 29 - 57 + 72);
    }
    
    public static void drawBoundingBox(final AxisAlignedBB lIlllIlIlIlIlll) {
        final Tessellator lIlllIlIlIllIIl = Tessellator.getInstance();
        final BufferBuilder lIlllIlIlIllIII = lIlllIlIlIllIIl.getBuffer();
        lIlllIlIlIllIII.begin(RenderUtils.llIIlIIl[17], DefaultVertexFormats.POSITION);
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIIl.draw();
        lIlllIlIlIllIII.begin(RenderUtils.llIIlIIl[17], DefaultVertexFormats.POSITION);
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIIl.draw();
        lIlllIlIlIllIII.begin(RenderUtils.llIIlIIl[17], DefaultVertexFormats.POSITION);
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIIl.draw();
        lIlllIlIlIllIII.begin(RenderUtils.llIIlIIl[17], DefaultVertexFormats.POSITION);
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIIl.draw();
        lIlllIlIlIllIII.begin(RenderUtils.llIIlIIl[17], DefaultVertexFormats.POSITION);
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIIl.draw();
        lIlllIlIlIllIII.begin(RenderUtils.llIIlIIl[17], DefaultVertexFormats.POSITION);
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.minX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.minZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.maxY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIII.pos(lIlllIlIlIlIlll.maxX, lIlllIlIlIlIlll.minY, lIlllIlIlIlIlll.maxZ);
        "".length();
        lIlllIlIlIllIIl.draw();
    }
    
    public static void drawTracer(final Entity lIllllIIIllIIII, final float lIllllIIIlIllll, final float lIllllIIIlIIIIl, final float lIllllIIIlIIIII, final float lIllllIIIlIllII, final float lIllllIIIlIlIll) {
        final double lIllllIIIlIlIlI = Wrapper.INSTANCE.mc().getRenderManager().viewerPosX;
        final double lIllllIIIlIlIIl = Wrapper.INSTANCE.mc().getRenderManager().viewerPosY;
        final double lIllllIIIlIlIII = Wrapper.INSTANCE.mc().getRenderManager().viewerPosZ;
        final double lIllllIIIlIIlll = lIllllIIIllIIII.lastTickPosX + (lIllllIIIllIIII.posX - lIllllIIIllIIII.lastTickPosX) * lIllllIIIlIlIll - lIllllIIIlIlIlI;
        final double lIllllIIIlIIllI = lIllllIIIllIIII.lastTickPosY + (lIllllIIIllIIII.posY - lIllllIIIllIIII.lastTickPosY) * lIllllIIIlIlIll + lIllllIIIllIIII.height / 2.0f - lIllllIIIlIlIIl;
        final double lIllllIIIlIIlIl = lIllllIIIllIIII.lastTickPosZ + (lIllllIIIllIIII.posZ - lIllllIIIllIIII.lastTickPosZ) * lIllllIIIlIlIll - lIllllIIIlIlIII;
        GL11.glBlendFunc(RenderUtils.llIIlIIl[9], RenderUtils.llIIlIIl[10]);
        GL11.glEnable(RenderUtils.llIIlIIl[12]);
        GL11.glEnable(RenderUtils.llIIlIIl[11]);
        GL11.glDisable(RenderUtils.llIIlIIl[13]);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(RenderUtils.llIIlIIl[8]);
        GL11.glDisable(RenderUtils.llIIlIIl[14]);
        GL11.glDepthMask((boolean)(RenderUtils.llIIlIIl[0] != 0));
        GL11.glColor4f(lIllllIIIlIllll, lIllllIIIlIIIIl, lIllllIIIlIIIII, lIllllIIIlIllII);
        Vec3d lIllllIIIlIIlII = null;
        if (lIIlIlllll(Scaffold.facingCam)) {
            lIllllIIIlIIlII = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(Scaffold.facingCam[RenderUtils.llIIlIIl[3]])).rotateYaw(-(float)Math.toRadians(Scaffold.facingCam[RenderUtils.llIIlIIl[0]]));
            "".length();
            if (-" ".length() >= 0) {
                return;
            }
        }
        else {
            lIllllIIIlIIlII = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(Wrapper.INSTANCE.player().rotationPitch)).rotateYaw(-(float)Math.toRadians(Wrapper.INSTANCE.player().rotationYaw));
        }
        GL11.glBegin(RenderUtils.llIIlIIl[3]);
        GL11.glVertex3d(lIllllIIIlIIlII.x, Wrapper.INSTANCE.player().getEyeHeight() + lIllllIIIlIIlII.y, lIllllIIIlIIlII.z);
        GL11.glVertex3d(lIllllIIIlIIlll, lIllllIIIlIIllI, lIllllIIIlIIlIl);
        GL11.glEnd();
        GL11.glEnable(RenderUtils.llIIlIIl[8]);
        GL11.glEnable(RenderUtils.llIIlIIl[14]);
        GL11.glEnable(RenderUtils.llIIlIIl[13]);
        GL11.glDepthMask((boolean)(RenderUtils.llIIlIIl[3] != 0));
    }
    
    private static boolean lIIlIlllII(final int lIlllIIlllIlllI) {
        return lIlllIIlllIlllI != 0;
    }
    
    public static void drawColorBox(final AxisAlignedBB lIlllIlIlllIIIl, final float lIlllIlIlllIIII, final float lIlllIlIllIllll, final float lIlllIlIllIlllI, final float lIlllIlIllIIllI) {
        final Tessellator lIlllIlIllIllII = Tessellator.getInstance();
        final BufferBuilder lIlllIlIllIlIll = lIlllIlIllIllII.getBuffer();
        lIlllIlIllIlIll.begin(RenderUtils.llIIlIIl[18], DefaultVertexFormats.POSITION_TEX);
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIllII.draw();
        lIlllIlIllIlIll.begin(RenderUtils.llIIlIIl[18], DefaultVertexFormats.POSITION_TEX);
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIllII.draw();
        lIlllIlIllIlIll.begin(RenderUtils.llIIlIIl[18], DefaultVertexFormats.POSITION_TEX);
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIllII.draw();
        lIlllIlIllIlIll.begin(RenderUtils.llIIlIIl[18], DefaultVertexFormats.POSITION_TEX);
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIllII.draw();
        lIlllIlIllIlIll.begin(RenderUtils.llIIlIIl[18], DefaultVertexFormats.POSITION_TEX);
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIllII.draw();
        lIlllIlIllIlIll.begin(RenderUtils.llIIlIIl[18], DefaultVertexFormats.POSITION_TEX);
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.minX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.minZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.maxY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIlIll.pos(lIlllIlIlllIIIl.maxX, lIlllIlIlllIIIl.minY, lIlllIlIlllIIIl.maxZ).color(lIlllIlIlllIIII, lIlllIlIllIllll, lIlllIlIllIlllI, lIlllIlIllIIllI).endVertex();
        lIlllIlIllIllII.draw();
    }
    
    public static void drawSolidBox(final AxisAlignedBB lIlllIlIllIIIIl) {
        GL11.glBegin(RenderUtils.llIIlIIl[18]);
        GL11.glVertex3d(lIlllIlIllIIIIl.minX, lIlllIlIllIIIIl.minY, lIlllIlIllIIIIl.minZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.maxX, lIlllIlIllIIIIl.minY, lIlllIlIllIIIIl.minZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.maxX, lIlllIlIllIIIIl.minY, lIlllIlIllIIIIl.maxZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.minX, lIlllIlIllIIIIl.minY, lIlllIlIllIIIIl.maxZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.minX, lIlllIlIllIIIIl.maxY, lIlllIlIllIIIIl.minZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.minX, lIlllIlIllIIIIl.maxY, lIlllIlIllIIIIl.maxZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.maxX, lIlllIlIllIIIIl.maxY, lIlllIlIllIIIIl.maxZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.maxX, lIlllIlIllIIIIl.maxY, lIlllIlIllIIIIl.minZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.minX, lIlllIlIllIIIIl.minY, lIlllIlIllIIIIl.minZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.minX, lIlllIlIllIIIIl.maxY, lIlllIlIllIIIIl.minZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.maxX, lIlllIlIllIIIIl.maxY, lIlllIlIllIIIIl.minZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.maxX, lIlllIlIllIIIIl.minY, lIlllIlIllIIIIl.minZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.maxX, lIlllIlIllIIIIl.minY, lIlllIlIllIIIIl.minZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.maxX, lIlllIlIllIIIIl.maxY, lIlllIlIllIIIIl.minZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.maxX, lIlllIlIllIIIIl.maxY, lIlllIlIllIIIIl.maxZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.maxX, lIlllIlIllIIIIl.minY, lIlllIlIllIIIIl.maxZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.minX, lIlllIlIllIIIIl.minY, lIlllIlIllIIIIl.maxZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.maxX, lIlllIlIllIIIIl.minY, lIlllIlIllIIIIl.maxZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.maxX, lIlllIlIllIIIIl.maxY, lIlllIlIllIIIIl.maxZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.minX, lIlllIlIllIIIIl.maxY, lIlllIlIllIIIIl.maxZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.minX, lIlllIlIllIIIIl.minY, lIlllIlIllIIIIl.minZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.minX, lIlllIlIllIIIIl.minY, lIlllIlIllIIIIl.maxZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.minX, lIlllIlIllIIIIl.maxY, lIlllIlIllIIIIl.maxZ);
        GL11.glVertex3d(lIlllIlIllIIIIl.minX, lIlllIlIllIIIIl.maxY, lIlllIlIllIIIIl.minZ);
        GL11.glEnd();
    }
    
    private static boolean lIIlIlllIl(final int lIlllIIllllIIll, final int lIlllIIllllIIlI) {
        return lIlllIIllllIIll == lIlllIIllllIIlI;
    }
    
    public static void drawSplash(final String lIllllIlIIIlIll) {
        final ScaledResolution lIllllIlIIIlIIl = new ScaledResolution(Wrapper.INSTANCE.mc());
        drawStringWithRect(lIllllIlIIIlIll, lIllllIlIIIlIIl.getScaledWidth() + RenderUtils.llIIlIIl[1] - RenderUtils.splashTickPos, lIllllIlIIIlIIl.getScaledHeight() - RenderUtils.llIIlIIl[2], ClickGui.getColor(), ColorUtils.color(0.0f, 0.0f, 0.0f, 0.0f), ColorUtils.color(0.0f, 0.0f, 0.0f, 0.5f));
        if (lIIlIlllII(RenderUtils.splashTimer.isDelay(10L) ? 1 : 0)) {
            RenderUtils.splashTimer.setLastMS();
            if (lIIlIlllII(RenderUtils.isSplash ? 1 : 0)) {
                RenderUtils.splashTickPos += RenderUtils.llIIlIIl[3];
                if (lIIlIlllIl(RenderUtils.splashTickPos, Wrapper.INSTANCE.fontRenderer().getStringWidth(lIllllIlIIIlIll) + RenderUtils.llIIlIIl[2])) {
                    RenderUtils.isSplash = (RenderUtils.llIIlIIl[0] != 0);
                    "".length();
                    if (" ".length() <= ((0xBF ^ 0xBA ^ (0x29 ^ 0x3)) & (0xF2 ^ 0xA6 ^ (0xF8 ^ 0x83) ^ -" ".length()))) {
                        return;
                    }
                }
            }
            else if (lIIlIllllI(RenderUtils.splashTickPos)) {
                RenderUtils.splashTickPos -= RenderUtils.llIIlIIl[3];
            }
        }
    }
    
    private static boolean lIIlIllllI(final int lIlllIIlllIlIlI) {
        return lIlllIIlllIlIlI > 0;
    }
    
    static {
        lIIlIllIll();
        lIIlIllIIl();
        DEFAULT_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        RenderUtils.splashTimer = new TimerUtils();
        RenderUtils.splashTickPos = RenderUtils.llIIlIIl[0];
        RenderUtils.isSplash = (RenderUtils.llIIlIIl[0] != 0);
    }
    
    private static boolean lIIlIlllll(final Object lIlllIIllllIIII) {
        return lIlllIIllllIIII != null;
    }
    
    public static void drawSolidBox() {
        drawSolidBox(RenderUtils.DEFAULT_AABB);
    }
    
    public static void drawOutlineBoundingBox(final AxisAlignedBB lIlllIlIlIlIIIl) {
        final Tessellator lIlllIlIlIlIIII = Tessellator.getInstance();
        final BufferBuilder lIlllIlIlIIllll = lIlllIlIlIlIIII.getBuffer();
        lIlllIlIlIIllll.begin(RenderUtils.llIIlIIl[17], DefaultVertexFormats.POSITION);
        lIlllIlIlIIllll.pos(lIlllIlIlIlIIIl.minX, lIlllIlIlIlIIIl.minY, lIlllIlIlIlIIIl.minZ).endVertex();
        lIlllIlIlIIllll.pos(lIlllIlIlIlIIIl.maxX, lIlllIlIlIlIIIl.minY, lIlllIlIlIlIIIl.minZ).endVertex();
        lIlllIlIlIIllll.pos(lIlllIlIlIlIIIl.maxX, lIlllIlIlIlIIIl.minY, lIlllIlIlIlIIIl.maxZ).endVertex();
        lIlllIlIlIIllll.pos(lIlllIlIlIlIIIl.minX, lIlllIlIlIlIIIl.minY, lIlllIlIlIlIIIl.maxZ).endVertex();
        lIlllIlIlIIllll.pos(lIlllIlIlIlIIIl.minX, lIlllIlIlIlIIIl.minY, lIlllIlIlIlIIIl.minZ).endVertex();
        lIlllIlIlIlIIII.draw();
        lIlllIlIlIIllll.begin(RenderUtils.llIIlIIl[17], DefaultVertexFormats.POSITION);
        lIlllIlIlIIllll.pos(lIlllIlIlIlIIIl.minX, lIlllIlIlIlIIIl.maxY, lIlllIlIlIlIIIl.minZ).endVertex();
        lIlllIlIlIIllll.pos(lIlllIlIlIlIIIl.maxX, lIlllIlIlIlIIIl.maxY, lIlllIlIlIlIIIl.minZ).endVertex();
        lIlllIlIlIIllll.pos(lIlllIlIlIlIIIl.maxX, lIlllIlIlIlIIIl.maxY, lIlllIlIlIlIIIl.maxZ).endVertex();
        lIlllIlIlIIllll.pos(lIlllIlIlIlIIIl.minX, lIlllIlIlIlIIIl.maxY, lIlllIlIlIlIIIl.maxZ).endVertex();
        lIlllIlIlIIllll.pos(lIlllIlIlIlIIIl.minX, lIlllIlIlIlIIIl.maxY, lIlllIlIlIlIIIl.minZ).endVertex();
        lIlllIlIlIlIIII.draw();
        lIlllIlIlIIllll.begin(RenderUtils.llIIlIIl[3], DefaultVertexFormats.POSITION);
        lIlllIlIlIIllll.pos(lIlllIlIlIlIIIl.minX, lIlllIlIlIlIIIl.minY, lIlllIlIlIlIIIl.minZ).endVertex();
        lIlllIlIlIIllll.pos(lIlllIlIlIlIIIl.minX, lIlllIlIlIlIIIl.maxY, lIlllIlIlIlIIIl.minZ).endVertex();
        lIlllIlIlIIllll.pos(lIlllIlIlIlIIIl.maxX, lIlllIlIlIlIIIl.minY, lIlllIlIlIlIIIl.minZ).endVertex();
        lIlllIlIlIIllll.pos(lIlllIlIlIlIIIl.maxX, lIlllIlIlIlIIIl.maxY, lIlllIlIlIlIIIl.minZ).endVertex();
        lIlllIlIlIIllll.pos(lIlllIlIlIlIIIl.maxX, lIlllIlIlIlIIIl.minY, lIlllIlIlIlIIIl.maxZ).endVertex();
        lIlllIlIlIIllll.pos(lIlllIlIlIlIIIl.maxX, lIlllIlIlIlIIIl.maxY, lIlllIlIlIlIIIl.maxZ).endVertex();
        lIlllIlIlIIllll.pos(lIlllIlIlIlIIIl.minX, lIlllIlIlIlIIIl.minY, lIlllIlIlIlIIIl.maxZ).endVertex();
        lIlllIlIlIIllll.pos(lIlllIlIlIlIIIl.minX, lIlllIlIlIlIIIl.maxY, lIlllIlIlIlIIIl.maxZ).endVertex();
        lIlllIlIlIlIIII.draw();
    }
    
    public static void drawBlockESP(final BlockPos lIlllIllIIIlIII, final float lIlllIllIIIlllI, final float lIlllIllIIIllIl, final float lIlllIllIIIIlIl) {
        GL11.glPushMatrix();
        GL11.glEnable(RenderUtils.llIIlIIl[12]);
        GL11.glBlendFunc(RenderUtils.llIIlIIl[9], RenderUtils.llIIlIIl[10]);
        GL11.glEnable(RenderUtils.llIIlIIl[11]);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(RenderUtils.llIIlIIl[8]);
        GL11.glEnable(RenderUtils.llIIlIIl[16]);
        GL11.glDisable(RenderUtils.llIIlIIl[14]);
        GL11.glDisable(RenderUtils.llIIlIIl[13]);
        final double lIlllIllIIIlIll = Wrapper.INSTANCE.mc().getRenderManager().viewerPosX;
        final double lIlllIllIIIlIlI = Wrapper.INSTANCE.mc().getRenderManager().viewerPosY;
        final double lIlllIllIIIlIIl = Wrapper.INSTANCE.mc().getRenderManager().viewerPosZ;
        GL11.glTranslated(-lIlllIllIIIlIll, -lIlllIllIIIlIlI, -lIlllIllIIIlIIl);
        GL11.glTranslated((double)lIlllIllIIIlIII.getX(), (double)lIlllIllIIIlIII.getY(), (double)lIlllIllIIIlIII.getZ());
        GL11.glColor4f(lIlllIllIIIlllI, lIlllIllIIIllIl, lIlllIllIIIIlIl, 0.3f);
        drawSolidBox();
        GL11.glColor4f(lIlllIllIIIlllI, lIlllIllIIIllIl, lIlllIllIIIIlIl, 0.7f);
        drawOutlinedBox();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(RenderUtils.llIIlIIl[13]);
        GL11.glEnable(RenderUtils.llIIlIIl[14]);
        GL11.glEnable(RenderUtils.llIIlIIl[8]);
        GL11.glDisable(RenderUtils.llIIlIIl[12]);
        GL11.glDisable(RenderUtils.llIIlIIl[11]);
        GL11.glPopMatrix();
    }
    
    private static int lIIllIIIII(final float n, final float n2) {
        return fcmpg(n, n2);
    }
    
    private static int lIIllIIIll(final float n, final float n2) {
        return fcmpg(n, n2);
    }
    
    private static void lIIlIllIIl() {
        (llIIIlll = new String[RenderUtils.llIIlIIl[3]])[RenderUtils.llIIlIIl[0]] = lIIlIlIlIl("U+eIFbof1PU=", "ZbJLA");
    }
    
    public static void drawTri(final double lIlllIlIlIIIIll, final double lIlllIlIIlllIlI, final double lIlllIlIIlllIIl, final double lIlllIlIIlllIII, final double lIlllIlIIllIlll, final double lIlllIlIIllIllI, final double lIlllIlIIllIlIl, final Color lIlllIlIIllllII) {
        GL11.glEnable(RenderUtils.llIIlIIl[12]);
        GL11.glDisable(RenderUtils.llIIlIIl[8]);
        GL11.glEnable(RenderUtils.llIIlIIl[11]);
        GL11.glBlendFunc(RenderUtils.llIIlIIl[9], RenderUtils.llIIlIIl[10]);
        GLUtils.glColor(lIlllIlIIllllII);
        GL11.glLineWidth((float)lIlllIlIIllIlIl);
        GL11.glBegin(RenderUtils.llIIlIIl[17]);
        GL11.glVertex2d(lIlllIlIlIIIIll, lIlllIlIIlllIlI);
        GL11.glVertex2d(lIlllIlIIlllIIl, lIlllIlIIlllIII);
        GL11.glVertex2d(lIlllIlIIllIlll, lIlllIlIIllIllI);
        GL11.glEnd();
        GL11.glDisable(RenderUtils.llIIlIIl[11]);
        GL11.glEnable(RenderUtils.llIIlIIl[8]);
    }
    
    public static void drawNukerBlocks(final Iterable<BlockPos> lIlllIlllIIllIl, final float lIlllIlllIlIIll, final float lIlllIlllIIlIll, final float lIlllIlllIIlIlI, final float lIlllIlllIIlIIl) {
        GL11.glPushMatrix();
        GL11.glEnable(RenderUtils.llIIlIIl[12]);
        GL11.glBlendFunc(RenderUtils.llIIlIIl[9], RenderUtils.llIIlIIl[10]);
        GL11.glEnable(RenderUtils.llIIlIIl[11]);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(RenderUtils.llIIlIIl[8]);
        GL11.glEnable(RenderUtils.llIIlIIl[16]);
        GL11.glDisable(RenderUtils.llIIlIIl[14]);
        GL11.glDisable(RenderUtils.llIIlIIl[13]);
        final WorldClient lIlllIlllIIllll = Wrapper.INSTANCE.world();
        final EntityPlayerSP lIlllIlllIIlllI = Wrapper.INSTANCE.player();
        final byte lIlllIlllIIIllI = (byte)lIlllIlllIIllIl.iterator();
        while (lIIlIlllII(((Iterator)lIlllIlllIIIllI).hasNext() ? 1 : 0)) {
            final BlockPos lIlllIlllIlIlIl = ((Iterator<BlockPos>)lIlllIlllIIIllI).next();
            final IBlockState lIlllIlllIllIlI = lIlllIlllIIllll.getBlockState(lIlllIlllIlIlIl);
            final double lIlllIlllIllIIl = lIlllIlllIIlllI.lastTickPosX + (lIlllIlllIIlllI.posX - lIlllIlllIIlllI.lastTickPosX) * lIlllIlllIIlIIl;
            final double lIlllIlllIllIII = lIlllIlllIIlllI.lastTickPosY + (lIlllIlllIIlllI.posY - lIlllIlllIIlllI.lastTickPosY) * lIlllIlllIIlIIl;
            final double lIlllIlllIlIlll = lIlllIlllIIlllI.lastTickPosZ + (lIlllIlllIIlllI.posZ - lIlllIlllIIlllI.lastTickPosZ) * lIlllIlllIIlIIl;
            GLUtils.glColor(new Color(lIlllIlllIlIIll, lIlllIlllIIlIll, lIlllIlllIIlIlI, 1.0f));
            final AxisAlignedBB lIlllIlllIlIllI = lIlllIlllIllIlI.getSelectedBoundingBox((World)lIlllIlllIIllll, lIlllIlllIlIlIl).grow(0.0020000000949949026).offset(-lIlllIlllIllIIl, -lIlllIlllIllIII, -lIlllIlllIlIlll);
            drawSelectionBoundingBox(lIlllIlllIlIllI);
            "".length();
            if (-"   ".length() > 0) {
                return;
            }
        }
        GL11.glEnable(RenderUtils.llIIlIIl[13]);
        GL11.glEnable(RenderUtils.llIIlIIl[14]);
        GL11.glEnable(RenderUtils.llIIlIIl[8]);
        GL11.glDisable(RenderUtils.llIIlIIl[12]);
        GL11.glDisable(RenderUtils.llIIlIIl[11]);
        GL11.glPopMatrix();
    }
    
    public static void drawRect(float lIlllIlIIIIlIII, float lIlllIlIIIIIlll, float lIlllIlIIIIIllI, float lIlllIlIIIIIlIl, final int lIlllIlIIIIlIIl) {
        if (lIIllIIIlI(lIIllIIlII(lIlllIlIIIIlIII, lIlllIlIIIIIllI))) {
            final float lIlllIlIIIIllll = lIlllIlIIIIlIII;
            lIlllIlIIIIlIII = lIlllIlIIIIIllI;
            lIlllIlIIIIIllI = lIlllIlIIIIllll;
        }
        if (lIIllIIIlI(lIIllIIlII(lIlllIlIIIIIlll, lIlllIlIIIIIlIl))) {
            final float lIlllIlIIIIlllI = lIlllIlIIIIIlll;
            lIlllIlIIIIIlll = lIlllIlIIIIIlIl;
            lIlllIlIIIIIlIl = lIlllIlIIIIlllI;
        }
        GL11.glEnable(RenderUtils.llIIlIIl[12]);
        GL11.glDisable(RenderUtils.llIIlIIl[8]);
        GL11.glBlendFunc(RenderUtils.llIIlIIl[9], RenderUtils.llIIlIIl[10]);
        GL11.glEnable(RenderUtils.llIIlIIl[11]);
        GL11.glPushMatrix();
        GLUtils.glColor(lIlllIlIIIIlIIl);
        GL11.glBegin(RenderUtils.llIIlIIl[18]);
        GL11.glVertex2d((double)lIlllIlIIIIlIII, (double)lIlllIlIIIIIlIl);
        GL11.glVertex2d((double)lIlllIlIIIIIllI, (double)lIlllIlIIIIIlIl);
        GL11.glVertex2d((double)lIlllIlIIIIIllI, (double)lIlllIlIIIIIlll);
        GL11.glVertex2d((double)lIlllIlIIIIlIII, (double)lIlllIlIIIIIlll);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(RenderUtils.llIIlIIl[8]);
        GL11.glDisable(RenderUtils.llIIlIIl[11]);
    }
    
    public static void drawVLine(final float lIlllIlIIIllllI, float lIlllIlIIIllIIl, float lIlllIlIIIllIII, final int lIlllIlIIIlIlll) {
        if (lIIllIIIlI(lIIllIIIll(lIlllIlIIIllIII, lIlllIlIIIllIIl))) {
            final float lIlllIlIIIlllll = lIlllIlIIIllIIl;
            lIlllIlIIIllIIl = lIlllIlIIIllIII;
            lIlllIlIIIllIII = lIlllIlIIIlllll;
        }
        drawRect(lIlllIlIIIllllI, lIlllIlIIIllIIl + 1.0f, lIlllIlIIIllllI + 1.0f, lIlllIlIIIllIII, lIlllIlIIIlIlll);
    }
    
    public static void drawBorderedRect(final double lIllllIIllIllll, final double lIllllIIllIIIlI, final double lIllllIIllIIIIl, final double lIllllIIllIllII, final float lIllllIIlIlllll, final int lIllllIIlIllllI, final int lIllllIIllIlIII) {
        drawRect((float)(int)lIllllIIllIllll, (float)(int)lIllllIIllIIIlI, (float)(int)lIllllIIllIIIIl, (float)(int)lIllllIIllIllII, lIllllIIllIlIII);
        final float lIllllIIllIIlll = (lIllllIIlIllllI >> RenderUtils.llIIlIIl[4] & RenderUtils.llIIlIIl[5]) / 255.0f;
        final float lIllllIIllIIllI = (lIllllIIlIllllI >> RenderUtils.llIIlIIl[6] & RenderUtils.llIIlIIl[5]) / 255.0f;
        final float lIllllIIllIIlIl = (lIllllIIlIllllI >> RenderUtils.llIIlIIl[7] & RenderUtils.llIIlIIl[5]) / 255.0f;
        final float lIllllIIllIIlII = (lIllllIIlIllllI & RenderUtils.llIIlIIl[5]) / 255.0f;
        GL11.glDisable(RenderUtils.llIIlIIl[8]);
        GL11.glBlendFunc(RenderUtils.llIIlIIl[9], RenderUtils.llIIlIIl[10]);
        GL11.glEnable(RenderUtils.llIIlIIl[11]);
        GL11.glPushMatrix();
        GL11.glColor4f(lIllllIIllIIllI, lIllllIIllIIlIl, lIllllIIllIIlII, lIllllIIllIIlll);
        GL11.glLineWidth(lIllllIIlIlllll);
        GL11.glBegin(RenderUtils.llIIlIIl[3]);
        GL11.glVertex2d(lIllllIIllIllll, lIllllIIllIIIlI);
        GL11.glVertex2d(lIllllIIllIllll, lIllllIIllIllII);
        GL11.glVertex2d(lIllllIIllIIIIl, lIllllIIllIllII);
        GL11.glVertex2d(lIllllIIllIIIIl, lIllllIIllIIIlI);
        GL11.glVertex2d(lIllllIIllIllll, lIllllIIllIIIlI);
        GL11.glVertex2d(lIllllIIllIIIIl, lIllllIIllIIIlI);
        GL11.glVertex2d(lIllllIIllIllll, lIllllIIllIllII);
        GL11.glVertex2d(lIllllIIllIIIIl, lIllllIIllIllII);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(RenderUtils.llIIlIIl[8]);
        GL11.glDisable(RenderUtils.llIIlIIl[11]);
    }
}
