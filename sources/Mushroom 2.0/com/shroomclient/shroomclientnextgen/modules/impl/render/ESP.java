package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.Render3dEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import com.shroomclient.shroomclientnextgen.util.TargetUtil;
import com.shroomclient.shroomclientnextgen.util.ThemeUtil;
import java.awt.*;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

@RegisterModule(
    name = "ESP",
    uniqueId = "esp",
    description = "See Players Through Walls",
    category = ModuleCategory.Render,
    enabledByDefault = true
)
public class ESP extends Module {

    @ConfigParentId("mcESP")
    @ConfigOption(name = "Outline", description = "Outlines Players", order = 1)
    public static Boolean minecraftGlow = true;

    @ConfigChild("mcESP")
    @ConfigOption(
        name = "Team Color",
        description = "Sets The Color Of The ESP To The Targets Team Color",
        order = 2
    )
    public static Boolean teamColor = false;

    @ConfigChild("mcESP")
    @ConfigOption(
        name = "Visibility Check",
        description = "Sets The Color Depending On If The Person Is Visible",
        order = 3
    )
    public static Boolean visibilityCheck = true;

    @ConfigOption(
        name = "Item ESP",
        description = "Dropped Item ESP",
        order = 4
    )
    public static Boolean droppedItems = true;

    /*
    @ConfigParentId("circleglow")
    @ConfigOption(
            name = "Circle Glow",
            description = "Draws A Cool Circle Glow Thing",
            order = 5
    )
    public static Boolean circleGlow = false;
    @ConfigChild("circleglow")
    @ConfigOption(
            name = "Circle Glow Opacity",
            description = "Glow Opacity",
            min = 10,
            max = 255,
            order = 6
    )
    public static Integer glowOpacity = 100;
    @ConfigChild("circleglow")
    @ConfigOption(
            name = "Circle Glow Accuracy",
            description = "Glow Accuracy",
            min = 10,
            max = 100,
            order = 7
    )
    public static Integer glowAccuracy = 60;
    @ConfigChild("circleglow")
    @ConfigOption(
            name = "Circle Glow Size",
            description = "Glow Size",
            min = 0.3f,
            max = 3f,
            precision = 3,
            order = 8
    )
    public static Float glowSize = 1f;
     */

    @ConfigOption(
        name = "HP",
        description = "Draws A Hp Bar Next To Players",
        order = 11
    )
    public static Boolean hp = true;

    @ConfigParentId("twodee")
    @ConfigOption(
        name = "2D ESP",
        description = "Draws A Square Around Players",
        order = 9
    )
    public static Boolean twoDee = true;

    @ConfigChild("twodee")
    @ConfigOption(
        name = "2D ESP Line Width",
        description = "",
        min = 0.01f,
        max = 0.3f,
        precision = 3,
        order = 10
    )
    public static Float lineWidth = 0.05f;

    @ConfigParentId("images")
    @ConfigOption(
        name = "Images",
        description = "Renders An Image Over Players",
        order = 11
    )
    public static Boolean animeGirl = false;

    @ConfigMode
    @ConfigChild("images")
    @ConfigOption(name = "Image Mode", description = "", order = 12)
    public static Mode mode = Mode.Anime_Girl;

    Identifier[] fictionalWomen = new Identifier[] {
        new Identifier("shroomclientnextgen", "animegirl1.png"),
        new Identifier("shroomclientnextgen", "animegirl2.png"),
        new Identifier("shroomclientnextgen", "animegirl3.png"),
        new Identifier("shroomclientnextgen", "animegirl4.png"),
        new Identifier("shroomclientnextgen", "animegirl5.png"),
        new Identifier("shroomclientnextgen", "animegirl6.png"),
        new Identifier("shroomclientnextgen", "animegirl7.png"),
        new Identifier("shroomclientnextgen", "animegirl8.png"),
    };
    Identifier[] scale = new Identifier[] {
        new Identifier("shroomclientnextgen", "scale.png"),
    };
    Identifier[] Swig1 = new Identifier[] {
        new Identifier("shroomclientnextgen", "swig.png"),
    };
    Identifier[] SillyBlack = new Identifier[] {
        new Identifier("shroomclientnextgen", "lang.png"),
    };
    Identifier[] toDraw = {};

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    boolean loaded = false;

