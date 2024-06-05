package net.minecraft.client.renderer.block.statemap;

import com.google.common.collect.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.client.resources.model.*;

public abstract class StateMapperBase implements IStateMapper
{
    protected Map field_178133_b;
    
    public StateMapperBase() {
        this.field_178133_b = Maps.newLinkedHashMap();
    }
    
    public String func_178131_a(final Map p_178131_1_) {
        final StringBuilder var2 = new StringBuilder();
        for (final Map.Entry var4 : p_178131_1_.entrySet()) {
            if (var2.length() != 0) {
                var2.append(",");
            }
            final IProperty var5 = var4.getKey();
            final Comparable var6 = var4.getValue();
            var2.append(var5.getName());
            var2.append("=");
            var2.append(var5.getName(var6));
        }
        if (var2.length() == 0) {
            var2.append("normal");
        }
        return var2.toString();
    }
    
    @Override
    public Map func_178130_a(final Block p_178130_1_) {
        for (final IBlockState var3 : p_178130_1_.getBlockState().getValidStates()) {
            this.field_178133_b.put(var3, this.func_178132_a(var3));
        }
        return this.field_178133_b;
    }
    
    protected abstract ModelResourceLocation func_178132_a(final IBlockState p0);
}
