/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.events;

import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.Event;

public class EventCalculateCooldown
extends Event {
    public Item itemStack;
    public float cooldown;

    public EventCalculateCooldown(Item item) {
        this.itemStack = item;
    }

    public Item getItemStack() {
        return this.itemStack;
    }

    public float getCooldown() {
        return this.cooldown;
    }

    public void setItemStack(Item item) {
        this.itemStack = item;
    }

    public void setCooldown(float f) {
        this.cooldown = f;
    }

    public String toString() {
        return "EventCalculateCooldown(itemStack=" + this.getItemStack() + ", cooldown=" + this.getCooldown() + ")";
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof EventCalculateCooldown)) {
            return true;
        }
        EventCalculateCooldown eventCalculateCooldown = (EventCalculateCooldown)object;
        if (!eventCalculateCooldown.canEqual(this)) {
            return true;
        }
        if (!super.equals(object)) {
            return true;
        }
        if (Float.compare(this.getCooldown(), eventCalculateCooldown.getCooldown()) != 0) {
            return true;
        }
        Item item = this.getItemStack();
        Item item2 = eventCalculateCooldown.getItemStack();
        return item == null ? item2 != null : !item.equals(item2);
    }

    protected boolean canEqual(Object object) {
        return object instanceof EventCalculateCooldown;
    }

    public int hashCode() {
        int n = 59;
        int n2 = super.hashCode();
        n2 = n2 * 59 + Float.floatToIntBits(this.getCooldown());
        Item item = this.getItemStack();
        n2 = n2 * 59 + (item == null ? 43 : item.hashCode());
        return n2;
    }
}

