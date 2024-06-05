package net.minecraft.crash;

import java.util.concurrent.*;
import net.minecraft.client.renderer.*;

class CrashReport3 implements Callable
{
    final CrashReport field_71490_a;
    
    CrashReport3(final CrashReport p_i1340_1_) {
        this.field_71490_a = p_i1340_1_;
    }
    
    @Override
    public String call() {
        return OpenGlHelper.func_183029_j();
    }
}
