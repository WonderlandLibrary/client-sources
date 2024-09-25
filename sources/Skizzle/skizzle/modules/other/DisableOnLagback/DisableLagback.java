/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.other.DisableOnLagback;

import java.util.ArrayList;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventPacket;
import skizzle.modules.Module;
import skizzle.modules.other.DisableOnLagback.EnableModule;
import skizzle.settings.BooleanSetting;
import skizzle.settings.NumberSetting;
import skizzle.ui.notifications.Notification;
import skizzle.util.Timer;

public class DisableLagback
extends Module {
    public Timer enableTimer;
    public Timer resetTimer = new Timer();
    public NumberSetting packetsStting;
    public BooleanSetting velocity;
    public BooleanSetting noFall;
    public BooleanSetting killaura;
    public Timer timer = new Timer();
    public BooleanSetting reEnable;
    public BooleanSetting speed;
    public NumberSetting reEnableTime;
    public BooleanSetting fastEat;
    public ArrayList<EnableModule> modules = new ArrayList();
    public BooleanSetting fastSneak;
    public int packets;
    public BooleanSetting fly;

    public static {
        throw throwable;
    }

    public DisableLagback() {
        super(Qprot0.0("\u8e10\u71c2\ub55b\ua7e5\ua1c9\u0b82\u8c2a\ue25e\u570c\u0aad\u81a2\uaf0b\u0ca8\u724c\ud5f1\u123a"), 0, Module.Category.OTHER);
        DisableLagback Nigga;
        Nigga.enableTimer = new Timer();
        Nigga.speed = new BooleanSetting(Qprot0.0("\u8e07\u71db\ub54d\ua7e1\ua1cf"), true);
        Nigga.velocity = new BooleanSetting(Qprot0.0("\u8e02\u71ce\ub544\ua7eb\ua1c8\u0b87\u8c3b\ue268"), false);
        Nigga.fly = new BooleanSetting(Qprot0.0("\u8e12\u71c7\ub551"), true);
        Nigga.fastSneak = new BooleanSetting(Qprot0.0("\u8e12\u71ca\ub55b\ua7f0\ua1f8\u0b80\u8c2a\ue270\u5709"), true);
        Nigga.killaura = new BooleanSetting(Qprot0.0("\u8e1f\u71c2\ub544\ua7e8\ua1ca\u0b9b\u8c3d\ue270"), true);
        Nigga.noFall = new BooleanSetting(Qprot0.0("\u8e1a\u71c4\ub56e\ua7e5\ua1c7\u0b82"), true);
        Nigga.fastEat = new BooleanSetting(Qprot0.0("\u8e12\u71ca\ub55b\ua7f0\ua1ee\u0b8f\u8c3b"), true);
        Nigga.packetsStting = new NumberSetting(Qprot0.0("\u8e04\u71ca\ub54b\ua7ef\ua1ce\u0b9a\u8c3c"), 3.0, 1.0, 10.0, 1.0);
        Nigga.reEnable = new BooleanSetting(Qprot0.0("\u8e06\u71ce\ub505\ua7e1\ua1c5\u0b8f\u8c2d\ue27d\u5707"), true);
        Nigga.reEnableTime = new NumberSetting(Qprot0.0("\u8e06\u71ce\ub505\ua7e1\ua1c5\u0b8f\u8c2d\ue27d\u5707\u0ac1\u81a7\uaf09\u0ca6\u724c\ud5eb"), 6.0, 2.0, 20.0, 0.0);
        Nigga.addSettings(Nigga.speed, Nigga.velocity, Nigga.fly, Nigga.fastSneak, Nigga.killaura, Nigga.noFall, Nigga.fastEat, Nigga.packetsStting, Nigga.reEnable, Nigga.reEnableTime);
    }

    @Override
    public void onEnable() {
        Nigga.packets = 0;
    }

    @Override
    public void onEvent(Event Nigga) {
        DisableLagback Nigga2;
        if (Nigga instanceof EventPacket) {
            EventPacket eventPacket = (EventPacket)Nigga;
            if (eventPacket.getPacket() instanceof S08PacketPlayerPosLook) {
                ++Nigga2.packets;
            }
            if (Nigga2.resetTimer.hasTimeElapsed((long)-1191036711 ^ 0xFFFFFFFFB9023B31L, true)) {
                Nigga2.packets = 0;
            }
        }
        if ((double)Nigga2.packets >= Nigga2.packetsStting.getValue()) {
            if (Nigga2.timer.hasTimeElapsed((long)-1123343820 ^ 0xFFFFFFFFBD0B27E8L, true)) {
                Client.notifications.notifs.add(new Notification(Qprot0.0("\u8e18\u71ca\ub54f\ue26f\uea43\u0b8d\u8c24"), Qprot0.0("\u8e07\u71ce\ub55a\ue27b\uea47\u0b9c\u8c6f\ue27c\u1284\u411e\u81a6\uaf4c\u0cba\u37c5\u9e78\u123a\u42ec\u3847\u52c9\uf507\u77f7\u01de\u9e64\uade6\u2df1\u3336\u2f1a\u5b5b"), Float.intBitsToFloat(1.08310899E9f ^ 0x7F4EEE51), Float.intBitsToFloat(2.13177907E9f ^ 0x7F105E3D), Notification.notificationType.WARNING));
            }
            for (Module module : Client.modules) {
                if (!module.isEnabled() || !(module.name.equals(Qprot0.0("\u8e07\u71db\ub54d\ue268\uea46")) && Nigga2.speed.isEnabled() || module.name.equals(Qprot0.0("\u8e12\u71c7\ub551")) && Nigga2.fly.isEnabled() || module.name.equals(Qprot0.0("\u8e12\u71ca\ub55b\ue279\uea71\u0b80\u8c2a\ue270\u1280")) && Nigga2.fastSneak.isEnabled() || module.name.equals(Qprot0.0("\u8e02\u71ce\ub544\ue262\uea41\u0b87\u8c3b\ue268")) && Nigga2.velocity.isEnabled() || module.name.equals(Qprot0.0("\u8e1f\u71c2\ub544\ue261\uea43\u0b9b\u8c3d\ue270\u12e2")) && Nigga2.killaura.isEnabled() || module.name.equals(Qprot0.0("\u8e1a\u71c4\ub56e\ue26c\uea4e\u0b82")) && Nigga2.noFall.isEnabled()) && (!module.name.equals(Qprot0.0("\u8e12\u71ca\ub55b\ue279\uea67\u0b8f\u8c3b")) || Nigga2.fastEat.isEnabled())) continue;
                module.toggle();
                module.hasToggledDisabled = false;
                if (module.name.equals(Qprot0.0("\u8e12\u71c7\ub551"))) continue;
                try {
                    Nigga2.modules.add(new EnableModule(module));
                    Nigga2.enableTimer.reset();
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        if (Nigga2.enableTimer.hasTimeElapsed((long)(Nigga2.reEnableTime.getValue() * 1000.0), false) && Nigga2.reEnable.isEnabled()) {
            try {
                for (EnableModule enableModule : Nigga2.modules) {
                    if (enableModule.module.isEnabled() || !enableModule.timer.hasTimeElapsed((long)(Nigga2.reEnableTime.getValue() * 1000.0), false) || enableModule.module.hasToggledDisabled) continue;
                    enableModule.module.toggle();
                }
                Nigga2.modules = new ArrayList();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }
}

