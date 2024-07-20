/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import ru.govno.client.event.Event;

public class EventCollectItem
extends Event {
    EntityItem entityItem;
    EntityLivingBase whoPicked;

    public EventCollectItem(EntityItem entityItem, EntityLivingBase whoPicked) {
        this.entityItem = entityItem;
        this.whoPicked = whoPicked;
    }

    public EntityItem getPickedEntityItem() {
        return this.entityItem;
    }

    public Entity getWhoPickItem() {
        return this.whoPicked;
    }

    public Vec3d getPickedVector() {
        return this.getPickedEntityItem().getPositionVector();
    }

    public ItemStack getPickedStack() {
        return this.getPickedEntityItem().getItem();
    }

    public boolean pickedStackIsEmpty() {
        return this.getPickedStack() == null || this.getPickedStack().func_190926_b();
    }

    public Item getPickedItem() {
        return this.getPickedStack().getItem();
    }
}

