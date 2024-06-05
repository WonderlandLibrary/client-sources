package net.minecraft.client.renderer;

import java.util.concurrent.*;
import net.minecraft.crash.*;

class RenderGlobal$1 implements Callable {
    final /* synthetic */ double val$p_180442_3_;
    final /* synthetic */ double val$p_180442_5_;
    final /* synthetic */ double val$p_180442_7_;
    
    @Override
    public String call() {
        return CrashReportCategory.getCoordinateInfo(this.val$p_180442_3_, this.val$p_180442_5_, this.val$p_180442_7_);
    }
}