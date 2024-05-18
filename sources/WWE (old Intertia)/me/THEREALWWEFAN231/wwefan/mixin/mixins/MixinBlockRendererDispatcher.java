package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.*;
import net.minecraft.crash.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { BlockRendererDispatcher.class }, priority = 9999)
public class MixinBlockRendererDispatcher
{
    public MixinBlockRendererDispatcher() {
        super();
    }
    
    @Inject(method = { "renderBlock" }, at = { @At("HEAD") }, cancellable = true)
    public void renderBlock(final IBlockState blockState, final BlockPos blockPos, final IBlockAccess blockAccess, final BufferBuilder bufferBuilder, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        try {
            hSNE.Seqo(new iQuY(blockState.getBlock(), blockPos));
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, "Tesselating block in world");
            CrashReportCategory.addBlockInfo(crashReport.makeCategory("Block being tesselated"), blockPos, blockState.getBlock(), blockState.getBlock().getMetaFromState(blockState));
            throw new ReportedException(crashReport);
        }
    }
}
