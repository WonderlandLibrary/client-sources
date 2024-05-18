/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
/*     */ import net.minecraft.dispenser.IBehaviorDispenseItem;
/*     */ import net.minecraft.dispenser.IBlockSource;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemArmor extends Item {
/*  23 */   private static final int[] maxDamageArray = new int[] { 11, 16, 15, 13 };
/*  24 */   public static final String[] EMPTY_SLOT_NAMES = new String[] { "minecraft:items/empty_armor_slot_helmet", "minecraft:items/empty_armor_slot_chestplate", "minecraft:items/empty_armor_slot_leggings", "minecraft:items/empty_armor_slot_boots" };
/*  25 */   private static final IBehaviorDispenseItem dispenserBehavior = (IBehaviorDispenseItem)new BehaviorDefaultDispenseItem()
/*     */     {
/*     */       protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
/*     */       {
/*  29 */         BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
/*  30 */         int i = blockpos.getX();
/*  31 */         int j = blockpos.getY();
/*  32 */         int k = blockpos.getZ();
/*  33 */         AxisAlignedBB axisalignedbb = new AxisAlignedBB(i, j, k, (i + 1), (j + 1), (k + 1));
/*  34 */         List<EntityLivingBase> list = source.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb, Predicates.and(EntitySelectors.NOT_SPECTATING, (Predicate)new EntitySelectors.ArmoredMob(stack)));
/*     */         
/*  36 */         if (list.size() > 0) {
/*     */           
/*  38 */           EntityLivingBase entitylivingbase = list.get(0);
/*  39 */           int l = (entitylivingbase instanceof EntityPlayer) ? 1 : 0;
/*  40 */           int i1 = EntityLiving.getArmorPosition(stack);
/*  41 */           ItemStack itemstack = stack.copy();
/*  42 */           itemstack.stackSize = 1;
/*  43 */           entitylivingbase.setCurrentItemOrArmor(i1 - l, itemstack);
/*     */           
/*  45 */           if (entitylivingbase instanceof EntityLiving)
/*     */           {
/*  47 */             ((EntityLiving)entitylivingbase).setEquipmentDropChance(i1, 2.0F);
/*     */           }
/*     */           
/*  50 */           stack.stackSize--;
/*  51 */           return stack;
/*     */         } 
/*     */ 
/*     */         
/*  55 */         return super.dispenseStack(source, stack);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int armorType;
/*     */ 
/*     */ 
/*     */   
/*     */   public final int damageReduceAmount;
/*     */ 
/*     */ 
/*     */   
/*     */   public final int renderIndex;
/*     */ 
/*     */ 
/*     */   
/*     */   private final ArmorMaterial material;
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemArmor(ArmorMaterial material, int renderIndex, int armorType) {
/*  79 */     this.material = material;
/*  80 */     this.armorType = armorType;
/*  81 */     this.renderIndex = renderIndex;
/*  82 */     this.damageReduceAmount = material.getDamageReductionAmount(armorType);
/*  83 */     setMaxDamage(material.getDurability(armorType));
/*  84 */     this.maxStackSize = 1;
/*  85 */     setCreativeTab(CreativeTabs.tabCombat);
/*  86 */     BlockDispenser.dispenseBehaviorRegistry.putObject(this, dispenserBehavior);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/*  91 */     if (renderPass > 0)
/*     */     {
/*  93 */       return 16777215;
/*     */     }
/*     */ 
/*     */     
/*  97 */     int i = getColor(stack);
/*     */     
/*  99 */     if (i < 0)
/*     */     {
/* 101 */       i = 16777215;
/*     */     }
/*     */     
/* 104 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemEnchantability() {
/* 113 */     return this.material.getEnchantability();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArmorMaterial getArmorMaterial() {
/* 121 */     return this.material;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasColor(ItemStack stack) {
/* 129 */     return (this.material != ArmorMaterial.LEATHER) ? false : (!stack.hasTagCompound() ? false : (!stack.getTagCompound().hasKey("display", 10) ? false : stack.getTagCompound().getCompoundTag("display").hasKey("color", 3)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColor(ItemStack stack) {
/* 137 */     if (this.material != ArmorMaterial.LEATHER)
/*     */     {
/* 139 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 143 */     NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */     
/* 145 */     if (nbttagcompound != null) {
/*     */       
/* 147 */       NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
/*     */       
/* 149 */       if (nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3))
/*     */       {
/* 151 */         return nbttagcompound1.getInteger("color");
/*     */       }
/*     */     } 
/*     */     
/* 155 */     return 10511680;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeColor(ItemStack stack) {
/* 164 */     if (this.material == ArmorMaterial.LEATHER) {
/*     */       
/* 166 */       NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */       
/* 168 */       if (nbttagcompound != null) {
/*     */         
/* 170 */         NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
/*     */         
/* 172 */         if (nbttagcompound1.hasKey("color"))
/*     */         {
/* 174 */           nbttagcompound1.removeTag("color");
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(ItemStack stack, int color) {
/* 185 */     if (this.material != ArmorMaterial.LEATHER)
/*     */     {
/* 187 */       throw new UnsupportedOperationException("Can't dye non-leather!");
/*     */     }
/*     */ 
/*     */     
/* 191 */     NBTTagCompound nbttagcompound = stack.getTagCompound();
/*     */     
/* 193 */     if (nbttagcompound == null) {
/*     */       
/* 195 */       nbttagcompound = new NBTTagCompound();
/* 196 */       stack.setTagCompound(nbttagcompound);
/*     */     } 
/*     */     
/* 199 */     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
/*     */     
/* 201 */     if (!nbttagcompound.hasKey("display", 10))
/*     */     {
/* 203 */       nbttagcompound.setTag("display", (NBTBase)nbttagcompound1);
/*     */     }
/*     */     
/* 206 */     nbttagcompound1.setInteger("color", color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
/* 215 */     return (this.material.getRepairItem() == repair.getItem()) ? true : super.getIsRepairable(toRepair, repair);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 223 */     int i = EntityLiving.getArmorPosition(itemStackIn) - 1;
/* 224 */     ItemStack itemstack = playerIn.getCurrentArmor(i);
/*     */     
/* 226 */     if (itemstack == null) {
/*     */       
/* 228 */       playerIn.setCurrentItemOrArmor(i, itemStackIn.copy());
/* 229 */       itemStackIn.stackSize = 0;
/*     */     } 
/*     */     
/* 232 */     return itemStackIn;
/*     */   }
/*     */   
/*     */   public enum ArmorMaterial
/*     */   {
/* 237 */     LEATHER("leather", 5, (String)new int[] { 1, 3, 2, 1 }, 15),
/* 238 */     CHAIN("chainmail", 15, (String)new int[] { 2, 5, 4, 1 }, 12),
/* 239 */     IRON("iron", 15, (String)new int[] { 2, 6, 5, 2 }, 9),
/* 240 */     GOLD("gold", 7, (String)new int[] { 2, 5, 3, 1 }, 25),
/* 241 */     DIAMOND("diamond", 33, (String)new int[] { 3, 8, 6, 3 }, 10);
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private final int maxDamageFactor;
/*     */     private final int[] damageReductionAmountArray;
/*     */     private final int enchantability;
/*     */     
/*     */     ArmorMaterial(String name, int maxDamage, int[] reductionAmounts, int enchantability) {
/* 250 */       this.name = name;
/* 251 */       this.maxDamageFactor = maxDamage;
/* 252 */       this.damageReductionAmountArray = reductionAmounts;
/* 253 */       this.enchantability = enchantability;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getDurability(int armorType) {
/* 258 */       return ItemArmor.maxDamageArray[armorType] * this.maxDamageFactor;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getDamageReductionAmount(int armorType) {
/* 263 */       return this.damageReductionAmountArray[armorType];
/*     */     }
/*     */ 
/*     */     
/*     */     public int getEnchantability() {
/* 268 */       return this.enchantability;
/*     */     }
/*     */ 
/*     */     
/*     */     public Item getRepairItem() {
/* 273 */       return (this == LEATHER) ? Items.leather : ((this == CHAIN) ? Items.iron_ingot : ((this == GOLD) ? Items.gold_ingot : ((this == IRON) ? Items.iron_ingot : ((this == DIAMOND) ? Items.diamond : null))));
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 278 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\ItemArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */