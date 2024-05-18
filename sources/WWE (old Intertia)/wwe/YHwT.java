package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class YHwT extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos axvC;
    final Scaffold wInt;
    
    YHwT(final Scaffold wInt, final BlockPos axvC) {
        this.wInt = wInt;
        this.axvC = axvC;
        super();
        this.put(this.axvC.add(0, -1, 0), EnumFacing.SOUTH);
    }
}
