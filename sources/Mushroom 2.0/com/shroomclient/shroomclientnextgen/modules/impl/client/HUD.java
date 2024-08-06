package com.shroomclient.shroomclientnextgen.modules.impl.client;

import com.shroomclient.shroomclientnextgen.ShroomClient;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.util.Identifier;

@RegisterModule(
    name = "HUD",
    uniqueId = "hud",
    description = "Shows Mushroom Watermark",
    category = ModuleCategory.Client,
    enabledByDefault = true
)
public class HUD extends Module {

    @ConfigMode
    @ConfigParentId("mode")
    @ConfigOption(
        name = "Mode",
        description = "What We Lookin Like?",
        order = 0.1
    )
    public Mode mode = Mode.Custom;

    @ConfigChild(value = "mode", parentEnumOrdinal = 3)
    @ConfigOption(name = "IntelliJ Colors", description = "", order = 0.2)
    public static ColorsIntelliJ IntellijColors = ColorsIntelliJ.Dark_Purple;

    @ConfigChild(value = "mode", parentEnumOrdinal = 3)
    @ConfigOption(
        name = "Shortened",
        description = "Shorter Text, Takes Up Less Space",
        order = 0.3
    )
    public static Boolean shortened = true;

    public enum Mode {
        Custom,
        Game_Sense,
        Logo,
        IntelliJ,
        Porn_Hub,
        Wurst,
    }

