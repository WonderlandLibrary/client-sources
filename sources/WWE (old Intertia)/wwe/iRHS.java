package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class iRHS extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos uyWw;
    final Scaffold ryBM;
    
    iRHS(final Scaffold ryBM, final BlockPos uyWw) {
        this.ryBM = ryBM;
        this.uyWw = uyWw;
        super();
        this.put(this.uyWw.add(0, 0, 1), EnumFacing.NORTH);
    }
}
