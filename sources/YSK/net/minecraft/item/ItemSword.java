package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.creativetab.*;
import com.google.common.collect.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;

public class ItemSword extends Item
{
    private static final String[] I;
    private final ToolMaterial material;
    private float attackDamage;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0010\u0007\"\u0014\u0015)B.\u000b\u001e.\u0004*\u0001\b", "GbCdz");
    }
    
    public String getToolMaterialName() {
        return this.material.toString();
    }
    
    static {
        I();
    }
    
    @Override
    public boolean getIsRepairable(final ItemStack itemStack, final ItemStack itemStack2) {
        int n;
        if (this.material.getRepairItem() == itemStack2.getItem()) {
            n = " ".length();
            "".length();
            if (4 != 4) {
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
        itemStack.damageItem(" ".length(), entityLivingBase2);
        return " ".length() != 0;
    }
    
    @Override
    public boolean canHarvestBlock(final Block block) {
        if (block == Blocks.web) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack itemStack) {
        return EnumAction.BLOCK;
    }
    
    @Override
    public float getStrVsBlock(final ItemStack itemStack, final Block block) {
        if (block == Blocks.web) {
            return 15.0f;
        }
        final Material material = block.getMaterial();
        float n;
        if (material != Material.plants && material != Material.vine && material != Material.coral && material != Material.leaves && material != Material.gourd) {
            n = 1.0f;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            n = 1.5f;
        }
        return n;
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
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getItemEnchantability() {
        return this.material.getEnchantability();
    }
    
    public float getDamageVsEntity() {
        return this.material.getDamageVsEntity();
    }
    
    @Override
    public boolean onBlockDestroyed(final ItemStack itemStack, final World world, final Block block, final BlockPos blockPos, final EntityLivingBase entityLivingBase) {
        if (block.getBlockHardness(world, blockPos) != 0.0) {
            itemStack.damageItem("  ".length(), entityLivingBase);
        }
        return " ".length() != 0;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        return itemStack;
    }
    
    public ItemSword(final ToolMaterial material) {
        this.material = material;
        this.maxStackSize = " ".length();
        this.setMaxDamage(material.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.attackDamage = 4.0f + material.getDamageVsEntity();
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack itemStack) {
        return 12944 + 35337 - 36332 + 60051;
    }
    
    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers() {
        final Multimap<String, AttributeModifier> itemAttributeModifiers = super.getItemAttributeModifiers();
        itemAttributeModifiers.put((Object)SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), (Object)new AttributeModifier(ItemSword.itemModifierUUID, ItemSword.I["".length()], this.attackDamage, "".length()));
        return itemAttributeModifiers;
    }
    
    @Override
    public boolean isFull3D() {
        return " ".length() != 0;
    }
}
