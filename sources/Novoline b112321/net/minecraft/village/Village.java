package net.minecraft.village;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.village.Village$VillageAggressor;
import net.minecraft.village.VillageDoorInfo;
import net.minecraft.world.World;

public class Village {
   private World worldObj;
   private final List villageDoorInfoList = Lists.newArrayList();
   private BlockPos centerHelper = BlockPos.ORIGIN;
   private BlockPos center = BlockPos.ORIGIN;
   private int villageRadius;
   private int lastAddDoorTimestamp;
   private int tickCounter;
   private int numVillagers;
   private int noBreedTicks;
   private TreeMap playerReputation = new TreeMap();
   private List villageAgressors = Lists.newArrayList();
   private int numIronGolems;

   public Village() {
   }

   public Village(World var1) {
      this.worldObj = var1;
   }

   public void setWorld(World var1) {
      this.worldObj = var1;
   }

   public void tick(int var1) {
      this.tickCounter = var1;
      this.removeDeadAndOutOfRangeDoors();
      this.removeDeadAndOldAgressors();
      if(var1 % 20 == 0) {
         this.updateNumVillagers();
      }

      if(var1 % 30 == 0) {
         this.updateNumIronGolems();
      }

      int var2 = this.numVillagers / 10;
      if(this.numIronGolems < var2 && this.villageDoorInfoList.size() > 20 && this.worldObj.rand.nextInt(7000) == 0) {
         Vec3 var3 = this.func_179862_a(this.center, 2, 4, 2);
         EntityIronGolem var4 = new EntityIronGolem(this.worldObj);
         var4.setPosition(var3.xCoord, var3.yCoord, var3.zCoord);
         this.worldObj.spawnEntityInWorld(var4);
         ++this.numIronGolems;
      }

   }

   private Vec3 func_179862_a(BlockPos var1, int var2, int var3, int var4) {
      for(int var5 = 0; var5 < 10; ++var5) {
         BlockPos var6 = var1.a(this.worldObj.rand.nextInt(16) - 8, this.worldObj.rand.nextInt(6) - 3, this.worldObj.rand.nextInt(16) - 8);
         if(this.func_179866_a(var6) && this.func_179861_a(new BlockPos(var2, var3, var4), var6)) {
            return new Vec3((double)var6.getX(), (double)var6.getY(), (double)var6.getZ());
         }
      }

      return null;
   }

