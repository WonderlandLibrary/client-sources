package optfine;

import net.minecraft.client.*;
import net.minecraft.profiler.*;
import net.minecraft.client.settings.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.gui.*;

public class Lagometer
{
    private static boolean[] gcs;
    private static long[] timesTick;
    private static Minecraft mc;
    private static long[] timesVisibility;
    private static int memMbSec;
    public static TimerNano timerTick;
    private static long[] timesServer;
    private static long memTimeDiffMs;
    private static long[] timesScheduledExecutables;
    private static long memLast;
    public static TimerNano timerServer;
    private static long[] timesChunkUpload;
    private static final String[] I;
    private static long renderTimeNano;
    private static long prevFrameTimeNano;
    private static Profiler profiler;
    private static int numRecordedFrameTimes;
    private static long[] timesChunkUpdate;
    public static TimerNano timerScheduledExecutables;
    public static boolean active;
    private static long[] timesTerrain;
    private static long memTimeStartMs;
    private static long memStart;
    private static long memTimeLast;
    public static TimerNano timerTerrain;
    public static TimerNano timerChunkUpload;
    private static long memDiff;
    public static TimerNano timerVisibility;
    private static long[] timesFrame;
    private static GameSettings gameSettings;
    public static TimerNano timerChunkUpdate;
    
    public static boolean isActive() {
        return Lagometer.active;
    }
    
