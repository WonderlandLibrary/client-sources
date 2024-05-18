package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class dGZy extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos uTkC;
    final Scaffold xMCL;
    
    dGZy(final Scaffold xmcl, final BlockPos uTkC) {
        this.xMCL = xmcl;
        this.uTkC = uTkC;
        super();
        this.put(this.uTkC.add(0, -1, 0), EnumFacing.UP);
    }
}
