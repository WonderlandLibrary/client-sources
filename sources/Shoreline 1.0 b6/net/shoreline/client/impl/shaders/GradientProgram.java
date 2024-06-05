package net.shoreline.client.impl.shaders;

import net.minecraft.util.math.Vec2f;
import net.shoreline.client.api.render.shader.Program;
import net.shoreline.client.api.render.shader.Shader;
import net.shoreline.client.api.render.shader.Uniform;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.glUniform2f;

public class GradientProgram extends Program {
    Uniform<Vec2f> resolution = new Uniform<>("resolution");

    public GradientProgram() {
        super(new Shader("gradient.frag", GL_FRAGMENT_SHADER));
    }

    @Override
    public void initUniforms() {
        resolution.init(id);
    }

    @Override
    public void updateUniforms() {
        glUniform2f(resolution.getId(), resolution.get().x, resolution.get().y);
    }

    public void setUniforms(Vec2f resolution) {
        this.resolution.set(resolution);
    }
}
