package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;

public class ItemBanner extends ItemBlock
{
    private static final String[] I;
    
    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.tabDecorations;
    }
    
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List<String> list, final boolean b) {
        final NBTTagCompound subCompound = itemStack.getSubCompound(ItemBanner.I["  ".length()], "".length() != 0);
        if (subCompound != null && subCompound.hasKey(ItemBanner.I["   ".length()])) {
            final NBTTagList tagList = subCompound.getTagList(ItemBanner.I[0x3A ^ 0x3E], 0x62 ^ 0x68);
            int length = "".length();
            "".length();
            if (4 == 2) {
                throw null;
            }
            while (length < tagList.tagCount() && length < (0xB1 ^ 0xB7)) {
                final NBTTagCompound compoundTag = tagList.getCompoundTagAt(length);
                final EnumDyeColor byDyeDamage = EnumDyeColor.byDyeDamage(compoundTag.getInteger(ItemBanner.I[0xB5 ^ 0xB0]));
                final TileEntityBanner.EnumBannerPattern patternByID = TileEntityBanner.EnumBannerPattern.getPatternByID(compoundTag.getString(ItemBanner.I[0xBA ^ 0xBC]));
                if (patternByID != null) {
                    list.add(StatCollector.translateToLocal(ItemBanner.I[0x30 ^ 0x37] + patternByID.getPatternName() + ItemBanner.I[0xF ^ 0x7] + byDyeDamage.getUnlocalizedName()));
                }
                ++length;
            }
        }
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        final EnumDyeColor[] values;
        final int length = (values = EnumDyeColor.values()).length;
        int i = "".length();
        "".length();
        if (1 >= 3) {
            throw null;
        }
        while (i < length) {
            final EnumDyeColor enumDyeColor = values[i];
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            TileEntityBanner.func_181020_a(nbtTagCompound, enumDyeColor.getDyeDamage(), null);
            final NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setTag(ItemBanner.I[0xB2 ^ 0xBB], nbtTagCompound);
            final ItemStack itemStack = new ItemStack(item, " ".length(), enumDyeColor.getDyeDamage());
            itemStack.setTagCompound(tagCompound);
            list.add(itemStack);
            ++i;
        }
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        if (n == 0) {
            return 3445013 + 6435769 - 3768593 + 10665026;
        }
        return this.getBaseColor(itemStack).getMapColor().colorValue;
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemValues, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing == EnumFacing.DOWN) {
            return "".length() != 0;
        }
        if (!world.getBlockState(offset).getBlock().getMaterial().isSolid()) {
            return "".length() != 0;
        }
        offset = offset.offset(enumFacing);
        if (!entityPlayer.canPlayerEdit(offset, enumFacing, itemValues)) {
            return "".length() != 0;
        }
        if (!Blocks.standing_banner.canPlaceBlockAt(world, offset)) {
            return "".length() != 0;
        }
        if (world.isRemote) {
            return " ".length() != 0;
        }
        if (enumFacing == EnumFacing.UP) {
            world.setBlockState(offset, Blocks.standing_banner.getDefaultState().withProperty((IProperty<Comparable>)BlockStandingSign.ROTATION, MathHelper.floor_double((entityPlayer.rotationYaw + 180.0f) * 16.0f / 360.0f + 0.5) & (0x8 ^ 0x7)), "   ".length());
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            world.setBlockState(offset, Blocks.wall_banner.getDefaultState().withProperty((IProperty<Comparable>)BlockWallSign.FACING, enumFacing), "   ".length());
        }
        itemValues.stackSize -= " ".length();
        final TileEntity tileEntity = world.getTileEntity(offset);
        if (tileEntity instanceof TileEntityBanner) {
            ((TileEntityBanner)tileEntity).setItemValues(itemValues);
        }
        return " ".length() != 0;
    }
    
    @Override
    public String getItemStackDisplayName(final ItemStack itemStack) {
        return StatCollector.translateToLocal(String.valueOf(ItemBanner.I["".length()]) + this.getBaseColor(itemStack).getUnlocalizedName() + ItemBanner.I[" ".length()]);
    }
    
    public ItemBanner() {
        super(Blocks.standing_banner);
        this.maxStackSize = (0xA ^ 0x1A);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHasSubtypes(" ".length() != 0);
        this.setMaxDamage("".length());
    }
    
    private EnumDyeColor getBaseColor(final ItemStack itemStack) {
        final NBTTagCompound subCompound = itemStack.getSubCompound(ItemBanner.I[0x20 ^ 0x2A], "".length() != 0);
        EnumDyeColor enumDyeColor;
        if (subCompound != null && subCompound.hasKey(ItemBanner.I[0x67 ^ 0x6C])) {
            enumDyeColor = EnumDyeColor.byDyeDamage(subCompound.getInteger(ItemBanner.I[0x93 ^ 0x9F]));
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        else {
            enumDyeColor = EnumDyeColor.byDyeDamage(itemStack.getMetadata());
        }
        return enumDyeColor;
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[0xAA ^ 0xA7])["".length()] = I("\u0018\f\u0010,i\u0013\u0019\u001b/\"\u0003V", "qxuAG");
        ItemBanner.I[" ".length()] = I("i\u001a8\t<", "GtYdY");
        ItemBanner.I["  ".length()] = I("2\u000b\n\u0011\u000e5\t\u0011\u001b\u0011\t3\u0004\u0015", "pgere");
        ItemBanner.I["   ".length()] = I("\u001a\u0013\u001b3<8\u001c\u001c", "JroGY");
        ItemBanner.I[0x7E ^ 0x7A] = I("\u0000\u0011'<&\"\u001e ", "PpSHC");
        ItemBanner.I[0x16 ^ 0x13] = I("5\u001c.&\u0013", "vsBIa");
        ItemBanner.I[0x13 ^ 0x15] = I("<\u00162$\f\u001e\u0019", "lwFPi");
        ItemBanner.I[0x36 ^ 0x31] = I("\"\u0007<\u001f])\u00127\u001c\u00169]", "KsYrs");
        ItemBanner.I[0x27 ^ 0x2F] = I("{", "UUIQM");
        ItemBanner.I[0x67 ^ 0x6E] = I("\u0000%\u0017\u0002\u0018\u0007'\f\b\u0007;\u001d\u0019\u0006", "BIxas");
        ItemBanner.I[0xBD ^ 0xB7] = I("%\u0014)-:\"\u00162'%\u001e,')", "gxFNQ");
        ItemBanner.I[0x52 ^ 0x59] = I("\u0006\u0005\u001c\t", "Ddola");
        ItemBanner.I[0xB6 ^ 0xBA] = I("+\u0003\u0005\u0003", "ibvfq");
    }
}
