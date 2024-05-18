package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class MpJx extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos BPlk;
    final Scaffold RjUc;
    
    MpJx(final Scaffold rjUc, final BlockPos bPlk) {
        this.RjUc = rjUc;
        this.BPlk = bPlk;
        super();
        this.put(this.BPlk.add(0, -1, 1), EnumFacing.WEST);
    }
}
