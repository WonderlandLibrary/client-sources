package im.expensive.utils.shader.shaders;

import im.expensive.utils.shader.IShader;

public class GaussianBloomGlsl implements IShader {

    @Override
    public String glsl() {
        return """
                #version 120

                uniform sampler2D textureIn, textureToCheck;
                uniform vec2 texelSize, direction;
                uniform float exposure, radius;
                uniform float weights[128];
                uniform bool avoidTexture;

                #define offset direction * texelSize

                void main() {
                    if (direction.y >= 1 && avoidTexture) {
                        if (texture2D(textureToCheck, gl_TexCoord[0].st).a != 0.0) discard;
                    }
                    vec4 innerAlpha = texture2D(textureIn, gl_TexCoord[0].st);
                    innerAlpha *= innerAlpha.a;
                    innerAlpha *= weights[0];



                    for (float r = 1.0; r <= radius; r ++) {
                        vec4 colorCurrent1 = texture2D(textureIn, gl_TexCoord[0].st + offset * r);
                        vec4 colorCurrent2 = texture2D(textureIn, gl_TexCoord[0].st - offset * r);

                        colorCurrent1.rgb *= colorCurrent1.a;
                        colorCurrent2.rgb *= colorCurrent2.a;

                        innerAlpha += (colorCurrent1 + colorCurrent2) * weights[int(r)];

                    }

                    gl_FragColor = vec4(innerAlpha.rgb / innerAlpha.a, mix(innerAlpha.a, 1.0 - exp(-innerAlpha.a * exposure), step(0.0, direction.y)));
                }
                        """;
    }

}
