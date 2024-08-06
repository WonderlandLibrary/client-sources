package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigHide;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderScreenEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderTickEvent;
import com.shroomclient.shroomclientnextgen.events.impl.ScreenClickedEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import java.awt.*;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.item.ItemStack;

@RegisterModule(
    name = "Inventory View",
    uniqueId = "inventoryrender",
    description = "Renders Your Inventory On Screen",
    category = ModuleCategory.Render
)
public class inventoryRender extends Module {

    @ConfigHide
    @ConfigOption(
        name = "X Position",
        description = "Open Chat And Drag It.",
        min = 1,
        max = 1000,
        order = 101
    )
    public static Integer xPos = 100;

    @ConfigHide
    @ConfigOption(
        name = "Y Position",
        description = "Open Chat And Drag It.",
        min = 1,
        max = 1000,
        order = 102
    )
    public static Integer yPos = 100;

    // why full caps..?
    // bc we need to yell at the users (i had caps lock on and i was lazy to re type)
    private static final int SLOT_SIZE = 16;
    private static final int PADDING = 2;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @ConfigParentId("backgroundinvrender")
    @ConfigOption(
        name = "Background",
        description = "Draws Background",
        order = 1
    )
    public static Boolean background = true;

    @ConfigChild("backgroundinvrender")
    @ConfigOption(
        name = "Shadow",
        description = "Draws Shadow To The Background",
        order = 2
    )
    public static Boolean shadow = true;

    @ConfigOption(
        name = "Show Hotbar",
        description = "Displays The Hotbar",
        order = 5
    )
    public static Boolean showhotbar = true;

    @ConfigOption(
        name = "Show Stack Size",
        description = "Displays The Stack Sizes",
        order = 6
    )
    public static Boolean showStackSize = true;

    @SubscribeEvent
    public void onRenderTick(RenderTickEvent event) {
        if (!C.isInGame() || C.p() == null) return;

        RenderUtil.setContext(event.drawContext);

        int xOffset = 1;
        int yOffset = 1;

        int totalRows = 4;
        int start = 0;
        if (!showhotbar) {
            totalRows = 3;
            start = 9;
        }

        square = new float[] {
            xPos - xOffset,
            yPos - yOffset,
            (SLOT_SIZE + PADDING) * 9 + (xOffset * 2),
            totalRows * (SLOT_SIZE + PADDING) + (yOffset * 2),
        };

        if (background) {
            RenderUtil.drawRoundedRect2(
                square[0],
                square[1],
                square[2],
                square[3],
                2,
                new Color(23, 23, 23, 100),
                false,
                false,
                false,
                false
            );

            if (shadow) RenderUtil.drawRoundedGlow(
                square[0],
                square[1],
                square[2],
                square[3],
                2,
                10,
                new Color(23, 23, 23),
                100,
                false,
                false,
                false,
                false
            );
        }

        for (int i = start; i < 36; i++) {
            int row = i % 9;
            int column = totalRows - (i / 9);

            if (i < 9) column = 0;

            int x = xPos + row * (SLOT_SIZE + PADDING);
            int y = yPos + ((totalRows - 1) - column) * (SLOT_SIZE + PADDING);

            ItemStack itemStack = C.p().getInventory().getStack(i);
            if (!itemStack.isEmpty()) {
                event.drawContext.getMatrices().push();
                if (showStackSize) event.drawContext
                    .getMatrices()
                    .translate(0, 0, -10000);
                RenderUtil.drawItem(itemStack, x, y);
                event.drawContext.getMatrices().pop();

                if (itemStack.getCount() > 1 && showStackSize) {
                    RenderUtil.drawTextShadow(
                        itemStack.getCount() + "",
                        (int) (x +
                            17 -
                            RenderUtil.getFontWidth(itemStack.getCount() + "")),
                        y + 9,
                        new Color(255, 255, 255)
                    );
                }
            }
        }
    }

    boolean dragging = false;
    double mouseXold = 0;
    double mouseYold = 0;
    double oldHudX = 0;
    double oldHudY = 0;

    public static float[] square = new float[] { 0, 0, 0, 0 };

    @SubscribeEvent
    public void ChatEvent(ScreenClickedEvent e) {
        if (C.mc.currentScreen instanceof ChatScreen) {
            if (
                e.mouseX >= square[0] &&
                e.mouseX <= square[0] + square[2] &&
                e.mouseY >= square[1] &&
                e.mouseY <= square[1] + square[3]
            ) {
                dragging = e.button == 0;
                mouseXold = e.mouseX;
                mouseYold = e.mouseY;

                oldHudX = xPos;
                oldHudY = yPos;
            }

            if (!e.down && e.button == 0) {
                dragging = false;
            }
        }
    }

    @SubscribeEvent
    public void onDrawScreen(RenderScreenEvent e) {
        if (C.mc.currentScreen instanceof ChatScreen) {
            double mouseMovedX = e.mouseX - mouseXold;
            double mouseMovedY = e.mouseY - mouseYold;

            if (dragging) {
                xPos = (int) (oldHudX + mouseMovedX);
                yPos = (int) (oldHudY + mouseMovedY);

                RenderUtil.drawRoundedGlow(
                    square[0],
                    square[1],
                    square[2],
                    square[3],
                    0,
                    5,
                    new Color(255, 255, 255),
                    200,
                    false,
                    false,
                    false,
                    false
                );
            }
        }
    }
}
