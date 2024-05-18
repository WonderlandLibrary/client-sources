package net.minecraft.client.shader;

import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import java.nio.*;

public class Framebuffer
{
    public int framebufferTextureWidth;
    public int framebufferTextureHeight;
    public float[] framebufferColor;
    public int framebufferFilter;
    public int framebufferObject;
    public int framebufferWidth;
    public int framebufferHeight;
    private static final String[] I;
    public int framebufferTexture;
    public boolean useDepth;
    public int depthBuffer;
    
    private static void I() {
        (I = new String[0x15 ^ 0x10])["".length()] = I("%&7?\u001a#'-;\u001d$,-+\u0017+$+6\u00052&--\r=+<-\t!\"%<\u00066", "bjhyH");
        Framebuffer.I[" ".length()] = I("\u001d\u0004\u000f\r\u0004\u001b\u0005\u0015\t\u0003\u001c\u000e\u0015\u0019\t\u0013\u0006\u0013\u0004\u001b\n\u0004\u0015\u001f\u0013\u0005\u0005\u0019\u0018\u0005\u0013\u0006\u0017\u0014\u0017\u000e\u001c\u0011\b\u001e\u0017\r\u001e\u001f", "ZHPKV");
        Framebuffer.I["  ".length()] = I("5=\u001a\u0013\u00193<\u0000\u0017\u001e47\u0000\u0007\u0014;?\u0006\u001a\u0006\"=\u0000\u0001\u000e-5\u0017\u0014\u001c-3\u0010\u0013\r7#", "rqEUK");
        Framebuffer.I["   ".length()] = I("2\u0018\u0006\u0012\u001f4\u0019\u001c\u0016\u00183\u0012\u001c\u0006\u0012<\u001a\u001a\u001b\u0000%\u0018\u001c\u0000\b*\u0006\u001c\u0015\t*\u0016\f\u0012\u000b0\u0006", "uTYTM");
        Framebuffer.I[0x7 ^ 0x3] = I("3\u0018\n+\u000f7\u001f\u000f1\u000b9\u0011+6\f2\u0011;\u0010\u001e5\u0000<0J&\u0011=6\u0018:\u0011-c\u001f:\u001f',\u001d:T:7\u000b \u0001:y", "TtICj");
    }
    
    public void checkFramebufferComplete() {
        final int glCheckFramebufferStatus = OpenGlHelper.glCheckFramebufferStatus(OpenGlHelper.GL_FRAMEBUFFER);
        if (glCheckFramebufferStatus == OpenGlHelper.GL_FRAMEBUFFER_COMPLETE) {
            return;
        }
        if (glCheckFramebufferStatus == OpenGlHelper.GL_FB_INCOMPLETE_ATTACHMENT) {
            throw new RuntimeException(Framebuffer.I["".length()]);
        }
        if (glCheckFramebufferStatus == OpenGlHelper.GL_FB_INCOMPLETE_MISS_ATTACH) {
            throw new RuntimeException(Framebuffer.I[" ".length()]);
        }
        if (glCheckFramebufferStatus == OpenGlHelper.GL_FB_INCOMPLETE_DRAW_BUFFER) {
            throw new RuntimeException(Framebuffer.I["  ".length()]);
        }
        if (glCheckFramebufferStatus == OpenGlHelper.GL_FB_INCOMPLETE_READ_BUFFER) {
            throw new RuntimeException(Framebuffer.I["   ".length()]);
        }
        throw new RuntimeException(Framebuffer.I[0xA9 ^ 0xAD] + glCheckFramebufferStatus);
    }
    
    public void framebufferRender(final int n, final int n2) {
        this.framebufferRenderExt(n, n2, " ".length() != 0);
    }
    
    public Framebuffer(final int n, final int n2, final boolean useDepth) {
        this.useDepth = useDepth;
        this.framebufferObject = -" ".length();
        this.framebufferTexture = -" ".length();
        this.depthBuffer = -" ".length();
        (this.framebufferColor = new float[0x7A ^ 0x7E])["".length()] = 1.0f;
        this.framebufferColor[" ".length()] = 1.0f;
        this.framebufferColor["  ".length()] = 1.0f;
        this.framebufferColor["   ".length()] = 0.0f;
        this.createBindFramebuffer(n, n2);
    }
    
