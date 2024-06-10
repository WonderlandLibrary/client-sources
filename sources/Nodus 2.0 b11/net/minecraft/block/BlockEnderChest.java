/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.entity.EntityLivingBase;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
/*   9:    */ import net.minecraft.init.Blocks;
/*  10:    */ import net.minecraft.inventory.InventoryEnderChest;
/*  11:    */ import net.minecraft.item.Item;
/*  12:    */ import net.minecraft.item.ItemStack;
/*  13:    */ import net.minecraft.tileentity.TileEntity;
/*  14:    */ import net.minecraft.tileentity.TileEntityEnderChest;
/*  15:    */ import net.minecraft.util.MathHelper;
/*  16:    */ import net.minecraft.world.World;
/*  17:    */ 
/*  18:    */ public class BlockEnderChest
/*  19:    */   extends BlockContainer
/*  20:    */ {
/*  21:    */   private static final String __OBFID = "CL_00000238";
/*  22:    */   
/*  23:    */   protected BlockEnderChest()
/*  24:    */   {
/*  25: 24 */     super(Material.rock);
/*  26: 25 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  27: 26 */     setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public boolean isOpaqueCube()
/*  31:    */   {
/*  32: 31 */     return false;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean renderAsNormalBlock()
/*  36:    */   {
/*  37: 36 */     return false;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int getRenderType()
/*  41:    */   {
/*  42: 44 */     return 22;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/*  46:    */   {
/*  47: 49 */     return Item.getItemFromBlock(Blocks.obsidian);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public int quantityDropped(Random p_149745_1_)
/*  51:    */   {
/*  52: 57 */     return 8;
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected boolean canSilkHarvest()
/*  56:    */   {
/*  57: 62 */     return true;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
/*  61:    */   {
/*  62: 70 */     byte var7 = 0;
/*  63: 71 */     int var8 = MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
/*  64: 73 */     if (var8 == 0) {
/*  65: 75 */       var7 = 2;
/*  66:    */     }
/*  67: 78 */     if (var8 == 1) {
/*  68: 80 */       var7 = 5;
/*  69:    */     }
/*  70: 83 */     if (var8 == 2) {
/*  71: 85 */       var7 = 3;
/*  72:    */     }
/*  73: 88 */     if (var8 == 3) {
/*  74: 90 */       var7 = 4;
/*  75:    */     }
/*  76: 93 */     p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, var7, 2);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  80:    */   {
/*  81:101 */     InventoryEnderChest var10 = p_149727_5_.getInventoryEnderChest();
/*  82:102 */     TileEntityEnderChest var11 = (TileEntityEnderChest)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
/*  83:104 */     if ((var10 != null) && (var11 != null))
/*  84:    */     {
/*  85:106 */       if (p_149727_1_.getBlock(p_149727_2_, p_149727_3_ + 1, p_149727_4_).isNormalCube()) {
/*  86:108 */         return true;
/*  87:    */       }
/*  88:110 */       if (p_149727_1_.isClient) {
/*  89:112 */         return true;
/*  90:    */       }
/*  91:116 */       var10.func_146031_a(var11);
/*  92:117 */       p_149727_5_.displayGUIChest(var10);
/*  93:118 */       return true;
/*  94:    */     }
/*  95:123 */     return true;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
/*  99:    */   {
/* 100:132 */     return new TileEntityEnderChest();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
/* 104:    */   {
/* 105:140 */     for (int var6 = 0; var6 < 3; var6++)
/* 106:    */     {
/* 107:142 */       double var10000 = p_149734_2_ + p_149734_5_.nextFloat();
/* 108:143 */       double var9 = p_149734_3_ + p_149734_5_.nextFloat();
/* 109:144 */       var10000 = p_149734_4_ + p_149734_5_.nextFloat();
/* 110:145 */       double var13 = 0.0D;
/* 111:146 */       double var15 = 0.0D;
/* 112:147 */       double var17 = 0.0D;
/* 113:148 */       int var19 = p_149734_5_.nextInt(2) * 2 - 1;
/* 114:149 */       int var20 = p_149734_5_.nextInt(2) * 2 - 1;
/* 115:150 */       var13 = (p_149734_5_.nextFloat() - 0.5D) * 0.125D;
/* 116:151 */       var15 = (p_149734_5_.nextFloat() - 0.5D) * 0.125D;
/* 117:152 */       var17 = (p_149734_5_.nextFloat() - 0.5D) * 0.125D;
/* 118:153 */       double var11 = p_149734_4_ + 0.5D + 0.25D * var20;
/* 119:154 */       var17 = p_149734_5_.nextFloat() * 1.0F * var20;
/* 120:155 */       double var7 = p_149734_2_ + 0.5D + 0.25D * var19;
/* 121:156 */       var13 = p_149734_5_.nextFloat() * 1.0F * var19;
/* 122:157 */       p_149734_1_.spawnParticle("portal", var7, var9, var11, var13, var15, var17);
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 127:    */   {
/* 128:163 */     this.blockIcon = p_149651_1_.registerIcon("obsidian");
/* 129:    */   }
/* 130:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockEnderChest
 * JD-Core Version:    0.7.0.1
 */