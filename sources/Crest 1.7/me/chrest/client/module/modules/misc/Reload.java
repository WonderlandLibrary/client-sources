// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.misc;

import me.chrest.client.gui.click.ClickGui;
import me.chrest.client.friend.FriendManager;
import me.chrest.client.option.OptionManager;
import me.chrest.client.module.ModuleManager;
import me.chrest.utils.ClientUtils;
import me.chrest.client.module.Module;

@Mod(displayName = "Load Config")
public class Reload extends Module
{
    @Override
    public void enable() {
        ClientUtils.mc().currentScreen = null;
        ModuleManager.load();
        OptionManager.load();
        FriendManager.load();
        ClickGui.getInstance().load();
    }
}
