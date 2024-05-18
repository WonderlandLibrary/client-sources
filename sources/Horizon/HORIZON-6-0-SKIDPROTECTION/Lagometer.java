package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;

public class Lagometer
{
    private static Minecraft áŒŠÆ;
    private static GameSettings áˆºÑ¢Õ;
    private static Profiler ÂµÈ;
    public static boolean HorizonCode_Horizon_È;
    public static HorizonCode_Horizon_È Â;
    public static HorizonCode_Horizon_È Ý;
    public static HorizonCode_Horizon_È Ø­áŒŠá;
    public static HorizonCode_Horizon_È Âµá€;
    public static HorizonCode_Horizon_È Ó;
    public static HorizonCode_Horizon_È à;
    public static HorizonCode_Horizon_È Ø;
    private static long[] á;
    private static long[] ˆÏ­;
    private static long[] £á;
    private static long[] Å;
    private static long[] £à;
    private static long[] µà;
    private static long[] ˆà;
    private static long[] ¥Æ;
    private static boolean[] Ø­à;
    private static int µÕ;
    private static long Æ;
    private static long Šáƒ;
    private static long Ï­Ðƒà;
    private static long áŒŠà;
    private static long ŠÄ;
    private static long Ñ¢á;
    private static long ŒÏ;
    private static long Çªà¢;
    private static int Ê;
    
    static {
        Lagometer.HorizonCode_Horizon_È = false;
        Lagometer.Â = new HorizonCode_Horizon_È();
        Lagometer.Ý = new HorizonCode_Horizon_È();
        Lagometer.Ø­áŒŠá = new HorizonCode_Horizon_È();
        Lagometer.Âµá€ = new HorizonCode_Horizon_È();
        Lagometer.Ó = new HorizonCode_Horizon_È();
        Lagometer.à = new HorizonCode_Horizon_È();
        Lagometer.Ø = new HorizonCode_Horizon_È();
        Lagometer.á = new long[512];
        Lagometer.ˆÏ­ = new long[512];
        Lagometer.£á = new long[512];
        Lagometer.Å = new long[512];
        Lagometer.£à = new long[512];
        Lagometer.µà = new long[512];
        Lagometer.ˆà = new long[512];
        Lagometer.¥Æ = new long[512];
        Lagometer.Ø­à = new boolean[512];
        Lagometer.µÕ = 0;
        Lagometer.Æ = -1L;
        Lagometer.Šáƒ = 0L;
        Lagometer.Ï­Ðƒà = System.currentTimeMillis();
        Lagometer.áŒŠà = Ø­áŒŠá();
        Lagometer.ŠÄ = Lagometer.Ï­Ðƒà;
        Lagometer.Ñ¢á = Lagometer.áŒŠà;
        Lagometer.ŒÏ = 1L;
        Lagometer.Çªà¢ = 0L;
        Lagometer.Ê = 0;
    }
    
    public static boolean HorizonCode_Horizon_È() {
        final long timeNowMs = System.currentTimeMillis();
        final long memNow = Ø­áŒŠá();
        boolean gc = false;
        if (memNow < Lagometer.Ñ¢á) {
            final double memDiffMb = Lagometer.Çªà¢ / 1000000.0;
            final double timeDiffSec = Lagometer.ŒÏ / 1000.0;
            final int mbSec = (int)(memDiffMb / timeDiffSec);
            if (mbSec > 0) {
                Lagometer.Ê = mbSec;
            }
            Lagometer.Ï­Ðƒà = timeNowMs;
            Lagometer.áŒŠà = memNow;
            Lagometer.ŒÏ = 0L;
            Lagometer.Çªà¢ = 0L;
            gc = true;
        }
        else {
            Lagometer.ŒÏ = timeNowMs - Lagometer.Ï­Ðƒà;
            Lagometer.Çªà¢ = memNow - Lagometer.áŒŠà;
        }
        Lagometer.ŠÄ = timeNowMs;
        Lagometer.Ñ¢á = memNow;
        return gc;
    }
    
