package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class rUbB extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos nmVK;
    final Scaffold Hpzy;
    
    rUbB(final Scaffold hpzy, final BlockPos nmVK) {
        this.Hpzy = hpzy;
        this.nmVK = nmVK;
        super();
        this.put(this.nmVK.add(0, -1, 0), EnumFacing.UP);
    }
}
