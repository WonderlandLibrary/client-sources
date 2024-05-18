package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class HVHa extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos PSfc;
    final Scaffold YYNW;
    
    HVHa(final Scaffold yynw, final BlockPos pSfc) {
        this.YYNW = yynw;
        this.PSfc = pSfc;
        super();
        this.put(this.PSfc.add(0, -1, 0), EnumFacing.WEST);
    }
}
