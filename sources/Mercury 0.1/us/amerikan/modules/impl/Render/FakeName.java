/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Render;

import us.amerikan.modules.Category;
import us.amerikan.modules.Module;

public class FakeName
extends Module {
    public static boolean isEnabled = false;
    public static String fakename = "Mercury";

    public FakeName() {
        super("FakeName", "FakeName", 24, Category.PLAYER);
    }

    @Override
    public void onEnable() {
        isEnabled = true;
    }

    @Override
    public void onDisable() {
        isEnabled = false;
    }
}

