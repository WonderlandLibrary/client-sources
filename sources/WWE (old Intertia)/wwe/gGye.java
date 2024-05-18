package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class gGye extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos easy;
    final Scaffold Pdmb;
    
    gGye(final Scaffold pdmb, final BlockPos easy) {
        this.Pdmb = pdmb;
        this.easy = easy;
        super();
        this.put(this.easy.add(-1, 0, 0), EnumFacing.EAST);
    }
}
