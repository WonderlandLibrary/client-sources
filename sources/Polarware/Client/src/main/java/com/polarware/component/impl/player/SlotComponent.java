package com.polarware.component.impl.player;

import com.polarware.Client;
import com.polarware.component.Component;
import com.polarware.event.bus.Listener;
import com.polarware.event.Priority;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.inventory.SyncCurrentItemEvent;
import com.polarware.event.impl.motion.PreUpdateEvent;
import com.polarware.event.impl.render.Render2DEvent;
import com.polarware.util.animation.Animation;
import com.polarware.util.animation.Easing;
import com.polarware.util.font.Font;
import com.polarware.util.font.FontManager;
import com.polarware.util.render.ColorUtil;
import com.polarware.util.render.RenderUtil;
import com.polarware.util.vector.Vector2d;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import java.awt.*;

public final class SlotComponent extends Component {

    private static final Animation animation = new Animation(Easing.EASE_OUT_ELASTIC, 250);
    private static boolean render;

    private final Font productSansLight = FontManager.getProductSansLight(18);
    private final Font productSansMedium = FontManager.getProductSansMedium(18);

    public static void setSlot(final int slot, final boolean render) {
        if (slot < 0 || slot > 8) {
            return;
        }

        mc.thePlayer.inventory.alternativeCurrentItem = slot;
        mc.thePlayer.inventory.alternativeSlot = true;
        SlotComponent.render = render;
    }

    public static void setSlot(final int slot) {
        setSlot(slot, true);
    }

    @EventLink(value = Priority.VERY_HIGH)
    public final Listener<Render2DEvent> onRender2D = event -> {

        final ScaledResolution scaledResolution = event.getScaledResolution();
        final double destinationY = render && mc.thePlayer.inventory.alternativeSlot &&
                (mc.thePlayer.inventory.alternativeCurrentItem != mc.thePlayer.inventory.currentItem || getItemStack() == null || getItemStack().getItem() instanceof ItemBlock) &&
                mc.currentScreen == null ? scaledResolution.getScaledHeight() - 90 : scaledResolution.getScaledHeight();
        animation.run(destinationY);
        animation.setDuration(2500);

        if (!render && animation.isFinished()) {
            return;
        }

        final ItemStack itemStack = getItemStack();

        if (itemStack != null) {
            final String stackSize = String.valueOf(itemStack.stackSize);
            final Item item = itemStack.getItem();

            final int halfWidth = scaledResolution.getScaledWidth() / 2;
            final double y = animation.getValue();

            // TODO: Convert some of those to constants
            final int edgeOffset = 3;
            final double width = 16 + edgeOffset * 2 + (item instanceof ItemBlock ? productSansMedium.width(stackSize) + 2 + edgeOffset : 0);
            final double height = 22;
            final double x = halfWidth - width / 2.0F;
            final double fontY = y + height / 2.0F - this.productSansLight.height() / 2.0F + 3;
            final int alpha = MathHelper.clamp_int((int) (255 * (float) (scaledResolution.getScaledHeight() - animation.getValue()) / 90.0F), 0, 255);

            if (alpha > 0) {
                NORMAL_RENDER_RUNNABLES.add(() -> {
                    RenderUtil.rectangle(x, y, width, height, getTheme().getBackgroundShade());

                    RenderUtil.renderItemIcon(x + edgeOffset, y + edgeOffset, itemStack);

                    if (item instanceof ItemBlock) {
                        float charX = (float) x + edgeOffset * 2.0F + 16.0F;

                        for (char i : stackSize.toCharArray()) {
                            String string = String.valueOf(i);

                            this.productSansMedium.drawString(
                                    string,
                                    charX, fontY,
                                    this.getTheme().getAccentColor(new Vector2d(charX * 32, fontY)).getRGB()
                            );

                            charX += this.productSansMedium.width(string) + 0.15F;
                        }
                    }
                });

                NORMAL_BLUR_RUNNABLES.add(() -> RenderUtil.rectangle(x, y, width, height, ColorUtil.withAlpha(Color.BLACK, alpha)));
                NORMAL_POST_BLOOM_RUNNABLES.add(() -> RenderUtil.rectangle(x, y, width, height, getTheme().getDropShadow()));
            }
        }
    };

    @EventLink(value = Priority.VERY_HIGH)
    public final Listener<SyncCurrentItemEvent> onSyncItem = event -> {
        final InventoryPlayer inventoryPlayer = mc.thePlayer.inventory;

        event.setSlot(inventoryPlayer.alternativeSlot ? inventoryPlayer.alternativeCurrentItem : inventoryPlayer.currentItem);
    };

    @EventLink(value = Priority.VERY_HIGH)
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        mc.thePlayer.inventory.alternativeSlot = false;
    };

    public static ItemStack getItemStack() {
        return (mc.thePlayer == null || mc.thePlayer.inventoryContainer == null ? null : mc.thePlayer.inventoryContainer.getSlot(getItemIndex() + 36).getStack());
    }

    public static int getItemIndex() {
        final InventoryPlayer inventoryPlayer = mc.thePlayer.inventory;
        return inventoryPlayer.alternativeSlot || !animation.isFinished() ? inventoryPlayer.alternativeCurrentItem : inventoryPlayer.currentItem;
    }
}