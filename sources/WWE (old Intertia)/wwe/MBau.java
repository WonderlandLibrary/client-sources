package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class MBau extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos NtNp;
    final Scaffold bbgG;
    
    MBau(final Scaffold bbgG, final BlockPos ntNp) {
        this.bbgG = bbgG;
        this.NtNp = ntNp;
        super();
        this.put(this.NtNp.add(-1, 0, 0), EnumFacing.EAST);
    }
}
