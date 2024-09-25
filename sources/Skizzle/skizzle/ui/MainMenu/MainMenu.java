/*
 * Decompiled with CFR 0.150.
 */
package skizzle.ui.MainMenu;

import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import skizzle.Client;
import skizzle.newFont.FontUtil;
import skizzle.newFont.MinecraftFontRenderer;
import skizzle.ui.MainMenu.MainButton;
import skizzle.ui.elements.Button;

public class MainMenu
extends GuiScreen {
    public static String[] changeLog;
    public static CopyOnWriteArrayList<Button> uiButtons;
    public static CopyOnWriteArrayList<MainButton> buttons;

    static {
        uiButtons = new CopyOnWriteArrayList();
        buttons = new CopyOnWriteArrayList();
    }

    public MainMenu() {
        MainMenu Nigga;
    }

    @Override
    public void initGui() {
        MainMenu Nigga;
        buttons = new CopyOnWriteArrayList();
        buttons.add(new MainButton(Qprot0.0("\u64d3\u71c2\u5f9a\u7f9a\u6e52\ue15f\u8c3f\u08a1\u8f7a\uc50d\u6b72\uaf1e"), Nigga.width / 2 - 80, Nigga.height / 2 - 45));
        buttons.add(new MainButton(Qprot0.0("\u64cd\u71de\u5f98\u7f89\u6e57\ue14a\u8c23\u08ac\u8f62\uc511\u6b65"), Nigga.width / 2 - 80, Nigga.height / 2 - 20));
        buttons.add(new MainButton(Qprot0.0("\u64cf\u71db\u5f80\u7f94\u6e51\ue154\u8c3c"), Nigga.width / 2 - 80, Nigga.height / 2 + 5));
        buttons.add(new MainButton(Qprot0.0("\u64d1\u71de\u5f9d\u7f89"), Nigga.width / 2 - 80, Nigga.height / 2 + 30));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void mouseClicked(int Nigga, int Nigga, int Nigga) {
        Nigga = 0;
        for (MainButton Nigga : MainMenu.buttons) {
            block11: {
                block13: {
                    block14: {
                        block12: {
                            Nigga.width / 2;
                            Nigga = 26 * Nigga + 250;
                            if (!Nigga.hovered(Nigga, Nigga)) break block11;
                            var8_8 = Nigga.name;
                            switch (var8_8.hashCode()) {
                                case -2064742086: {
                                    if (!var8_8.equals(Qprot0.0("\u64cd\u71de\u5f98\uf240\ue0ee\ue14a\u8c23\u08ac\u02ab\u4ba8\u6b65"))) {
                                        ** break;
                                    }
                                    break block12;
                                }
                                case -1500504759: {
                                    if (var8_8.equals(Qprot0.0("\u64d3\u71c2\u5f9a\uf253\ue0eb\ue15f\u8c3f\u08a1\u02b3\u4bb4\u6b72\uaf1e"))) break;
                                    ** break;
                                }
                                case 2528879: {
                                    if (!var8_8.equals(Qprot0.0("\u64d1\u71de\u5f9d\uf240"))) {
                                        ** break;
                                    }
                                    break block13;
                                }
                                case 415178366: {
                                    if (!var8_8.equals(Qprot0.0("\u64cf\u71db\u5f80\uf25d\ue0e8\ue154\u8c3c"))) {
                                        ** break;
                                    }
                                    break block14;
                                }
                            }
                            Nigga.mc.displayGuiScreen(new GuiSelectWorld(Nigga));
                            Nigga.mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation(Qprot0.0("\u64e7\u71de\u5f9d\uf21a\ue0e5\ue14f\u8c3b\u08b9\u02bd\u4ba3\u6b39\uaf1c\ue664\u27f8\u94cd\uf8f6")), Float.intBitsToFloat(1.08333069E9f ^ 2131906704)));
                            ** break;
                        }
                        Nigga.mc.displayGuiScreen(new GuiMultiplayer(Nigga));
                        Nigga.mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation(Qprot0.0("\u64e7\u71de\u5f9d\uf21a\ue0e5\ue14f\u8c3b\u08b9\u02bd\u4ba3\u6b39\uaf1c\ue664\u27f8\u94cd\uf8f6")), Float.intBitsToFloat(1.0823904E9f ^ 2130966379)));
                        ** break;
                    }
                    Nigga.mc.displayGuiScreen(new GuiOptions(Nigga, Nigga.mc.gameSettings));
                    Nigga.mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation(Qprot0.0("\u64e7\u71de\u5f9d\uf21a\ue0e5\ue14f\u8c3b\u08b9\u02bd\u4ba3\u6b39\uaf1c\ue664\u27f8\u94cd\uf8f6")), Float.intBitsToFloat(1.0862487E9f ^ 2134824733)));
                    ** break;
                }
                Nigga.mc.shutdown();
                Nigga.mc.getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation(Qprot0.0("\u64e7\u71de\u5f9d\uf21a\ue0e5\ue14f\u8c3b\u08b9\u02bd\u4ba3\u6b39\uaf1c\ue664\u27f8\u94cd\uf8f6")), Float.intBitsToFloat(1.10530445E9f ^ 2120326055)));
            }
            ++Nigga;
        }
        var5_6 = MainMenu.uiButtons.iterator();
        while (var5_6.hasNext()) {
            var5_6.next();
        }
    }

    @Override
    public void drawScreen(int Nigga, int Nigga2, float Nigga3) {
        int n;
        MainMenu Nigga4;
        MinecraftFontRenderer Nigga5 = FontUtil.cleanmedium;
        Nigga4.mc.getTextureManager().bindTexture(new ResourceLocation(Qprot0.0("\u64cd\u71ce\u5f9a\u7353\u63da\ue158\u8c2e\u08ae\u83ab\uc8d8\u6b65\uaf03\ue663\ua6e1\u17a8\uf8ab\u42f9\ud281\uc3a5")));
        GuiScreen.drawModalRectWithCustomSizedTexture(0.0, 0.0, Float.intBitsToFloat(2.11045542E9f ^ 0x7DCAFE97), Float.intBitsToFloat(2.135408E9f ^ 0x7F47BDA9), Nigga4.width, Nigga4.height, Nigga4.width, Nigga4.height);
        Nigga4.drawGradientRect(0, Nigga4.height - 100, Nigga4.width, Nigga4.height, 0, -301989888);
        int Nigga6 = 30;
        int Nigga7 = 0;
        if (changeLog != null) {
            String[] arrstring = changeLog;
            n = changeLog.length;
            for (int i = 0; i < n; ++i) {
                if (12 * (Nigga7 + 1) + 25 > Nigga6) {
                    Nigga6 = 12 * (Nigga7 + 1) + 25;
                }
                ++Nigga7;
            }
        }
        Gui.drawRect(Nigga4.width - 130, 8.0, Nigga4.width - 10, Nigga6, Integer.MIN_VALUE);
        Gui.drawRect(Nigga4.width - 130, 8.0, Nigga4.width - 10, 22.0, 0x30000000);
        Nigga5.drawString(Qprot0.0("\u64c3\u71c3\u5f95\u7348\u6392\ue15f\u8c03\u08a2\u83a7\uc885"), Nigga4.width - 94, 11.0, -1);
        Nigga7 = 0;
        if (changeLog != null) {
            String[] arrstring = changeLog;
            int n2 = changeLog.length;
            for (n = 0; n < n2; ++n) {
                String Nigga8 = arrstring[n];
                Nigga5.drawString(Nigga8.replace("\n", " "), Nigga4.width - 120, 12 * (Nigga7 + 1) + 13, -1);
                ++Nigga7;
            }
        }
        Nigga5.drawString(Qprot0.0("\u64cd\u71ca\u5f90\u7343\u63d5\ue158\u8c36\u08ed\u8393\uc8d4\u6b7e\uaf16\ue66c\ua6e2\u17a9"), 5.0, 5.0, -1);
        MainButton Nigga9 = null;
        for (MainButton Nigga10 : buttons) {
            if (Nigga10 == Nigga9) continue;
            boolean Nigga11 = Nigga10.hovered(Nigga, Nigga2);
            int Nigga12 = Nigga10.y;
            int Nigga13 = Nigga10.x;
            String Nigga14 = Nigga10.name;
            Gui.drawRect(Nigga13, Nigga12 - 6, Nigga13 + 160, Nigga12 + 15, Nigga11 ? 0x50000000 : 0x40000000);
            if (Nigga11) {
                int Nigga15 = 80 - Nigga10.animationStage;
                Gui.drawRect(Nigga13 + Nigga15, Nigga12 + 13, Nigga13 + 160 - Nigga15, Nigga12 + 15, -16740097);
                if (Nigga10.animationStage < 80) {
                    Nigga10.animationStage += 30;
                }
                if (Nigga10.animationStage > 80) {
                    Nigga10.animationStage = 80;
                }
            } else {
                Nigga10.animationStage = 0;
            }
            Nigga5.drawCenteredString(Nigga14, Nigga13 + 80, Nigga12 - 1, Nigga11 ? -16740097 : -1);
            Nigga9 = Nigga10;
        }
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)Nigga4.width / Float.intBitsToFloat(1.05490163E9f ^ 0x7EE08597), (float)Nigga4.height / Float.intBitsToFloat(1.05938669E9f ^ 0x7F24F52F), Float.intBitsToFloat(2.13852006E9f ^ 0x7F7739C2));
        GlStateManager.scale(Float.intBitsToFloat(1.06289773E9f ^ 0x7F5A8834), Float.intBitsToFloat(1.06440454E9f ^ 0x7F718630), Float.intBitsToFloat(1.10283968E9f ^ 0x7E3BFF4F));
        GlStateManager.translate(-((float)Nigga4.width / Float.intBitsToFloat(1.05650074E9f ^ 0x7EF8EBF5)), -((float)Nigga4.height / Float.intBitsToFloat(1.02375802E9f ^ 0x7D054EAF)), Float.intBitsToFloat(2.13011123E9f ^ 0x7EF6EAF1));
        FontUtil.cleanhuge.drawCenteredString(Client.name, (float)Nigga4.width / Float.intBitsToFloat(1.05696768E9f ^ 0x7F000BEA), (float)Nigga4.height / Float.intBitsToFloat(1.04408064E9f ^ 0x7E3B67FB) - (float)Nigga4.mc.fontRendererObj.FONT_HEIGHT / Float.intBitsToFloat(1.0616288E9f ^ 0x7F472B7E) - Float.intBitsToFloat(1.03722285E9f ^ 0x7F22C3DF), -1);
        GlStateManager.popMatrix();
    }

    @Override
    public void onGuiClosed() {
    }
}

