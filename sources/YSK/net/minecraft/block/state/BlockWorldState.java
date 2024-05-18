package net.minecraft.block.state;

import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import com.google.common.base.*;

public class BlockWorldState
{
    private IBlockState state;
    private TileEntity tileEntity;
    private final boolean field_181628_c;
    private boolean tileEntityInitialized;
    private final World world;
    private final BlockPos pos;
    
    public BlockWorldState(final World world, final BlockPos pos, final boolean field_181628_c) {
        this.world = world;
        this.pos = pos;
        this.field_181628_c = field_181628_c;
    }
    
    public static Predicate<BlockWorldState> hasState(final Predicate<IBlockState> predicate) {
        return (Predicate<BlockWorldState>)new Predicate<BlockWorldState>(predicate) {
            private final Predicate val$p_177510_0_;
            
            public boolean apply(final BlockWorldState blockWorldState) {
                if (blockWorldState != null && this.val$p_177510_0_.apply((Object)blockWorldState.getBlockState())) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
            
            public boolean apply(final Object o) {
                return this.apply((BlockWorldState)o);
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
                    if (3 <= -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
    }
    
    public BlockPos getPos() {
        return this.pos;
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
            if (-1 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public IBlockState getBlockState() {
        if (this.state == null && (this.field_181628_c || this.world.isBlockLoaded(this.pos))) {
            this.state = this.world.getBlockState(this.pos);
        }
        return this.state;
    }
    
    public TileEntity getTileEntity() {
        if (this.tileEntity == null && !this.tileEntityInitialized) {
            this.tileEntity = this.world.getTileEntity(this.pos);
            this.tileEntityInitialized = (" ".length() != 0);
        }
        return this.tileEntity;
    }
}
