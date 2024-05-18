/*
 * Decompiled with CFR 0_122.
 */
package Monix.Mod.mods;

import Monix.Category.Category;
import Monix.Mod.Mod;

public class AntiScoreboard
extends Mod {
    public static boolean ASB = false;

    public AntiScoreboard() {
        super("AntiScoreboard", "AntiScoreboard", 0, Category.EXPLOITS);
    }

    @Override
    public void onEnable() {
        ASB = true;
    }

    @Override
    public void onDisable() {
        ASB = false;
    }
}

