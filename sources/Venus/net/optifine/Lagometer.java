/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.profiler.IProfiler;
import net.optifine.Config;
import net.optifine.util.MemoryMonitor;
import org.lwjgl.opengl.GL11;

public class Lagometer {
    private static Minecraft mc;
    private static GameSettings gameSettings;
    private static IProfiler profiler;
    public static boolean active;
    public static TimerNano timerTick;
    public static TimerNano timerScheduledExecutables;
    public static TimerNano timerChunkUpload;
    public static TimerNano timerChunkUpdate;
    public static TimerNano timerVisibility;
    public static TimerNano timerTerrain;
    public static TimerNano timerServer;
    private static long[] timesFrame;
    private static long[] timesTick;
    private static long[] timesScheduledExecutables;
    private static long[] timesChunkUpload;
    private static long[] timesChunkUpdate;
    private static long[] timesVisibility;
    private static long[] timesTerrain;
    private static long[] timesServer;
    private static boolean[] gcs;
    private static int numRecordedFrameTimes;
    private static long prevFrameTimeNano;
    private static long renderTimeNano;

    public static void updateLagometer() {
        if (mc == null) {
            mc = Minecraft.getInstance();
            gameSettings = Lagometer.mc.gameSettings;
            profiler = mc.getProfiler();
        }
        if (Lagometer.gameSettings.showDebugInfo && (Lagometer.gameSettings.ofLagometer || Lagometer.gameSettings.showLagometer)) {
            active = true;
            long l = System.nanoTime();
            if (prevFrameTimeNano == -1L) {
                prevFrameTimeNano = l;
            } else {
                int n = numRecordedFrameTimes & timesFrame.length - 1;
                ++numRecordedFrameTimes;
                boolean bl = MemoryMonitor.isGcEvent();
                Lagometer.timesFrame[n] = l - prevFrameTimeNano - renderTimeNano;
                Lagometer.timesTick[n] = Lagometer.timerTick.timeNano;
                Lagometer.timesScheduledExecutables[n] = Lagometer.timerScheduledExecutables.timeNano;
                Lagometer.timesChunkUpload[n] = Lagometer.timerChunkUpload.timeNano;
                Lagometer.timesChunkUpdate[n] = Lagometer.timerChunkUpdate.timeNano;
                Lagometer.timesVisibility[n] = Lagometer.timerVisibility.timeNano;
                Lagometer.timesTerrain[n] = Lagometer.timerTerrain.timeNano;
                Lagometer.timesServer[n] = Lagometer.timerServer.timeNano;
                Lagometer.gcs[n] = bl;
                timerTick.reset();
                timerScheduledExecutables.reset();
                timerVisibility.reset();
                timerChunkUpdate.reset();
                timerChunkUpload.reset();
                timerTerrain.reset();
                timerServer.reset();
                prevFrameTimeNano = System.nanoTime();
            }
        } else {
            active = false;
            prevFrameTimeNano = -1L;
        }
    }

