package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.config.*;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.*;
import com.shroomclient.shroomclientnextgen.mixin.PlayerInteractEntityC2SPacketAccessor;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.*;
import java.awt.*;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.Identifier;

@RegisterModule(
    name = "Target HUD",
    uniqueId = "thud",
    description = "Shows Player Info For Current Enemy",
    category = ModuleCategory.Render,
    enabledByDefault = true
)
public class TargetHud extends Module {

    @ConfigMode
    @ConfigParentId("mode")
    @ConfigOption(name = "Mode", description = "", order = 1)
    public static Mode mode = Mode.Mushroom;

    @ConfigChild(value = "mode", parentEnumOrdinal = 4)
    @ConfigOption(name = "IntelliJ Colors", description = "", order = 1.01)
    public static ColorsIntelliJ IntellijColors = ColorsIntelliJ.Dark_Purple;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(name = "Radius", description = "", max = 30, order = 1.1)
    public static Integer radius = 0;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(name = "Head Radius", description = "", max = 20, order = 1.2)
    public static Integer headRadius = 0;

    @ConfigChild(value = "mode", parentEnumOrdinal = { 0, 2 })
    @ConfigOption(
        name = "Health Radius",
        description = "",
        max = 5,
        order = 1.3
    )
    public static Integer healthRadius = 0;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(name = "Show Held Item", description = "", order = 1.4)
    public static Boolean showHeldItem = false;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Show Hurt",
        description = "Makes The Players Head Turn Red When Hurt",
        order = 1.5
    )
    public static Boolean showHut = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Shadow",
        description = "Draws A Shadow Instead Of A Glow",
        order = 1.6
    )
    public static Boolean shadowWow = true;

    @ConfigChild(value = "mode", parentEnumOrdinal = 0)
    @ConfigOption(
        name = "Theme Colors",
        description = "Uses Your Current Theme Color",
        order = 1.7
    )
    public static Boolean themeColors = false;

    @ConfigHide
    @ConfigOption(
        name = "X Position",
        description = "Open Chat And Drag It.",
        min = 1,
        max = 1000,
        order = 102
    )
    public static Float xPos = 10F;

    @ConfigHide
    @ConfigOption(
        name = "Y Position",
        description = "Open Chat And Drag It.",
        min = 1,
        max = 1000,
        order = 101
    )
    public static Float yPos = 10F;

    @ConfigOption(
        name = "Constant Scale",
        description = "Makes the X and Y Scale The Same",
        order = 4
    )
    public static Boolean lockXandY = true;

    @ConfigOption(
        name = "Scale X",
        description = "",
        min = 0.1,
        max = 3,
        precision = 2,
        order = 99
    )
    public static Float scaleX = 0.5f;

    @ConfigOption(
        name = "Scale Y",
        description = "",
        min = 0.1,
        max = 3,
        precision = 2,
        order = 100
    )
    public static Float scaleY = 0.5f;

    @ConfigOption(
        name = "ESP Mode",
        description = "Draws An Effect Over Current Enemy",
        order = 2
    )
    public Espmode espmode = Espmode.Circle;

    long HitTime = 0;
    boolean resizing = false;
    float oldHudSizeX = 0;
    float oldHudSizeY = 0;
    boolean dragging = false;
    double mouseXold = 0;
    double mouseYold = 0;
    double oldHudX = 0;
    double oldHudY = 0;
    private @Nullable Entity lastHitEntity = null;

    public String Name = "e";
    private double Health = 20;
    private float Distance = 6.1f;

    @SubscribeEvent
    public void onRender(RenderTickEvent e) {
        RenderUtil.setContext(e.drawContext);
        RenderUtil.matrix = e.matrixStack;

        // INSANE BYPASS $$$$
        if (lockXandY) scaleY = scaleX;

        // draw target hud!
        if (C.mc.currentScreen == null && lastHitEntity != null) {
            if (System.currentTimeMillis() - HitTime > 1500) {
                lastHitEntity = null;
                return;
            }

            drawTargetHud(e.drawContext, lastHitEntity, false);
        }

        if (lastHitEntity != null) {
            if (
                !lastHitEntity.isAlive() || lastHitEntity.isSpectator()
            ) lastHitEntity = null;
        }
    }

    public float[] getTargetHudSquare() {
        return new float[] { xPos, yPos, width, height, 15 };
    }

    public float[] getTargetHudSquareScaled() {
        float[] coordinates = new float[] { xPos, yPos, width, height, 15 };

        coordinates[2] = coordinates[2] * scaleX;
        coordinates[3] = coordinates[3] * scaleY;

        return coordinates;
    }

    // maybe add more
    Identifier fakeIds = new Identifier("shroomclientnextgen", "mclovin.png");

    @SubscribeEvent
    public void onDrawScreen(RenderScreenEvent e) {
        if (C.mc.currentScreen instanceof ChatScreen) {
            float[] guiSize = getTargetHudSquare();

            double mouseMovedX = e.mouseX - mouseXold;
            double mouseMovedY = e.mouseY - mouseYold;

            if (dragging) {
                drawTargetHud(e.context, C.p(), true);

                xPos = (float) (oldHudX + mouseMovedX);
                yPos = (float) (oldHudY + mouseMovedY);
            } else {
                drawTargetHud(e.context, C.p(), false);

                doTargetHudTransformations(e.context, guiSize);

                RenderUtil.drawRect(
                    guiSize[2] - 5,
                    guiSize[3] - 5,
                    10,
                    10,
                    new Color(255, 255, 255)
                );

                reverseTargetHudTransformations(e.context, guiSize);

                if (resizing) {
                    float scaleXnew = 0.1f;
                    float scaleYnew = 0.1f;

                    // comparing numbers isnt slow so this while loop doesnt affect fps.
                    while (
                        guiSize[0] + (guiSize[2] * scaleXnew) <= e.mouseX
                    ) scaleXnew += 0.01f;
                    while (
                        guiSize[1] + (guiSize[3] * scaleYnew) <= e.mouseY
                    ) scaleYnew += 0.01f;

                    scaleX = scaleXnew;
                    scaleY = scaleYnew;
                }
            }
        }
    }

    public void doTargetHudTransformations(
        DrawContext context,
        float[] guiSize
    ) {
        context.getMatrices().translate(guiSize[0], guiSize[1], 0);
        context.getMatrices().scale(scaleX, scaleY, 0);
    }

    public void reverseTargetHudTransformations(
        DrawContext context,
        float[] guiSize
    ) {
        context.getMatrices().scale(1 / scaleX, 1 / scaleY, 0);
        context.getMatrices().translate(-guiSize[0], -guiSize[1], 0);
    }

    float width = 250;
    float height = 80;

    public void drawTargetHud(DrawContext c, Entity entity, boolean outline) {
        float[] guiSize = getTargetHudSquare();

        doTargetHudTransformations(c, guiSize);

        switch (mode) {
            case Mushroom -> {
                PlayerEntity ent = (PlayerEntity) entity;
                RenderUtil.drawRoundedRect2(
                    0,
                    0,
                    guiSize[2],
                    guiSize[3],
                    radius,
                    new Color(19, 19, 19, 200),
                    false,
                    false,
                    false,
                    false
                );

                if (!shadowWow) RenderUtil.drawRoundedGlow(
                    0,
                    0,
                    guiSize[2],
                    guiSize[3],
                    radius,
                    6,
                    ThemeUtil.themeColors()[0],
                    false,
                    false,
                    false,
                    false
                );
                else RenderUtil.drawRoundedGlow(
                    0,
                    0,
                    guiSize[2],
                    guiSize[3],
                    radius,
                    10,
                    new Color(19, 19, 19, 200),
                    200,
                    false,
                    false,
                    false,
                    false
                );

                if (outline) RenderUtil.drawRoundedGlow(
                    0,
                    0,
                    guiSize[2],
                    guiSize[3],
                    radius,
                    6,
                    new Color(255, 255, 255),
                    200,
                    false,
                    false,
                    false,
                    false
                );

                if (C.mc.getNetworkHandler() != null) {
                    PlayerListEntry p = C.mc
                        .getNetworkHandler()
                        .getPlayerListEntry(ent.getGameProfile().getId());
                    SkinTextures textures = p == null
                        ? DefaultSkinHelper.getSkinTextures(
                            ent.getGameProfile()
                        )
                        : p.getSkinTextures();
                    if (showHut) {
                        float opacity = (float) ((150 *
                                (Math.tan(ent.hurtTime / 10f))) /
                            255f);
                        RenderUtil.drawRoundedRectImage(
                            textures.texture(),
                            8,
                            72,
                            8,
                            72,
                            0,
                            8,
                            8,
                            8,
                            8,
                            64,
                            64,
                            headRadius,
                            1,
                            1 - opacity,
                            1 - opacity,
                            1
                        );
                    } else RenderUtil.drawRoundedRectImage(
                        textures.texture(),
                        8,
                        72,
                        8,
                        72,
                        0,
                        8,
                        8,
                        8,
                        8,
                        64,
                        64,
                        headRadius
                    );
                }

                int scalar = 8;
                int headEnd = 8 + 64;

                float textScale1 = 2.4f;
                c.getMatrices().scale(textScale1, textScale1, 0);

                if (!themeColors) RenderUtil.drawTextShadow(
                    ent.getName().getLiteralString(),
                    (int) ((headEnd + 8) / textScale1),
                    (int) (10 / textScale1),
                    new Color(255, 6, 50, 150)
                );
                else RenderUtil.drawTextShadow(
                    ent.getName().getLiteralString(),
                    (int) ((headEnd + 8) / textScale1),
                    (int) (10 / textScale1),
                    ThemeUtil.themeColors()[0]
                );
                c.getMatrices().scale(1f / textScale1, 1f / textScale1, 0);

                float textScale2 = 1.7f;
                c.getMatrices().scale(textScale2, textScale2, 0);
                float armor = 0;
                for (ItemStack s : ent.getArmorItems()) {
                    if (s.getItem() instanceof ArmorItem ai) {
                        armor += ai.getProtection();
                    }
                }

                String infoString =
                    (MathUtil.roundTo(
                            ent.getHealth() + ent.getAbsorptionAmount(),
                            1
                        )) +
                    "/" +
                    MathUtil.roundTo(ent.getMaxHealth(), 1);
                if (showHeldItem) infoString = (MathUtil.roundTo(
                        ent.getHealth() + ent.getAbsorptionAmount(),
                        1
                    )) +
                "/" +
                MathUtil.roundTo(ent.getMaxHealth(), 1) +
                (armor == 0 ? "" : " | " + armor) +
                (Objects.equals(
                            ent.getMainHandStack().getItem().toString(),
                            "air"
                        )
                        ? ""
                        : " | " + ent.getMainHandStack().getName().getString());

                if (!themeColors) RenderUtil.drawTextShadow(
                    infoString,
                    (int) ((headEnd + 8) / textScale2),
                    (int) (((int) (height -
                                8 -
                                16 -
                                (RenderUtil.fontSize * textScale2))) /
                        textScale2),
                    new Color(255, 6, 50, 150)
                );
                else RenderUtil.drawTextShadow(
                    infoString,
                    (int) ((headEnd + 8) / textScale2),
                    (int) (((int) (height -
                                8 -
                                16 -
                                (RenderUtil.fontSize * textScale2))) /
                        textScale2),
                    ThemeUtil.themeColors()[0]
                );
                c.getMatrices().scale(1f / textScale2, 1f / textScale2, 0);

                width = Math.max(
                    Math.max(
                        (RenderUtil.getFontWidth(
                                ent.getName().getLiteralString()
                            ) *
                            textScale1) +
                        85,
                        (RenderUtil.getFontWidth(infoString) * textScale2) + 85
                    ),
                    220
                );
                height = 80;

                float maxW = width - 8 - 8 - 8 - 8 * scalar;
                float w = maxW * (ent.getHealth() / ent.getMaxHealth());

                if (!themeColors) RenderUtil.drawRoundedRect2(
                    headEnd + 6,
                    height - 8 - 10,
                    w,
                    10,
                    healthRadius,
                    new Color(255, 6, 50, 150),
                    false,
                    false,
                    false,
                    false
                );
                else RenderUtil.drawRoundedRect2(
                    headEnd + 6,
                    height - 8 - 10,
                    w,
                    10,
                    healthRadius,
                    ThemeUtil.themeColors()[0],
                    false,
                    false,
                    false,
                    false
                );
            }
            case McLovin -> {
                width = 230;
                height = 100;

                PlayerEntity ent = (PlayerEntity) entity;

                RenderUtil.drawTexture(
                    fakeIds,
                    0,
                    0,
                    (int) width,
                    (int) height
                );

                // drawing head
                if (C.mc.getNetworkHandler() != null) {
                    int playerHeadX = 1;
                    int playerHeadY = 1;
                    int playerHeadW = 80;
                    int playerHeadH = 67;

                    float opacity = (float) ((150 *
                            (Math.tan(ent.hurtTime / 10f))) /
                        255f);
                    PlayerListEntry p = C.mc
                        .getNetworkHandler()
                        .getPlayerListEntry(ent.getGameProfile().getId());
                    SkinTextures textures = p == null
                        ? DefaultSkinHelper.getSkinTextures(
                            ent.getGameProfile()
                        )
                        : p.getSkinTextures();

                    RenderUtil.drawRoundedRectImage(
                        textures.texture(),
                        playerHeadX,
                        playerHeadX + playerHeadW,
                        playerHeadY,
                        playerHeadY + playerHeadH,
                        0,
                        8,
                        8,
                        8,
                        8,
                        64,
                        64,
                        0,
                        1,
                        1 - opacity,
                        1 - opacity,
                        1
                    );
                }

                // health bar
                float healthBarHeight = 9;
                float healthBarWidth = 78;
                float healthPercent = (ent.getHealth() / ent.getMaxHealth());

                RenderUtil.drawRect(
                    2,
                    68f,
                    healthBarWidth * healthPercent,
                    healthBarHeight,
                    new Color(255, 255, 255)
                );

                // name
                float scaleName = 1.5f;
                c.getMatrices().scale(scaleName, scaleName, scaleName);
                RenderUtil.drawString(
                    "§l" + ent.getName().getString(),
                    (int) (150f / scaleName) -
                    (RenderUtil.getFontWidth("§l" + ent.getName().getString()) /
                        2),
                    (int) (80f / scaleName),
                    new Color(0, 0, 0)
                );
                c
                    .getMatrices()
                    .scale(1 / scaleName, 1 / scaleName, 1 / scaleName);
            }
            case Raven -> {
                float textScale = 1.5f;
                PlayerEntity ent = (PlayerEntity) entity;
                String healthColor = ent.getHealth() < 7
                    ? "c"
                    : (ent.getHealth() > 15 ? "a" : "e");
                String infoText =
                    (entity.getDisplayName() == null
                            ? ent.getName().getString()
                            : ent.getDisplayName().getString()) +
                    " §" +
                    healthColor +
                    MathUtil.roundTo(ent.getHealth(), 1) +
                    " " +
                    (C.p().getHealth() >= ent.getHealth() ? "§aW" : "§cL");
                float textXoffset = 7 / textScale;

                // background
                width = RenderUtil.getFontWidth(infoText) * textScale +
                (textXoffset * 2) +
                3;
                height = 50;

                float radius = 12;

                RenderUtil.drawRoundedRect2(
                    0,
                    0,
                    width,
                    height,
                    radius,
                    new Color(23, 23, 23, 200),
                    false,
                    false,
                    false,
                    false
                );
                RenderUtil.drawRoundedGlow(
                    0,
                    0,
                    width,
                    height,
                    radius,
                    2,
                    ThemeUtil.themeColors()[0],
                    false,
                    false,
                    false,
                    false
                );

                // text
                c.getMatrices().scale(textScale, textScale, textScale);
                RenderUtil.drawString(
                    infoText,
                    textXoffset,
                    10 / textScale,
                    new Color(227, 27, 106)
                );
                c
                    .getMatrices()
                    .scale(1 / textScale, 1 / textScale, 1 / textScale);

                // health bar
                float healthBarHeight = 10;
                float healthBarWidth = width - radius - 10;
                float healthPercent = (ent.getHealth() / ent.getMaxHealth());

                RenderUtil.drawRoundedFade(
                    8,
                    height - healthBarHeight * 2,
                    healthBarWidth * healthPercent,
                    healthBarHeight,
                    healthRadius,
                    ThemeUtil.themeColors()[0],
                    ThemeUtil.themeColors()[1],
                    false,
                    false,
                    false,
                    false
                );
            }
            case Novoline -> {
                PlayerEntity ent = (PlayerEntity) entity;

                RenderUtil.drawRect(
                    0,
                    0,
                    width,
                    height,
                    new Color(40, 40, 40, 255)
                );

                // drawing head
                int playerHeadX = 4;
                int playerHeadY = 4;
                int playerHeadW = 55;
                int playerHeadH = 55;

                if (C.mc.getNetworkHandler() != null) {
                    float opacity = (float) ((150 *
                            (Math.tan(ent.hurtTime / 10f))) /
                        255f);
                    PlayerListEntry p = C.mc
                        .getNetworkHandler()
                        .getPlayerListEntry(ent.getGameProfile().getId());
                    SkinTextures textures = p == null
                        ? DefaultSkinHelper.getSkinTextures(
                            ent.getGameProfile()
                        )
                        : p.getSkinTextures();

                    RenderUtil.drawRoundedRectImage(
                        textures.texture(),
                        playerHeadX,
                        playerHeadX + playerHeadW,
                        playerHeadY,
                        playerHeadY + playerHeadH,
                        0,
                        8,
                        8,
                        8,
                        8,
                        64,
                        64,
                        0,
                        1,
                        1 - opacity,
                        1 - opacity,
                        1
                    );
                }

                // health bar
                float healthBarHeight = 25;
                float healthBarWidth = width - playerHeadW - 20;
                float healthPercent = (ent.getHealth() / ent.getMaxHealth());

                RenderUtil.drawRect(
                    playerHeadW + 10,
                    35,
                    healthBarWidth,
                    healthBarHeight,
                    new Color(30, 30, 30)
                );
                RenderUtil.drawRect(
                    playerHeadW + 10,
                    35,
                    healthBarWidth * healthPercent,
                    healthBarHeight,
                    new Color(159, 125, 179)
                );

                float scaleHealthPercent = 2f;
                c
                    .getMatrices()
                    .scale(
                        scaleHealthPercent,
                        scaleHealthPercent,
                        scaleHealthPercent
                    );
                String healthPercentText = (int) (healthPercent * 100) + "%";
                float percentageTextX = (int) ((playerHeadW +
                        (healthBarWidth / 2) -
                        (RenderUtil.getFontWidth(healthPercentText) / 2)));
                RenderUtil.drawTextShadow(
                    healthPercentText,
                    (int) (percentageTextX / scaleHealthPercent),
                    (int) (40 / scaleHealthPercent),
                    new Color(255, 255, 255)
                );
                c
                    .getMatrices()
                    .scale(
                        1 / scaleHealthPercent,
                        1 / scaleHealthPercent,
                        1 / scaleHealthPercent
                    );

                // name
                float scaleName = 2f;
                c.getMatrices().scale(scaleName, scaleName, scaleName);
                RenderUtil.drawTextShadow(
                    ent.getName().getString(),
                    (int) ((playerHeadW + 10) / scaleName),
                    (int) (10f / scaleName),
                    new Color(255, 255, 255)
                );
                c
                    .getMatrices()
                    .scale(1 / scaleName, 1 / scaleName, 1 / scaleName);

                width = RenderUtil.getFontWidth(ent.getName().getString()) *
                    scaleName +
                (int) ((playerHeadW + 10) / scaleName) +
                45;
                width = Math.max(200, width);
                height = 65;
            }
            case IntelliJ -> {
                PlayerEntity ent = (PlayerEntity) entity;
                String[] texts = {
                    "public String Name = \"",
                    "public boolean Winning = ",
                    "private float Distance = ",
                    "private double Health = ",
                    "private int Armor = ",
                };

                int armor = 0;
                for (ItemStack s : ent.getArmorItems()) {
                    if (s.getItem() instanceof ArmorItem ai) {
                        armor += ai.getProtection();
                    }
                }

                float textSize = 2;

                texts[0] += ent.getName().getString() + "\"";
                texts[1] += (C.p().getHealth() >= ent.getHealth());
                texts[2] += (MathUtil.roundTo(C.p().distanceTo(ent), 1)) + "F";
                texts[3] += (MathUtil.roundTo(ent.getHealth(), 2));
                texts[4] += armor;

                width = RenderUtil.getFontWidth(texts[0]);
                for (String string : texts) {
                    width = Math.max(RenderUtil.getFontWidth(string), width);
                }

                width *= textSize;

                width += 30;
                width = Math.max(200, width);
                height = 110;

                RenderUtil.drawRoundedGlow(
                    0,
                    0,
                    width,
                    height,
                    0,
                    5,
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
                    0,
                    0,
                    width,
                    height,
                    new Color(29, 29, 38)
                );
                else RenderUtil.drawRect(
                    0,
                    0,
                    width,
                    height,
                    new Color(43, 43, 43)
                );

                int i = 0;
                int xCordBase = 5;
                int yCordBase = 5;
                int ySeperation = 21;

                for (String string : texts) {
                    String[] stringSplit = string.split(" ");

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
                            splitPart.startsWith("false");

                        switch (IntellijColors) {
                            case Default -> {
                                textColor = new Color(169, 183, 198);

                                if (shouldBeOrange) textColor = new Color(
                                    203,
                                    111,
                                    46
                                );
                                else if (splitPart.startsWith("\"")) textColor =
                                    new Color(84, 132, 88);
                                else if (
                                    splitPart.toUpperCase().equals(splitPart) &&
                                    !splitPart.startsWith("=")
                                ) textColor = new Color(104, 151, 187);
                            }
                            case Dark_Purple -> {
                                if (shouldBeOrange) textColor = new Color(
                                    223,
                                    138,
                                    94
                                );
                                else if (splitPart.startsWith("\"")) textColor =
                                    new Color(73, 158, 97);
                                else if (
                                    splitPart.toUpperCase().equals(splitPart) &&
                                    !splitPart.startsWith("=")
                                ) textColor = new Color(77, 172, 240);
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
                            RenderUtil.getFontWidth(" ") -
                            4),
                        ySeperation * i + yCordBase,
                        new Color(203, 111, 46),
                        textSize
                    );

                    i++;
                }
            }
        }

        reverseTargetHudTransformations(c, guiSize);
    }

    @SubscribeEvent
    public void onDraw3d(Render3dEvent e) {
        switch (espmode) {
            case Circle -> {
                Entity target = lastHitEntity;
                //if (ModuleManager.isEnabled(KillAura.class)) target = KillAura.lastTargetEnt;
                //if (ModuleManager.isEnabled(KillAura2.class)) target = KillAura2.target;

                if (target != null) {
                    RenderUtil.drawCoolCircleGoBRRRRRRR(
                        target,
                        e.partialTicks,
                        e.matrixStack,
                        ThemeUtil.themeColors()[0]
                    );
                }
            }
            case Box -> {
                Entity target = lastHitEntity;
                //if (ModuleManager.isEnabled(KillAura.class)) target = KillAura.lastTargetEnt;
                //if (ModuleManager.isEnabled(KillAura2.class)) target = KillAura2.target;

                if (target != null) {
                    RenderUtil.drawBox2(
                        target.getX() - 0.5d,
                        target.getY(),
                        target.getZ() - 0.5d,
                        1d,
                        1.95d,
                        1d,
                        e.partialTicks,
                        e.matrixStack,
                        ThemeUtil.themeColors()[0],
                        100
                    );
                }
            }
            case Spinny_Square -> {
                Entity target = lastHitEntity;

                if (target != null) {
                    RenderUtil.fractionsHollowRoundedRect3d(
                        e.partialTicks,
                        e.matrixStack,
                        target
                    );
                }
            }
            case None -> {
                /* nofin! */
            }
        }
    }

    @SubscribeEvent
    public void packetEvent(PacketEvent.Send.Post e) {
        if (e.getPacket() instanceof PlayerInteractEntityC2SPacket p) {
            int eId = ((PlayerInteractEntityC2SPacketAccessor) p).getEntityId();
            if (
                C.w().getEntityById(eId) instanceof PlayerEntity ent &&
                !TargetUtil.isBot(ent)
            ) {
                lastHitEntity = C.w().getEntityById(eId);
                HitTime = System.currentTimeMillis();
            }
        }
    }

    @SubscribeEvent
    public void ChatEvent(ScreenClickedEvent e) {
        if (C.mc.currentScreen instanceof ChatScreen) {
            float[] size = getTargetHudSquareScaled();

            if (
                e.mouseX >= xPos + size[2] - (5 * scaleX) &&
                e.mouseX >= xPos + size[3] + (5 * scaleX) &&
                e.mouseY >= yPos + size[3] - (5 * scaleY) &&
                e.mouseY <= yPos + size[3] + (5 * scaleY)
            ) {
                resizing = e.button == 0;
                mouseXold = e.mouseX;
                mouseYold = e.mouseY;

                oldHudSizeX = scaleX;
                oldHudSizeY = scaleY;
            } else if (
                e.mouseX >= xPos &&
                e.mouseX <= xPos + size[2] &&
                e.mouseY >= yPos &&
                e.mouseY <= yPos + size[3]
            ) {
                dragging = e.button == 0;
                mouseXold = e.mouseX;
                mouseYold = e.mouseY;

                oldHudX = xPos;
                oldHudY = yPos;
            }

            if (!e.down && e.button == 0) {
                dragging = false;
                resizing = false;
            }
        }
    }

    @Override
    protected void onEnable() {
        lastHitEntity = null;
    }

    @Override
    protected void onDisable() {}

    public enum Mode {
        Mushroom,
        McLovin,
        Raven,
        Novoline,
        IntelliJ,
    }

    public enum ColorsIntelliJ {
        Default,
        Dark_Purple,
    }

    public enum Espmode {
        Circle,
        Box,
        Spinny_Square,
        None,
    }
}
