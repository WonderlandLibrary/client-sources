package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class tuiL extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos QgAd;
    final Scaffold suRZ;
    
    tuiL(final Scaffold suRZ, final BlockPos qgAd) {
        this.suRZ = suRZ;
        this.QgAd = qgAd;
        super();
        this.put(this.QgAd.add(1, -1, 1), EnumFacing.NORTH);
    }
}
