/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.entity.EntityLivingBase;
/*   8:    */ import net.minecraft.entity.item.EntityFallingBlock;
/*   9:    */ import net.minecraft.entity.player.EntityPlayer;
/*  10:    */ import net.minecraft.item.Item;
/*  11:    */ import net.minecraft.item.ItemStack;
/*  12:    */ import net.minecraft.util.IIcon;
/*  13:    */ import net.minecraft.util.MathHelper;
/*  14:    */ import net.minecraft.world.IBlockAccess;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ 
/*  17:    */ public class BlockAnvil
/*  18:    */   extends BlockFalling
/*  19:    */ {
/*  20: 19 */   public static final String[] field_149834_a = { "intact", "slightlyDamaged", "veryDamaged" };
/*  21: 20 */   private static final String[] field_149835_N = { "anvil_top_damaged_0", "anvil_top_damaged_1", "anvil_top_damaged_2" };
/*  22:    */   public int field_149833_b;
/*  23:    */   private IIcon[] field_149836_O;
/*  24:    */   private static final String __OBFID = "CL_00000192";
/*  25:    */   
/*  26:    */   protected BlockAnvil()
/*  27:    */   {
/*  28: 27 */     super(Material.anvil);
/*  29: 28 */     setLightOpacity(0);
/*  30: 29 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean renderAsNormalBlock()
/*  34:    */   {
/*  35: 34 */     return false;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean isOpaqueCube()
/*  39:    */   {
/*  40: 39 */     return false;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  44:    */   {
/*  45: 47 */     if ((this.field_149833_b == 3) && (p_149691_1_ == 1))
/*  46:    */     {
/*  47: 49 */       int var3 = (p_149691_2_ >> 2) % this.field_149836_O.length;
/*  48: 50 */       return this.field_149836_O[var3];
/*  49:    */     }
/*  50: 54 */     return this.blockIcon;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/*  54:    */   {
/*  55: 60 */     this.blockIcon = p_149651_1_.registerIcon("anvil_base");
/*  56: 61 */     this.field_149836_O = new IIcon[field_149835_N.length];
/*  57: 63 */     for (int var2 = 0; var2 < this.field_149836_O.length; var2++) {
/*  58: 65 */       this.field_149836_O[var2] = p_149651_1_.registerIcon(field_149835_N[var2]);
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
/*  63:    */   {
/*  64: 74 */     int var7 = MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
/*  65: 75 */     int var8 = p_149689_1_.getBlockMetadata(p_149689_2_, p_149689_3_, p_149689_4_) >> 2;
/*  66: 76 */     var7++;
/*  67: 77 */     var7 %= 4;
/*  68: 79 */     if (var7 == 0) {
/*  69: 81 */       p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x2 | var8 << 2, 2);
/*  70:    */     }
/*  71: 84 */     if (var7 == 1) {
/*  72: 86 */       p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x3 | var8 << 2, 2);
/*  73:    */     }
/*  74: 89 */     if (var7 == 2) {
/*  75: 91 */       p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var8 << 2, 2);
/*  76:    */     }
/*  77: 94 */     if (var7 == 3) {
/*  78: 96 */       p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0x1 | var8 << 2, 2);
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  83:    */   {
/*  84:105 */     if (p_149727_1_.isClient) {
/*  85:107 */       return true;
/*  86:    */     }
/*  87:111 */     p_149727_5_.displayGUIAnvil(p_149727_2_, p_149727_3_, p_149727_4_);
/*  88:112 */     return true;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public int getRenderType()
/*  92:    */   {
/*  93:121 */     return 35;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int damageDropped(int p_149692_1_)
/*  97:    */   {
/*  98:129 */     return p_149692_1_ >> 2;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
/* 102:    */   {
/* 103:134 */     int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_) & 0x3;
/* 104:136 */     if ((var5 != 3) && (var5 != 1)) {
/* 105:138 */       setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
/* 106:    */     } else {
/* 107:142 */       setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
/* 112:    */   {
/* 113:148 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
/* 114:149 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
/* 115:150 */     p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected void func_149829_a(EntityFallingBlock p_149829_1_)
/* 119:    */   {
/* 120:155 */     p_149829_1_.func_145806_a(true);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void func_149828_a(World p_149828_1_, int p_149828_2_, int p_149828_3_, int p_149828_4_, int p_149828_5_)
/* 124:    */   {
/* 125:160 */     p_149828_1_.playAuxSFX(1022, p_149828_2_, p_149828_3_, p_149828_4_, 0);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
/* 129:    */   {
/* 130:165 */     return true;
/* 131:    */   }
/* 132:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockAnvil
 * JD-Core Version:    0.7.0.1
 */