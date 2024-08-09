package dev.luvbeeq.shader;

import dev.excellent.impl.util.render.color.ColorUtil;
import net.minecraft.client.shader.ShaderDefault;

public final class DepthShader extends AbstractShader {
    private ShaderDefault nearUniform;
    private ShaderDefault farUniform;
    private ShaderDefault resolution;
    private ShaderDefault distance;
    private ShaderDefault color1;
    private ShaderDefault color2;
    private ShaderDefault color3;
    private ShaderDefault color4;
    private ShaderDefault clientColor;
    private ShaderDefault saturation;

    public void saturation(final float value) {
        saturation.set(value);
    }

    public void clientColor(final boolean value) {
        clientColor.set(value ? 1.0F : 0.0F);
    }

    public void color1(final int color) {
        color1.set(ColorUtil.getRGBAf(color));
    }

    public void color2(final int color) {
        color2.set(ColorUtil.getRGBAf(color));
    }

    public void color3(final int color) {
        color3.set(ColorUtil.getRGBAf(color));
    }

    public void color4(final int color) {
        color4.set(ColorUtil.getRGBAf(color));
    }

    public void setNear(final float value) {
        nearUniform.set(value);
    }


    public void setFar(final float value) {
        farUniform.set(value);
    }

    public void setDistance(final float value) {
        distance.set(value);
    }

    public void setResolution(final float x, final float y) {
        resolution.set(x, y);
    }

    public void setDepthBuffer(final int buffer) {
        shaderInstance.setSampler("depthTex", () -> buffer);
    }

    public void setBlurredBuffer(final int buffer) {
        shaderInstance.setSampler("blur", () -> buffer);
    }

    public void setMinecraftBuffer(final int buffer) {
        shaderInstance.setSampler("minecraft", () -> buffer);
    }


    @Override
    public String getShaderName() {
        return "depth_shader";
    }

    @Override
    public Shader shader() {
        return Shader.DEPTH_SHADER;
    }

    @Override
    public void handleShaderLoad() {
        super.handleShaderLoad();
        nearUniform = shaderInstance.safeGetUniform("near");
        farUniform = shaderInstance.safeGetUniform("far");
        resolution = shaderInstance.safeGetUniform("resolution");
        distance = shaderInstance.safeGetUniform("distance");
        color1 = shaderInstance.safeGetUniform("color1");
        color2 = shaderInstance.safeGetUniform("color2");
        color3 = shaderInstance.safeGetUniform("color3");
        color4 = shaderInstance.safeGetUniform("color4");
        clientColor = shaderInstance.safeGetUniform("clientColor");
        saturation = shaderInstance.safeGetUniform("saturation");
    }
}
