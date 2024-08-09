package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.event.impl.render.Render3DLastEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.util.shader.factory.RenderFactory;
import dev.excellent.impl.util.shader.impl.BloomShader;
import dev.excellent.impl.util.shader.impl.BlurShader;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.NumberValue;

@ModuleInfo(name = "Glass Hands", description = "Стеклянные руки", category = Category.RENDER)
public class GlassHands extends Module {
    public static Singleton<GlassHands> singleton = Singleton.create(() -> Module.link(GlassHands.class));
    public final BlurShader BLUR_SHADER = new BlurShader();
    public final BloomShader BLOOM_SHADER = new BloomShader();
    public final BooleanValue blur = new BooleanValue("Блюр", this, false);
    public final BooleanValue bloom = new BooleanValue("Блум", this, true);
    public final NumberValue saturation = new NumberValue("Яркость", this, 1.0F, 0.0F, 2.0F, 0.05F);
    private final Listener<Render2DEvent> onRender2D = event -> {
        if (blur.getValue()) {
            RenderFactory.addTask(() ->
                    BLUR_SHADER.draw(getSettings()[0], getSettings()[1], BlurShader.RenderType.DISPLAY, saturation.getValue().floatValue()));
        }
        if (bloom.getValue()) {
            RenderFactory.addTask(() ->
                    BLOOM_SHADER.draw(getSettings()[0], getSettings()[1], BloomShader.RenderType.DISPLAY, saturation.getValue().floatValue()));
        }
    };
    private final Listener<Render3DLastEvent> onRender3DLast = event -> {
        if (blur.getValue()) {
            BLUR_SHADER.addTask3D(() ->
                    mc.gameRenderer.renderHand(event.getMatrix(), event.getActiveRenderInfo(), event.getPartialTicks(), true, true, false));
            BLUR_SHADER.draw(getSettings()[0], getSettings()[1], BlurShader.RenderType.CAMERA, saturation.getValue().floatValue());
        }
        if (bloom.getValue()) {
            BLOOM_SHADER.addTask3D(() ->
                    mc.gameRenderer.renderHand(event.getMatrix(), event.getActiveRenderInfo(), event.getPartialTicks(), true, true, false));
            BLOOM_SHADER.draw(getSettings()[0], getSettings()[1], BloomShader.RenderType.CAMERA, saturation.getValue().floatValue());
        }
    };

    public int[] getSettings() {
        return new int[]{4, 4};
    }

    @Override
    public void toggle() {
        super.toggle();
        reset();
    }

    private void reset() {
        BLUR_SHADER.cameraBlurQueue.clear();
        BLUR_SHADER.displayBlurQueue.clear();
        BLUR_SHADER.reset();

        BLOOM_SHADER.cameraBlurQueue.clear();
        BLOOM_SHADER.displayBlurQueue.clear();
        BLOOM_SHADER.reset();
    }
}