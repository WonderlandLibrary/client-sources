/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   7:    */ import net.minecraft.creativetab.CreativeTabs;
/*   8:    */ import net.minecraft.entity.Entity;
/*   9:    */ import net.minecraft.init.Blocks;
/*  10:    */ import net.minecraft.item.Item;
/*  11:    */ import net.minecraft.item.ItemStack;
/*  12:    */ import net.minecraft.util.AxisAlignedBB;
/*  13:    */ import net.minecraft.util.IIcon;
/*  14:    */ import net.minecraft.world.IBlockAccess;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ 
/*  17:    */ public class BlockPane
/*  18:    */   extends Block
/*  19:    */ {
/*  20:    */   private final String field_150100_a;
/*  21:    */   private final boolean field_150099_b;
/*  22:    */   private final String field_150101_M;
/*  23:    */   private IIcon field_150102_N;
/*  24:    */   private static final String __OBFID = "CL_00000322";
/*  25:    */   
/*  26:    */   protected BlockPane(String p_i45432_1_, String p_i45432_2_, Material p_i45432_3_, boolean p_i45432_4_)
/*  27:    */   {
/*  28: 27 */     super(p_i45432_3_);
/*  29: 28 */     this.field_150100_a = p_i45432_2_;
/*  30: 29 */     this.field_150099_b = p_i45432_4_;
/*  31: 30 */     this.field_150101_M = p_i45432_1_;
/*  32: 31 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/*  36:    */   {
/*  37: 36 */     return !this.field_150099_b ? null : super.getItemDropped(p_149650_1_, p_149650_2_, p_149650_3_);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean isOpaqueCube()
/*  41:    */   {
/*  42: 41 */     return false;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean renderAsNormalBlock()
/*  46:    */   {
/*  47: 46 */     return false;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getRenderType()
/*  51:    */   {
/*  52: 54 */     return this.blockMaterial == Material.glass ? 41 : 18;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
/*  56:    */   {
/*  57: 59 */     return p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_) == this ? false : super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_)
/*  61:    */   {
/*  62: 64 */     boolean var8 = func_150098_a(p_149743_1_.getBlock(p_149743_2_, p_149743_3_, p_149743_4_ - 1));
/*  63: 65 */     boolean var9 = func_150098_a(p_149743_1_.getBlock(p_149743_2_, p_149743_3_, p_149743_4_ + 1));
/*  64: 66 */     boolean var10 = func_150098_a(p_149743_1_.getBlock(p_149743_2_ - 1, p_149743_3_, p_149743_4_));
/*  65: 67 */     boolean var11 = func_150098_a(p_149743_1_.getBlock(p_149743_2_ + 1, p_149743_3_, p_149743_4_));
/*  66: 69 */     if (((!var10) || (!var11)) && ((var10) || (var11) || (var8) || (var9)))
/*  67:    */     {
/*  68: 71 */       if ((var10) && (!var11))
/*  69:    */       {
/*  70: 73 */         setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
/*  71: 74 */         super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  72:    */       }
/*  73: 76 */       else if ((!var10) && (var11))
/*  74:    */       {
/*  75: 78 */         setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
/*  76: 79 */         super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  77:    */       }
/*  78:    */     }
/*  79:    */     else
/*  80:    */     {
/*  81: 84 */       setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
/*  82: 85 */       super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  83:    */     }
/*  84: 88 */     if (((!var8) || (!var9)) && ((var10) || (var11) || (var8) || (var9)))
/*  85:    */     {
/*  86: 90 */       if ((var8) && (!var9))
/*  87:    */       {
/*  88: 92 */         setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
/*  89: 93 */         super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  90:    */       }
/*  91: 95 */       else if ((!var8) && (var9))
/*  92:    */       {
/*  93: 97 */         setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
/*  94: 98 */         super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  95:    */       }
/*  96:    */     }
/*  97:    */     else
/*  98:    */     {
/*  99:103 */       setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
/* 100:104 */       super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setBlockBoundsForItemRender()
/* 105:    */   {
/* 106:113 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/* 110:    */   {
/* 111:118 */     float var5 = 0.4375F;
/* 112:119 */     float var6 = 0.5625F;
/* 113:120 */     float var7 = 0.4375F;
/* 114:121 */     float var8 = 0.5625F;
/* 115:122 */     boolean var9 = func_150098_a(p_149719_1_.getBlock(p_149719_2_, p_149719_3_, p_149719_4_ - 1));
/* 116:123 */     boolean var10 = func_150098_a(p_149719_1_.getBlock(p_149719_2_, p_149719_3_, p_149719_4_ + 1));
/* 117:124 */     boolean var11 = func_150098_a(p_149719_1_.getBlock(p_149719_2_ - 1, p_149719_3_, p_149719_4_));
/* 118:125 */     boolean var12 = func_150098_a(p_149719_1_.getBlock(p_149719_2_ + 1, p_149719_3_, p_149719_4_));
/* 119:127 */     if (((!var11) || (!var12)) && ((var11) || (var12) || (var9) || (var10)))
/* 120:    */     {
/* 121:129 */       if ((var11) && (!var12)) {
/* 122:131 */         var5 = 0.0F;
/* 123:133 */       } else if ((!var11) && (var12)) {
/* 124:135 */         var6 = 1.0F;
/* 125:    */       }
/* 126:    */     }
/* 127:    */     else
/* 128:    */     {
/* 129:140 */       var5 = 0.0F;
/* 130:141 */       var6 = 1.0F;
/* 131:    */     }
/* 132:144 */     if (((!var9) || (!var10)) && ((var11) || (var12) || (var9) || (var10)))
/* 133:    */     {
/* 134:146 */       if ((var9) && (!var10)) {
/* 135:148 */         var7 = 0.0F;
/* 136:150 */       } else if ((!var9) && (var10)) {
/* 137:152 */         var8 = 1.0F;
/* 138:    */       }
/* 139:    */     }
/* 140:    */     else
/* 141:    */     {
/* 142:157 */       var7 = 0.0F;
/* 143:158 */       var8 = 1.0F;
/* 144:    */     }
/* 145:161 */     setBlockBounds(var5, 0.0F, var7, var6, 1.0F, var8);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public IIcon func_150097_e()
/* 149:    */   {
/* 150:166 */     return this.field_150102_N;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public final boolean func_150098_a(Block p_150098_1_)
/* 154:    */   {
/* 155:171 */     return (p_150098_1_.func_149730_j()) || (p_150098_1_ == this) || (p_150098_1_ == Blocks.glass) || (p_150098_1_ == Blocks.stained_glass) || (p_150098_1_ == Blocks.stained_glass_pane) || ((p_150098_1_ instanceof BlockPane));
/* 156:    */   }
/* 157:    */   
/* 158:    */   protected boolean canSilkHarvest()
/* 159:    */   {
/* 160:176 */     return true;
/* 161:    */   }
/* 162:    */   
/* 163:    */   protected ItemStack createStackedBlock(int p_149644_1_)
/* 164:    */   {
/* 165:185 */     return new ItemStack(Item.getItemFromBlock(this), 1, p_149644_1_);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 169:    */   {
/* 170:190 */     this.blockIcon = p_149651_1_.registerIcon(this.field_150101_M);
/* 171:191 */     this.field_150102_N = p_149651_1_.registerIcon(this.field_150100_a);
/* 172:    */   }
/* 173:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockPane
 * JD-Core Version:    0.7.0.1
 */