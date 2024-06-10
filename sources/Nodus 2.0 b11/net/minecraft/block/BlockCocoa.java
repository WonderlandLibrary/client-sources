/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.entity.EntityLivingBase;
/*   7:    */ import net.minecraft.init.Blocks;
/*   8:    */ import net.minecraft.init.Items;
/*   9:    */ import net.minecraft.item.Item;
/*  10:    */ import net.minecraft.item.ItemStack;
/*  11:    */ import net.minecraft.util.AxisAlignedBB;
/*  12:    */ import net.minecraft.util.IIcon;
/*  13:    */ import net.minecraft.util.MathHelper;
/*  14:    */ import net.minecraft.world.IBlockAccess;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ 
/*  17:    */ public class BlockCocoa
/*  18:    */   extends BlockDirectional
/*  19:    */   implements IGrowable
/*  20:    */ {
/*  21:    */   private IIcon[] field_149989_a;
/*  22:    */   private static final String __OBFID = "CL_00000216";
/*  23:    */   
/*  24:    */   public BlockCocoa()
/*  25:    */   {
/*  26: 25 */     super(Material.plants);
/*  27: 26 */     setTickRandomly(true);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  31:    */   {
/*  32: 34 */     return this.field_149989_a[2];
/*  33:    */   }
/*  34:    */   
/*  35:    */   public IIcon func_149988_b(int p_149988_1_)
/*  36:    */   {
/*  37: 39 */     if ((p_149988_1_ < 0) || (p_149988_1_ >= this.field_149989_a.length)) {
/*  38: 41 */       p_149988_1_ = this.field_149989_a.length - 1;
/*  39:    */     }
/*  40: 44 */     return this.field_149989_a[p_149988_1_];
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
/*  44:    */   {
/*  45: 52 */     if (!canBlockStay(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_))
/*  46:    */     {
/*  47: 54 */       dropBlockAsItem(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_), 0);
/*  48: 55 */       p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, getBlockById(0), 0, 2);
/*  49:    */     }
/*  50: 57 */     else if (p_149674_1_.rand.nextInt(5) == 0)
/*  51:    */     {
/*  52: 59 */       int var6 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
/*  53: 60 */       int var7 = func_149987_c(var6);
/*  54: 62 */       if (var7 < 2)
/*  55:    */       {
/*  56: 64 */         var7++;
/*  57: 65 */         p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var7 << 2 | func_149895_l(var6), 2);
/*  58:    */       }
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_)
/*  63:    */   {
/*  64: 75 */     int var5 = func_149895_l(p_149718_1_.getBlockMetadata(p_149718_2_, p_149718_3_, p_149718_4_));
/*  65: 76 */     p_149718_2_ += net.minecraft.util.Direction.offsetX[var5];
/*  66: 77 */     p_149718_4_ += net.minecraft.util.Direction.offsetZ[var5];
/*  67: 78 */     Block var6 = p_149718_1_.getBlock(p_149718_2_, p_149718_3_, p_149718_4_);
/*  68: 79 */     return (var6 == Blocks.log) && (BlockLog.func_150165_c(p_149718_1_.getBlockMetadata(p_149718_2_, p_149718_3_, p_149718_4_)) == 3);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getRenderType()
/*  72:    */   {
/*  73: 87 */     return 28;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean renderAsNormalBlock()
/*  77:    */   {
/*  78: 92 */     return false;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean isOpaqueCube()
/*  82:    */   {
/*  83: 97 */     return false;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
/*  87:    */   {
/*  88:106 */     setBlockBoundsBasedOnState(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
/*  89:107 */     return super.getCollisionBoundingBoxFromPool(p_149668_1_, p_149668_2_, p_149668_3_, p_149668_4_);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
/*  93:    */   {
/*  94:115 */     setBlockBoundsBasedOnState(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
/*  95:116 */     return super.getSelectedBoundingBoxFromPool(p_149633_1_, p_149633_2_, p_149633_3_, p_149633_4_);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/*  99:    */   {
/* 100:121 */     int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
/* 101:122 */     int var6 = func_149895_l(var5);
/* 102:123 */     int var7 = func_149987_c(var5);
/* 103:124 */     int var8 = 4 + var7 * 2;
/* 104:125 */     int var9 = 5 + var7 * 2;
/* 105:126 */     float var10 = var8 / 2.0F;
/* 106:128 */     switch (var6)
/* 107:    */     {
/* 108:    */     case 0: 
/* 109:131 */       setBlockBounds((8.0F - var10) / 16.0F, (12.0F - var9) / 16.0F, (15.0F - var8) / 16.0F, (8.0F + var10) / 16.0F, 0.75F, 0.9375F);
/* 110:132 */       break;
/* 111:    */     case 1: 
/* 112:135 */       setBlockBounds(0.0625F, (12.0F - var9) / 16.0F, (8.0F - var10) / 16.0F, (1.0F + var8) / 16.0F, 0.75F, (8.0F + var10) / 16.0F);
/* 113:136 */       break;
/* 114:    */     case 2: 
/* 115:139 */       setBlockBounds((8.0F - var10) / 16.0F, (12.0F - var9) / 16.0F, 0.0625F, (8.0F + var10) / 16.0F, 0.75F, (1.0F + var8) / 16.0F);
/* 116:140 */       break;
/* 117:    */     case 3: 
/* 118:143 */       setBlockBounds((15.0F - var8) / 16.0F, (12.0F - var9) / 16.0F, (8.0F - var10) / 16.0F, 0.9375F, 0.75F, (8.0F + var10) / 16.0F);
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
/* 123:    */   {
/* 124:152 */     int var7 = ((MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3) + 0) % 4;
/* 125:153 */     p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 2);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public int onBlockPlaced(World p_149660_1_, int p_149660_2_, int p_149660_3_, int p_149660_4_, int p_149660_5_, float p_149660_6_, float p_149660_7_, float p_149660_8_, int p_149660_9_)
/* 129:    */   {
/* 130:158 */     if ((p_149660_5_ == 1) || (p_149660_5_ == 0)) {
/* 131:160 */       p_149660_5_ = 2;
/* 132:    */     }
/* 133:163 */     return net.minecraft.util.Direction.rotateOpposite[net.minecraft.util.Direction.facingToDirection[p_149660_5_]];
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
/* 137:    */   {
/* 138:168 */     if (!canBlockStay(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_))
/* 139:    */     {
/* 140:170 */       dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_), 0);
/* 141:171 */       p_149695_1_.setBlock(p_149695_2_, p_149695_3_, p_149695_4_, getBlockById(0), 0, 2);
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public static int func_149987_c(int p_149987_0_)
/* 146:    */   {
/* 147:177 */     return (p_149987_0_ & 0xC) >> 2;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
/* 151:    */   {
/* 152:185 */     int var8 = func_149987_c(p_149690_5_);
/* 153:186 */     byte var9 = 1;
/* 154:188 */     if (var8 >= 2) {
/* 155:190 */       var9 = 3;
/* 156:    */     }
/* 157:193 */     for (int var10 = 0; var10 < var9; var10++) {
/* 158:195 */       dropBlockAsItem_do(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(Items.dye, 1, 3));
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 163:    */   {
/* 164:204 */     return Items.dye;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_)
/* 168:    */   {
/* 169:212 */     return 3;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 173:    */   {
/* 174:217 */     this.field_149989_a = new IIcon[3];
/* 175:219 */     for (int var2 = 0; var2 < this.field_149989_a.length; var2++) {
/* 176:221 */       this.field_149989_a[var2] = p_149651_1_.registerIcon(getTextureName() + "_stage_" + var2);
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_)
/* 181:    */   {
/* 182:227 */     int var6 = p_149851_1_.getBlockMetadata(p_149851_2_, p_149851_3_, p_149851_4_);
/* 183:228 */     int var7 = func_149987_c(var6);
/* 184:229 */     return var7 < 2;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_)
/* 188:    */   {
/* 189:234 */     return true;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_)
/* 193:    */   {
/* 194:239 */     int var6 = p_149853_1_.getBlockMetadata(p_149853_3_, p_149853_4_, p_149853_5_);
/* 195:240 */     int var7 = BlockDirectional.func_149895_l(var6);
/* 196:241 */     int var8 = func_149987_c(var6);
/* 197:242 */     var8++;
/* 198:243 */     p_149853_1_.setBlockMetadataWithNotify(p_149853_3_, p_149853_4_, p_149853_5_, var8 << 2 | var7, 2);
/* 199:    */   }
/* 200:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockCocoa
 * JD-Core Version:    0.7.0.1
 */