package togglesneak;

import de.labystudio.modapi.EventHandler;
import de.labystudio.modapi.Listener;
import de.labystudio.modapi.events.RenderOverlayEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;

public class ToggleSneakModEvents implements Listener
{
    private static String hudText = "ToggleSneak";

    public static void SetHUDText(String text)
    {
        hudText = text;
    }

    @EventHandler
    public void onRender(RenderOverlayEvent e)
    {
        if (ToggleSneakMod.optionShowHUDText)
        {
            ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());

            switch (PositionMode.getByName(ToggleSneakMod.optionPositionMode))
            {
                case CUSTOM:
                    Minecraft.getMinecraft().fontRendererObj.drawString(hudText, ToggleSneakMod.optionHUDTextPosX, ToggleSneakMod.optionHUDTextPosY, 16777215);
                    break;

                case BOTTOMRIGHT:
                    Minecraft.getMinecraft().fontRendererObj.drawString(hudText, scaledresolution.getScaledWidth() - Minecraft.getMinecraft().fontRendererObj.getStringWidth(hudText) - 2, scaledresolution.getScaledHeight() - 10, 16777215);
                    break;

                case UNDERCHAT:
                    if (!(Minecraft.getMinecraft().currentScreen instanceof GuiChat))
                    {
                        Minecraft.getMinecraft().fontRendererObj.drawString(hudText, 2, scaledresolution.getScaledHeight() - 10, 16777215);
                    }

                    break;

                case TOPCENTER:
                    Minecraft.getMinecraft().fontRendererObj.drawString(hudText, scaledresolution.getScaledWidth() / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(hudText) / 2, 2, 16777215);
            }
        }
    }
}
