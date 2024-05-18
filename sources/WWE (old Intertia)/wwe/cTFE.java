package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class cTFE extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos ofgy;
    final Scaffold uXyZ;
    
    cTFE(final Scaffold uXyZ, final BlockPos ofgy) {
        this.uXyZ = uXyZ;
        this.ofgy = ofgy;
        super();
        this.put(this.ofgy.add(1, -1, 1), EnumFacing.WEST);
    }
}
