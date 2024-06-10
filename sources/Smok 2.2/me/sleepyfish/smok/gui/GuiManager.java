package me.sleepyfish.smok.gui;

import me.sleepyfish.smok.gui.guis.ClickGui;
import me.sleepyfish.smok.gui.guis.CosmeticsGui;

// Class from SMok Client by SleepyFish
public class GuiManager {

    private final ClickGui gui = new ClickGui();
    private final CosmeticsGui capeGui = new CosmeticsGui();

    public ClickGui getClickGui() {
        return this.gui;
    }

    public CosmeticsGui getCapeGui() {
        return capeGui;
    }

}