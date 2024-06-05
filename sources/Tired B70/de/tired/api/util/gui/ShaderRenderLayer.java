package de.tired.api.util.gui;

import de.tired.interfaces.IHook;

public abstract class ShaderRenderLayer implements IHook {

    public abstract void renderLayerWBlur();

    public abstract void renderNormalLayer();

}
