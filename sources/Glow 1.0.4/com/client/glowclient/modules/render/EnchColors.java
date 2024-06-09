package com.client.glowclient.modules.render;

import com.client.glowclient.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.modules.*;

public class EnchColors extends ModuleContainer
{
    public static nB mode;
    public static NumberValue red;
    public static NumberValue green;
    public static NumberValue blue;
    
    static {
        EnchColors.red = ValueFactory.M("EnchColors", "Red", "Red Color", 100.0, 1.0, 0.0, 255.0);
        final String s = "EnchColors";
        final String s2 = "Green";
        final String s3 = "Green Color";
        final double n = 1.0;
        final double n2 = 0.0;
        EnchColors.green = ValueFactory.M(s, s2, s3, n2, n, n2, 255.0);
        EnchColors.blue = ValueFactory.M("EnchColors", "Blue", "Blue Color", 175.0, 1.0, 0.0, 255.0);
        EnchColors.mode = ValueFactory.M("EnchColors", "Mode", "Mode of EnchantColors", "RGB", "RGB", "Rainbow", "HUD");
    }
    
    public EnchColors() {
        super(Category.RENDER, "EnchColors", false, -1, "Change colors of enchanted items");
    }
    
    @Override
    public boolean A() {
        return false;
    }
}
