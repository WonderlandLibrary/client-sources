package net.minecraft.item;

import java.util.*;
import net.minecraft.block.*;
import net.minecraft.creativetab.*;
import com.google.common.collect.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class ItemTool extends Item
{
    private float damageVsEntity;
    protected float efficiencyOnProperMaterial;
    private static final String[] I;
    private Set<Block> effectiveBlocks;
    protected ToolMaterial toolMaterial;
    
    @Override
    public boolean isFull3D() {
        return " ".length() != 0;
    }
    
    @Override
    public boolean getIsRepairable(final ItemStack itemStack, final ItemStack itemStack2) {
        int n;
        if (this.toolMaterial.getRepairItem() == itemStack2.getItem()) {
            n = " ".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            n = (super.getIsRepairable(itemStack, itemStack2) ? 1 : 0);
        }
        return n != 0;
    }
    
    @Override
    public boolean hitEntity(final ItemStack itemStack, final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
        itemStack.damageItem("  ".length(), entityLivingBase2);
        return " ".length() != 0;
    }
    
    static {
        I();
    }
    
    protected ItemTool(final float n, final ToolMaterial toolMaterial, final Set<Block> effectiveBlocks) {
        this.efficiencyOnProperMaterial = 4.0f;
        this.toolMaterial = toolMaterial;
        this.effectiveBlocks = effectiveBlocks;
        this.maxStackSize = " ".length();
        this.setMaxDamage(toolMaterial.getMaxUses());
        this.efficiencyOnProperMaterial = toolMaterial.getEfficiencyOnProperMaterial();
        this.damageVsEntity = n + toolMaterial.getDamageVsEntity();
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public float getStrVsBlock(final ItemStack itemStack, final Block block) {
        float efficiencyOnProperMaterial;
        if (this.effectiveBlocks.contains(block)) {
            efficiencyOnProperMaterial = this.efficiencyOnProperMaterial;
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            efficiencyOnProperMaterial = 1.0f;
        }
        return efficiencyOnProperMaterial;
    }
    
    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers() {
        final Multimap<String, AttributeModifier> itemAttributeModifiers = super.getItemAttributeModifiers();
        itemAttributeModifiers.put((Object)SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), (Object)new AttributeModifier(ItemTool.itemModifierUUID, ItemTool.I["".length()], this.damageVsEntity, "".length()));
        return itemAttributeModifiers;
    }
    
    @Override
    public int getItemEnchantability() {
        return this.toolMaterial.getEnchantability();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0005.8\ro<.3\b)8$%", "QAWaO");
    }
    
    @Override
    public boolean onBlockDestroyed(final ItemStack itemStack, final World world, final Block block, final BlockPos blockPos, final EntityLivingBase entityLivingBase) {
        if (block.getBlockHardness(world, blockPos) != 0.0) {
            itemStack.damageItem(" ".length(), entityLivingBase);
        }
        return " ".length() != 0;
    }
    
    public ToolMaterial getToolMaterial() {
        return this.toolMaterial;
    }
    
    public String getToolMaterialName() {
        return this.toolMaterial.toString();
    }
}
