/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Random;
/*   6:    */ import net.minecraft.block.material.Material;
/*   7:    */ import net.minecraft.creativetab.CreativeTabs;
/*   8:    */ import net.minecraft.init.Blocks;
/*   9:    */ import net.minecraft.util.AxisAlignedBB;
/*  10:    */ import net.minecraft.util.MovingObjectPosition;
/*  11:    */ import net.minecraft.util.Vec3;
/*  12:    */ import net.minecraft.world.ChunkPosition;
/*  13:    */ import net.minecraft.world.IBlockAccess;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public abstract class BlockRailBase
/*  17:    */   extends Block
/*  18:    */ {
/*  19:    */   protected final boolean field_150053_a;
/*  20:    */   private static final String __OBFID = "CL_00000195";
/*  21:    */   
/*  22:    */   public static final boolean func_150049_b_(World p_150049_0_, int p_150049_1_, int p_150049_2_, int p_150049_3_)
/*  23:    */   {
/*  24: 23 */     return func_150051_a(p_150049_0_.getBlock(p_150049_1_, p_150049_2_, p_150049_3_));
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static final boolean func_150051_a(Block p_150051_0_)
/*  28:    */   {
/*  29: 28 */     return (p_150051_0_ == Blocks.rail) || (p_150051_0_ == Blocks.golden_rail) || (p_150051_0_ == Blocks.detector_rail) || (p_150051_0_ == Blocks.activator_rail);
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected BlockRailBase(boolean p_i45389_1_)
/*  33:    */   {
/*  34: 33 */     super(Material.circuits);
/*  35: 34 */     this.field_150053_a = p_i45389_1_;
/*  36: 35 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*  37: 36 */     setCreativeTab(CreativeTabs.tabTransport);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean func_150050_e()
/*  41:    */   {
/*  42: 41 */     return this.field_150053_a;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  46:    */   {
/*  47: 50 */     return null;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean isOpaqueCube()
/*  51:    */   {
/*  52: 55 */     return false;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public MovingObjectPosition collisionRayTrace(World p_149731_1_, int p_149731_2_, int p_149731_3_, int p_149731_4_, Vec3 p_149731_5_, Vec3 p_149731_6_)
/*  56:    */   {
/*  57: 60 */     setBlockBoundsBasedOnState(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_);
/*  58: 61 */     return super.collisionRayTrace(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_, p_149731_5_, p_149731_6_);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  62:    */   {
/*  63: 66 */     int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
/*  64: 68 */     if ((var5 >= 2) && (var5 <= 5)) {
/*  65: 70 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
/*  66:    */     } else {
/*  67: 74 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean renderAsNormalBlock()
/*  72:    */   {
/*  73: 80 */     return false;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getRenderType()
/*  77:    */   {
/*  78: 88 */     return 9;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int quantityDropped(Random p_149745_1_)
/*  82:    */   {
/*  83: 96 */     return 1;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/*  87:    */   {
/*  88:101 */     return World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/*  92:    */   {
/*  93:106 */     if (!p_149726_1_.isClient)
/*  94:    */     {
/*  95:108 */       func_150052_a(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_, true);
/*  96:110 */       if (this.field_150053_a) {
/*  97:112 */         onNeighborBlockChange(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_, this);
/*  98:    */       }
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 103:    */   {
/* 104:119 */     if (!p_149695_1_.isClient)
/* 105:    */     {
/* 106:121 */       int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
/* 107:122 */       int var7 = var6;
/* 108:124 */       if (this.field_150053_a) {
/* 109:126 */         var7 = var6 & 0x7;
/* 110:    */       }
/* 111:129 */       boolean var8 = false;
/* 112:131 */       if (!World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_)) {
/* 113:133 */         var8 = true;
/* 114:    */       }
/* 115:136 */       if ((var7 == 2) && (!World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_ + 1, p_149695_3_, p_149695_4_))) {
/* 116:138 */         var8 = true;
/* 117:    */       }
/* 118:141 */       if ((var7 == 3) && (!World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_ - 1, p_149695_3_, p_149695_4_))) {
/* 119:143 */         var8 = true;
/* 120:    */       }
/* 121:146 */       if ((var7 == 4) && (!World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_ - 1))) {
/* 122:148 */         var8 = true;
/* 123:    */       }
/* 124:151 */       if ((var7 == 5) && (!World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_ + 1))) {
/* 125:153 */         var8 = true;
/* 126:    */       }
/* 127:156 */       if (var8)
/* 128:    */       {
/* 129:158 */         dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_), 0);
/* 130:159 */         p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/* 131:    */       }
/* 132:    */       else
/* 133:    */       {
/* 134:163 */         func_150048_a(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, var6, var7, p_149695_5_);
/* 135:    */       }
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   protected void func_150048_a(World p_150048_1_, int p_150048_2_, int p_150048_3_, int p_150048_4_, int p_150048_5_, int p_150048_6_, Block p_150048_7_) {}
/* 140:    */   
/* 141:    */   protected void func_150052_a(World p_150052_1_, int p_150052_2_, int p_150052_3_, int p_150052_4_, boolean p_150052_5_)
/* 142:    */   {
/* 143:172 */     if (!p_150052_1_.isClient) {
/* 144:174 */       new Rail(p_150052_1_, p_150052_2_, p_150052_3_, p_150052_4_).func_150655_a(p_150052_1_.isBlockIndirectlyGettingPowered(p_150052_2_, p_150052_3_, p_150052_4_), p_150052_5_);
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   public int getMobilityFlag()
/* 149:    */   {
/* 150:180 */     return 0;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/* 154:    */   {
/* 155:185 */     int var7 = p_149749_6_;
/* 156:187 */     if (this.field_150053_a) {
/* 157:189 */       var7 = p_149749_6_ & 0x7;
/* 158:    */     }
/* 159:192 */     super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/* 160:194 */     if ((var7 == 2) || (var7 == 3) || (var7 == 4) || (var7 == 5)) {
/* 161:196 */       p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_ + 1, p_149749_4_, p_149749_5_);
/* 162:    */     }
/* 163:199 */     if (this.field_150053_a)
/* 164:    */     {
/* 165:201 */       p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
/* 166:202 */       p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_ - 1, p_149749_4_, p_149749_5_);
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   public class Rail
/* 171:    */   {
/* 172:    */     private World field_150660_b;
/* 173:    */     private int field_150661_c;
/* 174:    */     private int field_150658_d;
/* 175:    */     private int field_150659_e;
/* 176:    */     private final boolean field_150656_f;
/* 177:213 */     private List field_150657_g = new ArrayList();
/* 178:    */     private static final String __OBFID = "CL_00000196";
/* 179:    */     
/* 180:    */     public Rail(World p_i45388_2_, int p_i45388_3_, int p_i45388_4_, int p_i45388_5_)
/* 181:    */     {
/* 182:218 */       this.field_150660_b = p_i45388_2_;
/* 183:219 */       this.field_150661_c = p_i45388_3_;
/* 184:220 */       this.field_150658_d = p_i45388_4_;
/* 185:221 */       this.field_150659_e = p_i45388_5_;
/* 186:222 */       Block var6 = p_i45388_2_.getBlock(p_i45388_3_, p_i45388_4_, p_i45388_5_);
/* 187:223 */       int var7 = p_i45388_2_.getBlockMetadata(p_i45388_3_, p_i45388_4_, p_i45388_5_);
/* 188:225 */       if (((BlockRailBase)var6).field_150053_a)
/* 189:    */       {
/* 190:227 */         this.field_150656_f = true;
/* 191:228 */         var7 &= 0xFFFFFFF7;
/* 192:    */       }
/* 193:    */       else
/* 194:    */       {
/* 195:232 */         this.field_150656_f = false;
/* 196:    */       }
/* 197:235 */       func_150648_a(var7);
/* 198:    */     }
/* 199:    */     
/* 200:    */     private void func_150648_a(int p_150648_1_)
/* 201:    */     {
/* 202:240 */       this.field_150657_g.clear();
/* 203:242 */       if (p_150648_1_ == 0)
/* 204:    */       {
/* 205:244 */         this.field_150657_g.add(new ChunkPosition(this.field_150661_c, this.field_150658_d, this.field_150659_e - 1));
/* 206:245 */         this.field_150657_g.add(new ChunkPosition(this.field_150661_c, this.field_150658_d, this.field_150659_e + 1));
/* 207:    */       }
/* 208:247 */       else if (p_150648_1_ == 1)
/* 209:    */       {
/* 210:249 */         this.field_150657_g.add(new ChunkPosition(this.field_150661_c - 1, this.field_150658_d, this.field_150659_e));
/* 211:250 */         this.field_150657_g.add(new ChunkPosition(this.field_150661_c + 1, this.field_150658_d, this.field_150659_e));
/* 212:    */       }
/* 213:252 */       else if (p_150648_1_ == 2)
/* 214:    */       {
/* 215:254 */         this.field_150657_g.add(new ChunkPosition(this.field_150661_c - 1, this.field_150658_d, this.field_150659_e));
/* 216:255 */         this.field_150657_g.add(new ChunkPosition(this.field_150661_c + 1, this.field_150658_d + 1, this.field_150659_e));
/* 217:    */       }
/* 218:257 */       else if (p_150648_1_ == 3)
/* 219:    */       {
/* 220:259 */         this.field_150657_g.add(new ChunkPosition(this.field_150661_c - 1, this.field_150658_d + 1, this.field_150659_e));
/* 221:260 */         this.field_150657_g.add(new ChunkPosition(this.field_150661_c + 1, this.field_150658_d, this.field_150659_e));
/* 222:    */       }
/* 223:262 */       else if (p_150648_1_ == 4)
/* 224:    */       {
/* 225:264 */         this.field_150657_g.add(new ChunkPosition(this.field_150661_c, this.field_150658_d + 1, this.field_150659_e - 1));
/* 226:265 */         this.field_150657_g.add(new ChunkPosition(this.field_150661_c, this.field_150658_d, this.field_150659_e + 1));
/* 227:    */       }
/* 228:267 */       else if (p_150648_1_ == 5)
/* 229:    */       {
/* 230:269 */         this.field_150657_g.add(new ChunkPosition(this.field_150661_c, this.field_150658_d, this.field_150659_e - 1));
/* 231:270 */         this.field_150657_g.add(new ChunkPosition(this.field_150661_c, this.field_150658_d + 1, this.field_150659_e + 1));
/* 232:    */       }
/* 233:272 */       else if (p_150648_1_ == 6)
/* 234:    */       {
/* 235:274 */         this.field_150657_g.add(new ChunkPosition(this.field_150661_c + 1, this.field_150658_d, this.field_150659_e));
/* 236:275 */         this.field_150657_g.add(new ChunkPosition(this.field_150661_c, this.field_150658_d, this.field_150659_e + 1));
/* 237:    */       }
/* 238:277 */       else if (p_150648_1_ == 7)
/* 239:    */       {
/* 240:279 */         this.field_150657_g.add(new ChunkPosition(this.field_150661_c - 1, this.field_150658_d, this.field_150659_e));
/* 241:280 */         this.field_150657_g.add(new ChunkPosition(this.field_150661_c, this.field_150658_d, this.field_150659_e + 1));
/* 242:    */       }
/* 243:282 */       else if (p_150648_1_ == 8)
/* 244:    */       {
/* 245:284 */         this.field_150657_g.add(new ChunkPosition(this.field_150661_c - 1, this.field_150658_d, this.field_150659_e));
/* 246:285 */         this.field_150657_g.add(new ChunkPosition(this.field_150661_c, this.field_150658_d, this.field_150659_e - 1));
/* 247:    */       }
/* 248:287 */       else if (p_150648_1_ == 9)
/* 249:    */       {
/* 250:289 */         this.field_150657_g.add(new ChunkPosition(this.field_150661_c + 1, this.field_150658_d, this.field_150659_e));
/* 251:290 */         this.field_150657_g.add(new ChunkPosition(this.field_150661_c, this.field_150658_d, this.field_150659_e - 1));
/* 252:    */       }
/* 253:    */     }
/* 254:    */     
/* 255:    */     private void func_150651_b()
/* 256:    */     {
/* 257:296 */       for (int var1 = 0; var1 < this.field_150657_g.size(); var1++)
/* 258:    */       {
/* 259:298 */         Rail var2 = func_150654_a((ChunkPosition)this.field_150657_g.get(var1));
/* 260:300 */         if ((var2 != null) && (var2.func_150653_a(this))) {
/* 261:302 */           this.field_150657_g.set(var1, new ChunkPosition(var2.field_150661_c, var2.field_150658_d, var2.field_150659_e));
/* 262:    */         } else {
/* 263:306 */           this.field_150657_g.remove(var1--);
/* 264:    */         }
/* 265:    */       }
/* 266:    */     }
/* 267:    */     
/* 268:    */     private boolean func_150646_a(int p_150646_1_, int p_150646_2_, int p_150646_3_)
/* 269:    */     {
/* 270:313 */       return BlockRailBase.func_150049_b_(this.field_150660_b, p_150646_1_, p_150646_2_ + 1, p_150646_3_) ? true : BlockRailBase.func_150049_b_(this.field_150660_b, p_150646_1_, p_150646_2_, p_150646_3_) ? true : BlockRailBase.func_150049_b_(this.field_150660_b, p_150646_1_, p_150646_2_ - 1, p_150646_3_);
/* 271:    */     }
/* 272:    */     
/* 273:    */     private Rail func_150654_a(ChunkPosition p_150654_1_)
/* 274:    */     {
/* 275:318 */       BlockRailBase tmp30_27 = BlockRailBase.this;tmp30_27.getClass(); BlockRailBase tmp89_86 = BlockRailBase.this;tmp89_86.getClass(); BlockRailBase tmp150_147 = BlockRailBase.this;tmp150_147.getClass();return BlockRailBase.func_150049_b_(this.field_150660_b, p_150654_1_.field_151329_a, p_150654_1_.field_151327_b - 1, p_150654_1_.field_151328_c) ? new Rail(tmp150_147, this.field_150660_b, p_150654_1_.field_151329_a, p_150654_1_.field_151327_b - 1, p_150654_1_.field_151328_c) : BlockRailBase.func_150049_b_(this.field_150660_b, p_150654_1_.field_151329_a, p_150654_1_.field_151327_b + 1, p_150654_1_.field_151328_c) ? new Rail(tmp89_86, this.field_150660_b, p_150654_1_.field_151329_a, p_150654_1_.field_151327_b + 1, p_150654_1_.field_151328_c) : BlockRailBase.func_150049_b_(this.field_150660_b, p_150654_1_.field_151329_a, p_150654_1_.field_151327_b, p_150654_1_.field_151328_c) ? new Rail(tmp30_27, this.field_150660_b, p_150654_1_.field_151329_a, p_150654_1_.field_151327_b, p_150654_1_.field_151328_c) : null;
/* 276:    */     }
/* 277:    */     
/* 278:    */     private boolean func_150653_a(Rail p_150653_1_)
/* 279:    */     {
/* 280:323 */       for (int var2 = 0; var2 < this.field_150657_g.size(); var2++)
/* 281:    */       {
/* 282:325 */         ChunkPosition var3 = (ChunkPosition)this.field_150657_g.get(var2);
/* 283:327 */         if ((var3.field_151329_a == p_150653_1_.field_150661_c) && (var3.field_151328_c == p_150653_1_.field_150659_e)) {
/* 284:329 */           return true;
/* 285:    */         }
/* 286:    */       }
/* 287:333 */       return false;
/* 288:    */     }
/* 289:    */     
/* 290:    */     private boolean func_150652_b(int p_150652_1_, int p_150652_2_, int p_150652_3_)
/* 291:    */     {
/* 292:338 */       for (int var4 = 0; var4 < this.field_150657_g.size(); var4++)
/* 293:    */       {
/* 294:340 */         ChunkPosition var5 = (ChunkPosition)this.field_150657_g.get(var4);
/* 295:342 */         if ((var5.field_151329_a == p_150652_1_) && (var5.field_151328_c == p_150652_3_)) {
/* 296:344 */           return true;
/* 297:    */         }
/* 298:    */       }
/* 299:348 */       return false;
/* 300:    */     }
/* 301:    */     
/* 302:    */     protected int func_150650_a()
/* 303:    */     {
/* 304:353 */       int var1 = 0;
/* 305:355 */       if (func_150646_a(this.field_150661_c, this.field_150658_d, this.field_150659_e - 1)) {
/* 306:357 */         var1++;
/* 307:    */       }
/* 308:360 */       if (func_150646_a(this.field_150661_c, this.field_150658_d, this.field_150659_e + 1)) {
/* 309:362 */         var1++;
/* 310:    */       }
/* 311:365 */       if (func_150646_a(this.field_150661_c - 1, this.field_150658_d, this.field_150659_e)) {
/* 312:367 */         var1++;
/* 313:    */       }
/* 314:370 */       if (func_150646_a(this.field_150661_c + 1, this.field_150658_d, this.field_150659_e)) {
/* 315:372 */         var1++;
/* 316:    */       }
/* 317:375 */       return var1;
/* 318:    */     }
/* 319:    */     
/* 320:    */     private boolean func_150649_b(Rail p_150649_1_)
/* 321:    */     {
/* 322:380 */       return func_150653_a(p_150649_1_);
/* 323:    */     }
/* 324:    */     
/* 325:    */     private void func_150645_c(Rail p_150645_1_)
/* 326:    */     {
/* 327:385 */       this.field_150657_g.add(new ChunkPosition(p_150645_1_.field_150661_c, p_150645_1_.field_150658_d, p_150645_1_.field_150659_e));
/* 328:386 */       boolean var2 = func_150652_b(this.field_150661_c, this.field_150658_d, this.field_150659_e - 1);
/* 329:387 */       boolean var3 = func_150652_b(this.field_150661_c, this.field_150658_d, this.field_150659_e + 1);
/* 330:388 */       boolean var4 = func_150652_b(this.field_150661_c - 1, this.field_150658_d, this.field_150659_e);
/* 331:389 */       boolean var5 = func_150652_b(this.field_150661_c + 1, this.field_150658_d, this.field_150659_e);
/* 332:390 */       byte var6 = -1;
/* 333:392 */       if ((var2) || (var3)) {
/* 334:394 */         var6 = 0;
/* 335:    */       }
/* 336:397 */       if ((var4) || (var5)) {
/* 337:399 */         var6 = 1;
/* 338:    */       }
/* 339:402 */       if (!this.field_150656_f)
/* 340:    */       {
/* 341:404 */         if ((var3) && (var5) && (!var2) && (!var4)) {
/* 342:406 */           var6 = 6;
/* 343:    */         }
/* 344:409 */         if ((var3) && (var4) && (!var2) && (!var5)) {
/* 345:411 */           var6 = 7;
/* 346:    */         }
/* 347:414 */         if ((var2) && (var4) && (!var3) && (!var5)) {
/* 348:416 */           var6 = 8;
/* 349:    */         }
/* 350:419 */         if ((var2) && (var5) && (!var3) && (!var4)) {
/* 351:421 */           var6 = 9;
/* 352:    */         }
/* 353:    */       }
/* 354:425 */       if (var6 == 0)
/* 355:    */       {
/* 356:427 */         if (BlockRailBase.func_150049_b_(this.field_150660_b, this.field_150661_c, this.field_150658_d + 1, this.field_150659_e - 1)) {
/* 357:429 */           var6 = 4;
/* 358:    */         }
/* 359:432 */         if (BlockRailBase.func_150049_b_(this.field_150660_b, this.field_150661_c, this.field_150658_d + 1, this.field_150659_e + 1)) {
/* 360:434 */           var6 = 5;
/* 361:    */         }
/* 362:    */       }
/* 363:438 */       if (var6 == 1)
/* 364:    */       {
/* 365:440 */         if (BlockRailBase.func_150049_b_(this.field_150660_b, this.field_150661_c + 1, this.field_150658_d + 1, this.field_150659_e)) {
/* 366:442 */           var6 = 2;
/* 367:    */         }
/* 368:445 */         if (BlockRailBase.func_150049_b_(this.field_150660_b, this.field_150661_c - 1, this.field_150658_d + 1, this.field_150659_e)) {
/* 369:447 */           var6 = 3;
/* 370:    */         }
/* 371:    */       }
/* 372:451 */       if (var6 < 0) {
/* 373:453 */         var6 = 0;
/* 374:    */       }
/* 375:456 */       int var7 = var6;
/* 376:458 */       if (this.field_150656_f) {
/* 377:460 */         var7 = this.field_150660_b.getBlockMetadata(this.field_150661_c, this.field_150658_d, this.field_150659_e) & 0x8 | var6;
/* 378:    */       }
/* 379:463 */       this.field_150660_b.setBlockMetadataWithNotify(this.field_150661_c, this.field_150658_d, this.field_150659_e, var7, 3);
/* 380:    */     }
/* 381:    */     
/* 382:    */     private boolean func_150647_c(int p_150647_1_, int p_150647_2_, int p_150647_3_)
/* 383:    */     {
/* 384:468 */       Rail var4 = func_150654_a(new ChunkPosition(p_150647_1_, p_150647_2_, p_150647_3_));
/* 385:470 */       if (var4 == null) {
/* 386:472 */         return false;
/* 387:    */       }
/* 388:476 */       var4.func_150651_b();
/* 389:477 */       return var4.func_150649_b(this);
/* 390:    */     }
/* 391:    */     
/* 392:    */     public void func_150655_a(boolean p_150655_1_, boolean p_150655_2_)
/* 393:    */     {
/* 394:483 */       boolean var3 = func_150647_c(this.field_150661_c, this.field_150658_d, this.field_150659_e - 1);
/* 395:484 */       boolean var4 = func_150647_c(this.field_150661_c, this.field_150658_d, this.field_150659_e + 1);
/* 396:485 */       boolean var5 = func_150647_c(this.field_150661_c - 1, this.field_150658_d, this.field_150659_e);
/* 397:486 */       boolean var6 = func_150647_c(this.field_150661_c + 1, this.field_150658_d, this.field_150659_e);
/* 398:487 */       byte var7 = -1;
/* 399:489 */       if (((var3) || (var4)) && (!var5) && (!var6)) {
/* 400:491 */         var7 = 0;
/* 401:    */       }
/* 402:494 */       if (((var5) || (var6)) && (!var3) && (!var4)) {
/* 403:496 */         var7 = 1;
/* 404:    */       }
/* 405:499 */       if (!this.field_150656_f)
/* 406:    */       {
/* 407:501 */         if ((var4) && (var6) && (!var3) && (!var5)) {
/* 408:503 */           var7 = 6;
/* 409:    */         }
/* 410:506 */         if ((var4) && (var5) && (!var3) && (!var6)) {
/* 411:508 */           var7 = 7;
/* 412:    */         }
/* 413:511 */         if ((var3) && (var5) && (!var4) && (!var6)) {
/* 414:513 */           var7 = 8;
/* 415:    */         }
/* 416:516 */         if ((var3) && (var6) && (!var4) && (!var5)) {
/* 417:518 */           var7 = 9;
/* 418:    */         }
/* 419:    */       }
/* 420:522 */       if (var7 == -1)
/* 421:    */       {
/* 422:524 */         if ((var3) || (var4)) {
/* 423:526 */           var7 = 0;
/* 424:    */         }
/* 425:529 */         if ((var5) || (var6)) {
/* 426:531 */           var7 = 1;
/* 427:    */         }
/* 428:534 */         if (!this.field_150656_f) {
/* 429:536 */           if (p_150655_1_)
/* 430:    */           {
/* 431:538 */             if ((var4) && (var6)) {
/* 432:540 */               var7 = 6;
/* 433:    */             }
/* 434:543 */             if ((var5) && (var4)) {
/* 435:545 */               var7 = 7;
/* 436:    */             }
/* 437:548 */             if ((var6) && (var3)) {
/* 438:550 */               var7 = 9;
/* 439:    */             }
/* 440:553 */             if ((var3) && (var5)) {
/* 441:555 */               var7 = 8;
/* 442:    */             }
/* 443:    */           }
/* 444:    */           else
/* 445:    */           {
/* 446:560 */             if ((var3) && (var5)) {
/* 447:562 */               var7 = 8;
/* 448:    */             }
/* 449:565 */             if ((var6) && (var3)) {
/* 450:567 */               var7 = 9;
/* 451:    */             }
/* 452:570 */             if ((var5) && (var4)) {
/* 453:572 */               var7 = 7;
/* 454:    */             }
/* 455:575 */             if ((var4) && (var6)) {
/* 456:577 */               var7 = 6;
/* 457:    */             }
/* 458:    */           }
/* 459:    */         }
/* 460:    */       }
/* 461:583 */       if (var7 == 0)
/* 462:    */       {
/* 463:585 */         if (BlockRailBase.func_150049_b_(this.field_150660_b, this.field_150661_c, this.field_150658_d + 1, this.field_150659_e - 1)) {
/* 464:587 */           var7 = 4;
/* 465:    */         }
/* 466:590 */         if (BlockRailBase.func_150049_b_(this.field_150660_b, this.field_150661_c, this.field_150658_d + 1, this.field_150659_e + 1)) {
/* 467:592 */           var7 = 5;
/* 468:    */         }
/* 469:    */       }
/* 470:596 */       if (var7 == 1)
/* 471:    */       {
/* 472:598 */         if (BlockRailBase.func_150049_b_(this.field_150660_b, this.field_150661_c + 1, this.field_150658_d + 1, this.field_150659_e)) {
/* 473:600 */           var7 = 2;
/* 474:    */         }
/* 475:603 */         if (BlockRailBase.func_150049_b_(this.field_150660_b, this.field_150661_c - 1, this.field_150658_d + 1, this.field_150659_e)) {
/* 476:605 */           var7 = 3;
/* 477:    */         }
/* 478:    */       }
/* 479:609 */       if (var7 < 0) {
/* 480:611 */         var7 = 0;
/* 481:    */       }
/* 482:614 */       func_150648_a(var7);
/* 483:615 */       int var8 = var7;
/* 484:617 */       if (this.field_150656_f) {
/* 485:619 */         var8 = this.field_150660_b.getBlockMetadata(this.field_150661_c, this.field_150658_d, this.field_150659_e) & 0x8 | var7;
/* 486:    */       }
/* 487:622 */       if ((p_150655_2_) || (this.field_150660_b.getBlockMetadata(this.field_150661_c, this.field_150658_d, this.field_150659_e) != var8))
/* 488:    */       {
/* 489:624 */         this.field_150660_b.setBlockMetadataWithNotify(this.field_150661_c, this.field_150658_d, this.field_150659_e, var8, 3);
/* 490:626 */         for (int var9 = 0; var9 < this.field_150657_g.size(); var9++)
/* 491:    */         {
/* 492:628 */           Rail var10 = func_150654_a((ChunkPosition)this.field_150657_g.get(var9));
/* 493:630 */           if (var10 != null)
/* 494:    */           {
/* 495:632 */             var10.func_150651_b();
/* 496:634 */             if (var10.func_150649_b(this)) {
/* 497:636 */               var10.func_150645_c(this);
/* 498:    */             }
/* 499:    */           }
/* 500:    */         }
/* 501:    */       }
/* 502:    */     }
/* 503:    */   }
/* 504:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockRailBase
 * JD-Core Version:    0.7.0.1
 */