package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.util.*;
import com.google.common.collect.*;

public class ItemRecord extends Item
{
    private static final Map field_150928_b;
    public final String recordName;
    
    protected ItemRecord(final String p_i45350_1_) {
        this.recordName = p_i45350_1_;
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabMisc);
        ItemRecord.field_150928_b.put("records." + p_i45350_1_, this);
    }
    
    public static ItemRecord getRecord(final String p_150926_0_) {
        return ItemRecord.field_150928_b.get(p_150926_0_);
    }
    
    @Override
    public boolean onItemUse(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        final IBlockState var9 = worldIn.getBlockState(pos);
        if (var9.getBlock() != Blocks.jukebox || (boolean)var9.getValue(BlockJukebox.HAS_RECORD)) {
            return false;
        }
        if (worldIn.isRemote) {
            return true;
        }
        ((BlockJukebox)Blocks.jukebox).insertRecord(worldIn, pos, var9, stack);
        worldIn.playAuxSFXAtEntity(null, 1005, pos, Item.getIdFromItem(this));
        --stack.stackSize;
        return true;
    }
    
    @Override
    public void addInformation(final ItemStack stack, final EntityPlayer playerIn, final List tooltip, final boolean advanced) {
        tooltip.add(this.getRecordNameLocal());
    }
    
    public String getRecordNameLocal() {
        return StatCollector.translateToLocal("item.record." + this.recordName + ".desc");
    }
    
    @Override
    public EnumRarity getRarity(final ItemStack stack) {
        return EnumRarity.RARE;
    }
    
    static {
        field_150928_b = Maps.newHashMap();
    }
}
