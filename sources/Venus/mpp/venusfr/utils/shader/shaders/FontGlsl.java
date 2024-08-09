/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.shader.shaders;

import mpp.venusfr.utils.shader.IShader;

public class FontGlsl
implements IShader {
    @Override
    public String glsl() {
        return "    #version 120\n\n\nuniform sampler2D Sampler;\nuniform vec2 TextureSize;\nuniform float Range; // distance field range of the msdf font texture\nuniform float EdgeStrength;\nuniform float Thickness;\nuniform vec4 color;\nuniform bool Outline; // if false, outline computation will be ignored (and its uniforms)\nuniform float OutlineThickness;\nuniform vec4 OutlineColor;\n\n\nfloat median(float red, float green, float blue) {\n  return max(min(red, green), min(max(red, green), blue));\n}\n\nvoid main() {\n    vec4 texColor = texture2D(Sampler, gl_TexCoord[0].st);\n\n    float dx = dFdx(gl_TexCoord[0].x) * TextureSize.x;\n    float dy = dFdy(gl_TexCoord[0].y) * TextureSize.y;\n    float toPixels = Range * inversesqrt(dx * dx + dy * dy);\n\n    float sigDist = median(texColor.r, texColor.g, texColor.b) - 0.5 + Thickness;\n\n\n    float alpha = smoothstep(-EdgeStrength, EdgeStrength, sigDist * toPixels);\n    if (Outline) {\n        float outlineAlpha = smoothstep(-EdgeStrength, EdgeStrength, (sigDist + OutlineThickness) * toPixels) - alpha;\n        float finalAlpha = alpha * color.a + outlineAlpha * color.a;\n\n        gl_FragColor = vec4(mix(OutlineColor.rgb, color.rgb, alpha), finalAlpha);\n        return;\n    }\n    gl_FragColor = vec4(color.rgb, color.a * alpha);\n}\n";
    }
}

