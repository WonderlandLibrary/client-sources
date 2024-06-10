/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.BlockSkull;
/*   6:    */ import net.minecraft.block.material.Material;
/*   7:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   8:    */ import net.minecraft.creativetab.CreativeTabs;
/*   9:    */ import net.minecraft.entity.player.EntityPlayer;
/*  10:    */ import net.minecraft.init.Blocks;
/*  11:    */ import net.minecraft.nbt.NBTTagCompound;
/*  12:    */ import net.minecraft.tileentity.TileEntity;
/*  13:    */ import net.minecraft.tileentity.TileEntitySkull;
/*  14:    */ import net.minecraft.util.IIcon;
/*  15:    */ import net.minecraft.util.MathHelper;
/*  16:    */ import net.minecraft.util.StatCollector;
/*  17:    */ import net.minecraft.world.World;
/*  18:    */ 
/*  19:    */ public class ItemSkull
/*  20:    */   extends Item
/*  21:    */ {
/*  22: 18 */   private static final String[] skullTypes = { "skeleton", "wither", "zombie", "char", "creeper" };
/*  23: 19 */   public static final String[] field_94587_a = { "skeleton", "wither", "zombie", "steve", "creeper" };
/*  24:    */   private IIcon[] field_94586_c;
/*  25:    */   private static final String __OBFID = "CL_00000067";
/*  26:    */   
/*  27:    */   public ItemSkull()
/*  28:    */   {
/*  29: 25 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  30: 26 */     setMaxDamage(0);
/*  31: 27 */     setHasSubtypes(true);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/*  35:    */   {
/*  36: 36 */     if (par7 == 0) {
/*  37: 38 */       return false;
/*  38:    */     }
/*  39: 40 */     if (!par3World.getBlock(par4, par5, par6).getMaterial().isSolid()) {
/*  40: 42 */       return false;
/*  41:    */     }
/*  42: 46 */     if (par7 == 1) {
/*  43: 48 */       par5++;
/*  44:    */     }
/*  45: 51 */     if (par7 == 2) {
/*  46: 53 */       par6--;
/*  47:    */     }
/*  48: 56 */     if (par7 == 3) {
/*  49: 58 */       par6++;
/*  50:    */     }
/*  51: 61 */     if (par7 == 4) {
/*  52: 63 */       par4--;
/*  53:    */     }
/*  54: 66 */     if (par7 == 5) {
/*  55: 68 */       par4++;
/*  56:    */     }
/*  57: 71 */     if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
/*  58: 73 */       return false;
/*  59:    */     }
/*  60: 75 */     if (!Blocks.skull.canPlaceBlockAt(par3World, par4, par5, par6)) {
/*  61: 77 */       return false;
/*  62:    */     }
/*  63: 81 */     par3World.setBlock(par4, par5, par6, Blocks.skull, par7, 2);
/*  64: 82 */     int var11 = 0;
/*  65: 84 */     if (par7 == 1) {
/*  66: 86 */       var11 = MathHelper.floor_double(par2EntityPlayer.rotationYaw * 16.0F / 360.0F + 0.5D) & 0xF;
/*  67:    */     }
/*  68: 89 */     TileEntity var12 = par3World.getTileEntity(par4, par5, par6);
/*  69: 91 */     if ((var12 != null) && ((var12 instanceof TileEntitySkull)))
/*  70:    */     {
/*  71: 93 */       String var13 = "";
/*  72: 95 */       if ((par1ItemStack.hasTagCompound()) && (par1ItemStack.getTagCompound().func_150297_b("SkullOwner", 8))) {
/*  73: 97 */         var13 = par1ItemStack.getTagCompound().getString("SkullOwner");
/*  74:    */       }
/*  75:100 */       ((TileEntitySkull)var12).func_145905_a(par1ItemStack.getItemDamage(), var13);
/*  76:101 */       ((TileEntitySkull)var12).func_145903_a(var11);
/*  77:102 */       ((BlockSkull)Blocks.skull).func_149965_a(par3World, par4, par5, par6, (TileEntitySkull)var12);
/*  78:    */     }
/*  79:105 */     par1ItemStack.stackSize -= 1;
/*  80:106 */     return true;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
/*  84:    */   {
/*  85:116 */     for (int var4 = 0; var4 < skullTypes.length; var4++) {
/*  86:118 */       p_150895_3_.add(new ItemStack(p_150895_1_, 1, var4));
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public IIcon getIconFromDamage(int par1)
/*  91:    */   {
/*  92:127 */     if ((par1 < 0) || (par1 >= skullTypes.length)) {
/*  93:129 */       par1 = 0;
/*  94:    */     }
/*  95:132 */     return this.field_94586_c[par1];
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int getMetadata(int par1)
/*  99:    */   {
/* 100:140 */     return par1;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getUnlocalizedName(ItemStack par1ItemStack)
/* 104:    */   {
/* 105:149 */     int var2 = par1ItemStack.getItemDamage();
/* 106:151 */     if ((var2 < 0) || (var2 >= skullTypes.length)) {
/* 107:153 */       var2 = 0;
/* 108:    */     }
/* 109:156 */     return super.getUnlocalizedName() + "." + skullTypes[var2];
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getItemStackDisplayName(ItemStack par1ItemStack)
/* 113:    */   {
/* 114:161 */     return (par1ItemStack.getItemDamage() == 3) && (par1ItemStack.hasTagCompound()) && (par1ItemStack.getTagCompound().func_150297_b("SkullOwner", 8)) ? StatCollector.translateToLocalFormatted("item.skull.player.name", new Object[] { par1ItemStack.getTagCompound().getString("SkullOwner") }) : super.getItemStackDisplayName(par1ItemStack);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void registerIcons(IIconRegister par1IconRegister)
/* 118:    */   {
/* 119:166 */     this.field_94586_c = new IIcon[field_94587_a.length];
/* 120:168 */     for (int var2 = 0; var2 < field_94587_a.length; var2++) {
/* 121:170 */       this.field_94586_c[var2] = par1IconRegister.registerIcon(getIconString() + "_" + field_94587_a[var2]);
/* 122:    */     }
/* 123:    */   }
/* 124:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemSkull
 * JD-Core Version:    0.7.0.1
 */