    @ConfigChild(value = "mode", parentEnumOrdinal = 4)
    @ConfigOption(
        name = "Theme Porn",
        description = "Makes The Word Porn Theme Color",
        order = 1.1
    )
    public static Boolean themPoern = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Box",
        description = "Draws A Box Behind The Watermark",
        order = 1
    )
    public static Boolean Box = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(name = "Shadow", description = "Cute Shadow", order = 1.1)
    public static Boolean Shadow = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Glow",
        description = "Puts A Glow Outline Around The Watermark",
        order = 2
    )
    public static Boolean Glow = false;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Underline",
        description = "Underlines The Watermark",
        order = 3
    )
    public static Boolean Underline = false;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Overline",
        description = "Underlines On Top I Think",
        order = 3.1
    )
    public static Boolean Overline = false;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(name = "Show FPS", description = "Shows fps", order = 4)
    public static Boolean fps = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(name = "Show Name", description = "Shows Name", order = 5)
    public static Boolean showName = false;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(name = "Show Time", description = "Shows Time...", order = 6)
    public static Boolean showTime = false;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(name = "Radius", description = "", max = 5, order = 7)
    public static Integer radius = 2;

    @ConfigChild(value = "mode", parentEnumOrdinal = 2)
    @ConfigOption(
        name = "Text",
        description = "Show The Mushroom Text",
        order = 7
    )
    public static Boolean showtext = false;

    @ConfigHide
    @ConfigOption(
        name = "X Position",
        description = "Open Chat And Drag It.",
        min = 1,
        max = 1000,
        order = 101
    )
    public static Integer xPos = 10;

    @ConfigHide
    @ConfigOption(
        name = "Y Position",
        description = "Open Chat And Drag It.",
        min = 1,
        max = 1000,
        order = 102
    )
    public static Integer yPos = 10;

    @SubscribeEvent
    public void onRender(RenderTickEvent e) {
        if (!C.isInGame()) return;

        RenderUtil.setContext(e.drawContext);
        RenderUtil.setMatrix(e.matrixStack);

        switch (mode) {
            case Custom -> {
                int width = (int) RenderUtil.getFontWidth("Mushroom ");

                int x = xPos;
                int y = yPos;

                LocalTime currentTime = LocalTime.now();
                String formattedTime = currentTime.format(
                    DateTimeFormatter.ofPattern("HH:mm:ss")
                );

                String text = ShroomClient.version;
                if (fps) text += " [" + C.mc.getCurrentFps() + " FPS]";
                if (showName) text += " | " + RenderUtil.getUnhiddenName();
                if (showTime) text += " | " + formattedTime;

                RenderUtil.setContext(e.drawContext);

                int height = RenderUtil.fontSize + 4;
                if (Underline) height += 2;
                if (Box) RenderUtil.drawRoundedRect2(
                    x - 4,
                    y - 3,
                    RenderUtil.getFontWidth(text) + width + 8,
                    height + 1,
                    radius,
                    new Color(12, 12, 12, 100),
                    false,
                    false,
                    false,
                    false
                );
                if (Shadow) RenderUtil.drawRoundedGlow(
                    x - 4,
                    y - 3,
                    RenderUtil.getFontWidth(text) + width + 8,
                    height + 1,
                    radius,
                    5,
                    new Color(12, 12, 12),
                    100,
                    false,
                    false,
                    false,
                    false
                );

                if (Glow) RenderUtil.drawRoundedGlow(
                    x - 4,
                    y - 3,
                    RenderUtil.getFontWidth(text) + width + 8,
                    height,
                    radius,
                    5,
                    ThemeUtil.themeColors()[0],
                    false,
                    false,
                    false,
                    false
                );
                if (Underline) RenderUtil.drawFade(
                    x,
                    RenderUtil.fontSize + y + 1,
                    RenderUtil.getFontWidth(text) + width,
                    1,
                    ThemeUtil.themeColors()[0],
                    ThemeUtil.themeColors()[1]
                );
                if (Overline) RenderUtil.drawFade(
                    x - 4,
                    y - 3,
                    RenderUtil.getFontWidth(text) + width + 8,
                    1,
                    ThemeUtil.themeColors()[0],
                    ThemeUtil.themeColors()[1]
                );

                RenderUtil.drawTextShadow(
                    text,
                    x + width,
                    y,
                    ThemeUtil.themeColors()[0]
                );

                RenderUtil.drawTextShadow(
                    "M",
                    x,
                    y + 1,
                    ThemeUtil.getGradient()[0]
                );
                RenderUtil.drawTextShadow(
                    "ushroom",
                    (int) (x + RenderUtil.getFontWidth("§lM")),
                    y + 1,
                    ThemeUtil.themeColors()[0]
                );

                square = new float[] {
                    xPos - 4,
                    yPos - 3,
                    RenderUtil.getFontWidth(text) + width + 8,
                    height + 1,
                };
            }
            case Logo -> {
                int logoW = (40);
                int logoH = (40);
                int x = xPos;
                int y = yPos;
                RenderUtil.drawTexture("icon.png", x, y + 1, logoW, logoH);
                if (showtext) RenderUtil.drawTexture(
                    "mushroomtext.png",
                    x + 42,
                    y + 13,
                    110,
                    18
                );

                square = new float[] { x, y, logoW, logoH };
            }
            case Porn_Hub -> {
                int x = xPos;
                int y = yPos;
                if (themPoern) RenderUtil.drawRoundedRectImage(
                    new Identifier("shroomclientnextgen", "porn.png"),
                    x,
                    y + 1,
                    0,
                    0,
                    120,
                    40,
                    0,
                    ThemeUtil.themeColors()[0]
                );
                else RenderUtil.drawTexture("porn.png", x, y + 1, 120, 40);

                RenderUtil.drawRoundedRectImage(
                    new Identifier("shroomclientnextgen", "hub.png"),
                    x,
                    y + 1,
                    0,
                    0,
                    120,
                    40,
                    0,
                    new Color(255, 255, 255)
                );

                square = new float[] { x, y, 120, 40 };
            }
            case Wurst -> {
                int logoW = (90);
                int logoH = (20);
                int x = xPos;
                int y = yPos;

                String wurstClientText =
                    "v" + ShroomClient.version + " MC1.20.4";
                RenderUtil.drawRect(
                    x,
                    y + 5,
                    logoW + RenderUtil.getFontWidth(wurstClientText) + 5,
                    11,
                    new Color(230, 230, 230, 120)
                );
                RenderUtil.drawText(
                    wurstClientText,
                    x + logoW + 1,
                    y + 7,
                    new Color(0, 0, 0)
                );

                RenderUtil.drawTexture("wurst.png", x, y + 1, logoW, logoH);

                square = new float[] {
                    x,
                    y,
                    logoW + RenderUtil.getFontWidth(wurstClientText) + 5,
                    logoH,
                };
            }
            case Game_Sense -> {
                int width = (int) RenderUtil.getFontWidth("Mushroom ");

                int x = xPos;
                int y = yPos;
                int verstextx = (int) (x +
                    RenderUtil.getFontWidth("Mushroom") +
                    RenderUtil.getFontWidth("Sense") +
                    5);

                String text = ShroomClient.version;

                String texter = "MushroomSense";

                RenderUtil.setContext(e.drawContext);

                String serverName = "singleplayer";
                if (
                    C.mc.getNetworkHandler() != null &&
                    C.mc.getNetworkHandler().getServerInfo() != null
                ) serverName = C.mc.getNetworkHandler().getServerInfo().address;

                float boxW =
                    RenderUtil.getFontWidth(texter) +
                    width +
                    24 +
                    RenderUtil.getFontWidth(" | " + serverName);

                int height = RenderUtil.fontSize + 6;
                RenderUtil.drawRect(
                    x - 8,
                    y - 6,
                    boxW + 6,
                    height + 7,
                    new Color(44, 44, 44, 184)
                );
                RenderUtil.drawRect(
                    x - 5,
                    y - 3,
                    boxW,
                    height + 1,
                    new Color(0, 0, 0, 178)
                );
                RenderUtil.drawFade(
                    x,
                    RenderUtil.fontSize + y + 1,
                    boxW - 10,
                    1,
                    ThemeUtil.themeColors()[0],
                    ThemeUtil.themeColors()[1]
                );

                RenderUtil.drawTextShadow("Mushroom", x, y + 1, Color.white);
                RenderUtil.drawTextShadow(
                    "Sense ",
                    (int) (x + RenderUtil.getFontWidth("Mushroom")),
                    y + 1,
                    ThemeUtil.themeColors()[0]
                );
                RenderUtil.drawTextShadow(
                    "| Public(v" + text + ")" + " | " + serverName,
                    verstextx,
                    y + 1,
                    Color.white
                );

                square = new float[] { xPos - 4, yPos - 3, boxW, height + 1 };
            }
            case IntelliJ -> {
                float height = 42;

                if (shortened) height = 28;

                float textSize = 1;

                String clientName = "Mushroom";

                String[] texts = {
                    "private Class client = new Client(String name, double version)",
                    "client.name = \"" + clientName + "\"",
                    "client.version = " + ShroomClient.version,
                };

                if (shortened) {
                    texts = new String[] {
                        "client.name = \"" + clientName + "\"",
                        "client.version = " + ShroomClient.version,
                    };
                }

                float width = RenderUtil.getFontWidth(texts[0]);
                for (String string : texts) {
                    width = Math.max(RenderUtil.getFontWidth(string), width);
                }

                width += 20;
                if (shortened) width -= 5;

                width *= textSize;

                RenderUtil.drawRoundedGlow(
                    xPos,
                    yPos,
                    width,
                    height,
                    0,
                    3,
                    new Color(60, 63, 65),
                    255,
                    false,
                    false,
                    false,
                    false
                );

                if (
                    IntellijColors == ColorsIntelliJ.Dark_Purple
                ) RenderUtil.drawRect(
                    xPos,
                    yPos,
                    width,
                    height,
                    new Color(29, 29, 38)
                );
                else RenderUtil.drawRect(
                    xPos,
                    yPos,
                    width,
                    height,
                    new Color(43, 43, 43)
                );

                int i = 0;
                int xCordBase = 5 + xPos;
                int yCordBase = 4 + yPos;
                int ySeperation = 13;

                for (String clientText : texts) {
                    String[] stringSplit = clientText.split(" ");

                    int lengthSoFar = 0;
                    for (String splitPart : stringSplit) {
                        Color textColor = new Color(200, 200, 200);

                        final boolean shouldBeOrange =
                            splitPart.startsWith("public") ||
                            splitPart.startsWith("private") ||
                            splitPart.startsWith("boolean") ||
                            splitPart.startsWith("double") ||
                            splitPart.startsWith("int") ||
                            splitPart.startsWith("float") ||
                            splitPart.startsWith("true") ||
                            splitPart.startsWith("false") ||
                            splitPart.startsWith("new");

                        switch (IntellijColors) {
                            case Default -> {
                                textColor = new Color(169, 183, 198);

                                if (shouldBeOrange) textColor = new Color(
                                    203,
                                    111,
                                    46
                                );
                                else if (
                                    splitPart.toUpperCase().equals(splitPart) &&
                                    !splitPart.startsWith("=")
                                ) textColor = new Color(104, 151, 187);
                                else if (splitPart.startsWith("\"")) textColor =
                                    new Color(84, 132, 88);
                            }
                            case Dark_Purple -> {
                                if (shouldBeOrange) textColor = new Color(
                                    223,
                                    138,
                                    94
                                );
                                else if (
                                    splitPart.toUpperCase().equals(splitPart) &&
                                    !splitPart.startsWith("=")
                                ) textColor = new Color(77, 172, 240);
                                else if (splitPart.startsWith("\"")) textColor =
                                    new Color(73, 158, 97);
                            }
                        }

                        RenderUtil.drawText(
                            splitPart,
                            xCordBase + lengthSoFar,
                            ySeperation * i + yCordBase,
                            textColor,
                            textSize
                        );

                        lengthSoFar +=
                        (int) (RenderUtil.getFontWidth(splitPart + " ") *
                                textSize +
                            1);
                    }

                    RenderUtil.drawText(
                        ";",
                        (int) (xCordBase +
                            lengthSoFar -
                            RenderUtil.getFontWidth(" ")),
                        ySeperation * i + yCordBase,
                        new Color(203, 111, 46),
                        textSize
                    );

                    i++;
                }

                square = new float[] { xPos, yPos, width, height };
            }
        }
    }

    //this comment gotta be here for some reason to push
    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    // TODO MAKE A DRAGGABLE LIB THINGY!!
    // dragging code
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

    public enum ColorsIntelliJ {
        Default,
        Dark_Purple,
    }
}
