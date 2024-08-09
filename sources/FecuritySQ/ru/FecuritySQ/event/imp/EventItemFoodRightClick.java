package ru.FecuritySQ.event.imp;

import net.minecraft.item.ItemStack;
import ru.FecuritySQ.event.Event;

public class EventItemFoodRightClick extends Event {

    public ItemStack stack;

    public EventItemFoodRightClick(ItemStack stack){
        this.stack = stack;
    }

}
