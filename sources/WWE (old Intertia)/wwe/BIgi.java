package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class BIgi extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos eJwQ;
    final Scaffold dEiQ;
    
    BIgi(final Scaffold dEiQ, final BlockPos eJwQ) {
        this.dEiQ = dEiQ;
        this.eJwQ = eJwQ;
        super();
        this.put(this.eJwQ.add(1, 0, 0), EnumFacing.WEST);
    }
}
