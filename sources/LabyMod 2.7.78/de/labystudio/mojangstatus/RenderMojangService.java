package de.labystudio.mojangstatus;

import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Color;
import de.labystudio.utils.ServiceStatus;
import de.labystudio.utils.Utils;
import java.util.ArrayList;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class RenderMojangService
{
    public static void renderOnMultiplayerGui()
    {
        if (ConfigManager.settings.mojangStatus)
        {
            GlStateManager.pushMatrix();
            int i = 0;

            for (Object s0 : new ArrayList(LabyMod.getInstance().mojangStatus.keySet()))
            {
                String s = (String) s0;
                ServiceStatus servicestatus = (ServiceStatus)LabyMod.getInstance().mojangStatus.get(s);
                float f = (float)((double)((System.currentTimeMillis() + (long)(i * 4)) % 1000L) * 0.1D) / 10.0F;

                if (f > 5.0F)
                {
                    f = 10.0F - f;
                }

                f = 0.1F * f + 0.5F;
                long j = (System.currentTimeMillis() - servicestatus.getCreated()) / 1000L;
                String s1 = j + "s";

                if (j >= 60L)
                {
                    s1 = Utils.parseTimeNormal(j);
                }

                float f1 = 0.3F;

                if (servicestatus.getColor().equals("red"))
                {
                    f1 = f;
                }

                float f2 = 0.3F;

                if (servicestatus.getColor().equals("green"))
                {
                    f2 = f;
                    s1 = "Is back online!";

                    if (j > 10L)
                    {
                        LabyMod.getInstance().mojangStatus.remove(s);
                    }
                }

                float f3 = 0.3F;

                if (servicestatus.getColor().equals("yellow"))
                {
                    f1 = f;
                    f2 = f;
                }

                GlStateManager.color(f1, f2, f3);
                LabyMod.getInstance().mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/achievement/achievement_background.png"));
                LabyMod.getInstance().draw.drawTexturedModalRect(1, 35 + i, 257, 459, 23, 23);
                LabyMod.getInstance().draw.drawRect(25.0D, (double)(38 + i), (double)(LabyMod.getInstance().draw.getStringWidth(s) + 40) * 0.7D, (double)(45 + i), Color.toRGB(50, 50, 50, 170));
                LabyMod.getInstance().draw.drawRect(25.0D, (double)(48 + i), (double)(LabyMod.getInstance().draw.getStringWidth(s1) + 49) * 0.6D, (double)(54 + i), Color.toRGB(70, 70, 70, 170));
                LabyMod.getInstance().draw.drawString(Color.cl(servicestatus.getChatColor()) + s, 27.0D, (double)(39 + i), 0.7D);
                LabyMod.getInstance().draw.drawString(s1, 27.0D, (double)(49 + i), 0.6D);
                LabyMod.getInstance().draw.drawCenteredString(servicestatus.getStatus(), 13.0D, (double)(45 + i), 0.5D);
                i += 23;
            }

            GlStateManager.popMatrix();
        }
    }
}
