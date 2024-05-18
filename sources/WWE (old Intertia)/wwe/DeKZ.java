package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class DeKZ extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos mlUR;
    final Scaffold iCBF;
    
    DeKZ(final Scaffold icbf, final BlockPos mlUR) {
        this.iCBF = icbf;
        this.mlUR = mlUR;
        super();
        this.put(this.mlUR.add(1, 0, 0), EnumFacing.WEST);
    }
}
