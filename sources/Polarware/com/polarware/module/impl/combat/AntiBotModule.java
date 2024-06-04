package com.polarware.module.impl.combat;

import com.polarware.Client;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.combat.antibot.*;
import com.polarware.value.impl.BooleanValue;

@ModuleInfo(name = "module.combat.antibot.name", description = "module.combat.antibot.description", category = Category.COMBAT)
public final class AntiBotModule extends Module {

    private final BooleanValue advancedAntiBot = new BooleanValue("Always Nearby Check", this, false,
            new AdvancedAntiBot("", this));

    private final BooleanValue watchdogAntiBot = new BooleanValue("Watchdog Check", this, false,
            new WatchdogAntiBot("", this));

    private final BooleanValue funcraftAntiBot = new BooleanValue("Funcraft Check", this, false,
            new FuncraftAntiBot("", this));

    private final BooleanValue ncps = new BooleanValue("NPC Detection Check", this, false,
            new NPCAntiBot("", this));

    private final BooleanValue middleClick = new BooleanValue("Middle Click Bot", this, false,
            new MiddleClickBot("", this));

    @Override
    protected void onDisable() {
        Client.INSTANCE.getBotComponent().clear();
    }
}
