package net.minecraft.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;
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
import net.minecraft.init.Items;
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
   private NBTTagCompound stackTagCompound;
   private int itemDamage;
   private static final String __OBFID = "CL_00000043";
   private boolean canDestroyCacheResult;
   private EntityItemFrame itemFrame;
   public int animationsToGo;
   private Block canPlaceOnCacheBlock;
   private Block canDestroyCacheBlock;
   public static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.###");
   private boolean canPlaceOnCacheResult;
   public int stackSize;
   private Item item;

   public ItemStack(Block var1, int var2, int var3) {
      this(Item.getItemFromBlock(var1), var2, var3);
   }

   public NBTTagCompound writeToNBT(NBTTagCompound var1) {
      ResourceLocation var2 = (ResourceLocation)Item.itemRegistry.getNameForObject(this.item);
      var1.setString("id", var2 == null ? "minecraft:air" : var2.toString());
      var1.setByte("Count", (byte)this.stackSize);
      var1.setShort("Damage", (short)this.itemDamage);
      if (this.stackTagCompound != null) {
         var1.setTag("tag", this.stackTagCompound);
      }

      return var1;
   }

   public EnumRarity getRarity() {
      return this.getItem().getRarity(this);
   }

   public int getItemDamage() {
      return this.itemDamage;
   }

   public boolean isItemDamaged() {
      return this.isItemStackDamageable() && this.itemDamage > 0;
   }

   public boolean isItemEnchanted() {
      return this.stackTagCompound != null && this.stackTagCompound.hasKey("ench", 9);
   }

   public void setItemDamage(int var1) {
      this.itemDamage = var1;
      if (this.itemDamage < 0) {
         this.itemDamage = 0;
      }

   }

   public boolean canDestroy(Block var1) {
      if (var1 == this.canDestroyCacheBlock) {
         return this.canDestroyCacheResult;
      } else {
         this.canDestroyCacheBlock = var1;
         if (this.hasTagCompound() && this.stackTagCompound.hasKey("CanDestroy", 9)) {
            NBTTagList var2 = this.stackTagCompound.getTagList("CanDestroy", 8);

            for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
               Block var4 = Block.getBlockFromName(var2.getStringTagAt(var3));
               if (var4 == var1) {
                  this.canDestroyCacheResult = true;
                  return true;
               }
            }
         }

         this.canDestroyCacheResult = false;
         return false;
      }
   }

   public void hitEntity(EntityLivingBase var1, EntityPlayer var2) {
      boolean var3 = this.item.hitEntity(this, var1, var2);
      if (var3) {
         var2.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
      }

   }

   public boolean interactWithEntity(EntityPlayer var1, EntityLivingBase var2) {
      return this.item.itemInteractionForEntity(this, var1, var2);
   }

   public ItemStack useItemRightClick(World var1, EntityPlayer var2) {
      return this.getItem().onItemRightClick(this, var1, var2);
   }

   public boolean getHasSubtypes() {
      return this.item.getHasSubtypes();
   }

   public boolean onItemUse(EntityPlayer var1, World var2, BlockPos var3, EnumFacing var4, float var5, float var6, float var7) {
      boolean var8 = this.getItem().onItemUse(this, var1, var2, var3, var4, var5, var6, var7);
      if (var8) {
         var1.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
      }

      return var8;
   }

   public boolean isItemStackDamageable() {
      return this.item == null ? false : (this.item.getMaxDamage() <= 0 ? false : !this.hasTagCompound() || !this.getTagCompound().getBoolean("Unbreakable"));
   }

   public ItemStack splitStack(int var1) {
      ItemStack var2 = new ItemStack(this.item, var1, this.itemDamage);
      if (this.stackTagCompound != null) {
         var2.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
      }

      this.stackSize -= var1;
      return var2;
   }

   public boolean hasDisplayName() {
      return this.stackTagCompound == null ? false : (!this.stackTagCompound.hasKey("display", 10) ? false : this.stackTagCompound.getCompoundTag("display").hasKey("Name", 8));
   }

   public void addEnchantment(Enchantment var1, int var2) {
      if (this.stackTagCompound == null) {
         this.setTagCompound(new NBTTagCompound());
      }

      if (!this.stackTagCompound.hasKey("ench", 9)) {
         this.stackTagCompound.setTag("ench", new NBTTagList());
      }

      NBTTagList var3 = this.stackTagCompound.getTagList("ench", 10);
      NBTTagCompound var4 = new NBTTagCompound();
      var4.setShort("id", (short)var1.effectId);
      var4.setShort("lvl", (short)((byte)var2));
      var3.appendTag(var4);
   }

   public Item getItem() {
      return this.item;
   }

   private boolean isItemStackEqual(ItemStack var1) {
      return this.stackSize != var1.stackSize ? false : (this.item != var1.item ? false : (this.itemDamage != var1.itemDamage ? false : (this.stackTagCompound == null && var1.stackTagCompound != null ? false : this.stackTagCompound == null || this.stackTagCompound.equals(var1.stackTagCompound))));
   }

   public static ItemStack copyItemStack(ItemStack var0) {
      return var0 == null ? null : var0.copy();
   }

   public void setRepairCost(int var1) {
      if (!this.hasTagCompound()) {
         this.stackTagCompound = new NBTTagCompound();
      }

      this.stackTagCompound.setInteger("RepairCost", var1);
   }

   public EntityItemFrame getItemFrame() {
      return this.itemFrame;
   }

   public int getMaxDamage() {
      return this.item.getMaxDamage();
   }

   public int getMetadata() {
      return this.itemDamage;
   }

   public void onPlayerStoppedUsing(World var1, EntityPlayer var2, int var3) {
      this.getItem().onPlayerStoppedUsing(this, var1, var2, var3);
   }

   public boolean isItemEnchantable() {
      return !this.getItem().isItemTool(this) ? false : !this.isItemEnchanted();
   }

   public boolean canHarvestBlock(Block var1) {
      return this.item.canHarvestBlock(var1);
   }

   public void clearCustomName() {
      if (this.stackTagCompound != null && this.stackTagCompound.hasKey("display", 10)) {
         NBTTagCompound var1 = this.stackTagCompound.getCompoundTag("display");
         var1.removeTag("Name");
         if (var1.hasNoTags()) {
            this.stackTagCompound.removeTag("display");
            if (this.stackTagCompound.hasNoTags()) {
               this.setTagCompound((NBTTagCompound)null);
            }
         }
      }

   }

   public ItemStack onItemUseFinish(World var1, EntityPlayer var2) {
      return this.getItem().onItemUseFinish(this, var1, var2);
   }

   public static boolean areItemStacksEqual(ItemStack var0, ItemStack var1) {
      return var0 == null && var1 == null ? true : (var0 != null && var1 != null ? var0.isItemStackEqual(var1) : false);
   }

   public ItemStack copy() {
      ItemStack var1 = new ItemStack(this.item, this.stackSize, this.itemDamage);
      if (this.stackTagCompound != null) {
         var1.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
      }

      return var1;
   }

   public ItemStack(Item var1) {
      this((Item)var1, 1);
   }

   public NBTTagList getEnchantmentTagList() {
      return this.stackTagCompound == null ? null : this.stackTagCompound.getTagList("ench", 10);
   }

   public boolean hasEffect() {
      return this.getItem().hasEffect(this);
   }

   public Multimap getAttributeModifiers() {
      Object var1;
      if (this.hasTagCompound() && this.stackTagCompound.hasKey("AttributeModifiers", 9)) {
         var1 = HashMultimap.create();
         NBTTagList var2 = this.stackTagCompound.getTagList("AttributeModifiers", 10);

         for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            AttributeModifier var5 = SharedMonsterAttributes.readAttributeModifierFromNBT(var4);
            if (var5 != null && var5.getID().getLeastSignificantBits() != 0L && var5.getID().getMostSignificantBits() != 0L) {
               ((Multimap)var1).put(var4.getString("AttributeName"), var5);
            }
         }
      } else {
         var1 = this.getItem().getItemAttributeModifiers();
      }

      return (Multimap)var1;
   }

   public String getUnlocalizedName() {
      return this.item.getUnlocalizedName(this);
   }

   public int getRepairCost() {
      return this.hasTagCompound() && this.stackTagCompound.hasKey("RepairCost", 3) ? this.stackTagCompound.getInteger("RepairCost") : 0;
   }

   public boolean canEditBlocks() {
      return this.getItem().canItemEditBlocks();
   }

   public boolean canPlaceOn(Block var1) {
      if (var1 == this.canPlaceOnCacheBlock) {
         return this.canPlaceOnCacheResult;
      } else {
         this.canPlaceOnCacheBlock = var1;
         if (this.hasTagCompound() && this.stackTagCompound.hasKey("CanPlaceOn", 9)) {
            NBTTagList var2 = this.stackTagCompound.getTagList("CanPlaceOn", 8);

            for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
               Block var4 = Block.getBlockFromName(var2.getStringTagAt(var3));
               if (var4 == var1) {
                  this.canPlaceOnCacheResult = true;
                  return true;
               }
            }
         }

         this.canPlaceOnCacheResult = false;
         return false;
      }
   }

   public void updateAnimation(World var1, Entity var2, int var3, boolean var4) {
      if (this.animationsToGo > 0) {
         --this.animationsToGo;
      }

      this.item.onUpdate(this, var1, var2, var3, var4);
   }

   public void setItem(Item var1) {
      this.item = var1;
   }

   public void onCrafting(World var1, EntityPlayer var2, int var3) {
      var2.addStat(StatList.objectCraftStats[Item.getIdFromItem(this.item)], var3);
      this.item.onCreated(this, var1, var2);
   }

   public List getTooltip(EntityPlayer var1, boolean var2) {
      ArrayList var3 = Lists.newArrayList();
      String var4 = this.getDisplayName();
      if (this.hasDisplayName()) {
         var4 = String.valueOf((new StringBuilder()).append(EnumChatFormatting.ITALIC).append(var4));
      }

      var4 = String.valueOf((new StringBuilder(String.valueOf(var4))).append(EnumChatFormatting.RESET));
      if (var2) {
         String var5 = "";
         if (var4.length() > 0) {
            var4 = String.valueOf((new StringBuilder(String.valueOf(var4))).append(" ("));
            var5 = ")";
         }

         int var6 = Item.getIdFromItem(this.item);
         if (this.getHasSubtypes()) {
            var4 = String.valueOf((new StringBuilder(String.valueOf(var4))).append(String.format("#%04d/%d%s", var6, this.itemDamage, var5)));
         } else {
            var4 = String.valueOf((new StringBuilder(String.valueOf(var4))).append(String.format("#%04d%s", var6, var5)));
         }
      } else if (!this.hasDisplayName() && this.item == Items.filled_map) {
         var4 = String.valueOf((new StringBuilder(String.valueOf(var4))).append(" #").append(this.itemDamage));
      }

      var3.add(var4);
      int var16 = 0;
      if (this.hasTagCompound() && this.stackTagCompound.hasKey("HideFlags", 99)) {
         var16 = this.stackTagCompound.getInteger("HideFlags");
      }

      if ((var16 & 32) == 0) {
         this.item.addInformation(this, var1, var3, var2);
      }

      int var7;
      NBTTagList var17;
      if (this.hasTagCompound()) {
         if ((var16 & 1) == 0) {
            NBTTagList var8 = this.getEnchantmentTagList();
            if (var8 != null) {
               for(int var9 = 0; var9 < var8.tagCount(); ++var9) {
                  short var10 = var8.getCompoundTagAt(var9).getShort("id");
                  short var11 = var8.getCompoundTagAt(var9).getShort("lvl");
                  if (Enchantment.func_180306_c(var10) != null) {
                     var3.add(Enchantment.func_180306_c(var10).getTranslatedName(var11));
                  }
               }
            }
         }

         if (this.stackTagCompound.hasKey("display", 10)) {
            NBTTagCompound var18 = this.stackTagCompound.getCompoundTag("display");
            if (var18.hasKey("color", 3)) {
               if (var2) {
                  var3.add(String.valueOf((new StringBuilder("Color: #")).append(Integer.toHexString(var18.getInteger("color")).toUpperCase())));
               } else {
                  var3.add(String.valueOf((new StringBuilder()).append(EnumChatFormatting.ITALIC).append(StatCollector.translateToLocal("item.dyed"))));
               }
            }

            if (var18.getTagType("Lore") == 9) {
               var17 = var18.getTagList("Lore", 8);
               if (var17.tagCount() > 0) {
                  for(var7 = 0; var7 < var17.tagCount(); ++var7) {
                     var3.add(String.valueOf((new StringBuilder()).append(EnumChatFormatting.DARK_PURPLE).append(EnumChatFormatting.ITALIC).append(var17.getStringTagAt(var7))));
                  }
               }
            }
         }
      }

      Multimap var19 = this.getAttributeModifiers();
      if (!var19.isEmpty() && (var16 & 2) == 0) {
         var3.add("");
         Iterator var20 = var19.entries().iterator();

         while(var20.hasNext()) {
            Entry var22 = (Entry)var20.next();
            AttributeModifier var23 = (AttributeModifier)var22.getValue();
            double var12 = var23.getAmount();
            if (var23.getID() == Item.itemModifierUUID) {
               var12 += (double)EnchantmentHelper.func_152377_a(this, EnumCreatureAttribute.UNDEFINED);
            }

            double var14;
            if (var23.getOperation() != 1 && var23.getOperation() != 2) {
               var14 = var12;
            } else {
               var14 = var12 * 100.0D;
            }

            if (var12 > 0.0D) {
               var3.add(String.valueOf((new StringBuilder()).append(EnumChatFormatting.BLUE).append(StatCollector.translateToLocalFormatted(String.valueOf((new StringBuilder("attribute.modifier.plus.")).append(var23.getOperation())), DECIMALFORMAT.format(var14), StatCollector.translateToLocal(String.valueOf((new StringBuilder("attribute.name.")).append((String)var22.getKey())))))));
            } else if (var12 < 0.0D) {
               var14 *= -1.0D;
               var3.add(String.valueOf((new StringBuilder()).append(EnumChatFormatting.RED).append(StatCollector.translateToLocalFormatted(String.valueOf((new StringBuilder("attribute.modifier.take.")).append(var23.getOperation())), DECIMALFORMAT.format(var14), StatCollector.translateToLocal(String.valueOf((new StringBuilder("attribute.name.")).append((String)var22.getKey())))))));
            }
         }
      }

      if (this.hasTagCompound() && this.getTagCompound().getBoolean("Unbreakable") && (var16 & 4) == 0) {
         var3.add(String.valueOf((new StringBuilder()).append(EnumChatFormatting.BLUE).append(StatCollector.translateToLocal("item.unbreakable"))));
      }

      Block var21;
      if (this.hasTagCompound() && this.stackTagCompound.hasKey("CanDestroy", 9) && (var16 & 8) == 0) {
         var17 = this.stackTagCompound.getTagList("CanDestroy", 8);
         if (var17.tagCount() > 0) {
            var3.add("");
            var3.add(String.valueOf((new StringBuilder()).append(EnumChatFormatting.GRAY).append(StatCollector.translateToLocal("item.canBreak"))));

            for(var7 = 0; var7 < var17.tagCount(); ++var7) {
               var21 = Block.getBlockFromName(var17.getStringTagAt(var7));
               if (var21 != null) {
                  var3.add(String.valueOf((new StringBuilder()).append(EnumChatFormatting.DARK_GRAY).append(var21.getLocalizedName())));
               } else {
                  var3.add(String.valueOf((new StringBuilder()).append(EnumChatFormatting.DARK_GRAY).append("missingno")));
               }
            }
         }
      }

      if (this.hasTagCompound() && this.stackTagCompound.hasKey("CanPlaceOn", 9) && (var16 & 16) == 0) {
         var17 = this.stackTagCompound.getTagList("CanPlaceOn", 8);
         if (var17.tagCount() > 0) {
            var3.add("");
            var3.add(String.valueOf((new StringBuilder()).append(EnumChatFormatting.GRAY).append(StatCollector.translateToLocal("item.canPlace"))));

            for(var7 = 0; var7 < var17.tagCount(); ++var7) {
               var21 = Block.getBlockFromName(var17.getStringTagAt(var7));
               if (var21 != null) {
                  var3.add(String.valueOf((new StringBuilder()).append(EnumChatFormatting.DARK_GRAY).append(var21.getLocalizedName())));
               } else {
                  var3.add(String.valueOf((new StringBuilder()).append(EnumChatFormatting.DARK_GRAY).append("missingno")));
               }
            }
         }
      }

      if (var2) {
         if (this.isItemDamaged()) {
            var3.add(String.valueOf((new StringBuilder("Durability: ")).append(this.getMaxDamage() - this.getItemDamage()).append(" / ").append(this.getMaxDamage())));
         }

         var3.add(String.valueOf((new StringBuilder()).append(EnumChatFormatting.DARK_GRAY).append(((ResourceLocation)Item.itemRegistry.getNameForObject(this.item)).toString())));
         if (this.hasTagCompound()) {
            var3.add(String.valueOf((new StringBuilder()).append(EnumChatFormatting.DARK_GRAY).append("NBT: ").append(this.getTagCompound().getKeySet().size()).append(" tag(s)")));
         }
      }

      return var3;
   }

   public ItemStack(Item var1, int var2, int var3) {
      this.canDestroyCacheBlock = null;
      this.canDestroyCacheResult = false;
      this.canPlaceOnCacheBlock = null;
      this.canPlaceOnCacheResult = false;
      this.item = var1;
      this.stackSize = var2;
      this.itemDamage = var3;
      if (this.itemDamage < 0) {
         this.itemDamage = 0;
      }

   }

   public int getMaxStackSize() {
      return this.getItem().getItemStackLimit();
   }

   public boolean attemptDamageItem(int var1, Random var2) {
      if (!this.isItemStackDamageable()) {
         return false;
      } else {
         if (var1 > 0) {
            int var3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, this);
            int var4 = 0;

            for(int var5 = 0; var3 > 0 && var5 < var1; ++var5) {
               if (EnchantmentDurability.negateDamage(this, var3, var2)) {
                  ++var4;
               }
            }

            var1 -= var4;
            if (var1 <= 0) {
               return false;
            }
         }

         this.itemDamage += var1;
         return this.itemDamage > this.getMaxDamage();
      }
   }

   public IChatComponent getChatComponent() {
      ChatComponentText var1 = new ChatComponentText(this.getDisplayName());
      if (this.hasDisplayName()) {
         var1.getChatStyle().setItalic(true);
      }

      IChatComponent var2 = (new ChatComponentText("[")).appendSibling(var1).appendText("]");
      if (this.item != null) {
         NBTTagCompound var3 = new NBTTagCompound();
         this.writeToNBT(var3);
         var2.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ChatComponentText(var3.toString())));
         var2.getChatStyle().setColor(this.getRarity().rarityColor);
      }

      return var2;
   }

   private ItemStack() {
      this.canDestroyCacheBlock = null;
      this.canDestroyCacheResult = false;
      this.canPlaceOnCacheBlock = null;
      this.canPlaceOnCacheResult = false;
   }

   public static boolean areItemsEqual(ItemStack var0, ItemStack var1) {
      return var0 == null && var1 == null ? true : (var0 != null && var1 != null ? var0.isItemEqual(var1) : false);
   }

   public void setTagCompound(NBTTagCompound var1) {
      this.stackTagCompound = var1;
   }

   public static boolean areItemStackTagsEqual(ItemStack var0, ItemStack var1) {
      return var0 == null && var1 == null ? true : (var0 != null && var1 != null ? (var0.stackTagCompound == null && var1.stackTagCompound != null ? false : var0.stackTagCompound == null || var0.stackTagCompound.equals(var1.stackTagCompound)) : false);
   }

   public boolean getIsItemStackEqual(ItemStack var1) {
      return this.isItemStackEqual(var1);
   }

   public NBTTagCompound getTagCompound() {
      return this.stackTagCompound;
   }

   public EnumAction getItemUseAction() {
      return this.getItem().getItemUseAction(this);
   }

   public void readFromNBT(NBTTagCompound var1) {
      if (var1.hasKey("id", 8)) {
         this.item = Item.getByNameOrId(var1.getString("id"));
      } else {
         this.item = Item.getItemById(var1.getShort("id"));
      }

      this.stackSize = var1.getByte("Count");
      this.itemDamage = var1.getShort("Damage");
      if (this.itemDamage < 0) {
         this.itemDamage = 0;
      }

      if (var1.hasKey("tag", 10)) {
         this.stackTagCompound = var1.getCompoundTag("tag");
         if (this.item != null) {
            this.item.updateItemStackNBT(this.stackTagCompound);
         }
      }

   }

   public NBTTagCompound getSubCompound(String var1, boolean var2) {
      if (this.stackTagCompound != null && this.stackTagCompound.hasKey(var1, 10)) {
         return this.stackTagCompound.getCompoundTag(var1);
      } else if (var2) {
         NBTTagCompound var3 = new NBTTagCompound();
         this.setTagInfo(var1, var3);
         return var3;
      } else {
         return null;
      }
   }

   public int getMaxItemUseDuration() {
      return this.getItem().getMaxItemUseDuration(this);
   }

   public boolean isItemEqual(ItemStack var1) {
      return var1 != null && this.item == var1.item && this.itemDamage == var1.itemDamage;
   }

   public boolean hasTagCompound() {
      return this.stackTagCompound != null;
   }

   public void onBlockDestroyed(World var1, Block var2, BlockPos var3, EntityPlayer var4) {
      boolean var5 = this.item.onBlockDestroyed(this, var1, var2, var3, var4);
      if (var5) {
         var4.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
      }

   }

   public ItemStack(Item var1, int var2) {
      this((Item)var1, var2, 0);
   }

   public boolean isOnItemFrame() {
      return this.itemFrame != null;
   }

   public ItemStack(Block var1, int var2) {
      this((Block)var1, var2, 0);
   }

   public void setTagInfo(String var1, NBTBase var2) {
      if (this.stackTagCompound == null) {
         this.setTagCompound(new NBTTagCompound());
      }

      this.stackTagCompound.setTag(var1, var2);
   }

   public String getDisplayName() {
      String var1 = this.getItem().getItemStackDisplayName(this);
      if (this.stackTagCompound != null && this.stackTagCompound.hasKey("display", 10)) {
         NBTTagCompound var2 = this.stackTagCompound.getCompoundTag("display");
         if (var2.hasKey("Name", 8)) {
            var1 = var2.getString("Name");
         }
      }

      return var1;
   }

   public void damageItem(int var1, EntityLivingBase var2) {
      if ((!(var2 instanceof EntityPlayer) || !((EntityPlayer)var2).capabilities.isCreativeMode) && this.isItemStackDamageable() && this.attemptDamageItem(var1, var2.getRNG())) {
         var2.renderBrokenItemStack(this);
         --this.stackSize;
         if (var2 instanceof EntityPlayer) {
            EntityPlayer var3 = (EntityPlayer)var2;
            var3.triggerAchievement(StatList.objectBreakStats[Item.getIdFromItem(this.item)]);
            if (this.stackSize == 0 && this.getItem() instanceof ItemBow) {
               var3.destroyCurrentEquippedItem();
            }
         }

         if (this.stackSize < 0) {
            this.stackSize = 0;
         }

         this.itemDamage = 0;
      }

   }

   public boolean isStackable() {
      return this.getMaxStackSize() > 1 && (!this.isItemStackDamageable() || !this.isItemDamaged());
   }

   public static ItemStack loadItemStackFromNBT(NBTTagCompound var0) {
      ItemStack var1 = new ItemStack();
      var1.readFromNBT(var0);
      return var1.getItem() != null ? var1 : null;
   }

   public void setItemFrame(EntityItemFrame var1) {
      this.itemFrame = var1;
   }

   public ItemStack(Block var1) {
      this((Block)var1, 1);
   }

   public ItemStack setStackDisplayName(String var1) {
      if (this.stackTagCompound == null) {
         this.stackTagCompound = new NBTTagCompound();
      }

      if (!this.stackTagCompound.hasKey("display", 10)) {
         this.stackTagCompound.setTag("display", new NBTTagCompound());
      }

      this.stackTagCompound.getCompoundTag("display").setString("Name", var1);
      return this;
   }

   public float getStrVsBlock(Block var1) {
      return this.getItem().getStrVsBlock(this, var1);
   }

   public String toString() {
      return String.valueOf((new StringBuilder(String.valueOf(this.stackSize))).append("x").append(this.item.getUnlocalizedName()).append("@").append(this.itemDamage));
   }
}
