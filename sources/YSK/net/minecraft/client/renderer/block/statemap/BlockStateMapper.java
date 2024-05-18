package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.client.resources.model.*;
import com.google.common.base.*;
import java.util.*;
import com.google.common.collect.*;

public class BlockStateMapper
{
    private Map<Block, IStateMapper> blockStateMap;
    private Set<Block> setBuiltInBlocks;
    
    public Map<IBlockState, ModelResourceLocation> putAllStateModelLocations() {
        final IdentityHashMap identityHashMap = Maps.newIdentityHashMap();
        final Iterator<Block> iterator = Block.blockRegistry.iterator();
        "".length();
        if (0 == 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Block block = iterator.next();
            if (!this.setBuiltInBlocks.contains(block)) {
                identityHashMap.putAll(((IStateMapper)Objects.firstNonNull((Object)this.blockStateMap.get(block), (Object)new DefaultStateMapper())).putStateModelLocations(block));
            }
        }
        return (Map<IBlockState, ModelResourceLocation>)identityHashMap;
    }
    
    public void registerBlockStateMapper(final Block block, final IStateMapper stateMapper) {
        this.blockStateMap.put(block, stateMapper);
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
            if (2 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void registerBuiltInBlocks(final Block... array) {
        Collections.addAll(this.setBuiltInBlocks, array);
    }
    
    public BlockStateMapper() {
        this.blockStateMap = (Map<Block, IStateMapper>)Maps.newIdentityHashMap();
        this.setBuiltInBlocks = (Set<Block>)Sets.newIdentityHashSet();
    }
}
