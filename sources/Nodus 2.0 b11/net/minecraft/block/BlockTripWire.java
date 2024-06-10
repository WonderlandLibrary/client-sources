/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Random;
/*   6:    */ import net.minecraft.block.material.Material;
/*   7:    */ import net.minecraft.entity.Entity;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
/*   9:    */ import net.minecraft.init.Blocks;
/*  10:    */ import net.minecraft.init.Items;
/*  11:    */ import net.minecraft.item.Item;
/*  12:    */ import net.minecraft.item.ItemStack;
/*  13:    */ import net.minecraft.util.AABBPool;
/*  14:    */ import net.minecraft.util.AxisAlignedBB;
/*  15:    */ import net.minecraft.world.IBlockAccess;
/*  16:    */ import net.minecraft.world.World;
/*  17:    */ 
/*  18:    */ public class BlockTripWire
/*  19:    */   extends Block
/*  20:    */ {
/*  21:    */   private static final String __OBFID = "CL_00000328";
/*  22:    */   
/*  23:    */   public BlockTripWire()
/*  24:    */   {
/*  25: 23 */     super(Material.circuits);
/*  26: 24 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
/*  27: 25 */     setTickRandomly(true);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public int func_149738_a(World p_149738_1_)
/*  31:    */   {
/*  32: 30 */     return 10;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  36:    */   {
/*  37: 39 */     return null;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean isOpaqueCube()
/*  41:    */   {
/*  42: 44 */     return false;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean renderAsNormalBlock()
/*  46:    */   {
/*  47: 49 */     return false;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int getRenderBlockPass()
/*  51:    */   {
/*  52: 57 */     return 1;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getRenderType()
/*  56:    */   {
/*  57: 65 */     return 30;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/*  61:    */   {
/*  62: 70 */     return Items.string;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/*  66:    */   {
/*  67: 78 */     return Items.string;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/*  71:    */   {
/*  72: 83 */     int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
/*  73: 84 */     boolean var7 = (var6 & 0x2) == 2;
/*  74: 85 */     boolean var8 = !World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_);
/*  75: 87 */     if (var7 != var8)
/*  76:    */     {
/*  77: 89 */       dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, var6, 0);
/*  78: 90 */       p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  83:    */   {
/*  84: 96 */     int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
/*  85: 97 */     boolean var6 = (var5 & 0x4) == 4;
/*  86: 98 */     boolean var7 = (var5 & 0x2) == 2;
/*  87:100 */     if (!var7) {
/*  88:102 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.09375F, 1.0F);
/*  89:104 */     } else if (!var6) {
/*  90:106 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*  91:    */     } else {
/*  92:110 */       setBlockBounds(0.0F, 0.0625F, 0.0F, 1.0F, 0.15625F, 1.0F);
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/*  97:    */   {
/*  98:116 */     int var5 = World.doesBlockHaveSolidTopSurface(p_149726_1_, p_149726_2_, p_149726_3_ - 1, p_149726_4_) ? 0 : 2;
/*  99:117 */     p_149726_1_.setBlockMetadataWithNotify(p_149726_2_, p_149726_3_, p_149726_4_, var5, 3);
/* 100:118 */     func_150138_a(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_, var5);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/* 104:    */   {
/* 105:123 */     func_150138_a(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_6_ | 0x1);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_)
/* 109:    */   {
/* 110:131 */     if (!p_149681_1_.isClient) {
/* 111:133 */       if ((p_149681_6_.getCurrentEquippedItem() != null) && (p_149681_6_.getCurrentEquippedItem().getItem() == Items.shears)) {
/* 112:135 */         p_149681_1_.setBlockMetadataWithNotify(p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_ | 0x8, 4);
/* 113:    */       }
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   private void func_150138_a(World p_150138_1_, int p_150138_2_, int p_150138_3_, int p_150138_4_, int p_150138_5_)
/* 118:    */   {
/* 119:142 */     int var6 = 0;
/* 120:144 */     while (var6 < 2)
/* 121:    */     {
/* 122:146 */       int var7 = 1;
/* 123:150 */       while (var7 < 42)
/* 124:    */       {
/* 125:152 */         int var8 = p_150138_2_ + net.minecraft.util.Direction.offsetX[var6] * var7;
/* 126:153 */         int var9 = p_150138_4_ + net.minecraft.util.Direction.offsetZ[var6] * var7;
/* 127:154 */         Block var10 = p_150138_1_.getBlock(var8, p_150138_3_, var9);
/* 128:156 */         if (var10 == Blocks.tripwire_hook)
/* 129:    */         {
/* 130:158 */           int var11 = p_150138_1_.getBlockMetadata(var8, p_150138_3_, var9) & 0x3;
/* 131:160 */           if (var11 != net.minecraft.util.Direction.rotateOpposite[var6]) {
/* 132:    */             break;
/* 133:    */           }
/* 134:162 */           Blocks.tripwire_hook.func_150136_a(p_150138_1_, var8, p_150138_3_, var9, false, p_150138_1_.getBlockMetadata(var8, p_150138_3_, var9), true, var7, p_150138_5_);
/* 135:    */           
/* 136:164 */           break;
/* 137:    */         }
/* 138:165 */         if (var10 != Blocks.tripwire) {
/* 139:    */           break;
/* 140:    */         }
/* 141:167 */         var7++;
/* 142:    */       }
/* 143:172 */       var6++;
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_)
/* 148:    */   {
/* 149:180 */     if (!p_149670_1_.isClient) {
/* 150:182 */       if ((p_149670_1_.getBlockMetadata(p_149670_2_, p_149670_3_, p_149670_4_) & 0x1) != 1) {
/* 151:184 */         func_150140_e(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_);
/* 152:    */       }
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/* 157:    */   {
/* 158:194 */     if (!p_149674_1_.isClient) {
/* 159:196 */       if ((p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_) & 0x1) == 1) {
/* 160:198 */         func_150140_e(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
/* 161:    */       }
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   private void func_150140_e(World p_150140_1_, int p_150140_2_, int p_150140_3_, int p_150140_4_)
/* 166:    */   {
/* 167:205 */     int var5 = p_150140_1_.getBlockMetadata(p_150140_2_, p_150140_3_, p_150140_4_);
/* 168:206 */     boolean var6 = (var5 & 0x1) == 1;
/* 169:207 */     boolean var7 = false;
/* 170:208 */     List var8 = p_150140_1_.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getAABBPool().getAABB(p_150140_2_ + this.field_149759_B, p_150140_3_ + this.field_149760_C, p_150140_4_ + this.field_149754_D, p_150140_2_ + this.field_149755_E, p_150140_3_ + this.field_149756_F, p_150140_4_ + this.field_149757_G));
/* 171:210 */     if (!var8.isEmpty())
/* 172:    */     {
/* 173:212 */       Iterator var9 = var8.iterator();
/* 174:214 */       while (var9.hasNext())
/* 175:    */       {
/* 176:216 */         Entity var10 = (Entity)var9.next();
/* 177:218 */         if (!var10.doesEntityNotTriggerPressurePlate())
/* 178:    */         {
/* 179:220 */           var7 = true;
/* 180:221 */           break;
/* 181:    */         }
/* 182:    */       }
/* 183:    */     }
/* 184:226 */     if ((var7) && (!var6)) {
/* 185:228 */       var5 |= 0x1;
/* 186:    */     }
/* 187:231 */     if ((!var7) && (var6)) {
/* 188:233 */       var5 &= 0xFFFFFFFE;
/* 189:    */     }
/* 190:236 */     if (var7 != var6)
/* 191:    */     {
/* 192:238 */       p_150140_1_.setBlockMetadataWithNotify(p_150140_2_, p_150140_3_, p_150140_4_, var5, 3);
/* 193:239 */       func_150138_a(p_150140_1_, p_150140_2_, p_150140_3_, p_150140_4_, var5);
/* 194:    */     }
/* 195:242 */     if (var7) {
/* 196:244 */       p_150140_1_.scheduleBlockUpdate(p_150140_2_, p_150140_3_, p_150140_4_, this, func_149738_a(p_150140_1_));
/* 197:    */     }
/* 198:    */   }
/* 199:    */   
/* 200:    */   public static boolean func_150139_a(IBlockAccess p_150139_0_, int p_150139_1_, int p_150139_2_, int p_150139_3_, int p_150139_4_, int p_150139_5_)
/* 201:    */   {
/* 202:250 */     int var6 = p_150139_1_ + net.minecraft.util.Direction.offsetX[p_150139_5_];
/* 203:251 */     int var8 = p_150139_3_ + net.minecraft.util.Direction.offsetZ[p_150139_5_];
/* 204:252 */     Block var9 = p_150139_0_.getBlock(var6, p_150139_2_, var8);
/* 205:253 */     boolean var10 = (p_150139_4_ & 0x2) == 2;
/* 206:256 */     if (var9 == Blocks.tripwire_hook)
/* 207:    */     {
/* 208:258 */       int var11 = p_150139_0_.getBlockMetadata(var6, p_150139_2_, var8);
/* 209:259 */       int var13 = var11 & 0x3;
/* 210:260 */       return var13 == net.minecraft.util.Direction.rotateOpposite[p_150139_5_];
/* 211:    */     }
/* 212:262 */     if (var9 == Blocks.tripwire)
/* 213:    */     {
/* 214:264 */       int var11 = p_150139_0_.getBlockMetadata(var6, p_150139_2_, var8);
/* 215:265 */       boolean var12 = (var11 & 0x2) == 2;
/* 216:266 */       return var10 == var12;
/* 217:    */     }
/* 218:270 */     return false;
/* 219:    */   }
/* 220:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockTripWire
 * JD-Core Version:    0.7.0.1
 */