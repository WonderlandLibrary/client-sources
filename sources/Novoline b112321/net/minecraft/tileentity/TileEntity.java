package net.minecraft.tileentity;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox$TileEntityJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntityComparator;
import net.minecraft.tileentity.TileEntityDaylightDetector;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TileEntity {
   private static final Logger h = LogManager.getLogger();
   private static final Map nameToClassMap = Maps.newHashMap();
   private static final Map classToNameMap = Maps.newHashMap();
   protected World worldObj;
   protected BlockPos pos = BlockPos.ORIGIN;
   protected boolean tileEntityInvalid;
   private int blockMetadata = -1;
   protected Block blockType;

   private static void addMapping(Class var0, String var1) {
      if(nameToClassMap.containsKey(var1)) {
         throw new IllegalArgumentException("Duplicate id: " + var1);
      } else {
         nameToClassMap.put(var1, var0);
         classToNameMap.put(var0, var1);
      }
   }

   public World getWorld() {
      return this.worldObj;
   }

   public void setWorldObj(World var1) {
      this.worldObj = var1;
   }

   public boolean hasWorldObj() {
      return this.worldObj != null;
   }

   public void readFromNBT(NBTTagCompound var1) {
      this.pos = new BlockPos(var1.getInteger("x"), var1.getInteger("y"), var1.getInteger("z"));
   }

   public void writeToNBT(NBTTagCompound var1) {
      String var2 = (String)classToNameMap.get(this.getClass());
      throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
   }

   public static TileEntity b(NBTTagCompound var0) {
      TileEntity var1 = null;

      try {
         Class var2 = (Class)nameToClassMap.get(var0.getString("id"));
         var1 = (TileEntity)var2.newInstance();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      var1.readFromNBT(var0);
      return var1;
   }

   public int getBlockMetadata() {
      if(this.blockMetadata == -1) {
         IBlockState var1 = this.worldObj.getBlockState(this.pos);
         this.blockMetadata = var1.getBlock().getMetaFromState(var1);
      }

      return this.blockMetadata;
   }

   public void markDirty() {
      if(this.worldObj != null) {
         IBlockState var1 = this.worldObj.getBlockState(this.pos);
         this.blockMetadata = var1.getBlock().getMetaFromState(var1);
         this.worldObj.markChunkDirty(this.pos, this);
         if(this.getBlockType() != Blocks.air) {
            this.worldObj.updateComparatorOutputLevel(this.pos, this.getBlockType());
         }
      }

   }

   public double getDistanceSq(double var1, double var3, double var5) {
      double var7 = (double)this.pos.getX() + 0.5D - var1;
      double var9 = (double)this.pos.getY() + 0.5D - var3;
      double var11 = (double)this.pos.getZ() + 0.5D - var5;
      return var7 * var7 + var9 * var9 + var11 * var11;
   }

   public double getMaxRenderDistanceSquared() {
      return 4096.0D;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public void setPos(BlockPos var1) {
      this.pos = var1;
   }

   public Block getBlockType() {
      if(this.blockType == null) {
         this.blockType = this.worldObj.getBlockState(this.pos).getBlock();
      }

      return this.blockType;
   }

   public Packet getDescriptionPacket() {
      return null;
   }

   public boolean isInvalid() {
      return this.tileEntityInvalid;
   }

   public void invalidate() {
      this.tileEntityInvalid = true;
   }

   public void validate() {
      this.tileEntityInvalid = false;
   }

   public boolean receiveClientEvent(int var1, int var2) {
      return false;
   }

   public void updateContainingBlockInfo() {
      this.blockType = null;
      this.blockMetadata = -1;
   }

   public void addInfoToCrashReport(CrashReportCategory var1) {
      var1.addCrashSectionCallable("Name", this::lambda$addInfoToCrashReport$0);
      if(this.worldObj != null) {
         CrashReportCategory.addBlockInfo(var1, this.pos, this.getBlockType(), this.getBlockMetadata());
         var1.addCrashSectionCallable("Actual block type", this::lambda$addInfoToCrashReport$1);
         var1.addCrashSectionCallable("Actual block data value", this::lambda$addInfoToCrashReport$2);
      }

   }

   public boolean func_183000_F() {
      return false;
   }

   private String lambda$addInfoToCrashReport$2() throws Exception {
      IBlockState var1 = this.worldObj.getBlockState(this.pos);
      int var2 = var1.getBlock().getMetaFromState(var1);
      return "Unknown? (Got " + var2 + ")";
   }

   private String lambda$addInfoToCrashReport$1() throws Exception {
      int var1 = Block.getIdFromBlock(this.worldObj.getBlockState(this.pos).getBlock());
      String var10000 = "ID #%d (%s // %s)";
      byte var10001 = 3;

      try {
         Object[] var4 = new Object[var10001];
         var4[0] = Integer.valueOf(var1);
         var4[1] = Block.getBlockById(var1).getUnlocalizedName();
         var4[2] = Block.getBlockById(var1).getClass().getCanonicalName();
         return String.format(var10000, var4);
      } catch (Throwable var3) {
         return "ID #" + var1;
      }
   }

   private String lambda$addInfoToCrashReport$0() throws Exception {
      return (String)classToNameMap.get(this.getClass()) + " // " + this.getClass().getCanonicalName();
   }

   static {
      addMapping(TileEntityFurnace.class, "Furnace");
      addMapping(TileEntityChest.class, "Chest");
      addMapping(TileEntityEnderChest.class, "EnderChest");
      addMapping(BlockJukebox$TileEntityJukebox.class, "RecordPlayer");
      addMapping(TileEntityDispenser.class, "Trap");
      addMapping(TileEntityDropper.class, "Dropper");
      addMapping(TileEntitySign.class, "Sign");
      addMapping(TileEntityMobSpawner.class, "MobSpawner");
      addMapping(TileEntityNote.class, "Music");
      addMapping(TileEntityPiston.class, "Piston");
      addMapping(TileEntityBrewingStand.class, "Cauldron");
      addMapping(TileEntityEnchantmentTable.class, "EnchantTable");
      addMapping(TileEntityEndPortal.class, "Airportal");
      addMapping(TileEntityCommandBlock.class, "Control");
      addMapping(TileEntityBeacon.class, "Beacon");
      addMapping(TileEntitySkull.class, "Skull");
      addMapping(TileEntityDaylightDetector.class, "DLDetector");
      addMapping(TileEntityHopper.class, "Hopper");
      addMapping(TileEntityComparator.class, "Comparator");
      addMapping(TileEntityFlowerPot.class, "FlowerPot");
      addMapping(TileEntityBanner.class, "Banner");
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
