/*   1:    */ package net.minecraft.item;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.creativetab.CreativeTabs;
/*   5:    */ import net.minecraft.entity.player.EntityPlayer;
/*   6:    */ import net.minecraft.potion.PotionEffect;
/*   7:    */ import net.minecraft.util.FoodStats;
/*   8:    */ import net.minecraft.world.World;
/*   9:    */ 
/*  10:    */ public class ItemFood
/*  11:    */   extends Item
/*  12:    */ {
/*  13:    */   public final int itemUseDuration;
/*  14:    */   private final int healAmount;
/*  15:    */   private final float saturationModifier;
/*  16:    */   private final boolean isWolfsFavoriteMeat;
/*  17:    */   private boolean alwaysEdible;
/*  18:    */   private int potionId;
/*  19:    */   private int potionDuration;
/*  20:    */   private int potionAmplifier;
/*  21:    */   private float potionEffectProbability;
/*  22:    */   private static final String __OBFID = "CL_00000036";
/*  23:    */   
/*  24:    */   public ItemFood(int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_)
/*  25:    */   {
/*  26: 42 */     this.itemUseDuration = 32;
/*  27: 43 */     this.healAmount = p_i45339_1_;
/*  28: 44 */     this.isWolfsFavoriteMeat = p_i45339_3_;
/*  29: 45 */     this.saturationModifier = p_i45339_2_;
/*  30: 46 */     setCreativeTab(CreativeTabs.tabFood);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public ItemFood(int p_i45340_1_, boolean p_i45340_2_)
/*  34:    */   {
/*  35: 51 */     this(p_i45340_1_, 0.6F, p_i45340_2_);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/*  39:    */   {
/*  40: 56 */     par1ItemStack.stackSize -= 1;
/*  41: 57 */     par3EntityPlayer.getFoodStats().func_151686_a(this, par1ItemStack);
/*  42: 58 */     par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
/*  43: 59 */     onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
/*  44: 60 */     return par1ItemStack;
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/*  48:    */   {
/*  49: 65 */     if ((!par2World.isClient) && (this.potionId > 0) && (par2World.rand.nextFloat() < this.potionEffectProbability)) {
/*  50: 67 */       par3EntityPlayer.addPotionEffect(new PotionEffect(this.potionId, this.potionDuration * 20, this.potionAmplifier));
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getMaxItemUseDuration(ItemStack par1ItemStack)
/*  55:    */   {
/*  56: 76 */     return 32;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public EnumAction getItemUseAction(ItemStack par1ItemStack)
/*  60:    */   {
/*  61: 84 */     return EnumAction.eat;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
/*  65:    */   {
/*  66: 92 */     if (par3EntityPlayer.canEat(this.alwaysEdible)) {
/*  67: 94 */       par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
/*  68:    */     }
/*  69: 97 */     return par1ItemStack;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int func_150905_g(ItemStack p_150905_1_)
/*  73:    */   {
/*  74:102 */     return this.healAmount;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public float func_150906_h(ItemStack p_150906_1_)
/*  78:    */   {
/*  79:107 */     return this.saturationModifier;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean isWolfsFavoriteMeat()
/*  83:    */   {
/*  84:115 */     return this.isWolfsFavoriteMeat;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public ItemFood setPotionEffect(int par1, int par2, int par3, float par4)
/*  88:    */   {
/*  89:124 */     this.potionId = par1;
/*  90:125 */     this.potionDuration = par2;
/*  91:126 */     this.potionAmplifier = par3;
/*  92:127 */     this.potionEffectProbability = par4;
/*  93:128 */     return this;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public ItemFood setAlwaysEdible()
/*  97:    */   {
/*  98:136 */     this.alwaysEdible = true;
/*  99:137 */     return this;
/* 100:    */   }
/* 101:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemFood
 * JD-Core Version:    0.7.0.1
 */