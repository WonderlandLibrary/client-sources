package com.alan.clients.module.impl.combat;

import com.alan.clients.Client;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.module.impl.combat.antibot.*;
import com.alan.clients.value.impl.BooleanValue;

@ModuleInfo(aliases = {"module.combat.antibot.name"}, description = "module.combat.antibot.description", category = Category.COMBAT)
public final class AntiBot extends Module {

    private final BooleanValue funcraftAntiBot = new BooleanValue("Funcraft Check", this, false,
            new FuncraftAntiBot("", this));

    private final BooleanValue hypixelTestAntiBot = new BooleanValue("Hypixel Test Check", this, false,
            new HypixelTestCheck("", this));

    private final BooleanValue ncps = new BooleanValue("NPC Detection Check", this, false,
            new NPCAntiBot("", this));

    private final BooleanValue duplicate = new BooleanValue("Duplicate Name Check", this, false,
            new DuplicateNameCheck("", this));

    private final BooleanValue ping = new BooleanValue("No Ping Check", this, false,
            new PingCheck("", this));

    private final BooleanValue negativeIDCheck = new BooleanValue("Negative Unique ID Check", this, false,
            new NegativeIDCheck("", this));

    private final BooleanValue duplicateIDCheck = new BooleanValue("Duplicate Unique ID Check", this, false,
            new DuplicateIDCheck("", this));

    private final BooleanValue ticksVisible = new BooleanValue("Time Visible Check", this, false,
            new TicksVisibleCheck("", this));

    private final BooleanValue middleClick = new BooleanValue("Middle Click Bot", this, false,
            new MiddleClickBot("", this));

    @Override
    public void onDisable() {
        Client.INSTANCE.getBotManager().clear();
    }
}
