/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   7:    */ import net.minecraft.creativetab.CreativeTabs;
/*   8:    */ import net.minecraft.entity.monster.EntitySilverfish;
/*   9:    */ import net.minecraft.init.Blocks;
/*  10:    */ import net.minecraft.item.Item;
/*  11:    */ import net.minecraft.item.ItemStack;
/*  12:    */ import net.minecraft.util.IIcon;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ import org.apache.commons.lang3.tuple.ImmutablePair;
/*  15:    */ 
/*  16:    */ public class BlockSilverfish
/*  17:    */   extends Block
/*  18:    */ {
/*  19: 18 */   public static final String[] field_150198_a = { "stone", "cobble", "brick", "mossybrick", "crackedbrick", "chiseledbrick" };
/*  20:    */   private static final String __OBFID = "CL_00000271";
/*  21:    */   
/*  22:    */   public BlockSilverfish()
/*  23:    */   {
/*  24: 23 */     super(Material.field_151571_B);
/*  25: 24 */     setHardness(0.0F);
/*  26: 25 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  30:    */   {
/*  31: 33 */     switch (p_149691_2_)
/*  32:    */     {
/*  33:    */     case 1: 
/*  34: 36 */       return Blocks.cobblestone.getBlockTextureFromSide(p_149691_1_);
/*  35:    */     case 2: 
/*  36: 39 */       return Blocks.stonebrick.getBlockTextureFromSide(p_149691_1_);
/*  37:    */     case 3: 
/*  38: 42 */       return Blocks.stonebrick.getIcon(p_149691_1_, 1);
/*  39:    */     case 4: 
/*  40: 45 */       return Blocks.stonebrick.getIcon(p_149691_1_, 2);
/*  41:    */     case 5: 
/*  42: 48 */       return Blocks.stonebrick.getIcon(p_149691_1_, 3);
/*  43:    */     }
/*  44: 51 */     return Blocks.stone.getBlockTextureFromSide(p_149691_1_);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void registerBlockIcons(IIconRegister p_149651_1_) {}
/*  48:    */   
/*  49:    */   public void onBlockDestroyedByPlayer(World p_149664_1_, int p_149664_2_, int p_149664_3_, int p_149664_4_, int p_149664_5_)
/*  50:    */   {
/*  51: 59 */     if (!p_149664_1_.isClient)
/*  52:    */     {
/*  53: 61 */       EntitySilverfish var6 = new EntitySilverfish(p_149664_1_);
/*  54: 62 */       var6.setLocationAndAngles(p_149664_2_ + 0.5D, p_149664_3_, p_149664_4_ + 0.5D, 0.0F, 0.0F);
/*  55: 63 */       p_149664_1_.spawnEntityInWorld(var6);
/*  56: 64 */       var6.spawnExplosionParticle();
/*  57:    */     }
/*  58: 67 */     super.onBlockDestroyedByPlayer(p_149664_1_, p_149664_2_, p_149664_3_, p_149664_4_, p_149664_5_);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int quantityDropped(Random p_149745_1_)
/*  62:    */   {
/*  63: 75 */     return 0;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static boolean func_150196_a(Block p_150196_0_)
/*  67:    */   {
/*  68: 80 */     return (p_150196_0_ == Blocks.stone) || (p_150196_0_ == Blocks.cobblestone) || (p_150196_0_ == Blocks.stonebrick);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static int func_150195_a(Block p_150195_0_, int p_150195_1_)
/*  72:    */   {
/*  73: 85 */     if (p_150195_1_ == 0)
/*  74:    */     {
/*  75: 87 */       if (p_150195_0_ == Blocks.cobblestone) {
/*  76: 89 */         return 1;
/*  77:    */       }
/*  78: 92 */       if (p_150195_0_ == Blocks.stonebrick) {
/*  79: 94 */         return 2;
/*  80:    */       }
/*  81:    */     }
/*  82: 97 */     else if (p_150195_0_ == Blocks.stonebrick)
/*  83:    */     {
/*  84: 99 */       switch (p_150195_1_)
/*  85:    */       {
/*  86:    */       case 1: 
/*  87:102 */         return 3;
/*  88:    */       case 2: 
/*  89:105 */         return 4;
/*  90:    */       case 3: 
/*  91:108 */         return 5;
/*  92:    */       }
/*  93:    */     }
/*  94:112 */     return 0;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static ImmutablePair func_150197_b(int p_150197_0_)
/*  98:    */   {
/*  99:117 */     switch (p_150197_0_)
/* 100:    */     {
/* 101:    */     case 1: 
/* 102:120 */       return new ImmutablePair(Blocks.cobblestone, Integer.valueOf(0));
/* 103:    */     case 2: 
/* 104:123 */       return new ImmutablePair(Blocks.stonebrick, Integer.valueOf(0));
/* 105:    */     case 3: 
/* 106:126 */       return new ImmutablePair(Blocks.stonebrick, Integer.valueOf(1));
/* 107:    */     case 4: 
/* 108:129 */       return new ImmutablePair(Blocks.stonebrick, Integer.valueOf(2));
/* 109:    */     case 5: 
/* 110:132 */       return new ImmutablePair(Blocks.stonebrick, Integer.valueOf(3));
/* 111:    */     }
/* 112:135 */     return new ImmutablePair(Blocks.stone, Integer.valueOf(0));
/* 113:    */   }
/* 114:    */   
/* 115:    */   protected ItemStack createStackedBlock(int p_149644_1_)
/* 116:    */   {
/* 117:145 */     switch (p_149644_1_)
/* 118:    */     {
/* 119:    */     case 1: 
/* 120:148 */       return new ItemStack(Blocks.cobblestone);
/* 121:    */     case 2: 
/* 122:151 */       return new ItemStack(Blocks.stonebrick);
/* 123:    */     case 3: 
/* 124:154 */       return new ItemStack(Blocks.stonebrick, 1, 1);
/* 125:    */     case 4: 
/* 126:157 */       return new ItemStack(Blocks.stonebrick, 1, 2);
/* 127:    */     case 5: 
/* 128:160 */       return new ItemStack(Blocks.stonebrick, 1, 3);
/* 129:    */     }
/* 130:163 */     return new ItemStack(Blocks.stone);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
/* 134:    */   {
/* 135:172 */     if (!p_149690_1_.isClient)
/* 136:    */     {
/* 137:174 */       EntitySilverfish var8 = new EntitySilverfish(p_149690_1_);
/* 138:175 */       var8.setLocationAndAngles(p_149690_2_ + 0.5D, p_149690_3_, p_149690_4_ + 0.5D, 0.0F, 0.0F);
/* 139:176 */       p_149690_1_.spawnEntityInWorld(var8);
/* 140:177 */       var8.spawnExplosionParticle();
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_)
/* 145:    */   {
/* 146:186 */     return p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_, p_149643_4_);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/* 150:    */   {
/* 151:191 */     for (int var4 = 0; var4 < field_150198_a.length; var4++) {
/* 152:193 */       p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
/* 153:    */     }
/* 154:    */   }
/* 155:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockSilverfish
 * JD-Core Version:    0.7.0.1
 */