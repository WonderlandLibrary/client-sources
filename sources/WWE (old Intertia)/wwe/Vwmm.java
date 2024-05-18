package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class Vwmm extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos jSqo;
    final Scaffold rkHl;
    
    Vwmm(final Scaffold rkHl, final BlockPos jSqo) {
        this.rkHl = rkHl;
        this.jSqo = jSqo;
        super();
        this.put(this.jSqo.add(-1, 0, 0), EnumFacing.EAST);
    }
}
