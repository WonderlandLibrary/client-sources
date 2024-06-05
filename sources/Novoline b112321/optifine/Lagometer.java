package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.profiler.Profiler;
import optifine.Config;
import optifine.Lagometer$TimerNano;
import optifine.MatchBlock;
import org.lwjgl.opengl.GL11;

public class Lagometer {
   private static Minecraft mc;
   private static GameSettings gameSettings;
   private static Profiler profiler;
   public static boolean active = false;
   public static Lagometer$TimerNano timerTick = new Lagometer$TimerNano();
   public static Lagometer$TimerNano timerScheduledExecutables = new Lagometer$TimerNano();
   public static Lagometer$TimerNano timerChunkUpload = new Lagometer$TimerNano();
   public static Lagometer$TimerNano timerChunkUpdate = new Lagometer$TimerNano();
   public static Lagometer$TimerNano timerVisibility = new Lagometer$TimerNano();
   public static Lagometer$TimerNano timerTerrain = new Lagometer$TimerNano();
   public static Lagometer$TimerNano timerServer = new Lagometer$TimerNano();
   private static long[] timesFrame = new long[512];
   private static long[] timesTick = new long[512];
   private static long[] timesScheduledExecutables = new long[512];
   private static long[] timesChunkUpload = new long[512];
   private static long[] timesChunkUpdate = new long[512];
   private static long[] timesVisibility = new long[512];
   private static long[] timesTerrain = new long[512];
   private static long[] timesServer = new long[512];
   private static boolean[] gcs = new boolean[512];
   private static int numRecordedFrameTimes = 0;
   private static long prevFrameTimeNano = -1L;
   private static long renderTimeNano = 0L;
   private static long memTimeStartMs = System.currentTimeMillis();
   private static long memStart = getMemoryUsed();
   private static long memTimeLast = memTimeStartMs;
   private static long memLast = memStart;
   private static long memTimeDiffMs = 1L;
   private static long memDiff = 0L;
   private static int memMbSec = 0;

   public static boolean updateMemoryAllocation() {
      MatchBlock.b();
      long var1 = System.currentTimeMillis();
      long var3 = getMemoryUsed();
      boolean var5 = false;
      if(var3 < memLast) {
         double var6 = (double)memDiff / 1000000.0D;
         double var8 = (double)memTimeDiffMs / 1000.0D;
         int var10 = (int)(var6 / var8);
         if(var10 > 0) {
            memMbSec = var10;
         }

         memTimeStartMs = var1;
         memStart = var3;
         memTimeDiffMs = 0L;
         memDiff = 0L;
         var5 = true;
      }

      memTimeDiffMs = var1 - memTimeStartMs;
      memDiff = var3 - memStart;
      memTimeLast = var1;
      memLast = var3;
      return var5;
   }

   private static long getMemoryUsed() {
      Runtime var0 = Runtime.getRuntime();
      return var0.totalMemory() - var0.freeMemory();
   }

   public static void updateLagometer() {
      PacketRemapper[] var0 = MatchBlock.b();
      if(mc == null) {
         mc = Minecraft.getMinecraft();
         gameSettings = mc.gameSettings;
         profiler = mc.mcProfiler;
      }

      if(gameSettings.showDebugInfo && (gameSettings.ofLagometer || gameSettings.field_181657_aC)) {
         active = true;
         long var1 = System.nanoTime();
         if(prevFrameTimeNano == -1L) {
            prevFrameTimeNano = var1;
         }

         int var3 = numRecordedFrameTimes & timesFrame.length - 1;
         ++numRecordedFrameTimes;
         boolean var4 = updateMemoryAllocation();
         timesFrame[var3] = var1 - prevFrameTimeNano - renderTimeNano;
         timesTick[var3] = timerTick.timeNano;
         timesScheduledExecutables[var3] = timerScheduledExecutables.timeNano;
         timesChunkUpload[var3] = timerChunkUpload.timeNano;
         timesChunkUpdate[var3] = timerChunkUpdate.timeNano;
         timesVisibility[var3] = timerVisibility.timeNano;
         timesTerrain[var3] = timerTerrain.timeNano;
         timesServer[var3] = timerServer.timeNano;
         gcs[var3] = var4;
         Lagometer$TimerNano.access$000(timerTick);
         Lagometer$TimerNano.access$000(timerScheduledExecutables);
         Lagometer$TimerNano.access$000(timerVisibility);
         Lagometer$TimerNano.access$000(timerChunkUpdate);
         Lagometer$TimerNano.access$000(timerChunkUpload);
         Lagometer$TimerNano.access$000(timerTerrain);
         Lagometer$TimerNano.access$000(timerServer);
         prevFrameTimeNano = System.nanoTime();
      }

      active = false;
      prevFrameTimeNano = -1L;
   }

