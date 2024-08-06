package com.shroomclient.shroomclientnextgen.modules.impl.player;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.EventBus;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.events.impl.PacketEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderTickEvent;
import com.shroomclient.shroomclientnextgen.mixin.PlayerMoveC2SPacketAccessor;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.modules.impl.combat.Criticals;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.Scaffold;
import com.shroomclient.shroomclientnextgen.util.*;
import java.awt.*;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

@RegisterModule(
    name = "No Fall",
    uniqueId = "nofall",
    description = "Removes Fall Damage",
    category = ModuleCategory.Player
)
public class NoFall extends Module {

    @ConfigParentId("render")
    @ConfigOption(name = "Render", description = "", order = 2)
    public static Boolean render = true;

    @ConfigChild("render")
    @ConfigOption(
        name = "Always Render",
        description = "Always Render The GUI",
        order = 3
    )
    public static Boolean alwaysrender = false;

    @ConfigChild("render")
    @ConfigOption(
        name = "Glow",
        description = "Renders A Glow Around The GUI",
        order = 4
    )
    public static Boolean glow = true;

    public static boolean noFallBlinking = false;
    public static boolean vulcanevil = false;
    public static boolean offgroundbleh = false;

    public enum Mode {
        Off_Ground,
        Vulcan,
        Vulcan_Yport,
        Blink,
        Hypixel,
    }

    @ConfigMode
    @ConfigOption(name = "Mode", description = "", order = 1)
    public Mode mode = Mode.Hypixel;

    int ticks = 0;
    boolean gogogo = false;
    Color onoffcolor = new Color(255, 0, 0);
    String text = "Nofall";
    long hitground = 0;
    boolean hypixelNofAll = false;
    public boolean stop = false;

    public static int countAir(Vec3d pos) {
        int count = 0;

        double x = pos.getX();
        double y = pos.getY();
        if ((int) y == 0) return 0;
        double z = pos.getZ();
        BlockPos p = new BlockPos((int) x, ((int) y) - 1, (int) z);
        int rY = p.getY();
        while (rY >= 0) {
            if (
                C.w()
                    .getBlockState(new BlockPos(p.getX(), rY, p.getZ()))
                    .getBlock() instanceof
                AirBlock
            ) {
                count++;
            } else {
                return count;
            }
            rY--;
        }
        return count;
    }

