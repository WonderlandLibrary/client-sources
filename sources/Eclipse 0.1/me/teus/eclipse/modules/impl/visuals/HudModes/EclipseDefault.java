package me.teus.eclipse.modules.impl.visuals.HudModes;

import me.teus.eclipse.Client;
import me.teus.eclipse.modules.Module;
import me.teus.eclipse.utils.RectUtils;
import me.teus.eclipse.utils.RenderUtils;
import me.teus.eclipse.utils.RoundedUtils;
import me.teus.eclipse.utils.font.FontLoaders;
import me.teus.eclipse.utils.interfaces.HUDStyle;
import me.teus.eclipse.utils.managers.ModuleManager;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Collections;
import java.util.Comparator;

public class EclipseDefault implements HUDStyle{

    public static void draw() {

        String displayName = Client.getInstance().CLIENT_NAME + "Sense Recode | User: Matto";

        RectUtils.rect(2.5, 2.5, FontLoaders.tenacity21.getStringWidth(displayName) + 3, fr.getHeight() + 2.5, new Color(0, 0, 0, 150).getRGB());

        GL11.glEnable(GL11.GL_BLEND);
        FontLoaders.tenacity21.drawString(displayName, 4, 4, -1);
        GL11.glDisable(GL11.GL_BLEND);

        //Collections.sort(ModuleManager.modules, new ModuleComparator());

        float index = 0;
        for (Module m : ModuleManager.modules) {
            if (!m.toggled) continue;
            int color = RenderUtils.fadeBetween(new Color(150, 0, 255).getRGB(), new Color(0, 0, 255).getRGB(), (float) ((System.currentTimeMillis() + (index * 100)) % 1000L) / 500.0f);

           // RectUtils.rect(sr.getScaledWidth() - FontLoaders.tenacityBold21.getStringWidth(m.getDisplayName()) - 4, (FontLoaders.tenacityBold21.getHeight() * index), 4 + FontLoaders.tenacityBold21.getStringWidth(m.getDisplayName()), FontLoaders.tenacityBold21.getHeight() + 3.5, new Color(0, 0, 0, 150).getRGB());
            GL11.glEnable(GL11.GL_BLEND);
            FontLoaders.tenacityBold21.drawString(m.getDisplayName(), sr.getScaledWidth() - FontLoaders.tenacityBold21.getStringWidth(m.getDisplayName()) - 2, 2 + (FontLoaders.tenacityBold21.getHeight() * index), color);
            GL11.glDisable(GL11.GL_BLEND);
            index += 1.4;
        }
    }

    public static class ModuleComparator implements Comparator<Module> {

        @Override
        public int compare(Module agr0, Module agr1) {
            if(fr.getStringWidth(agr0.getDisplayName()) > fr.getStringWidth(agr1.getDisplayName())){
                return -1;
            }
            if(fr.getStringWidth(agr0.getDisplayName()) < fr.getStringWidth(agr1.getDisplayName())){
                return 1;
            }
            return 0;
        }

    }
}
