package net.minecraft.client.renderer.block.statemap;

import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;

public interface IStateMapper {
  Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block paramBlock);
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\block\statemap\IStateMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */