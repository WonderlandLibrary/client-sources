// 
// Decompiled by Procyon v0.6.0
// 

package net.augustus.utils;

import viamcp.gui.GuiProtocolSelector;

public class ViaMCP
{
    public static boolean isActive() {
        return viamcp.ViaMCP.getInstance().getVersion() > 47 && GuiProtocolSelector.active;
    }
}
