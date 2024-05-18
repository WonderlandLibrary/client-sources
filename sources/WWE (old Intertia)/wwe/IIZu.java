package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class IIZu extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos bgol;
    final Scaffold yCpS;
    
    IIZu(final Scaffold yCpS, final BlockPos bgol) {
        this.yCpS = yCpS;
        this.bgol = bgol;
        super();
        this.put(this.bgol.add(0, 0, 1), EnumFacing.NORTH);
    }
}