   private boolean func_179861_a(BlockPos var1, BlockPos var2) {
      if(!World.doesBlockHaveSolidTopSurface(this.worldObj, var2.down())) {
         return false;
      } else {
         int var3 = var2.getX() - var1.getX() / 2;
         int var4 = var2.getZ() - var1.getZ() / 2;

         for(int var5 = var3; var5 < var3 + var1.getX(); ++var5) {
            for(int var6 = var2.getY(); var6 < var2.getY() + var1.getY(); ++var6) {
               for(int var7 = var4; var7 < var4 + var1.getZ(); ++var7) {
                  if(this.worldObj.getBlockState(new BlockPos(var5, var6, var7)).getBlock().isNormalCube()) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   private void updateNumIronGolems() {
      List var1 = this.worldObj.getEntitiesWithinAABB(EntityIronGolem.class, new AxisAlignedBB((double)(this.center.getX() - this.villageRadius), (double)(this.center.getY() - 4), (double)(this.center.getZ() - this.villageRadius), (double)(this.center.getX() + this.villageRadius), (double)(this.center.getY() + 4), (double)(this.center.getZ() + this.villageRadius)));
      this.numIronGolems = var1.size();
   }

   private void updateNumVillagers() {
      List var1 = this.worldObj.getEntitiesWithinAABB(EntityVillager.class, new AxisAlignedBB((double)(this.center.getX() - this.villageRadius), (double)(this.center.getY() - 4), (double)(this.center.getZ() - this.villageRadius), (double)(this.center.getX() + this.villageRadius), (double)(this.center.getY() + 4), (double)(this.center.getZ() + this.villageRadius)));
      this.numVillagers = var1.size();
      if(this.numVillagers == 0) {
         this.playerReputation.clear();
      }

   }

   public BlockPos getCenter() {
      return this.center;
   }

   public int getVillageRadius() {
      return this.villageRadius;
   }

   public int getNumVillageDoors() {
      return this.villageDoorInfoList.size();
   }

   public int getTicksSinceLastDoorAdding() {
      return this.tickCounter - this.lastAddDoorTimestamp;
   }

   public int getNumVillagers() {
      return this.numVillagers;
   }

   public boolean func_179866_a(BlockPos var1) {
      return this.center.distanceSq(var1) < (double)(this.villageRadius * this.villageRadius);
   }

   public List getVillageDoorInfoList() {
      return this.villageDoorInfoList;
   }

   public VillageDoorInfo getNearestDoor(BlockPos var1) {
      VillageDoorInfo var2 = null;
      int var3 = Integer.MAX_VALUE;

      for(VillageDoorInfo var5 : this.villageDoorInfoList) {
         int var6 = var5.getDistanceToDoorBlockSq(var1);
         if(var6 < var3) {
            var2 = var5;
            var3 = var6;
         }
      }

      return var2;
   }

   public VillageDoorInfo getDoorInfo(BlockPos var1) {
      VillageDoorInfo var2 = null;
      int var3 = Integer.MAX_VALUE;

      for(VillageDoorInfo var5 : this.villageDoorInfoList) {
         int var6 = var5.getDistanceToDoorBlockSq(var1);
         if(var6 > 256) {
            var6 = var6 * 1000;
         } else {
            var6 = var5.getDoorOpeningRestrictionCounter();
         }

         if(var6 < var3) {
            var2 = var5;
            var3 = var6;
         }
      }

      return var2;
   }

   public VillageDoorInfo getExistedDoor(BlockPos var1) {
      if(this.center.distanceSq(var1) > (double)(this.villageRadius * this.villageRadius)) {
         return null;
      } else {
         for(VillageDoorInfo var3 : this.villageDoorInfoList) {
            if(var3.getDoorBlockPos().getX() == var1.getX() && var3.getDoorBlockPos().getZ() == var1.getZ() && Math.abs(var3.getDoorBlockPos().getY() - var1.getY()) <= 1) {
               return var3;
            }
         }

         return null;
      }
   }

   public void addVillageDoorInfo(VillageDoorInfo var1) {
      this.villageDoorInfoList.add(var1);
      this.centerHelper = this.centerHelper.add(var1.getDoorBlockPos());
      this.g();
      this.lastAddDoorTimestamp = var1.getInsidePosY();
   }

   public boolean isAnnihilated() {
      return this.villageDoorInfoList.isEmpty();
   }

   public void addOrRenewAgressor(EntityLivingBase var1) {
      for(Village$VillageAggressor var3 : this.villageAgressors) {
         if(var3.agressor == var1) {
            var3.agressionTime = this.tickCounter;
            return;
         }
      }

      this.villageAgressors.add(new Village$VillageAggressor(this, var1, this.tickCounter));
   }

   public EntityLivingBase findNearestVillageAggressor(EntityLivingBase var1) {
      double var2 = Double.MAX_VALUE;
      Village$VillageAggressor var4 = null;

      for(Village$VillageAggressor var6 : this.villageAgressors) {
         double var8 = var6.agressor.getDistanceSqToEntity(var1);
         if(var8 <= var2) {
            var4 = var6;
            var2 = var8;
         }
      }

      return var4.agressor;
   }

   public EntityPlayer getNearestTargetPlayer(EntityLivingBase var1) {
      double var2 = Double.MAX_VALUE;
      EntityPlayer var4 = null;

      for(String var6 : this.playerReputation.keySet()) {
         if(this.isPlayerReputationTooLow(var6)) {
            EntityPlayer var7 = this.worldObj.getPlayerEntityByName(var6);
            double var8 = var7.getDistanceSqToEntity(var1);
            if(var8 <= var2) {
               var4 = var7;
               var2 = var8;
            }
         }
      }

      return var4;
   }

   private void removeDeadAndOldAgressors() {
      Iterator var1 = this.villageAgressors.iterator();

      while(var1.hasNext()) {
         Village$VillageAggressor var2 = (Village$VillageAggressor)var1.next();
         if(!var2.agressor.isEntityAlive() || Math.abs(this.tickCounter - var2.agressionTime) > 300) {
            var1.remove();
         }
      }

   }

   private void removeDeadAndOutOfRangeDoors() {
      boolean var1 = false;
      boolean var2 = this.worldObj.rand.nextInt(50) == 0;
      Iterator var3 = this.villageDoorInfoList.iterator();

      while(var3.hasNext()) {
         VillageDoorInfo var4 = (VillageDoorInfo)var3.next();
         var4.resetDoorOpeningRestrictionCounter();
         if(!this.isWoodDoor(var4.getDoorBlockPos()) || Math.abs(this.tickCounter - var4.getInsidePosY()) > 1200) {
            this.centerHelper = this.centerHelper.subtract(var4.getDoorBlockPos());
            var1 = true;
            var4.setIsDetachedFromVillageFlag(true);
            var3.remove();
         }
      }

      this.g();
   }

   private boolean isWoodDoor(BlockPos var1) {
      Block var2 = this.worldObj.getBlockState(var1).getBlock();
      return var2 instanceof BlockDoor && var2.getMaterial() == Material.wood;
   }

   private void g() {
      int var1 = this.villageDoorInfoList.size();
      this.center = new BlockPos(0, 0, 0);
      this.villageRadius = 0;
   }

   public int getReputationForPlayer(String var1) {
      Integer var2 = (Integer)this.playerReputation.get(var1);
      return var2.intValue();
   }

   public int setReputationForPlayer(String var1, int var2) {
      int var3 = this.getReputationForPlayer(var1);
      int var4 = MathHelper.clamp_int(var3 + var2, -30, 10);
      this.playerReputation.put(var1, Integer.valueOf(var4));
      return var4;
   }

   public boolean isPlayerReputationTooLow(String var1) {
      return this.getReputationForPlayer(var1) <= -15;
   }

   public void readVillageDataFromNBT(NBTTagCompound var1) {
      this.numVillagers = var1.getInteger("PopSize");
      this.villageRadius = var1.getInteger("Radius");
      this.numIronGolems = var1.getInteger("Golems");
      this.lastAddDoorTimestamp = var1.getInteger("Stable");
      this.tickCounter = var1.getInteger("Tick");
      this.noBreedTicks = var1.getInteger("MTick");
      this.center = new BlockPos(var1.getInteger("CX"), var1.getInteger("CY"), var1.getInteger("CZ"));
      this.centerHelper = new BlockPos(var1.getInteger("ACX"), var1.getInteger("ACY"), var1.getInteger("ACZ"));
      NBTTagList var2 = var1.getTagList("Doors", 10);

      for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
         NBTTagCompound var4 = var2.getCompoundTagAt(var3);
         VillageDoorInfo var5 = new VillageDoorInfo(new BlockPos(var4.getInteger("X"), var4.getInteger("Y"), var4.getInteger("Z")), var4.getInteger("IDX"), var4.getInteger("IDZ"), var4.getInteger("TS"));
         this.villageDoorInfoList.add(var5);
      }

      NBTTagList var8 = var1.getTagList("Players", 10);

      for(int var9 = 0; var9 < var8.tagCount(); ++var9) {
         NBTTagCompound var10 = var8.getCompoundTagAt(var9);
         if(var10.hasKey("UUID")) {
            PlayerProfileCache var6 = MinecraftServer.getServer().getPlayerProfileCache();
            GameProfile var7 = var6.getProfileByUUID(UUID.fromString(var10.getString("UUID")));
            this.playerReputation.put(var7.getName(), Integer.valueOf(var10.getInteger("S")));
         } else {
            this.playerReputation.put(var10.getString("Name"), Integer.valueOf(var10.getInteger("S")));
         }
      }

   }

   public void writeVillageDataToNBT(NBTTagCompound var1) {
      var1.setInteger("PopSize", this.numVillagers);
      var1.setInteger("Radius", this.villageRadius);
      var1.setInteger("Golems", this.numIronGolems);
      var1.setInteger("Stable", this.lastAddDoorTimestamp);
      var1.setInteger("Tick", this.tickCounter);
      var1.setInteger("MTick", this.noBreedTicks);
      var1.setInteger("CX", this.center.getX());
      var1.setInteger("CY", this.center.getY());
      var1.setInteger("CZ", this.center.getZ());
      var1.setInteger("ACX", this.centerHelper.getX());
      var1.setInteger("ACY", this.centerHelper.getY());
      var1.setInteger("ACZ", this.centerHelper.getZ());
      NBTTagList var2 = new NBTTagList();

      for(VillageDoorInfo var4 : this.villageDoorInfoList) {
         NBTTagCompound var5 = new NBTTagCompound();
         var5.setInteger("X", var4.getDoorBlockPos().getX());
         var5.setInteger("Y", var4.getDoorBlockPos().getY());
         var5.setInteger("Z", var4.getDoorBlockPos().getZ());
         var5.setInteger("IDX", var4.getInsideOffsetX());
         var5.setInteger("IDZ", var4.getInsideOffsetZ());
         var5.setInteger("TS", var4.getInsidePosY());
         var2.appendTag(var5);
      }

      var1.setTag("Doors", var2);
      NBTTagList var9 = new NBTTagList();

      for(String var11 : this.playerReputation.keySet()) {
         NBTTagCompound var6 = new NBTTagCompound();
         PlayerProfileCache var7 = MinecraftServer.getServer().getPlayerProfileCache();
         GameProfile var8 = var7.getGameProfileForUsername(var11);
         var6.setString("UUID", var8.getId().toString());
         var6.setInteger("S", ((Integer)this.playerReputation.get(var11)).intValue());
         var9.appendTag(var6);
      }

      var1.setTag("Players", var9);
   }

   public void endMatingSeason() {
      this.noBreedTicks = this.tickCounter;
   }

   public boolean isMatingSeason() {
      return this.noBreedTicks == 0 || this.tickCounter - this.noBreedTicks >= 3600;
   }

   public void setDefaultPlayerReputation(int var1) {
      for(String var3 : this.playerReputation.keySet()) {
         this.setReputationForPlayer(var3, var1);
      }

   }
}
