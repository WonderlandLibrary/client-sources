package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;
import net.minecraft.stats.*;
import net.minecraft.creativetab.*;

public class ItemFood extends Item
{
    private int potionDuration;
    private int potionAmplifier;
    private float potionEffectProbability;
    private boolean alwaysEdible;
    private final boolean isWolfsFavoriteMeat;
    public final int itemUseDuration;
    private int potionId;
    private final int healAmount;
    private final float saturationModifier;
    private static final String[] I;
    
    public ItemFood setPotionEffect(final int potionId, final int potionDuration, final int potionAmplifier, final float potionEffectProbability) {
        this.potionId = potionId;
        this.potionDuration = potionDuration;
        this.potionAmplifier = potionAmplifier;
        this.potionEffectProbability = potionEffectProbability;
        return this;
    }
    
    public int getHealAmount(final ItemStack itemStack) {
        return this.healAmount;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0002\r\u001a\u000b\n\u001dB\u0016\u001a\u0017\u0000", "pltoe");
    }
    
    protected void onFoodEaten(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (!world.isRemote && this.potionId > 0 && world.rand.nextFloat() < this.potionEffectProbability) {
            entityPlayer.addPotionEffect(new PotionEffect(this.potionId, this.potionDuration * (0x6A ^ 0x7E), this.potionAmplifier));
        }
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
            if (3 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.canEat(this.alwaysEdible)) {
            entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        }
        return itemStack;
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack itemStack) {
        return 0x27 ^ 0x7;
    }
    
    public ItemFood setAlwaysEdible() {
        this.alwaysEdible = (" ".length() != 0);
        return this;
    }
    
    @Override
    public ItemStack onItemUseFinish(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        itemStack.stackSize -= " ".length();
        entityPlayer.getFoodStats().addStats(this, itemStack);
        world.playSoundAtEntity(entityPlayer, ItemFood.I["".length()], 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
        this.onFoodEaten(itemStack, world, entityPlayer);
        entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }
    
    public float getSaturationModifier(final ItemStack itemStack) {
        return this.saturationModifier;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack itemStack) {
        return EnumAction.EAT;
    }
    
    public ItemFood(final int healAmount, final float saturationModifier, final boolean isWolfsFavoriteMeat) {
        this.itemUseDuration = (0x56 ^ 0x76);
        this.healAmount = healAmount;
        this.isWolfsFavoriteMeat = isWolfsFavoriteMeat;
        this.saturationModifier = saturationModifier;
        this.setCreativeTab(CreativeTabs.tabFood);
    }
    
    public boolean isWolfsFavoriteMeat() {
        return this.isWolfsFavoriteMeat;
    }
    
    public ItemFood(final int n, final boolean b) {
        this(n, 0.6f, b);
    }
    
    static {
        I();
    }
}
