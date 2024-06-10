/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.entity.EntityLivingBase;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.item.ItemStack;
/*   8:    */ import net.minecraft.util.IIcon;
/*   9:    */ import net.minecraft.util.MathHelper;
/*  10:    */ import net.minecraft.world.IBlockAccess;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public abstract class BlockRedstoneDiode
/*  14:    */   extends BlockDirectional
/*  15:    */ {
/*  16:    */   protected final boolean field_149914_a;
/*  17:    */   private static final String __OBFID = "CL_00000226";
/*  18:    */   
/*  19:    */   protected BlockRedstoneDiode(boolean p_i45400_1_)
/*  20:    */   {
/*  21: 21 */     super(Material.circuits);
/*  22: 22 */     this.field_149914_a = p_i45400_1_;
/*  23: 23 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean renderAsNormalBlock()
/*  27:    */   {
/*  28: 28 */     return false;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/*  32:    */   {
/*  33: 33 */     return !World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_) ? false : super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_)
/*  37:    */   {
/*  38: 41 */     return !World.doesBlockHaveSolidTopSurface(p_149718_1_, p_149718_2_, p_149718_3_ - 1, p_149718_4_) ? false : super.canBlockStay(p_149718_1_, p_149718_2_, p_149718_3_, p_149718_4_);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  42:    */   {
/*  43: 49 */     int var6 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
/*  44: 51 */     if (!func_149910_g(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, var6))
/*  45:    */     {
/*  46: 53 */       boolean var7 = func_149900_a(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, var6);
/*  47: 55 */       if ((this.field_149914_a) && (!var7))
/*  48:    */       {
/*  49: 57 */         p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, func_149898_i(), var6, 2);
/*  50:    */       }
/*  51: 59 */       else if (!this.field_149914_a)
/*  52:    */       {
/*  53: 61 */         p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, func_149906_e(), var6, 2);
/*  54: 63 */         if (!var7) {
/*  55: 65 */           p_149674_1_.func_147454_a(p_149674_2_, p_149674_3_, p_149674_4_, func_149906_e(), func_149899_k(var6), -1);
/*  56:    */         }
/*  57:    */       }
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  62:    */   {
/*  63: 76 */     return p_149691_1_ == 1 ? this.blockIcon : p_149691_1_ == 0 ? Blocks.unlit_redstone_torch.getBlockTextureFromSide(p_149691_1_) : this.field_149914_a ? Blocks.redstone_torch.getBlockTextureFromSide(p_149691_1_) : Blocks.double_stone_slab.getBlockTextureFromSide(1);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
/*  67:    */   {
/*  68: 81 */     return (p_149646_5_ != 0) && (p_149646_5_ != 1);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getRenderType()
/*  72:    */   {
/*  73: 89 */     return 36;
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected boolean func_149905_c(int p_149905_1_)
/*  77:    */   {
/*  78: 94 */     return this.field_149914_a;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int isProvidingStrongPower(IBlockAccess p_149748_1_, int p_149748_2_, int p_149748_3_, int p_149748_4_, int p_149748_5_)
/*  82:    */   {
/*  83: 99 */     return isProvidingWeakPower(p_149748_1_, p_149748_2_, p_149748_3_, p_149748_4_, p_149748_5_);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_)
/*  87:    */   {
/*  88:104 */     int var6 = p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_);
/*  89:106 */     if (!func_149905_c(var6)) {
/*  90:108 */       return 0;
/*  91:    */     }
/*  92:112 */     int var7 = func_149895_l(var6);
/*  93:113 */     return (var7 == 3) && (p_149709_5_ == 5) ? func_149904_f(p_149709_1_, p_149709_2_, p_149709_3_, p_149709_4_, var6) : (var7 == 2) && (p_149709_5_ == 2) ? func_149904_f(p_149709_1_, p_149709_2_, p_149709_3_, p_149709_4_, var6) : (var7 == 1) && (p_149709_5_ == 4) ? func_149904_f(p_149709_1_, p_149709_2_, p_149709_3_, p_149709_4_, var6) : (var7 == 0) && (p_149709_5_ == 3) ? func_149904_f(p_149709_1_, p_149709_2_, p_149709_3_, p_149709_4_, var6) : 0;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/*  97:    */   {
/*  98:119 */     if (!canBlockStay(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_))
/*  99:    */     {
/* 100:121 */       dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_), 0);
/* 101:122 */       p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/* 102:123 */       p_149695_1_.notifyBlocksOfNeighborChange(p_149695_2_ + 1, p_149695_3_, p_149695_4_, this);
/* 103:124 */       p_149695_1_.notifyBlocksOfNeighborChange(p_149695_2_ - 1, p_149695_3_, p_149695_4_, this);
/* 104:125 */       p_149695_1_.notifyBlocksOfNeighborChange(p_149695_2_, p_149695_3_, p_149695_4_ + 1, this);
/* 105:126 */       p_149695_1_.notifyBlocksOfNeighborChange(p_149695_2_, p_149695_3_, p_149695_4_ - 1, this);
/* 106:127 */       p_149695_1_.notifyBlocksOfNeighborChange(p_149695_2_, p_149695_3_ - 1, p_149695_4_, this);
/* 107:128 */       p_149695_1_.notifyBlocksOfNeighborChange(p_149695_2_, p_149695_3_ + 1, p_149695_4_, this);
/* 108:    */     }
/* 109:    */     else
/* 110:    */     {
/* 111:132 */       func_149897_b(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   protected void func_149897_b(World p_149897_1_, int p_149897_2_, int p_149897_3_, int p_149897_4_, Block p_149897_5_)
/* 116:    */   {
/* 117:138 */     int var6 = p_149897_1_.getBlockMetadata(p_149897_2_, p_149897_3_, p_149897_4_);
/* 118:140 */     if (!func_149910_g(p_149897_1_, p_149897_2_, p_149897_3_, p_149897_4_, var6))
/* 119:    */     {
/* 120:142 */       boolean var7 = func_149900_a(p_149897_1_, p_149897_2_, p_149897_3_, p_149897_4_, var6);
/* 121:144 */       if (((this.field_149914_a) && (!var7)) || ((!this.field_149914_a) && (var7) && (!p_149897_1_.func_147477_a(p_149897_2_, p_149897_3_, p_149897_4_, this))))
/* 122:    */       {
/* 123:146 */         byte var8 = -1;
/* 124:148 */         if (func_149912_i(p_149897_1_, p_149897_2_, p_149897_3_, p_149897_4_, var6)) {
/* 125:150 */           var8 = -3;
/* 126:152 */         } else if (this.field_149914_a) {
/* 127:154 */           var8 = -2;
/* 128:    */         }
/* 129:157 */         p_149897_1_.func_147454_a(p_149897_2_, p_149897_3_, p_149897_4_, this, func_149901_b(var6), var8);
/* 130:    */       }
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean func_149910_g(IBlockAccess p_149910_1_, int p_149910_2_, int p_149910_3_, int p_149910_4_, int p_149910_5_)
/* 135:    */   {
/* 136:164 */     return false;
/* 137:    */   }
/* 138:    */   
/* 139:    */   protected boolean func_149900_a(World p_149900_1_, int p_149900_2_, int p_149900_3_, int p_149900_4_, int p_149900_5_)
/* 140:    */   {
/* 141:169 */     return func_149903_h(p_149900_1_, p_149900_2_, p_149900_3_, p_149900_4_, p_149900_5_) > 0;
/* 142:    */   }
/* 143:    */   
/* 144:    */   protected int func_149903_h(World p_149903_1_, int p_149903_2_, int p_149903_3_, int p_149903_4_, int p_149903_5_)
/* 145:    */   {
/* 146:174 */     int var6 = func_149895_l(p_149903_5_);
/* 147:175 */     int var7 = p_149903_2_ + net.minecraft.util.Direction.offsetX[var6];
/* 148:176 */     int var8 = p_149903_4_ + net.minecraft.util.Direction.offsetZ[var6];
/* 149:177 */     int var9 = p_149903_1_.getIndirectPowerLevelTo(var7, p_149903_3_, var8, net.minecraft.util.Direction.directionToFacing[var6]);
/* 150:178 */     return var9 >= 15 ? var9 : Math.max(var9, p_149903_1_.getBlock(var7, p_149903_3_, var8) == Blocks.redstone_wire ? p_149903_1_.getBlockMetadata(var7, p_149903_3_, var8) : 0);
/* 151:    */   }
/* 152:    */   
/* 153:    */   protected int func_149902_h(IBlockAccess p_149902_1_, int p_149902_2_, int p_149902_3_, int p_149902_4_, int p_149902_5_)
/* 154:    */   {
/* 155:183 */     int var6 = func_149895_l(p_149902_5_);
/* 156:185 */     switch (var6)
/* 157:    */     {
/* 158:    */     case 0: 
/* 159:    */     case 2: 
/* 160:189 */       return Math.max(func_149913_i(p_149902_1_, p_149902_2_ - 1, p_149902_3_, p_149902_4_, 4), func_149913_i(p_149902_1_, p_149902_2_ + 1, p_149902_3_, p_149902_4_, 5));
/* 161:    */     case 1: 
/* 162:    */     case 3: 
/* 163:193 */       return Math.max(func_149913_i(p_149902_1_, p_149902_2_, p_149902_3_, p_149902_4_ + 1, 3), func_149913_i(p_149902_1_, p_149902_2_, p_149902_3_, p_149902_4_ - 1, 2));
/* 164:    */     }
/* 165:196 */     return 0;
/* 166:    */   }
/* 167:    */   
/* 168:    */   protected int func_149913_i(IBlockAccess p_149913_1_, int p_149913_2_, int p_149913_3_, int p_149913_4_, int p_149913_5_)
/* 169:    */   {
/* 170:202 */     Block var6 = p_149913_1_.getBlock(p_149913_2_, p_149913_3_, p_149913_4_);
/* 171:203 */     return func_149908_a(var6) ? p_149913_1_.isBlockProvidingPowerTo(p_149913_2_, p_149913_3_, p_149913_4_, p_149913_5_) : var6 == Blocks.redstone_wire ? p_149913_1_.getBlockMetadata(p_149913_2_, p_149913_3_, p_149913_4_) : 0;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public boolean canProvidePower()
/* 175:    */   {
/* 176:211 */     return true;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
/* 180:    */   {
/* 181:219 */     int var7 = ((MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3) + 2) % 4;
/* 182:220 */     p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 3);
/* 183:221 */     boolean var8 = func_149900_a(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, var7);
/* 184:223 */     if (var8) {
/* 185:225 */       p_149689_1_.scheduleBlockUpdate(p_149689_2_, p_149689_3_, p_149689_4_, this, 1);
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/* 190:    */   {
/* 191:231 */     func_149911_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/* 192:    */   }
/* 193:    */   
/* 194:    */   protected void func_149911_e(World p_149911_1_, int p_149911_2_, int p_149911_3_, int p_149911_4_)
/* 195:    */   {
/* 196:236 */     int var5 = func_149895_l(p_149911_1_.getBlockMetadata(p_149911_2_, p_149911_3_, p_149911_4_));
/* 197:238 */     if (var5 == 1)
/* 198:    */     {
/* 199:240 */       p_149911_1_.func_147460_e(p_149911_2_ + 1, p_149911_3_, p_149911_4_, this);
/* 200:241 */       p_149911_1_.func_147441_b(p_149911_2_ + 1, p_149911_3_, p_149911_4_, this, 4);
/* 201:    */     }
/* 202:244 */     if (var5 == 3)
/* 203:    */     {
/* 204:246 */       p_149911_1_.func_147460_e(p_149911_2_ - 1, p_149911_3_, p_149911_4_, this);
/* 205:247 */       p_149911_1_.func_147441_b(p_149911_2_ - 1, p_149911_3_, p_149911_4_, this, 5);
/* 206:    */     }
/* 207:250 */     if (var5 == 2)
/* 208:    */     {
/* 209:252 */       p_149911_1_.func_147460_e(p_149911_2_, p_149911_3_, p_149911_4_ + 1, this);
/* 210:253 */       p_149911_1_.func_147441_b(p_149911_2_, p_149911_3_, p_149911_4_ + 1, this, 2);
/* 211:    */     }
/* 212:256 */     if (var5 == 0)
/* 213:    */     {
/* 214:258 */       p_149911_1_.func_147460_e(p_149911_2_, p_149911_3_, p_149911_4_ - 1, this);
/* 215:259 */       p_149911_1_.func_147441_b(p_149911_2_, p_149911_3_, p_149911_4_ - 1, this, 3);
/* 216:    */     }
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void onBlockDestroyedByPlayer(World p_149664_1_, int p_149664_2_, int p_149664_3_, int p_149664_4_, int p_149664_5_)
/* 220:    */   {
/* 221:265 */     if (this.field_149914_a)
/* 222:    */     {
/* 223:267 */       p_149664_1_.notifyBlocksOfNeighborChange(p_149664_2_ + 1, p_149664_3_, p_149664_4_, this);
/* 224:268 */       p_149664_1_.notifyBlocksOfNeighborChange(p_149664_2_ - 1, p_149664_3_, p_149664_4_, this);
/* 225:269 */       p_149664_1_.notifyBlocksOfNeighborChange(p_149664_2_, p_149664_3_, p_149664_4_ + 1, this);
/* 226:270 */       p_149664_1_.notifyBlocksOfNeighborChange(p_149664_2_, p_149664_3_, p_149664_4_ - 1, this);
/* 227:271 */       p_149664_1_.notifyBlocksOfNeighborChange(p_149664_2_, p_149664_3_ - 1, p_149664_4_, this);
/* 228:272 */       p_149664_1_.notifyBlocksOfNeighborChange(p_149664_2_, p_149664_3_ + 1, p_149664_4_, this);
/* 229:    */     }
/* 230:275 */     super.onBlockDestroyedByPlayer(p_149664_1_, p_149664_2_, p_149664_3_, p_149664_4_, p_149664_5_);
/* 231:    */   }
/* 232:    */   
/* 233:    */   public boolean isOpaqueCube()
/* 234:    */   {
/* 235:280 */     return false;
/* 236:    */   }
/* 237:    */   
/* 238:    */   protected boolean func_149908_a(Block p_149908_1_)
/* 239:    */   {
/* 240:285 */     return p_149908_1_.canProvidePower();
/* 241:    */   }
/* 242:    */   
/* 243:    */   protected int func_149904_f(IBlockAccess p_149904_1_, int p_149904_2_, int p_149904_3_, int p_149904_4_, int p_149904_5_)
/* 244:    */   {
/* 245:290 */     return 15;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public static boolean func_149909_d(Block p_149909_0_)
/* 249:    */   {
/* 250:295 */     return (Blocks.unpowered_repeater.func_149907_e(p_149909_0_)) || (Blocks.unpowered_comparator.func_149907_e(p_149909_0_));
/* 251:    */   }
/* 252:    */   
/* 253:    */   public boolean func_149907_e(Block p_149907_1_)
/* 254:    */   {
/* 255:300 */     return (p_149907_1_ == func_149906_e()) || (p_149907_1_ == func_149898_i());
/* 256:    */   }
/* 257:    */   
/* 258:    */   public boolean func_149912_i(World p_149912_1_, int p_149912_2_, int p_149912_3_, int p_149912_4_, int p_149912_5_)
/* 259:    */   {
/* 260:305 */     int var6 = func_149895_l(p_149912_5_);
/* 261:307 */     if (func_149909_d(p_149912_1_.getBlock(p_149912_2_ - net.minecraft.util.Direction.offsetX[var6], p_149912_3_, p_149912_4_ - net.minecraft.util.Direction.offsetZ[var6])))
/* 262:    */     {
/* 263:309 */       int var7 = p_149912_1_.getBlockMetadata(p_149912_2_ - net.minecraft.util.Direction.offsetX[var6], p_149912_3_, p_149912_4_ - net.minecraft.util.Direction.offsetZ[var6]);
/* 264:310 */       int var8 = func_149895_l(var7);
/* 265:311 */       return var8 != var6;
/* 266:    */     }
/* 267:315 */     return false;
/* 268:    */   }
/* 269:    */   
/* 270:    */   protected int func_149899_k(int p_149899_1_)
/* 271:    */   {
/* 272:321 */     return func_149901_b(p_149899_1_);
/* 273:    */   }
/* 274:    */   
/* 275:    */   protected abstract int func_149901_b(int paramInt);
/* 276:    */   
/* 277:    */   protected abstract BlockRedstoneDiode func_149906_e();
/* 278:    */   
/* 279:    */   protected abstract BlockRedstoneDiode func_149898_i();
/* 280:    */   
/* 281:    */   public boolean func_149667_c(Block p_149667_1_)
/* 282:    */   {
/* 283:332 */     return func_149907_e(p_149667_1_);
/* 284:    */   }
/* 285:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockRedstoneDiode
 * JD-Core Version:    0.7.0.1
 */