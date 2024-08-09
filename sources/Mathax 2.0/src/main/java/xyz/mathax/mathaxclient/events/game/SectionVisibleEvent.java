package xyz.mathax.mathaxclient.events.game;

import net.minecraft.item.ItemStack;

public class SectionVisibleEvent {
    private static final SectionVisibleEvent INSTANCE = new SectionVisibleEvent();

    public ItemStack.TooltipSection section;

    public boolean visible;

    public static SectionVisibleEvent get(ItemStack.TooltipSection section, boolean visible) {
        INSTANCE.section = section;
        INSTANCE.visible = visible;
        return INSTANCE;
    }
}