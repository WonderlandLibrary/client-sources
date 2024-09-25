/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package skizzle.modules.render.PacketStats;

import java.util.ArrayList;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.lwjgl.opengl.GL11;
import skizzle.events.Event;
import skizzle.events.listeners.EventPacket;
import skizzle.events.listeners.EventRenderGUI;
import skizzle.modules.Module;
import skizzle.modules.render.PacketStats.Stat;
import skizzle.util.RenderUtil;
import skizzle.util.Timer;

public class NetStats
extends Module {
    public int serverTickPackets;
    public ArrayList<Stat> serverStats;
    public int tickPackets;
    public ArrayList<Stat> stats = new ArrayList();
    public Timer resetTimer;

    public NetStats() {
        super(Qprot0.0("\uef98\u71ce\ud4de\ua7d7\uc151\u6a0d\u8c3b\u83e0"), 0, Module.Category.RENDER);
        NetStats Nigga;
        Nigga.serverStats = new ArrayList();
        Nigga.resetTimer = new Timer();
    }

    @Override
    public void onEvent(Event Nigga) {
        NetStats Nigga2;
        if ((float)Nigga2.stats.size() > Float.intBitsToFloat(1.01696032E9f ^ 0x7F679534) * Nigga2.mc.timer.timerSpeed) {
            Nigga2.stats.remove(0);
        }
        if (Nigga2.serverStats.size() > 500) {
            Nigga2.serverStats.remove(0);
        }
        if (Nigga instanceof EventRenderGUI) {
            try {
                double Nigga3;
                Stat Nigga4;
                double Nigga5;
                double Nigga6 = Nigga2.mc.displayWidth / 4 - 96;
                double Nigga7 = Nigga2.mc.displayHeight / 2 - 126;
                GL11.glTranslated((double)Nigga6, (double)Nigga7, (double)0.0);
                GL11.glEnable((int)3089);
                RenderUtil.scissor(Nigga6 + 5.0, 0.0, 182.0, Nigga2.mc.displayHeight);
                GL11.glColor4f((float)Float.intBitsToFloat(1.08494541E9f ^ 0x7F2AF43F), (float)Float.intBitsToFloat(1.11103258E9f ^ 0x7DB90327), (float)Float.intBitsToFloat(1.08374541E9f ^ 0x7F18A443), (float)Float.intBitsToFloat(1.10927027E9f ^ 0x7D9E1F2F));
                GlStateManager.disableTexture2D();
                GL11.glLineWidth((float)Float.intBitsToFloat(1.05702688E9f ^ 0x7F00F35F));
                ArrayList Nigga8 = (ArrayList)Nigga2.serverStats.clone();
                Stat Nigga9 = new Stat(0);
                GL11.glBegin((int)3);
                GL11.glColor4d((double)0.0, (double)0.0, (double)1.0, (double)0.0);
                int Nigga10 = 0;
                for (Stat Nigga11 : Nigga8) {
                    try {
                        Nigga5 = (float)(System.currentTimeMillis() - Nigga11.time) / Float.intBitsToFloat(1.06089075E9f ^ 0x7E4BE867) - Float.intBitsToFloat(1.06711027E9f ^ 0x7F3ACF78);
                        Nigga4 = new Stat(Nigga11.packets);
                        if (Nigga8.size() > Nigga10) {
                            Nigga4 = (Stat)Nigga8.get(Nigga10 + 1);
                        }
                        Nigga3 = (45 - Nigga11.packets * 6 + (45 - Nigga9.packets * 6) + (45 - Nigga4.packets * 6)) / 3 + 8;
                        GL11.glVertex2d((double)Nigga5, (double)Nigga3);
                        Nigga9 = Nigga11;
                    }
                    catch (Exception exception) {}
                    ++Nigga10;
                }
                GL11.glEnd();
                GL11.glLineWidth((float)Float.intBitsToFloat(1.09041101E9f ^ 0x7F7E5A1B));
                Nigga8 = (ArrayList)Nigga2.stats.clone();
                RenderUtil.initMask();
                GL11.glEnable((int)2848);
                GL11.glBegin((int)3);
                Nigga9 = new Stat(0);
                Nigga10 = 0;
                for (Stat Nigga11 : Nigga8) {
                    try {
                        Nigga5 = (float)(System.currentTimeMillis() - Nigga11.time) / Float.intBitsToFloat(1.04269184E9f ^ 0x7F563716) - Float.intBitsToFloat(1.04665235E9f ^ 0x7EC2A5CB);
                        Nigga4 = new Stat(Nigga11.packets);
                        if (Nigga8.size() > Nigga10) {
                            Nigga4 = (Stat)Nigga8.get(Nigga10 + 1);
                        }
                        Nigga3 = (45 - Nigga11.packets * 6 + (45 - Nigga9.packets * 6) + (45 - Nigga4.packets * 6)) / 4 + 10;
                        GL11.glVertex2d((double)Nigga5, (double)Nigga3);
                        Nigga9 = Nigga11;
                    }
                    catch (Exception exception) {}
                    ++Nigga10;
                }
                GL11.glEnd();
                RenderUtil.useMask();
                Gui.drawRect(30.0, -600.0, 157.0, 0.0, -1426124784);
                Gui.drawStaticGradientRect(0, -600, 30, 0, -61424, 0xFF1010);
                Gui.drawStaticGradientRect(157, -600, 190, 0, 0xFF1010, -1426124784);
                Gui.drawStaticGradientRect(0, 0, 30, 20, 0xFF1010, 0xFFFF10, -1426124784, -1426063600);
                Gui.drawStaticGradientRectVert(30.0, 0.0, 157.0, 20.0, -1426124784, -1426063600);
                Gui.drawStaticGradientRect(157, 0, 190, 20, -1426124784, -1426063600, 0xFF1010, 0xFFFF10);
                GlStateManager.disableAlpha();
                Gui.drawStaticGradientRectVert(30.0, 20.0, 157.0, 40.0, -1426063600, -1441726704);
                Gui.drawStaticGradientRect(0, 20, 30, 40, 0xFFFF10, 0x10FF10, -1426063600, -1441726704);
                Gui.drawStaticGradientRect(157, 20, 190, 40, -1426063600, -1441726704, 0xFFFF10, 0x10FF10);
                GlStateManager.disableAlpha();
                RenderUtil.disableMask();
                GlStateManager.enableTexture2D();
                GL11.glDisable((int)3089);
                GL11.glTranslated((double)(-Nigga6), (double)(-Nigga7), (double)0.0);
                GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
            }
            catch (Exception exception) {}
        }
        if (Nigga instanceof EventPacket) {
            if (("" + ((EventPacket)Nigga).getPacket().getClass()).toLowerCase().contains(Qprot0.0("\uefa6\u71ca\ud4c9\ue266\u0bf9\u6a18\u8c3f\u83ff\u128a\ua0af\ue024\uaf1e")) && Nigga.isOutgoing() && !Nigga.isCancelled()) {
                ++Nigga2.tickPackets;
                Nigga2.stats.add(new Stat(Nigga2.tickPackets));
            }
            if (((EventPacket)Nigga).getPacket() instanceof S08PacketPlayerPosLook && Nigga.isIncoming() && !Nigga.isCancelled()) {
                ++Nigga2.serverTickPackets;
                Nigga2.serverStats.add(new Stat(Nigga2.serverTickPackets));
            }
        }
        try {
            if (Nigga2.resetTimer.hasTimeElapsed((long)-796264212 ^ 0xFFFFFFFFD089F8DEL, true) && Nigga2.mc.thePlayer != null) {
                ++Nigga2.serverTickPackets;
                Nigga2.serverStats.add(new Stat(0));
                Nigga2.tickPackets = 0;
                Nigga2.serverTickPackets = 0;
            }
        }
        catch (Exception exception) {}
    }

    public static {
        throw throwable;
    }
}

