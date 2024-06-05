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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import optifine.Reflector;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class GuiOverlayDebug extends Gui {
   private final Minecraft mc;
   private static final String __OBFID = "CL_00001956";
   private final FontRenderer fontRenderer;

   protected void func_180798_a() {
      List var1 = this.call();

      for(int var2 = 0; var2 < var1.size(); ++var2) {
         String var3 = (String)var1.get(var2);
         if (!Strings.isNullOrEmpty(var3)) {
            int var4 = this.fontRenderer.FONT_HEIGHT;
            int var5 = this.fontRenderer.getStringWidth(var3);
            boolean var6 = true;
            int var7 = 2 + var4 * var2;
            drawRect(1.0D, (double)(var7 - 1), (double)(2 + var5 + 1), (double)(var7 + var4 - 1), -1873784752);
            this.fontRenderer.drawString(var3, 2.0D, (double)var7, 14737632);
         }
      }

   }

   protected void func_175239_b(ScaledResolution var1) {
      List var2 = this.func_175238_c();

      for(int var3 = 0; var3 < var2.size(); ++var3) {
         String var4 = (String)var2.get(var3);
         if (!Strings.isNullOrEmpty(var4)) {
            int var5 = this.fontRenderer.FONT_HEIGHT;
            int var6 = this.fontRenderer.getStringWidth(var4);
            int var7 = var1.getScaledWidth() - 2 - var6;
            int var8 = 2 + var5 * var3;
            drawRect((double)(var7 - 1), (double)(var8 - 1), (double)(var7 + var6 + 1), (double)(var8 + var5 - 1), -1873784752);
            this.fontRenderer.drawString(var4, (double)var7, (double)var8, 14737632);
         }
      }

   }

   private boolean func_175236_d() {
      return this.mc.thePlayer.func_175140_cp() || this.mc.gameSettings.field_178879_v;
   }

   public GuiOverlayDebug(Minecraft var1) {
      this.mc = var1;
      this.fontRenderer = var1.fontRendererObj;
   }

   protected List func_175238_c() {
      long var1 = Runtime.getRuntime().maxMemory();
      long var3 = Runtime.getRuntime().totalMemory();
      long var5 = Runtime.getRuntime().freeMemory();
      long var7 = var3 - var5;
      ArrayList var9 = Lists.newArrayList(new String[]{String.format("Java: %s %dbit", System.getProperty("java.version"), this.mc.isJava64bit() ? 64 : 32), String.format("Mem: % 2d%% %03d/%03dMB", var7 * 100L / var1, func_175240_a(var7), func_175240_a(var1)), String.format("Allocated: % 2d%% %03dMB", var3 * 100L / var1, func_175240_a(var3)), "", String.format("Display: %dx%d (%s)", Display.getWidth(), Display.getHeight(), GL11.glGetString(7936)), GL11.glGetString(7937), GL11.glGetString(7938)});
      if (Reflector.FMLCommonHandler_getBrandings.exists()) {
         Object var10 = Reflector.call(Reflector.FMLCommonHandler_instance);
         var9.add("");
         var9.addAll((Collection)Reflector.call(var10, Reflector.FMLCommonHandler_getBrandings, false));
      }

      if (this.func_175236_d()) {
         return var9;
      } else {
         if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.mc.objectMouseOver.func_178782_a() != null) {
            BlockPos var15 = this.mc.objectMouseOver.func_178782_a();
            IBlockState var11 = this.mc.theWorld.getBlockState(var15);
            if (this.mc.theWorld.getWorldType() != WorldType.DEBUG_WORLD) {
               var11 = var11.getBlock().getActualState(var11, this.mc.theWorld, var15);
            }

            var9.add("");
            var9.add(String.valueOf(Block.blockRegistry.getNameForObject(var11.getBlock())));

            Entry var12;
            String var13;
            for(UnmodifiableIterator var14 = var11.getProperties().entrySet().iterator(); var14.hasNext(); var9.add(String.valueOf((new StringBuilder(String.valueOf(((IProperty)var12.getKey()).getName()))).append(": ").append(var13)))) {
               var12 = (Entry)var14.next();
               var13 = ((Comparable)var12.getValue()).toString();
               if (var12.getValue() == Boolean.TRUE) {
                  var13 = String.valueOf((new StringBuilder()).append(EnumChatFormatting.GREEN).append(var13));
               } else if (var12.getValue() == Boolean.FALSE) {
                  var13 = String.valueOf((new StringBuilder()).append(EnumChatFormatting.RED).append(var13));
               }
            }
         }

         return var9;
      }
   }

   protected List call() {
      BlockPos var1 = new BlockPos(this.mc.func_175606_aa().posX, this.mc.func_175606_aa().getEntityBoundingBox().minY, this.mc.func_175606_aa().posZ);
      if (this.func_175236_d()) {
         return Lists.newArrayList(new String[]{String.valueOf((new StringBuilder("Minecraft 1.8 (")).append(this.mc.func_175600_c()).append("/").append(ClientBrandRetriever.getClientModName()).append(")")), this.mc.debug, this.mc.renderGlobal.getDebugInfoRenders(), this.mc.renderGlobal.getDebugInfoEntities(), String.valueOf((new StringBuilder("P: ")).append(this.mc.effectRenderer.getStatistics()).append(". T: ").append(this.mc.theWorld.getDebugLoadedEntities())), this.mc.theWorld.getProviderName(), "", String.format("Chunk-relative: %d %d %d", var1.getX() & 15, var1.getY() & 15, var1.getZ() & 15)});
      } else {
         Entity var2 = this.mc.func_175606_aa();
         EnumFacing var3 = var2.func_174811_aO();
         String var4 = "Invalid";
         switch(var3) {
         case NORTH:
            var4 = "Towards negative Z";
            break;
         case SOUTH:
            var4 = "Towards positive Z";
            break;
         case WEST:
            var4 = "Towards negative X";
            break;
         case EAST:
            var4 = "Towards positive X";
         }

         ArrayList var5 = Lists.newArrayList(new String[]{String.valueOf((new StringBuilder("Minecraft 1.8 (")).append(this.mc.func_175600_c()).append("/").append(ClientBrandRetriever.getClientModName()).append(")")), this.mc.debug, this.mc.renderGlobal.getDebugInfoRenders(), this.mc.renderGlobal.getDebugInfoEntities(), String.valueOf((new StringBuilder("P: ")).append(this.mc.effectRenderer.getStatistics()).append(". T: ").append(this.mc.theWorld.getDebugLoadedEntities())), this.mc.theWorld.getProviderName(), "", String.format("XYZ: %.3f / %.5f / %.3f", this.mc.func_175606_aa().posX, this.mc.func_175606_aa().getEntityBoundingBox().minY, this.mc.func_175606_aa().posZ), String.format("Block: %d %d %d", var1.getX(), var1.getY(), var1.getZ()), String.format("Chunk: %d %d %d in %d %d %d", var1.getX() & 15, var1.getY() & 15, var1.getZ() & 15, var1.getX() >> 4, var1.getY() >> 4, var1.getZ() >> 4), String.format("Facing: %s (%s) (%.1f / %.1f)", var3, var4, MathHelper.wrapAngleTo180_float(var2.rotationYaw), MathHelper.wrapAngleTo180_float(var2.rotationPitch))});
         if (this.mc.theWorld != null && this.mc.theWorld.isBlockLoaded(var1)) {
            Chunk var6 = this.mc.theWorld.getChunkFromBlockCoords(var1);
            var5.add(String.valueOf((new StringBuilder("Biome: ")).append(var6.getBiome(var1, this.mc.theWorld.getWorldChunkManager()).biomeName)));
            var5.add(String.valueOf((new StringBuilder("Light: ")).append(var6.setLight(var1, 0)).append(" (").append(var6.getLightFor(EnumSkyBlock.SKY, var1)).append(" sky, ").append(var6.getLightFor(EnumSkyBlock.BLOCK, var1)).append(" block)")));
            DifficultyInstance var7 = this.mc.theWorld.getDifficultyForLocation(var1);
            if (this.mc.isIntegratedServerRunning() && this.mc.getIntegratedServer() != null) {
               EntityPlayerMP var8 = this.mc.getIntegratedServer().getConfigurationManager().func_177451_a(this.mc.thePlayer.getUniqueID());
               if (var8 != null) {
                  var7 = var8.worldObj.getDifficultyForLocation(new BlockPos(var8));
               }
            }

            var5.add(String.format("Local Difficulty: %.2f (Day %d)", var7.func_180168_b(), this.mc.theWorld.getWorldTime() / 24000L));
         }

         if (this.mc.entityRenderer != null && this.mc.entityRenderer.isShaderActive()) {
            var5.add(String.valueOf((new StringBuilder("Shader: ")).append(this.mc.entityRenderer.getShaderGroup().getShaderGroupName())));
         }

         if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.mc.objectMouseOver.func_178782_a() != null) {
            BlockPos var9 = this.mc.objectMouseOver.func_178782_a();
            var5.add(String.format("Looking at: %d %d %d", var9.getX(), var9.getY(), var9.getZ()));
         }

         return var5;
      }
   }

   private static long func_175240_a(long var0) {
      return var0 / 1024L / 1024L;
   }

   public void func_175237_a(ScaledResolution var1) {
      this.mc.mcProfiler.startSection("debug");
      GlStateManager.pushMatrix();
      this.func_180798_a();
      this.func_175239_b(var1);
      GlStateManager.popMatrix();
      this.mc.mcProfiler.endSection();
   }

   static final class SwitchEnumFacing {
      static final int[] field_178907_a = new int[EnumFacing.values().length];
      private static final String __OBFID = "CL_00001955";

      static {
         try {
            field_178907_a[EnumFacing.NORTH.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_178907_a[EnumFacing.SOUTH.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_178907_a[EnumFacing.WEST.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_178907_a[EnumFacing.EAST.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
