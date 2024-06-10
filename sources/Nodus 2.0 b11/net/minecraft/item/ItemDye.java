/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.block.BlockColored;
/*   7:    */ import net.minecraft.block.BlockLog;
/*   8:    */ import net.minecraft.block.IGrowable;
/*   9:    */ import net.minecraft.block.material.Material;
/*  10:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  11:    */ import net.minecraft.creativetab.CreativeTabs;
/*  12:    */ import net.minecraft.entity.EntityLivingBase;
/*  13:    */ import net.minecraft.entity.passive.EntitySheep;
/*  14:    */ import net.minecraft.entity.player.EntityPlayer;
/*  15:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  16:    */ import net.minecraft.init.Blocks;
/*  17:    */ import net.minecraft.util.IIcon;
/*  18:    */ import net.minecraft.util.MathHelper;
/*  19:    */ import net.minecraft.world.World;
/*  20:    */ 
/*  21:    */ public class ItemDye
/*  22:    */   extends Item
/*  23:    */ {
/*  24: 21 */   public static final String[] field_150923_a = { "black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white" };
/*  25: 22 */   public static final String[] field_150921_b = { "black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "light_blue", "magenta", "orange", "white" };
/*  26: 23 */   public static final int[] field_150922_c = { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320 };
/*  27:    */   private IIcon[] field_150920_d;
/*  28:    */   private static final String __OBFID = "CL_00000022";
/*  29:    */   
/*  30:    */   public ItemDye()
/*  31:    */   {
/*  32: 29 */     setHasSubtypes(true);
/*  33: 30 */     setMaxDamage(0);
/*  34: 31 */     setCreativeTab(CreativeTabs.tabMaterials);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public IIcon getIconFromDamage(int par1)
/*  38:    */   {
/*  39: 39 */     int var2 = MathHelper.clamp_int(par1, 0, 15);
/*  40: 40 */     return this.field_150920_d[var2];
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getUnlocalizedName(ItemStack par1ItemStack)
/*  44:    */   {
/*  45: 49 */     int var2 = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 15);
/*  46: 50 */     return super.getUnlocalizedName() + "." + field_150923_a[var2];
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/*  50:    */   {
/*  51: 59 */     if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
/*  52: 61 */       return false;
/*  53:    */     }
/*  54: 65 */     if (par1ItemStack.getItemDamage() == 15)
/*  55:    */     {
/*  56: 67 */       if (func_150919_a(par1ItemStack, par3World, par4, par5, par6))
/*  57:    */       {
/*  58: 69 */         if (!par3World.isClient) {
/*  59: 71 */           par3World.playAuxSFX(2005, par4, par5, par6, 0);
/*  60:    */         }
/*  61: 74 */         return true;
/*  62:    */       }
/*  63:    */     }
/*  64: 77 */     else if (par1ItemStack.getItemDamage() == 3)
/*  65:    */     {
/*  66: 79 */       Block var11 = par3World.getBlock(par4, par5, par6);
/*  67: 80 */       int var12 = par3World.getBlockMetadata(par4, par5, par6);
/*  68: 82 */       if ((var11 == Blocks.log) && (BlockLog.func_150165_c(var12) == 3))
/*  69:    */       {
/*  70: 84 */         if (par7 == 0) {
/*  71: 86 */           return false;
/*  72:    */         }
/*  73: 89 */         if (par7 == 1) {
/*  74: 91 */           return false;
/*  75:    */         }
/*  76: 94 */         if (par7 == 2) {
/*  77: 96 */           par6--;
/*  78:    */         }
/*  79: 99 */         if (par7 == 3) {
/*  80:101 */           par6++;
/*  81:    */         }
/*  82:104 */         if (par7 == 4) {
/*  83:106 */           par4--;
/*  84:    */         }
/*  85:109 */         if (par7 == 5) {
/*  86:111 */           par4++;
/*  87:    */         }
/*  88:114 */         if (par3World.isAirBlock(par4, par5, par6))
/*  89:    */         {
/*  90:116 */           int var13 = Blocks.cocoa.onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, 0);
/*  91:117 */           par3World.setBlock(par4, par5, par6, Blocks.cocoa, var13, 2);
/*  92:119 */           if (!par2EntityPlayer.capabilities.isCreativeMode) {
/*  93:121 */             par1ItemStack.stackSize -= 1;
/*  94:    */           }
/*  95:    */         }
/*  96:125 */         return true;
/*  97:    */       }
/*  98:    */     }
/*  99:129 */     return false;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static boolean func_150919_a(ItemStack p_150919_0_, World p_150919_1_, int p_150919_2_, int p_150919_3_, int p_150919_4_)
/* 103:    */   {
/* 104:135 */     Block var5 = p_150919_1_.getBlock(p_150919_2_, p_150919_3_, p_150919_4_);
/* 105:137 */     if ((var5 instanceof IGrowable))
/* 106:    */     {
/* 107:139 */       IGrowable var6 = (IGrowable)var5;
/* 108:141 */       if (var6.func_149851_a(p_150919_1_, p_150919_2_, p_150919_3_, p_150919_4_, p_150919_1_.isClient))
/* 109:    */       {
/* 110:143 */         if (!p_150919_1_.isClient)
/* 111:    */         {
/* 112:145 */           if (var6.func_149852_a(p_150919_1_, p_150919_1_.rand, p_150919_2_, p_150919_3_, p_150919_4_)) {
/* 113:147 */             var6.func_149853_b(p_150919_1_, p_150919_1_.rand, p_150919_2_, p_150919_3_, p_150919_4_);
/* 114:    */           }
/* 115:150 */           p_150919_0_.stackSize -= 1;
/* 116:    */         }
/* 117:153 */         return true;
/* 118:    */       }
/* 119:    */     }
/* 120:157 */     return false;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static void func_150918_a(World p_150918_0_, int p_150918_1_, int p_150918_2_, int p_150918_3_, int p_150918_4_)
/* 124:    */   {
/* 125:162 */     if (p_150918_4_ == 0) {
/* 126:164 */       p_150918_4_ = 15;
/* 127:    */     }
/* 128:167 */     Block var5 = p_150918_0_.getBlock(p_150918_1_, p_150918_2_, p_150918_3_);
/* 129:169 */     if (var5.getMaterial() != Material.air)
/* 130:    */     {
/* 131:171 */       var5.setBlockBoundsBasedOnState(p_150918_0_, p_150918_1_, p_150918_2_, p_150918_3_);
/* 132:173 */       for (int var6 = 0; var6 < p_150918_4_; var6++)
/* 133:    */       {
/* 134:175 */         double var7 = itemRand.nextGaussian() * 0.02D;
/* 135:176 */         double var9 = itemRand.nextGaussian() * 0.02D;
/* 136:177 */         double var11 = itemRand.nextGaussian() * 0.02D;
/* 137:178 */         p_150918_0_.spawnParticle("happyVillager", p_150918_1_ + itemRand.nextFloat(), p_150918_2_ + itemRand.nextFloat() * var5.getBlockBoundsMaxY(), p_150918_3_ + itemRand.nextFloat(), var7, var9, var11);
/* 138:    */       }
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase)
/* 143:    */   {
/* 144:188 */     if ((par3EntityLivingBase instanceof EntitySheep))
/* 145:    */     {
/* 146:190 */       EntitySheep var4 = (EntitySheep)par3EntityLivingBase;
/* 147:191 */       int var5 = BlockColored.func_150032_b(par1ItemStack.getItemDamage());
/* 148:193 */       if ((!var4.getSheared()) && (var4.getFleeceColor() != var5))
/* 149:    */       {
/* 150:195 */         var4.setFleeceColor(var5);
/* 151:196 */         par1ItemStack.stackSize -= 1;
/* 152:    */       }
/* 153:199 */       return true;
/* 154:    */     }
/* 155:203 */     return false;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
/* 159:    */   {
/* 160:212 */     for (int var4 = 0; var4 < 16; var4++) {
/* 161:214 */       p_150895_3_.add(new ItemStack(p_150895_1_, 1, var4));
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void registerIcons(IIconRegister par1IconRegister)
/* 166:    */   {
/* 167:220 */     this.field_150920_d = new IIcon[field_150921_b.length];
/* 168:222 */     for (int var2 = 0; var2 < field_150921_b.length; var2++) {
/* 169:224 */       this.field_150920_d[var2] = par1IconRegister.registerIcon(getIconString() + "_" + field_150921_b[var2]);
/* 170:    */     }
/* 171:    */   }
/* 172:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemDye
 * JD-Core Version:    0.7.0.1
 */