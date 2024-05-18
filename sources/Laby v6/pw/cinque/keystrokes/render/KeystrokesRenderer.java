package pw.cinque.keystrokes.render;

import de.labystudio.modapi.EventHandler;
import de.labystudio.modapi.Listener;
import de.labystudio.modapi.events.RenderOverlayEvent;
import mods.togglesprint.me.jannik.Jannik;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import pw.cinque.keystrokes.KeystrokesMod;
import pw.cinque.keystrokes.settings.Location;

public class KeystrokesRenderer implements Listener
{
    private Minecraft mc = Minecraft.getMinecraft();

    @EventHandler
    public void onRender(RenderOverlayEvent event)
    {
        if (this.mc.currentScreen == null && !this.mc.gameSettings.showDebugInfo)
        {
            ScaledResolution scaledresolution = new ScaledResolution(this.mc);
            int i = 5;
            int j = 5;

            switch (Location.values()[KeystrokesMod.location])
            {
                case TOP_LEFT:
                    break;

                case TOP_RIGHT:
                    i += scaledresolution.getScaledWidth() - KeystrokesMod.width;
                    break;

                case BOTTOM_LEFT:
                    j += scaledresolution.getScaledHeight() - KeystrokesMod.height;
                    break;

                case BOTTOM_RIGHT:
                    i += scaledresolution.getScaledWidth() - KeystrokesMod.width;
                    j += scaledresolution.getScaledHeight() - KeystrokesMod.height;
                    break;

                default:
                    return;
            }

            for (Key key : KeystrokesMod.keys)
            {
            	if (key.getName().equals("LMB")) {
            		key.setPressed(true);
            	}
            	
            	if (key.getName().equals("RMB")) {
            		key.setPressed(true);
            	}
            	
                int k = this.mc.fontRendererObj.getStringWidth(key.getName());
                Gui.drawRect(i + key.getX(), j + key.getY(), i + key.getX() + key.getWidth(), j + key.getY() + key.getHeight(), key.isPressed() ? 1728053247 : 1711276032);
                this.mc.fontRendererObj.drawString(key.getName(), i + key.getX() + key.getWidth() / 2 - k / 2, j + key.getY() + key.getHeight() / 2 - 4, key.isPressed() ? -16777216 : -1);
            }
        }
    }
}
