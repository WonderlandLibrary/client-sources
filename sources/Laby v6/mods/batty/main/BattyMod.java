package mods.batty.main;

import de.labystudio.modapi.ModAPI;
import de.labystudio.modapi.Module;
import net.minecraft.client.Minecraft;

public class BattyMod extends Module
{
    private static BattyMod instance;
    private BattyUI batheartgui = null;
    private BattyListener listener = new BattyListener();
    private int updateCounter;

    public void onEnable()
    {
        instance = this;
        ModAPI.registerListener(this.listener);
        this.batheartgui = new BattyUI(Minecraft.getMinecraft());
    }

    public static BattyMod getInstance()
    {
        return instance;
    }

    public BattyUI getBatheartgui()
    {
        return this.batheartgui;
    }

    public BattyListener getListener()
    {
        return this.listener;
    }

    public int getUpdateCounter()
    {
        return this.updateCounter;
    }

    public void upUpdateCounter()
    {
        ++this.updateCounter;
    }

    public void setBatheartgui(BattyUI batheartgui)
    {
        this.batheartgui = batheartgui;
    }
}
