package net.minecraft.src;

import java.nio.*;
import org.lwjgl.opengl.*;
import java.util.*;

public class TextureAnimation
{
    private String srcTex;
    private String dstTex;
    private int dstTextId;
    private int dstX;
    private int dstY;
    private int frameWidth;
    private int frameHeight;
    private CustomAnimationFrame[] frames;
    private int activeFrame;
    private ByteBuffer imageData;
    
    public TextureAnimation(final String var1, final byte[] var2, final String var3, final int var4, final int var5, final int var6, final int var7, final int var8, final Properties var9, final int var10) {
        this.srcTex = null;
        this.dstTex = null;
        this.dstTextId = -1;
        this.dstX = 0;
        this.dstY = 0;
        this.frameWidth = 0;
        this.frameHeight = 0;
        this.frames = null;
        this.activeFrame = 0;
        this.imageData = null;
        this.srcTex = var1;
        this.dstTex = var3;
        this.dstTextId = var4;
        this.dstX = var5;
        this.dstY = var6;
        this.frameWidth = var7;
        this.frameHeight = var8;
        final int var11 = var7 * var8 * 4;
        if (var2.length % var11 != 0) {
            Config.dbg("Invalid animated texture length: " + var2.length + ", frameWidth: " + var8 + ", frameHeight: " + var8);
        }
        (this.imageData = GLAllocation.createDirectByteBuffer(var2.length)).put(var2);
        int var12 = var2.length / var11;
        if (var9.get("tile.0") != null) {
            for (int var13 = 0; var9.get("tile." + var13) != null; ++var13) {
                var12 = var13 + 1;
            }
        }
        final String var14 = ((Hashtable<K, String>)var9).get("duration");
        final int var15 = Config.parseInt(var14, var10);
        this.frames = new CustomAnimationFrame[var12];
        for (int var16 = 0; var16 < this.frames.length; ++var16) {
            final String var17 = ((Hashtable<K, String>)var9).get("tile." + var16);
            final int var18 = Config.parseInt(var17, var16);
            final String var19 = ((Hashtable<K, String>)var9).get("duration." + var16);
            final int var20 = Config.parseInt(var19, var15);
            final CustomAnimationFrame var21 = new CustomAnimationFrame(var18, var20);
            this.frames[var16] = var21;
        }
    }
    
    public boolean nextFrame() {
        if (this.frames.length <= 0) {
            return false;
        }
        if (this.activeFrame >= this.frames.length) {
            this.activeFrame = 0;
        }
        final CustomAnimationFrame customAnimationFrame;
        final CustomAnimationFrame var1 = customAnimationFrame = this.frames[this.activeFrame];
        ++customAnimationFrame.counter;
        if (var1.counter < var1.duration) {
            return false;
        }
        var1.counter = 0;
        ++this.activeFrame;
        if (this.activeFrame >= this.frames.length) {
            this.activeFrame = 0;
        }
        return true;
    }
    
    public int getActiveFrameIndex() {
        if (this.frames.length <= 0) {
            return 0;
        }
        if (this.activeFrame >= this.frames.length) {
            this.activeFrame = 0;
        }
        final CustomAnimationFrame var1 = this.frames[this.activeFrame];
        return var1.index;
    }
    
    public int getFrameCount() {
        return this.frames.length;
    }
    
    public boolean updateTexture() {
        if (!this.nextFrame()) {
            return false;
        }
        final int var1 = this.frameWidth * this.frameHeight * 4;
        final int var2 = this.getActiveFrameIndex();
        final int var3 = var1 * var2;
        if (var3 + var1 > this.imageData.capacity()) {
            return false;
        }
        this.imageData.position(var3);
        Config.getRenderEngine().bindTexture(this.dstTextId);
        GL11.glTexSubImage2D(3553, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, 6408, 5121, this.imageData);
        return true;
    }
}
