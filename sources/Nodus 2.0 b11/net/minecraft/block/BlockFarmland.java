/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.entity.Entity;
/*   7:    */ import net.minecraft.entity.player.EntityPlayer;
/*   8:    */ import net.minecraft.init.Blocks;
/*   9:    */ import net.minecraft.item.Item;
/*  10:    */ import net.minecraft.util.AABBPool;
/*  11:    */ import net.minecraft.util.AxisAlignedBB;
/*  12:    */ import net.minecraft.util.IIcon;
/*  13:    */ import net.minecraft.world.GameRules;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public class BlockFarmland
/*  17:    */   extends Block
/*  18:    */ {
/*  19:    */   private IIcon field_149824_a;
/*  20:    */   private IIcon field_149823_b;
/*  21:    */   private static final String __OBFID = "CL_00000241";
/*  22:    */   
/*  23:    */   protected BlockFarmland()
/*  24:    */   {
/*  25: 22 */     super(Material.ground);
/*  26: 23 */     setTickRandomly(true);
/*  27: 24 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
/*  28: 25 */     setLightOpacity(255);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  32:    */   {
/*  33: 34 */     return AxisAlignedBB.getAABBPool().getAABB(p_149668_2_ + 0, p_149668_3_ + 0, p_149668_4_ + 0, p_149668_2_ + 1, p_149668_3_ + 1, p_149668_4_ + 1);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public boolean isOpaqueCube()
/*  37:    */   {
/*  38: 39 */     return false;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean renderAsNormalBlock()
/*  42:    */   {
/*  43: 44 */     return false;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  47:    */   {
/*  48: 52 */     return p_149691_1_ == 1 ? this.field_149823_b : p_149691_2_ > 0 ? this.field_149824_a : Blocks.dirt.getBlockTextureFromSide(p_149691_1_);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  52:    */   {
/*  53: 60 */     if ((!func_149821_m(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_)) && (!p_149674_1_.canLightningStrikeAt(p_149674_2_, p_149674_3_ + 1, p_149674_4_)))
/*  54:    */     {
/*  55: 62 */       int var6 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
/*  56: 64 */       if (var6 > 0) {
/*  57: 66 */         p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var6 - 1, 2);
/*  58: 68 */       } else if (!func_149822_e(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_)) {
/*  59: 70 */         p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, Blocks.dirt);
/*  60:    */       }
/*  61:    */     }
/*  62:    */     else
/*  63:    */     {
/*  64: 75 */       p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, 7, 2);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void onFallenUpon(World p_149746_1_, int p_149746_2_, int p_149746_3_, int p_149746_4_, Entity p_149746_5_, float p_149746_6_)
/*  69:    */   {
/*  70: 84 */     if ((!p_149746_1_.isClient) && (p_149746_1_.rand.nextFloat() < p_149746_6_ - 0.5F))
/*  71:    */     {
/*  72: 86 */       if ((!(p_149746_5_ instanceof EntityPlayer)) && (!p_149746_1_.getGameRules().getGameRuleBooleanValue("mobGriefing"))) {
/*  73: 88 */         return;
/*  74:    */       }
/*  75: 91 */       p_149746_1_.setBlock(p_149746_2_, p_149746_3_, p_149746_4_, Blocks.dirt);
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   private boolean func_149822_e(World p_149822_1_, int p_149822_2_, int p_149822_3_, int p_149822_4_)
/*  80:    */   {
/*  81: 97 */     byte var5 = 0;
/*  82: 99 */     for (int var6 = p_149822_2_ - var5; var6 <= p_149822_2_ + var5; var6++) {
/*  83:101 */       for (int var7 = p_149822_4_ - var5; var7 <= p_149822_4_ + var5; var7++)
/*  84:    */       {
/*  85:103 */         Block var8 = p_149822_1_.getBlock(var6, p_149822_3_ + 1, var7);
/*  86:105 */         if ((var8 == Blocks.wheat) || (var8 == Blocks.melon_stem) || (var8 == Blocks.pumpkin_stem) || (var8 == Blocks.potatoes) || (var8 == Blocks.carrots)) {
/*  87:107 */           return true;
/*  88:    */         }
/*  89:    */       }
/*  90:    */     }
/*  91:112 */     return false;
/*  92:    */   }
/*  93:    */   
/*  94:    */   private boolean func_149821_m(World p_149821_1_, int p_149821_2_, int p_149821_3_, int p_149821_4_)
/*  95:    */   {
/*  96:117 */     for (int var5 = p_149821_2_ - 4; var5 <= p_149821_2_ + 4; var5++) {
/*  97:119 */       for (int var6 = p_149821_3_; var6 <= p_149821_3_ + 1; var6++) {
/*  98:121 */         for (int var7 = p_149821_4_ - 4; var7 <= p_149821_4_ + 4; var7++) {
/*  99:123 */           if (p_149821_1_.getBlock(var5, var6, var7).getMaterial() == Material.water) {
/* 100:125 */             return true;
/* 101:    */           }
/* 102:    */         }
/* 103:    */       }
/* 104:    */     }
/* 105:131 */     return false;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 109:    */   {
/* 110:136 */     super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
/* 111:137 */     Material var6 = p_149695_1_.getBlock(p_149695_2_, p_149695_3_ + 1, p_149695_4_).getMaterial();
/* 112:139 */     if (var6.isSolid()) {
/* 113:141 */       p_149695_1_.setBlock(p_149695_2_, p_149695_3_, p_149695_4_, Blocks.dirt);
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 118:    */   {
/* 119:147 */     return Blocks.dirt.getItemDropped(0, p_149650_2_, p_149650_3_);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 123:    */   {
/* 124:155 */     return Item.getItemFromBlock(Blocks.dirt);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 128:    */   {
/* 129:160 */     this.field_149824_a = p_149651_1_.registerIcon(getTextureName() + "_wet");
/* 130:161 */     this.field_149823_b = p_149651_1_.registerIcon(getTextureName() + "_dry");
/* 131:    */   }
/* 132:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockFarmland
 * JD-Core Version:    0.7.0.1
 */