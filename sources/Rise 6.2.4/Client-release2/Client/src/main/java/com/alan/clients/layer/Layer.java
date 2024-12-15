package com.alan.clients.layer;

import com.alan.clients.util.Accessor;
import com.alan.clients.util.shader.base.RiseShader;
import com.alan.clients.util.shader.base.ShaderRenderType;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Layer implements Accessor {
    private final ArrayList<Runnable> runnables = new ArrayList<>();
    RiseShader shader = null;

    public Layer(RiseShader shader) {
        this.shader = shader;
    }

    public Layer() {
    }

    public void run(ShaderRenderType type) {
        if (runnables.isEmpty()) {
            return;
        }

        if (shader == null) {
            mc.getFramebuffer().bindFramebuffer(false);
            runnables.forEach(Runnable::run);
        } else {
            shader.run(type, 0, runnables);
            if (type == ShaderRenderType.OVERLAY) shader.update();
        }
    }

    public void clear() {
        runnables.clear();
    }

    public void add(Runnable runnable) {
        this.runnables.add(runnable);
    }
}