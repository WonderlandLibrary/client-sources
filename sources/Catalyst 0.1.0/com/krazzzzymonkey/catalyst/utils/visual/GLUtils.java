// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils.visual;

import java.util.Iterator;
import org.lwjgl.input.Mouse;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import java.util.Random;
import net.minecraft.client.renderer.Tessellator;
import java.util.List;

public final class GLUtils
{
    private static final /* synthetic */ int[] lllII;
    public static /* synthetic */ List<Integer> textures;
    private static final /* synthetic */ Tessellator tessellator;
    public static /* synthetic */ List<Integer> vbos;
    private static final /* synthetic */ Random random;
    
    public static void glColor(final int lllIllIIllIIlII) {
        GlStateManager.color((lllIllIIllIIlII >> GLUtils.lllII[9] & GLUtils.lllII[10]) / 255.0f, (lllIllIIllIIlII >> GLUtils.lllII[11] & GLUtils.lllII[10]) / 255.0f, (lllIllIIllIIlII & GLUtils.lllII[10]) / 255.0f, (lllIllIIllIIlII >> GLUtils.lllII[12] & GLUtils.lllII[10]) / 255.0f);
    }
    
    public static Color getRandomColor(final int lllIllIIlIlIIIl, final float lllIllIIlIlIIII) {
        final float lllIllIIlIlIIll = GLUtils.random.nextFloat();
        final float lllIllIIlIlIIlI = (GLUtils.random.nextInt(lllIllIIlIlIIIl) + (float)lllIllIIlIlIIIl) / lllIllIIlIlIIIl + lllIllIIlIlIIIl;
        return getHSBColor(lllIllIIlIlIIll, lllIllIIlIlIIlI, lllIllIIlIlIIII);
    }
    
    public static void glColor(final float lllIllIIlllIIII, final float lllIllIIllIllll, final float lllIllIIllIlllI, final float lllIllIIllIlIIl) {
        GlStateManager.color(lllIllIIlllIIII, lllIllIIllIllll, lllIllIIllIlllI, lllIllIIllIlIIl);
    }
    
    private static boolean lllIIll(final int lllIllIIlIIIIII) {
        return lllIllIIlIIIIII != 0;
    }
    
    private static boolean lllIIlI(final int lllIllIIlIIlIll, final int lllIllIIlIIlIlI) {
        return lllIllIIlIIlIll >= lllIllIIlIIlIlI;
    }
    
    public static void cleanup() {
        GL15.glBindBuffer(GLUtils.lllII[7], GLUtils.lllII[0]);
        GL11.glBindTexture(GLUtils.lllII[13], GLUtils.lllII[0]);
        short lllIllIllIlIllI = (short)GLUtils.vbos.iterator();
        while (lllIIll(((Iterator)lllIllIllIlIllI).hasNext() ? 1 : 0)) {
            final int lllIllIllIllIII = ((Iterator<Integer>)lllIllIllIlIllI).next();
            GL15.glDeleteBuffers(lllIllIllIllIII);
            "".length();
            if (-" ".length() > 0) {
                return;
            }
        }
        lllIllIllIlIllI = (short)GLUtils.textures.iterator();
        while (lllIIll(((Iterator)lllIllIllIlIllI).hasNext() ? 1 : 0)) {
            final int lllIllIllIlIlll = ((Iterator<Integer>)lllIllIllIlIllI).next();
            GL11.glDeleteTextures(lllIllIllIlIlll);
            "".length();
            if ("  ".length() <= -" ".length()) {
                return;
            }
        }
    }
    
    public static void disableGL2D() {
        GL11.glEnable(GLUtils.lllII[13]);
        GL11.glEnable(GLUtils.lllII[27]);
        GL11.glDisable(GLUtils.lllII[29]);
        GL11.glHint(GLUtils.lllII[30], GLUtils.lllII[33]);
        GL11.glHint(GLUtils.lllII[32], GLUtils.lllII[33]);
    }
    
    public static int getScreenHeight() {
        return Minecraft.getMinecraft().displayHeight / getScaleFactor();
    }
    
    public static int getScreenWidth() {
        return Minecraft.getMinecraft().displayWidth / getScaleFactor();
    }
    
