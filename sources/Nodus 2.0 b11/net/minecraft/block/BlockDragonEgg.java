/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.entity.item.EntityFallingBlock;
/*   6:    */ import net.minecraft.entity.player.EntityPlayer;
/*   7:    */ import net.minecraft.item.Item;
/*   8:    */ import net.minecraft.world.IBlockAccess;
/*   9:    */ import net.minecraft.world.World;
/*  10:    */ 
/*  11:    */ public class BlockDragonEgg
/*  12:    */   extends Block
/*  13:    */ {
/*  14:    */   private static final String __OBFID = "CL_00000232";
/*  15:    */   
/*  16:    */   public BlockDragonEgg()
/*  17:    */   {
/*  18: 17 */     super(Material.dragonEgg);
/*  19: 18 */     setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/*  23:    */   {
/*  24: 23 */     p_149726_1_.scheduleBlockUpdate(p_149726_2_, p_149726_3_, p_149726_4_, this, func_149738_a(p_149726_1_));
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/*  28:    */   {
/*  29: 28 */     p_149695_1_.scheduleBlockUpdate(p_149695_2_, p_149695_3_, p_149695_4_, this, func_149738_a(p_149695_1_));
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  33:    */   {
/*  34: 36 */     func_150018_e(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
/*  35:    */   }
/*  36:    */   
/*  37:    */   private void func_150018_e(World p_150018_1_, int p_150018_2_, int p_150018_3_, int p_150018_4_)
/*  38:    */   {
/*  39: 41 */     if ((BlockFalling.func_149831_e(p_150018_1_, p_150018_2_, p_150018_3_ - 1, p_150018_4_)) && (p_150018_3_ >= 0))
/*  40:    */     {
/*  41: 43 */       byte var5 = 32;
/*  42: 45 */       if ((!BlockFalling.field_149832_M) && (p_150018_1_.checkChunksExist(p_150018_2_ - var5, p_150018_3_ - var5, p_150018_4_ - var5, p_150018_2_ + var5, p_150018_3_ + var5, p_150018_4_ + var5)))
/*  43:    */       {
/*  44: 47 */         EntityFallingBlock var6 = new EntityFallingBlock(p_150018_1_, p_150018_2_ + 0.5F, p_150018_3_ + 0.5F, p_150018_4_ + 0.5F, this);
/*  45: 48 */         p_150018_1_.spawnEntityInWorld(var6);
/*  46:    */       }
/*  47:    */       else
/*  48:    */       {
/*  49: 52 */         p_150018_1_.setBlockToAir(p_150018_2_, p_150018_3_, p_150018_4_);
/*  50: 54 */         while ((BlockFalling.func_149831_e(p_150018_1_, p_150018_2_, p_150018_3_ - 1, p_150018_4_)) && (p_150018_3_ > 0)) {
/*  51: 56 */           p_150018_3_--;
/*  52:    */         }
/*  53: 59 */         if (p_150018_3_ > 0) {
/*  54: 61 */           p_150018_1_.setBlock(p_150018_2_, p_150018_3_, p_150018_4_, this, 0, 2);
/*  55:    */         }
/*  56:    */       }
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  61:    */   {
/*  62: 72 */     func_150019_m(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
/*  63: 73 */     return true;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void onBlockClicked(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_)
/*  67:    */   {
/*  68: 81 */     func_150019_m(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_);
/*  69:    */   }
/*  70:    */   
/*  71:    */   private void func_150019_m(World p_150019_1_, int p_150019_2_, int p_150019_3_, int p_150019_4_)
/*  72:    */   {
/*  73: 86 */     if (p_150019_1_.getBlock(p_150019_2_, p_150019_3_, p_150019_4_) == this) {
/*  74: 88 */       for (int var5 = 0; var5 < 1000; var5++)
/*  75:    */       {
/*  76: 90 */         int var6 = p_150019_2_ + p_150019_1_.rand.nextInt(16) - p_150019_1_.rand.nextInt(16);
/*  77: 91 */         int var7 = p_150019_3_ + p_150019_1_.rand.nextInt(8) - p_150019_1_.rand.nextInt(8);
/*  78: 92 */         int var8 = p_150019_4_ + p_150019_1_.rand.nextInt(16) - p_150019_1_.rand.nextInt(16);
/*  79: 94 */         if (p_150019_1_.getBlock(var6, var7, var8).blockMaterial == Material.air)
/*  80:    */         {
/*  81: 96 */           if (!p_150019_1_.isClient)
/*  82:    */           {
/*  83: 98 */             p_150019_1_.setBlock(var6, var7, var8, this, p_150019_1_.getBlockMetadata(p_150019_2_, p_150019_3_, p_150019_4_), 2);
/*  84: 99 */             p_150019_1_.setBlockToAir(p_150019_2_, p_150019_3_, p_150019_4_);
/*  85:    */           }
/*  86:    */           else
/*  87:    */           {
/*  88:103 */             short var9 = 128;
/*  89:105 */             for (int var10 = 0; var10 < var9; var10++)
/*  90:    */             {
/*  91:107 */               double var11 = p_150019_1_.rand.nextDouble();
/*  92:108 */               float var13 = (p_150019_1_.rand.nextFloat() - 0.5F) * 0.2F;
/*  93:109 */               float var14 = (p_150019_1_.rand.nextFloat() - 0.5F) * 0.2F;
/*  94:110 */               float var15 = (p_150019_1_.rand.nextFloat() - 0.5F) * 0.2F;
/*  95:111 */               double var16 = var6 + (p_150019_2_ - var6) * var11 + (p_150019_1_.rand.nextDouble() - 0.5D) * 1.0D + 0.5D;
/*  96:112 */               double var18 = var7 + (p_150019_3_ - var7) * var11 + p_150019_1_.rand.nextDouble() * 1.0D - 0.5D;
/*  97:113 */               double var20 = var8 + (p_150019_4_ - var8) * var11 + (p_150019_1_.rand.nextDouble() - 0.5D) * 1.0D + 0.5D;
/*  98:114 */               p_150019_1_.spawnParticle("portal", var16, var18, var20, var13, var14, var15);
/*  99:    */             }
/* 100:    */           }
/* 101:118 */           return;
/* 102:    */         }
/* 103:    */       }
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int func_149738_a(World p_149738_1_)
/* 108:    */   {
/* 109:126 */     return 5;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean isOpaqueCube()
/* 113:    */   {
/* 114:131 */     return false;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public boolean renderAsNormalBlock()
/* 118:    */   {
/* 119:136 */     return false;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
/* 123:    */   {
/* 124:141 */     return true;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public int getRenderType()
/* 128:    */   {
/* 129:149 */     return 27;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 133:    */   {
/* 134:157 */     return Item.getItemById(0);
/* 135:    */   }
/* 136:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockDragonEgg
 * JD-Core Version:    0.7.0.1
 */