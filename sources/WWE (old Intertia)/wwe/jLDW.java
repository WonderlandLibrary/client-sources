package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class jLDW extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos rjYD;
    final Scaffold Zfqz;
    
    jLDW(final Scaffold zfqz, final BlockPos rjYD) {
        this.Zfqz = zfqz;
        this.rjYD = rjYD;
        super();
        this.put(this.rjYD.add(0, 0, 1), EnumFacing.NORTH);
    }
}
