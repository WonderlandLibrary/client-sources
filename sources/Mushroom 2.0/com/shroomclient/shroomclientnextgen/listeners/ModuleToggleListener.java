package com.shroomclient.shroomclientnextgen.listeners;

import com.shroomclient.shroomclientnextgen.annotations.RegisterListeners;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.ModuleStateChangeEvent;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.client.Notifications;
import com.shroomclient.shroomclientnextgen.util.ThemeUtil;

@RegisterListeners
public class ModuleToggleListener {

    @SubscribeEvent
    public void onModuleStateChange(ModuleStateChangeEvent e) {
        if (e.oldState != e.newState) {
            Notifications.notify(
                ModuleManager.getModuleWithInfo(
                    ModuleManager.getModule(e.module)
                )
                    .an()
                    .name() +
                " " +
                (e.newState ? "enabled" : "disabled"),
                e.newState
                    ? ThemeUtil.themeColors()[0]
                    : ThemeUtil.themeColors()[1],
                e.newState ? 1 : 2
            );
        }
    }
}
