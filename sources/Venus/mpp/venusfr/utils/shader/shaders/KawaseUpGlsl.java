/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.shader.shaders;

import mpp.venusfr.utils.shader.IShader;

public class KawaseUpGlsl
implements IShader {
    @Override
    public String glsl() {
        return "#version 120\nuniform sampler2D image;\nuniform float offset;\nuniform vec2 resolution;\n\nvoid main()\n{\n    vec2 uv = gl_TexCoord[0].xy / 2.0;\n    vec2 halfpixel = resolution / 2.0;\n    vec3 sum = texture2D(image, uv + vec2(-halfpixel.x * 2.0, 0.0) * offset).rgb;\n    sum += texture2D(image, uv + vec2(-halfpixel.x, halfpixel.y) * offset).rgb * 2.0;\n    sum += texture2D(image, uv + vec2(0.0, halfpixel.y * 2.0) * offset).rgb;\n    sum += texture2D(image, uv + vec2(halfpixel.x, halfpixel.y) * offset).rgb * 2.0;\n    sum += texture2D(image, uv + vec2(halfpixel.x * 2.0, 0.0) * offset).rgb;\n    sum += texture2D(image, uv + vec2(halfpixel.x, -halfpixel.y) * offset).rgb * 2.0;\n    sum += texture2D(image, uv + vec2(0.0, -halfpixel.y * 2.0) * offset).rgb;\n    sum += texture2D(image, uv + vec2(-halfpixel.x, -halfpixel.y) * offset).rgb * 2.0;\n    gl_FragColor = vec4(sum / 12.0, 1);\n}\n";
    }

    @Override
    public String getName() {
        return "upkawasi";
    }
}

