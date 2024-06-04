package com.polarware.module.impl.player;

import com.polarware.Client;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.player.flagdetector.Flight;
import com.polarware.module.impl.player.flagdetector.Friction;
import com.polarware.module.impl.player.flagdetector.MathGround;
import com.polarware.module.impl.player.flagdetector.Strafe;
import com.polarware.util.chat.ChatUtil;
import com.polarware.value.impl.BooleanValue;

@ModuleInfo(name = "module.player.flagdetector.name", description = "module.player.flagdetector.description", category = Category.PLAYER)
public class FlagDetectorModule extends Module {
    public BooleanValue strafe = new BooleanValue("Strafe (Watchdog)", this, false, new Strafe("", this));
    public BooleanValue friction = new BooleanValue("Friction", this, false, new Friction("", this));
    public BooleanValue flight = new BooleanValue("Flight", this, false, new Flight("", this));
    public BooleanValue mathGround = new BooleanValue("Math Ground", this, false, new MathGround("", this));

    @Override
    protected void onEnable() {
        if (!Client.DEVELOPMENT_SWITCH) {
            ChatUtil.display("This module is only enabled for developers or config makersconfig");

            toggle();
        }
    }

    public void fail(String check) {
        ChatUtil.display("§7failed " + Client.INSTANCE.getThemeManager().getTheme().getChatAccentColor() + check);
    }
}
