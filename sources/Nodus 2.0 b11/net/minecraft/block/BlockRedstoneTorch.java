/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Random;
/*   8:    */ import net.minecraft.init.Blocks;
/*   9:    */ import net.minecraft.item.Item;
/*  10:    */ import net.minecraft.world.IBlockAccess;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public class BlockRedstoneTorch
/*  14:    */   extends BlockTorch
/*  15:    */ {
/*  16:    */   private boolean field_150113_a;
/*  17: 17 */   private static Map field_150112_b = new HashMap();
/*  18:    */   private static final String __OBFID = "CL_00000298";
/*  19:    */   
/*  20:    */   private boolean func_150111_a(World p_150111_1_, int p_150111_2_, int p_150111_3_, int p_150111_4_, boolean p_150111_5_)
/*  21:    */   {
/*  22: 22 */     if (!field_150112_b.containsKey(p_150111_1_)) {
/*  23: 24 */       field_150112_b.put(p_150111_1_, new ArrayList());
/*  24:    */     }
/*  25: 27 */     List var6 = (List)field_150112_b.get(p_150111_1_);
/*  26: 29 */     if (p_150111_5_) {
/*  27: 31 */       var6.add(new Toggle(p_150111_2_, p_150111_3_, p_150111_4_, p_150111_1_.getTotalWorldTime()));
/*  28:    */     }
/*  29: 34 */     int var7 = 0;
/*  30: 36 */     for (int var8 = 0; var8 < var6.size(); var8++)
/*  31:    */     {
/*  32: 38 */       Toggle var9 = (Toggle)var6.get(var8);
/*  33: 40 */       if ((var9.field_150847_a == p_150111_2_) && (var9.field_150845_b == p_150111_3_) && (var9.field_150846_c == p_150111_4_))
/*  34:    */       {
/*  35: 42 */         var7++;
/*  36: 44 */         if (var7 >= 8) {
/*  37: 46 */           return true;
/*  38:    */         }
/*  39:    */       }
/*  40:    */     }
/*  41: 51 */     return false;
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected BlockRedstoneTorch(boolean p_i45423_1_)
/*  45:    */   {
/*  46: 56 */     this.field_150113_a = p_i45423_1_;
/*  47: 57 */     setTickRandomly(true);
/*  48: 58 */     setCreativeTab(null);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int func_149738_a(World p_149738_1_)
/*  52:    */   {
/*  53: 63 */     return 2;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/*  57:    */   {
/*  58: 68 */     if (p_149726_1_.getBlockMetadata(p_149726_2_, p_149726_3_, p_149726_4_) == 0) {
/*  59: 70 */       super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/*  60:    */     }
/*  61: 73 */     if (this.field_150113_a)
/*  62:    */     {
/*  63: 75 */       p_149726_1_.notifyBlocksOfNeighborChange(p_149726_2_, p_149726_3_ - 1, p_149726_4_, this);
/*  64: 76 */       p_149726_1_.notifyBlocksOfNeighborChange(p_149726_2_, p_149726_3_ + 1, p_149726_4_, this);
/*  65: 77 */       p_149726_1_.notifyBlocksOfNeighborChange(p_149726_2_ - 1, p_149726_3_, p_149726_4_, this);
/*  66: 78 */       p_149726_1_.notifyBlocksOfNeighborChange(p_149726_2_ + 1, p_149726_3_, p_149726_4_, this);
/*  67: 79 */       p_149726_1_.notifyBlocksOfNeighborChange(p_149726_2_, p_149726_3_, p_149726_4_ - 1, this);
/*  68: 80 */       p_149726_1_.notifyBlocksOfNeighborChange(p_149726_2_, p_149726_3_, p_149726_4_ + 1, this);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/*  73:    */   {
/*  74: 86 */     if (this.field_150113_a)
/*  75:    */     {
/*  76: 88 */       p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_ - 1, p_149749_4_, this);
/*  77: 89 */       p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_ + 1, p_149749_4_, this);
/*  78: 90 */       p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_ - 1, p_149749_3_, p_149749_4_, this);
/*  79: 91 */       p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_ + 1, p_149749_3_, p_149749_4_, this);
/*  80: 92 */       p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_ - 1, this);
/*  81: 93 */       p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_ + 1, this);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_)
/*  86:    */   {
/*  87: 99 */     if (!this.field_150113_a) {
/*  88:101 */       return 0;
/*  89:    */     }
/*  90:105 */     int var6 = p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_);
/*  91:106 */     return (var6 == 2) && (p_149709_5_ == 4) ? 0 : (var6 == 1) && (p_149709_5_ == 5) ? 0 : (var6 == 4) && (p_149709_5_ == 2) ? 0 : (var6 == 3) && (p_149709_5_ == 3) ? 0 : (var6 == 5) && (p_149709_5_ == 1) ? 0 : 15;
/*  92:    */   }
/*  93:    */   
/*  94:    */   private boolean func_150110_m(World p_150110_1_, int p_150110_2_, int p_150110_3_, int p_150110_4_)
/*  95:    */   {
/*  96:112 */     int var5 = p_150110_1_.getBlockMetadata(p_150110_2_, p_150110_3_, p_150110_4_);
/*  97:113 */     return (var5 == 5) && (p_150110_1_.getIndirectPowerOutput(p_150110_2_, p_150110_3_ - 1, p_150110_4_, 0));
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/* 101:    */   {
/* 102:121 */     boolean var6 = func_150110_m(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
/* 103:122 */     List var7 = (List)field_150112_b.get(p_149674_1_);
/* 104:124 */     while ((var7 != null) && (!var7.isEmpty()) && (p_149674_1_.getTotalWorldTime() - ((Toggle)var7.get(0)).field_150844_d > 60L)) {
/* 105:126 */       var7.remove(0);
/* 106:    */     }
/* 107:129 */     if (this.field_150113_a)
/* 108:    */     {
/* 109:131 */       if (var6)
/* 110:    */       {
/* 111:133 */         p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, Blocks.unlit_redstone_torch, p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_), 3);
/* 112:135 */         if (func_150111_a(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, true))
/* 113:    */         {
/* 114:137 */           p_149674_1_.playSoundEffect(p_149674_2_ + 0.5F, p_149674_3_ + 0.5F, p_149674_4_ + 0.5F, "random.fizz", 0.5F, 2.6F + (p_149674_1_.rand.nextFloat() - p_149674_1_.rand.nextFloat()) * 0.8F);
/* 115:139 */           for (int var8 = 0; var8 < 5; var8++)
/* 116:    */           {
/* 117:141 */             double var9 = p_149674_2_ + p_149674_5_.nextDouble() * 0.6D + 0.2D;
/* 118:142 */             double var11 = p_149674_3_ + p_149674_5_.nextDouble() * 0.6D + 0.2D;
/* 119:143 */             double var13 = p_149674_4_ + p_149674_5_.nextDouble() * 0.6D + 0.2D;
/* 120:144 */             p_149674_1_.spawnParticle("smoke", var9, var11, var13, 0.0D, 0.0D, 0.0D);
/* 121:    */           }
/* 122:    */         }
/* 123:    */       }
/* 124:    */     }
/* 125:149 */     else if ((!var6) && (!func_150111_a(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, false))) {
/* 126:151 */       p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, Blocks.redstone_torch, p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_), 3);
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 131:    */   {
/* 132:157 */     if (!func_150108_b(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_))
/* 133:    */     {
/* 134:159 */       boolean var6 = func_150110_m(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
/* 135:161 */       if (((this.field_150113_a) && (var6)) || ((!this.field_150113_a) && (!var6))) {
/* 136:163 */         p_149695_1_.scheduleBlockUpdate(p_149695_2_, p_149695_3_, p_149695_4_, this, func_149738_a(p_149695_1_));
/* 137:    */       }
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   public int isProvidingStrongPower(IBlockAccess p_149748_1_, int p_149748_2_, int p_149748_3_, int p_149748_4_, int p_149748_5_)
/* 142:    */   {
/* 143:170 */     return p_149748_5_ == 0 ? isProvidingWeakPower(p_149748_1_, p_149748_2_, p_149748_3_, p_149748_4_, p_149748_5_) : 0;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 147:    */   {
/* 148:175 */     return Item.getItemFromBlock(Blocks.redstone_torch);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean canProvidePower()
/* 152:    */   {
/* 153:183 */     return true;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
/* 157:    */   {
/* 158:191 */     if (this.field_150113_a)
/* 159:    */     {
/* 160:193 */       int var6 = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);
/* 161:194 */       double var7 = p_149734_2_ + 0.5F + (p_149734_5_.nextFloat() - 0.5F) * 0.2D;
/* 162:195 */       double var9 = p_149734_3_ + 0.7F + (p_149734_5_.nextFloat() - 0.5F) * 0.2D;
/* 163:196 */       double var11 = p_149734_4_ + 0.5F + (p_149734_5_.nextFloat() - 0.5F) * 0.2D;
/* 164:197 */       double var13 = 0.219999998807907D;
/* 165:198 */       double var15 = 0.2700000107288361D;
/* 166:200 */       if (var6 == 1) {
/* 167:202 */         p_149734_1_.spawnParticle("reddust", var7 - var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
/* 168:204 */       } else if (var6 == 2) {
/* 169:206 */         p_149734_1_.spawnParticle("reddust", var7 + var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
/* 170:208 */       } else if (var6 == 3) {
/* 171:210 */         p_149734_1_.spawnParticle("reddust", var7, var9 + var13, var11 - var15, 0.0D, 0.0D, 0.0D);
/* 172:212 */       } else if (var6 == 4) {
/* 173:214 */         p_149734_1_.spawnParticle("reddust", var7, var9 + var13, var11 + var15, 0.0D, 0.0D, 0.0D);
/* 174:    */       } else {
/* 175:218 */         p_149734_1_.spawnParticle("reddust", var7, var9, var11, 0.0D, 0.0D, 0.0D);
/* 176:    */       }
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 181:    */   {
/* 182:228 */     return Item.getItemFromBlock(Blocks.redstone_torch);
/* 183:    */   }
/* 184:    */   
/* 185:    */   public boolean func_149667_c(Block p_149667_1_)
/* 186:    */   {
/* 187:233 */     return (p_149667_1_ == Blocks.unlit_redstone_torch) || (p_149667_1_ == Blocks.redstone_torch);
/* 188:    */   }
/* 189:    */   
/* 190:    */   static class Toggle
/* 191:    */   {
/* 192:    */     int field_150847_a;
/* 193:    */     int field_150845_b;
/* 194:    */     int field_150846_c;
/* 195:    */     long field_150844_d;
/* 196:    */     private static final String __OBFID = "CL_00000299";
/* 197:    */     
/* 198:    */     public Toggle(int p_i45422_1_, int p_i45422_2_, int p_i45422_3_, long p_i45422_4_)
/* 199:    */     {
/* 200:246 */       this.field_150847_a = p_i45422_1_;
/* 201:247 */       this.field_150845_b = p_i45422_2_;
/* 202:248 */       this.field_150846_c = p_i45422_3_;
/* 203:249 */       this.field_150844_d = p_i45422_4_;
/* 204:    */     }
/* 205:    */   }
/* 206:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockRedstoneTorch
 * JD-Core Version:    0.7.0.1
 */