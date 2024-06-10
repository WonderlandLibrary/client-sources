package maxstats.weave.mixin;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.render.color.ColorUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {

    @Shadow protected abstract void loadShader(ResourceLocation resourceLocation);

    //private boolean fastRenderer;

    @Inject(method = "renderWorld", at = @At("HEAD"))
    public void isShaderActive(float ff, long ll, CallbackInfo ci) {
        if (ClientUtils.inClickGui()) {
            //fastRenderer = Smok.inst.mc.gameSettings.fboEnable;

            if (Smok.inst.guiManager.getClickGui().renderingBlur) {
                //if (!fastRenderer) {
                //    Smok.inst.mc.gameSettings.fboEnable = true;
                //}

                if (Smok.inst.guiManager.getClickGui().allowRenderBlur) {
                    String path = "shaders/post/blur.json";

                    if (ColorUtils.isGooberDate() || Smok.inst.mc.getSession().getUsername().toLowerCase().startsWith("smellon")) {
                        path = "shaders/post/wobble.json";
                    }

                    this.loadShader(new ResourceLocation(path));
                    Smok.inst.guiManager.getClickGui().allowRenderBlur = false;
                }
            }
        } else {
            //if (fastRenderer) {
            //    Smok.inst.mc.gameSettings.fboEnable = fastRenderer;
            //    fastRenderer = false;
            //}
        }
    }

}