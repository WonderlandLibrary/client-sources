/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.item.Item;
/*   9:    */ import net.minecraft.item.ItemStack;
/*  10:    */ import net.minecraft.util.IIcon;
/*  11:    */ import net.minecraft.util.MathHelper;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ import net.minecraft.world.gen.feature.WorldGenBigTree;
/*  14:    */ import net.minecraft.world.gen.feature.WorldGenCanopyTree;
/*  15:    */ import net.minecraft.world.gen.feature.WorldGenForest;
/*  16:    */ import net.minecraft.world.gen.feature.WorldGenMegaJungle;
/*  17:    */ import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
/*  18:    */ import net.minecraft.world.gen.feature.WorldGenSavannaTree;
/*  19:    */ import net.minecraft.world.gen.feature.WorldGenTaiga2;
/*  20:    */ import net.minecraft.world.gen.feature.WorldGenTrees;
/*  21:    */ import net.minecraft.world.gen.feature.WorldGenerator;
/*  22:    */ 
/*  23:    */ public class BlockSapling
/*  24:    */   extends BlockBush
/*  25:    */   implements IGrowable
/*  26:    */ {
/*  27: 25 */   public static final String[] field_149882_a = { "oak", "spruce", "birch", "jungle", "acacia", "roofed_oak" };
/*  28: 26 */   private static final IIcon[] field_149881_b = new IIcon[field_149882_a.length];
/*  29:    */   private static final String __OBFID = "CL_00000305";
/*  30:    */   
/*  31:    */   protected BlockSapling()
/*  32:    */   {
/*  33: 31 */     float var1 = 0.4F;
/*  34: 32 */     setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, var1 * 2.0F, 0.5F + var1);
/*  35: 33 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  39:    */   {
/*  40: 41 */     if (!p_149674_1_.isClient)
/*  41:    */     {
/*  42: 43 */       super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
/*  43: 45 */       if ((p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) >= 9) && (p_149674_5_.nextInt(7) == 0)) {
/*  44: 47 */         func_149879_c(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
/*  45:    */       }
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  50:    */   {
/*  51: 57 */     p_149691_2_ &= 0x7;
/*  52: 58 */     return field_149881_b[MathHelper.clamp_int(p_149691_2_, 0, 5)];
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void func_149879_c(World p_149879_1_, int p_149879_2_, int p_149879_3_, int p_149879_4_, Random p_149879_5_)
/*  56:    */   {
/*  57: 63 */     int var6 = p_149879_1_.getBlockMetadata(p_149879_2_, p_149879_3_, p_149879_4_);
/*  58: 65 */     if ((var6 & 0x8) == 0) {
/*  59: 67 */       p_149879_1_.setBlockMetadataWithNotify(p_149879_2_, p_149879_3_, p_149879_4_, var6 | 0x8, 4);
/*  60:    */     } else {
/*  61: 71 */       func_149878_d(p_149879_1_, p_149879_2_, p_149879_3_, p_149879_4_, p_149879_5_);
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void func_149878_d(World p_149878_1_, int p_149878_2_, int p_149878_3_, int p_149878_4_, Random p_149878_5_)
/*  66:    */   {
/*  67: 77 */     int var6 = p_149878_1_.getBlockMetadata(p_149878_2_, p_149878_3_, p_149878_4_) & 0x7;
/*  68: 78 */     Object var7 = p_149878_5_.nextInt(10) == 0 ? new WorldGenBigTree(true) : new WorldGenTrees(true);
/*  69: 79 */     int var8 = 0;
/*  70: 80 */     int var9 = 0;
/*  71: 81 */     boolean var10 = false;
/*  72: 83 */     switch (var6)
/*  73:    */     {
/*  74:    */     case 0: 
/*  75:    */     default: 
/*  76:    */       break;
/*  77:    */     case 1: 
/*  78: 91 */       for (var8 = 0; var8 >= -1; var8--) {
/*  79: 93 */         for (var9 = 0; var9 >= -1; var9--) {
/*  80: 95 */           if ((func_149880_a(p_149878_1_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9, 1)) && (func_149880_a(p_149878_1_, p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9, 1)) && (func_149880_a(p_149878_1_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9 + 1, 1)) && (func_149880_a(p_149878_1_, p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9 + 1, 1)))
/*  81:    */           {
/*  82: 97 */             var7 = new WorldGenMegaPineTree(false, p_149878_5_.nextBoolean());
/*  83: 98 */             var10 = true;
/*  84: 99 */             break;
/*  85:    */           }
/*  86:    */         }
/*  87:    */       }
/*  88:104 */       if (!var10)
/*  89:    */       {
/*  90:106 */         var9 = 0;
/*  91:107 */         var8 = 0;
/*  92:108 */         var7 = new WorldGenTaiga2(true);
/*  93:    */       }
/*  94:111 */       break;
/*  95:    */     case 2: 
/*  96:114 */       var7 = new WorldGenForest(true, false);
/*  97:115 */       break;
/*  98:    */     case 3: 
/*  99:119 */       for (var8 = 0; var8 >= -1; var8--) {
/* 100:121 */         for (var9 = 0; var9 >= -1; var9--) {
/* 101:123 */           if ((func_149880_a(p_149878_1_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9, 3)) && (func_149880_a(p_149878_1_, p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9, 3)) && (func_149880_a(p_149878_1_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9 + 1, 3)) && (func_149880_a(p_149878_1_, p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9 + 1, 3)))
/* 102:    */           {
/* 103:125 */             var7 = new WorldGenMegaJungle(true, 10, 20, 3, 3);
/* 104:126 */             var10 = true;
/* 105:127 */             break;
/* 106:    */           }
/* 107:    */         }
/* 108:    */       }
/* 109:132 */       if (!var10)
/* 110:    */       {
/* 111:134 */         var9 = 0;
/* 112:135 */         var8 = 0;
/* 113:136 */         var7 = new WorldGenTrees(true, 4 + p_149878_5_.nextInt(7), 3, 3, false);
/* 114:    */       }
/* 115:139 */       break;
/* 116:    */     case 4: 
/* 117:142 */       var7 = new WorldGenSavannaTree(true);
/* 118:143 */       break;
/* 119:    */     case 5: 
/* 120:147 */       for (var8 = 0; var8 >= -1; var8--) {
/* 121:149 */         for (var9 = 0; var9 >= -1; var9--) {
/* 122:151 */           if ((func_149880_a(p_149878_1_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9, 5)) && (func_149880_a(p_149878_1_, p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9, 5)) && (func_149880_a(p_149878_1_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9 + 1, 5)) && (func_149880_a(p_149878_1_, p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9 + 1, 5)))
/* 123:    */           {
/* 124:153 */             var7 = new WorldGenCanopyTree(true);
/* 125:154 */             var10 = true;
/* 126:155 */             break;
/* 127:    */           }
/* 128:    */         }
/* 129:    */       }
/* 130:160 */       if (!var10) {
/* 131:    */         return;
/* 132:    */       }
/* 133:    */       break;
/* 134:    */     }
/* 135:166 */     Block var11 = Blocks.air;
/* 136:168 */     if (var10)
/* 137:    */     {
/* 138:170 */       p_149878_1_.setBlock(p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9, var11, 0, 4);
/* 139:171 */       p_149878_1_.setBlock(p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9, var11, 0, 4);
/* 140:172 */       p_149878_1_.setBlock(p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9 + 1, var11, 0, 4);
/* 141:173 */       p_149878_1_.setBlock(p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9 + 1, var11, 0, 4);
/* 142:    */     }
/* 143:    */     else
/* 144:    */     {
/* 145:177 */       p_149878_1_.setBlock(p_149878_2_, p_149878_3_, p_149878_4_, var11, 0, 4);
/* 146:    */     }
/* 147:180 */     if (!((WorldGenerator)var7).generate(p_149878_1_, p_149878_5_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9)) {
/* 148:182 */       if (var10)
/* 149:    */       {
/* 150:184 */         p_149878_1_.setBlock(p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9, this, var6, 4);
/* 151:185 */         p_149878_1_.setBlock(p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9, this, var6, 4);
/* 152:186 */         p_149878_1_.setBlock(p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9 + 1, this, var6, 4);
/* 153:187 */         p_149878_1_.setBlock(p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9 + 1, this, var6, 4);
/* 154:    */       }
/* 155:    */       else
/* 156:    */       {
/* 157:191 */         p_149878_1_.setBlock(p_149878_2_, p_149878_3_, p_149878_4_, this, var6, 4);
/* 158:    */       }
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   public boolean func_149880_a(World p_149880_1_, int p_149880_2_, int p_149880_3_, int p_149880_4_, int p_149880_5_)
/* 163:    */   {
/* 164:198 */     return (p_149880_1_.getBlock(p_149880_2_, p_149880_3_, p_149880_4_) == this) && ((p_149880_1_.getBlockMetadata(p_149880_2_, p_149880_3_, p_149880_4_) & 0x7) == p_149880_5_);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public int damageDropped(int p_149692_1_)
/* 168:    */   {
/* 169:206 */     return MathHelper.clamp_int(p_149692_1_ & 0x7, 0, 5);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/* 173:    */   {
/* 174:211 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
/* 175:212 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
/* 176:213 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
/* 177:214 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 3));
/* 178:215 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 4));
/* 179:216 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 5));
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 183:    */   {
/* 184:221 */     for (int var2 = 0; var2 < field_149881_b.length; var2++) {
/* 185:223 */       field_149881_b[var2] = p_149651_1_.registerIcon(getTextureName() + "_" + field_149882_a[var2]);
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_)
/* 190:    */   {
/* 191:229 */     return true;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_)
/* 195:    */   {
/* 196:234 */     return p_149852_1_.rand.nextFloat() < 0.45D;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_)
/* 200:    */   {
/* 201:239 */     func_149879_c(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_, p_149853_2_);
/* 202:    */   }
/* 203:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockSapling
 * JD-Core Version:    0.7.0.1
 */