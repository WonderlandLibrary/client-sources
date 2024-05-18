package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import com.mojang.authlib.*;
import java.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class ItemSkull extends Item
{
    private static final String[] skullTypes;
    private static final String[] I;
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        int i = "".length();
        "".length();
        if (-1 == 1) {
            throw null;
        }
        while (i < ItemSkull.skullTypes.length) {
            list.add(new ItemStack(item, " ".length(), i));
            ++i;
        }
    }
    
    public ItemSkull() {
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setMaxDamage("".length());
        this.setHasSubtypes(" ".length() != 0);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing == EnumFacing.DOWN) {
            return "".length() != 0;
        }
        if (!world.getBlockState(offset).getBlock().isReplaceable(world, offset)) {
            if (!world.getBlockState(offset).getBlock().getMaterial().isSolid()) {
                return "".length() != 0;
            }
            offset = offset.offset(enumFacing);
        }
        if (!entityPlayer.canPlayerEdit(offset, enumFacing, itemStack)) {
            return "".length() != 0;
        }
        if (!Blocks.skull.canPlaceBlockAt(world, offset)) {
            return "".length() != 0;
        }
        if (!world.isRemote) {
            world.setBlockState(offset, Blocks.skull.getDefaultState().withProperty((IProperty<Comparable>)BlockSkull.FACING, enumFacing), "   ".length());
            int length = "".length();
            if (enumFacing == EnumFacing.UP) {
                length = (MathHelper.floor_double(entityPlayer.rotationYaw * 16.0f / 360.0f + 0.5) & (0x28 ^ 0x27));
            }
            final TileEntity tileEntity = world.getTileEntity(offset);
            if (tileEntity instanceof TileEntitySkull) {
                final TileEntitySkull tileEntitySkull = (TileEntitySkull)tileEntity;
                if (itemStack.getMetadata() == "   ".length()) {
                    GameProfile gameProfileFromNBT = null;
                    if (itemStack.hasTagCompound()) {
                        final NBTTagCompound tagCompound = itemStack.getTagCompound();
                        if (tagCompound.hasKey(ItemSkull.I[0x6 ^ 0x3], 0x51 ^ 0x5B)) {
                            gameProfileFromNBT = NBTUtil.readGameProfileFromNBT(tagCompound.getCompoundTag(ItemSkull.I[0x73 ^ 0x75]));
                            "".length();
                            if (3 == 2) {
                                throw null;
                            }
                        }
                        else if (tagCompound.hasKey(ItemSkull.I[0x1F ^ 0x18], 0x86 ^ 0x8E) && tagCompound.getString(ItemSkull.I[0x7A ^ 0x72]).length() > 0) {
                            gameProfileFromNBT = new GameProfile((UUID)null, tagCompound.getString(ItemSkull.I[0xCF ^ 0xC6]));
                        }
                    }
                    tileEntitySkull.setPlayerProfile(gameProfileFromNBT);
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else {
                    tileEntitySkull.setType(itemStack.getMetadata());
                }
                tileEntitySkull.setSkullRotation(length);
                Blocks.skull.checkWitherSpawn(world, offset, tileEntitySkull);
            }
            itemStack.stackSize -= " ".length();
        }
        return " ".length() != 0;
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        int n = itemStack.getMetadata();
        if (n < 0 || n >= ItemSkull.skullTypes.length) {
            n = "".length();
        }
        return String.valueOf(super.getUnlocalizedName()) + ItemSkull.I[0x82 ^ 0x88] + ItemSkull.skullTypes[n];
    }
    
    private static void I() {
        (I = new String[0x3F ^ 0x28])["".length()] = I("\"\b1\u0002\b%\f:", "QcTnm");
        ItemSkull.I[" ".length()] = I("#\r\u0003=$&", "TdwUA");
        ItemSkull.I["  ".length()] = I("\u0014\u000b\f53\u000b", "ndaWZ");
        ItemSkull.I["   ".length()] = I("2)+\u0017", "QAJev");
        ItemSkull.I[0x59 ^ 0x5D] = I("\f\u0007/\u001d#\n\u0007", "ouJxS");
        ItemSkull.I[0x34 ^ 0x31] = I("2-\u0005!5.1\u001e(+", "aFpMY");
        ItemSkull.I[0x45 ^ 0x43] = I("#\u0000'\u001c.?\u001c<\u00150", "pkRpB");
        ItemSkull.I[0x80 ^ 0x87] = I("\u0004.1\u0006\u0007\u00182*\u000f\u0019", "WEDjk");
        ItemSkull.I[0xA3 ^ 0xAB] = I("\u001c(6\t)\u00004-\u00007", "OCCeE");
        ItemSkull.I[0xA4 ^ 0xAD] = I("\u000b#\u0002!(\u0017?\u0019(6", "XHwMD");
        ItemSkull.I[0x16 ^ 0x1C] = I("E", "kzfUu");
        ItemSkull.I[0x97 ^ 0x9C] = I("6\u001d\u0007\u000b'*\u0001\u001c\u00029", "evrgK");
        ItemSkull.I[0x13 ^ 0x1F] = I("!'\u001f\u000bc;8\u000f\n!f#\u0016\u00074-!T\b,%6", "HSzfM");
        ItemSkull.I[0x9F ^ 0x92] = I("\u00063<\u0004=\u001a/'\r#", "UXIhQ");
        ItemSkull.I[0x4C ^ 0x42] = I("8\u0002!\u00029$\u001e:\u000b'", "kiTnU");
        ItemSkull.I[0x31 ^ 0x3E] = I("\u0004\u001e!\u0015;\u0018\u0002:\u001c%", "WuTyW");
        ItemSkull.I[0x5 ^ 0x15] = I("\n\u0015\b-", "DteHS");
        ItemSkull.I[0x1C ^ 0xD] = I("\r=\u0011/`\u0017\"\u0001.\"J9\u0018#7\u0001;Z,/\t,", "dItBN");
        ItemSkull.I[0x16 ^ 0x4] = I("\u000b98<", "EXUYT");
        ItemSkull.I[0x63 ^ 0x70] = I("#\u001f\"\u000e\u001b?\u00039\u0007\u0005", "ptWbw");
        ItemSkull.I[0x6F ^ 0x7B] = I(" \u0002?6(<\u001e$?6", "siJZD");
        ItemSkull.I[0xD7 ^ 0xC2] = I("5\r6\u0002-)\u0011-\u000b3", "ffCnA");
        ItemSkull.I[0x8E ^ 0x98] = I("*\u001e\u0014\u001e\r6\u0002\u000f\u0017\u0013", "yuara");
    }
    
    static {
        I();
        final String[] skullTypes2 = new String[0x90 ^ 0x95];
        skullTypes2["".length()] = ItemSkull.I["".length()];
        skullTypes2[" ".length()] = ItemSkull.I[" ".length()];
        skullTypes2["  ".length()] = ItemSkull.I["  ".length()];
        skullTypes2["   ".length()] = ItemSkull.I["   ".length()];
        skullTypes2[0xB6 ^ 0xB2] = ItemSkull.I[0xA7 ^ 0xA3];
        skullTypes = skullTypes2;
    }
    
    @Override
    public boolean updateItemStackNBT(final NBTTagCompound nbtTagCompound) {
        super.updateItemStackNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey(ItemSkull.I[0x3C ^ 0x2F], 0x11 ^ 0x19) && nbtTagCompound.getString(ItemSkull.I[0x4 ^ 0x10]).length() > 0) {
            nbtTagCompound.setTag(ItemSkull.I[0x48 ^ 0x5E], NBTUtil.writeGameProfile(new NBTTagCompound(), TileEntitySkull.updateGameprofile(new GameProfile((UUID)null, nbtTagCompound.getString(ItemSkull.I[0x88 ^ 0x9D])))));
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public int getMetadata(final int n) {
        return n;
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
            if (1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String getItemStackDisplayName(final ItemStack itemStack) {
        if (itemStack.getMetadata() == "   ".length() && itemStack.hasTagCompound()) {
            if (itemStack.getTagCompound().hasKey(ItemSkull.I[0x6C ^ 0x67], 0x1D ^ 0x15)) {
                final String s = ItemSkull.I[0x32 ^ 0x3E];
                final Object[] array = new Object[" ".length()];
                array["".length()] = itemStack.getTagCompound().getString(ItemSkull.I[0xAE ^ 0xA3]);
                return StatCollector.translateToLocalFormatted(s, array);
            }
            if (itemStack.getTagCompound().hasKey(ItemSkull.I[0x6D ^ 0x63], 0x1F ^ 0x15)) {
                final NBTTagCompound compoundTag = itemStack.getTagCompound().getCompoundTag(ItemSkull.I[0x71 ^ 0x7E]);
                if (compoundTag.hasKey(ItemSkull.I[0x50 ^ 0x40], 0xB8 ^ 0xB0)) {
                    final String s2 = ItemSkull.I[0xA8 ^ 0xB9];
                    final Object[] array2 = new Object[" ".length()];
                    array2["".length()] = compoundTag.getString(ItemSkull.I[0x27 ^ 0x35]);
                    return StatCollector.translateToLocalFormatted(s2, array2);
                }
            }
        }
        return super.getItemStackDisplayName(itemStack);
    }
}
