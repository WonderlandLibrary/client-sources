package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class BeCY extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos mALP;
    final Scaffold WdrE;
    
    BeCY(final Scaffold wdrE, final BlockPos malp) {
        this.WdrE = wdrE;
        this.mALP = malp;
        super();
        this.put(this.mALP.add(0, -1, 1), EnumFacing.NORTH);
    }
}
