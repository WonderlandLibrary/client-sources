package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class gGOF extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos LwqJ;
    final Scaffold NpTt;
    
    gGOF(final Scaffold npTt, final BlockPos lwqJ) {
        this.NpTt = npTt;
        this.LwqJ = lwqJ;
        super();
        this.put(this.LwqJ.add(0, -1, 0), EnumFacing.NORTH);
    }
}
