package wtf.diablo.client.shader.actual;

public final class ShaderConstants {

    public static final String ROUNDED_RECTANGLE = "#version 330 core\n" +
            "\n" +
            "uniform vec2 position, size;\n" +
            "uniform vec4 radius, color;\n" +
            "\n" +
            "float roundedRect(vec2 roundedPosition, vec2 absoluteSize, vec4 radius)\n" +
            "{\n" +
            "    bool variable = roundedPosition.x > 0.0;\n" +
            "    bool variable2 = roundedPosition.y > 0.0;\n" +
            "\n" +
            "    radius.xy = (variable) ? radius.xy : radius.zw;\n" +
            "    radius.x  = (variable2) ? radius.x : radius.y;\n" +
            "\n" +
            "    vec2 positionAndSizeFactorVariable = abs(roundedPosition) - absoluteSize + radius.x;\n" +
            "    return min(max(positionAndSizeFactorVariable.x, positionAndSizeFactorVariable.y), 0.0) + length(max(positionAndSizeFactorVariable, 0.0)) - radius.x;\n" +
            "}\n" +
            "\n" +
            "void main( void )\n" +
            "{\n" +
            "    float distRect = roundedRect(gl_FragCoord.xy - position - size / 2.0F, size / 2.0F, radius);\n" +
            "    float smoothedRectAlpha = 1.0F - smoothstep(0.0F, 3.0F, distRect);\n" +
            "    vec4 actualRoundColor = vec4(color.xyz, 0.0);\n" +
            "    actualRoundColor = mix(actualRoundColor, color, smoothedRectAlpha);\n" +
            "    gl_FragColor = actualRoundColor;\n" +
            "}\n";


    public static final String ROUNDED_RECTANGLE_OUTLINE = "#version 330 core\n" +
            "\n" +
            "uniform vec2 position, size;\n" +
            "uniform vec4 radius, color, outlineColor;\n" +
            "uniform float outlineThickness;\n" +
            "\n" +
            "float roundedRect(vec2 roundedPosition, vec2 absoluteSize, vec4 radius)\n" +
            "{\n" +
            "    vec2 positionAndSizeFactorVariable = abs(roundedPosition) - absoluteSize + radius.x;\n" +
            "    return min(max(positionAndSizeFactorVariable.x, positionAndSizeFactorVariable.y), 0.0) + length(max(positionAndSizeFactorVariable, 0.0)) - radius.x;\n" +
            "}\n" +
            "\n" +
            "void main( void )\n" +
            "{\n" +
            "    float distance = roundedRect(gl_FragCoord.xy - position - (size * .5), (size * .5) + (outlineThickness *.5) - 1.0, radius);\n" +
            "    float xCoord = abs(distance) - (outlineThickness * .5);\n" +
            "    float smoothedRectAlpha = smoothstep(0.0, 2.0, xCoord);\n" +
            "\n" +
            "    vec4 actualRoundInsideColor;\n" +
            "\n" +
            "    if (distance < 0) {\n" +
            "        actualRoundInsideColor = color;\n" +
            "    } else {\n" +
            "        actualRoundInsideColor = vec4(outlineColor.rgb,  0.0);\n" +
            "    }\n" +
            "\n" +
            "    gl_FragColor = mix(outlineColor, actualRoundInsideColor, smoothedRectAlpha);\n" +
            "}\n";


    public static final String MAIN_MENU = "// made by gaston\n" +
            "#ifdef GL_ES\n" +
            "precision highp float;\n" +
            "#endif\n" +
            "\n" +
            "uniform float time;\n" +
            "uniform vec2 resolution;\n" +
            "\n" +
            "const int num = 50;\n" +
            "\n" +
            "void main( void ) {\n" +
            "    float sum = 0.;\n" +
            "    float size = resolution.x / 2000.0;\n" +
            "    for (int i = 0; i < num; ++i) {\n" +
            "        vec2 position = resolution / 2.0;\n" +
            "\tfloat t = (float(i) + time) / 5.0;\n" +
            "\tfloat c = float(i) * 4.0;\n" +
            "        position.x += tan(8.0 * t + c) * resolution.x * .2;\n" +
            "        position.y += sin(t) * resolution.y * 0.5;\n" +
            "\n" +
            "        sum += size / length(gl_FragCoord.xy - position);\n" +
            "    }\n" +
            "\tsum=pow(sum,0.65);\n" +
            "    gl_FragColor = vec4(sum * 0.66, sum * 0.57, sum*0.9, 1);\n" +
            "}";

    public static final String KAWASE_UP_BLUR =  "#version 330 core\n" +
            "\n" +
            "out vec4 fragColor;\n" +
            "\n" +
            "uniform sampler2D texture, texture2;\n" +
            "uniform vec2 offset, eachPixel, resolution;\n" +
            "uniform int temp;\n" +
            "#define pixel (eachPixel * offset)\n" +
            "\n" +
            "void main( void ) \n" +
            "{\n" +
            "\n" +
            " vec2 position = vec2(gl_FragCoord.xy / resolution);\n" +
            "    float x = pixel.x;\n" +
            "    float y = pixel.y;\n" +
            "\n" +
            "    vec4 value = texture2D(texture, position + vec2(-pixel.x * 2.0, 0.0));\n" +
            "    value += texture2D(texture, position + vec2(-pixel.x, pixel.y) * offset) * 2.0;\n" +
            "    value += texture2D(texture, position + vec2(0.0, pixel.y * 2.0));\n" +
            "    value += texture2D(texture, position + vec2(pixel.x, pixel.y) * offset) * 2.0;\n" +
            "    value += texture2D(texture, position + vec2(pixel.x * 2.0, 0.0));\n" +
            "    value += texture2D(texture, position + vec2(pixel.x, -pixel.y) * offset) * 2.0;\n" +
            "    value += texture2D(texture, position + vec2(0.0, -pixel.y * 2.0));\n" +
            "    value += texture2D(texture, position + vec2(-pixel.x, -pixel.y)) * 2.0;\n" +
            "\n" +
            "    gl_FragColor = vec4(value.rgb / 13.0, mix(1.0, texture2D(texture2, position.xy).a, temp));\n" +
            "\n" +
            "}\n";


    public static final String KAWASE_DOWN_BLUR = "#version 330 core\n" +
            "\n" +
            "out vec4 fragColor;\n" +
            "\n" +
            "uniform sampler2D texture;\n" +
            "uniform vec2 offset, eachPixel, resolution;\n" +
            "\n" +
            "#define pixel (eachPixel * offset)\n" +
            "\n" +
            "void main( void ) {\n" +
            "\n" +
            "    // The position of the pixel(s).\n" +
            "    vec2 position = gl_FragCoord.xy / resolution;\n" +
            "\n" +
            "    // The color value.\n" +
            "    vec4 value = texture2D(texture, position) * 4.0;\n" +
            "\n" +
            "    // Increment color and do math yay my favorite.\n" +
            "    value += texture2D(texture, position + pixel * offset);\n" +
            "\n" +
            "    // Increment color and do math yay my favorite.\n" +
            "    value += texture2D(texture, position - pixel * offset);\n" +
            "\n" +
            "    // Increment color and do math yay my favorite.\n" +
            "    value += texture2D(texture, position + vec2(pixel.x, -pixel.y) * offset);\n" +
            "\n" +
            "    // Increment color and do math yay my favorite.\n" +
            "    value += texture2D(texture, position - vec2(pixel.x, -pixel.y) * offset);\n" +
            "\n" +
            "    // Set fragment color to a new vec4 with the color divided by 8 and the alpha set to 1.5.\n" +
            "    fragColor = vec4(value.rgb / 9.0, 1.0);\n" +
            "}";

    // need to recode this
    public static final String KAWASE_UP_GLOW =  "#version 120\n" +
            "uniform sampler2D texture, texture2;\n" +
            "uniform vec2 eachPixel, offset, resolution;\n" +
            "uniform int tempInt;\n" +
            "\n" +
            "void main() {\n" +
            "    vec2 pos = vec2(gl_FragCoord.xy / resolution);\n" +
            "\n" +
            "    vec4 value = texture2D(texture, pos + vec2(-eachPixel.x * 2.0, 0.0) * offset);\n" +
            "    value.rgb *= value.a;\n" +
            "\n" +
            "    for (int i = 0; i <= 1; i++) {\n" +
            "        vec4 smpl1 =  texture2D(texture, pos + vec2(-eachPixel.x, eachPixel.y) * offset);\n" +
            "        smpl1.rgb *= smpl1.a;\n" +
            "        value += smpl1 * 2.0;\n" +
            "    }\n" +
            "\n" +
            "    for (int j = 0; j <= 1; j++) {\n" +
            "        vec4 smp2 = texture2D(texture, pos + vec2(0.0, eachPixel.y * 2.0) * offset);\n" +
            "        smp2.rgb *= smp2.a;\n" +
            "        value += smp2;\n" +
            "    }\n" +
            "\n" +
            "    for (int k = 0; k <= 1; k++) {\n" +
            "        vec4 smp3 = texture2D(texture, pos + vec2(eachPixel.x, eachPixel.y) * offset);\n" +
            "        smp3.rgb *= smp3.a;\n" +
            "        value += smp3 * 2.0;\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "    for (int l = 0; l <= 1; l++) {\n" +
            "        vec4 smp4 = texture2D(texture, pos + vec2(eachPixel.x * 2.0, 0.0) * offset);\n" +
            "        smp4.rgb *= smp4.a;\n" +
            "        value += smp4;\n" +
            "    }\n" +
            "\n" +
            "    for (int m = 0; m <= 1; m++) {\n" +
            "        vec4 smp5 = texture2D(texture, pos + vec2(eachPixel.x, -eachPixel.y) * offset);\n" +
            "        smp5.rgb *= smp5.a;\n" +
            "        value += smp5 * 2.0;\n" +
            "    }\n" +
            "\n" +
            "    for (int n = 0; n <= 1; n++) {\n" +
            "        vec4 smp6 = texture2D(texture, pos + vec2(0.0, -eachPixel.y * 2.0) * offset);\n" +
            "        smp6.rgb *= smp6.a;\n" +
            "        value += smp6;\n" +
            "    }\n" +
            "\n" +
            "    for (int o = 0; o <= 1; o++) {\n" +
            "        vec4 smp7 = texture2D(texture, pos + vec2(-eachPixel.x, -eachPixel.y) * offset);\n" +
            "        smp7.rgb *= smp7.a;\n" +
            "        value += smp7 * 2.0;\n" +
            "    }\n" +
            "\n" +
            "    vec4 result = value / 24.0;\n" +
            "    gl_FragColor = vec4(result.rgb / result.a, mix(result.a, result.a * (1.0 - texture2D(texture2, gl_TexCoord[0].st).a),tempInt));\n" +
            "}\n";


    public static final String KAWASE_DOWN_GLOW = "#version 330 core\n" +
            "\n" +
            "out vec4 fragColor;\n" +
            "\n" +
            "uniform sampler2D texture;\n" +
            "uniform vec2 offset, eachPixel, resolution;\n" +
            "#define pixel (eachPixel * offset)\n" +
            "\n" +
            "void initValues() {\n" +
            "    vec4 value = vec4(texture2D(texture, gl_FragCoord.xy / resolution) * 4.0);\n" +
            "    value.rgb *= value.a;\n" +
            "\n" +
            "    vec4 texture1 = texture2D(texture, gl_FragCoord.xy / resolution - pixel.xy * offset);\n" +
            "    texture1.rgb *= texture1.a;\n" +
            "    value += texture1;\n" +
            "\n" +
            "\n" +
            "    vec4 texture2 = texture2D(texture, gl_FragCoord.xy / resolution + pixel.xy * offset);\n" +
            "    texture2.rgb *= texture2.a;\n" +
            "    value += texture2;\n" +
            "\n" +
            "\n" +
            "    vec4 texture3 = texture2D(texture, gl_FragCoord.xy / resolution + (vec2(pixel.x, -pixel.y) * offset));\n" +
            "    texture3.rgb *= texture3.a;\n" +
            "    value += texture3;\n" +
            "\n" +
            "\n" +
            "    vec4 texture4 = texture2D(texture, gl_FragCoord.xy / resolution - vec2(pixel.x, -pixel.y) * offset);\n" +
            "    texture4.rgb *= texture4.a;\n" +
            "    value += texture4;\n" +
            "    fragColor = vec4(value.rgb / 8.0, value.a / 6.0);\n" +
            "}\n" +
            "\n" +
            "void main( void ) {\n" +
            "    initValues();\n" +
            "}\n";
}