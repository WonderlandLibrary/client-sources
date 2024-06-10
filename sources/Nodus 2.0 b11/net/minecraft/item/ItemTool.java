/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Multimap;
/*   4:    */ import java.util.Set;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.creativetab.CreativeTabs;
/*   7:    */ import net.minecraft.entity.EntityLivingBase;
/*   8:    */ import net.minecraft.entity.SharedMonsterAttributes;
/*   9:    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*  10:    */ import net.minecraft.entity.ai.attributes.IAttribute;
/*  11:    */ import net.minecraft.world.World;
/*  12:    */ 
/*  13:    */ public class ItemTool
/*  14:    */   extends Item
/*  15:    */ {
/*  16:    */   private Set field_150914_c;
/*  17: 15 */   protected float efficiencyOnProperMaterial = 4.0F;
/*  18:    */   private float damageVsEntity;
/*  19:    */   protected Item.ToolMaterial toolMaterial;
/*  20:    */   private static final String __OBFID = "CL_00000019";
/*  21:    */   
/*  22:    */   protected ItemTool(float p_i45333_1_, Item.ToolMaterial p_i45333_2_, Set p_i45333_3_)
/*  23:    */   {
/*  24: 26 */     this.toolMaterial = p_i45333_2_;
/*  25: 27 */     this.field_150914_c = p_i45333_3_;
/*  26: 28 */     this.maxStackSize = 1;
/*  27: 29 */     setMaxDamage(p_i45333_2_.getMaxUses());
/*  28: 30 */     this.efficiencyOnProperMaterial = p_i45333_2_.getEfficiencyOnProperMaterial();
/*  29: 31 */     this.damageVsEntity = (p_i45333_1_ + p_i45333_2_.getDamageVsEntity());
/*  30: 32 */     setCreativeTab(CreativeTabs.tabTools);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_)
/*  34:    */   {
/*  35: 37 */     return this.field_150914_c.contains(p_150893_2_) ? this.efficiencyOnProperMaterial : 1.0F;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
/*  39:    */   {
/*  40: 46 */     par1ItemStack.damageItem(2, par3EntityLivingBase);
/*  41: 47 */     return true;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_)
/*  45:    */   {
/*  46: 52 */     if (p_150894_3_.getBlockHardness(p_150894_2_, p_150894_4_, p_150894_5_, p_150894_6_) != 0.0D) {
/*  47: 54 */       p_150894_1_.damageItem(1, p_150894_7_);
/*  48:    */     }
/*  49: 57 */     return true;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean isFull3D()
/*  53:    */   {
/*  54: 65 */     return true;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Item.ToolMaterial func_150913_i()
/*  58:    */   {
/*  59: 70 */     return this.toolMaterial;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getItemEnchantability()
/*  63:    */   {
/*  64: 78 */     return this.toolMaterial.getEnchantability();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String getToolMaterialName()
/*  68:    */   {
/*  69: 86 */     return this.toolMaterial.toString();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
/*  73:    */   {
/*  74: 94 */     return this.toolMaterial.func_150995_f() == par2ItemStack.getItem() ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Multimap getItemAttributeModifiers()
/*  78:    */   {
/*  79:102 */     Multimap var1 = super.getItemAttributeModifiers();
/*  80:103 */     var1.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", this.damageVsEntity, 0));
/*  81:104 */     return var1;
/*  82:    */   }
/*  83:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemTool
 * JD-Core Version:    0.7.0.1
 */