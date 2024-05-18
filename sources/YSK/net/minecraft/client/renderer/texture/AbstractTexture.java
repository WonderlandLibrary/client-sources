package net.minecraft.client.renderer.texture;

import org.lwjgl.opengl.*;

public abstract class AbstractTexture implements ITextureObject
{
    protected int glTextureId;
    protected boolean mipmapLast;
    protected boolean blurLast;
    protected boolean mipmap;
    protected boolean blur;
    
    @Override
    public int getGlTextureId() {
        if (this.glTextureId == -" ".length()) {
            this.glTextureId = TextureUtil.glGenTextures();
        }
        return this.glTextureId;
    }
    
    public AbstractTexture() {
        this.glTextureId = -" ".length();
    }
    
    public void setBlurMipmapDirect(final boolean blur, final boolean mipmap) {
        this.blur = blur;
        this.mipmap = mipmap;
        final int n = -" ".length();
        final int n2 = -" ".length();
        int n4;
        int n5;
        if (blur) {
            int n3;
            if (mipmap) {
                n3 = 6335 + 1140 - 7143 + 9655;
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                n3 = 7703 + 7988 - 9845 + 3883;
            }
            n4 = n3;
            n5 = 5179 + 9600 - 11110 + 6060;
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else {
            int n6;
            if (mipmap) {
                n6 = 605 + 7506 - 775 + 2650;
                "".length();
                if (0 == -1) {
                    throw null;
                }
            }
            else {
                n6 = 1102 + 1339 + 5877 + 1410;
            }
            n4 = n6;
            n5 = 7067 + 4194 - 4501 + 2968;
        }
        GL11.glTexParameteri(1750 + 191 + 359 + 1253, 2375 + 1585 + 213 + 6068, n4);
        GL11.glTexParameteri(2678 + 716 - 1009 + 1168, 7890 + 5235 - 9089 + 6204, n5);
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
            if (4 <= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void deleteGlTexture() {
        if (this.glTextureId != -" ".length()) {
            TextureUtil.deleteTexture(this.glTextureId);
            this.glTextureId = -" ".length();
        }
    }
    
    @Override
    public void setBlurMipmap(final boolean b, final boolean b2) {
        this.blurLast = this.blur;
        this.mipmapLast = this.mipmap;
        this.setBlurMipmapDirect(b, b2);
    }
    
    @Override
    public void restoreLastBlurMipmap() {
        this.setBlurMipmapDirect(this.blurLast, this.mipmapLast);
    }
}
