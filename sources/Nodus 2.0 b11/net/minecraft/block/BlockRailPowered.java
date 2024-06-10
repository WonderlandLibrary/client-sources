/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   4:    */ import net.minecraft.util.IIcon;
/*   5:    */ import net.minecraft.world.World;
/*   6:    */ 
/*   7:    */ public class BlockRailPowered
/*   8:    */   extends BlockRailBase
/*   9:    */ {
/*  10:    */   protected IIcon field_150059_b;
/*  11:    */   private static final String __OBFID = "CL_00000288";
/*  12:    */   
/*  13:    */   protected BlockRailPowered()
/*  14:    */   {
/*  15: 14 */     super(true);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  19:    */   {
/*  20: 22 */     return (p_149691_2_ & 0x8) == 0 ? this.blockIcon : this.field_150059_b;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/*  24:    */   {
/*  25: 27 */     super.registerBlockIcons(p_149651_1_);
/*  26: 28 */     this.field_150059_b = p_149651_1_.registerIcon(getTextureName() + "_powered");
/*  27:    */   }
/*  28:    */   
/*  29:    */   protected boolean func_150058_a(World p_150058_1_, int p_150058_2_, int p_150058_3_, int p_150058_4_, int p_150058_5_, boolean p_150058_6_, int p_150058_7_)
/*  30:    */   {
/*  31: 33 */     if (p_150058_7_ >= 8) {
/*  32: 35 */       return false;
/*  33:    */     }
/*  34: 39 */     int var8 = p_150058_5_ & 0x7;
/*  35: 40 */     boolean var9 = true;
/*  36: 42 */     switch (var8)
/*  37:    */     {
/*  38:    */     case 0: 
/*  39: 45 */       if (p_150058_6_) {
/*  40: 47 */         p_150058_4_++;
/*  41:    */       } else {
/*  42: 51 */         p_150058_4_--;
/*  43:    */       }
/*  44: 54 */       break;
/*  45:    */     case 1: 
/*  46: 57 */       if (p_150058_6_) {
/*  47: 59 */         p_150058_2_--;
/*  48:    */       } else {
/*  49: 63 */         p_150058_2_++;
/*  50:    */       }
/*  51: 66 */       break;
/*  52:    */     case 2: 
/*  53: 69 */       if (p_150058_6_)
/*  54:    */       {
/*  55: 71 */         p_150058_2_--;
/*  56:    */       }
/*  57:    */       else
/*  58:    */       {
/*  59: 75 */         p_150058_2_++;
/*  60: 76 */         p_150058_3_++;
/*  61: 77 */         var9 = false;
/*  62:    */       }
/*  63: 80 */       var8 = 1;
/*  64: 81 */       break;
/*  65:    */     case 3: 
/*  66: 84 */       if (p_150058_6_)
/*  67:    */       {
/*  68: 86 */         p_150058_2_--;
/*  69: 87 */         p_150058_3_++;
/*  70: 88 */         var9 = false;
/*  71:    */       }
/*  72:    */       else
/*  73:    */       {
/*  74: 92 */         p_150058_2_++;
/*  75:    */       }
/*  76: 95 */       var8 = 1;
/*  77: 96 */       break;
/*  78:    */     case 4: 
/*  79: 99 */       if (p_150058_6_)
/*  80:    */       {
/*  81:101 */         p_150058_4_++;
/*  82:    */       }
/*  83:    */       else
/*  84:    */       {
/*  85:105 */         p_150058_4_--;
/*  86:106 */         p_150058_3_++;
/*  87:107 */         var9 = false;
/*  88:    */       }
/*  89:110 */       var8 = 0;
/*  90:111 */       break;
/*  91:    */     case 5: 
/*  92:114 */       if (p_150058_6_)
/*  93:    */       {
/*  94:116 */         p_150058_4_++;
/*  95:117 */         p_150058_3_++;
/*  96:118 */         var9 = false;
/*  97:    */       }
/*  98:    */       else
/*  99:    */       {
/* 100:122 */         p_150058_4_--;
/* 101:    */       }
/* 102:125 */       var8 = 0;
/* 103:    */     }
/* 104:128 */     return func_150057_a(p_150058_1_, p_150058_2_, p_150058_3_, p_150058_4_, p_150058_6_, p_150058_7_, var8);
/* 105:    */   }
/* 106:    */   
/* 107:    */   protected boolean func_150057_a(World p_150057_1_, int p_150057_2_, int p_150057_3_, int p_150057_4_, boolean p_150057_5_, int p_150057_6_, int p_150057_7_)
/* 108:    */   {
/* 109:134 */     Block var8 = p_150057_1_.getBlock(p_150057_2_, p_150057_3_, p_150057_4_);
/* 110:136 */     if (var8 == this)
/* 111:    */     {
/* 112:138 */       int var9 = p_150057_1_.getBlockMetadata(p_150057_2_, p_150057_3_, p_150057_4_);
/* 113:139 */       int var10 = var9 & 0x7;
/* 114:141 */       if ((p_150057_7_ == 1) && ((var10 == 0) || (var10 == 4) || (var10 == 5))) {
/* 115:143 */         return false;
/* 116:    */       }
/* 117:146 */       if ((p_150057_7_ == 0) && ((var10 == 1) || (var10 == 2) || (var10 == 3))) {
/* 118:148 */         return false;
/* 119:    */       }
/* 120:151 */       if ((var9 & 0x8) != 0)
/* 121:    */       {
/* 122:153 */         if (p_150057_1_.isBlockIndirectlyGettingPowered(p_150057_2_, p_150057_3_, p_150057_4_)) {
/* 123:155 */           return true;
/* 124:    */         }
/* 125:158 */         return func_150058_a(p_150057_1_, p_150057_2_, p_150057_3_, p_150057_4_, var9, p_150057_5_, p_150057_6_ + 1);
/* 126:    */       }
/* 127:    */     }
/* 128:162 */     return false;
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected void func_150048_a(World p_150048_1_, int p_150048_2_, int p_150048_3_, int p_150048_4_, int p_150048_5_, int p_150048_6_, Block p_150048_7_)
/* 132:    */   {
/* 133:167 */     boolean var8 = p_150048_1_.isBlockIndirectlyGettingPowered(p_150048_2_, p_150048_3_, p_150048_4_);
/* 134:168 */     var8 = (var8) || (func_150058_a(p_150048_1_, p_150048_2_, p_150048_3_, p_150048_4_, p_150048_5_, true, 0)) || (func_150058_a(p_150048_1_, p_150048_2_, p_150048_3_, p_150048_4_, p_150048_5_, false, 0));
/* 135:169 */     boolean var9 = false;
/* 136:171 */     if ((var8) && ((p_150048_5_ & 0x8) == 0))
/* 137:    */     {
/* 138:173 */       p_150048_1_.setBlockMetadataWithNotify(p_150048_2_, p_150048_3_, p_150048_4_, p_150048_6_ | 0x8, 3);
/* 139:174 */       var9 = true;
/* 140:    */     }
/* 141:176 */     else if ((!var8) && ((p_150048_5_ & 0x8) != 0))
/* 142:    */     {
/* 143:178 */       p_150048_1_.setBlockMetadataWithNotify(p_150048_2_, p_150048_3_, p_150048_4_, p_150048_6_, 3);
/* 144:179 */       var9 = true;
/* 145:    */     }
/* 146:182 */     if (var9)
/* 147:    */     {
/* 148:184 */       p_150048_1_.notifyBlocksOfNeighborChange(p_150048_2_, p_150048_3_ - 1, p_150048_4_, this);
/* 149:186 */       if ((p_150048_6_ == 2) || (p_150048_6_ == 3) || (p_150048_6_ == 4) || (p_150048_6_ == 5)) {
/* 150:188 */         p_150048_1_.notifyBlocksOfNeighborChange(p_150048_2_, p_150048_3_ + 1, p_150048_4_, this);
/* 151:    */       }
/* 152:    */     }
/* 153:    */   }
/* 154:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockRailPowered
 * JD-Core Version:    0.7.0.1
 */