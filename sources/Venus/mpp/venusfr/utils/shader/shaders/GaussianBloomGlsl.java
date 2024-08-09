/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.shader.shaders;

import mpp.venusfr.utils.shader.IShader;

public class GaussianBloomGlsl
implements IShader {
    @Override
    public String glsl() {
        return "#version 120\n\nuniform sampler2D textureIn, textureToCheck;\nuniform vec2 texelSize, direction;\nuniform float exposure, radius;\nuniform float weights[128];\nuniform bool avoidTexture;\n\n#define offset direction * texelSize\n\nvoid main() {\n    if (direction.y >= 1 && avoidTexture) {\n        if (texture2D(textureToCheck, gl_TexCoord[0].st).a != 0.0) discard;\n    }\n    vec4 innerAlpha = texture2D(textureIn, gl_TexCoord[0].st);\n    innerAlpha *= innerAlpha.a;\n    innerAlpha *= weights[0];\n\n\n\n    for (float r = 1.0; r <= radius; r ++) {\n        vec4 colorCurrent1 = texture2D(textureIn, gl_TexCoord[0].st + offset * r);\n        vec4 colorCurrent2 = texture2D(textureIn, gl_TexCoord[0].st - offset * r);\n\n        colorCurrent1.rgb *= colorCurrent1.a;\n        colorCurrent2.rgb *= colorCurrent2.a;\n\n        innerAlpha += (colorCurrent1 + colorCurrent2) * weights[int(r)];\n\n    }\n\n    gl_FragColor = vec4(innerAlpha.rgb / innerAlpha.a, mix(innerAlpha.a, 1.0 - exp(-innerAlpha.a * exposure), step(0.0, direction.y)));\n}\n";
    }
}

