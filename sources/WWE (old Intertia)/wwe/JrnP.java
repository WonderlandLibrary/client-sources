package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class JrnP extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos EtUV;
    final Scaffold rxFU;
    
    JrnP(final Scaffold rxFU, final BlockPos etUV) {
        this.rxFU = rxFU;
        this.EtUV = etUV;
        super();
        this.put(this.EtUV.add(1, 0, 0), EnumFacing.WEST);
    }
}
