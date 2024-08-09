/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.uniform;

import java.util.ArrayList;
import java.util.List;
import net.optifine.shaders.uniform.ShaderUniform1f;
import net.optifine.shaders.uniform.ShaderUniform1i;
import net.optifine.shaders.uniform.ShaderUniform2i;
import net.optifine.shaders.uniform.ShaderUniform3f;
import net.optifine.shaders.uniform.ShaderUniform4f;
import net.optifine.shaders.uniform.ShaderUniform4i;
import net.optifine.shaders.uniform.ShaderUniformBase;
import net.optifine.shaders.uniform.ShaderUniformM4;

public class ShaderUniforms {
    private final List<ShaderUniformBase> listUniforms = new ArrayList<ShaderUniformBase>();

    public void setProgram(int n) {
        for (int i = 0; i < this.listUniforms.size(); ++i) {
            ShaderUniformBase shaderUniformBase = this.listUniforms.get(i);
            shaderUniformBase.setProgram(n);
        }
    }

    public void reset() {
        for (int i = 0; i < this.listUniforms.size(); ++i) {
            ShaderUniformBase shaderUniformBase = this.listUniforms.get(i);
            shaderUniformBase.reset();
        }
    }

    public ShaderUniform1i make1i(String string) {
        ShaderUniform1i shaderUniform1i = new ShaderUniform1i(string);
        this.listUniforms.add(shaderUniform1i);
        return shaderUniform1i;
    }

    public ShaderUniform2i make2i(String string) {
        ShaderUniform2i shaderUniform2i = new ShaderUniform2i(string);
        this.listUniforms.add(shaderUniform2i);
        return shaderUniform2i;
    }

    public ShaderUniform4i make4i(String string) {
        ShaderUniform4i shaderUniform4i = new ShaderUniform4i(string);
        this.listUniforms.add(shaderUniform4i);
        return shaderUniform4i;
    }

    public ShaderUniform1f make1f(String string) {
        ShaderUniform1f shaderUniform1f = new ShaderUniform1f(string);
        this.listUniforms.add(shaderUniform1f);
        return shaderUniform1f;
    }

    public ShaderUniform3f make3f(String string) {
        ShaderUniform3f shaderUniform3f = new ShaderUniform3f(string);
        this.listUniforms.add(shaderUniform3f);
        return shaderUniform3f;
    }

    public ShaderUniform4f make4f(String string) {
        ShaderUniform4f shaderUniform4f = new ShaderUniform4f(string);
        this.listUniforms.add(shaderUniform4f);
        return shaderUniform4f;
    }

    public ShaderUniformM4 makeM4(String string) {
        ShaderUniformM4 shaderUniformM4 = new ShaderUniformM4(string);
        this.listUniforms.add(shaderUniformM4);
        return shaderUniformM4;
    }
}

