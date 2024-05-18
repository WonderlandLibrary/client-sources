package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class tKGX extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos vtTt;
    final Scaffold UviG;
    
    tKGX(final Scaffold uviG, final BlockPos vtTt) {
        this.UviG = uviG;
        this.vtTt = vtTt;
        super();
        this.put(this.vtTt.add(0, -1, 1), EnumFacing.EAST);
    }
}
