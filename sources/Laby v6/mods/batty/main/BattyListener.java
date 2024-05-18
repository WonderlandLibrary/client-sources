package mods.batty.main;

import de.labystudio.modapi.EventHandler;
import de.labystudio.modapi.Listener;
import de.labystudio.modapi.events.GameTickEvent;
import de.labystudio.modapi.events.InitScreenEvent;
import de.labystudio.modapi.events.RenderOverlayEvent;
import net.minecraft.client.gui.GuiMainMenu;

public class BattyListener implements Listener
{
    @EventHandler
    public void onRenderOverlay(RenderOverlayEvent event)
    {
        BattyUtils.updateTimer(BattyMod.getInstance().getUpdateCounter());
        BattyMod.getInstance().getBatheartgui().renderPlayerInfo();
    }

    @EventHandler
    public void onInitScreen(InitScreenEvent event)
    {
        if (event.getScreen() instanceof GuiMainMenu)
        {
            BattyMod.getInstance().getBatheartgui().timerRunning = false;
            BattyConfig.storeRuntimeOptions();
        }
    }

    @EventHandler
    public void onGameTick(GameTickEvent event)
    {
        BattyMod.getInstance().upUpdateCounter();
        BattyUI battyui = BattyMod.getInstance().getBatheartgui();

        if (BattyUI.hideunhideCoordskey.isPressed())
        {
            battyui.hideUnhideCoords();
        }

        if (BattyUI.hideunhideTimerkey.isPressed())
        {
            battyui.hideUnhideStopWatch();
        }

        if (BattyUI.resetTimerkey.isPressed())
        {
            battyui.resetTimer = true;
        }

        if (BattyUI.startstopTimerkey.isPressed())
        {
            battyui.toggleTimer = true;
        }

        if (BattyUI.moveCoordScreenPos.isPressed())
        {
            battyui.rotateScreenCoords();
        }

        if (BattyUI.copyCoordsClipboard.isPressed())
        {
            battyui.copyScreenCoords();
        }

        if (BattyUI.moveTimerScreenPos.isPressed())
        {
            battyui.rotateScreenTimer();
        }

        if (BattyUI.hideunhideFPSkey.isPressed())
        {
            battyui.hideUnhideFPS();
        }

        if (BattyUI.moveFPSScreenPos.isPressed())
        {
            battyui.rotateScreenFPS();
        }
    }
}
