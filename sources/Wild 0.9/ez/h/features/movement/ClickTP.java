package ez.h.features.movement;

import ez.h.features.*;
import java.awt.*;
import ez.h.ui.clickgui.options.*;
import ez.h.utils.*;
import ez.h.event.*;
import ez.h.event.events.*;
import java.util.*;
import org.lwjgl.input.*;

public class ClickTP extends Feature
{
    OptionColor color;
    et pos;
    OptionMode mode;
    OptionBoolean silentSneak;
    
    public ClickTP() {
        super("ClickTP", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c\u0441\u044f \u043f\u0440\u0438 \u043d\u0430\u0436\u0430\u0442\u0438\u0438 \u043d\u0430 \u041f\u041a\u041c \u043d\u0430 \u0431\u043b\u043e\u043a \u043d\u0430 \u043a\u043e\u0442\u043e\u0440\u044b\u0439 \u0432\u044b \u0441\u043c\u043e\u0442\u0440\u0438\u0442\u0435.", Category.MOVEMENT);
        this.mode = new OptionMode(this, "Mode", "Flag", new String[] { "Flag", "Teleport" }, 0);
        this.silentSneak = new OptionBoolean(this, "Silent Sneak", true);
        this.color = new OptionColor(this, "Color", new Color(0, 113 + 130 - 101 + 58, 57 + 156 - 21 + 8));
        this.addOptions(this.mode, this.silentSneak, this.color);
    }
    
    @EventTarget
    public void onRender(final EventRender3D eventRender3D) {
        if (this.pos == null || !this.isEnabled()) {
            return;
        }
        bus.G();
        RenderUtils.Boxes.drawBlockESP(this.pos, this.color.getColor().getRed() / 255.0f, this.color.getColor().getGreen() / 255.0f, this.color.getColor().getBlue() / 255.0f);
        bus.f();
        bus.H();
    }
    
    @Override
    public void onDisable() {
        this.pos = null;
        super.onDisable();
    }
    
    @EventTarget
    public void onEvent(final EventMotion eventMotion) {
        this.setSuffix(this.mode.getMode());
        this.pos = Objects.requireNonNull(ClickTP.mc.h.a(100.0, 1.0f)).a();
        if (ClickTP.mc.m != null || !this.isEnabled()) {
            return;
        }
        if (ClickTP.mc.t.ad.i && Mouse.isButtonDown(1)) {
            if (this.silentSneak.enabled) {
                ClickTP.mc.h.d.a((ht)new lq((vg)ClickTP.mc.h, lq.a.a));
            }
            if (this.mode.isMode("Flag")) {
                eventMotion.setX(this.pos.p() + 0.5f);
                eventMotion.setY(this.pos.q() + 1.0f);
                eventMotion.setZ(this.pos.r() + 0.5f);
            }
            if (this.mode.isMode("Teleport")) {
                ClickTP.mc.h.b((double)(this.pos.p() + 0.5f), (double)(this.pos.q() + 1.0f), (double)(this.pos.r() + 0.5f));
            }
            if (this.silentSneak.enabled) {
                ClickTP.mc.h.d.a((ht)new lq((vg)ClickTP.mc.h, lq.a.b));
            }
        }
    }
}
