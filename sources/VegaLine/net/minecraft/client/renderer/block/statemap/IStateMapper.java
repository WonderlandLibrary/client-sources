/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.block.statemap;

import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

public interface IStateMapper {
    public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block var1);
}

