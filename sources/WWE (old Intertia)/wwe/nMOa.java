package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class nMOa extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos NmOn;
    final Scaffold ZVgq;
    
    nMOa(final Scaffold zVgq, final BlockPos nmOn) {
        this.ZVgq = zVgq;
        this.NmOn = nmOn;
        super();
        this.put(this.NmOn.add(0, 0, 1), EnumFacing.NORTH);
    }
}
