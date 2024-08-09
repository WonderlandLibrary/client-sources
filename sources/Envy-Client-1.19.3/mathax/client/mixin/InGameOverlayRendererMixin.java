package mathax.client.mixin;

import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.render.NoRender;
import mathax.client.systems.modules.render.SmallFire;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRendererMixin {
    @Inject(method = "renderFireOverlay", at = @At("HEAD"))
    private static void renderFireOverlay(MinecraftClient client, MatrixStack matrices, CallbackInfo ci) {
        SmallFire smallFire = Modules.get().get(SmallFire.class);
        if (smallFire.isActive()) {
            Vector3f scale = smallFire.getFireScale();
            Vector3f position = smallFire.getFirePosition();

            matrices.scale(scale.x, scale.y, scale.z);
            matrices.translate(position.x, position.y, position.z);
        }
    }

    @Inject(method = "renderFireOverlay", at = @At("HEAD"), cancellable = true)
    private static void onRenderFireOverlay(MinecraftClient minecraftClient, MatrixStack matrixStack, CallbackInfo info) {
        if (Modules.get().get(NoRender.class).noFireOverlay()) info.cancel();
    }

    @Inject(method = "renderUnderwaterOverlay", at = @At("HEAD"), cancellable = true)
    private static void onRenderUnderwaterOverlay(MinecraftClient minecraftClient, MatrixStack matrixStack, CallbackInfo info) {
        if (Modules.get().get(NoRender.class).noWaterOverlay()) info.cancel();
    }

    @Inject(method = "renderInWallOverlay", at = @At("HEAD"), cancellable = true)
    private static void render(Sprite sprite, MatrixStack matrices, CallbackInfo info) {
        if (Modules.get().get(NoRender.class).noInWallOverlay()) info.cancel();
    }
}
