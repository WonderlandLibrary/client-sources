package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { BlockNote.class }, priority = 9999)
public class MixinBlockNote
{
    public MixinBlockNote() {
        super();
    }
    
    @Inject(method = { "eventReceived" }, at = { @At("HEAD") }, cancellable = true)
    public void eventReceived(final IBlockState blockState, final World world, final BlockPos blockPos, final int n, final int n2, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        hSNE.Seqo(new jWya(blockPos, n2, n));
    }
}
