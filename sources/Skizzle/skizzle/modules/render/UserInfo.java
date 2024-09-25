/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.render;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.MathHelper;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventRenderGUI;
import skizzle.modules.Module;
import skizzle.newFont.MinecraftFontRenderer;
import skizzle.util.Timer;

public class UserInfo
extends Module {
    public Timer timer;
    public float subtract = Float.intBitsToFloat(2.13518822E9f ^ 0x7F446315);

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventRenderGUI) {
            UserInfo Nigga2;
            MinecraftFontRenderer Nigga3 = Client.fontNormal;
            ScaledResolution Nigga4 = new ScaledResolution(Nigga2.mc, Nigga2.mc.displayWidth, Nigga2.mc.displayHeight);
            GuiInventory.drawEntityOnScreen(36, Nigga4.getScaledHeight() / 2 + 55, 40, MathHelper.wrapAngleTo180_float(Nigga2.mc.thePlayer.rotationYawHead) / Float.intBitsToFloat(1.04155366E9f ^ 0x7E14D8E3), -Nigga2.mc.thePlayer.rotationPitch, Nigga2.mc.thePlayer);
            Gui.drawRect(5.0, Nigga4.getScaledHeight() / 2 - 35, 70.0, Nigga4.getScaledHeight() / 2 + 60, Integer.MIN_VALUE);
            Gui.drawRect(5.0, Nigga4.getScaledHeight() / 2 - 53, Nigga3.getStringWidth(Qprot0.0("\u8938\u71ca\ub267\ue268\ued06\u0cec") + Nigga2.mc.thePlayer.getName()) + 15, Nigga4.getScaledHeight() / 2 - 35, Integer.MIN_VALUE);
            Gui.drawRect(6.0, Nigga4.getScaledHeight() / 2 - 51, 8.0, Nigga4.getScaledHeight() / 2 - 26, Client.RGBColor);
            Nigga3.drawStringWithShadow(Qprot0.0("\u8938\u71ca\ub267\ue268\ued06\u0cec") + Nigga2.mc.thePlayer.getName(), 12.0, Nigga4.getScaledHeight() / 2 - 50, Client.RGBColor);
            Nigga3.drawStringWithShadow(Qprot0.0("\u893e\u71ce\ub26b\ue261\ued48\u0ca4\u8c75\ue513") + Nigga2.mc.thePlayer.getHealth(), 12.0, Nigga4.getScaledHeight() / 2 - 40, Client.RGBColor);
        }
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    public UserInfo() {
        super(Qprot0.0("\u8923\u71d8\ub26f\ua7f6\ua30c\u0ca2\u8c29\ue55c"), 0, Module.Category.RENDER);
        UserInfo Nigga;
        Nigga.timer = new Timer();
    }

    public static {
        throw throwable;
    }
}

