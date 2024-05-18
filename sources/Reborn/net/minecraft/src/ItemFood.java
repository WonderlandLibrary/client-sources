package net.minecraft.src;

public class ItemFood extends Item
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
    
    public ItemFood(final int par1, final int par2, final float par3, final boolean par4) {
        super(par1);
        this.itemUseDuration = 32;
        this.healAmount = par2;
        this.isWolfsFavoriteMeat = par4;
        this.saturationModifier = par3;
        this.setCreativeTab(CreativeTabs.tabFood);
    }
    
    public ItemFood(final int par1, final int par2, final boolean par3) {
        this(par1, par2, 0.6f, par3);
    }
    
    @Override
    public ItemStack onEaten(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        --par1ItemStack.stackSize;
        par3EntityPlayer.getFoodStats().addStats(this);
        par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5f, par2World.rand.nextFloat() * 0.1f + 0.9f);
        this.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
        return par1ItemStack;
    }
    
    protected void onFoodEaten(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        if (!par2World.isRemote && this.potionId > 0 && par2World.rand.nextFloat() < this.potionEffectProbability) {
            par3EntityPlayer.addPotionEffect(new PotionEffect(this.potionId, this.potionDuration * 20, this.potionAmplifier));
        }
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack par1ItemStack) {
        return 32;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
        return EnumAction.eat;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        if (par3EntityPlayer.canEat(this.alwaysEdible)) {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        }
        return par1ItemStack;
    }
    
    public int getHealAmount() {
        return this.healAmount;
    }
    
    public float getSaturationModifier() {
        return this.saturationModifier;
    }
    
    public boolean isWolfsFavoriteMeat() {
        return this.isWolfsFavoriteMeat;
    }
    
    public ItemFood setPotionEffect(final int par1, final int par2, final int par3, final float par4) {
        this.potionId = par1;
        this.potionDuration = par2;
        this.potionAmplifier = par3;
        this.potionEffectProbability = par4;
        return this;
    }
    
    public ItemFood setAlwaysEdible() {
        this.alwaysEdible = true;
        return this;
    }
}
