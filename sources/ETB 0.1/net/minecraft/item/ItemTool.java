package net.minecraft.item;

import com.google.common.collect.Multimap;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemTool extends Item
{
  private Set effectiveBlocksTool;
  protected float efficiencyOnProperMaterial = 4.0F;
  
  private float damageVsEntity;
  
  protected Item.ToolMaterial toolMaterial;
  
  private static final String __OBFID = "CL_00000019";
  

  protected ItemTool(float p_i45333_1_, Item.ToolMaterial p_i45333_2_, Set p_i45333_3_)
  {
    toolMaterial = p_i45333_2_;
    effectiveBlocksTool = p_i45333_3_;
    maxStackSize = 1;
    setMaxDamage(p_i45333_2_.getMaxUses());
    efficiencyOnProperMaterial = p_i45333_2_.getEfficiencyOnProperMaterial();
    damageVsEntity = (p_i45333_1_ + p_i45333_2_.getDamageVsEntity());
    setCreativeTab(CreativeTabs.tabTools);
  }
  
  public float getStrVsBlock(ItemStack stack, Block p_150893_2_)
  {
    return effectiveBlocksTool.contains(p_150893_2_) ? efficiencyOnProperMaterial : 1.0F;
  }
  







  public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
  {
    stack.damageItem(2, attacker);
    return true;
  }
  



  public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn)
  {
    if (blockIn.getBlockHardness(worldIn, pos) != 0.0D)
    {
      stack.damageItem(1, playerIn);
    }
    
    return true;
  }
  



  public boolean isFull3D()
  {
    return true;
  }
  
  public Item.ToolMaterial getToolMaterial()
  {
    return toolMaterial;
  }
  



  public int getItemEnchantability()
  {
    return toolMaterial.getEnchantability();
  }
  



  public String getToolMaterialName()
  {
    return toolMaterial.toString();
  }
  






  public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
  {
    return toolMaterial.getBaseItemForRepair() == repair.getItem() ? true : super.getIsRepairable(toRepair, repair);
  }
  



  public Multimap getItemAttributeModifiers()
  {
    Multimap var1 = super.getItemAttributeModifiers();
    var1.put(net.minecraft.entity.SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Tool modifier", damageVsEntity, 0));
    return var1;
  }
}
