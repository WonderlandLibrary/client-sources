/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.entity.player.EntityPlayer;
/*   5:    */ import net.minecraft.init.Blocks;
/*   6:    */ import net.minecraft.init.Items;
/*   7:    */ import net.minecraft.item.Item;
/*   8:    */ import net.minecraft.world.IBlockAccess;
/*   9:    */ import net.minecraft.world.World;
/*  10:    */ 
/*  11:    */ public class BlockRedstoneRepeater
/*  12:    */   extends BlockRedstoneDiode
/*  13:    */ {
/*  14: 13 */   public static final double[] field_149973_b = { -0.0625D, 0.0625D, 0.1875D, 0.3125D };
/*  15: 14 */   private static final int[] field_149974_M = { 1, 2, 3, 4 };
/*  16:    */   private static final String __OBFID = "CL_00000301";
/*  17:    */   
/*  18:    */   protected BlockRedstoneRepeater(boolean p_i45424_1_)
/*  19:    */   {
/*  20: 19 */     super(p_i45424_1_);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  24:    */   {
/*  25: 27 */     int var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
/*  26: 28 */     int var11 = (var10 & 0xC) >> 2;
/*  27: 29 */     var11 = var11 + 1 << 2 & 0xC;
/*  28: 30 */     p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var11 | var10 & 0x3, 3);
/*  29: 31 */     return true;
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected int func_149901_b(int p_149901_1_)
/*  33:    */   {
/*  34: 36 */     return field_149974_M[((p_149901_1_ & 0xC) >> 2)] * 2;
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected BlockRedstoneDiode func_149906_e()
/*  38:    */   {
/*  39: 41 */     return Blocks.powered_repeater;
/*  40:    */   }
/*  41:    */   
/*  42:    */   protected BlockRedstoneDiode func_149898_i()
/*  43:    */   {
/*  44: 46 */     return Blocks.unpowered_repeater;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/*  48:    */   {
/*  49: 51 */     return Items.repeater;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/*  53:    */   {
/*  54: 59 */     return Items.repeater;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int getRenderType()
/*  58:    */   {
/*  59: 67 */     return 15;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean func_149910_g(IBlockAccess p_149910_1_, int p_149910_2_, int p_149910_3_, int p_149910_4_, int p_149910_5_)
/*  63:    */   {
/*  64: 72 */     return func_149902_h(p_149910_1_, p_149910_2_, p_149910_3_, p_149910_4_, p_149910_5_) > 0;
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected boolean func_149908_a(Block p_149908_1_)
/*  68:    */   {
/*  69: 77 */     return func_149909_d(p_149908_1_);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
/*  73:    */   {
/*  74: 85 */     if (this.field_149914_a)
/*  75:    */     {
/*  76: 87 */       int var6 = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);
/*  77: 88 */       int var7 = func_149895_l(var6);
/*  78: 89 */       double var8 = p_149734_2_ + 0.5F + (p_149734_5_.nextFloat() - 0.5F) * 0.2D;
/*  79: 90 */       double var10 = p_149734_3_ + 0.4F + (p_149734_5_.nextFloat() - 0.5F) * 0.2D;
/*  80: 91 */       double var12 = p_149734_4_ + 0.5F + (p_149734_5_.nextFloat() - 0.5F) * 0.2D;
/*  81: 92 */       double var14 = 0.0D;
/*  82: 93 */       double var16 = 0.0D;
/*  83: 95 */       if (p_149734_5_.nextInt(2) == 0)
/*  84:    */       {
/*  85: 97 */         switch (var7)
/*  86:    */         {
/*  87:    */         case 0: 
/*  88:100 */           var16 = -0.3125D;
/*  89:101 */           break;
/*  90:    */         case 1: 
/*  91:104 */           var14 = 0.3125D;
/*  92:105 */           break;
/*  93:    */         case 2: 
/*  94:108 */           var16 = 0.3125D;
/*  95:109 */           break;
/*  96:    */         case 3: 
/*  97:112 */           var14 = -0.3125D;
/*  98:    */         }
/*  99:    */       }
/* 100:    */       else
/* 101:    */       {
/* 102:117 */         int var18 = (var6 & 0xC) >> 2;
/* 103:119 */         switch (var7)
/* 104:    */         {
/* 105:    */         case 0: 
/* 106:122 */           var16 = field_149973_b[var18];
/* 107:123 */           break;
/* 108:    */         case 1: 
/* 109:126 */           var14 = -field_149973_b[var18];
/* 110:127 */           break;
/* 111:    */         case 2: 
/* 112:130 */           var16 = -field_149973_b[var18];
/* 113:131 */           break;
/* 114:    */         case 3: 
/* 115:134 */           var14 = field_149973_b[var18];
/* 116:    */         }
/* 117:    */       }
/* 118:138 */       p_149734_1_.spawnParticle("reddust", var8 + var14, var10, var12 + var16, 0.0D, 0.0D, 0.0D);
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/* 123:    */   {
/* 124:144 */     super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/* 125:145 */     func_149911_e(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_);
/* 126:    */   }
/* 127:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockRedstoneRepeater
 * JD-Core Version:    0.7.0.1
 */