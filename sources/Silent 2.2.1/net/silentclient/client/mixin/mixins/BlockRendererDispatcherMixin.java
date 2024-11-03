package net.silentclient.client.mixin.mixins;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.silentclient.client.Client;
import net.silentclient.client.mods.settings.FPSBoostMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockRendererDispatcher.class)
public class BlockRendererDispatcherMixin {
    @Inject(method = "renderBlock", at = @At("HEAD"), cancellable = true)
    public void fpsBoostBlocks(IBlockState state, BlockPos pos, IBlockAccess blockAccess, WorldRenderer worldRendererIn, CallbackInfoReturnable<Boolean> cir) {
        if (Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Hide Flowers").getValBoolean() && FPSBoostMod.flowersBlocks.contains(state.getBlock())) {
            cir.setReturnValue(false);
            cir.cancel();
        }
        if(Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Hide Tall Grass").getValBoolean() && FPSBoostMod.tallGrass.contains(state.getBlock())) {
            cir.setReturnValue(false);
            cir.cancel();
        }
        if(Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Hide Fences").getValBoolean() && FPSBoostMod.fencesBlocks.contains(state.getBlock())) {
            cir.setReturnValue(false);
            cir.cancel();
        }
        if(Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Hide Fence Gates").getValBoolean() && FPSBoostMod.fencesGatesBlocks.contains(state.getBlock())) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
