package ez.h.features.combat;

import java.util.stream.*;
import java.util.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.*;
import ez.h.event.events.*;
import ez.h.managers.*;

public class HitBox extends Feature
{
    OptionBoolean players;
    OptionSlider mobssize;
    OptionSlider playerssize;
    OptionBoolean mobs;
    OptionBoolean hidden;
    OptionSlider hitChance;
    
    @Override
    public void updateElements() {
        this.playerssize.display = this.players.enabled;
        this.mobssize.display = this.mobs.enabled;
        super.updateElements();
    }
    
    @Override
    public void onDisable() {
        final Iterator<aed> iterator = HitBox.mc.f.i.iterator();
        while (iterator.hasNext()) {
            this.setEntityBoundingBoxSize((vg)iterator.next());
        }
        for (final vg entityBoundingBoxSize : (List)HitBox.mc.f.e.stream().filter(vg -> vg instanceof ade).collect(Collectors.toList())) {
            if (entityBoundingBoxSize == null) {
                continue;
            }
            this.setEntityBoundingBoxSize(entityBoundingBoxSize);
        }
        super.onDisable();
    }
    
    void setEntityBoundingBoxSize(final vg vg) {
        final EntitySize entitySize = this.getEntitySize(vg);
        vg.G = entitySize.width;
        vg.H = entitySize.height;
        final double n = vg.G / 2.0;
        vg.a(new bhb(vg.p - n, vg.q, vg.r - n, vg.p + n, vg.q + vg.H, vg.r + n));
    }
    
    public HitBox() {
        super("HitBox", "\u0423\u0432\u0435\u043b\u0438\u0447\u0438\u0432\u0430\u0435\u0442 \u0445\u0438\u0442\u0431\u043e\u043a\u0441\u044b \u0441\u0443\u0449\u043d\u043e\u0441\u0442\u0435\u0439.", Category.COMBAT);
        this.hitChance = new OptionSlider(this, "Hit Chance", 100.0f, 0.0f, 100.0f, OptionSlider.SliderType.NULLINT);
        this.hidden = new OptionBoolean(this, "Hidden", false);
        this.playerssize = new OptionSlider(this, "Players Size", 0.2f, 0.01f, 3.0f, OptionSlider.SliderType.NULL);
        this.players = new OptionBoolean(this, "Players", true);
        this.mobs = new OptionBoolean(this, "Mobs", true);
        this.mobssize = new OptionSlider(this, "Mobs Size", 0.2f, 0.01f, 3.0f, OptionSlider.SliderType.NULL);
        this.addOptions(this.players, this.playerssize, this.mobs, this.mobssize, this.hitChance, this.hidden);
    }
    
    @EventTarget
    public void onAttack(final EventAttack eventAttack) {
        if (Math.random() * 100.0 > this.hitChance.getNum()) {
            eventAttack.setCancelled(true);
        }
    }
    
    EntitySize getEntitySize(final vg vg) {
        return new EntitySize(vg.G, vg.H);
    }
    
    void setEntityBoundingBoxSize(final vg vg, final float n, final float n2) {
        final EntitySize entitySize = this.getEntitySize(vg);
        vg.G = entitySize.width;
        vg.H = entitySize.height;
        final double n3 = n / 2.0;
        vg.a(new bhb(vg.p - n3, vg.q, vg.r - n3, vg.p + n3, vg.q + n2, vg.r + n3));
    }
    
    @EventTarget
    public void onUpdate(final EventMotion eventMotion) {
        this.setSuffix("Players: " + String.format("%.2f", this.playerssize.getNum()) + " Mobs: " + String.format("%.2f", this.mobssize.getNum()));
        if (this.players.enabled) {
            for (final aed aed : HitBox.mc.f.i) {
                if (aed != null && !aed.F) {
                    if (aed == HitBox.mc.h) {
                        continue;
                    }
                    if (FriendManager.isFriend(aed.h_())) {
                        continue;
                    }
                    this.setEntityBoundingBoxSize((vg)aed, this.getEntitySize((vg)aed).width + aed.H * this.playerssize.getNum(), this.getEntitySize((vg)aed).height + aed.G * this.playerssize.getNum());
                }
            }
        }
        if (this.mobs.enabled) {
            for (final vg vg : HitBox.mc.f.e) {
                if (vg != null && !vg.F) {
                    if (!(vg instanceof ade)) {
                        continue;
                    }
                    this.setEntityBoundingBoxSize(vg, this.getEntitySize(vg).width + vg.G * this.mobssize.getNum(), this.getEntitySize(vg).height + vg.H * this.mobssize.getNum());
                }
            }
        }
    }
    
    static class EntitySize
    {
        public float width;
        public float height;
        
        public EntitySize(final float width, final float height) {
            this.width = width;
            this.height = height;
        }
    }
}
