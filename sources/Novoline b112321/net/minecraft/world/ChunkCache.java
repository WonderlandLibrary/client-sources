package net.minecraft.world;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.Chunk$EnumCreateEntityType;

public class ChunkCache implements IBlockAccess {
   protected int chunkX;
   protected int chunkZ;
   protected Chunk[][] chunkArray;
   protected boolean hasExtendedLevels;
   protected World worldObj;

   public ChunkCache(World var1, BlockPos var2, BlockPos var3, int var4) {
      this.worldObj = var1;
      this.chunkX = var2.getX() - var4 >> 4;
      this.chunkZ = var2.getZ() - var4 >> 4;
      int var5 = var3.getX() + var4 >> 4;
      int var6 = var3.getZ() + var4 >> 4;
      this.chunkArray = new Chunk[var5 - this.chunkX + 1][var6 - this.chunkZ + 1];
      this.hasExtendedLevels = true;

      for(int var7 = this.chunkX; var7 <= var5; ++var7) {
         for(int var8 = this.chunkZ; var8 <= var6; ++var8) {
            this.chunkArray[var7 - this.chunkX][var8 - this.chunkZ] = var1.getChunkFromChunkCoords(var7, var8);
         }
      }

      for(int var10 = var2.getX() >> 4; var10 <= var3.getX() >> 4; ++var10) {
         for(int var11 = var2.getZ() >> 4; var11 <= var3.getZ() >> 4; ++var11) {
            Chunk var9 = this.chunkArray[var10 - this.chunkX][var11 - this.chunkZ];
            if(!var9.getAreLevelsEmpty(var2.getY(), var3.getY())) {
               this.hasExtendedLevels = false;
            }
         }
      }

   }

   public boolean extendedLevelsInChunkCache() {
      return this.hasExtendedLevels;
   }

   public TileEntity getTileEntity(BlockPos var1) {
      int var2 = (var1.getX() >> 4) - this.chunkX;
      int var3 = (var1.getZ() >> 4) - this.chunkZ;
      return this.chunkArray[var2][var3].getTileEntity(var1, Chunk$EnumCreateEntityType.IMMEDIATE);
   }

   public int getCombinedLight(BlockPos var1, int var2) {
      int var3 = this.getLightForExt(EnumSkyBlock.SKY, var1);
      int var4 = this.getLightForExt(EnumSkyBlock.BLOCK, var1);
      if(var4 < var2) {
         var4 = var2;
      }

      return var3 << 20 | var4 << 4;
   }

   public IBlockState getBlockState(BlockPos var1) {
      if(var1.getY() >= 0 && var1.getY() < 256) {
         int var2 = (var1.getX() >> 4) - this.chunkX;
         int var3 = (var1.getZ() >> 4) - this.chunkZ;
         if(var2 < this.chunkArray.length && var3 < this.chunkArray[var2].length) {
            Chunk var4 = this.chunkArray[var2][var3];
            return var4.getBlockState(var1);
         }
      }

      return Blocks.air.getDefaultState();
   }

   public BiomeGenBase getBiomeGenForCoords(BlockPos var1) {
      return this.worldObj.getBiomeGenForCoords(var1);
   }

   private int getLightForExt(EnumSkyBlock var1, BlockPos var2) {
      if(var1 == EnumSkyBlock.SKY && this.worldObj.provider.getHasNoSky()) {
         return 0;
      } else if(var2.getY() >= 0 && var2.getY() < 256) {
         if(this.getBlockState(var2).getBlock().getUseNeighborBrightness()) {
            int var9 = 0;

            for(EnumFacing var7 : EnumFacing.values()) {
               int var8 = this.getLightFor(var1, var2.offset(var7));
               if(var8 > var9) {
                  var9 = var8;
               }

               if(var9 >= 15) {
                  return var9;
               }
            }

            return var9;
         } else {
            int var3 = (var2.getX() >> 4) - this.chunkX;
            int var4 = (var2.getZ() >> 4) - this.chunkZ;
            return this.chunkArray[var3][var4].getLightFor(var1, var2);
         }
      } else {
         return var1.defaultLightValue;
      }
   }

   public boolean isAirBlock(BlockPos var1) {
      return this.getBlockState(var1).getBlock().getMaterial() == Material.air;
   }

   public int getLightFor(EnumSkyBlock var1, BlockPos var2) {
      if(var2.getY() >= 0 && var2.getY() < 256) {
         int var3 = (var2.getX() >> 4) - this.chunkX;
         int var4 = (var2.getZ() >> 4) - this.chunkZ;
         return this.chunkArray[var3][var4].getLightFor(var1, var2);
      } else {
         return var1.defaultLightValue;
      }
   }

   public int getStrongPower(BlockPos var1, EnumFacing var2) {
      IBlockState var3 = this.getBlockState(var1);
      return var3.getBlock().getStrongPower(this, var1, var3, var2);
   }

   public WorldType getWorldType() {
      return this.worldObj.getWorldType();
   }
}
