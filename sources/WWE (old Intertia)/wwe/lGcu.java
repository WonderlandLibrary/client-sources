package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class lGcu extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos BwPT;
    final Scaffold ufEF;
    
    lGcu(final Scaffold ufEF, final BlockPos bwPT) {
        this.ufEF = ufEF;
        this.BwPT = bwPT;
        super();
        this.put(this.BwPT.add(0, 0, -1), EnumFacing.SOUTH);
    }
}
