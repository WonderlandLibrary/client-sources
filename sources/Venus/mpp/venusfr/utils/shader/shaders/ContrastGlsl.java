/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.shader.shaders;

import mpp.venusfr.utils.shader.IShader;

public class ContrastGlsl
implements IShader {
    @Override
    public String glsl() {
        return "#version 120\n\nuniform sampler2D texture;\nuniform float contrast;\n\n\nvoid main()\n{\n    vec4 color = texture2D(texture, gl_TexCoord[0].st);\n    gl_FragColor = vec4(vec3(mix(0, color.r, contrast),mix(0, color.g, contrast),mix(0, color.b, contrast)), 1);\n}\n";
    }
}

