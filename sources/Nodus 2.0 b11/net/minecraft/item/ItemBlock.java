/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.Block.SoundType;
/*   6:    */ import net.minecraft.block.material.Material;
/*   7:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   8:    */ import net.minecraft.creativetab.CreativeTabs;
/*   9:    */ import net.minecraft.entity.player.EntityPlayer;
/*  10:    */ import net.minecraft.init.Blocks;
/*  11:    */ import net.minecraft.util.IIcon;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ 
/*  14:    */ public class ItemBlock
/*  15:    */   extends Item
/*  16:    */ {
/*  17:    */   protected final Block field_150939_a;
/*  18:    */   private IIcon field_150938_b;
/*  19:    */   private static final String __OBFID = "CL_00001772";
/*  20:    */   
/*  21:    */   public ItemBlock(Block p_i45328_1_)
/*  22:    */   {
/*  23: 21 */     this.field_150939_a = p_i45328_1_;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public ItemBlock setUnlocalizedName(String p_150937_1_)
/*  27:    */   {
/*  28: 29 */     super.setUnlocalizedName(p_150937_1_);
/*  29: 30 */     return this;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public int getSpriteNumber()
/*  33:    */   {
/*  34: 38 */     return this.field_150939_a.getItemIconName() != null ? 1 : 0;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public IIcon getIconFromDamage(int par1)
/*  38:    */   {
/*  39: 46 */     return this.field_150938_b != null ? this.field_150938_b : this.field_150939_a.getBlockTextureFromSide(1);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/*  43:    */   {
/*  44: 55 */     Block var11 = par3World.getBlock(par4, par5, par6);
/*  45: 57 */     if ((var11 == Blocks.snow_layer) && ((par3World.getBlockMetadata(par4, par5, par6) & 0x7) < 1))
/*  46:    */     {
/*  47: 59 */       par7 = 1;
/*  48:    */     }
/*  49: 61 */     else if ((var11 != Blocks.vine) && (var11 != Blocks.tallgrass) && (var11 != Blocks.deadbush))
/*  50:    */     {
/*  51: 63 */       if (par7 == 0) {
/*  52: 65 */         par5--;
/*  53:    */       }
/*  54: 68 */       if (par7 == 1) {
/*  55: 70 */         par5++;
/*  56:    */       }
/*  57: 73 */       if (par7 == 2) {
/*  58: 75 */         par6--;
/*  59:    */       }
/*  60: 78 */       if (par7 == 3) {
/*  61: 80 */         par6++;
/*  62:    */       }
/*  63: 83 */       if (par7 == 4) {
/*  64: 85 */         par4--;
/*  65:    */       }
/*  66: 88 */       if (par7 == 5) {
/*  67: 90 */         par4++;
/*  68:    */       }
/*  69:    */     }
/*  70: 94 */     if (par1ItemStack.stackSize == 0) {
/*  71: 96 */       return false;
/*  72:    */     }
/*  73: 98 */     if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
/*  74:100 */       return false;
/*  75:    */     }
/*  76:102 */     if ((par5 == 255) && (this.field_150939_a.getMaterial().isSolid())) {
/*  77:104 */       return false;
/*  78:    */     }
/*  79:106 */     if (par3World.canPlaceEntityOnSide(this.field_150939_a, par4, par5, par6, false, par7, par2EntityPlayer, par1ItemStack))
/*  80:    */     {
/*  81:108 */       int var12 = getMetadata(par1ItemStack.getItemDamage());
/*  82:109 */       int var13 = this.field_150939_a.onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, var12);
/*  83:111 */       if (par3World.setBlock(par4, par5, par6, this.field_150939_a, var13, 3))
/*  84:    */       {
/*  85:113 */         if (par3World.getBlock(par4, par5, par6) == this.field_150939_a)
/*  86:    */         {
/*  87:115 */           this.field_150939_a.onBlockPlacedBy(par3World, par4, par5, par6, par2EntityPlayer, par1ItemStack);
/*  88:116 */           this.field_150939_a.onPostBlockPlaced(par3World, par4, par5, par6, var13);
/*  89:    */         }
/*  90:119 */         par3World.playSoundEffect(par4 + 0.5F, par5 + 0.5F, par6 + 0.5F, this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.func_150497_c() + 1.0F) / 2.0F, this.field_150939_a.stepSound.func_150494_d() * 0.8F);
/*  91:120 */         par1ItemStack.stackSize -= 1;
/*  92:    */       }
/*  93:123 */       return true;
/*  94:    */     }
/*  95:127 */     return false;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean func_150936_a(World p_150936_1_, int p_150936_2_, int p_150936_3_, int p_150936_4_, int p_150936_5_, EntityPlayer p_150936_6_, ItemStack p_150936_7_)
/*  99:    */   {
/* 100:133 */     Block var8 = p_150936_1_.getBlock(p_150936_2_, p_150936_3_, p_150936_4_);
/* 101:135 */     if (var8 == Blocks.snow_layer)
/* 102:    */     {
/* 103:137 */       p_150936_5_ = 1;
/* 104:    */     }
/* 105:139 */     else if ((var8 != Blocks.vine) && (var8 != Blocks.tallgrass) && (var8 != Blocks.deadbush))
/* 106:    */     {
/* 107:141 */       if (p_150936_5_ == 0) {
/* 108:143 */         p_150936_3_--;
/* 109:    */       }
/* 110:146 */       if (p_150936_5_ == 1) {
/* 111:148 */         p_150936_3_++;
/* 112:    */       }
/* 113:151 */       if (p_150936_5_ == 2) {
/* 114:153 */         p_150936_4_--;
/* 115:    */       }
/* 116:156 */       if (p_150936_5_ == 3) {
/* 117:158 */         p_150936_4_++;
/* 118:    */       }
/* 119:161 */       if (p_150936_5_ == 4) {
/* 120:163 */         p_150936_2_--;
/* 121:    */       }
/* 122:166 */       if (p_150936_5_ == 5) {
/* 123:168 */         p_150936_2_++;
/* 124:    */       }
/* 125:    */     }
/* 126:172 */     return p_150936_1_.canPlaceEntityOnSide(this.field_150939_a, p_150936_2_, p_150936_3_, p_150936_4_, false, p_150936_5_, null, p_150936_7_);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String getUnlocalizedName(ItemStack par1ItemStack)
/* 130:    */   {
/* 131:181 */     return this.field_150939_a.getUnlocalizedName();
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String getUnlocalizedName()
/* 135:    */   {
/* 136:189 */     return this.field_150939_a.getUnlocalizedName();
/* 137:    */   }
/* 138:    */   
/* 139:    */   public CreativeTabs getCreativeTab()
/* 140:    */   {
/* 141:197 */     return this.field_150939_a.getCreativeTabToDisplayOn();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
/* 145:    */   {
/* 146:205 */     this.field_150939_a.getSubBlocks(p_150895_1_, p_150895_2_, p_150895_3_);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void registerIcons(IIconRegister par1IconRegister)
/* 150:    */   {
/* 151:210 */     String var2 = this.field_150939_a.getItemIconName();
/* 152:212 */     if (var2 != null) {
/* 153:214 */       this.field_150938_b = par1IconRegister.registerIcon(var2);
/* 154:    */     }
/* 155:    */   }
/* 156:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemBlock
 * JD-Core Version:    0.7.0.1
 */