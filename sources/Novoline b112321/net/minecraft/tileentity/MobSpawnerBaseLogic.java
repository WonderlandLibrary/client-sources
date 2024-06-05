package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.List;
import net.mk;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StringUtils;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

public abstract class MobSpawnerBaseLogic {
   private int spawnDelay = 20;
   private String mobID = "Pig";
   private final List minecartToSpawn = Lists.newArrayList();
   private mk m;
   private double mobRotation;
   private double prevMobRotation;
   private int minSpawnDelay = 200;
   private int maxSpawnDelay = 800;
   private int spawnCount = 4;
   private Entity cachedEntity;
   private int maxNearbyEntities = 6;
   private int activatingRangeFromPlayer = 16;
   private int spawnRange = 4;

   private String getEntityNameToSpawn() {
      if(this.b() == null) {
         if(this.mobID != null && this.mobID.equals("Minecart")) {
            this.mobID = "MinecartRideable";
         }

         return this.mobID;
      } else {
         return mk.a(this.b());
      }
   }

   public void setEntityName(String var1) {
      this.mobID = var1;
   }

   private boolean isActivated() {
      BlockPos var1 = this.getSpawnerPosition();
      return this.getSpawnerWorld().isAnyPlayerWithinRangeAt((double)var1.getX() + 0.5D, (double)var1.getY() + 0.5D, (double)var1.getZ() + 0.5D, (double)this.activatingRangeFromPlayer);
   }

