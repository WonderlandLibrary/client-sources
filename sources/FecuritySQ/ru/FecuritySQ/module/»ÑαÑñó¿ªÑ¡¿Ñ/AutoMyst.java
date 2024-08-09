package ru.FecuritySQ.module.передвижение;

import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.*;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.utils.Counter;

public class AutoMyst extends Module {
    public Counter timer = new Counter();
    public AutoMyst() {
        super(Category.Передвижение, GLFW.GLFW_KEY_K);
        setEnabled(true);
    }
    public boolean isWhiteItem(ItemStack itemStack) {

        return (itemStack.getItem() instanceof ArmorItem || itemStack.getItem() instanceof EnderPearlItem || itemStack.getItem() instanceof SwordItem || itemStack.getItem() instanceof ToolItem ||  itemStack.getItem() instanceof PotionItem || itemStack.getItem() instanceof BlockItem || itemStack.getItem() instanceof ArrowItem || itemStack.getItem() instanceof CompassItem);

    }


    private boolean isEmpty(Container container) {

        for (int index = 0; index < container.inventorySlots.size(); index++) {

            if (isWhiteItem(container.getSlot(index).getStack()))

                return false;

        }

        return true;

    }


    @Override
    public void event(Event e) {
        if (e instanceof EventUpdate && isEnabled()) {
            float delay = 0;


            if (mc.player.openContainer instanceof ChestContainer) {

                ChestContainer container = (ChestContainer) mc.player.openContainer;

                for (int index = 0; index < container.inventorySlots.size(); ++index) {

                    if (container.getLowerChestInventory().getStackInSlot(index).getItem() != Item.getItemById(0)) {

                        mc.playerController.windowClick(container.windowId, index, 0, ClickType.QUICK_MOVE, mc.player);

                        timer.reset();

                        continue;

                    }
                }
            }
        }
    }
}
