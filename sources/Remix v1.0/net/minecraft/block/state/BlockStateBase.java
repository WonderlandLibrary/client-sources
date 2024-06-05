package net.minecraft.block.state;

import com.google.common.base.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import com.google.common.collect.*;
import java.util.*;

public abstract class BlockStateBase implements IBlockState
{
    private static final Joiner COMMA_JOINER;
    private static final Function field_177233_b;
    private int blockId;
    private int blockStateId;
    private int metadata;
    private ResourceLocation blockLocation;
    
    public BlockStateBase() {
        this.blockId = -1;
        this.blockStateId = -1;
        this.metadata = -1;
        this.blockLocation = null;
    }
    
    protected static Object cyclePropertyValue(final Collection values, final Object currentValue) {
        final Iterator var2 = values.iterator();
        while (var2.hasNext()) {
            if (var2.next().equals(currentValue)) {
                if (var2.hasNext()) {
                    return var2.next();
                }
                return values.iterator().next();
            }
        }
        return var2.next();
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
    
    public int getMetadata() {
        if (this.metadata < 0) {
            this.metadata = this.getBlock().getMetaFromState(this);
        }
        return this.metadata;
    }
    
    public ResourceLocation getBlockLocation() {
        if (this.blockLocation == null) {
            this.blockLocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.getBlock());
        }
        return this.blockLocation;
    }
    
    @Override
    public IBlockState cycleProperty(final IProperty property) {
        return this.withProperty(property, (Comparable)cyclePropertyValue(property.getAllowedValues(), this.getValue(property)));
    }
    
    @Override
    public String toString() {
        final StringBuilder var1 = new StringBuilder();
        var1.append(Block.blockRegistry.getNameForObject(this.getBlock()));
        if (!this.getProperties().isEmpty()) {
            var1.append("[");
            BlockStateBase.COMMA_JOINER.appendTo(var1, Iterables.transform((Iterable)this.getProperties().entrySet(), BlockStateBase.field_177233_b));
            var1.append("]");
        }
        return var1.toString();
    }
    
    public ImmutableTable<IProperty, Comparable, IBlockState> getPropertyValueTable() {
        return null;
    }
    
    static {
        COMMA_JOINER = Joiner.on(',');
        field_177233_b = (Function)new Function() {
            public String func_177225_a(final Map.Entry p_177225_1_) {
                if (p_177225_1_ == null) {
                    return "<NULL>";
                }
                final IProperty var2 = p_177225_1_.getKey();
                return var2.getName() + "=" + var2.getName((Comparable)p_177225_1_.getValue());
            }
            
            public Object apply(final Object p_apply_1_) {
                return this.func_177225_a((Map.Entry)p_apply_1_);
            }
        };
    }
}
