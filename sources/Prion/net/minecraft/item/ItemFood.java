package net.minecraft.item;

import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;


















public class ItemFood
  extends Item
{
  public final int itemUseDuration;
  private final int healAmount;
  private final float saturationModifier;
  private final boolean isWolfsFavoriteMeat;
  private boolean alwaysEdible;
  private int potionId;
  private int potionDuration;
  private int potionAmplifier;
  private float potionEffectProbability;
  private static final String __OBFID = "CL_00000036";
  
  public ItemFood(int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_)
  {
    itemUseDuration = 32;
    healAmount = p_i45339_1_;
    isWolfsFavoriteMeat = p_i45339_3_;
    saturationModifier = p_i45339_2_;
    setCreativeTab(CreativeTabs.tabFood);
  }
  
  public ItemFood(int p_i45340_1_, boolean p_i45340_2_)
  {
    this(p_i45340_1_, 0.6F, p_i45340_2_);
  }
  




  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn)
  {
    stackSize -= 1;
    playerIn.getFoodStats().addStats(this, stack);
    worldIn.playSoundAtEntity(playerIn, "random.burp", 0.5F, rand.nextFloat() * 0.1F + 0.9F);
    onFoodEaten(stack, worldIn, playerIn);
    playerIn.triggerAchievement(net.minecraft.stats.StatList.objectUseStats[Item.getIdFromItem(this)]);
    return stack;
  }
  
  protected void onFoodEaten(ItemStack p_77849_1_, World worldIn, EntityPlayer p_77849_3_)
  {
    if ((!isRemote) && (potionId > 0) && (rand.nextFloat() < potionEffectProbability))
    {
      p_77849_3_.addPotionEffect(new PotionEffect(potionId, potionDuration * 20, potionAmplifier));
    }
  }
  



  public int getMaxItemUseDuration(ItemStack stack)
  {
    return 32;
  }
  



  public EnumAction getItemUseAction(ItemStack stack)
  {
    return EnumAction.EAT;
  }
  



  public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
  {
    if (playerIn.canEat(alwaysEdible))
    {
      playerIn.setItemInUse(itemStackIn, getMaxItemUseDuration(itemStackIn));
    }
    
    return itemStackIn;
  }
  
  public int getHealAmount(ItemStack itemStackIn)
  {
    return healAmount;
  }
  
  public float getSaturationModifier(ItemStack itemStackIn)
  {
    return saturationModifier;
  }
  



  public boolean isWolfsFavoriteMeat()
  {
    return isWolfsFavoriteMeat;
  }
  




  public ItemFood setPotionEffect(int p_77844_1_, int duration, int amplifier, float probability)
  {
    potionId = p_77844_1_;
    potionDuration = duration;
    potionAmplifier = amplifier;
    potionEffectProbability = probability;
    return this;
  }
  



  public ItemFood setAlwaysEdible()
  {
    alwaysEdible = true;
    return this;
  }
}
