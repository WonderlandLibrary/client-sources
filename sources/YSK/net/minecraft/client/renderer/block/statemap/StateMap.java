package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import com.google.common.collect.*;
import java.util.*;

public class StateMap extends StateMapperBase
{
    private final List<IProperty<?>> ignored;
    private final IProperty<?> name;
    private final String suffix;
    
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
            if (0 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    StateMap(final IProperty property, final String s, final List list, final StateMap stateMap) {
        this(property, s, list);
    }
    
    @Override
    protected ModelResourceLocation getModelResourceLocation(final IBlockState blockState) {
        final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap((Map)blockState.getProperties());
        String s;
        if (this.name == null) {
            s = Block.blockRegistry.getNameForObject(blockState.getBlock()).toString();
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        else {
            s = this.name.getName(linkedHashMap.remove(this.name));
        }
        if (this.suffix != null) {
            s = String.valueOf(s) + this.suffix;
        }
        final Iterator<IProperty<?>> iterator = this.ignored.iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            linkedHashMap.remove(iterator.next());
        }
        return new ModelResourceLocation(s, this.getPropertyString(linkedHashMap));
    }
    
    private StateMap(final IProperty<?> name, final String suffix, final List<IProperty<?>> ignored) {
        this.name = name;
        this.suffix = suffix;
        this.ignored = ignored;
    }
    
    public static class Builder
    {
        private String suffix;
        private IProperty<?> name;
        private final List<IProperty<?>> ignored;
        
        public Builder() {
            this.ignored = (List<IProperty<?>>)Lists.newArrayList();
        }
        
        public Builder withName(final IProperty<?> name) {
            this.name = name;
            return this;
        }
        
        public Builder ignore(final IProperty<?>... array) {
            Collections.addAll(this.ignored, array);
            return this;
        }
        
        public StateMap build() {
            return new StateMap(this.name, this.suffix, this.ignored, null);
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
                if (0 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public Builder withSuffix(final String suffix) {
            this.suffix = suffix;
            return this;
        }
    }
}
