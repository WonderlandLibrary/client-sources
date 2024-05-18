package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class qeOH extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos PEvh;
    final Scaffold iLmS;
    
    qeOH(final Scaffold iLmS, final BlockPos pEvh) {
        this.iLmS = iLmS;
        this.PEvh = pEvh;
        super();
        this.put(this.PEvh.add(1, 0, 0), EnumFacing.WEST);
    }
}
