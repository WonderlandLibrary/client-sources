/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.entity.EntityLivingBase;
/*   7:    */ import net.minecraft.entity.item.EntityItem;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
/*   9:    */ import net.minecraft.init.Blocks;
/*  10:    */ import net.minecraft.inventory.Container;
/*  11:    */ import net.minecraft.inventory.IInventory;
/*  12:    */ import net.minecraft.item.Item;
/*  13:    */ import net.minecraft.item.ItemStack;
/*  14:    */ import net.minecraft.nbt.NBTTagCompound;
/*  15:    */ import net.minecraft.tileentity.TileEntity;
/*  16:    */ import net.minecraft.tileentity.TileEntityFurnace;
/*  17:    */ import net.minecraft.util.IIcon;
/*  18:    */ import net.minecraft.util.MathHelper;
/*  19:    */ import net.minecraft.world.World;
/*  20:    */ 
/*  21:    */ public class BlockFurnace
/*  22:    */   extends BlockContainer
/*  23:    */ {
/*  24: 23 */   private final Random field_149933_a = new Random();
/*  25:    */   private final boolean field_149932_b;
/*  26:    */   private static boolean field_149934_M;
/*  27:    */   private IIcon field_149935_N;
/*  28:    */   private IIcon field_149936_O;
/*  29:    */   private static final String __OBFID = "CL_00000248";
/*  30:    */   
/*  31:    */   protected BlockFurnace(boolean p_i45407_1_)
/*  32:    */   {
/*  33: 32 */     super(Material.rock);
/*  34: 33 */     this.field_149932_b = p_i45407_1_;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/*  38:    */   {
/*  39: 38 */     return Item.getItemFromBlock(Blocks.furnace);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/*  43:    */   {
/*  44: 43 */     super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/*  45: 44 */     func_149930_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/*  46:    */   }
/*  47:    */   
/*  48:    */   private void func_149930_e(World p_149930_1_, int p_149930_2_, int p_149930_3_, int p_149930_4_)
/*  49:    */   {
/*  50: 49 */     if (!p_149930_1_.isClient)
/*  51:    */     {
/*  52: 51 */       Block var5 = p_149930_1_.getBlock(p_149930_2_, p_149930_3_, p_149930_4_ - 1);
/*  53: 52 */       Block var6 = p_149930_1_.getBlock(p_149930_2_, p_149930_3_, p_149930_4_ + 1);
/*  54: 53 */       Block var7 = p_149930_1_.getBlock(p_149930_2_ - 1, p_149930_3_, p_149930_4_);
/*  55: 54 */       Block var8 = p_149930_1_.getBlock(p_149930_2_ + 1, p_149930_3_, p_149930_4_);
/*  56: 55 */       byte var9 = 3;
/*  57: 57 */       if ((var5.func_149730_j()) && (!var6.func_149730_j())) {
/*  58: 59 */         var9 = 3;
/*  59:    */       }
/*  60: 62 */       if ((var6.func_149730_j()) && (!var5.func_149730_j())) {
/*  61: 64 */         var9 = 2;
/*  62:    */       }
/*  63: 67 */       if ((var7.func_149730_j()) && (!var8.func_149730_j())) {
/*  64: 69 */         var9 = 5;
/*  65:    */       }
/*  66: 72 */       if ((var8.func_149730_j()) && (!var7.func_149730_j())) {
/*  67: 74 */         var9 = 4;
/*  68:    */       }
/*  69: 77 */       p_149930_1_.setBlockMetadataWithNotify(p_149930_2_, p_149930_3_, p_149930_4_, var9, 2);
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  74:    */   {
/*  75: 86 */     return p_149691_1_ != p_149691_2_ ? this.blockIcon : p_149691_1_ == 0 ? this.field_149935_N : p_149691_1_ == 1 ? this.field_149935_N : this.field_149936_O;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/*  79:    */   {
/*  80: 91 */     this.blockIcon = p_149651_1_.registerIcon("furnace_side");
/*  81: 92 */     this.field_149936_O = p_149651_1_.registerIcon(this.field_149932_b ? "furnace_front_on" : "furnace_front_off");
/*  82: 93 */     this.field_149935_N = p_149651_1_.registerIcon("furnace_top");
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
/*  86:    */   {
/*  87:101 */     if (this.field_149932_b)
/*  88:    */     {
/*  89:103 */       int var6 = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);
/*  90:104 */       float var7 = p_149734_2_ + 0.5F;
/*  91:105 */       float var8 = p_149734_3_ + 0.0F + p_149734_5_.nextFloat() * 6.0F / 16.0F;
/*  92:106 */       float var9 = p_149734_4_ + 0.5F;
/*  93:107 */       float var10 = 0.52F;
/*  94:108 */       float var11 = p_149734_5_.nextFloat() * 0.6F - 0.3F;
/*  95:110 */       if (var6 == 4)
/*  96:    */       {
/*  97:112 */         p_149734_1_.spawnParticle("smoke", var7 - var10, var8, var9 + var11, 0.0D, 0.0D, 0.0D);
/*  98:113 */         p_149734_1_.spawnParticle("flame", var7 - var10, var8, var9 + var11, 0.0D, 0.0D, 0.0D);
/*  99:    */       }
/* 100:115 */       else if (var6 == 5)
/* 101:    */       {
/* 102:117 */         p_149734_1_.spawnParticle("smoke", var7 + var10, var8, var9 + var11, 0.0D, 0.0D, 0.0D);
/* 103:118 */         p_149734_1_.spawnParticle("flame", var7 + var10, var8, var9 + var11, 0.0D, 0.0D, 0.0D);
/* 104:    */       }
/* 105:120 */       else if (var6 == 2)
/* 106:    */       {
/* 107:122 */         p_149734_1_.spawnParticle("smoke", var7 + var11, var8, var9 - var10, 0.0D, 0.0D, 0.0D);
/* 108:123 */         p_149734_1_.spawnParticle("flame", var7 + var11, var8, var9 - var10, 0.0D, 0.0D, 0.0D);
/* 109:    */       }
/* 110:125 */       else if (var6 == 3)
/* 111:    */       {
/* 112:127 */         p_149734_1_.spawnParticle("smoke", var7 + var11, var8, var9 + var10, 0.0D, 0.0D, 0.0D);
/* 113:128 */         p_149734_1_.spawnParticle("flame", var7 + var11, var8, var9 + var10, 0.0D, 0.0D, 0.0D);
/* 114:    */       }
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/* 119:    */   {
/* 120:138 */     if (p_149727_1_.isClient) {
/* 121:140 */       return true;
/* 122:    */     }
/* 123:144 */     TileEntityFurnace var10 = (TileEntityFurnace)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
/* 124:146 */     if (var10 != null) {
/* 125:148 */       p_149727_5_.func_146101_a(var10);
/* 126:    */     }
/* 127:151 */     return true;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static void func_149931_a(boolean p_149931_0_, World p_149931_1_, int p_149931_2_, int p_149931_3_, int p_149931_4_)
/* 131:    */   {
/* 132:157 */     int var5 = p_149931_1_.getBlockMetadata(p_149931_2_, p_149931_3_, p_149931_4_);
/* 133:158 */     TileEntity var6 = p_149931_1_.getTileEntity(p_149931_2_, p_149931_3_, p_149931_4_);
/* 134:159 */     field_149934_M = true;
/* 135:161 */     if (p_149931_0_) {
/* 136:163 */       p_149931_1_.setBlock(p_149931_2_, p_149931_3_, p_149931_4_, Blocks.lit_furnace);
/* 137:    */     } else {
/* 138:167 */       p_149931_1_.setBlock(p_149931_2_, p_149931_3_, p_149931_4_, Blocks.furnace);
/* 139:    */     }
/* 140:170 */     field_149934_M = false;
/* 141:171 */     p_149931_1_.setBlockMetadataWithNotify(p_149931_2_, p_149931_3_, p_149931_4_, var5, 2);
/* 142:173 */     if (var6 != null)
/* 143:    */     {
/* 144:175 */       var6.validate();
/* 145:176 */       p_149931_1_.setTileEntity(p_149931_2_, p_149931_3_, p_149931_4_, var6);
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
/* 150:    */   {
/* 151:185 */     return new TileEntityFurnace();
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
/* 155:    */   {
/* 156:193 */     int var7 = MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
/* 157:195 */     if (var7 == 0) {
/* 158:197 */       p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 2, 2);
/* 159:    */     }
/* 160:200 */     if (var7 == 1) {
/* 161:202 */       p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 5, 2);
/* 162:    */     }
/* 163:205 */     if (var7 == 2) {
/* 164:207 */       p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 3, 2);
/* 165:    */     }
/* 166:210 */     if (var7 == 3) {
/* 167:212 */       p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 4, 2);
/* 168:    */     }
/* 169:215 */     if (p_149689_6_.hasDisplayName()) {
/* 170:217 */       ((TileEntityFurnace)p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_)).func_145951_a(p_149689_6_.getDisplayName());
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/* 175:    */   {
/* 176:223 */     if (!field_149934_M)
/* 177:    */     {
/* 178:225 */       TileEntityFurnace var7 = (TileEntityFurnace)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
/* 179:227 */       if (var7 != null)
/* 180:    */       {
/* 181:229 */         for (int var8 = 0; var8 < var7.getSizeInventory(); var8++)
/* 182:    */         {
/* 183:231 */           ItemStack var9 = var7.getStackInSlot(var8);
/* 184:233 */           if (var9 != null)
/* 185:    */           {
/* 186:235 */             float var10 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
/* 187:236 */             float var11 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
/* 188:237 */             float var12 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
/* 189:239 */             while (var9.stackSize > 0)
/* 190:    */             {
/* 191:241 */               int var13 = this.field_149933_a.nextInt(21) + 10;
/* 192:243 */               if (var13 > var9.stackSize) {
/* 193:245 */                 var13 = var9.stackSize;
/* 194:    */               }
/* 195:248 */               var9.stackSize -= var13;
/* 196:249 */               EntityItem var14 = new EntityItem(p_149749_1_, p_149749_2_ + var10, p_149749_3_ + var11, p_149749_4_ + var12, new ItemStack(var9.getItem(), var13, var9.getItemDamage()));
/* 197:251 */               if (var9.hasTagCompound()) {
/* 198:253 */                 var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
/* 199:    */               }
/* 200:256 */               float var15 = 0.05F;
/* 201:257 */               var14.motionX = ((float)this.field_149933_a.nextGaussian() * var15);
/* 202:258 */               var14.motionY = ((float)this.field_149933_a.nextGaussian() * var15 + 0.2F);
/* 203:259 */               var14.motionZ = ((float)this.field_149933_a.nextGaussian() * var15);
/* 204:260 */               p_149749_1_.spawnEntityInWorld(var14);
/* 205:    */             }
/* 206:    */           }
/* 207:    */         }
/* 208:265 */         p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
/* 209:    */       }
/* 210:    */     }
/* 211:269 */     super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public boolean hasComparatorInputOverride()
/* 215:    */   {
/* 216:274 */     return true;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public int getComparatorInputOverride(World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_)
/* 220:    */   {
/* 221:279 */     return Container.calcRedstoneFromInventory((IInventory)p_149736_1_.getTileEntity(p_149736_2_, p_149736_3_, p_149736_4_));
/* 222:    */   }
/* 223:    */   
/* 224:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 225:    */   {
/* 226:287 */     return Item.getItemFromBlock(Blocks.furnace);
/* 227:    */   }
/* 228:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockFurnace
 * JD-Core Version:    0.7.0.1
 */