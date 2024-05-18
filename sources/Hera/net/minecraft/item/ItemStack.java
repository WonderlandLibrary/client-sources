/*     */ package net.minecraft.item;
/*     */ 
/*     */ import com.google.common.collect.HashMultimap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Multimap;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentDurability;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.event.HoverEvent;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public final class ItemStack
/*     */ {
/*  39 */   public static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.###");
/*     */ 
/*     */   
/*     */   public int stackSize;
/*     */ 
/*     */   
/*     */   public int animationsToGo;
/*     */ 
/*     */   
/*     */   private Item item;
/*     */   
/*     */   private NBTTagCompound stackTagCompound;
/*     */   
/*     */   private int itemDamage;
/*     */   
/*     */   private EntityItemFrame itemFrame;
/*     */   
/*     */   private Block canDestroyCacheBlock;
/*     */   
/*     */   private boolean canDestroyCacheResult;
/*     */   
/*     */   private Block canPlaceOnCacheBlock;
/*     */   
/*     */   private boolean canPlaceOnCacheResult;
/*     */ 
/*     */   
/*     */   public ItemStack(Block blockIn) {
/*  66 */     this(blockIn, 1);
/*     */   }
/*     */   
/*     */   public ItemStack(Block blockIn, int amount) {
/*  70 */     this(blockIn, amount, 0);
/*     */   }
/*     */   
/*     */   public ItemStack(Block blockIn, int amount, int meta) {
/*  74 */     this(Item.getItemFromBlock(blockIn), amount, meta);
/*     */   }
/*     */   
/*     */   public ItemStack(Item itemIn) {
/*  78 */     this(itemIn, 1);
/*     */   }
/*     */   
/*     */   public ItemStack(Item itemIn, int amount) {
/*  82 */     this(itemIn, amount, 0);
/*     */   }
/*     */   
/*     */   public ItemStack(Item itemIn, int amount, int meta) {
/*  86 */     this.canDestroyCacheBlock = null;
/*  87 */     this.canDestroyCacheResult = false;
/*  88 */     this.canPlaceOnCacheBlock = null;
/*  89 */     this.canPlaceOnCacheResult = false;
/*  90 */     this.item = itemIn;
/*  91 */     this.stackSize = amount;
/*  92 */     this.itemDamage = meta;
/*     */     
/*  94 */     if (this.itemDamage < 0) {
/*  95 */       this.itemDamage = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public static ItemStack loadItemStackFromNBT(NBTTagCompound nbt) {
/* 100 */     ItemStack itemstack = new ItemStack();
/* 101 */     itemstack.readFromNBT(nbt);
/* 102 */     return (itemstack.getItem() != null) ? itemstack : null;
/*     */   }
/*     */   
/*     */   private ItemStack() {
/* 106 */     this.canDestroyCacheBlock = null;
/* 107 */     this.canDestroyCacheResult = false;
/* 108 */     this.canPlaceOnCacheBlock = null;
/* 109 */     this.canPlaceOnCacheResult = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack splitStack(int amount) {
/* 117 */     ItemStack itemstack = new ItemStack(this.item, amount, this.itemDamage);
/*     */     
/* 119 */     if (this.stackTagCompound != null) {
/* 120 */       itemstack.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
/*     */     }
/*     */     
/* 123 */     this.stackSize -= amount;
/* 124 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItem() {
/* 131 */     return this.item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 140 */     boolean flag = getItem().onItemUse(this, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
/*     */     
/* 142 */     if (flag) {
/* 143 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
/*     */     }
/*     */     
/* 146 */     return flag;
/*     */   }
/*     */   
/*     */   public float getStrVsBlock(Block blockIn) {
/* 150 */     return getItem().getStrVsBlock(this, blockIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack useItemRightClick(World worldIn, EntityPlayer playerIn) {
/* 158 */     return getItem().onItemRightClick(this, worldIn, playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack onItemUseFinish(World worldIn, EntityPlayer playerIn) {
/* 166 */     return getItem().onItemUseFinish(this, worldIn, playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
/* 173 */     ResourceLocation resourcelocation = (ResourceLocation)Item.itemRegistry.getNameForObject(this.item);
/* 174 */     nbt.setString("id", (resourcelocation == null) ? "minecraft:air" : resourcelocation.toString());
/* 175 */     nbt.setByte("Count", (byte)this.stackSize);
/* 176 */     nbt.setShort("Damage", (short)this.itemDamage);
/*     */     
/* 178 */     if (this.stackTagCompound != null) {
/* 179 */       nbt.setTag("tag", (NBTBase)this.stackTagCompound);
/*     */     }
/*     */     
/* 182 */     return nbt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/* 189 */     if (nbt.hasKey("id", 8)) {
/* 190 */       this.item = Item.getByNameOrId(nbt.getString("id"));
/*     */     } else {
/* 192 */       this.item = Item.getItemById(nbt.getShort("id"));
/*     */     } 
/*     */     
/* 195 */     this.stackSize = nbt.getByte("Count");
/* 196 */     this.itemDamage = nbt.getShort("Damage");
/*     */     
/* 198 */     if (this.itemDamage < 0) {
/* 199 */       this.itemDamage = 0;
/*     */     }
/*     */     
/* 202 */     if (nbt.hasKey("tag", 10)) {
/* 203 */       this.stackTagCompound = nbt.getCompoundTag("tag");
/*     */       
/* 205 */       if (this.item != null) {
/* 206 */         this.item.updateItemStackNBT(this.stackTagCompound);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxStackSize() {
/* 215 */     return getItem().getItemStackLimit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStackable() {
/* 222 */     return (getMaxStackSize() > 1 && (!isItemStackDamageable() || !isItemDamaged()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemStackDamageable() {
/* 229 */     return (this.item == null) ? false : (
/* 230 */       (this.item.getMaxDamage() <= 0) ? false : (
/* 231 */       !(hasTagCompound() && getTagCompound().getBoolean("Unbreakable"))));
/*     */   }
/*     */   
/*     */   public boolean getHasSubtypes() {
/* 235 */     return this.item.getHasSubtypes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemDamaged() {
/* 242 */     return (isItemStackDamageable() && this.itemDamage > 0);
/*     */   }
/*     */   
/*     */   public int getItemDamage() {
/* 246 */     return this.itemDamage;
/*     */   }
/*     */   
/*     */   public int getMetadata() {
/* 250 */     return this.itemDamage;
/*     */   }
/*     */   
/*     */   public void setItemDamage(int meta) {
/* 254 */     this.itemDamage = meta;
/*     */     
/* 256 */     if (this.itemDamage < 0) {
/* 257 */       this.itemDamage = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxDamage() {
/* 265 */     return this.item.getMaxDamage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attemptDamageItem(int amount, Random rand) {
/* 276 */     if (!isItemStackDamageable()) {
/* 277 */       return false;
/*     */     }
/* 279 */     if (amount > 0) {
/* 280 */       int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, this);
/* 281 */       int j = 0;
/*     */       
/* 283 */       for (int k = 0; i > 0 && k < amount; k++) {
/* 284 */         if (EnchantmentDurability.negateDamage(this, i, rand)) {
/* 285 */           j++;
/*     */         }
/*     */       } 
/*     */       
/* 289 */       amount -= j;
/*     */       
/* 291 */       if (amount <= 0) {
/* 292 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 296 */     this.itemDamage += amount;
/* 297 */     return (this.itemDamage > getMaxDamage());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void damageItem(int amount, EntityLivingBase entityIn) {
/* 305 */     if ((!(entityIn instanceof EntityPlayer) || !((EntityPlayer)entityIn).capabilities.isCreativeMode) && 
/* 306 */       isItemStackDamageable() && 
/* 307 */       attemptDamageItem(amount, entityIn.getRNG())) {
/* 308 */       entityIn.renderBrokenItemStack(this);
/* 309 */       this.stackSize--;
/*     */       
/* 311 */       if (entityIn instanceof EntityPlayer) {
/* 312 */         EntityPlayer entityplayer = (EntityPlayer)entityIn;
/* 313 */         entityplayer.triggerAchievement(StatList.objectBreakStats[Item.getIdFromItem(this.item)]);
/*     */         
/* 315 */         if (this.stackSize == 0 && getItem() instanceof ItemBow) {
/* 316 */           entityplayer.destroyCurrentEquippedItem();
/*     */         }
/*     */       } 
/*     */       
/* 320 */       if (this.stackSize < 0) {
/* 321 */         this.stackSize = 0;
/*     */       }
/*     */       
/* 324 */       this.itemDamage = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void hitEntity(EntityLivingBase entityIn, EntityPlayer playerIn) {
/* 334 */     boolean flag = this.item.hitEntity(this, entityIn, (EntityLivingBase)playerIn);
/*     */     
/* 336 */     if (flag) {
/* 337 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyed(World worldIn, Block blockIn, BlockPos pos, EntityPlayer playerIn) {
/* 345 */     boolean flag = this.item.onBlockDestroyed(this, worldIn, blockIn, pos, (EntityLivingBase)playerIn);
/*     */     
/* 347 */     if (flag) {
/* 348 */       playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canHarvestBlock(Block blockIn) {
/* 356 */     return this.item.canHarvestBlock(blockIn);
/*     */   }
/*     */   
/*     */   public boolean interactWithEntity(EntityPlayer playerIn, EntityLivingBase entityIn) {
/* 360 */     return this.item.itemInteractionForEntity(this, playerIn, entityIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack copy() {
/* 367 */     ItemStack itemstack = new ItemStack(this.item, this.stackSize, this.itemDamage);
/*     */     
/* 369 */     if (this.stackTagCompound != null) {
/* 370 */       itemstack.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
/*     */     }
/*     */     
/* 373 */     return itemstack;
/*     */   }
/*     */   
/*     */   public static boolean areItemStackTagsEqual(ItemStack stackA, ItemStack stackB) {
/* 377 */     return (stackA == null && 
/* 378 */       stackB == null) ? true : (
/*     */       
/* 380 */       (stackA != null && stackB != null) ? (
/* 381 */       (stackA.stackTagCompound == null && stackB.stackTagCompound != null) ? false : (
/* 382 */       !(stackA.stackTagCompound != null && 
/* 383 */       !stackA.stackTagCompound.equals(stackB.stackTagCompound)))) : false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB) {
/* 392 */     return (stackA == null && stackB == null) ? true : (
/* 393 */       (stackA != null && stackB != null) ? stackA.isItemStackEqual(stackB) : false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isItemStackEqual(ItemStack other) {
/* 401 */     return (this.stackSize != other.stackSize) ? false : (
/* 402 */       (this.item != other.item) ? false : (
/* 403 */       (this.itemDamage != other.itemDamage) ? false : (
/* 404 */       (this.stackTagCompound == null && other.stackTagCompound != null) ? false : (
/* 405 */       !(this.stackTagCompound != null && 
/* 406 */       !this.stackTagCompound.equals(other.stackTagCompound))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean areItemsEqual(ItemStack stackA, ItemStack stackB) {
/* 413 */     return (stackA == null && stackB == null) ? true : (
/* 414 */       (stackA != null && stackB != null) ? stackA.isItemEqual(stackB) : false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemEqual(ItemStack other) {
/* 422 */     return (other != null && this.item == other.item && this.itemDamage == other.itemDamage);
/*     */   }
/*     */   
/*     */   public String getUnlocalizedName() {
/* 426 */     return this.item.getUnlocalizedName(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack copyItemStack(ItemStack stack) {
/* 433 */     return (stack == null) ? null : stack.copy();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 437 */     return String.valueOf(this.stackSize) + "x" + this.item.getUnlocalizedName() + "@" + this.itemDamage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateAnimation(World worldIn, Entity entityIn, int inventorySlot, boolean isCurrentItem) {
/* 445 */     if (this.animationsToGo > 0) {
/* 446 */       this.animationsToGo--;
/*     */     }
/*     */     
/* 449 */     this.item.onUpdate(this, worldIn, entityIn, inventorySlot, isCurrentItem);
/*     */   }
/*     */   
/*     */   public void onCrafting(World worldIn, EntityPlayer playerIn, int amount) {
/* 453 */     playerIn.addStat(StatList.objectCraftStats[Item.getIdFromItem(this.item)], amount);
/* 454 */     this.item.onCreated(this, worldIn, playerIn);
/*     */   }
/*     */   
/*     */   public boolean getIsItemStackEqual(ItemStack p_179549_1_) {
/* 458 */     return isItemStackEqual(p_179549_1_);
/*     */   }
/*     */   
/*     */   public int getMaxItemUseDuration() {
/* 462 */     return getItem().getMaxItemUseDuration(this);
/*     */   }
/*     */   
/*     */   public EnumAction getItemUseAction() {
/* 466 */     return getItem().getItemUseAction(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPlayerStoppedUsing(World worldIn, EntityPlayer playerIn, int timeLeft) {
/* 474 */     getItem().onPlayerStoppedUsing(this, worldIn, playerIn, timeLeft);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasTagCompound() {
/* 482 */     return (this.stackTagCompound != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getTagCompound() {
/* 489 */     return this.stackTagCompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getSubCompound(String key, boolean create) {
/* 496 */     if (this.stackTagCompound != null && this.stackTagCompound.hasKey(key, 10))
/* 497 */       return this.stackTagCompound.getCompoundTag(key); 
/* 498 */     if (create) {
/* 499 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 500 */       setTagInfo(key, (NBTBase)nbttagcompound);
/* 501 */       return nbttagcompound;
/*     */     } 
/* 503 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagList getEnchantmentTagList() {
/* 508 */     return (this.stackTagCompound == null) ? null : this.stackTagCompound.getTagList("ench", 10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTagCompound(NBTTagCompound nbt) {
/* 516 */     this.stackTagCompound = nbt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/* 523 */     String s = getItem().getItemStackDisplayName(this);
/*     */     
/* 525 */     if (this.stackTagCompound != null && this.stackTagCompound.hasKey("display", 10)) {
/* 526 */       NBTTagCompound nbttagcompound = this.stackTagCompound.getCompoundTag("display");
/*     */       
/* 528 */       if (nbttagcompound.hasKey("Name", 8)) {
/* 529 */         s = nbttagcompound.getString("Name");
/*     */       }
/*     */     } 
/*     */     
/* 533 */     return s;
/*     */   }
/*     */   
/*     */   public ItemStack setStackDisplayName(String displayName) {
/* 537 */     if (this.stackTagCompound == null) {
/* 538 */       this.stackTagCompound = new NBTTagCompound();
/*     */     }
/*     */     
/* 541 */     if (!this.stackTagCompound.hasKey("display", 10)) {
/* 542 */       this.stackTagCompound.setTag("display", (NBTBase)new NBTTagCompound());
/*     */     }
/*     */     
/* 545 */     this.stackTagCompound.getCompoundTag("display").setString("Name", displayName);
/* 546 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearCustomName() {
/* 553 */     if (this.stackTagCompound != null && 
/* 554 */       this.stackTagCompound.hasKey("display", 10)) {
/* 555 */       NBTTagCompound nbttagcompound = this.stackTagCompound.getCompoundTag("display");
/* 556 */       nbttagcompound.removeTag("Name");
/*     */       
/* 558 */       if (nbttagcompound.hasNoTags()) {
/* 559 */         this.stackTagCompound.removeTag("display");
/*     */         
/* 561 */         if (this.stackTagCompound.hasNoTags()) {
/* 562 */           setTagCompound(null);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasDisplayName() {
/* 573 */     return (this.stackTagCompound == null) ? false : (
/* 574 */       !this.stackTagCompound.hasKey("display", 10) ? false : 
/* 575 */       this.stackTagCompound.getCompoundTag("display").hasKey("Name", 8));
/*     */   }
/*     */   
/*     */   public List<String> getTooltip(EntityPlayer playerIn, boolean advanced) {
/* 579 */     List<String> list = Lists.newArrayList();
/* 580 */     String s = getDisplayName();
/*     */     
/* 582 */     if (hasDisplayName()) {
/* 583 */       s = EnumChatFormatting.ITALIC + s;
/*     */     }
/*     */     
/* 586 */     s = String.valueOf(s) + EnumChatFormatting.RESET;
/*     */     
/* 588 */     if (advanced) {
/* 589 */       String s1 = "";
/*     */       
/* 591 */       if (s.length() > 0) {
/* 592 */         s = String.valueOf(s) + " (";
/* 593 */         s1 = ")";
/*     */       } 
/*     */       
/* 596 */       int i = Item.getIdFromItem(this.item);
/*     */       
/* 598 */       if (getHasSubtypes()) {
/* 599 */         s = String.valueOf(s) + String.format("#%04d/%d%s", 
/* 600 */             new Object[] { Integer.valueOf(i), Integer.valueOf(this.itemDamage), s1 });
/*     */       } else {
/* 602 */         s = String.valueOf(s) + String.format("#%04d%s", new Object[] { Integer.valueOf(i), s1 });
/*     */       } 
/* 604 */     } else if (!hasDisplayName() && this.item == Items.filled_map) {
/* 605 */       s = String.valueOf(s) + " #" + this.itemDamage;
/*     */     } 
/*     */     
/* 608 */     list.add(s);
/* 609 */     int i1 = 0;
/*     */     
/* 611 */     if (hasTagCompound() && this.stackTagCompound.hasKey("HideFlags", 99)) {
/* 612 */       i1 = this.stackTagCompound.getInteger("HideFlags");
/*     */     }
/*     */     
/* 615 */     if ((i1 & 0x20) == 0) {
/* 616 */       this.item.addInformation(this, playerIn, list, advanced);
/*     */     }
/*     */     
/* 619 */     if (hasTagCompound()) {
/* 620 */       if ((i1 & 0x1) == 0) {
/* 621 */         NBTTagList nbttaglist = getEnchantmentTagList();
/*     */         
/* 623 */         if (nbttaglist != null) {
/* 624 */           for (int j = 0; j < nbttaglist.tagCount(); j++) {
/* 625 */             int k = nbttaglist.getCompoundTagAt(j).getShort("id");
/* 626 */             int l = nbttaglist.getCompoundTagAt(j).getShort("lvl");
/*     */             
/* 628 */             if (Enchantment.getEnchantmentById(k) != null) {
/* 629 */               list.add(Enchantment.getEnchantmentById(k).getTranslatedName(l));
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 635 */       if (this.stackTagCompound.hasKey("display", 10)) {
/* 636 */         NBTTagCompound nbttagcompound = this.stackTagCompound.getCompoundTag("display");
/*     */         
/* 638 */         if (nbttagcompound.hasKey("color", 3)) {
/* 639 */           if (advanced) {
/* 640 */             list.add("Color: #" + Integer.toHexString(nbttagcompound.getInteger("color")).toUpperCase());
/*     */           } else {
/* 642 */             list.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("item.dyed"));
/*     */           } 
/*     */         }
/*     */         
/* 646 */         if (nbttagcompound.getTagId("Lore") == 9) {
/* 647 */           NBTTagList nbttaglist1 = nbttagcompound.getTagList("Lore", 8);
/*     */           
/* 649 */           if (nbttaglist1.tagCount() > 0) {
/* 650 */             for (int j1 = 0; j1 < nbttaglist1.tagCount(); j1++) {
/* 651 */               list.add(EnumChatFormatting.DARK_PURPLE + EnumChatFormatting.ITALIC + 
/* 652 */                   nbttaglist1.getStringTagAt(j1));
/*     */             }
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 659 */     Multimap<String, AttributeModifier> multimap = getAttributeModifiers();
/*     */     
/* 661 */     if (!multimap.isEmpty() && (i1 & 0x2) == 0) {
/* 662 */       list.add("");
/*     */       
/* 664 */       for (Map.Entry<String, AttributeModifier> entry : (Iterable<Map.Entry<String, AttributeModifier>>)multimap.entries()) {
/* 665 */         double d1; AttributeModifier attributemodifier = entry.getValue();
/* 666 */         double d0 = attributemodifier.getAmount();
/*     */         
/* 668 */         if (attributemodifier.getID() == Item.itemModifierUUID) {
/* 669 */           d0 += EnchantmentHelper.func_152377_a(this, EnumCreatureAttribute.UNDEFINED);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 674 */         if (attributemodifier.getOperation() != 1 && attributemodifier.getOperation() != 2) {
/* 675 */           d1 = d0;
/*     */         } else {
/* 677 */           d1 = d0 * 100.0D;
/*     */         } 
/*     */         
/* 680 */         if (d0 > 0.0D) {
/* 681 */           list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted(
/* 682 */                 "attribute.modifier.plus." + attributemodifier.getOperation(), 
/* 683 */                 new Object[] { DECIMALFORMAT.format(d1), 
/* 684 */                   StatCollector.translateToLocal("attribute.name." + (String)entry.getKey()) })); continue;
/* 685 */         }  if (d0 < 0.0D) {
/* 686 */           d1 *= -1.0D;
/* 687 */           list.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted(
/* 688 */                 "attribute.modifier.take." + attributemodifier.getOperation(), 
/* 689 */                 new Object[] { DECIMALFORMAT.format(d1), 
/* 690 */                   StatCollector.translateToLocal("attribute.name." + (String)entry.getKey()) }));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 695 */     if (hasTagCompound() && getTagCompound().getBoolean("Unbreakable") && (i1 & 0x4) == 0) {
/* 696 */       list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("item.unbreakable"));
/*     */     }
/*     */     
/* 699 */     if (hasTagCompound() && this.stackTagCompound.hasKey("CanDestroy", 9) && (i1 & 0x8) == 0) {
/* 700 */       NBTTagList nbttaglist2 = this.stackTagCompound.getTagList("CanDestroy", 8);
/*     */       
/* 702 */       if (nbttaglist2.tagCount() > 0) {
/* 703 */         list.add("");
/* 704 */         list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("item.canBreak"));
/*     */         
/* 706 */         for (int k1 = 0; k1 < nbttaglist2.tagCount(); k1++) {
/* 707 */           Block block = Block.getBlockFromName(nbttaglist2.getStringTagAt(k1));
/*     */           
/* 709 */           if (block != null) {
/* 710 */             list.add(EnumChatFormatting.DARK_GRAY + block.getLocalizedName());
/*     */           } else {
/* 712 */             list.add(EnumChatFormatting.DARK_GRAY + "missingno");
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 718 */     if (hasTagCompound() && this.stackTagCompound.hasKey("CanPlaceOn", 9) && (i1 & 0x10) == 0) {
/* 719 */       NBTTagList nbttaglist3 = this.stackTagCompound.getTagList("CanPlaceOn", 8);
/*     */       
/* 721 */       if (nbttaglist3.tagCount() > 0) {
/* 722 */         list.add("");
/* 723 */         list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("item.canPlace"));
/*     */         
/* 725 */         for (int l1 = 0; l1 < nbttaglist3.tagCount(); l1++) {
/* 726 */           Block block1 = Block.getBlockFromName(nbttaglist3.getStringTagAt(l1));
/*     */           
/* 728 */           if (block1 != null) {
/* 729 */             list.add(EnumChatFormatting.DARK_GRAY + block1.getLocalizedName());
/*     */           } else {
/* 731 */             list.add(EnumChatFormatting.DARK_GRAY + "missingno");
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 737 */     if (advanced) {
/* 738 */       if (isItemDamaged()) {
/* 739 */         list.add("Durability: " + (getMaxDamage() - getItemDamage()) + " / " + getMaxDamage());
/*     */       }
/*     */       
/* 742 */       list.add(EnumChatFormatting.DARK_GRAY + (
/* 743 */           (ResourceLocation)Item.itemRegistry.getNameForObject(this.item)).toString());
/*     */       
/* 745 */       if (hasTagCompound()) {
/* 746 */         list.add(EnumChatFormatting.DARK_GRAY + "NBT: " + getTagCompound().getKeySet().size() + " tag(s)");
/*     */       }
/*     */     } 
/*     */     
/* 750 */     return list;
/*     */   }
/*     */   
/*     */   public boolean hasEffect() {
/* 754 */     return getItem().hasEffect(this);
/*     */   }
/*     */   
/*     */   public EnumRarity getRarity() {
/* 758 */     return getItem().getRarity(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemEnchantable() {
/* 765 */     return !getItem().isItemTool(this) ? false : (!isItemEnchanted());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEnchantment(Enchantment ench, int level) {
/* 772 */     if (this.stackTagCompound == null) {
/* 773 */       setTagCompound(new NBTTagCompound());
/*     */     }
/*     */     
/* 776 */     if (!this.stackTagCompound.hasKey("ench", 9)) {
/* 777 */       this.stackTagCompound.setTag("ench", (NBTBase)new NBTTagList());
/*     */     }
/*     */     
/* 780 */     NBTTagList nbttaglist = this.stackTagCompound.getTagList("ench", 10);
/* 781 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 782 */     nbttagcompound.setShort("id", (short)ench.effectId);
/* 783 */     nbttagcompound.setShort("lvl", (short)(byte)level);
/* 784 */     nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemEnchanted() {
/* 791 */     return (this.stackTagCompound != null && this.stackTagCompound.hasKey("ench", 9));
/*     */   }
/*     */   
/*     */   public void setTagInfo(String key, NBTBase value) {
/* 795 */     if (this.stackTagCompound == null) {
/* 796 */       setTagCompound(new NBTTagCompound());
/*     */     }
/*     */     
/* 799 */     this.stackTagCompound.setTag(key, value);
/*     */   }
/*     */   
/*     */   public boolean canEditBlocks() {
/* 803 */     return getItem().canItemEditBlocks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnItemFrame() {
/* 810 */     return (this.itemFrame != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemFrame(EntityItemFrame frame) {
/* 817 */     this.itemFrame = frame;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityItemFrame getItemFrame() {
/* 824 */     return this.itemFrame;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRepairCost() {
/* 831 */     return (hasTagCompound() && this.stackTagCompound.hasKey("RepairCost", 3)) ? 
/* 832 */       this.stackTagCompound.getInteger("RepairCost") : 
/* 833 */       0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRepairCost(int cost) {
/* 840 */     if (!hasTagCompound()) {
/* 841 */       this.stackTagCompound = new NBTTagCompound();
/*     */     }
/*     */     
/* 844 */     this.stackTagCompound.setInteger("RepairCost", cost);
/*     */   }
/*     */ 
/*     */   
/*     */   public Multimap<String, AttributeModifier> getAttributeModifiers() {
/*     */     Multimap<String, AttributeModifier> multimap;
/* 850 */     if (hasTagCompound() && this.stackTagCompound.hasKey("AttributeModifiers", 9)) {
/* 851 */       HashMultimap hashMultimap = HashMultimap.create();
/* 852 */       NBTTagList nbttaglist = this.stackTagCompound.getTagList("AttributeModifiers", 10);
/*     */       
/* 854 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 855 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 856 */         AttributeModifier attributemodifier = 
/* 857 */           SharedMonsterAttributes.readAttributeModifierFromNBT(nbttagcompound);
/*     */         
/* 859 */         if (attributemodifier != null && attributemodifier.getID().getLeastSignificantBits() != 0L && 
/* 860 */           attributemodifier.getID().getMostSignificantBits() != 0L) {
/* 861 */           hashMultimap.put(nbttagcompound.getString("AttributeName"), attributemodifier);
/*     */         }
/*     */       } 
/*     */     } else {
/* 865 */       multimap = getItem().getItemAttributeModifiers();
/*     */     } 
/*     */     
/* 868 */     return multimap;
/*     */   }
/*     */   
/*     */   public void setItem(Item newItem) {
/* 872 */     this.item = newItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getChatComponent() {
/* 880 */     ChatComponentText chatcomponenttext = new ChatComponentText(getDisplayName());
/*     */     
/* 882 */     if (hasDisplayName()) {
/* 883 */       chatcomponenttext.getChatStyle().setItalic(Boolean.valueOf(true));
/*     */     }
/*     */     
/* 886 */     IChatComponent ichatcomponent = (new ChatComponentText("[")).appendSibling((IChatComponent)chatcomponenttext).appendText("]");
/*     */     
/* 888 */     if (this.item != null) {
/* 889 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 890 */       writeToNBT(nbttagcompound);
/* 891 */       ichatcomponent.getChatStyle().setChatHoverEvent(
/* 892 */           new HoverEvent(HoverEvent.Action.SHOW_ITEM, (IChatComponent)new ChatComponentText(nbttagcompound.toString())));
/* 893 */       ichatcomponent.getChatStyle().setColor((getRarity()).rarityColor);
/*     */     } 
/*     */     
/* 896 */     return ichatcomponent;
/*     */   }
/*     */   
/*     */   public boolean canDestroy(Block blockIn) {
/* 900 */     if (blockIn == this.canDestroyCacheBlock) {
/* 901 */       return this.canDestroyCacheResult;
/*     */     }
/* 903 */     this.canDestroyCacheBlock = blockIn;
/*     */     
/* 905 */     if (hasTagCompound() && this.stackTagCompound.hasKey("CanDestroy", 9)) {
/* 906 */       NBTTagList nbttaglist = this.stackTagCompound.getTagList("CanDestroy", 8);
/*     */       
/* 908 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 909 */         Block block = Block.getBlockFromName(nbttaglist.getStringTagAt(i));
/*     */         
/* 911 */         if (block == blockIn) {
/* 912 */           this.canDestroyCacheResult = true;
/* 913 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 918 */     this.canDestroyCacheResult = false;
/* 919 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceOn(Block blockIn) {
/* 924 */     if (blockIn == this.canPlaceOnCacheBlock) {
/* 925 */       return this.canPlaceOnCacheResult;
/*     */     }
/* 927 */     this.canPlaceOnCacheBlock = blockIn;
/*     */     
/* 929 */     if (hasTagCompound() && this.stackTagCompound.hasKey("CanPlaceOn", 9)) {
/* 930 */       NBTTagList nbttaglist = this.stackTagCompound.getTagList("CanPlaceOn", 8);
/*     */       
/* 932 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 933 */         Block block = Block.getBlockFromName(nbttaglist.getStringTagAt(i));
/*     */         
/* 935 */         if (block == blockIn) {
/* 936 */           this.canPlaceOnCacheResult = true;
/* 937 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 942 */     this.canPlaceOnCacheResult = false;
/* 943 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAttackDamage() {
/* 949 */     if (!(getItem() instanceof ItemSword) && !(getItem() instanceof ItemTool))
/*     */     {
/* 951 */       return 0.0F;
/*     */     }
/*     */ 
/*     */     
/* 955 */     Multimap<String, AttributeModifier> multimap = getAttributeModifiers();
/*     */     
/* 957 */     if (!multimap.isEmpty()) {
/*     */       
/* 959 */       Iterator<Map.Entry> iterator = multimap.entries().iterator();
/*     */       
/* 961 */       if (iterator.hasNext()) {
/*     */         double damage;
/*     */ 
/*     */         
/* 965 */         Map.Entry entry = iterator.next();
/* 966 */         AttributeModifier attributeModifier = (AttributeModifier)entry.getValue();
/*     */         
/* 968 */         if (attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2) {
/*     */           
/* 970 */           damage = attributeModifier.getAmount();
/*     */         }
/*     */         else {
/*     */           
/* 974 */           damage = attributeModifier.getAmount() * 100.0D;
/*     */         } 
/*     */ 
/*     */         
/* 978 */         if (attributeModifier.getAmount() > 1.0D) {
/* 979 */           return 1.0F + (float)damage;
/*     */         }
/* 981 */         return 1.0F;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 987 */     return 1.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\ItemStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */