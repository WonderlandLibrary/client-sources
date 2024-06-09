/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.other;

import me.thekirkayt.client.module.Module;

@Module.Mod(displayName="MoreInventory")
public class MoreInventory
extends Module {
    public static boolean cancelClose() {
        return new MoreInventory().getInstance().isEnabled();
    }
}

