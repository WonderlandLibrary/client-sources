/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package skizzle.ui;

import java.awt.Color;
import java.util.Calendar;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;
import skizzle.Client;
import skizzle.events.listeners.EventRenderGUI;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;
import skizzle.modules.render.HUDModule;
import skizzle.newFont.FontUtil;
import skizzle.newFont.MinecraftFontRenderer;
import skizzle.util.Colors;
import skizzle.util.ColourUtil;
import skizzle.util.RenderUtil;
import skizzle.util.Timer;

public class HUD {
    public HUDModule hudModule;
    public boolean ghostIsEnabled = false;
    public CopyOnWriteArrayList<Module> displayModules;
    public float moduleHeight;
    public Timer fpsTimer;
    public long lastFPSMS;
    public Minecraft mc = Minecraft.getMinecraft();
    public float fps = Float.intBitsToFloat(2.12961024E9f ^ 0x7EEF4639);

    public HUD() {
        HUD Nigga;
        Nigga.fpsTimer = new Timer();
        Nigga.lastFPSMS = System.currentTimeMillis();
        Nigga.hudModule = ModuleManager.hudModule;
        Nigga.displayModules = new CopyOnWriteArrayList();
    }

    /*
     * WARNING - void declaration
     */
    public void draw() {
        if (!Client.ghostMode) {
            String Nigga;
            HUD Nigga2;
            Nigga2.hudModule = ModuleManager.hudModule;
            ScaledResolution Nigga3 = new ScaledResolution(Nigga2.mc, Nigga2.mc.displayWidth, Nigga2.mc.displayHeight);
            MinecraftFontRenderer Nigga4 = Client.fontNormal;
            int Nigga5 = 1000 * Client.rgbSpeed;
            if (!ModuleManager.hudModule.color.getMode().equals(Qprot0.0("\u010b\u71ca\u3a43\u88d6\u1e37\u848e"))) {
                Client.hue = (float)(System.currentTimeMillis() % (long)Nigga5) / (float)Nigga5;
                Client.RGBColor = Color.HSBtoRGB(Client.hue, Float.intBitsToFloat(1.07677581E9f ^ 0x7F628789), Float.intBitsToFloat(1.09801318E9f ^ 0x7E143FBF));
                Client.RGBColor2 = Color.HSBtoRGB(Client.hue, Float.intBitsToFloat(1.07726362E9f ^ 0x7F068FF3), Float.intBitsToFloat(1.07788557E9f ^ 0x7F26A3CA));
            } else {
                Client.RGBColor = Colors.getColor((int)ModuleManager.hudModule.colorRed.getValue(), (int)ModuleManager.hudModule.colorGreen.getValue(), (int)ModuleManager.hudModule.colorBlue.getValue());
                Client.RGBColor2 = Colors.getColor((int)(ModuleManager.hudModule.colorRed.getValue() / 0.0), (int)(ModuleManager.hudModule.colorGreen.getValue() / 0.0), (int)(ModuleManager.hudModule.colorBlue.getValue() / 0.0), 0);
            }
            Nigga2.displayModules = new CopyOnWriteArrayList();
            for (Module module : Client.modules) {
                Nigga2.displayModules.add(module);
            }
            Client.modules.sort(Comparator.comparingDouble(arg_0 -> HUD.lambda$0(Nigga4, arg_0)).reversed());
            Nigga2.displayModules.sort(Comparator.comparingDouble(arg_0 -> HUD.lambda$1(Nigga4, arg_0)).reversed());
            if (!Nigga2.mc.gameSettings.showDebugInfo) {
                void var4_11;
                String string = Client.name;
                if (Client.date.getTime().getDate() == 1 && Client.date.getTime().getMonth() == 3) {
                    String string2 = Qprot0.0("\u010a\u71c0\u3a44\u88c6\u1e2c\u848f\u8c2a");
                }
                FontUtil.cleanlarge.drawStringWithShadow((String)var4_11, 5.0, !ModuleManager.hudModule.fontSetting.getMode().equals(Qprot0.0("\u0117\u71c4\u3a59\u88da\u1e31\u848d\u8c28")) ? 0 : 2, Client.RGBColor);
                Nigga = Qprot0.0("\u01fe\u71cd") + Client.version;
                Nigga4.drawString(Nigga, FontUtil.cleanlarge.getStringWidth((String)var4_11) + 7, 2.0, -1);
            }
            if (Client.showHud) {
                String Nigga6;
                void var4_14;
                boolean bl = false;
                while (++var4_14 < 4) {
                    if (Nigga2.mc.thePlayer.inventory.armorItemInSlot((int)var4_14) == null) continue;
                    Nigga = Nigga2.mc.thePlayer.inventory.armorItemInSlot((int)var4_14).getDisplayName();
                    if (Nigga.contains(Qprot0.0("\u011e\u71c4\u3a41\u88d6\u1e3d\u848d"))) {
                        String Nigga7;
                        Nigga = Nigga7 = Nigga.replace(Qprot0.0("\u011e\u71c4\u3a41\u88d6\u1e3d\u848d"), Qprot0.0("\u013e\u71c4\u3a41\u88d6"));
                    }
                    if (Nigga.contains(Qprot0.0("\u0115\u71ce\u3a4c\u88c6\u1e30\u8486\u8c3d"))) {
                        Nigga6 = Nigga.replace(Qprot0.0("\u011a\u71ca\u3a5d"), Qprot0.0("\u0131\u71ce\u3a41\u88df\u1e3d\u8497"));
                        Nigga6 = Nigga.replace(Qprot0.0("\u010d\u71de\u3a43\u88db\u1e3b"), Qprot0.0("\u013a\u71c3\u3a48\u88c1\u1e2c\u8493\u8c23\u6d75\u7820\ub577"));
                        Nigga = Nigga6 = Nigga.replace(Qprot0.0("\u0109\u71ca\u3a43\u88c6\u1e2b"), Qprot0.0("\u0135\u71ce\u3a4a\u88d5\u1e31\u848d\u8c28\u6d67"));
                    }
                    Nigga6 = Nigga.toLowerCase();
                    Nigga6.replace(' ', '_');
                }
                if (!Nigga2.mc.gameSettings.showDebugInfo) {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(Float.intBitsToFloat(1.06728019E9f ^ 0x7F1D6724), Float.intBitsToFloat(1.06785658E9f ^ 0x7F2632CC), Float.intBitsToFloat(2.09474509E9f ^ 0x7CDB461F));
                    GlStateManager.scale(0.0, 0.0, 1.0);
                    GlStateManager.translate(Float.intBitsToFloat(-1.07831296E9f ^ 0x7F1A3FCA), Float.intBitsToFloat(-1.10039795E9f ^ 0x7F6942A1), Float.intBitsToFloat(2.12232358E9f ^ 0x7E80169D));
                    int n = 0;
                    if (Nigga2.hudModule.coords.isEnabled()) {
                        FontUtil.cleankindalarge.drawSmoothString(Qprot0.0("\u011a\u71c4\u3a42\u88c0\u1e3c\u8490\u8c6f") + (Object)((Object)EnumChatFormatting.WHITE) + (float)Math.round(Nigga2.mc.thePlayer.posX * 10.0) / Float.intBitsToFloat(1.04723206E9f ^ 0x7F4B7E23) + Qprot0.0("\u0175\u718b") + (float)Math.round(Nigga2.mc.thePlayer.posY * 10.0) / Float.intBitsToFloat(1.0433623E9f ^ 0x7F1071E2) + Qprot0.0("\u0175\u718b") + (float)Math.round(Nigga2.mc.thePlayer.posZ * 10.0) / Float.intBitsToFloat(1.05851264E9f ^ 0x7E379F1B), 15.0, 34 + n, Client.RGBColor);
                        var4_16 += 15;
                    }
                    Client.date = Calendar.getInstance();
                    if (Nigga2.hudModule.clock.isEnabled()) {
                        void var4_16;
                        Nigga = "" + Client.date.getTime().getSeconds();
                        if (Client.date.getTime().getSeconds() < 10) {
                            Nigga = "0" + Client.date.getTime().getSeconds();
                        }
                        Nigga6 = "" + Client.date.getTime().getMinutes();
                        if (Client.date.getTime().getMinutes() < 10) {
                            Nigga6 = "0" + Client.date.getTime().getMinutes();
                        }
                        FontUtil.cleankindalarge.drawSmoothString(Qprot0.0("\u010d\u71c2\u3a40\u88d7\u1e78") + (Object)((Object)EnumChatFormatting.WHITE) + Client.date.getTime().getHours() + ":" + Nigga6 + ":" + Nigga, 15.0, 34 + var4_16, Client.RGBColor);
                        var4_17 += 15;
                    }
                    if (ModuleManager.hudModule.speed.isEnabled()) {
                        void var4_17;
                        double Nigga9 = Math.sqrt(Math.abs((Nigga2.mc.thePlayer.posX - Nigga2.mc.thePlayer.lastTickPosX) * (Nigga2.mc.thePlayer.posX - Nigga2.mc.thePlayer.lastTickPosX)) + Math.abs((Nigga2.mc.thePlayer.posZ - Nigga2.mc.thePlayer.lastTickPosZ) * (Nigga2.mc.thePlayer.posZ - Nigga2.mc.thePlayer.lastTickPosZ)));
                        Nigga9 *= 10.0;
                        double Nigga10 = Math.round(Nigga9 *= (double)(Float.intBitsToFloat(1.00743635E9f ^ 0x7EC44237) * Nigga2.mc.timer.timerSpeed));
                        Nigga9 = Nigga10 / 100.0;
                        FontUtil.cleankindalarge.drawSmoothString(Qprot0.0("\u010a\u71db\u3a48\u88d7\u1e3c\u84c3") + (Object)((Object)EnumChatFormatting.WHITE) + Nigga9, 15.0, 34 + var4_17, Client.RGBColor);
                        var4_18 += 15;
                    }
                    if (Nigga2.hudModule.fps.isEnabled()) {
                        void var4_18;
                        float Nigga11 = Math.abs((float)Minecraft.getFPS() - Nigga2.fps) / Float.intBitsToFloat(1.01623814E9f ^ 0x7EDA904D);
                        if (Nigga11 == Float.intBitsToFloat(2.11917504E9f ^ 0x7E500B43)) {
                            Nigga11 = Float.intBitsToFloat(1.12636173E9f ^ 0x7EEE268B);
                        }
                        if (Nigga2.fpsTimer.hasTimeElapsed((long)((Float.intBitsToFloat(1.03797056E9f ^ 0x7F162C75) - Math.abs((float)Minecraft.getFPS() - Nigga2.fps)) / Float.intBitsToFloat(1.06913011E9f ^ 0x7E99A187)), true)) {
                            int Nigga12 = (int)((System.currentTimeMillis() - Nigga2.lastFPSMS) / ((long)733115980 ^ 0x2BB27646L));
                            if (Nigga12 == 0) {
                                Nigga12 = 1;
                            }
                            Nigga2.lastFPSMS = System.currentTimeMillis();
                            if ((float)Minecraft.getFPS() > Nigga2.fps) {
                                Nigga2.fps += Nigga11;
                                Nigga2.fpsTimer.reset();
                            }
                            if ((float)Minecraft.getFPS() < Nigga2.fps) {
                                Nigga2.fps -= Nigga11;
                                Nigga2.fpsTimer.reset();
                            }
                        }
                        FontUtil.cleankindalarge.drawSmoothString(Qprot0.0("\u011f\u71fb\u3a7e\u8892") + (Object)((Object)EnumChatFormatting.WHITE) + (Nigga2.hudModule.niceFPS.isEnabled() ? Math.round(Nigga2.fps) : Minecraft.getFPS()), 15.0, 34 + var4_18, Client.RGBColor);
                    }
                    GlStateManager.popMatrix();
                }
                boolean bl2 = false;
                int Nigga13 = 0;
                double Nigga14 = 0.0;
                for (Module Nigga15 : Nigga2.displayModules) {
                    if (!Nigga15.toggled || Nigga15.name.equals(Qprot0.0("\u010d\u71ca\u3a4f\u8892\u1e1f\u84b6\u8c06")) || Nigga15.name.equals(Qprot0.0("\u0111\u71fe\u3a69")) || Nigga15.name.equals(Qprot0.0("\u011d\u71c2\u3a5e\u88d1\u1e37\u8491\u8c2b\u6d46\u7804\ub551"))) continue;
                    ++Nigga13;
                }
                for (Module Nigga7 : Nigga2.displayModules) {
                    if (Nigga2.mc.gameSettings.showDebugInfo || Nigga2.ghostIsEnabled || !Nigga7.displayHud || Nigga7.name.equals(Qprot0.0("\u010d\u71ca\u3a4f\u8892\u1e1f\u84b6\u8c06")) || Nigga7.name.equals(Qprot0.0("\u0111\u71fe\u3a69")) || Nigga7.name.equals(Qprot0.0("\u011d\u71c2\u3a5e\u88d1\u1e37\u8491\u8c2b\u6d46\u7804\ub551"))) continue;
                    GlStateManager.pushMatrix();
                    int Nigga16 = Client.RGBColor;
                    if (ModuleManager.hudModule.color.getMode().equals(Qprot0.0("\u010b\u71ca\u3a44\u88dc\u1e3a\u848c\u8c38")) || ModuleManager.hudModule.color.getMode().equals(Qprot0.0("\u010b\u71ce\u3a5b\u88d7\u1e2a\u8490\u8c2a\u6d70\u7806\ub573\u0ea7\uaf02\u83ad\u5d74\u6a16")) || ModuleManager.hudModule.color.getMode().equals(Qprot0.0("\u011b\u718d\u3a7a\u8892\u1e0a\u8482\u8c26\u6d7a\u7836\ub57d\u0eb9"))) {
                        Nigga16 = ColourUtil.getRainbow((float)ModuleManager.hudModule.rgbSpeed.getValue(), (float)Nigga2.hudModule.saturation.getValue() / Float.intBitsToFloat(1.02929491E9f ^ 0x7E26CB3B), Float.intBitsToFloat(1.08557069E9f ^ 0x7F347E4F), (long)(Nigga14 * 30.0 * Nigga2.hudModule.rainbowOffset.getValue()));
                    }
                    if (Nigga2.hudModule.color.getMode().equals(Qprot0.0("\u010b\u71ca\u3a43\u88d6\u1e37\u848e"))) {
                        Nigga16 = Nigga7.rndColor;
                    }
                    float Nigga17 = Float.intBitsToFloat(-1.06094323E9f ^ 0x7F034A92);
                    if (!ModuleManager.hudModule.fontSetting.getMode().equals(Qprot0.0("\u0117\u71c4\u3a59\u88da\u1e31\u848d\u8c28"))) {
                        Nigga17 = Float.intBitsToFloat(1.10241843E9f ^ 0x7E759213);
                    }
                    Nigga2.moduleHeight = (float)(6 + Nigga4.FONT_HEIGHT) + (float)Nigga14 * (float)(Nigga4.FONT_HEIGHT + 6);
                    if (Nigga2.hudModule.displayMode.getMode().equals(Qprot0.0("\u011b\u71ca\u3a4e\u88d9\u1e3f\u8491\u8c20\u6d61\u783a\ub576")) || Nigga2.hudModule.displayMode.getMode().equals(Qprot0.0("\u0117\u71c4\u3a0d\u88f0\u1e39\u8480\u8c24\u6d73\u7826\ub57d\u0ebb\uaf02\u83ab"))) {
                        GlStateManager.translate(Float.intBitsToFloat(2.12366221E9f ^ 0x7E9483A3), Float.intBitsToFloat(-1.09944896E9f ^ 0x7EF7BD7D), Float.intBitsToFloat(2.13357466E9f ^ 0x7F2BC402));
                        if (Nigga2.hudModule.displayMode.getMode().equals(Qprot0.0("\u011b\u71ca\u3a4e\u88d9\u1e3f\u8491\u8c20\u6d61\u783a\ub576"))) {
                            Gui.drawRect((double)(Nigga3.getScaledWidth() - Nigga4.getStringWidth(Nigga7.displayName) - 12) + Nigga7.animStage, (float)(5.2 + Nigga14 * ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()) - (double)Nigga17), (double)Nigga3.getScaledWidth() + Nigga7.animStage, (double)((float)(5.2 + Nigga14 * ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()) - (double)Nigga17)) + ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()), 0x70000000);
                            if (ModuleManager.hudModule.sides.getMode().equals(Qprot0.0("\u0115\u71c2\u3a43\u88d7\u1e2b"))) {
                                int n;
                                if (Nigga14 != 0.0 && (double)n <= (double)(Nigga3.getScaledWidth() - Nigga4.getStringWidth(Nigga7.displayName) - 13) + Nigga7.animStage) {
                                    Gui.drawRect(n, (float)(5.2 + Nigga14 * ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()) - (double)Nigga17), (double)(Nigga3.getScaledWidth() - Nigga4.getStringWidth(Nigga7.displayName) - 12) + Nigga7.animStage, (float)(5.2 + Nigga14 * ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()) - (double)Nigga17) + Float.intBitsToFloat(1.09525504E9f ^ 0x7EC84433), Nigga16);
                                }
                                if (Nigga14 == (double)(Nigga13 - 1) && (double)n <= (double)(Nigga3.getScaledWidth() - Nigga4.getStringWidth(Nigga7.displayName) - 13) + Nigga7.animStage) {
                                    Gui.drawRect((double)(Nigga3.getScaledWidth() - Nigga4.getStringWidth(Nigga7.displayName) - 13) + Nigga7.animStage, (double)((float)(5.2 + Nigga14 * ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()) - (double)Nigga17)) + ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()), (double)Nigga3.getScaledWidth() + Nigga7.animStage, (double)((float)(5.2 + Nigga14 * ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()) - (double)Nigga17)) + ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()) + 1.0, ColourUtil.getRainbow((float)ModuleManager.hudModule.rgbSpeed.getValue(), (float)Nigga2.hudModule.saturation.getValue() / Float.intBitsToFloat(1.03644774E9f ^ 0x7EB9EFF9), Float.intBitsToFloat(1.09026112E9f ^ 0x7F7C108A), (long)((Nigga14 + 0.0) * 30.0 * Nigga2.hudModule.rainbowOffset.getValue())));
                                }
                                n = Nigga3.getScaledWidth() - Nigga4.getStringWidth(Nigga7.displayName) - 13 + (int)Nigga7.animStage;
                                RenderUtil.initMask();
                                Gui.drawRect((double)(Nigga3.getScaledWidth() - Nigga4.getStringWidth(Nigga7.displayName) - 13) + Nigga7.animStage, (float)(5.2 + Nigga14 * ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()) - (double)Nigga17), (double)(Nigga3.getScaledWidth() - Nigga4.getStringWidth(Nigga7.displayName) - 12) + Nigga7.animStage, (double)((float)(5.2 + Nigga14 * ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()) - (double)Nigga17)) + ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()), -1);
                                RenderUtil.useMask();
                                Gui.drawStaticGradientRectVert((double)(Nigga3.getScaledWidth() - Nigga4.getStringWidth(Nigga7.displayName) - 13) + Nigga7.animStage, (float)(5.2 + Nigga14 * ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()) - (double)Nigga17), (double)(Nigga3.getScaledWidth() - Nigga4.getStringWidth(Nigga7.displayName) - 12) + Nigga7.animStage, (double)((float)(5.2 + Nigga14 * ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()) - (double)Nigga17)) + ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()), Nigga16, ColourUtil.getRainbow((float)ModuleManager.hudModule.rgbSpeed.getValue(), (float)Nigga2.hudModule.saturation.getValue() / Float.intBitsToFloat(1.04760717E9f ^ 0x7D0E379F), Float.intBitsToFloat(1.13948518E9f ^ 0x7C6B2A3F), (long)((Nigga14 + 1.0) * 30.0 * Nigga2.hudModule.rainbowOffset.getValue())));
                                GlStateManager.disableAlpha();
                                RenderUtil.disableMask();
                            }
                            if (ModuleManager.hudModule.sides.getMode().equals(Qprot0.0("\u0115\u71ce\u3a4b\u88c6"))) {
                                Gui.drawRect((double)(Nigga3.getScaledWidth() - Nigga4.getStringWidth(Nigga7.displayName) - 14) + Nigga7.animStage, (float)(5.2 + Nigga14 * ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()) - (double)Nigga17), (double)(Nigga3.getScaledWidth() - Nigga4.getStringWidth(Nigga7.displayName) - 12) + Nigga7.animStage, (double)((float)(5.2 + Nigga14 * ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()) - (double)Nigga17)) + ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()), Nigga16);
                            }
                            if (ModuleManager.hudModule.sides.getMode().equals(Qprot0.0("\u010b\u71c2\u3a4a\u88da\u1e2c"))) {
                                Gui.drawRect((double)(Nigga3.getScaledWidth() - 2) + Nigga7.animStage, (float)(5.2 + Nigga14 * ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()) - (double)Nigga17), (double)Nigga3.getScaledWidth() + Nigga7.animStage + Nigga7.animStage, (double)((float)(5.2 + Nigga14 * ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()) - (double)Nigga17)) + ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()), Nigga16);
                            }
                        }
                        Nigga4.drawString(Nigga7.displayName, (double)((float)((double)(Nigga3.getScaledWidth() - Nigga4.getStringWidth(Nigga7.displayName)) - 6.5)) + Nigga7.animStage, (float)(5.2 + Nigga14 * ((double)(Nigga4.FONT_HEIGHT - 3) + Nigga2.hudModule.spacing.getValue()) - (double)Nigga17) + (float)Nigga2.hudModule.spacing.getValue() / Float.intBitsToFloat(1.05356819E9f ^ 0x7ECC2CBF) - Float.intBitsToFloat(1.05731814E9f ^ 0x7F0564F2), Nigga16);
                        GlStateManager.translate(Float.intBitsToFloat(2.12405299E9f ^ 0x7E9A79C7), Float.intBitsToFloat(1.05974547E9f ^ 0x7F2A6EDF), Float.intBitsToFloat(2.13446093E9f ^ 0x7F3949E7));
                    } else if (Nigga2.hudModule.displayMode.getMode().equals(Qprot0.0("\u011a\u71c4\u3a40\u88c2\u1e39\u8480\u8c3b"))) {
                        Nigga4.drawString(Nigga7.displayName, (double)((float)((double)(Nigga3.getScaledWidth() - Nigga4.getStringWidth(Nigga7.displayName)) - 2.5)) + Nigga7.animStage, (double)1.1f + Nigga14 * (double)(Nigga4.FONT_HEIGHT - 2) - (double)Nigga17, Nigga16);
                    }
                    GL11.glColor4d((double)1.0, (double)1.0, (double)1.0, (double)1.0);
                    GlStateManager.popMatrix();
                    Nigga14 += Nigga7.animStageVerticle / Nigga2.hudModule.spacing.getValue();
                }
            }
        }
        Client.onEvent(new EventRenderGUI());
    }

    public static double lambda$1(MinecraftFontRenderer minecraftFontRenderer, Object Nigga) {
        return minecraftFontRenderer.getStringWidth(((Module)Nigga).displayName);
    }

    public static double lambda$0(MinecraftFontRenderer minecraftFontRenderer, Object Nigga) {
        return minecraftFontRenderer.getStringWidth(((Module)Nigga).name);
    }

    public static {
        throw throwable;
    }

    public boolean checkGhost() {
        HUD Nigga;
        return Nigga.ghostIsEnabled;
    }
}