    @SubscribeEvent
    public void draw3dEvent(Render3dEvent e) {
        if (!loaded) {
            C.mc.gameRenderer.loadPostProcessor(
                new Identifier("shaders/post/entity_outline.json")
            );
            loaded = true;
        }

        for (AbstractClientPlayerEntity player : C.w().getPlayers()) {
            if (player != C.p() && !TargetUtil.isBot(player)) {
                if (animeGirl) {
                    switch (mode) {
                        case Anime_Girl -> {
                            toDraw = fictionalWomen;
                        }
                        case Swig -> {
                            toDraw = Swig1;
                        }
                        case Scale -> {
                            toDraw = scale;
                        }
                        // use default to guarantee todraw isnt null, and doesnt crack
                        default -> {
                            toDraw = SillyBlack;
                        }
                    }

                    int index = player.getId() % toDraw.length;
                    RenderUtil.drawAnimeGirlESP(
                        player,
                        0.8f,
                        2f,
                        e.partialTicks,
                        e.matrixStack,
                        toDraw[index]
                    );
                }
                double playX =
                    player.lastRenderX +
                    (player.getX() - player.lastRenderX) * e.partialTicks;
                double playY =
                    player.lastRenderY +
                    (player.getY() - player.lastRenderY) * e.partialTicks;
                double playZ =
                    player.lastRenderZ +
                    (player.getZ() - player.lastRenderZ) * e.partialTicks;

                Vec3d playerPos = new Vec3d(playX, playY, playZ);

                Color color = ThemeUtil.themeColors(
                    (int) playerPos.x,
                    (int) playerPos.z,
                    30,
                    0.005f
                )[0];
                if (teamColor) color = new Color(player.getTeamColorValue());
                else if (visibilityCheck) {
                    if (C.p().canSee(player)) color =
                        ThemeUtil.getGradient()[0];
                    else color = ThemeUtil.getGradient()[1];
                }

                /*
                if (circleGlow) {
                    for (int i = 0; i < glowAccuracy; i++) {
                        float growSize = (float) (glowAccuracy - i) / glowAccuracy;

                        Color colorWithOpac = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (glowOpacity - (growSize * glowOpacity)));

                        RenderUtil.drawGlowESP(playerPos, (growSize * 0.8) * glowSize, (growSize * 1.6) * glowSize, e.partialTicks, e.matrixStack, colorWithOpac);
                    }
                }
                 */

                if (twoDee) {
                    RenderUtil.draw2dESP(
                        playerPos,
                        0.8f,
                        2f,
                        lineWidth,
                        e.partialTicks,
                        e.matrixStack,
                        color
                    );
                }
                if (hp) {
                    float playerHealthPercent =
                        player.getHealth() / player.getMaxHealth();
                    int maxNumber = 200;

                    int healthGreen = maxNumber;
                    int healthRed = maxNumber;

                    // if statment not needed
                    if (playerHealthPercent > 0.5f) healthRed = Math.max(
                        Math.min(
                            (int) ((0.5f - (playerHealthPercent - 0.5f)) *
                                maxNumber *
                                2),
                            maxNumber
                        ),
                        0
                    );
                    else healthGreen = Math.max(
                        Math.min(
                            (int) (((playerHealthPercent)) * maxNumber * 2),
                            maxNumber
                        ),
                        0
                    );

                    RenderUtil.drawHealthBar(
                        playerPos,
                        0.8f,
                        1.8f * playerHealthPercent,
                        0.05f,
                        e.partialTicks,
                        e.matrixStack,
                        new Color(healthRed, healthGreen, 0)
                    );
                }
            }
        }
    }

    public enum Mode {
        Anime_Girl,
        Swig,
        Scale,
        Silly_Blackie,
    }
}
