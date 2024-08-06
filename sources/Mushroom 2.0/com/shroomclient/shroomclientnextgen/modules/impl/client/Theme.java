package com.shroomclient.shroomclientnextgen.modules.impl.client;

import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;

@RegisterModule(
    name = "Theme",
    uniqueId = "theme",
    description = "Color Of The GUI",
    category = ModuleCategory.Client
)
public class Theme extends Module {

    @ConfigMode
    @ConfigOption(name = "Client Theme", description = "", order = 1)
    public ClientTheme clientTheme = ClientTheme.Mushroom;

    @Override
    protected void onEnable() {
        ModuleManager.setEnabled(Theme.class, false, true);
    }

    @Override
    protected void onDisable() {}

    public enum ClientTheme {
        Aquatic,
        Flame,
        Rose,
        Summer,
        Mystic,
        Green_Apple,
        Mushroom,
        Twilight,
        Sky,
        Hot_Pink,
        Sunrise,
        Lolipop,
        Hyper,
        Pastel,
        Femboy,
        Mint,
        Emo,
        Blossom,
        Peach,
        Pinky,
        Trans,
        Rainbow,
        Lesbian,
        Gay,
        Bisexual,
        Dirty,
        Bright,
        Cool_Blue,
        Blood,
        Pretty,
    }
}
