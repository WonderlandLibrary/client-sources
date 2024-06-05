package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

public abstract class MobSpawnerBaseLogic {
   private Entity cachedEntity;
   private static final String __OBFID = "CL_00000129";
   private int spawnDelay = 20;
   private int maxNearbyEntities = 6;
   private int maxSpawnDelay = 800;
   private final List minecartToSpawn = Lists.newArrayList();
   private double field_98284_d;
   private int minSpawnDelay = 200;
   private int activatingRangeFromPlayer = 16;
   private MobSpawnerBaseLogic.WeightedRandomMinecart randomEntity;
   private int spawnRange = 4;
   private double field_98287_c;
   private int spawnCount = 4;
   private String mobID = "Pig";

   public Entity func_180612_a(World var1) {
      if (this.cachedEntity == null) {
         Entity var2 = EntityList.createEntityByName(this.getEntityNameToSpawn(), var1);
         if (var2 != null) {
            var2 = this.func_180613_a(var2, false);
            this.cachedEntity = var2;
         }
      }

      return this.cachedEntity;
   }

   public boolean setDelayToMin(int var1) {
      if (var1 == 1 && this.getSpawnerWorld().isRemote) {
         this.spawnDelay = this.minSpawnDelay;
         return true;
      } else {
         return false;
      }
   }

   public void readFromNBT(NBTTagCompound var1) {
      this.mobID = var1.getString("EntityId");
      this.spawnDelay = var1.getShort("Delay");
      this.minecartToSpawn.clear();
      if (var1.hasKey("SpawnPotentials", 9)) {
         NBTTagList var2 = var1.getTagList("SpawnPotentials", 10);

         for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
            this.minecartToSpawn.add(new MobSpawnerBaseLogic.WeightedRandomMinecart(this, var2.getCompoundTagAt(var3)));
         }
      }

      if (var1.hasKey("SpawnData", 10)) {
         this.setRandomEntity(new MobSpawnerBaseLogic.WeightedRandomMinecart(this, var1.getCompoundTag("SpawnData"), this.mobID));
      } else {
         this.setRandomEntity((MobSpawnerBaseLogic.WeightedRandomMinecart)null);
      }

      if (var1.hasKey("MinSpawnDelay", 99)) {
         this.minSpawnDelay = var1.getShort("MinSpawnDelay");
         this.maxSpawnDelay = var1.getShort("MaxSpawnDelay");
         this.spawnCount = var1.getShort("SpawnCount");
      }

      if (var1.hasKey("MaxNearbyEntities", 99)) {
         this.maxNearbyEntities = var1.getShort("MaxNearbyEntities");
         this.activatingRangeFromPlayer = var1.getShort("RequiredPlayerRange");
      }

      if (var1.hasKey("SpawnRange", 99)) {
         this.spawnRange = var1.getShort("SpawnRange");
      }

