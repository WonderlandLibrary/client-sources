/*   1:    */ package net.minecraft.entity.item;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Random;
/*   6:    */ import java.util.Set;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.block.BlockFalling;
/*   9:    */ import net.minecraft.block.ITileEntityProvider;
/*  10:    */ import net.minecraft.block.material.Material;
/*  11:    */ import net.minecraft.crash.CrashReportCategory;
/*  12:    */ import net.minecraft.entity.Entity;
/*  13:    */ import net.minecraft.init.Blocks;
/*  14:    */ import net.minecraft.item.ItemStack;
/*  15:    */ import net.minecraft.nbt.NBTBase;
/*  16:    */ import net.minecraft.nbt.NBTTagCompound;
/*  17:    */ import net.minecraft.tileentity.TileEntity;
/*  18:    */ import net.minecraft.util.DamageSource;
/*  19:    */ import net.minecraft.util.MathHelper;
/*  20:    */ import net.minecraft.world.World;
/*  21:    */ 
/*  22:    */ public class EntityFallingBlock
/*  23:    */   extends Entity
/*  24:    */ {
/*  25:    */   private Block field_145811_e;
/*  26:    */   public int field_145814_a;
/*  27:    */   public int field_145812_b;
/*  28:    */   public boolean field_145813_c;
/*  29:    */   private boolean field_145808_f;
/*  30:    */   private boolean field_145809_g;
/*  31:    */   private int field_145815_h;
/*  32:    */   private float field_145816_i;
/*  33:    */   public NBTTagCompound field_145810_d;
/*  34:    */   private static final String __OBFID = "CL_00001668";
/*  35:    */   
/*  36:    */   public EntityFallingBlock(World par1World)
/*  37:    */   {
/*  38: 35 */     super(par1World);
/*  39: 36 */     this.field_145813_c = true;
/*  40: 37 */     this.field_145815_h = 40;
/*  41: 38 */     this.field_145816_i = 2.0F;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public EntityFallingBlock(World p_i45318_1_, double p_i45318_2_, double p_i45318_4_, double p_i45318_6_, Block p_i45318_8_)
/*  45:    */   {
/*  46: 43 */     this(p_i45318_1_, p_i45318_2_, p_i45318_4_, p_i45318_6_, p_i45318_8_, 0);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public EntityFallingBlock(World p_i45319_1_, double p_i45319_2_, double p_i45319_4_, double p_i45319_6_, Block p_i45319_8_, int p_i45319_9_)
/*  50:    */   {
/*  51: 48 */     super(p_i45319_1_);
/*  52: 49 */     this.field_145813_c = true;
/*  53: 50 */     this.field_145815_h = 40;
/*  54: 51 */     this.field_145816_i = 2.0F;
/*  55: 52 */     this.field_145811_e = p_i45319_8_;
/*  56: 53 */     this.field_145814_a = p_i45319_9_;
/*  57: 54 */     this.preventEntitySpawning = true;
/*  58: 55 */     setSize(0.98F, 0.98F);
/*  59: 56 */     this.yOffset = (this.height / 2.0F);
/*  60: 57 */     setPosition(p_i45319_2_, p_i45319_4_, p_i45319_6_);
/*  61: 58 */     this.motionX = 0.0D;
/*  62: 59 */     this.motionY = 0.0D;
/*  63: 60 */     this.motionZ = 0.0D;
/*  64: 61 */     this.prevPosX = p_i45319_2_;
/*  65: 62 */     this.prevPosY = p_i45319_4_;
/*  66: 63 */     this.prevPosZ = p_i45319_6_;
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected boolean canTriggerWalking()
/*  70:    */   {
/*  71: 72 */     return false;
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected void entityInit() {}
/*  75:    */   
/*  76:    */   public boolean canBeCollidedWith()
/*  77:    */   {
/*  78: 82 */     return !this.isDead;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void onUpdate()
/*  82:    */   {
/*  83: 90 */     if (this.field_145811_e.getMaterial() == Material.air)
/*  84:    */     {
/*  85: 92 */       setDead();
/*  86:    */     }
/*  87:    */     else
/*  88:    */     {
/*  89: 96 */       this.prevPosX = this.posX;
/*  90: 97 */       this.prevPosY = this.posY;
/*  91: 98 */       this.prevPosZ = this.posZ;
/*  92: 99 */       this.field_145812_b += 1;
/*  93:100 */       this.motionY -= 0.03999999910593033D;
/*  94:101 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/*  95:102 */       this.motionX *= 0.9800000190734863D;
/*  96:103 */       this.motionY *= 0.9800000190734863D;
/*  97:104 */       this.motionZ *= 0.9800000190734863D;
/*  98:106 */       if (!this.worldObj.isClient)
/*  99:    */       {
/* 100:108 */         int var1 = MathHelper.floor_double(this.posX);
/* 101:109 */         int var2 = MathHelper.floor_double(this.posY);
/* 102:110 */         int var3 = MathHelper.floor_double(this.posZ);
/* 103:112 */         if (this.field_145812_b == 1)
/* 104:    */         {
/* 105:114 */           if (this.worldObj.getBlock(var1, var2, var3) != this.field_145811_e)
/* 106:    */           {
/* 107:116 */             setDead();
/* 108:117 */             return;
/* 109:    */           }
/* 110:120 */           this.worldObj.setBlockToAir(var1, var2, var3);
/* 111:    */         }
/* 112:123 */         if (this.onGround)
/* 113:    */         {
/* 114:125 */           this.motionX *= 0.699999988079071D;
/* 115:126 */           this.motionZ *= 0.699999988079071D;
/* 116:127 */           this.motionY *= -0.5D;
/* 117:129 */           if (this.worldObj.getBlock(var1, var2, var3) != Blocks.piston_extension)
/* 118:    */           {
/* 119:131 */             setDead();
/* 120:133 */             if ((!this.field_145808_f) && (this.worldObj.canPlaceEntityOnSide(this.field_145811_e, var1, var2, var3, true, 1, null, null)) && (!BlockFalling.func_149831_e(this.worldObj, var1, var2 - 1, var3)) && (this.worldObj.setBlock(var1, var2, var3, this.field_145811_e, this.field_145814_a, 3)))
/* 121:    */             {
/* 122:135 */               if ((this.field_145811_e instanceof BlockFalling)) {
/* 123:137 */                 ((BlockFalling)this.field_145811_e).func_149828_a(this.worldObj, var1, var2, var3, this.field_145814_a);
/* 124:    */               }
/* 125:140 */               if ((this.field_145810_d != null) && ((this.field_145811_e instanceof ITileEntityProvider)))
/* 126:    */               {
/* 127:142 */                 TileEntity var4 = this.worldObj.getTileEntity(var1, var2, var3);
/* 128:144 */                 if (var4 != null)
/* 129:    */                 {
/* 130:146 */                   NBTTagCompound var5 = new NBTTagCompound();
/* 131:147 */                   var4.writeToNBT(var5);
/* 132:148 */                   Iterator var6 = this.field_145810_d.func_150296_c().iterator();
/* 133:150 */                   while (var6.hasNext())
/* 134:    */                   {
/* 135:152 */                     String var7 = (String)var6.next();
/* 136:153 */                     NBTBase var8 = this.field_145810_d.getTag(var7);
/* 137:155 */                     if ((!var7.equals("x")) && (!var7.equals("y")) && (!var7.equals("z"))) {
/* 138:157 */                       var5.setTag(var7, var8.copy());
/* 139:    */                     }
/* 140:    */                   }
/* 141:161 */                   var4.readFromNBT(var5);
/* 142:162 */                   var4.onInventoryChanged();
/* 143:    */                 }
/* 144:    */               }
/* 145:    */             }
/* 146:166 */             else if ((this.field_145813_c) && (!this.field_145808_f))
/* 147:    */             {
/* 148:168 */               entityDropItem(new ItemStack(this.field_145811_e, 1, this.field_145811_e.damageDropped(this.field_145814_a)), 0.0F);
/* 149:    */             }
/* 150:    */           }
/* 151:    */         }
/* 152:172 */         else if (((this.field_145812_b > 100) && (!this.worldObj.isClient) && ((var2 < 1) || (var2 > 256))) || (this.field_145812_b > 600))
/* 153:    */         {
/* 154:174 */           if (this.field_145813_c) {
/* 155:176 */             entityDropItem(new ItemStack(this.field_145811_e, 1, this.field_145811_e.damageDropped(this.field_145814_a)), 0.0F);
/* 156:    */           }
/* 157:179 */           setDead();
/* 158:    */         }
/* 159:    */       }
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   protected void fall(float par1)
/* 164:    */   {
/* 165:190 */     if (this.field_145809_g)
/* 166:    */     {
/* 167:192 */       int var2 = MathHelper.ceiling_float_int(par1 - 1.0F);
/* 168:194 */       if (var2 > 0)
/* 169:    */       {
/* 170:196 */         ArrayList var3 = new ArrayList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox));
/* 171:197 */         boolean var4 = this.field_145811_e == Blocks.anvil;
/* 172:198 */         DamageSource var5 = var4 ? DamageSource.anvil : DamageSource.fallingBlock;
/* 173:199 */         Iterator var6 = var3.iterator();
/* 174:201 */         while (var6.hasNext())
/* 175:    */         {
/* 176:203 */           Entity var7 = (Entity)var6.next();
/* 177:204 */           var7.attackEntityFrom(var5, Math.min(MathHelper.floor_float(var2 * this.field_145816_i), this.field_145815_h));
/* 178:    */         }
/* 179:207 */         if ((var4) && (this.rand.nextFloat() < 0.0500000007450581D + var2 * 0.05D))
/* 180:    */         {
/* 181:209 */           int var8 = this.field_145814_a >> 2;
/* 182:210 */           int var9 = this.field_145814_a & 0x3;
/* 183:211 */           var8++;
/* 184:213 */           if (var8 > 2) {
/* 185:215 */             this.field_145808_f = true;
/* 186:    */           } else {
/* 187:219 */             this.field_145814_a = (var9 | var8 << 2);
/* 188:    */           }
/* 189:    */         }
/* 190:    */       }
/* 191:    */     }
/* 192:    */   }
/* 193:    */   
/* 194:    */   protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/* 195:    */   {
/* 196:231 */     par1NBTTagCompound.setByte("Tile", (byte)Block.getIdFromBlock(this.field_145811_e));
/* 197:232 */     par1NBTTagCompound.setInteger("TileID", Block.getIdFromBlock(this.field_145811_e));
/* 198:233 */     par1NBTTagCompound.setByte("Data", (byte)this.field_145814_a);
/* 199:234 */     par1NBTTagCompound.setByte("Time", (byte)this.field_145812_b);
/* 200:235 */     par1NBTTagCompound.setBoolean("DropItem", this.field_145813_c);
/* 201:236 */     par1NBTTagCompound.setBoolean("HurtEntities", this.field_145809_g);
/* 202:237 */     par1NBTTagCompound.setFloat("FallHurtAmount", this.field_145816_i);
/* 203:238 */     par1NBTTagCompound.setInteger("FallHurtMax", this.field_145815_h);
/* 204:240 */     if (this.field_145810_d != null) {
/* 205:242 */       par1NBTTagCompound.setTag("TileEntityData", this.field_145810_d);
/* 206:    */     }
/* 207:    */   }
/* 208:    */   
/* 209:    */   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/* 210:    */   {
/* 211:251 */     if (par1NBTTagCompound.func_150297_b("TileID", 99)) {
/* 212:253 */       this.field_145811_e = Block.getBlockById(par1NBTTagCompound.getInteger("TileID"));
/* 213:    */     } else {
/* 214:257 */       this.field_145811_e = Block.getBlockById(par1NBTTagCompound.getByte("Tile") & 0xFF);
/* 215:    */     }
/* 216:260 */     this.field_145814_a = (par1NBTTagCompound.getByte("Data") & 0xFF);
/* 217:261 */     this.field_145812_b = (par1NBTTagCompound.getByte("Time") & 0xFF);
/* 218:263 */     if (par1NBTTagCompound.func_150297_b("HurtEntities", 99))
/* 219:    */     {
/* 220:265 */       this.field_145809_g = par1NBTTagCompound.getBoolean("HurtEntities");
/* 221:266 */       this.field_145816_i = par1NBTTagCompound.getFloat("FallHurtAmount");
/* 222:267 */       this.field_145815_h = par1NBTTagCompound.getInteger("FallHurtMax");
/* 223:    */     }
/* 224:269 */     else if (this.field_145811_e == Blocks.anvil)
/* 225:    */     {
/* 226:271 */       this.field_145809_g = true;
/* 227:    */     }
/* 228:274 */     if (par1NBTTagCompound.func_150297_b("DropItem", 99)) {
/* 229:276 */       this.field_145813_c = par1NBTTagCompound.getBoolean("DropItem");
/* 230:    */     }
/* 231:279 */     if (par1NBTTagCompound.func_150297_b("TileEntityData", 10)) {
/* 232:281 */       this.field_145810_d = par1NBTTagCompound.getCompoundTag("TileEntityData");
/* 233:    */     }
/* 234:284 */     if (this.field_145811_e.getMaterial() == Material.air) {
/* 235:286 */       this.field_145811_e = Blocks.sand;
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   public float getShadowSize()
/* 240:    */   {
/* 241:292 */     return 0.0F;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public World func_145807_e()
/* 245:    */   {
/* 246:297 */     return this.worldObj;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void func_145806_a(boolean p_145806_1_)
/* 250:    */   {
/* 251:302 */     this.field_145809_g = p_145806_1_;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public boolean canRenderOnFire()
/* 255:    */   {
/* 256:310 */     return false;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void addEntityCrashInfo(CrashReportCategory par1CrashReportCategory)
/* 260:    */   {
/* 261:315 */     super.addEntityCrashInfo(par1CrashReportCategory);
/* 262:316 */     par1CrashReportCategory.addCrashSection("Immitating block ID", Integer.valueOf(Block.getIdFromBlock(this.field_145811_e)));
/* 263:317 */     par1CrashReportCategory.addCrashSection("Immitating block data", Integer.valueOf(this.field_145814_a));
/* 264:    */   }
/* 265:    */   
/* 266:    */   public Block func_145805_f()
/* 267:    */   {
/* 268:322 */     return this.field_145811_e;
/* 269:    */   }
/* 270:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.item.EntityFallingBlock
 * JD-Core Version:    0.7.0.1
 */