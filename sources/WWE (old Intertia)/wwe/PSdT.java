package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class PSdT extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos NyAD;
    final Scaffold vugz;
    
    PSdT(final Scaffold vugz, final BlockPos nyAD) {
        this.vugz = vugz;
        this.NyAD = nyAD;
        super();
        this.put(this.NyAD.add(0, 0, -1), EnumFacing.SOUTH);
    }
}