    public static void enableGL2D() {
        GL11.glDisable(GLUtils.lllII[27]);
        GL11.glEnable(GLUtils.lllII[28]);
        GL11.glDisable(GLUtils.lllII[13]);
        GL11.glBlendFunc(GLUtils.lllII[22], GLUtils.lllII[23]);
        GL11.glDepthMask((boolean)(GLUtils.lllII[1] != 0));
        GL11.glEnable(GLUtils.lllII[29]);
        GL11.glHint(GLUtils.lllII[30], GLUtils.lllII[31]);
        GL11.glHint(GLUtils.lllII[32], GLUtils.lllII[31]);
    }
    
    public static int getTexture() {
        final int lllIlllIIIlIlIl = GL11.glGenTextures();
        GLUtils.textures.add(lllIlllIIIlIlIl);
        "".length();
        return lllIlllIIIlIlIl;
    }
    
    public static void glScissor(final float lllIlllIIlllIIl, final float lllIlllIIllllIl, final float lllIlllIIllIlll, final float lllIlllIIllIllI) {
        final int lllIlllIIlllIlI = getScaleFactor();
        GL11.glScissor((int)(lllIlllIIlllIIl * lllIlllIIlllIlI), (int)(Minecraft.getMinecraft().displayHeight - lllIlllIIllIllI * lllIlllIIlllIlI), (int)((lllIlllIIllIlll - lllIlllIIlllIIl) * lllIlllIIlllIlI), (int)((lllIlllIIllIllI - lllIlllIIllllIl) * lllIlllIIlllIlI));
    }
    
    static {
        llIllll();
        random = new Random();
        tessellator = Tessellator.getInstance();
        GLUtils.vbos = new ArrayList<Integer>();
        GLUtils.textures = new ArrayList<Integer>();
    }
    
    public static Color getRandomColor() {
        return getRandomColor(GLUtils.lllII[4], 0.6f);
    }
    
    private static boolean lllIIIl(final int lllIllIIlIIIlll, final int lllIllIIlIIIllI) {
        return lllIllIIlIIIlll < lllIllIIlIIIllI;
    }
    
    public static int applyTexture(final int lllIllIllllIlIl, final BufferedImage lllIllIllllIlII, final int lllIllIllllIIll, final int lllIllIllllIIlI) {
        final int[] lllIllIllllIlll = new int[lllIllIllllIlII.getWidth() * lllIllIllllIlII.getHeight()];
        lllIllIllllIlII.getRGB(GLUtils.lllII[0], GLUtils.lllII[0], lllIllIllllIlII.getWidth(), lllIllIllllIlII.getHeight(), lllIllIllllIlll, GLUtils.lllII[0], lllIllIllllIlII.getWidth());
        "".length();
        final ByteBuffer lllIllIllllIllI = BufferUtils.createByteBuffer(lllIllIllllIlII.getWidth() * lllIllIllllIlII.getHeight() * GLUtils.lllII[8]);
        int lllIllIllllllII = GLUtils.lllII[0];
        while (lllIIIl(lllIllIllllllII, lllIllIllllIlII.getHeight())) {
            int lllIllIllllllIl = GLUtils.lllII[0];
            while (lllIIIl(lllIllIllllllIl, lllIllIllllIlII.getWidth())) {
                final int lllIllIlllllllI = lllIllIllllIlll[lllIllIllllllII * lllIllIllllIlII.getWidth() + lllIllIllllllIl];
                lllIllIllllIllI.put((byte)(lllIllIlllllllI >> GLUtils.lllII[9] & GLUtils.lllII[10]));
                "".length();
                lllIllIllllIllI.put((byte)(lllIllIlllllllI >> GLUtils.lllII[11] & GLUtils.lllII[10]));
                "".length();
                lllIllIllllIllI.put((byte)(lllIllIlllllllI & GLUtils.lllII[10]));
                "".length();
                lllIllIllllIllI.put((byte)(lllIllIlllllllI >> GLUtils.lllII[12] & GLUtils.lllII[10]));
                "".length();
                ++lllIllIllllllIl;
                "".length();
                if (" ".length() <= 0) {
                    return (0x17 ^ 0x48) & ~(0xD7 ^ 0x88);
                }
            }
            ++lllIllIllllllII;
            "".length();
            if (-"  ".length() >= 0) {
                return (0x7D ^ 0x65) & ~(0x8B ^ 0x93);
            }
        }
        lllIllIllllIllI.flip();
        "".length();
        applyTexture(lllIllIllllIlIl, lllIllIllllIlII.getWidth(), lllIllIllllIlII.getHeight(), lllIllIllllIllI, lllIllIllllIIll, lllIllIllllIIlI);
        "".length();
        return lllIllIllllIlIl;
    }
    
