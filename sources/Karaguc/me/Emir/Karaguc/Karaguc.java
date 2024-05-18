package me.Emir.Karaguc;

import org.lwjgl.opengl.Display;

import de.Hero.clickgui.ClickGUI;
import de.Hero.settings.SettingsManager;
import me.Emir.Karaguc.event.EventManager;
import me.Emir.Karaguc.event.EventTarget;
import me.Emir.Karaguc.event.events.EventKey;
import me.Emir.Karaguc.friend.FriendManager;
import me.Emir.Karaguc.module.ModuleManager;
import me.Emir.Karaguc.ui.alt.AltManager;

public enum Karaguc {
	
	instance;
	
    public String name = "Karaguc", 
    		version = "0.10";

    public SettingsManager settingsManager;
    public EventManager eventManager;
    public ModuleManager moduleManager;
    public AltManager altManager;
    public FriendManager friendManager;
    public ClickGUI clickGUI;

    public void startClient() {
        settingsManager = new SettingsManager();
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
        altManager = new AltManager();
        friendManager = new FriendManager();
        clickGUI = new ClickGUI();

        moduleManager.getModuleByName("HUD").toggle();

        System.out.println("[" + name + "] Starting b" + version);
        Display.setTitle(name + " b" + version);

        eventManager.register(this);
    }

    public void stopClient() {
        eventManager.unregister(this);
    }

    @EventTarget
    public void onKey(EventKey event) {
        moduleManager.getModules().stream().filter(module -> module.getKey() == event.getKey()).forEach(module -> module.toggle());
    }
}