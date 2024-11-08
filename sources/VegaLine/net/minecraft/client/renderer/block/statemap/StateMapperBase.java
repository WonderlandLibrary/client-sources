/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.block.statemap;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;

public abstract class StateMapperBase
implements IStateMapper {
    protected Map<IBlockState, ModelResourceLocation> mapStateModelLocations = Maps.newLinkedHashMap();

    public String getPropertyString(Map<IProperty<?>, Comparable<?>> values) {
        StringBuilder stringbuilder = new StringBuilder();
        for (Map.Entry<IProperty<?>, Comparable<?>> entry : values.entrySet()) {
            if (stringbuilder.length() != 0) {
                stringbuilder.append(",");
            }
            IProperty<?> iproperty = entry.getKey();
            stringbuilder.append(iproperty.getName());
            stringbuilder.append("=");
            stringbuilder.append(this.getPropertyName(iproperty, entry.getValue()));
        }
        if (stringbuilder.length() == 0) {
            stringbuilder.append("normal");
        }
        return stringbuilder.toString();
    }

    private <T extends Comparable<T>> String getPropertyName(IProperty<T> property, Comparable<?> value) {
        return property.getName(value);
    }

    @Override
    public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn) {
        for (IBlockState iblockstate : blockIn.getBlockState().getValidStates()) {
            this.mapStateModelLocations.put(iblockstate, this.getModelResourceLocation(iblockstate));
        }
        return this.mapStateModelLocations;
    }

    protected abstract ModelResourceLocation getModelResourceLocation(IBlockState var1);
}

