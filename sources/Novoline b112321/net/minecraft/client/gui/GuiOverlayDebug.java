package net.minecraft.client.gui;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiOverlayDebug$GuiOverlayDebug$1;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition$MovingObjectType;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import net.optifine.Reflector;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class GuiOverlayDebug extends Gui {
   private final Minecraft mc;
   private final FontRenderer fontRenderer;
   private static final String h = "CL_00001956";

   public GuiOverlayDebug(Minecraft var1) {
      this.mc = var1;
      this.fontRenderer = var1.fontRendererObj;
   }

   public void renderDebugInfo(ScaledResolution var1) {
      this.mc.mcProfiler.startSection("debug");
      GlStateManager.pushMatrix();
      this.renderDebugInfoLeft();
      this.renderDebugInfoRight(var1);
      GlStateManager.popMatrix();
      this.mc.mcProfiler.endSection();
   }

   private boolean isReducedDebug() {
      return this.mc.player.hasReducedDebug() || this.mc.gameSettings.reducedDebugInfo;
   }

   protected void renderDebugInfoLeft() {
      List var1 = this.call();

      for(int var2 = 0; var2 < var1.size(); ++var2) {
         String var3 = (String)var1.get(var2);
         if(!Strings.isNullOrEmpty(var3)) {
            int var4 = this.fontRenderer.getHeight();
            int var5 = this.fontRenderer.d(var3);
            boolean var6 = true;
            int var7 = 2 + var4 * var2;
            drawRect(1, var7 - 1, 2 + var5 + 1, var7 + var4 - 1, -1873784752);
            this.fontRenderer.drawString(var3, 2.0F, (float)var7, 14737632);
         }
      }

   }

   protected void renderDebugInfoRight(ScaledResolution var1) {
      List var2 = this.getDebugInfoRight();

      for(int var3 = 0; var3 < var2.size(); ++var3) {
         String var4 = (String)var2.get(var3);
         if(!Strings.isNullOrEmpty(var4)) {
            int var5 = this.fontRenderer.getHeight();
            int var6 = this.fontRenderer.d(var4);
            int var7 = var1.getScaledWidth() - 2 - var6;
            int var8 = 2 + var5 * var3;
            drawRect(var7 - 1, var8 - 1, var7 + var6 + 1, var8 + var5 - 1, -1873784752);
            this.fontRenderer.drawString(var4, (float)var7, (float)var8, 14737632);
         }
      }

   }

   protected List call() {
      BlockPos var1 = new BlockPos(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().getEntityBoundingBox().minY, this.mc.getRenderViewEntity().posZ);
      if(this.isReducedDebug()) {
         return Lists.newArrayList(new String[]{"Minecraft 1.8.8 (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ")", this.mc.debug, this.mc.renderGlobal.getDebugInfoRenders(), this.mc.renderGlobal.getDebugInfoEntities(), "P: " + this.mc.effectRenderer.getStatistics() + ". T: " + this.mc.world.getDebugLoadedEntities(), this.mc.world.getProviderName(), "", String.format("Chunk-relative: %d %d %d", new Object[]{Integer.valueOf(var1.getX() & 15), Integer.valueOf(var1.getY() & 15), Integer.valueOf(var1.getZ() & 15)})});
      } else {
         Entity var2 = this.mc.getRenderViewEntity();
         EnumFacing var3 = var2.getHorizontalFacing();
         String var4 = "Invalid";
         switch(GuiOverlayDebug$GuiOverlayDebug$1.field_178907_a[var3.ordinal()]) {
         case 1:
            var4 = "Towards negative Z";
            break;
         case 2:
            var4 = "Towards positive Z";
            break;
         case 3:
            var4 = "Towards negative X";
            break;
         case 4:
            var4 = "Towards positive X";
         }

         ArrayList var5 = Lists.newArrayList(new String[]{"Minecraft 1.8.8 (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ")", this.mc.debug, this.mc.renderGlobal.getDebugInfoRenders(), this.mc.renderGlobal.getDebugInfoEntities(), "P: " + this.mc.effectRenderer.getStatistics() + ". T: " + this.mc.world.getDebugLoadedEntities(), this.mc.world.getProviderName(), "", String.format("XYZ: %.3f / %.2f / %.3f", new Object[]{Double.valueOf(this.mc.getRenderViewEntity().posX), Double.valueOf(this.mc.getRenderViewEntity().posY), Double.valueOf(this.mc.getRenderViewEntity().posZ)}), String.format("Block: %d %d %d", new Object[]{Integer.valueOf(var1.getX()), Integer.valueOf(var1.getY()), Integer.valueOf(var1.getZ())}), String.format("Chunk: %d %d %d in %d %d %d", new Object[]{Integer.valueOf(var1.getX() & 15), Integer.valueOf(var1.getY() & 15), Integer.valueOf(var1.getZ() & 15), Integer.valueOf(var1.getX() >> 4), Integer.valueOf(var1.getY() >> 4), Integer.valueOf(var1.getZ() >> 4)}), String.format("Facing: %s (%s) (%.1f / %.1f)", new Object[]{var3, var4, Float.valueOf(MathHelper.wrapAngleTo180_float(var2.rotationYaw)), Float.valueOf(MathHelper.wrapAngleTo180_float(var2.rotationPitch))})});
         if(this.mc.world != null && this.mc.world.isBlockLoaded(var1)) {
            Chunk var6 = this.mc.world.getChunkFromBlockCoords(var1);
            var5.add("Biome: " + var6.getBiome(var1, this.mc.world.getWorldChunkManager()).biomeName);
            var5.add("Light: " + var6.getLightSubtracted(var1, 0) + " (" + var6.getLightFor(EnumSkyBlock.SKY, var1) + " sky, " + var6.getLightFor(EnumSkyBlock.BLOCK, var1) + " block)");
            DifficultyInstance var7 = this.mc.world.getDifficultyForLocation(var1);
            if(this.mc.isIntegratedServerRunning() && this.mc.getIntegratedServer() != null) {
               EntityPlayerMP var8 = this.mc.getIntegratedServer().getConfigurationManager().getPlayerByUUID(this.mc.player.getUniqueID());
               var7 = var8.worldObj.getDifficultyForLocation(new BlockPos(var8));
            }

            var5.add(String.format("Local Difficulty: %.2f (Day %d)", new Object[]{Float.valueOf(var7.getAdditionalDifficulty()), Long.valueOf(this.mc.world.getWorldTime() / 24000L)}));
         }

         if(this.mc.entityRenderer != null && this.mc.entityRenderer.isShaderActive()) {
            var5.add("Shader: " + this.mc.entityRenderer.getShaderGroup().getShaderGroupName());
         }

         if(this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition$MovingObjectType.BLOCK && this.mc.objectMouseOver.getBlockPos() != null) {
            BlockPos var9 = this.mc.objectMouseOver.getBlockPos();
            var5.add(String.format("Looking at: %d %d %d", new Object[]{Integer.valueOf(var9.getX()), Integer.valueOf(var9.getY()), Integer.valueOf(var9.getZ())}));
         }

         return var5;
      }
   }

   protected List getDebugInfoRight() {
      long var1 = Runtime.getRuntime().maxMemory();
      long var3 = Runtime.getRuntime().totalMemory();
      long var5 = Runtime.getRuntime().freeMemory();
      long var7 = var3 - var5;
      ArrayList var9 = Lists.newArrayList(new String[]{String.format("Java: %s %dbit", new Object[]{System.getProperty("java.version"), Integer.valueOf(this.mc.isJava64bit()?64:32)}), String.format("Mem: % 2d%% %03d/%03dMB", new Object[]{Long.valueOf(var7 * 100L / var1), Long.valueOf(bytesToMb(var7)), Long.valueOf(bytesToMb(var1))}), String.format("Allocated: % 2d%% %03dMB", new Object[]{Long.valueOf(var3 * 100L / var1), Long.valueOf(bytesToMb(var3))}), "", String.format("CPU: %s", new Object[]{OpenGlHelper.func_183029_j()}), "", String.format("Display: %dx%d (%s)", new Object[]{Integer.valueOf(Display.getWidth()), Integer.valueOf(Display.getHeight()), GL11.glGetString(7936)}), GL11.glGetString(7937), GL11.glGetString(7938)});
      if(Reflector.aZ.d()) {
         Object var10 = Reflector.f(Reflector.dk, new Object[0]);
         var9.add("");
         var9.addAll((Collection)Reflector.f(var10, Reflector.aZ, new Object[]{Boolean.FALSE}));
      }

      if(!this.isReducedDebug() && this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition$MovingObjectType.BLOCK && this.mc.objectMouseOver.getBlockPos() != null) {
         BlockPos var15 = this.mc.objectMouseOver.getBlockPos();
         IBlockState var11 = this.mc.world.getBlockState(var15);
         if(this.mc.world.getWorldType() != WorldType.DEBUG_WORLD) {
            var11 = var11.getBlock().getActualState(var11, this.mc.world, var15);
         }

         var9.add("");
         var9.add(String.valueOf(Block.blockRegistry.getNameForObject(var11.getBlock())));

         Entry var12;
         String var13;
         for(UnmodifiableIterator var14 = var11.getProperties().entrySet().iterator(); var14.hasNext(); var9.add(((IProperty)var12.getKey()).getName() + ": " + var13)) {
            var12 = (Entry)var14.next();
            var13 = var12.getValue().toString();
            if(var12.getValue() == Boolean.TRUE) {
               var13 = EnumChatFormatting.GREEN + var13;
            } else if(var12.getValue() == Boolean.FALSE) {
               var13 = EnumChatFormatting.RED + var13;
            }
         }
      }

      return var9;
   }

   private void func_181554_e() {
      GlStateManager.disableDepth();
      FrameTimer var1 = this.mc.func_181539_aj();
      int var2 = var1.func_181749_a();
      int var3 = var1.func_181750_b();
      long[] var4 = var1.func_181746_c();
      ScaledResolution var5 = new ScaledResolution(this.mc);
      int var6 = var2;
      int var7 = 0;
      drawRect(0, var5.getScaledHeight() - 60, 240, var5.getScaledHeight(), -1873784752);

      while(var6 != var3) {
         int var8 = var1.func_181748_a(var4[var6], 30);
         int var9 = this.func_181552_c(MathHelper.clamp_int(var8, 0, 60), 0, 30, 60);
         this.drawVerticalLine(var7, var5.getScaledHeight(), var5.getScaledHeight() - var8, var9);
         ++var7;
         var6 = var1.func_181751_b(var6 + 1);
      }

      drawRect(1, var5.getScaledHeight() - 30 + 1, 14, var5.getScaledHeight() - 30 + 10, -1873784752);
      this.fontRenderer.drawString("60", 2.0F, (float)(var5.getScaledHeight() - 30 + 2), 14737632);
      this.drawHorizontalLine(0, 239, var5.getScaledHeight() - 30, -1);
      drawRect(1, var5.getScaledHeight() - 60 + 1, 14, var5.getScaledHeight() - 60 + 10, -1873784752);
      this.fontRenderer.drawString("30", 2.0F, (float)(var5.getScaledHeight() - 60 + 2), 14737632);
      this.drawHorizontalLine(0, 239, var5.getScaledHeight() - 60, -1);
      this.drawHorizontalLine(0, 239, var5.getScaledHeight() - 1, -1);
      this.drawVerticalLine(0, var5.getScaledHeight() - 60, var5.getScaledHeight(), -1);
      this.drawVerticalLine(239, var5.getScaledHeight() - 60, var5.getScaledHeight(), -1);
      if(this.mc.gameSettings.limitFramerate <= 120) {
         this.drawHorizontalLine(0, 239, var5.getScaledHeight() - 60 + this.mc.gameSettings.limitFramerate / 2, -16711681);
      }

      GlStateManager.enableDepth();
   }

   private int func_181552_c(int var1, int var2, int var3, int var4) {
      return var1 < var3?this.func_181553_a(-16711936, -256, (float)var1 / (float)var3):this.func_181553_a(-256, -65536, (float)(var1 - var3) / (float)(var4 - var3));
   }

   private int func_181553_a(int var1, int var2, float var3) {
      int var4 = var1 >> 24 & 255;
      int var5 = var1 >> 16 & 255;
      int var6 = var1 >> 8 & 255;
      int var7 = var1 & 255;
      int var8 = var2 >> 24 & 255;
      int var9 = var2 >> 16 & 255;
      int var10 = var2 >> 8 & 255;
      int var11 = var2 & 255;
      int var12 = MathHelper.clamp_int((int)((float)var4 + (float)(var8 - var4) * var3), 0, 255);
      int var13 = MathHelper.clamp_int((int)((float)var5 + (float)(var9 - var5) * var3), 0, 255);
      int var14 = MathHelper.clamp_int((int)((float)var6 + (float)(var10 - var6) * var3), 0, 255);
      int var15 = MathHelper.clamp_int((int)((float)var7 + (float)(var11 - var7) * var3), 0, 255);
      return var12 << 24 | var13 << 16 | var14 << 8 | var15;
   }

   private static long bytesToMb(long var0) {
      return var0 / 1024L / 1024L;
   }
}
