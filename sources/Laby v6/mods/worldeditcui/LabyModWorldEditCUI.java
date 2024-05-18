package mods.worldeditcui;

import de.labystudio.modapi.ModAPI;
import de.labystudio.modapi.Module;
import mods.worldeditcui.event.listeners.CUIListenerWorldRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderHelper;

public class LabyModWorldEditCUI extends Module
{
    private static final int DELAYED_HELO_TICKS = 10;
    private static final String CHANNEL_WECUI = "WECUI";
    public WorldEditCUI controller;
    private WorldClient lastWorld;
    private EntityPlayerSP lastPlayer;
    boolean visible = true;
    private boolean alwaysOnTop = false;
    private CUIListenerWorldRender worldRenderListener;
    private int delayedHelo = 0;

    public void onEnable()
    {
        (this.controller = new WorldEditCUI()).initialise();
        this.worldRenderListener = new CUIListenerWorldRender(this.controller, Minecraft.getMinecraft());
        ModAPI.addSettingsButton("WorldEditCUI", new LabyModWorldEditCUISettings(this));
        ModAPI.registerListener(new LabyModWorldEditCUIEvents(this));
    }

    public void onPostRenderEntities(double partialTicks)
    {
        if (this.visible && !this.alwaysOnTop)
        {
            RenderHelper.disableStandardItemLighting();
            this.worldRenderListener.onRender(partialTicks);
            RenderHelper.enableStandardItemLighting();
        }
    }

    public WorldEditCUI getController()
    {
        return this.controller;
    }
}
