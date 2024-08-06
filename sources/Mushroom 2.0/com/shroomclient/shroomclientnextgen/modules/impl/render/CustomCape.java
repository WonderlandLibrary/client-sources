package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;

@RegisterModule(
    name = "Cape",
    uniqueId = "customcape",
    description = "Adds A Custom Cape To Players",
    category = ModuleCategory.Render,
    enabledByDefault = true
)
public class CustomCape extends Module {

    @ConfigMode
    @ConfigOption(name = "Mode", description = "", order = 1)
    public static Mode mode = Mode.Mushroom;

    @ConfigOption(name = "Give Everyone Capes", description = "", order = 2)
    public static Boolean everyoneCapes = false;

    //skin mixin
    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    public enum Mode {
        Girl,
        Kiss,
        Cave_Mushroom,
        Minecon2011,
        Minecon2012,
        Minecon2013,
        Minecon2015,
        Minecon2016,
        Mushroom_Anime,
        Mushroom,
        Traps,
        Astolfo,
        Grim,
        Moon,
        Raven_Anime,
        Raven,
        Gato,
        Rise_6,
        Rape,
    }
}
