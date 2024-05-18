package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class FVrM extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos gxTU;
    final Scaffold korV;
    
    FVrM(final Scaffold korV, final BlockPos gxTU) {
        this.korV = korV;
        this.gxTU = gxTU;
        super();
        this.put(this.gxTU.add(1, -1, 1), EnumFacing.EAST);
    }
}
