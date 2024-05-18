package baritone.launch.mixins;

import org.spongepowered.asm.mixin.*;
import com.google.common.base.*;
import javax.annotation.*;

@Mixin({ et.class })
public class MixinBlockPos extends fq
{
    public MixinBlockPos(final int n, final int n2, final int n3) {
        super(n, n2, n3);
    }
    
    @Nonnull
    public String toString() {
        return MoreObjects.toStringHelper("BlockPos").add("x", this.p()).add("y", this.q()).add("z", this.r()).toString();
    }
    
    public int compareTo(final Object o) {
        return super.l((fq)o);
    }
}
