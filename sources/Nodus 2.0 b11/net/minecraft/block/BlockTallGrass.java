/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   7:    */ import net.minecraft.creativetab.CreativeTabs;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
/*   9:    */ import net.minecraft.init.Blocks;
/*  10:    */ import net.minecraft.init.Items;
/*  11:    */ import net.minecraft.item.Item;
/*  12:    */ import net.minecraft.item.ItemStack;
/*  13:    */ import net.minecraft.util.IIcon;
/*  14:    */ import net.minecraft.world.ColorizerGrass;
/*  15:    */ import net.minecraft.world.IBlockAccess;
/*  16:    */ import net.minecraft.world.World;
/*  17:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  18:    */ 
/*  19:    */ public class BlockTallGrass
/*  20:    */   extends BlockBush
/*  21:    */   implements IGrowable
/*  22:    */ {
/*  23: 21 */   private static final String[] field_149871_a = { "deadbush", "tallgrass", "fern" };
/*  24:    */   private IIcon[] field_149870_b;
/*  25:    */   private static final String __OBFID = "CL_00000321";
/*  26:    */   
/*  27:    */   protected BlockTallGrass()
/*  28:    */   {
/*  29: 27 */     super(Material.vine);
/*  30: 28 */     float var1 = 0.4F;
/*  31: 29 */     setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 0.8F, 0.5F + var1);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  35:    */   {
/*  36: 37 */     if (p_149691_2_ >= this.field_149870_b.length) {
/*  37: 39 */       p_149691_2_ = 0;
/*  38:    */     }
/*  39: 42 */     return this.field_149870_b[p_149691_2_];
/*  40:    */   }
/*  41:    */   
/*  42:    */   public int getBlockColor()
/*  43:    */   {
/*  44: 47 */     double var1 = 0.5D;
/*  45: 48 */     double var3 = 1.0D;
/*  46: 49 */     return ColorizerGrass.getGrassColor(var1, var3);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_)
/*  50:    */   {
/*  51: 57 */     return func_149854_a(p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_));
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getRenderColor(int p_149741_1_)
/*  55:    */   {
/*  56: 65 */     return p_149741_1_ == 0 ? 16777215 : ColorizerGrass.getGrassColor(0.5D, 1.0D);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
/*  60:    */   {
/*  61: 74 */     int var5 = p_149720_1_.getBlockMetadata(p_149720_2_, p_149720_3_, p_149720_4_);
/*  62: 75 */     return var5 == 0 ? 16777215 : p_149720_1_.getBiomeGenForCoords(p_149720_2_, p_149720_4_).getBiomeGrassColor(p_149720_2_, p_149720_3_, p_149720_4_);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/*  66:    */   {
/*  67: 80 */     return p_149650_2_.nextInt(8) == 0 ? Items.wheat_seeds : null;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int quantityDroppedWithBonus(int p_149679_1_, Random p_149679_2_)
/*  71:    */   {
/*  72: 88 */     return 1 + p_149679_2_.nextInt(p_149679_1_ * 2 + 1);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_)
/*  76:    */   {
/*  77: 93 */     if ((!p_149636_1_.isClient) && (p_149636_2_.getCurrentEquippedItem() != null) && (p_149636_2_.getCurrentEquippedItem().getItem() == Items.shears))
/*  78:    */     {
/*  79: 95 */       p_149636_2_.addStat(net.minecraft.stats.StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
/*  80: 96 */       dropBlockAsItem_do(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, new ItemStack(Blocks.tallgrass, 1, p_149636_6_));
/*  81:    */     }
/*  82:    */     else
/*  83:    */     {
/*  84:100 */       super.harvestBlock(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_)
/*  89:    */   {
/*  90:109 */     return p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_, p_149643_4_);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/*  94:    */   {
/*  95:114 */     for (int var4 = 1; var4 < 3; var4++) {
/*  96:116 */       p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 101:    */   {
/* 102:122 */     this.field_149870_b = new IIcon[field_149871_a.length];
/* 103:124 */     for (int var2 = 0; var2 < this.field_149870_b.length; var2++) {
/* 104:126 */       this.field_149870_b[var2] = p_149651_1_.registerIcon(field_149871_a[var2]);
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_)
/* 109:    */   {
/* 110:132 */     int var6 = p_149851_1_.getBlockMetadata(p_149851_2_, p_149851_3_, p_149851_4_);
/* 111:133 */     return var6 != 0;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_)
/* 115:    */   {
/* 116:138 */     return true;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_)
/* 120:    */   {
/* 121:143 */     int var6 = p_149853_1_.getBlockMetadata(p_149853_3_, p_149853_4_, p_149853_5_);
/* 122:144 */     byte var7 = 2;
/* 123:146 */     if (var6 == 2) {
/* 124:148 */       var7 = 3;
/* 125:    */     }
/* 126:151 */     if (Blocks.double_plant.canPlaceBlockAt(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_)) {
/* 127:153 */       Blocks.double_plant.func_149889_c(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_, var7, 2);
/* 128:    */     }
/* 129:    */   }
/* 130:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockTallGrass
 * JD-Core Version:    0.7.0.1
 */