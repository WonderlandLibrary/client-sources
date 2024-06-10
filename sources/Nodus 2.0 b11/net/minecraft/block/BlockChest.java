/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Random;
/*   6:    */ import net.minecraft.block.material.Material;
/*   7:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   8:    */ import net.minecraft.creativetab.CreativeTabs;
/*   9:    */ import net.minecraft.entity.EntityLivingBase;
/*  10:    */ import net.minecraft.entity.item.EntityItem;
/*  11:    */ import net.minecraft.entity.passive.EntityOcelot;
/*  12:    */ import net.minecraft.entity.player.EntityPlayer;
/*  13:    */ import net.minecraft.inventory.Container;
/*  14:    */ import net.minecraft.inventory.IInventory;
/*  15:    */ import net.minecraft.inventory.InventoryLargeChest;
/*  16:    */ import net.minecraft.item.ItemStack;
/*  17:    */ import net.minecraft.nbt.NBTTagCompound;
/*  18:    */ import net.minecraft.tileentity.TileEntity;
/*  19:    */ import net.minecraft.tileentity.TileEntityChest;
/*  20:    */ import net.minecraft.util.AABBPool;
/*  21:    */ import net.minecraft.util.AxisAlignedBB;
/*  22:    */ import net.minecraft.util.MathHelper;
/*  23:    */ import net.minecraft.world.IBlockAccess;
/*  24:    */ import net.minecraft.world.World;
/*  25:    */ 
/*  26:    */ public class BlockChest
/*  27:    */   extends BlockContainer
/*  28:    */ {
/*  29: 26 */   private final Random field_149955_b = new Random();
/*  30:    */   public final int field_149956_a;
/*  31:    */   private static final String __OBFID = "CL_00000214";
/*  32:    */   
/*  33:    */   protected BlockChest(int p_i45397_1_)
/*  34:    */   {
/*  35: 32 */     super(Material.wood);
/*  36: 33 */     this.field_149956_a = p_i45397_1_;
/*  37: 34 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  38: 35 */     setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean isOpaqueCube()
/*  42:    */   {
/*  43: 40 */     return false;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean renderAsNormalBlock()
/*  47:    */   {
/*  48: 45 */     return false;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getRenderType()
/*  52:    */   {
/*  53: 53 */     return 22;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  57:    */   {
/*  58: 58 */     if (p_149719_1_.getBlock(p_149719_2_, p_149719_3_, p_149719_4_ - 1) == this) {
/*  59: 60 */       setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
/*  60: 62 */     } else if (p_149719_1_.getBlock(p_149719_2_, p_149719_3_, p_149719_4_ + 1) == this) {
/*  61: 64 */       setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
/*  62: 66 */     } else if (p_149719_1_.getBlock(p_149719_2_ - 1, p_149719_3_, p_149719_4_) == this) {
/*  63: 68 */       setBlockBounds(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*  64: 70 */     } else if (p_149719_1_.getBlock(p_149719_2_ + 1, p_149719_3_, p_149719_4_) == this) {
/*  65: 72 */       setBlockBounds(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
/*  66:    */     } else {
/*  67: 76 */       setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/*  72:    */   {
/*  73: 82 */     super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/*  74: 83 */     func_149954_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/*  75: 84 */     Block var5 = p_149726_1_.getBlock(p_149726_2_, p_149726_3_, p_149726_4_ - 1);
/*  76: 85 */     Block var6 = p_149726_1_.getBlock(p_149726_2_, p_149726_3_, p_149726_4_ + 1);
/*  77: 86 */     Block var7 = p_149726_1_.getBlock(p_149726_2_ - 1, p_149726_3_, p_149726_4_);
/*  78: 87 */     Block var8 = p_149726_1_.getBlock(p_149726_2_ + 1, p_149726_3_, p_149726_4_);
/*  79: 89 */     if (var5 == this) {
/*  80: 91 */       func_149954_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_ - 1);
/*  81:    */     }
/*  82: 94 */     if (var6 == this) {
/*  83: 96 */       func_149954_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_ + 1);
/*  84:    */     }
/*  85: 99 */     if (var7 == this) {
/*  86:101 */       func_149954_e(p_149726_1_, p_149726_2_ - 1, p_149726_3_, p_149726_4_);
/*  87:    */     }
/*  88:104 */     if (var8 == this) {
/*  89:106 */       func_149954_e(p_149726_1_, p_149726_2_ + 1, p_149726_3_, p_149726_4_);
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
/*  94:    */   {
/*  95:115 */     Block var7 = p_149689_1_.getBlock(p_149689_2_, p_149689_3_, p_149689_4_ - 1);
/*  96:116 */     Block var8 = p_149689_1_.getBlock(p_149689_2_, p_149689_3_, p_149689_4_ + 1);
/*  97:117 */     Block var9 = p_149689_1_.getBlock(p_149689_2_ - 1, p_149689_3_, p_149689_4_);
/*  98:118 */     Block var10 = p_149689_1_.getBlock(p_149689_2_ + 1, p_149689_3_, p_149689_4_);
/*  99:119 */     byte var11 = 0;
/* 100:120 */     int var12 = MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
/* 101:122 */     if (var12 == 0) {
/* 102:124 */       var11 = 2;
/* 103:    */     }
/* 104:127 */     if (var12 == 1) {
/* 105:129 */       var11 = 5;
/* 106:    */     }
/* 107:132 */     if (var12 == 2) {
/* 108:134 */       var11 = 3;
/* 109:    */     }
/* 110:137 */     if (var12 == 3) {
/* 111:139 */       var11 = 4;
/* 112:    */     }
/* 113:142 */     if ((var7 != this) && (var8 != this) && (var9 != this) && (var10 != this))
/* 114:    */     {
/* 115:144 */       p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var11, 3);
/* 116:    */     }
/* 117:    */     else
/* 118:    */     {
/* 119:148 */       if (((var7 == this) || (var8 == this)) && ((var11 == 4) || (var11 == 5)))
/* 120:    */       {
/* 121:150 */         if (var7 == this) {
/* 122:152 */           p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_ - 1, var11, 3);
/* 123:    */         } else {
/* 124:156 */           p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_ + 1, var11, 3);
/* 125:    */         }
/* 126:159 */         p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var11, 3);
/* 127:    */       }
/* 128:162 */       if (((var9 == this) || (var10 == this)) && ((var11 == 2) || (var11 == 3)))
/* 129:    */       {
/* 130:164 */         if (var9 == this) {
/* 131:166 */           p_149689_1_.setBlockMetadataWithNotify(p_149689_2_ - 1, p_149689_3_, p_149689_4_, var11, 3);
/* 132:    */         } else {
/* 133:170 */           p_149689_1_.setBlockMetadataWithNotify(p_149689_2_ + 1, p_149689_3_, p_149689_4_, var11, 3);
/* 134:    */         }
/* 135:173 */         p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var11, 3);
/* 136:    */       }
/* 137:    */     }
/* 138:177 */     if (p_149689_6_.hasDisplayName()) {
/* 139:179 */       ((TileEntityChest)p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_)).func_145976_a(p_149689_6_.getDisplayName());
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void func_149954_e(World p_149954_1_, int p_149954_2_, int p_149954_3_, int p_149954_4_)
/* 144:    */   {
/* 145:185 */     if (!p_149954_1_.isClient)
/* 146:    */     {
/* 147:187 */       Block var5 = p_149954_1_.getBlock(p_149954_2_, p_149954_3_, p_149954_4_ - 1);
/* 148:188 */       Block var6 = p_149954_1_.getBlock(p_149954_2_, p_149954_3_, p_149954_4_ + 1);
/* 149:189 */       Block var7 = p_149954_1_.getBlock(p_149954_2_ - 1, p_149954_3_, p_149954_4_);
/* 150:190 */       Block var8 = p_149954_1_.getBlock(p_149954_2_ + 1, p_149954_3_, p_149954_4_);
/* 151:191 */       boolean var9 = true;
/* 152:    */       byte var15;
/* 153:200 */       if ((var5 != this) && (var6 != this))
/* 154:    */       {
/* 155:202 */         if ((var7 != this) && (var8 != this))
/* 156:    */         {
/* 157:204 */           byte var15 = 3;
/* 158:206 */           if ((var5.func_149730_j()) && (!var6.func_149730_j())) {
/* 159:208 */             var15 = 3;
/* 160:    */           }
/* 161:211 */           if ((var6.func_149730_j()) && (!var5.func_149730_j())) {
/* 162:213 */             var15 = 2;
/* 163:    */           }
/* 164:216 */           if ((var7.func_149730_j()) && (!var8.func_149730_j())) {
/* 165:218 */             var15 = 5;
/* 166:    */           }
/* 167:221 */           if ((var8.func_149730_j()) && (!var7.func_149730_j())) {
/* 168:223 */             var15 = 4;
/* 169:    */           }
/* 170:    */         }
/* 171:    */         else
/* 172:    */         {
/* 173:228 */           int var10 = var7 == this ? p_149954_2_ - 1 : p_149954_2_ + 1;
/* 174:229 */           Block var11 = p_149954_1_.getBlock(var10, p_149954_3_, p_149954_4_ - 1);
/* 175:230 */           int var12 = var7 == this ? p_149954_2_ - 1 : p_149954_2_ + 1;
/* 176:231 */           Block var13 = p_149954_1_.getBlock(var12, p_149954_3_, p_149954_4_ + 1);
/* 177:232 */           byte var15 = 3;
/* 178:233 */           boolean var14 = true;
/* 179:    */           int var16;
/* 180:    */           int var16;
/* 181:235 */           if (var7 == this) {
/* 182:237 */             var16 = p_149954_1_.getBlockMetadata(p_149954_2_ - 1, p_149954_3_, p_149954_4_);
/* 183:    */           } else {
/* 184:241 */             var16 = p_149954_1_.getBlockMetadata(p_149954_2_ + 1, p_149954_3_, p_149954_4_);
/* 185:    */           }
/* 186:244 */           if (var16 == 2) {
/* 187:246 */             var15 = 2;
/* 188:    */           }
/* 189:249 */           if (((var5.func_149730_j()) || (var11.func_149730_j())) && (!var6.func_149730_j()) && (!var13.func_149730_j())) {
/* 190:251 */             var15 = 3;
/* 191:    */           }
/* 192:254 */           if (((var6.func_149730_j()) || (var13.func_149730_j())) && (!var5.func_149730_j()) && (!var11.func_149730_j())) {
/* 193:256 */             var15 = 2;
/* 194:    */           }
/* 195:    */         }
/* 196:    */       }
/* 197:    */       else
/* 198:    */       {
/* 199:262 */         int var10 = var5 == this ? p_149954_4_ - 1 : p_149954_4_ + 1;
/* 200:263 */         Block var11 = p_149954_1_.getBlock(p_149954_2_ - 1, p_149954_3_, var10);
/* 201:264 */         int var12 = var5 == this ? p_149954_4_ - 1 : p_149954_4_ + 1;
/* 202:265 */         Block var13 = p_149954_1_.getBlock(p_149954_2_ + 1, p_149954_3_, var12);
/* 203:266 */         var15 = 5;
/* 204:267 */         boolean var14 = true;
/* 205:    */         int var16;
/* 206:    */         int var16;
/* 207:269 */         if (var5 == this) {
/* 208:271 */           var16 = p_149954_1_.getBlockMetadata(p_149954_2_, p_149954_3_, p_149954_4_ - 1);
/* 209:    */         } else {
/* 210:275 */           var16 = p_149954_1_.getBlockMetadata(p_149954_2_, p_149954_3_, p_149954_4_ + 1);
/* 211:    */         }
/* 212:278 */         if (var16 == 4) {
/* 213:280 */           var15 = 4;
/* 214:    */         }
/* 215:283 */         if (((var7.func_149730_j()) || (var11.func_149730_j())) && (!var8.func_149730_j()) && (!var13.func_149730_j())) {
/* 216:285 */           var15 = 5;
/* 217:    */         }
/* 218:288 */         if (((var8.func_149730_j()) || (var13.func_149730_j())) && (!var7.func_149730_j()) && (!var11.func_149730_j())) {
/* 219:290 */           var15 = 4;
/* 220:    */         }
/* 221:    */       }
/* 222:294 */       p_149954_1_.setBlockMetadataWithNotify(p_149954_2_, p_149954_3_, p_149954_4_, var15, 3);
/* 223:    */     }
/* 224:    */   }
/* 225:    */   
/* 226:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/* 227:    */   {
/* 228:300 */     int var5 = 0;
/* 229:302 */     if (p_149742_1_.getBlock(p_149742_2_ - 1, p_149742_3_, p_149742_4_) == this) {
/* 230:304 */       var5++;
/* 231:    */     }
/* 232:307 */     if (p_149742_1_.getBlock(p_149742_2_ + 1, p_149742_3_, p_149742_4_) == this) {
/* 233:309 */       var5++;
/* 234:    */     }
/* 235:312 */     if (p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_ - 1) == this) {
/* 236:314 */       var5++;
/* 237:    */     }
/* 238:317 */     if (p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_ + 1) == this) {
/* 239:319 */       var5++;
/* 240:    */     }
/* 241:322 */     return var5 <= 1;
/* 242:    */   }
/* 243:    */   
/* 244:    */   private boolean func_149952_n(World p_149952_1_, int p_149952_2_, int p_149952_3_, int p_149952_4_)
/* 245:    */   {
/* 246:327 */     return p_149952_1_.getBlock(p_149952_2_, p_149952_3_, p_149952_4_) == this;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 250:    */   {
/* 251:332 */     super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
/* 252:333 */     TileEntityChest var6 = (TileEntityChest)p_149695_1_.getTileEntity(p_149695_2_, p_149695_3_, p_149695_4_);
/* 253:335 */     if (var6 != null) {
/* 254:337 */       var6.updateContainingBlockInfo();
/* 255:    */     }
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/* 259:    */   {
/* 260:343 */     TileEntityChest var7 = (TileEntityChest)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
/* 261:345 */     if (var7 != null)
/* 262:    */     {
/* 263:347 */       for (int var8 = 0; var8 < var7.getSizeInventory(); var8++)
/* 264:    */       {
/* 265:349 */         ItemStack var9 = var7.getStackInSlot(var8);
/* 266:351 */         if (var9 != null)
/* 267:    */         {
/* 268:353 */           float var10 = this.field_149955_b.nextFloat() * 0.8F + 0.1F;
/* 269:354 */           float var11 = this.field_149955_b.nextFloat() * 0.8F + 0.1F;
/* 270:    */           EntityItem var14;
/* 271:357 */           for (float var12 = this.field_149955_b.nextFloat() * 0.8F + 0.1F; var9.stackSize > 0; p_149749_1_.spawnEntityInWorld(var14))
/* 272:    */           {
/* 273:359 */             int var13 = this.field_149955_b.nextInt(21) + 10;
/* 274:361 */             if (var13 > var9.stackSize) {
/* 275:363 */               var13 = var9.stackSize;
/* 276:    */             }
/* 277:366 */             var9.stackSize -= var13;
/* 278:367 */             var14 = new EntityItem(p_149749_1_, p_149749_2_ + var10, p_149749_3_ + var11, p_149749_4_ + var12, new ItemStack(var9.getItem(), var13, var9.getItemDamage()));
/* 279:368 */             float var15 = 0.05F;
/* 280:369 */             var14.motionX = ((float)this.field_149955_b.nextGaussian() * var15);
/* 281:370 */             var14.motionY = ((float)this.field_149955_b.nextGaussian() * var15 + 0.2F);
/* 282:371 */             var14.motionZ = ((float)this.field_149955_b.nextGaussian() * var15);
/* 283:373 */             if (var9.hasTagCompound()) {
/* 284:375 */               var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
/* 285:    */             }
/* 286:    */           }
/* 287:    */         }
/* 288:    */       }
/* 289:381 */       p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
/* 290:    */     }
/* 291:384 */     super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/* 292:    */   }
/* 293:    */   
/* 294:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/* 295:    */   {
/* 296:392 */     if (p_149727_1_.isClient) {
/* 297:394 */       return true;
/* 298:    */     }
/* 299:398 */     IInventory var10 = func_149951_m(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
/* 300:400 */     if (var10 != null) {
/* 301:402 */       p_149727_5_.displayGUIChest(var10);
/* 302:    */     }
/* 303:405 */     return true;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public IInventory func_149951_m(World p_149951_1_, int p_149951_2_, int p_149951_3_, int p_149951_4_)
/* 307:    */   {
/* 308:411 */     Object var5 = (TileEntityChest)p_149951_1_.getTileEntity(p_149951_2_, p_149951_3_, p_149951_4_);
/* 309:413 */     if (var5 == null) {
/* 310:415 */       return null;
/* 311:    */     }
/* 312:417 */     if (p_149951_1_.getBlock(p_149951_2_, p_149951_3_ + 1, p_149951_4_).isNormalCube()) {
/* 313:419 */       return null;
/* 314:    */     }
/* 315:421 */     if (func_149953_o(p_149951_1_, p_149951_2_, p_149951_3_, p_149951_4_)) {
/* 316:423 */       return null;
/* 317:    */     }
/* 318:425 */     if ((p_149951_1_.getBlock(p_149951_2_ - 1, p_149951_3_, p_149951_4_) == this) && ((p_149951_1_.getBlock(p_149951_2_ - 1, p_149951_3_ + 1, p_149951_4_).isNormalCube()) || (func_149953_o(p_149951_1_, p_149951_2_ - 1, p_149951_3_, p_149951_4_)))) {
/* 319:427 */       return null;
/* 320:    */     }
/* 321:429 */     if ((p_149951_1_.getBlock(p_149951_2_ + 1, p_149951_3_, p_149951_4_) == this) && ((p_149951_1_.getBlock(p_149951_2_ + 1, p_149951_3_ + 1, p_149951_4_).isNormalCube()) || (func_149953_o(p_149951_1_, p_149951_2_ + 1, p_149951_3_, p_149951_4_)))) {
/* 322:431 */       return null;
/* 323:    */     }
/* 324:433 */     if ((p_149951_1_.getBlock(p_149951_2_, p_149951_3_, p_149951_4_ - 1) == this) && ((p_149951_1_.getBlock(p_149951_2_, p_149951_3_ + 1, p_149951_4_ - 1).isNormalCube()) || (func_149953_o(p_149951_1_, p_149951_2_, p_149951_3_, p_149951_4_ - 1)))) {
/* 325:435 */       return null;
/* 326:    */     }
/* 327:437 */     if ((p_149951_1_.getBlock(p_149951_2_, p_149951_3_, p_149951_4_ + 1) == this) && ((p_149951_1_.getBlock(p_149951_2_, p_149951_3_ + 1, p_149951_4_ + 1).isNormalCube()) || (func_149953_o(p_149951_1_, p_149951_2_, p_149951_3_, p_149951_4_ + 1)))) {
/* 328:439 */       return null;
/* 329:    */     }
/* 330:443 */     if (p_149951_1_.getBlock(p_149951_2_ - 1, p_149951_3_, p_149951_4_) == this) {
/* 331:445 */       var5 = new InventoryLargeChest("container.chestDouble", (TileEntityChest)p_149951_1_.getTileEntity(p_149951_2_ - 1, p_149951_3_, p_149951_4_), (IInventory)var5);
/* 332:    */     }
/* 333:448 */     if (p_149951_1_.getBlock(p_149951_2_ + 1, p_149951_3_, p_149951_4_) == this) {
/* 334:450 */       var5 = new InventoryLargeChest("container.chestDouble", (IInventory)var5, (TileEntityChest)p_149951_1_.getTileEntity(p_149951_2_ + 1, p_149951_3_, p_149951_4_));
/* 335:    */     }
/* 336:453 */     if (p_149951_1_.getBlock(p_149951_2_, p_149951_3_, p_149951_4_ - 1) == this) {
/* 337:455 */       var5 = new InventoryLargeChest("container.chestDouble", (TileEntityChest)p_149951_1_.getTileEntity(p_149951_2_, p_149951_3_, p_149951_4_ - 1), (IInventory)var5);
/* 338:    */     }
/* 339:458 */     if (p_149951_1_.getBlock(p_149951_2_, p_149951_3_, p_149951_4_ + 1) == this) {
/* 340:460 */       var5 = new InventoryLargeChest("container.chestDouble", (IInventory)var5, (TileEntityChest)p_149951_1_.getTileEntity(p_149951_2_, p_149951_3_, p_149951_4_ + 1));
/* 341:    */     }
/* 342:463 */     return (IInventory)var5;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
/* 346:    */   {
/* 347:472 */     TileEntityChest var3 = new TileEntityChest();
/* 348:473 */     return var3;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public boolean canProvidePower()
/* 352:    */   {
/* 353:481 */     return this.field_149956_a == 1;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_)
/* 357:    */   {
/* 358:486 */     if (!canProvidePower()) {
/* 359:488 */       return 0;
/* 360:    */     }
/* 361:492 */     int var6 = ((TileEntityChest)p_149709_1_.getTileEntity(p_149709_2_, p_149709_3_, p_149709_4_)).field_145987_o;
/* 362:493 */     return MathHelper.clamp_int(var6, 0, 15);
/* 363:    */   }
/* 364:    */   
/* 365:    */   public int isProvidingStrongPower(IBlockAccess p_149748_1_, int p_149748_2_, int p_149748_3_, int p_149748_4_, int p_149748_5_)
/* 366:    */   {
/* 367:499 */     return p_149748_5_ == 1 ? isProvidingWeakPower(p_149748_1_, p_149748_2_, p_149748_3_, p_149748_4_, p_149748_5_) : 0;
/* 368:    */   }
/* 369:    */   
/* 370:    */   private static boolean func_149953_o(World p_149953_0_, int p_149953_1_, int p_149953_2_, int p_149953_3_)
/* 371:    */   {
/* 372:504 */     Iterator var4 = p_149953_0_.getEntitiesWithinAABB(EntityOcelot.class, AxisAlignedBB.getAABBPool().getAABB(p_149953_1_, p_149953_2_ + 1, p_149953_3_, p_149953_1_ + 1, p_149953_2_ + 2, p_149953_3_ + 1)).iterator();
/* 373:    */     EntityOcelot var6;
/* 374:    */     do
/* 375:    */     {
/* 376:509 */       if (!var4.hasNext()) {
/* 377:511 */         return false;
/* 378:    */       }
/* 379:514 */       EntityOcelot var5 = (EntityOcelot)var4.next();
/* 380:515 */       var6 = var5;
/* 381:517 */     } while (!var6.isSitting());
/* 382:519 */     return true;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public boolean hasComparatorInputOverride()
/* 386:    */   {
/* 387:524 */     return true;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public int getComparatorInputOverride(World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_)
/* 391:    */   {
/* 392:529 */     return Container.calcRedstoneFromInventory(func_149951_m(p_149736_1_, p_149736_2_, p_149736_3_, p_149736_4_));
/* 393:    */   }
/* 394:    */   
/* 395:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 396:    */   {
/* 397:534 */     this.blockIcon = p_149651_1_.registerIcon("planks_oak");
/* 398:    */   }
/* 399:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockChest
 * JD-Core Version:    0.7.0.1
 */