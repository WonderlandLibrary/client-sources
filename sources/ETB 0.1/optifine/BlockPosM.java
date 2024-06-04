package optifine;

import com.google.common.collect.AbstractIterator;
import java.util.Iterator;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class BlockPosM extends BlockPos
{
  private int mx;
  private int my;
  private int mz;
  private int level;
  private BlockPosM[] facings;
  private boolean needsUpdate;
  
  public BlockPosM(int x, int y, int z)
  {
    this(x, y, z, 0);
  }
  
  public BlockPosM(double xIn, double yIn, double zIn)
  {
    this(MathHelper.floor_double(xIn), MathHelper.floor_double(yIn), MathHelper.floor_double(zIn));
  }
  
  public BlockPosM(int x, int y, int z, int level)
  {
    super(0, 0, 0);
    mx = x;
    my = y;
    mz = z;
    this.level = level;
  }
  



  public int getX()
  {
    return mx;
  }
  



  public int getY()
  {
    return my;
  }
  



  public int getZ()
  {
    return mz;
  }
  
  public void setXyz(int x, int y, int z)
  {
    mx = x;
    my = y;
    mz = z;
    needsUpdate = true;
  }
  
  public void setXyz(double xIn, double yIn, double zIn)
  {
    setXyz(MathHelper.floor_double(xIn), MathHelper.floor_double(yIn), MathHelper.floor_double(zIn));
  }
  



  public BlockPos offset(EnumFacing facing)
  {
    if (level <= 0)
    {
      return super.offset(facing, 1);
    }
    

    if (facings == null)
    {
      facings = new BlockPosM[EnumFacing.VALUES.length];
    }
    
    if (needsUpdate)
    {
      update();
    }
    
    int index = facing.getIndex();
    BlockPosM bpm = facings[index];
    
    if (bpm == null)
    {
      int nx = mx + facing.getFrontOffsetX();
      int ny = my + facing.getFrontOffsetY();
      int nz = mz + facing.getFrontOffsetZ();
      bpm = new BlockPosM(nx, ny, nz, level - 1);
      facings[index] = bpm;
    }
    
    return bpm;
  }
  




  public BlockPos offset(EnumFacing facing, int n)
  {
    return n == 1 ? offset(facing) : super.offset(facing, n);
  }
  
  private void update()
  {
    for (int i = 0; i < 6; i++)
    {
      BlockPosM bpm = facings[i];
      
      if (bpm != null)
      {
        EnumFacing facing = EnumFacing.VALUES[i];
        int nx = mx + facing.getFrontOffsetX();
        int ny = my + facing.getFrontOffsetY();
        int nz = mz + facing.getFrontOffsetZ();
        bpm.setXyz(nx, ny, nz);
      }
    }
    
    needsUpdate = false;
  }
  
  public static Iterable getAllInBoxMutable(BlockPos from, BlockPos to)
  {
    BlockPos posFrom = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
    final BlockPos posTo = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
    new Iterable()
    {
      public Iterator iterator()
      {
        new AbstractIterator()
        {
          private BlockPosM theBlockPosM = null;
          
          protected BlockPosM computeNext0() {
            if (theBlockPosM == null)
            {
              theBlockPosM = new BlockPosM(val$posFrom.getX(), val$posFrom.getY(), val$posFrom.getZ(), 3);
              return theBlockPosM;
            }
            if (theBlockPosM.equals(val$posTo))
            {
              return (BlockPosM)endOfData();
            }
            

            int bx = theBlockPosM.getX();
            int by = theBlockPosM.getY();
            int bz = theBlockPosM.getZ();
            
            if (bx < val$posTo.getX())
            {
              bx++;
            }
            else if (by < val$posTo.getY())
            {
              bx = val$posFrom.getX();
              by++;
            }
            else if (bz < val$posTo.getZ())
            {
              bx = val$posFrom.getX();
              by = val$posFrom.getY();
              bz++;
            }
            
            theBlockPosM.setXyz(bx, by, bz);
            return theBlockPosM;
          }
          
          protected Object computeNext()
          {
            return computeNext0();
          }
        };
      }
    };
  }
  
  public BlockPos getImmutable()
  {
    return new BlockPos(getX(), getY(), getZ());
  }
}
