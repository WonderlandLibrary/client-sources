package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Map;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Display;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.List;
import com.google.common.base.Strings;

public class GuiOverlayDebug extends Gui_1808253012
{
    private final Minecraft HorizonCode_Horizon_È;
    private final FontRenderer Â;
    private static final String Ý = "CL_00001956";
    
    public GuiOverlayDebug(final Minecraft mc) {
        this.HorizonCode_Horizon_È = mc;
        this.Â = mc.µà;
    }
    
    public void HorizonCode_Horizon_È(final ScaledResolution scaledResolutionIn) {
        this.HorizonCode_Horizon_È.ÇŽÕ.HorizonCode_Horizon_È("debug");
        GlStateManager.Çªà¢();
        this.HorizonCode_Horizon_È();
        this.Â(scaledResolutionIn);
        GlStateManager.Ê();
        Lagometer.HorizonCode_Horizon_È(scaledResolutionIn);
        this.HorizonCode_Horizon_È.ÇŽÕ.Â();
    }
    
    private boolean Ø­áŒŠá() {
        return this.HorizonCode_Horizon_È.á.¥Ðƒá() || this.HorizonCode_Horizon_È.ŠÄ.Ðƒáƒ;
    }
    
    protected void HorizonCode_Horizon_È() {
        final List var1 = this.Â();
        for (int var2 = 0; var2 < var1.size(); ++var2) {
            final String var3 = var1.get(var2);
            if (!Strings.isNullOrEmpty(var3)) {
                final int var4 = this.Â.HorizonCode_Horizon_È;
                final int var5 = this.Â.HorizonCode_Horizon_È(var3);
                final boolean var6 = true;
                final int var7 = 2 + var4 * var2;
                Gui_1808253012.HorizonCode_Horizon_È(1, var7 - 1, 2 + var5 + 1, var7 + var4 - 1, -1873784752);
                this.Â.HorizonCode_Horizon_È(var3, 2, var7, 14737632);
            }
        }
    }
    
    protected void Â(final ScaledResolution p_175239_1_) {
        final List var2 = this.Ý();
        for (int var3 = 0; var3 < var2.size(); ++var3) {
            final String var4 = var2.get(var3);
            if (!Strings.isNullOrEmpty(var4)) {
                final int var5 = this.Â.HorizonCode_Horizon_È;
                final int var6 = this.Â.HorizonCode_Horizon_È(var4);
                final int var7 = p_175239_1_.HorizonCode_Horizon_È() - 2 - var6;
                final int var8 = 2 + var5 * var3;
                Gui_1808253012.HorizonCode_Horizon_È(var7 - 1, var8 - 1, var7 + var6 + 1, var8 + var5 - 1, -1873784752);
                this.Â.HorizonCode_Horizon_È(var4, var7, var8, 14737632);
            }
        }
    }
    
