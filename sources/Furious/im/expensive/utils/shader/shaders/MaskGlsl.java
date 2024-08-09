package im.expensive.utils.shader.shaders;

import im.expensive.utils.shader.IShader;

public class MaskGlsl implements IShader {

    @Override
    public String glsl() {
        return """
                #version 120

                uniform vec2 location, rectSize;


                // rectangle

                vec4 rect(vec2 uv, vec2 location, vec2 rectSize) {
                    vec2 p = (uv - location) / rectSize;
                    return vec4(1.0, 1.0, 1.0, 1.0) * (step(0.0, p.x) * step(0.0, p.y) * step(p.x, 1.0) * step(p.y, 1.0));
                }

                void main() {
                    vec2 uv = gl_TexCoord[0].xy;
                    gl_FragColor = rect(uv, location, rectSize);
                }
                """;
    }

}
