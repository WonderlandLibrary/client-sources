package io.github.liticane.monoxide.util.render.shader.container;


import io.github.liticane.monoxide.util.interfaces.Methods;
import io.github.liticane.monoxide.util.render.shader.annotation.Info;
import io.github.liticane.monoxide.util.render.shader.data.ShaderRenderType;
import io.github.liticane.monoxide.util.render.shader.render.Type;

import java.util.List;

public abstract class ShaderContainer extends ShaderReload implements Methods {

    public String vert, frag;

    public ShaderContainer() {
        final Info info = getClass().getAnnotation(Info.class);
        vert = info.vert();
        frag = info.frag();
    }


    protected abstract void reload();

    public abstract void doRender(final ShaderRenderType type, Type renderType, List<Runnable> runnables);

}