    protected List Â() {
        final BlockPos var1 = new BlockPos(this.HorizonCode_Horizon_È.ÇŽá€().ŒÏ, this.HorizonCode_Horizon_È.ÇŽá€().£É().Â, this.HorizonCode_Horizon_È.ÇŽá€().Ê);
        if (this.Ø­áŒŠá()) {
            return Lists.newArrayList((Object[])new String[] { "Minecraft 1.8 (" + this.HorizonCode_Horizon_È.Ø­áŒŠá() + "/" + ClientBrandRetriever.HorizonCode_Horizon_È() + ")", this.HorizonCode_Horizon_È.á€, this.HorizonCode_Horizon_È.áˆºÑ¢Õ.Ó(), this.HorizonCode_Horizon_È.áˆºÑ¢Õ.à(), "P: " + this.HorizonCode_Horizon_È.Å.Â() + ". T: " + this.HorizonCode_Horizon_È.áŒŠÆ.Å(), this.HorizonCode_Horizon_È.áŒŠÆ.£à(), "", String.format("Chunk-relative: %d %d %d", var1.HorizonCode_Horizon_È() & 0xF, var1.Â() & 0xF, var1.Ý() & 0xF) });
        }
        final Entity var2 = this.HorizonCode_Horizon_È.ÇŽá€();
        final EnumFacing var3 = var2.ˆà¢();
        String var4 = "Invalid";
        switch (GuiOverlayDebug.HorizonCode_Horizon_È.HorizonCode_Horizon_È[var3.ordinal()]) {
            case 1: {
                var4 = "Towards negative Z";
                break;
            }
            case 2: {
                var4 = "Towards positive Z";
                break;
            }
            case 3: {
                var4 = "Towards negative X";
                break;
            }
            case 4: {
                var4 = "Towards positive X";
                break;
            }
        }
        final ArrayList var5 = Lists.newArrayList((Object[])new String[] { "Minecraft 1.8 (" + this.HorizonCode_Horizon_È.Ø­áŒŠá() + "/" + ClientBrandRetriever.HorizonCode_Horizon_È() + ")", this.HorizonCode_Horizon_È.á€, this.HorizonCode_Horizon_È.áˆºÑ¢Õ.Ó(), this.HorizonCode_Horizon_È.áˆºÑ¢Õ.à(), "P: " + this.HorizonCode_Horizon_È.Å.Â() + ". T: " + this.HorizonCode_Horizon_È.áŒŠÆ.Å(), this.HorizonCode_Horizon_È.áŒŠÆ.£à(), "", String.format("XYZ: %.3f / %.5f / %.3f", this.HorizonCode_Horizon_È.ÇŽá€().ŒÏ, this.HorizonCode_Horizon_È.ÇŽá€().£É().Â, this.HorizonCode_Horizon_È.ÇŽá€().Ê), String.format("Block: %d %d %d", var1.HorizonCode_Horizon_È(), var1.Â(), var1.Ý()), String.format("Chunk: %d %d %d in %d %d %d", var1.HorizonCode_Horizon_È() & 0xF, var1.Â() & 0xF, var1.Ý() & 0xF, var1.HorizonCode_Horizon_È() >> 4, var1.Â() >> 4, var1.Ý() >> 4), String.format("Facing: %s (%s) (%.1f / %.1f)", var3, var4, MathHelper.à(var2.É), MathHelper.à(var2.áƒ)) });
        if (this.HorizonCode_Horizon_È.áŒŠÆ != null && this.HorizonCode_Horizon_È.áŒŠÆ.Ó(var1)) {
            final Chunk var6 = this.HorizonCode_Horizon_È.áŒŠÆ.à(var1);
            var5.add("Biome: " + var6.HorizonCode_Horizon_È(var1, this.HorizonCode_Horizon_È.áŒŠÆ.áŒŠÆ()).£Ï);
            var5.add("Light: " + var6.HorizonCode_Horizon_È(var1, 0) + " (" + var6.HorizonCode_Horizon_È(EnumSkyBlock.HorizonCode_Horizon_È, var1) + " sky, " + var6.HorizonCode_Horizon_È(EnumSkyBlock.Â, var1) + " block)");
            DifficultyInstance var7 = this.HorizonCode_Horizon_È.áŒŠÆ.Ê(var1);
            if (this.HorizonCode_Horizon_È.Ê() && this.HorizonCode_Horizon_È.ˆá() != null) {
                final EntityPlayerMP var8 = this.HorizonCode_Horizon_È.ˆá().Œ().HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.á.£áŒŠá());
                if (var8 != null) {
                    var7 = var8.Ï­Ðƒà.Ê(new BlockPos(var8));
                }
            }
            var5.add(String.format("Local Difficulty: %.2f (Day %d)", var7.HorizonCode_Horizon_È(), this.HorizonCode_Horizon_È.áŒŠÆ.Ï­Ðƒà() / 24000L));
        }
        if (this.HorizonCode_Horizon_È.µÕ != null && this.HorizonCode_Horizon_È.µÕ.HorizonCode_Horizon_È()) {
            var5.add("Shader: " + this.HorizonCode_Horizon_È.µÕ.Âµá€().Â());
        }
        if (this.HorizonCode_Horizon_È.áŒŠà != null && this.HorizonCode_Horizon_È.áŒŠà.HorizonCode_Horizon_È == MovingObjectPosition.HorizonCode_Horizon_È.Â && this.HorizonCode_Horizon_È.áŒŠà.HorizonCode_Horizon_È() != null) {
            final BlockPos var9 = this.HorizonCode_Horizon_È.áŒŠà.HorizonCode_Horizon_È();
            var5.add(String.format("Looking at: %d %d %d", var9.HorizonCode_Horizon_È(), var9.Â(), var9.Ý()));
        }
        return var5;
    }
    
    protected List Ý() {
        final long var1 = Runtime.getRuntime().maxMemory();
        final long var2 = Runtime.getRuntime().totalMemory();
        final long var3 = Runtime.getRuntime().freeMemory();
        final long var4 = var2 - var3;
        final ArrayList var5 = Lists.newArrayList((Object[])new String[] { String.format("Java: %s %dbit", System.getProperty("java.version"), this.HorizonCode_Horizon_È.ˆáŠ() ? 64 : 32), String.format("Mem: % 2d%% %03d/%03dMB", var4 * 100L / var1, HorizonCode_Horizon_È(var4), HorizonCode_Horizon_È(var1)), String.format("Allocated: % 2d%% %03dMB", var2 * 100L / var1, HorizonCode_Horizon_È(var2)), "", String.format("Display: %dx%d (%s)", Display.getWidth(), Display.getHeight(), GL11.glGetString(7936)), GL11.glGetString(7937), GL11.glGetString(7938) });
        if (this.Ø­áŒŠá()) {
            return var5;
        }
        if (this.HorizonCode_Horizon_È.áŒŠà != null && this.HorizonCode_Horizon_È.áŒŠà.HorizonCode_Horizon_È == MovingObjectPosition.HorizonCode_Horizon_È.Â && this.HorizonCode_Horizon_È.áŒŠà.HorizonCode_Horizon_È() != null) {
            final BlockPos var6 = this.HorizonCode_Horizon_È.áŒŠà.HorizonCode_Horizon_È();
            IBlockState var7 = this.HorizonCode_Horizon_È.áŒŠÆ.Â(var6);
            if (this.HorizonCode_Horizon_È.áŒŠÆ.s_() != WorldType.Ø) {
                var7 = var7.Ý().HorizonCode_Horizon_È(var7, this.HorizonCode_Horizon_È.áŒŠÆ, var6);
            }
            var5.add("");
            var5.add(String.valueOf(Block.HorizonCode_Horizon_È.Â(var7.Ý())));
            for (final Map.Entry var9 : var7.Â().entrySet()) {
                String var10 = var9.getValue().toString();
                if (var9.getValue() == Boolean.TRUE) {
                    var10 = EnumChatFormatting.ÂµÈ + var10;
                }
                else if (var9.getValue() == Boolean.FALSE) {
                    var10 = EnumChatFormatting.ˆÏ­ + var10;
                }
                var5.add(String.valueOf(var9.getKey().HorizonCode_Horizon_È()) + ": " + var10);
            }
        }
        return var5;
    }
    
    private static long HorizonCode_Horizon_È(final long p_175240_0_) {
        return p_175240_0_ / 1024L / 1024L;
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00001955";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                GuiOverlayDebug.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                GuiOverlayDebug.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                GuiOverlayDebug.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                GuiOverlayDebug.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
