package HORIZON-6-0-SKIDPROTECTION;

import java.util.Hashtable;
import org.lwjgl.opengl.GL11;
import java.util.Properties;
import java.nio.ByteBuffer;

public class TextureAnimation
{
    private String HorizonCode_Horizon_È;
    private String Â;
    private int Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private int à;
    private CustomAnimationFrame[] Ø;
    private int áŒŠÆ;
    private ByteBuffer áˆºÑ¢Õ;
    
    public TextureAnimation(final String texFrom, final byte[] srcData, final String texTo, final int dstTexId, final int dstX, final int dstY, final int frameWidth, final int frameHeight, final Properties props, final int durDef) {
        this.HorizonCode_Horizon_È = null;
        this.Â = null;
        this.Ý = -1;
        this.Ø­áŒŠá = 0;
        this.Âµá€ = 0;
        this.Ó = 0;
        this.à = 0;
        this.Ø = null;
        this.áŒŠÆ = 0;
        this.áˆºÑ¢Õ = null;
        this.HorizonCode_Horizon_È = texFrom;
        this.Â = texTo;
        this.Ý = dstTexId;
        this.Ø­áŒŠá = dstX;
        this.Âµá€ = dstY;
        this.Ó = frameWidth;
        this.à = frameHeight;
        final int frameLen = frameWidth * frameHeight * 4;
        if (srcData.length % frameLen != 0) {
            Config.Â("Invalid animated texture length: " + srcData.length + ", frameWidth: " + frameHeight + ", frameHeight: " + frameHeight);
        }
        (this.áˆºÑ¢Õ = GLAllocation.Ý(srcData.length)).put(srcData);
        int numFrames = srcData.length / frameLen;
        if (props.get("tile.0") != null) {
            for (int durationDefStr = 0; props.get("tile." + durationDefStr) != null; ++durationDefStr) {
                numFrames = durationDefStr + 1;
            }
        }
        final String var21 = ((Hashtable<K, String>)props).get("duration");
        final int durationDef = Config.HorizonCode_Horizon_È(var21, durDef);
        this.Ø = new CustomAnimationFrame[numFrames];
        for (int i = 0; i < this.Ø.length; ++i) {
            final String indexStr = ((Hashtable<K, String>)props).get("tile." + i);
            final int index = Config.HorizonCode_Horizon_È(indexStr, i);
            final String durationStr = ((Hashtable<K, String>)props).get("duration." + i);
            final int duration = Config.HorizonCode_Horizon_È(durationStr, durationDef);
            final CustomAnimationFrame frm = new CustomAnimationFrame(index, duration);
            this.Ø[i] = frm;
        }
    }
    
    public boolean HorizonCode_Horizon_È() {
        if (this.Ø.length <= 0) {
            return false;
        }
        if (this.áŒŠÆ >= this.Ø.length) {
            this.áŒŠÆ = 0;
        }
        final CustomAnimationFrame customAnimationFrame;
        final CustomAnimationFrame frame = customAnimationFrame = this.Ø[this.áŒŠÆ];
        ++customAnimationFrame.Ý;
        if (frame.Ý < frame.Â) {
            return false;
        }
        frame.Ý = 0;
        ++this.áŒŠÆ;
        if (this.áŒŠÆ >= this.Ø.length) {
            this.áŒŠÆ = 0;
        }
        return true;
    }
    
    public int Â() {
        if (this.Ø.length <= 0) {
            return 0;
        }
        if (this.áŒŠÆ >= this.Ø.length) {
            this.áŒŠÆ = 0;
        }
        final CustomAnimationFrame frame = this.Ø[this.áŒŠÆ];
        return frame.HorizonCode_Horizon_È;
    }
    
    public int Ý() {
        return this.Ø.length;
    }
    
    public boolean Ø­áŒŠá() {
        if (!this.HorizonCode_Horizon_È()) {
            return false;
        }
        final int frameLen = this.Ó * this.à * 4;
        final int imgNum = this.Â();
        final int offset = frameLen * imgNum;
        if (offset + frameLen > this.áˆºÑ¢Õ.capacity()) {
            return false;
        }
        this.áˆºÑ¢Õ.position(offset);
        GlStateManager.áŒŠÆ(this.Ý);
        GL11.glTexSubImage2D(3553, 0, this.Ø­áŒŠá, this.Âµá€, this.Ó, this.à, 6408, 5121, this.áˆºÑ¢Õ);
        return true;
    }
    
    public String Âµá€() {
        return this.HorizonCode_Horizon_È;
    }
    
    public String Ó() {
        return this.Â;
    }
    
    public int à() {
        return this.Ý;
    }
}