    public static int applyTexture(final int lllIllIlllIIIII, final int lllIllIlllIIlIl, final int lllIllIlllIIlII, final ByteBuffer lllIllIlllIIIll, final int lllIllIllIlllII, final int lllIllIllIllIll) {
        GL11.glBindTexture(GLUtils.lllII[13], lllIllIlllIIIII);
        GL11.glTexParameteri(GLUtils.lllII[13], GLUtils.lllII[14], lllIllIllIlllII);
        GL11.glTexParameteri(GLUtils.lllII[13], GLUtils.lllII[15], lllIllIllIlllII);
        GL11.glTexParameteri(GLUtils.lllII[13], GLUtils.lllII[16], lllIllIllIllIll);
        GL11.glTexParameteri(GLUtils.lllII[13], GLUtils.lllII[17], lllIllIllIllIll);
        GL11.glPixelStorei(GLUtils.lllII[18], GLUtils.lllII[1]);
        GL11.glTexImage2D(GLUtils.lllII[13], GLUtils.lllII[0], GLUtils.lllII[19], lllIllIlllIIlIl, lllIllIlllIIlII, GLUtils.lllII[0], GLUtils.lllII[20], GLUtils.lllII[21], lllIllIlllIIIll);
        GL11.glBindTexture(GLUtils.lllII[13], GLUtils.lllII[0]);
        return lllIllIlllIIIII;
    }
    
    public static void drawBorderRect(final float lllIllIllIIlIlI, final float lllIllIllIIlIIl, final float lllIllIllIIlIII, final float lllIllIllIIllII, final float lllIllIllIIlIll) {
        drawBorder(lllIllIllIIlIll, lllIllIllIIlIlI, lllIllIllIIlIIl, lllIllIllIIlIII, lllIllIllIIllII);
        drawRect(lllIllIllIIlIlI, lllIllIllIIlIIl, lllIllIllIIlIII, lllIllIllIIllII);
    }
    
    public static int getScaleFactor() {
        int lllIlllIIllIIIl = GLUtils.lllII[1];
        final boolean lllIlllIIllIIII = Minecraft.getMinecraft().isUnicode();
        int lllIlllIIlIllll = Minecraft.getMinecraft().gameSettings.guiScale;
        if (lllIIII(lllIlllIIlIllll)) {
            lllIlllIIlIllll = GLUtils.lllII[4];
        }
        while (lllIIIl(lllIlllIIllIIIl, lllIlllIIlIllll) && lllIIlI(Minecraft.getMinecraft().displayWidth / (lllIlllIIllIIIl + GLUtils.lllII[1]), GLUtils.lllII[5]) && lllIIlI(Minecraft.getMinecraft().displayHeight / (lllIlllIIllIIIl + GLUtils.lllII[1]), GLUtils.lllII[6])) {
            ++lllIlllIIllIIIl;
            "".length();
            if (-" ".length() > 0) {
                return (65 + 126 - 34 + 2 ^ 73 + 4 + 38 + 59) & (0x81 ^ 0xBD ^ (0x1E ^ 0x13) ^ -" ".length());
            }
        }
        if (lllIIll(lllIlllIIllIIII ? 1 : 0) && lllIIll(lllIlllIIllIIIl % GLUtils.lllII[2]) && lllIlII(lllIlllIIllIIIl, GLUtils.lllII[1])) {
            --lllIlllIIllIIIl;
        }
        return lllIlllIIllIIIl;
    }
    
    private static boolean lllIIII(final int lllIllIIIlllllI) {
        return lllIllIIIlllllI == 0;
    }
    
    private static boolean lllIlII(final int lllIllIIIlllIll, final int lllIllIIIlllIlI) {
        return lllIllIIIlllIll != lllIllIIIlllIlI;
    }
    