    private static long getMemoryUsed() {
        final Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
    
    static {
        I();
        Lagometer.active = ("".length() != 0);
        Lagometer.timerTick = new TimerNano();
        Lagometer.timerScheduledExecutables = new TimerNano();
        Lagometer.timerChunkUpload = new TimerNano();
        Lagometer.timerChunkUpdate = new TimerNano();
        Lagometer.timerVisibility = new TimerNano();
        Lagometer.timerTerrain = new TimerNano();
        Lagometer.timerServer = new TimerNano();
        Lagometer.timesFrame = new long[28 + 495 - 492 + 481];
        Lagometer.timesTick = new long[499 + 397 - 420 + 36];
        Lagometer.timesScheduledExecutables = new long[283 + 229 - 86 + 86];
        Lagometer.timesChunkUpload = new long[309 + 169 - 373 + 407];
        Lagometer.timesChunkUpdate = new long[133 + 477 - 282 + 184];
        Lagometer.timesVisibility = new long[221 + 379 - 533 + 445];
        Lagometer.timesTerrain = new long[228 + 503 - 342 + 123];
        Lagometer.timesServer = new long[435 + 329 - 538 + 286];
        Lagometer.gcs = new boolean[32 + 70 + 184 + 226];
        Lagometer.numRecordedFrameTimes = "".length();
        Lagometer.prevFrameTimeNano = -1L;
        Lagometer.renderTimeNano = 0L;
        Lagometer.memTimeStartMs = System.currentTimeMillis();
        Lagometer.memStart = getMemoryUsed();
        Lagometer.memTimeLast = Lagometer.memTimeStartMs;
        Lagometer.memLast = Lagometer.memStart;
        Lagometer.memTimeDiffMs = 1L;
        Lagometer.memDiff = 0L;
        Lagometer.memMbSec = "".length();
    }
    
    public static void updateLagometer() {
        if (Lagometer.mc == null) {
            Lagometer.mc = Minecraft.getMinecraft();
            Lagometer.gameSettings = Lagometer.mc.gameSettings;
            Lagometer.profiler = Lagometer.mc.mcProfiler;
        }
        if (Lagometer.gameSettings.showDebugInfo && Lagometer.gameSettings.ofLagometer) {
            Lagometer.active = (" ".length() != 0);
            final long nanoTime = System.nanoTime();
            if (Lagometer.prevFrameTimeNano == -1L) {
                Lagometer.prevFrameTimeNano = nanoTime;
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            else {
                final int n = Lagometer.numRecordedFrameTimes & Lagometer.timesFrame.length - " ".length();
                Lagometer.numRecordedFrameTimes += " ".length();
                final boolean updateMemoryAllocation = updateMemoryAllocation();
                Lagometer.timesFrame[n] = nanoTime - Lagometer.prevFrameTimeNano - Lagometer.renderTimeNano;
                Lagometer.timesTick[n] = Lagometer.timerTick.timeNano;
                Lagometer.timesScheduledExecutables[n] = Lagometer.timerScheduledExecutables.timeNano;
                Lagometer.timesChunkUpload[n] = Lagometer.timerChunkUpload.timeNano;
                Lagometer.timesChunkUpdate[n] = Lagometer.timerChunkUpdate.timeNano;
                Lagometer.timesVisibility[n] = Lagometer.timerVisibility.timeNano;
                Lagometer.timesTerrain[n] = Lagometer.timerTerrain.timeNano;
                Lagometer.timesServer[n] = Lagometer.timerServer.timeNano;
                Lagometer.gcs[n] = updateMemoryAllocation;
                TimerNano.access$0(Lagometer.timerTick);
                TimerNano.access$0(Lagometer.timerScheduledExecutables);
                TimerNano.access$0(Lagometer.timerVisibility);
                TimerNano.access$0(Lagometer.timerChunkUpdate);
                TimerNano.access$0(Lagometer.timerChunkUpload);
                TimerNano.access$0(Lagometer.timerTerrain);
                TimerNano.access$0(Lagometer.timerServer);
                Lagometer.prevFrameTimeNano = System.nanoTime();
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
        }
        else {
            Lagometer.active = ("".length() != 0);
            Lagometer.prevFrameTimeNano = -1L;
        }
    }
    
    public static boolean updateMemoryAllocation() {
        final long currentTimeMillis = System.currentTimeMillis();
        final long memoryUsed = getMemoryUsed();
        int n = "".length();
        if (memoryUsed < Lagometer.memLast) {
            final int memMbSec = (int)(Lagometer.memDiff / 1000000.0 / (Lagometer.memTimeDiffMs / 1000.0));
            if (memMbSec > 0) {
                Lagometer.memMbSec = memMbSec;
            }
            Lagometer.memTimeStartMs = currentTimeMillis;
            Lagometer.memStart = memoryUsed;
            Lagometer.memTimeDiffMs = 0L;
            Lagometer.memDiff = 0L;
            n = " ".length();
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            Lagometer.memTimeDiffMs = currentTimeMillis - Lagometer.memTimeStartMs;
            Lagometer.memDiff = memoryUsed - Lagometer.memStart;
        }
        Lagometer.memTimeLast = currentTimeMillis;
        Lagometer.memLast = memoryUsed;
        return n != 0;
    }
    
    private static long renderTime(final int n, final long n2, final int n3, final int n4, final int n5, final float n6, final WorldRenderer worldRenderer) {
        final long n7 = n2 / 200000L;
        if (n7 < 3L) {
            return 0L;
        }
        worldRenderer.pos(n + 0.5f, n6 - n7 + 0.5f, 0.0).color(n3, n4, n5, 39 + 58 - 43 + 201).endVertex();
        worldRenderer.pos(n + 0.5f, n6 + 0.5f, 0.0).color(n3, n4, n5, 78 + 105 + 21 + 51).endVertex();
        return n7;
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
            if (3 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static void showLagometer(final ScaledResolution scaledResolution) {
        if (Lagometer.gameSettings != null && Lagometer.gameSettings.ofLagometer) {
            final long nanoTime = System.nanoTime();
            GlStateManager.clear(148 + 147 - 163 + 124);
            GlStateManager.matrixMode(1000 + 1916 + 2007 + 966);
            GlStateManager.pushMatrix();
            GlStateManager.enableColorMaterial();
            GlStateManager.loadIdentity();
            GlStateManager.ortho(0.0, Lagometer.mc.displayWidth, Lagometer.mc.displayHeight, 0.0, 1000.0, 3000.0);
            GlStateManager.matrixMode(2873 + 5604 - 4402 + 1813);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0f, 0.0f, -2000.0f);
            GL11.glLineWidth(1.0f);
            GlStateManager.disableTexture2D();
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.begin(" ".length(), DefaultVertexFormats.POSITION_COLOR);
            int i = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (i < Lagometer.timesFrame.length) {
                int n = (i - Lagometer.numRecordedFrameTimes & Lagometer.timesFrame.length - " ".length()) * (0xC2 ^ 0xA6) / Lagometer.timesFrame.length;
                n += 155;
                final float n2 = Lagometer.mc.displayHeight;
                if (Lagometer.gcs[i]) {
                    renderTime(i, Lagometer.timesFrame[i], n, n / "  ".length(), "".length(), n2, worldRenderer);
                    "".length();
                    if (3 <= 1) {
                        throw null;
                    }
                }
                else {
                    renderTime(i, Lagometer.timesFrame[i], n, n, n, n2, worldRenderer);
                    final float n3 = n2 - renderTime(i, Lagometer.timesServer[i], n / "  ".length(), n / "  ".length(), n / "  ".length(), n2, worldRenderer);
                    final float n4 = n3 - renderTime(i, Lagometer.timesTerrain[i], "".length(), n, "".length(), n3, worldRenderer);
                    final float n5 = n4 - renderTime(i, Lagometer.timesVisibility[i], n, n, "".length(), n4, worldRenderer);
                    final float n6 = n5 - renderTime(i, Lagometer.timesChunkUpdate[i], n, "".length(), "".length(), n5, worldRenderer);
                    final float n7 = n6 - renderTime(i, Lagometer.timesChunkUpload[i], n, "".length(), n, n6, worldRenderer);
                    final float n8 = n7 - renderTime(i, Lagometer.timesScheduledExecutables[i], "".length(), "".length(), n, n7, worldRenderer);
                    final float n9 = n8 - renderTime(i, Lagometer.timesTick[i], "".length(), n, n, n8, worldRenderer);
                }
                ++i;
            }
            instance.draw();
            GlStateManager.matrixMode(5375 + 204 + 276 + 34);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(3789 + 2880 - 3907 + 3126);
            GlStateManager.popMatrix();
            GlStateManager.enableTexture2D();
            final float limit = Config.limit(1.0f - (float)((System.currentTimeMillis() - Lagometer.memTimeStartMs) / 1000.0), 0.0f, 1.0f);
            final int n10 = (int)(170.0f + limit * 85.0f) << (0x2D ^ 0x3D) | (int)(100.0f + limit * 55.0f) << (0x26 ^ 0x2E) | (int)(10.0f + limit * 10.0f);
            final int n11 = (39 + 374 - 221 + 320) / scaledResolution.getScaleFactor() + "  ".length();
            final int n12 = Lagometer.mc.displayHeight / scaledResolution.getScaleFactor() - (0xB ^ 0x3);
            final GuiIngame ingameGUI = Lagometer.mc.ingameGUI;
            Gui.drawRect(n11 - " ".length(), n12 - " ".length(), n11 + (0x35 ^ 0x7), n12 + (0x74 ^ 0x7E), -(634768014 + 866438027 - 812245890 + 916389145));
            Lagometer.mc.fontRendererObj.drawString(Lagometer.I["".length()] + Lagometer.memMbSec + Lagometer.I[" ".length()], n11, n12, n10);
            Lagometer.renderTimeNano = System.nanoTime() - nanoTime;
        }
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("q", "QTEOf");
        Lagometer.I[" ".length()] = I("g\u0003\u0001W\u001d", "GNCxn");
    }
    
    public static class TimerNano
    {
        public long timeNano;
        public long timeStartNano;
        
        public void start() {
            if (Lagometer.active && this.timeStartNano == 0L) {
                this.timeStartNano = System.nanoTime();
            }
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
                if (0 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private void reset() {
            this.timeNano = 0L;
            this.timeStartNano = 0L;
        }
        
        public void end() {
            if (Lagometer.active && this.timeStartNano != 0L) {
                this.timeNano += System.nanoTime() - this.timeStartNano;
                this.timeStartNano = 0L;
            }
        }
        
        static void access$0(final TimerNano timerNano) {
            timerNano.reset();
        }
        
        public TimerNano() {
            this.timeStartNano = 0L;
            this.timeNano = 0L;
        }
    }
}
