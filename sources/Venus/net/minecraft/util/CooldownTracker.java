/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import mpp.venusfr.events.EventCalculateCooldown;
import mpp.venusfr.events.EventCooldown;
import mpp.venusfr.venusfr;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;

public class CooldownTracker {
    private final Map<Item, Cooldown> cooldowns = Maps.newHashMap();
    private int ticks;
    EventCooldown eventCooldown = new EventCooldown(null, null);

    public boolean hasCooldown(Item item) {
        return this.getCooldown(item, 0.0f) > 0.0f;
    }

    public float getCooldown(Item item, float f) {
        Cooldown cooldown = this.cooldowns.get(item);
        EventCalculateCooldown eventCalculateCooldown = new EventCalculateCooldown(item);
        venusfr.getInstance().getEventBus().post(eventCalculateCooldown);
        if (eventCalculateCooldown.getCooldown() != 0.0f) {
            return eventCalculateCooldown.getCooldown();
        }
        if (cooldown != null) {
            float f2 = cooldown.expireTicks - cooldown.createTicks;
            float f3 = (float)cooldown.expireTicks - ((float)this.ticks + f);
            return MathHelper.clamp(f3 / f2, 0.0f, 1.0f);
        }
        return 0.0f;
    }

    public void tick() {
        ++this.ticks;
        if (!this.cooldowns.isEmpty()) {
            Iterator<Map.Entry<Item, Cooldown>> iterator2 = this.cooldowns.entrySet().iterator();
            while (iterator2.hasNext()) {
                Map.Entry<Item, Cooldown> entry = iterator2.next();
                if (entry.getValue().expireTicks > this.ticks) continue;
                this.eventCooldown.setItem(entry.getKey());
                this.eventCooldown.setCooldownType(EventCooldown.CooldownType.REMOVED);
                venusfr.getInstance().getEventBus().post(this.eventCooldown);
                iterator2.remove();
                this.notifyOnRemove(entry.getKey());
            }
        }
    }

    public void setCooldown(Item item, int n) {
        this.eventCooldown.setItem(item);
        this.eventCooldown.setCooldownType(EventCooldown.CooldownType.ADDED);
        venusfr.getInstance().getEventBus().post(this.eventCooldown);
        this.cooldowns.put(item, new Cooldown(this, this.ticks, this.ticks + n));
        this.notifyOnSet(item, n);
    }

    public void removeCooldown(Item item) {
        this.eventCooldown.setItem(item);
        this.eventCooldown.setCooldownType(EventCooldown.CooldownType.REMOVED);
        venusfr.getInstance().getEventBus().post(this.eventCooldown);
        this.cooldowns.remove(item);
        this.notifyOnRemove(item);
    }

    protected void notifyOnSet(Item item, int n) {
    }

    protected void notifyOnRemove(Item item) {
    }

    class Cooldown {
        private final int createTicks;
        private final int expireTicks;
        final CooldownTracker this$0;

        private Cooldown(CooldownTracker cooldownTracker, int n, int n2) {
            this.this$0 = cooldownTracker;
            this.createTicks = n;
            this.expireTicks = n2;
        }
    }
}

