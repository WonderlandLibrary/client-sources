package me.vaziak.sensation.client.impl.visual;

import java.util.ArrayList;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import me.vaziak.sensation.client.api.property.impl.ItemsProperty;

/**
 * @author Speccy
 */
public class XRay extends Module {

    public static DoubleProperty trans = new DoubleProperty("Transparency", "The visibility of blocks not whitelisted... ei: stone will be (this) % less transparent than diamond", null, 100, 1, 255, 5);
    public static ItemsProperty blocks = new ItemsProperty("XRay Blocks", "Blocks that will stay fully visible", null, true, new ArrayList<>());
    public XRay() {
        super("XRay", Category.VISUAL);
        registerValue(trans, blocks);
    }
    
    public void onEnable() {
        mc.renderGlobal.loadRenderers();
    }
    
    public void onDisable() {
        mc.renderGlobal.loadRenderers();
    }

}
