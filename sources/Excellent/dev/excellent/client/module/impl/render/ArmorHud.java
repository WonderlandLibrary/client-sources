package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.value.impl.DragValue;
import net.minecraft.item.ItemStack;
import net.mojang.blaze3d.matrix.MatrixStack;
import net.mojang.blaze3d.systems.RenderSystem;
import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ModuleInfo(name = "Armor Hud", description = "Показывает информацию о броне.", category = Category.RENDER)
public class ArmorHud extends Module {
    private final DragValue drag = new DragValue("Position", this, new Vector2d(0, 100));
    private State state = State.VERTICAL;
    private Side side = Side.NONE;
    private final Listener<Render2DEvent> onRender = event -> {
        MatrixStack matrix = event.getMatrix();

        double margin = 2;

        double x, y, width, height;

        x = drag.position.x;
        y = drag.position.y;

        double itemSize = 16;

        List<ItemStack> items = new ArrayList<>(mc.player.inventory.armorInventory);
        items.removeIf(item -> item.equals(ItemStack.EMPTY));

        Collections.reverse(items);

        height = margin + itemSize + margin;

        width = margin + (items.size() * itemSize) + margin;

        drag.size.set(state.equals(State.HORIZONTAL) ? width : height, state.equals(State.HORIZONTAL) ? height : width);

        setSide(x, y, width, height, itemSize);
        if (items.isEmpty()) {
            return;
        }
        RenderUtil.renderClientRect(matrix, (float) x, (float) y, (float) drag.size.x, (float) drag.size.y, false, 0);


        double offset = 0;
        for (ItemStack stack : items) {
            RenderSystem.pushMatrix();
            float xItem = (float) (x + margin);
            float yItem = (float) (y + margin);
            if (state.equals(State.HORIZONTAL)) xItem += (float) offset;
            if (state.equals(State.VERTICAL)) yItem += (float) offset;
            mc.getItemRenderer().renderItemAndEffectIntoGUI(stack, xItem, yItem);
            mc.getItemRenderer().renderItemOverlayIntoGUI(mc.fontRenderer, stack, xItem, yItem, stack.getCount() == 1 ? null : stack.getCount() + "");
            RenderSystem.popMatrix();
            offset += itemSize;
        }
    };

    private void setSide(double x, double y, double width, double height, double itemSize) {
        if (state.equals(State.HORIZONTAL)) {
            if (!side.equals(Side.NONE)) state = State.VERTICAL;
            if (side.equals(Side.NONE) && isHover(x + itemSize, y, 0, 0, width + height, scaled().y))
                side = Side.LEFT;
            if (drag.isRender() && side.equals(Side.NONE) && isHover(x, y, scaled().x - (width + height), 0, (width + height), scaled().y))
                side = Side.RIGHT;
        }
        if (state.equals(State.VERTICAL)) {
            if (side.equals(Side.NONE)) state = State.HORIZONTAL;
            if (side.equals(Side.LEFT) && !isHover(x + itemSize, y, 0, 0, width + height, scaled().y))
                side = Side.NONE;
            if (drag.isRender() && side.equals(Side.RIGHT) && !isHover(x, y, scaled().x - (width + height), 0, (width + height), scaled().y))
                side = Side.NONE;
        }
    }

    private enum State {
        VERTICAL, HORIZONTAL
    }

    private enum Side {
        LEFT, RIGHT, NONE
    }


}
