/*   1:    */ package net.minecraft.tileentity;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import net.minecraft.block.Block;
/*   7:    */ import net.minecraft.block.BlockPistonMoving;
/*   8:    */ import net.minecraft.entity.Entity;
/*   9:    */ import net.minecraft.init.Blocks;
/*  10:    */ import net.minecraft.nbt.NBTTagCompound;
/*  11:    */ import net.minecraft.util.AxisAlignedBB;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ 
/*  14:    */ public class TileEntityPiston
/*  15:    */   extends TileEntity
/*  16:    */ {
/*  17:    */   private Block field_145869_a;
/*  18:    */   private int field_145876_i;
/*  19:    */   private int field_145874_j;
/*  20:    */   private boolean field_145875_k;
/*  21:    */   private boolean field_145872_l;
/*  22:    */   private float field_145873_m;
/*  23:    */   private float field_145870_n;
/*  24: 22 */   private List field_145871_o = new ArrayList();
/*  25:    */   private static final String __OBFID = "CL_00000369";
/*  26:    */   
/*  27:    */   public TileEntityPiston() {}
/*  28:    */   
/*  29:    */   public TileEntityPiston(Block p_i45444_1_, int p_i45444_2_, int p_i45444_3_, boolean p_i45444_4_, boolean p_i45444_5_)
/*  30:    */   {
/*  31: 29 */     this.field_145869_a = p_i45444_1_;
/*  32: 30 */     this.field_145876_i = p_i45444_2_;
/*  33: 31 */     this.field_145874_j = p_i45444_3_;
/*  34: 32 */     this.field_145875_k = p_i45444_4_;
/*  35: 33 */     this.field_145872_l = p_i45444_5_;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Block func_145861_a()
/*  39:    */   {
/*  40: 38 */     return this.field_145869_a;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int getBlockMetadata()
/*  44:    */   {
/*  45: 43 */     return this.field_145876_i;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean func_145868_b()
/*  49:    */   {
/*  50: 48 */     return this.field_145875_k;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int func_145864_c()
/*  54:    */   {
/*  55: 53 */     return this.field_145874_j;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean func_145867_d()
/*  59:    */   {
/*  60: 58 */     return this.field_145872_l;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public float func_145860_a(float p_145860_1_)
/*  64:    */   {
/*  65: 63 */     if (p_145860_1_ > 1.0F) {
/*  66: 65 */       p_145860_1_ = 1.0F;
/*  67:    */     }
/*  68: 68 */     return this.field_145870_n + (this.field_145873_m - this.field_145870_n) * p_145860_1_;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public float func_145865_b(float p_145865_1_)
/*  72:    */   {
/*  73: 73 */     return this.field_145875_k ? (func_145860_a(p_145865_1_) - 1.0F) * net.minecraft.util.Facing.offsetsXForSide[this.field_145874_j] : (1.0F - func_145860_a(p_145865_1_)) * net.minecraft.util.Facing.offsetsXForSide[this.field_145874_j];
/*  74:    */   }
/*  75:    */   
/*  76:    */   public float func_145862_c(float p_145862_1_)
/*  77:    */   {
/*  78: 78 */     return this.field_145875_k ? (func_145860_a(p_145862_1_) - 1.0F) * net.minecraft.util.Facing.offsetsYForSide[this.field_145874_j] : (1.0F - func_145860_a(p_145862_1_)) * net.minecraft.util.Facing.offsetsYForSide[this.field_145874_j];
/*  79:    */   }
/*  80:    */   
/*  81:    */   public float func_145859_d(float p_145859_1_)
/*  82:    */   {
/*  83: 83 */     return this.field_145875_k ? (func_145860_a(p_145859_1_) - 1.0F) * net.minecraft.util.Facing.offsetsZForSide[this.field_145874_j] : (1.0F - func_145860_a(p_145859_1_)) * net.minecraft.util.Facing.offsetsZForSide[this.field_145874_j];
/*  84:    */   }
/*  85:    */   
/*  86:    */   private void func_145863_a(float p_145863_1_, float p_145863_2_)
/*  87:    */   {
/*  88: 88 */     if (this.field_145875_k) {
/*  89: 90 */       p_145863_1_ = 1.0F - p_145863_1_;
/*  90:    */     } else {
/*  91: 94 */       p_145863_1_ -= 1.0F;
/*  92:    */     }
/*  93: 97 */     AxisAlignedBB var3 = Blocks.piston_extension.func_149964_a(this.worldObj, this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145869_a, p_145863_1_, this.field_145874_j);
/*  94: 99 */     if (var3 != null)
/*  95:    */     {
/*  96:101 */       List var4 = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, var3);
/*  97:103 */       if (!var4.isEmpty())
/*  98:    */       {
/*  99:105 */         this.field_145871_o.addAll(var4);
/* 100:106 */         Iterator var5 = this.field_145871_o.iterator();
/* 101:108 */         while (var5.hasNext())
/* 102:    */         {
/* 103:110 */           Entity var6 = (Entity)var5.next();
/* 104:111 */           var6.moveEntity(p_145863_2_ * net.minecraft.util.Facing.offsetsXForSide[this.field_145874_j], p_145863_2_ * net.minecraft.util.Facing.offsetsYForSide[this.field_145874_j], p_145863_2_ * net.minecraft.util.Facing.offsetsZForSide[this.field_145874_j]);
/* 105:    */         }
/* 106:114 */         this.field_145871_o.clear();
/* 107:    */       }
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void func_145866_f()
/* 112:    */   {
/* 113:121 */     if ((this.field_145870_n < 1.0F) && (this.worldObj != null))
/* 114:    */     {
/* 115:123 */       this.field_145870_n = (this.field_145873_m = 1.0F);
/* 116:124 */       this.worldObj.removeTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 117:125 */       invalidate();
/* 118:127 */       if (this.worldObj.getBlock(this.field_145851_c, this.field_145848_d, this.field_145849_e) == Blocks.piston_extension)
/* 119:    */       {
/* 120:129 */         this.worldObj.setBlock(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145869_a, this.field_145876_i, 3);
/* 121:130 */         this.worldObj.func_147460_e(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145869_a);
/* 122:    */       }
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void updateEntity()
/* 127:    */   {
/* 128:137 */     this.field_145870_n = this.field_145873_m;
/* 129:139 */     if (this.field_145870_n >= 1.0F)
/* 130:    */     {
/* 131:141 */       func_145863_a(1.0F, 0.25F);
/* 132:142 */       this.worldObj.removeTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/* 133:143 */       invalidate();
/* 134:145 */       if (this.worldObj.getBlock(this.field_145851_c, this.field_145848_d, this.field_145849_e) == Blocks.piston_extension)
/* 135:    */       {
/* 136:147 */         this.worldObj.setBlock(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145869_a, this.field_145876_i, 3);
/* 137:148 */         this.worldObj.func_147460_e(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145869_a);
/* 138:    */       }
/* 139:    */     }
/* 140:    */     else
/* 141:    */     {
/* 142:153 */       this.field_145873_m += 0.5F;
/* 143:155 */       if (this.field_145873_m >= 1.0F) {
/* 144:157 */         this.field_145873_m = 1.0F;
/* 145:    */       }
/* 146:160 */       if (this.field_145875_k) {
/* 147:162 */         func_145863_a(this.field_145873_m, this.field_145873_m - this.field_145870_n + 0.0625F);
/* 148:    */       }
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void readFromNBT(NBTTagCompound p_145839_1_)
/* 153:    */   {
/* 154:169 */     super.readFromNBT(p_145839_1_);
/* 155:170 */     this.field_145869_a = Block.getBlockById(p_145839_1_.getInteger("blockId"));
/* 156:171 */     this.field_145876_i = p_145839_1_.getInteger("blockData");
/* 157:172 */     this.field_145874_j = p_145839_1_.getInteger("facing");
/* 158:173 */     this.field_145870_n = (this.field_145873_m = p_145839_1_.getFloat("progress"));
/* 159:174 */     this.field_145875_k = p_145839_1_.getBoolean("extending");
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void writeToNBT(NBTTagCompound p_145841_1_)
/* 163:    */   {
/* 164:179 */     super.writeToNBT(p_145841_1_);
/* 165:180 */     p_145841_1_.setInteger("blockId", Block.getIdFromBlock(this.field_145869_a));
/* 166:181 */     p_145841_1_.setInteger("blockData", this.field_145876_i);
/* 167:182 */     p_145841_1_.setInteger("facing", this.field_145874_j);
/* 168:183 */     p_145841_1_.setFloat("progress", this.field_145870_n);
/* 169:184 */     p_145841_1_.setBoolean("extending", this.field_145875_k);
/* 170:    */   }
/* 171:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.tileentity.TileEntityPiston
 * JD-Core Version:    0.7.0.1
 */