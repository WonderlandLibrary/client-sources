/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.events;

import net.minecraft.item.Item;

public class EventCooldown {
    private Item item;
    private CooldownType cooldownType;

    public boolean isAdded() {
        return this.cooldownType == CooldownType.ADDED;
    }

    public boolean isRemoved() {
        return this.cooldownType == CooldownType.REMOVED;
    }

    public EventCooldown(Item item, CooldownType cooldownType) {
        this.item = item;
        this.cooldownType = cooldownType;
    }

    public Item getItem() {
        return this.item;
    }

    public CooldownType getCooldownType() {
        return this.cooldownType;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setCooldownType(CooldownType cooldownType) {
        this.cooldownType = cooldownType;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof EventCooldown)) {
            return true;
        }
        EventCooldown eventCooldown = (EventCooldown)object;
        if (!eventCooldown.canEqual(this)) {
            return true;
        }
        Item item = this.getItem();
        Item item2 = eventCooldown.getItem();
        if (item == null ? item2 != null : !item.equals(item2)) {
            return true;
        }
        CooldownType cooldownType = this.getCooldownType();
        CooldownType cooldownType2 = eventCooldown.getCooldownType();
        return cooldownType == null ? cooldownType2 != null : !((Object)((Object)cooldownType)).equals((Object)cooldownType2);
    }

    protected boolean canEqual(Object object) {
        return object instanceof EventCooldown;
    }

    public int hashCode() {
        int n = 59;
        int n2 = 1;
        Item item = this.getItem();
        n2 = n2 * 59 + (item == null ? 43 : item.hashCode());
        CooldownType cooldownType = this.getCooldownType();
        n2 = n2 * 59 + (cooldownType == null ? 43 : ((Object)((Object)cooldownType)).hashCode());
        return n2;
    }

    public String toString() {
        return "EventCooldown(item=" + this.getItem() + ", cooldownType=" + this.getCooldownType() + ")";
    }

    public static enum CooldownType {
        ADDED,
        REMOVED;

    }
}

