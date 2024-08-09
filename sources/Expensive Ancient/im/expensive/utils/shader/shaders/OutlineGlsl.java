package im.expensive.utils.shader.shaders;

import im.expensive.utils.shader.IShader;

public class OutlineGlsl implements IShader {

    @Override
    public String glsl() {
        return """
                #version 120
                                
                uniform sampler2D textureIn, textureToCheck;
                uniform vec2 texelSize, direction;
                uniform float size;
                uniform vec3 color;
                                
                #define offset direction * texelSize
                                
                void main() {
                    if (direction.y == 1) {
                        if (texture2D(textureToCheck, gl_TexCoord[0].st).a != 0.0) discard;
                    }
                    vec4 innerAlpha = texture2D(textureIn, gl_TexCoord[0].st);
                            
                    for (float r = 1.0; r <= size; r ++) {
                        vec4 colorCurrent1 = texture2D(textureIn, gl_TexCoord[0].st + offset * r);
                        vec4 colorCurrent2 = texture2D(textureIn, gl_TexCoord[0].st - offset * r);
                                
                        innerAlpha += (colorCurrent1 + colorCurrent2);
                                
                    }
                                
                    gl_FragColor = vec4(color.rgb, mix(1, 1.0 - exp(-innerAlpha.a), step(0.0, direction.y)));
                }
                """;
    }

}
