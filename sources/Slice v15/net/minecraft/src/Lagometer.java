package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.profiler.Profiler;

public class Lagometer
{
  private static Minecraft mc;
  private static GameSettings gameSettings;
  private static Profiler profiler;
  public static boolean active = false;
  public static TimerNano timerTick = new TimerNano();
  public static TimerNano timerScheduledExecutables = new TimerNano();
  public static TimerNano timerChunkUpload = new TimerNano();
  public static TimerNano timerChunkUpdate = new TimerNano();
  public static TimerNano timerVisibility = new TimerNano();
  public static TimerNano timerTerrain = new TimerNano();
  public static TimerNano timerServer = new TimerNano();
  private static long[] timesFrame = new long['Ȁ'];
  private static long[] timesTick = new long['Ȁ'];
  private static long[] timesScheduledExecutables = new long['Ȁ'];
  private static long[] timesChunkUpload = new long['Ȁ'];
  private static long[] timesChunkUpdate = new long['Ȁ'];
  private static long[] timesVisibility = new long['Ȁ'];
  private static long[] timesTerrain = new long['Ȁ'];
  private static long[] timesServer = new long['Ȁ'];
  private static boolean[] gcs = new boolean['Ȁ'];
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
  
  public Lagometer() {}
  
  public static boolean updateMemoryAllocation() { long timeNowMs = System.currentTimeMillis();
    long memNow = getMemoryUsed();
    boolean gc = false;
    
    if (memNow < memLast)
    {
      double memDiffMb = memDiff / 1000000.0D;
      double timeDiffSec = memTimeDiffMs / 1000.0D;
      int mbSec = (int)(memDiffMb / timeDiffSec);
      
      if (mbSec > 0)
      {
        memMbSec = mbSec;
      }
      
      memTimeStartMs = timeNowMs;
      memStart = memNow;
      memTimeDiffMs = 0L;
      memDiff = 0L;
      gc = true;
    }
    else
    {
      memTimeDiffMs = timeNowMs - memTimeStartMs;
      memDiff = memNow - memStart;
    }
    
    memTimeLast = timeNowMs;
    memLast = memNow;
    return gc;
  }
  
  private static long getMemoryUsed()
  {
    Runtime r = Runtime.getRuntime();
    return r.totalMemory() - r.freeMemory();
  }
  
  public static void updateLagometer()
  {
    if (mc == null)
    {
      mc = Minecraft.getMinecraft();
      gameSettings = Minecraft.gameSettings;
      profiler = mcmcProfiler;
    }
    
    if ((gameSettingsshowDebugInfo) && (gameSettingsofLagometer))
    {
      active = true;
      long timeNowNano = System.nanoTime();
      
      if (prevFrameTimeNano == -1L)
      {
        prevFrameTimeNano = timeNowNano;
      }
      else
      {
        int frameIndex = numRecordedFrameTimes & timesFrame.length - 1;
        numRecordedFrameTimes += 1;
        boolean gc = updateMemoryAllocation();
        timesFrame[frameIndex] = (timeNowNano - prevFrameTimeNano - renderTimeNano);
        timesTick[frameIndex] = timerTicktimeNano;
        timesScheduledExecutables[frameIndex] = timerScheduledExecutablestimeNano;
        timesChunkUpload[frameIndex] = timerChunkUploadtimeNano;
        timesChunkUpdate[frameIndex] = timerChunkUpdatetimeNano;
        timesVisibility[frameIndex] = timerVisibilitytimeNano;
        timesTerrain[frameIndex] = timerTerraintimeNano;
        timesServer[frameIndex] = timerServertimeNano;
        gcs[frameIndex] = gc;
        timerTick.reset();
        timerScheduledExecutables.reset();
        timerVisibility.reset();
        timerChunkUpdate.reset();
        timerChunkUpload.reset();
        timerTerrain.reset();
        timerServer.reset();
        prevFrameTimeNano = System.nanoTime();
      }
    }
    else
    {
      active = false;
      prevFrameTimeNano = -1L;
    }
  }
  
