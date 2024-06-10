/*   1:    */ package net.minecraft.world;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Random;
/*  10:    */ import net.minecraft.block.Block;
/*  11:    */ import net.minecraft.block.material.Material;
/*  12:    */ import net.minecraft.enchantment.EnchantmentProtection;
/*  13:    */ import net.minecraft.entity.Entity;
/*  14:    */ import net.minecraft.entity.EntityLivingBase;
/*  15:    */ import net.minecraft.entity.item.EntityTNTPrimed;
/*  16:    */ import net.minecraft.entity.player.EntityPlayer;
/*  17:    */ import net.minecraft.init.Blocks;
/*  18:    */ import net.minecraft.util.AABBPool;
/*  19:    */ import net.minecraft.util.AxisAlignedBB;
/*  20:    */ import net.minecraft.util.DamageSource;
/*  21:    */ import net.minecraft.util.MathHelper;
/*  22:    */ import net.minecraft.util.Vec3;
/*  23:    */ import net.minecraft.util.Vec3Pool;
/*  24:    */ 
/*  25:    */ public class Explosion
/*  26:    */ {
/*  27:    */   public boolean isFlaming;
/*  28: 29 */   public boolean isSmoking = true;
/*  29: 30 */   private int field_77289_h = 16;
/*  30: 31 */   private Random explosionRNG = new Random();
/*  31:    */   private World worldObj;
/*  32:    */   public double explosionX;
/*  33:    */   public double explosionY;
/*  34:    */   public double explosionZ;
/*  35:    */   public Entity exploder;
/*  36:    */   public float explosionSize;
/*  37: 40 */   public List affectedBlockPositions = new ArrayList();
/*  38: 41 */   private Map field_77288_k = new HashMap();
/*  39:    */   private static final String __OBFID = "CL_00000134";
/*  40:    */   
/*  41:    */   public Explosion(World par1World, Entity par2Entity, double par3, double par5, double par7, float par9)
/*  42:    */   {
/*  43: 46 */     this.worldObj = par1World;
/*  44: 47 */     this.exploder = par2Entity;
/*  45: 48 */     this.explosionSize = par9;
/*  46: 49 */     this.explosionX = par3;
/*  47: 50 */     this.explosionY = par5;
/*  48: 51 */     this.explosionZ = par7;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void doExplosionA()
/*  52:    */   {
/*  53: 59 */     float var1 = this.explosionSize;
/*  54: 60 */     HashSet var2 = new HashSet();
/*  55: 68 */     for (int var3 = 0; var3 < this.field_77289_h; var3++) {
/*  56: 70 */       for (int var4 = 0; var4 < this.field_77289_h; var4++) {
/*  57: 72 */         for (int var5 = 0; var5 < this.field_77289_h; var5++) {
/*  58: 74 */           if ((var3 == 0) || (var3 == this.field_77289_h - 1) || (var4 == 0) || (var4 == this.field_77289_h - 1) || (var5 == 0) || (var5 == this.field_77289_h - 1))
/*  59:    */           {
/*  60: 76 */             double var6 = var3 / (this.field_77289_h - 1.0F) * 2.0F - 1.0F;
/*  61: 77 */             double var8 = var4 / (this.field_77289_h - 1.0F) * 2.0F - 1.0F;
/*  62: 78 */             double var10 = var5 / (this.field_77289_h - 1.0F) * 2.0F - 1.0F;
/*  63: 79 */             double var12 = Math.sqrt(var6 * var6 + var8 * var8 + var10 * var10);
/*  64: 80 */             var6 /= var12;
/*  65: 81 */             var8 /= var12;
/*  66: 82 */             var10 /= var12;
/*  67: 83 */             float var14 = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
/*  68: 84 */             double var15 = this.explosionX;
/*  69: 85 */             double var17 = this.explosionY;
/*  70: 86 */             double var19 = this.explosionZ;
/*  71: 88 */             for (float var21 = 0.3F; var14 > 0.0F; var14 -= var21 * 0.75F)
/*  72:    */             {
/*  73: 90 */               int var22 = MathHelper.floor_double(var15);
/*  74: 91 */               int var23 = MathHelper.floor_double(var17);
/*  75: 92 */               int var24 = MathHelper.floor_double(var19);
/*  76: 93 */               Block var25 = this.worldObj.getBlock(var22, var23, var24);
/*  77: 95 */               if (var25.getMaterial() != Material.air)
/*  78:    */               {
/*  79: 97 */                 float var26 = this.exploder != null ? this.exploder.func_145772_a(this, this.worldObj, var22, var23, var24, var25) : var25.getExplosionResistance(this.exploder);
/*  80: 98 */                 var14 -= (var26 + 0.3F) * var21;
/*  81:    */               }
/*  82:101 */               if ((var14 > 0.0F) && ((this.exploder == null) || (this.exploder.func_145774_a(this, this.worldObj, var22, var23, var24, var25, var14)))) {
/*  83:103 */                 var2.add(new ChunkPosition(var22, var23, var24));
/*  84:    */               }
/*  85:106 */               var15 += var6 * var21;
/*  86:107 */               var17 += var8 * var21;
/*  87:108 */               var19 += var10 * var21;
/*  88:    */             }
/*  89:    */           }
/*  90:    */         }
/*  91:    */       }
/*  92:    */     }
/*  93:115 */     this.affectedBlockPositions.addAll(var2);
/*  94:116 */     this.explosionSize *= 2.0F;
/*  95:117 */     var3 = MathHelper.floor_double(this.explosionX - this.explosionSize - 1.0D);
/*  96:118 */     int var4 = MathHelper.floor_double(this.explosionX + this.explosionSize + 1.0D);
/*  97:119 */     int var5 = MathHelper.floor_double(this.explosionY - this.explosionSize - 1.0D);
/*  98:120 */     int var29 = MathHelper.floor_double(this.explosionY + this.explosionSize + 1.0D);
/*  99:121 */     int var7 = MathHelper.floor_double(this.explosionZ - this.explosionSize - 1.0D);
/* 100:122 */     int var30 = MathHelper.floor_double(this.explosionZ + this.explosionSize + 1.0D);
/* 101:123 */     List var9 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, AxisAlignedBB.getAABBPool().getAABB(var3, var5, var7, var4, var29, var30));
/* 102:124 */     Vec3 var31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.explosionX, this.explosionY, this.explosionZ);
/* 103:126 */     for (int var11 = 0; var11 < var9.size(); var11++)
/* 104:    */     {
/* 105:128 */       Entity var32 = (Entity)var9.get(var11);
/* 106:129 */       double var13 = var32.getDistance(this.explosionX, this.explosionY, this.explosionZ) / this.explosionSize;
/* 107:131 */       if (var13 <= 1.0D)
/* 108:    */       {
/* 109:133 */         double var15 = var32.posX - this.explosionX;
/* 110:134 */         double var17 = var32.posY + var32.getEyeHeight() - this.explosionY;
/* 111:135 */         double var19 = var32.posZ - this.explosionZ;
/* 112:136 */         double var34 = MathHelper.sqrt_double(var15 * var15 + var17 * var17 + var19 * var19);
/* 113:138 */         if (var34 != 0.0D)
/* 114:    */         {
/* 115:140 */           var15 /= var34;
/* 116:141 */           var17 /= var34;
/* 117:142 */           var19 /= var34;
/* 118:143 */           double var33 = this.worldObj.getBlockDensity(var31, var32.boundingBox);
/* 119:144 */           double var35 = (1.0D - var13) * var33;
/* 120:145 */           var32.attackEntityFrom(DamageSource.setExplosionSource(this), (int)((var35 * var35 + var35) / 2.0D * 8.0D * this.explosionSize + 1.0D));
/* 121:146 */           double var27 = EnchantmentProtection.func_92092_a(var32, var35);
/* 122:147 */           var32.motionX += var15 * var27;
/* 123:148 */           var32.motionY += var17 * var27;
/* 124:149 */           var32.motionZ += var19 * var27;
/* 125:151 */           if ((var32 instanceof EntityPlayer)) {
/* 126:153 */             this.field_77288_k.put((EntityPlayer)var32, this.worldObj.getWorldVec3Pool().getVecFromPool(var15 * var35, var17 * var35, var19 * var35));
/* 127:    */           }
/* 128:    */         }
/* 129:    */       }
/* 130:    */     }
/* 131:159 */     this.explosionSize = var1;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void doExplosionB(boolean par1)
/* 135:    */   {
/* 136:167 */     this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
/* 137:169 */     if ((this.explosionSize >= 2.0F) && (this.isSmoking)) {
/* 138:171 */       this.worldObj.spawnParticle("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
/* 139:    */     } else {
/* 140:175 */       this.worldObj.spawnParticle("largeexplode", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
/* 141:    */     }
/* 142:185 */     if (this.isSmoking)
/* 143:    */     {
/* 144:187 */       Iterator var2 = this.affectedBlockPositions.iterator();
/* 145:189 */       while (var2.hasNext())
/* 146:    */       {
/* 147:191 */         ChunkPosition var3 = (ChunkPosition)var2.next();
/* 148:192 */         int var4 = var3.field_151329_a;
/* 149:193 */         int var5 = var3.field_151327_b;
/* 150:194 */         int var6 = var3.field_151328_c;
/* 151:195 */         Block var7 = this.worldObj.getBlock(var4, var5, var6);
/* 152:197 */         if (par1)
/* 153:    */         {
/* 154:199 */           double var8 = var4 + this.worldObj.rand.nextFloat();
/* 155:200 */           double var10 = var5 + this.worldObj.rand.nextFloat();
/* 156:201 */           double var12 = var6 + this.worldObj.rand.nextFloat();
/* 157:202 */           double var14 = var8 - this.explosionX;
/* 158:203 */           double var16 = var10 - this.explosionY;
/* 159:204 */           double var18 = var12 - this.explosionZ;
/* 160:205 */           double var20 = MathHelper.sqrt_double(var14 * var14 + var16 * var16 + var18 * var18);
/* 161:206 */           var14 /= var20;
/* 162:207 */           var16 /= var20;
/* 163:208 */           var18 /= var20;
/* 164:209 */           double var22 = 0.5D / (var20 / this.explosionSize + 0.1D);
/* 165:210 */           var22 *= (this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
/* 166:211 */           var14 *= var22;
/* 167:212 */           var16 *= var22;
/* 168:213 */           var18 *= var22;
/* 169:214 */           this.worldObj.spawnParticle("explode", (var8 + this.explosionX * 1.0D) / 2.0D, (var10 + this.explosionY * 1.0D) / 2.0D, (var12 + this.explosionZ * 1.0D) / 2.0D, var14, var16, var18);
/* 170:215 */           this.worldObj.spawnParticle("smoke", var8, var10, var12, var14, var16, var18);
/* 171:    */         }
/* 172:218 */         if (var7.getMaterial() != Material.air)
/* 173:    */         {
/* 174:220 */           if (var7.canDropFromExplosion(this)) {
/* 175:222 */             var7.dropBlockAsItemWithChance(this.worldObj, var4, var5, var6, this.worldObj.getBlockMetadata(var4, var5, var6), 1.0F / this.explosionSize, 0);
/* 176:    */           }
/* 177:225 */           this.worldObj.setBlock(var4, var5, var6, Blocks.air, 0, 3);
/* 178:226 */           var7.onBlockDestroyedByExplosion(this.worldObj, var4, var5, var6, this);
/* 179:    */         }
/* 180:    */       }
/* 181:    */     }
/* 182:231 */     if (this.isFlaming)
/* 183:    */     {
/* 184:233 */       Iterator var2 = this.affectedBlockPositions.iterator();
/* 185:235 */       while (var2.hasNext())
/* 186:    */       {
/* 187:237 */         ChunkPosition var3 = (ChunkPosition)var2.next();
/* 188:238 */         int var4 = var3.field_151329_a;
/* 189:239 */         int var5 = var3.field_151327_b;
/* 190:240 */         int var6 = var3.field_151328_c;
/* 191:241 */         Block var7 = this.worldObj.getBlock(var4, var5, var6);
/* 192:242 */         Block var24 = this.worldObj.getBlock(var4, var5 - 1, var6);
/* 193:244 */         if ((var7.getMaterial() == Material.air) && (var24.func_149730_j()) && (this.explosionRNG.nextInt(3) == 0)) {
/* 194:246 */           this.worldObj.setBlock(var4, var5, var6, Blocks.fire);
/* 195:    */         }
/* 196:    */       }
/* 197:    */     }
/* 198:    */   }
/* 199:    */   
/* 200:    */   public Map func_77277_b()
/* 201:    */   {
/* 202:254 */     return this.field_77288_k;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public EntityLivingBase getExplosivePlacedBy()
/* 206:    */   {
/* 207:262 */     return (this.exploder instanceof EntityLivingBase) ? (EntityLivingBase)this.exploder : (this.exploder instanceof EntityTNTPrimed) ? ((EntityTNTPrimed)this.exploder).getTntPlacedBy() : this.exploder == null ? null : null;
/* 208:    */   }
/* 209:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.Explosion
 * JD-Core Version:    0.7.0.1
 */