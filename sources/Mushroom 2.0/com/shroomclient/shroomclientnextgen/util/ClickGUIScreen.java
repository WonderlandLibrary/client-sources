package com.shroomclient.shroomclientnextgen.util;

import static com.shroomclient.shroomclientnextgen.modules.impl.client.ClickGUI.*;

import com.shroomclient.shroomclientnextgen.ShroomClient;
import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.config.types.*;
import com.shroomclient.shroomclientnextgen.modules.KeybindManager;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.client.ClickGUI;
import com.shroomclient.shroomclientnextgen.modules.impl.client.Notifications;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ClickGUIScreen extends Screen {

    public static float oldX;
    public static float oldY;
    static float[] guiSize;
    static float[] resize;
    static DrawContext context;
    static float scaleSizeWe;
    static float scaleSizeHe;
    float cguiRoundedNess = 5;
    boolean clickGUIanimation = true;
    boolean sorted = false;
    textStuff HoverText = null;
    private boolean lmbDown = false;
    private boolean lmbClicked = false;
    private boolean rmbDown = false;
    private boolean rmbClicked = false;
    private boolean mmbDown = false;
    private boolean mmbClicked = false;

    public ClickGUIScreen() {
        super(Text.literal(""));
    }

    public static boolean isHovered2(
        float mX,
        float mY,
        double x,
        double y,
        double w,
        double h,
        double scrolledOffset
    ) {
        if (mY + scrolledOffset < 35) return false;
        if (mY + scrolledOffset > 300) return false;

        return mX >= x && mX <= x + w && mY >= y && mY <= y + h;
    }

    public static boolean isHovered(
        float mX,
        float mY,
        double x,
        double y,
        double w,
        double h
    ) {
        return mX >= x && mX <= x + w && mY >= y && mY <= y + h;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    // only save configs on gui close, saves ur fps
    @Override
    public void close() {
        ModuleManager.save();
        if (this.client != null) this.client.setScreen(null);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        switch (mouseButton) {
            case 0:
                lmbDown = true;
                lmbClicked = true;
                break;
            case 1:
                rmbDown = true;
                rmbClicked = true;
                break;
            case 2:
                mmbDown = true;
                mmbClicked = true;
                break;
        }

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseReleased(
        double mouseX,
        double mouseY,
        int mouseButton
    ) {
        switch (mouseButton) {
            case 0:
                lmbDown = false;
                break;
            case 1:
                rmbDown = false;
                break;
            case 2:
                mmbDown = false;
                break;
        }

        return super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseScrolled(
        double mouseX,
        double mouseY,
        double horizontalAmount,
        double verticalAmount
    ) {
        double scrollOffset =
            ClickGUI.scrollOffsetsTEMP[getCategoryNumber()] +
            verticalAmount * 20;

        ClickGUI.scrollOffsetsTEMP[getCategoryNumber()] = scrollOffset;
        ClickGUI.scrollOffsets[getCategoryNumber()] += verticalAmount * 5;

        return super.mouseScrolled(
            mouseX,
            mouseY,
            horizontalAmount,
            verticalAmount
        );
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { // Escape
            // GUI Will be closed so we clear all listeners
            KeybindManager.bindListeners.clear();
            search.setFocused(false);
        } else {
            // This allows us to bind key in the ClickGUI
            //Bus.post(new KeyPressEvent(keyCode, scanCode));
        }

        // wait this is so smart !!! (idk why the textbox isnt working)
        if (
            !search.keyPressed(keyCode, scanCode, modifiers) &&
            modifiers == 0 &&
            search.isFocused()
        ) {
            search.write(String.valueOf((char) keyCode).toLowerCase());
        }

        //search.keyPressed(keyCode,scanCode,modifiers);
        // 65 30 0
        //ChatUtil.chat(keyCode + " : " + scanCode + " " + modifiers);

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    ModuleCategory lastCat = ModuleCategory.Combat;

    boolean dragging = false;

    double mouseXold = 0;
    double mouseYold = 0;

    double oldHudX = ClickGUI.xPos;
    double oldHudY = ClickGUI.yPos;

    @Override
    public void render(
        DrawContext context,
        int mXrel,
        int mYrel,
        float partialTicks
    ) {
        scaleSizeWe = ClickGUI.scaleSizeW;
        scaleSizeHe = ClickGUI.scaleSizeH;

        //renderBackground(context, mX, mY, partialTicks);

        float guiWidth = 500;
        float guiHeight = 300;

        float radius = 10;

        Color backgroundColor = new Color(23, 23, 23);
        Color seperatorColor = new Color(0, 0, 0);

        guiSize = new float[] { ClickGUI.xPos, ClickGUI.yPos };

        context.getMatrices().push();

        context.getMatrices().translate(guiSize[0], guiSize[1], 0);
        // messes w mouse coords. :(
        //context.getMatrices().scale(scaleSizeWe, scaleSizeHe, 0);

        ClickGUIScreen.context = context;

        float mX = mXrel;
        float mY = mYrel;

        mX -= guiSize[0];
        mY -= guiSize[1];

        RenderUtil.drawRoundedRect2(
            0,
            0,
            guiWidth,
            guiHeight,
            radius,
            backgroundColor,
            false,
            false,
            false,
            false
        );
        RenderUtil.drawRoundedGlow(
            0,
            0,
            guiWidth,
            guiHeight,
            radius,
            5,
            seperatorColor,
            255,
            false,
            false,
            false,
            false
        );

        float titleTextX = 35;
        float titleTextWidth = 100;

        float mushroomTitleHeight = 15;
        float mushroomLogoSize = 25;
        RenderUtil.drawTexture(
            "icon.png",
            3,
            5,
            mushroomLogoSize,
            mushroomLogoSize
        );
        RenderUtil.drawTexture(
            "mushroomtext.png",
            titleTextX,
            10,
            titleTextWidth,
            mushroomTitleHeight
        );

        float seperatorX = titleTextX + titleTextWidth + 10;
        RenderUtil.drawRect(seperatorX, 0, 3, guiHeight, seperatorColor);

        float normalTitleHeight = mushroomLogoSize + 10;
        RenderUtil.drawRect(
            0,
            normalTitleHeight,
            seperatorX,
            3,
            seperatorColor
        );
        //RenderUtil.drawRect(seperatorX, normalTitleHeight, guiWidth-seperatorX, 3, seperatorColor);

        int i = 0;

        int startCategoryY = (int) (normalTitleHeight + 10);

        int pictureSize = 20;
        int gapBetween = 5;
        int distanceBetweenCategory = pictureSize + gapBetween * 2;
        int categoryHeight = pictureSize + gapBetween;

        context.enableScissor(
            (int) guiSize[0] + 1,
            (int) guiSize[1] + 1,
            (int) (guiSize[0] + guiWidth),
            (int) (guiSize[1] + guiHeight)
        );

        int offsetFromPic = 15;

        int startingY = 0;

        for (ModuleCategory cat : ModuleCategory.values()) {
            float categoryX = 0;
            float categoryY =
                startCategoryY +
                (distanceBetweenCategory * i) -
                gapBetween / 2f;

            // drawing categorys!
            if (selectedCategory == cat) {
                RenderUtil.drawRoundedRect2(
                    categoryX,
                    categoryY,
                    seperatorX,
                    categoryHeight,
                    5,
                    new Color(0, 0, 0),
                    false,
                    false,
                    false,
                    false
                );
            }

            RenderUtil.drawRoundedRectImage(
                new Identifier(
                    "shroomclientnextgen",
                    cat.name().toLowerCase() + ".png"
                ),
                5,
                startCategoryY + (distanceBetweenCategory * i),
                0,
                0,
                pictureSize,
                pictureSize,
                0,
                ThemeUtil.themeColors()[0]
            );
            RenderUtil.drawText(
                cat.name(),
                pictureSize + offsetFromPic,
                startCategoryY +
                (distanceBetweenCategory * i) +
                pictureSize / 2 -
                7,
                new Color(255, 255, 255),
                2f
            );

            if (lmbClicked) {
                if (
                    isHovered(
                        mX,
                        mY,
                        categoryX,
                        categoryY,
                        seperatorX,
                        categoryHeight
                    )
                ) {
                    ClickGUI.scrollOffsets[getCategoryNumber()] =
                        ClickGUI.scrollOffsetsTEMP[getCategoryNumber()];

                    selectedCategory = cat;
                }
            }

            // drawing normal settings
            if (selectedCategory == cat) {
                lastCat = cat;

                float middle = (seperatorX + (guiWidth - seperatorX) / 2);

                float titleSize = 3;

                int textX = (int) (middle -
                    (RenderUtil.getFontWidth(cat.name()) * titleSize) / 2f);
                int textY = 8;

                int yLevel = (int) (textY +
                    RenderUtil.getFontHeight("h") * titleSize +
                    10);
                startingY = yLevel;

                int indentSizeX = 20;
                int height = 20;
                int indentSizeY = 10;

                float textScale = 1.5f;

                int xTextOffset = 5;
                int yLevelTextOffset = 5;

                int toggleNormalRadius = 5;
                int toggleHeight = 10;
                int toggleWidth = 30;
                int toggleOffset = 10;
                int toggleCircleDiametre = 12;

                float startX = seperatorX + indentSizeX;
                float endW = guiWidth - seperatorX - indentSizeX * 2;

                float toggleButtonX =
                    startX + endW - toggleWidth - toggleOffset;

                float scrolledOffset =
                    (float) ClickGUI.scrollOffsets[getCategoryNumber()];

                context.getMatrices().push();
                context.getMatrices().translate(0, scrolledOffset, 0);

                mY -= (int) scrolledOffset;

                ArrayList<String> minMaxDrawnAlr = new ArrayList<>();

                if (cat == ModuleCategory.Search) {
                    for (Module mod : ModuleManager.getSortedModulesByLength()) {
                        ModuleManager.ModuleWithInfo inf =
                            ModuleManager.getModuleWithInfo(mod);
                        String name =
                            inf.an().name() +
                            (KeybindManager.getKeyBind(mod.getClass()) == null
                                    ? ""
                                    : (" [" +
                                        KeybindManager.getKeyBind(
                                            mod.getClass()
                                        ) +
                                        "]"));
                        String description = inf.an().description();
                        boolean enabled = mod.isEnabled();

                        if (
                            isHovered2(
                                mX,
                                mY,
                                startX,
                                yLevel,
                                endW,
                                height,
                                scrolledOffset
                            )
                        ) {
                            if (lmbClicked) mod.toggle(false);
                            if (rmbClicked) {
                                if (ClickGUI.expandedModules.contains(mod)) {
                                    ClickGUI.expandedModules.remove(mod);
                                } else {
                                    ClickGUI.expandedModules.add(mod);
                                }
                            }

                            if (mmbClicked) {
                                KeybindManager.listenForBind(mod.getClass());
                            }
                        }

                        float ogYlevel = yLevel;

                        RenderUtil.drawText(
                            name,
                            (int) (startX) + xTextOffset,
                            yLevel + yLevelTextOffset,
                            new Color(255, 255, 255),
                            textScale
                        );

                        Color bgColor = enabled
                            ? new Color(105, 241, 143)
                            : new Color(252, 42, 81);
                        RenderUtil.drawRoundedRect2(
                            toggleButtonX,
                            yLevel + (height / 2f) - (toggleHeight / 2f),
                            toggleWidth,
                            toggleHeight,
                            toggleNormalRadius,
                            bgColor,
                            false,
                            false,
                            false,
                            false
                        );
                        float circleX =
                            toggleButtonX +
                            (enabled
                                    ? (toggleWidth - toggleCircleDiametre)
                                    : 0);
                        RenderUtil.drawRoundedRect2(
                            circleX,
                            yLevel +
                            (height / 2f) -
                            (toggleCircleDiametre / 2f),
                            toggleCircleDiametre,
                            toggleCircleDiametre,
                            toggleCircleDiametre / 2f,
                            new Color(255, 255, 255),
                            false,
                            false,
                            false,
                            false
                        );

                        yLevel += height + indentSizeY;

                        if (ClickGUI.expandedModules.contains(mod)) {
                            float extrasTextScale = 1.2f;
                            float extrasDescScale = 0.7f;

                            if (
                                !Objects.equals(description, "")
                            ) RenderUtil.drawText(
                                description,
                                (int) (startX) + xTextOffset,
                                yLevel - 10,
                                new Color(200, 200, 200),
                                1
                            );
                            if (!Objects.equals(description, "")) yLevel +=
                            indentSizeY;

                            List<ConfigOption> subSettings =
                                ModuleManager.getModuleConfig(mod, false, true);
                            subSettings.sort(
                                Comparator.comparingDouble(
                                    ConfigOption::getOrder
                                )
                            );

                            HashMap<String, ConfigOption> parents =
                                new HashMap<>();
                            for (ConfigOption opt : subSettings) {
                                Field field = opt.getField();
                                if (
                                    field.isAnnotationPresent(
                                        ConfigParentId.class
                                    )
                                ) {
                                    String pId = field
                                        .getAnnotation(ConfigParentId.class)
                                        .value();
                                    parents.put(pId, opt);
                                }
                            }

                            for (ConfigOption opt : subSettings) {
                                Field field = opt.getField();
                                if (
                                    field.isAnnotationPresent(ConfigChild.class)
                                ) {
                                    ConfigChild cC = field.getAnnotation(
                                        ConfigChild.class
                                    );
                                    ConfigOption parent = parents.get(
                                        cC.value()
                                    );

                                    if (cC.parentEnumOrdinal().length != 0) { // It's an enum
                                        ConfigOptionEnum cE =
                                            (ConfigOptionEnum) parent;
                                        if (
                                            !Arrays.stream(
                                                cC.parentEnumOrdinal()
                                            ).anyMatch(
                                                idx ->
                                                    cE.getOption().getIndex() ==
                                                    idx
                                            )
                                        ) continue;
                                    } else {
                                        if (
                                            parent != null &&
                                            !((ConfigOptionBoolean) parent).get()
                                        ) continue;
                                    }
                                }

                                if (opt instanceof ConfigOptionBoolean b) {
                                    // Draw the checkbox
                                    RenderUtil.drawText(
                                        opt.getName(),
                                        (int) (startX) + xTextOffset,
                                        yLevel,
                                        new Color(240, 240, 240),
                                        extrasTextScale
                                    );
                                    RenderUtil.drawText(
                                        opt.getDescription(),
                                        (int) (startX) + xTextOffset,
                                        yLevel + 13,
                                        new Color(200, 200, 200),
                                        extrasDescScale
                                    );

                                    float radiusOfCheck = 4;

                                    RenderUtil.drawRoundedRect2(
                                        startX + endW - 20,
                                        yLevel,
                                        10,
                                        10,
                                        radiusOfCheck,
                                        new Color(255, 255, 255),
                                        false,
                                        false,
                                        false,
                                        false
                                    );

                                    if (b.get()) RenderUtil.drawRoundedRect2(
                                        startX + endW - 20,
                                        yLevel,
                                        10,
                                        10,
                                        radiusOfCheck,
                                        new Color(105, 241, 143),
                                        false,
                                        false,
                                        false,
                                        false
                                    );
                                    //RenderUtil.drawTexture("on.png", startX + endW - 20, yLevel, 10, 10);

                                    if (
                                        isHovered2(
                                            mX,
                                            mY,
                                            startX,
                                            yLevel,
                                            endW,
                                            height,
                                            scrolledOffset
                                        )
                                    ) { // Toggle checkbox on click
                                        if (lmbClicked) b.set(!b.get());
                                    }
                                } else if (
                                    opt instanceof ConfigOptionEnum<?> e
                                ) {
                                    ConfigOptionEnum<Object> o =
                                        (ConfigOptionEnum<Object>) e;
                                    // Draw selected value (replace _ w space too)
                                    RenderUtil.drawText(
                                        opt.getName(),
                                        (int) (startX + xTextOffset),
                                        yLevel,
                                        new Color(240, 240, 240),
                                        extrasTextScale
                                    );
                                    RenderUtil.drawText(
                                        opt.getDescription(),
                                        (int) (startX + xTextOffset),
                                        yLevel + 13,
                                        new Color(200, 200, 200),
                                        extrasDescScale
                                    );

                                    String string = e
                                        .getOption()
                                        .getName()
                                        .replaceAll("_", " ")
                                        .replaceAll("DOT", ".");
                                    float highestWidth =
                                        RenderUtil.getFontWidth(string);

                                    ConfigOptionEnum.EnumOption<?> eCurrent =
                                        e.getOption();

                                    while (
                                        eCurrent.next().getIndex() !=
                                        e.getOption().getIndex()
                                    ) {
                                        eCurrent = eCurrent.next();
                                        String string2 = eCurrent
                                            .getName()
                                            .replaceAll("_", " ")
                                            .replaceAll("DOT", ".");

                                        if (
                                            RenderUtil.getFontWidth(string2) >
                                            highestWidth
                                        ) highestWidth =
                                            RenderUtil.getFontWidth(string2);
                                    }

                                    int modeX = (int) (startX +
                                        endW -
                                        20 -
                                        RenderUtil.getFontWidth(string) *
                                            extrasTextScale);

                                    if (
                                        !ClickGUI.modesExtended.contains(
                                            e.getName()
                                        )
                                    ) {
                                        RenderUtil.drawText(
                                            string,
                                            modeX,
                                            yLevel,
                                            new Color(240, 240, 240),
                                            extrasTextScale
                                        );

                                        if (
                                            isHovered2(
                                                mX,
                                                mY,
                                                startX,
                                                yLevel,
                                                endW,
                                                height,
                                                scrolledOffset
                                            )
                                        ) {
                                            if (rmbClicked || lmbClicked) {
                                                ClickGUI.modesExtended.add(
                                                    e.getName()
                                                );
                                                lmbClicked = false;
                                            }
                                        }
                                    } else {
                                        modeX = (int) (startX +
                                            endW -
                                            30 -
                                            highestWidth);
                                        int oldY = yLevel;

                                        int currentIndex = e
                                            .getOption()
                                            .getIndex();
                                        eCurrent = e.getOption();

                                        if (
                                            isHovered2(
                                                mX,
                                                mY,
                                                modeX,
                                                yLevel,
                                                highestWidth + 25,
                                                indentSizeY + 5,
                                                scrolledOffset
                                            ) &&
                                            lmbClicked
                                        ) {
                                            o.set(eCurrent.getValue());
                                            ClickGUI.modesExtended.remove(
                                                e.getName()
                                            );
                                            lmbClicked = false;
                                            lmbDown = false;
                                        }

                                        String string2 = eCurrent
                                            .getName()
                                            .replaceAll("_", " ")
                                            .replaceAll("DOT", ".");

                                        RenderUtil.drawText(
                                            string2,
                                            modeX,
                                            yLevel,
                                            new Color(240, 240, 240),
                                            extrasTextScale
                                        );
                                        yLevel += indentSizeY + 5;

                                        while (
                                            eCurrent.next().getIndex() !=
                                            currentIndex
                                        ) {
                                            eCurrent = eCurrent.next();

                                            string2 = eCurrent
                                                .getName()
                                                .replaceAll("_", " ")
                                                .replaceAll("DOT", ".");

                                            if (
                                                isHovered2(
                                                    mX,
                                                    mY,
                                                    modeX,
                                                    yLevel,
                                                    highestWidth + 25,
                                                    indentSizeY + 5,
                                                    scrolledOffset
                                                ) &&
                                                (lmbClicked || rmbClicked)
                                            ) {
                                                o.set(eCurrent.getValue());
                                                ClickGUI.modesExtended.remove(
                                                    e.getName()
                                                );
                                                lmbClicked = false;
                                                lmbDown = false;
                                            }

                                            RenderUtil.drawText(
                                                string2,
                                                modeX,
                                                yLevel,
                                                new Color(240, 240, 240),
                                                extrasTextScale
                                            );
                                            yLevel += indentSizeY + 5;
                                        }

                                        yLevel -= 2;

                                        RenderUtil.drawRoundedGlow(
                                            modeX - 5,
                                            oldY - 5,
                                            highestWidth + 25,
                                            yLevel - oldY + 5,
                                            5,
                                            5,
                                            new Color(50, 50, 50),
                                            false,
                                            false,
                                            false,
                                            false
                                        );

                                        if (
                                            isHovered2(
                                                mX,
                                                mY,
                                                startX,
                                                oldY,
                                                endW,
                                                yLevel - oldY + 5,
                                                scrolledOffset
                                            ) &&
                                            (rmbClicked || lmbClicked)
                                        ) {
                                            ClickGUI.modesExtended.remove(
                                                e.getName()
                                            );
                                            rmbClicked = false;
                                        }

                                        yLevel -= 10;
                                    }
                                } else if (
                                    opt instanceof ConfigOptionMinMax m
                                ) {
                                    minMaxDrawnAlr.add(m.getName());

                                    float newXForSlider = startX + 10;
                                    float newYForSlider = yLevel + 10;
                                    float newWForSlider = endW - 20;
                                    float newHForSlider = 10f;
                                    float radiusSlider = 5;

                                    float sStartX = 300;
                                    float sStartY = newYForSlider + (3f);
                                    float sW = 160;

                                    // get max fraction
                                    double selectedFracMin =
                                        (Double.parseDouble(
                                                m.min.get().toString()
                                            ) -
                                            m.min.getMinimum()) /
                                        (m.min.getMaximum() -
                                            m.min.getMinimum());
                                    double selectedFracMax =
                                        (Double.parseDouble(
                                                m.max.get().toString()
                                            ) -
                                            m.max.getMinimum()) /
                                        (m.max.getMaximum() -
                                            m.max.getMinimum());

                                    double minXcoord =
                                        sStartX +
                                        sW * selectedFracMin -
                                        radiusSlider;
                                    double maxXcoord =
                                        sStartX +
                                        sW * selectedFracMax -
                                        radiusSlider;

                                    // Draw Slider
                                    RenderUtil.drawRect(
                                        sStartX,
                                        sStartY,
                                        sW,
                                        2,
                                        new Color(50, 50, 50)
                                    );
                                    RenderUtil.drawRect(
                                        (float) minXcoord,
                                        sStartY,
                                        (float) (maxXcoord + 6 - minXcoord),
                                        2,
                                        ThemeUtil.themeColors()[0]
                                    );

                                    RenderUtil.drawTriangle(
                                        (float) (minXcoord),
                                        sStartY - radiusSlider + 1.5f,
                                        4,
                                        9,
                                        ThemeUtil.themeColors()[0]
                                    );
                                    RenderUtil.drawTriangle(
                                        (float) (maxXcoord + 6),
                                        sStartY - radiusSlider + 1.5f,
                                        -4,
                                        9,
                                        ThemeUtil.themeColors()[0]
                                    );

                                    //RenderUtil.drawRoundedRect2((float) (minXcoord), sStartY - radiusSlider + 1.5f, radiusSlider * 2, radiusSlider * 2, radiusSlider, ThemeUtil.themeColors()[0], false, false, false, false);
                                    //RenderUtil.drawRoundedRect2((float) (maxXcoord), sStartY - radiusSlider + 1.5f, radiusSlider * 2, radiusSlider * 2, radiusSlider, ThemeUtil.themeColors()[0], false, false, false, false);

                                    /*
                                    // Draw selected portion
                                    RenderUtil.drawRoundedRect2(sStartX + twoScaledW, sStartY + twoScaledH, (float) (rSW * selectedFrac), sH - (twoScaledH * 2), radiusSlider, ThemeUtil.themeColors()[0], false,true,false,true);
                                    // Draw unselected portion
                                    RenderUtil.drawRoundedRect2((float) (sStartX + twoScaledW + rSW * selectedFrac), sStartY + twoScaledH, (float) (rSW * (1 - selectedFrac)), sH - (twoScaledH * 2), radiusSlider, new Color(50, 50, 50), true,false,true,false);
                                     */

                                    // Draw current Max
                                    String seperator = "/";
                                    RenderUtil.drawTextShadow(
                                        m.min.get().toString() +
                                        seperator +
                                        m.max.get().toString(),
                                        (int) (newXForSlider +
                                            newWForSlider -
                                            (8f) -
                                            textRenderer.getWidth(
                                                m.min.get().toString() +
                                                seperator +
                                                m.max.get().toString()
                                            )),
                                        (int) (newYForSlider -
                                            (textRenderer.fontHeight / 2) -
                                            5),
                                        ThemeUtil.themeColors()[1]
                                    );

                                    // Draw name
                                    RenderUtil.drawText(
                                        opt
                                            .getName()
                                            .replace(" MIN", "")
                                            .replace(" MAX", ""),
                                        (int) (startX) + xTextOffset,
                                        yLevel,
                                        new Color(240, 240, 240),
                                        extrasTextScale
                                    );
                                    RenderUtil.drawText(
                                        opt.getDescription(),
                                        (int) (startX) + xTextOffset,
                                        yLevel + 13,
                                        new Color(200, 200, 200),
                                        extrasDescScale
                                    );

                                    if (
                                        mX >= sStartX &&
                                        mX <=
                                            minXcoord +
                                                radiusSlider * 2 +
                                                radiusSlider &&
                                        mY >= newYForSlider &&
                                        mY <= newYForSlider + (newHForSlider)
                                    ) {
                                        if (lmbDown) {
                                            double fraction =
                                                (mX - (sStartX + (2f))) /
                                                (sW - (8f));
                                            double value =
                                                (m.min.getMaximum() -
                                                        m.min.getMinimum()) *
                                                    fraction +
                                                m.min.getMinimum();
                                            Class<?> t = m.min.getType();
                                            if (t.equals(Integer.class)) {
                                                ((ConfigOptionNumber<
                                                            Integer
                                                        >) m.min).set(
                                                        (int) Math.round(value)
                                                    );
                                            } else if (t.equals(Long.class)) {
                                                ((ConfigOptionNumber<
                                                            Long
                                                        >) m.min).set(
                                                        Math.round(value)
                                                    );
                                            } else if (t.equals(Float.class)) {
                                                ((ConfigOptionNumber<
                                                            Float
                                                        >) m.min).set(
                                                        (float) value
                                                    );
                                            } else if (t.equals(Double.class)) {
                                                ((ConfigOptionNumber<
                                                            Double
                                                        >) m.min).set(value);
                                            }

                                            double minValue =
                                                Double.parseDouble(
                                                    m.min.get().toString()
                                                );
                                            double maxValue =
                                                Double.parseDouble(
                                                    m.max.get().toString()
                                                );

                                            if (minValue > maxValue) {
                                                if (t.equals(Integer.class)) {
                                                    ((ConfigOptionNumber<
                                                                Integer
                                                            >) m.max).set(
                                                            (int) Math.round(
                                                                minValue
                                                            )
                                                        );
                                                } else if (
                                                    t.equals(Long.class)
                                                ) {
                                                    ((ConfigOptionNumber<
                                                                Long
                                                            >) m.max).set(
                                                            Math.round(minValue)
                                                        );
                                                } else if (
                                                    t.equals(Float.class)
                                                ) {
                                                    ((ConfigOptionNumber<
                                                                Float
                                                            >) m.max).set(
                                                            (float) minValue
                                                        );
                                                } else if (
                                                    t.equals(Double.class)
                                                ) {
                                                    ((ConfigOptionNumber<
                                                                Double
                                                            >) m.max).set(
                                                            minValue
                                                        );
                                                }
                                            }
                                        }
                                    }

                                    if (
                                        mX >= maxXcoord - radiusSlider &&
                                        mX <= sStartX + sW &&
                                        mY >= newYForSlider &&
                                        mY <= newYForSlider + (newHForSlider)
                                    ) {
                                        if (lmbDown) {
                                            double fraction =
                                                (mX - (sStartX + (2f))) /
                                                (sW - (8f));
                                            double value =
                                                (m.max.getMaximum() -
                                                        m.max.getMinimum()) *
                                                    fraction +
                                                m.max.getMinimum();
                                            Class<?> t = m.max.getType();
                                            if (t.equals(Integer.class)) {
                                                ((ConfigOptionNumber<
                                                            Integer
                                                        >) m.max).set(
                                                        (int) Math.round(value)
                                                    );
                                            } else if (t.equals(Long.class)) {
                                                ((ConfigOptionNumber<
                                                            Long
                                                        >) m.max).set(
                                                        Math.round(value)
                                                    );
                                            } else if (t.equals(Float.class)) {
                                                ((ConfigOptionNumber<
                                                            Float
                                                        >) m.max).set(
                                                        (float) value
                                                    );
                                            } else if (t.equals(Double.class)) {
                                                ((ConfigOptionNumber<
                                                            Double
                                                        >) m.max).set(value);
                                            }

                                            double minValue =
                                                Double.parseDouble(
                                                    m.min.get().toString()
                                                );
                                            double maxValue =
                                                Double.parseDouble(
                                                    m.max.get().toString()
                                                );

                                            if (maxValue < minValue) {
                                                if (t.equals(Integer.class)) {
                                                    ((ConfigOptionNumber<
                                                                Integer
                                                            >) m.min).set(
                                                            (int) Math.round(
                                                                maxValue
                                                            )
                                                        );
                                                } else if (
                                                    t.equals(Long.class)
                                                ) {
                                                    ((ConfigOptionNumber<
                                                                Long
                                                            >) m.min).set(
                                                            Math.round(maxValue)
                                                        );
                                                } else if (
                                                    t.equals(Float.class)
                                                ) {
                                                    ((ConfigOptionNumber<
                                                                Float
                                                            >) m.min).set(
                                                            (float) maxValue
                                                        );
                                                } else if (
                                                    t.equals(Double.class)
                                                ) {
                                                    ((ConfigOptionNumber<
                                                                Double
                                                            >) m.min).set(
                                                            maxValue
                                                        );
                                                }
                                            }
                                        }
                                    }
                                } else if (
                                    opt instanceof ConfigOptionNumber<?> n
                                ) {
                                    if (
                                        minMaxDrawnAlr.contains(n.getName())
                                    ) continue;

                                    float newXForSlider = startX + 10;
                                    float newYForSlider = yLevel + 10;
                                    float newWForSlider = endW - 20;
                                    float newHForSlider = 10f;
                                    float radiusSlider = 5;

                                    float sStartX = 300;
                                    float sStartY = newYForSlider + (3f);
                                    float sW = 160;

                                    double selectedFrac =
                                        (Double.parseDouble(
                                                n.get().toString()
                                            ) -
                                            n.getMinimum()) /
                                        (n.getMaximum() - n.getMinimum());

                                    // Draw Slider
                                    RenderUtil.drawRect(
                                        sStartX,
                                        sStartY,
                                        sW,
                                        2,
                                        new Color(50, 50, 50)
                                    );
                                    RenderUtil.drawRoundedRect2(
                                        (float) (sStartX +
                                            sW * selectedFrac -
                                            radiusSlider),
                                        sStartY - radiusSlider + 1.5f,
                                        radiusSlider * 2,
                                        radiusSlider * 2,
                                        radiusSlider,
                                        ThemeUtil.themeColors()[0],
                                        false,
                                        false,
                                        false,
                                        false
                                    );

                                    /*
                                    // Draw selected portion
                                    RenderUtil.drawRoundedRect2(sStartX + twoScaledW, sStartY + twoScaledH, (float) (rSW * selectedFrac), sH - (twoScaledH * 2), radiusSlider, ThemeUtil.themeColors()[0], false,true,false,true);
                                    // Draw unselected portion
                                    RenderUtil.drawRoundedRect2((float) (sStartX + twoScaledW + rSW * selectedFrac), sStartY + twoScaledH, (float) (rSW * (1 - selectedFrac)), sH - (twoScaledH * 2), radiusSlider, new Color(50, 50, 50), true,false,true,false);

                                     */

                                    // Draw current value
                                    RenderUtil.drawTextShadow(
                                        n.get().toString(),
                                        (int) (newXForSlider +
                                            newWForSlider -
                                            (8f) -
                                            textRenderer.getWidth(
                                                n.get().toString()
                                            )),
                                        (int) (newYForSlider -
                                            (textRenderer.fontHeight / 2) -
                                            5),
                                        ThemeUtil.themeColors()[1]
                                    );

                                    // Draw name
                                    RenderUtil.drawText(
                                        opt.getName(),
                                        (int) (startX) + xTextOffset,
                                        yLevel,
                                        new Color(240, 240, 240),
                                        extrasTextScale
                                    );
                                    RenderUtil.drawText(
                                        opt.getDescription(),
                                        (int) (startX) + xTextOffset,
                                        yLevel + 13,
                                        new Color(200, 200, 200),
                                        extrasDescScale
                                    );

                                    if (
                                        mX >= sStartX &&
                                        mX <= sStartX + sW &&
                                        mY >= newYForSlider &&
                                        mY <= newYForSlider + (newHForSlider)
                                    ) {
                                        if (lmbDown) {
                                            double fraction =
                                                (mX - (sStartX + (2f))) /
                                                (sW - (8f));
                                            double value =
                                                (n.getMaximum() -
                                                        n.getMinimum()) *
                                                    fraction +
                                                n.getMinimum();
                                            Class<?> t = n.getType();
                                            if (t.equals(Integer.class)) {
                                                ((ConfigOptionNumber<
                                                            Integer
                                                        >) n).set(
                                                        (int) Math.round(value)
                                                    );
                                            } else if (t.equals(Long.class)) {
                                                ((ConfigOptionNumber<
                                                            Long
                                                        >) n).set(
                                                        Math.round(value)
                                                    );
                                            } else if (t.equals(Float.class)) {
                                                ((ConfigOptionNumber<
                                                            Float
                                                        >) n).set(
                                                        (float) value
                                                    );
                                            } else if (t.equals(Double.class)) {
                                                ((ConfigOptionNumber<
                                                            Double
                                                        >) n).set(value);
                                            }
                                        }
                                    }
                                }

                                yLevel += height + (indentSizeY / 2);
                            }

                            yLevel += indentSizeY;
                        }

                        // cool
                        RenderUtil.drawRoundedGlow(
                            startX,
                            ogYlevel,
                            endW,
                            yLevel - ogYlevel - indentSizeY,
                            5,
                            5,
                            new Color(0, 0, 0),
                            false,
                            false,
                            false,
                            false
                        );
                    }
                } else if (cat == ModuleCategory.Configs) {
                    for (File file : Objects.requireNonNull(
                        Paths.get(ModuleManager.configsFileBase.toURI())
                            .toFile()
                            .listFiles()
                    )) {
                        if (
                            !file.getName().equals("keybinds.mushroom") &&
                            !file.getName().equals("config.mushroom")
                        ) {
                            RenderUtil.drawRoundedGlow(
                                startX,
                                yLevel,
                                endW,
                                height,
                                5,
                                5,
                                new Color(0, 0, 0),
                                false,
                                false,
                                false,
                                false
                            );

                            if (
                                Objects.equals(
                                    ShroomClient.selectedConfig,
                                    file.getName()
                                )
                            ) RenderUtil.drawText(
                                file.getName().replace(".mushroom", ""),
                                (int) (startX) + xTextOffset,
                                yLevel + yLevelTextOffset,
                                ThemeUtil.themeColors()[0],
                                textScale
                            );
                            else RenderUtil.drawText(
                                file.getName().replace(".mushroom", ""),
                                (int) (startX) + xTextOffset,
                                yLevel + yLevelTextOffset,
                                new Color(255, 255, 255),
                                textScale
                            );

                            //RenderUtil.drawTexture("on.png", startX + endW - 30, yLevel, 20, 20);

                            if (
                                isHovered2(
                                    mX,
                                    mY,
                                    startX,
                                    yLevel,
                                    endW,
                                    height,
                                    scrolledOffset
                                )
                            ) {
                                if (lmbClicked) {
                                    ModuleManager.removeOldConfig();
                                    ModuleManager.save();

                                    // set old file to NOT loaded.
                                    File old = new File(
                                        ModuleManager.configFileBaseString +
                                        "\\" +
                                        ShroomClient.selectedConfig
                                    );
                                    if (old.isFile()) {
                                        try {
                                            String oldText = Files.readString(
                                                old.toPath()
                                            );
                                            if (oldText.contains("[LOADED] ")) {
                                                oldText = oldText.replaceAll(
                                                    "\\[LOADED] ",
                                                    ""
                                                );
                                                Files.write(
                                                    old.toPath(),
                                                    oldText.getBytes()
                                                );
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    ShroomClient.selectedConfig =
                                        file.getName();

                                    ModuleManager.configsFile = new File(
                                        ModuleManager.configFileBaseString +
                                        "\\" +
                                        ShroomClient.selectedConfig
                                    );
                                    if (ModuleManager.configsFile.isFile()) {
                                        try {
                                            ModuleManager.configsFileText =
                                                Files.readString(
                                                    ModuleManager.configsFile.toPath()
                                                );
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    List<ModuleManager.ModuleWithInfo> module =
                                        ModuleManager.getModules()
                                            .stream()
                                            .toList();

                                    for (ModuleManager.ModuleWithInfo mod : module) {
                                        ModuleManager.load(mod.module());
                                    }

                                    Notifications.notify(
                                        "loaded \"" +
                                        file
                                            .getName()
                                            .replaceAll("\\.mushroom", "") +
                                        "\"",
                                        new Color(100, 250, 160),
                                        1
                                    );
                                    ModuleManager.save();
                                }
                            }

                            yLevel += height + indentSizeY;
                        }
                    }
                } else {
                    for (Module mod : ModuleManager.getSortedModulesByAlphabetAndCategory(
                        cat
                    )) {
                        ModuleManager.ModuleWithInfo inf =
                            ModuleManager.getModuleWithInfo(mod);
                        String name =
                            inf.an().name() +
                            (KeybindManager.getKeyBind(mod.getClass()) == null
                                    ? ""
                                    : (" [" +
                                        KeybindManager.getKeyBind(
                                            mod.getClass()
                                        ) +
                                        "]"));
                        String description = inf.an().description();
                        boolean enabled = mod.isEnabled();

                        if (
                            isHovered2(
                                mX,
                                mY,
                                startX,
                                yLevel,
                                endW,
                                height,
                                scrolledOffset
                            )
                        ) {
                            if (lmbClicked) mod.toggle(false);
                            if (rmbClicked) {
                                if (ClickGUI.expandedModules.contains(mod)) {
                                    ClickGUI.expandedModules.remove(mod);
                                } else {
                                    ClickGUI.expandedModules.add(mod);
                                }
                            }

                            if (mmbClicked) {
                                KeybindManager.listenForBind(mod.getClass());
                            }
                        }

                        float ogYlevel = yLevel;
                        RenderUtil.drawText(
                            name,
                            (int) (startX) + xTextOffset,
                            yLevel + yLevelTextOffset,
                            new Color(255, 255, 255),
                            textScale
                        );

                        Color bgColor = enabled
                            ? new Color(105, 241, 143)
                            : new Color(252, 42, 81);
                        RenderUtil.drawRoundedRect2(
                            toggleButtonX,
                            yLevel + (height / 2f) - (toggleHeight / 2f),
                            toggleWidth,
                            toggleHeight,
                            toggleNormalRadius,
                            bgColor,
                            false,
                            false,
                            false,
                            false
                        );
                        float circleX =
                            toggleButtonX +
                            (enabled
                                    ? (toggleWidth - toggleCircleDiametre)
                                    : 0);
                        RenderUtil.drawRoundedRect2(
                            circleX,
                            yLevel +
                            (height / 2f) -
                            (toggleCircleDiametre / 2f),
                            toggleCircleDiametre,
                            toggleCircleDiametre,
                            toggleCircleDiametre / 2f,
                            new Color(255, 255, 255),
                            false,
                            false,
                            false,
                            false
                        );

                        yLevel += height + indentSizeY;

                        if (ClickGUI.expandedModules.contains(mod)) {
                            float extrasTextScale = 1.2f;
                            float extrasDescScale = 0.7f;

                            if (
                                !Objects.equals(description, "")
                            ) RenderUtil.drawText(
                                description,
                                (int) (startX) + xTextOffset,
                                yLevel - 10,
                                new Color(200, 200, 200),
                                1
                            );
                            if (!Objects.equals(description, "")) yLevel +=
                            indentSizeY;

                            List<ConfigOption> subSettings =
                                ModuleManager.getModuleConfig(mod, false, true);
                            subSettings.sort(
                                Comparator.comparingDouble(
                                    ConfigOption::getOrder
                                )
                            );

                            HashMap<String, ConfigOption> parents =
                                new HashMap<>();
                            for (ConfigOption opt : subSettings) {
                                Field field = opt.getField();
                                if (
                                    field.isAnnotationPresent(
                                        ConfigParentId.class
                                    )
                                ) {
                                    String pId = field
                                        .getAnnotation(ConfigParentId.class)
                                        .value();
                                    parents.put(pId, opt);
                                }
                            }

                            for (ConfigOption opt : subSettings) {
                                Field field = opt.getField();
                                if (
                                    field.isAnnotationPresent(ConfigChild.class)
                                ) {
                                    ConfigChild cC = field.getAnnotation(
                                        ConfigChild.class
                                    );
                                    ConfigOption parent = parents.get(
                                        cC.value()
                                    );

                                    if (cC.parentEnumOrdinal().length != 0) { // It's an enum
                                        ConfigOptionEnum cE =
                                            (ConfigOptionEnum) parent;
                                        if (
                                            !Arrays.stream(
                                                cC.parentEnumOrdinal()
                                            ).anyMatch(
                                                idx ->
                                                    cE.getOption().getIndex() ==
                                                    idx
                                            )
                                        ) continue;
                                    } else {
                                        if (
                                            !((ConfigOptionBoolean) parent).get()
                                        ) continue;
                                    }
                                }

                                if (opt instanceof ConfigOptionBoolean b) {
                                    // Draw the checkbox
                                    RenderUtil.drawText(
                                        opt.getName(),
                                        (int) (startX) + xTextOffset,
                                        yLevel,
                                        new Color(240, 240, 240),
                                        extrasTextScale
                                    );
                                    RenderUtil.drawText(
                                        opt.getDescription(),
                                        (int) (startX) + xTextOffset,
                                        yLevel + 13,
                                        new Color(200, 200, 200),
                                        extrasDescScale
                                    );

                                    float radiusOfCheck = 4;

                                    RenderUtil.drawRoundedRect2(
                                        startX + endW - 20,
                                        yLevel,
                                        10,
                                        10,
                                        radiusOfCheck,
                                        new Color(255, 255, 255),
                                        false,
                                        false,
                                        false,
                                        false
                                    );

                                    if (b.get()) RenderUtil.drawRoundedRect2(
                                        startX + endW - 20,
                                        yLevel,
                                        10,
                                        10,
                                        radiusOfCheck,
                                        new Color(105, 241, 143),
                                        false,
                                        false,
                                        false,
                                        false
                                    );
                                    //RenderUtil.drawTexture("on.png", startX + endW - 20, yLevel, 10, 10);

                                    if (
                                        isHovered2(
                                            mX,
                                            mY,
                                            startX,
                                            yLevel,
                                            endW,
                                            height,
                                            scrolledOffset
                                        )
                                    ) { // Toggle checkbox on click
                                        if (lmbClicked) b.set(!b.get());
                                    }
                                } else if (
                                    opt instanceof ConfigOptionEnum<?> e
                                ) {
                                    ConfigOptionEnum<Object> o =
                                        (ConfigOptionEnum<Object>) e;
                                    // Draw selected value (replace _ w space too)
                                    RenderUtil.drawText(
                                        opt.getName(),
                                        (int) (startX + xTextOffset),
                                        yLevel,
                                        new Color(240, 240, 240),
                                        extrasTextScale
                                    );
                                    RenderUtil.drawText(
                                        opt.getDescription(),
                                        (int) (startX + xTextOffset),
                                        yLevel + 13,
                                        new Color(200, 200, 200),
                                        extrasDescScale
                                    );

                                    String string = e
                                        .getOption()
                                        .getName()
                                        .replaceAll("_", " ")
                                        .replaceAll("DOT", ".");
                                    float highestWidth =
                                        RenderUtil.getFontWidth(string);

                                    ConfigOptionEnum.EnumOption<?> eCurrent =
                                        e.getOption();

                                    while (
                                        eCurrent.next().getIndex() !=
                                        e.getOption().getIndex()
                                    ) {
                                        eCurrent = eCurrent.next();
                                        String string2 = eCurrent
                                            .getName()
                                            .replaceAll("_", " ")
                                            .replaceAll("DOT", ".");

                                        if (
                                            RenderUtil.getFontWidth(string2) >
                                            highestWidth
                                        ) highestWidth =
                                            RenderUtil.getFontWidth(string2);
                                    }

                                    int modeX = (int) (startX +
                                        endW -
                                        20 -
                                        RenderUtil.getFontWidth(string) *
                                            extrasTextScale);

                                    if (
                                        !ClickGUI.modesExtended.contains(
                                            e.getName()
                                        )
                                    ) {
                                        RenderUtil.drawText(
                                            string,
                                            modeX,
                                            yLevel,
                                            new Color(240, 240, 240),
                                            extrasTextScale
                                        );

                                        if (
                                            isHovered2(
                                                mX,
                                                mY,
                                                startX,
                                                yLevel,
                                                endW,
                                                height,
                                                scrolledOffset
                                            )
                                        ) {
                                            if (rmbClicked || lmbClicked) {
                                                ClickGUI.modesExtended.add(
                                                    e.getName()
                                                );
                                                lmbClicked = false;
                                            }
                                        }
                                    } else {
                                        modeX = (int) (startX +
                                            endW -
                                            30 -
                                            highestWidth);
                                        int oldY = yLevel;

                                        int currentIndex = e
                                            .getOption()
                                            .getIndex();
                                        eCurrent = e.getOption();

                                        if (
                                            isHovered2(
                                                mX,
                                                mY,
                                                modeX,
                                                yLevel,
                                                highestWidth + 25,
                                                indentSizeY + 5,
                                                scrolledOffset
                                            ) &&
                                            lmbClicked
                                        ) {
                                            o.set(eCurrent.getValue());
                                            ClickGUI.modesExtended.remove(
                                                e.getName()
                                            );
                                            lmbClicked = false;
                                            lmbDown = false;
                                        }

                                        String string2 = eCurrent
                                            .getName()
                                            .replaceAll("_", " ")
                                            .replaceAll("DOT", ".");

                                        RenderUtil.drawText(
                                            string2,
                                            modeX,
                                            yLevel,
                                            new Color(240, 240, 240),
                                            extrasTextScale
                                        );
                                        yLevel += indentSizeY + 5;

                                        while (
                                            eCurrent.next().getIndex() !=
                                            currentIndex
                                        ) {
                                            eCurrent = eCurrent.next();

                                            string2 = eCurrent
                                                .getName()
                                                .replaceAll("_", " ")
                                                .replaceAll("DOT", ".");

                                            if (
                                                isHovered2(
                                                    mX,
                                                    mY,
                                                    modeX,
                                                    yLevel,
                                                    highestWidth + 25,
                                                    indentSizeY + 5,
                                                    scrolledOffset
                                                ) &&
                                                (lmbClicked || rmbClicked)
                                            ) {
                                                o.set(eCurrent.getValue());
                                                ClickGUI.modesExtended.remove(
                                                    e.getName()
                                                );
                                                lmbClicked = false;
                                                lmbDown = false;
                                            }

                                            RenderUtil.drawText(
                                                string2,
                                                modeX,
                                                yLevel,
                                                new Color(240, 240, 240),
                                                extrasTextScale
                                            );
                                            yLevel += indentSizeY + 5;
                                        }

                                        yLevel -= 2;

                                        RenderUtil.drawRoundedGlow(
                                            modeX - 5,
                                            oldY - 5,
                                            highestWidth + 25,
                                            yLevel - oldY + 5,
                                            5,
                                            5,
                                            new Color(50, 50, 50),
                                            false,
                                            false,
                                            false,
                                            false
                                        );

                                        if (
                                            isHovered2(
                                                mX,
                                                mY,
                                                startX,
                                                oldY,
                                                endW,
                                                yLevel - oldY + 5,
                                                scrolledOffset
                                            ) &&
                                            (rmbClicked || lmbClicked)
                                        ) {
                                            ClickGUI.modesExtended.remove(
                                                e.getName()
                                            );
                                            rmbClicked = false;
                                        }

                                        yLevel -= 10;
                                    }
                                } else if (
                                    opt instanceof ConfigOptionMinMax m
                                ) {
                                    minMaxDrawnAlr.add(m.getName());

                                    float newXForSlider = startX + 10;
                                    float newYForSlider = yLevel + 10;
                                    float newWForSlider = endW - 20;
                                    float newHForSlider = 10f;
                                    float radiusSlider = 5;

                                    float sStartX = 300;
                                    float sStartY = newYForSlider + (3f);
                                    float sW = 160;

                                    // get max fraction
                                    double selectedFracMin =
                                        (Double.parseDouble(
                                                m.min.get().toString()
                                            ) -
                                            m.min.getMinimum()) /
                                        (m.min.getMaximum() -
                                            m.min.getMinimum());
                                    double selectedFracMax =
                                        (Double.parseDouble(
                                                m.max.get().toString()
                                            ) -
                                            m.max.getMinimum()) /
                                        (m.max.getMaximum() -
                                            m.max.getMinimum());

                                    double minXcoord =
                                        sStartX +
                                        sW * selectedFracMin -
                                        radiusSlider;
                                    double maxXcoord =
                                        sStartX +
                                        sW * selectedFracMax -
                                        radiusSlider;

                                    // Draw Slider
                                    RenderUtil.drawRect(
                                        sStartX,
                                        sStartY,
                                        sW,
                                        2,
                                        new Color(50, 50, 50)
                                    );
                                    RenderUtil.drawRect(
                                        (float) minXcoord,
                                        sStartY,
                                        (float) (maxXcoord + 6 - minXcoord),
                                        2,
                                        ThemeUtil.themeColors()[0]
                                    );

                                    RenderUtil.drawTriangle(
                                        (float) (minXcoord),
                                        sStartY - radiusSlider + 1.5f,
                                        4,
                                        9,
                                        ThemeUtil.themeColors()[0]
                                    );
                                    RenderUtil.drawTriangle(
                                        (float) (maxXcoord + 6),
                                        sStartY - radiusSlider + 1.5f,
                                        -4,
                                        9,
                                        ThemeUtil.themeColors()[0]
                                    );

                                    //RenderUtil.drawRoundedRect2((float) (minXcoord), sStartY - radiusSlider + 1.5f, radiusSlider * 2, radiusSlider * 2, radiusSlider, ThemeUtil.themeColors()[0], false, false, false, false);
                                    //RenderUtil.drawRoundedRect2((float) (maxXcoord), sStartY - radiusSlider + 1.5f, radiusSlider * 2, radiusSlider * 2, radiusSlider, ThemeUtil.themeColors()[0], false, false, false, false);

                                    /*
                                    // Draw selected portion
                                    RenderUtil.drawRoundedRect2(sStartX + twoScaledW, sStartY + twoScaledH, (float) (rSW * selectedFrac), sH - (twoScaledH * 2), radiusSlider, ThemeUtil.themeColors()[0], false,true,false,true);
                                    // Draw unselected portion
                                    RenderUtil.drawRoundedRect2((float) (sStartX + twoScaledW + rSW * selectedFrac), sStartY + twoScaledH, (float) (rSW * (1 - selectedFrac)), sH - (twoScaledH * 2), radiusSlider, new Color(50, 50, 50), true,false,true,false);
                                     */

                                    // Draw current Max
                                    String seperator = "/";
                                    RenderUtil.drawTextShadow(
                                        m.min.get().toString() +
                                        seperator +
                                        m.max.get().toString(),
                                        (int) (newXForSlider +
                                            newWForSlider -
                                            (8f) -
                                            textRenderer.getWidth(
                                                m.min.get().toString() +
                                                seperator +
                                                m.max.get().toString()
                                            )),
                                        (int) (newYForSlider -
                                            (textRenderer.fontHeight / 2) -
                                            5),
                                        ThemeUtil.themeColors()[1]
                                    );

                                    // Draw name
                                    RenderUtil.drawText(
                                        opt
                                            .getName()
                                            .replace(" MIN", "")
                                            .replace(" MAX", ""),
                                        (int) (startX) + xTextOffset,
                                        yLevel,
                                        new Color(240, 240, 240),
                                        extrasTextScale
                                    );
                                    RenderUtil.drawText(
                                        opt.getDescription(),
                                        (int) (startX) + xTextOffset,
                                        yLevel + 13,
                                        new Color(200, 200, 200),
                                        extrasDescScale
                                    );

                                    if (
                                        mX >= sStartX &&
                                        mX <=
                                            minXcoord +
                                                radiusSlider * 2 +
                                                radiusSlider &&
                                        mY >= newYForSlider &&
                                        mY <= newYForSlider + (newHForSlider)
                                    ) {
                                        if (lmbDown) {
                                            double fraction =
                                                (mX - (sStartX + (2f))) /
                                                (sW - (8f));
                                            double value =
                                                (m.min.getMaximum() -
                                                        m.min.getMinimum()) *
                                                    fraction +
                                                m.min.getMinimum();
                                            Class<?> t = m.min.getType();
                                            if (t.equals(Integer.class)) {
                                                ((ConfigOptionNumber<
                                                            Integer
                                                        >) m.min).set(
                                                        (int) Math.round(value)
                                                    );
                                            } else if (t.equals(Long.class)) {
                                                ((ConfigOptionNumber<
                                                            Long
                                                        >) m.min).set(
                                                        Math.round(value)
                                                    );
                                            } else if (t.equals(Float.class)) {
                                                ((ConfigOptionNumber<
                                                            Float
                                                        >) m.min).set(
                                                        (float) value
                                                    );
                                            } else if (t.equals(Double.class)) {
                                                ((ConfigOptionNumber<
                                                            Double
                                                        >) m.min).set(value);
                                            }

                                            double minValue =
                                                Double.parseDouble(
                                                    m.min.get().toString()
                                                );
                                            double maxValue =
                                                Double.parseDouble(
                                                    m.max.get().toString()
                                                );

                                            if (minValue > maxValue) {
                                                if (t.equals(Integer.class)) {
                                                    ((ConfigOptionNumber<
                                                                Integer
                                                            >) m.max).set(
                                                            (int) Math.round(
                                                                minValue
                                                            )
                                                        );
                                                } else if (
                                                    t.equals(Long.class)
                                                ) {
                                                    ((ConfigOptionNumber<
                                                                Long
                                                            >) m.max).set(
                                                            Math.round(minValue)
                                                        );
                                                } else if (
                                                    t.equals(Float.class)
                                                ) {
                                                    ((ConfigOptionNumber<
                                                                Float
                                                            >) m.max).set(
                                                            (float) minValue
                                                        );
                                                } else if (
                                                    t.equals(Double.class)
                                                ) {
                                                    ((ConfigOptionNumber<
                                                                Double
                                                            >) m.max).set(
                                                            minValue
                                                        );
                                                }
                                            }
                                        }
                                    }

                                    if (
                                        mX >= maxXcoord - radiusSlider &&
                                        mX <= sStartX + sW &&
                                        mY >= newYForSlider &&
                                        mY <= newYForSlider + (newHForSlider)
                                    ) {
                                        if (lmbDown) {
                                            double fraction =
                                                (mX - (sStartX + (2f))) /
                                                (sW - (8f));
                                            double value =
                                                (m.max.getMaximum() -
                                                        m.max.getMinimum()) *
                                                    fraction +
                                                m.max.getMinimum();
                                            Class<?> t = m.max.getType();
                                            if (t.equals(Integer.class)) {
                                                ((ConfigOptionNumber<
                                                            Integer
                                                        >) m.max).set(
                                                        (int) Math.round(value)
                                                    );
                                            } else if (t.equals(Long.class)) {
                                                ((ConfigOptionNumber<
                                                            Long
                                                        >) m.max).set(
                                                        Math.round(value)
                                                    );
                                            } else if (t.equals(Float.class)) {
                                                ((ConfigOptionNumber<
                                                            Float
                                                        >) m.max).set(
                                                        (float) value
                                                    );
                                            } else if (t.equals(Double.class)) {
                                                ((ConfigOptionNumber<
                                                            Double
                                                        >) m.max).set(value);
                                            }

                                            double minValue =
                                                Double.parseDouble(
                                                    m.min.get().toString()
                                                );
                                            double maxValue =
                                                Double.parseDouble(
                                                    m.max.get().toString()
                                                );

                                            if (maxValue < minValue) {
                                                if (t.equals(Integer.class)) {
                                                    ((ConfigOptionNumber<
                                                                Integer
                                                            >) m.min).set(
                                                            (int) Math.round(
                                                                maxValue
                                                            )
                                                        );
                                                } else if (
                                                    t.equals(Long.class)
                                                ) {
                                                    ((ConfigOptionNumber<
                                                                Long
                                                            >) m.min).set(
                                                            Math.round(maxValue)
                                                        );
                                                } else if (
                                                    t.equals(Float.class)
                                                ) {
                                                    ((ConfigOptionNumber<
                                                                Float
                                                            >) m.min).set(
                                                            (float) maxValue
                                                        );
                                                } else if (
                                                    t.equals(Double.class)
                                                ) {
                                                    ((ConfigOptionNumber<
                                                                Double
                                                            >) m.min).set(
                                                            maxValue
                                                        );
                                                }
                                            }
                                        }
                                    }
                                } else if (
                                    opt instanceof ConfigOptionNumber<?> n
                                ) {
                                    if (
                                        minMaxDrawnAlr.contains(n.getName())
                                    ) continue;

                                    float newXForSlider = startX + 10;
                                    float newYForSlider = yLevel + 10;
                                    float newWForSlider = endW - 20;
                                    float newHForSlider = 10f;
                                    float radiusSlider = 5;

                                    float sStartX = 300;
                                    float sStartY = newYForSlider + (3f);
                                    float sW = 160;

                                    double selectedFrac =
                                        (Double.parseDouble(
                                                n.get().toString()
                                            ) -
                                            n.getMinimum()) /
                                        (n.getMaximum() - n.getMinimum());

                                    // Draw Slider
                                    RenderUtil.drawRect(
                                        sStartX,
                                        sStartY,
                                        sW,
                                        2,
                                        new Color(50, 50, 50)
                                    );
                                    RenderUtil.drawRoundedRect2(
                                        (float) (sStartX +
                                            sW * selectedFrac -
                                            radiusSlider),
                                        sStartY - radiusSlider + 1.5f,
                                        radiusSlider * 2,
                                        radiusSlider * 2,
                                        radiusSlider,
                                        ThemeUtil.themeColors()[0],
                                        false,
                                        false,
                                        false,
                                        false
                                    );

                                    /*
                                    // Draw selected portion
                                    RenderUtil.drawRoundedRect2(sStartX + twoScaledW, sStartY + twoScaledH, (float) (rSW * selectedFrac), sH - (twoScaledH * 2), radiusSlider, ThemeUtil.themeColors()[0], false,true,false,true);
                                    // Draw unselected portion
                                    RenderUtil.drawRoundedRect2((float) (sStartX + twoScaledW + rSW * selectedFrac), sStartY + twoScaledH, (float) (rSW * (1 - selectedFrac)), sH - (twoScaledH * 2), radiusSlider, new Color(50, 50, 50), true,false,true,false);

                                     */

                                    // Draw current value
                                    RenderUtil.drawTextShadow(
                                        n.get().toString(),
                                        (int) (newXForSlider +
                                            newWForSlider -
                                            (8f) -
                                            textRenderer.getWidth(
                                                n.get().toString()
                                            )),
                                        (int) (newYForSlider -
                                            (textRenderer.fontHeight / 2) -
                                            5),
                                        ThemeUtil.themeColors()[1]
                                    );

                                    // Draw name
                                    RenderUtil.drawText(
                                        opt.getName(),
                                        (int) (startX) + xTextOffset,
                                        yLevel,
                                        new Color(240, 240, 240),
                                        extrasTextScale
                                    );
                                    RenderUtil.drawText(
                                        opt.getDescription(),
                                        (int) (startX) + xTextOffset,
                                        yLevel + 13,
                                        new Color(200, 200, 200),
                                        extrasDescScale
                                    );

                                    if (
                                        mX >= sStartX &&
                                        mX <= sStartX + sW &&
                                        mY >= newYForSlider &&
                                        mY <= newYForSlider + (newHForSlider)
                                    ) {
                                        if (lmbDown) {
                                            double fraction =
                                                (mX - (sStartX + (2f))) /
                                                (sW - (8f));
                                            double value =
                                                (n.getMaximum() -
                                                        n.getMinimum()) *
                                                    fraction +
                                                n.getMinimum();
                                            Class<?> t = n.getType();
                                            if (t.equals(Integer.class)) {
                                                ((ConfigOptionNumber<
                                                            Integer
                                                        >) n).set(
                                                        (int) Math.round(value)
                                                    );
                                            } else if (t.equals(Long.class)) {
                                                ((ConfigOptionNumber<
                                                            Long
                                                        >) n).set(
                                                        Math.round(value)
                                                    );
                                            } else if (t.equals(Float.class)) {
                                                ((ConfigOptionNumber<
                                                            Float
                                                        >) n).set(
                                                        (float) value
                                                    );
                                            } else if (t.equals(Double.class)) {
                                                ((ConfigOptionNumber<
                                                            Double
                                                        >) n).set(value);
                                            }
                                        }
                                    }
                                }

                                yLevel += height + (indentSizeY / 2);
                            }

                            yLevel += indentSizeY;
                        }

                        // cool
                        RenderUtil.drawRoundedGlow(
                            startX,
                            ogYlevel,
                            endW,
                            yLevel - ogYlevel - indentSizeY,
                            5,
                            5,
                            new Color(0, 0, 0),
                            false,
                            false,
                            false,
                            false
                        );
                    }
                }

                if (scrolledOffset > 0) {
                    ClickGUI.scrollOffsets[getCategoryNumber()] -=
                    C.mc.getTickDelta() * (scrolledOffset / 10f);
                    ClickGUI.scrollOffsetsTEMP[getCategoryNumber()] =
                        ClickGUI.scrollOffsets[getCategoryNumber()];
                } else if ((-scrolledOffset + guiHeight) > yLevel) {
                    ClickGUI.scrollOffsets[getCategoryNumber()] +=
                    C.mc.getTickDelta() *
                    (((-scrolledOffset + guiHeight) - yLevel) / 10f);
                    ClickGUI.scrollOffsetsTEMP[getCategoryNumber()] =
                        ClickGUI.scrollOffsets[getCategoryNumber()];
                } else if (
                    ClickGUI.scrollOffsets[getCategoryNumber()] !=
                    ClickGUI.scrollOffsetsTEMP[getCategoryNumber()]
                ) ClickGUI.scrollOffsets[getCategoryNumber()] +=
                (ClickGUI.scrollOffsetsTEMP[getCategoryNumber()] -
                    ClickGUI.scrollOffsets[getCategoryNumber()]) *
                C.mc.getTickDelta() *
                0.1f;

                context.getMatrices().pop();
                mY += (int) scrolledOffset;

                RenderUtil.drawRoundedRect2(
                    seperatorX + 5,
                    0,
                    guiWidth - seperatorX - 5,
                    startingY - 10,
                    radius,
                    backgroundColor,
                    true,
                    false,
                    true,
                    true
                );
                if (cat == ModuleCategory.Search) {
                    ArrayList<ModuleManager.ModuleWithInfo> theMods =
                        ModuleManager.getModules();
                    List<String> mods = theMods
                        .stream()
                        .map(mod -> mod.an().name())
                        .collect(Collectors.toList());
                    // Sort high score to low score
                    if (
                        !search.getText().isEmpty()
                    ) ModuleManager.sortedModules = FuzzySearch.extractAll(
                        search.getText(),
                        mods,
                        40
                    )
                        .stream()
                        .sorted(
                            Comparator.comparingInt(
                                r -> Integer.MAX_VALUE - r.getScore()
                            )
                        )
                        .map(r -> theMods.get(r.getIndex()).module())
                        .collect(Collectors.toList());
                    else ModuleManager.sortedModules = theMods
                        .stream()
                        .sorted(
                            Comparator.comparingInt(
                                mmm -> mmm.an().name().compareTo("a")
                            )
                        )
                        .map(ModuleManager.ModuleWithInfo::module)
                        .collect(Collectors.toList());

                    search.setSuggestion(
                        search.getText().isEmpty() ? "Search" : ""
                    );

                    if (
                        isHovered(
                            mX,
                            mY,
                            search.getX(),
                            search.getY(),
                            search.getWidth(),
                            search.getHeight()
                        )
                    ) {
                        if (lmbClicked) {
                            search.setFocused(true);
                            lmbClicked = false;
                            lmbDown = false;
                        }
                    } else if (lmbClicked || rmbClicked || mmbClicked) {
                        search.setFocused(false);
                    }

                    search.setDrawsBackground(false);

                    float textSize = 3;

                    context.getMatrices().push();
                    context
                        .getMatrices()
                        .translate((int) (seperatorX + 20), 6, 0);
                    context.getMatrices().scale(textSize, textSize, 0);
                    search.render(context, (int) mX, (int) mY, 0);
                    context.getMatrices().pop();
                } else RenderUtil.drawText(
                    cat.name(),
                    textX,
                    textY,
                    new Color(255, 255, 255),
                    titleSize
                );
            }

            i++;
        }

        //ClickGUI.scrollOffsets = new double[]{0, 0, 0, 0, 0, 0};
        //ClickGUI.scrollOffsetsTEMP = new double[]{0, 0, 0, 0, 0, 0};

        lmbClicked = false;
        rmbClicked = false;
        mmbClicked = false;

        context.disableScissor();

        if (
            isHovered(
                mX,
                mY,
                seperatorX,
                0,
                guiWidth - seperatorX,
                startingY - 10
            ) &&
            lmbDown &&
            !dragging
        ) {
            dragging = true;

            mouseXold = mXrel;
            mouseYold = mYrel;

            oldHudX = xPos;
            oldHudY = yPos;
        }

        if (!lmbDown) dragging = false;

        double mouseMovedX = mXrel - mouseXold;
        double mouseMovedY = mYrel - mouseYold;

        if (dragging) {
            ClickGUI.xPos = (int) (oldHudX + mouseMovedX);
            ClickGUI.yPos = (int) (oldHudY + mouseMovedY);

            RenderUtil.drawRoundedGlow(
                0,
                0,
                guiWidth,
                guiHeight,
                radius,
                5,
                new Color(243, 104, 123),
                100,
                false,
                false,
                false,
                false
            );
        }

        context.getMatrices().pop();
    }

    // i like records i think this is good java update (now can we make the language not look dumb?)
    private record textStuff(String text, int x, int y, Color color) {}

    public int getCategoryNumber() {
        for (int i = 0; i < ModuleCategory.values().length; i++) {
            if (lastCat == ModuleCategory.values()[i]) {
                return i;
            }
        }

        return 0;
    }
}
