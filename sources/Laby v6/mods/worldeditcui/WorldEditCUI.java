package mods.worldeditcui;

import mods.worldeditcui.config.CUIConfiguration;
import mods.worldeditcui.debug.CUIDebug;
import mods.worldeditcui.exceptions.InitialisationException;
import mods.worldeditcui.render.region.BaseRegion;
import mods.worldeditcui.render.region.CuboidRegion;

public class WorldEditCUI
{
    public static final int PROTOCOL_VERSION = 3;
    private BaseRegion selection;
    private CUIDebug debugger;
    private CUIConfiguration configuration;
    private static WorldEditCUI instance;

    public void initialise()
    {
        instance = this;
        this.selection = new CuboidRegion(this);
        this.configuration = CUIConfiguration.create();
        this.debugger = new CUIDebug(this);

        try
        {
            this.selection.initialise();
            this.configuration.initialise();
            this.debugger.initialise();
        }
        catch (InitialisationException initialisationexception)
        {
            initialisationexception.printStackTrace();
        }
    }

    public static WorldEditCUI getInstance()
    {
        return instance;
    }

    public CUIConfiguration getConfiguration()
    {
        return this.configuration;
    }

    public CUIDebug getDebugger()
    {
        return this.debugger;
    }

    public BaseRegion getSelection()
    {
        return this.selection;
    }

    public void setSelection(BaseRegion selection)
    {
        this.selection = selection;
    }
}
