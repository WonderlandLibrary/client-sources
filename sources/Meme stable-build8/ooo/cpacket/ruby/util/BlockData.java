package ooo.cpacket.ruby.util;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BlockData
{
    public BlockPos position;
    public EnumFacing face;
    
    public BlockData(final BlockPos position, final EnumFacing face) {
        this.position = position;
        this.face = face;
    }
}