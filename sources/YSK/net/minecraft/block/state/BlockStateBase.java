package net.minecraft.block.state;

import net.minecraft.util.*;
import com.google.common.base.*;
import net.minecraft.block.*;
import com.google.common.collect.*;
import net.minecraft.block.properties.*;
import java.util.*;

public abstract class BlockStateBase implements IBlockState
{
    private static final Joiner COMMA_JOINER;
    private static final String[] I;
    private int blockStateId;
    private ResourceLocation blockLocation;
    private int metadata;
    private static final String __OBFID;
    private int blockId;
    private static final Function MAP_ENTRY_TO_STRING;
    
    public int getMetadata() {
        if (this.metadata < 0) {
            this.metadata = this.getBlock().getMetaFromState(this);
        }
        return this.metadata;
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
            if (2 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(Block.blockRegistry.getNameForObject(this.getBlock()));
        if (!this.getProperties().isEmpty()) {
            sb.append(BlockStateBase.I[" ".length()]);
            BlockStateBase.COMMA_JOINER.appendTo(sb, Iterables.transform((Iterable)this.getProperties().entrySet(), BlockStateBase.MAP_ENTRY_TO_STRING));
            sb.append(BlockStateBase.I["  ".length()]);
        }
        return sb.toString();
    }
    
    public int getBlockId() {
        if (this.blockId < 0) {
            this.blockId = Block.getIdFromBlock(this.getBlock());
        }
        return this.blockId;
    }
    
    public int getBlockStateId() {
        if (this.blockStateId < 0) {
            this.blockStateId = Block.getStateId(this);
        }
        return this.blockStateId;
    }
    
    protected static Object cyclePropertyValue(final Collection collection, final Object o) {
        final Iterator<Object> iterator = collection.iterator();
        "".length();
        if (4 <= 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (iterator.next().equals(o)) {
                if (iterator.hasNext()) {
                    return iterator.next();
                }
                return collection.iterator().next();
            }
        }
        return iterator.next();
    }
    
    public ResourceLocation getBlockLocation() {
        if (this.blockLocation == null) {
            this.blockLocation = Block.blockRegistry.getNameForObject(this.getBlock());
        }
        return this.blockLocation;
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("/\u0016\u001dWG\\jpWD^", "lZBgw");
        BlockStateBase.I[" ".length()] = I("\u0019", "BYRnX");
        BlockStateBase.I["  ".length()] = I("\u001b", "FxngW");
    }
    
    public BlockStateBase() {
        this.blockId = -" ".length();
        this.blockStateId = -" ".length();
        this.metadata = -" ".length();
        this.blockLocation = null;
    }
    
    @Override
    public IBlockState cycleProperty(final IProperty property) {
        return this.withProperty((IProperty<Comparable>)property, cyclePropertyValue(property.getAllowedValues(), this.getValue((IProperty<T>)property)));
    }
    
    static {
        I();
        __OBFID = BlockStateBase.I["".length()];
        COMMA_JOINER = Joiner.on((char)(0x31 ^ 0x1D));
        MAP_ENTRY_TO_STRING = (Function)new Function() {
            private static final String[] I;
            private static final String __OBFID;
            
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
                    if (1 == 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public String apply(final Map.Entry entry) {
                if (entry == null) {
                    return BlockStateBase$1.I["".length()];
                }
                final IProperty property = entry.getKey();
                return String.valueOf(property.getName()) + BlockStateBase$1.I[" ".length()] + property.getName((Comparable)entry.getValue());
            }
            
            public Object apply(final Object o) {
                return this.apply((Map.Entry)o);
            }
            
            static {
                I();
                __OBFID = BlockStateBase$1.I["  ".length()];
            }
            
            private static void I() {
                (I = new String["   ".length()])["".length()] = I("H\n:\b9J", "tDoDu");
                BlockStateBase$1.I[" ".length()] = I("m", "PQGGe");
                BlockStateBase$1.I["  ".length()] = I("\u000b\u0007\u0005V]x{hV^y", "HKZfm");
            }
        };
    }
}