   public static void showLagometer(ScaledResolution var0) {
      PacketRemapper[] var1 = MatchBlock.b();
      if(gameSettings != null && (gameSettings.ofLagometer || gameSettings.field_181657_aC)) {
         long var2 = System.nanoTime();
         GlStateManager.clear(256);
         GlStateManager.matrixMode(5889);
         GlStateManager.pushMatrix();
         GlStateManager.enableColorMaterial();
         GlStateManager.loadIdentity();
         GlStateManager.ortho(0.0D, (double)mc.displayWidth, (double)mc.displayHeight, 0.0D, 1000.0D, 3000.0D);
         GlStateManager.matrixMode(5888);
         GlStateManager.pushMatrix();
         GlStateManager.loadIdentity();
         GlStateManager.translate(0.0F, 0.0F, -2000.0F);
         GL11.glLineWidth(1.0F);
         GlStateManager.disableTexture2D();
         Tessellator var4 = Tessellator.getInstance();
         WorldRenderer var5 = var4.getWorldRenderer();
         var5.begin(1, DefaultVertexFormats.POSITION_COLOR);
         int var6 = 0;
         if(var6 < timesFrame.length) {
            int var7 = (var6 - numRecordedFrameTimes & timesFrame.length - 1) * 100 / timesFrame.length;
            var7 = var7 + 155;
            float var8 = (float)mc.displayHeight;
            long var9 = 0L;
            if(gcs[var6]) {
               renderTime(var6, timesFrame[var6], var7, var7 / 2, 0, var8, var5);
            }

            renderTime(var6, timesFrame[var6], var7, var7, var7, var8, var5);
            var8 = var8 - (float)renderTime(var6, timesServer[var6], var7 / 2, var7 / 2, var7 / 2, var8, var5);
            var8 = var8 - (float)renderTime(var6, timesTerrain[var6], 0, var7, 0, var8, var5);
            var8 = var8 - (float)renderTime(var6, timesVisibility[var6], var7, var7, 0, var8, var5);
            var8 = var8 - (float)renderTime(var6, timesChunkUpdate[var6], var7, 0, 0, var8, var5);
            var8 = var8 - (float)renderTime(var6, timesChunkUpload[var6], var7, 0, var7, var8, var5);
            var8 = var8 - (float)renderTime(var6, timesScheduledExecutables[var6], 0, 0, var7, var8, var5);
            float var10000 = var8 - (float)renderTime(var6, timesTick[var6], 0, var7, var7, var8, var5);
            ++var6;
         }

         renderTimeDivider(0, timesFrame.length, 33333333L, 196, 196, 196, (float)mc.displayHeight, var5);
         renderTimeDivider(0, timesFrame.length, 16666666L, 196, 196, 196, (float)mc.displayHeight, var5);
         var4.draw();
         GlStateManager.enableTexture2D();
         var6 = mc.displayHeight - 80;
         int var19 = mc.displayHeight - 160;
         mc.fontRendererObj.b("30", 2.0F, (float)(var19 + 1), -8947849);
         mc.fontRendererObj.b("30", 1.0F, (float)var19, -3881788);
         mc.fontRendererObj.b("60", 2.0F, (float)(var6 + 1), -8947849);
         mc.fontRendererObj.b("60", 1.0F, (float)var6, -3881788);
         GlStateManager.matrixMode(5889);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
         GlStateManager.popMatrix();
         GlStateManager.enableTexture2D();
         float var26 = 1.0F - (float)((double)(System.currentTimeMillis() - memTimeStartMs) / 1000.0D);
         var26 = Config.a(var26, 0.0F, 1.0F);
         int var28 = (int)(170.0F + var26 * 85.0F);
         int var10 = (int)(100.0F + var26 * 55.0F);
         int var11 = (int)(10.0F + var26 * 10.0F);
         int var12 = var28 << 16 | var10 << 8 | var11;
         int var13 = 512 / var0.getScaleFactor() + 2;
         int var14 = mc.displayHeight / var0.getScaleFactor() - 8;
         GuiIngame var15 = mc.ingameGUI;
         GuiIngame.drawRect(var13 - 1, var14 - 1, var13 + 50, var14 + 10, -1605349296);
         mc.fontRendererObj.b(" " + memMbSec + " MB/s", (float)var13, (float)var14, var12);
         renderTimeNano = System.nanoTime() - var2;
      }

   }

   private static long renderTime(int var0, long var1, int var3, int var4, int var5, float var6, WorldRenderer var7) {
      MatchBlock.b();
      long var9 = var1 / 200000L;
      if(var9 < 3L) {
         return 0L;
      } else {
         var7.pos((double)((float)var0 + 0.5F), (double)(var6 - (float)var9 + 0.5F), 0.0D).color(var3, var4, var5, 255).endVertex();
         var7.pos((double)((float)var0 + 0.5F), (double)(var6 + 0.5F), 0.0D).color(var3, var4, var5, 255).endVertex();
         return var9;
      }
   }

   private static long renderTimeDivider(int var0, int var1, long var2, int var4, int var5, int var6, float var7, WorldRenderer var8) {
      MatchBlock.b();
      long var10 = var2 / 200000L;
      if(var10 < 3L) {
         return 0L;
      } else {
         var8.pos((double)((float)var0 + 0.5F), (double)(var7 - (float)var10 + 0.5F), 0.0D).color(var4, var5, var6, 255).endVertex();
         var8.pos((double)((float)var1 + 0.5F), (double)(var7 - (float)var10 + 0.5F), 0.0D).color(var4, var5, var6, 255).endVertex();
         return var10;
      }
   }

   public static boolean isActive() {
      return active;
   }
}