   public void updateSpawner() {
      if(this.isActivated()) {
         BlockPos var1 = this.getSpawnerPosition();
         if(this.getSpawnerWorld().isRemote) {
            double var2 = (double)((float)var1.getX() + this.getSpawnerWorld().rand.nextFloat());
            double var4 = (double)((float)var1.getY() + this.getSpawnerWorld().rand.nextFloat());
            double var6 = (double)((float)var1.getZ() + this.getSpawnerWorld().rand.nextFloat());
            this.getSpawnerWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var2, var4, var6, 0.0D, 0.0D, 0.0D, new int[0]);
            this.getSpawnerWorld().spawnParticle(EnumParticleTypes.FLAME, var2, var4, var6, 0.0D, 0.0D, 0.0D, new int[0]);
            if(this.spawnDelay > 0) {
               --this.spawnDelay;
            }

            this.prevMobRotation = this.mobRotation;
            this.mobRotation = (this.mobRotation + (double)(1000.0F / ((float)this.spawnDelay + 200.0F))) % 360.0D;
         } else {
            if(this.spawnDelay == -1) {
               this.resetTimer();
            }

            if(this.spawnDelay > 0) {
               --this.spawnDelay;
               return;
            }

            boolean var8 = false;
            byte var3 = 0;
            if(var3 < this.spawnCount) {
               Entity var9 = EntityList.createEntityByName(this.getEntityNameToSpawn(), this.getSpawnerWorld());
               return;
            }

            this.resetTimer();
         }
      }

   }

   private Entity spawnNewEntity(Entity var1, boolean var2) {
      if(this.b() != null) {
         NBTTagCompound var3 = new NBTTagCompound();
         var1.writeToNBTOptional(var3);

         for(String var5 : mk.b(this.b()).getKeySet()) {
            NBTBase var6 = mk.b(this.b()).getTag(var5);
            var3.setTag(var5, var6.copy());
         }

         var1.readFromNBT(var3);
         if(var1.worldObj != null) {
            var1.worldObj.spawnEntityInWorld(var1);
         }

         NBTTagCompound var11;
         for(Entity var12 = var1; var3.hasKey("Riding", 10); var3 = var11) {
            var11 = var3.getCompoundTag("Riding");
            Entity var13 = EntityList.createEntityByName(var11.getString("id"), var1.worldObj);
            NBTTagCompound var7 = new NBTTagCompound();
            var13.writeToNBTOptional(var7);

            for(String var9 : var11.getKeySet()) {
               NBTBase var10 = var11.getTag(var9);
               var7.setTag(var9, var10.copy());
            }

            var13.readFromNBT(var7);
            var13.setLocationAndAngles(var12.posX, var12.posY, var12.posZ, var12.rotationYaw, var12.rotationPitch);
            if(var1.worldObj != null) {
               var1.worldObj.spawnEntityInWorld(var13);
            }

            var12.mountEntity(var13);
            var12 = var13;
         }
      } else if(var1 instanceof EntityLivingBase && var1.worldObj != null) {
         if(var1 instanceof EntityLiving) {
            ((EntityLiving)var1).onInitialSpawn(var1.worldObj.getDifficultyForLocation(new BlockPos(var1)), (IEntityLivingData)null);
         }

         var1.worldObj.spawnEntityInWorld(var1);
      }

      return var1;
   }

   private void resetTimer() {
      if(this.maxSpawnDelay <= this.minSpawnDelay) {
         this.spawnDelay = this.minSpawnDelay;
      } else {
         int var1 = this.maxSpawnDelay - this.minSpawnDelay;
         this.spawnDelay = this.minSpawnDelay + this.getSpawnerWorld().rand.nextInt(var1);
      }

      if(!this.minecartToSpawn.isEmpty()) {
         this.a((mk)WeightedRandom.getRandomItem(this.getSpawnerWorld().rand, this.minecartToSpawn));
      }

      this.func_98267_a(1);
   }

   public void readFromNBT(NBTTagCompound var1) {
      this.mobID = var1.getString("EntityId");
      this.spawnDelay = var1.getShort("Delay");
      this.minecartToSpawn.clear();
      if(var1.hasKey("SpawnPotentials", 9)) {
         NBTTagList var2 = var1.getTagList("SpawnPotentials", 10);

         for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
            this.minecartToSpawn.add(new mk(this, var2.getCompoundTagAt(var3)));
         }
      }

      if(var1.hasKey("SpawnData", 10)) {
         this.a(new mk(this, var1.getCompoundTag("SpawnData"), this.mobID));
      } else {
         this.a((mk)null);
      }

      if(var1.hasKey("MinSpawnDelay", 99)) {
         this.minSpawnDelay = var1.getShort("MinSpawnDelay");
         this.maxSpawnDelay = var1.getShort("MaxSpawnDelay");
         this.spawnCount = var1.getShort("SpawnCount");
      }

      if(var1.hasKey("MaxNearbyEntities", 99)) {
         this.maxNearbyEntities = var1.getShort("MaxNearbyEntities");
         this.activatingRangeFromPlayer = var1.getShort("RequiredPlayerRange");
      }

      if(var1.hasKey("SpawnRange", 99)) {
         this.spawnRange = var1.getShort("SpawnRange");
      }

      if(this.getSpawnerWorld() != null) {
         this.cachedEntity = null;
      }

   }

   public void writeToNBT(NBTTagCompound var1) {
      String var2 = this.getEntityNameToSpawn();
      if(!StringUtils.isNullOrEmpty(var2)) {
         var1.setString("EntityId", var2);
         var1.setShort("Delay", (short)this.spawnDelay);
         var1.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
         var1.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
         var1.setShort("SpawnCount", (short)this.spawnCount);
         var1.setShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
         var1.setShort("RequiredPlayerRange", (short)this.activatingRangeFromPlayer);
         var1.setShort("SpawnRange", (short)this.spawnRange);
         if(this.b() != null) {
            var1.setTag("SpawnData", mk.b(this.b()).copy());
         }

         if(this.b() != null || !this.minecartToSpawn.isEmpty()) {
            NBTTagList var3 = new NBTTagList();
            if(!this.minecartToSpawn.isEmpty()) {
               for(mk var5 : this.minecartToSpawn) {
                  var3.appendTag(var5.a());
               }
            } else {
               var3.appendTag(this.b().a());
            }

            var1.setTag("SpawnPotentials", var3);
         }
      }

   }

   public Entity func_180612_a(World var1) {
      if(this.cachedEntity == null) {
         Entity var2 = EntityList.createEntityByName(this.getEntityNameToSpawn(), var1);
         var2 = this.spawnNewEntity(var2, false);
         this.cachedEntity = var2;
      }

      return this.cachedEntity;
   }

   public boolean setDelayToMin(int var1) {
      if(var1 == 1 && this.getSpawnerWorld().isRemote) {
         this.spawnDelay = this.minSpawnDelay;
         return true;
      } else {
         return false;
      }
   }

   private mk b() {
      return this.m;
   }

   public void a(mk var1) {
      this.m = var1;
   }

   public abstract void func_98267_a(int var1);

   public abstract World getSpawnerWorld();

   public abstract BlockPos getSpawnerPosition();

   public double getMobRotation() {
      return this.mobRotation;
   }

   public double getPrevMobRotation() {
      return this.prevMobRotation;
   }
}
