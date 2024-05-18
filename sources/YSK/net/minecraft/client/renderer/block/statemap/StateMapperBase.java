package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.state.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.block.*;
import com.google.common.collect.*;
import java.io.*;

public abstract class StateMapperBase implements IStateMapper
{
    private static final String[] I;
    protected Map<IBlockState, ModelResourceLocation> mapStateModelLocations;
    
    public String getPropertyString(final Map<IProperty, Comparable> map) {
        final StringBuilder sb = new StringBuilder();
        final Iterator<Map.Entry<IProperty, Comparable>> iterator = map.entrySet().iterator();
        "".length();
        if (4 == 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<IProperty, Comparable> entry = iterator.next();
            if (sb.length() != 0) {
                sb.append(StateMapperBase.I["".length()]);
            }
            final IProperty<Comparable> property = entry.getKey();
            final Comparable comparable = entry.getValue();
            sb.append(property.getName());
            sb.append(StateMapperBase.I[" ".length()]);
            sb.append(property.getName(comparable));
        }
        if (sb.length() == 0) {
            sb.append(StateMapperBase.I["  ".length()]);
        }
        return sb.toString();
    }
    
    @Override
    public Map<IBlockState, ModelResourceLocation> putStateModelLocations(final Block block) {
        final Iterator iterator = block.getBlockState().getValidStates().iterator();
        "".length();
        if (-1 == 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final IBlockState blockState = iterator.next();
            this.mapStateModelLocations.put(blockState, this.getModelResourceLocation(blockState));
        }
        return this.mapStateModelLocations;
    }
    
    protected abstract ModelResourceLocation getModelResourceLocation(final IBlockState p0);
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("u", "YIGKk");
        StateMapperBase.I[" ".length()] = I("\u007f", "BmQxY");
        StateMapperBase.I["  ".length()] = I("\u00069\u0018\u0001#\u0004", "hVjlB");
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
            if (2 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public StateMapperBase() {
        this.mapStateModelLocations = (Map<IBlockState, ModelResourceLocation>)Maps.newLinkedHashMap();
    }
    
    static {
        I();
    }
}
