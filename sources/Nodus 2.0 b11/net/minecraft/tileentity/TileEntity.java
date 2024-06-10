/*   1:    */ package net.minecraft.tileentity;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Map;
/*   5:    */ import java.util.concurrent.Callable;
/*   6:    */ import net.minecraft.block.Block;
/*   7:    */ import net.minecraft.block.BlockJukebox.TileEntityJukebox;
/*   8:    */ import net.minecraft.crash.CrashReportCategory;
/*   9:    */ import net.minecraft.init.Blocks;
/*  10:    */ import net.minecraft.nbt.NBTTagCompound;
/*  11:    */ import net.minecraft.network.Packet;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ import org.apache.logging.log4j.LogManager;
/*  14:    */ import org.apache.logging.log4j.Logger;
/*  15:    */ 
/*  16:    */ public class TileEntity
/*  17:    */ {
/*  18: 18 */   private static final Logger logger = ;
/*  19: 23 */   private static Map nameToClassMap = new HashMap();
/*  20: 28 */   private static Map classToNameMap = new HashMap();
/*  21:    */   protected World worldObj;
/*  22:    */   public int field_145851_c;
/*  23:    */   public int field_145848_d;
/*  24:    */   public int field_145849_e;
/*  25:    */   protected boolean tileEntityInvalid;
/*  26: 36 */   public int blockMetadata = -1;
/*  27:    */   public Block blockType;
/*  28:    */   private static final String __OBFID = "CL_00000340";
/*  29:    */   
/*  30:    */   private static void func_145826_a(Class p_145826_0_, String p_145826_1_)
/*  31:    */   {
/*  32: 44 */     if (nameToClassMap.containsKey(p_145826_1_)) {
/*  33: 46 */       throw new IllegalArgumentException("Duplicate id: " + p_145826_1_);
/*  34:    */     }
/*  35: 50 */     nameToClassMap.put(p_145826_1_, p_145826_0_);
/*  36: 51 */     classToNameMap.put(p_145826_0_, p_145826_1_);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public World getWorldObj()
/*  40:    */   {
/*  41: 60 */     return this.worldObj;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setWorldObj(World p_145834_1_)
/*  45:    */   {
/*  46: 68 */     this.worldObj = p_145834_1_;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean hasWorldObj()
/*  50:    */   {
/*  51: 76 */     return this.worldObj != null;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void readFromNBT(NBTTagCompound p_145839_1_)
/*  55:    */   {
/*  56: 81 */     this.field_145851_c = p_145839_1_.getInteger("x");
/*  57: 82 */     this.field_145848_d = p_145839_1_.getInteger("y");
/*  58: 83 */     this.field_145849_e = p_145839_1_.getInteger("z");
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void writeToNBT(NBTTagCompound p_145841_1_)
/*  62:    */   {
/*  63: 88 */     String var2 = (String)classToNameMap.get(getClass());
/*  64: 90 */     if (var2 == null) {
/*  65: 92 */       throw new RuntimeException(getClass() + " is missing a mapping! This is a bug!");
/*  66:    */     }
/*  67: 96 */     p_145841_1_.setString("id", var2);
/*  68: 97 */     p_145841_1_.setInteger("x", this.field_145851_c);
/*  69: 98 */     p_145841_1_.setInteger("y", this.field_145848_d);
/*  70: 99 */     p_145841_1_.setInteger("z", this.field_145849_e);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static TileEntity createAndLoadEntity(NBTTagCompound p_145827_0_)
/*  74:    */   {
/*  75:110 */     TileEntity var1 = null;
/*  76:    */     try
/*  77:    */     {
/*  78:114 */       Class var2 = (Class)nameToClassMap.get(p_145827_0_.getString("id"));
/*  79:116 */       if (var2 != null) {
/*  80:118 */         var1 = (TileEntity)var2.newInstance();
/*  81:    */       }
/*  82:    */     }
/*  83:    */     catch (Exception var3)
/*  84:    */     {
/*  85:123 */       var3.printStackTrace();
/*  86:    */     }
/*  87:126 */     if (var1 != null) {
/*  88:128 */       var1.readFromNBT(p_145827_0_);
/*  89:    */     } else {
/*  90:132 */       logger.warn("Skipping BlockEntity with id " + p_145827_0_.getString("id"));
/*  91:    */     }
/*  92:135 */     return var1;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getBlockMetadata()
/*  96:    */   {
/*  97:140 */     if (this.blockMetadata == -1) {
/*  98:142 */       this.blockMetadata = this.worldObj.getBlockMetadata(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*  99:    */     }
/* 100:145 */     return this.blockMetadata;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void onInventoryChanged()
/* 104:    */   {
/* 105:153 */     if (this.worldObj != null)
/* 106:    */     {
/* 107:155 */       this.blockMetadata = this.worldObj.getBlockMetadata(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 108:156 */       this.worldObj.func_147476_b(this.field_145851_c, this.field_145848_d, this.field_145849_e, this);
/* 109:158 */       if (getBlockType() != Blocks.air) {
/* 110:160 */         this.worldObj.func_147453_f(this.field_145851_c, this.field_145848_d, this.field_145849_e, getBlockType());
/* 111:    */       }
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public double getDistanceFrom(double p_145835_1_, double p_145835_3_, double p_145835_5_)
/* 116:    */   {
/* 117:170 */     double var7 = this.field_145851_c + 0.5D - p_145835_1_;
/* 118:171 */     double var9 = this.field_145848_d + 0.5D - p_145835_3_;
/* 119:172 */     double var11 = this.field_145849_e + 0.5D - p_145835_5_;
/* 120:173 */     return var7 * var7 + var9 * var9 + var11 * var11;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public double getMaxRenderDistanceSquared()
/* 124:    */   {
/* 125:178 */     return 4096.0D;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Block getBlockType()
/* 129:    */   {
/* 130:186 */     if (this.blockType == null) {
/* 131:188 */       this.blockType = this.worldObj.getBlock(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 132:    */     }
/* 133:191 */     return this.blockType;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Packet getDescriptionPacket()
/* 137:    */   {
/* 138:199 */     return null;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public boolean isInvalid()
/* 142:    */   {
/* 143:204 */     return this.tileEntityInvalid;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void invalidate()
/* 147:    */   {
/* 148:212 */     this.tileEntityInvalid = true;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void validate()
/* 152:    */   {
/* 153:220 */     this.tileEntityInvalid = false;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public boolean receiveClientEvent(int p_145842_1_, int p_145842_2_)
/* 157:    */   {
/* 158:225 */     return false;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void updateContainingBlockInfo()
/* 162:    */   {
/* 163:230 */     this.blockType = null;
/* 164:231 */     this.blockMetadata = -1;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void func_145828_a(CrashReportCategory p_145828_1_)
/* 168:    */   {
/* 169:236 */     p_145828_1_.addCrashSectionCallable("Name", new Callable()
/* 170:    */     {
/* 171:    */       private static final String __OBFID = "CL_00000341";
/* 172:    */       
/* 173:    */       public String call()
/* 174:    */       {
/* 175:241 */         return (String)TileEntity.classToNameMap.get(TileEntity.this.getClass()) + " // " + TileEntity.this.getClass().getCanonicalName();
/* 176:    */       }
/* 177:243 */     });
/* 178:244 */     CrashReportCategory.func_147153_a(p_145828_1_, this.field_145851_c, this.field_145848_d, this.field_145849_e, getBlockType(), getBlockMetadata());
/* 179:245 */     p_145828_1_.addCrashSectionCallable("Actual block type", new Callable()
/* 180:    */     {
/* 181:    */       private static final String __OBFID = "CL_00000343";
/* 182:    */       
/* 183:    */       public String call()
/* 184:    */       {
/* 185:250 */         int var1 = Block.getIdFromBlock(TileEntity.this.worldObj.getBlock(TileEntity.this.field_145851_c, TileEntity.this.field_145848_d, TileEntity.this.field_145849_e));
/* 186:    */         try
/* 187:    */         {
/* 188:254 */           return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(var1), Block.getBlockById(var1).getUnlocalizedName(), Block.getBlockById(var1).getClass().getCanonicalName() });
/* 189:    */         }
/* 190:    */         catch (Throwable var3) {}
/* 191:258 */         return "ID #" + var1;
/* 192:    */       }
/* 193:261 */     });
/* 194:262 */     p_145828_1_.addCrashSectionCallable("Actual block data value", new Callable()
/* 195:    */     {
/* 196:    */       private static final String __OBFID = "CL_00000344";
/* 197:    */       
/* 198:    */       public String call()
/* 199:    */       {
/* 200:267 */         int var1 = TileEntity.this.worldObj.getBlockMetadata(TileEntity.this.field_145851_c, TileEntity.this.field_145848_d, TileEntity.this.field_145849_e);
/* 201:269 */         if (var1 < 0) {
/* 202:271 */           return "Unknown? (Got " + var1 + ")";
/* 203:    */         }
/* 204:275 */         String var2 = String.format("%4s", new Object[] { Integer.toBinaryString(var1) }).replace(" ", "0");
/* 205:276 */         return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[] { Integer.valueOf(var1), var2 });
/* 206:    */       }
/* 207:    */     });
/* 208:    */   }
/* 209:    */   
/* 210:    */   static
/* 211:    */   {
/* 212:284 */     func_145826_a(TileEntityFurnace.class, "Furnace");
/* 213:285 */     func_145826_a(TileEntityChest.class, "Chest");
/* 214:286 */     func_145826_a(TileEntityEnderChest.class, "EnderChest");
/* 215:287 */     func_145826_a(BlockJukebox.TileEntityJukebox.class, "RecordPlayer");
/* 216:288 */     func_145826_a(TileEntityDispenser.class, "Trap");
/* 217:289 */     func_145826_a(TileEntityDropper.class, "Dropper");
/* 218:290 */     func_145826_a(TileEntitySign.class, "Sign");
/* 219:291 */     func_145826_a(TileEntityMobSpawner.class, "MobSpawner");
/* 220:292 */     func_145826_a(TileEntityNote.class, "Music");
/* 221:293 */     func_145826_a(TileEntityPiston.class, "Piston");
/* 222:294 */     func_145826_a(TileEntityBrewingStand.class, "Cauldron");
/* 223:295 */     func_145826_a(TileEntityEnchantmentTable.class, "EnchantTable");
/* 224:296 */     func_145826_a(TileEntityEndPortal.class, "Airportal");
/* 225:297 */     func_145826_a(TileEntityCommandBlock.class, "Control");
/* 226:298 */     func_145826_a(TileEntityBeacon.class, "Beacon");
/* 227:299 */     func_145826_a(TileEntitySkull.class, "Skull");
/* 228:300 */     func_145826_a(TileEntityDaylightDetector.class, "DLDetector");
/* 229:301 */     func_145826_a(TileEntityHopper.class, "Hopper");
/* 230:302 */     func_145826_a(TileEntityComparator.class, "Comparator");
/* 231:303 */     func_145826_a(TileEntityFlowerPot.class, "FlowerPot");
/* 232:    */   }
/* 233:    */   
/* 234:    */   public void updateEntity() {}
/* 235:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.tileentity.TileEntity
 * JD-Core Version:    0.7.0.1
 */