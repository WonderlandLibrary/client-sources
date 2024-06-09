/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemFishFood
/*     */   extends ItemFood
/*     */ {
/*     */   private final boolean cooked;
/*     */   
/*     */   public ItemFishFood(boolean cooked) {
/*  20 */     super(0, 0.0F, false);
/*  21 */     this.cooked = cooked;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHealAmount(ItemStack stack) {
/*  26 */     FishType itemfishfood$fishtype = FishType.byItemStack(stack);
/*  27 */     return (this.cooked && itemfishfood$fishtype.canCook()) ? itemfishfood$fishtype.getCookedHealAmount() : itemfishfood$fishtype.getUncookedHealAmount();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSaturationModifier(ItemStack stack) {
/*  32 */     FishType itemfishfood$fishtype = FishType.byItemStack(stack);
/*  33 */     return (this.cooked && itemfishfood$fishtype.canCook()) ? itemfishfood$fishtype.getCookedSaturationModifier() : itemfishfood$fishtype.getUncookedSaturationModifier();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPotionEffect(ItemStack stack) {
/*  38 */     return (FishType.byItemStack(stack) == FishType.PUFFERFISH) ? "+0-1+2+3+13&4-4" : null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
/*  43 */     FishType itemfishfood$fishtype = FishType.byItemStack(stack);
/*     */     
/*  45 */     if (itemfishfood$fishtype == FishType.PUFFERFISH) {
/*     */       
/*  47 */       player.addPotionEffect(new PotionEffect(Potion.poison.id, 1200, 3));
/*  48 */       player.addPotionEffect(new PotionEffect(Potion.hunger.id, 300, 2));
/*  49 */       player.addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 1));
/*     */     } 
/*     */     
/*  52 */     super.onFoodEaten(stack, worldIn, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/*     */     byte b;
/*     */     int i;
/*     */     FishType[] arrayOfFishType;
/*  60 */     for (i = (arrayOfFishType = FishType.values()).length, b = 0; b < i; ) { FishType itemfishfood$fishtype = arrayOfFishType[b];
/*     */       
/*  62 */       if (!this.cooked || itemfishfood$fishtype.canCook())
/*     */       {
/*  64 */         subItems.add(new ItemStack(this, 1, itemfishfood$fishtype.getMetadata()));
/*     */       }
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(ItemStack stack) {
/*  75 */     FishType itemfishfood$fishtype = FishType.byItemStack(stack);
/*  76 */     return String.valueOf(getUnlocalizedName()) + "." + itemfishfood$fishtype.getUnlocalizedName() + "." + ((this.cooked && itemfishfood$fishtype.canCook()) ? "cooked" : "raw");
/*     */   }
/*     */   
/*     */   public enum FishType
/*     */   {
/*  81 */     COD(0, "cod", 2, 0.1F, 5, 0.6F),
/*  82 */     SALMON(1, "salmon", 2, 0.1F, 6, 0.8F),
/*  83 */     CLOWNFISH(2, "clownfish", 1, 0.1F),
/*  84 */     PUFFERFISH(3, "pufferfish", 1, 0.1F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       FishType[] arrayOfFishType;
/* 164 */       for (i = (arrayOfFishType = values()).length, b = 0; b < i; ) { FishType itemfishfood$fishtype = arrayOfFishType[b];
/*     */         
/* 166 */         META_LOOKUP.put(Integer.valueOf(itemfishfood$fishtype.getMetadata()), itemfishfood$fishtype);
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     private boolean cookable = false;
/*     */     private static final Map<Integer, FishType> META_LOOKUP = Maps.newHashMap();
/*     */     private final int meta;
/*     */     private final String unlocalizedName;
/*     */     private final int uncookedHealAmount;
/*     */     private final float uncookedSaturationModifier;
/*     */     private final int cookedHealAmount;
/*     */     private final float cookedSaturationModifier;
/*     */     
/*     */     FishType(int meta, String unlocalizedName, int uncookedHeal, float uncookedSaturation, int cookedHeal, float cookedSaturation) {
/*     */       this.meta = meta;
/*     */       this.unlocalizedName = unlocalizedName;
/*     */       this.uncookedHealAmount = uncookedHeal;
/*     */       this.uncookedSaturationModifier = uncookedSaturation;
/*     */       this.cookedHealAmount = cookedHeal;
/*     */       this.cookedSaturationModifier = cookedSaturation;
/*     */       this.cookable = true;
/*     */     }
/*     */     
/*     */     FishType(int meta, String unlocalizedName, int uncookedHeal, float uncookedSaturation) {
/*     */       this.meta = meta;
/*     */       this.unlocalizedName = unlocalizedName;
/*     */       this.uncookedHealAmount = uncookedHeal;
/*     */       this.uncookedSaturationModifier = uncookedSaturation;
/*     */       this.cookedHealAmount = 0;
/*     */       this.cookedSaturationModifier = 0.0F;
/*     */       this.cookable = false;
/*     */     }
/*     */     
/*     */     public int getMetadata() {
/*     */       return this.meta;
/*     */     }
/*     */     
/*     */     public String getUnlocalizedName() {
/*     */       return this.unlocalizedName;
/*     */     }
/*     */     
/*     */     public int getUncookedHealAmount() {
/*     */       return this.uncookedHealAmount;
/*     */     }
/*     */     
/*     */     public float getUncookedSaturationModifier() {
/*     */       return this.uncookedSaturationModifier;
/*     */     }
/*     */     
/*     */     public int getCookedHealAmount() {
/*     */       return this.cookedHealAmount;
/*     */     }
/*     */     
/*     */     public float getCookedSaturationModifier() {
/*     */       return this.cookedSaturationModifier;
/*     */     }
/*     */     
/*     */     public boolean canCook() {
/*     */       return this.cookable;
/*     */     }
/*     */     
/*     */     public static FishType byMetadata(int meta) {
/*     */       FishType itemfishfood$fishtype = META_LOOKUP.get(Integer.valueOf(meta));
/*     */       return (itemfishfood$fishtype == null) ? COD : itemfishfood$fishtype;
/*     */     }
/*     */     
/*     */     public static FishType byItemStack(ItemStack stack) {
/*     */       return (stack.getItem() instanceof ItemFishFood) ? byMetadata(stack.getMetadata()) : COD;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\ItemFishFood.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */