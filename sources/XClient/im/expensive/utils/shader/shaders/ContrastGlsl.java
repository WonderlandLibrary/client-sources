package im.expensive.utils.shader.shaders;

import im.expensive.utils.shader.IShader;

public class ContrastGlsl implements IShader {

    @Override
    public String glsl() {
        return """
                #version 120

                uniform sampler2D texture;
                uniform float contrast;


                void main()
                {
                    vec4 color = texture2D(texture, gl_TexCoord[0].st);
                    gl_FragColor = vec4(vec3(mix(0, color.r, contrast),mix(0, color.g, contrast),mix(0, color.b, contrast)), 1);
                }
                """;
    }

}
