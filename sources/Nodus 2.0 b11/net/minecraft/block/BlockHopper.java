/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   7:    */ import net.minecraft.creativetab.CreativeTabs;
/*   8:    */ import net.minecraft.entity.Entity;
/*   9:    */ import net.minecraft.entity.EntityLivingBase;
/*  10:    */ import net.minecraft.entity.item.EntityItem;
/*  11:    */ import net.minecraft.entity.player.EntityPlayer;
/*  12:    */ import net.minecraft.init.Blocks;
/*  13:    */ import net.minecraft.inventory.Container;
/*  14:    */ import net.minecraft.item.ItemStack;
/*  15:    */ import net.minecraft.nbt.NBTTagCompound;
/*  16:    */ import net.minecraft.tileentity.TileEntity;
/*  17:    */ import net.minecraft.tileentity.TileEntityHopper;
/*  18:    */ import net.minecraft.util.AxisAlignedBB;
/*  19:    */ import net.minecraft.util.IIcon;
/*  20:    */ import net.minecraft.world.IBlockAccess;
/*  21:    */ import net.minecraft.world.World;
/*  22:    */ 
/*  23:    */ public class BlockHopper
/*  24:    */   extends BlockContainer
/*  25:    */ {
/*  26: 26 */   private final Random field_149922_a = new Random();
/*  27:    */   private IIcon field_149921_b;
/*  28:    */   private IIcon field_149923_M;
/*  29:    */   private IIcon field_149924_N;
/*  30:    */   private static final String __OBFID = "CL_00000257";
/*  31:    */   
/*  32:    */   public BlockHopper()
/*  33:    */   {
/*  34: 34 */     super(Material.iron);
/*  35: 35 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  36: 36 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  40:    */   {
/*  41: 41 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_)
/*  45:    */   {
/*  46: 46 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
/*  47: 47 */     super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  48: 48 */     float var8 = 0.125F;
/*  49: 49 */     setBlockBounds(0.0F, 0.0F, 0.0F, var8, 1.0F, 1.0F);
/*  50: 50 */     super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  51: 51 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var8);
/*  52: 52 */     super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  53: 53 */     setBlockBounds(1.0F - var8, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  54: 54 */     super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  55: 55 */     setBlockBounds(0.0F, 0.0F, 1.0F - var8, 1.0F, 1.0F, 1.0F);
/*  56: 56 */     super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  57: 57 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
/*  61:    */   {
/*  62: 62 */     int var10 = net.minecraft.util.Facing.oppositeSide[p_149660_5_];
/*  63: 64 */     if (var10 == 1) {
/*  64: 66 */       var10 = 0;
/*  65:    */     }
/*  66: 69 */     return var10;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
/*  70:    */   {
/*  71: 77 */     return new TileEntityHopper();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
/*  75:    */   {
/*  76: 85 */     super.onBlockPlacedBy(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_, p_149689_6_);
/*  77: 87 */     if (p_149689_6_.hasDisplayName())
/*  78:    */     {
/*  79: 89 */       TileEntityHopper var7 = func_149920_e(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_);
/*  80: 90 */       var7.func_145886_a(p_149689_6_.getDisplayName());
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
/*  85:    */   {
/*  86: 96 */     super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/*  87: 97 */     func_149919_e(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  91:    */   {
/*  92:105 */     if (p_149727_1_.isClient) {
/*  93:107 */       return true;
/*  94:    */     }
/*  95:111 */     TileEntityHopper var10 = func_149920_e(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
/*  96:113 */     if (var10 != null) {
/*  97:115 */       p_149727_5_.func_146093_a(var10);
/*  98:    */     }
/*  99:118 */     return true;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 103:    */   {
/* 104:124 */     func_149919_e(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
/* 105:    */   }
/* 106:    */   
/* 107:    */   private void func_149919_e(World p_149919_1_, int p_149919_2_, int p_149919_3_, int p_149919_4_)
/* 108:    */   {
/* 109:129 */     int var5 = p_149919_1_.getBlockMetadata(p_149919_2_, p_149919_3_, p_149919_4_);
/* 110:130 */     int var6 = func_149918_b(var5);
/* 111:131 */     boolean var7 = !p_149919_1_.isBlockIndirectlyGettingPowered(p_149919_2_, p_149919_3_, p_149919_4_);
/* 112:132 */     boolean var8 = func_149917_c(var5);
/* 113:134 */     if (var7 != var8) {
/* 114:136 */       p_149919_1_.setBlockMetadataWithNotify(p_149919_2_, p_149919_3_, p_149919_4_, var6 | (var7 ? 0 : 8), 4);
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/* 119:    */   {
/* 120:142 */     TileEntityHopper var7 = (TileEntityHopper)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
/* 121:144 */     if (var7 != null)
/* 122:    */     {
/* 123:146 */       for (int var8 = 0; var8 < var7.getSizeInventory(); var8++)
/* 124:    */       {
/* 125:148 */         ItemStack var9 = var7.getStackInSlot(var8);
/* 126:150 */         if (var9 != null)
/* 127:    */         {
/* 128:152 */           float var10 = this.field_149922_a.nextFloat() * 0.8F + 0.1F;
/* 129:153 */           float var11 = this.field_149922_a.nextFloat() * 0.8F + 0.1F;
/* 130:154 */           float var12 = this.field_149922_a.nextFloat() * 0.8F + 0.1F;
/* 131:156 */           while (var9.stackSize > 0)
/* 132:    */           {
/* 133:158 */             int var13 = this.field_149922_a.nextInt(21) + 10;
/* 134:160 */             if (var13 > var9.stackSize) {
/* 135:162 */               var13 = var9.stackSize;
/* 136:    */             }
/* 137:165 */             var9.stackSize -= var13;
/* 138:166 */             EntityItem var14 = new EntityItem(p_149749_1_, p_149749_2_ + var10, p_149749_3_ + var11, p_149749_4_ + var12, new ItemStack(var9.getItem(), var13, var9.getItemDamage()));
/* 139:168 */             if (var9.hasTagCompound()) {
/* 140:170 */               var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
/* 141:    */             }
/* 142:173 */             float var15 = 0.05F;
/* 143:174 */             var14.motionX = ((float)this.field_149922_a.nextGaussian() * var15);
/* 144:175 */             var14.motionY = ((float)this.field_149922_a.nextGaussian() * var15 + 0.2F);
/* 145:176 */             var14.motionZ = ((float)this.field_149922_a.nextGaussian() * var15);
/* 146:177 */             p_149749_1_.spawnEntityInWorld(var14);
/* 147:    */           }
/* 148:    */         }
/* 149:    */       }
/* 150:182 */       p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
/* 151:    */     }
/* 152:185 */     super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public int getRenderType()
/* 156:    */   {
/* 157:193 */     return 38;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean renderAsNormalBlock()
/* 161:    */   {
/* 162:198 */     return false;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public boolean isOpaqueCube()
/* 166:    */   {
/* 167:203 */     return false;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
/* 171:    */   {
/* 172:208 */     return true;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/* 176:    */   {
/* 177:216 */     return p_149691_1_ == 1 ? this.field_149923_M : this.field_149921_b;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public static int func_149918_b(int p_149918_0_)
/* 181:    */   {
/* 182:221 */     return p_149918_0_ & 0x7;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public static boolean func_149917_c(int p_149917_0_)
/* 186:    */   {
/* 187:226 */     return (p_149917_0_ & 0x8) != 8;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public boolean hasComparatorInputOverride()
/* 191:    */   {
/* 192:231 */     return true;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public int getComparatorInputOverride(World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_)
/* 196:    */   {
/* 197:236 */     return Container.calcRedstoneFromInventory(func_149920_e(p_149736_1_, p_149736_2_, p_149736_3_, p_149736_4_));
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 201:    */   {
/* 202:241 */     this.field_149921_b = p_149651_1_.registerIcon("hopper_outside");
/* 203:242 */     this.field_149923_M = p_149651_1_.registerIcon("hopper_top");
/* 204:243 */     this.field_149924_N = p_149651_1_.registerIcon("hopper_inside");
/* 205:    */   }
/* 206:    */   
/* 207:    */   public static IIcon func_149916_e(String p_149916_0_)
/* 208:    */   {
/* 209:248 */     return p_149916_0_.equals("hopper_inside") ? Blocks.hopper.field_149924_N : p_149916_0_.equals("hopper_outside") ? Blocks.hopper.field_149921_b : null;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public String getItemIconName()
/* 213:    */   {
/* 214:256 */     return "hopper";
/* 215:    */   }
/* 216:    */   
/* 217:    */   public static TileEntityHopper func_149920_e(IBlockAccess p_149920_0_, int p_149920_1_, int p_149920_2_, int p_149920_3_)
/* 218:    */   {
/* 219:261 */     return (TileEntityHopper)p_149920_0_.getTileEntity(p_149920_1_, p_149920_2_, p_149920_3_);
/* 220:    */   }
/* 221:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockHopper
 * JD-Core Version:    0.7.0.1
 */