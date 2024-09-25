/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package skizzle.modules.render;

import java.awt.Color;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventKey;
import skizzle.events.listeners.EventRenderGUI;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;
import skizzle.newFont.MinecraftFontRenderer;
import skizzle.settings.BooleanSetting;
import skizzle.settings.KeybindSetting;
import skizzle.settings.ModeSetting;
import skizzle.settings.NumberSetting;
import skizzle.settings.Setting;
import skizzle.util.ColourUtil;
import skizzle.util.RenderUtil;
import skizzle.util.Timer;

public class TABGui
extends Module {
    public Timer animTimerM;
    public int currentTab;
    public boolean expanded;
    public Timer animTimerV;
    public Timer animTimerH = new Timer();

    public static {
        throw throwable;
    }

    public TABGui() {
        super(Qprot0.0("\u07bf\u71ca\u3cfd\ua7a4\u297f\u8204\u8c06"), 43, Module.Category.RENDER);
        TABGui Nigga;
        Nigga.animTimerV = new Timer();
        Nigga.animTimerM = new Timer();
    }

    @Override
    public void onEnable() {
        Client.tabGUI = true;
    }

    @Override
    public void onDisable() {
        Client.tabGUI = false;
    }

    @Override
    public void onEvent(Event Nigga) {
        Object Nigga2;
        int Nigga3;
        TABGui Nigga4;
        if (Nigga instanceof EventRenderGUI && !Client.ghostMode && !Nigga4.mc.gameSettings.showDebugInfo) {
            Nigga3 = -10;
            if (ModuleManager.hudModule.fps.isEnabled()) {
                Nigga3 += 7;
            }
            if (ModuleManager.hudModule.speed.isEnabled()) {
                Nigga3 += 7;
            }
            if (ModuleManager.hudModule.coords.isEnabled()) {
                Nigga3 += 7;
            }
            if (ModuleManager.hudModule.clock.isEnabled()) {
                Nigga3 += 7;
            }
            Nigga2 = Client.fontNormal;
            int Nigga5 = 3;
            int Nigga6 = Client.RGBColor;
            int Nigga7 = Color.HSBtoRGB(Client.hue, Float.intBitsToFloat(1.10488192E9f ^ 0x7EE81A2F), Float.intBitsToFloat(1.1000329E9f ^ 0x7E88B217));
            RenderUtil.drawRoundedRect(5.0, 33 + Nigga3, 70.0, 30 + Module.Category.values().length * 15 + 3 + Nigga3, 6.0, -1440735200);
            int Nigga8 = 0;
            for (Module.Category Nigga9 : Module.Category.values()) {
                int Nigga10 = Nigga6;
                if (!ModuleManager.hudModule.color.getMode().equals(Qprot0.0("\u07af\u71c2\u3cec\ue26c\u73d1\u823d\u8c2a\u6bc2"))) {
                    Nigga10 = ColourUtil.getRainbow((float)ModuleManager.hudModule.rgbSpeed.getValue(), Float.intBitsToFloat(1.07514701E9f ^ 0x7F59BDFD), Float.intBitsToFloat(1.12471936E9f ^ 0x7C89DB1F), (long)((double)(Nigga8 * 30) * ModuleManager.hudModule.rainbowOffset.getValue()));
                }
                if (Nigga4.currentTab == Nigga8) {
                    RenderUtil.drawRoundedRect(7.0, 34 + Nigga8 * 15 + Nigga3, 68.0, 48 + Nigga8 * 15 + Nigga3 - 1, 6.0, Nigga10);
                    ((MinecraftFontRenderer)Nigga2).drawString(Qprot0.0("\u07cb\u718b\u3cbf") + Nigga9.name, 10.0, 33 + Nigga8 * 15 + Nigga5 + Nigga3, -1);
                } else {
                    ((MinecraftFontRenderer)Nigga2).drawString(Nigga9.name, 10.0, 33 + Nigga8 * 15 + Nigga5 + Nigga3, -1);
                }
                ++Nigga8;
            }
            if (Nigga4.expanded) {
                Module.Category Nigga9;
                Nigga9 = Module.Category.values()[Nigga4.currentTab];
                List<Module> Nigga11 = Client.getModulesByCategory(Nigga9);
                int Nigga12 = 0;
                for (Module Nigga13 : Nigga11) {
                    if (((MinecraftFontRenderer)Nigga2).getStringWidth(Qprot0.0("\u07d5\u718b") + Nigga13.name) <= Nigga12) continue;
                    Nigga12 = ((MinecraftFontRenderer)Nigga2).getStringWidth(Qprot0.0("\u07cb\u718b\u3cbf") + Nigga13.name);
                }
                RenderUtil.drawRoundedRect(70.0, 33 + Nigga3, 85 + Nigga12, 32.0 + (double)Nigga11.size() * 15.9 + 3.0 + (double)Nigga3, 6.0, -1440735200);
                Nigga8 = 0;
                for (Module Nigga13 : Nigga11) {
                    int Nigga14 = Nigga6;
                    if (!ModuleManager.hudModule.color.getMode().equals(Qprot0.0("\u07af\u71c2\u3cec\ue26c\u73d1\u823d\u8c2a\u6bc2"))) {
                        Nigga14 = ColourUtil.getRainbow((float)ModuleManager.hudModule.rgbSpeed.getValue(), Float.intBitsToFloat(1.07391002E9f ^ 0x7F4E5DE0), Float.intBitsToFloat(1.08708147E9f ^ 0x7F4B8C04), Nigga8 * 30);
                    }
                    if (Nigga8 == Nigga9.moduleIndex) {
                        RenderUtil.drawRoundedRect(72.0, 32 + Nigga8 * 16 + 1 + Nigga3, 83 + Nigga12, 32 + Nigga8 * 16 + 16 + Nigga3, 6.0, Nigga14);
                    }
                    GlStateManager.pushMatrix();
                    ((MinecraftFontRenderer)Nigga2).drawString(Nigga8 == Nigga9.moduleIndex ? Qprot0.0("\u07cb\u718b\u3cbf") + Nigga13.name : Nigga13.name, 80.0, 33 + Nigga8 * 16 + Nigga5 + Nigga3, -1);
                    if (Nigga13.toggled) {
                        RenderUtil.drawRoundedRect(73.0, 33 + Nigga8 * 16 + Nigga3 + 1, 75.0, 33 + Nigga8 * 16 + 12 + 3 + Nigga3, 2.0, Nigga14);
                    }
                    GlStateManager.popMatrix();
                    if (Nigga8 == Nigga9.moduleIndex && Nigga13.expanded) {
                        Setting Nigga15;
                        int Nigga16 = 0;
                        int Nigga17 = 0;
                        for (Setting Nigga18 : Nigga13.settings) {
                            if (Nigga18 instanceof BooleanSetting) {
                                Nigga15 = (BooleanSetting)Nigga18;
                                if (Nigga17 < ((MinecraftFontRenderer)Nigga2).getStringWidth(String.valueOf(Nigga18.name) + Qprot0.0("\u07d1\u718b") + (((BooleanSetting)Nigga15).enabled ? Qprot0.0("\u07ae\u71c5\u3cfe\ue26f\u73df\u8234\u8c2b") : Qprot0.0("\u07af\u71c2\u3cec\ue26c\u73d1\u823d\u8c2a\u6bc2")))) {
                                    Nigga17 = ((MinecraftFontRenderer)Nigga2).getStringWidth(String.valueOf(Nigga18.name) + Qprot0.0("\u07d1\u718b") + (((BooleanSetting)Nigga15).enabled ? Qprot0.0("\u07ae\u71c5\u3cfe\ue26f\u73df\u8234\u8c2b") : Qprot0.0("\u07af\u71c2\u3cec\ue26c\u73d1\u823d\u8c2a\u6bc2")));
                                }
                            }
                            if (Nigga18 instanceof NumberSetting) {
                                Nigga15 = (NumberSetting)Nigga18;
                                if (Nigga17 < ((MinecraftFontRenderer)Nigga2).getStringWidth(String.valueOf(Nigga18.name) + Qprot0.0("\u07d1\u718b") + ((NumberSetting)Nigga15).getValue())) {
                                    Nigga17 = ((MinecraftFontRenderer)Nigga2).getStringWidth(String.valueOf(Nigga18.name) + Qprot0.0("\u07d1\u718b") + ((NumberSetting)Nigga15).getValue());
                                }
                            }
                            if (Nigga18 instanceof ModeSetting) {
                                Nigga15 = (ModeSetting)Nigga18;
                                if (Nigga17 < ((MinecraftFontRenderer)Nigga2).getStringWidth(String.valueOf(Nigga18.name) + Qprot0.0("\u07d1\u718b") + ((ModeSetting)Nigga15).getMode())) {
                                    Nigga17 = ((MinecraftFontRenderer)Nigga2).getStringWidth(String.valueOf(Nigga18.name) + Qprot0.0("\u07d1\u718b") + ((ModeSetting)Nigga15).getMode());
                                }
                            }
                            if (Nigga18 instanceof KeybindSetting) {
                                Nigga15 = (KeybindSetting)Nigga18;
                                if (Nigga17 < ((MinecraftFontRenderer)Nigga2).getStringWidth(String.valueOf(Nigga18.name) + Qprot0.0("\u07d1\u718b") + (!Keyboard.getKeyName((int)((KeybindSetting)Nigga15).code).equals(Qprot0.0("\u07a5\u71e4\u3cd1\ue248")) ? Keyboard.getKeyName((int)((KeybindSetting)Nigga15).code) : Qprot0.0("\u07a5\u71c4\u3ceb\ue22d\u73e0\u8234\u8c3b")))) {
                                    Nigga17 = ((MinecraftFontRenderer)Nigga2).getStringWidth(String.valueOf(Nigga18.name) + Qprot0.0("\u07d1\u718b") + (!Keyboard.getKeyName((int)((KeybindSetting)Nigga15).code).equals(Qprot0.0("\u07a5\u71e4\u3cd1\ue248")) ? Keyboard.getKeyName((int)((KeybindSetting)Nigga15).code) : Qprot0.0("\u07a5\u71c4\u3ceb\ue22d\u73e0\u8234\u8c3b")));
                                }
                            }
                            ++Nigga16;
                        }
                        if (!Nigga13.settings.isEmpty()) {
                            if (Nigga13.settingAnimStageH > 0 && Nigga4.animTimerH.hasTimeElapsed((long)1983945884 ^ 0x76409C9DL, true)) {
                                --Nigga13.settingAnimStageH;
                            }
                            if (Nigga13.settingAnimStageV > 0 && Nigga4.animTimerV.hasTimeElapsed((long)1137989963 ^ 0x43D4594AL, true)) {
                                int Nigga19 = Nigga13.settings.size() / 4;
                                if (Nigga19 == 0) {
                                    Nigga19 = 1;
                                }
                                Nigga13.settingAnimStageV -= Nigga19;
                            }
                            if (Nigga13.settingAnimStageV < 0) {
                                Nigga13.settingAnimStageV = 0;
                            }
                            RenderUtil.drawRoundedRect(Nigga12 + 17 + 68, 33.5 + (double)Nigga3, Nigga17 + 5 + 68 + 27 + Nigga12 - Nigga13.settingAnimStageH, (double)(30 + Nigga13.settings.size() * 16) + 1.5 + 3.0 - (double)Nigga13.settingAnimStageV + (double)Nigga3, 6.0, -1440735200);
                            if ((float)(33 + Nigga13.index * 16 + 12) + Float.intBitsToFloat(1.0441984E9f ^ 0x7E8D3409) - (float)Nigga13.settingAnimStageV > (float)(28 + Nigga13.index * 16)) {
                                RenderUtil.drawRoundedRect(Nigga12 + 12 + 74, 34 + Nigga13.index * 16 + Nigga3, Nigga17 + 5 + 79 + 16 + Nigga12 - Nigga13.settingAnimStageH - 1, (float)(33 + Nigga13.index * 16 + 12) + Float.intBitsToFloat(1.07312416E9f ^ 0x7F469329) - (float)Nigga13.settingAnimStageV + (float)Nigga3 - Float.intBitsToFloat(1.11445325E9f ^ 0x7DED34EF), 6.0, Nigga13.settings.get((int)Nigga13.index).focused ? Nigga7 : Nigga14);
                            }
                            Nigga16 = 0;
                            for (Setting Nigga18 : Nigga13.settings) {
                                if (33 + (Nigga13.settings.size() - 3) * 16 + 12 - Nigga13.settingAnimStageV > Nigga16 * 16 + Nigga5 + 1) {
                                    if (Nigga18 instanceof BooleanSetting) {
                                        Nigga15 = (BooleanSetting)Nigga18;
                                        Client.drawString(String.valueOf(Nigga18.name) + Qprot0.0("\u07d1\u718b") + (((BooleanSetting)Nigga15).enabled ? Qprot0.0("\u07ae\u71c5\u3cfe\ue26f\u73df\u8234\u8c2b") : Qprot0.0("\u07af\u71c2\u3cec\ue26c\u73d1\u823d\u8c2a\u6bc2")), 92 + Nigga12, 32 + Nigga16 * 16 + Nigga5 + 1 + Nigga3, -1, false);
                                    }
                                    if (Nigga18 instanceof NumberSetting) {
                                        Nigga15 = (NumberSetting)Nigga18;
                                        Client.drawString(String.valueOf(Nigga18.name) + Qprot0.0("\u07d1\u718b") + ((NumberSetting)Nigga15).getValue(), 92 + Nigga12, 32 + Nigga16 * 16 + Nigga5 + 1 + Nigga3, -1, false);
                                    }
                                    if (Nigga18 instanceof ModeSetting) {
                                        Nigga15 = (ModeSetting)Nigga18;
                                        Client.drawString(String.valueOf(Nigga18.name) + Qprot0.0("\u07d1\u718b") + ((ModeSetting)Nigga15).getMode(), 92 + Nigga12, 32 + Nigga16 * 16 + Nigga5 + 1 + Nigga3, -1, false);
                                    }
                                    if (Nigga18 instanceof KeybindSetting) {
                                        Nigga15 = (KeybindSetting)Nigga18;
                                        Client.drawString(String.valueOf(Nigga18.name) + Qprot0.0("\u07d1\u718b") + (!Keyboard.getKeyName((int)((KeybindSetting)Nigga15).code).equals(Qprot0.0("\u07a5\u71e4\u3cd1\ue248")) ? Keyboard.getKeyName((int)((KeybindSetting)Nigga15).code) : Qprot0.0("\u07a5\u71c4\u3ceb\ue22d\u73e0\u8234\u8c3b")), 92 + Nigga12, 32 + Nigga16 * 16 + Nigga5 + 1 + Nigga3, -1, false);
                                    }
                                }
                                ++Nigga16;
                            }
                        }
                    }
                    if (!Nigga13.expanded) {
                        Nigga13.settingAnimStageH = 70;
                        Nigga13.settingAnimStageV = (int)((float)(33 + (Nigga13.settings.size() - 3) * 16 + 12) + Float.intBitsToFloat(1.06630394E9f ^ 0x7F3E81C1));
                    }
                    ++Nigga8;
                }
            }
        }
        if (Nigga instanceof EventKey) {
            Nigga3 = ((EventKey)Nigga).code;
            Nigga2 = Module.Category.values()[Nigga4.currentTab];
            List<Module> Nigga20 = Client.getModulesByCategory((Module.Category)((Object)Nigga2));
            if (Nigga3 == 205) {
                Module Nigga21 = Nigga20.get(((Module.Category)Nigga2).moduleIndex);
                if (Nigga4.expanded && !Nigga21.expanded && !Nigga21.settings.isEmpty()) {
                    Nigga21.expanded = true;
                }
                if (!Nigga4.expanded) {
                    Nigga4.expanded = true;
                }
                if (Nigga4.expanded && !Nigga20.isEmpty() && Nigga20.get((int)Nigga2.moduleIndex).expanded && !Nigga21.settings.isEmpty()) {
                    Setting Nigga22 = Nigga21.settings.get(Nigga21.index);
                    if (Nigga21.settings.get((int)Nigga21.index).focused) {
                        if (Nigga22 instanceof BooleanSetting) {
                            ((BooleanSetting)Nigga22).toggle();
                        }
                        if (Nigga22 instanceof ModeSetting) {
                            ((ModeSetting)Nigga22).cycle(true);
                        }
                        if (Nigga22 instanceof NumberSetting) {
                            ((NumberSetting)Nigga22).increment(true);
                        }
                    }
                }
            }
            if (Nigga3 == 28 && Nigga4.expanded && Nigga20.size() != 0) {
                Nigga20 = Client.getModulesByCategory((Module.Category)((Object)Nigga2));
                Module Nigga23 = Nigga20.get(((Module.Category)Nigga2).moduleIndex);
                if (Nigga4.expanded && (!Nigga4.expanded || Nigga20.isEmpty() || !Nigga20.get((int)Nigga2.moduleIndex).expanded)) {
                    Nigga23.toggle();
                }
                if (!Nigga23.settings.isEmpty() && Nigga23.expanded) {
                    boolean bl = Nigga23.settings.get((int)Nigga23.index).focused = !Nigga23.settings.get((int)Nigga23.index).focused;
                }
            }
            if (Nigga3 == 200) {
                if (Nigga4.expanded) {
                    if (Nigga4.expanded && !Nigga20.isEmpty() && Nigga20.get((int)Nigga2.moduleIndex).expanded) {
                        Module Nigga24 = Nigga20.get(((Module.Category)Nigga2).moduleIndex);
                        if (!Nigga24.settings.isEmpty() && !Nigga24.settings.get((int)Nigga24.index).focused) {
                            Nigga24.index = Nigga24.index <= 0 ? Nigga24.settings.size() - 1 : --Nigga24.index;
                        }
                    } else {
                        ((Module.Category)Nigga2).moduleIndex = ((Module.Category)Nigga2).moduleIndex <= 0 ? Nigga20.size() - 1 : --((Module.Category)Nigga2).moduleIndex;
                    }
                } else {
                    Nigga4.currentTab = Nigga4.currentTab <= 0 ? Module.Category.values().length - 1 : --Nigga4.currentTab;
                }
            }
            if (Nigga3 == 208) {
                if (Nigga4.expanded) {
                    if (Nigga4.expanded && !Nigga20.isEmpty() && Nigga20.get((int)Nigga2.moduleIndex).expanded) {
                        Module Nigga25 = Nigga20.get(((Module.Category)Nigga2).moduleIndex);
                        if (!Nigga25.settings.isEmpty() && !Nigga25.settings.get((int)Nigga25.index).focused) {
                            Nigga25.index = Nigga25.index >= Nigga25.settings.size() - 1 ? 0 : ++Nigga25.index;
                        }
                    } else {
                        ((Module.Category)Nigga2).moduleIndex = ((Module.Category)Nigga2).moduleIndex >= Nigga20.size() - 1 ? 0 : ++((Module.Category)Nigga2).moduleIndex;
                    }
                } else {
                    Nigga4.currentTab = Nigga4.currentTab >= Module.Category.values().length - 1 ? 0 : ++Nigga4.currentTab;
                }
            }
            if (Nigga3 == 203) {
                Module Nigga26 = Nigga20.get(((Module.Category)Nigga2).moduleIndex);
                if (Nigga4.expanded && !Nigga20.isEmpty() && Nigga20.get((int)Nigga2.moduleIndex).expanded) {
                    if (!Nigga26.settings.get((int)Nigga26.index).focused) {
                        Nigga20.get((int)Nigga2.moduleIndex).expanded = false;
                    }
                } else {
                    Nigga4.expanded = false;
                }
                if (!(!Nigga4.expanded && Nigga20.isEmpty() && Nigga20.get((int)Nigga2.moduleIndex).expanded || Nigga26.settings.isEmpty())) {
                    Setting Nigga27 = Nigga26.settings.get(Nigga26.index);
                    if (Nigga26.settings.get((int)Nigga26.index).focused) {
                        if (Nigga27 instanceof BooleanSetting) {
                            ((BooleanSetting)Nigga27).toggle();
                        }
                        if (Nigga27 instanceof ModeSetting) {
                            ((ModeSetting)Nigga27).cycle(false);
                        }
                        if (Nigga27 instanceof NumberSetting) {
                            ((NumberSetting)Nigga27).increment(false);
                        }
                    }
                }
            }
            if (Nigga4.expanded && !Nigga20.isEmpty() && Nigga20.get((int)Nigga2.moduleIndex).expanded) {
                Module Nigga28 = Nigga20.get(((Module.Category)Nigga2).moduleIndex);
                if (!Nigga28.settings.isEmpty() && Nigga28.settings.get(Nigga28.index) instanceof KeybindSetting && Nigga28.settings.get((int)Nigga28.index).focused) {
                    if (Nigga3 != 28 && Nigga3 != 200 && Nigga3 != 208 && Nigga3 != 205 && Nigga3 != 203 && Nigga3 != 1) {
                        KeybindSetting Nigga29 = (KeybindSetting)Nigga28.settings.get(Nigga28.index);
                        Nigga29.code = Nigga3;
                        Nigga29.focused = false;
                        return;
                    }
                    if (Nigga3 == 1) {
                        KeybindSetting Nigga30 = (KeybindSetting)Nigga28.settings.get(Nigga28.index);
                        Nigga30.code = 0;
                        Nigga30.focused = false;
                        return;
                    }
                }
            }
        }
    }
}

