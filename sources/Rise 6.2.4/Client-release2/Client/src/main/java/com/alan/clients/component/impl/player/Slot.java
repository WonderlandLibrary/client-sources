package com.alan.clients.component.impl.player;

import com.alan.clients.component.Component;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.inventory.SyncCurrentItemEvent;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.animation.Easing;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.shader.impl.BloomShader;
import com.alan.clients.util.font.Font;


import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.awt.*;

import static com.alan.clients.font.Fonts.MAIN;
import static com.alan.clients.layer.Layers.*;

public final class Slot extends Component {

    // Fields for rendering and animations (from ModernInterface style)
    private static final Animation animation = new Animation(Easing.EASE_OUT_EXPO, 900);
    private static boolean render = true;
    public static boolean finished = true;
    private static int delayed;

    // Fonts similar to ModernInterface
    private final Font productSansLight = MAIN.get(18, Weight.LIGHT);
    private final Font productSansMedium = MAIN.get(18, Weight.MEDIUM);

    // Internal logic for alternative slot
    // alternativeSlot and alternativeCurrentItem should be fields in mc.thePlayer.inventory
    // as implemented previously in your codebase.
    // This logic mirrors your final snippet logic.

    /**
     * Sets the slot silently by using alternative slot logic.
     */
    public static void setSlot(final int slot) {
        setSlot(slot, true);
    }

    /**
     * Set the slot silently, optionally with rendering effects.
     */
    public static void setSlot(final int slot, final boolean renderEffect) {
        if (slot < 0 || slot >= 9) return;


        InventoryPlayer inventory = mc.thePlayer.inventory;
        inventory.alternativeCurrentItem = slot;
        inventory.alternativeSlot = true;
        render = renderEffect;
        finished = false;

        // Sync with server (as in ModernInterface's setSlot)
        mc.playerController.syncCurrentPlayItem();
    }

    /**
     * Delayed slot change logic. Similar approach as in your SlotComponent code,
     * now integrated here. Uses random checks as before.
     */
    public static void setSlotDelayed(final int slot, boolean force) {
        setSlotDelayed(slot, force, true);
    }

    public static void setSlotDelayed(final int slot, boolean force, boolean renderEffect) {
        InventoryPlayer inventory = mc.thePlayer.inventory;

        // Mimic delay logic
        if (Math.random() * Math.random() > 0.25 || force) {
            setSlot(mc.playerController.currentPlayerItem, renderEffect);
        } else {
            setSlot(slot, renderEffect);
        }
    }

    /**
     * On sync event, tell the server which slot is being used.
     */
    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<SyncCurrentItemEvent> onSyncItem = event -> {
        final InventoryPlayer inventoryPlayer = mc.thePlayer.inventory;
        event.setSlot(inventoryPlayer.alternativeSlot ? inventoryPlayer.alternativeCurrentItem : inventoryPlayer.currentItem);
    };

