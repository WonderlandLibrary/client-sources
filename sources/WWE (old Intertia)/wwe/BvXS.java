package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class BvXS extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos ATPo;
    final Scaffold iAaJ;
    
    BvXS(final Scaffold iAaJ, final BlockPos atPo) {
        this.iAaJ = iAaJ;
        this.ATPo = atPo;
        super();
        this.put(this.ATPo.add(1, 0, 0), EnumFacing.WEST);
    }
}