  public static void showLagometer(ScaledResolution scaledResolution)
  {
    if ((gameSettings != null) && (gameSettingsofLagometer))
    {
      long timeRenderStartNano = System.nanoTime();
      GlStateManager.clear(256);
      GlStateManager.matrixMode(5889);
      GlStateManager.pushMatrix();
      GlStateManager.enableColorMaterial();
      GlStateManager.loadIdentity();
      GlStateManager.ortho(0.0D, Minecraft.displayWidth, Minecraft.displayHeight, 0.0D, 1000.0D, 3000.0D);
      GlStateManager.matrixMode(5888);
      GlStateManager.pushMatrix();
      GlStateManager.loadIdentity();
      GlStateManager.translate(0.0F, 0.0F, -2000.0F);
      org.lwjgl.opengl.GL11.glLineWidth(1.0F);
      GlStateManager.func_179090_x();
      Tessellator tess = Tessellator.getInstance();
      WorldRenderer tessellator = tess.getWorldRenderer();
      tessellator.startDrawing(1);
      

      for (int lumMem = 0; lumMem < timesFrame.length; lumMem++)
      {
        int memColR = (lumMem - numRecordedFrameTimes & timesFrame.length - 1) * 100 / timesFrame.length;
        memColR += 155;
        float memColG = Minecraft.displayHeight;
        long memColB = 0L;
        
        if (gcs[lumMem] != 0)
        {
          renderTime(lumMem, timesFrame[lumMem], memColR, memColR / 2, 0, memColG, tessellator);
        }
        else
        {
          renderTime(lumMem, timesFrame[lumMem], memColR, memColR, memColR, memColG, tessellator);
          memColG -= (float)renderTime(lumMem, timesServer[lumMem], memColR / 2, memColR / 2, memColR / 2, memColG, tessellator);
          memColG -= (float)renderTime(lumMem, timesTerrain[lumMem], 0, memColR, 0, memColG, tessellator);
          memColG -= (float)renderTime(lumMem, timesVisibility[lumMem], memColR, memColR, 0, memColG, tessellator);
          memColG -= (float)renderTime(lumMem, timesChunkUpdate[lumMem], memColR, 0, 0, memColG, tessellator);
          memColG -= (float)renderTime(lumMem, timesChunkUpload[lumMem], memColR, 0, memColR, memColG, tessellator);
          memColG -= (float)renderTime(lumMem, timesScheduledExecutables[lumMem], 0, 0, memColR, memColG, tessellator);
          float f1 = memColG - (float)renderTime(lumMem, timesTick[lumMem], 0, memColR, memColR, memColG, tessellator);
        }
      }
      
      tess.draw();
      GlStateManager.matrixMode(5889);
      GlStateManager.popMatrix();
      GlStateManager.matrixMode(5888);
      GlStateManager.popMatrix();
      GlStateManager.func_179098_w();
      float var12 = 1.0F - (float)((System.currentTimeMillis() - memTimeStartMs) / 1000.0D);
      var12 = Config.limit(var12, 0.0F, 1.0F);
      int memColR = (int)(170.0F + var12 * 85.0F);
      int var13 = (int)(100.0F + var12 * 55.0F);
      int var14 = (int)(10.0F + var12 * 10.0F);
      int colMem = memColR << 16 | var13 << 8 | var14;
      int posX = 512 / scaledResolution.getScaleFactor() + 2;
      int posY = Minecraft.displayHeight / scaledResolution.getScaleFactor() - 8;
      GuiIngame var15 = mcingameGUI;
      GuiIngame.drawRect(posX - 1, posY - 1, posX + 50, posY + 10, -1605349296);
      Minecraft.fontRendererObj.drawString(" " + memMbSec + " MB/s", posX, posY, colMem);
      renderTimeNano = System.nanoTime() - timeRenderStartNano;
    }
  }
  
  private static long renderTime(int frameNum, long time, int r, int g, int b, float baseHeight, WorldRenderer tessellator)
  {
    long heightTime = time / 200000L;
    
    if (heightTime < 3L)
    {
      return 0L;
    }
    

    tessellator.func_178961_b(r, g, b, 255);
    tessellator.addVertex(frameNum + 0.5F, baseHeight - (float)heightTime + 0.5F, 0.0D);
    tessellator.addVertex(frameNum + 0.5F, baseHeight + 0.5F, 0.0D);
    return heightTime;
  }
  

  public static boolean isActive()
  {
    return active;
  }
  
  public static class TimerNano
  {
    public long timeStartNano = 0L;
    public long timeNano = 0L;
    
    public TimerNano() {}
    
    public void start() { if (Lagometer.active)
      {
        if (timeStartNano == 0L)
        {
          timeStartNano = System.nanoTime();
        }
      }
    }
    
    public void end()
    {
      if (Lagometer.active)
      {
        if (timeStartNano != 0L)
        {
          timeNano += System.nanoTime() - timeStartNano;
          timeStartNano = 0L;
        }
      }
    }
    
    private void reset()
    {
      timeNano = 0L;
      timeStartNano = 0L;
    }
  }
}