    private static long Ø­áŒŠá() {
        final Runtime r = Runtime.getRuntime();
        return r.totalMemory() - r.freeMemory();
    }
    
    public static void Â() {
        if (Lagometer.áŒŠÆ == null) {
            Lagometer.áŒŠÆ = Minecraft.áŒŠà();
            Lagometer.áˆºÑ¢Õ = Lagometer.áŒŠÆ.ŠÄ;
            Lagometer.ÂµÈ = Lagometer.áŒŠÆ.ÇŽÕ;
        }
        if (Lagometer.áˆºÑ¢Õ.µÐƒÓ && Lagometer.áˆºÑ¢Õ.áŒŠà) {
            Lagometer.HorizonCode_Horizon_È = true;
            final long timeNowNano = System.nanoTime();
            if (Lagometer.Æ == -1L) {
                Lagometer.Æ = timeNowNano;
            }
            else {
                final int frameIndex = Lagometer.µÕ & Lagometer.á.length - 1;
                ++Lagometer.µÕ;
                final boolean gc = HorizonCode_Horizon_È();
                Lagometer.á[frameIndex] = timeNowNano - Lagometer.Æ - Lagometer.Šáƒ;
                Lagometer.ˆÏ­[frameIndex] = Lagometer.Â.Â;
                Lagometer.£á[frameIndex] = Lagometer.Ý.Â;
                Lagometer.Å[frameIndex] = Lagometer.Ø­áŒŠá.Â;
                Lagometer.£à[frameIndex] = Lagometer.Âµá€.Â;
                Lagometer.µà[frameIndex] = Lagometer.Ó.Â;
                Lagometer.ˆà[frameIndex] = Lagometer.à.Â;
                Lagometer.¥Æ[frameIndex] = Lagometer.Ø.Â;
                Lagometer.Ø­à[frameIndex] = gc;
                Lagometer.Â.Ý();
                Lagometer.Ý.Ý();
                Lagometer.Ó.Ý();
                Lagometer.Âµá€.Ý();
                Lagometer.Ø­áŒŠá.Ý();
                Lagometer.à.Ý();
                Lagometer.Ø.Ý();
                Lagometer.Æ = System.nanoTime();
            }
        }
        else {
            Lagometer.HorizonCode_Horizon_È = false;
            Lagometer.Æ = -1L;
        }
    }
    
