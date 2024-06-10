/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.material.Material;
/*   4:    */ import net.minecraft.creativetab.CreativeTabs;
/*   5:    */ import net.minecraft.entity.EntityLivingBase;
/*   6:    */ import net.minecraft.entity.player.EntityPlayer;
/*   7:    */ import net.minecraft.item.ItemStack;
/*   8:    */ import net.minecraft.util.AxisAlignedBB;
/*   9:    */ import net.minecraft.util.MathHelper;
/*  10:    */ import net.minecraft.world.IBlockAccess;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public class BlockLever
/*  14:    */   extends Block
/*  15:    */ {
/*  16:    */   private static final String __OBFID = "CL_00000264";
/*  17:    */   
/*  18:    */   protected BlockLever()
/*  19:    */   {
/*  20: 19 */     super(Material.circuits);
/*  21: 20 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  25:    */   {
/*  26: 29 */     return null;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean isOpaqueCube()
/*  30:    */   {
/*  31: 34 */     return false;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean renderAsNormalBlock()
/*  35:    */   {
/*  36: 39 */     return false;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getRenderType()
/*  40:    */   {
/*  41: 47 */     return 12;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean canPlaceBlockOnSide(World p_149707_1_, int p_149707_2_, int p_149707_3_, int p_149707_4_, int p_149707_5_)
/*  45:    */   {
/*  46: 55 */     return (p_149707_5_ == 0) && (p_149707_1_.getBlock(p_149707_2_, p_149707_3_ + 1, p_149707_4_).isNormalCube());
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/*  50:    */   {
/*  51: 60 */     return World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_) ? true : p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_ + 1).isNormalCube() ? true : p_149742_1_.getBlock(p_149742_2_, p_149742_3_, p_149742_4_ - 1).isNormalCube() ? true : p_149742_1_.getBlock(p_149742_2_ + 1, p_149742_3_, p_149742_4_).isNormalCube() ? true : p_149742_1_.getBlock(p_149742_2_ - 1, p_149742_3_, p_149742_4_).isNormalCube() ? true : p_149742_1_.getBlock(p_149742_2_, p_149742_3_ + 1, p_149742_4_).isNormalCube();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
/*  55:    */   {
/*  56: 65 */     int var11 = p_149660_9_ & 0x8;
/*  57: 66 */     int var10 = p_149660_9_ & 0x7;
/*  58: 67 */     byte var12 = -1;
/*  59: 69 */     if ((p_149660_5_ == 0) && (p_149660_1_.getBlock(p_149660_2_, p_149660_3_ + 1, p_149660_4_).isNormalCube())) {
/*  60: 71 */       var12 = 0;
/*  61:    */     }
/*  62: 74 */     if ((p_149660_5_ == 1) && (World.doesBlockHaveSolidTopSurface(p_149660_1_, p_149660_2_, p_149660_3_ - 1, p_149660_4_))) {
/*  63: 76 */       var12 = 5;
/*  64:    */     }
/*  65: 79 */     if ((p_149660_5_ == 2) && (p_149660_1_.getBlock(p_149660_2_, p_149660_3_, p_149660_4_ + 1).isNormalCube())) {
/*  66: 81 */       var12 = 4;
/*  67:    */     }
/*  68: 84 */     if ((p_149660_5_ == 3) && (p_149660_1_.getBlock(p_149660_2_, p_149660_3_, p_149660_4_ - 1).isNormalCube())) {
/*  69: 86 */       var12 = 3;
/*  70:    */     }
/*  71: 89 */     if ((p_149660_5_ == 4) && (p_149660_1_.getBlock(p_149660_2_ + 1, p_149660_3_, p_149660_4_).isNormalCube())) {
/*  72: 91 */       var12 = 2;
/*  73:    */     }
/*  74: 94 */     if ((p_149660_5_ == 5) && (p_149660_1_.getBlock(p_149660_2_ - 1, p_149660_3_, p_149660_4_).isNormalCube())) {
/*  75: 96 */       var12 = 1;
/*  76:    */     }
/*  77: 99 */     return var12 + var11;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
/*  81:    */   {
/*  82:107 */     int var7 = p_149689_1_.getBlockMetadata(p_149689_2_, p_149689_3_, p_149689_4_);
/*  83:108 */     int var8 = var7 & 0x7;
/*  84:109 */     int var9 = var7 & 0x8;
/*  85:111 */     if (var8 == func_149819_b(1))
/*  86:    */     {
/*  87:113 */       if ((MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x1) == 0) {
/*  88:115 */         p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x5 | var9, 2);
/*  89:    */       } else {
/*  90:119 */         p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x6 | var9, 2);
/*  91:    */       }
/*  92:    */     }
/*  93:122 */     else if (var8 == func_149819_b(0)) {
/*  94:124 */       if ((MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x1) == 0) {
/*  95:126 */         p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x7 | var9, 2);
/*  96:    */       } else {
/*  97:130 */         p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var9, 2);
/*  98:    */       }
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static int func_149819_b(int p_149819_0_)
/* 103:    */   {
/* 104:137 */     switch (p_149819_0_)
/* 105:    */     {
/* 106:    */     case 0: 
/* 107:140 */       return 0;
/* 108:    */     case 1: 
/* 109:143 */       return 5;
/* 110:    */     case 2: 
/* 111:146 */       return 4;
/* 112:    */     case 3: 
/* 113:149 */       return 3;
/* 114:    */     case 4: 
/* 115:152 */       return 2;
/* 116:    */     case 5: 
/* 117:155 */       return 1;
/* 118:    */     }
/* 119:158 */     return -1;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 123:    */   {
/* 124:164 */     if (func_149820_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_))
/* 125:    */     {
/* 126:166 */       int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_) & 0x7;
/* 127:167 */       boolean var7 = false;
/* 128:169 */       if ((!p_149695_1_.getBlock(p_149695_2_ - 1, p_149695_3_, p_149695_4_).isNormalCube()) && (var6 == 1)) {
/* 129:171 */         var7 = true;
/* 130:    */       }
/* 131:174 */       if ((!p_149695_1_.getBlock(p_149695_2_ + 1, p_149695_3_, p_149695_4_).isNormalCube()) && (var6 == 2)) {
/* 132:176 */         var7 = true;
/* 133:    */       }
/* 134:179 */       if ((!p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_ - 1).isNormalCube()) && (var6 == 3)) {
/* 135:181 */         var7 = true;
/* 136:    */       }
/* 137:184 */       if ((!p_149695_1_.getBlock(p_149695_2_, p_149695_3_, p_149695_4_ + 1).isNormalCube()) && (var6 == 4)) {
/* 138:186 */         var7 = true;
/* 139:    */       }
/* 140:189 */       if ((!World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_)) && (var6 == 5)) {
/* 141:191 */         var7 = true;
/* 142:    */       }
/* 143:194 */       if ((!World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_)) && (var6 == 6)) {
/* 144:196 */         var7 = true;
/* 145:    */       }
/* 146:199 */       if ((!p_149695_1_.getBlock(p_149695_2_, p_149695_3_ + 1, p_149695_4_).isNormalCube()) && (var6 == 0)) {
/* 147:201 */         var7 = true;
/* 148:    */       }
/* 149:204 */       if ((!p_149695_1_.getBlock(p_149695_2_, p_149695_3_ + 1, p_149695_4_).isNormalCube()) && (var6 == 7)) {
/* 150:206 */         var7 = true;
/* 151:    */       }
/* 152:209 */       if (var7)
/* 153:    */       {
/* 154:211 */         dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_), 0);
/* 155:212 */         p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/* 156:    */       }
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   private boolean func_149820_e(World p_149820_1_, int p_149820_2_, int p_149820_3_, int p_149820_4_)
/* 161:    */   {
/* 162:219 */     if (!canPlaceBlockAt(p_149820_1_, p_149820_2_, p_149820_3_, p_149820_4_))
/* 163:    */     {
/* 164:221 */       dropBlockAsItem(p_149820_1_, p_149820_2_, p_149820_3_, p_149820_4_, p_149820_1_.getBlockMetadata(p_149820_2_, p_149820_3_, p_149820_4_), 0);
/* 165:222 */       p_149820_1_.setBlockToAir(p_149820_2_, p_149820_3_, p_149820_4_);
/* 166:223 */       return false;
/* 167:    */     }
/* 168:227 */     return true;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/* 172:    */   {
/* 173:233 */     int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_) & 0x7;
/* 174:234 */     float var6 = 0.1875F;
/* 175:236 */     if (var5 == 1)
/* 176:    */     {
/* 177:238 */       setBlockBounds(0.0F, 0.2F, 0.5F - var6, var6 * 2.0F, 0.8F, 0.5F + var6);
/* 178:    */     }
/* 179:240 */     else if (var5 == 2)
/* 180:    */     {
/* 181:242 */       setBlockBounds(1.0F - var6 * 2.0F, 0.2F, 0.5F - var6, 1.0F, 0.8F, 0.5F + var6);
/* 182:    */     }
/* 183:244 */     else if (var5 == 3)
/* 184:    */     {
/* 185:246 */       setBlockBounds(0.5F - var6, 0.2F, 0.0F, 0.5F + var6, 0.8F, var6 * 2.0F);
/* 186:    */     }
/* 187:248 */     else if (var5 == 4)
/* 188:    */     {
/* 189:250 */       setBlockBounds(0.5F - var6, 0.2F, 1.0F - var6 * 2.0F, 0.5F + var6, 0.8F, 1.0F);
/* 190:    */     }
/* 191:252 */     else if ((var5 != 5) && (var5 != 6))
/* 192:    */     {
/* 193:254 */       if ((var5 == 0) || (var5 == 7))
/* 194:    */       {
/* 195:256 */         var6 = 0.25F;
/* 196:257 */         setBlockBounds(0.5F - var6, 0.4F, 0.5F - var6, 0.5F + var6, 1.0F, 0.5F + var6);
/* 197:    */       }
/* 198:    */     }
/* 199:    */     else
/* 200:    */     {
/* 201:262 */       var6 = 0.25F;
/* 202:263 */       setBlockBounds(0.5F - var6, 0.0F, 0.5F - var6, 0.5F + var6, 0.6F, 0.5F + var6);
/* 203:    */     }
/* 204:    */   }
/* 205:    */   
/* 206:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/* 207:    */   {
/* 208:272 */     if (p_149727_1_.isClient) {
/* 209:274 */       return true;
/* 210:    */     }
/* 211:278 */     int var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
/* 212:279 */     int var11 = var10 & 0x7;
/* 213:280 */     int var12 = 8 - (var10 & 0x8);
/* 214:281 */     p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var11 + var12, 3);
/* 215:282 */     p_149727_1_.playSoundEffect(p_149727_2_ + 0.5D, p_149727_3_ + 0.5D, p_149727_4_ + 0.5D, "random.click", 0.3F, var12 > 0 ? 0.6F : 0.5F);
/* 216:283 */     p_149727_1_.notifyBlocksOfNeighborChange(p_149727_2_, p_149727_3_, p_149727_4_, this);
/* 217:285 */     if (var11 == 1) {
/* 218:287 */       p_149727_1_.notifyBlocksOfNeighborChange(p_149727_2_ - 1, p_149727_3_, p_149727_4_, this);
/* 219:289 */     } else if (var11 == 2) {
/* 220:291 */       p_149727_1_.notifyBlocksOfNeighborChange(p_149727_2_ + 1, p_149727_3_, p_149727_4_, this);
/* 221:293 */     } else if (var11 == 3) {
/* 222:295 */       p_149727_1_.notifyBlocksOfNeighborChange(p_149727_2_, p_149727_3_, p_149727_4_ - 1, this);
/* 223:297 */     } else if (var11 == 4) {
/* 224:299 */       p_149727_1_.notifyBlocksOfNeighborChange(p_149727_2_, p_149727_3_, p_149727_4_ + 1, this);
/* 225:301 */     } else if ((var11 != 5) && (var11 != 6))
/* 226:    */     {
/* 227:303 */       if ((var11 == 0) || (var11 == 7)) {
/* 228:305 */         p_149727_1_.notifyBlocksOfNeighborChange(p_149727_2_, p_149727_3_ + 1, p_149727_4_, this);
/* 229:    */       }
/* 230:    */     }
/* 231:    */     else {
/* 232:310 */       p_149727_1_.notifyBlocksOfNeighborChange(p_149727_2_, p_149727_3_ - 1, p_149727_4_, this);
/* 233:    */     }
/* 234:313 */     return true;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/* 238:    */   {
/* 239:319 */     if ((p_149749_6_ & 0x8) > 0)
/* 240:    */     {
/* 241:321 */       p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_, this);
/* 242:322 */       int var7 = p_149749_6_ & 0x7;
/* 243:324 */       if (var7 == 1) {
/* 244:326 */         p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_ - 1, p_149749_3_, p_149749_4_, this);
/* 245:328 */       } else if (var7 == 2) {
/* 246:330 */         p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_ + 1, p_149749_3_, p_149749_4_, this);
/* 247:332 */       } else if (var7 == 3) {
/* 248:334 */         p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_ - 1, this);
/* 249:336 */       } else if (var7 == 4) {
/* 250:338 */         p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_ + 1, this);
/* 251:340 */       } else if ((var7 != 5) && (var7 != 6))
/* 252:    */       {
/* 253:342 */         if ((var7 == 0) || (var7 == 7)) {
/* 254:344 */           p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_ + 1, p_149749_4_, this);
/* 255:    */         }
/* 256:    */       }
/* 257:    */       else {
/* 258:349 */         p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_ - 1, p_149749_4_, this);
/* 259:    */       }
/* 260:    */     }
/* 261:353 */     super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/* 262:    */   }
/* 263:    */   
/* 264:    */   public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_)
/* 265:    */   {
/* 266:358 */     return (p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_) & 0x8) > 0 ? 15 : 0;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public int isProvidingStrongPower(IBlockAccess p_149748_1_, int p_149748_2_, int p_149748_3_, int p_149748_4_, int p_149748_5_)
/* 270:    */   {
/* 271:363 */     int var6 = p_149748_1_.getBlockMetadata(p_149748_2_, p_149748_3_, p_149748_4_);
/* 272:365 */     if ((var6 & 0x8) == 0) {
/* 273:367 */       return 0;
/* 274:    */     }
/* 275:371 */     int var7 = var6 & 0x7;
/* 276:372 */     return (var7 == 1) && (p_149748_5_ == 5) ? 15 : (var7 == 2) && (p_149748_5_ == 4) ? 15 : (var7 == 3) && (p_149748_5_ == 3) ? 15 : (var7 == 4) && (p_149748_5_ == 2) ? 15 : (var7 == 5) && (p_149748_5_ == 1) ? 15 : (var7 == 6) && (p_149748_5_ == 1) ? 15 : (var7 == 7) && (p_149748_5_ == 0) ? 15 : (var7 == 0) && (p_149748_5_ == 0) ? 15 : 0;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public boolean canProvidePower()
/* 280:    */   {
/* 281:381 */     return true;
/* 282:    */   }
/* 283:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockLever
 * JD-Core Version:    0.7.0.1
 */