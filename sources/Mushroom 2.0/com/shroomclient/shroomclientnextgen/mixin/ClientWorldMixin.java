package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.events.Bus;
import com.shroomclient.shroomclientnextgen.events.impl.BlockChangeEvent;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.render.BarrierESP;
import com.shroomclient.shroomclientnextgen.util.C;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {

    @Inject(
        at = @At("TAIL"),
        method = "getBlockParticle()Lnet/minecraft/block/Block;",
        cancellable = true
    )
    private void onGetBlockParticle(CallbackInfoReturnable<Block> cir) {
        if (!ModuleManager.isEnabled(BarrierESP.class)) return;

        cir.cancel();
        cir.setReturnValue(Blocks.BARRIER);
    }

    @Inject(at = @At("HEAD"), method = "handleBlockUpdate")
    private void onBlockUpdate(
        BlockPos pos,
        BlockState state,
        int flags,
        CallbackInfo ci
    ) {
        BlockState old = C.w().getBlockState(pos);

        if (state != old) {
            Bus.post(new BlockChangeEvent(pos, old, state));
        }
    }
}
