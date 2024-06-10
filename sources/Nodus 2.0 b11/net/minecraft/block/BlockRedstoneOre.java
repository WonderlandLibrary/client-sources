/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.entity.Entity;
/*   6:    */ import net.minecraft.entity.player.EntityPlayer;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.init.Items;
/*   9:    */ import net.minecraft.item.Item;
/*  10:    */ import net.minecraft.item.ItemStack;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public class BlockRedstoneOre
/*  14:    */   extends Block
/*  15:    */ {
/*  16:    */   private boolean field_150187_a;
/*  17:    */   private static final String __OBFID = "CL_00000294";
/*  18:    */   
/*  19:    */   public BlockRedstoneOre(boolean p_i45420_1_)
/*  20:    */   {
/*  21: 20 */     super(Material.rock);
/*  22: 22 */     if (p_i45420_1_) {
/*  23: 24 */       setTickRandomly(true);
/*  24:    */     }
/*  25: 27 */     this.field_150187_a = p_i45420_1_;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int func_149738_a(World p_149738_1_)
/*  29:    */   {
/*  30: 32 */     return 30;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void onBlockClicked(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_)
/*  34:    */   {
/*  35: 40 */     func_150185_e(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_);
/*  36: 41 */     super.onBlockClicked(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_, p_149699_5_);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void onEntityWalking(World p_149724_1_, int p_149724_2_, int p_149724_3_, int p_149724_4_, Entity p_149724_5_)
/*  40:    */   {
/*  41: 46 */     func_150185_e(p_149724_1_, p_149724_2_, p_149724_3_, p_149724_4_);
/*  42: 47 */     super.onEntityWalking(p_149724_1_, p_149724_2_, p_149724_3_, p_149724_4_, p_149724_5_);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  46:    */   {
/*  47: 55 */     func_150185_e(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
/*  48: 56 */     return super.onBlockActivated(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, p_149727_5_, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
/*  49:    */   }
/*  50:    */   
/*  51:    */   private void func_150185_e(World p_150185_1_, int p_150185_2_, int p_150185_3_, int p_150185_4_)
/*  52:    */   {
/*  53: 61 */     func_150186_m(p_150185_1_, p_150185_2_, p_150185_3_, p_150185_4_);
/*  54: 63 */     if (this == Blocks.redstone_ore) {
/*  55: 65 */       p_150185_1_.setBlock(p_150185_2_, p_150185_3_, p_150185_4_, Blocks.lit_redstone_ore);
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  60:    */   {
/*  61: 74 */     if (this == Blocks.lit_redstone_ore) {
/*  62: 76 */       p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, Blocks.redstone_ore);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/*  67:    */   {
/*  68: 82 */     return Items.redstone;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int quantityDroppedWithBonus(int p_149679_1_, Random p_149679_2_)
/*  72:    */   {
/*  73: 90 */     return quantityDropped(p_149679_2_) + p_149679_2_.nextInt(p_149679_1_ + 1);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int quantityDropped(Random p_149745_1_)
/*  77:    */   {
/*  78: 98 */     return 4 + p_149745_1_.nextInt(2);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
/*  82:    */   {
/*  83:106 */     super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, p_149690_7_);
/*  84:108 */     if (getItemDropped(p_149690_5_, p_149690_1_.rand, p_149690_7_) != Item.getItemFromBlock(this))
/*  85:    */     {
/*  86:110 */       int var8 = 1 + p_149690_1_.rand.nextInt(5);
/*  87:111 */       dropXpOnBlockBreak(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, var8);
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
/*  92:    */   {
/*  93:120 */     if (this.field_150187_a) {
/*  94:122 */       func_150186_m(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_);
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   private void func_150186_m(World p_150186_1_, int p_150186_2_, int p_150186_3_, int p_150186_4_)
/*  99:    */   {
/* 100:128 */     Random var5 = p_150186_1_.rand;
/* 101:129 */     double var6 = 0.0625D;
/* 102:131 */     for (int var8 = 0; var8 < 6; var8++)
/* 103:    */     {
/* 104:133 */       double var9 = p_150186_2_ + var5.nextFloat();
/* 105:134 */       double var11 = p_150186_3_ + var5.nextFloat();
/* 106:135 */       double var13 = p_150186_4_ + var5.nextFloat();
/* 107:137 */       if ((var8 == 0) && (!p_150186_1_.getBlock(p_150186_2_, p_150186_3_ + 1, p_150186_4_).isOpaqueCube())) {
/* 108:139 */         var11 = p_150186_3_ + 1 + var6;
/* 109:    */       }
/* 110:142 */       if ((var8 == 1) && (!p_150186_1_.getBlock(p_150186_2_, p_150186_3_ - 1, p_150186_4_).isOpaqueCube())) {
/* 111:144 */         var11 = p_150186_3_ + 0 - var6;
/* 112:    */       }
/* 113:147 */       if ((var8 == 2) && (!p_150186_1_.getBlock(p_150186_2_, p_150186_3_, p_150186_4_ + 1).isOpaqueCube())) {
/* 114:149 */         var13 = p_150186_4_ + 1 + var6;
/* 115:    */       }
/* 116:152 */       if ((var8 == 3) && (!p_150186_1_.getBlock(p_150186_2_, p_150186_3_, p_150186_4_ - 1).isOpaqueCube())) {
/* 117:154 */         var13 = p_150186_4_ + 0 - var6;
/* 118:    */       }
/* 119:157 */       if ((var8 == 4) && (!p_150186_1_.getBlock(p_150186_2_ + 1, p_150186_3_, p_150186_4_).isOpaqueCube())) {
/* 120:159 */         var9 = p_150186_2_ + 1 + var6;
/* 121:    */       }
/* 122:162 */       if ((var8 == 5) && (!p_150186_1_.getBlock(p_150186_2_ - 1, p_150186_3_, p_150186_4_).isOpaqueCube())) {
/* 123:164 */         var9 = p_150186_2_ + 0 - var6;
/* 124:    */       }
/* 125:167 */       if ((var9 < p_150186_2_) || (var9 > p_150186_2_ + 1) || (var11 < 0.0D) || (var11 > p_150186_3_ + 1) || (var13 < p_150186_4_) || (var13 > p_150186_4_ + 1)) {
/* 126:169 */         p_150186_1_.spawnParticle("reddust", var9, var11, var13, 0.0D, 0.0D, 0.0D);
/* 127:    */       }
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected ItemStack createStackedBlock(int p_149644_1_)
/* 132:    */   {
/* 133:180 */     return new ItemStack(Blocks.redstone_ore);
/* 134:    */   }
/* 135:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockRedstoneOre
 * JD-Core Version:    0.7.0.1
 */