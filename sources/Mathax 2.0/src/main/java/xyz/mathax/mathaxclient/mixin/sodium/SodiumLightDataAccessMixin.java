package xyz.mathax.mathaxclient.mixin.sodium;

import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.Xray;
import me.jellysquid.mods.sodium.client.model.light.data.LightDataAccess;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LightDataAccess.class, remap = false)
public class SodiumLightDataAccessMixin {
    private static final int FULL_LIGHT = 15 << 20 | 15 << 4;

    @Shadow
    protected BlockRenderView world;

    @Shadow
    @Final
    private BlockPos.Mutable pos;

    @Unique private Xray xray;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(CallbackInfo info) {
        xray = Modules.get().get(Xray.class);
    }

    @ModifyVariable(method = "compute", at = @At(value = "STORE"), name = "lm")
    private int compute_modifyAO(int light) {
        if (xray.isEnabled()) {
            BlockState state = world.getBlockState(pos);
            if (!xray.isBlocked(state.getBlock(), pos)) {
                return FULL_LIGHT;
            }
        }

        return light;
    }
}
