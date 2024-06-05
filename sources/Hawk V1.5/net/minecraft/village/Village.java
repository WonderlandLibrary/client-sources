package net.minecraft.village;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Village {
   private int tickCounter;
   private int numVillagers;
   private World worldObj;
   private List villageAgressors;
   private TreeMap playerReputation;
   private int noBreedTicks;
   private final List villageDoorInfoList = Lists.newArrayList();
   private BlockPos center;
   private int lastAddDoorTimestamp;
   private BlockPos centerHelper;
   private int villageRadius;
   private static final String __OBFID = "CL_00001631";
   private int numIronGolems;

   public boolean isAnnihilated() {
      return this.villageDoorInfoList.isEmpty();
   }

   public BlockPos func_180608_a() {
      return this.center;
   }

   public List getVillageDoorInfoList() {
      return this.villageDoorInfoList;
   }

   public EntityLivingBase findNearestVillageAggressor(EntityLivingBase var1) {
      double var2 = Double.MAX_VALUE;
      Village.VillageAgressor var4 = null;

      for(int var5 = 0; var5 < this.villageAgressors.size(); ++var5) {
         Village.VillageAgressor var6 = (Village.VillageAgressor)this.villageAgressors.get(var5);
         double var7 = var6.agressor.getDistanceSqToEntity(var1);
         if (var7 <= var2) {
            var4 = var6;
            var2 = var7;
         }
      }

      return var4 != null ? var4.agressor : null;
   }

   public int getNumVillagers() {
      return this.numVillagers;
   }

   private Vec3 func_179862_a(BlockPos var1, int var2, int var3, int var4) {
      for(int var5 = 0; var5 < 10; ++var5) {
         BlockPos var6 = var1.add(this.worldObj.rand.nextInt(16) - 8, this.worldObj.rand.nextInt(6) - 3, this.worldObj.rand.nextInt(16) - 8);
         if (this.func_179866_a(var6) && this.func_179861_a(new BlockPos(var2, var3, var4), var6)) {
            return new Vec3((double)var6.getX(), (double)var6.getY(), (double)var6.getZ());
         }
      }

      return null;
   }

   public VillageDoorInfo func_179864_e(BlockPos var1) {
      if (this.center.distanceSq(var1) > (double)(this.villageRadius * this.villageRadius)) {
         return null;
      } else {
         Iterator var2 = this.villageDoorInfoList.iterator();

         VillageDoorInfo var3;
         do {
            if (!var2.hasNext()) {
               return null;
            }

            var3 = (VillageDoorInfo)var2.next();
         } while(var3.func_179852_d().getX() != var1.getX() || var3.func_179852_d().getZ() != var1.getZ() || Math.abs(var3.func_179852_d().getY() - var1.getY()) > 1);

         return var3;
      }
   }

   private void removeDeadAndOldAgressors() {
      Iterator var1 = this.villageAgressors.iterator();

      while(true) {
         Village.VillageAgressor var2;
         do {
            if (!var1.hasNext()) {
               return;
            }

            var2 = (Village.VillageAgressor)var1.next();
         } while(var2.agressor.isEntityAlive() && Math.abs(this.tickCounter - var2.agressionTime) <= 300);

         var1.remove();
      }
   }

   public void addOrRenewAgressor(EntityLivingBase var1) {
      Iterator var2 = this.villageAgressors.iterator();

      while(var2.hasNext()) {
         Village.VillageAgressor var3 = (Village.VillageAgressor)var2.next();
         if (var3.agressor == var1) {
            var3.agressionTime = this.tickCounter;
            return;
         }
      }

      this.villageAgressors.add(new Village.VillageAgressor(this, var1, this.tickCounter));
   }

   public void endMatingSeason() {
      this.noBreedTicks = this.tickCounter;
   }

   public EntityPlayer func_82685_c(EntityLivingBase var1) {
      double var2 = Double.MAX_VALUE;
      EntityPlayer var4 = null;
      Iterator var5 = this.playerReputation.keySet().iterator();

      while(var5.hasNext()) {
         String var6 = (String)var5.next();
         if (this.isPlayerReputationTooLow(var6)) {
            EntityPlayer var7 = this.worldObj.getPlayerEntityByName(var6);
            if (var7 != null) {
               double var8 = var7.getDistanceSqToEntity(var1);
               if (var8 <= var2) {
                  var4 = var7;
                  var2 = var8;
               }
            }
         }
      }

      return var4;
   }

   public Village(World var1) {
      this.centerHelper = BlockPos.ORIGIN;
      this.center = BlockPos.ORIGIN;
      this.playerReputation = new TreeMap();
      this.villageAgressors = Lists.newArrayList();
      this.worldObj = var1;
   }

   private void updateVillageRadiusAndCenter() {
      int var1 = this.villageDoorInfoList.size();
      if (var1 == 0) {
         this.center = new BlockPos(0, 0, 0);
         this.villageRadius = 0;
      } else {
         this.center = new BlockPos(this.centerHelper.getX() / var1, this.centerHelper.getY() / var1, this.centerHelper.getZ() / var1);
         int var2 = 0;

         VillageDoorInfo var3;
         for(Iterator var4 = this.villageDoorInfoList.iterator(); var4.hasNext(); var2 = Math.max(var3.func_179848_a(this.center), var2)) {
            var3 = (VillageDoorInfo)var4.next();
         }

         this.villageRadius = Math.max(32, (int)Math.sqrt((double)var2) + 1);
      }

   }

   public void tick(int var1) {
      this.tickCounter = var1;
      this.removeDeadAndOutOfRangeDoors();
      this.removeDeadAndOldAgressors();
      if (var1 % 20 == 0) {
         this.updateNumVillagers();
      }

      if (var1 % 30 == 0) {
         this.updateNumIronGolems();
      }

      int var2 = this.numVillagers / 10;
      if (this.numIronGolems < var2 && this.villageDoorInfoList.size() > 20 && this.worldObj.rand.nextInt(7000) == 0) {
         Vec3 var3 = this.func_179862_a(this.center, 2, 4, 2);
         if (var3 != null) {
            EntityIronGolem var4 = new EntityIronGolem(this.worldObj);
            var4.setPosition(var3.xCoord, var3.yCoord, var3.zCoord);
            this.worldObj.spawnEntityInWorld(var4);
            ++this.numIronGolems;
         }
      }

   }

   public boolean func_179866_a(BlockPos var1) {
      return this.center.distanceSq(var1) < (double)(this.villageRadius * this.villageRadius);
   }

   private boolean func_179860_f(BlockPos var1) {
      Block var2 = this.worldObj.getBlockState(var1).getBlock();
      return var2 instanceof BlockDoor ? var2.getMaterial() == Material.wood : false;
   }

   public void addVillageDoorInfo(VillageDoorInfo var1) {
      this.villageDoorInfoList.add(var1);
      this.centerHelper = this.centerHelper.add(var1.func_179852_d());
      this.updateVillageRadiusAndCenter();
      this.lastAddDoorTimestamp = var1.getInsidePosY();
   }

   public int getTicksSinceLastDoorAdding() {
      return this.tickCounter - this.lastAddDoorTimestamp;
   }

   public Village() {
      this.centerHelper = BlockPos.ORIGIN;
      this.center = BlockPos.ORIGIN;
      this.playerReputation = new TreeMap();
      this.villageAgressors = Lists.newArrayList();
   }

   public int setReputationForPlayer(String var1, int var2) {
      int var3 = this.getReputationForPlayer(var1);
      int var4 = MathHelper.clamp_int(var3 + var2, -30, 10);
      this.playerReputation.put(var1, var4);
      return var4;
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
      Iterator var3 = this.villageDoorInfoList.iterator();

      while(var3.hasNext()) {
         VillageDoorInfo var4 = (VillageDoorInfo)var3.next();
         NBTTagCompound var5 = new NBTTagCompound();
         var5.setInteger("X", var4.func_179852_d().getX());
         var5.setInteger("Y", var4.func_179852_d().getY());
         var5.setInteger("Z", var4.func_179852_d().getZ());
         var5.setInteger("IDX", var4.func_179847_f());
         var5.setInteger("IDZ", var4.func_179855_g());
         var5.setInteger("TS", var4.getInsidePosY());
         var2.appendTag(var5);
      }

      var1.setTag("Doors", var2);
      NBTTagList var9 = new NBTTagList();
      Iterator var8 = this.playerReputation.keySet().iterator();

      while(var8.hasNext()) {
         String var6 = (String)var8.next();
         NBTTagCompound var7 = new NBTTagCompound();
         var7.setString("Name", var6);
         var7.setInteger("S", (Integer)this.playerReputation.get(var6));
         var9.appendTag(var7);
      }

      var1.setTag("Players", var9);
   }

   public boolean isMatingSeason() {
      return this.noBreedTicks == 0 || this.tickCounter - this.noBreedTicks >= 3600;
   }

   public VillageDoorInfo func_179865_b(BlockPos var1) {
      VillageDoorInfo var2 = null;
      int var3 = Integer.MAX_VALUE;
      Iterator var4 = this.villageDoorInfoList.iterator();

      while(var4.hasNext()) {
         VillageDoorInfo var5 = (VillageDoorInfo)var4.next();
         int var6 = var5.func_179848_a(var1);
         if (var6 < var3) {
            var2 = var5;
            var3 = var6;
         }
      }

      return var2;
   }

   private void removeDeadAndOutOfRangeDoors() {
      boolean var1 = false;
      boolean var2 = this.worldObj.rand.nextInt(50) == 0;
      Iterator var3 = this.villageDoorInfoList.iterator();

      while(true) {
         VillageDoorInfo var4;
         do {
            if (!var3.hasNext()) {
               if (var1) {
                  this.updateVillageRadiusAndCenter();
               }

               return;
            }

            var4 = (VillageDoorInfo)var3.next();
            if (var2) {
               var4.resetDoorOpeningRestrictionCounter();
            }
         } while(this.func_179860_f(var4.func_179852_d()) && Math.abs(this.tickCounter - var4.getInsidePosY()) <= 1200);

         this.centerHelper = this.centerHelper.add(var4.func_179852_d().multiply(-1));
         var1 = true;
         var4.func_179853_a(true);
         var3.remove();
      }
   }

   public void func_82691_a(World var1) {
      this.worldObj = var1;
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

      NBTTagList var6 = var1.getTagList("Players", 10);

      for(int var7 = 0; var7 < var6.tagCount(); ++var7) {
         NBTTagCompound var8 = var6.getCompoundTagAt(var7);
         this.playerReputation.put(var8.getString("Name"), var8.getInteger("S"));
      }

   }

   private void updateNumIronGolems() {
      List var1 = this.worldObj.getEntitiesWithinAABB(EntityIronGolem.class, new AxisAlignedBB((double)(this.center.getX() - this.villageRadius), (double)(this.center.getY() - 4), (double)(this.center.getZ() - this.villageRadius), (double)(this.center.getX() + this.villageRadius), (double)(this.center.getY() + 4), (double)(this.center.getZ() + this.villageRadius)));
      this.numIronGolems = var1.size();
   }

   private void updateNumVillagers() {
      List var1 = this.worldObj.getEntitiesWithinAABB(EntityVillager.class, new AxisAlignedBB((double)(this.center.getX() - this.villageRadius), (double)(this.center.getY() - 4), (double)(this.center.getZ() - this.villageRadius), (double)(this.center.getX() + this.villageRadius), (double)(this.center.getY() + 4), (double)(this.center.getZ() + this.villageRadius)));
      this.numVillagers = var1.size();
      if (this.numVillagers == 0) {
         this.playerReputation.clear();
      }

   }

   public int getReputationForPlayer(String var1) {
      Integer var2 = (Integer)this.playerReputation.get(var1);
      return var2 != null ? var2 : 0;
   }

   public int getNumVillageDoors() {
      return this.villageDoorInfoList.size();
   }

   public VillageDoorInfo func_179863_c(BlockPos var1) {
      VillageDoorInfo var2 = null;
      int var3 = Integer.MAX_VALUE;
      Iterator var4 = this.villageDoorInfoList.iterator();

      while(var4.hasNext()) {
         VillageDoorInfo var5 = (VillageDoorInfo)var4.next();
         int var6 = var5.func_179848_a(var1);
         if (var6 > 256) {
            var6 *= 1000;
         } else {
            var6 = var5.getDoorOpeningRestrictionCounter();
         }

         if (var6 < var3) {
            var2 = var5;
            var3 = var6;
         }
      }

      return var2;
   }

   public int getVillageRadius() {
      return this.villageRadius;
   }

   private boolean func_179861_a(BlockPos var1, BlockPos var2) {
      if (!World.doesBlockHaveSolidTopSurface(this.worldObj, var2.offsetDown())) {
         return false;
      } else {
         int var3 = var2.getX() - var1.getX() / 2;
         int var4 = var2.getZ() - var1.getZ() / 2;

         for(int var5 = var3; var5 < var3 + var1.getX(); ++var5) {
            for(int var6 = var2.getY(); var6 < var2.getY() + var1.getY(); ++var6) {
               for(int var7 = var4; var7 < var4 + var1.getZ(); ++var7) {
                  if (this.worldObj.getBlockState(new BlockPos(var5, var6, var7)).getBlock().isNormalCube()) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   public void setDefaultPlayerReputation(int var1) {
      Iterator var2 = this.playerReputation.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         this.setReputationForPlayer(var3, var1);
      }

   }

   public boolean isPlayerReputationTooLow(String var1) {
      return this.getReputationForPlayer(var1) <= -15;
   }

   class VillageAgressor {
      public EntityLivingBase agressor;
      final Village this$0;
      public int agressionTime;
      private static final String __OBFID = "CL_00001632";

      VillageAgressor(Village var1, EntityLivingBase var2, int var3) {
         this.this$0 = var1;
         this.agressor = var2;
         this.agressionTime = var3;
      }
   }
}
