// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.misc;

import me.chrest.client.module.Module;

@Mod(displayName = "More Inventory")
public class MoreInventory extends Module
{
    public static boolean cancelClose() {
        return new MoreInventory().getInstance().isEnabled();
    }
}
