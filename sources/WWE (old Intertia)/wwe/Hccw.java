package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class Hccw extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos PAhL;
    final Scaffold ezsQ;
    
    Hccw(final Scaffold ezsQ, final BlockPos pAhL) {
        this.ezsQ = ezsQ;
        this.PAhL = pAhL;
        super();
        this.put(this.PAhL.add(0, -1, 0), EnumFacing.EAST);
    }
}
