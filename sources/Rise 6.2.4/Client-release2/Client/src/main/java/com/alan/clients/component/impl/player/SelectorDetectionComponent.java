package com.alan.clients.component.impl.player;

import com.alan.clients.component.Component;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public final class SelectorDetectionComponent extends Component {

    private static boolean selector;

    public static boolean selector() {
        return selector;
    }

    public static boolean selector(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        } else if (itemStack == mc.thePlayer.inventory.getItemStack()) {
            return selector();
        } else {
            return !trueName(itemStack).contains(itemStack.getDisplayName());
        }
    }

    public static boolean selector(ItemStack itemStack, boolean checkIfTrueName) {
        if (itemStack == null) {
            return false;
        } else if (itemStack == mc.thePlayer.inventory.getItemStack()) {
            return selector();
        } else {
            if (checkIfTrueName) {
                return !trueName(itemStack).contains(itemStack.getDisplayName());
            } else {
                return false;
            }
        }
    }

    public static boolean selector(int itemSlot) {
        return selector(mc.thePlayer.inventory.getStackInSlot(itemSlot));
    }

    public static boolean selector(int itemSlot, boolean checkIfTrueName) {
        return selector(mc.thePlayer.inventory.getStackInSlot(itemSlot), checkIfTrueName);
    }

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (getComponent(Slot.class).getItemStack() != null) {
            ItemStack itemStack = getComponent(Slot.class).getItemStack();

            selector = !trueName(itemStack).contains(itemStack.getDisplayName());
        } else {
            selector = false;
        }
    };

    public static String trueName(ItemStack itemStack) {
        String name = ("" + StatCollector.translateToLocal(itemStack.getUnlocalizedName() + ".name")).trim();
        final String s1 = EntityList.getStringFromID(itemStack.getMetadata());

        if (s1 != null) {
            name = name + " " + StatCollector.translateToLocal("entity." + s1 + ".name");
        }

        return name;
    }
}