    public void unbindFramebufferTexture() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            GlStateManager.bindTexture("".length());
        }
    }
    
    public void setFramebufferFilter(final int framebufferFilter) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            this.framebufferFilter = framebufferFilter;
            GlStateManager.bindTexture(this.framebufferTexture);
            GL11.glTexParameterf(116 + 3073 - 1286 + 1650, 2258 + 5396 - 1372 + 3959, (float)framebufferFilter);
            GL11.glTexParameterf(1720 + 1441 - 2022 + 2414, 128 + 8684 - 920 + 2348, (float)framebufferFilter);
            GL11.glTexParameterf(1203 + 2211 - 2323 + 2462, 9125 + 8166 - 9958 + 2909, 10496.0f);
            GL11.glTexParameterf(697 + 2838 - 3421 + 3439, 5291 + 5375 - 1983 + 1560, 10496.0f);
            GlStateManager.bindTexture("".length());
        }
    }
    
    public void framebufferRenderExt(final int n, final int n2, final boolean b) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            GlStateManager.colorMask(" ".length() != 0, " ".length() != 0, " ".length() != 0, "".length() != 0);
            GlStateManager.disableDepth();
            GlStateManager.depthMask("".length() != 0);
            GlStateManager.matrixMode(1283 + 363 + 1769 + 2474);
            GlStateManager.loadIdentity();
            GlStateManager.ortho(0.0, n, n2, 0.0, 1000.0, 3000.0);
            GlStateManager.matrixMode(286 + 1898 - 1391 + 5095);
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0f, 0.0f, -2000.0f);
            GlStateManager.viewport("".length(), "".length(), n, n2);
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableAlpha();
            if (b) {
                GlStateManager.disableBlend();
                GlStateManager.enableColorMaterial();
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.bindFramebufferTexture();
            final float n3 = n;
            final float n4 = n2;
            final float n5 = this.framebufferWidth / this.framebufferTextureWidth;
            final float n6 = this.framebufferHeight / this.framebufferTextureHeight;
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.begin(0x92 ^ 0x95, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldRenderer.pos(0.0, n4, 0.0).tex(0.0, 0.0).color(161 + 239 - 344 + 199, 55 + 248 - 255 + 207, 5 + 201 - 174 + 223, 141 + 111 - 88 + 91).endVertex();
            worldRenderer.pos(n3, n4, 0.0).tex(n5, 0.0).color(195 + 174 - 315 + 201, 250 + 103 - 264 + 166, 146 + 92 - 205 + 222, 154 + 237 - 313 + 177).endVertex();
            worldRenderer.pos(n3, 0.0, 0.0).tex(n5, n6).color(103 + 223 - 89 + 18, 97 + 76 - 170 + 252, 129 + 59 - 172 + 239, 253 + 128 - 240 + 114).endVertex();
            worldRenderer.pos(0.0, 0.0, 0.0).tex(0.0, n6).color(133 + 106 - 77 + 93, 166 + 17 - 6 + 78, 161 + 1 - 36 + 129, 147 + 19 - 88 + 177).endVertex();
            instance.draw();
            this.unbindFramebufferTexture();
            GlStateManager.depthMask(" ".length() != 0);
            GlStateManager.colorMask(" ".length() != 0, " ".length() != 0, " ".length() != 0, " ".length() != 0);
        }
    }
    
    public void deleteFramebuffer() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            this.unbindFramebufferTexture();
            this.unbindFramebuffer();
            if (this.depthBuffer > -" ".length()) {
                OpenGlHelper.glDeleteRenderbuffers(this.depthBuffer);
                this.depthBuffer = -" ".length();
            }
            if (this.framebufferTexture > -" ".length()) {
                TextureUtil.deleteTexture(this.framebufferTexture);
                this.framebufferTexture = -" ".length();
            }
            if (this.framebufferObject > -" ".length()) {
                OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, "".length());
                OpenGlHelper.glDeleteFramebuffers(this.framebufferObject);
                this.framebufferObject = -" ".length();
            }
        }
    }
    
    static {
        I();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void framebufferClear() {
        this.bindFramebuffer(" ".length() != 0);
        GlStateManager.clearColor(this.framebufferColor["".length()], this.framebufferColor[" ".length()], this.framebufferColor["  ".length()], this.framebufferColor["   ".length()]);
        int n = 8841 + 13130 - 18021 + 12434;
        if (this.useDepth) {
            GlStateManager.clearDepth(1.0);
            n |= 122 + 18 - 79 + 195;
        }
        GlStateManager.clear(n);
        this.unbindFramebuffer();
    }
    
    public void unbindFramebuffer() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, "".length());
        }
    }
    
    public void bindFramebufferTexture() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            GlStateManager.bindTexture(this.framebufferTexture);
        }
    }
    
    public void createBindFramebuffer(final int framebufferWidth, final int framebufferHeight) {
        if (!OpenGlHelper.isFramebufferEnabled()) {
            this.framebufferWidth = framebufferWidth;
            this.framebufferHeight = framebufferHeight;
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        else {
            GlStateManager.enableDepth();
            if (this.framebufferObject >= 0) {
                this.deleteFramebuffer();
            }
            this.createFramebuffer(framebufferWidth, framebufferHeight);
            this.checkFramebufferComplete();
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, "".length());
        }
    }
    
    public void setFramebufferColor(final float n, final float n2, final float n3, final float n4) {
        this.framebufferColor["".length()] = n;
        this.framebufferColor[" ".length()] = n2;
        this.framebufferColor["  ".length()] = n3;
        this.framebufferColor["   ".length()] = n4;
    }
    
    public void bindFramebuffer(final boolean b) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebufferObject);
            if (b) {
                GlStateManager.viewport("".length(), "".length(), this.framebufferWidth, this.framebufferHeight);
            }
        }
    }
    
    public void createFramebuffer(final int n, final int n2) {
        this.framebufferWidth = n;
        this.framebufferHeight = n2;
        this.framebufferTextureWidth = n;
        this.framebufferTextureHeight = n2;
        if (!OpenGlHelper.isFramebufferEnabled()) {
            this.framebufferClear();
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        else {
            this.framebufferObject = OpenGlHelper.glGenFramebuffers();
            this.framebufferTexture = TextureUtil.glGenTextures();
            if (this.useDepth) {
                this.depthBuffer = OpenGlHelper.glGenRenderbuffers();
            }
            this.setFramebufferFilter(9590 + 213 - 4111 + 4036);
            GlStateManager.bindTexture(this.framebufferTexture);
            GL11.glTexImage2D(364 + 2632 - 1715 + 2272, "".length(), 21281 + 19834 - 18036 + 9777, this.framebufferTextureWidth, this.framebufferTextureHeight, "".length(), 6313 + 4931 - 6928 + 2092, 1552 + 1352 - 1024 + 3241, (ByteBuffer)null);
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebufferObject);
            OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0, 1010 + 2812 - 2094 + 1825, this.framebufferTexture, "".length());
            if (this.useDepth) {
                OpenGlHelper.glBindRenderbuffer(OpenGlHelper.GL_RENDERBUFFER, this.depthBuffer);
                OpenGlHelper.glRenderbufferStorage(OpenGlHelper.GL_RENDERBUFFER, 29969 + 3173 - 32736 + 32784, this.framebufferTextureWidth, this.framebufferTextureHeight);
                OpenGlHelper.glFramebufferRenderbuffer(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_DEPTH_ATTACHMENT, OpenGlHelper.GL_RENDERBUFFER, this.depthBuffer);
            }
            this.framebufferClear();
            this.unbindFramebufferTexture();
        }
    }
}
