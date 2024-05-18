package de.dietrichpaul.clientbase.util.render.api;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import ladysnake.satin.api.event.ResolutionChangeCallback;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedCoreShader;
import ladysnake.satin.api.managed.ManagedFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.Uniform1f;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public class Blur {

    private static ManagedCoreShader stencilShader;
    private static ManagedShaderEffect blurShader;
    private static ManagedFramebuffer blurred;
    private static SimpleFramebuffer stencil;
    private static Uniform1f antialias;

    public static void loadShaders() {
        ShaderEffectManager sm = ShaderEffectManager.getInstance();

        stencilShader = sm.manageCoreShader(new Identifier("clientbase", "stencil"), VertexFormats.POSITION_TEXTURE);
        antialias = stencilShader.findUniform1f("Antialias");

        blurShader = sm.manage(new Identifier("clientbase", "shaders/post/blur.json"));
        blurred = blurShader.getTarget("out");
        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            blurShader.render(tickDelta);
        });


        ResolutionChangeCallback.EVENT.register((newWidth, newHeight) -> {
            if (stencil == null)
                stencil = new SimpleFramebuffer(newWidth, newHeight, false, false);
            else stencil.resize(newWidth, newHeight, false);
        });
    }

    public static void beginStencil() {
        stencil.clear(false);
        stencil.beginWrite(false);
    }

    public static void endStencil(float antialias) {
        MinecraftClient.getInstance().getFramebuffer().beginWrite(true);

        Blur.antialias.set(antialias / 255.0F);

        ShaderProgram program = stencilShader.getProgram();
        program.addSampler("Sampler0", stencil);
        program.addSampler("Sampler1", blurred.getFramebuffer());

        Matrix4f matrix4f = new Matrix4f().setOrtho(0.0f, 1, 1, 0.0f, 1000.0f, 3000.0f);
        if (program.modelViewMat != null) {
            program.modelViewMat.set(new Matrix4f().translation(0.0f, 0.0f, -2000.0f));
        }
        if (program.projectionMat != null) {
            program.projectionMat.set(matrix4f);
        }

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        program.bind();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(0.0, 1, 0.0).texture(0.0f, 0.0f).next();
        bufferBuilder.vertex(1, 1, 0.0).texture(1, 0.0f).next();
        bufferBuilder.vertex(1, 0.0, 0.0).texture(1, 1).next();
        bufferBuilder.vertex(0.0, 0.0, 0.0).texture(0.0f, 1).next();
        BufferRenderer.draw(bufferBuilder.end());
        program.unbind();
    }

}
