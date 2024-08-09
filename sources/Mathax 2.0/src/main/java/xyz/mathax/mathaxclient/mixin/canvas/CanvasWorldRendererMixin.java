package xyz.mathax.mathaxclient.mixin.canvas;

// TODO: 1.19.3
//@Mixin(value = CanvasWorldRenderer.class, remap = false)
public class CanvasWorldRendererMixin {
    /*@ModifyVariable(method = "renderWorld", at = @At("LOAD"), name = "blockOutlines")
    private boolean renderWorld_blockOutlines(boolean blockOutlines) {
        if (Modules.get().isEnabled(BlockSelection.class)) return false;
        return blockOutlines;
    }
    @Inject(method = "renderWorld", at = @At("HEAD"))
    private void onRenderHead(MatrixStack viewMatrixStack, float tickDelta, long frameStartNanos, boolean blockOutlines, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, net.minecraft.class_1159 projectionMatrix, CallbackInfo info) {
        PostProcessShaders.beginRender();
    }*/

    // Injected through ASM because mixins are fucking retarded and don't work outside of development environment for this one injection
    /*@Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/OutlineVertexConsumerProvider;draw()V", shift = At.Shift.AFTER))
    private void onRenderOutlines(CallbackInfo info) {
        PostProcessShaders.endRender();
    }*/
}