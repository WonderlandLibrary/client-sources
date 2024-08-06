package com.shroomclient.shroomclientnextgen.modules.impl.client;

import com.shroomclient.shroomclientnextgen.config.*;
import com.shroomclient.shroomclientnextgen.config.types.ConfigOptionEnum;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderScreenEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderTickEvent;
import com.shroomclient.shroomclientnextgen.events.impl.ScreenClickedEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import com.shroomclient.shroomclientnextgen.util.ThemeUtil;
import java.awt.*;
import java.util.Comparator;
import java.util.Iterator;
import net.minecraft.client.gui.screen.ChatScreen;
import org.jetbrains.annotations.Nullable;

@RegisterModule(
    name = "Array List",
    uniqueId = "arrayList",
    description = "Display enabled modules",
    category = ModuleCategory.Client,
    enabledByDefault = true
)
public class ArrayListModule extends Module {

    @ConfigMode
    @ConfigParentId("mode")
    @ConfigOption(
        name = "Color mode",
        description = "How to determine the color of the arraylist",
        order = 0.1
    )
    public ColorMode colorMode = ColorMode.Theme_Color;

    @ConfigChild(value = "mode", parentEnumOrdinal = { 1, 2 })
    @ConfigOption(
        name = "Flag type",
        description = "What flag to use for the colors",
        order = 0.11
    )
    public FlagType flagType = FlagType.Trans;

    @ConfigOption(
        name = "Sorting Mode",
        description = "How to determine the color of the arraylist",
        order = 0.2
    )
    public SortingMode sorting = SortingMode.Size;

    private enum ColorMode {
        Theme_Color,
        Flag,
        Moving_Flag,
        White,
    }

    private enum SortingMode {
        Size,
        Alphabetical,
    }

    private enum FlagType {
        Trans,
        Rainbow,
        Lesbian,
        Gay,
        Bisexual,
        Poland,
    }

    @ConfigChild(value = "mode", parentEnumOrdinal = { 0, 2 })
    @ConfigOption(
        name = "Fade Speed",
        description = "",
        max = 0.5,
        precision = 2,
        order = 1
    )
    public Float fadeSpeed = 0.01f;

    @ConfigOption(name = "Side Bar", description = "", order = 2)
    public Boolean SideBar = false;

    @ConfigOption(
        name = "Ignore Render/Client",
        description = "Doesn't Render Client Or Render Modules",
        order = 3
    )
    public Boolean NoRenderSilly = true;

    @ConfigOption(
        name = "Mode",
        description = "Renders The Mode For Modules",
        order = 4
    )
    public Boolean prefix = true;

    @ConfigOption(name = "Remove Spaces", description = "", order = 4.1)
    public Boolean removeSpaces = false;

    @ConfigOption(name = "Remove Capitals", description = "", order = 4.11)
    public Boolean removeCaps = false;

    @ConfigOption(
        name = "Background",
        description = "Renders A Background Behind The Arraylist",
        order = 5
    )
    public Boolean Background = false;

    @ConfigHide
    @ConfigOption(
        name = "X Position",
        description = "Open Chat And Drag It.",
        order = 100,
        max = 10000
    )
    public Integer xPos = 200;

    @ConfigHide
    @ConfigOption(
        name = "Y Position",
        description = "Open Chat And Drag It.",
        order = 101,
        max = 10000
    )
    public Integer yPos = 10;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    private Color getModulePartColor(int mI, int yOffset) {
        Color color = null;
        switch (colorMode) {
            case Theme_Color -> {
                color = ThemeUtil.themeColors(
                    yOffset + yPos,
                    10,
                    50,
                    fadeSpeed
                )[0];
            }
            case Flag -> {
                Color[] colors = getColors();
                color = colors[mI % colors.length];
            }
            case Moving_Flag -> {
                Color[] colors = getColors();
                color = colors[(int) ((mI +
                            MovementUtil.ticks * 3 * fadeSpeed) %
                        colors.length)];
            }
            case White -> {
                color = new Color(255, 255, 255);
            }
        }
        return color;
    }

