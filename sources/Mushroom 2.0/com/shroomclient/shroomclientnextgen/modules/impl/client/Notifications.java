package com.shroomclient.shroomclientnextgen.modules.impl.client;

import com.shroomclient.shroomclientnextgen.annotations.AlwaysPost;
import com.shroomclient.shroomclientnextgen.config.*;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderScreenEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderTickEvent;
import com.shroomclient.shroomclientnextgen.events.impl.ScreenClickedEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import com.shroomclient.shroomclientnextgen.util.ThemeUtil;
import java.awt.*;
import java.util.ArrayList;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;

@RegisterModule(
    name = "Notifications",
    uniqueId = "notifications",
    description = "Notfications In Bottom Right",
    category = ModuleCategory.Client,
    enabledByDefault = true
)
public class Notifications extends Module {

    @ConfigMode
    @ConfigParentId("mode")
    @ConfigOption(name = "Mode", description = "", order = 0.9)
    public static Mode mode = Mode.Single;

    public enum Mode {
        Mushroom,
        Single,
        Compact,
    }

    private static final ArrayList<Notification> notifications =
        new ArrayList<>();

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Glow",
        description = "Puts A Glow Outline Around The Notification",
        order = 1
    )
    public static Boolean Glow = true;

    @ConfigHide
    @ConfigChild(value = "mode", parentEnumOrdinal = 1)
    @ConfigOption(
        name = "X Position",
        description = "Open Chat And Drag It.",
        min = 1,
        max = 1000,
        order = 101
    )
    public static Integer xPos = 100;

    @ConfigHide
    @ConfigChild(value = "mode", parentEnumOrdinal = 1)
    @ConfigOption(
        name = "Y Position",
        description = "Open Chat And Drag It.",
        min = 1,
        max = 1000,
        order = 102
    )
    public static Integer yPos = 100;

    String[] types = { "icon", "on", "off" };
    float popupSpeed = 100f;
    float popupTime = 3000f;

    public static void notify(String text, Color textColor, int type) {
        notifications.add(
            new Notification(text, textColor, System.currentTimeMillis(), type)
        );
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @SubscribeEvent
    @AlwaysPost
    public void onRender(RenderTickEvent e) {
        RenderUtil.setContext(e.drawContext);

        switch (mode) {
            case Mushroom -> {
                if (!C.isInGame()) return;
                if (!isEnabled()) {
                    notifications.clear();
                    return;
                }

                int i = 0;
                for (Notification noti : notifications) {
                    Window window = C.mc.getWindow();

                    long time = System.currentTimeMillis() - noti.time;

                    float width = RenderUtil.getFontWidth(noti.text) + 50;

                    if (time < popupSpeed) {
                        width *= (time / popupSpeed);
                    }
                    if (time > (popupTime - popupSpeed)) {
                        width *=
                        (1 - ((time - (popupTime - popupSpeed)) / popupSpeed));

                        if (time > popupTime) {
                            notifications.remove(i);
                            return;
                        }
                    }

                    if (!noti.text.startsWith("Click GUI")) {
                        // background
                        RenderUtil.drawRoundedRect2(
                            window.getScaledWidth() - width,
                            window.getScaledHeight() - 60 - (i * 70),
                            width,
                            50,
                            5,
                            new Color(27, 27, 27, 200),
                            false,
                            true,
                            false,
                            true
                        );
                        if (Glow) RenderUtil.drawRoundedGlow(
                            window.getScaledWidth() - width,
                            window.getScaledHeight() - 60 - (i * 70),
                            width,
                            50,
                            5,
                            5,
                            ThemeUtil.themeColors()[0],
                            false,
                            true,
                            false,
                            true
                        );

                        // logo n shi
                        RenderUtil.drawTexture(
                            types[noti.type] + ".png",
                            (int) (window.getScaledWidth() - width + 5),
                            window.getScaledHeight() - 60 - (i * 70) + 5,
                            20,
                            20
                        );

                        if (time < popupTime) {
                            float fadeEndingX =
                                window.getScaledWidth() -
                                width +
                                10 +
                                (width - 20) * (time / popupTime) -
                                10;
                            float fadeEndingW =
                                (width - 20) -
                                ((width - 20) * (time / popupTime) - 10);

                            RenderUtil.drawRoundedRect2(
                                window.getScaledWidth() - width + 10,
                                window.getScaledHeight() - 30 - (i * 70),
                                (width - 20) * (time / popupTime) - 10,
                                10,
                                5,
                                ThemeUtil.getGradient()[0],
                                false,
                                true,
                                false,
                                true
                            );
                            RenderUtil.drawRoundedRect2(
                                fadeEndingX,
                                window.getScaledHeight() - 30 - (i * 70),
                                (width - 20) -
                                ((width - 20) * (time / popupTime) - 10),
                                10,
                                5,
                                ThemeUtil.getGradient()[1],
                                true,
                                false,
                                true,
                                false
                            );

                            // not smart width thingy :(
                            int wid = 10;
                            while (
                                fadeEndingX + wid > window.getScaledWidth() - 15
                            ) wid -= 1;

                            // fade inbetween
                            RenderUtil.drawFade(
                                fadeEndingX,
                                window.getScaledHeight() - 30 - (i * 70),
                                wid,
                                10,
                                ThemeUtil.getGradient()[0],
                                ThemeUtil.getGradient()[1]
                            );
                            if (10 - wid >= 1) RenderUtil.drawRoundedRect2(
                                fadeEndingX,
                                window.getScaledHeight() - 30 - (i * 70),
                                10 - wid,
                                10,
                                5,
                                ThemeUtil.getGradient()[0],
                                true,
                                false,
                                true,
                                false
                            );
                        } else {
                            RenderUtil.drawRoundedRect2(
                                window.getScaledWidth() - width,
                                window.getScaledHeight() - 30 - (i * 70),
                                (width),
                                10,
                                5,
                                ThemeUtil.getGradient()[0],
                                false,
                                true,
                                false,
                                true
                            );
                        }

                        RenderUtil.drawTextShadow(
                            noti.text,
                            (int) (window.getScaledWidth() - width + 30),
                            window.getScaledHeight() - 50 - (i * 70),
                            noti.textColor
                        );

                        i++;
                    }
                }
            }
            case Single -> {
                if (!notifications.isEmpty()) {
                    Notification noti = notifications.get(
                        notifications.size() - 1
                    );

                    long time = System.currentTimeMillis() - noti.time;

                    if (
                        noti.text.equals("example notification") &&
                        C.mc.currentScreen == null
                    ) {
                        notifications.clear();
                        return;
                    }

                    if (time > (popupTime - popupSpeed)) {
                        if (time > popupTime) {
                            notifications.clear();
                            return;
                        }
                    }
                    if (!noti.text.startsWith("Click GUI")) {
                        // looks odd
                        int alpha = 155;

                        MatrixStack matrixStack = e.drawContext.getMatrices();

                        float xScale = 5;
                        float yScale = 5;

                        matrixStack.push();
                        matrixStack.translate(xPos, yPos, 0);

                        int notiImageBoxX = 5;
                        int notiImageBoxY = 5;

                        int notiImageBoxWidth = 20;
                        int notiImageBoxHeight = 20;
                        int notiImageBoxRadius = 5;

                        int notiImageOffset = 2;

                        float textScale = 1.2f;
                        float textScale2 = 1.2f;
                        int textOffsetX = 2;
                        int textX =
                            notiImageBoxX + notiImageBoxWidth + textOffsetX;

                        int widthWhole = (int) (textX +
                            RenderUtil.getFontWidth(noti.text) * textScale +
                            notiImageBoxX);
                        int heightWhole = 30;

                        float sizeMulti = (float) Math.sin(0.00105f * time);

                        if (
                            sizeMulti > 0.2 ||
                            noti.text.equals("example notification")
                        ) sizeMulti = 0.2f;

                        int radius = 5;

                        matrixStack.scale(sizeMulti, sizeMulti, 0);

                        matrixStack.translate((-widthWhole), 0, 0);

                        matrixStack.scale(xScale, yScale, 0);

                        int xCoord = -widthWhole;

                        // background
                        RenderUtil.drawRoundedGlow(
                            xCoord,
                            0,
                            widthWhole,
                            heightWhole,
                            radius,
                            5,
                            new Color(27, 27, 27),
                            alpha,
                            false,
                            false,
                            false,
                            false
                        );
                        RenderUtil.drawRoundedRect2(
                            xCoord,
                            0,
                            widthWhole,
                            heightWhole,
                            radius,
                            new Color(27, 27, 27, alpha),
                            false,
                            false,
                            false,
                            false
                        );

                        // noti type image thing (tick / cross)
                        //RenderUtil.drawRoundedRect2(notiImageBoxX, notiImageBoxY, notiImageBoxWidth, notiImageBoxHeight, notiImageBoxRadius, new Color(255, 255, 255, alpha), false, false, false, false);
                        RenderUtil.drawTexture(
                            types[noti.type] + ".png",
                            notiImageBoxX + notiImageOffset + xCoord,
                            notiImageBoxY + notiImageOffset,
                            notiImageBoxWidth - notiImageOffset * 2,
                            notiImageBoxHeight - notiImageOffset * 2
                        );

                        matrixStack.push();
                        matrixStack.translate(textX, 5, 0);
                        matrixStack.scale(textScale, textScale, 0);
                        RenderUtil.drawTextShadow(
                            "Mushroom",
                            (int) (xCoord / textScale),
                            0,
                            new Color(0x7DEEF3)
                        );
                        matrixStack.pop();

                        matrixStack.push();
                        matrixStack.translate(textX, 17, 0);
                        matrixStack.scale(textScale2, textScale2, 0);
                        RenderUtil.drawTextShadow(
                            noti.text,
                            (int) (xCoord / textScale2),
                            0,
                            noti.textColor
                        );
                        matrixStack.pop();

                        matrixStack.pop();

                        square = new float[] {
                            xPos - 28 + xCoord,
                            yPos,
                            widthWhole,
                            heightWhole,
                        };
                    }
                }
            }
            case Compact -> {
                if (!C.isInGame()) return;
                if (!isEnabled()) {
                    notifications.clear();
                    return;
                }

                int i = 0;
                for (Notification noti : notifications) {
                    RenderUtil.setContext(e.drawContext);

                    Window window = C.mc.getWindow();

                    long time = System.currentTimeMillis() - noti.time;

                    float width = RenderUtil.getFontWidth(noti.text) + 50;

                    if (time < popupSpeed) {
                        width *= (time / popupSpeed);
                    }
                    if (time > (popupTime - popupSpeed)) {
                        width *=
                        (1 - ((time - (popupTime - popupSpeed)) / popupSpeed));

                        if (time > popupTime) {
                            notifications.remove(i);
                            return;
                        }
                    }

                    if (!noti.text.startsWith("Click GUI")) {
                        // background
                        RenderUtil.drawRect(
                            window.getScaledWidth() - width + 35,
                            window.getScaledHeight() - 40 - (i * 40),
                            width - 35,
                            30,
                            new Color(27, 27, 27, 200)
                        );

                        // logo n shi
                        //RenderUtil.drawTexture(types[noti.type] + ".png", (int) (window.getScaledWidth() - width + 5), window.getScaledHeight() - 40 - (i * 40) + 5, 15, 10);

                        if (time < popupTime) {
                            float fadeEndingX =
                                window.getScaledWidth() -
                                width +
                                10 +
                                (width - 20) * (time / popupTime) -
                                10;
                            float fadeEndingW =
                                (width - 20) -
                                ((width - 20) * (time / popupTime) - 10);

                            RenderUtil.drawRect(
                                window.getScaledWidth() - width + 10 + 25,
                                window.getScaledHeight() - 15 - (i * 40),
                                (width - 20) * (time / popupTime) - 10,
                                5,
                                ThemeUtil.getGradient()[0]
                            );
                            RenderUtil.drawRect(
                                fadeEndingX + 25,
                                window.getScaledHeight() - 15 - (i * 40),
                                (width - 20) -
                                ((width - 20) * (time / popupTime) - 10),
                                5,
                                ThemeUtil.getGradient()[1]
                            );

                            // not smart width thingy :(
                            int wid = 10;
                            while (
                                fadeEndingX + wid > window.getScaledWidth() - 15
                            ) wid -= 1;

                            // fade inbetween
                            RenderUtil.drawFade(
                                fadeEndingX + 25,
                                window.getScaledHeight() - 15 - (i * 40),
                                wid,
                                5,
                                ThemeUtil.getGradient()[0],
                                ThemeUtil.getGradient()[1]
                            );
                            if (10 - wid >= 1) RenderUtil.drawRoundedRect2(
                                fadeEndingX + 25,
                                window.getScaledHeight() - 15 - (i * 40),
                                10 - wid,
                                5,
                                5,
                                ThemeUtil.getGradient()[0],
                                true,
                                false,
                                true,
                                false
                            );
                        } else {
                            RenderUtil.drawRect(
                                window.getScaledWidth() - width + 25,
                                window.getScaledHeight() - 15 - (i * 40),
                                (width),
                                5,
                                ThemeUtil.getGradient()[0]
                            );
                        }

                        RenderUtil.drawTextShadow(
                            noti.text,
                            (int) (window.getScaledWidth() - width + 30 + 15),
                            window.getScaledHeight() - 30 - (i * 40),
                            noti.textColor
                        );

                        i++;
                    }
                }
            }
        }
    }

    private record Notification(
        String text,
        Color textColor,
        long time,
        int type
    ) {}

    boolean dragging = false;
    double mouseXold = 0;
    double mouseYold = 0;
    double oldHudX = 0;
    double oldHudY = 0;

    public static float[] square = new float[] { 0, 0, 0, 0 };

    @SubscribeEvent
    public void ChatEvent(ScreenClickedEvent e) {
        if (C.mc.currentScreen instanceof ChatScreen && mode == Mode.Single) {
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
        if (C.mc.currentScreen instanceof ChatScreen && mode == Mode.Single) {
            if (
                notifications.isEmpty() ||
                notifications.get(0).text.equals("example notification")
            ) Notifications.notify(
                "example notification",
                new Color(255, 147, 147),
                0
            );

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