    public static void HorizonCode_Horizon_È(final ScaledResolution scaledResolution) {
        if (Lagometer.áˆºÑ¢Õ != null && Lagometer.áˆºÑ¢Õ.áŒŠà) {
            final long timeRenderStartNano = System.nanoTime();
            GlStateManager.ÂµÈ(256);
            GlStateManager.á(5889);
            GlStateManager.Çªà¢();
            GlStateManager.à();
            GlStateManager.ŒÏ();
            GlStateManager.HorizonCode_Horizon_È(0.0, Lagometer.áŒŠÆ.Ó, Lagometer.áŒŠÆ.à, 0.0, 1000.0, 3000.0);
            GlStateManager.á(5888);
            GlStateManager.Çªà¢();
            GlStateManager.ŒÏ();
            GlStateManager.Â(0.0f, 0.0f, -2000.0f);
            GL11.glLineWidth(1.0f);
            GlStateManager.Æ();
            final Tessellator tess = Tessellator.HorizonCode_Horizon_È();
            final WorldRenderer tessellator = tess.Ý();
            tessellator.HorizonCode_Horizon_È(1);
            for (int lumMem = 0; lumMem < Lagometer.á.length; ++lumMem) {
                int memColR = (lumMem - Lagometer.µÕ & Lagometer.á.length - 1) * 100 / Lagometer.á.length;
                memColR += 155;
                float memColG = Lagometer.áŒŠÆ.à;
                final long memColB = 0L;
                if (Lagometer.Ø­à[lumMem]) {
                    HorizonCode_Horizon_È(lumMem, Lagometer.á[lumMem], memColR, memColR / 2, 0, memColG, tessellator);
                }
                else {
                    HorizonCode_Horizon_È(lumMem, Lagometer.á[lumMem], memColR, memColR, memColR, memColG, tessellator);
                    memColG -= HorizonCode_Horizon_È(lumMem, Lagometer.¥Æ[lumMem], memColR / 2, memColR / 2, memColR / 2, memColG, tessellator);
                    memColG -= HorizonCode_Horizon_È(lumMem, Lagometer.ˆà[lumMem], 0, memColR, 0, memColG, tessellator);
                    memColG -= HorizonCode_Horizon_È(lumMem, Lagometer.µà[lumMem], memColR, memColR, 0, memColG, tessellator);
                    memColG -= HorizonCode_Horizon_È(lumMem, Lagometer.£à[lumMem], memColR, 0, 0, memColG, tessellator);
                    memColG -= HorizonCode_Horizon_È(lumMem, Lagometer.Å[lumMem], memColR, 0, memColR, memColG, tessellator);
                    memColG -= HorizonCode_Horizon_È(lumMem, Lagometer.£á[lumMem], 0, 0, memColR, memColG, tessellator);
                    final float n = memColG - HorizonCode_Horizon_È(lumMem, Lagometer.ˆÏ­[lumMem], 0, memColR, memColR, memColG, tessellator);
                }
            }
            tess.Â();
            GlStateManager.á(5889);
            GlStateManager.Ê();
            GlStateManager.á(5888);
            GlStateManager.Ê();
            GlStateManager.µÕ();
            float var12 = 1.0f - (float)((System.currentTimeMillis() - Lagometer.Ï­Ðƒà) / 1000.0);
            var12 = Config.HorizonCode_Horizon_È(var12, 0.0f, 1.0f);
            int memColR = (int)(170.0f + var12 * 85.0f);
            final int var13 = (int)(100.0f + var12 * 55.0f);
            final int var14 = (int)(10.0f + var12 * 10.0f);
            final int colMem = memColR << 16 | var13 << 8 | var14;
            final int posX = 512 / scaledResolution.Âµá€() + 2;
            final int posY = Lagometer.áŒŠÆ.à / scaledResolution.Âµá€() - 8;
            final GuiIngame var15 = Lagometer.áŒŠÆ.Šáƒ;
            Gui_1808253012.HorizonCode_Horizon_È(posX - 1, posY - 1, posX + 50, posY + 10, -1605349296);
            Lagometer.áŒŠÆ.µà.HorizonCode_Horizon_È(" " + Lagometer.Ê + " MB/s", posX, posY, colMem);
            Lagometer.Šáƒ = System.nanoTime() - timeRenderStartNano;
        }
    }
    
    private static long HorizonCode_Horizon_È(final int frameNum, final long time, final int r, final int g, final int b, final float baseHeight, final WorldRenderer tessellator) {
        final long heightTime = time / 200000L;
        if (heightTime < 3L) {
            return 0L;
        }
        tessellator.Â(r, g, b, 255);
        tessellator.Â(frameNum + 0.5f, baseHeight - heightTime + 0.5f, 0.0);
        tessellator.Â(frameNum + 0.5f, baseHeight + 0.5f, 0.0);
        return heightTime;
    }
    
    public static boolean Ý() {
        return Lagometer.HorizonCode_Horizon_È;
    }
    
    public static class HorizonCode_Horizon_È
    {
        public long HorizonCode_Horizon_È;
        public long Â;
        
        public HorizonCode_Horizon_È() {
            this.HorizonCode_Horizon_È = 0L;
            this.Â = 0L;
        }
        
        public void HorizonCode_Horizon_È() {
            if (Lagometer.HorizonCode_Horizon_È && this.HorizonCode_Horizon_È == 0L) {
                this.HorizonCode_Horizon_È = System.nanoTime();
            }
        }
        
        public void Â() {
            if (Lagometer.HorizonCode_Horizon_È && this.HorizonCode_Horizon_È != 0L) {
                this.Â += System.nanoTime() - this.HorizonCode_Horizon_È;
                this.HorizonCode_Horizon_È = 0L;
            }
        }
        
        private void Ý() {
            this.Â = 0L;
            this.HorizonCode_Horizon_È = 0L;
        }
    }
}
