package dev.luvbeeq.shader;

import dev.excellent.impl.util.render.CustomFramebuffer;
import dev.excellent.impl.util.shader.ShaderLink;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;


public enum KawaseBlur {
    INSTANCE;

    public final CustomFramebuffer BLURRED = new CustomFramebuffer(false).setLinear();
    public final CustomFramebuffer ADDITIONAL = new CustomFramebuffer(false).setLinear();
    Minecraft mc = Minecraft.getInstance();
    Framebuffer mcFramebuffer = mc.getFramebuffer();

    ShaderLink kawaseUp = ShaderLink.create("kawaseUpF");
    ShaderLink kawaseDown = ShaderLink.create("kawaseDownF");

    public void updateBlur(float offset, int steps) {
        ADDITIONAL.setup(true);
        mc.gameRenderer.buffer.bindFramebufferTexture();
        kawaseDown.init();
        kawaseDown.setUniformf("offset", offset);
        kawaseDown.setUniformf("resolution", 1f / mc.getMainWindow().getWidth(),
                1f / mc.getMainWindow().getHeight());
        CustomFramebuffer.drawTexture();
        CustomFramebuffer[] buffers = {this.ADDITIONAL, this.BLURRED};
        for (int i = 1; i < steps; ++i) {
            int step = i % 2;
            buffers[step].setup(true);
            buffers[(step + 1) % 2].draw();
            buffers[step].stop();
        }
        kawaseUp.init();
        kawaseUp.setUniformf("offset", offset);
        kawaseUp.setUniformf("resolution", 1f / mc.getMainWindow().getWidth(),
                1f / mc.getMainWindow().getHeight());
        for (int i = 0; i < steps; ++i) {
            int step = i % 2;
            buffers[(step + 1) % 2].setup(true);
            buffers[step].draw();
            buffers[step].stop();
        }
        kawaseUp.unload();
        mcFramebuffer.bindFramebuffer(false);
        ADDITIONAL.stop();
    }
}
