package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class aXTR extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos DYrn;
    final Scaffold kYfs;
    
    aXTR(final Scaffold kYfs, final BlockPos dYrn) {
        this.kYfs = kYfs;
        this.DYrn = dYrn;
        super();
        this.put(this.DYrn.add(0, 0, -1), EnumFacing.SOUTH);
    }
}