    public static void showLagometer(MatrixStack matrixStack, int n) {
        if (gameSettings != null && (Lagometer.gameSettings.ofLagometer || Lagometer.gameSettings.showLagometer)) {
            int n2;
            int n3;
            long l = System.nanoTime();
            GlStateManager.clear(256);
            GlStateManager.matrixMode(5889);
            GlStateManager.pushMatrix();
            int n4 = mc.getMainWindow().getFramebufferWidth();
            int n5 = mc.getMainWindow().getFramebufferHeight();
            GlStateManager.enableColorMaterial();
            GlStateManager.loadIdentity();
            GlStateManager.ortho(0.0, n4, n5, 0.0, 1000.0, 3000.0);
            GlStateManager.matrixMode(5888);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            GlStateManager.translatef(0.0f, 0.0f, -2000.0f);
            GL11.glLineWidth(1.0f);
            GlStateManager.disableTexture();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            bufferBuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
            for (n3 = 0; n3 < timesFrame.length; ++n3) {
                n2 = (n3 - numRecordedFrameTimes & timesFrame.length - 1) * 100 / timesFrame.length;
                n2 += 155;
                float f = n5;
                long l2 = 0L;
                if (gcs[n3]) {
                    Lagometer.renderTime(n3, timesFrame[n3], n2, n2 / 2, 0, f, bufferBuilder);
                    continue;
                }
                Lagometer.renderTime(n3, timesFrame[n3], n2, n2, n2, f, bufferBuilder);
                f -= (float)Lagometer.renderTime(n3, timesServer[n3], n2 / 2, n2 / 2, n2 / 2, f, bufferBuilder);
                f -= (float)Lagometer.renderTime(n3, timesTerrain[n3], 0, n2, 0, f, bufferBuilder);
                f -= (float)Lagometer.renderTime(n3, timesVisibility[n3], n2, n2, 0, f, bufferBuilder);
                f -= (float)Lagometer.renderTime(n3, timesChunkUpdate[n3], n2, 0, 0, f, bufferBuilder);
                f -= (float)Lagometer.renderTime(n3, timesChunkUpload[n3], n2, 0, n2, f, bufferBuilder);
                f -= (float)Lagometer.renderTime(n3, timesScheduledExecutables[n3], 0, 0, n2, f, bufferBuilder);
                float f2 = f - (float)Lagometer.renderTime(n3, timesTick[n3], 0, n2, n2, f, bufferBuilder);
            }
            Lagometer.renderTimeDivider(0, timesFrame.length, 33333333L, 196, 196, 196, n5, bufferBuilder);
            Lagometer.renderTimeDivider(0, timesFrame.length, 16666666L, 196, 196, 196, n5, bufferBuilder);
            tessellator.draw();
            GlStateManager.enableTexture();
            n3 = n5 - 80;
            n2 = n5 - 160;
            String string = Config.isShowFrameTime() ? "33" : "30";
            String string2 = Config.isShowFrameTime() ? "17" : "60";
            Lagometer.mc.fontRenderer.drawString(matrixStack, string, 2.0f, n2 + 1, -8947849);
            Lagometer.mc.fontRenderer.drawString(matrixStack, string, 1.0f, n2, -3881788);
            Lagometer.mc.fontRenderer.drawString(matrixStack, string2, 2.0f, n3 + 1, -8947849);
            Lagometer.mc.fontRenderer.drawString(matrixStack, string2, 1.0f, n3, -3881788);
            GlStateManager.matrixMode(5889);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
            GlStateManager.popMatrix();
            GlStateManager.enableTexture();
            float f = 1.0f - (float)((double)(System.currentTimeMillis() - MemoryMonitor.getStartTimeMs()) / 1000.0);
            f = Config.limit(f, 0.0f, 1.0f);
            int n6 = (int)(170.0f + f * 85.0f);
            int n7 = (int)(100.0f + f * 55.0f);
            int n8 = (int)(10.0f + f * 10.0f);
            int n9 = n6 << 16 | n7 << 8 | n8;
            int n10 = 512 / n + 2;
            int n11 = n5 / n - 8;
            IngameGui ingameGui = Lagometer.mc.ingameGUI;
            IngameGui.fill(matrixStack, n10 - 1, n11 - 1, n10 + 50, n11 + 10, -1605349296);
            Lagometer.mc.fontRenderer.drawString(matrixStack, " " + MemoryMonitor.getGcRateMb() + " MB/s", n10, n11, n9);
            renderTimeNano = System.nanoTime() - l;
        }
    }

    private static long renderTime(int n, long l, int n2, int n3, int n4, float f, BufferBuilder bufferBuilder) {
        long l2 = l / 200000L;
        if (l2 < 3L) {
            return 0L;
        }
        bufferBuilder.pos((float)n + 0.5f, f - (float)l2 + 0.5f, 0.0).color(n2, n3, n4, 255).endVertex();
        bufferBuilder.pos((float)n + 0.5f, f + 0.5f, 0.0).color(n2, n3, n4, 255).endVertex();
        return l2;
    }

    private static long renderTimeDivider(int n, int n2, long l, int n3, int n4, int n5, float f, BufferBuilder bufferBuilder) {
        long l2 = l / 200000L;
        if (l2 < 3L) {
            return 0L;
        }
        bufferBuilder.pos((float)n + 0.5f, f - (float)l2 + 0.5f, 0.0).color(n3, n4, n5, 255).endVertex();
        bufferBuilder.pos((float)n2 + 0.5f, f - (float)l2 + 0.5f, 0.0).color(n3, n4, n5, 255).endVertex();
        return l2;
    }

    public static boolean isActive() {
        return active;
    }

    static {
        active = false;
        timerTick = new TimerNano();
        timerScheduledExecutables = new TimerNano();
        timerChunkUpload = new TimerNano();
        timerChunkUpdate = new TimerNano();
        timerVisibility = new TimerNano();
        timerTerrain = new TimerNano();
        timerServer = new TimerNano();
        timesFrame = new long[512];
        timesTick = new long[512];
        timesScheduledExecutables = new long[512];
        timesChunkUpload = new long[512];
        timesChunkUpdate = new long[512];
        timesVisibility = new long[512];
        timesTerrain = new long[512];
        timesServer = new long[512];
        gcs = new boolean[512];
        numRecordedFrameTimes = 0;
        prevFrameTimeNano = -1L;
        renderTimeNano = 0L;
    }

    public static class TimerNano {
        public long timeStartNano = 0L;
        public long timeNano = 0L;

        public void start() {
            if (active && this.timeStartNano == 0L) {
                this.timeStartNano = System.nanoTime();
            }
        }

        public void end() {
            if (active && this.timeStartNano != 0L) {
                this.timeNano += System.nanoTime() - this.timeStartNano;
                this.timeStartNano = 0L;
            }
        }

        private void reset() {
            this.timeNano = 0L;
            this.timeStartNano = 0L;
        }
    }
}

