package net.minecraft.item;

import java.util.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

public class ItemTool extends Item
{
    protected float efficiencyOnProperMaterial;
    protected ToolMaterial toolMaterial;
    private Set effectiveBlocksTool;
    private float damageVsEntity;
    
    protected ItemTool(final float p_i45333_1_, final ToolMaterial p_i45333_2_, final Set p_i45333_3_) {
        this.efficiencyOnProperMaterial = 4.0f;
        this.toolMaterial = p_i45333_2_;
        this.effectiveBlocksTool = p_i45333_3_;
        this.maxStackSize = 1;
        this.setMaxDamage(p_i45333_2_.getMaxUses());
        this.efficiencyOnProperMaterial = p_i45333_2_.getEfficiencyOnProperMaterial();
        this.damageVsEntity = p_i45333_1_ + p_i45333_2_.getDamageVsEntity();
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public float getStrVsBlock(final ItemStack stack, final Block p_150893_2_) {
        return this.effectiveBlocksTool.contains(p_150893_2_) ? this.efficiencyOnProperMaterial : 1.0f;
    }
    
    @Override
    public boolean hitEntity(final ItemStack stack, final EntityLivingBase target, final EntityLivingBase attacker) {
        stack.damageItem(2, attacker);
        return true;
    }
    
    @Override
    public boolean onBlockDestroyed(final ItemStack stack, final World worldIn, final Block blockIn, final BlockPos pos, final EntityLivingBase playerIn) {
        if (blockIn.getBlockHardness(worldIn, pos) != 0.0) {
            stack.damageItem(1, playerIn);
        }
        return true;
    }
    
    @Override
    public boolean isFull3D() {
        return true;
    }
    
    public ToolMaterial getToolMaterial() {
        return this.toolMaterial;
    }
    
    @Override
    public int getItemEnchantability() {
        return this.toolMaterial.getEnchantability();
    }
    
    public String getToolMaterialName() {
        return this.toolMaterial.toString();
    }
    
    @Override
    public boolean getIsRepairable(final ItemStack toRepair, final ItemStack repair) {
        return this.toolMaterial.getBaseItemForRepair() == repair.getItem() || super.getIsRepairable(toRepair, repair);
    }
    
    @Override
    public Multimap getItemAttributeModifiers() {
        final Multimap var1 = super.getItemAttributeModifiers();
        var1.put((Object)SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), (Object)new AttributeModifier(ItemTool.itemModifierUUID, "Tool modifier", this.damageVsEntity, 0));
        return var1;
    }
}
