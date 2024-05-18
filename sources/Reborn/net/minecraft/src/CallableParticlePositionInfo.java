package net.minecraft.src;

import java.util.concurrent.*;

class CallableParticlePositionInfo implements Callable
{
    final double field_85101_a;
    final double field_85099_b;
    final double field_85100_c;
    final RenderGlobal globalRenderer;
    
    CallableParticlePositionInfo(final RenderGlobal par1RenderGlobal, final double par2, final double par4, final double par6) {
        this.globalRenderer = par1RenderGlobal;
        this.field_85101_a = par2;
        this.field_85099_b = par4;
        this.field_85100_c = par6;
    }
    
    public String callParticlePositionInfo() {
        return CrashReportCategory.func_85074_a(this.field_85101_a, this.field_85099_b, this.field_85100_c);
    }
    
    @Override
    public Object call() {
        return this.callParticlePositionInfo();
    }
}
