/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   7:    */ import net.minecraft.creativetab.CreativeTabs;
/*   8:    */ import net.minecraft.entity.Entity;
/*   9:    */ import net.minecraft.entity.EntityLivingBase;
/*  10:    */ import net.minecraft.entity.player.EntityPlayer;
/*  11:    */ import net.minecraft.init.Blocks;
/*  12:    */ import net.minecraft.item.ItemStack;
/*  13:    */ import net.minecraft.tileentity.TileEntity;
/*  14:    */ import net.minecraft.tileentity.TileEntityPiston;
/*  15:    */ import net.minecraft.util.AxisAlignedBB;
/*  16:    */ import net.minecraft.util.IIcon;
/*  17:    */ import net.minecraft.util.MathHelper;
/*  18:    */ import net.minecraft.world.IBlockAccess;
/*  19:    */ import net.minecraft.world.World;
/*  20:    */ 
/*  21:    */ public class BlockPistonBase
/*  22:    */   extends Block
/*  23:    */ {
/*  24:    */   private final boolean field_150082_a;
/*  25:    */   private IIcon field_150081_b;
/*  26:    */   private IIcon field_150083_M;
/*  27:    */   private IIcon field_150084_N;
/*  28:    */   private static final String __OBFID = "CL_00000366";
/*  29:    */   
/*  30:    */   public BlockPistonBase(boolean p_i45443_1_)
/*  31:    */   {
/*  32: 31 */     super(Material.piston);
/*  33: 32 */     this.field_150082_a = p_i45443_1_;
/*  34: 33 */     setStepSound(soundTypePiston);
/*  35: 34 */     setHardness(0.5F);
/*  36: 35 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public IIcon func_150073_e()
/*  40:    */   {
/*  41: 40 */     return this.field_150084_N;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void func_150070_b(float p_150070_1_, float p_150070_2_, float p_150070_3_, float p_150070_4_, float p_150070_5_, float p_150070_6_)
/*  45:    */   {
/*  46: 45 */     setBlockBounds(p_150070_1_, p_150070_2_, p_150070_3_, p_150070_4_, p_150070_5_, p_150070_6_);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  50:    */   {
/*  51: 53 */     int var3 = func_150076_b(p_149691_2_);
/*  52: 54 */     return p_149691_1_ == net.minecraft.util.Facing.oppositeSide[var3] ? this.field_150083_M : p_149691_1_ == var3 ? this.field_150081_b : (!func_150075_c(p_149691_2_)) && (this.field_149759_B <= 0.0D) && (this.field_149760_C <= 0.0D) && (this.field_149754_D <= 0.0D) && (this.field_149755_E >= 1.0D) && (this.field_149756_F >= 1.0D) && (this.field_149757_G >= 1.0D) ? this.field_150084_N : var3 > 5 ? this.field_150084_N : this.blockIcon;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static IIcon func_150074_e(String p_150074_0_)
/*  56:    */   {
/*  57: 59 */     return p_150074_0_ == "piston_inner" ? Blocks.piston.field_150081_b : p_150074_0_ == "piston_top_sticky" ? Blocks.sticky_piston.field_150084_N : p_150074_0_ == "piston_top_normal" ? Blocks.piston.field_150084_N : p_150074_0_ == "piston_side" ? Blocks.piston.blockIcon : null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/*  61:    */   {
/*  62: 64 */     this.blockIcon = p_149651_1_.registerIcon("piston_side");
/*  63: 65 */     this.field_150084_N = p_149651_1_.registerIcon(this.field_150082_a ? "piston_top_sticky" : "piston_top_normal");
/*  64: 66 */     this.field_150081_b = p_149651_1_.registerIcon("piston_inner");
/*  65: 67 */     this.field_150083_M = p_149651_1_.registerIcon("piston_bottom");
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getRenderType()
/*  69:    */   {
/*  70: 75 */     return 16;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean isOpaqueCube()
/*  74:    */   {
/*  75: 80 */     return false;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  79:    */   {
/*  80: 88 */     return false;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
/*  84:    */   {
/*  85: 96 */     int var7 = func_150071_a(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_);
/*  86: 97 */     p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 2);
/*  87: 99 */     if (!p_149689_1_.isClient) {
/*  88:101 */       func_150078_e(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_);
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/*  93:    */   {
/*  94:107 */     if (!p_149695_1_.isClient) {
/*  95:109 */       func_150078_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/* 100:    */   {
/* 101:115 */     if ((!p_149726_1_.isClient) && (p_149726_1_.getTileEntity(p_149726_2_, p_149726_3_, p_149726_4_) == null)) {
/* 102:117 */       func_150078_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   private void func_150078_e(World p_150078_1_, int p_150078_2_, int p_150078_3_, int p_150078_4_)
/* 107:    */   {
/* 108:123 */     int var5 = p_150078_1_.getBlockMetadata(p_150078_2_, p_150078_3_, p_150078_4_);
/* 109:124 */     int var6 = func_150076_b(var5);
/* 110:126 */     if (var6 != 7)
/* 111:    */     {
/* 112:128 */       boolean var7 = func_150072_a(p_150078_1_, p_150078_2_, p_150078_3_, p_150078_4_, var6);
/* 113:130 */       if ((var7) && (!func_150075_c(var5)))
/* 114:    */       {
/* 115:132 */         if (func_150077_h(p_150078_1_, p_150078_2_, p_150078_3_, p_150078_4_, var6)) {
/* 116:134 */           p_150078_1_.func_147452_c(p_150078_2_, p_150078_3_, p_150078_4_, this, 0, var6);
/* 117:    */         }
/* 118:    */       }
/* 119:137 */       else if ((!var7) && (func_150075_c(var5)))
/* 120:    */       {
/* 121:139 */         p_150078_1_.setBlockMetadataWithNotify(p_150078_2_, p_150078_3_, p_150078_4_, var6, 2);
/* 122:140 */         p_150078_1_.func_147452_c(p_150078_2_, p_150078_3_, p_150078_4_, this, 1, var6);
/* 123:    */       }
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   private boolean func_150072_a(World p_150072_1_, int p_150072_2_, int p_150072_3_, int p_150072_4_, int p_150072_5_)
/* 128:    */   {
/* 129:147 */     return p_150072_1_.getIndirectPowerOutput(p_150072_2_ - 1, p_150072_3_ + 1, p_150072_4_, 4) ? true : p_150072_1_.getIndirectPowerOutput(p_150072_2_, p_150072_3_ + 1, p_150072_4_ + 1, 3) ? true : p_150072_1_.getIndirectPowerOutput(p_150072_2_, p_150072_3_ + 1, p_150072_4_ - 1, 2) ? true : p_150072_1_.getIndirectPowerOutput(p_150072_2_, p_150072_3_ + 2, p_150072_4_, 1) ? true : p_150072_1_.getIndirectPowerOutput(p_150072_2_, p_150072_3_, p_150072_4_, 0) ? true : (p_150072_5_ != 4) && (p_150072_1_.getIndirectPowerOutput(p_150072_2_ - 1, p_150072_3_, p_150072_4_, 4)) ? true : (p_150072_5_ != 5) && (p_150072_1_.getIndirectPowerOutput(p_150072_2_ + 1, p_150072_3_, p_150072_4_, 5)) ? true : (p_150072_5_ != 3) && (p_150072_1_.getIndirectPowerOutput(p_150072_2_, p_150072_3_, p_150072_4_ + 1, 3)) ? true : (p_150072_5_ != 2) && (p_150072_1_.getIndirectPowerOutput(p_150072_2_, p_150072_3_, p_150072_4_ - 1, 2)) ? true : (p_150072_5_ != 1) && (p_150072_1_.getIndirectPowerOutput(p_150072_2_, p_150072_3_ + 1, p_150072_4_, 1)) ? true : (p_150072_5_ != 0) && (p_150072_1_.getIndirectPowerOutput(p_150072_2_, p_150072_3_ - 1, p_150072_4_, 0)) ? true : p_150072_1_.getIndirectPowerOutput(p_150072_2_ + 1, p_150072_3_ + 1, p_150072_4_, 5);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public boolean onBlockEventReceived(World p_149696_1_, int p_149696_2_, int p_149696_3_, int p_149696_4_, int p_149696_5_, int p_149696_6_)
/* 133:    */   {
/* 134:152 */     if (!p_149696_1_.isClient)
/* 135:    */     {
/* 136:154 */       boolean var7 = func_150072_a(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_6_);
/* 137:156 */       if ((var7) && (p_149696_5_ == 1))
/* 138:    */       {
/* 139:158 */         p_149696_1_.setBlockMetadataWithNotify(p_149696_2_, p_149696_3_, p_149696_4_, p_149696_6_ | 0x8, 2);
/* 140:159 */         return false;
/* 141:    */       }
/* 142:162 */       if ((!var7) && (p_149696_5_ == 0)) {
/* 143:164 */         return false;
/* 144:    */       }
/* 145:    */     }
/* 146:168 */     if (p_149696_5_ == 0)
/* 147:    */     {
/* 148:170 */       if (!func_150079_i(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_6_)) {
/* 149:172 */         return false;
/* 150:    */       }
/* 151:175 */       p_149696_1_.setBlockMetadataWithNotify(p_149696_2_, p_149696_3_, p_149696_4_, p_149696_6_ | 0x8, 2);
/* 152:176 */       p_149696_1_.playSoundEffect(p_149696_2_ + 0.5D, p_149696_3_ + 0.5D, p_149696_4_ + 0.5D, "tile.piston.out", 0.5F, p_149696_1_.rand.nextFloat() * 0.25F + 0.6F);
/* 153:    */     }
/* 154:178 */     else if (p_149696_5_ == 1)
/* 155:    */     {
/* 156:180 */       TileEntity var16 = p_149696_1_.getTileEntity(p_149696_2_ + net.minecraft.util.Facing.offsetsXForSide[p_149696_6_], p_149696_3_ + net.minecraft.util.Facing.offsetsYForSide[p_149696_6_], p_149696_4_ + net.minecraft.util.Facing.offsetsZForSide[p_149696_6_]);
/* 157:182 */       if ((var16 instanceof TileEntityPiston)) {
/* 158:184 */         ((TileEntityPiston)var16).func_145866_f();
/* 159:    */       }
/* 160:187 */       p_149696_1_.setBlock(p_149696_2_, p_149696_3_, p_149696_4_, Blocks.piston_extension, p_149696_6_, 3);
/* 161:188 */       p_149696_1_.setTileEntity(p_149696_2_, p_149696_3_, p_149696_4_, BlockPistonMoving.func_149962_a(this, p_149696_6_, p_149696_6_, false, true));
/* 162:190 */       if (this.field_150082_a)
/* 163:    */       {
/* 164:192 */         int var8 = p_149696_2_ + net.minecraft.util.Facing.offsetsXForSide[p_149696_6_] * 2;
/* 165:193 */         int var9 = p_149696_3_ + net.minecraft.util.Facing.offsetsYForSide[p_149696_6_] * 2;
/* 166:194 */         int var10 = p_149696_4_ + net.minecraft.util.Facing.offsetsZForSide[p_149696_6_] * 2;
/* 167:195 */         Block var11 = p_149696_1_.getBlock(var8, var9, var10);
/* 168:196 */         int var12 = p_149696_1_.getBlockMetadata(var8, var9, var10);
/* 169:197 */         boolean var13 = false;
/* 170:199 */         if (var11 == Blocks.piston_extension)
/* 171:    */         {
/* 172:201 */           TileEntity var14 = p_149696_1_.getTileEntity(var8, var9, var10);
/* 173:203 */           if ((var14 instanceof TileEntityPiston))
/* 174:    */           {
/* 175:205 */             TileEntityPiston var15 = (TileEntityPiston)var14;
/* 176:207 */             if ((var15.func_145864_c() == p_149696_6_) && (var15.func_145868_b()))
/* 177:    */             {
/* 178:209 */               var15.func_145866_f();
/* 179:210 */               var11 = var15.func_145861_a();
/* 180:211 */               var12 = var15.getBlockMetadata();
/* 181:212 */               var13 = true;
/* 182:    */             }
/* 183:    */           }
/* 184:    */         }
/* 185:217 */         if ((!var13) && (var11.getMaterial() != Material.air) && (func_150080_a(var11, p_149696_1_, var8, var9, var10, false)) && ((var11.getMobilityFlag() == 0) || (var11 == Blocks.piston) || (var11 == Blocks.sticky_piston)))
/* 186:    */         {
/* 187:219 */           p_149696_2_ += net.minecraft.util.Facing.offsetsXForSide[p_149696_6_];
/* 188:220 */           p_149696_3_ += net.minecraft.util.Facing.offsetsYForSide[p_149696_6_];
/* 189:221 */           p_149696_4_ += net.minecraft.util.Facing.offsetsZForSide[p_149696_6_];
/* 190:222 */           p_149696_1_.setBlock(p_149696_2_, p_149696_3_, p_149696_4_, Blocks.piston_extension, var12, 3);
/* 191:223 */           p_149696_1_.setTileEntity(p_149696_2_, p_149696_3_, p_149696_4_, BlockPistonMoving.func_149962_a(var11, var12, p_149696_6_, false, false));
/* 192:224 */           p_149696_1_.setBlockToAir(var8, var9, var10);
/* 193:    */         }
/* 194:226 */         else if (!var13)
/* 195:    */         {
/* 196:228 */           p_149696_1_.setBlockToAir(p_149696_2_ + net.minecraft.util.Facing.offsetsXForSide[p_149696_6_], p_149696_3_ + net.minecraft.util.Facing.offsetsYForSide[p_149696_6_], p_149696_4_ + net.minecraft.util.Facing.offsetsZForSide[p_149696_6_]);
/* 197:    */         }
/* 198:    */       }
/* 199:    */       else
/* 200:    */       {
/* 201:233 */         p_149696_1_.setBlockToAir(p_149696_2_ + net.minecraft.util.Facing.offsetsXForSide[p_149696_6_], p_149696_3_ + net.minecraft.util.Facing.offsetsYForSide[p_149696_6_], p_149696_4_ + net.minecraft.util.Facing.offsetsZForSide[p_149696_6_]);
/* 202:    */       }
/* 203:236 */       p_149696_1_.playSoundEffect(p_149696_2_ + 0.5D, p_149696_3_ + 0.5D, p_149696_4_ + 0.5D, "tile.piston.in", 0.5F, p_149696_1_.rand.nextFloat() * 0.15F + 0.6F);
/* 204:    */     }
/* 205:239 */     return true;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/* 209:    */   {
/* 210:244 */     int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
/* 211:246 */     if (func_150075_c(var5))
/* 212:    */     {
/* 213:248 */       float var6 = 0.25F;
/* 214:250 */       switch (func_150076_b(var5))
/* 215:    */       {
/* 216:    */       case 0: 
/* 217:253 */         setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 218:254 */         break;
/* 219:    */       case 1: 
/* 220:257 */         setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
/* 221:258 */         break;
/* 222:    */       case 2: 
/* 223:261 */         setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
/* 224:262 */         break;
/* 225:    */       case 3: 
/* 226:265 */         setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
/* 227:266 */         break;
/* 228:    */       case 4: 
/* 229:269 */         setBlockBounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 230:270 */         break;
/* 231:    */       case 5: 
/* 232:273 */         setBlockBounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
/* 233:    */       }
/* 234:    */     }
/* 235:    */     else
/* 236:    */     {
/* 237:278 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setBlockBoundsForItemRender()
/* 242:    */   {
/* 243:287 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_)
/* 247:    */   {
/* 248:292 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 249:293 */     super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/* 250:    */   }
/* 251:    */   
/* 252:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/* 253:    */   {
/* 254:302 */     setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
/* 255:303 */     return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
/* 256:    */   }
/* 257:    */   
/* 258:    */   public boolean renderAsNormalBlock()
/* 259:    */   {
/* 260:308 */     return false;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public static int func_150076_b(int p_150076_0_)
/* 264:    */   {
/* 265:313 */     return p_150076_0_ & 0x7;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public static boolean func_150075_c(int p_150075_0_)
/* 269:    */   {
/* 270:318 */     return (p_150075_0_ & 0x8) != 0;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public static int func_150071_a(World p_150071_0_, int p_150071_1_, int p_150071_2_, int p_150071_3_, EntityLivingBase p_150071_4_)
/* 274:    */   {
/* 275:323 */     if ((MathHelper.abs((float)p_150071_4_.posX - p_150071_1_) < 2.0F) && (MathHelper.abs((float)p_150071_4_.posZ - p_150071_3_) < 2.0F))
/* 276:    */     {
/* 277:325 */       double var5 = p_150071_4_.posY + 1.82D - p_150071_4_.yOffset;
/* 278:327 */       if (var5 - p_150071_2_ > 2.0D) {
/* 279:329 */         return 1;
/* 280:    */       }
/* 281:332 */       if (p_150071_2_ - var5 > 0.0D) {
/* 282:334 */         return 0;
/* 283:    */       }
/* 284:    */     }
/* 285:338 */     int var7 = MathHelper.floor_double(p_150071_4_.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
/* 286:339 */     return var7 == 3 ? 4 : var7 == 2 ? 3 : var7 == 1 ? 5 : var7 == 0 ? 2 : 0;
/* 287:    */   }
/* 288:    */   
/* 289:    */   private static boolean func_150080_a(Block p_150080_0_, World p_150080_1_, int p_150080_2_, int p_150080_3_, int p_150080_4_, boolean p_150080_5_)
/* 290:    */   {
/* 291:344 */     if (p_150080_0_ == Blocks.obsidian) {
/* 292:346 */       return false;
/* 293:    */     }
/* 294:350 */     if ((p_150080_0_ != Blocks.piston) && (p_150080_0_ != Blocks.sticky_piston))
/* 295:    */     {
/* 296:352 */       if (p_150080_0_.getBlockHardness(p_150080_1_, p_150080_2_, p_150080_3_, p_150080_4_) == -1.0F) {
/* 297:354 */         return false;
/* 298:    */       }
/* 299:357 */       if (p_150080_0_.getMobilityFlag() == 2) {
/* 300:359 */         return false;
/* 301:    */       }
/* 302:362 */       if (p_150080_0_.getMobilityFlag() == 1)
/* 303:    */       {
/* 304:364 */         if (!p_150080_5_) {
/* 305:366 */           return false;
/* 306:    */         }
/* 307:369 */         return true;
/* 308:    */       }
/* 309:    */     }
/* 310:372 */     else if (func_150075_c(p_150080_1_.getBlockMetadata(p_150080_2_, p_150080_3_, p_150080_4_)))
/* 311:    */     {
/* 312:374 */       return false;
/* 313:    */     }
/* 314:377 */     return !(p_150080_0_ instanceof ITileEntityProvider);
/* 315:    */   }
/* 316:    */   
/* 317:    */   private static boolean func_150077_h(World p_150077_0_, int p_150077_1_, int p_150077_2_, int p_150077_3_, int p_150077_4_)
/* 318:    */   {
/* 319:383 */     int var5 = p_150077_1_ + net.minecraft.util.Facing.offsetsXForSide[p_150077_4_];
/* 320:384 */     int var6 = p_150077_2_ + net.minecraft.util.Facing.offsetsYForSide[p_150077_4_];
/* 321:385 */     int var7 = p_150077_3_ + net.minecraft.util.Facing.offsetsZForSide[p_150077_4_];
/* 322:386 */     int var8 = 0;
/* 323:390 */     while (var8 < 13)
/* 324:    */     {
/* 325:392 */       if ((var6 <= 0) || (var6 >= 255)) {
/* 326:394 */         return false;
/* 327:    */       }
/* 328:397 */       Block var9 = p_150077_0_.getBlock(var5, var6, var7);
/* 329:399 */       if (var9.getMaterial() == Material.air) {
/* 330:    */         break;
/* 331:    */       }
/* 332:401 */       if (!func_150080_a(var9, p_150077_0_, var5, var6, var7, true)) {
/* 333:403 */         return false;
/* 334:    */       }
/* 335:406 */       if (var9.getMobilityFlag() == 1) {
/* 336:    */         break;
/* 337:    */       }
/* 338:408 */       if (var8 == 12) {
/* 339:410 */         return false;
/* 340:    */       }
/* 341:413 */       var5 += net.minecraft.util.Facing.offsetsXForSide[p_150077_4_];
/* 342:414 */       var6 += net.minecraft.util.Facing.offsetsYForSide[p_150077_4_];
/* 343:415 */       var7 += net.minecraft.util.Facing.offsetsZForSide[p_150077_4_];
/* 344:416 */       var8++;
/* 345:    */     }
/* 346:422 */     return true;
/* 347:    */   }
/* 348:    */   
/* 349:    */   private boolean func_150079_i(World p_150079_1_, int p_150079_2_, int p_150079_3_, int p_150079_4_, int p_150079_5_)
/* 350:    */   {
/* 351:428 */     int var6 = p_150079_2_ + net.minecraft.util.Facing.offsetsXForSide[p_150079_5_];
/* 352:429 */     int var7 = p_150079_3_ + net.minecraft.util.Facing.offsetsYForSide[p_150079_5_];
/* 353:430 */     int var8 = p_150079_4_ + net.minecraft.util.Facing.offsetsZForSide[p_150079_5_];
/* 354:431 */     int var9 = 0;
/* 355:435 */     while (var9 < 13)
/* 356:    */     {
/* 357:437 */       if ((var7 <= 0) || (var7 >= 255)) {
/* 358:439 */         return false;
/* 359:    */       }
/* 360:442 */       Block var10 = p_150079_1_.getBlock(var6, var7, var8);
/* 361:444 */       if (var10.getMaterial() != Material.air)
/* 362:    */       {
/* 363:446 */         if (!func_150080_a(var10, p_150079_1_, var6, var7, var8, true)) {
/* 364:448 */           return false;
/* 365:    */         }
/* 366:451 */         if (var10.getMobilityFlag() != 1)
/* 367:    */         {
/* 368:453 */           if (var9 == 12) {
/* 369:455 */             return false;
/* 370:    */           }
/* 371:458 */           var6 += net.minecraft.util.Facing.offsetsXForSide[p_150079_5_];
/* 372:459 */           var7 += net.minecraft.util.Facing.offsetsYForSide[p_150079_5_];
/* 373:460 */           var8 += net.minecraft.util.Facing.offsetsZForSide[p_150079_5_];
/* 374:461 */           var9++;
/* 375:    */         }
/* 376:    */         else
/* 377:    */         {
/* 378:465 */           var10.dropBlockAsItem(p_150079_1_, var6, var7, var8, p_150079_1_.getBlockMetadata(var6, var7, var8), 0);
/* 379:466 */           p_150079_1_.setBlockToAir(var6, var7, var8);
/* 380:    */         }
/* 381:    */       }
/* 382:    */     }
/* 383:470 */     var9 = var6;
/* 384:471 */     int var19 = var7;
/* 385:472 */     int var11 = var8;
/* 386:473 */     int var12 = 0;
/* 387:    */     int var16;
/* 388:479 */     for (Block[] var13 = new Block[13]; (var6 != p_150079_2_) || (var7 != p_150079_3_) || (var8 != p_150079_4_); var8 = var16)
/* 389:    */     {
/* 390:481 */       int var14 = var6 - net.minecraft.util.Facing.offsetsXForSide[p_150079_5_];
/* 391:482 */       int var15 = var7 - net.minecraft.util.Facing.offsetsYForSide[p_150079_5_];
/* 392:483 */       var16 = var8 - net.minecraft.util.Facing.offsetsZForSide[p_150079_5_];
/* 393:484 */       Block var17 = p_150079_1_.getBlock(var14, var15, var16);
/* 394:485 */       int var18 = p_150079_1_.getBlockMetadata(var14, var15, var16);
/* 395:487 */       if ((var17 == this) && (var14 == p_150079_2_) && (var15 == p_150079_3_) && (var16 == p_150079_4_))
/* 396:    */       {
/* 397:489 */         p_150079_1_.setBlock(var6, var7, var8, Blocks.piston_extension, p_150079_5_ | (this.field_150082_a ? 8 : 0), 4);
/* 398:490 */         p_150079_1_.setTileEntity(var6, var7, var8, BlockPistonMoving.func_149962_a(Blocks.piston_head, p_150079_5_ | (this.field_150082_a ? 8 : 0), p_150079_5_, true, false));
/* 399:    */       }
/* 400:    */       else
/* 401:    */       {
/* 402:494 */         p_150079_1_.setBlock(var6, var7, var8, Blocks.piston_extension, var18, 4);
/* 403:495 */         p_150079_1_.setTileEntity(var6, var7, var8, BlockPistonMoving.func_149962_a(var17, var18, p_150079_5_, true, false));
/* 404:    */       }
/* 405:498 */       var13[(var12++)] = var17;
/* 406:499 */       var6 = var14;
/* 407:500 */       var7 = var15;
/* 408:    */     }
/* 409:503 */     var6 = var9;
/* 410:504 */     var7 = var19;
/* 411:505 */     var8 = var11;
/* 412:    */     int var16;
/* 413:507 */     for (var12 = 0; (var6 != p_150079_2_) || (var7 != p_150079_3_) || (var8 != p_150079_4_); var8 = var16)
/* 414:    */     {
/* 415:509 */       int var14 = var6 - net.minecraft.util.Facing.offsetsXForSide[p_150079_5_];
/* 416:510 */       int var15 = var7 - net.minecraft.util.Facing.offsetsYForSide[p_150079_5_];
/* 417:511 */       var16 = var8 - net.minecraft.util.Facing.offsetsZForSide[p_150079_5_];
/* 418:512 */       p_150079_1_.notifyBlocksOfNeighborChange(var14, var15, var16, var13[(var12++)]);
/* 419:513 */       var6 = var14;
/* 420:514 */       var7 = var15;
/* 421:    */     }
/* 422:517 */     return true;
/* 423:    */   }
/* 424:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockPistonBase
 * JD-Core Version:    0.7.0.1
 */