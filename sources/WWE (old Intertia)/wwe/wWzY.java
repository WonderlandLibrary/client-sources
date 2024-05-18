package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class wWzY extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos WwMb;
    final Scaffold jBhS;
    
    wWzY(final Scaffold jBhS, final BlockPos wwMb) {
        this.jBhS = jBhS;
        this.WwMb = wwMb;
        super();
        this.put(this.WwMb.add(-1, 0, 0), EnumFacing.EAST);
    }
}
