package net.minecraft.item;

import com.google.common.collect.Multimap;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemTool extends Item {
   private float damageVsEntity;
   protected float efficiencyOnProperMaterial = 4.0F;
   protected Item.ToolMaterial toolMaterial;
   private static final String __OBFID = "CL_00000019";
   private Set effectiveBlocksTool;

   public float getStrVsBlock(ItemStack var1, Block var2) {
      return this.effectiveBlocksTool.contains(var2) ? this.efficiencyOnProperMaterial : 1.0F;
   }

   protected ItemTool(float var1, Item.ToolMaterial var2, Set var3) {
      this.toolMaterial = var2;
      this.effectiveBlocksTool = var3;
      this.maxStackSize = 1;
      this.setMaxDamage(var2.getMaxUses());
      this.efficiencyOnProperMaterial = var2.getEfficiencyOnProperMaterial();
      this.damageVsEntity = var1 + var2.getDamageVsEntity();
      this.setCreativeTab(CreativeTabs.tabTools);
   }

   public boolean hitEntity(ItemStack var1, EntityLivingBase var2, EntityLivingBase var3) {
      var1.damageItem(2, var3);
      return true;
   }

   public boolean isFull3D() {
      return true;
   }

   public int getItemEnchantability() {
      return this.toolMaterial.getEnchantability();
   }

   public boolean onBlockDestroyed(ItemStack var1, World var2, Block var3, BlockPos var4, EntityLivingBase var5) {
      if ((double)var3.getBlockHardness(var2, var4) != 0.0D) {
         var1.damageItem(1, var5);
      }

      return true;
   }

   public String getToolMaterialName() {
      return this.toolMaterial.toString();
   }

   public boolean getIsRepairable(ItemStack var1, ItemStack var2) {
      return this.toolMaterial.getBaseItemForRepair() == var2.getItem() ? true : super.getIsRepairable(var1, var2);
   }

   public Item.ToolMaterial getToolMaterial() {
      return this.toolMaterial;
   }

   public Multimap getItemAttributeModifiers() {
      Multimap var1 = super.getItemAttributeModifiers();
      var1.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Tool modifier", (double)this.damageVsEntity, 0));
      return var1;
   }
}
