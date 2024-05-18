package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.stats.*;
import net.minecraft.block.state.*;
import com.google.common.collect.*;

public class ItemRecord extends Item
{
    private static final Map<String, ItemRecord> RECORDS;
    private static final String[] I;
    public final String recordName;
    
    public String getRecordNameLocal() {
        return StatCollector.translateToLocal(ItemRecord.I[" ".length()] + this.recordName + ItemRecord.I["  ".length()]);
    }
    
    protected ItemRecord(final String recordName) {
        this.recordName = recordName;
        this.maxStackSize = " ".length();
        this.setCreativeTab(CreativeTabs.tabMisc);
        ItemRecord.RECORDS.put(ItemRecord.I["".length()] + recordName, this);
    }
    
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List<String> list, final boolean b) {
        list.add(this.getRecordNameLocal());
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        final IBlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() != Blocks.jukebox || blockState.getValue((IProperty<Boolean>)BlockJukebox.HAS_RECORD)) {
            return "".length() != 0;
        }
        if (world.isRemote) {
            return " ".length() != 0;
        }
        ((BlockJukebox)Blocks.jukebox).insertRecord(world, blockPos, blockState, itemStack);
        world.playAuxSFXAtEntity(null, 350 + 427 + 128 + 100, blockPos, Item.getIdFromItem(this));
        itemStack.stackSize -= " ".length();
        entityPlayer.triggerAchievement(StatList.field_181740_X);
        return " ".length() != 0;
    }
    
    @Override
    public EnumRarity getRarity(final ItemStack itemStack) {
        return EnumRarity.RARE;
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("&\b\u0005\"\u00040\u001eH", "TmfMv");
        ItemRecord.I[" ".length()] = I("\u000b\u001d=\"G\u0010\f; \u001b\u0006G", "biXOi");
        ItemRecord.I["  ".length()] = I("F46?5", "hPSLV");
    }
    
    public static ItemRecord getRecord(final String s) {
        return ItemRecord.RECORDS.get(s);
    }
    
    static {
        I();
        RECORDS = Maps.newHashMap();
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
            if (0 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
