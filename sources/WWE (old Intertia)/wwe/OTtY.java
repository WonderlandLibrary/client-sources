package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class OTtY extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos VRLR;
    final Scaffold AIcd;
    
    OTtY(final Scaffold aIcd, final BlockPos vrlr) {
        this.AIcd = aIcd;
        this.VRLR = vrlr;
        super();
        this.put(this.VRLR.add(-1, 0, 0), EnumFacing.EAST);
    }
}