      if (this.getSpawnerWorld() != null) {
         this.cachedEntity = null;
      }

   }

   public double func_177223_e() {
      return this.field_98284_d;
   }

   public abstract void func_98267_a(int var1);

   public void setRandomEntity(MobSpawnerBaseLogic.WeightedRandomMinecart var1) {
      this.randomEntity = var1;
   }

   private MobSpawnerBaseLogic.WeightedRandomMinecart getRandomEntity() {
      return this.randomEntity;
   }

   private Entity func_180613_a(Entity var1, boolean var2) {
      if (this.getRandomEntity() != null) {
         NBTTagCompound var3 = new NBTTagCompound();
         var1.writeToNBTOptional(var3);
         Iterator var4 = MobSpawnerBaseLogic.WeightedRandomMinecart.access$1(this.getRandomEntity()).getKeySet().iterator();

         while(var4.hasNext()) {
            String var5 = (String)var4.next();
            NBTBase var6 = MobSpawnerBaseLogic.WeightedRandomMinecart.access$1(this.getRandomEntity()).getTag(var5);
            var3.setTag(var5, var6.copy());
         }

         var1.readFromNBT(var3);
         if (var1.worldObj != null && var2) {
            var1.worldObj.spawnEntityInWorld(var1);
         }

         NBTTagCompound var12;
         for(Entity var13 = var1; var3.hasKey("Riding", 10); var3 = var12) {
            var12 = var3.getCompoundTag("Riding");
            Entity var7 = EntityList.createEntityByName(var12.getString("id"), var1.worldObj);
            if (var7 != null) {
               NBTTagCompound var8 = new NBTTagCompound();
               var7.writeToNBTOptional(var8);
               Iterator var9 = var12.getKeySet().iterator();

               while(var9.hasNext()) {
                  String var10 = (String)var9.next();
                  NBTBase var11 = var12.getTag(var10);
                  var8.setTag(var10, var11.copy());
               }

               var7.readFromNBT(var8);
               var7.setLocationAndAngles(var13.posX, var13.posY, var13.posZ, var13.rotationYaw, var13.rotationPitch);
               if (var1.worldObj != null && var2) {
                  var1.worldObj.spawnEntityInWorld(var7);
               }

               var13.mountEntity(var7);
            }

            var13 = var7;
         }
      } else if (var1 instanceof EntityLivingBase && var1.worldObj != null && var2) {
         ((EntityLiving)var1).func_180482_a(var1.worldObj.getDifficultyForLocation(new BlockPos(var1)), (IEntityLivingData)null);
         var1.worldObj.spawnEntityInWorld(var1);
      }

      return var1;
   }

   public void writeToNBT(NBTTagCompound var1) {
      var1.setString("EntityId", this.getEntityNameToSpawn());
      var1.setShort("Delay", (short)this.spawnDelay);
      var1.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
      var1.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
      var1.setShort("SpawnCount", (short)this.spawnCount);
      var1.setShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
      var1.setShort("RequiredPlayerRange", (short)this.activatingRangeFromPlayer);
      var1.setShort("SpawnRange", (short)this.spawnRange);
      if (this.getRandomEntity() != null) {
         var1.setTag("SpawnData", MobSpawnerBaseLogic.WeightedRandomMinecart.access$1(this.getRandomEntity()).copy());
      }

      if (this.getRandomEntity() != null || this.minecartToSpawn.size() > 0) {
         NBTTagList var2 = new NBTTagList();
         if (this.minecartToSpawn.size() > 0) {
            Iterator var3 = this.minecartToSpawn.iterator();

            while(var3.hasNext()) {
               MobSpawnerBaseLogic.WeightedRandomMinecart var4 = (MobSpawnerBaseLogic.WeightedRandomMinecart)var3.next();
               var2.appendTag(var4.func_98220_a());
            }
         } else {
            var2.appendTag(this.getRandomEntity().func_98220_a());
         }

         var1.setTag("SpawnPotentials", var2);
      }

   }

   private void resetTimer() {
      if (this.maxSpawnDelay <= this.minSpawnDelay) {
         this.spawnDelay = this.minSpawnDelay;
      } else {
         int var1 = this.maxSpawnDelay - this.minSpawnDelay;
         this.spawnDelay = this.minSpawnDelay + this.getSpawnerWorld().rand.nextInt(var1);
      }

      if (this.minecartToSpawn.size() > 0) {
         this.setRandomEntity((MobSpawnerBaseLogic.WeightedRandomMinecart)WeightedRandom.getRandomItem(this.getSpawnerWorld().rand, this.minecartToSpawn));
      }

      this.func_98267_a(1);
   }

   public abstract World getSpawnerWorld();

   public double func_177222_d() {
      return this.field_98287_c;
   }

   private boolean isActivated() {
      BlockPos var1 = this.func_177221_b();
      return this.getSpawnerWorld().func_175636_b((double)var1.getX() + 0.5D, (double)var1.getY() + 0.5D, (double)var1.getZ() + 0.5D, (double)this.activatingRangeFromPlayer);
   }

   public void setEntityName(String var1) {
      this.mobID = var1;
   }

   private String getEntityNameToSpawn() {
      if (this.getRandomEntity() == null) {
         if (this.mobID.equals("Minecart")) {
            this.mobID = "MinecartRideable";
         }

         return this.mobID;
      } else {
         return MobSpawnerBaseLogic.WeightedRandomMinecart.access$0(this.getRandomEntity());
      }
   }

   public void updateSpawner() {
      if (this.isActivated()) {
         BlockPos var1 = this.func_177221_b();
         double var2;
         if (this.getSpawnerWorld().isRemote) {
            double var13 = (double)((float)var1.getX() + this.getSpawnerWorld().rand.nextFloat());
            double var14 = (double)((float)var1.getY() + this.getSpawnerWorld().rand.nextFloat());
            var2 = (double)((float)var1.getZ() + this.getSpawnerWorld().rand.nextFloat());
            this.getSpawnerWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var13, var14, var2, 0.0D, 0.0D, 0.0D);
            this.getSpawnerWorld().spawnParticle(EnumParticleTypes.FLAME, var13, var14, var2, 0.0D, 0.0D, 0.0D);
            if (this.spawnDelay > 0) {
               --this.spawnDelay;
            }

            this.field_98284_d = this.field_98287_c;
            this.field_98287_c = (this.field_98287_c + (double)(1000.0F / ((float)this.spawnDelay + 200.0F))) % 360.0D;
         } else {
            if (this.spawnDelay == -1) {
               this.resetTimer();
            }

            if (this.spawnDelay > 0) {
               --this.spawnDelay;
               return;
            }

            boolean var4 = false;
            int var5 = 0;

            while(true) {
               if (var5 >= this.spawnCount) {
                  if (var4) {
                     this.resetTimer();
                  }
                  break;
               }

               Entity var6 = EntityList.createEntityByName(this.getEntityNameToSpawn(), this.getSpawnerWorld());
               if (var6 == null) {
                  return;
               }

               int var7 = this.getSpawnerWorld().getEntitiesWithinAABB(var6.getClass(), (new AxisAlignedBB((double)var1.getX(), (double)var1.getY(), (double)var1.getZ(), (double)(var1.getX() + 1), (double)(var1.getY() + 1), (double)(var1.getZ() + 1))).expand((double)this.spawnRange, (double)this.spawnRange, (double)this.spawnRange)).size();
               if (var7 >= this.maxNearbyEntities) {
                  this.resetTimer();
                  return;
               }

               var2 = (double)var1.getX() + (this.getSpawnerWorld().rand.nextDouble() - this.getSpawnerWorld().rand.nextDouble()) * (double)this.spawnRange + 0.5D;
               double var8 = (double)(var1.getY() + this.getSpawnerWorld().rand.nextInt(3) - 1);
               double var10 = (double)var1.getZ() + (this.getSpawnerWorld().rand.nextDouble() - this.getSpawnerWorld().rand.nextDouble()) * (double)this.spawnRange + 0.5D;
               EntityLiving var12 = var6 instanceof EntityLiving ? (EntityLiving)var6 : null;
               var6.setLocationAndAngles(var2, var8, var10, this.getSpawnerWorld().rand.nextFloat() * 360.0F, 0.0F);
               if (var12 == null || var12.getCanSpawnHere() && var12.handleLavaMovement()) {
                  this.func_180613_a(var6, true);
                  this.getSpawnerWorld().playAuxSFX(2004, var1, 0);
                  if (var12 != null) {
                     var12.spawnExplosionParticle();
                  }

                  var4 = true;
               }

               ++var5;
            }
         }
      }

   }

   public abstract BlockPos func_177221_b();

   public class WeightedRandomMinecart extends WeightedRandom.Item {
      private final NBTTagCompound field_98222_b;
      private static final String __OBFID = "CL_00000130";
      final MobSpawnerBaseLogic this$0;
      private final String entityType;

      private WeightedRandomMinecart(MobSpawnerBaseLogic var1, NBTTagCompound var2, String var3, int var4) {
         super(var4);
         this.this$0 = var1;
         if (var3.equals("Minecart")) {
            if (var2 != null) {
               var3 = EntityMinecart.EnumMinecartType.func_180038_a(var2.getInteger("Type")).func_180040_b();
            } else {
               var3 = "MinecartRideable";
            }
         }

         this.field_98222_b = var2;
         this.entityType = var3;
      }

      static NBTTagCompound access$1(MobSpawnerBaseLogic.WeightedRandomMinecart var0) {
         return var0.field_98222_b;
      }

      public WeightedRandomMinecart(MobSpawnerBaseLogic var1, NBTTagCompound var2, String var3) {
         this(var1, var2, var3, 1);
      }

      public WeightedRandomMinecart(MobSpawnerBaseLogic var1, NBTTagCompound var2) {
         this(var1, var2.getCompoundTag("Properties"), var2.getString("Type"), var2.getInteger("Weight"));
      }

      static String access$0(MobSpawnerBaseLogic.WeightedRandomMinecart var0) {
         return var0.entityType;
      }

      public NBTTagCompound func_98220_a() {
         NBTTagCompound var1 = new NBTTagCompound();
         var1.setTag("Properties", this.field_98222_b);
         var1.setString("Type", this.entityType);
         var1.setInteger("Weight", this.itemWeight);
         return var1;
      }
   }
}
