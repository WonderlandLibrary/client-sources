/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   5:    */ import net.minecraft.init.Blocks;
/*   6:    */ import net.minecraft.init.Items;
/*   7:    */ import net.minecraft.item.Item;
/*   8:    */ import net.minecraft.item.ItemStack;
/*   9:    */ import net.minecraft.util.IIcon;
/*  10:    */ import net.minecraft.util.MathHelper;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public class BlockCrops
/*  14:    */   extends BlockBush
/*  15:    */   implements IGrowable
/*  16:    */ {
/*  17:    */   private IIcon[] field_149867_a;
/*  18:    */   private static final String __OBFID = "CL_00000222";
/*  19:    */   
/*  20:    */   protected BlockCrops()
/*  21:    */   {
/*  22: 21 */     setTickRandomly(true);
/*  23: 22 */     float var1 = 0.5F;
/*  24: 23 */     setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 0.25F, 0.5F + var1);
/*  25: 24 */     setCreativeTab(null);
/*  26: 25 */     setHardness(0.0F);
/*  27: 26 */     setStepSound(soundTypeGrass);
/*  28: 27 */     disableStats();
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected boolean func_149854_a(Block p_149854_1_)
/*  32:    */   {
/*  33: 32 */     return p_149854_1_ == Blocks.farmland;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  37:    */   {
/*  38: 40 */     super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
/*  39: 42 */     if (p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) >= 9)
/*  40:    */     {
/*  41: 44 */       int var6 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
/*  42: 46 */       if (var6 < 7)
/*  43:    */       {
/*  44: 48 */         float var7 = func_149864_n(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
/*  45: 50 */         if (p_149674_5_.nextInt((int)(25.0F / var7) + 1) == 0)
/*  46:    */         {
/*  47: 52 */           var6++;
/*  48: 53 */           p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var6, 2);
/*  49:    */         }
/*  50:    */       }
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void func_149863_m(World p_149863_1_, int p_149863_2_, int p_149863_3_, int p_149863_4_)
/*  55:    */   {
/*  56: 61 */     int var5 = p_149863_1_.getBlockMetadata(p_149863_2_, p_149863_3_, p_149863_4_) + MathHelper.getRandomIntegerInRange(p_149863_1_.rand, 2, 5);
/*  57: 63 */     if (var5 > 7) {
/*  58: 65 */       var5 = 7;
/*  59:    */     }
/*  60: 68 */     p_149863_1_.setBlockMetadataWithNotify(p_149863_2_, p_149863_3_, p_149863_4_, var5, 2);
/*  61:    */   }
/*  62:    */   
/*  63:    */   private float func_149864_n(World p_149864_1_, int p_149864_2_, int p_149864_3_, int p_149864_4_)
/*  64:    */   {
/*  65: 73 */     float var5 = 1.0F;
/*  66: 74 */     Block var6 = p_149864_1_.getBlock(p_149864_2_, p_149864_3_, p_149864_4_ - 1);
/*  67: 75 */     Block var7 = p_149864_1_.getBlock(p_149864_2_, p_149864_3_, p_149864_4_ + 1);
/*  68: 76 */     Block var8 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_);
/*  69: 77 */     Block var9 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_);
/*  70: 78 */     Block var10 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_ - 1);
/*  71: 79 */     Block var11 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_ - 1);
/*  72: 80 */     Block var12 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_ + 1);
/*  73: 81 */     Block var13 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_ + 1);
/*  74: 82 */     boolean var14 = (var8 == this) || (var9 == this);
/*  75: 83 */     boolean var15 = (var6 == this) || (var7 == this);
/*  76: 84 */     boolean var16 = (var10 == this) || (var11 == this) || (var12 == this) || (var13 == this);
/*  77: 86 */     for (int var17 = p_149864_2_ - 1; var17 <= p_149864_2_ + 1; var17++) {
/*  78: 88 */       for (int var18 = p_149864_4_ - 1; var18 <= p_149864_4_ + 1; var18++)
/*  79:    */       {
/*  80: 90 */         float var19 = 0.0F;
/*  81: 92 */         if (p_149864_1_.getBlock(var17, p_149864_3_ - 1, var18) == Blocks.farmland)
/*  82:    */         {
/*  83: 94 */           var19 = 1.0F;
/*  84: 96 */           if (p_149864_1_.getBlockMetadata(var17, p_149864_3_ - 1, var18) > 0) {
/*  85: 98 */             var19 = 3.0F;
/*  86:    */           }
/*  87:    */         }
/*  88:102 */         if ((var17 != p_149864_2_) || (var18 != p_149864_4_)) {
/*  89:104 */           var19 /= 4.0F;
/*  90:    */         }
/*  91:107 */         var5 += var19;
/*  92:    */       }
/*  93:    */     }
/*  94:111 */     if ((var16) || ((var14) && (var15))) {
/*  95:113 */       var5 /= 2.0F;
/*  96:    */     }
/*  97:116 */     return var5;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 101:    */   {
/* 102:124 */     if ((p_149691_2_ < 0) || (p_149691_2_ > 7)) {
/* 103:126 */       p_149691_2_ = 7;
/* 104:    */     }
/* 105:129 */     return this.field_149867_a[p_149691_2_];
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getRenderType()
/* 109:    */   {
/* 110:137 */     return 6;
/* 111:    */   }
/* 112:    */   
/* 113:    */   protected Item func_149866_i()
/* 114:    */   {
/* 115:142 */     return Items.wheat_seeds;
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected Item func_149865_P()
/* 119:    */   {
/* 120:147 */     return Items.wheat;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
/* 124:    */   {
/* 125:155 */     super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, 0);
/* 126:157 */     if (!p_149690_1_.isClient) {
/* 127:159 */       if (p_149690_5_ >= 7)
/* 128:    */       {
/* 129:161 */         int var8 = 3 + p_149690_7_;
/* 130:163 */         for (int var9 = 0; var9 < var8; var9++) {
/* 131:165 */           if (p_149690_1_.rand.nextInt(15) <= p_149690_5_) {
/* 132:167 */             dropBlockAsItem_do(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(func_149866_i(), 1, 0));
/* 133:    */           }
/* 134:    */         }
/* 135:    */       }
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 140:    */   {
/* 141:176 */     return p_149650_1_ == 7 ? func_149865_P() : func_149866_i();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public int quantityDropped(Random p_149745_1_)
/* 145:    */   {
/* 146:184 */     return 1;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 150:    */   {
/* 151:192 */     return func_149866_i();
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 155:    */   {
/* 156:197 */     this.field_149867_a = new IIcon[8];
/* 157:199 */     for (int var2 = 0; var2 < this.field_149867_a.length; var2++) {
/* 158:201 */       this.field_149867_a[var2] = p_149651_1_.registerIcon(getTextureName() + "_stage_" + var2);
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_)
/* 163:    */   {
/* 164:207 */     return p_149851_1_.getBlockMetadata(p_149851_2_, p_149851_3_, p_149851_4_) != 7;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_)
/* 168:    */   {
/* 169:212 */     return true;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_)
/* 173:    */   {
/* 174:217 */     func_149863_m(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_);
/* 175:    */   }
/* 176:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockCrops
 * JD-Core Version:    0.7.0.1
 */