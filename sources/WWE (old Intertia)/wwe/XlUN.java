package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class XlUN extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos mCxS;
    final Scaffold qvJV;
    
    XlUN(final Scaffold qvJV, final BlockPos mCxS) {
        this.qvJV = qvJV;
        this.mCxS = mCxS;
        super();
        this.put(this.mCxS.add(-1, -1, 1), EnumFacing.SOUTH);
    }
}
