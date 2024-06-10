/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*   5:    */ import net.minecraft.creativetab.CreativeTabs;
/*   6:    */ import net.minecraft.enchantment.Enchantment;
/*   7:    */ import net.minecraft.enchantment.EnchantmentHelper;
/*   8:    */ import net.minecraft.entity.player.EntityPlayer;
/*   9:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  10:    */ import net.minecraft.entity.player.PlayerCapabilities;
/*  11:    */ import net.minecraft.entity.projectile.EntityArrow;
/*  12:    */ import net.minecraft.init.Items;
/*  13:    */ import net.minecraft.util.IIcon;
/*  14:    */ import net.minecraft.world.World;
/*  15:    */ 
/*  16:    */ public class ItemBow
/*  17:    */   extends Item
/*  18:    */ {
/*  19: 15 */   public static final String[] bowPullIconNameArray = { "pulling_0", "pulling_1", "pulling_2" };
/*  20:    */   private IIcon[] iconArray;
/*  21:    */   private static final String __OBFID = "CL_00001777";
/*  22:    */   
/*  23:    */   public ItemBow()
/*  24:    */   {
/*  25: 21 */     this.maxStackSize = 1;
/*  26: 22 */     setMaxDamage(384);
/*  27: 23 */     setCreativeTab(CreativeTabs.tabCombat);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
/*  31:    */   {
/*  32: 31 */     boolean var5 = (par3EntityPlayer.capabilities.isCreativeMode) || (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0);
/*  33: 33 */     if ((var5) || (par3EntityPlayer.inventory.hasItem(Items.arrow)))
/*  34:    */     {
/*  35: 35 */       int var6 = getMaxItemUseDuration(par1ItemStack) - par4;
/*  36: 36 */       float var7 = var6 / 20.0F;
/*  37: 37 */       var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;
/*  38: 39 */       if (var7 < 0.1D) {
/*  39: 41 */         return;
/*  40:    */       }
/*  41: 44 */       if (var7 > 1.0F) {
/*  42: 46 */         var7 = 1.0F;
/*  43:    */       }
/*  44: 49 */       EntityArrow var8 = new EntityArrow(par2World, par3EntityPlayer, var7 * 2.0F);
/*  45: 51 */       if (var7 == 1.0F) {
/*  46: 53 */         var8.setIsCritical(true);
/*  47:    */       }
/*  48: 56 */       int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);
/*  49: 58 */       if (var9 > 0) {
/*  50: 60 */         var8.setDamage(var8.getDamage() + var9 * 0.5D + 0.5D);
/*  51:    */       }
/*  52: 63 */       int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);
/*  53: 65 */       if (var10 > 0) {
/*  54: 67 */         var8.setKnockbackStrength(var10);
/*  55:    */       }
/*  56: 70 */       if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0) {
/*  57: 72 */         var8.setFire(100);
/*  58:    */       }
/*  59: 75 */       par1ItemStack.damageItem(1, par3EntityPlayer);
/*  60: 76 */       par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);
/*  61: 78 */       if (var5) {
/*  62: 80 */         var8.canBePickedUp = 2;
/*  63:    */       } else {
/*  64: 84 */         par3EntityPlayer.inventory.consumeInventoryItem(Items.arrow);
/*  65:    */       }
/*  66: 87 */       if (!par2World.isClient) {
/*  67: 89 */         par2World.spawnEntityInWorld(var8);
/*  68:    */       }
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/*  73:    */   {
/*  74: 96 */     return par1ItemStack;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getMaxItemUseDuration(ItemStack par1ItemStack)
/*  78:    */   {
/*  79:104 */     return 72000;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public EnumAction getItemUseAction(ItemStack par1ItemStack)
/*  83:    */   {
/*  84:112 */     return EnumAction.bow;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/*  88:    */   {
/*  89:120 */     if ((par3EntityPlayer.capabilities.isCreativeMode) || (par3EntityPlayer.inventory.hasItem(Items.arrow))) {
/*  90:122 */       par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
/*  91:    */     }
/*  92:125 */     return par1ItemStack;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getItemEnchantability()
/*  96:    */   {
/*  97:133 */     return 1;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void registerIcons(IIconRegister par1IconRegister)
/* 101:    */   {
/* 102:138 */     this.itemIcon = par1IconRegister.registerIcon(getIconString() + "_standby");
/* 103:139 */     this.iconArray = new IIcon[bowPullIconNameArray.length];
/* 104:141 */     for (int var2 = 0; var2 < this.iconArray.length; var2++) {
/* 105:143 */       this.iconArray[var2] = par1IconRegister.registerIcon(getIconString() + "_" + bowPullIconNameArray[var2]);
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public IIcon getItemIconForUseDuration(int par1)
/* 110:    */   {
/* 111:152 */     return this.iconArray[par1];
/* 112:    */   }
/* 113:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemBow
 * JD-Core Version:    0.7.0.1
 */