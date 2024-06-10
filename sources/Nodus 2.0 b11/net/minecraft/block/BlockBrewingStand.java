/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   7:    */ import net.minecraft.entity.Entity;
/*   8:    */ import net.minecraft.entity.EntityLivingBase;
/*   9:    */ import net.minecraft.entity.item.EntityItem;
/*  10:    */ import net.minecraft.entity.player.EntityPlayer;
/*  11:    */ import net.minecraft.init.Items;
/*  12:    */ import net.minecraft.inventory.Container;
/*  13:    */ import net.minecraft.inventory.IInventory;
/*  14:    */ import net.minecraft.item.Item;
/*  15:    */ import net.minecraft.item.ItemStack;
/*  16:    */ import net.minecraft.tileentity.TileEntity;
/*  17:    */ import net.minecraft.tileentity.TileEntityBrewingStand;
/*  18:    */ import net.minecraft.util.AxisAlignedBB;
/*  19:    */ import net.minecraft.util.IIcon;
/*  20:    */ import net.minecraft.world.World;
/*  21:    */ 
/*  22:    */ public class BlockBrewingStand
/*  23:    */   extends BlockContainer
/*  24:    */ {
/*  25: 24 */   private Random field_149961_a = new Random();
/*  26:    */   private IIcon field_149960_b;
/*  27:    */   private static final String __OBFID = "CL_00000207";
/*  28:    */   
/*  29:    */   public BlockBrewingStand()
/*  30:    */   {
/*  31: 30 */     super(Material.iron);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean isOpaqueCube()
/*  35:    */   {
/*  36: 35 */     return false;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getRenderType()
/*  40:    */   {
/*  41: 43 */     return 25;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
/*  45:    */   {
/*  46: 51 */     return new TileEntityBrewingStand();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean renderAsNormalBlock()
/*  50:    */   {
/*  51: 56 */     return false;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_)
/*  55:    */   {
/*  56: 61 */     setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.875F, 0.5625F);
/*  57: 62 */     super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  58: 63 */     setBlockBoundsForItemRender();
/*  59: 64 */     super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setBlockBoundsForItemRender()
/*  63:    */   {
/*  64: 72 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  68:    */   {
/*  69: 80 */     if (p_149727_1_.isClient) {
/*  70: 82 */       return true;
/*  71:    */     }
/*  72: 86 */     TileEntityBrewingStand var10 = (TileEntityBrewingStand)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
/*  73: 88 */     if (var10 != null) {
/*  74: 90 */       p_149727_5_.func_146098_a(var10);
/*  75:    */     }
/*  76: 93 */     return true;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
/*  80:    */   {
/*  81:102 */     if (p_149689_6_.hasDisplayName()) {
/*  82:104 */       ((TileEntityBrewingStand)p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_)).func_145937_a(p_149689_6_.getDisplayName());
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
/*  87:    */   {
/*  88:113 */     double var6 = p_149734_2_ + 0.4F + p_149734_5_.nextFloat() * 0.2F;
/*  89:114 */     double var8 = p_149734_3_ + 0.7F + p_149734_5_.nextFloat() * 0.3F;
/*  90:115 */     double var10 = p_149734_4_ + 0.4F + p_149734_5_.nextFloat() * 0.2F;
/*  91:116 */     p_149734_1_.spawnParticle("smoke", var6, var8, var10, 0.0D, 0.0D, 0.0D);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/*  95:    */   {
/*  96:121 */     TileEntity var7 = p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
/*  97:123 */     if ((var7 instanceof TileEntityBrewingStand))
/*  98:    */     {
/*  99:125 */       TileEntityBrewingStand var8 = (TileEntityBrewingStand)var7;
/* 100:127 */       for (int var9 = 0; var9 < var8.getSizeInventory(); var9++)
/* 101:    */       {
/* 102:129 */         ItemStack var10 = var8.getStackInSlot(var9);
/* 103:131 */         if (var10 != null)
/* 104:    */         {
/* 105:133 */           float var11 = this.field_149961_a.nextFloat() * 0.8F + 0.1F;
/* 106:134 */           float var12 = this.field_149961_a.nextFloat() * 0.8F + 0.1F;
/* 107:135 */           float var13 = this.field_149961_a.nextFloat() * 0.8F + 0.1F;
/* 108:137 */           while (var10.stackSize > 0)
/* 109:    */           {
/* 110:139 */             int var14 = this.field_149961_a.nextInt(21) + 10;
/* 111:141 */             if (var14 > var10.stackSize) {
/* 112:143 */               var14 = var10.stackSize;
/* 113:    */             }
/* 114:146 */             var10.stackSize -= var14;
/* 115:147 */             EntityItem var15 = new EntityItem(p_149749_1_, p_149749_2_ + var11, p_149749_3_ + var12, p_149749_4_ + var13, new ItemStack(var10.getItem(), var14, var10.getItemDamage()));
/* 116:148 */             float var16 = 0.05F;
/* 117:149 */             var15.motionX = ((float)this.field_149961_a.nextGaussian() * var16);
/* 118:150 */             var15.motionY = ((float)this.field_149961_a.nextGaussian() * var16 + 0.2F);
/* 119:151 */             var15.motionZ = ((float)this.field_149961_a.nextGaussian() * var16);
/* 120:152 */             p_149749_1_.spawnEntityInWorld(var15);
/* 121:    */           }
/* 122:    */         }
/* 123:    */       }
/* 124:    */     }
/* 125:158 */     super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
/* 129:    */   {
/* 130:163 */     return Items.brewing_stand;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
/* 134:    */   {
/* 135:171 */     return Items.brewing_stand;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean hasComparatorInputOverride()
/* 139:    */   {
/* 140:176 */     return true;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public int getComparatorInputOverride(World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_)
/* 144:    */   {
/* 145:181 */     return Container.calcRedstoneFromInventory((IInventory)p_149736_1_.getTileEntity(p_149736_2_, p_149736_3_, p_149736_4_));
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 149:    */   {
/* 150:186 */     super.registerBlockIcons(p_149651_1_);
/* 151:187 */     this.field_149960_b = p_149651_1_.registerIcon(getTextureName() + "_base");
/* 152:    */   }
/* 153:    */   
/* 154:    */   public IIcon func_149959_e()
/* 155:    */   {
/* 156:192 */     return this.field_149960_b;
/* 157:    */   }
/* 158:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockBrewingStand
 * JD-Core Version:    0.7.0.1
 */