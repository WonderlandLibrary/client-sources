package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStronghold$Start;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.StructureStrongholdPieces$Stairs2;

public class MapGenStronghold extends MapGenStructure {
   private List field_151546_e;
   private boolean ranBiomeCheck;
   private ChunkCoordIntPair[] structureCoords;
   private double field_82671_h;
   private int field_82672_i;

   public MapGenStronghold() {
      this.structureCoords = new ChunkCoordIntPair[3];
      this.field_82671_h = 32.0D;
      this.field_82672_i = 3;
      this.field_151546_e = Lists.newArrayList();

      for(BiomeGenBase var4 : BiomeGenBase.getBiomeGenArray()) {
         if(var4.minHeight > 0.0F) {
            this.field_151546_e.add(var4);
         }
      }

   }

   public MapGenStronghold(Map var1) {
      this();

      for(Entry var3 : var1.entrySet()) {
         if(((String)var3.getKey()).equals("distance")) {
            this.field_82671_h = MathHelper.parseDoubleWithDefaultAndMax((String)var3.getValue(), this.field_82671_h, 1.0D);
         } else if(((String)var3.getKey()).equals("count")) {
            this.structureCoords = new ChunkCoordIntPair[MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), this.structureCoords.length, 1)];
         } else if(((String)var3.getKey()).equals("spread")) {
            this.field_82672_i = MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), this.field_82672_i, 1);
         }
      }

   }

   public String getStructureName() {
      return "Stronghold";
   }

   protected boolean canSpawnStructureAtCoords(int var1, int var2) {
      if(!this.ranBiomeCheck) {
         Random var3 = new Random();
         var3.setSeed(this.worldObj.getSeed());
         double var4 = var3.nextDouble() * 3.141592653589793D * 2.0D;
         int var6 = 1;

         for(int var7 = 0; var7 < this.structureCoords.length; ++var7) {
            double var8 = (1.25D * (double)var6 + var3.nextDouble()) * this.field_82671_h * (double)var6;
            int var10 = (int)Math.round((double)MathHelper.cos(var4) * var8);
            int var11 = (int)Math.round((double)MathHelper.sin(var4) * var8);
            BlockPos var12 = this.worldObj.getWorldChunkManager().findBiomePosition((var10 << 4) + 8, (var11 << 4) + 8, 112, this.field_151546_e, var3);
            var10 = var12.getX() >> 4;
            var11 = var12.getZ() >> 4;
            this.structureCoords[var7] = new ChunkCoordIntPair(var10, var11);
            var4 += 6.283185307179586D * (double)var6 / (double)this.field_82672_i;
            if(var7 == this.field_82672_i) {
               var6 += 2 + var3.nextInt(5);
               this.field_82672_i += 1 + var3.nextInt(2);
            }
         }

         this.ranBiomeCheck = true;
      }

      for(ChunkCoordIntPair var15 : this.structureCoords) {
         if(var1 == var15.chunkXPos && var2 == var15.chunkZPos) {
            return true;
         }
      }

      return false;
   }

   protected List getCoordList() {
      ArrayList var1 = Lists.newArrayList();

      for(ChunkCoordIntPair var5 : this.structureCoords) {
         var1.add(var5.getCenterBlock(64));
      }

      return var1;
   }

   protected StructureStart getStructureStart(int var1, int var2) {
      MapGenStronghold$Start var3;
      for(var3 = new MapGenStronghold$Start(this.worldObj, this.rand, var1, var2); var3.getComponents().isEmpty() || ((StructureStrongholdPieces$Stairs2)var3.getComponents().get(0)).strongholdPortalRoom == null; var3 = new MapGenStronghold$Start(this.worldObj, this.rand, var1, var2)) {
         ;
      }

      return var3;
   }
}
