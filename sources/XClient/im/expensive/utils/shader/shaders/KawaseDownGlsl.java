package im.expensive.utils.shader.shaders;

import static net.minecraft.util.math.MathHelper.rotWrap;

import im.expensive.utils.shader.IShader;

public class KawaseDownGlsl implements IShader {

    @Override
    public String glsl() {
        return "#version 120\n" +
                "\n" +
                "uniform sampler2D image;\n" +
                "uniform float offset;\n" +
                "uniform vec2 resolution;\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "    vec2 uv = gl_TexCoord[0].xy * 2.0;\n" +
                "    vec2 halfpixel = resolution * 2.0;\n" +
                "    vec3 sum = texture2D(image, uv).rgb * 4.0;\n" +
                "    sum += texture2D(image, uv - halfpixel.xy * offset).rgb;\n" +
                "    sum += texture2D(image, uv + halfpixel.xy * offset).rgb;\n" +
                "    sum += texture2D(image, uv + vec2(halfpixel.x, -halfpixel.y) * offset).rgb;\n" +
                "    sum += texture2D(image, uv - vec2(halfpixel.x, -halfpixel.y) * offset).rgb;\n" +
                "    gl_FragColor = vec4(sum / 8.0, 1);\n" +
                "}";
    }

    @Override
    public String getName() {
        return "downkawasi";
    }
}
