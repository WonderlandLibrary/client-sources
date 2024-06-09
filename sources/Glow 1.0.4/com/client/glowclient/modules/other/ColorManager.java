package com.client.glowclient.modules.other;

import com.client.glowclient.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.utils.*;

public class ColorManager extends ModuleContainer
{
    public static NumberValue saturation;
    public static nB rendering;
    public static NumberValue brightness;
    public static NumberValue rainbowSpeed;
    
    public ColorManager() {
        super(Category.OTHER, "ColorManager", false, -1, "Manage client colors");
    }
    
    @Override
    public boolean A() {
        return false;
    }
    
    static {
        ColorManager.saturation = ValueFactory.M("ColorManager", "Saturation", "Saturation of rainbow colors", 255.0, 1.0, 0.0, 255.0);
        ColorManager.brightness = ValueFactory.M("ColorManager", "Brightness", "Brightness of rainbow colors", 255.0, 1.0, 0.0, 255.0);
        ColorManager.rendering = ValueFactory.M("ColorManager", "Rendering", "Rendering rainbow up or down", "Down", "Down", "Up");
        final String s = "ColorManager";
        final String s2 = "RainbowSpeed";
        final String s3 = "Speed of rainbow's change";
        final double n = 1.0;
        final double n2 = 0.01;
        ColorManager.rainbowSpeed = ValueFactory.M(s, s2, s3, n, n2, n2, 1.0);
    }
    
    @Override
    public boolean k() {
        return true;
    }
}
