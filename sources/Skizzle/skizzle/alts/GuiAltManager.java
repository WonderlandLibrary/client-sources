/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package skizzle.alts;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import skizzle.Client;
import skizzle.alts.Alt;
import skizzle.alts.AltLoginThread;
import skizzle.alts.AltManager;
import skizzle.alts.GuiAddAlt;
import skizzle.alts.GuiAltLogin;
import skizzle.alts.GuiRenameAlt;
import skizzle.newFont.FontUtil;
import skizzle.newFont.MinecraftFontRenderer;
import skizzle.ui.elements.Button;
import skizzle.util.RenderUtil;

public class GuiAltManager
extends GuiScreen {
    public Button login;
    public Button remove;
    public Alt selectedAlt = null;
    public AltLoginThread loginThread;
    public String status = (Object)((Object)EnumChatFormatting.GRAY) + Qprot0.0("\uebd1\u71c4\ud0f3\ua7e5\uc500\u6e51\u8c3c\u87ca\u5711\u6e43\ue464\uaf09\u6952\u7259\ub130\u77fe");
    public int offset;
    public Button rename;

    @Override
    public void sActionPerformed(Button Nigga) throws IOException {
        switch (Nigga.id) {
            case 0: {
                GuiAltManager Nigga2;
                if (Nigga2.loginThread == null) {
                    Nigga2.mc.displayGuiScreen(null);
                    break;
                }
                if (!Nigga2.loginThread.getStatus().equals((Object)((Object)EnumChatFormatting.YELLOW) + Qprot0.0("\uebde\u71df\ud0a7\ue4a4\u0446\u6e55\u8c3b\u8783\u1449\uaf06\ue428\uaf18\u695e\u3148\u707e\u77f5\u42ee\u5de8\u544c\u1b04")) && !Nigga2.loginThread.getStatus().equals((Object)((Object)EnumChatFormatting.RED) + Qprot0.0("\uebdb\u71c4\ud0f3\ue4af\u0444\u6e51\u8c6f\u8782\u144e\uaf15\ue428\uaf0e\u6950\u310b\u7079\u77bb") + (Object)((Object)EnumChatFormatting.YELLOW) + Qprot0.0("\uebbf\u71e7\ud0bc\ue4a6\u044c\u6e4c\u8c21\u878d\u1407\uaf08\ue466\uaf42\u691f\u3146"))) {
                    Nigga2.mc.displayGuiScreen(null);
                    break;
                }
                Nigga2.loginThread.setStatus((Object)((Object)EnumChatFormatting.RED) + Qprot0.0("\uebd9\u71ca\ud0ba\ue4ad\u044e\u6e41\u8c6f\u879e\u1448\uaf41\ue464\uaf03\u6956\u3101\u707c\u77bb\u42a9\u5d98\u5449\u1b0f\u1238\u01d9\ufb9f\uab69\uc3f8\u56ea\u2f07\u3ea1\u0115\u8ed2\u0a1f\u881c\u6636\uee2e") + (Object)((Object)EnumChatFormatting.YELLOW) + Qprot0.0("\uebbf\u71e7\ud0bc\ue4a6\u044c\u6e4c\u8c21\u878d\u1407\uaf08\ue466\uaf42\u691f\u3146"));
                break;
            }
            case 1: {
                GuiAltManager Nigga2;
                Nigga2.selectedAlt.getUsername();
                Nigga2.selectedAlt.getPassword();
                Nigga2.loginThread = new AltLoginThread(Nigga2.selectedAlt);
                Nigga2.loginThread.start();
                break;
            }
            case 2: {
                GuiAltManager Nigga2;
                if (Nigga2.loginThread != null) {
                    Nigga2.loginThread = null;
                }
                AltManager.registry.remove(Nigga2.selectedAlt);
                Nigga2.status = Qprot0.0("\ueb38\u71ca\ud081\ue4a4\u0446\u6e4a\u8c39\u878f\u1443\uaf4f");
                Nigga2.selectedAlt = null;
                break;
            }
            case 3: {
                GuiAltManager Nigga2;
                Nigga2.mc.displayGuiScreen(new GuiAddAlt(Nigga2));
                break;
            }
            case 4: {
                GuiAltManager Nigga2;
                Nigga2.mc.displayGuiScreen(new GuiAltLogin(Nigga2));
                break;
            }
            case 6: {
                GuiAltManager Nigga2;
                Nigga2.mc.displayGuiScreen(new GuiRenameAlt(Nigga2));
                break;
            }
        }
    }

    public static {
        throw throwable;
    }

    @Override
    public void drawScreen(int Nigga, int Nigga2, float Nigga3) {
        GuiAltManager Nigga4;
        MinecraftFontRenderer Nigga5 = Client.fontNormal;
        Nigga4.drawDefaultBackground();
        Gui.drawRect(0.0, 0.0, (float)Nigga4.mc.displayWidth / Float.intBitsToFloat(1.05569862E9f ^ 0x7EECAEA3), (float)Nigga4.mc.displayHeight / Float.intBitsToFloat(1.06115168E9f ^ 0x7F3FE3D0), -14342875);
        int Nigga6 = 30;
        boolean Nigga7 = false;
        for (Alt Nigga8 : AltManager.registry) {
            int Nigga9;
            int n = Nigga9 = Nigga7 ? 250 : 0;
            if (AltManager.lastAlt != null && AltManager.lastAlt.getUsername().equals(Nigga8.getUsername()) && AltManager.lastAlt.getPassword().equals(Nigga8.getPassword())) {
                RenderUtil.drawRoundedRectWithShadow(Nigga4.width - 300, 100.0, Nigga4.width - 50, 400.0, 10.0, -12566463, 3.0, 3.0, -2146430960);
                FontUtil.mediumfont.drawCenteredString(Qprot0.0("\ueb38\u71c7\ud090\u7353\u9ee0\u6e57\u8c2a\u8784\u83b4\u35f8\ue449\uaf0f\u6952\ua6e0\ueade\u77f4\u42fd"), Nigga4.width - 175, Float.intBitsToFloat(1.03260205E9f ^ 0x7F5041BE), -1);
                Nigga5.drawString(Qprot0.0("\uebca\u71d8\ud0b6\u7354\u9efc\u6e44\u8c22\u878f\u83fa\u35f8") + Nigga4.mc.getSession().getUsername(), Nigga4.width - 290, 130.0, -1);
                Nigga5.drawString(Qprot0.0("\uebcb\u71d2\ud0a3\u7343\u9ea8\u6e05") + (Object)((Object)Nigga4.mc.getSession().getSessionType()), Nigga4.width - 290, 145.0, -1);
                Nigga5.drawString(Qprot0.0("\uebcf\u71ca\ud0a0\u7355\u9ee5\u6e4a\u8c3d\u878e\u83fa\u35f8") + (Nigga4.hovering(Nigga4.width - 250, 160.0, Nigga4.width - 248 + Nigga5.getStringWidth(Nigga8.getPassword()), 170.0, Nigga, Nigga2) ? Nigga8.getPassword() : Nigga8.getPassword().replaceAll(".", "*")), Nigga4.width - 290, 160.0, -1);
            }
            if (!Nigga4.isAltInArea(Nigga6 + 30)) continue;
            String Nigga10 = Nigga8.getMask().equals("") ? Nigga8.getUsername() : Nigga8.getMask();
            String Nigga11 = Nigga8.getPassword().equals("") ? Qprot0.0("\ueb38\u71c8\ud090\u7354\u9ef3\u6e46\u8c24\u878f\u83a4") : Nigga8.getPassword().replaceAll(".", "*");
            double Nigga12 = 2.0;
            double Nigga13 = 2.0;
            if (AltManager.lastAlt != null && AltManager.lastAlt.getUsername().equals(Nigga8.getUsername()) && AltManager.lastAlt.getPassword().equals(Nigga8.getPassword())) {
                Nigga13 = 0.0;
                Nigga12 = 0.0;
            }
            if (Nigga4.hovering(100 + Nigga9, 30 + Nigga6 - Nigga4.offset, 300 + Nigga9, 80 + Nigga6 - Nigga4.offset, Nigga, Nigga2)) {
                RenderUtil.drawRoundedRectWithShadow((double)(100 + Nigga9) - Nigga12, (double)(30 + Nigga6 - Nigga4.offset) - Nigga13, (double)(320 + Nigga9) - Nigga12, (double)(80 + Nigga6 - Nigga4.offset) - Nigga13, 10.0, -8355712, Nigga12, Nigga13, -2146430960);
            } else if (Nigga8 == Nigga4.selectedAlt) {
                RenderUtil.drawRoundedRectWithShadow((double)(100 + Nigga9) - Nigga12, (double)(30 + Nigga6 - Nigga4.offset) - Nigga13, (double)(320 + Nigga9) - Nigga12, (double)(80 + Nigga6 - Nigga4.offset) - Nigga13, 10.0, -9408400, Nigga12, Nigga13, -2146430960);
            } else {
                RenderUtil.drawRoundedRectWithShadow((double)(100 + Nigga9) - Nigga12, (double)(30 + Nigga6 - Nigga4.offset) - Nigga13, (double)(320 + Nigga9) - Nigga12, (double)(80 + Nigga6 - Nigga4.offset) - Nigga13, 10.0, -12566463, Nigga12, Nigga13, -2146430960);
            }
            if (AltManager.lastAlt != null && AltManager.lastAlt.getUsername().equals(Nigga8.getUsername()) && AltManager.lastAlt.getPassword().equals(Nigga8.getPassword())) {
                Nigga5.drawString(Qprot0.0("\ueb38\u71ca\ud09f\u7349\u9ef5\u6e42\u8c2a\u878e\u83e0\u3591\ue466"), 250 + Nigga9, 40 + Nigga6 - Nigga4.offset, 0x909090);
            }
            int Nigga14 = 40;
            int Nigga15 = 40;
            RenderUtil.initMask();
            RenderUtil.drawRoundedRect((double)(105 + Nigga9) - Nigga12, (double)(35 + Nigga6 - Nigga4.offset) - Nigga13, (double)(105 + Nigga9) - Nigga12 + (double)Nigga14, (double)(35 + Nigga6 - Nigga4.offset) - Nigga13 + (double)Nigga15, 10.0, -1);
            RenderUtil.useMask();
            Nigga4.mc.getTextureManager().bindTexture(new ResourceLocation(Qprot0.0("\uebcc\u71c0\ud0ba\u735c\u9ee8\u6e49\u8c2a\u87c5\u83b3\u35ac\ue46d\uaf1a\u6954\ua6d0\ueac3\u77ff\u42e8\u5dac\uc3ec\u81a3\u1237\u01cd")));
            GL11.glEnable((int)3042);
            GL11.glEnable((int)2848);
            GuiScreen.drawModalRectWithCustomSizedTexture((double)(105 + Nigga9) - Nigga12, (double)(35 + Nigga6 - Nigga4.offset) - Nigga13, Float.intBitsToFloat(2.13633331E9f ^ 0x7F55DBDD), Float.intBitsToFloat(2.1148137E9f ^ 0x7E0D7EE3), Nigga14, Nigga15, Nigga14, Nigga15);
            RenderUtil.disableMask();
            Nigga5.drawString(Nigga10, (double)(150 + Nigga9) - Nigga12, (double)(40 + Nigga6 - Nigga4.offset) - Nigga13, -1);
            Nigga5.drawString(Qprot0.0("\uebda\u71c6\ud0b2\u734f\u9efe\u6e1f\u8c6f") + Nigga8.getUsername(), (double)(150 + Nigga9) - Nigga12, (double)(51 + Nigga6 - Nigga4.offset) - Nigga13, 0x909090);
            Nigga5.drawString(Nigga11, (double)(150 + Nigga9) - Nigga12, (double)(63 + Nigga6 - Nigga4.offset) - Nigga13, 0x606060);
            Nigga7 = !Nigga7;
            Nigga6 += Nigga7 ? 0 : 55;
            if (Nigga8.headTexture != null || Nigga8.getMask() == Nigga8.getUsername()) continue;
            try {
                Throwable throwable = null;
                Object var19_22 = null;
                try (InputStream Nigga16 = new URL(Qprot0.0("\uebf7\u71df\ud0a7\u7356\u9ee1\u6e1f\u8c60\u87c5\u83ad\u35b1\ue466\uaf03\u6945\ua6ee\uead9\u77b4\u42e7\u5dad\uc3b6\u81fc\u1238\u01dc\ufb9b\u3cda\u5954\u56ea\u2f51") + Nigga8.getMask()).openStream();){
                    Nigga8.headTexture = String.valueOf(Client.skizzlePath) + Nigga8.getMask() + Qprot0.0("\uebb1\u71db\ud0bd\u7341");
                    Files.copy(Nigga16, Paths.get(String.valueOf(Client.skizzlePath) + Nigga8.getMask() + Qprot0.0("\uebb1\u71db\ud0bd\u7341"), new String[0]), new CopyOption[0]);
                }
                catch (Throwable throwable2) {
                    if (throwable == null) {
                        throwable = throwable2;
                    } else if (throwable != throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                    throw throwable;
                }
            }
            catch (Exception Nigga17) {
                Nigga17.printStackTrace();
            }
        }
        if (Mouse.hasWheel()) {
            int Nigga18 = Mouse.getDWheel();
            if (Nigga18 < 0) {
                Nigga4.offset = (int)((double)Nigga4.offset - (double)Nigga18 / 9.23);
                if (Nigga4.offset > Nigga4.mc.displayHeight - Nigga6 - 320) {
                    Nigga4.offset = Nigga4.mc.displayHeight - Nigga6 - 320;
                }
            } else if (Nigga18 > 0) {
                Nigga4.offset = (int)((double)Nigga4.offset - (double)Nigga18 / 9.23);
                if (Nigga4.offset < 0) {
                    Nigga4.offset = 0;
                }
            }
        }
        double Nigga19 = (double)Nigga4.offset / ((double)(Nigga4.mc.displayHeight - Nigga6) - 320.0);
        double Nigga20 = 55.0 + (double)Nigga4.offset / 2.0 * Nigga19 * 4.0;
        RenderUtil.drawRoundedRect(5.0, Nigga20, 10.0, Nigga20 + 1.0 / (double)AltManager.registry.size() * 1000.0, 5.0, -8355712);
        Gui.drawRect(0.0, 0.0, (float)Nigga4.mc.displayWidth / Float.intBitsToFloat(1.05104736E9f ^ 0x7EA5B5AB), 50.0, -15198184);
        Nigga5.drawString(Nigga4.mc.getSession().getUsername(), 10.0, 10.0, 0x808080);
        Gui.drawRect(0.0, (float)Nigga4.mc.displayHeight / Float.intBitsToFloat(1.02438925E9f ^ 0x7D0EF06F) - Float.intBitsToFloat(1.0246096E9f ^ 0x7F5A4D38), (float)Nigga4.mc.displayWidth / Float.intBitsToFloat(1.06105568E9f ^ 0x7F3E6CD1), (float)Nigga4.mc.displayHeight / Float.intBitsToFloat(1.0582727E9f ^ 0x7F13F5DA), -15198184);
        Nigga5.drawCenteredString(Nigga4.loginThread == null ? Nigga4.status : Nigga4.loginThread.getStatus(), Nigga4.width / 2, Float.intBitsToFloat(1.05301747E9f ^ 0x7F63C56F), -1);
        super.drawScreen(Nigga, Nigga2, Nigga3);
        if (Nigga4.selectedAlt == null) {
            Nigga4.login.enabled = false;
            Nigga4.remove.enabled = false;
            Nigga4.rename.enabled = false;
        } else {
            Nigga4.login.enabled = true;
            Nigga4.remove.enabled = true;
            Nigga4.rename.enabled = true;
        }
        if (Keyboard.isKeyDown((int)200)) {
            Nigga4.offset -= 26;
            if (Nigga4.offset < 0) {
                Nigga4.offset = 0;
            }
        } else if (Keyboard.isKeyDown((int)208)) {
            Nigga4.offset += 26;
            if (Nigga4.offset < 0) {
                Nigga4.offset = 0;
            }
        }
    }

    public void prepareScissorBox(float Nigga, float Nigga2, float Nigga3, float Nigga4) {
        GuiAltManager Nigga5;
        ScaledResolution Nigga6 = new ScaledResolution(Nigga5.mc, Nigga5.mc.displayWidth, Nigga5.mc.displayHeight);
        int Nigga7 = Nigga6.getScaleFactor();
        GL11.glScissor((int)((int)(Nigga * (float)Nigga7)), (int)((int)(((float)Nigga6.getScaledHeight() - Nigga4) * (float)Nigga7)), (int)((int)((Nigga3 - Nigga) * (float)Nigga7)), (int)((int)((Nigga4 - Nigga2) * (float)Nigga7)));
    }

    public GuiAltManager() {
        GuiAltManager Nigga;
    }

    public boolean hovering(double Nigga, double Nigga2, double Nigga3, double Nigga4, double Nigga5, double Nigga6) {
        return Nigga5 >= Nigga && Nigga5 <= Nigga3 && Nigga6 >= Nigga2 && Nigga6 <= Nigga4;
    }

    public boolean isMouseOverAlt(int Nigga, int Nigga2, int Nigga3) {
        GuiAltManager Nigga4;
        return Nigga >= Nigga4.width / 3 - 20 && Nigga2 >= Nigga3 - 6 && Nigga <= Nigga4.width / 3 + 252 && Nigga2 <= Nigga3 + 22 && Nigga >= 0 && Nigga2 >= 33 && Nigga <= Nigga4.width && Nigga2 <= Nigga4.height - 50;
    }

    @Override
    public void initGui() {
        GuiAltManager Nigga;
        Nigga.sButtonList.add(new Button(Qprot0.0("\uebdc\u71ca\ud0bd\u7f9e\u9db2\u6e49"), 0, Nigga.width / 2 + 4 + 50, Nigga.height - 24, 100, 20));
        Nigga.login = new Button(1, Nigga.width / 2 - 154, Nigga.height - 48, 100, 20, Qprot0.0("\uebd3\u71c4\ud0b4\u7f94\u9db9"));
        Nigga.sButtonList.add(Nigga.login);
        Nigga.remove = new Button(2, Nigga.width / 2 - 154, Nigga.height - 24, 100, 20, Qprot0.0("\uebcd\u71ce\ud0be\u7f92\u9da1\u6e40"));
        Nigga.sButtonList.add(Nigga.remove);
        Nigga.sButtonList.add(new Button(Qprot0.0("\uebde\u71cf\ud0b7"), 3, Nigga.width / 2 + 4 + 50, Nigga.height - 48, 100, 20));
        Nigga.sButtonList.add(new Button(Qprot0.0("\uebdb\u71c2\ud0a1\u7f98\u9db4\u6e51\u8c6f\u87a6\u8f74\u36fa\ue461\uaf02"), 4, Nigga.width / 2 - 50, Nigga.height - 48, 100, 20));
        Nigga.rename = new Button(Qprot0.0("\uebda\u71cf\ud0ba\u7f89"), 6, Nigga.width / 2 - 50, Nigga.height - 24, 100, 20);
        Nigga.sButtonList.add(Nigga.rename);
        Nigga.login.enabled = false;
        Nigga.remove.enabled = false;
        Nigga.rename.enabled = false;
    }

    @Override
    public void mouseClicked(int Nigga, int Nigga2, int Nigga3) throws IOException {
        GuiAltManager Nigga4;
        if (Nigga4.offset < 0) {
            Nigga4.offset = 0;
        }
        int Nigga5 = 30;
        boolean Nigga6 = false;
        for (Alt Nigga7 : AltManager.registry) {
            int Nigga8;
            int n = Nigga8 = Nigga6 ? 220 : 0;
            if (Nigga4.hovering(100 + Nigga8, 30 + Nigga5 - Nigga4.offset, 300 + Nigga8, 80 + Nigga5 - Nigga4.offset, Nigga, Nigga2)) {
                if (Nigga7 == Nigga4.selectedAlt) {
                    Nigga4.sActionPerformed((Button)Nigga4.sButtonList.get(1));
                    return;
                }
                Nigga4.selectedAlt = Nigga7;
            }
            Nigga6 = !Nigga6;
            Nigga5 += Nigga6 ? 0 : 55;
        }
        try {
            super.mouseClicked(Nigga, Nigga2, Nigga3);
        }
        catch (IOException Nigga9) {
            Nigga9.printStackTrace();
        }
    }

    public boolean isAltInArea(int Nigga) {
        GuiAltManager Nigga2;
        return Nigga - Nigga2.offset <= Nigga2.height - 50;
    }
}

