package ez.h.features.another;

import ez.h.event.events.*;
import java.nio.charset.*;
import java.util.*;
import ez.h.event.*;
import java.awt.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class AntiBot extends Feature
{
    public OptionBoolean remove;
    OptionMode mode;
    public static ArrayList<vg> bots;
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (!this.isEnabled() || AntiBot.mc.h == null) {
            return;
        }
        if (AntiBot.mc.f == null || AntiBot.mc.f.e == null || AntiBot.mc.f.e.size() == 0) {
            return;
        }
        if (this.mode.isMode("Matrix3")) {
            AntiBot.mc.f.i.forEach(bud -> {
                if (!((aed)bud).bm().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + ((aed)bud).h_()).getBytes(StandardCharsets.UTF_8))) && bud != AntiBot.mc.h) {
                    AntiBot.bots.add((vg)bud);
                    if (this.remove.isEnabled()) {
                        AntiBot.mc.f.e((vg)bud);
                    }
                }
                return;
            });
        }
        for (final vg vg : AntiBot.mc.f.e) {
            if (!(vg instanceof aed)) {
                continue;
            }
            if (vg == AntiBot.mc.h) {
                continue;
            }
            if (this.mode.isMode("Matrix") && vg.T <= (0x49 ^ 0x5D) && vg.s == 0.0 && vg.u == 0.0 && vg.m != vg.p && vg.o != vg.r && AntiBot.mc.h.getDistance(vg) <= 25.0f && AntiBot.mc.h.d.a(vg.bm()).c() != 0 && ((aed)vg).ay > 0) {
                AntiBot.bots.add(vg);
                if (this.remove.enabled) {
                    this.removeEntity(vg);
                }
            }
            if (!this.mode.isMode("Matrix2") || (vg.aY() != null && (!vg.aX() || vg.T > (0x65 ^ 0x76)))) {
                continue;
            }
            AntiBot.bots.add(vg);
            if (!this.remove.enabled) {
                continue;
            }
            this.removeEntity(vg);
        }
    }
    
    @Override
    public void onDisable() {
        AntiBot.bots.clear();
        super.onDisable();
    }
    
    void removeEntity(final vg vg) {
        if (!this.remove.enabled) {
            return;
        }
        Notifications.addNotification("Removed bot", vg.h_(), new Color(0x44 ^ 0x2, 6 + 3 + 136 + 55, 0x3D ^ 0xF), new Color(-1));
        AntiBot.mc.f.e(vg);
    }
    
    static {
        AntiBot.bots = new ArrayList<vg>();
    }
    
    public boolean isBot(final aed aed) {
        return AntiBot.bots.contains(aed);
    }
    
    public AntiBot() {
        super("AntiBot", "\u0423\u0431\u0438\u0440\u0430\u0435\u0442 \u0441\u0443\u0449\u043d\u043e\u0441\u0442\u0435\u0439 \u0431\u043e\u0442\u043e\u0432.", Category.ANOTHER);
        this.mode = new OptionMode(this, "Mode", "Matrix3", new String[] { "Matrix3", "Matrix2", "Matrix" }, 0);
        this.remove = new OptionBoolean(this, "Remove", true);
        this.addOptions(this.mode, this.remove);
    }
}
