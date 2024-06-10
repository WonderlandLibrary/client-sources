/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*   8:    */ import net.minecraft.dispenser.IBehaviorDispenseItem;
/*   9:    */ import net.minecraft.dispenser.IBlockSource;
/*  10:    */ import net.minecraft.dispenser.IPosition;
/*  11:    */ import net.minecraft.dispenser.PositionImpl;
/*  12:    */ import net.minecraft.entity.EntityLivingBase;
/*  13:    */ import net.minecraft.entity.item.EntityItem;
/*  14:    */ import net.minecraft.entity.player.EntityPlayer;
/*  15:    */ import net.minecraft.inventory.Container;
/*  16:    */ import net.minecraft.inventory.IInventory;
/*  17:    */ import net.minecraft.item.ItemStack;
/*  18:    */ import net.minecraft.nbt.NBTTagCompound;
/*  19:    */ import net.minecraft.tileentity.TileEntity;
/*  20:    */ import net.minecraft.tileentity.TileEntityDispenser;
/*  21:    */ import net.minecraft.util.EnumFacing;
/*  22:    */ import net.minecraft.util.IIcon;
/*  23:    */ import net.minecraft.util.IRegistry;
/*  24:    */ import net.minecraft.util.RegistryDefaulted;
/*  25:    */ import net.minecraft.world.World;
/*  26:    */ 
/*  27:    */ public class BlockDispenser
/*  28:    */   extends BlockContainer
/*  29:    */ {
/*  30: 29 */   public static final IRegistry dispenseBehaviorRegistry = new RegistryDefaulted(new BehaviorDefaultDispenseItem());
/*  31: 30 */   protected Random field_149942_b = new Random();
/*  32:    */   protected IIcon field_149944_M;
/*  33:    */   protected IIcon field_149945_N;
/*  34:    */   protected IIcon field_149946_O;
/*  35:    */   private static final String __OBFID = "CL_00000229";
/*  36:    */   
/*  37:    */   protected BlockDispenser()
/*  38:    */   {
/*  39: 38 */     super(Material.rock);
/*  40: 39 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public int func_149738_a(World p_149738_1_)
/*  44:    */   {
/*  45: 44 */     return 4;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/*  49:    */   {
/*  50: 49 */     super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/*  51: 50 */     func_149938_m(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void func_149938_m(World p_149938_1_, int p_149938_2_, int p_149938_3_, int p_149938_4_)
/*  55:    */   {
/*  56: 55 */     if (!p_149938_1_.isClient)
/*  57:    */     {
/*  58: 57 */       Block var5 = p_149938_1_.getBlock(p_149938_2_, p_149938_3_, p_149938_4_ - 1);
/*  59: 58 */       Block var6 = p_149938_1_.getBlock(p_149938_2_, p_149938_3_, p_149938_4_ + 1);
/*  60: 59 */       Block var7 = p_149938_1_.getBlock(p_149938_2_ - 1, p_149938_3_, p_149938_4_);
/*  61: 60 */       Block var8 = p_149938_1_.getBlock(p_149938_2_ + 1, p_149938_3_, p_149938_4_);
/*  62: 61 */       byte var9 = 3;
/*  63: 63 */       if ((var5.func_149730_j()) && (!var6.func_149730_j())) {
/*  64: 65 */         var9 = 3;
/*  65:    */       }
/*  66: 68 */       if ((var6.func_149730_j()) && (!var5.func_149730_j())) {
/*  67: 70 */         var9 = 2;
/*  68:    */       }
/*  69: 73 */       if ((var7.func_149730_j()) && (!var8.func_149730_j())) {
/*  70: 75 */         var9 = 5;
/*  71:    */       }
/*  72: 78 */       if ((var8.func_149730_j()) && (!var7.func_149730_j())) {
/*  73: 80 */         var9 = 4;
/*  74:    */       }
/*  75: 83 */       p_149938_1_.setBlockMetadataWithNotify(p_149938_2_, p_149938_3_, p_149938_4_, var9, 2);
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  80:    */   {
/*  81: 92 */     int var3 = p_149691_2_ & 0x7;
/*  82: 93 */     return (var3 != 1) && (var3 != 0) ? this.field_149944_M : (p_149691_1_ != 1) && (p_149691_1_ != 0) ? this.blockIcon : p_149691_1_ == var3 ? this.field_149946_O : (var3 != 1) && (var3 != 0) ? this.field_149945_N : this.field_149944_M;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/*  86:    */   {
/*  87: 98 */     this.blockIcon = p_149651_1_.registerIcon("furnace_side");
/*  88: 99 */     this.field_149944_M = p_149651_1_.registerIcon("furnace_top");
/*  89:100 */     this.field_149945_N = p_149651_1_.registerIcon(getTextureName() + "_front_horizontal");
/*  90:101 */     this.field_149946_O = p_149651_1_.registerIcon(getTextureName() + "_front_vertical");
/*  91:    */   }
/*  92:    */   
/*  93:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  94:    */   {
/*  95:109 */     if (p_149727_1_.isClient) {
/*  96:111 */       return true;
/*  97:    */     }
/*  98:115 */     TileEntityDispenser var10 = (TileEntityDispenser)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
/*  99:117 */     if (var10 != null) {
/* 100:119 */       p_149727_5_.func_146102_a(var10);
/* 101:    */     }
/* 102:122 */     return true;
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected void func_149941_e(World p_149941_1_, int p_149941_2_, int p_149941_3_, int p_149941_4_)
/* 106:    */   {
/* 107:128 */     BlockSourceImpl var5 = new BlockSourceImpl(p_149941_1_, p_149941_2_, p_149941_3_, p_149941_4_);
/* 108:129 */     TileEntityDispenser var6 = (TileEntityDispenser)var5.getBlockTileEntity();
/* 109:131 */     if (var6 != null)
/* 110:    */     {
/* 111:133 */       int var7 = var6.func_146017_i();
/* 112:135 */       if (var7 < 0)
/* 113:    */       {
/* 114:137 */         p_149941_1_.playAuxSFX(1001, p_149941_2_, p_149941_3_, p_149941_4_, 0);
/* 115:    */       }
/* 116:    */       else
/* 117:    */       {
/* 118:141 */         ItemStack var8 = var6.getStackInSlot(var7);
/* 119:142 */         IBehaviorDispenseItem var9 = func_149940_a(var8);
/* 120:144 */         if (var9 != IBehaviorDispenseItem.itemDispenseBehaviorProvider)
/* 121:    */         {
/* 122:146 */           ItemStack var10 = var9.dispense(var5, var8);
/* 123:147 */           var6.setInventorySlotContents(var7, var10.stackSize == 0 ? null : var10);
/* 124:    */         }
/* 125:    */       }
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   protected IBehaviorDispenseItem func_149940_a(ItemStack p_149940_1_)
/* 130:    */   {
/* 131:155 */     return (IBehaviorDispenseItem)dispenseBehaviorRegistry.getObject(p_149940_1_.getItem());
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 135:    */   {
/* 136:160 */     boolean var6 = (p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_)) || (p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_ + 1, p_149695_4_));
/* 137:161 */     int var7 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
/* 138:162 */     boolean var8 = (var7 & 0x8) != 0;
/* 139:164 */     if ((var6) && (!var8))
/* 140:    */     {
/* 141:166 */       p_149695_1_.scheduleBlockUpdate(p_149695_2_, p_149695_3_, p_149695_4_, this, func_149738_a(p_149695_1_));
/* 142:167 */       p_149695_1_.setBlockMetadataWithNotify(p_149695_2_, p_149695_3_, p_149695_4_, var7 | 0x8, 4);
/* 143:    */     }
/* 144:169 */     else if ((!var6) && (var8))
/* 145:    */     {
/* 146:171 */       p_149695_1_.setBlockMetadataWithNotify(p_149695_2_, p_149695_3_, p_149695_4_, var7 & 0xFFFFFFF7, 4);
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/* 151:    */   {
/* 152:180 */     if (!p_149674_1_.isClient) {
/* 153:182 */       func_149941_e(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:    */   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
/* 158:    */   {
/* 159:191 */     return new TileEntityDispenser();
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
/* 163:    */   {
/* 164:199 */     int var7 = BlockPistonBase.func_150071_a(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_);
/* 165:200 */     p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 2);
/* 166:202 */     if (p_149689_6_.hasDisplayName()) {
/* 167:204 */       ((TileEntityDispenser)p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_)).func_146018_a(p_149689_6_.getDisplayName());
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/* 172:    */   {
/* 173:210 */     TileEntityDispenser var7 = (TileEntityDispenser)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
/* 174:212 */     if (var7 != null)
/* 175:    */     {
/* 176:214 */       for (int var8 = 0; var8 < var7.getSizeInventory(); var8++)
/* 177:    */       {
/* 178:216 */         ItemStack var9 = var7.getStackInSlot(var8);
/* 179:218 */         if (var9 != null)
/* 180:    */         {
/* 181:220 */           float var10 = this.field_149942_b.nextFloat() * 0.8F + 0.1F;
/* 182:221 */           float var11 = this.field_149942_b.nextFloat() * 0.8F + 0.1F;
/* 183:222 */           float var12 = this.field_149942_b.nextFloat() * 0.8F + 0.1F;
/* 184:224 */           while (var9.stackSize > 0)
/* 185:    */           {
/* 186:226 */             int var13 = this.field_149942_b.nextInt(21) + 10;
/* 187:228 */             if (var13 > var9.stackSize) {
/* 188:230 */               var13 = var9.stackSize;
/* 189:    */             }
/* 190:233 */             var9.stackSize -= var13;
/* 191:234 */             EntityItem var14 = new EntityItem(p_149749_1_, p_149749_2_ + var10, p_149749_3_ + var11, p_149749_4_ + var12, new ItemStack(var9.getItem(), var13, var9.getItemDamage()));
/* 192:236 */             if (var9.hasTagCompound()) {
/* 193:238 */               var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
/* 194:    */             }
/* 195:241 */             float var15 = 0.05F;
/* 196:242 */             var14.motionX = ((float)this.field_149942_b.nextGaussian() * var15);
/* 197:243 */             var14.motionY = ((float)this.field_149942_b.nextGaussian() * var15 + 0.2F);
/* 198:244 */             var14.motionZ = ((float)this.field_149942_b.nextGaussian() * var15);
/* 199:245 */             p_149749_1_.spawnEntityInWorld(var14);
/* 200:    */           }
/* 201:    */         }
/* 202:    */       }
/* 203:250 */       p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
/* 204:    */     }
/* 205:253 */     super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/* 206:    */   }
/* 207:    */   
/* 208:    */   public static IPosition func_149939_a(IBlockSource p_149939_0_)
/* 209:    */   {
/* 210:258 */     EnumFacing var1 = func_149937_b(p_149939_0_.getBlockMetadata());
/* 211:259 */     double var2 = p_149939_0_.getX() + 0.7D * var1.getFrontOffsetX();
/* 212:260 */     double var4 = p_149939_0_.getY() + 0.7D * var1.getFrontOffsetY();
/* 213:261 */     double var6 = p_149939_0_.getZ() + 0.7D * var1.getFrontOffsetZ();
/* 214:262 */     return new PositionImpl(var2, var4, var6);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public static EnumFacing func_149937_b(int p_149937_0_)
/* 218:    */   {
/* 219:267 */     return EnumFacing.getFront(p_149937_0_ & 0x7);
/* 220:    */   }
/* 221:    */   
/* 222:    */   public boolean hasComparatorInputOverride()
/* 223:    */   {
/* 224:272 */     return true;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public int getComparatorInputOverride(World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_)
/* 228:    */   {
/* 229:277 */     return Container.calcRedstoneFromInventory((IInventory)p_149736_1_.getTileEntity(p_149736_2_, p_149736_3_, p_149736_4_));
/* 230:    */   }
/* 231:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockDispenser
 * JD-Core Version:    0.7.0.1
 */