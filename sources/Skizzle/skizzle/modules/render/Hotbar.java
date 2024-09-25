/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package skizzle.modules.render;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventRenderIGUI;
import skizzle.modules.Module;
import skizzle.newFont.FontUtil;
import skizzle.newFont.MinecraftFontRenderer;
import skizzle.util.AnimationHelper;
import skizzle.util.RenderUtil;

public class Hotbar
extends Module {
    public AnimationHelper chatMove;
    public AnimationHelper hotbarSlot = new AnimationHelper();

    @Override
    public void onDisable() {
    }

    public Hotbar() {
        super(Qprot0.0("\u36e3\u71c4\u0dab\ua7e6\u1819\ub363"), 0, Module.Category.RENDER);
        Hotbar Nigga;
        Nigga.chatMove = new AnimationHelper();
    }

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventRenderIGUI) {
            Hotbar Nigga2;
            int Nigga3 = Nigga2.mc.displayWidth / 2;
            int Nigga4 = Nigga2.mc.displayHeight / 2 + 1;
            int Nigga5 = Nigga3 / 2;
            MinecraftFontRenderer Nigga6 = FontUtil.cleanSmall;
            if (Nigga.isPre()) {
                if (Nigga2.hotbarSlot.hasNanoElapsedMili((long)505127810 ^ 0x1E1BA383L, true)) {
                    if (Nigga2.hotbarSlot.stage < (double)(Nigga2.mc.thePlayer.inventory.currentItem * 20)) {
                        Nigga2.hotbarSlot.stage += Client.delay / 15.0 + Math.pow(Math.abs((double)(Nigga2.mc.thePlayer.inventory.currentItem * 20) - Nigga2.hotbarSlot.stage), 1.2) / 40.0;
                    } else if (Nigga2.hotbarSlot.stage > (double)(Nigga2.mc.thePlayer.inventory.currentItem * 20)) {
                        Nigga2.hotbarSlot.stage -= Client.delay / 15.0 + Math.pow(Math.abs((double)(Nigga2.mc.thePlayer.inventory.currentItem * 20) - Nigga2.hotbarSlot.stage), 1.2) / 40.0;
                    }
                    if (Nigga2.hotbarSlot.stage > 180.0) {
                        Nigga2.hotbarSlot.stage = 180.0;
                    }
                    if (Nigga2.hotbarSlot.stage < 0.0) {
                        Nigga2.hotbarSlot.stage = 0.0;
                    }
                    if (Nigga2.mc.currentScreen instanceof GuiChat) {
                        if (Nigga2.chatMove.stage < 13.0) {
                            Nigga2.chatMove.stage += Client.delay;
                        }
                    } else if (Nigga2.chatMove.stage > 0.0) {
                        Nigga2.chatMove.stage -= Client.delay;
                    }
                }
                Gui.drawRect(0.0, Nigga4 - 25, Nigga3, Nigga4, -1877995504);
                Gui.drawRect((double)(Nigga5 - 92) + Nigga2.hotbarSlot.stage, Nigga4 - 22, (double)(Nigga5 - 91) + Nigga2.hotbarSlot.stage + 22.0, Nigga4, -1426063361);
                GlStateManager.enableBlend();
            } else {
                GL11.glEnable((int)3089);
                RenderUtil.scissor(0.0, Nigga4 - 23, Nigga3, 23.0 - Nigga2.chatMove.stage);
                int Nigga7 = 0;
                Nigga6.drawString(Qprot0.0("\u36f3\u7191\u0dff\ue2aa\ua295") + (double)Math.round(Nigga2.mc.thePlayer.posX * 10.0) / 10.0, 2.0, Nigga4 - 24, Client.RGBColor);
                Nigga6.drawString(Qprot0.0("\u36f2\u7191\u0dff\ue2aa\ua295") + (double)Math.round(Nigga2.mc.thePlayer.posY * 10.0) / 10.0, 6 + (Nigga7 += Nigga6.getStringWidth(Qprot0.0("\u36f3\u7191\u0dff\ue2aa\ua295") + (double)Math.round(Nigga2.mc.thePlayer.posX * 10.0) / 10.0)), Nigga4 - 24, Client.RGBColor);
                Nigga6.drawString(Qprot0.0("\u36f1\u7191\u0dff\ue2aa\ua295") + (double)Math.round(Nigga2.mc.thePlayer.posZ * 10.0) / 10.0, 10 + (Nigga7 += Nigga6.getStringWidth(Qprot0.0("\u36f2\u7191\u0dff\ue2aa\ua295") + (double)Math.round(Nigga2.mc.thePlayer.posY * 10.0) / 10.0)), Nigga4 - 24, Client.RGBColor);
                Nigga6.drawString(Qprot0.0("\u36fb\u71c2\u0db1\ue26a\ua2d3\ub3b6\u8c29") + Client.getPlayerPing(), 2.0, Nigga4 - 12, Client.RGBColor);
                Nigga7 = Nigga6.getStringWidth(Qprot0.0("\u36fb\u71c2\u0db1\ue26a\ua2d3\ub3b6\u8c29") + Client.getPlayerPing());
                Nigga6.drawString(Qprot0.0("\u36e2\u71fb\u0de5\ue22d\ua254\ub377") + Client.currentIP, 6 + Nigga7, Nigga4 - 12, Client.RGBColor);
                double Nigga8 = Math.sqrt(Math.abs((Nigga2.mc.thePlayer.posX - Nigga2.mc.thePlayer.lastTickPosX) * (Nigga2.mc.thePlayer.posX - Nigga2.mc.thePlayer.lastTickPosX)) + Math.abs((Nigga2.mc.thePlayer.posZ - Nigga2.mc.thePlayer.lastTickPosZ) * (Nigga2.mc.thePlayer.posZ - Nigga2.mc.thePlayer.lastTickPosZ)));
                Nigga8 *= 10.0;
                double Nigga9 = Math.round(Nigga8 *= (double)(Float.intBitsToFloat(1.03540173E9f ^ 0x7F7EFA02) * Nigga2.mc.timer.timerSpeed));
                Nigga8 = Nigga9 / 100.0;
                Nigga6.drawString(Qprot0.0("\u36e9\u71fb\u0d8c\ue22d\ua254\ub377") + Nigga8, 10 + (Nigga7 += Nigga6.getStringWidth(Qprot0.0("\u36e2\u71fb\u0de5\ue22d\ua254\ub377") + Client.currentIP)), Nigga4 - 12, Client.RGBColor);
                GL11.glDisable((int)3089);
            }
        }
    }

    public static {
        throw throwable;
    }

    @Override
    public void onEnable() {
    }
}