    public static boolean isOverVoid() {
        final BlockPos block = new BlockPos(
            (int) C.p().getX(),
            (int) C.p().getY(),
            (int) C.p().getZ()
        );
        if (Blocks.VOID_AIR.equals(C.w().getBlockState(block).getBlock())) {
            return false;
        }

        final Box player = C.p().getBoundingBox();
        return !C.w()
            .getCollisions(
                C.p(),
                new Box(
                    player.minX,
                    0.0,
                    player.minZ,
                    player.maxX,
                    player.maxY,
                    player.maxZ
                )
            )
            .iterator()
            .hasNext();
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {
        noFallBlinking = false;
        BlinkUtil.setOutgoingBlink(false);
        BlinkUtil.setIncomingBlink(false);
    }

    @SubscribeEvent
    public void onRenderTick(RenderTickEvent e) {
        if (render) {
            if (!alwaysrender) {
                if (noFallBlinking || vulcanevil || offgroundbleh) {
                    RenderUtil.setContext(e.drawContext);
                    switch (mode) {
                        case Blink -> text = "Blinking " + ticks;
                        case Vulcan, Off_Ground, Hypixel -> text = "Nofall";
                    }
                    int boxWidth = (int) (RenderUtil.getFontWidth(text) + 2);
                    int boxHeight = 13;

                    RenderUtil.drawCenteredRoundedRect(
                        C.mc.getWindow().getScaledWidth() / 1.5f,
                        C.mc.getWindow().getScaledHeight() -
                        (C.mc.getWindow().getScaledHeight() / 4f) +
                        3,
                        boxWidth + 3,
                        boxHeight,
                        5,
                        new Color(20, 20, 20, 100),
                        false,
                        false,
                        false,
                        false
                    );
                    if (glow) RenderUtil.drawCenteredRoundedGlow(
                        C.mc.getWindow().getScaledWidth() / 1.5f,
                        C.mc.getWindow().getScaledHeight() -
                        (C.mc.getWindow().getScaledHeight() / 4f) +
                        3,
                        boxWidth + 3,
                        boxHeight,
                        5,
                        5,
                        ThemeUtil.themeColors()[0],
                        false,
                        false,
                        false,
                        false
                    );
                    RenderUtil.drawTextShadow(
                        text,
                        (int) (C.mc.getWindow().getScaledWidth() / 1.5 -
                            (boxWidth / 2)) +
                        1,
                        C.mc.getWindow().getScaledHeight() -
                        (C.mc.getWindow().getScaledHeight() / 4) +
                        boxHeight / 4 -
                        4,
                        ThemeUtil.themeColors()[0]
                    );
                }
            } else {
                if (noFallBlinking || vulcanevil || offgroundbleh) {
                    onoffcolor = new Color(0, 255, 0);
                } else {
                    onoffcolor = new Color(255, 0, 0);
                }
                RenderUtil.setContext(e.drawContext);
                switch (mode) {
                    case Blink -> text = "Blinking";
                    case Vulcan, Off_Ground, Hypixel -> text = "Nofall";
                }
                int boxWidth = (int) (RenderUtil.getFontWidth(text) + 2);
                int boxHeight = 13;
                RenderUtil.drawCenteredRoundedRect(
                    C.mc.getWindow().getScaledWidth() / 1.5f,
                    C.mc.getWindow().getScaledHeight() -
                    (C.mc.getWindow().getScaledHeight() / 4f) +
                    3,
                    boxWidth + 3,
                    boxHeight,
                    5,
                    new Color(20, 20, 20, 100),
                    false,
                    false,
                    false,
                    false
                );
                if (glow) RenderUtil.drawCenteredRoundedGlow(
                    C.mc.getWindow().getScaledWidth() / 1.5f,
                    C.mc.getWindow().getScaledHeight() -
                    (C.mc.getWindow().getScaledHeight() / 4f) +
                    3,
                    boxWidth + 3,
                    boxHeight,
                    5,
                    5,
                    ThemeUtil.themeColors()[0],
                    false,
                    false,
                    false,
                    false
                );
                RenderUtil.drawTextShadow(
                    text,
                    (int) (C.mc.getWindow().getScaledWidth() / 1.5 -
                        (boxWidth / 2)) +
                    1,
                    C.mc.getWindow().getScaledHeight() -
                    (C.mc.getWindow().getScaledHeight() / 4) +
                    boxHeight / 4 -
                    4,
                    onoffcolor
                );
            }
        }
    }

    @SubscribeEvent(EventBus.Priority.HIGHEST)
    public void onMotion(MotionEvent.Pre e) {
        if (
            !ModuleManager.isEnabled(Criticals.class) ||
            Criticals.mode != Criticals.Mode.Off_Ground
        ) {
            switch (mode) {
                case Vulcan -> {
                    if (!isOverVoid() && !stop) {
                        if (
                            countAir(new Vec3d(e.getX(), e.getY(), e.getZ())) >
                                1 &&
                            !e.isOnGround() &&
                            !isOverVoid() &&
                            !ModuleManager.isEnabled(Scaffold.class)
                        ) {
                            if (!e.isOnGround()) {
                                BlinkUtil.setOutgoingBlink(true);
                                vulcanevil = true;
                            } else {
                                BlinkUtil.setOutgoingBlink(false);
                                vulcanevil = false;
                            }
                            if (C.p().fallDistance >= 4.0 && !e.isOnGround()) {
                                C.p().fallDistance = 0;
                                e.setOnGround(true);
                                C.p().setVelocity(0, -0.1, 0);
                            }
                        } else {
                            BlinkUtil.setOutgoingBlink(false);
                            vulcanevil = false;
                        }
                    }
                }
                case Vulcan_Yport -> {
                    if (!isOverVoid() && !stop) {
                        if (
                            countAir(new Vec3d(e.getX(), e.getY(), e.getZ())) >
                                1 &&
                            !e.isOnGround() &&
                            !isOverVoid() &&
                            !ModuleManager.isEnabled(Scaffold.class)
                        ) {
                            C.p().setSneaking(true);
                            C.p()
                                .setVelocity(
                                    C.p().getVelocity().getX(),
                                    -10,
                                    C.p().getVelocity().getZ()
                                );
                            e.setOnGround(true);
                        }
                    }
                }
                case Blink -> {
                    if (!noFallBlinking) ticks = 0;

                    if (!ModuleManager.isEnabled(Scaffold.class)) {
                        if (
                            countAir(new Vec3d(e.getX(), e.getY(), e.getZ())) >
                                3 &&
                            !e.isOnGround() &&
                            !isOverVoid()
                        ) {
                            noFallBlinking = true;
                        }
                        if (noFallBlinking) {
                            if (!C.p().isOnGround()) {
                                e.setOnGround(true);

                                BlinkUtil.setIncomingBlink(true);
                                BlinkUtil.setOutgoingBlink(true);
                                C.p().fallDistance = 0.0f;
                                if (isOverVoid()) {
                                    noFallBlinking = false;
                                    BlinkUtil.setIncomingBlink(false);
                                    BlinkUtil.setOutgoingBlink(false);
                                } else {
                                    ticks++;
                                }
                            } else {
                                noFallBlinking = false;
                                BlinkUtil.setIncomingBlink(false);
                                BlinkUtil.setOutgoingBlink(false);
                            }
                        }
                    } else {
                        if (noFallBlinking) {
                            noFallBlinking = false;
                            BlinkUtil.setIncomingBlink(false);
                            BlinkUtil.setOutgoingBlink(false);
                        }
                    }
                }
                case Hypixel -> {
                    if (C.p().fallDistance > 3) {
                        hypixelNofAll = true;
                    }

                    if (
                        C.p().fallDistance < 3 &&
                        !C.p().isOnGround() &&
                        hypixelNofAll
                    ) {
                        hypixelNofAll = false;
                    }

                    if (C.p().isOnGround() && hypixelNofAll) {
                        if (
                            !C.mc.options.jumpKey.isPressed()
                        ) MovementUtil.jump();
                    }
                }
            }
        }

        if (C.p().horizontalCollision) {
            if (!C.p().isClimbing()) stop = true;
        } else if (stop) stop = false;
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send.Pre e) {
        if (
            ModuleManager.isEnabled(Criticals.class) &&
            Criticals.mode == Criticals.Mode.Off_Ground
        ) return;

        if (e.getPacket() instanceof PlayerMoveC2SPacket p) {
            if (mode == Mode.Off_Ground || hypixelNofAll) {
                ((PlayerMoveC2SPacketAccessor) p).setOnGround(false);
                offgroundbleh = true;
            } else {
                offgroundbleh = false;
            }
        }
    }
}
