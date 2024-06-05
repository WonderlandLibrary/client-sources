package net.minecraft.block.state;

import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import com.google.common.collect.*;
import java.util.*;

static class StateImplemenation extends BlockStateBase
{
    private final Block block;
    private final ImmutableMap properties;
    private ImmutableTable propertyValueTable;
    
    private StateImplemenation(final Block p_i45660_1_, final ImmutableMap p_i45660_2_) {
        this.block = p_i45660_1_;
        this.properties = p_i45660_2_;
    }
    
    StateImplemenation(final Block p_i45661_1_, final ImmutableMap p_i45661_2_, final Object p_i45661_3_) {
        this(p_i45661_1_, p_i45661_2_);
    }
    
    @Override
    public Collection getPropertyNames() {
        return Collections.unmodifiableCollection((Collection<?>)this.properties.keySet());
    }
    
    @Override
    public Comparable getValue(final IProperty property) {
        if (!this.properties.containsKey((Object)property)) {
            throw new IllegalArgumentException("Cannot get property " + property + " as it does not exist in " + this.block.getBlockState());
        }
        return property.getValueClass().cast(this.properties.get((Object)property));
    }
    
    @Override
    public IBlockState withProperty(final IProperty property, final Comparable value) {
        if (!this.properties.containsKey((Object)property)) {
            throw new IllegalArgumentException("Cannot set property " + property + " as it does not exist in " + this.block.getBlockState());
        }
        if (!property.getAllowedValues().contains(value)) {
            throw new IllegalArgumentException("Cannot set property " + property + " to " + value + " on block " + Block.blockRegistry.getNameForObject(this.block) + ", it is not an allowed value");
        }
        return (this.properties.get((Object)property) == value) ? this : ((IBlockState)this.propertyValueTable.get((Object)property, (Object)value));
    }
    
    @Override
    public ImmutableMap getProperties() {
        return this.properties;
    }
    
    @Override
    public Block getBlock() {
        return this.block;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        return this == p_equals_1_;
    }
    
    @Override
    public int hashCode() {
        return this.properties.hashCode();
    }
    
    public void buildPropertyValueTable(final Map map) {
        if (this.propertyValueTable != null) {
            throw new IllegalStateException();
        }
        final HashBasedTable var2 = HashBasedTable.create();
        for (final IProperty var4 : this.properties.keySet()) {
            for (final Comparable var6 : var4.getAllowedValues()) {
                if (var6 != this.properties.get((Object)var4)) {
                    var2.put((Object)var4, (Object)var6, map.get(this.setPropertyValue(var4, var6)));
                }
            }
        }
        this.propertyValueTable = ImmutableTable.copyOf((Table)var2);
    }
    
    private Map setPropertyValue(final IProperty property, final Comparable value) {
        final HashMap var3 = Maps.newHashMap((Map)this.properties);
        var3.put(property, value);
        return var3;
    }
}