    @Nullable
    private Color[] getColors() {
        Color[] colors = null;
        switch (flagType) {
            case Trans -> {
                colors = new Color[] {
                    new Color(91, 206, 250),
                    new Color(245, 169, 184),
                    Color.WHITE,
                    new Color(245, 169, 184),
                };
            }
            case Rainbow -> {
                colors = new Color[] {
                    new Color(228, 3, 3),
                    new Color(255, 140, 0),
                    new Color(255, 237, 0),
                    new Color(0, 128, 38),
                    new Color(36, 64, 142),
                    new Color(115, 41, 130),
                };
            }
            case Lesbian -> {
                colors = new Color[] {
                    new Color(213, 45, 0),
                    new Color(239, 118, 39),
                    new Color(255, 154, 86),
                    new Color(255, 255, 255),
                    new Color(209, 98, 164),
                    new Color(181, 86, 144),
                    new Color(163, 2, 98),
                };
            }
            case Gay -> {
                colors = new Color[] {
                    new Color(7, 141, 112),
                    new Color(38, 206, 170),
                    new Color(152, 232, 193),
                    new Color(255, 255, 255),
                    new Color(123, 173, 226),
                    new Color(80, 73, 204),
                    new Color(61, 26, 120),
                };
            }
            case Bisexual -> {
                colors = new Color[] {
                    new Color(214, 2, 112),
                    new Color(214, 2, 112),
                    new Color(155, 79, 150),
                    new Color(0, 56, 168),
                    new Color(0, 56, 168),
                };
            }
            case Poland -> {
                colors = new Color[] {
                    new Color(255, 0, 0),
                    new Color(255, 255, 255),
                };
            }
        }
        return colors;
    }

