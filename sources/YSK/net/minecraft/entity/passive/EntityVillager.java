package net.minecraft.entity.passive;

import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.stats.*;
import net.minecraft.entity.effect.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.world.*;
import net.minecraft.village.*;
import net.minecraft.entity.item.*;
import net.minecraft.nbt.*;
import net.minecraft.potion.*;
import net.minecraft.entity.monster.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.enchantment.*;

public class EntityVillager extends EntityAgeable implements INpc, IMerchant
{
    Village villageObj;
    private boolean areAdditionalTasksSet;
    private int careerLevel;
    private InventoryBasic villagerInventory;
    private EntityPlayer buyingPlayer;
    private boolean needsInitilization;
    private static final String[] I;
    private boolean isLookingForHome;
    private String lastBuyingPlayer;
    private int randomTickDivider;
    private boolean isMating;
    private boolean isPlaying;
    private int wealth;
    private boolean isWillingToMate;
    private MerchantRecipeList buyingList;
    private int timeUntilReset;
    private int careerId;
    private static final ITradeList[][][][] DEFAULT_TRADE_LIST_MAP;
    
    @Override
    public EntityPlayer getCustomer() {
        return this.buyingPlayer;
    }
    
    static {
        I();
        final ITradeList[][][][] default_TRADE_LIST_MAP = new ITradeList[0x5D ^ 0x58][][][];
        final int length = "".length();
        final ITradeList[][][] array = new ITradeList[0x68 ^ 0x6C][][];
        final int length2 = "".length();
        final ITradeList[][] array2 = new ITradeList[0x9C ^ 0x98][];
        final int length3 = "".length();
        final ITradeList[] array3 = new ITradeList[0x64 ^ 0x60];
        array3["".length()] = new EmeraldForItems(Items.wheat, new PriceInfo(0x45 ^ 0x57, 0x33 ^ 0x25));
        array3[" ".length()] = new EmeraldForItems(Items.potato, new PriceInfo(0x2A ^ 0x25, 0xAF ^ 0xBC));
        array3["  ".length()] = new EmeraldForItems(Items.carrot, new PriceInfo(0xCB ^ 0xC4, 0x21 ^ 0x32));
        array3["   ".length()] = new ListItemForEmeralds(Items.bread, new PriceInfo(-(0x6 ^ 0x2), -"  ".length()));
        array2[length3] = array3;
        final int length4 = " ".length();
        final ITradeList[] array4 = new ITradeList["  ".length()];
        array4["".length()] = new EmeraldForItems(Item.getItemFromBlock(Blocks.pumpkin), new PriceInfo(0x64 ^ 0x6C, 0x4B ^ 0x46));
        array4[" ".length()] = new ListItemForEmeralds(Items.pumpkin_pie, new PriceInfo(-"   ".length(), -"  ".length()));
        array2[length4] = array4;
        final int length5 = "  ".length();
        final ITradeList[] array5 = new ITradeList["  ".length()];
        array5["".length()] = new EmeraldForItems(Item.getItemFromBlock(Blocks.melon_block), new PriceInfo(0x9B ^ 0x9C, 0xA4 ^ 0xA8));
        array5[" ".length()] = new ListItemForEmeralds(Items.apple, new PriceInfo(-(0xA9 ^ 0xAC), -(0x8E ^ 0x89)));
        array2[length5] = array5;
        final int length6 = "   ".length();
        final ITradeList[] array6 = new ITradeList["  ".length()];
        array6["".length()] = new ListItemForEmeralds(Items.cookie, new PriceInfo(-(0x22 ^ 0x24), -(0xA0 ^ 0xAA)));
        array6[" ".length()] = new ListItemForEmeralds(Items.cake, new PriceInfo(" ".length(), " ".length()));
        array2[length6] = array6;
        array[length2] = array2;
        final int length7 = " ".length();
        final ITradeList[][] array7 = new ITradeList["  ".length()][];
        final int length8 = "".length();
        final ITradeList[] array8 = new ITradeList["   ".length()];
        array8["".length()] = new EmeraldForItems(Items.string, new PriceInfo(0x8E ^ 0x81, 0x5 ^ 0x11));
        array8[" ".length()] = new EmeraldForItems(Items.coal, new PriceInfo(0x9E ^ 0x8E, 0x33 ^ 0x2B));
        array8["  ".length()] = new ItemAndEmeraldToItem(Items.fish, new PriceInfo(0x6C ^ 0x6A, 0x6D ^ 0x6B), Items.cooked_fish, new PriceInfo(0x11 ^ 0x17, 0xBF ^ 0xB9));
        array7[length8] = array8;
        final int length9 = " ".length();
        final ITradeList[] array9 = new ITradeList[" ".length()];
        array9["".length()] = new ListEnchantedItemForEmeralds(Items.fishing_rod, new PriceInfo(0x5C ^ 0x5B, 0xAC ^ 0xA4));
        array7[length9] = array9;
        array[length7] = array7;
        final int length10 = "  ".length();
        final ITradeList[][] array10 = new ITradeList["  ".length()][];
        final int length11 = "".length();
        final ITradeList[] array11 = new ITradeList["  ".length()];
        array11["".length()] = new EmeraldForItems(Item.getItemFromBlock(Blocks.wool), new PriceInfo(0x85 ^ 0x95, 0x29 ^ 0x3F));
        array11[" ".length()] = new ListItemForEmeralds(Items.shears, new PriceInfo("   ".length(), 0x23 ^ 0x27));
        array10[length11] = array11;
        final int length12 = " ".length();
        final ITradeList[] array12 = new ITradeList[0xB0 ^ 0xA0];
        array12["".length()] = new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), " ".length(), "".length()), new PriceInfo(" ".length(), "  ".length()));
        array12[" ".length()] = new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), " ".length(), " ".length()), new PriceInfo(" ".length(), "  ".length()));
        array12["  ".length()] = new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), " ".length(), "  ".length()), new PriceInfo(" ".length(), "  ".length()));
        array12["   ".length()] = new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), " ".length(), "   ".length()), new PriceInfo(" ".length(), "  ".length()));
        array12[0x15 ^ 0x11] = new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), " ".length(), 0x32 ^ 0x36), new PriceInfo(" ".length(), "  ".length()));
        array12[0x8C ^ 0x89] = new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), " ".length(), 0x91 ^ 0x94), new PriceInfo(" ".length(), "  ".length()));
        array12[0xB5 ^ 0xB3] = new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), " ".length(), 0x2C ^ 0x2A), new PriceInfo(" ".length(), "  ".length()));
        array12[0x28 ^ 0x2F] = new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), " ".length(), 0xF ^ 0x8), new PriceInfo(" ".length(), "  ".length()));
        array12[0x93 ^ 0x9B] = new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), " ".length(), 0x73 ^ 0x7B), new PriceInfo(" ".length(), "  ".length()));
        array12[0x25 ^ 0x2C] = new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), " ".length(), 0x4D ^ 0x44), new PriceInfo(" ".length(), "  ".length()));
        array12[0x45 ^ 0x4F] = new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), " ".length(), 0xA0 ^ 0xAA), new PriceInfo(" ".length(), "  ".length()));
        array12[0x1B ^ 0x10] = new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), " ".length(), 0x78 ^ 0x73), new PriceInfo(" ".length(), "  ".length()));
        array12[0x3A ^ 0x36] = new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), " ".length(), 0x90 ^ 0x9C), new PriceInfo(" ".length(), "  ".length()));
        array12[0x8A ^ 0x87] = new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), " ".length(), 0x18 ^ 0x15), new PriceInfo(" ".length(), "  ".length()));
        array12[0xA1 ^ 0xAF] = new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), " ".length(), 0x95 ^ 0x9B), new PriceInfo(" ".length(), "  ".length()));
        array12[0x5C ^ 0x53] = new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), " ".length(), 0x79 ^ 0x76), new PriceInfo(" ".length(), "  ".length()));
        array10[length12] = array12;
        array[length10] = array10;
        final int length13 = "   ".length();
        final ITradeList[][] array13 = new ITradeList["  ".length()][];
        final int length14 = "".length();
        final ITradeList[] array14 = new ITradeList["  ".length()];
        array14["".length()] = new EmeraldForItems(Items.string, new PriceInfo(0x40 ^ 0x4F, 0x7B ^ 0x6F));
        array14[" ".length()] = new ListItemForEmeralds(Items.arrow, new PriceInfo(-(0x52 ^ 0x5E), -(0x24 ^ 0x2C)));
        array13[length14] = array14;
        final int length15 = " ".length();
        final ITradeList[] array15 = new ITradeList["  ".length()];
        array15["".length()] = new ListItemForEmeralds(Items.bow, new PriceInfo("  ".length(), "   ".length()));
        array15[" ".length()] = new ItemAndEmeraldToItem(Item.getItemFromBlock(Blocks.gravel), new PriceInfo(0x17 ^ 0x1D, 0x71 ^ 0x7B), Items.flint, new PriceInfo(0x6E ^ 0x68, 0xC ^ 0x6));
        array13[length15] = array15;
        array[length13] = array13;
        default_TRADE_LIST_MAP[length] = array;
        final int length16 = " ".length();
        final ITradeList[][][] array16 = new ITradeList[" ".length()][][];
        final int length17 = "".length();
        final ITradeList[][] array17 = new ITradeList[0x5A ^ 0x5C][];
        final int length18 = "".length();
        final ITradeList[] array18 = new ITradeList["  ".length()];
        array18["".length()] = new EmeraldForItems(Items.paper, new PriceInfo(0x54 ^ 0x4C, 0x3D ^ 0x19));
        array18[" ".length()] = new ListEnchantedBookForEmeralds();
        array17[length18] = array18;
        final int length19 = " ".length();
        final ITradeList[] array19 = new ITradeList["   ".length()];
        array19["".length()] = new EmeraldForItems(Items.book, new PriceInfo(0x8F ^ 0x87, 0x87 ^ 0x8D));
        array19[" ".length()] = new ListItemForEmeralds(Items.compass, new PriceInfo(0xAF ^ 0xA5, 0x6F ^ 0x63));
        array19["  ".length()] = new ListItemForEmeralds(Item.getItemFromBlock(Blocks.bookshelf), new PriceInfo("   ".length(), 0x66 ^ 0x62));
        array17[length19] = array19;
        final int length20 = "  ".length();
        final ITradeList[] array20 = new ITradeList["   ".length()];
        array20["".length()] = new EmeraldForItems(Items.written_book, new PriceInfo("  ".length(), "  ".length()));
        array20[" ".length()] = new ListItemForEmeralds(Items.clock, new PriceInfo(0x1F ^ 0x15, 0x19 ^ 0x15));
        array20["  ".length()] = new ListItemForEmeralds(Item.getItemFromBlock(Blocks.glass), new PriceInfo(-(0x5A ^ 0x5F), -"   ".length()));
        array17[length20] = array20;
        final int length21 = "   ".length();
        final ITradeList[] array21 = new ITradeList[" ".length()];
        array21["".length()] = new ListEnchantedBookForEmeralds();
        array17[length21] = array21;
        final int n = 0x4C ^ 0x48;
        final ITradeList[] array22 = new ITradeList[" ".length()];
        array22["".length()] = new ListEnchantedBookForEmeralds();
        array17[n] = array22;
        final int n2 = 0x8F ^ 0x8A;
        final ITradeList[] array23 = new ITradeList[" ".length()];
        array23["".length()] = new ListItemForEmeralds(Items.name_tag, new PriceInfo(0xBE ^ 0xAA, 0x15 ^ 0x3));
        array17[n2] = array23;
        array16[length17] = array17;
        default_TRADE_LIST_MAP[length16] = array16;
        final int length22 = "  ".length();
        final ITradeList[][][] array24 = new ITradeList[" ".length()][][];
        final int length23 = "".length();
        final ITradeList[][] array25 = new ITradeList[0x18 ^ 0x1C][];
        final int length24 = "".length();
        final ITradeList[] array26 = new ITradeList["  ".length()];
        array26["".length()] = new EmeraldForItems(Items.rotten_flesh, new PriceInfo(0x9A ^ 0xBE, 0x3F ^ 0x17));
        array26[" ".length()] = new EmeraldForItems(Items.gold_ingot, new PriceInfo(0x3D ^ 0x35, 0x7A ^ 0x70));
        array25[length24] = array26;
        final int length25 = " ".length();
        final ITradeList[] array27 = new ITradeList["  ".length()];
        array27["".length()] = new ListItemForEmeralds(Items.redstone, new PriceInfo(-(0xB2 ^ 0xB6), -" ".length()));
        array27[" ".length()] = new ListItemForEmeralds(new ItemStack(Items.dye, " ".length(), EnumDyeColor.BLUE.getDyeDamage()), new PriceInfo(-"  ".length(), -" ".length()));
        array25[length25] = array27;
        final int length26 = "  ".length();
        final ITradeList[] array28 = new ITradeList["  ".length()];
        array28["".length()] = new ListItemForEmeralds(Items.ender_eye, new PriceInfo(0x5E ^ 0x59, 0x3C ^ 0x37));
        array28[" ".length()] = new ListItemForEmeralds(Item.getItemFromBlock(Blocks.glowstone), new PriceInfo(-"   ".length(), -" ".length()));
        array25[length26] = array28;
        final int length27 = "   ".length();
        final ITradeList[] array29 = new ITradeList[" ".length()];
        array29["".length()] = new ListItemForEmeralds(Items.experience_bottle, new PriceInfo("   ".length(), 0x1B ^ 0x10));
        array25[length27] = array29;
        array24[length23] = array25;
        default_TRADE_LIST_MAP[length22] = array24;
        final int length28 = "   ".length();
        final ITradeList[][][] array30 = new ITradeList["   ".length()][][];
        final int length29 = "".length();
        final ITradeList[][] array31 = new ITradeList[0x45 ^ 0x41][];
        final int length30 = "".length();
        final ITradeList[] array32 = new ITradeList["  ".length()];
        array32["".length()] = new EmeraldForItems(Items.coal, new PriceInfo(0xBF ^ 0xAF, 0x27 ^ 0x3F));
        array32[" ".length()] = new ListItemForEmeralds(Items.iron_helmet, new PriceInfo(0x38 ^ 0x3C, 0x5E ^ 0x58));
        array31[length30] = array32;
        final int length31 = " ".length();
        final ITradeList[] array33 = new ITradeList["  ".length()];
        array33["".length()] = new EmeraldForItems(Items.iron_ingot, new PriceInfo(0x10 ^ 0x17, 0x58 ^ 0x51));
        array33[" ".length()] = new ListItemForEmeralds(Items.iron_chestplate, new PriceInfo(0x96 ^ 0x9C, 0x29 ^ 0x27));
        array31[length31] = array33;
        final int length32 = "  ".length();
        final ITradeList[] array34 = new ITradeList["  ".length()];
        array34["".length()] = new EmeraldForItems(Items.diamond, new PriceInfo("   ".length(), 0x5A ^ 0x5E));
        array34[" ".length()] = new ListEnchantedItemForEmeralds(Items.diamond_chestplate, new PriceInfo(0x2B ^ 0x3B, 0x77 ^ 0x64));
        array31[length32] = array34;
        final int length33 = "   ".length();
        final ITradeList[] array35 = new ITradeList[0x1 ^ 0x5];
        array35["".length()] = new ListItemForEmeralds(Items.chainmail_boots, new PriceInfo(0x52 ^ 0x57, 0xAA ^ 0xAD));
        array35[" ".length()] = new ListItemForEmeralds(Items.chainmail_leggings, new PriceInfo(0x1F ^ 0x16, 0x3E ^ 0x35));
        array35["  ".length()] = new ListItemForEmeralds(Items.chainmail_helmet, new PriceInfo(0x68 ^ 0x6D, 0x64 ^ 0x63));
        array35["   ".length()] = new ListItemForEmeralds(Items.chainmail_chestplate, new PriceInfo(0x3D ^ 0x36, 0x1E ^ 0x11));
        array31[length33] = array35;
        array30[length29] = array31;
        final int length34 = " ".length();
        final ITradeList[][] array36 = new ITradeList["   ".length()][];
        final int length35 = "".length();
        final ITradeList[] array37 = new ITradeList["  ".length()];
        array37["".length()] = new EmeraldForItems(Items.coal, new PriceInfo(0x14 ^ 0x4, 0x38 ^ 0x20));
        array37[" ".length()] = new ListItemForEmeralds(Items.iron_axe, new PriceInfo(0x37 ^ 0x31, 0x48 ^ 0x40));
        array36[length35] = array37;
        final int length36 = " ".length();
        final ITradeList[] array38 = new ITradeList["  ".length()];
        array38["".length()] = new EmeraldForItems(Items.iron_ingot, new PriceInfo(0xA6 ^ 0xA1, 0x71 ^ 0x78));
        array38[" ".length()] = new ListEnchantedItemForEmeralds(Items.iron_sword, new PriceInfo(0x96 ^ 0x9F, 0x27 ^ 0x2D));
        array36[length36] = array38;
        final int length37 = "  ".length();
        final ITradeList[] array39 = new ITradeList["   ".length()];
        array39["".length()] = new EmeraldForItems(Items.diamond, new PriceInfo("   ".length(), 0x9D ^ 0x99));
        array39[" ".length()] = new ListEnchantedItemForEmeralds(Items.diamond_sword, new PriceInfo(0xE ^ 0x2, 0xA0 ^ 0xAF));
        array39["  ".length()] = new ListEnchantedItemForEmeralds(Items.diamond_axe, new PriceInfo(0x4D ^ 0x44, 0x53 ^ 0x5F));
        array36[length37] = array39;
        array30[length34] = array36;
        final int length38 = "  ".length();
        final ITradeList[][] array40 = new ITradeList["   ".length()][];
        final int length39 = "".length();
        final ITradeList[] array41 = new ITradeList["  ".length()];
        array41["".length()] = new EmeraldForItems(Items.coal, new PriceInfo(0x49 ^ 0x59, 0x69 ^ 0x71));
        array41[" ".length()] = new ListEnchantedItemForEmeralds(Items.iron_shovel, new PriceInfo(0x8D ^ 0x88, 0x3F ^ 0x38));
        array40[length39] = array41;
        final int length40 = " ".length();
        final ITradeList[] array42 = new ITradeList["  ".length()];
        array42["".length()] = new EmeraldForItems(Items.iron_ingot, new PriceInfo(0x61 ^ 0x66, 0x26 ^ 0x2F));
        array42[" ".length()] = new ListEnchantedItemForEmeralds(Items.iron_pickaxe, new PriceInfo(0x4F ^ 0x46, 0x23 ^ 0x28));
        array40[length40] = array42;
        final int length41 = "  ".length();
        final ITradeList[] array43 = new ITradeList["  ".length()];
        array43["".length()] = new EmeraldForItems(Items.diamond, new PriceInfo("   ".length(), 0x18 ^ 0x1C));
        array43[" ".length()] = new ListEnchantedItemForEmeralds(Items.diamond_pickaxe, new PriceInfo(0x61 ^ 0x6D, 0x87 ^ 0x88));
        array40[length41] = array43;
        array30[length38] = array40;
        default_TRADE_LIST_MAP[length28] = array30;
        final int n3 = 0x3 ^ 0x7;
        final ITradeList[][][] array44 = new ITradeList["  ".length()][][];
        final int length42 = "".length();
        final ITradeList[][] array45 = new ITradeList["  ".length()][];
        final int length43 = "".length();
        final ITradeList[] array46 = new ITradeList["  ".length()];
        array46["".length()] = new EmeraldForItems(Items.porkchop, new PriceInfo(0x91 ^ 0x9F, 0xD2 ^ 0xC0));
        array46[" ".length()] = new EmeraldForItems(Items.chicken, new PriceInfo(0x41 ^ 0x4F, 0x5C ^ 0x4E));
        array45[length43] = array46;
        final int length44 = " ".length();
        final ITradeList[] array47 = new ITradeList["   ".length()];
        array47["".length()] = new EmeraldForItems(Items.coal, new PriceInfo(0xA1 ^ 0xB1, 0x3 ^ 0x1B));
        array47[" ".length()] = new ListItemForEmeralds(Items.cooked_porkchop, new PriceInfo(-(0xBF ^ 0xB8), -(0x5E ^ 0x5B)));
        array47["  ".length()] = new ListItemForEmeralds(Items.cooked_chicken, new PriceInfo(-(0x6B ^ 0x63), -(0x12 ^ 0x14)));
        array45[length44] = array47;
        array44[length42] = array45;
        final int length45 = " ".length();
        final ITradeList[][] array48 = new ITradeList["   ".length()][];
        final int length46 = "".length();
        final ITradeList[] array49 = new ITradeList["  ".length()];
        array49["".length()] = new EmeraldForItems(Items.leather, new PriceInfo(0x60 ^ 0x69, 0xA1 ^ 0xAD));
        array49[" ".length()] = new ListItemForEmeralds(Items.leather_leggings, new PriceInfo("  ".length(), 0x60 ^ 0x64));
        array48[length46] = array49;
        final int length47 = " ".length();
        final ITradeList[] array50 = new ITradeList[" ".length()];
        array50["".length()] = new ListEnchantedItemForEmeralds(Items.leather_chestplate, new PriceInfo(0x5A ^ 0x5D, 0xCD ^ 0xC1));
        array48[length47] = array50;
        final int length48 = "  ".length();
        final ITradeList[] array51 = new ITradeList[" ".length()];
        array51["".length()] = new ListItemForEmeralds(Items.saddle, new PriceInfo(0x21 ^ 0x29, 0x73 ^ 0x79));
        array48[length48] = array51;
        array44[length45] = array48;
        default_TRADE_LIST_MAP[n3] = array44;
        DEFAULT_TRADE_LIST_MAP = default_TRADE_LIST_MAP;
    }
    
    @Override
    public void setRevengeTarget(final EntityLivingBase revengeTarget) {
        super.setRevengeTarget(revengeTarget);
        if (this.villageObj != null && revengeTarget != null) {
            this.villageObj.addOrRenewAgressor(revengeTarget);
            if (revengeTarget instanceof EntityPlayer) {
                int n = -" ".length();
                if (this.isChild()) {
                    n = -"   ".length();
                }
                this.villageObj.setReputationForPlayer(revengeTarget.getName(), n);
                if (this.isEntityAlive()) {
                    this.worldObj.setEntityState(this, (byte)(0xAF ^ 0xA2));
                }
            }
        }
    }
    
    public void setPlaying(final boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
    
    @Override
    public boolean interact(final EntityPlayer customer) {
        final ItemStack currentItem = customer.inventory.getCurrentItem();
        int n;
        if (currentItem != null && currentItem.getItem() == Items.spawn_egg) {
            n = " ".length();
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        if (n == 0 && this.isEntityAlive() && !this.isTrading() && !this.isChild()) {
            if (!this.worldObj.isRemote && (this.buyingList == null || this.buyingList.size() > 0)) {
                this.setCustomer(customer);
                customer.displayVillagerTradeGui(this);
            }
            customer.triggerAchievement(StatList.timesTalkedToVillagerStat);
            return " ".length() != 0;
        }
        return super.interact(customer);
    }
    
    private static void I() {
        (I = new String[0x78 ^ 0x5B])["".length()] = I(">.\u000b*9", "wZnGJ");
        EntityVillager.I[" ".length()] = I("\u0018#\b-\u0006;\"\u000e$\r", "HQgKc");
        EntityVillager.I["  ".length()] = I("\u001f#\u0010'\u0010>", "MJsOu");
        EntityVillager.I["   ".length()] = I("\u000e\u0011\u001f )?", "MpmEL");
        EntityVillager.I[0x67 ^ 0x63] = I("\u0013;\u0011\u0007\u0006\"\u0016\u0006\u0014\u0006<", "PZcbc");
        EntityVillager.I[0x44 ^ 0x41] = I("\u0015\b(-%,\u0006", "BaDAL");
        EntityVillager.I[0x6A ^ 0x6C] = I("\u0000\u0010\u000b\t\u0006<", "Ovmlt");
        EntityVillager.I[0x6E ^ 0x69] = I("\f:50\u00041;1,", "ETCUj");
        EntityVillager.I[0x74 ^ 0x7C] = I("\u0013\u001e7770\u001f1><", "ClXQR");
        EntityVillager.I[0xB5 ^ 0xBC] = I("\u001f\u00117\u0010\u0001>", "MxTxd");
        EntityVillager.I[0xB2 ^ 0xB8] = I("+*\u0019!\u0007\u001a", "hKkDb");
        EntityVillager.I[0x63 ^ 0x68] = I(",17\u0015+\u001d\u001c \u0006+\u0003", "oPEpN");
        EntityVillager.I[0x76 ^ 0x7A] = I("8\b$\u0019\u0000\u0001\u0006", "oaHui");
        EntityVillager.I[0x53 ^ 0x5E] = I("#.4\u0002#\u001f", "lHRgQ");
        EntityVillager.I[0x4A ^ 0x44] = I("\t# \n+5", "FEFoY");
        EntityVillager.I[0x1B ^ 0x14] = I("\u0001\r\u0012&\u0018<\f\u0016:", "HcdCv");
        EntityVillager.I[0xAA ^ 0xBA] = I("\u0019\u001d\u000eB?\u001d\u001e\u0000\r.\u0011\u0000B\u0004(\u0013\u0015\u0000\t", "trllI");
        EntityVillager.I[0x59 ^ 0x48] = I("\f\u00050e\u0007\b\u0006>*\u0016\u0004\u0018|\"\u0015\r\u000f", "ajRKq");
        EntityVillager.I[0x42 ^ 0x50] = I(",-/c8(.!,)$0c%'5", "ABMMN");
        EntityVillager.I[0x69 ^ 0x7A] = I("\u00197\nB4\u001d4\u0004\r%\u0011*F\b'\u0015,\u0000", "tXhlB");
        EntityVillager.I[0xB2 ^ 0xA6] = I("\u0000\u001b:V4\u0004\u00184\u0019%\b\u0006v\u0001'\u001e", "mtXxB");
        EntityVillager.I[0x8A ^ 0x9F] = I("!%\u001ay&%&\u001467)8V.5?", "LJxWP");
        EntityVillager.I[0xBA ^ 0xAC] = I("<\u000e/b=8\r!-,4\u0013c\"$", "QaMLK");
        EntityVillager.I[0x74 ^ 0x63] = I(")\u00061\u0003\u0002=", "OgCng");
        EntityVillager.I[0x7D ^ 0x65] = I("$<0<<08\":", "BUCTY");
        EntityVillager.I[0x8F ^ 0x96] = I("?\t\r9/)\u0013\f", "LahIG");
        EntityVillager.I[0x17 ^ 0xD] = I("\u0004\u001e/\u00101\n\u00178", "brJdR");
        EntityVillager.I[0xA5 ^ 0xBE] = I("\u001b\u0002\t\u0015\u0013\u0005\u0002\n\t", "wkkgr");
        EntityVillager.I[0xBA ^ 0xA6] = I("\t\u0014?\u001c\u0001\t", "jxZnh");
        EntityVillager.I[0x91 ^ 0x8C] = I(";=\u0007\"\b", "ZOjMz");
        EntityVillager.I[0x85 ^ 0x9B] = I("\u0012\u001f\u0019\t+\u000b", "ezxyD");
        EntityVillager.I[0x13 ^ 0xC] = I("3<.$", "GSAHh");
        EntityVillager.I[0xA9 ^ 0x89] = I("&\u0007\u001c*+!\u0000", "DrhIC");
        EntityVillager.I[0x2F ^ 0xE] = I("4\u001d\u00165 =\n", "XxwAH");
        EntityVillager.I[0x5F ^ 0x7D] = I("'\u0006?\u00008;F\u001d\u0000 .\t,\f>l", "BhKiL");
    }
    
    private void spawnParticles(final EnumParticleTypes enumParticleTypes) {
        int i = "".length();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (i < (0x9A ^ 0x9F)) {
            this.worldObj.spawnParticle(enumParticleTypes, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 1.0 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, new int["".length()]);
            ++i;
        }
    }
    
    private void setAdditionalAItasks() {
        if (!this.areAdditionalTasksSet) {
            this.areAdditionalTasksSet = (" ".length() != 0);
            if (this.isChild()) {
                this.tasks.addTask(0xD ^ 0x5, new EntityAIPlay(this, 0.32));
                "".length();
                if (4 == 1) {
                    throw null;
                }
            }
            else if (this.getProfession() == 0) {
                this.tasks.addTask(0x51 ^ 0x57, new EntityAIHarvestFarmland(this, 0.6));
            }
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setProfession(nbtTagCompound.getInteger(EntityVillager.I[0x1D ^ 0x15]));
        this.wealth = nbtTagCompound.getInteger(EntityVillager.I[0x57 ^ 0x5E]);
        this.careerId = nbtTagCompound.getInteger(EntityVillager.I[0x74 ^ 0x7E]);
        this.careerLevel = nbtTagCompound.getInteger(EntityVillager.I[0x28 ^ 0x23]);
        this.isWillingToMate = nbtTagCompound.getBoolean(EntityVillager.I[0x4B ^ 0x47]);
        if (nbtTagCompound.hasKey(EntityVillager.I[0x1D ^ 0x10], 0x3C ^ 0x36)) {
            this.buyingList = new MerchantRecipeList(nbtTagCompound.getCompoundTag(EntityVillager.I[0x33 ^ 0x3D]));
        }
        final NBTTagList tagList = nbtTagCompound.getTagList(EntityVillager.I[0xBC ^ 0xB3], 0x93 ^ 0x99);
        int i = "".length();
        "".length();
        if (1 >= 3) {
            throw null;
        }
        while (i < tagList.tagCount()) {
            final ItemStack loadItemStackFromNBT = ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(i));
            if (loadItemStackFromNBT != null) {
                this.villagerInventory.func_174894_a(loadItemStackFromNBT);
            }
            ++i;
        }
        this.setCanPickUpLoot(" ".length() != 0);
        this.setAdditionalAItasks();
    }
    
    private void populateBuyingList() {
        final ITradeList[][][] array = EntityVillager.DEFAULT_TRADE_LIST_MAP[this.getProfession()];
        if (this.careerId != 0 && this.careerLevel != 0) {
            this.careerLevel += " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            this.careerId = this.rand.nextInt(array.length) + " ".length();
            this.careerLevel = " ".length();
        }
        if (this.buyingList == null) {
            this.buyingList = new MerchantRecipeList();
        }
        final int n = this.careerId - " ".length();
        final int n2 = this.careerLevel - " ".length();
        final ITradeList[][] array2 = array[n];
        if (n2 >= 0 && n2 < array2.length) {
            final ITradeList[] array3;
            final int length = (array3 = array2[n2]).length;
            int i = "".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
            while (i < length) {
                array3[i].modifyMerchantRecipeList(this.buyingList, this.rand);
                ++i;
            }
        }
    }
    
    public boolean func_175553_cp() {
        return this.hasEnoughItems(" ".length());
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private boolean canVillagerPickupItem(final Item item) {
        if (item != Items.bread && item != Items.potato && item != Items.carrot && item != Items.wheat && item != Items.wheat_seeds) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public boolean getIsWillingToMate(final boolean b) {
        if (!this.isWillingToMate && b && this.func_175553_cp()) {
            int n = "".length();
            int i = "".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
            while (i < this.villagerInventory.getSizeInventory()) {
                final ItemStack stackInSlot = this.villagerInventory.getStackInSlot(i);
                if (stackInSlot != null) {
                    if (stackInSlot.getItem() == Items.bread && stackInSlot.stackSize >= "   ".length()) {
                        n = " ".length();
                        this.villagerInventory.decrStackSize(i, "   ".length());
                        "".length();
                        if (false == true) {
                            throw null;
                        }
                    }
                    else if ((stackInSlot.getItem() == Items.potato || stackInSlot.getItem() == Items.carrot) && stackInSlot.stackSize >= (0x2B ^ 0x27)) {
                        n = " ".length();
                        this.villagerInventory.decrStackSize(i, 0xB1 ^ 0xBD);
                    }
                }
                if (n != 0) {
                    this.worldObj.setEntityState(this, (byte)(0xBA ^ 0xA8));
                    this.isWillingToMate = (" ".length() != 0);
                    "".length();
                    if (0 >= 3) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        return this.isWillingToMate;
    }
    
    @Override
    public float getEyeHeight() {
        float n = 1.62f;
        if (this.isChild()) {
            n -= 0.81;
        }
        return n;
    }
    
    @Override
    protected String getLivingSound() {
        String s;
        if (this.isTrading()) {
            s = EntityVillager.I[0x3 ^ 0x13];
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            s = EntityVillager.I[0xA ^ 0x1B];
        }
        return s;
    }
    
    public void setLookingForHome() {
        this.isLookingForHome = (" ".length() != 0);
    }
    
    @Override
    protected boolean canDespawn() {
        return "".length() != 0;
    }
    
    public int getProfession() {
        return Math.max(this.dataWatcher.getWatchableObjectInt(0xD6 ^ 0xC6) % (0x47 ^ 0x42), "".length());
    }
    
    @Override
    public void onStruckByLightning(final EntityLightningBolt entityLightningBolt) {
        if (!this.worldObj.isRemote && !this.isDead) {
            final EntityWitch entityWitch = new EntityWitch(this.worldObj);
            entityWitch.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entityWitch.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(entityWitch)), null);
            entityWitch.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                entityWitch.setCustomNameTag(this.getCustomNameTag());
                entityWitch.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
            }
            this.worldObj.spawnEntityInWorld(entityWitch);
            this.setDead();
        }
    }
    
    @Override
    public boolean replaceItemInInventory(final int n, final ItemStack itemStack) {
        if (super.replaceItemInInventory(n, itemStack)) {
            return " ".length() != 0;
        }
        final int n2 = n - (299 + 167 - 300 + 134);
        if (n2 >= 0 && n2 < this.villagerInventory.getSizeInventory()) {
            this.villagerInventory.setInventorySlotContents(n2, itemStack);
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public EntityVillager(final World world, final int profession) {
        super(world);
        this.villagerInventory = new InventoryBasic(EntityVillager.I["".length()], "".length() != 0, 0xB6 ^ 0xBE);
        this.setProfession(profession);
        this.setSize(0.6f, 1.8f);
        ((PathNavigateGround)this.getNavigator()).setBreakDoors(" ".length() != 0);
        ((PathNavigateGround)this.getNavigator()).setAvoidsWater(" ".length() != 0);
        this.tasks.addTask("".length(), new EntityAISwimming(this));
        this.tasks.addTask(" ".length(), new EntityAIAvoidEntity<Object>(this, EntityZombie.class, 8.0f, 0.6, 0.6));
        this.tasks.addTask(" ".length(), new EntityAITradePlayer(this));
        this.tasks.addTask(" ".length(), new EntityAILookAtTradePlayer(this));
        this.tasks.addTask("  ".length(), new EntityAIMoveIndoors(this));
        this.tasks.addTask("   ".length(), new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(0x5 ^ 0x1, new EntityAIOpenDoor(this, (boolean)(" ".length() != 0)));
        this.tasks.addTask(0x57 ^ 0x52, new EntityAIMoveTowardsRestriction(this, 0.6));
        this.tasks.addTask(0x27 ^ 0x21, new EntityAIVillagerMate(this));
        this.tasks.addTask(0x88 ^ 0x8F, new EntityAIFollowGolem(this));
        this.tasks.addTask(0x1A ^ 0x13, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0f, 1.0f));
        this.tasks.addTask(0x16 ^ 0x1F, new EntityAIVillagerInteract(this));
        this.tasks.addTask(0x55 ^ 0x5C, new EntityAIWander(this, 0.6));
        this.tasks.addTask(0xA9 ^ 0xA3, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f));
        this.setCanPickUpLoot(" ".length() != 0);
    }
    
    public boolean isTrading() {
        if (this.buyingPlayer != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public MerchantRecipeList getRecipes(final EntityPlayer entityPlayer) {
        if (this.buyingList == null) {
            this.populateBuyingList();
        }
        return this.buyingList;
    }
    
    @Override
    protected String getDeathSound() {
        return EntityVillager.I[0xBE ^ 0xAD];
    }
    
    public boolean isPlaying() {
        return this.isPlaying;
    }
    
    public void setProfession(final int n) {
        this.dataWatcher.updateObject(0x59 ^ 0x49, n);
    }
    
    public void setIsWillingToMate(final boolean isWillingToMate) {
        this.isWillingToMate = isWillingToMate;
    }
    
    @Override
    protected String getHurtSound() {
        return EntityVillager.I[0x8F ^ 0x9D];
    }
    
    @Override
    public void verifySellingItem(final ItemStack itemStack) {
        if (!this.worldObj.isRemote && this.livingSoundTime > -this.getTalkInterval() + (0xA4 ^ 0xB0)) {
            this.livingSoundTime = -this.getTalkInterval();
            if (itemStack != null) {
                this.playSound(EntityVillager.I[0x5B ^ 0x4E], this.getSoundVolume(), this.getSoundPitch());
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                this.playSound(EntityVillager.I[0x3A ^ 0x2C], this.getSoundVolume(), this.getSoundPitch());
            }
        }
    }
    
    @Override
    protected void updateEquipmentIfNeeded(final EntityItem entityItem) {
        final ItemStack entityItem2 = entityItem.getEntityItem();
        if (this.canVillagerPickupItem(entityItem2.getItem())) {
            final ItemStack func_174894_a = this.villagerInventory.func_174894_a(entityItem2);
            if (func_174894_a == null) {
                entityItem.setDead();
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else {
                entityItem2.stackSize = func_174894_a.stackSize;
            }
        }
    }
    
    @Override
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, IEntityLivingData onInitialSpawn) {
        onInitialSpawn = super.onInitialSpawn(difficultyInstance, onInitialSpawn);
        this.setProfession(this.worldObj.rand.nextInt(0xB2 ^ 0xB7));
        this.setAdditionalAItasks();
        return onInitialSpawn;
    }
    
    @Override
    public void useRecipe(final MerchantRecipe merchantRecipe) {
        merchantRecipe.incrementToolUses();
        this.livingSoundTime = -this.getTalkInterval();
        this.playSound(EntityVillager.I[0x33 ^ 0x27], this.getSoundVolume(), this.getSoundPitch());
        int n = "   ".length() + this.rand.nextInt(0x6A ^ 0x6E);
        if (merchantRecipe.getToolUses() == " ".length() || this.rand.nextInt(0xB1 ^ 0xB4) == 0) {
            this.timeUntilReset = (0x62 ^ 0x4A);
            this.needsInitilization = (" ".length() != 0);
            this.isWillingToMate = (" ".length() != 0);
            if (this.buyingPlayer != null) {
                this.lastBuyingPlayer = this.buyingPlayer.getName();
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                this.lastBuyingPlayer = null;
            }
            n += 5;
        }
        if (merchantRecipe.getItemToBuy().getItem() == Items.emerald) {
            this.wealth += merchantRecipe.getItemToBuy().stackSize;
        }
        if (merchantRecipe.getRewardsExp()) {
            this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY + 0.5, this.posZ, n));
        }
    }
    
    public void setMating(final boolean isMating) {
        this.isMating = isMating;
    }
    
    public boolean func_175557_cr() {
        int n;
        if (this.getProfession() == 0) {
            n = " ".length();
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        int n2;
        if (n != 0) {
            if (this.hasEnoughItems(0xB ^ 0xE)) {
                n2 = "".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
            else {
                n2 = " ".length();
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
        }
        else if (this.hasEnoughItems(" ".length())) {
            n2 = "".length();
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            n2 = " ".length();
        }
        return n2 != 0;
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == (0x48 ^ 0x44)) {
            this.spawnParticles(EnumParticleTypes.HEART);
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else if (b == (0x80 ^ 0x8D)) {
            this.spawnParticles(EnumParticleTypes.VILLAGER_ANGRY);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (b == (0x78 ^ 0x76)) {
            this.spawnParticles(EnumParticleTypes.VILLAGER_HAPPY);
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    public boolean isMating() {
        return this.isMating;
    }
    
    public boolean canAbondonItems() {
        return this.hasEnoughItems("  ".length());
    }
    
    public InventoryBasic getVillagerInventory() {
        return this.villagerInventory;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger(EntityVillager.I[" ".length()], this.getProfession());
        nbtTagCompound.setInteger(EntityVillager.I["  ".length()], this.wealth);
        nbtTagCompound.setInteger(EntityVillager.I["   ".length()], this.careerId);
        nbtTagCompound.setInteger(EntityVillager.I[0xBC ^ 0xB8], this.careerLevel);
        nbtTagCompound.setBoolean(EntityVillager.I[0x57 ^ 0x52], this.isWillingToMate);
        if (this.buyingList != null) {
            nbtTagCompound.setTag(EntityVillager.I[0x5A ^ 0x5C], this.buyingList.getRecipiesAsTags());
        }
        final NBTTagList list = new NBTTagList();
        int i = "".length();
        "".length();
        if (-1 >= 1) {
            throw null;
        }
        while (i < this.villagerInventory.getSizeInventory()) {
            final ItemStack stackInSlot = this.villagerInventory.getStackInSlot(i);
            if (stackInSlot != null) {
                list.appendTag(stackInSlot.writeToNBT(new NBTTagCompound()));
            }
            ++i;
        }
        nbtTagCompound.setTag(EntityVillager.I[0xC0 ^ 0xC7], list);
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    @Override
    protected void updateAITasks() {
        final int randomTickDivider = this.randomTickDivider - " ".length();
        this.randomTickDivider = randomTickDivider;
        if (randomTickDivider <= 0) {
            final BlockPos blockPos = new BlockPos(this);
            this.worldObj.getVillageCollection().addToVillagerPositionList(blockPos);
            this.randomTickDivider = (0x27 ^ 0x61) + this.rand.nextInt(0xB0 ^ 0x82);
            this.villageObj = this.worldObj.getVillageCollection().getNearestVillage(blockPos, 0x58 ^ 0x78);
            if (this.villageObj == null) {
                this.detachHome();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                this.setHomePosAndDistance(this.villageObj.getCenter(), (int)(this.villageObj.getVillageRadius() * 1.0f));
                if (this.isLookingForHome) {
                    this.isLookingForHome = ("".length() != 0);
                    this.villageObj.setDefaultPlayerReputation(0x6A ^ 0x6F);
                }
            }
        }
        if (!this.isTrading() && this.timeUntilReset > 0) {
            this.timeUntilReset -= " ".length();
            if (this.timeUntilReset <= 0) {
                if (this.needsInitilization) {
                    final Iterator<MerchantRecipe> iterator = this.buyingList.iterator();
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        final MerchantRecipe merchantRecipe = iterator.next();
                        if (merchantRecipe.isRecipeDisabled()) {
                            merchantRecipe.increaseMaxTradeUses(this.rand.nextInt(0x7A ^ 0x7C) + this.rand.nextInt(0x48 ^ 0x4E) + "  ".length());
                        }
                    }
                    this.populateBuyingList();
                    this.needsInitilization = ("".length() != 0);
                    if (this.villageObj != null && this.lastBuyingPlayer != null) {
                        this.worldObj.setEntityState(this, (byte)(0x3A ^ 0x34));
                        this.villageObj.setReputationForPlayer(this.lastBuyingPlayer, " ".length());
                    }
                }
                this.addPotionEffect(new PotionEffect(Potion.regeneration.id, 56 + 114 - 109 + 139, "".length()));
            }
        }
        super.updateAITasks();
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x8C ^ 0x9C, "".length());
    }
    
    public boolean isFarmItemInInventory() {
        int i = "".length();
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (i < this.villagerInventory.getSizeInventory()) {
            final ItemStack stackInSlot = this.villagerInventory.getStackInSlot(i);
            if (stackInSlot != null && (stackInSlot.getItem() == Items.wheat_seeds || stackInSlot.getItem() == Items.potato || stackInSlot.getItem() == Items.carrot)) {
                return " ".length() != 0;
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean allowLeashing() {
        return "".length() != 0;
    }
    
    @Override
    public EntityVillager createChild(final EntityAgeable entityAgeable) {
        final EntityVillager entityVillager = new EntityVillager(this.worldObj);
        entityVillager.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(entityVillager)), null);
        return entityVillager;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        final String customNameTag = this.getCustomNameTag();
        if (customNameTag != null && customNameTag.length() > 0) {
            final ChatComponentText chatComponentText = new ChatComponentText(customNameTag);
            chatComponentText.getChatStyle().setChatHoverEvent(this.getHoverEvent());
            chatComponentText.getChatStyle().setInsertion(this.getUniqueID().toString());
            return chatComponentText;
        }
        if (this.buyingList == null) {
            this.populateBuyingList();
        }
        String s = null;
        switch (this.getProfession()) {
            case 0: {
                if (this.careerId == " ".length()) {
                    s = EntityVillager.I[0x44 ^ 0x53];
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                    break;
                }
                else if (this.careerId == "  ".length()) {
                    s = EntityVillager.I[0x0 ^ 0x18];
                    "".length();
                    if (0 >= 2) {
                        throw null;
                    }
                    break;
                }
                else if (this.careerId == "   ".length()) {
                    s = EntityVillager.I[0x27 ^ 0x3E];
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                    break;
                }
                else {
                    if (this.careerId != (0x4F ^ 0x4B)) {
                        break;
                    }
                    s = EntityVillager.I[0xAC ^ 0xB6];
                    "".length();
                    if (4 < 4) {
                        throw null;
                    }
                    break;
                }
                break;
            }
            case 1: {
                s = EntityVillager.I[0x33 ^ 0x28];
                "".length();
                if (1 < 1) {
                    throw null;
                }
                break;
            }
            case 2: {
                s = EntityVillager.I[0x9C ^ 0x80];
                "".length();
                if (0 >= 3) {
                    throw null;
                }
                break;
            }
            case 3: {
                if (this.careerId == " ".length()) {
                    s = EntityVillager.I[0x81 ^ 0x9C];
                    "".length();
                    if (3 >= 4) {
                        throw null;
                    }
                    break;
                }
                else if (this.careerId == "  ".length()) {
                    s = EntityVillager.I[0x26 ^ 0x38];
                    "".length();
                    if (3 == 0) {
                        throw null;
                    }
                    break;
                }
                else {
                    if (this.careerId != "   ".length()) {
                        break;
                    }
                    s = EntityVillager.I[0x94 ^ 0x8B];
                    "".length();
                    if (1 < -1) {
                        throw null;
                    }
                    break;
                }
                break;
            }
            case 4: {
                if (this.careerId == " ".length()) {
                    s = EntityVillager.I[0x3F ^ 0x1F];
                    "".length();
                    if (4 == 1) {
                        throw null;
                    }
                    break;
                }
                else {
                    if (this.careerId == "  ".length()) {
                        s = EntityVillager.I[0x73 ^ 0x52];
                        break;
                    }
                    break;
                }
                break;
            }
        }
        if (s != null) {
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(EntityVillager.I[0x61 ^ 0x43] + s, new Object["".length()]);
            chatComponentTranslation.getChatStyle().setChatHoverEvent(this.getHoverEvent());
            chatComponentTranslation.getChatStyle().setInsertion(this.getUniqueID().toString());
            return chatComponentTranslation;
        }
        return super.getDisplayName();
    }
    
    @Override
    public void setRecipes(final MerchantRecipeList list) {
    }
    
    public EntityVillager(final World world) {
        this(world, "".length());
    }
    
    @Override
    protected void onGrowingAdult() {
        if (this.getProfession() == 0) {
            this.tasks.addTask(0xB8 ^ 0xB0, new EntityAIHarvestFarmland(this, 0.6));
        }
        super.onGrowingAdult();
    }
    
    @Override
    public void setCustomer(final EntityPlayer buyingPlayer) {
        this.buyingPlayer = buyingPlayer;
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        if (this.villageObj != null) {
            final Entity entity = damageSource.getEntity();
            if (entity != null) {
                if (entity instanceof EntityPlayer) {
                    this.villageObj.setReputationForPlayer(entity.getName(), -"  ".length());
                    "".length();
                    if (0 == 3) {
                        throw null;
                    }
                }
                else if (entity instanceof IMob) {
                    this.villageObj.endMatingSeason();
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
            }
            else if (this.worldObj.getClosestPlayerToEntity(this, 16.0) != null) {
                this.villageObj.endMatingSeason();
            }
        }
        super.onDeath(damageSource);
    }
    
    private boolean hasEnoughItems(final int n) {
        int n2;
        if (this.getProfession() == 0) {
            n2 = " ".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final int n3 = n2;
        int i = "".length();
        "".length();
        if (1 == 3) {
            throw null;
        }
        while (i < this.villagerInventory.getSizeInventory()) {
            final ItemStack stackInSlot = this.villagerInventory.getStackInSlot(i);
            if (stackInSlot != null) {
                if ((stackInSlot.getItem() == Items.bread && stackInSlot.stackSize >= "   ".length() * n) || (stackInSlot.getItem() == Items.potato && stackInSlot.stackSize >= (0x6D ^ 0x61) * n) || (stackInSlot.getItem() == Items.carrot && stackInSlot.stackSize >= (0x53 ^ 0x5F) * n)) {
                    return " ".length() != 0;
                }
                if (n3 != 0 && stackInSlot.getItem() == Items.wheat && stackInSlot.stackSize >= (0x2D ^ 0x24) * n) {
                    return " ".length() != 0;
                }
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    static class ListEnchantedItemForEmeralds implements ITradeList
    {
        public ItemStack field_179407_a;
        public PriceInfo field_179406_b;
        
        @Override
        public void modifyMerchantRecipeList(final MerchantRecipeList list, final Random random) {
            int n = " ".length();
            if (this.field_179406_b != null) {
                n = this.field_179406_b.getPrice(random);
            }
            list.add(new MerchantRecipe(new ItemStack(Items.emerald, n, "".length()), EnchantmentHelper.addRandomEnchantment(random, new ItemStack(this.field_179407_a.getItem(), " ".length(), this.field_179407_a.getMetadata()), (0x6B ^ 0x6E) + random.nextInt(0xCD ^ 0xC2))));
        }
        
        public ListEnchantedItemForEmeralds(final Item item, final PriceInfo field_179406_b) {
            this.field_179407_a = new ItemStack(item);
            this.field_179406_b = field_179406_b;
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    static class PriceInfo extends Tuple<Integer, Integer>
    {
        public int getPrice(final Random random) {
            int intValue;
            if (((Tuple<Integer, B>)this).getFirst() >= ((Tuple<A, Integer>)this).getSecond()) {
                intValue = ((Tuple<Integer, B>)this).getFirst();
                "".length();
                if (-1 == 2) {
                    throw null;
                }
            }
            else {
                intValue = ((Tuple<Integer, B>)this).getFirst() + random.nextInt(((Tuple<A, Integer>)this).getSecond() - ((Tuple<Integer, B>)this).getFirst() + " ".length());
            }
            return intValue;
        }
        
        public PriceInfo(final int n, final int n2) {
            super(n, n2);
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    interface ITradeList
    {
        void modifyMerchantRecipeList(final MerchantRecipeList p0, final Random p1);
    }
    
    static class ListItemForEmeralds implements ITradeList
    {
        public ItemStack field_179403_a;
        public PriceInfo field_179402_b;
        
        public ListItemForEmeralds(final ItemStack field_179403_a, final PriceInfo field_179402_b) {
            this.field_179403_a = field_179403_a;
            this.field_179402_b = field_179402_b;
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public ListItemForEmeralds(final Item item, final PriceInfo field_179402_b) {
            this.field_179403_a = new ItemStack(item);
            this.field_179402_b = field_179402_b;
        }
        
        @Override
        public void modifyMerchantRecipeList(final MerchantRecipeList list, final Random random) {
            int n = " ".length();
            if (this.field_179402_b != null) {
                n = this.field_179402_b.getPrice(random);
            }
            ItemStack itemStack;
            ItemStack itemStack2;
            if (n < 0) {
                itemStack = new ItemStack(Items.emerald, " ".length(), "".length());
                itemStack2 = new ItemStack(this.field_179403_a.getItem(), -n, this.field_179403_a.getMetadata());
                "".length();
                if (4 == 3) {
                    throw null;
                }
            }
            else {
                itemStack = new ItemStack(Items.emerald, n, "".length());
                itemStack2 = new ItemStack(this.field_179403_a.getItem(), " ".length(), this.field_179403_a.getMetadata());
            }
            list.add(new MerchantRecipe(itemStack, itemStack2));
        }
    }
    
    static class ItemAndEmeraldToItem implements ITradeList
    {
        public ItemStack field_179411_a;
        public PriceInfo field_179408_d;
        public PriceInfo field_179409_b;
        public ItemStack field_179410_c;
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (1 < 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public ItemAndEmeraldToItem(final Item item, final PriceInfo field_179409_b, final Item item2, final PriceInfo field_179408_d) {
            this.field_179411_a = new ItemStack(item);
            this.field_179409_b = field_179409_b;
            this.field_179410_c = new ItemStack(item2);
            this.field_179408_d = field_179408_d;
        }
        
        @Override
        public void modifyMerchantRecipeList(final MerchantRecipeList list, final Random random) {
            int n = " ".length();
            if (this.field_179409_b != null) {
                n = this.field_179409_b.getPrice(random);
            }
            int n2 = " ".length();
            if (this.field_179408_d != null) {
                n2 = this.field_179408_d.getPrice(random);
            }
            list.add(new MerchantRecipe(new ItemStack(this.field_179411_a.getItem(), n, this.field_179411_a.getMetadata()), new ItemStack(Items.emerald), new ItemStack(this.field_179410_c.getItem(), n2, this.field_179410_c.getMetadata())));
        }
    }
    
    static class EmeraldForItems implements ITradeList
    {
        public PriceInfo price;
        public Item sellItem;
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (false) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public void modifyMerchantRecipeList(final MerchantRecipeList list, final Random random) {
            int n = " ".length();
            if (this.price != null) {
                n = this.price.getPrice(random);
            }
            list.add(new MerchantRecipe(new ItemStack(this.sellItem, n, "".length()), Items.emerald));
        }
        
        public EmeraldForItems(final Item sellItem, final PriceInfo price) {
            this.sellItem = sellItem;
            this.price = price;
        }
    }
    
    static class ListEnchantedBookForEmeralds implements ITradeList
    {
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public void modifyMerchantRecipeList(final MerchantRecipeList list, final Random random) {
            final Enchantment enchantment = Enchantment.enchantmentsBookList[random.nextInt(Enchantment.enchantmentsBookList.length)];
            final int randomIntegerInRange = MathHelper.getRandomIntegerInRange(random, enchantment.getMinLevel(), enchantment.getMaxLevel());
            final ItemStack enchantedItemStack = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, randomIntegerInRange));
            int n = "  ".length() + random.nextInt((0xC6 ^ 0xC3) + randomIntegerInRange * (0x6B ^ 0x61)) + "   ".length() * randomIntegerInRange;
            if (n > (0x38 ^ 0x78)) {
                n = (0x6A ^ 0x2A);
            }
            list.add(new MerchantRecipe(new ItemStack(Items.book), new ItemStack(Items.emerald, n), enchantedItemStack));
        }
    }
}
