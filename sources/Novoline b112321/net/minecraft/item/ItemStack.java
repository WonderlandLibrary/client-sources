package net.minecraft.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;
import net.adM;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEvent$Action;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public final class ItemStack {
   public static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.###");
   public int stackSize;
   public int animationsToGo;
   private Item item;
   private NBTTagCompound stackTagCompound;
   private int itemDamage;
   private EntityItemFrame itemFrame;
   private Block canDestroyCacheBlock;
   private boolean canDestroyCacheResult;
   private Block canPlaceOnCacheBlock;
   private boolean canPlaceOnCacheResult;

   public ItemStack(Block var1) {
      this((Block)var1, 1);
   }

   public ItemStack(Block var1, int var2) {
      this((Block)var1, var2, 0);
   }

   public ItemStack(Block var1, int var2, int var3) {
      this(Item.getItemFromBlock(var1), var2, var3);
   }

   public ItemStack(Item var1) {
      this((Item)var1, 1);
   }

   public ItemStack(Item var1, int var2) {
      this((Item)var1, var2, 0);
   }

   public ItemStack(Item var1, int var2, int var3) {
      this.canDestroyCacheBlock = null;
      this.canDestroyCacheResult = false;
      this.canPlaceOnCacheBlock = null;
      this.canPlaceOnCacheResult = false;
      this.item = var1;
      this.stackSize = var2;
      this.itemDamage = var3;
      if(this.itemDamage < 0) {
         this.itemDamage = 0;
      }

   }

   public static ItemStack loadItemStackFromNBT(NBTTagCompound var0) {
      ItemStack var1 = new ItemStack();
      var1.readFromNBT(var0);
      return var1.item != null?var1:null;
   }

   private ItemStack() {
      this.canDestroyCacheBlock = null;
      this.canDestroyCacheResult = false;
      this.canPlaceOnCacheBlock = null;
      this.canPlaceOnCacheResult = false;
   }

   public ItemStack splitStack(int var1) {
      ItemStack var2 = new ItemStack(this.item, var1, this.itemDamage);
      if(this.stackTagCompound != null) {
         var2.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
      }

      this.stackSize -= var1;
      return var2;
   }

   public Item getItem() {
      return this.item;
   }

   public boolean onItemUse(EntityPlayer var1, World var2, BlockPos var3, EnumFacing var4, float var5, float var6, float var7) {
      boolean var8 = adM.a(this.item, this, var1, var2, var3, var4, var5, var6, var7);
      var1.triggerAchievement(StatList.objectUseStats[Item.b(this.item)]);
      return var8;
   }

   public float getStrVsBlock(Block var1) {
      return this.item.getStrVsBlock(this, var1);
   }

   public ItemStack useItemRightClick(World var1, EntityPlayer var2) {
      return this.item.onItemRightClick(this, var1, var2);
   }

   public ItemStack onItemUseFinish(World var1, EntityPlayer var2) {
      return this.item.onItemUseFinish(this, var1, var2);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound var1) {
      ResourceLocation var2 = (ResourceLocation)Item.itemRegistry.getNameForObject(this.item);
      var1.setString("id", "minecraft:air");
      var1.setByte("Count", (byte)this.stackSize);
      var1.setShort("Damage", (short)this.itemDamage);
      if(this.stackTagCompound != null) {
         var1.setTag("tag", this.stackTagCompound);
      }

      return var1;
   }

   public void readFromNBT(NBTTagCompound var1) {
      if(var1.hasKey("id", 8)) {
         this.item = Item.getByNameOrId(var1.getString("id"));
      } else {
         this.item = Item.getItemById(var1.getShort("id"));
      }

      this.stackSize = var1.getByte("Count");
      this.itemDamage = var1.getShort("Damage");
      if(this.itemDamage < 0) {
         this.itemDamage = 0;
      }

      if(var1.hasKey("tag", 10)) {
         this.stackTagCompound = var1.getCompoundTag("tag");
         if(this.item != null) {
            this.item.updateItemStackNBT(this.stackTagCompound);
         }
      }

   }

   public int getMaxStackSize() {
      return this.item.getItemStackLimit();
   }

   public boolean isStackable() {
      return this.getMaxStackSize() > 1 && (!this.isItemStackDamageable() || !this.isItemDamaged());
   }

   public boolean isItemStackDamageable() {
      return this.item != null && this.item.getMaxDamage() > 0 && (!this.hasTagCompound() || !this.stackTagCompound.getBoolean("Unbreakable"));
   }

   public boolean getHasSubtypes() {
      return this.item.getHasSubtypes();
   }

   public boolean isItemDamaged() {
      return this.isItemStackDamageable() && this.itemDamage > 0;
   }

   public int getItemDamage() {
      return this.itemDamage;
   }

   public int getMetadata() {
      return this.itemDamage;
   }

   public void setItemDamage(int var1) {
      this.itemDamage = var1;
      if(this.itemDamage < 0) {
         this.itemDamage = 0;
      }

   }

   public int getMaxDamage() {
      return this.item.getMaxDamage();
   }

   public boolean attemptDamageItem(int var1, Random var2) {
      if(!this.isItemStackDamageable()) {
         return false;
      } else {
         int var3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, this);
         int var4 = 0;

         for(int var5 = 0; var5 < var1; ++var5) {
            if(EnchantmentDurability.negateDamage(this, var3, var2)) {
               ++var4;
            }
         }

         var1 = var1 - var4;
         return false;
      }
   }

   public void damageItem(int var1, EntityLivingBase var2) {
      if((!(var2 instanceof EntityPlayer) || !((EntityPlayer)var2).abilities.isCreative()) && this.isItemStackDamageable() && this.attemptDamageItem(var1, var2.getRNG())) {
         var2.renderBrokenItemStack(this);
         --this.stackSize;
         if(var2 instanceof EntityPlayer) {
            EntityPlayer var3 = (EntityPlayer)var2;
            var3.triggerAchievement(StatList.objectBreakStats[Item.b(this.item)]);
            if(this.stackSize == 0 && this.item instanceof ItemBow) {
               var3.destroyCurrentEquippedItem();
            }
         }

         if(this.stackSize < 0) {
            this.stackSize = 0;
         }

         this.itemDamage = 0;
      }

   }

   public void hitEntity(EntityLivingBase var1, EntityPlayer var2) {
      boolean var3 = this.item.hitEntity(this, var1, var2);
      var2.triggerAchievement(StatList.objectUseStats[Item.b(this.item)]);
   }

   public void onBlockDestroyed(World var1, Block var2, BlockPos var3, EntityPlayer var4) {
      boolean var5 = this.item.onBlockDestroyed(this, var1, var2, var3, var4);
      var4.triggerAchievement(StatList.objectUseStats[Item.b(this.item)]);
   }

   public boolean canHarvestBlock(Block var1) {
      return this.item.canHarvestBlock(var1);
   }

   public boolean interactWithEntity(EntityPlayer var1, EntityLivingBase var2) {
      return this.item.itemInteractionForEntity(this, var1, var2);
   }

   public ItemStack copy() {
      ItemStack var1 = new ItemStack(this.item, this.stackSize, this.itemDamage);
      if(this.stackTagCompound != null) {
         var1.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
      }

      return var1;
   }

   public static boolean areItemStackTagsEqual(ItemStack var0, ItemStack var1) {
      return (var0.stackTagCompound != null || var1.stackTagCompound == null) && (var0.stackTagCompound == null || var0.stackTagCompound.equals(var1.stackTagCompound));
   }

   public static boolean areItemStacksEqual(ItemStack var0, ItemStack var1) {
      return var0.isItemStackEqual(var1);
   }

   private boolean isItemStackEqual(ItemStack var1) {
      return this.stackSize == var1.stackSize && this.item == var1.item && this.itemDamage == var1.itemDamage && (this.stackTagCompound != null || var1.stackTagCompound == null) && (this.stackTagCompound == null || this.stackTagCompound.equals(var1.stackTagCompound));
   }

   public static boolean areItemsEqual(ItemStack var0, ItemStack var1) {
      return var0.isItemEqual(var1);
   }

   public boolean isItemEqual(ItemStack var1) {
      return this.item == var1.item && this.itemDamage == var1.itemDamage;
   }

   public String getUnlocalizedName() {
      return this.item.getUnlocalizedName(this);
   }

   public static ItemStack d(ItemStack var0) {
      return null;
   }

   public String toString() {
      return this.stackSize + "x" + this.item.getUnlocalizedName() + "@" + this.itemDamage;
   }

   public void updateAnimation(World var1, Entity var2, int var3, boolean var4) {
      if(this.animationsToGo > 0) {
         --this.animationsToGo;
      }

      this.item.onUpdate(this, var1, var2, var3, var4);
   }

   public void onCrafting(World var1, EntityPlayer var2, int var3) {
      var2.addStat(StatList.objectCraftStats[Item.b(this.item)], var3);
      this.item.onCreated(this, var1, var2);
   }

   public boolean getIsItemStackEqual(ItemStack var1) {
      return this.isItemStackEqual(var1);
   }

   public int getMaxItemUseDuration() {
      return this.item.getMaxItemUseDuration(this);
   }

   public EnumAction getItemUseAction() {
      return this.item.getItemUseAction(this);
   }

   public void onPlayerStoppedUsing(World var1, EntityPlayer var2, int var3) {
      this.item.onPlayerStoppedUsing(this, var1, var2, var3);
   }

   public boolean hasTagCompound() {
      return this.stackTagCompound != null;
   }

   public NBTTagCompound getTagCompound() {
      return this.stackTagCompound;
   }

   public NBTTagCompound getSubCompound(String var1, boolean var2) {
      if(this.stackTagCompound != null && this.stackTagCompound.hasKey(var1, 10)) {
         return this.stackTagCompound.getCompoundTag(var1);
      } else {
         NBTTagCompound var3 = new NBTTagCompound();
         this.setTagInfo(var1, var3);
         return var3;
      }
   }

   public NBTTagList getEnchantmentTagList() {
      return this.stackTagCompound == null?null:this.stackTagCompound.getTagList("ench", 10);
   }

   public void setTagCompound(NBTTagCompound var1) {
      this.stackTagCompound = var1;
   }

   public String getDisplayName() {
      String var1 = this.item.getItemStackDisplayName(this);
      if(this.stackTagCompound != null && this.stackTagCompound.hasKey("display", 10)) {
         NBTTagCompound var2 = this.stackTagCompound.getCompoundTag("display");
         if(var2.hasKey("Name", 8)) {
            var1 = var2.getString("Name");
         }
      }

      return var1;
   }

   public ItemStack setStackDisplayName(String var1) {
      if(this.stackTagCompound == null) {
         this.stackTagCompound = new NBTTagCompound();
      }

      if(!this.stackTagCompound.hasKey("display", 10)) {
         this.stackTagCompound.setTag("display", new NBTTagCompound());
      }

      this.stackTagCompound.getCompoundTag("display").setString("Name", var1);
      return this;
   }

   public void clearCustomName() {
      if(this.stackTagCompound != null && this.stackTagCompound.hasKey("display", 10)) {
         NBTTagCompound var1 = this.stackTagCompound.getCompoundTag("display");
         var1.removeTag("Name");
         if(var1.hasNoTags()) {
            this.stackTagCompound.removeTag("display");
            if(this.stackTagCompound.hasNoTags()) {
               this.setTagCompound((NBTTagCompound)null);
            }
         }
      }

   }

   public boolean hasDisplayName() {
      return this.stackTagCompound != null && this.stackTagCompound.hasKey("display", 10) && this.stackTagCompound.getCompoundTag("display").hasKey("Name", 8);
   }

   public List getTooltip(EntityPlayer var1, boolean var2) {
      ArrayList var3 = Lists.newArrayList();
      String var4 = this.getDisplayName();
      if(this.hasDisplayName()) {
         var4 = EnumChatFormatting.ITALIC + var4;
      }

      var4 = var4 + EnumChatFormatting.RESET;
      String var5 = "";
      if(!var4.isEmpty()) {
         var4 = var4 + " (";
         var5 = ")";
      }

      int var6 = Item.b(this.item);
      if(this.getHasSubtypes()) {
         var4 = var4 + String.format("#%04d/%d%s", new Object[]{Integer.valueOf(var6), Integer.valueOf(this.itemDamage), var5});
      } else {
         var4 = var4 + String.format("#%04d%s", new Object[]{Integer.valueOf(var6), var5});
      }

      var3.add(var4);
      int var16 = 0;
      if(this.hasTagCompound() && this.stackTagCompound.hasKey("HideFlags", 99)) {
         var16 = this.stackTagCompound.getInteger("HideFlags");
      }

      if((var16 & 32) == 0) {
         this.item.addInformation(this, var1, var3, var2);
      }

      if(this.hasTagCompound()) {
         if((var16 & 1) == 0) {
            NBTTagList var17 = this.getEnchantmentTagList();

            for(int var7 = 0; var7 < var17.tagCount(); ++var7) {
               short var8 = var17.getCompoundTagAt(var7).getShort("id");
               short var9 = var17.getCompoundTagAt(var7).getShort("lvl");
               if(Enchantment.getEnchantmentById(var8) != null) {
                  var3.add(Enchantment.getEnchantmentById(var8).getTranslatedName(var9));
               }
            }
         }

         if(this.stackTagCompound.hasKey("display", 10)) {
            NBTTagCompound var18 = this.stackTagCompound.getCompoundTag("display");
            if(var18.hasKey("color", 3)) {
               var3.add("Color: #" + Integer.toHexString(var18.getInteger("color")).toUpperCase());
            }

            if(var18.getTagId("Lore") == 9) {
               NBTTagList var20 = var18.getTagList("Lore", 8);
               if(var20.tagCount() > 0) {
                  for(int var24 = 0; var24 < var20.tagCount(); ++var24) {
                     var3.add(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.ITALIC + var20.getStringTagAt(var24));
                  }
               }
            }
         }
      }

      Multimap var19 = this.getAttributeModifiers();
      if(!var19.isEmpty() && (var16 & 2) == 0) {
         var3.add("");

         for(Entry var25 : var19.entries()) {
            AttributeModifier var28 = (AttributeModifier)var25.getValue();
            double var10 = var28.getAmount();
            if(var28.getID() == Item.itemModifierUUID) {
               var10 += (double)EnchantmentHelper.func_152377_a(this, EnumCreatureAttribute.UNDEFINED);
            }

            double var12;
            if(var28.getOperation() != 1 && var28.getOperation() != 2) {
               var12 = var10;
            } else {
               var12 = var10 * 100.0D;
            }

            if(var10 > 0.0D) {
               var3.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + var28.getOperation(), new Object[]{DECIMALFORMAT.format(var12), StatCollector.translateToLocal("attribute.name." + (String)var25.getKey())}));
            } else if(var10 < 0.0D) {
               var12 = var12 * -1.0D;
               var3.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + var28.getOperation(), new Object[]{DECIMALFORMAT.format(var12), StatCollector.translateToLocal("attribute.name." + (String)var25.getKey())}));
            }
         }
      }

      if(this.hasTagCompound() && this.stackTagCompound.getBoolean("Unbreakable") && (var16 & 4) == 0) {
         var3.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("item.unbreakable"));
      }

      if(this.hasTagCompound() && this.stackTagCompound.hasKey("CanDestroy", 9) && (var16 & 8) == 0) {
         NBTTagList var22 = this.stackTagCompound.getTagList("CanDestroy", 8);
         if(var22.tagCount() > 0) {
            var3.add("");
            var3.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("item.canBreak"));

            for(int var26 = 0; var26 < var22.tagCount(); ++var26) {
               Block var29 = Block.getBlockFromName(var22.getStringTagAt(var26));
               var3.add(EnumChatFormatting.DARK_GRAY + var29.getLocalizedName());
            }
         }
      }

      if(this.hasTagCompound() && this.stackTagCompound.hasKey("CanPlaceOn", 9) && (var16 & 16) == 0) {
         NBTTagList var23 = this.stackTagCompound.getTagList("CanPlaceOn", 8);
         if(var23.tagCount() > 0) {
            var3.add("");
            var3.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("item.canPlace"));

            for(int var27 = 0; var27 < var23.tagCount(); ++var27) {
               Block var30 = Block.getBlockFromName(var23.getStringTagAt(var27));
               var3.add(EnumChatFormatting.DARK_GRAY + var30.getLocalizedName());
            }
         }
      }

      if(this.isItemDamaged()) {
         var3.add("Durability: " + (this.getMaxDamage() - this.itemDamage) + " / " + this.getMaxDamage());
      }

      var3.add(EnumChatFormatting.DARK_GRAY + ((ResourceLocation)Item.itemRegistry.getNameForObject(this.item)).toString());
      if(this.hasTagCompound()) {
         var3.add(EnumChatFormatting.DARK_GRAY + "NBT: " + this.stackTagCompound.getKeySet().size() + " tag(s)");
      }

      return var3;
   }

   public boolean hasEffect() {
      return this.item.hasEffect(this);
   }

   public EnumRarity getRarity() {
      return this.item.getRarity(this);
   }

   public boolean isItemEnchantable() {
      return this.item.isItemTool(this) && !this.isItemEnchanted();
   }

   public void addEnchantment(Enchantment var1, int var2) {
      if(this.stackTagCompound == null) {
         this.setTagCompound(new NBTTagCompound());
      }

      if(!this.stackTagCompound.hasKey("ench", 9)) {
         this.stackTagCompound.setTag("ench", new NBTTagList());
      }

      NBTTagList var3 = this.stackTagCompound.getTagList("ench", 10);
      NBTTagCompound var4 = new NBTTagCompound();
      var4.setShort("id", (short)var1.effectId);
      var4.setShort("lvl", (short)((byte)var2));
      var3.appendTag(var4);
   }

   public boolean isItemEnchanted() {
      return this.stackTagCompound != null && this.stackTagCompound.hasKey("ench", 9);
   }

   public void setTagInfo(String var1, NBTBase var2) {
      if(this.stackTagCompound == null) {
         this.setTagCompound(new NBTTagCompound());
      }

      this.stackTagCompound.setTag(var1, var2);
   }

   public boolean canEditBlocks() {
      return this.item.canItemEditBlocks();
   }

   public boolean isOnItemFrame() {
      return this.itemFrame != null;
   }

   public void setItemFrame(EntityItemFrame var1) {
      this.itemFrame = var1;
   }

   public EntityItemFrame getItemFrame() {
      return this.itemFrame;
   }

   public int getRepairCost() {
      return this.hasTagCompound() && this.stackTagCompound.hasKey("RepairCost", 3)?this.stackTagCompound.getInteger("RepairCost"):0;
   }

   public void setRepairCost(int var1) {
      if(!this.hasTagCompound()) {
         this.stackTagCompound = new NBTTagCompound();
      }

      this.stackTagCompound.setInteger("RepairCost", var1);
   }

   public Multimap getAttributeModifiers() {
      Object var1;
      if(this.hasTagCompound() && this.stackTagCompound.hasKey("AttributeModifiers", 9)) {
         var1 = HashMultimap.create();
         NBTTagList var2 = this.stackTagCompound.getTagList("AttributeModifiers", 10);

         for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            AttributeModifier var5 = SharedMonsterAttributes.readAttributeModifierFromNBT(var4);
            if(var5.getID().getLeastSignificantBits() != 0L && var5.getID().getMostSignificantBits() != 0L) {
               ((Multimap)var1).put(var4.getString("AttributeName"), var5);
            }
         }
      } else {
         var1 = this.item.getItemAttributeModifiers();
      }

      return (Multimap)var1;
   }

   public void setItem(Item var1) {
      this.item = var1;
   }

   public IChatComponent getChatComponent() {
      ChatComponentText var1 = new ChatComponentText(this.getDisplayName());
      if(this.hasDisplayName()) {
         var1.getChatStyle().setItalic(Boolean.TRUE);
      }

      IChatComponent var2 = (new ChatComponentText("[")).appendSibling(var1).appendText("]");
      if(this.item != null) {
         NBTTagCompound var3 = new NBTTagCompound();
         this.writeToNBT(var3);
         var2.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent$Action.SHOW_ITEM, new ChatComponentText(var3.toString())));
         var2.getChatStyle().setColor(this.getRarity().rarityColor);
      }

      return var2;
   }

   public boolean canDestroy(Block var1) {
      if(var1 == this.canDestroyCacheBlock) {
         return this.canDestroyCacheResult;
      } else {
         this.canDestroyCacheBlock = var1;
         if(this.hasTagCompound() && this.stackTagCompound.hasKey("CanDestroy", 9)) {
            NBTTagList var2 = this.stackTagCompound.getTagList("CanDestroy", 8);

            for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
               Block var4 = Block.getBlockFromName(var2.getStringTagAt(var3));
               if(var4 == var1) {
                  this.canDestroyCacheResult = true;
                  return true;
               }
            }
         }

         this.canDestroyCacheResult = false;
         return false;
      }
   }

   public boolean canPlaceOn(Block var1) {
      if(var1 == this.canPlaceOnCacheBlock) {
         return this.canPlaceOnCacheResult;
      } else {
         this.canPlaceOnCacheBlock = var1;
         if(this.hasTagCompound() && this.stackTagCompound.hasKey("CanPlaceOn", 9)) {
            NBTTagList var2 = this.stackTagCompound.getTagList("CanPlaceOn", 8);

            for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
               Block var4 = Block.getBlockFromName(var2.getStringTagAt(var3));
               if(var4 == var1) {
                  this.canPlaceOnCacheResult = true;
                  return true;
               }
            }
         }

         this.canPlaceOnCacheResult = false;
         return false;
      }
   }
}
