package im.expensive.utils.shader.shaders;

import static net.minecraft.util.math.MathHelper.rotWrap;

import im.expensive.utils.shader.IShader;

public class KawaseUpGlsl implements IShader {

    @Override
    public String glsl() {
        return """
                #version 120
                uniform sampler2D image;
                uniform float offset;
                uniform vec2 resolution;
                
                void main()
                {
                    vec2 uv = gl_TexCoord[0].xy / 2.0;
                    vec2 halfpixel = resolution / 2.0;
                    vec3 sum = texture2D(image, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset).rgb;
                    sum += texture2D(image, uv + vec2(-halfpixel.x, halfpixel.y) * offset).rgb * 2.0;
                    sum += texture2D(image, uv + vec2(0.0, halfpixel.y * 2.0) * offset).rgb;   
                    sum += texture2D(image, uv + vec2(halfpixel.x, halfpixel.y) * offset).rgb * 2.0;
                    sum += texture2D(image, uv + vec2(halfpixel.x * 2.0, 0.0) * offset).rgb;
                    sum += texture2D(image, uv + vec2(halfpixel.x, -halfpixel.y) * offset).rgb * 2.0; 
                    sum += texture2D(image, uv + vec2(0.0, -halfpixel.y * 2.0) * offset).rgb;  
                    sum += texture2D(image, uv + vec2(-halfpixel.x, -halfpixel.y) * offset).rgb * 2.0; 
                    gl_FragColor = vec4(sum / 12.0, 1);
                }
                """;
    }

    @Override
    public String getName() {
        return "upkawasi";
    }

}