    /**
     * Each update, revert back to normal slot usage to keep client and server in sync.
     */
    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        final InventoryPlayer inventoryPlayer = mc.thePlayer.inventory;
        inventoryPlayer.alternativeSlot = false;
        inventoryPlayer.alternativeCurrentItem = inventoryPlayer.currentItem;
    };

    /**
     * OnRender2D event to draw the slot selection visually, similar to how ModernInterface draws HUD elements.
     */
    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<Render2DEvent> onRender2D = event -> {
        if (finished || mc.thePlayer == null || !render) {
            return;
        }

        final ScaledResolution scaledResolution = event.getScaledResolution();
        double scaleVal = animation.getValue();
        double opacity = 1 - 10 * Math.abs(1 - animation.getValue());

        // Current item stack from the selected (alternative) slot
        ItemStack stack = getItemStack();
        ItemStack itemStack = (stack != null && mc.thePlayer.inventory.alternativeSlot) ? stack : null;

        final boolean enabled = itemStack != null && itemStack.stackSize > 0 && mc.thePlayer.inventory.alternativeSlot &&
                (mc.thePlayer.inventory.alternativeCurrentItem != mc.thePlayer.inventory.currentItem ||
                        getItemStack() == null ||
                        (getItemStack() != null && getItemStack().getItem() instanceof ItemBlock)) &&
                mc.currentScreen == null;

        animation.run(!enabled ? 1.1 : 1);
        animation.setDuration(900);
        animation.setEasing(Easing.EASE_OUT_EXPO);

        final String stackSizeStr = String.valueOf(itemStack == null ? 0 : itemStack.stackSize);
        final Item item = itemStack == null ? Items.egg : itemStack.getItem();

        final int halfWidth = scaledResolution.getScaledWidth() / 2;
        final double y = scaledResolution.getScaledHeight() - 90; // Adjust this y as needed
        final int edgeOffset = 3;
        final float amountWidth = productSansLight.width("Amount:") + 2;
        final double itemBoxWidth = 16 + edgeOffset * 2 + (item instanceof ItemBlock ? amountWidth + productSansMedium.width(stackSizeStr) + 2 + edgeOffset : 0);
        final double itemBoxHeight = 22;
        final double x = halfWidth - itemBoxWidth / 2.0F;
        final double fontY = y + itemBoxHeight / 2.0F - this.productSansLight.height() / 2.0F + 3;

        if (enabled) {
            // BLUR layer: Add a black blurred background shape
            getLayer(BLUR).add(() -> {
                GlStateManager.pushMatrix();
                GlStateManager.translate((x + itemBoxWidth * 0.5F) * (1 - scaleVal), (y + itemBoxHeight * 0.5F) * (1 - scaleVal), 0);
                GlStateManager.scale(scaleVal, scaleVal, scaleVal);
                RenderUtil.roundedRectangle(x, y, itemBoxWidth, itemBoxHeight, getTheme().getRound(), Color.BLACK);
                GlStateManager.popMatrix();
            });

            // REGULAR layer: Add the main background and text/icon
            getLayer(REGULAR, 1).add(() -> {
                GlStateManager.pushMatrix();
                GlStateManager.translate((x + itemBoxWidth * 0.5F) * (1 - scaleVal), (y + itemBoxHeight * 0.5F) * (1 - scaleVal), 0);
                GlStateManager.scale(scaleVal, scaleVal, scaleVal);

                // Draw main background
                RenderUtil.roundedRectangle(x, y, itemBoxWidth, itemBoxHeight, getTheme().getRound(),
                        ColorUtil.withAlpha(getTheme().getBackgroundShade(), (int) (getTheme().getBackgroundShade().getAlpha() * opacity)));

                // If item is a block, show "Amount:" text
                if (item instanceof ItemBlock) {
                    productSansLight.drawWithShadow("Amount:", x + edgeOffset * 2 + 16, fontY,
                            ColorUtil.withAlpha(Color.WHITE, (int) (255 * opacity)).getRGB());
                    productSansMedium.drawWithShadow(stackSizeStr, x + edgeOffset * 2 + 16 + amountWidth, fontY,
                            ColorUtil.withAlpha(getTheme().getFirstColor(), (int) (255 * opacity)).getRGB());
                }

                // Draw the item iconw
                RenderUtil.renderItemIcon(x + edgeOffset, y + edgeOffset,
                        ColorUtil.withAlpha(Color.white, Math.min(255, Math.max((int) (255 * opacity), 0))).getRGB(),
                        itemStack);

                GlStateManager.popMatrix();
            });

            // BLOOM layer: Add a bloom effect if animation not finished
            if (!animation.isFinished()) {
              //  BloomShader.setNextRender(20);
                getLayer(BLOOM).add(() -> {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate((x + itemBoxWidth * 0.5F) * (1 - scaleVal), (y + itemBoxHeight * 0.5F) * (1 - scaleVal), 0);
                    GlStateManager.scale(scaleVal, scaleVal, 0);

                    RenderUtil.roundedRectangle(x + 0.5F, y + 0.5F,
                            itemBoxWidth - 1, itemBoxHeight - 1,
                            getTheme().getRound() + 1,
                            ColorUtil.withAlpha(getTheme().getDropShadow(),
                                    (int) (getTheme().getDropShadow().getAlpha() * opacity)));

                    GlStateManager.popMatrix();
                });
            }
        }

        if (!enabled && animation.isFinished()) {
            finished = true;
        }
    };

    /**
     * Get the current item stack from the active slot.
     */
    public static ItemStack getItemStack() {
        return (mc.thePlayer == null || mc.thePlayer.inventoryContainer == null
                ? null
                : mc.thePlayer.inventoryContainer.getSlot(getItemIndex() + 36).getStack());
    }

    public static Item getItem() {
        ItemStack stack = getItemStack();
        return stack == null ? null : stack.getItem();
    }

    /**
     * Returns the currently active slot index (alternative if active, else current).
     */
    public static int getItemIndex() {
        final InventoryPlayer inventoryPlayer = mc.thePlayer.inventory;
        return inventoryPlayer.alternativeSlot ? inventoryPlayer.alternativeCurrentItem : inventoryPlayer.currentItem;
    }
}
