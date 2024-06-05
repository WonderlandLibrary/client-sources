package net.minecraft.village;

import net.minecraft.entity.EntityLiving$SpawnPlacementType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.village.Village;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;

public class VillageSiege {
   private World worldObj;
   private boolean field_75535_b;
   private int field_75536_c = -1;
   private int field_75533_d;
   private int field_75534_e;
   private Village theVillage;
   private int field_75532_g;
   private int field_75538_h;
   private int field_75539_i;

   public VillageSiege(World var1) {
      this.worldObj = var1;
   }

   public void tick() {
      if(this.worldObj.isDaytime()) {
         this.field_75536_c = 0;
      } else if(this.field_75536_c != 2) {
         if(this.field_75536_c == 0) {
            float var1 = this.worldObj.getCelestialAngle(0.0F);
            if((double)var1 < 0.5D || (double)var1 > 0.501D) {
               return;
            }

            this.field_75536_c = this.worldObj.rand.nextInt(10) == 0?1:2;
            this.field_75535_b = false;
            if(this.field_75536_c == 2) {
               return;
            }
         }

         if(this.field_75536_c != -1) {
            if(!this.field_75535_b) {
               if(!this.func_75529_b()) {
                  return;
               }

               this.field_75535_b = true;
            }

            if(this.field_75534_e > 0) {
               --this.field_75534_e;
            } else {
               this.field_75534_e = 2;
               if(this.field_75533_d > 0) {
                  this.a();
                  --this.field_75533_d;
               } else {
                  this.field_75536_c = 2;
               }
            }
         }
      }

   }

   private boolean func_75529_b() {
      for(EntityPlayer var3 : this.worldObj.getPlayerEntities()) {
         if(!var3.isSpectator()) {
            this.theVillage = this.worldObj.getVillageCollection().getNearestVillage(new BlockPos(var3), 1);
            if(this.theVillage != null && this.theVillage.getNumVillageDoors() >= 10 && this.theVillage.getTicksSinceLastDoorAdding() >= 20 && this.theVillage.getNumVillagers() >= 20) {
               BlockPos var4 = this.theVillage.getCenter();
               float var5 = (float)this.theVillage.getVillageRadius();
               boolean var6 = false;
               byte var7 = 0;
               if(var7 < 10) {
                  float var8 = this.worldObj.rand.nextFloat() * 3.1415927F * 2.0F;
                  this.field_75532_g = var4.getX() + (int)((double)(MathHelper.cos(var8) * var5) * 0.9D);
                  this.field_75538_h = var4.getY();
                  this.field_75539_i = var4.getZ() + (int)((double)(MathHelper.sin(var8) * var5) * 0.9D);
                  var6 = false;

                  for(Village var10 : this.worldObj.getVillageCollection().getVillageList()) {
                     if(var10 != this.theVillage && var10.func_179866_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i))) {
                        var6 = true;
                        break;
                     }
                  }
               }

               return false;
            }
         }
      }

      return false;
   }

   private boolean a() {
      Vec3 var1 = this.func_179867_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i));
      return false;
   }

   private Vec3 func_179867_a(BlockPos var1) {
      for(int var2 = 0; var2 < 10; ++var2) {
         BlockPos var3 = var1.a(this.worldObj.rand.nextInt(16) - 8, this.worldObj.rand.nextInt(6) - 3, this.worldObj.rand.nextInt(16) - 8);
         if(this.theVillage.func_179866_a(var3) && SpawnerAnimals.canCreatureTypeSpawnAtLocation(EntityLiving$SpawnPlacementType.ON_GROUND, this.worldObj, var3)) {
            return new Vec3((double)var3.getX(), (double)var3.getY(), (double)var3.getZ());
         }
      }

      return null;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