    public static void drawRect(final float lllIllIlIlIlllI, final float lllIllIlIlIlIII, final float lllIllIlIlIIlll, final float lllIllIlIlIIllI) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GLUtils.lllII[22], GLUtils.lllII[23]);
        final BufferBuilder lllIllIlIlIlIlI = GLUtils.tessellator.getBuffer();
        lllIllIlIlIlIlI.begin(GLUtils.lllII[24], DefaultVertexFormats.POSITION);
        lllIllIlIlIlIlI.pos((double)lllIllIlIlIlllI, (double)lllIllIlIlIIllI, 0.0).endVertex();
        lllIllIlIlIlIlI.pos((double)lllIllIlIlIIlll, (double)lllIllIlIlIIllI, 0.0).endVertex();
        lllIllIlIlIlIlI.pos((double)lllIllIlIlIIlll, (double)lllIllIlIlIlIII, 0.0).endVertex();
        lllIllIlIlIlIlI.pos((double)lllIllIlIlIlllI, (double)lllIllIlIlIlIII, 0.0).endVertex();
        GLUtils.tessellator.draw();
        GlStateManager.enableTexture2D();
    }
    
    public static int applyTexture(final int lllIlllIIIIllll, final File lllIlllIIIIlllI, final int lllIlllIIIIlIIl, final int lllIlllIIIIllII) throws IOException {
        applyTexture(lllIlllIIIIllll, ImageIO.read(lllIlllIIIIlllI), lllIlllIIIIlIIl, lllIlllIIIIllII);
        "".length();
        return lllIlllIIIIllll;
    }
    
    public static int genVBO() {
        final int lllIlllIIIllIII = GL15.glGenBuffers();
        GLUtils.vbos.add(lllIlllIIIllIII);
        "".length();
        GL15.glBindBuffer(GLUtils.lllII[7], lllIlllIIIllIII);
        return lllIlllIIIllIII;
    }
    
    private static void llIllll() {
        (lllII = new int[34])[0] = ((0xEF ^ 0x87 ^ (0x1A ^ 0x49)) & (16 + 49 + 88 + 26 ^ 1 + 7 + 57 + 71 ^ -" ".length()));
        GLUtils.lllII[1] = " ".length();
        GLUtils.lllII[2] = "  ".length();
        GLUtils.lllII[3] = "   ".length();
        GLUtils.lllII[4] = (-(0xFFFFEA5E & 0x5DB5) & (0xFFFFDFFB & 0x6BFF));
        GLUtils.lllII[5] = (-(0xFFFFCDB6 & 0x7679) & (0xFFFFE7EF & 0x5D7F));
        GLUtils.lllII[6] = 154 + 163 - 260 + 183;
        GLUtils.lllII[7] = (-(0xFFFFF6ED & 0x4D73) & (0xFFFFDEFA & 0xEDF7));
        GLUtils.lllII[8] = (1 + 48 + 13 + 92 ^ 132 + 99 - 112 + 39);
        GLUtils.lllII[9] = (0x1B ^ 0x32 ^ (0x8 ^ 0x31));
        GLUtils.lllII[10] = 123 + 194 - 221 + 159;
        GLUtils.lllII[11] = (0x4D ^ 0x11 ^ (0x5 ^ 0x51));
        GLUtils.lllII[12] = (113 + 82 - 106 + 48 ^ 20 + 118 - 106 + 113);
        GLUtils.lllII[13] = (0xFFFF8FE1 & 0x7DFF);
        GLUtils.lllII[14] = (0xFFFFEF33 & 0x38CD);
        GLUtils.lllII[15] = (0xFFFFFF16 & 0x28E9);
        GLUtils.lllII[16] = (-(0xFFFFCFF7 & 0x75FA) & (0xFFFFFFF7 & 0x6DFB));
        GLUtils.lllII[17] = (-(0xFFFFE2DB & 0x5FBD) & (0xFFFFEA9B & 0x7FFF));
        GLUtils.lllII[18] = (-(0xFFFFD11D & 0x7FE3) & (0xFFFFFDFD & 0x5FF7));
        GLUtils.lllII[19] = (-(0xFFFFF7A5 & 0x3DDE) & (0xFFFFF5FF & 0xBFDB));
        GLUtils.lllII[20] = (0xFFFFB9FF & 0x5F08);
        GLUtils.lllII[21] = (-(0xFFFFE2B9 & 0x3FCF) & (0xFFFFFEAF & 0x37D9));
        GLUtils.lllII[22] = (0xFFFFC7EA & 0x3B17);
        GLUtils.lllII[23] = (0xFFFFF753 & 0xBAF);
        GLUtils.lllII[24] = (0x4E ^ 0x1 ^ (0x2E ^ 0x66));
        GLUtils.lllII[25] = (-(0xFFFFED6F & 0x32DF) & (0xFFFFBDEF & 0x7F5F));
        GLUtils.lllII[26] = (-(0xFFFFEBF5 & 0x165F) & (0xFFFFDFFC & 0x3F57));
        GLUtils.lllII[27] = (0xFFFF8B77 & 0x7FF9);
        GLUtils.lllII[28] = (0xFFFFABE3 & 0x5FFE);
        GLUtils.lllII[29] = (0xFFFFFBB3 & 0xF6C);
        GLUtils.lllII[30] = (-(0xFFFFEDAA & 0x73DF) & (0xFFFFEDFB & 0x7FDF));
        GLUtils.lllII[31] = (0xFFFFB3BF & 0x5D42);
        GLUtils.lllII[32] = (0xFFFFFCDF & 0xF73);
        GLUtils.lllII[33] = (0xFFFFB54A & 0x5BB5);
    }
    
    public static void glScissor(final int[] lllIlllIlIIIlII) {
        glScissor((float)lllIlllIlIIIlII[GLUtils.lllII[0]], (float)lllIlllIlIIIlII[GLUtils.lllII[1]], (float)(lllIlllIlIIIlII[GLUtils.lllII[0]] + lllIlllIlIIIlII[GLUtils.lllII[2]]), (float)(lllIlllIlIIIlII[GLUtils.lllII[1]] + lllIlllIlIIIlII[GLUtils.lllII[3]]));
    }
    
    public static int getMouseX() {
        return Mouse.getX() * getScreenWidth() / Minecraft.getMinecraft().displayWidth;
    }
    
    public static Color getHSBColor(final float lllIllIIlIlllII, final float lllIllIIlIllllI, final float lllIllIIlIllIlI) {
        return Color.getHSBColor(lllIllIIlIlllII, lllIllIIlIllllI, lllIllIIlIllIlI);
    }
    
    public static void drawBorder(final float lllIllIlIlllIIl, final float lllIllIlIlllIII, final float lllIllIlIllllIl, final float lllIllIlIllllII, final float lllIllIlIllIlIl) {
        GL11.glLineWidth(lllIllIlIlllIIl);
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GLUtils.lllII[22], GLUtils.lllII[23]);
        final BufferBuilder lllIllIlIlllIlI = GLUtils.tessellator.getBuffer();
        lllIllIlIlllIlI.begin(GLUtils.lllII[2], DefaultVertexFormats.POSITION);
        lllIllIlIlllIlI.pos((double)lllIllIlIlllIII, (double)lllIllIlIllllIl, 0.0).endVertex();
        lllIllIlIlllIlI.pos((double)lllIllIlIlllIII, (double)lllIllIlIllIlIl, 0.0).endVertex();
        lllIllIlIlllIlI.pos((double)lllIllIlIllllII, (double)lllIllIlIllIlIl, 0.0).endVertex();
        lllIllIlIlllIlI.pos((double)lllIllIlIllllII, (double)lllIllIlIllllIl, 0.0).endVertex();
        GLUtils.tessellator.draw();
        GlStateManager.enableTexture2D();
    }
    
    public static void glColor(final Color lllIllIIllIIlll) {
        GlStateManager.color(lllIllIIllIIlll.getRed() / 255.0f, lllIllIIllIIlll.getGreen() / 255.0f, lllIllIIllIIlll.getBlue() / 255.0f, lllIllIIllIIlll.getAlpha() / 255.0f);
    }
    
    public static int getMouseY() {
        return getScreenHeight() - Mouse.getY() * getScreenHeight() / Minecraft.getMinecraft().displayWidth - GLUtils.lllII[1];
    }
    
    public static void drawGradientRect(final int lllIllIlIIIIlII, final int lllIllIlIIlIIll, final int lllIllIlIIlIIlI, final int lllIllIlIIlIIIl, final int lllIllIlIIlIIII, final int lllIllIlIIIllll) {
        final float lllIllIlIIIlllI = (lllIllIlIIlIIII >> GLUtils.lllII[12] & GLUtils.lllII[10]) / 255.0f;
        final float lllIllIlIIIllIl = (lllIllIlIIlIIII >> GLUtils.lllII[9] & GLUtils.lllII[10]) / 255.0f;
        final float lllIllIlIIIllII = (lllIllIlIIlIIII >> GLUtils.lllII[11] & GLUtils.lllII[10]) / 255.0f;
        final float lllIllIlIIIlIll = (lllIllIlIIlIIII & GLUtils.lllII[10]) / 255.0f;
        final float lllIllIlIIIlIlI = (lllIllIlIIIllll >> GLUtils.lllII[12] & GLUtils.lllII[10]) / 255.0f;
        final float lllIllIlIIIlIIl = (lllIllIlIIIllll >> GLUtils.lllII[9] & GLUtils.lllII[10]) / 255.0f;
        final float lllIllIlIIIlIII = (lllIllIlIIIllll >> GLUtils.lllII[11] & GLUtils.lllII[10]) / 255.0f;
        final float lllIllIlIIIIlll = (lllIllIlIIIllll & GLUtils.lllII[10]) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(GLUtils.lllII[25]);
        final Tessellator lllIllIlIIIIllI = Tessellator.getInstance();
        final BufferBuilder lllIllIlIIIIlIl = lllIllIlIIIIllI.getBuffer();
        lllIllIlIIIIlIl.begin(GLUtils.lllII[24], DefaultVertexFormats.POSITION_COLOR);
        lllIllIlIIIIlIl.pos(lllIllIlIIIIlII + (double)lllIllIlIIlIIlI, (double)lllIllIlIIlIIll, 0.0).color(lllIllIlIIIllIl, lllIllIlIIIllII, lllIllIlIIIlIll, lllIllIlIIIlllI).endVertex();
        lllIllIlIIIIlIl.pos((double)lllIllIlIIIIlII, (double)lllIllIlIIlIIll, 0.0).color(lllIllIlIIIllIl, lllIllIlIIIllII, lllIllIlIIIlIll, lllIllIlIIIlllI).endVertex();
        lllIllIlIIIIlIl.pos((double)lllIllIlIIIIlII, lllIllIlIIlIIll + (double)lllIllIlIIlIIIl, 0.0).color(lllIllIlIIIlIIl, lllIllIlIIIlIII, lllIllIlIIIIlll, lllIllIlIIIlIlI).endVertex();
        lllIllIlIIIIlIl.pos(lllIllIlIIIIlII + (double)lllIllIlIIlIIlI, lllIllIlIIlIIll + (double)lllIllIlIIlIIIl, 0.0).color(lllIllIlIIIlIIl, lllIllIlIIIlIII, lllIllIlIIIIlll, lllIllIlIIIlIlI).endVertex();
        lllIllIlIIIIllI.draw();
        GlStateManager.shadeModel(GLUtils.lllII[26]);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public static boolean isHovered(final int lllIlllIIlIIlIl, final int lllIlllIIlIIlII, final int lllIlllIIIlllIl, final int lllIlllIIIlllII, final int lllIlllIIlIIIIl, final int lllIlllIIlIIIII) {
        int n;
        if (lllIIlI(lllIlllIIlIIIIl, lllIlllIIlIIlIl) && lllIlIl(lllIlllIIlIIIIl, lllIlllIIlIIlIl + lllIlllIIIlllIl) && lllIIlI(lllIlllIIlIIIII, lllIlllIIlIIlII) && lllIIIl(lllIlllIIlIIIII, lllIlllIIlIIlII + lllIlllIIIlllII)) {
            n = GLUtils.lllII[1];
            "".length();
            if ("   ".length() == (0x41 ^ 0x5C ^ (0x3E ^ 0x27))) {
                return ((0x9C ^ 0xA2 ^ (0xE8 ^ 0x8C)) & (0x11 ^ 0x9 ^ (0xC8 ^ 0x8A) ^ -" ".length())) != 0x0;
            }
        }
        else {
            n = GLUtils.lllII[0];
        }
        return n != 0;
    }
    
    private static boolean lllIlIl(final int lllIllIIlIIIIll, final int lllIllIIlIIIIlI) {
        return lllIllIIlIIIIll <= lllIllIIlIIIIlI;
    }
}
