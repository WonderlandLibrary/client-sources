package im.expensive.utils.shader.shaders;

import im.expensive.utils.shader.IShader;

/**
 * SmoothGlsl
 */
public class SmoothGlsl implements IShader {

    @Override
    public String glsl() {
       return "\n" +
       "uniform vec2 location, rectSize;\n" +
       "uniform vec4 color;\n" +
       "uniform float radius;\n" +
       "uniform bool blur;\n" +
       "\n" +
       "float roundSDF(vec2 p, vec2 b, float r) {\n" +
       "    return length(max(abs(p) - b, 0.0)) - r;\n" +
       "}\n" +
       "\n" +
       "\n" +
       "void main() {\n" +
       "    vec2 rectHalf = rectSize * .5;\n" +
       "    // Smooth the result (free antialiasing).\n" +
       "    float smoothedAlpha =  (1.0-smoothstep(0.0, 1.0, roundSDF(rectHalf - (gl_TexCoord[0].st * rectSize), rectHalf - radius - 1., radius))) * color.a;\n" +
       "    gl_FragColor = vec4(color.rgb, smoothedAlpha);// mix(quadColor, shadowColor, 0.0);\n" +
       "\n" +
       "}";
    }

    

}