/*   1:    */ package net.minecraft.block;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.entity.item.EntityItem;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
/*   9:    */ import net.minecraft.init.Items;
/*  10:    */ import net.minecraft.item.Item;
/*  11:    */ import net.minecraft.item.ItemStack;
/*  12:    */ import net.minecraft.nbt.NBTTagCompound;
/*  13:    */ import net.minecraft.tileentity.TileEntity;
/*  14:    */ import net.minecraft.util.IIcon;
/*  15:    */ import net.minecraft.world.World;
/*  16:    */ 
/*  17:    */ public class BlockJukebox
/*  18:    */   extends BlockContainer
/*  19:    */ {
/*  20:    */   private IIcon field_149927_a;
/*  21:    */   private static final String __OBFID = "CL_00000260";
/*  22:    */   
/*  23:    */   protected BlockJukebox()
/*  24:    */   {
/*  25: 23 */     super(Material.wood);
/*  26: 24 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public IIcon getIcon(int p_149691_1_, int p_149691_2_)
/*  30:    */   {
/*  31: 32 */     return p_149691_1_ == 1 ? this.field_149927_a : this.blockIcon;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
/*  35:    */   {
/*  36: 40 */     if (p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_) == 0) {
/*  37: 42 */       return false;
/*  38:    */     }
/*  39: 46 */     func_149925_e(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
/*  40: 47 */     return true;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void func_149926_b(World p_149926_1_, int p_149926_2_, int p_149926_3_, int p_149926_4_, ItemStack p_149926_5_)
/*  44:    */   {
/*  45: 53 */     if (!p_149926_1_.isClient)
/*  46:    */     {
/*  47: 55 */       TileEntityJukebox var6 = (TileEntityJukebox)p_149926_1_.getTileEntity(p_149926_2_, p_149926_3_, p_149926_4_);
/*  48: 57 */       if (var6 != null)
/*  49:    */       {
/*  50: 59 */         var6.func_145857_a(p_149926_5_.copy());
/*  51: 60 */         p_149926_1_.setBlockMetadataWithNotify(p_149926_2_, p_149926_3_, p_149926_4_, 1, 2);
/*  52:    */       }
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void func_149925_e(World p_149925_1_, int p_149925_2_, int p_149925_3_, int p_149925_4_)
/*  57:    */   {
/*  58: 67 */     if (!p_149925_1_.isClient)
/*  59:    */     {
/*  60: 69 */       TileEntityJukebox var5 = (TileEntityJukebox)p_149925_1_.getTileEntity(p_149925_2_, p_149925_3_, p_149925_4_);
/*  61: 71 */       if (var5 != null)
/*  62:    */       {
/*  63: 73 */         ItemStack var6 = var5.func_145856_a();
/*  64: 75 */         if (var6 != null)
/*  65:    */         {
/*  66: 77 */           p_149925_1_.playAuxSFX(1005, p_149925_2_, p_149925_3_, p_149925_4_, 0);
/*  67: 78 */           p_149925_1_.playRecord(null, p_149925_2_, p_149925_3_, p_149925_4_);
/*  68: 79 */           var5.func_145857_a(null);
/*  69: 80 */           p_149925_1_.setBlockMetadataWithNotify(p_149925_2_, p_149925_3_, p_149925_4_, 0, 2);
/*  70: 81 */           float var7 = 0.7F;
/*  71: 82 */           double var8 = p_149925_1_.rand.nextFloat() * var7 + (1.0F - var7) * 0.5D;
/*  72: 83 */           double var10 = p_149925_1_.rand.nextFloat() * var7 + (1.0F - var7) * 0.2D + 0.6D;
/*  73: 84 */           double var12 = p_149925_1_.rand.nextFloat() * var7 + (1.0F - var7) * 0.5D;
/*  74: 85 */           ItemStack var14 = var6.copy();
/*  75: 86 */           EntityItem var15 = new EntityItem(p_149925_1_, p_149925_2_ + var8, p_149925_3_ + var10, p_149925_4_ + var12, var14);
/*  76: 87 */           var15.delayBeforeCanPickup = 10;
/*  77: 88 */           p_149925_1_.spawnEntityInWorld(var15);
/*  78:    */         }
/*  79:    */       }
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
/*  84:    */   {
/*  85: 96 */     func_149925_e(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_);
/*  86: 97 */     super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
/*  90:    */   {
/*  91:105 */     if (!p_149690_1_.isClient) {
/*  92:107 */       super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, 0);
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
/*  97:    */   {
/*  98:116 */     return new TileEntityJukebox();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void registerBlockIcons(IIconRegister p_149651_1_)
/* 102:    */   {
/* 103:121 */     this.blockIcon = p_149651_1_.registerIcon(getTextureName() + "_side");
/* 104:122 */     this.field_149927_a = p_149651_1_.registerIcon(getTextureName() + "_top");
/* 105:    */   }
/* 106:    */   
/* 107:    */   public boolean hasComparatorInputOverride()
/* 108:    */   {
/* 109:127 */     return true;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public int getComparatorInputOverride(World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_)
/* 113:    */   {
/* 114:132 */     ItemStack var6 = ((TileEntityJukebox)p_149736_1_.getTileEntity(p_149736_2_, p_149736_3_, p_149736_4_)).func_145856_a();
/* 115:133 */     return var6 == null ? 0 : Item.getIdFromItem(var6.getItem()) + 1 - Item.getIdFromItem(Items.record_13);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static class TileEntityJukebox
/* 119:    */     extends TileEntity
/* 120:    */   {
/* 121:    */     private ItemStack field_145858_a;
/* 122:    */     private static final String __OBFID = "CL_00000261";
/* 123:    */     
/* 124:    */     public void readFromNBT(NBTTagCompound p_145839_1_)
/* 125:    */     {
/* 126:143 */       super.readFromNBT(p_145839_1_);
/* 127:145 */       if (p_145839_1_.func_150297_b("RecordItem", 10)) {
/* 128:147 */         func_145857_a(ItemStack.loadItemStackFromNBT(p_145839_1_.getCompoundTag("RecordItem")));
/* 129:149 */       } else if (p_145839_1_.getInteger("Record") > 0) {
/* 130:151 */         func_145857_a(new ItemStack(Item.getItemById(p_145839_1_.getInteger("Record")), 1, 0));
/* 131:    */       }
/* 132:    */     }
/* 133:    */     
/* 134:    */     public void writeToNBT(NBTTagCompound p_145841_1_)
/* 135:    */     {
/* 136:157 */       super.writeToNBT(p_145841_1_);
/* 137:159 */       if (func_145856_a() != null)
/* 138:    */       {
/* 139:161 */         p_145841_1_.setTag("RecordItem", func_145856_a().writeToNBT(new NBTTagCompound()));
/* 140:162 */         p_145841_1_.setInteger("Record", Item.getIdFromItem(func_145856_a().getItem()));
/* 141:    */       }
/* 142:    */     }
/* 143:    */     
/* 144:    */     public ItemStack func_145856_a()
/* 145:    */     {
/* 146:168 */       return this.field_145858_a;
/* 147:    */     }
/* 148:    */     
/* 149:    */     public void func_145857_a(ItemStack p_145857_1_)
/* 150:    */     {
/* 151:173 */       this.field_145858_a = p_145857_1_;
/* 152:174 */       onInventoryChanged();
/* 153:    */     }
/* 154:    */   }
/* 155:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockJukebox
 * JD-Core Version:    0.7.0.1
 */