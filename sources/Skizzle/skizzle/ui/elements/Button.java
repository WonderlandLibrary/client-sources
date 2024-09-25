/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package skizzle.ui.elements;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.Session;
import org.lwjgl.opengl.GL11;
import skizzle.Client;
import skizzle.modules.ModuleManager;
import skizzle.newFont.FontUtil;
import skizzle.users.ServerManager;
import skizzle.util.AnimationHelper;
import skizzle.util.Colors;
import skizzle.util.RandomHelper;
import skizzle.util.RenderUtil;

public class Button
extends AnimationHelper {
    public String name;
    public int y;
    public int x;
    public int id;
    public int height;
    public int width;
    public int mid;
    public boolean enabled;
    public boolean hovering;

    public boolean isHovering(int Nigga, int Nigga2) {
        Button Nigga3;
        return Nigga > Nigga3.x && Nigga2 > Nigga3.y && Nigga < Nigga3.x + Nigga3.width && Nigga2 < Nigga3.y + Nigga3.height;
    }

    public void click() {
        Button Nigga;
        if (Nigga.id == 1001) {
            ModuleManager.killaura.toggle();
            Nigga.name = String.valueOf(ModuleManager.killaura.isEnabled() ? Qprot0.0("\u5d4f\u71c2\u660c\u601f\u3ba4\ud8dd\u8c2a") : Qprot0.0("\u5d4e\u71c5\u661e\u601c\u3baa\ud8d4")) + Qprot0.0("\u5d2b\u71e0\u6616\u6012\u3baa\ud8d0\u8c3a\u3134\u90f9");
        }
        if (Nigga.id == 1002) {
            ModuleManager.invManager.toggle();
            Nigga.name = String.valueOf(ModuleManager.invManager.isEnabled() ? Qprot0.0("\u5d4f\u71c2\u660c\u601f\u3ba4\ud8dd\u8c2a") : Qprot0.0("\u5d4e\u71c5\u661e\u601c\u3baa\ud8d4")) + Qprot0.0("\u5d2b\u71e6\u661e\u6010\u3ba7\ud8d6\u8c2a\u3134");
        }
        if (Nigga.id == 1003) {
            ModuleManager.chestStealer.toggle();
            Nigga.name = String.valueOf(ModuleManager.chestStealer.isEnabled() ? Qprot0.0("\u5d4f\u71c2\u660c\u601f\u3ba4\ud8dd\u8c2a") : Qprot0.0("\u5d4e\u71c5\u661e\u601c\u3baa\ud8d4")) + Qprot0.0("\u5d2b\u71e8\u6617\u601b\u3bb5\ud8c5\u8c1c\u3132\u90fd\u90ed\u52f0\uaf09\udfef");
        }
        if (Nigga.id == 1004) {
            String Nigga2 = RandomHelper.randomName();
            Minecraft.getMinecraft().session = new Session(Nigga2, "", "", Qprot0.0("\u5d66\u71c4\u6615\u601f\u3ba8\ud8d6"));
            new Thread(() -> Button.lambda$0(Nigga2)).start();
        }
    }

    public Button(String Nigga, int Nigga2, int Nigga3, int Nigga4, int Nigga5, int Nigga6) {
        Button Nigga7;
        Nigga7.name = Nigga;
        Nigga7.id = Nigga2;
        Nigga7.x = Nigga3;
        Nigga7.y = Nigga4;
        Nigga7.width = Nigga5;
        Nigga7.height = Nigga6;
        Nigga7.mid = Nigga3 + Nigga5 / 2;
        Nigga7.enabled = true;
    }

    public void hover() {
        Nigga.direction = AnimationHelper.Directions.IN;
    }

    public Button(int Nigga, int Nigga2, int Nigga3, int Nigga4, int Nigga5, String Nigga6) {
        Button Nigga7;
        Nigga7.name = Nigga6;
        Nigga7.id = Nigga;
        Nigga7.x = Nigga2;
        Nigga7.y = Nigga3;
        Nigga7.width = Nigga4;
        Nigga7.height = Nigga5;
        Nigga7.mid = Nigga2 + Nigga4 / 2;
        Nigga7.enabled = true;
    }

    public static {
        throw throwable;
    }

    public void animate() {
        Button Nigga;
        if (Nigga.hasTimeElapsed((long)89992159 ^ 0x55D2BDEL, true) && Nigga.enabled) {
            if (Nigga.hovering && Nigga.stage < (double)(Nigga.width - 10)) {
                Nigga.stage += Client.delay / (20.0 / (double)Nigga.width);
            }
            if (!Nigga.hovering && Nigga.stage > 0.0) {
                Nigga.stage -= Client.delay / (20.0 / (double)Nigga.width);
            }
            if (Nigga.stage > (double)Nigga.width) {
                Nigga.stage = Nigga.width;
            }
            if (Nigga.stage < 0.0) {
                Nigga.stage = 0.0;
            }
            return;
        }
        Nigga.stage = 0.0;
    }

    public void draw(int Nigga, int Nigga2) {
        Button Nigga3;
        Nigga3.hovering = Nigga3.isHovering(Nigga, Nigga2);
        Nigga3.animate();
        RenderUtil.initMask();
        RenderUtil.drawRoundedRect(Nigga3.x, Nigga3.y, Nigga3.x + Nigga3.width, Nigga3.y + Nigga3.height, 20.0, -1);
        RenderUtil.useMask();
        Gui.drawRect(Nigga3.x, Nigga3.y, Nigga3.x + Nigga3.width, Nigga3.y + Nigga3.height, Nigga3.getHoverColor());
        RenderUtil.disableMask();
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
        RenderUtil.initMask();
        RenderUtil.drawRoundedRect((double)Nigga3.mid - Nigga3.stage / 2.0, Nigga3.y + Nigga3.height - 2, (double)Nigga3.mid + Nigga3.stage / 2.0, Nigga3.y + Nigga3.height, 2.0, -1);
        RenderUtil.useMask();
        Gui.drawRect((double)Nigga3.mid - Nigga3.stage / 2.0, Nigga3.y + Nigga3.height - 2, (double)Nigga3.mid + Nigga3.stage / 2.0, Nigga3.y + Nigga3.height, Client.RGBColor);
        RenderUtil.disableMask();
        GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
        FontUtil.cleanmediumish.drawString(Nigga3.name, (float)Nigga3.mid - (float)Client.fontNormal.getStringWidth(Nigga3.name) / Float.intBitsToFloat(1.06242528E9f ^ 0x7F5352A4), Nigga3.y + Nigga3.height / 4, Nigga3.enabled ? -1 : 0x909090);
        GL11.glColor4f((float)Float.intBitsToFloat(1.09204595E9f ^ 0x7E974C81), (float)Float.intBitsToFloat(1.1169367E9f ^ 0x7D1319EF), (float)Float.intBitsToFloat(1.09439398E9f ^ 0x7EBB207D), (float)Float.intBitsToFloat(1.08309632E9f ^ 0x7F0EBD15));
    }

    public int getHoverColor() {
        Button Nigga;
        double Nigga2 = Nigga.stage / (double)Nigga.width;
        int Nigga3 = (int)(32.0 * Nigga2);
        new Color(0x70000000);
        return Nigga.enabled ? Colors.getColor(Nigga3, Nigga3, Nigga3, 130) : 0x70202020;
    }

    public static void lambda$0(String string) {
        ServerManager.sendUserData(string);
    }
}

