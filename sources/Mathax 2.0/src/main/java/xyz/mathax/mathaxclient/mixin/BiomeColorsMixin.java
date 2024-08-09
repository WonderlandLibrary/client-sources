package xyz.mathax.mathaxclient.mixin;

import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.Ambience;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BiomeColors.class)
public class BiomeColorsMixin {
    @Inject(method = "getWaterColor", at = @At("HEAD"), cancellable = true)
    private static void onGetWaterColor(BlockRenderView world, BlockPos pos, CallbackInfoReturnable<Integer> infoReturnable) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isEnabled() && ambience.customWaterColorSetting.get()) {
            infoReturnable.setReturnValue(ambience.waterColorSetting.get().getPacked());
        }
    }

    @Inject(method = "getFoliageColor", at = @At("HEAD"), cancellable = true)
    private static void onGetFoliageColor(BlockRenderView world, BlockPos pos, CallbackInfoReturnable<Integer> infoReturnable) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isEnabled() && ambience.customFoliageColorSetting.get()) {
            infoReturnable.setReturnValue(ambience.foliageColorSetting.get().getPacked());
        }
    }

    @Inject(method = "getGrassColor", at = @At("HEAD"), cancellable = true)
    private static void onGetGrassColor(BlockRenderView world, BlockPos pos, CallbackInfoReturnable<Integer> infoReturnable) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isEnabled() && ambience.customGrassColorSetting.get()) {
            infoReturnable.setReturnValue(ambience.grassColorSetting.get().getPacked());
        }
    }
}
