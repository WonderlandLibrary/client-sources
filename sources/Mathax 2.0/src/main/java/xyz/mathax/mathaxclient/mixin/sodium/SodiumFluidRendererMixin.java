package xyz.mathax.mathaxclient.mixin.sodium;

import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.Ambience;
import xyz.mathax.mathaxclient.systems.modules.render.Xray;
import me.jellysquid.mods.sodium.client.model.light.LightPipeline;
import me.jellysquid.mods.sodium.client.model.quad.ModelQuadView;
import me.jellysquid.mods.sodium.client.model.quad.blender.ColorSampler;
import me.jellysquid.mods.sodium.client.render.chunk.compile.buffers.ChunkModelBuilder;
import me.jellysquid.mods.sodium.client.render.pipeline.FluidRenderer;
import me.jellysquid.mods.sodium.client.util.color.ColorARGB;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(value = FluidRenderer.class, remap = false)
public class SodiumFluidRendererMixin {
    @Final
    @Shadow
    private int[] quadColors;

    @Unique
    private final ThreadLocal<Integer> alphas = new ThreadLocal<>();

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(BlockRenderView world, FluidState fluidState, BlockPos pos, BlockPos offset, ChunkModelBuilder buffers, CallbackInfoReturnable<Boolean> infoReturnable) {
        int alpha = Xray.getAlpha(fluidState.getBlockState(), pos);
        if (alpha == 0) {
            infoReturnable.setReturnValue(false);
        } else {
            alphas.set(alpha);
        }
    }

    @Inject(method = "calculateQuadColors", at = @At("TAIL"))
    private void onCalculateQuadColors(ModelQuadView quad, BlockRenderView world, BlockPos pos, LightPipeline lighter, Direction direction, float brightness, ColorSampler<FluidState> colorSampler, FluidState fluidState, CallbackInfo info) {
        Ambience ambience = Modules.get().get(Ambience.class);
        if (ambience.isEnabled() && ambience.customLavaColorSetting.get() && fluidState.isIn(FluidTags.LAVA)) {
            Arrays.fill(quadColors, ColorARGB.toABGR(ambience.lavaColorSetting.get().getPacked()));
        } else {
            int alpha = alphas.get();
            if (alpha != -1) {
                quadColors[0] = (alpha << 24) | (quadColors[0] & 0x00FFFFFF);
                quadColors[1] = (alpha << 24) | (quadColors[1] & 0x00FFFFFF);
                quadColors[2] = (alpha << 24) | (quadColors[2] & 0x00FFFFFF);
                quadColors[3] = (alpha << 24) | (quadColors[3] & 0x00FFFFFF);
            }
        }
    }
}
