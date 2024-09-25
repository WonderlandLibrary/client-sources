/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.modules.Module;
import skizzle.settings.BooleanSetting;
import skizzle.settings.ModeSetting;
import skizzle.settings.NumberSetting;

public class HUDModule
extends Module {
    public ModeSetting infoSetting;
    public NumberSetting rgbSpeed;
    public ModeSetting fontSetting;
    public ModeSetting displayMode;
    public NumberSetting colorRed;
    public FontRenderer defaultFont;
    public BooleanSetting niceFPS;
    public NumberSetting colorGreen;
    public Minecraft mc = Minecraft.getMinecraft();
    public BooleanSetting speed;
    public BooleanSetting coords;
    public ModeSetting sides;
    public ModeSetting color;
    public BooleanSetting dynamicScoreboard;
    public NumberSetting rainbowOffset;
    public NumberSetting spacing;
    public NumberSetting saturation;
    public BooleanSetting clock;
    public NumberSetting animSpeed;
    public BooleanSetting rgbOutline;
    public BooleanSetting fps;
    public BooleanSetting noDepthOutline;
    public BooleanSetting rgbEnabled;
    public NumberSetting colorBlue;

    public HUDModule() {
        super(Qprot0.0("\u35ce\u71fe\u0ebe"), 0, Module.Category.RENDER);
        HUDModule Nigga;
        Nigga.rgbSpeed = new NumberSetting(Qprot0.0("\u35c5\u71c3\u0e88\ua7eb\u6738\ub05d\u8c6f\u5990\u5712\ucc7a\u3a74\uaf08"), 5.0, 1.0, 15.0, 1.0);
        Nigga.colorRed = new NumberSetting(Qprot0.0("\u35d4\u71ce\u0e9e"), 0.0, 0.0, 255.0, 1.0);
        Nigga.colorGreen = new NumberSetting(Qprot0.0("\u35c1\u71d9\u0e9f\ua7e1\u673b"), 144.0, 0.0, 255.0, 1.0);
        Nigga.colorBlue = new NumberSetting(Qprot0.0("\u35c4\u71c7\u0e8f\ua7e1"), 255.0, 0.0, 255.0, 1.0);
        Nigga.saturation = new NumberSetting(Qprot0.0("\u35d5\u71ca\u0e8e\ua7f1\u6727\ub05d\u8c3b\u59aa\u570d\ucc71"), 204.0, 0.0, 255.0, 1.0);
        Nigga.fps = new BooleanSetting(Qprot0.0("\u35d5\u71c3\u0e95\ua7f3\u6775\ub07a\u8c1f\u5990"), true);
        Nigga.niceFPS = new BooleanSetting(Qprot0.0("\u35d5\u71c6\u0e95\ua7eb\u6721\ub054\u8c6f\u5985\u5732\ucc4c"), true);
        Nigga.speed = new BooleanSetting(Qprot0.0("\u35d5\u71c3\u0e95\ua7f3\u6775\ub06f\u8c3f\u59a6\u5707\ucc7b"), true);
        Nigga.clock = new BooleanSetting(Qprot0.0("\u35c5\u71c7\u0e95\ua7e7\u673e"), true);
        Nigga.coords = new BooleanSetting(Qprot0.0("\u35c5\u71c4\u0e95\ua7f6\u6731\ub04f"), true);
        Nigga.rgbEnabled = new BooleanSetting(Qprot0.0("\u35c5\u71c3\u0e88\ua7eb\u6738\ub05d"), false);
        Nigga.color = new ModeSetting(Qprot0.0("\u35c5\u71c4\u0e96\ua7eb\u6727"), Qprot0.0("\u35d4\u71ca\u0e93\ua7ea\u6737\ub053\u8c38"), Qprot0.0("\u35d5\u71df\u0e9b\ua7f0\u673c\ub05f"), Qprot0.0("\u35d4\u71ca\u0e93\ua7ea\u6737\ub053\u8c38"), Qprot0.0("\u35d4\u71ce\u0e8c\ua7e1\u6727\ub04f\u8c2a\u59a7\u5730\ucc7e\u3a78\uaf02\ub77a\u7242\u131b"), Qprot0.0("\u35c4\u718d\u0ead\ua7a4\u6707\ub05d\u8c26\u59ad\u5700\ucc70\u3a66"), Qprot0.0("\u35d4\u71ca\u0e94\ua7e0\u673a\ub051"));
        Nigga.sides = new ModeSetting(Qprot0.0("\u35cb\u71c4\u0e9e\ua7f1\u6739\ub059\u8c6f\u5981\u5703\ucc6d\u3a62"), Qprot0.0("\u35ca\u71ce\u0e9c\ua7f0"), Qprot0.0("\u35ca\u71ce\u0e9c\ua7f0"), Qprot0.0("\u35d4\u71c2\u0e9d\ua7ec\u6721"), Qprot0.0("\u35ca\u71c2\u0e94\ua7e1\u6726"));
        Nigga.fontSetting = new ModeSetting(Qprot0.0("\u35c0\u71c4\u0e94\ua7f0\u6726"), Qprot0.0("\u35d5\u71c0\u0e93\ua7fe\u672f\ub050\u8c2a"), Qprot0.0("\u35d5\u71c0\u0e93\ua7fe\u672f\ub050\u8c2a"), Qprot0.0("\u35c8\u71c4\u0e8e\ua7ec\u673c\ub052\u8c28"), Qprot0.0("\u35c3\u71dd\u0e9f\ua7f6\u672c\ub048\u8c27\u59aa\u570c\ucc78"));
        Nigga.infoSetting = new ModeSetting(Qprot0.0("\u35cb\u71c4\u0e9e\ua7f1\u6739\ub059\u8c6f\u598a\u570c\ucc79\u3a7e"), Qprot0.0("\u35c8\u71c4\u0e88\ua7e9\u6734\ub050"), Qprot0.0("\u35c8\u71c4\u0e88\ua7e9\u6734\ub050"), Qprot0.0("\u35d2\u71c4\u0e95\ua7a4\u6718\ub049\u8c2c\u59ab"), Qprot0.0("\u35c8\u71c4\u0e8e\ua7ec\u673c\ub052\u8c28"));
        Nigga.dynamicScoreboard = new BooleanSetting(Qprot0.0("\u35c2\u71d2\u0e9b\ua7e9\u673c\ub05f\u8c6f\u5990\u5701\ucc70\u3a63\uaf09\ub77a\u7242\u130d\ua9f1\u42ed"), true);
        Nigga.animSpeed = new NumberSetting(Qprot0.0("\u35c7\u71c5\u0e93\ua7e9\u6734\ub048\u8c26\u59ac\u570c\ucc3f\u3a42\uaf1c\ub77d\u7248\u1308"), 1.0, 0.0, 6.0, 0.0);
        Nigga.rainbowOffset = new NumberSetting(Qprot0.0("\u35d4\u71ca\u0e93\ua7ea\u6737\ub053\u8c38\u59e3\u572d\ucc79\u3a77\uaf1f\ub77d\u7259"), 1.0, 0.0, 20.0, 1.0);
        Nigga.rgbOutline = new BooleanSetting(Qprot0.0("\u35c4\u71c7\u0e95\ua7e7\u673e\ub01c\u8c00\u59b6\u5716\ucc73\u3a78\uaf02\ub77d"), false);
        Nigga.noDepthOutline = new BooleanSetting(Qprot0.0("\u35c8\u71c4\u0eda\ua7c0\u6730\ub04c\u8c3b\u59ab\u5742\ucc5d\u3a7d\uaf03\ub77b\u7246\u134c\ua9cc\u42fc\u8395\u170c\u787d\ucc2e\u01cf"), false);
        Nigga.displayMode = new ModeSetting(Qprot0.0("\u35cb\u71c4\u0e9e\ua7e1"), Qprot0.0("\u35c4\u71ca\u0e99\ua7ef\u6732\ub04e\u8c20\u59b6\u570c\ucc7b"), Qprot0.0("\u35c4\u71ca\u0e99\ua7ef\u6732\ub04e\u8c20\u59b6\u570c\ucc7b"), Qprot0.0("\u35c5\u71c4\u0e97\ua7f4\u6734\ub05f\u8c3b"), Qprot0.0("\u35c8\u71c4\u0eda\ua7c6\u6734\ub05f\u8c24\u59a4\u5710\ucc70\u3a64\uaf02\ub77c"));
        Nigga.spacing = new NumberSetting(Qprot0.0("\u35d5\u71db\u0e9b\ua7e7\u673c\ub052\u8c28"), 5.0, 0.0, 20.0, 0.0);
        Nigga.defaultFont = Nigga.mc.fontRendererObj;
        Nigga.addSettings(Nigga.displayMode, Nigga.colorRed, Nigga.colorGreen, Nigga.colorBlue, Nigga.saturation, Nigga.fps, Nigga.niceFPS, Nigga.speed, Nigga.clock, Nigga.coords, Nigga.rgbSpeed, Nigga.rainbowOffset, Nigga.rgbEnabled, Nigga.color, Nigga.sides, Nigga.infoSetting, Nigga.dynamicScoreboard, Nigga.fontSetting, Nigga.rgbOutline, Nigga.noDepthOutline, Nigga.animSpeed, Nigga.spacing);
        Nigga.toggled = true;
    }

    public static {
        throw throwable;
    }

    @Override
    public void onDisable() {
        Client.showHud = false;
    }

    @Override
    public void onEvent(Event Nigga) {
        HUDModule Nigga2;
        if (Nigga2.fps.isEnabled() && !Client.showFPS) {
            Client.showFPS = true;
        }
        if (!Nigga2.fps.isEnabled()) {
            Client.showFPS = false;
        }
        if (Nigga2.speed.isEnabled() && !Client.showSpeed) {
            Client.showSpeed = true;
        }
        if (!Nigga2.speed.isEnabled()) {
            Client.showSpeed = false;
        }
        if (Nigga2.rgbEnabled.isEnabled() && !Client.rgbEnabled) {
            Client.rgbEnabled = true;
        }
        if (!Nigga2.rgbEnabled.isEnabled()) {
            Client.rgbEnabled = false;
        }
        if ((double)Client.rgbSpeed != Nigga2.rgbSpeed.getValue()) {
            Client.rgbSpeed = (int)Nigga2.rgbSpeed.getValue();
        }
    }

    @Override
    public void onEnable() {
        Client.showHud = true;
    }
}

