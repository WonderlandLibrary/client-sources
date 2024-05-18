package net.minecraft.client.renderer.tileentity;

import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.texture.*;

public abstract class TileEntitySpecialRenderer<T extends TileEntity>
{
    protected static final ResourceLocation[] DESTROY_STAGES;
    private static final String[] I;
    protected TileEntityRendererDispatcher rendererDispatcher;
    
    static {
        I();
        final ResourceLocation[] destroy_STAGES = new ResourceLocation[0xC8 ^ 0xC2];
        destroy_STAGES["".length()] = new ResourceLocation(TileEntitySpecialRenderer.I["".length()]);
        destroy_STAGES[" ".length()] = new ResourceLocation(TileEntitySpecialRenderer.I[" ".length()]);
        destroy_STAGES["  ".length()] = new ResourceLocation(TileEntitySpecialRenderer.I["  ".length()]);
        destroy_STAGES["   ".length()] = new ResourceLocation(TileEntitySpecialRenderer.I["   ".length()]);
        destroy_STAGES[0x39 ^ 0x3D] = new ResourceLocation(TileEntitySpecialRenderer.I[0x9D ^ 0x99]);
        destroy_STAGES[0xA7 ^ 0xA2] = new ResourceLocation(TileEntitySpecialRenderer.I[0x67 ^ 0x62]);
        destroy_STAGES[0xBE ^ 0xB8] = new ResourceLocation(TileEntitySpecialRenderer.I[0xAA ^ 0xAC]);
        destroy_STAGES[0xB7 ^ 0xB0] = new ResourceLocation(TileEntitySpecialRenderer.I[0x3D ^ 0x3A]);
        destroy_STAGES[0x57 ^ 0x5F] = new ResourceLocation(TileEntitySpecialRenderer.I[0x38 ^ 0x30]);
        destroy_STAGES[0x81 ^ 0x88] = new ResourceLocation(TileEntitySpecialRenderer.I[0x44 ^ 0x4D]);
        DESTROY_STAGES = destroy_STAGES;
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
            if (-1 >= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean func_181055_a() {
        return "".length() != 0;
    }
    
    public abstract void renderTileEntityAt(final T p0, final double p1, final double p2, final double p3, final float p4, final int p5);
    
    protected World getWorld() {
        return this.rendererDispatcher.worldObj;
    }
    
    public FontRenderer getFontRenderer() {
        return this.rendererDispatcher.getFontRenderer();
    }
    
    protected void bindTexture(final ResourceLocation resourceLocation) {
        final TextureManager renderEngine = this.rendererDispatcher.renderEngine;
        if (renderEngine != null) {
            renderEngine.bindTexture(resourceLocation);
        }
    }
    
    public void setRendererDispatcher(final TileEntityRendererDispatcher rendererDispatcher) {
        this.rendererDispatcher = rendererDispatcher;
    }
    
    private static void I() {
        (I = new String[0x17 ^ 0x1D])["".length()] = I("5-\u001b&$3-\u0010}3-'\u00009\"n,\u0006!%3'\u001a\r\"5)\u00047\u000eqf\u0013<6", "AHcRQ");
        TileEntitySpecialRenderer.I[" ".length()] = I("0\u000b.\u000e\u00056\u000b%U\u0012(\u00015\u0011\u0003k\n3\t\u00046\u0001/%\u00030\u000f1\u001f/u@&\u0014\u0017", "DnVzp");
        TileEntitySpecialRenderer.I["  ".length()] = I("\u0007)\u000b\u0004#\u0001)\u0000_4\u001f#\u0010\u001b%\\(\u0016\u0003\"\u0001#\n/%\u0007-\u0014\u0015\tAb\u0003\u001e1", "sLspV");
        TileEntitySpecialRenderer.I["   ".length()] = I(">#\f\u0019\u00068#\u0007B\u0011&)\u0017\u0006\u0000e\"\u0011\u001e\u00078)\r2\u0000>'\u0013\b,yh\u0004\u0003\u0014", "JFtms");
        TileEntitySpecialRenderer.I[0x3F ^ 0x3B] = I("%\u0001-9&#\u0001&b1=\u000b6& ~\u00000>'#\u000b,\u0012 %\u00052(\feJ%#4", "QdUMS");
        TileEntitySpecialRenderer.I[0x22 ^ 0x27] = I("\f)/\u00074\n)$\\#\u0014#4\u00182W(2\u00005\n#.,2\f-0\u0016\u001eMb'\u001d&", "xLWsA");
        TileEntitySpecialRenderer.I[0x58 ^ 0x5E] = I("\u0001\u0012?\u000e\u0007\u0007\u00124U\u0010\u0019\u0018$\u0011\u0001Z\u0013\"\t\u0006\u0007\u0018>%\u0001\u0001\u0016 \u001f-CY7\u0014\u0015", "uwGzr");
        TileEntitySpecialRenderer.I[0x94 ^ 0x93] = I("2,,\u001f94,'D.*&7\u0000?i-1\u001884&-4?2(3\u000e\u0013qg$\u0005+", "FITkL");
        TileEntitySpecialRenderer.I[0xCD ^ 0xC5] = I("\u0000*1,\u0000\u0006*:w\u0017\u0018 *3\u0006[+,+\u0001\u0006 0\u0007\u0006\u0000..=*La96\u0012", "tOIXu");
        TileEntitySpecialRenderer.I[0x85 ^ 0x8C] = I("\u00114\u0000\u0016\u001d\u00174\u000bM\n\t>\u001b\t\u001bJ5\u001d\u0011\u001c\u0017>\u0001=\u001b\u00110\u001f\u00077\\\u007f\b\f\u000f", "eQxbh");
    }
}
