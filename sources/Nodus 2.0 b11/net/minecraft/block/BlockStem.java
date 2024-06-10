/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.init.Blocks;
/*   7:    */ import net.minecraft.init.Items;
/*   8:    */ import net.minecraft.item.Item;
/*   9:    */ import net.minecraft.item.ItemStack;
/*  10:    */ import net.minecraft.util.IIcon;
/*  11:    */ import net.minecraft.util.MathHelper;
/*  12:    */ import net.minecraft.world.IBlockAccess;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ 
/*  15:    */ public class BlockStem
/*  16:    */   extends BlockBush
/*  17:    */   implements IGrowable
/*  18:    */ {
/*  19:    */   private final Block field_149877_a;
/*  20:    */   private IIcon field_149876_b;
/*  21:    */   private static final String __OBFID = "CL_00000316";
/*  22:    */   
/*  23:    */   protected BlockStem(Block p_i45430_1_)
/*  24:    */   {
/*  25: 24 */     this.field_149877_a = p_i45430_1_;
/*  26: 25 */     setTickRandomly(true);
/*  27: 26 */     float var2 = 0.125F;
/*  28: 27 */     setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, 0.25F, 0.5F + var2);
/*  29: 28 */     setCreativeTab(null);
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected boolean func_149854_a(Block p_149854_1_)
/*  33:    */   {
/*  34: 33 */     return p_149854_1_ == Blocks.farmland;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  38:    */   {
/*  39: 41 */     super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
/*  40: 43 */     if (p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) >= 9)
/*  41:    */     {
/*  42: 45 */       float var6 = func_149875_n(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
/*  43: 47 */       if (p_149674_5_.nextInt((int)(25.0F / var6) + 1) == 0)
/*  44:    */       {
/*  45: 49 */         int var7 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
/*  46: 51 */         if (var7 < 7)
/*  47:    */         {
/*  48: 53 */           var7++;
/*  49: 54 */           p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var7, 2);
/*  50:    */         }
/*  51:    */         else
/*  52:    */         {
/*  53: 58 */           if (p_149674_1_.getBlock(p_149674_2_ - 1, p_149674_3_, p_149674_4_) == this.field_149877_a) {
/*  54: 60 */             return;
/*  55:    */           }
/*  56: 63 */           if (p_149674_1_.getBlock(p_149674_2_ + 1, p_149674_3_, p_149674_4_) == this.field_149877_a) {
/*  57: 65 */             return;
/*  58:    */           }
/*  59: 68 */           if (p_149674_1_.getBlock(p_149674_2_, p_149674_3_, p_149674_4_ - 1) == this.field_149877_a) {
/*  60: 70 */             return;
/*  61:    */           }
/*  62: 73 */           if (p_149674_1_.getBlock(p_149674_2_, p_149674_3_, p_149674_4_ + 1) == this.field_149877_a) {
/*  63: 75 */             return;
/*  64:    */           }
/*  65: 78 */           int var8 = p_149674_5_.nextInt(4);
/*  66: 79 */           int var9 = p_149674_2_;
/*  67: 80 */           int var10 = p_149674_4_;
/*  68: 82 */           if (var8 == 0) {
/*  69: 84 */             var9 = p_149674_2_ - 1;
/*  70:    */           }
/*  71: 87 */           if (var8 == 1) {
/*  72: 89 */             var9++;
/*  73:    */           }
/*  74: 92 */           if (var8 == 2) {
/*  75: 94 */             var10 = p_149674_4_ - 1;
/*  76:    */           }
/*  77: 97 */           if (var8 == 3) {
/*  78: 99 */             var10++;
/*  79:    */           }
/*  80:102 */           Block var11 = p_149674_1_.getBlock(var9, p_149674_3_ - 1, var10);
/*  81:104 */           if ((p_149674_1_.getBlock(var9, p_149674_3_, var10).blockMaterial == Material.air) && ((var11 == Blocks.farmland) || (var11 == Blocks.dirt) || (var11 == Blocks.grass))) {
/*  82:106 */             p_149674_1_.setBlock(var9, p_149674_3_, var10, this.field_149877_a);
/*  83:    */           }
/*  84:    */         }
/*  85:    */       }
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void func_149874_m(World p_149874_1_, int p_149874_2_, int p_149874_3_, int p_149874_4_)
/*  90:    */   {
/*  91:115 */     int var5 = p_149874_1_.getBlockMetadata(p_149874_2_, p_149874_3_, p_149874_4_) + MathHelper.getRandomIntegerInRange(p_149874_1_.rand, 2, 5);
/*  92:117 */     if (var5 > 7) {
/*  93:119 */       var5 = 7;
/*  94:    */     }
/*  95:122 */     p_149874_1_.setBlockMetadataWithNotify(p_149874_2_, p_149874_3_, p_149874_4_, var5, 2);
/*  96:    */   }
/*  97:    */   
/*  98:    */   private float func_149875_n(World p_149875_1_, int p_149875_2_, int p_149875_3_, int p_149875_4_)
/*  99:    */   {
/* 100:127 */     float var5 = 1.0F;
/* 101:128 */     Block var6 = p_149875_1_.getBlock(p_149875_2_, p_149875_3_, p_149875_4_ - 1);
/* 102:129 */     Block var7 = p_149875_1_.getBlock(p_149875_2_, p_149875_3_, p_149875_4_ + 1);
/* 103:130 */     Block var8 = p_149875_1_.getBlock(p_149875_2_ - 1, p_149875_3_, p_149875_4_);
/* 104:131 */     Block var9 = p_149875_1_.getBlock(p_149875_2_ + 1, p_149875_3_, p_149875_4_);
/* 105:132 */     Block var10 = p_149875_1_.getBlock(p_149875_2_ - 1, p_149875_3_, p_149875_4_ - 1);
/* 106:133 */     Block var11 = p_149875_1_.getBlock(p_149875_2_ + 1, p_149875_3_, p_149875_4_ - 1);
/* 107:134 */     Block var12 = p_149875_1_.getBlock(p_149875_2_ + 1, p_149875_3_, p_149875_4_ + 1);
/* 108:135 */     Block var13 = p_149875_1_.getBlock(p_149875_2_ - 1, p_149875_3_, p_149875_4_ + 1);
/* 109:136 */     boolean var14 = (var8 == this) || (var9 == this);
/* 110:137 */     boolean var15 = (var6 == this) || (var7 == this);
/* 111:138 */     boolean var16 = (var10 == this) || (var11 == this) || (var12 == this) || (var13 == this);
/* 112:140 */     for (int var17 = p_149875_2_ - 1; var17 <= p_149875_2_ + 1; var17++) {
/* 113:142 */       for (int var18 = p_149875_4_ - 1; var18 <= p_149875_4_ + 1; var18++)
/* 114:    */       {
/* 115:144 */         Block var19 = p_149875_1_.getBlock(var17, p_149875_3_ - 1, var18);
/* 116:145 */         float var20 = 0.0F;
/* 117:147 */         if (var19 == Blocks.farmland)
/* 118:    */         {
/* 119:149 */           var20 = 1.0F;
/* 120:151 */           if (p_149875_1_.getBlockMetadata(var17, p_149875_3_ - 1, var18) > 0) {
/* 121:153 */             var20 = 3.0F;
/* 122:    */           }
/* 123:    */         }
/* 124:157 */         if ((var17 != p_149875_2_) || (var18 != p_149875_4_)) {
/* 125:159 */           var20 /= 4.0F;
/* 126:    */         }
/* 127:162 */         var5 += var20;
/* 128:    */       }
/* 129:    */     }
/* 130:166 */     if ((var16) || ((var14) && (var15))) {
/* 131:168 */       var5 /= 2.0F;
/* 132:    */     }
/* 133:171 */     return var5;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int getRenderColor(int p_149741_1_)
/* 137:    */   {
/* 138:179 */     int var2 = p_149741_1_ * 32;
/* 139:180 */     int var3 = 255 - p_149741_1_ * 8;
/* 140:181 */     int var4 = p_149741_1_ * 4;
/* 141:182 */     return var2 << 16 | var3 << 8 | var4;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
/* 145:    */   {
/* 146:191 */     return getRenderColor(p_149720_1_.getBlockMetadata(p_149720_2_, p_149720_3_, p_149720_4_));
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setBlockBoundsForItemRender()
/* 150:    */   {
/* 151:199 */     float var1 = 0.125F;
/* 152:200 */     setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 0.25F, 0.5F + var1);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/* 156:    */   {
/* 157:205 */     this.field_149756_F = ((p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_) * 2 + 2) / 16.0F);
/* 158:206 */     float var5 = 0.125F;
/* 159:207 */     setBlockBounds(0.5F - var5, 0.0F, 0.5F - var5, 0.5F + var5, (float)this.field_149756_F, 0.5F + var5);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public int getRenderType()
/* 163:    */   {
/* 164:215 */     return 19;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public int func_149873_e(IBlockAccess p_149873_1_, int p_149873_2_, int p_149873_3_, int p_149873_4_)
/* 168:    */   {
/* 169:220 */     int var5 = p_149873_1_.getBlockMetadata(p_149873_2_, p_149873_3_, p_149873_4_);
/* 170:221 */     return p_149873_1_.getBlock(p_149873_2_, p_149873_3_, p_149873_4_ + 1) == this.field_149877_a ? 3 : p_149873_1_.getBlock(p_149873_2_, p_149873_3_, p_149873_4_ - 1) == this.field_149877_a ? 2 : p_149873_1_.getBlock(p_149873_2_ + 1, p_149873_3_, p_149873_4_) == this.field_149877_a ? 1 : p_149873_1_.getBlock(p_149873_2_ - 1, p_149873_3_, p_149873_4_) == this.field_149877_a ? 0 : var5 < 7 ? -1 : -1;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
/* 174:    */   {
/* 175:229 */     super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, p_149690_7_);
/* 176:231 */     if (!p_149690_1_.isClient)
/* 177:    */     {
/* 178:233 */       Item var8 = null;
/* 179:235 */       if (this.field_149877_a == Blocks.pumpkin) {
/* 180:237 */         var8 = Items.pumpkin_seeds;
/* 181:    */       }
/* 182:240 */       if (this.field_149877_a == Blocks.melon_block) {
/* 183:242 */         var8 = Items.melon_seeds;
/* 184:    */       }
/* 185:245 */       for (int var9 = 0; var9 < 3; var9++) {
/* 186:247 */         if (p_149690_1_.rand.nextInt(15) <= p_149690_5_) {
/* 187:249 */           dropBlockAsItem_do(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(var8));
/* 188:    */         }
/* 189:    */       }
/* 190:    */     }
/* 191:    */   }
/* 192:    */   
/* 193:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 194:    */   {
/* 195:257 */     return null;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public int quantityDropped(Random p_149745_1_)
/* 199:    */   {
/* 200:265 */     return 1;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 204:    */   {
/* 205:273 */     return this.field_149877_a == Blocks.melon_block ? Items.melon_seeds : this.field_149877_a == Blocks.pumpkin ? Items.pumpkin_seeds : Item.getItemById(0);
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 209:    */   {
/* 210:278 */     this.blockIcon = p_149651_1_.registerIcon(getTextureName() + "_disconnected");
/* 211:279 */     this.field_149876_b = p_149651_1_.registerIcon(getTextureName() + "_connected");
/* 212:    */   }
/* 213:    */   
/* 214:    */   public IIcon func_149872_i()
/* 215:    */   {
/* 216:284 */     return this.field_149876_b;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_)
/* 220:    */   {
/* 221:289 */     return p_149851_1_.getBlockMetadata(p_149851_2_, p_149851_3_, p_149851_4_) != 7;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_)
/* 225:    */   {
/* 226:294 */     return true;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_)
/* 230:    */   {
/* 231:299 */     func_149874_m(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_);
/* 232:    */   }
/* 233:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockStem
 * JD-Core Version:    0.7.0.1
 */