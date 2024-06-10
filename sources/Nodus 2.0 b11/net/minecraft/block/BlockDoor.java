/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.IconFlipped;
/*   6:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   7:    */ import net.minecraft.entity.player.EntityPlayer;
/*   8:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*   9:    */ import net.minecraft.init.Items;
/*  10:    */ import net.minecraft.item.Item;
/*  11:    */ import net.minecraft.util.AxisAlignedBB;
/*  12:    */ import net.minecraft.util.IIcon;
/*  13:    */ import net.minecraft.util.MovingObjectPosition;
/*  14:    */ import net.minecraft.util.Vec3;
/*  15:    */ import net.minecraft.world.IBlockAccess;
/*  16:    */ import net.minecraft.world.World;
/*  17:    */ 
/*  18:    */ public class BlockDoor
/*  19:    */   extends Block
/*  20:    */ {
/*  21:    */   private IIcon[] field_150017_a;
/*  22:    */   private IIcon[] field_150016_b;
/*  23:    */   private static final String __OBFID = "CL_00000230";
/*  24:    */   
/*  25:    */   protected BlockDoor(Material p_i45402_1_)
/*  26:    */   {
/*  27: 25 */     super(p_i45402_1_);
/*  28: 26 */     float var2 = 0.5F;
/*  29: 27 */     float var3 = 1.0F;
/*  30: 28 */     setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var3, 0.5F + var2);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  34:    */   {
/*  35: 36 */     return this.field_150016_b[0];
/*  36:    */   }
/*  37:    */   
/*  38:    */   public IIcon getIcon(IBlockAccess p_149673_1_, int p_149673_2_, int p_149673_3_, int p_149673_4_, int p_149673_5_)
/*  39:    */   {
/*  40: 41 */     if ((p_149673_5_ != 1) && (p_149673_5_ != 0))
/*  41:    */     {
/*  42: 43 */       int var6 = func_150012_g(p_149673_1_, p_149673_2_, p_149673_3_, p_149673_4_);
/*  43: 44 */       int var7 = var6 & 0x3;
/*  44: 45 */       boolean var8 = (var6 & 0x4) != 0;
/*  45: 46 */       boolean var9 = false;
/*  46: 47 */       boolean var10 = (var6 & 0x8) != 0;
/*  47: 49 */       if (var8)
/*  48:    */       {
/*  49: 51 */         if ((var7 == 0) && (p_149673_5_ == 2)) {
/*  50: 53 */           var9 = !var9;
/*  51: 55 */         } else if ((var7 == 1) && (p_149673_5_ == 5)) {
/*  52: 57 */           var9 = !var9;
/*  53: 59 */         } else if ((var7 == 2) && (p_149673_5_ == 3)) {
/*  54: 61 */           var9 = !var9;
/*  55: 63 */         } else if ((var7 == 3) && (p_149673_5_ == 4)) {
/*  56: 65 */           var9 = !var9;
/*  57:    */         }
/*  58:    */       }
/*  59:    */       else
/*  60:    */       {
/*  61: 70 */         if ((var7 == 0) && (p_149673_5_ == 5)) {
/*  62: 72 */           var9 = !var9;
/*  63: 74 */         } else if ((var7 == 1) && (p_149673_5_ == 3)) {
/*  64: 76 */           var9 = !var9;
/*  65: 78 */         } else if ((var7 == 2) && (p_149673_5_ == 4)) {
/*  66: 80 */           var9 = !var9;
/*  67: 82 */         } else if ((var7 == 3) && (p_149673_5_ == 2)) {
/*  68: 84 */           var9 = !var9;
/*  69:    */         }
/*  70: 87 */         if ((var6 & 0x10) != 0) {
/*  71: 89 */           var9 = !var9;
/*  72:    */         }
/*  73:    */       }
/*  74: 93 */       return var10 ? this.field_150017_a[0] : this.field_150016_b[0];
/*  75:    */     }
/*  76: 97 */     return this.field_150016_b[0];
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/*  80:    */   {
/*  81:103 */     this.field_150017_a = new IIcon[2];
/*  82:104 */     this.field_150016_b = new IIcon[2];
/*  83:105 */     this.field_150017_a[0] = p_149651_1_.registerIcon(getTextureName() + "_upper");
/*  84:106 */     this.field_150016_b[0] = p_149651_1_.registerIcon(getTextureName() + "_lower");
/*  85:107 */     this.field_150017_a[1] = new IconFlipped(this.field_150017_a[0], true, false);
/*  86:108 */     this.field_150016_b[1] = new IconFlipped(this.field_150016_b[0], true, false);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isOpaqueCube()
/*  90:    */   {
/*  91:113 */     return false;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_)
/*  95:    */   {
/*  96:118 */     int var5 = func_150012_g(p_149655_1_, p_149655_2_, p_149655_3_, p_149655_4_);
/*  97:119 */     return (var5 & 0x4) != 0;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean renderAsNormalBlock()
/* 101:    */   {
/* 102:124 */     return false;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int getRenderType()
/* 106:    */   {
/* 107:132 */     return 7;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
/* 111:    */   {
/* 112:140 */     setBlockBoundsBasedOnState(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
/* 113:141 */     return super.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/* 117:    */   {
/* 118:150 */     setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
/* 119:151 */     return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/* 123:    */   {
/* 124:156 */     func_150011_b(func_150012_g(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_));
/* 125:    */   }
/* 126:    */   
/* 127:    */   public int func_150013_e(IBlockAccess p_150013_1_, int p_150013_2_, int p_150013_3_, int p_150013_4_)
/* 128:    */   {
/* 129:161 */     return func_150012_g(p_150013_1_, p_150013_2_, p_150013_3_, p_150013_4_) & 0x3;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public boolean func_150015_f(IBlockAccess p_150015_1_, int p_150015_2_, int p_150015_3_, int p_150015_4_)
/* 133:    */   {
/* 134:166 */     return (func_150012_g(p_150015_1_, p_150015_2_, p_150015_3_, p_150015_4_) & 0x4) != 0;
/* 135:    */   }
/* 136:    */   
/* 137:    */   private void func_150011_b(int p_150011_1_)
/* 138:    */   {
/* 139:171 */     float var2 = 0.1875F;
/* 140:172 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
/* 141:173 */     int var3 = p_150011_1_ & 0x3;
/* 142:174 */     boolean var4 = (p_150011_1_ & 0x4) != 0;
/* 143:175 */     boolean var5 = (p_150011_1_ & 0x10) != 0;
/* 144:177 */     if (var3 == 0)
/* 145:    */     {
/* 146:179 */       if (var4)
/* 147:    */       {
/* 148:181 */         if (!var5) {
/* 149:183 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
/* 150:    */         } else {
/* 151:187 */           setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
/* 152:    */         }
/* 153:    */       }
/* 154:    */       else {
/* 155:192 */         setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
/* 156:    */       }
/* 157:    */     }
/* 158:195 */     else if (var3 == 1)
/* 159:    */     {
/* 160:197 */       if (var4)
/* 161:    */       {
/* 162:199 */         if (!var5) {
/* 163:201 */           setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 164:    */         } else {
/* 165:205 */           setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
/* 166:    */         }
/* 167:    */       }
/* 168:    */       else {
/* 169:210 */         setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
/* 170:    */       }
/* 171:    */     }
/* 172:213 */     else if (var3 == 2)
/* 173:    */     {
/* 174:215 */       if (var4)
/* 175:    */       {
/* 176:217 */         if (!var5) {
/* 177:219 */           setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
/* 178:    */         } else {
/* 179:223 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
/* 180:    */         }
/* 181:    */       }
/* 182:    */       else {
/* 183:228 */         setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 184:    */       }
/* 185:    */     }
/* 186:231 */     else if (var3 == 3) {
/* 187:233 */       if (var4)
/* 188:    */       {
/* 189:235 */         if (!var5) {
/* 190:237 */           setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
/* 191:    */         } else {
/* 192:241 */           setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 193:    */         }
/* 194:    */       }
/* 195:    */       else {
/* 196:246 */         setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
/* 197:    */       }
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void onBlockClicked(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_) {}
/* 202:    */   
/* 203:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/* 204:    */   {
/* 205:261 */     if (this.blockMaterial == Material.iron) {
/* 206:263 */       return true;
/* 207:    */     }
/* 208:267 */     int var10 = func_150012_g(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
/* 209:268 */     int var11 = var10 & 0x7;
/* 210:269 */     var11 ^= 0x4;
/* 211:271 */     if ((var10 & 0x8) == 0)
/* 212:    */     {
/* 213:273 */       p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var11, 2);
/* 214:274 */       p_149727_1_.markBlockRangeForRenderUpdate(p_149727_2_, p_149727_3_, p_149727_4_, p_149727_2_, p_149727_3_, p_149727_4_);
/* 215:    */     }
/* 216:    */     else
/* 217:    */     {
/* 218:278 */       p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_ - 1, p_149727_4_, var11, 2);
/* 219:279 */       p_149727_1_.markBlockRangeForRenderUpdate(p_149727_2_, p_149727_3_ - 1, p_149727_4_, p_149727_2_, p_149727_3_, p_149727_4_);
/* 220:    */     }
/* 221:282 */     p_149727_1_.playAuxSFXAtEntity(p_149727_5_, 1003, p_149727_2_, p_149727_3_, p_149727_4_, 0);
/* 222:283 */     return true;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void func_150014_a(World p_150014_1_, int p_150014_2_, int p_150014_3_, int p_150014_4_, boolean p_150014_5_)
/* 226:    */   {
/* 227:289 */     int var6 = func_150012_g(p_150014_1_, p_150014_2_, p_150014_3_, p_150014_4_);
/* 228:290 */     boolean var7 = (var6 & 0x4) != 0;
/* 229:292 */     if (var7 != p_150014_5_)
/* 230:    */     {
/* 231:294 */       int var8 = var6 & 0x7;
/* 232:295 */       var8 ^= 0x4;
/* 233:297 */       if ((var6 & 0x8) == 0)
/* 234:    */       {
/* 235:299 */         p_150014_1_.setBlockMetadataWithNotify(p_150014_2_, p_150014_3_, p_150014_4_, var8, 2);
/* 236:300 */         p_150014_1_.markBlockRangeForRenderUpdate(p_150014_2_, p_150014_3_, p_150014_4_, p_150014_2_, p_150014_3_, p_150014_4_);
/* 237:    */       }
/* 238:    */       else
/* 239:    */       {
/* 240:304 */         p_150014_1_.setBlockMetadataWithNotify(p_150014_2_, p_150014_3_ - 1, p_150014_4_, var8, 2);
/* 241:305 */         p_150014_1_.markBlockRangeForRenderUpdate(p_150014_2_, p_150014_3_ - 1, p_150014_4_, p_150014_2_, p_150014_3_, p_150014_4_);
/* 242:    */       }
/* 243:308 */       p_150014_1_.playAuxSFXAtEntity(null, 1003, p_150014_2_, p_150014_3_, p_150014_4_, 0);
/* 244:    */     }
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 248:    */   {
/* 249:314 */     int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
/* 250:316 */     if ((var6 & 0x8) == 0)
/* 251:    */     {
/* 252:318 */       boolean var7 = false;
/* 253:320 */       if (p_149695_1_.getBlock(p_149695_2_, p_149695_3_ + 1, p_149695_4_) != this)
/* 254:    */       {
/* 255:322 */         p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/* 256:323 */         var7 = true;
/* 257:    */       }
/* 258:326 */       if (!World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_))
/* 259:    */       {
/* 260:328 */         p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/* 261:329 */         var7 = true;
/* 262:331 */         if (p_149695_1_.getBlock(p_149695_2_, p_149695_3_ + 1, p_149695_4_) == this) {
/* 263:333 */           p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_ + 1, p_149695_4_);
/* 264:    */         }
/* 265:    */       }
/* 266:337 */       if (var7)
/* 267:    */       {
/* 268:339 */         if (!p_149695_1_.isClient) {
/* 269:341 */           dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, var6, 0);
/* 270:    */         }
/* 271:    */       }
/* 272:    */       else
/* 273:    */       {
/* 274:346 */         boolean var8 = (p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_)) || (p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_ + 1, p_149695_4_));
/* 275:348 */         if (((var8) || (p_149695_5_.canProvidePower())) && (p_149695_5_ != this)) {
/* 276:350 */           func_150014_a(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, var8);
/* 277:    */         }
/* 278:    */       }
/* 279:    */     }
/* 280:    */     else
/* 281:    */     {
/* 282:356 */       if (p_149695_1_.getBlock(p_149695_2_, p_149695_3_ - 1, p_149695_4_) != this) {
/* 283:358 */         p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/* 284:    */       }
/* 285:361 */       if (p_149695_5_ != this) {
/* 286:363 */         onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_, p_149695_5_);
/* 287:    */       }
/* 288:    */     }
/* 289:    */   }
/* 290:    */   
/* 291:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 292:    */   {
/* 293:370 */     return this.blockMaterial == Material.iron ? Items.iron_door : (p_149650_1_ & 0x8) != 0 ? null : Items.wooden_door;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public MovingObjectPosition collisionRayTrace(World p_149731_1_, int p_149731_2_, int p_149731_3_, int p_149731_4_, Vec3 p_149731_5_, Vec3 p_149731_6_)
/* 297:    */   {
/* 298:375 */     setBlockBoundsBasedOnState(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_);
/* 299:376 */     return super.collisionRayTrace(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_, p_149731_5_, p_149731_6_);
/* 300:    */   }
/* 301:    */   
/* 302:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/* 303:    */   {
/* 304:381 */     return p_149742_3_ < 255;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public int getMobilityFlag()
/* 308:    */   {
/* 309:386 */     return 1;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public int func_150012_g(IBlockAccess p_150012_1_, int p_150012_2_, int p_150012_3_, int p_150012_4_)
/* 313:    */   {
/* 314:391 */     int var5 = p_150012_1_.getBlockMetadata(p_150012_2_, p_150012_3_, p_150012_4_);
/* 315:392 */     boolean var6 = (var5 & 0x8) != 0;
/* 316:    */     int var8;
/* 317:    */     int var7;
/* 318:    */     int var8;
/* 319:396 */     if (var6)
/* 320:    */     {
/* 321:398 */       int var7 = p_150012_1_.getBlockMetadata(p_150012_2_, p_150012_3_ - 1, p_150012_4_);
/* 322:399 */       var8 = var5;
/* 323:    */     }
/* 324:    */     else
/* 325:    */     {
/* 326:403 */       var7 = var5;
/* 327:404 */       var8 = p_150012_1_.getBlockMetadata(p_150012_2_, p_150012_3_ + 1, p_150012_4_);
/* 328:    */     }
/* 329:407 */     boolean var9 = (var8 & 0x1) != 0;
/* 330:408 */     return var7 & 0x7 | (var6 ? 8 : 0) | (var9 ? 16 : 0);
/* 331:    */   }
/* 332:    */   
/* 333:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 334:    */   {
/* 335:416 */     return this.blockMaterial == Material.iron ? Items.iron_door : Items.wooden_door;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_)
/* 339:    */   {
/* 340:424 */     if ((p_149681_6_.capabilities.isCreativeMode) && ((p_149681_5_ & 0x8) != 0) && (p_149681_1_.getBlock(p_149681_2_, p_149681_3_ - 1, p_149681_4_) == this)) {
/* 341:426 */       p_149681_1_.setBlockToAir(p_149681_2_, p_149681_3_ - 1, p_149681_4_);
/* 342:    */     }
/* 343:    */   }
/* 344:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockDoor
 * JD-Core Version:    0.7.0.1
 */