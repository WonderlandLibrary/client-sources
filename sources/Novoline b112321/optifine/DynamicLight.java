package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
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
import net.minecraft.util.BlockPos$MutableBlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import optifine.Config;
import optifine.DynamicLights;
import optifine.MatchBlock;

public class DynamicLight {
   private Entity entity = null;
   private double offsetY = 0.0D;
   private double lastPosX = -2.147483648E9D;
   private double lastPosY = -2.147483648E9D;
   private double lastPosZ = -2.147483648E9D;
   private int lastLightLevel = 0;
   private boolean underwater = false;
   private long timeCheckMs = 0L;
   private Set setLitChunkPos = new HashSet();
   private BlockPos$MutableBlockPos blockPosMutable = new BlockPos$MutableBlockPos();

   public DynamicLight(Entity var1) {
      this.entity = var1;
      this.offsetY = (double)var1.getEyeHeight();
   }

   public void update(RenderGlobal var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(Config.aO()) {
         long var3 = System.currentTimeMillis();
         if(var3 < this.timeCheckMs + 500L) {
            return;
         }

         this.timeCheckMs = var3;
      }

      double var32 = this.entity.posX - 0.5D;
      double var5 = this.entity.posY - 0.5D + this.offsetY;
      double var7 = this.entity.posZ - 0.5D;
      int var9 = DynamicLights.getLightLevel(this.entity);
      double var10 = var32 - this.lastPosX;
      double var12 = var5 - this.lastPosY;
      double var14 = var7 - this.lastPosZ;
      double var16 = 0.1D;
      if(Math.abs(var10) > var16 || Math.abs(var12) > var16 || Math.abs(var14) > var16 || this.lastLightLevel != var9) {
         this.lastPosX = var32;
         this.lastPosY = var5;
         this.lastPosZ = var7;
         this.lastLightLevel = var9;
         this.underwater = false;
         WorldClient var18 = var1.getWorld();
         if(var18 != null) {
            this.blockPosMutable.func_181079_c(MathHelper.floor_double(var32), MathHelper.floor_double(var5), MathHelper.floor_double(var7));
            IBlockState var19 = var18.getBlockState(this.blockPosMutable);
            Block var20 = var19.getBlock();
            this.underwater = var20 == Blocks.water;
         }

         HashSet var33 = new HashSet();
         EnumFacing var34 = (MathHelper.floor_double(var32) & 15) >= 8?EnumFacing.EAST:EnumFacing.WEST;
         EnumFacing var21 = (MathHelper.floor_double(var5) & 15) >= 8?EnumFacing.UP:EnumFacing.DOWN;
         EnumFacing var22 = (MathHelper.floor_double(var7) & 15) >= 8?EnumFacing.SOUTH:EnumFacing.NORTH;
         BlockPos var23 = new BlockPos(var32, var5, var7);
         RenderChunk var24 = var1.getRenderChunk(var23);
         RenderChunk var25 = var1.a(var24, var34);
         RenderChunk var26 = var1.a(var24, var22);
         RenderChunk var27 = var1.a(var25, var22);
         RenderChunk var28 = var1.a(var24, var21);
         RenderChunk var29 = var1.a(var28, var34);
         RenderChunk var30 = var1.a(var28, var22);
         RenderChunk var31 = var1.a(var29, var22);
         this.updateChunkLight(var24, this.setLitChunkPos, var33);
         this.updateChunkLight(var25, this.setLitChunkPos, var33);
         this.updateChunkLight(var26, this.setLitChunkPos, var33);
         this.updateChunkLight(var27, this.setLitChunkPos, var33);
         this.updateChunkLight(var28, this.setLitChunkPos, var33);
         this.updateChunkLight(var29, this.setLitChunkPos, var33);
         this.updateChunkLight(var30, this.setLitChunkPos, var33);
         this.updateChunkLight(var31, this.setLitChunkPos, var33);
         this.updateLitChunks(var1);
         this.setLitChunkPos = var33;
      }

   }

   private void updateChunkLight(RenderChunk var1, Set var2, Set var3) {
      PacketRemapper[] var4 = MatchBlock.b();
      if(var1 != null) {
         CompiledChunk var5 = var1.getCompiledChunk();
         if(var5 != null && !var5.isEmpty()) {
            var1.setNeedsUpdate(true);
         }

         BlockPos var6 = var1.getPosition();
         if(var2 != null) {
            var2.remove(var6);
         }

         if(var3 != null) {
            var3.add(var6);
         }
      }

   }

   public void updateLitChunks(RenderGlobal var1) {
      MatchBlock.b();
      Iterator var3 = this.setLitChunkPos.iterator();
      if(var3.hasNext()) {
         BlockPos var4 = (BlockPos)var3.next();
         RenderChunk var5 = var1.getRenderChunk(var4);
         this.updateChunkLight(var5, (Set)null, (Set)null);
      }

   }

   public Entity getEntity() {
      return this.entity;
   }

   public double getLastPosX() {
      return this.lastPosX;
   }

   public double getLastPosY() {
      return this.lastPosY;
   }

   public double getLastPosZ() {
      return this.lastPosZ;
   }

   public int getLastLightLevel() {
      return this.lastLightLevel;
   }

   public boolean isUnderwater() {
      return this.underwater;
   }

   public double getOffsetY() {
      return this.offsetY;
   }

   public String toString() {
      return "Entity: " + this.entity + ", offsetY: " + this.offsetY;
   }
}
