package net.minecraft.creativetab;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs$1;
import net.minecraft.creativetab.CreativeTabs$10;
import net.minecraft.creativetab.CreativeTabs$11;
import net.minecraft.creativetab.CreativeTabs$12;
import net.minecraft.creativetab.CreativeTabs$2;
import net.minecraft.creativetab.CreativeTabs$3;
import net.minecraft.creativetab.CreativeTabs$4;
import net.minecraft.creativetab.CreativeTabs$5;
import net.minecraft.creativetab.CreativeTabs$6;
import net.minecraft.creativetab.CreativeTabs$7;
import net.minecraft.creativetab.CreativeTabs$8;
import net.minecraft.creativetab.CreativeTabs$9;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class CreativeTabs {
   public static final CreativeTabs[] creativeTabArray = new CreativeTabs[12];
   public static final CreativeTabs tabBlock = new CreativeTabs$1(0, "buildingBlocks");
   public static final CreativeTabs tabDecorations = new CreativeTabs$2(1, "decorations");
   public static final CreativeTabs tabRedstone = new CreativeTabs$3(2, "redstone");
   public static final CreativeTabs tabTransport = new CreativeTabs$4(3, "transportation");
   public static final CreativeTabs tabMisc = (new CreativeTabs$5(4, "misc")).setRelevantEnchantmentTypes(new EnumEnchantmentType[]{EnumEnchantmentType.ALL});
   public static final CreativeTabs tabAllSearch = (new CreativeTabs$6(5, "search")).setBackgroundImageName("item_search.png");
   public static final CreativeTabs tabFood = new CreativeTabs$7(6, "food");
   public static final CreativeTabs tabTools = (new CreativeTabs$8(7, "tools")).setRelevantEnchantmentTypes(new EnumEnchantmentType[]{EnumEnchantmentType.DIGGER, EnumEnchantmentType.FISHING_ROD, EnumEnchantmentType.BREAKABLE});
   public static final CreativeTabs tabCombat = (new CreativeTabs$9(8, "combat")).setRelevantEnchantmentTypes(new EnumEnchantmentType[]{EnumEnchantmentType.ARMOR, EnumEnchantmentType.ARMOR_FEET, EnumEnchantmentType.ARMOR_HEAD, EnumEnchantmentType.ARMOR_LEGS, EnumEnchantmentType.ARMOR_TORSO, EnumEnchantmentType.BOW, EnumEnchantmentType.WEAPON});
   public static final CreativeTabs tabBrewing = new CreativeTabs$10(9, "brewing");
   public static final CreativeTabs tabMaterials = new CreativeTabs$11(10, "materials");
   public static final CreativeTabs tabInventory = (new CreativeTabs$12(11, "inventory")).setBackgroundImageName("inventory.png").setNoScrollbar().setNoTitle();
   private final int tabIndex;
   private final String tabLabel;
   private String theTexture = "items.png";
   private boolean hasScrollbar = true;
   private boolean drawTitle = true;
   private EnumEnchantmentType[] enchantmentTypes;
   private ItemStack iconItemStack;

   public CreativeTabs(int var1, String var2) {
      this.tabIndex = var1;
      this.tabLabel = var2;
      creativeTabArray[var1] = this;
   }

   public int getTabIndex() {
      return this.tabIndex;
   }

   public String getTabLabel() {
      return this.tabLabel;
   }

   public String getTranslatedTabLabel() {
      return "itemGroup." + this.getTabLabel();
   }

   public ItemStack getIconItemStack() {
      if(this.iconItemStack == null) {
         this.iconItemStack = new ItemStack(this.getTabIconItem(), 1, this.getIconItemDamage());
      }

      return this.iconItemStack;
   }

   public abstract Item getTabIconItem();

   public int getIconItemDamage() {
      return 0;
   }

   public String getBackgroundImageName() {
      return this.theTexture;
   }

   public CreativeTabs setBackgroundImageName(String var1) {
      this.theTexture = var1;
      return this;
   }

   public boolean drawInForegroundOfTab() {
      return this.drawTitle;
   }

   public CreativeTabs setNoTitle() {
      this.drawTitle = false;
      return this;
   }

   public boolean shouldHidePlayerInventory() {
      return this.hasScrollbar;
   }

   public CreativeTabs setNoScrollbar() {
      this.hasScrollbar = false;
      return this;
   }

   public int getTabColumn() {
      return this.tabIndex % 6;
   }

   public boolean isTabInFirstRow() {
      return this.tabIndex < 6;
   }

   public EnumEnchantmentType[] getRelevantEnchantmentTypes() {
      return this.enchantmentTypes;
   }

   public CreativeTabs setRelevantEnchantmentTypes(EnumEnchantmentType... var1) {
      this.enchantmentTypes = var1;
      return this;
   }

   public boolean hasRelevantEnchantmentType(EnumEnchantmentType var1) {
      if(this.enchantmentTypes != null) {
         for(EnumEnchantmentType var5 : this.enchantmentTypes) {
            if(var5 == var1) {
               return true;
            }
         }
      }

      return false;
   }

   public void displayAllReleventItems(List var1) {
      for(Item var3 : Item.itemRegistry) {
         if(var3.getCreativeTab() == this) {
            var3.getSubItems(var3, this, var1);
         }
      }

      if(this.getRelevantEnchantmentTypes() != null) {
         this.addEnchantmentBooksToList(var1, this.getRelevantEnchantmentTypes());
      }

   }

   public void addEnchantmentBooksToList(List var1, EnumEnchantmentType... var2) {
      for(Enchantment var6 : Enchantment.enchantmentsBookList) {
         if(var6.type != null) {
            boolean var7 = false;

            for(int var8 = 0; var8 < var2.length; ++var8) {
               if(var6.type == var2[var8]) {
                  var7 = true;
               }
            }

            var1.add(Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(var6, var6.getMaxLevel())));
         }
      }

   }
}
