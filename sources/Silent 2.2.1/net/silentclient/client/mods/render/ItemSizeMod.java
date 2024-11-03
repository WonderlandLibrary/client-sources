package net.silentclient.client.mods.render;

import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class ItemSizeMod extends Mod {
    public ItemSizeMod() {
        super("Item Size", ModCategory.MODS, "silentclient/icons/mods/itemsize.png");
    }

    @Override
    public void setup() {
        this.addSliderSetting("X", this, 0.75, -1, 1, false);
        this.addSliderSetting("Y", this, -0.15, -1, 1, false);
        this.addSliderSetting("Z", this, -1, -1, 1, false);
    }
}
