/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Multimap;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.entity.EntityLivingBase;
/*   8:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*   9:    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*  10:    */ import net.minecraft.entity.ai.attributes.IAttribute;
/*  11:    */ import net.minecraft.entity.player.EntityPlayer;
/*  12:    */ import net.minecraft.init.Blocks;
/*  13:    */ import net.minecraft.world.World;
/*  14:    */ 
/*  15:    */ public class ItemSword
/*  16:    */   extends Item
/*  17:    */ {
/*  18:    */   private float field_150934_a;
/*  19:    */   private final Item.ToolMaterial field_150933_b;
/*  20:    */   private static final String __OBFID = "CL_00000072";
/*  21:    */   
/*  22:    */   public ItemSword(Item.ToolMaterial p_i45356_1_)
/*  23:    */   {
/*  24: 22 */     this.field_150933_b = p_i45356_1_;
/*  25: 23 */     this.maxStackSize = 1;
/*  26: 24 */     setMaxDamage(p_i45356_1_.getMaxUses());
/*  27: 25 */     setCreativeTab(CreativeTabs.tabCombat);
/*  28: 26 */     this.field_150934_a = (4.0F + p_i45356_1_.getDamageVsEntity());
/*  29:    */   }
/*  30:    */   
/*  31:    */   public float func_150931_i()
/*  32:    */   {
/*  33: 31 */     return this.field_150933_b.getDamageVsEntity();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_)
/*  37:    */   {
/*  38: 36 */     if (p_150893_2_ == Blocks.web) {
/*  39: 38 */       return 15.0F;
/*  40:    */     }
/*  41: 42 */     Material var3 = p_150893_2_.getMaterial();
/*  42: 43 */     return (var3 != Material.plants) && (var3 != Material.vine) && (var3 != Material.coral) && (var3 != Material.leaves) && (var3 != Material.field_151572_C) ? 1.0F : 1.5F;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
/*  46:    */   {
/*  47: 53 */     par1ItemStack.damageItem(1, par3EntityLivingBase);
/*  48: 54 */     return true;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_)
/*  52:    */   {
/*  53: 59 */     if (p_150894_3_.getBlockHardness(p_150894_2_, p_150894_4_, p_150894_5_, p_150894_6_) != 0.0D) {
/*  54: 61 */       p_150894_1_.damageItem(2, p_150894_7_);
/*  55:    */     }
/*  56: 64 */     return true;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean isFull3D()
/*  60:    */   {
/*  61: 72 */     return true;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public EnumAction getItemUseAction(ItemStack par1ItemStack)
/*  65:    */   {
/*  66: 80 */     return EnumAction.block;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getMaxItemUseDuration(ItemStack par1ItemStack)
/*  70:    */   {
/*  71: 88 */     return 72000;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/*  75:    */   {
/*  76: 96 */     par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
/*  77: 97 */     return par1ItemStack;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean func_150897_b(Block p_150897_1_)
/*  81:    */   {
/*  82:102 */     return p_150897_1_ == Blocks.web;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getItemEnchantability()
/*  86:    */   {
/*  87:110 */     return this.field_150933_b.getEnchantability();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String func_150932_j()
/*  91:    */   {
/*  92:115 */     return this.field_150933_b.toString();
/*  93:    */   }
/*  94:    */   
/*  95:    */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*  96:    */   {
/*  97:123 */     return this.field_150933_b.func_150995_f() == par2ItemStack.getItem() ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Multimap getItemAttributeModifiers()
/* 101:    */   {
/* 102:131 */     Multimap var1 = super.getItemAttributeModifiers();
/* 103:132 */     var1.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", this.field_150934_a, 0));
/* 104:133 */     return var1;
/* 105:    */   }
/* 106:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemSword
 * JD-Core Version:    0.7.0.1
 */