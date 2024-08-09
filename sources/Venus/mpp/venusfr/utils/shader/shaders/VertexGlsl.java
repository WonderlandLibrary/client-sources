/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.shader.shaders;

import mpp.venusfr.utils.shader.IShader;

public class VertexGlsl
implements IShader {
    @Override
    public String glsl() {
        return "#version 120\n void main() {\n     // \u0412\u044b\u0431\u043e\u0440\u043a\u0430 \u0434\u0430\u043d\u043d\u044b\u0445 \u0438\u0437 \u0442\u0435\u043a\u0441\u0442\u0443\u0440\u044b \u0432\u043e \u0444\u0440\u0430\u0433\u043c\u0435\u043d\u0442\u043d\u043e\u043c \u0448\u0435\u0439\u0434\u0435\u0440\u0435 (\u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u044b)\n     gl_TexCoord[0] = gl_MultiTexCoord0;\n     gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n }\n";
    }
}

