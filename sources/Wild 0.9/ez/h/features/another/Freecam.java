package ez.h.features.another;

import ez.h.features.*;
import java.awt.*;
import ez.h.ui.clickgui.options.*;
import ez.h.ui.fonts.*;
import ez.h.event.*;
import java.util.*;
import com.mojang.authlib.*;
import ez.h.event.events.*;
import ez.h.utils.*;

public class Freecam extends Feature
{
    double y;
    OptionBoolean disableOnDamage;
    double z;
    OptionColor offsetColor;
    bue freecamEntity;
    double x;
    OptionBoolean showOffset;
    ams oldGameType;
    OptionSlider speed;
    
    public Freecam() {
        super("Freecam", "\u0420\u0435\u0436\u0438\u043c \u0441\u0432\u043e\u0431\u043e\u0434\u043d\u043e\u0433\u043e \u043f\u043e\u043b\u0451\u0442\u0430 \u043a\u0430\u043c\u0435\u0440\u044b.", Category.ANOTHER);
        this.speed = new OptionSlider(this, "Speed", 0.4f, 0.1f, 3.0f, OptionSlider.SliderType.BPS);
        this.disableOnDamage = new OptionBoolean(this, "Disable On Damage", true);
        this.showOffset = new OptionBoolean(this, "Show Offset", true);
        this.offsetColor = new OptionColor(this, "Offset Color", new Color(-1), true);
        this.addOptions(this.speed, this.disableOnDamage, this.showOffset, this.offsetColor);
    }
    
    @EventTarget
    public void onRender(final EventRender2D eventRender2D) {
        if (!this.showOffset.enabled) {
            return;
        }
        CFontManager.manropesmall.drawCenteredString("X " + (int)(Freecam.mc.h.p - this.freecamEntity.p) + " Y " + (int)(Freecam.mc.h.q - this.freecamEntity.q) + " Z " + (int)(Freecam.mc.h.r - this.freecamEntity.r), eventRender2D.width / 2.0f, eventRender2D.height / 2.0f + 20.0f, this.offsetColor.getColor().getRGB());
    }
    
    @Override
    public void onDisable() {
        if (Freecam.mc.h == null) {
            return;
        }
        if (this.freecamEntity != null) {
            Freecam.mc.h.a(this.freecamEntity.p, this.freecamEntity.q, this.freecamEntity.r, this.freecamEntity.v, this.freecamEntity.w);
            Freecam.mc.f.e(this.freecamEntity.S());
        }
        if (Freecam.mc.v().a(Freecam.mc.af.c()) != null && Freecam.mc.v() != null) {
            Freecam.mc.v().a(Freecam.mc.af.c()).c = this.oldGameType;
        }
        Freecam.mc.h.setMotion(0.0f);
        Freecam.mc.h.t = 0.0;
        Freecam.mc.h.q = this.y;
        Freecam.mc.g.a();
        Freecam.mc.h.Q = false;
        super.onDisable();
    }
    
    @Override
    public void onEnable() {
        if (Freecam.mc.h == null || Freecam.mc.f == null) {
            return;
        }
        this.oldGameType = Freecam.mc.c.l();
        if (Freecam.mc.v().a(Freecam.mc.af.c()) != null) {
            Freecam.mc.v().a(Freecam.mc.af.c()).c = ams.e;
        }
        this.x = Freecam.mc.h.p;
        this.y = Freecam.mc.h.q;
        this.z = Freecam.mc.h.r;
        this.freecamEntity = new bue((amu)Freecam.mc.f, new GameProfile(new UUID(69L, 96L), Freecam.mc.K().c()));
        this.freecamEntity.bv = Freecam.mc.h.bv;
        this.freecamEntity.bx = Freecam.mc.h.bx;
        this.freecamEntity.a(Freecam.mc.h.p, Freecam.mc.h.q, Freecam.mc.h.r, Freecam.mc.h.v, Freecam.mc.h.w);
        this.freecamEntity.a(ams.d);
        this.freecamEntity.aP = Freecam.mc.h.aP;
        Freecam.mc.f.a(this.freecamEntity.S(), (vg)this.freecamEntity);
        Freecam.mc.g.a();
        super.onEnable();
    }
    
    @EventTarget
    public void onPacketSent(final EventPacketSend eventPacketSend) {
        this.setSuffix(this.speed.getNum() + "");
        eventPacketSend.setCancelled(!(eventPacketSend.getPacket() instanceof lj) && !(eventPacketSend.getPacket() instanceof li) && !(eventPacketSend.getPacket() instanceof jd) && !(eventPacketSend.getPacket() instanceof la) && !(eventPacketSend.getPacket() instanceof mb) && !(eventPacketSend.getPacket() instanceof ma));
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (this.disableOnDamage.enabled && Freecam.mc.h.ay > 0) {
            this.toggle();
        }
        Freecam.mc.h.t = 0.0;
        Freecam.mc.h.setMotion(0.0f);
        final float num = this.speed.getNum();
        Freecam.mc.h.Q = true;
        if (Freecam.mc.h.isMoving()) {
            Utils.setMotion(num);
        }
        if (Freecam.mc.t.Y.e()) {
            Freecam.mc.h.t = -num;
        }
        if (Freecam.mc.t.X.e()) {
            Freecam.mc.h.t = num;
        }
    }
    
    @Override
    public void updateElements() {
        this.offsetColor.display = this.showOffset.enabled;
        super.updateElements();
    }
}
