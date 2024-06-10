/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.entity.player.EntityPlayer;
/*   6:    */ import net.minecraft.entity.player.InventoryPlayer;
/*   7:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*   8:    */ import net.minecraft.init.Blocks;
/*   9:    */ import net.minecraft.init.Items;
/*  10:    */ import net.minecraft.item.Item;
/*  11:    */ import net.minecraft.item.ItemBlock;
/*  12:    */ import net.minecraft.item.ItemStack;
/*  13:    */ import net.minecraft.tileentity.TileEntity;
/*  14:    */ import net.minecraft.tileentity.TileEntityFlowerPot;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ 
/*  17:    */ public class BlockFlowerPot
/*  18:    */   extends BlockContainer
/*  19:    */ {
/*  20:    */   private static final String __OBFID = "CL_00000247";
/*  21:    */   
/*  22:    */   public BlockFlowerPot()
/*  23:    */   {
/*  24: 21 */     super(Material.circuits);
/*  25: 22 */     setBlockBoundsForItemRender();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setBlockBoundsForItemRender()
/*  29:    */   {
/*  30: 30 */     float var1 = 0.375F;
/*  31: 31 */     float var2 = var1 / 2.0F;
/*  32: 32 */     setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var1, 0.5F + var2);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean isOpaqueCube()
/*  36:    */   {
/*  37: 37 */     return false;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int getRenderType()
/*  41:    */   {
/*  42: 45 */     return 33;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean renderAsNormalBlock()
/*  46:    */   {
/*  47: 50 */     return false;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  51:    */   {
/*  52: 58 */     ItemStack var10 = p_149727_5_.inventory.getCurrentItem();
/*  53: 60 */     if ((var10 != null) && ((var10.getItem() instanceof ItemBlock)))
/*  54:    */     {
/*  55: 62 */       if (p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_) != 0) {
/*  56: 64 */         return false;
/*  57:    */       }
/*  58: 68 */       TileEntityFlowerPot var11 = func_149929_e(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
/*  59: 70 */       if (var11 != null)
/*  60:    */       {
/*  61: 72 */         Block var12 = Block.getBlockFromItem(var10.getItem());
/*  62: 74 */         if (!func_149928_a(var12, var10.getItemDamage())) {
/*  63: 76 */           return false;
/*  64:    */         }
/*  65: 80 */         var11.func_145964_a(var10.getItem(), var10.getItemDamage());
/*  66: 81 */         var11.onInventoryChanged();
/*  67: 83 */         if (!p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var10.getItemDamage(), 2)) {
/*  68: 85 */           p_149727_1_.func_147471_g(p_149727_2_, p_149727_3_, p_149727_4_);
/*  69:    */         }
/*  70: 88 */         if (!p_149727_5_.capabilities.isCreativeMode) {
/*  71: 88 */           if (--var10.stackSize <= 0) {
/*  72: 90 */             p_149727_5_.inventory.setInventorySlotContents(p_149727_5_.inventory.currentItem, null);
/*  73:    */           }
/*  74:    */         }
/*  75: 93 */         return true;
/*  76:    */       }
/*  77: 98 */       return false;
/*  78:    */     }
/*  79:104 */     return false;
/*  80:    */   }
/*  81:    */   
/*  82:    */   private boolean func_149928_a(Block p_149928_1_, int p_149928_2_)
/*  83:    */   {
/*  84:110 */     return (p_149928_1_ == Blocks.tallgrass) && (p_149928_2_ == 2);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/*  88:    */   {
/*  89:118 */     TileEntityFlowerPot var5 = func_149929_e(p_149694_1_, p_149694_2_, p_149694_3_, p_149694_4_);
/*  90:119 */     return (var5 != null) && (var5.func_145965_a() != null) ? var5.func_145965_a() : Items.flower_pot;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_)
/*  94:    */   {
/*  95:127 */     TileEntityFlowerPot var5 = func_149929_e(p_149643_1_, p_149643_2_, p_149643_3_, p_149643_4_);
/*  96:128 */     return (var5 != null) && (var5.func_145965_a() != null) ? var5.func_145966_b() : 0;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isFlowerPot()
/* 100:    */   {
/* 101:136 */     return true;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
/* 105:    */   {
/* 106:141 */     return (super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_)) && (World.doesBlockHaveSolidTopSurface(p_149742_1_, p_149742_2_, p_149742_3_ - 1, p_149742_4_));
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 110:    */   {
/* 111:146 */     if (!World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_))
/* 112:    */     {
/* 113:148 */       dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_), 0);
/* 114:149 */       p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
/* 119:    */   {
/* 120:158 */     super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, p_149690_7_);
/* 121:159 */     TileEntityFlowerPot var8 = func_149929_e(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_);
/* 122:161 */     if ((var8 != null) && (var8.func_145965_a() != null)) {
/* 123:163 */       dropBlockAsItem_do(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(var8.func_145965_a(), 1, var8.func_145966_b()));
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/* 128:    */   {
/* 129:169 */     TileEntityFlowerPot var7 = func_149929_e(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_);
/* 130:171 */     if ((var7 != null) && (var7.func_145965_a() != null)) {
/* 131:173 */       dropBlockAsItem_do(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, new ItemStack(var7.func_145965_a(), 1, var7.func_145966_b()));
/* 132:    */     }
/* 133:176 */     super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_)
/* 137:    */   {
/* 138:184 */     super.onBlockHarvested(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_, p_149681_6_);
/* 139:186 */     if (p_149681_6_.capabilities.isCreativeMode)
/* 140:    */     {
/* 141:188 */       TileEntityFlowerPot var7 = func_149929_e(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_);
/* 142:190 */       if (var7 != null) {
/* 143:192 */         var7.func_145964_a(Item.getItemById(0), 0);
/* 144:    */       }
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 149:    */   {
/* 150:199 */     return Items.flower_pot;
/* 151:    */   }
/* 152:    */   
/* 153:    */   private TileEntityFlowerPot func_149929_e(World p_149929_1_, int p_149929_2_, int p_149929_3_, int p_149929_4_)
/* 154:    */   {
/* 155:204 */     TileEntity var5 = p_149929_1_.getTileEntity(p_149929_2_, p_149929_3_, p_149929_4_);
/* 156:205 */     return (var5 != null) && ((var5 instanceof TileEntityFlowerPot)) ? (TileEntityFlowerPot)var5 : null;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
/* 160:    */   {
/* 161:213 */     Object var3 = null;
/* 162:214 */     byte var4 = 0;
/* 163:216 */     switch (p_149915_2_)
/* 164:    */     {
/* 165:    */     case 1: 
/* 166:219 */       var3 = Blocks.red_flower;
/* 167:220 */       var4 = 0;
/* 168:221 */       break;
/* 169:    */     case 2: 
/* 170:224 */       var3 = Blocks.yellow_flower;
/* 171:225 */       break;
/* 172:    */     case 3: 
/* 173:228 */       var3 = Blocks.sapling;
/* 174:229 */       var4 = 0;
/* 175:230 */       break;
/* 176:    */     case 4: 
/* 177:233 */       var3 = Blocks.sapling;
/* 178:234 */       var4 = 1;
/* 179:235 */       break;
/* 180:    */     case 5: 
/* 181:238 */       var3 = Blocks.sapling;
/* 182:239 */       var4 = 2;
/* 183:240 */       break;
/* 184:    */     case 6: 
/* 185:243 */       var3 = Blocks.sapling;
/* 186:244 */       var4 = 3;
/* 187:245 */       break;
/* 188:    */     case 7: 
/* 189:248 */       var3 = Blocks.red_mushroom;
/* 190:249 */       break;
/* 191:    */     case 8: 
/* 192:252 */       var3 = Blocks.brown_mushroom;
/* 193:253 */       break;
/* 194:    */     case 9: 
/* 195:256 */       var3 = Blocks.cactus;
/* 196:257 */       break;
/* 197:    */     case 10: 
/* 198:260 */       var3 = Blocks.deadbush;
/* 199:261 */       break;
/* 200:    */     case 11: 
/* 201:264 */       var3 = Blocks.tallgrass;
/* 202:265 */       var4 = 2;
/* 203:266 */       break;
/* 204:    */     case 12: 
/* 205:269 */       var3 = Blocks.sapling;
/* 206:270 */       var4 = 4;
/* 207:271 */       break;
/* 208:    */     case 13: 
/* 209:274 */       var3 = Blocks.sapling;
/* 210:275 */       var4 = 5;
/* 211:    */     }
/* 212:278 */     return new TileEntityFlowerPot(Item.getItemFromBlock((Block)var3), var4);
/* 213:    */   }
/* 214:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockFlowerPot
 * JD-Core Version:    0.7.0.1
 */