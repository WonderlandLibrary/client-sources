package wtf.diablo.client.module.impl.render;

import net.minecraft.client.shader.Framebuffer;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.impl.client.renderering.EventShader;
import wtf.diablo.client.gui.clickgui.material.MaterialClickGUI;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.shader.shaders.KawaseBloom;
import wtf.diablo.client.shader.shaders.KawaseBlur;
import wtf.diablo.client.util.mc.player.chat.ChatUtil;
import wtf.diablo.client.util.render.gl.FramebufferUtils;


@ModuleMetaData(
        name = "Effects",
        description = "Adds effects to the game",
        category = ModuleCategoryEnum.RENDER
)
public final class EffectsModule extends AbstractModule {
    private Framebuffer stencilFramebuffer = new Framebuffer(1, 1, false);
    private final BooleanSetting blur = new BooleanSetting("Blur", true);

    private final BooleanSetting bloom = new BooleanSetting("Bloom", true);

    private final NumberSetting<Integer> shadowRadius = new NumberSetting<>("Glow Iterations", 3, 1, 8, 1);
    private final NumberSetting<Integer> shadowOffset = new NumberSetting<>("Glow Offset", 2, 1, 10, 1);
    private final NumberSetting<Integer> iterations = new NumberSetting<>("Blur Iterations", 2, 1, 8, 1);
    private final NumberSetting<Integer> offset = new NumberSetting<>("Blur Offset", 2, 1, 10, 1);

    public EffectsModule() {
        this.registerSettings(
                this.blur,
                this.bloom,
                this.shadowRadius,
                this.shadowOffset,
                this.iterations,
                this.offset
        );
    }

    private void blurThings(final boolean bloom)
    {
    }

    public void blurScreen() {
        if (mc.gameSettings.ofFastRender) {
            ChatUtil.addChatMessage("Please disable fast render to use Blur and Bloom.");
            return;
        }


        if (this.blur.getValue()) {
            this.stencilFramebuffer = FramebufferUtils.createFrameBuffer(stencilFramebuffer);

            this.stencilFramebuffer.framebufferClear();
            this.stencilFramebuffer.bindFramebuffer(false);
            Diablo.getInstance().getEventBus().call(new EventShader(false));
            this.blurThings(false);
            this.stencilFramebuffer.unbindFramebuffer();

            KawaseBlur.renderBlur(this.stencilFramebuffer.framebufferTexture, this.iterations.getValue(), this.offset.getValue());
        }

        if (this.bloom.getValue()) {
            this.stencilFramebuffer = FramebufferUtils.createFrameBuffer(stencilFramebuffer);
            this.stencilFramebuffer.framebufferClear();
            this.stencilFramebuffer.bindFramebuffer(false);
            Diablo.getInstance().getEventBus().call(new EventShader(true));
            this.blurThings(true);

            this.stencilFramebuffer.unbindFramebuffer();

            KawaseBloom.renderBlur(this.stencilFramebuffer.framebufferTexture, this.shadowRadius.getValue(), this.shadowOffset.getValue());
        }
    }
}