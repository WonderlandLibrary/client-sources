package net.minecraft.tileentity;

import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;

public class TileEntityNote extends TileEntity
{
    public byte note;
    public boolean previousRedstoneState;
    
    @Override
    public void writeToNBT(final NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setByte("note", this.note);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.note = compound.getByte("note");
        this.note = (byte)MathHelper.clamp_int(this.note, 0, 24);
    }
    
    public void changePitch() {
        this.note = (byte)((this.note + 1) % 25);
        this.markDirty();
    }
    
    public void func_175108_a(final World worldIn, final BlockPos p_175108_2_) {
        if (worldIn.getBlockState(p_175108_2_.offsetUp()).getBlock().getMaterial() == Material.air) {
            final Material var3 = worldIn.getBlockState(p_175108_2_.offsetDown()).getBlock().getMaterial();
            byte var4 = 0;
            if (var3 == Material.rock) {
                var4 = 1;
            }
            if (var3 == Material.sand) {
                var4 = 2;
            }
            if (var3 == Material.glass) {
                var4 = 3;
            }
            if (var3 == Material.wood) {
                var4 = 4;
            }
            worldIn.addBlockEvent(p_175108_2_, Blocks.noteblock, var4, this.note);
        }
    }
}
