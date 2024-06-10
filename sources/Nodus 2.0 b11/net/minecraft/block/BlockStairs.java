/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.material.MapColor;
/*   6:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   7:    */ import net.minecraft.creativetab.CreativeTabs;
/*   8:    */ import net.minecraft.entity.Entity;
/*   9:    */ import net.minecraft.entity.EntityLivingBase;
/*  10:    */ import net.minecraft.entity.player.EntityPlayer;
/*  11:    */ import net.minecraft.init.Blocks;
/*  12:    */ import net.minecraft.item.ItemStack;
/*  13:    */ import net.minecraft.util.AxisAlignedBB;
/*  14:    */ import net.minecraft.util.IIcon;
/*  15:    */ import net.minecraft.util.MathHelper;
/*  16:    */ import net.minecraft.util.MovingObjectPosition;
/*  17:    */ import net.minecraft.util.Vec3;
/*  18:    */ import net.minecraft.world.Explosion;
/*  19:    */ import net.minecraft.world.IBlockAccess;
/*  20:    */ import net.minecraft.world.World;
/*  21:    */ 
/*  22:    */ public class BlockStairs
/*  23:    */   extends Block
/*  24:    */ {
/*  25: 24 */   private static final int[][] field_150150_a = { { 2, 6 }, { 3, 7 }, { 2, 3 }, { 6, 7 }, { 0, 4 }, { 1, 5 }, { 0, 1 }, { 4, 5 } };
/*  26:    */   private final Block field_150149_b;
/*  27:    */   private final int field_150151_M;
/*  28:    */   private boolean field_150152_N;
/*  29:    */   private int field_150153_O;
/*  30:    */   private static final String __OBFID = "CL_00000314";
/*  31:    */   
/*  32:    */   protected BlockStairs(Block p_i45428_1_, int p_i45428_2_)
/*  33:    */   {
/*  34: 33 */     super(p_i45428_1_.blockMaterial);
/*  35: 34 */     this.field_150149_b = p_i45428_1_;
/*  36: 35 */     this.field_150151_M = p_i45428_2_;
/*  37: 36 */     setHardness(p_i45428_1_.blockHardness);
/*  38: 37 */     setResistance(p_i45428_1_.blockResistance / 3.0F);
/*  39: 38 */     setStepSound(p_i45428_1_.stepSound);
/*  40: 39 */     setLightOpacity(255);
/*  41: 40 */     setCreativeTab(CreativeTabs.tabBlock);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  45:    */   {
/*  46: 45 */     if (this.field_150152_N) {
/*  47: 47 */       setBlockBounds(0.5F * (this.field_150153_O % 2), 0.5F * (this.field_150153_O / 2 % 2), 0.5F * (this.field_150153_O / 4 % 2), 0.5F + 0.5F * (this.field_150153_O % 2), 0.5F + 0.5F * (this.field_150153_O / 2 % 2), 0.5F + 0.5F * (this.field_150153_O / 4 % 2));
/*  48:    */     } else {
/*  49: 51 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean isOpaqueCube()
/*  54:    */   {
/*  55: 57 */     return false;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean renderAsNormalBlock()
/*  59:    */   {
/*  60: 62 */     return false;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getRenderType()
/*  64:    */   {
/*  65: 70 */     return 10;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void func_150147_e(IBlockAccess p_150147_1_, int p_150147_2_, int p_150147_3_, int p_150147_4_)
/*  69:    */   {
/*  70: 75 */     int var5 = p_150147_1_.getBlockMetadata(p_150147_2_, p_150147_3_, p_150147_4_);
/*  71: 77 */     if ((var5 & 0x4) != 0) {
/*  72: 79 */       setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  73:    */     } else {
/*  74: 83 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static boolean func_150148_a(Block p_150148_0_)
/*  79:    */   {
/*  80: 89 */     return p_150148_0_ instanceof BlockStairs;
/*  81:    */   }
/*  82:    */   
/*  83:    */   private boolean func_150146_f(IBlockAccess p_150146_1_, int p_150146_2_, int p_150146_3_, int p_150146_4_, int p_150146_5_)
/*  84:    */   {
/*  85: 94 */     Block var6 = p_150146_1_.getBlock(p_150146_2_, p_150146_3_, p_150146_4_);
/*  86: 95 */     return (func_150148_a(var6)) && (p_150146_1_.getBlockMetadata(p_150146_2_, p_150146_3_, p_150146_4_) == p_150146_5_);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean func_150145_f(IBlockAccess p_150145_1_, int p_150145_2_, int p_150145_3_, int p_150145_4_)
/*  90:    */   {
/*  91:100 */     int var5 = p_150145_1_.getBlockMetadata(p_150145_2_, p_150145_3_, p_150145_4_);
/*  92:101 */     int var6 = var5 & 0x3;
/*  93:102 */     float var7 = 0.5F;
/*  94:103 */     float var8 = 1.0F;
/*  95:105 */     if ((var5 & 0x4) != 0)
/*  96:    */     {
/*  97:107 */       var7 = 0.0F;
/*  98:108 */       var8 = 0.5F;
/*  99:    */     }
/* 100:111 */     float var9 = 0.0F;
/* 101:112 */     float var10 = 1.0F;
/* 102:113 */     float var11 = 0.0F;
/* 103:114 */     float var12 = 0.5F;
/* 104:115 */     boolean var13 = true;
/* 105:120 */     if (var6 == 0)
/* 106:    */     {
/* 107:122 */       var9 = 0.5F;
/* 108:123 */       var12 = 1.0F;
/* 109:124 */       Block var14 = p_150145_1_.getBlock(p_150145_2_ + 1, p_150145_3_, p_150145_4_);
/* 110:125 */       int var15 = p_150145_1_.getBlockMetadata(p_150145_2_ + 1, p_150145_3_, p_150145_4_);
/* 111:127 */       if ((func_150148_a(var14)) && ((var5 & 0x4) == (var15 & 0x4)))
/* 112:    */       {
/* 113:129 */         int var16 = var15 & 0x3;
/* 114:131 */         if ((var16 == 3) && (!func_150146_f(p_150145_1_, p_150145_2_, p_150145_3_, p_150145_4_ + 1, var5)))
/* 115:    */         {
/* 116:133 */           var12 = 0.5F;
/* 117:134 */           var13 = false;
/* 118:    */         }
/* 119:136 */         else if ((var16 == 2) && (!func_150146_f(p_150145_1_, p_150145_2_, p_150145_3_, p_150145_4_ - 1, var5)))
/* 120:    */         {
/* 121:138 */           var11 = 0.5F;
/* 122:139 */           var13 = false;
/* 123:    */         }
/* 124:    */       }
/* 125:    */     }
/* 126:143 */     else if (var6 == 1)
/* 127:    */     {
/* 128:145 */       var10 = 0.5F;
/* 129:146 */       var12 = 1.0F;
/* 130:147 */       Block var14 = p_150145_1_.getBlock(p_150145_2_ - 1, p_150145_3_, p_150145_4_);
/* 131:148 */       int var15 = p_150145_1_.getBlockMetadata(p_150145_2_ - 1, p_150145_3_, p_150145_4_);
/* 132:150 */       if ((func_150148_a(var14)) && ((var5 & 0x4) == (var15 & 0x4)))
/* 133:    */       {
/* 134:152 */         int var16 = var15 & 0x3;
/* 135:154 */         if ((var16 == 3) && (!func_150146_f(p_150145_1_, p_150145_2_, p_150145_3_, p_150145_4_ + 1, var5)))
/* 136:    */         {
/* 137:156 */           var12 = 0.5F;
/* 138:157 */           var13 = false;
/* 139:    */         }
/* 140:159 */         else if ((var16 == 2) && (!func_150146_f(p_150145_1_, p_150145_2_, p_150145_3_, p_150145_4_ - 1, var5)))
/* 141:    */         {
/* 142:161 */           var11 = 0.5F;
/* 143:162 */           var13 = false;
/* 144:    */         }
/* 145:    */       }
/* 146:    */     }
/* 147:166 */     else if (var6 == 2)
/* 148:    */     {
/* 149:168 */       var11 = 0.5F;
/* 150:169 */       var12 = 1.0F;
/* 151:170 */       Block var14 = p_150145_1_.getBlock(p_150145_2_, p_150145_3_, p_150145_4_ + 1);
/* 152:171 */       int var15 = p_150145_1_.getBlockMetadata(p_150145_2_, p_150145_3_, p_150145_4_ + 1);
/* 153:173 */       if ((func_150148_a(var14)) && ((var5 & 0x4) == (var15 & 0x4)))
/* 154:    */       {
/* 155:175 */         int var16 = var15 & 0x3;
/* 156:177 */         if ((var16 == 1) && (!func_150146_f(p_150145_1_, p_150145_2_ + 1, p_150145_3_, p_150145_4_, var5)))
/* 157:    */         {
/* 158:179 */           var10 = 0.5F;
/* 159:180 */           var13 = false;
/* 160:    */         }
/* 161:182 */         else if ((var16 == 0) && (!func_150146_f(p_150145_1_, p_150145_2_ - 1, p_150145_3_, p_150145_4_, var5)))
/* 162:    */         {
/* 163:184 */           var9 = 0.5F;
/* 164:185 */           var13 = false;
/* 165:    */         }
/* 166:    */       }
/* 167:    */     }
/* 168:189 */     else if (var6 == 3)
/* 169:    */     {
/* 170:191 */       Block var14 = p_150145_1_.getBlock(p_150145_2_, p_150145_3_, p_150145_4_ - 1);
/* 171:192 */       int var15 = p_150145_1_.getBlockMetadata(p_150145_2_, p_150145_3_, p_150145_4_ - 1);
/* 172:194 */       if ((func_150148_a(var14)) && ((var5 & 0x4) == (var15 & 0x4)))
/* 173:    */       {
/* 174:196 */         int var16 = var15 & 0x3;
/* 175:198 */         if ((var16 == 1) && (!func_150146_f(p_150145_1_, p_150145_2_ + 1, p_150145_3_, p_150145_4_, var5)))
/* 176:    */         {
/* 177:200 */           var10 = 0.5F;
/* 178:201 */           var13 = false;
/* 179:    */         }
/* 180:203 */         else if ((var16 == 0) && (!func_150146_f(p_150145_1_, p_150145_2_ - 1, p_150145_3_, p_150145_4_, var5)))
/* 181:    */         {
/* 182:205 */           var9 = 0.5F;
/* 183:206 */           var13 = false;
/* 184:    */         }
/* 185:    */       }
/* 186:    */     }
/* 187:211 */     setBlockBounds(var9, var7, var11, var10, var8, var12);
/* 188:212 */     return var13;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public boolean func_150144_g(IBlockAccess p_150144_1_, int p_150144_2_, int p_150144_3_, int p_150144_4_)
/* 192:    */   {
/* 193:217 */     int var5 = p_150144_1_.getBlockMetadata(p_150144_2_, p_150144_3_, p_150144_4_);
/* 194:218 */     int var6 = var5 & 0x3;
/* 195:219 */     float var7 = 0.5F;
/* 196:220 */     float var8 = 1.0F;
/* 197:222 */     if ((var5 & 0x4) != 0)
/* 198:    */     {
/* 199:224 */       var7 = 0.0F;
/* 200:225 */       var8 = 0.5F;
/* 201:    */     }
/* 202:228 */     float var9 = 0.0F;
/* 203:229 */     float var10 = 0.5F;
/* 204:230 */     float var11 = 0.5F;
/* 205:231 */     float var12 = 1.0F;
/* 206:232 */     boolean var13 = false;
/* 207:237 */     if (var6 == 0)
/* 208:    */     {
/* 209:239 */       Block var14 = p_150144_1_.getBlock(p_150144_2_ - 1, p_150144_3_, p_150144_4_);
/* 210:240 */       int var15 = p_150144_1_.getBlockMetadata(p_150144_2_ - 1, p_150144_3_, p_150144_4_);
/* 211:242 */       if ((func_150148_a(var14)) && ((var5 & 0x4) == (var15 & 0x4)))
/* 212:    */       {
/* 213:244 */         int var16 = var15 & 0x3;
/* 214:246 */         if ((var16 == 3) && (!func_150146_f(p_150144_1_, p_150144_2_, p_150144_3_, p_150144_4_ - 1, var5)))
/* 215:    */         {
/* 216:248 */           var11 = 0.0F;
/* 217:249 */           var12 = 0.5F;
/* 218:250 */           var13 = true;
/* 219:    */         }
/* 220:252 */         else if ((var16 == 2) && (!func_150146_f(p_150144_1_, p_150144_2_, p_150144_3_, p_150144_4_ + 1, var5)))
/* 221:    */         {
/* 222:254 */           var11 = 0.5F;
/* 223:255 */           var12 = 1.0F;
/* 224:256 */           var13 = true;
/* 225:    */         }
/* 226:    */       }
/* 227:    */     }
/* 228:260 */     else if (var6 == 1)
/* 229:    */     {
/* 230:262 */       Block var14 = p_150144_1_.getBlock(p_150144_2_ + 1, p_150144_3_, p_150144_4_);
/* 231:263 */       int var15 = p_150144_1_.getBlockMetadata(p_150144_2_ + 1, p_150144_3_, p_150144_4_);
/* 232:265 */       if ((func_150148_a(var14)) && ((var5 & 0x4) == (var15 & 0x4)))
/* 233:    */       {
/* 234:267 */         var9 = 0.5F;
/* 235:268 */         var10 = 1.0F;
/* 236:269 */         int var16 = var15 & 0x3;
/* 237:271 */         if ((var16 == 3) && (!func_150146_f(p_150144_1_, p_150144_2_, p_150144_3_, p_150144_4_ - 1, var5)))
/* 238:    */         {
/* 239:273 */           var11 = 0.0F;
/* 240:274 */           var12 = 0.5F;
/* 241:275 */           var13 = true;
/* 242:    */         }
/* 243:277 */         else if ((var16 == 2) && (!func_150146_f(p_150144_1_, p_150144_2_, p_150144_3_, p_150144_4_ + 1, var5)))
/* 244:    */         {
/* 245:279 */           var11 = 0.5F;
/* 246:280 */           var12 = 1.0F;
/* 247:281 */           var13 = true;
/* 248:    */         }
/* 249:    */       }
/* 250:    */     }
/* 251:285 */     else if (var6 == 2)
/* 252:    */     {
/* 253:287 */       Block var14 = p_150144_1_.getBlock(p_150144_2_, p_150144_3_, p_150144_4_ - 1);
/* 254:288 */       int var15 = p_150144_1_.getBlockMetadata(p_150144_2_, p_150144_3_, p_150144_4_ - 1);
/* 255:290 */       if ((func_150148_a(var14)) && ((var5 & 0x4) == (var15 & 0x4)))
/* 256:    */       {
/* 257:292 */         var11 = 0.0F;
/* 258:293 */         var12 = 0.5F;
/* 259:294 */         int var16 = var15 & 0x3;
/* 260:296 */         if ((var16 == 1) && (!func_150146_f(p_150144_1_, p_150144_2_ - 1, p_150144_3_, p_150144_4_, var5)))
/* 261:    */         {
/* 262:298 */           var13 = true;
/* 263:    */         }
/* 264:300 */         else if ((var16 == 0) && (!func_150146_f(p_150144_1_, p_150144_2_ + 1, p_150144_3_, p_150144_4_, var5)))
/* 265:    */         {
/* 266:302 */           var9 = 0.5F;
/* 267:303 */           var10 = 1.0F;
/* 268:304 */           var13 = true;
/* 269:    */         }
/* 270:    */       }
/* 271:    */     }
/* 272:308 */     else if (var6 == 3)
/* 273:    */     {
/* 274:310 */       Block var14 = p_150144_1_.getBlock(p_150144_2_, p_150144_3_, p_150144_4_ + 1);
/* 275:311 */       int var15 = p_150144_1_.getBlockMetadata(p_150144_2_, p_150144_3_, p_150144_4_ + 1);
/* 276:313 */       if ((func_150148_a(var14)) && ((var5 & 0x4) == (var15 & 0x4)))
/* 277:    */       {
/* 278:315 */         int var16 = var15 & 0x3;
/* 279:317 */         if ((var16 == 1) && (!func_150146_f(p_150144_1_, p_150144_2_ - 1, p_150144_3_, p_150144_4_, var5)))
/* 280:    */         {
/* 281:319 */           var13 = true;
/* 282:    */         }
/* 283:321 */         else if ((var16 == 0) && (!func_150146_f(p_150144_1_, p_150144_2_ + 1, p_150144_3_, p_150144_4_, var5)))
/* 284:    */         {
/* 285:323 */           var9 = 0.5F;
/* 286:324 */           var10 = 1.0F;
/* 287:325 */           var13 = true;
/* 288:    */         }
/* 289:    */       }
/* 290:    */     }
/* 291:330 */     if (var13) {
/* 292:332 */       setBlockBounds(var9, var7, var11, var10, var8, var12);
/* 293:    */     }
/* 294:335 */     return var13;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_)
/* 298:    */   {
/* 299:340 */     func_150147_e(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_);
/* 300:341 */     super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/* 301:342 */     boolean var8 = func_150145_f(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_);
/* 302:343 */     super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/* 303:345 */     if ((var8) && (func_150144_g(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_))) {
/* 304:347 */       super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/* 305:    */     }
/* 306:350 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
/* 310:    */   {
/* 311:358 */     this.field_150149_b.randomDisplayTick(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_, p_149734_5_);
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void onBlockClicked(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_)
/* 315:    */   {
/* 316:366 */     this.field_150149_b.onBlockClicked(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_, p_149699_5_);
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void onBlockDestroyedByPlayer(World p_149664_1_, int p_149664_2_, int p_149664_3_, int p_149664_4_, int p_149664_5_)
/* 320:    */   {
/* 321:371 */     this.field_150149_b.onBlockDestroyedByPlayer(p_149664_1_, p_149664_2_, p_149664_3_, p_149664_4_, p_149664_5_);
/* 322:    */   }
/* 323:    */   
/* 324:    */   public int getBlockBrightness(IBlockAccess p_149677_1_, int p_149677_2_, int p_149677_3_, int p_149677_4_)
/* 325:    */   {
/* 326:376 */     return this.field_150149_b.getBlockBrightness(p_149677_1_, p_149677_2_, p_149677_3_, p_149677_4_);
/* 327:    */   }
/* 328:    */   
/* 329:    */   public float getExplosionResistance(Entity p_149638_1_)
/* 330:    */   {
/* 331:384 */     return this.field_150149_b.getExplosionResistance(p_149638_1_);
/* 332:    */   }
/* 333:    */   
/* 334:    */   public int getRenderBlockPass()
/* 335:    */   {
/* 336:392 */     return this.field_150149_b.getRenderBlockPass();
/* 337:    */   }
/* 338:    */   
/* 339:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 340:    */   {
/* 341:400 */     return this.field_150149_b.getIcon(p_149691_1_, this.field_150151_M);
/* 342:    */   }
/* 343:    */   
/* 344:    */   public int func_149738_a(World p_149738_1_)
/* 345:    */   {
/* 346:405 */     return this.field_150149_b.func_149738_a(p_149738_1_);
/* 347:    */   }
/* 348:    */   
/* 349:    */   public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
/* 350:    */   {
/* 351:413 */     return this.field_150149_b.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
/* 352:    */   }
/* 353:    */   
/* 354:    */   public void velocityToAddToEntity(World p_149640_1_, int p_149640_2_, int p_149640_3_, int p_149640_4_, Entity p_149640_5_, Vec3 p_149640_6_)
/* 355:    */   {
/* 356:418 */     this.field_150149_b.velocityToAddToEntity(p_149640_1_, p_149640_2_, p_149640_3_, p_149640_4_, p_149640_5_, p_149640_6_);
/* 357:    */   }
/* 358:    */   
/* 359:    */   public boolean isCollidable()
/* 360:    */   {
/* 361:423 */     return this.field_150149_b.isCollidable();
/* 362:    */   }
/* 363:    */   
/* 364:    */   public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_)
/* 365:    */   {
/* 366:432 */     return this.field_150149_b.canCollideCheck(p_149678_1_, p_149678_2_);
/* 367:    */   }
/* 368:    */   
/* 369:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/* 370:    */   {
/* 371:437 */     return this.field_150149_b.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_);
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/* 375:    */   {
/* 376:442 */     onNeighborBlockChange(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_, Blocks.air);
/* 377:443 */     this.field_150149_b.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/* 381:    */   {
/* 382:448 */     this.field_150149_b.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/* 383:    */   }
/* 384:    */   
/* 385:    */   public void onEntityWalking(World p_149724_1_, int p_149724_2_, int p_149724_3_, int p_149724_4_, Entity p_149724_5_)
/* 386:    */   {
/* 387:453 */     this.field_150149_b.onEntityWalking(p_149724_1_, p_149724_2_, p_149724_3_, p_149724_4_, p_149724_5_);
/* 388:    */   }
/* 389:    */   
/* 390:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/* 391:    */   {
/* 392:461 */     this.field_150149_b.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
/* 393:    */   }
/* 394:    */   
/* 395:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/* 396:    */   {
/* 397:469 */     return this.field_150149_b.onBlockActivated(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, p_149727_5_, 0, 0.0F, 0.0F, 0.0F);
/* 398:    */   }
/* 399:    */   
/* 400:    */   public void onBlockDestroyedByExplosion(World p_149723_1_, int p_149723_2_, int p_149723_3_, int p_149723_4_, Explosion p_149723_5_)
/* 401:    */   {
/* 402:477 */     this.field_150149_b.onBlockDestroyedByExplosion(p_149723_1_, p_149723_2_, p_149723_3_, p_149723_4_, p_149723_5_);
/* 403:    */   }
/* 404:    */   
/* 405:    */   public MapColor getMapColor(int p_149728_1_)
/* 406:    */   {
/* 407:482 */     return this.field_150149_b.getMapColor(this.field_150151_M);
/* 408:    */   }
/* 409:    */   
/* 410:    */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
/* 411:    */   {
/* 412:490 */     int var7 = MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
/* 413:491 */     int var8 = p_149689_1_.getBlockMetadata(p_149689_2_, p_149689_3_, p_149689_4_) & 0x4;
/* 414:493 */     if (var7 == 0) {
/* 415:495 */       p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x2 | var8, 2);
/* 416:    */     }
/* 417:498 */     if (var7 == 1) {
/* 418:500 */       p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x1 | var8, 2);
/* 419:    */     }
/* 420:503 */     if (var7 == 2) {
/* 421:505 */       p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x3 | var8, 2);
/* 422:    */     }
/* 423:508 */     if (var7 == 3) {
/* 424:510 */       p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var8, 2);
/* 425:    */     }
/* 426:    */   }
/* 427:    */   
/* 428:    */   public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
/* 429:    */   {
/* 430:516 */     return (p_149660_5_ != 0) && ((p_149660_5_ == 1) || (p_149660_7_ <= 0.5D)) ? p_149660_9_ : p_149660_9_ | 0x4;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public MovingObjectPosition collisionRayTrace(World p_149731_1_, int p_149731_2_, int p_149731_3_, int p_149731_4_, Vec3 p_149731_5_, Vec3 p_149731_6_)
/* 434:    */   {
/* 435:521 */     MovingObjectPosition[] var7 = new MovingObjectPosition[8];
/* 436:522 */     int var8 = p_149731_1_.getBlockMetadata(p_149731_2_, p_149731_3_, p_149731_4_);
/* 437:523 */     int var9 = var8 & 0x3;
/* 438:524 */     boolean var10 = (var8 & 0x4) == 4;
/* 439:525 */     int[] var11 = field_150150_a[(var9 + 0)];
/* 440:526 */     this.field_150152_N = true;
/* 441:531 */     for (int var12 = 0; var12 < 8; var12++)
/* 442:    */     {
/* 443:533 */       this.field_150153_O = var12;
/* 444:534 */       int[] var13 = var11;
/* 445:535 */       int var14 = var11.length;
/* 446:537 */       for (int var15 = 0; var15 < var14; var15++)
/* 447:    */       {
/* 448:539 */         int var16 = var13[var15];
/* 449:541 */         if (var16 != var12) {}
/* 450:    */       }
/* 451:547 */       var7[var12] = super.collisionRayTrace(p_149731_1_, p_149731_2_, p_149731_3_, p_149731_4_, p_149731_5_, p_149731_6_);
/* 452:    */     }
/* 453:550 */     int[] var21 = var11;
/* 454:551 */     int var24 = var11.length;
/* 455:553 */     for (int var14 = 0; var14 < var24; var14++)
/* 456:    */     {
/* 457:555 */       int var15 = var21[var14];
/* 458:556 */       var7[var15] = null;
/* 459:    */     }
/* 460:559 */     MovingObjectPosition var23 = null;
/* 461:560 */     double var22 = 0.0D;
/* 462:561 */     MovingObjectPosition[] var25 = var7;
/* 463:562 */     int var16 = var7.length;
/* 464:564 */     for (int var17 = 0; var17 < var16; var17++)
/* 465:    */     {
/* 466:566 */       MovingObjectPosition var18 = var25[var17];
/* 467:568 */       if (var18 != null)
/* 468:    */       {
/* 469:570 */         double var19 = var18.hitVec.squareDistanceTo(p_149731_6_);
/* 470:572 */         if (var19 > var22)
/* 471:    */         {
/* 472:574 */           var23 = var18;
/* 473:575 */           var22 = var19;
/* 474:    */         }
/* 475:    */       }
/* 476:    */     }
/* 477:580 */     return var23;
/* 478:    */   }
/* 479:    */   
/* 480:    */   public void registerBlockIcons(IIconRegister p_149651_1_) {}
/* 481:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockStairs
 * JD-Core Version:    0.7.0.1
 */