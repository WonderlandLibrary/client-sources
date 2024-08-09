/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.shader.shaders;

import mpp.venusfr.utils.shader.IShader;

public class MaskGlsl
implements IShader {
    @Override
    public String glsl() {
        return "#version 120\n\nuniform vec2 location, rectSize;\n\n\n// rectangle\n\nvec4 rect(vec2 uv, vec2 location, vec2 rectSize) {\n    vec2 p = (uv - location) / rectSize;\n    return vec4(1.0, 1.0, 1.0, 1.0) * (step(0.0, p.x) * step(0.0, p.y) * step(p.x, 1.0) * step(p.y, 1.0));\n}\n\nvoid main() {\n    vec2 uv = gl_TexCoord[0].xy;\n    gl_FragColor = rect(uv, location, rectSize);\n}\n";
    }
}

