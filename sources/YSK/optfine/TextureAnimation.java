package optfine;

import net.minecraft.util.*;
import java.nio.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.texture.*;
import java.util.*;

public class TextureAnimation
{
    private int dstTextId;
    private int dstY;
    private int frameHeight;
    private CustomAnimationFrame[] frames;
    private int frameWidth;
    byte[] srcData;
    private int dstX;
    private String srcTex;
    private int activeFrame;
    ResourceLocation dstTexLoc;
    private ByteBuffer imageData;
    private String dstTex;
    private static final String[] I;
    
    public ResourceLocation getDstTexLoc() {
        return this.dstTexLoc;
    }
    
    public String getDstTex() {
        return this.dstTex;
    }
    
    static {
        I();
    }
    
    public int getFrameCount() {
        return this.frames.length;
    }
    
    public int getActiveFrameIndex() {
        if (this.frames.length <= 0) {
            return "".length();
        }
        if (this.activeFrame >= this.frames.length) {
            this.activeFrame = "".length();
        }
        return this.frames[this.activeFrame].index;
    }
    
    public boolean nextFrame() {
        if (this.frames.length <= 0) {
            return "".length() != 0;
        }
        if (this.activeFrame >= this.frames.length) {
            this.activeFrame = "".length();
        }
        final CustomAnimationFrame customAnimationFrame2;
        final CustomAnimationFrame customAnimationFrame = customAnimationFrame2 = this.frames[this.activeFrame];
        customAnimationFrame2.counter += " ".length();
        if (customAnimationFrame.counter < customAnimationFrame.duration) {
            return "".length() != 0;
        }
        customAnimationFrame.counter = "".length();
        this.activeFrame += " ".length();
        if (this.activeFrame >= this.frames.length) {
            this.activeFrame = "".length();
        }
        return " ".length() != 0;
    }
    
    public boolean updateTexture() {
        if (this.dstTextId < 0) {
            final ITextureObject texture = TextureUtils.getTexture(this.dstTexLoc);
            if (texture == null) {
                return "".length() != 0;
            }
            this.dstTextId = texture.getGlTextureId();
        }
        if (this.imageData == null) {
            (this.imageData = GLAllocation.createDirectByteBuffer(this.srcData.length)).put(this.srcData);
            this.srcData = null;
        }
        if (!this.nextFrame()) {
            return "".length() != 0;
        }
        final int n = this.frameWidth * this.frameHeight * (0x14 ^ 0x10);
        final int n2 = n * this.getActiveFrameIndex();
        if (n2 + n > this.imageData.capacity()) {
            return "".length() != 0;
        }
        this.imageData.position(n2);
        GlStateManager.bindTexture(this.dstTextId);
        GL11.glTexSubImage2D(3210 + 3050 - 3338 + 631, "".length(), this.dstX, this.dstY, this.frameWidth, this.frameHeight, 2144 + 5357 - 6125 + 5032, 5048 + 3669 - 8235 + 4639, this.imageData);
        return " ".length() != 0;
    }
    
    public String getSrcTex() {
        return this.srcTex;
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
            if (0 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public TextureAnimation(final String srcTex, final byte[] srcData, final String dstTex, final ResourceLocation dstTexLoc, final int dstX, final int dstY, final int frameWidth, final int frameHeight, final Properties properties, final int n) {
        this.srcTex = null;
        this.dstTex = null;
        this.dstTexLoc = null;
        this.dstTextId = -" ".length();
        this.dstX = "".length();
        this.dstY = "".length();
        this.frameWidth = "".length();
        this.frameHeight = "".length();
        this.frames = null;
        this.activeFrame = "".length();
        this.srcData = null;
        this.imageData = null;
        this.srcTex = srcTex;
        this.dstTex = dstTex;
        this.dstTexLoc = dstTexLoc;
        this.dstX = dstX;
        this.dstY = dstY;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        final int n2 = frameWidth * frameHeight * (0xB9 ^ 0xBD);
        if (srcData.length % n2 != 0) {
            Config.warn(TextureAnimation.I["".length()] + srcData.length + TextureAnimation.I[" ".length()] + frameWidth + TextureAnimation.I["  ".length()] + frameHeight);
        }
        this.srcData = srcData;
        int n3 = srcData.length / n2;
        if (properties.get(TextureAnimation.I["   ".length()]) != null) {
            int length = "".length();
            "".length();
            if (3 < 0) {
                throw null;
            }
            while (properties.get(TextureAnimation.I[0x95 ^ 0x91] + length) != null) {
                n3 = length + " ".length();
                ++length;
            }
        }
        final int int1 = Config.parseInt(((Hashtable<K, String>)properties).get(TextureAnimation.I[0x82 ^ 0x87]), n);
        this.frames = new CustomAnimationFrame[n3];
        int i = "".length();
        "".length();
        if (2 <= 1) {
            throw null;
        }
        while (i < this.frames.length) {
            this.frames[i] = new CustomAnimationFrame(Config.parseInt((String)properties.get(TextureAnimation.I[0x97 ^ 0x91] + i), i), Config.parseInt((String)properties.get(TextureAnimation.I[0xB0 ^ 0xB7] + i), int1));
            ++i;
        }
    }
    
    private static void I() {
        (I = new String[0xA5 ^ 0xAD])["".length()] = I("&=\u000409\u00067R0;\u0006>\u0013%0\u000bs\u00064-\u001b&\u00004u\u00036\u001c6!\u0007iR", "oSrQU");
        TextureAnimation.I[" ".length()] = I("^M(\u0013\t\u001f\b\u0019\b\f\u0006\u0005tA", "rmNah");
        TextureAnimation.I["  ".length()] = I("up\u0015\u0001\u000045;\u0016\b>8\u0007IA", "YPssa");
        TextureAnimation.I["   ".length()] = I("\u001a\u0013*(t^", "nzFMZ");
        TextureAnimation.I[0x75 ^ 0x71] = I("6(\r\u0012F", "BAawh");
        TextureAnimation.I[0x68 ^ 0x6D] = I("\r!?\"5\u0000;#", "iTMCA");
        TextureAnimation.I[0xAD ^ 0xAB] = I("\u0018\u0013\u001a'w", "lzvBY");
        TextureAnimation.I[0x3 ^ 0x4] = I(" :1)\u0012- -f", "DOCHf");
    }
}