    @SubscribeEvent
    public void onRenderTick(RenderTickEvent e) {
        if (!C.isInGame()) return;

        int yOffset = -1;

        RenderUtil.setContext(e.drawContext);

        int mI = 0;
        for (
            Iterator<ModuleManager.ModuleWithInfo> it =
                ModuleManager.getModulesByToggledState(true)
                    .stream()
                    .map(ModuleManager::getModuleWithInfo)
                    .filter(moduleWithInfo -> {
                        ModuleCategory category = moduleWithInfo
                            .an()
                            .category();
                        return (
                            (category != ModuleCategory.Render &&
                                category != ModuleCategory.Client) ||
                            !NoRenderSilly
                        );
                    })
                    .sorted(
                        Comparator.comparingInt(m -> {
                            if (sorting == SortingMode.Size) {
                                ModuleManager.ModuleWithInfo mm =
                                    ((ModuleManager.ModuleWithInfo) m);

                                String mmTextName = mm.an().name();
                                mmTextName = removeSpaces
                                    ? mmTextName.replaceAll(" ", "")
                                    : mmTextName;
                                mmTextName = removeCaps
                                    ? mmTextName.toLowerCase()
                                    : mmTextName;

                                if (
                                    prefix
                                ) for (com.shroomclient.shroomclientnextgen.config.types.ConfigOption opt : ModuleManager.getModuleConfig(
                                    mm.module(),
                                    true
                                )) {
                                    if (opt instanceof ConfigOptionEnum<?> j) {
                                        mmTextName +=
                                        " " +
                                        j
                                            .getOption()
                                            .getName()
                                            .replaceAll("_", " ");
                                        break;
                                    }
                                }
                                return (int) RenderUtil.getFontWidth(
                                    mmTextName
                                );
                            } else {
                                ModuleManager.ModuleWithInfo mm =
                                    ((ModuleManager.ModuleWithInfo) m);

                                return -mm.an().name().compareTo("a");
                            }
                        }).reversed()
                    )
                    .iterator();
            it.hasNext();
        ) {
            ModuleManager.ModuleWithInfo mm = it.next();

            String mmTextName = mm.an().name();
            mmTextName = removeSpaces
                ? mmTextName.replaceAll(" ", "")
                : mmTextName;
            mmTextName = removeCaps ? mmTextName.toLowerCase() : mmTextName;
            String mmTextName2 = mm.an().name();
            mmTextName2 = removeSpaces
                ? mmTextName2.replaceAll(" ", "")
                : mmTextName2;
            mmTextName2 = removeCaps ? mmTextName2.toLowerCase() : mmTextName2;

            String mmTextNameExtra = "";
            if (
                prefix
            ) for (com.shroomclient.shroomclientnextgen.config.types.ConfigOption opt : ModuleManager.getModuleConfig(
                mm.module(),
                true
            )) {
                if (opt instanceof ConfigOptionEnum<?> j) {
                    mmTextName += j.getOption().getName();
                    mmTextNameExtra = j.getOption().getName().replace("_", " ");
                    break;
                }
            }

            if (yOffset == -1) square = new float[] {
                square[0],
                square[1],
                RenderUtil.getFontWidth(mmTextName) + 13,
                yOffset,
            };

            int newXpos = xPos;
            int dir = -1;

            if (xPos < C.mc.getWindow().getScaledWidth() / 2) {
                newXpos -= (int) square[2];
                dir = 1;
            }

            mmTextNameExtra = mmTextNameExtra.replace("_", " ");

            if (dir == -1) {
                String namer = mmTextName2;
                if (!mmTextNameExtra.isEmpty()) {
                    mmTextName2 += " ";
                    mmTextName += " ";
                }

                if (Background) {
                    RenderUtil.drawRect(
                        (int) (newXpos -
                            6 -
                            RenderUtil.getFontWidth(mmTextName)),
                        yOffset + yPos,
                        RenderUtil.getFontWidth(mmTextName) + 6,
                        RenderUtil.fontSize,
                        new Color(12, 12, 12, 100)
                    );
                }

                RenderUtil.drawTextShadow(
                    namer,
                    (int) (newXpos -
                        3 -
                        RenderUtil.getFontWidth(mmTextName2 + mmTextNameExtra)),
                    yOffset + yPos,
                    getModulePartColor(mI, yOffset)
                );
                RenderUtil.drawTextShadow(
                    mmTextNameExtra,
                    (int) ((newXpos -
                            3 -
                            RenderUtil.getFontWidth(
                                mmTextName2 + mmTextNameExtra
                            )) +
                        RenderUtil.getFontWidth(mmTextName2)),
                    yOffset + yPos,
                    new Color(150, 150, 150)
                );
            } else {
                if (!mmTextNameExtra.isEmpty()) {
                    mmTextNameExtra += " ";
                    mmTextName2 += " ";
                }

                if (Background) {
                    String mmTextName3 = mm.an().name();
                    mmTextName3 = removeSpaces
                        ? mmTextName3.replaceAll(" ", "")
                        : mmTextName3;
                    mmTextName3 = removeCaps
                        ? mmTextName3.toLowerCase()
                        : mmTextName3;

                    RenderUtil.drawRect(
                        newXpos + 6,
                        yOffset + yPos,
                        RenderUtil.getFontWidth(mmTextName3 + mmTextNameExtra) +
                        6,
                        RenderUtil.fontSize,
                        new Color(12, 12, 12, 100)
                    );
                }

                RenderUtil.drawTextShadow(
                    mmTextNameExtra,
                    (newXpos + 9),
                    yOffset + yPos,
                    new Color(150, 150, 150)
                );
                RenderUtil.drawTextShadow(
                    mmTextName2,
                    (int) (newXpos +
                        9 +
                        RenderUtil.getFontWidth(mmTextNameExtra)),
                    yOffset + yPos,
                    getModulePartColor(mI, yOffset)
                );
            }

            if (SideBar) {
                // i need glow effect in arraylist
                //RenderUtil.drawRoundedGlow(e.drawContext.getScaledWindowWidth(), yOffset - 1, 5, 9, 3, 2, new Color(203, 9, 229, 60), true, true, true, true);

                //RenderUtil.drawDownFade(e.drawContext.getScaledWindowWidth() - 1, yOffset, 1, RenderUtil.fontSize - 2, ThemeUtil.themeColors(yOffset, 10, 50, fadeSpeed)[0], ThemeUtil.themeColors(yOffset, 10, 50, fadeSpeed)[1]);
                RenderUtil.drawRect(
                    newXpos + (dir == 1 ? 6 : 0),
                    yPos + yOffset - 1,
                    1,
                    RenderUtil.fontSize + 2,
                    getModulePartColor(mI, yOffset)
                );
            }

            yOffset += RenderUtil.fontSize;

            square = new float[] {
                xPos - square[2] + 3,
                yPos - 3,
                square[2],
                yOffset + 5,
            };

            mI++;
        }
        ModuleManager.getModulesByToggledState(true).sort(
            Comparator.comparingInt(
                m ->
                    C.mc.textRenderer.getWidth(
                        ModuleManager.getModuleWithInfo((Module) m).an().name()
                    )
            ).reversed()
        );
    }

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
}
