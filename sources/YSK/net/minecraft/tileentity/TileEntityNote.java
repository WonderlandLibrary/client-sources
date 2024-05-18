package net.minecraft.tileentity;

import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class TileEntityNote extends TileEntity
{
    public boolean previousRedstoneState;
    private static final String[] I;
    public byte note;
    
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
            if (2 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void changePitch() {
        this.note = (byte)((this.note + " ".length()) % (0x71 ^ 0x68));
        this.markDirty();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("%\f2\u001f", "KcFzR");
        TileEntityNote.I[" ".length()] = I("\u0017\u001f0\u0001", "ypDdp");
    }
    
    static {
        I();
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setByte(TileEntityNote.I["".length()], this.note);
    }
    
    public void triggerNote(final World world, final BlockPos blockPos) {
        if (world.getBlockState(blockPos.up()).getBlock().getMaterial() == Material.air) {
            final Material material = world.getBlockState(blockPos.down()).getBlock().getMaterial();
            int n = "".length();
            if (material == Material.rock) {
                n = " ".length();
            }
            if (material == Material.sand) {
                n = "  ".length();
            }
            if (material == Material.glass) {
                n = "   ".length();
            }
            if (material == Material.wood) {
                n = (0x7F ^ 0x7B);
            }
            world.addBlockEvent(blockPos, Blocks.noteblock, n, this.note);
        }
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.note = nbtTagCompound.getByte(TileEntityNote.I[" ".length()]);
        this.note = (byte)MathHelper.clamp_int(this.note, "".length(), 0x4F ^ 0x57);
    }
}
