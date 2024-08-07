/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockJukebox;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TileEntity
/*     */ {
/*     */   public TileEntity() {
/*  26 */     this.pos = BlockPos.ORIGIN;
/*     */     
/*  28 */     this.blockMetadata = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final Logger logger = LogManager.getLogger();
/*     */   private static Map<String, Class<? extends TileEntity>> nameToClassMap = Maps.newHashMap();
/*     */   private static Map<Class<? extends TileEntity>, String> classToNameMap = Maps.newHashMap();
/*     */   protected World worldObj;
/*     */   
/*     */   private static void addMapping(Class<? extends TileEntity> cl, String id) {
/*  38 */     if (nameToClassMap.containsKey(id))
/*     */     {
/*  40 */       throw new IllegalArgumentException("Duplicate id: " + id);
/*     */     }
/*     */ 
/*     */     
/*  44 */     nameToClassMap.put(id, cl);
/*  45 */     classToNameMap.put(cl, id);
/*     */   }
/*     */   
/*     */   protected BlockPos pos;
/*     */   protected boolean tileEntityInvalid;
/*     */   private int blockMetadata;
/*     */   protected Block blockType;
/*     */   
/*     */   public World getWorld() {
/*  54 */     return this.worldObj;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldObj(World worldIn) {
/*  62 */     this.worldObj = worldIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasWorldObj() {
/*  70 */     return (this.worldObj != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  75 */     this.pos = new BlockPos(compound.getInteger("x"), compound.getInteger("y"), compound.getInteger("z"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/*  80 */     String s = classToNameMap.get(getClass());
/*     */     
/*  82 */     if (s == null)
/*     */     {
/*  84 */       throw new RuntimeException(getClass() + " is missing a mapping! This is a bug!");
/*     */     }
/*     */ 
/*     */     
/*  88 */     compound.setString("id", s);
/*  89 */     compound.setInteger("x", this.pos.getX());
/*  90 */     compound.setInteger("y", this.pos.getY());
/*  91 */     compound.setInteger("z", this.pos.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TileEntity createAndLoadEntity(NBTTagCompound nbt) {
/* 100 */     TileEntity tileentity = null;
/*     */ 
/*     */     
/*     */     try {
/* 104 */       Class<? extends TileEntity> oclass = nameToClassMap.get(nbt.getString("id"));
/*     */       
/* 106 */       if (oclass != null)
/*     */       {
/* 108 */         tileentity = oclass.newInstance();
/*     */       }
/*     */     }
/* 111 */     catch (Exception exception) {
/*     */       
/* 113 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 116 */     if (tileentity != null) {
/*     */       
/* 118 */       tileentity.readFromNBT(nbt);
/*     */     }
/*     */     else {
/*     */       
/* 122 */       logger.warn("Skipping BlockEntity with id " + nbt.getString("id"));
/*     */     } 
/*     */     
/* 125 */     return tileentity;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlockMetadata() {
/* 130 */     if (this.blockMetadata == -1) {
/*     */       
/* 132 */       IBlockState iblockstate = this.worldObj.getBlockState(this.pos);
/* 133 */       this.blockMetadata = iblockstate.getBlock().getMetaFromState(iblockstate);
/*     */     } 
/*     */     
/* 136 */     return this.blockMetadata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 145 */     if (this.worldObj != null) {
/*     */       
/* 147 */       IBlockState iblockstate = this.worldObj.getBlockState(this.pos);
/* 148 */       this.blockMetadata = iblockstate.getBlock().getMetaFromState(iblockstate);
/* 149 */       this.worldObj.markChunkDirty(this.pos, this);
/*     */       
/* 151 */       if (getBlockType() != Blocks.air)
/*     */       {
/* 153 */         this.worldObj.updateComparatorOutputLevel(this.pos, getBlockType());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDistanceSq(double x, double y, double z) {
/* 163 */     double d0 = this.pos.getX() + 0.5D - x;
/* 164 */     double d1 = this.pos.getY() + 0.5D - y;
/* 165 */     double d2 = this.pos.getZ() + 0.5D - z;
/* 166 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxRenderDistanceSquared() {
/* 171 */     return 4096.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getPos() {
/* 176 */     return this.pos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block getBlockType() {
/* 184 */     if (this.blockType == null)
/*     */     {
/* 186 */       this.blockType = this.worldObj.getBlockState(this.pos).getBlock();
/*     */     }
/*     */     
/* 189 */     return this.blockType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet getDescriptionPacket() {
/* 198 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInvalid() {
/* 203 */     return this.tileEntityInvalid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidate() {
/* 211 */     this.tileEntityInvalid = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate() {
/* 219 */     this.tileEntityInvalid = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean receiveClientEvent(int id, int type) {
/* 224 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateContainingBlockInfo() {
/* 229 */     this.blockType = null;
/* 230 */     this.blockMetadata = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addInfoToCrashReport(CrashReportCategory reportCategory) {
/* 235 */     reportCategory.addCrashSectionCallable("Name", new Callable<String>()
/*     */         {
/*     */           public String call() throws Exception
/*     */           {
/* 239 */             return String.valueOf(TileEntity.classToNameMap.get(TileEntity.this.getClass())) + " // " + TileEntity.this.getClass().getCanonicalName();
/*     */           }
/*     */         });
/*     */     
/* 243 */     if (this.worldObj != null) {
/*     */       
/* 245 */       CrashReportCategory.addBlockInfo(reportCategory, this.pos, getBlockType(), getBlockMetadata());
/* 246 */       reportCategory.addCrashSectionCallable("Actual block type", new Callable<String>()
/*     */           {
/*     */             public String call() throws Exception
/*     */             {
/* 250 */               int i = Block.getIdFromBlock(TileEntity.this.worldObj.getBlockState(TileEntity.this.pos).getBlock());
/*     */ 
/*     */               
/*     */               try {
/* 254 */                 return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(i), Block.getBlockById(i).getUnlocalizedName(), Block.getBlockById(i).getClass().getCanonicalName() });
/*     */               }
/* 256 */               catch (Throwable var3) {
/*     */                 
/* 258 */                 return "ID #" + i;
/*     */               } 
/*     */             }
/*     */           });
/* 262 */       reportCategory.addCrashSectionCallable("Actual block data value", new Callable<String>()
/*     */           {
/*     */             public String call() throws Exception
/*     */             {
/* 266 */               IBlockState iblockstate = TileEntity.this.worldObj.getBlockState(TileEntity.this.pos);
/* 267 */               int i = iblockstate.getBlock().getMetaFromState(iblockstate);
/*     */               
/* 269 */               if (i < 0)
/*     */               {
/* 271 */                 return "Unknown? (Got " + i + ")";
/*     */               }
/*     */ 
/*     */               
/* 275 */               String s = String.format("%4s", new Object[] { Integer.toBinaryString(i) }).replace(" ", "0");
/* 276 */               return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[] { Integer.valueOf(i), s });
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPos(BlockPos posIn) {
/* 285 */     this.pos = posIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_183000_F() {
/* 290 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 295 */     addMapping((Class)TileEntityFurnace.class, "Furnace");
/* 296 */     addMapping((Class)TileEntityChest.class, "Chest");
/* 297 */     addMapping((Class)TileEntityEnderChest.class, "EnderChest");
/* 298 */     addMapping((Class)BlockJukebox.TileEntityJukebox.class, "RecordPlayer");
/* 299 */     addMapping((Class)TileEntityDispenser.class, "Trap");
/* 300 */     addMapping((Class)TileEntityDropper.class, "Dropper");
/* 301 */     addMapping((Class)TileEntitySign.class, "Sign");
/* 302 */     addMapping((Class)TileEntityMobSpawner.class, "MobSpawner");
/* 303 */     addMapping((Class)TileEntityNote.class, "Music");
/* 304 */     addMapping((Class)TileEntityPiston.class, "Piston");
/* 305 */     addMapping((Class)TileEntityBrewingStand.class, "Cauldron");
/* 306 */     addMapping((Class)TileEntityEnchantmentTable.class, "EnchantTable");
/* 307 */     addMapping((Class)TileEntityEndPortal.class, "Airportal");
/* 308 */     addMapping((Class)TileEntityCommandBlock.class, "Control");
/* 309 */     addMapping((Class)TileEntityBeacon.class, "Beacon");
/* 310 */     addMapping((Class)TileEntitySkull.class, "Skull");
/* 311 */     addMapping((Class)TileEntityDaylightDetector.class, "DLDetector");
/* 312 */     addMapping((Class)TileEntityHopper.class, "Hopper");
/* 313 */     addMapping((Class)TileEntityComparator.class, "Comparator");
/* 314 */     addMapping((Class)TileEntityFlowerPot.class, "FlowerPot");
/* 315 */     addMapping((Class)TileEntityBanner.class, "Banner");
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\tileentity\TileEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */