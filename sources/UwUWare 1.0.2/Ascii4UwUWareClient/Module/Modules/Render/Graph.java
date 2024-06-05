package Ascii4UwUWareClient.Module.Modules.Render;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.Render.EventRender2D;
import Ascii4UwUWareClient.API.Events.World.EventPacketSend;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Util.EventMotion;
import Ascii4UwUWareClient.Util.Render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.Packet;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Graph extends Module{
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final String type;

    public Graph(int x, int y, int width, int height, String type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
    }
    ArrayList<Double> speed = new ArrayList<Double>();

    public void render() {
        int color = new Color(255, 255, 255, 255).getRGB();
        Gui.drawRect(x + 0.5f, y, x + width, y + 0.5f, color);
        Gui.drawRect(x + 0.5f, y + height, x + width, y + height + 0.5f, color);
        Gui.drawRect(x + 0.5f, y + 0.5f, x + 1f, y + height, color);
        Gui.drawRect(x + width, y, x + 0.5f + width, y + height + 0.5f, color);
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        if (speed.size() > 0) {
            ArrayList<Double> reversed = new ArrayList<>(speed);

            Collections.reverse(reversed);
            double max = 0.1;
            double pies = 0;
            for (double k : reversed) {
                pies = pies + k;
                if (k > max) {
                    max = k;
                }
            }
            pies = pies / reversed.size();
            final int piesS = (int) ((pies / max) * (height - 8));
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5, 0.5, 0.5);
            fr.drawString((int) pies + " avg", (x + width + 2) * 2, ((y + height - piesS - 3) * 2), -1);
            int pps = 0;
            int i2 = 0;
            for (double i : reversed) {
                if (i2 < 20) {
                    pps = (int) (i + pps);
                }
                i2++;
            }
            fr.drawString(type + " Packets | " + pps + " p/s", (x + 1) * 2, (y - 6) * 2, -1);
            GlStateManager.popMatrix();
            RenderUtils.drawRect(x + 1, (y + height - piesS - 2f), x + width, (y + height - piesS - 2f), new Color(0, 200, 0, 200).getRGB());

            int i = 0;
            int prevY = 0;
            int prevX = 0;
            for (double k : reversed) {
                final int widthAdd = (int) ((k / max) * (height - 8));
                if (i != 0) {
                    RenderUtils.drawRect(prevX, (y + height - 2f) - prevY, x + width - i, (y + height - 2f) - widthAdd, new Color(255, 255, 255, 200).getRGB());
                }
                prevX = x + width - i;
                prevY = widthAdd;
                i++;
            }
        }
    }

    public void eventMotion(int packets) {
        speed.add((double) packets);
        if (speed.size() > width) {
            speed.remove(0);
        }
    }
}