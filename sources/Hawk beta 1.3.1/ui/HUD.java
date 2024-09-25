package eze.ui;

import eze.util.*;
import net.minecraft.client.*;
import eze.*;
import eze.modules.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.*;
import eze.modules.render.*;
import java.awt.*;
import eze.events.listeners.*;
import eze.events.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class HUD
{
    Timer timer;
    public Minecraft mc;
    
    public HUD() {
        this.timer = new Timer();
        this.mc = Minecraft.getMinecraft();
    }
    
    public void draw() {
        final ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        final FontRenderer fr = this.mc.fontRendererObj;
        final float huehud = System.currentTimeMillis() % 3500L / 3500.0f;
        int color = 0;
        Collections.sort(Client.modules, new ModuleComparator());
        GlStateManager.translate(4.0f, 4.0f, 0.0f);
        GlStateManager.scale(2.0f, 2.0f, 1.0f);
        GlStateManager.translate(-4.0f, -4.0f, 0.0f);
        fr.drawStringWithShadow("Hawk Client", 4.0, 4.0, -1);
        GlStateManager.translate(4.0f, 4.0f, 0.0f);
        GlStateManager.scale(0.5, 0.5, 1.0);
        GlStateManager.translate(-4.0f, -4.0f, 0.0f);
        int count = 0;
        fr.drawStringWithShadow("FPS : " + Minecraft.debugFPS, 6.0, 102.0, -1);
        GuiInventory.drawEntityOnScreen(30, 300, 50, 0.0f, 0.0f, this.mc.thePlayer);
        Gui.drawRect(5.0, 200.0, 55.0, 310.0, -1879048192);
        for (final Module m : Client.modules) {
            if (m.toggled) {
                if (m.name.equals("TabGUI")) {
                    continue;
                }
                final double offset = count * (fr.FONT_HEIGHT + 6);
                if (ModulesListOptions.ColorOptionInt == 0) {
                    color = Color.HSBtoRGB(huehud, 1.0f, 1.0f);
                }
                if (ModulesListOptions.ColorOptionInt == 1) {
                    color = -6746096;
                }
                if (ModulesListOptions.ColorOptionInt == 2) {
                    color = -16756481;
                }
                if (ModulesListOptions.ColorOptionInt == 3) {
                    color = -1350377;
                }
                if (ModulesListOptions.ColorOptionInt == 4) {
                    color = -13571305;
                }
                if (ModulesListOptions.ColorOptionInt == 5) {
                    color = -1;
                }
                Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.name) - 10, offset, sr.getScaledWidth() - fr.getStringWidth(m.name) - 8, 6 + fr.FONT_HEIGHT + offset, color);
                Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.name) - 8, offset, sr.getScaledWidth(), 6 + fr.FONT_HEIGHT + offset, -1879048192);
                fr.drawStringWithShadow(m.name, sr.getScaledWidth() - fr.getStringWidth(m.name) - 4, 4.0 + offset, -1);
                ++count;
            }
        }
        Client.onEvent(new EventRenderGUI());
    }
    
    public static class ModuleComparator implements Comparator<Module>
    {
        @Override
        public int compare(final Module arg0, final Module arg1) {
            if (Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg0.name) > Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg1.name)) {
                return -1;
            }
            if (Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg0.name) < Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg1.name)) {
                return 1;
            }
            return 0;
        }
    }
}
