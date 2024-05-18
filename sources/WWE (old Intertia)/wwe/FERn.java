package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class FERn extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos WFbw;
    final Scaffold Dmjx;
    
    FERn(final Scaffold dmjx, final BlockPos wFbw) {
        this.Dmjx = dmjx;
        this.WFbw = wFbw;
        super();
        this.put(this.WFbw.add(0, -1, 1), EnumFacing.SOUTH);
    }
}
