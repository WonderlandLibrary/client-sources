package optifine;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class DynamicLight {
   private double lastPosZ = -2.147483648E9D;
   private double lastPosX = -2.147483648E9D;
   private int lastLightLevel = 0;
   private double offsetY = 0.0D;
   private Entity entity = null;
   private BlockPosM blockPosMutable = new BlockPosM(0, 0, 0);
   private boolean underwater = false;
   private long timeCheckMs = 0L;
   private Set<BlockPos> setLitChunkPos = new HashSet();
   private double lastPosY = -2.147483648E9D;

   public String toString() {
      return String.valueOf((new StringBuilder("Entity: ")).append(this.entity).append(", offsetY: ").append(this.offsetY));
   }

   public Entity getEntity() {
      return this.entity;
   }

   public DynamicLight(Entity var1) {
      this.entity = var1;
      this.offsetY = (double)var1.getEyeHeight();
   }

   public double getOffsetY() {
      return this.offsetY;
   }

   public void updateLitChunks(RenderGlobal var1) {
      Iterator var2 = this.setLitChunkPos.iterator();

      while(var2.hasNext()) {
         BlockPos var3 = (BlockPos)var2.next();
         RenderChunk var4 = var1.getRenderChunk(var3);
         this.updateChunkLight(var4, (Set)null, (Set)null);
      }

   }

   public void update(RenderGlobal var1) {
      if (Config.isDynamicLightsFast()) {
         long var2 = System.currentTimeMillis();
         if (var2 < this.timeCheckMs + 500L) {
            return;
         }

         this.timeCheckMs = var2;
      }

      double var31 = this.entity.posX - 0.5D;
      double var4 = this.entity.posY - 0.5D + this.offsetY;
      double var6 = this.entity.posZ - 0.5D;
      int var8 = DynamicLights.getLightLevel(this.entity);
      double var9 = var31 - this.lastPosX;
      double var11 = var4 - this.lastPosY;
      double var13 = var6 - this.lastPosZ;
      double var15 = 0.1D;
      if (Math.abs(var9) > var15 || Math.abs(var11) > var15 || Math.abs(var13) > var15 || this.lastLightLevel != var8) {
         this.lastPosX = var31;
         this.lastPosY = var4;
         this.lastPosZ = var6;
         this.lastLightLevel = var8;
         this.underwater = false;
         WorldClient var17 = var1.getWorld();
         if (var17 != null) {
            this.blockPosMutable.setXyz(MathHelper.floor_double(var31), MathHelper.floor_double(var4), MathHelper.floor_double(var6));
            IBlockState var18 = var17.getBlockState(this.blockPosMutable);
            Block var19 = var18.getBlock();
            this.underwater = var19 == Blocks.water;
         }

         HashSet var32 = new HashSet();
         if (var8 > 0) {
            EnumFacing var33 = (MathHelper.floor_double(var31) & 15) >= 8 ? EnumFacing.EAST : EnumFacing.WEST;
            EnumFacing var20 = (MathHelper.floor_double(var4) & 15) >= 8 ? EnumFacing.UP : EnumFacing.DOWN;
            EnumFacing var21 = (MathHelper.floor_double(var6) & 15) >= 8 ? EnumFacing.SOUTH : EnumFacing.NORTH;
            BlockPos var22 = new BlockPos(var31, var4, var6);
            RenderChunk var23 = var1.getRenderChunk(var22);
            RenderChunk var24 = var1.getRenderChunk(var23, var33);
            RenderChunk var25 = var1.getRenderChunk(var23, var21);
            RenderChunk var26 = var1.getRenderChunk(var24, var21);
            RenderChunk var27 = var1.getRenderChunk(var23, var20);
            RenderChunk var28 = var1.getRenderChunk(var27, var33);
            RenderChunk var29 = var1.getRenderChunk(var27, var21);
            RenderChunk var30 = var1.getRenderChunk(var28, var21);
            this.updateChunkLight(var23, this.setLitChunkPos, var32);
            this.updateChunkLight(var24, this.setLitChunkPos, var32);
            this.updateChunkLight(var25, this.setLitChunkPos, var32);
            this.updateChunkLight(var26, this.setLitChunkPos, var32);
            this.updateChunkLight(var27, this.setLitChunkPos, var32);
            this.updateChunkLight(var28, this.setLitChunkPos, var32);
            this.updateChunkLight(var29, this.setLitChunkPos, var32);
            this.updateChunkLight(var30, this.setLitChunkPos, var32);
         }

         this.updateLitChunks(var1);
         this.setLitChunkPos = var32;
      }

   }

   private void updateChunkLight(RenderChunk var1, Set<BlockPos> var2, Set<BlockPos> var3) {
      if (var1 != null) {
         CompiledChunk var4 = var1.func_178571_g();
         if (var4 != null && !var4.func_178489_a()) {
            var1.func_178575_a(true);
         }

         BlockPos var5 = var1.func_178568_j();
         if (var2 != null) {
            var2.remove(var5);
         }

         if (var3 != null) {
            var3.add(var5);
         }
      }

   }

   public double getLastPosZ() {
      return this.lastPosZ;
   }

   public double getLastPosY() {
      return this.lastPosY;
   }

   public int getLastLightLevel() {
      return this.lastLightLevel;
   }

   public double getLastPosX() {
      return this.lastPosX;
   }

   public boolean isUnderwater() {
      return this.underwater;
   }
}
