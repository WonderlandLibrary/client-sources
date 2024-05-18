package net.minecraft.src;

import java.util.*;

public class CreativeTabs
{
    public static final CreativeTabs[] creativeTabArray;
    public static final CreativeTabs tabBlock;
    public static final CreativeTabs tabDecorations;
    public static final CreativeTabs tabRedstone;
    public static final CreativeTabs tabTransport;
    public static final CreativeTabs tabMisc;
    public static final CreativeTabs tabAllSearch;
    public static final CreativeTabs tabFood;
    public static final CreativeTabs tabTools;
    public static final CreativeTabs tabCombat;
    public static final CreativeTabs tabBrewing;
    public static final CreativeTabs tabMaterials;
    public static final CreativeTabs tabInventory;
    private final int tabIndex;
    private final String tabLabel;
    private String backgroundImageName;
    private boolean hasScrollbar;
    private boolean drawTitle;
    
    static {
        creativeTabArray = new CreativeTabs[12];
        tabBlock = new CreativeTabBlock(0, "buildingBlocks");
        tabDecorations = new CreativeTabDeco(1, "decorations");
        tabRedstone = new CreativeTabRedstone(2, "redstone");
        tabTransport = new CreativeTabTransport(3, "transportation");
        tabMisc = new CreativeTabMisc(4, "misc");
        tabAllSearch = new CreativeTabSearch(5, "search").setBackgroundImageName("search.png");
        tabFood = new CreativeTabFood(6, "food");
        tabTools = new CreativeTabTools(7, "tools");
        tabCombat = new CreativeTabCombat(8, "combat");
        tabBrewing = new CreativeTabBrewing(9, "brewing");
        tabMaterials = new CreativeTabMaterial(10, "materials");
        tabInventory = new CreativeTabInventory(11, "inventory").setBackgroundImageName("survival_inv.png").setNoScrollbar().setNoTitle();
    }
    
    public CreativeTabs(final int par1, final String par2Str) {
        this.backgroundImageName = "list_items.png";
        this.hasScrollbar = true;
        this.drawTitle = true;
        this.tabIndex = par1;
        this.tabLabel = par2Str;
        CreativeTabs.creativeTabArray[par1] = this;
    }
    
    public int getTabIndex() {
        return this.tabIndex;
    }
    
    public String getTabLabel() {
        return this.tabLabel;
    }
    
    public String getTranslatedTabLabel() {
        return StringTranslate.getInstance().translateKey("itemGroup." + this.getTabLabel());
    }
    
    public Item getTabIconItem() {
        return Item.itemsList[this.getTabIconItemIndex()];
    }
    
    public int getTabIconItemIndex() {
        return 1;
    }
    
    public String getBackgroundImageName() {
        return this.backgroundImageName;
    }
    
    public CreativeTabs setBackgroundImageName(final String par1Str) {
        this.backgroundImageName = par1Str;
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
    
    public void displayAllReleventItems(final List par1List) {
        for (final Item var5 : Item.itemsList) {
            if (var5 != null && var5.getCreativeTab() == this) {
                var5.getSubItems(var5.itemID, this, par1List);
            }
        }
    }
    
    public void func_92116_a(final List par1List, final EnumEnchantmentType... par2ArrayOfEnumEnchantmentType) {
        for (final Enchantment var6 : Enchantment.enchantmentsList) {
            if (var6 != null && var6.type != null) {
                boolean var7 = false;
                for (int var8 = 0; var8 < par2ArrayOfEnumEnchantmentType.length && !var7; ++var8) {
                    if (var6.type == par2ArrayOfEnumEnchantmentType[var8]) {
                        var7 = true;
                    }
                }
                if (var7) {
                    par1List.add(Item.enchantedBook.func_92111_a(new EnchantmentData(var6, var6.getMaxLevel())));
                }
            }
        }
    }
}
