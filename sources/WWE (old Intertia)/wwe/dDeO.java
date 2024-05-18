package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class dDeO extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos gXrF;
    final Scaffold dHVf;
    
    dDeO(final Scaffold dhVf, final BlockPos gXrF) {
        this.dHVf = dhVf;
        this.gXrF = gXrF;
        super();
        this.put(this.gXrF.add(0, 0, -1), EnumFacing.SOUTH);
    }
}
