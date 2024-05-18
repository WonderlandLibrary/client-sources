package wwe;

import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.world.*;

class vkJi extends HashMap<BlockPos, EnumFacing>
{
    final BlockPos GnDu;
    final Scaffold UkDC;
    
    vkJi(final Scaffold ukDC, final BlockPos gnDu) {
        this.UkDC = ukDC;
        this.GnDu = gnDu;
        super();
        this.put(this.GnDu.add(0, 0, -1), EnumFacing.SOUTH);
    }
}
