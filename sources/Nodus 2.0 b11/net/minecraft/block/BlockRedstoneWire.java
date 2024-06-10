/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Random;
/*   6:    */ import java.util.Set;
/*   7:    */ import net.minecraft.block.material.Material;
/*   8:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   9:    */ import net.minecraft.init.Blocks;
/*  10:    */ import net.minecraft.init.Items;
/*  11:    */ import net.minecraft.item.Item;
/*  12:    */ import net.minecraft.util.AxisAlignedBB;
/*  13:    */ import net.minecraft.util.IIcon;
/*  14:    */ import net.minecraft.world.ChunkPosition;
/*  15:    */ import net.minecraft.world.IBlockAccess;
/*  16:    */ import net.minecraft.world.World;
/*  17:    */ 
/*  18:    */ public class BlockRedstoneWire
/*  19:    */   extends Block
/*  20:    */ {
/*  21: 21 */   private boolean field_150181_a = true;
/*  22: 22 */   private Set field_150179_b = new HashSet();
/*  23:    */   private IIcon field_150182_M;
/*  24:    */   private IIcon field_150183_N;
/*  25:    */   private IIcon field_150184_O;
/*  26:    */   private IIcon field_150180_P;
/*  27:    */   private static final String __OBFID = "CL_00000295";
/*  28:    */   
/*  29:    */   public BlockRedstoneWire()
/*  30:    */   {
/*  31: 31 */     super(Material.circuits);
/*  32: 32 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  36:    */   {
/*  37: 41 */     return null;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean isOpaqueCube()
/*  41:    */   {
/*  42: 46 */     return false;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean renderAsNormalBlock()
/*  46:    */   {
/*  47: 51 */     return false;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getRenderType()
/*  51:    */   {
/*  52: 59 */     return 5;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
/*  56:    */   {
/*  57: 68 */     return 8388608;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/*  61:    */   {
/*  62: 73 */     return (World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_)) || (p_149742_1_.getBlock(p_149742_2_, p_149742_3_ - 1, p_149742_4_) == Blocks.glowstone);
/*  63:    */   }
/*  64:    */   
/*  65:    */   private void func_150177_e(World p_150177_1_, int p_150177_2_, int p_150177_3_, int p_150177_4_)
/*  66:    */   {
/*  67: 78 */     func_150175_a(p_150177_1_, p_150177_2_, p_150177_3_, p_150177_4_, p_150177_2_, p_150177_3_, p_150177_4_);
/*  68: 79 */     ArrayList var5 = new ArrayList(this.field_150179_b);
/*  69: 80 */     this.field_150179_b.clear();
/*  70: 82 */     for (int var6 = 0; var6 < var5.size(); var6++)
/*  71:    */     {
/*  72: 84 */       ChunkPosition var7 = (ChunkPosition)var5.get(var6);
/*  73: 85 */       p_150177_1_.notifyBlocksOfNeighborChange(var7.field_151329_a, var7.field_151327_b, var7.field_151328_c, this);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   private void func_150175_a(World p_150175_1_, int p_150175_2_, int p_150175_3_, int p_150175_4_, int p_150175_5_, int p_150175_6_, int p_150175_7_)
/*  78:    */   {
/*  79: 91 */     int var8 = p_150175_1_.getBlockMetadata(p_150175_2_, p_150175_3_, p_150175_4_);
/*  80: 92 */     byte var9 = 0;
/*  81: 93 */     int var15 = func_150178_a(p_150175_1_, p_150175_5_, p_150175_6_, p_150175_7_, var9);
/*  82: 94 */     this.field_150181_a = false;
/*  83: 95 */     int var10 = p_150175_1_.getStrongestIndirectPower(p_150175_2_, p_150175_3_, p_150175_4_);
/*  84: 96 */     this.field_150181_a = true;
/*  85: 98 */     if ((var10 > 0) && (var10 > var15 - 1)) {
/*  86:100 */       var15 = var10;
/*  87:    */     }
/*  88:103 */     int var11 = 0;
/*  89:105 */     for (int var12 = 0; var12 < 4; var12++)
/*  90:    */     {
/*  91:107 */       int var13 = p_150175_2_;
/*  92:108 */       int var14 = p_150175_4_;
/*  93:110 */       if (var12 == 0) {
/*  94:112 */         var13 = p_150175_2_ - 1;
/*  95:    */       }
/*  96:115 */       if (var12 == 1) {
/*  97:117 */         var13++;
/*  98:    */       }
/*  99:120 */       if (var12 == 2) {
/* 100:122 */         var14 = p_150175_4_ - 1;
/* 101:    */       }
/* 102:125 */       if (var12 == 3) {
/* 103:127 */         var14++;
/* 104:    */       }
/* 105:130 */       if ((var13 != p_150175_5_) || (var14 != p_150175_7_)) {
/* 106:132 */         var11 = func_150178_a(p_150175_1_, var13, p_150175_3_, var14, var11);
/* 107:    */       }
/* 108:135 */       if ((p_150175_1_.getBlock(var13, p_150175_3_, var14).isNormalCube()) && (!p_150175_1_.getBlock(p_150175_2_, p_150175_3_ + 1, p_150175_4_).isNormalCube()))
/* 109:    */       {
/* 110:137 */         if (((var13 != p_150175_5_) || (var14 != p_150175_7_)) && (p_150175_3_ >= p_150175_6_)) {
/* 111:139 */           var11 = func_150178_a(p_150175_1_, var13, p_150175_3_ + 1, var14, var11);
/* 112:    */         }
/* 113:    */       }
/* 114:142 */       else if ((!p_150175_1_.getBlock(var13, p_150175_3_, var14).isNormalCube()) && ((var13 != p_150175_5_) || (var14 != p_150175_7_)) && (p_150175_3_ <= p_150175_6_)) {
/* 115:144 */         var11 = func_150178_a(p_150175_1_, var13, p_150175_3_ - 1, var14, var11);
/* 116:    */       }
/* 117:    */     }
/* 118:148 */     if (var11 > var15) {
/* 119:150 */       var15 = var11 - 1;
/* 120:152 */     } else if (var15 > 0) {
/* 121:154 */       var15--;
/* 122:    */     } else {
/* 123:158 */       var15 = 0;
/* 124:    */     }
/* 125:161 */     if (var10 > var15 - 1) {
/* 126:163 */       var15 = var10;
/* 127:    */     }
/* 128:166 */     if (var8 != var15)
/* 129:    */     {
/* 130:168 */       p_150175_1_.setBlockMetadataWithNotify(p_150175_2_, p_150175_3_, p_150175_4_, var15, 2);
/* 131:169 */       this.field_150179_b.add(new ChunkPosition(p_150175_2_, p_150175_3_, p_150175_4_));
/* 132:170 */       this.field_150179_b.add(new ChunkPosition(p_150175_2_ - 1, p_150175_3_, p_150175_4_));
/* 133:171 */       this.field_150179_b.add(new ChunkPosition(p_150175_2_ + 1, p_150175_3_, p_150175_4_));
/* 134:172 */       this.field_150179_b.add(new ChunkPosition(p_150175_2_, p_150175_3_ - 1, p_150175_4_));
/* 135:173 */       this.field_150179_b.add(new ChunkPosition(p_150175_2_, p_150175_3_ + 1, p_150175_4_));
/* 136:174 */       this.field_150179_b.add(new ChunkPosition(p_150175_2_, p_150175_3_, p_150175_4_ - 1));
/* 137:175 */       this.field_150179_b.add(new ChunkPosition(p_150175_2_, p_150175_3_, p_150175_4_ + 1));
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   private void func_150172_m(World p_150172_1_, int p_150172_2_, int p_150172_3_, int p_150172_4_)
/* 142:    */   {
/* 143:181 */     if (p_150172_1_.getBlock(p_150172_2_, p_150172_3_, p_150172_4_) == this)
/* 144:    */     {
/* 145:183 */       p_150172_1_.notifyBlocksOfNeighborChange(p_150172_2_, p_150172_3_, p_150172_4_, this);
/* 146:184 */       p_150172_1_.notifyBlocksOfNeighborChange(p_150172_2_ - 1, p_150172_3_, p_150172_4_, this);
/* 147:185 */       p_150172_1_.notifyBlocksOfNeighborChange(p_150172_2_ + 1, p_150172_3_, p_150172_4_, this);
/* 148:186 */       p_150172_1_.notifyBlocksOfNeighborChange(p_150172_2_, p_150172_3_, p_150172_4_ - 1, this);
/* 149:187 */       p_150172_1_.notifyBlocksOfNeighborChange(p_150172_2_, p_150172_3_, p_150172_4_ + 1, this);
/* 150:188 */       p_150172_1_.notifyBlocksOfNeighborChange(p_150172_2_, p_150172_3_ - 1, p_150172_4_, this);
/* 151:189 */       p_150172_1_.notifyBlocksOfNeighborChange(p_150172_2_, p_150172_3_ + 1, p_150172_4_, this);
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/* 156:    */   {
/* 157:195 */     super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/* 158:197 */     if (!p_149726_1_.isClient)
/* 159:    */     {
/* 160:199 */       func_150177_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/* 161:200 */       p_149726_1_.notifyBlocksOfNeighborChange(p_149726_2_, p_149726_3_ + 1, p_149726_4_, this);
/* 162:201 */       p_149726_1_.notifyBlocksOfNeighborChange(p_149726_2_, p_149726_3_ - 1, p_149726_4_, this);
/* 163:202 */       func_150172_m(p_149726_1_, p_149726_2_ - 1, p_149726_3_, p_149726_4_);
/* 164:203 */       func_150172_m(p_149726_1_, p_149726_2_ + 1, p_149726_3_, p_149726_4_);
/* 165:204 */       func_150172_m(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_ - 1);
/* 166:205 */       func_150172_m(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_ + 1);
/* 167:207 */       if (p_149726_1_.getBlock(p_149726_2_ - 1, p_149726_3_, p_149726_4_).isNormalCube()) {
/* 168:209 */         func_150172_m(p_149726_1_, p_149726_2_ - 1, p_149726_3_ + 1, p_149726_4_);
/* 169:    */       } else {
/* 170:213 */         func_150172_m(p_149726_1_, p_149726_2_ - 1, p_149726_3_ - 1, p_149726_4_);
/* 171:    */       }
/* 172:216 */       if (p_149726_1_.getBlock(p_149726_2_ + 1, p_149726_3_, p_149726_4_).isNormalCube()) {
/* 173:218 */         func_150172_m(p_149726_1_, p_149726_2_ + 1, p_149726_3_ + 1, p_149726_4_);
/* 174:    */       } else {
/* 175:222 */         func_150172_m(p_149726_1_, p_149726_2_ + 1, p_149726_3_ - 1, p_149726_4_);
/* 176:    */       }
/* 177:225 */       if (p_149726_1_.getBlock(p_149726_2_, p_149726_3_, p_149726_4_ - 1).isNormalCube()) {
/* 178:227 */         func_150172_m(p_149726_1_, p_149726_2_, p_149726_3_ + 1, p_149726_4_ - 1);
/* 179:    */       } else {
/* 180:231 */         func_150172_m(p_149726_1_, p_149726_2_, p_149726_3_ - 1, p_149726_4_ - 1);
/* 181:    */       }
/* 182:234 */       if (p_149726_1_.getBlock(p_149726_2_, p_149726_3_, p_149726_4_ + 1).isNormalCube()) {
/* 183:236 */         func_150172_m(p_149726_1_, p_149726_2_, p_149726_3_ + 1, p_149726_4_ + 1);
/* 184:    */       } else {
/* 185:240 */         func_150172_m(p_149726_1_, p_149726_2_, p_149726_3_ - 1, p_149726_4_ + 1);
/* 186:    */       }
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/* 191:    */   {
/* 192:247 */     super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/* 193:249 */     if (!p_149749_1_.isClient)
/* 194:    */     {
/* 195:251 */       p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_ + 1, p_149749_4_, this);
/* 196:252 */       p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_ - 1, p_149749_4_, this);
/* 197:253 */       p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_ + 1, p_149749_3_, p_149749_4_, this);
/* 198:254 */       p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_ - 1, p_149749_3_, p_149749_4_, this);
/* 199:255 */       p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_ + 1, this);
/* 200:256 */       p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_ - 1, this);
/* 201:257 */       func_150177_e(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_);
/* 202:258 */       func_150172_m(p_149749_1_, p_149749_2_ - 1, p_149749_3_, p_149749_4_);
/* 203:259 */       func_150172_m(p_149749_1_, p_149749_2_ + 1, p_149749_3_, p_149749_4_);
/* 204:260 */       func_150172_m(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_ - 1);
/* 205:261 */       func_150172_m(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_ + 1);
/* 206:263 */       if (p_149749_1_.getBlock(p_149749_2_ - 1, p_149749_3_, p_149749_4_).isNormalCube()) {
/* 207:265 */         func_150172_m(p_149749_1_, p_149749_2_ - 1, p_149749_3_ + 1, p_149749_4_);
/* 208:    */       } else {
/* 209:269 */         func_150172_m(p_149749_1_, p_149749_2_ - 1, p_149749_3_ - 1, p_149749_4_);
/* 210:    */       }
/* 211:272 */       if (p_149749_1_.getBlock(p_149749_2_ + 1, p_149749_3_, p_149749_4_).isNormalCube()) {
/* 212:274 */         func_150172_m(p_149749_1_, p_149749_2_ + 1, p_149749_3_ + 1, p_149749_4_);
/* 213:    */       } else {
/* 214:278 */         func_150172_m(p_149749_1_, p_149749_2_ + 1, p_149749_3_ - 1, p_149749_4_);
/* 215:    */       }
/* 216:281 */       if (p_149749_1_.getBlock(p_149749_2_, p_149749_3_, p_149749_4_ - 1).isNormalCube()) {
/* 217:283 */         func_150172_m(p_149749_1_, p_149749_2_, p_149749_3_ + 1, p_149749_4_ - 1);
/* 218:    */       } else {
/* 219:287 */         func_150172_m(p_149749_1_, p_149749_2_, p_149749_3_ - 1, p_149749_4_ - 1);
/* 220:    */       }
/* 221:290 */       if (p_149749_1_.getBlock(p_149749_2_, p_149749_3_, p_149749_4_ + 1).isNormalCube()) {
/* 222:292 */         func_150172_m(p_149749_1_, p_149749_2_, p_149749_3_ + 1, p_149749_4_ + 1);
/* 223:    */       } else {
/* 224:296 */         func_150172_m(p_149749_1_, p_149749_2_, p_149749_3_ - 1, p_149749_4_ + 1);
/* 225:    */       }
/* 226:    */     }
/* 227:    */   }
/* 228:    */   
/* 229:    */   private int func_150178_a(World p_150178_1_, int p_150178_2_, int p_150178_3_, int p_150178_4_, int p_150178_5_)
/* 230:    */   {
/* 231:303 */     if (p_150178_1_.getBlock(p_150178_2_, p_150178_3_, p_150178_4_) != this) {
/* 232:305 */       return p_150178_5_;
/* 233:    */     }
/* 234:309 */     int var6 = p_150178_1_.getBlockMetadata(p_150178_2_, p_150178_3_, p_150178_4_);
/* 235:310 */     return var6 > p_150178_5_ ? var6 : p_150178_5_;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 239:    */   {
/* 240:316 */     if (!p_149695_1_.isClient)
/* 241:    */     {
/* 242:318 */       boolean var6 = canPlaceBlockAt(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
/* 243:320 */       if (var6)
/* 244:    */       {
/* 245:322 */         func_150177_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
/* 246:    */       }
/* 247:    */       else
/* 248:    */       {
/* 249:326 */         dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, 0, 0);
/* 250:327 */         p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/* 251:    */       }
/* 252:330 */       super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
/* 253:    */     }
/* 254:    */   }
/* 255:    */   
/* 256:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 257:    */   {
/* 258:336 */     return Items.redstone;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public int isProvidingStrongPower(IBlockAccess p_149748_1_, int p_149748_2_, int p_149748_3_, int p_149748_4_, int p_149748_5_)
/* 262:    */   {
/* 263:341 */     return !this.field_150181_a ? 0 : isProvidingWeakPower(p_149748_1_, p_149748_2_, p_149748_3_, p_149748_4_, p_149748_5_);
/* 264:    */   }
/* 265:    */   
/* 266:    */   public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_)
/* 267:    */   {
/* 268:346 */     if (!this.field_150181_a) {
/* 269:348 */       return 0;
/* 270:    */     }
/* 271:352 */     int var6 = p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_);
/* 272:354 */     if (var6 == 0) {
/* 273:356 */       return 0;
/* 274:    */     }
/* 275:358 */     if (p_149709_5_ == 1) {
/* 276:360 */       return var6;
/* 277:    */     }
/* 278:364 */     boolean var7 = (func_150176_g(p_149709_1_, p_149709_2_ - 1, p_149709_3_, p_149709_4_, 1)) || ((!p_149709_1_.getBlock(p_149709_2_ - 1, p_149709_3_, p_149709_4_).isNormalCube()) && (func_150176_g(p_149709_1_, p_149709_2_ - 1, p_149709_3_ - 1, p_149709_4_, -1)));
/* 279:365 */     boolean var8 = (func_150176_g(p_149709_1_, p_149709_2_ + 1, p_149709_3_, p_149709_4_, 3)) || ((!p_149709_1_.getBlock(p_149709_2_ + 1, p_149709_3_, p_149709_4_).isNormalCube()) && (func_150176_g(p_149709_1_, p_149709_2_ + 1, p_149709_3_ - 1, p_149709_4_, -1)));
/* 280:366 */     boolean var9 = (func_150176_g(p_149709_1_, p_149709_2_, p_149709_3_, p_149709_4_ - 1, 2)) || ((!p_149709_1_.getBlock(p_149709_2_, p_149709_3_, p_149709_4_ - 1).isNormalCube()) && (func_150176_g(p_149709_1_, p_149709_2_, p_149709_3_ - 1, p_149709_4_ - 1, -1)));
/* 281:367 */     boolean var10 = (func_150176_g(p_149709_1_, p_149709_2_, p_149709_3_, p_149709_4_ + 1, 0)) || ((!p_149709_1_.getBlock(p_149709_2_, p_149709_3_, p_149709_4_ + 1).isNormalCube()) && (func_150176_g(p_149709_1_, p_149709_2_, p_149709_3_ - 1, p_149709_4_ + 1, -1)));
/* 282:369 */     if (!p_149709_1_.getBlock(p_149709_2_, p_149709_3_ + 1, p_149709_4_).isNormalCube())
/* 283:    */     {
/* 284:371 */       if ((p_149709_1_.getBlock(p_149709_2_ - 1, p_149709_3_, p_149709_4_).isNormalCube()) && (func_150176_g(p_149709_1_, p_149709_2_ - 1, p_149709_3_ + 1, p_149709_4_, -1))) {
/* 285:373 */         var7 = true;
/* 286:    */       }
/* 287:376 */       if ((p_149709_1_.getBlock(p_149709_2_ + 1, p_149709_3_, p_149709_4_).isNormalCube()) && (func_150176_g(p_149709_1_, p_149709_2_ + 1, p_149709_3_ + 1, p_149709_4_, -1))) {
/* 288:378 */         var8 = true;
/* 289:    */       }
/* 290:381 */       if ((p_149709_1_.getBlock(p_149709_2_, p_149709_3_, p_149709_4_ - 1).isNormalCube()) && (func_150176_g(p_149709_1_, p_149709_2_, p_149709_3_ + 1, p_149709_4_ - 1, -1))) {
/* 291:383 */         var9 = true;
/* 292:    */       }
/* 293:386 */       if ((p_149709_1_.getBlock(p_149709_2_, p_149709_3_, p_149709_4_ + 1).isNormalCube()) && (func_150176_g(p_149709_1_, p_149709_2_, p_149709_3_ + 1, p_149709_4_ + 1, -1))) {
/* 294:388 */         var10 = true;
/* 295:    */       }
/* 296:    */     }
/* 297:392 */     return (p_149709_5_ == 5) && (var8) && (!var9) && (!var10) ? var6 : (p_149709_5_ == 4) && (var7) && (!var9) && (!var10) ? var6 : (p_149709_5_ == 3) && (var10) && (!var7) && (!var8) ? var6 : (p_149709_5_ == 2) && (var9) && (!var7) && (!var8) ? var6 : (!var9) && (!var8) && (!var7) && (!var10) && (p_149709_5_ >= 2) && (p_149709_5_ <= 5) ? var6 : 0;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public boolean canProvidePower()
/* 301:    */   {
/* 302:402 */     return this.field_150181_a;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
/* 306:    */   {
/* 307:410 */     int var6 = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);
/* 308:412 */     if (var6 > 0)
/* 309:    */     {
/* 310:414 */       double var7 = p_149734_2_ + 0.5D + (p_149734_5_.nextFloat() - 0.5D) * 0.2D;
/* 311:415 */       double var9 = p_149734_3_ + 0.0625F;
/* 312:416 */       double var11 = p_149734_4_ + 0.5D + (p_149734_5_.nextFloat() - 0.5D) * 0.2D;
/* 313:417 */       float var13 = var6 / 15.0F;
/* 314:418 */       float var14 = var13 * 0.6F + 0.4F;
/* 315:420 */       if (var6 == 0) {
/* 316:422 */         var14 = 0.0F;
/* 317:    */       }
/* 318:425 */       float var15 = var13 * var13 * 0.7F - 0.5F;
/* 319:426 */       float var16 = var13 * var13 * 0.6F - 0.7F;
/* 320:428 */       if (var15 < 0.0F) {
/* 321:430 */         var15 = 0.0F;
/* 322:    */       }
/* 323:433 */       if (var16 < 0.0F) {
/* 324:435 */         var16 = 0.0F;
/* 325:    */       }
/* 326:438 */       p_149734_1_.spawnParticle("reddust", var7, var9, var11, var14, var15, var16);
/* 327:    */     }
/* 328:    */   }
/* 329:    */   
/* 330:    */   public static boolean func_150174_f(IBlockAccess p_150174_0_, int p_150174_1_, int p_150174_2_, int p_150174_3_, int p_150174_4_)
/* 331:    */   {
/* 332:444 */     Block var5 = p_150174_0_.getBlock(p_150174_1_, p_150174_2_, p_150174_3_);
/* 333:446 */     if (var5 == Blocks.redstone_wire) {
/* 334:448 */       return true;
/* 335:    */     }
/* 336:450 */     if (!Blocks.unpowered_repeater.func_149907_e(var5)) {
/* 337:452 */       return (var5.canProvidePower()) && (p_150174_4_ != -1);
/* 338:    */     }
/* 339:456 */     int var6 = p_150174_0_.getBlockMetadata(p_150174_1_, p_150174_2_, p_150174_3_);
/* 340:457 */     return (p_150174_4_ == (var6 & 0x3)) || (p_150174_4_ == net.minecraft.util.Direction.rotateOpposite[(var6 & 0x3)]);
/* 341:    */   }
/* 342:    */   
/* 343:    */   public static boolean func_150176_g(IBlockAccess p_150176_0_, int p_150176_1_, int p_150176_2_, int p_150176_3_, int p_150176_4_)
/* 344:    */   {
/* 345:463 */     if (func_150174_f(p_150176_0_, p_150176_1_, p_150176_2_, p_150176_3_, p_150176_4_)) {
/* 346:465 */       return true;
/* 347:    */     }
/* 348:467 */     if (p_150176_0_.getBlock(p_150176_1_, p_150176_2_, p_150176_3_) == Blocks.powered_repeater)
/* 349:    */     {
/* 350:469 */       int var5 = p_150176_0_.getBlockMetadata(p_150176_1_, p_150176_2_, p_150176_3_);
/* 351:470 */       return p_150176_4_ == (var5 & 0x3);
/* 352:    */     }
/* 353:474 */     return false;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 357:    */   {
/* 358:483 */     return Items.redstone;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 362:    */   {
/* 363:488 */     this.field_150182_M = p_149651_1_.registerIcon(getTextureName() + "_" + "cross");
/* 364:489 */     this.field_150183_N = p_149651_1_.registerIcon(getTextureName() + "_" + "line");
/* 365:490 */     this.field_150184_O = p_149651_1_.registerIcon(getTextureName() + "_" + "cross_overlay");
/* 366:491 */     this.field_150180_P = p_149651_1_.registerIcon(getTextureName() + "_" + "line_overlay");
/* 367:492 */     this.blockIcon = this.field_150182_M;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public static IIcon func_150173_e(String p_150173_0_)
/* 371:    */   {
/* 372:497 */     return p_150173_0_.equals("line_overlay") ? Blocks.redstone_wire.field_150180_P : p_150173_0_.equals("cross_overlay") ? Blocks.redstone_wire.field_150184_O : p_150173_0_.equals("line") ? Blocks.redstone_wire.field_150183_N : p_150173_0_.equals("cross") ? Blocks.redstone_wire.field_150182_M : null;
/* 373:    */   }
/* 374:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockRedstoneWire
 * JD-Core Version:    0.7.0.1
 */