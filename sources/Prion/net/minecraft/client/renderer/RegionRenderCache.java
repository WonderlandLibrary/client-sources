package net.minecraft.client.renderer;

import java.util.Arrays;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.Chunk.EnumCreateEntityType;

public class RegionRenderCache extends ChunkCache
{
  private static final IBlockState field_175632_f = net.minecraft.init.Blocks.air.getDefaultState();
  private final BlockPos field_175633_g;
  private int[] field_175634_h;
  private IBlockState[] field_175635_i;
  private static final String __OBFID = "CL_00002565";
  
  public RegionRenderCache(World worldIn, BlockPos p_i46273_2_, BlockPos p_i46273_3_, int p_i46273_4_)
  {
    super(worldIn, p_i46273_2_, p_i46273_3_, p_i46273_4_);
    field_175633_g = p_i46273_2_.subtract(new net.minecraft.util.Vec3i(p_i46273_4_, p_i46273_4_, p_i46273_4_));
    boolean var5 = true;
    field_175634_h = new int['ὀ'];
    Arrays.fill(field_175634_h, -1);
    field_175635_i = new IBlockState['ὀ'];
  }
  
  public TileEntity getTileEntity(BlockPos pos)
  {
    int var2 = (pos.getX() >> 4) - chunkX;
    int var3 = (pos.getZ() >> 4) - chunkZ;
    return chunkArray[var2][var3].func_177424_a(pos, Chunk.EnumCreateEntityType.QUEUED);
  }
  
  public int getCombinedLight(BlockPos p_175626_1_, int p_175626_2_)
  {
    int var3 = func_175630_e(p_175626_1_);
    int var4 = field_175634_h[var3];
    
    if (var4 == -1)
    {
      var4 = super.getCombinedLight(p_175626_1_, p_175626_2_);
      field_175634_h[var3] = var4;
    }
    
    return var4;
  }
  
  public IBlockState getBlockState(BlockPos pos)
  {
    int var2 = func_175630_e(pos);
    IBlockState var3 = field_175635_i[var2];
    
    if (var3 == null)
    {
      var3 = func_175631_c(pos);
      field_175635_i[var2] = var3;
    }
    
    return var3;
  }
  
  private IBlockState func_175631_c(BlockPos p_175631_1_)
  {
    if ((p_175631_1_.getY() >= 0) && (p_175631_1_.getY() < 256))
    {
      int var2 = (p_175631_1_.getX() >> 4) - chunkX;
      int var3 = (p_175631_1_.getZ() >> 4) - chunkZ;
      return chunkArray[var2][var3].getBlockState(p_175631_1_);
    }
    

    return field_175632_f;
  }
  

  private int func_175630_e(BlockPos p_175630_1_)
  {
    int var2 = p_175630_1_.getX() - field_175633_g.getX();
    int var3 = p_175630_1_.getY() - field_175633_g.getY();
    int var4 = p_175630_1_.getZ() - field_175633_g.getZ();
    return var2 * 400 + var4 * 20 + var3;
  }
}
