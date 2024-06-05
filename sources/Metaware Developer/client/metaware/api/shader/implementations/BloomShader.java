package client.metaware.api.shader.implementations;

import client.metaware.api.shader.ShaderProgram;
import client.metaware.api.utils.MinecraftUtil;
import javafx.scene.effect.Bloom;
import net.minecraft.client.shader.Framebuffer;

import java.util.Arrays;

import static org.lwjgl.opengl.GL11.*;

public class BloomShader implements MinecraftUtil {

    private ShaderProgram bloomShader = new ShaderProgram("fragment/bloom.frag");

    private static Framebuffer bloomBuffer = new Framebuffer(1, 1, false);

    private float radius;

    public BloomShader(int radius){
        this.radius = radius;
    }

    public void bloom() {

        // horizontal bloom
        bloomShader.init();
        setupUniforms(1, 0, 0);
        bloomBuffer.framebufferClear();
        bloomBuffer.bindFramebuffer(true);
        glBindTexture(GL_TEXTURE_2D, mc.getFramebuffer().framebufferTexture);
        bloomShader.renderCanvas();
        bloomBuffer.unbindFramebuffer();


        // vertical bloom
        bloomShader.init();
        setupUniforms(0, 1, 0);
        mc.getFramebuffer().bindFramebuffer(true);
        glBindTexture(GL_TEXTURE_2D, bloomBuffer.framebufferTexture);
        bloomShader.renderCanvas();
        bloomShader.uninit();
    }

    public void setupUniforms(float x, float y, int textureID) {
        bloomShader.setUniformi("originalTexture", 0);
        bloomShader.setUniformi("checkedTexture", textureID);
        bloomShader.setUniformf("texelSize", (float) (1.0 / mc.displayWidth), (float) (1.0 / mc.displayHeight));
        bloomShader.setUniformf("direction", x, y);
        bloomShader.setUniformf("shadowAlpha", 120);
        bloomShader.setUniformf("radius", radius);
    }
}