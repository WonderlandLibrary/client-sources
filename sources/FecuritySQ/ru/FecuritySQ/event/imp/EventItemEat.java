package ru.FecuritySQ.event.imp;

import net.minecraft.item.ItemStack;
import ru.FecuritySQ.event.Event;

public class EventItemEat extends Event {

    public ItemStack stack;

    public EventItemEat(ItemStack stack){
        this.stack = stack;
    }
}
