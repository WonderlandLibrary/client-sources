/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.item.Item;
/*   9:    */ import net.minecraft.util.IIcon;
/*  10:    */ import net.minecraft.world.ColorizerGrass;
/*  11:    */ import net.minecraft.world.IBlockAccess;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  14:    */ import org.apache.logging.log4j.LogManager;
/*  15:    */ import org.apache.logging.log4j.Logger;
/*  16:    */ 
/*  17:    */ public class BlockGrass
/*  18:    */   extends Block
/*  19:    */   implements IGrowable
/*  20:    */ {
/*  21: 18 */   private static final Logger logger = ;
/*  22:    */   private IIcon field_149991_b;
/*  23:    */   private IIcon field_149993_M;
/*  24:    */   private IIcon field_149994_N;
/*  25:    */   private static final String __OBFID = "CL_00000251";
/*  26:    */   
/*  27:    */   protected BlockGrass()
/*  28:    */   {
/*  29: 26 */     super(Material.grass);
/*  30: 27 */     setTickRandomly(true);
/*  31: 28 */     setCreativeTab(CreativeTabs.tabBlock);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  35:    */   {
/*  36: 36 */     return p_149691_1_ == 0 ? Blocks.dirt.getBlockTextureFromSide(p_149691_1_) : p_149691_1_ == 1 ? this.field_149991_b : this.blockIcon;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public IIcon getIcon(IBlockAccess p_149673_1_, int p_149673_2_, int p_149673_3_, int p_149673_4_, int p_149673_5_)
/*  40:    */   {
/*  41: 41 */     if (p_149673_5_ == 1) {
/*  42: 43 */       return this.field_149991_b;
/*  43:    */     }
/*  44: 45 */     if (p_149673_5_ == 0) {
/*  45: 47 */       return Blocks.dirt.getBlockTextureFromSide(p_149673_5_);
/*  46:    */     }
/*  47: 51 */     Material var6 = p_149673_1_.getBlock(p_149673_2_, p_149673_3_ + 1, p_149673_4_).getMaterial();
/*  48: 52 */     return (var6 != Material.field_151597_y) && (var6 != Material.craftedSnow) ? this.blockIcon : this.field_149993_M;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/*  52:    */   {
/*  53: 58 */     this.blockIcon = p_149651_1_.registerIcon(getTextureName() + "_side");
/*  54: 59 */     this.field_149991_b = p_149651_1_.registerIcon(getTextureName() + "_top");
/*  55: 60 */     this.field_149993_M = p_149651_1_.registerIcon(getTextureName() + "_side_snowed");
/*  56: 61 */     this.field_149994_N = p_149651_1_.registerIcon(getTextureName() + "_side_overlay");
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getBlockColor()
/*  60:    */   {
/*  61: 66 */     double var1 = 0.5D;
/*  62: 67 */     double var3 = 1.0D;
/*  63: 68 */     return ColorizerGrass.getGrassColor(var1, var3);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getRenderColor(int p_149741_1_)
/*  67:    */   {
/*  68: 76 */     return getBlockColor();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
/*  72:    */   {
/*  73: 85 */     int var5 = 0;
/*  74: 86 */     int var6 = 0;
/*  75: 87 */     int var7 = 0;
/*  76: 89 */     for (int var8 = -1; var8 <= 1; var8++) {
/*  77: 91 */       for (int var9 = -1; var9 <= 1; var9++)
/*  78:    */       {
/*  79: 93 */         int var10 = p_149720_1_.getBiomeGenForCoords(p_149720_2_ + var9, p_149720_4_ + var8).getBiomeGrassColor(p_149720_2_ + var9, p_149720_3_, p_149720_4_ + var8);
/*  80: 94 */         var5 += ((var10 & 0xFF0000) >> 16);
/*  81: 95 */         var6 += ((var10 & 0xFF00) >> 8);
/*  82: 96 */         var7 += (var10 & 0xFF);
/*  83:    */       }
/*  84:    */     }
/*  85:100 */     return (var5 / 9 & 0xFF) << 16 | (var6 / 9 & 0xFF) << 8 | var7 / 9 & 0xFF;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  89:    */   {
/*  90:108 */     if (!p_149674_1_.isClient) {
/*  91:110 */       if ((p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) < 4) && (p_149674_1_.getBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_).getLightOpacity() > 2)) {
/*  92:112 */         p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, Blocks.dirt);
/*  93:114 */       } else if (p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) >= 9) {
/*  94:116 */         for (int var6 = 0; var6 < 4; var6++)
/*  95:    */         {
/*  96:118 */           int var7 = p_149674_2_ + p_149674_5_.nextInt(3) - 1;
/*  97:119 */           int var8 = p_149674_3_ + p_149674_5_.nextInt(5) - 3;
/*  98:120 */           int var9 = p_149674_4_ + p_149674_5_.nextInt(3) - 1;
/*  99:121 */           Block var10 = p_149674_1_.getBlock(var7, var8 + 1, var9);
/* 100:123 */           if ((p_149674_1_.getBlock(var7, var8, var9) == Blocks.dirt) && (p_149674_1_.getBlockMetadata(var7, var8, var9) == 0) && (p_149674_1_.getBlockLightValue(var7, var8 + 1, var9) >= 4) && (var10.getLightOpacity() <= 2)) {
/* 101:125 */             p_149674_1_.setBlock(var7, var8, var9, Blocks.grass);
/* 102:    */           }
/* 103:    */         }
/* 104:    */       }
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 109:    */   {
/* 110:134 */     return Blocks.dirt.getItemDropped(0, p_149650_2_, p_149650_3_);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static IIcon func_149990_e()
/* 114:    */   {
/* 115:139 */     return Blocks.grass.field_149994_N;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_)
/* 119:    */   {
/* 120:144 */     return true;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_)
/* 124:    */   {
/* 125:149 */     return true;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_)
/* 129:    */   {
/* 130:154 */     int var6 = 0;
/* 131:    */     label295:
/* 132:156 */     while (var6 < 128)
/* 133:    */     {
/* 134:158 */       int var7 = p_149853_3_;
/* 135:159 */       int var8 = p_149853_4_ + 1;
/* 136:160 */       int var9 = p_149853_5_;
/* 137:161 */       int var10 = 0;
/* 138:165 */       while (var10 < var6 / 16)
/* 139:    */       {
/* 140:167 */         var7 += p_149853_2_.nextInt(3) - 1;
/* 141:168 */         var8 += (p_149853_2_.nextInt(3) - 1) * p_149853_2_.nextInt(3) / 2;
/* 142:169 */         var9 += p_149853_2_.nextInt(3) - 1;
/* 143:171 */         if ((p_149853_1_.getBlock(var7, var8 - 1, var9) != Blocks.grass) || (p_149853_1_.getBlock(var7, var8, var9).isNormalCube())) {
/* 144:    */           break label295;
/* 145:    */         }
/* 146:173 */         var10++;
/* 147:    */       }
/* 148:177 */       if (p_149853_1_.getBlock(var7, var8, var9).blockMaterial == Material.air) {
/* 149:179 */         if (p_149853_2_.nextInt(8) != 0)
/* 150:    */         {
/* 151:181 */           if (Blocks.tallgrass.canBlockStay(p_149853_1_, var7, var8, var9)) {
/* 152:183 */             p_149853_1_.setBlock(var7, var8, var9, Blocks.tallgrass, 1, 3);
/* 153:    */           }
/* 154:    */         }
/* 155:    */         else
/* 156:    */         {
/* 157:188 */           String var13 = p_149853_1_.getBiomeGenForCoords(var7, var9).func_150572_a(p_149853_2_, var7, var8, var9);
/* 158:189 */           logger.debug("Flower in " + p_149853_1_.getBiomeGenForCoords(var7, var9).biomeName + ": " + var13);
/* 159:190 */           BlockFlower var11 = BlockFlower.func_149857_e(var13);
/* 160:192 */           if ((var11 != null) && (var11.canBlockStay(p_149853_1_, var7, var8, var9)))
/* 161:    */           {
/* 162:194 */             int var12 = BlockFlower.func_149856_f(var13);
/* 163:195 */             p_149853_1_.setBlock(var7, var8, var9, var11, var12, 3);
/* 164:    */           }
/* 165:    */         }
/* 166:    */       }
/* 167:200 */       var6++;
/* 168:    */     }
/* 169:    */   }
/* 170:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockGrass
 * JD-Core Version:    0.7.0.1
 */