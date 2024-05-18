package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class gtbS extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos WJBJ;
    final Scaffold cdLk;
    
    gtbS(final Scaffold cdLk, final BlockPos wjbj) {
        this.cdLk = cdLk;
        this.WJBJ = wjbj;
        super();
        this.put(this.WJBJ.add(0, 0, 1), EnumFacing.NORTH);
    }
}
