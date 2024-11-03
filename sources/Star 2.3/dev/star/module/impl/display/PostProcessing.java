package dev.star.module.impl.display;

import dev.star.Client;
import dev.star.event.impl.render.ShaderEvent;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.impl.render.ClickGUIMod;
import dev.star.module.settings.ParentAttribute;
import dev.star.module.settings.impl.BooleanSetting;
import dev.star.module.settings.impl.NumberSetting;
import dev.star.utils.render.RenderUtil;
import dev.star.utils.render.blur.KawaseBloom;
import dev.star.utils.render.blur.KawaseBlur;
import net.minecraft.client.shader.Framebuffer;

public class PostProcessing extends Module {
    public final BooleanSetting blur = new BooleanSetting("Blur", true);
    private final NumberSetting iterations = new NumberSetting("Blur Iterations", 2, 8, 1, 1);
    private final NumberSetting offset = new NumberSetting("Blur Offset", 2, 10, 1, 1);
    private final BooleanSetting bloom = new BooleanSetting("Bloom", true);
    private final NumberSetting shadowRadius = new NumberSetting("Bloom Iterations", 2, 8, 1, 1);
    private final NumberSetting shadowOffset = new NumberSetting("Bloom Offset", 1, 10, 1, 1);

    public PostProcessing() {
        super("Shader", Category.DISPLAY, "blurs shit");
        shadowRadius.addParent(bloom, ParentAttribute.BOOLEAN_CONDITION);
        shadowOffset.addParent(bloom, ParentAttribute.BOOLEAN_CONDITION);
        addSettings(blur, iterations, offset, bloom, shadowRadius, shadowOffset);
        this.setToggled(true);
    }

    public void stuffToBlur() {
        if (mc.currentScreen == ClickGUIMod.dropdownClickGui) {
            ClickGUIMod.dropdownClickGui.renderEffects();
        }

        RenderUtil.resetColor();
        NotificationsMod notificationsMod = Client.INSTANCE.getModuleCollection().getModule(NotificationsMod.class);
        if (notificationsMod.isEnabled()) {
            notificationsMod.renderEffects();
        }
    }

    private Framebuffer stencilFramebuffer = new Framebuffer(1, 1, false);

    public void blurScreen() {
        if (!enabled) return;
        if (blur.isEnabled()) {
            stencilFramebuffer = RenderUtil.createFrameBuffer(stencilFramebuffer);

            stencilFramebuffer.framebufferClear();
            stencilFramebuffer.bindFramebuffer(false);
            Client.INSTANCE.getEventProtocol().handleEvent(new ShaderEvent(false));
            stuffToBlur();
            stencilFramebuffer.unbindFramebuffer();

            KawaseBlur.renderBlur(stencilFramebuffer.framebufferTexture, iterations.getValue().intValue(), offset.getValue().intValue());
        }

        if (bloom.isEnabled()) {
            stencilFramebuffer = RenderUtil.createFrameBuffer(stencilFramebuffer);
            stencilFramebuffer.framebufferClear();
            stencilFramebuffer.bindFramebuffer(false);

            Client.INSTANCE.getEventProtocol().handleEvent(new ShaderEvent(true));
            stuffToBlur();

            stencilFramebuffer.unbindFramebuffer();

            KawaseBloom.renderBlur(stencilFramebuffer.framebufferTexture, shadowRadius.getValue().intValue(), shadowOffset.getValue().intValue());
        }
    }
}
