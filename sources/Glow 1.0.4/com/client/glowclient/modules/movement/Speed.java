package com.client.glowclient.modules.movement;

import com.client.glowclient.events.*;
import net.minecraft.entity.*;
import com.client.glowclient.sponge.mixinutils.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.client.*;
import com.client.glowclient.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.modules.*;

public class Speed extends ModuleContainer
{
    public static BooleanValue sprint;
    public static BooleanValue useTimer;
    public static double G;
    private double d;
    public static BooleanValue spoofCam;
    private static boolean A;
    public static nB mode;
    private double b;
    
    @SubscribeEvent
    public void d(final EventUpdate eventUpdate) {
        if (Speed.mode.e().equals("BunnyHop")) {
            final float n = 0.48f;
            if (u.M((Entity)Wrapper.mc.player)) {
                if (Speed.spoofCam.M()) {
                    HookTranslator.v20 = false;
                }
                if (Speed.useTimer.M()) {
                    Ta.M(1.09f);
                }
                float n2;
                if (Wrapper.mc.player.motionY < -0.05) {
                    n2 = 1.79f;
                }
                else {
                    n2 = 1.18f;
                }
                if (Wrapper.mc.player.moveForward > 0.0f && !Wrapper.mc.player.collidedHorizontally) {
                    Wrapper.mc.player.setSprinting(true);
                }
                if (Wrapper.mc.player.onGround) {
                    final EntityPlayerSP player = Wrapper.mc.player;
                    player.motionY += n;
                    final EntityPlayerSP player2 = Wrapper.mc.player;
                    player2.motionX *= n2;
                    final EntityPlayerSP player3 = Wrapper.mc.player;
                    player3.motionZ *= n2;
                }
            }
        }
    }
    
    @SubscribeEvent
    public void M(final Cd cd) {
        if (!Speed.mode.e().equals("Development") || !u.M((Entity)Wrapper.mc.player) || u.M((Entity)Wrapper.mc.player) > 1.0 || cd.getPacket() instanceof CPacketPlayer$PositionRotation) {}
    }
    
    @SubscribeEvent
    public void B(final EventUpdate eventUpdate) {
        if (Speed.mode.e().equals("Strafe")) {
            final float n = 1.75f;
            final float n2 = 0.26f;
            if (Speed.useTimer.M()) {
                Ta.M(1.09f);
            }
            if (u.M((Entity)Wrapper.mc.player)) {
                if (Speed.spoofCam.M()) {
                    HookTranslator.v20 = false;
                }
                if (Wrapper.mc.player.onGround) {
                    u.M((Entity)Wrapper.mc.player, n);
                    final EntityPlayerSP player = Wrapper.mc.player;
                    player.motionY += 0.48;
                    return;
                }
                u.M(n2, (Entity)Wrapper.mc.player);
            }
        }
    }
    
    @Override
    public String M() {
        return Speed.mode.e();
    }
    
    @SubscribeEvent
    public void E(final EventUpdate eventUpdate) {
        if (Speed.mode.e().equals("GroundBoost")) {
            final double motionY = 0.4;
            float n = 0.26f;
            if (Wrapper.mc.player.onGround) {
                Speed.G = Wrapper.mc.player.posY;
            }
            if (u.M((Entity)Wrapper.mc.player) <= 1.0) {
                final double d = 1.0;
                this.b = 1.0;
                this.d = d;
            }
            if (u.M((Entity)Wrapper.mc.player) && !Wrapper.mc.player.collidedHorizontally && !fa.D((Entity)Wrapper.mc.player) && fa.M((Entity)Wrapper.mc.player)) {
                Speed.A = true;
                HookTranslator.v20 = (Speed.spoofCam.M() && Wrapper.mc.player.getRidingEntity() == null);
                final boolean nextBoolean = new Random().nextBoolean();
                if (Wrapper.mc.player.posY >= Speed.G + motionY) {
                    Wrapper.mc.player.motionY = -motionY;
                    ++this.b;
                    if (this.b == 1.0) {
                        n = 0.075f;
                    }
                    if (this.b == 2.0) {
                        n = 0.175f;
                    }
                    if (this.b == 3.0) {
                        n = 0.275f;
                    }
                    if (this.b == 4.0) {
                        n = 0.35f;
                    }
                    if (this.b == 5.0) {
                        n = 0.375f;
                    }
                    if (this.b == 6.0) {
                        n = 0.4f;
                    }
                    if (this.b == 7.0) {
                        n = 0.425f;
                    }
                    if (this.b == 8.0) {
                        n = 0.45f;
                    }
                    if (this.b == 9.0) {
                        n = 0.475f;
                    }
                    if (this.b == 10.0) {
                        n = 0.5f;
                    }
                    if (this.b == 11.0) {
                        n = 0.5f;
                    }
                    if (this.b == 12.0) {
                        n = 0.525f;
                    }
                    if (this.b == 13.0) {
                        n = 0.525f;
                    }
                    if (this.b == 14.0) {
                        n = 0.535f;
                    }
                    if (this.b == 15.0) {
                        n = 0.535f;
                    }
                    if (this.b == 16.0) {
                        n = 0.545f;
                    }
                    if (this.b >= 17.0) {
                        n = 0.545f;
                    }
                    if (Speed.useTimer.M()) {
                        Ta.M(1.0f);
                    }
                }
                float n2 = 0.0f;
                Label_0752: {
                    if (Wrapper.mc.player.posY == Speed.G) {
                        Wrapper.mc.player.motionY = motionY;
                        ++this.d;
                        if (this.d == 1.0) {
                            n = 0.075f;
                        }
                        if (this.d == 2.0) {
                            n = 0.175f;
                        }
                        if (this.d == 3.0) {
                            n = 0.375f;
                        }
                        if (this.d == 4.0) {
                            n = 0.6f;
                        }
                        if (this.d == 5.0) {
                            n = 0.775f;
                        }
                        if (this.d == 6.0) {
                            n = 0.825f;
                        }
                        if (this.d == 7.0) {
                            n = 0.875f;
                        }
                        if (this.d == 8.0) {
                            n = 0.925f;
                        }
                        if (this.d == 9.0) {
                            n = 0.975f;
                        }
                        if (this.d == 10.0) {
                            n = 1.05f;
                        }
                        if (this.d == 11.0) {
                            n = 1.1f;
                        }
                        if (this.d == 12.0) {
                            n = 1.1f;
                        }
                        if (this.d == 13.0) {
                            n = 1.15f;
                        }
                        if (this.d == 14.0) {
                            n = 1.15f;
                        }
                        if (this.d == 15.0) {
                            n = 1.175f;
                        }
                        if (this.d == 16.0) {
                            n = 1.175f;
                        }
                        if (this.d >= 17.0) {
                            n = 1.2f;
                        }
                        if (Speed.useTimer.M()) {
                            if (nextBoolean) {
                                Ta.M(1.3f);
                                n2 = n;
                                break Label_0752;
                            }
                            Ta.M(1.0f);
                        }
                    }
                    n2 = n;
                }
                u.M(n2, (Entity)Wrapper.mc.player);
                return;
            }
            if (Speed.A) {
                Wrapper.mc.player.motionY = -0.1;
                Speed.A = false;
            }
            HookTranslator.v20 = false;
            final double b = 0.0;
            this.d = 0.0;
            this.b = b;
            this.d();
        }
    }
    
    @SubscribeEvent
    public void e(final EventUpdate eventUpdate) {
        if (Speed.sprint.M() && u.M((Entity)Wrapper.mc.player) && Wrapper.mc.player.getRidingEntity() == null && !Wrapper.mc.player.collidedHorizontally && !Wrapper.mc.player.isSprinting()) {
            Wrapper.mc.player.setSprinting(true);
        }
    }
    
    @Override
    public void E() {
        if (Speed.mode.e().equals("Boost") || Speed.mode.e().equals("GroundBoost")) {
            Wrapper.mc.player.motionY = -0.1;
        }
        HookTranslator.v20 = false;
        final float n = 1.0f;
        final double b = 0.0;
        this.d = 0.0;
        this.b = b;
        Ta.M(n);
    }
    
    private void d() {
        final float n = (float)Math.toRadians(Wrapper.mc.player.rotationYaw);
        if (fa.D((Entity)Wrapper.mc.player)) {
            if (Wrapper.mc.gameSettings.keyBindForward.isKeyDown() && !Wrapper.mc.gameSettings.keyBindSneak.isKeyDown() && Wrapper.mc.player.onGround && !Speed.mode.e().equals("Tunnel")) {
                final EntityPlayerSP player = Wrapper.mc.player;
                player.motionX -= DegreeUtils.D(n) * 0.15;
                final EntityPlayerSP player2 = Wrapper.mc.player;
                player2.motionZ += DegreeUtils.M(n) * 0.15;
            }
        }
        else if (Wrapper.mc.player.collidedHorizontally) {
            if (Wrapper.mc.gameSettings.keyBindForward.isKeyDown() && !Wrapper.mc.gameSettings.keyBindSneak.isKeyDown() && Wrapper.mc.player.onGround) {
                final EntityPlayerSP player3 = Wrapper.mc.player;
                player3.motionX -= DegreeUtils.D(n) * 0.03;
                final EntityPlayerSP player4 = Wrapper.mc.player;
                player4.motionZ += DegreeUtils.M(n) * 0.03;
            }
        }
        else if (!fa.M((Entity)Wrapper.mc.player)) {
            if (Wrapper.mc.gameSettings.keyBindForward.isKeyDown() && !Wrapper.mc.gameSettings.keyBindSneak.isKeyDown() && Wrapper.mc.player.onGround) {
                final EntityPlayerSP player5 = Wrapper.mc.player;
                player5.motionX -= DegreeUtils.D(n) * 0.03;
                final EntityPlayerSP player6 = Wrapper.mc.player;
                player6.motionZ += DegreeUtils.M(n) * 0.03;
            }
        }
        else {
            Wrapper.mc.player.motionX = 0.0;
            Wrapper.mc.player.motionZ = 0.0;
        }
    }
    
    @SubscribeEvent
    public void k(final EventUpdate eventUpdate) {
        if (Speed.mode.e().equals("Boost")) {
            final double motionY = 0.4;
            float n = 0.26f;
            if (Wrapper.mc.player.onGround) {
                Speed.G = Wrapper.mc.player.posY;
            }
            if (u.M((Entity)Wrapper.mc.player) <= 1.0) {
                final double d = 1.0;
                this.b = 1.0;
                this.d = d;
            }
            if (u.M((Entity)Wrapper.mc.player) && !Wrapper.mc.player.collidedHorizontally && !fa.D((Entity)Wrapper.mc.player) && fa.M((Entity)Wrapper.mc.player)) {
                Speed.A = true;
                HookTranslator.v20 = (Speed.spoofCam.M() && Wrapper.mc.player.getRidingEntity() == null);
                final boolean nextBoolean = new Random().nextBoolean();
                if (Wrapper.mc.player.posY >= Speed.G + motionY) {
                    Wrapper.mc.player.motionY = -motionY;
                    ++this.b;
                    if (this.b == 1.0) {
                        n = 0.075f;
                    }
                    if (this.b == 2.0) {
                        n = 0.15f;
                    }
                    if (this.b == 3.0) {
                        n = 0.175f;
                    }
                    if (this.b == 4.0) {
                        n = 0.2f;
                    }
                    if (this.b == 5.0) {
                        n = 0.225f;
                    }
                    if (this.b == 6.0) {
                        n = 0.25f;
                    }
                    if (this.b >= 7.0) {
                        n = 0.27895f;
                    }
                    if (Speed.useTimer.M()) {
                        Ta.M(1.0f);
                    }
                }
                float n2 = 0.0f;
                Label_0465: {
                    if (Wrapper.mc.player.posY == Speed.G) {
                        Wrapper.mc.player.motionY = motionY;
                        ++this.d;
                        if (this.d == 1.0) {
                            n = 0.075f;
                        }
                        if (this.d == 2.0) {
                            n = 0.175f;
                        }
                        if (this.d == 3.0) {
                            n = 0.325f;
                        }
                        if (this.d == 4.0) {
                            n = 0.375f;
                        }
                        if (this.d == 5.0) {
                            n = 0.4f;
                        }
                        if (this.d >= 6.0) {
                            n = 0.43395f;
                        }
                        if (Speed.useTimer.M()) {
                            if (nextBoolean) {
                                Ta.M(1.3f);
                                n2 = n;
                                break Label_0465;
                            }
                            Ta.M(1.0f);
                        }
                    }
                    n2 = n;
                }
                u.M(n2, (Entity)Wrapper.mc.player);
                return;
            }
            if (Speed.A) {
                Wrapper.mc.player.motionY = -0.1;
                Speed.A = false;
            }
            HookTranslator.v20 = false;
            final double b = 0.0;
            this.d = 0.0;
            this.b = b;
            this.d();
        }
    }
    
    @SubscribeEvent
    public void A(final EventUpdate eventUpdate) {
        if (Speed.mode.e().equals("Tunnel")) {
            final BlockPos blockPos = new BlockPos(Wrapper.mc.player.posX, Wrapper.mc.player.posY + 2.0, Wrapper.mc.player.posZ);
            final BlockPos blockPos2 = new BlockPos(Wrapper.mc.player.posX, Wrapper.mc.player.posY - 1.0, Wrapper.mc.player.posZ);
            if (Wrapper.mc.world.getBlockState(blockPos).getBlock() != Blocks.AIR && Wrapper.mc.world.getBlockState(blockPos).getBlock() != Blocks.PORTAL && Wrapper.mc.world.getBlockState(blockPos).getBlock() != Blocks.END_PORTAL && Wrapper.mc.world.getBlockState(blockPos).getBlock() != Blocks.WATER && Wrapper.mc.world.getBlockState(blockPos).getBlock() != Blocks.FLOWING_WATER && Wrapper.mc.world.getBlockState(blockPos).getBlock() != Blocks.LAVA && Wrapper.mc.world.getBlockState(blockPos).getBlock() != Blocks.FLOWING_LAVA && Wrapper.mc.world.getBlockState(blockPos2).getBlock() != Blocks.ICE && Wrapper.mc.world.getBlockState(blockPos2).getBlock() != Blocks.FROSTED_ICE && Wrapper.mc.world.getBlockState(blockPos2).getBlock() != Blocks.PACKED_ICE && !Wrapper.mc.player.isInWater()) {
                final float n = (float)Math.toRadians(MinecraftHelper.getPlayer().rotationYaw);
                if (Wrapper.mc.gameSettings.keyBindForward.isKeyDown() && !Wrapper.mc.gameSettings.keyBindSneak.isKeyDown() && Wrapper.mc.player.onGround) {
                    final EntityPlayerSP player = Wrapper.mc.player;
                    player.motionX -= DegreeUtils.D(n) * 0.15;
                    final EntityPlayerSP player2 = Wrapper.mc.player;
                    player2.motionZ += DegreeUtils.M(n) * 0.15;
                }
            }
        }
    }
    
    static {
        Speed.mode = ValueFactory.M("Speed", "Mode", "Speed Mode", "BunnyHop", "BunnyHop", "Strafe", "Boost", "GroundBoost", "Tunnel", "Development");
        Speed.useTimer = ValueFactory.M("Speed", "UseTimer", "Uses timer to boost the player", true);
        Speed.sprint = ValueFactory.M("Speed", "Sprint", "Automatically sprint", true);
        Speed.spoofCam = ValueFactory.M("Speed", "SpoofCam", "Spoof the camera to stop shakyness with some speed modes", true);
        Speed.A = false;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (Speed.mode.e().equals("Development")) {
            final double motionY = 0.4;
            float n = 0.26f;
            if (Wrapper.mc.player.onGround) {
                Speed.G = Wrapper.mc.player.posY;
            }
            if (u.M((Entity)Wrapper.mc.player) <= 1.0) {
                final double d = 1.0;
                this.b = 1.0;
                this.d = d;
            }
            if (u.M((Entity)Wrapper.mc.player) && !Wrapper.mc.player.collidedHorizontally && !fa.D((Entity)Wrapper.mc.player) && fa.M((Entity)Wrapper.mc.player)) {
                Speed.A = true;
                HookTranslator.v20 = (Speed.spoofCam.M() && Wrapper.mc.player.getRidingEntity() == null);
                final boolean nextBoolean = new Random().nextBoolean();
                if (Wrapper.mc.player.posY >= Speed.G + motionY) {
                    Wrapper.mc.player.motionY = -motionY;
                    ++this.b;
                    if (this.b == 1.0) {
                        n = 0.075f;
                    }
                    if (this.b == 2.0) {
                        n = 0.175f;
                    }
                    if (this.b == 3.0) {
                        n = 0.275f;
                    }
                    if (this.b == 4.0) {
                        n = 0.35f;
                    }
                    if (this.b == 5.0) {
                        n = 0.375f;
                    }
                    if (this.b == 6.0) {
                        n = 0.4f;
                    }
                    if (this.b == 7.0) {
                        n = 0.425f;
                    }
                    if (this.b == 8.0) {
                        n = 0.45f;
                    }
                    if (this.b == 9.0) {
                        n = 0.475f;
                    }
                    if (this.b == 10.0) {
                        n = 0.5f;
                    }
                    if (this.b == 11.0) {
                        n = 0.5f;
                    }
                    if (this.b == 12.0) {
                        n = 0.525f;
                    }
                    if (this.b == 13.0) {
                        n = 0.525f;
                    }
                    if (this.b == 14.0) {
                        n = 0.535f;
                    }
                    if (this.b == 15.0) {
                        n = 0.535f;
                    }
                    if (this.b == 16.0) {
                        n = 0.545f;
                    }
                    if (this.b >= 17.0) {
                        n = 0.545f;
                    }
                    if (Speed.useTimer.M()) {
                        Ta.M(1.0f);
                    }
                }
                float n2 = 0.0f;
                Label_0752: {
                    if (Wrapper.mc.player.posY == Speed.G) {
                        Wrapper.mc.player.motionY = motionY;
                        ++this.d;
                        if (this.d == 1.0) {
                            n = 0.075f;
                        }
                        if (this.d == 2.0) {
                            n = 0.175f;
                        }
                        if (this.d == 3.0) {
                            n = 0.375f;
                        }
                        if (this.d == 4.0) {
                            n = 0.6f;
                        }
                        if (this.d == 5.0) {
                            n = 0.775f;
                        }
                        if (this.d == 6.0) {
                            n = 0.825f;
                        }
                        if (this.d == 7.0) {
                            n = 0.875f;
                        }
                        if (this.d == 8.0) {
                            n = 0.925f;
                        }
                        if (this.d == 9.0) {
                            n = 0.975f;
                        }
                        if (this.d == 10.0) {
                            n = 1.05f;
                        }
                        if (this.d == 11.0) {
                            n = 1.1f;
                        }
                        if (this.d == 12.0) {
                            n = 1.1f;
                        }
                        if (this.d == 13.0) {
                            n = 1.15f;
                        }
                        if (this.d == 14.0) {
                            n = 1.15f;
                        }
                        if (this.d == 15.0) {
                            n = 1.175f;
                        }
                        if (this.d == 16.0) {
                            n = 1.175f;
                        }
                        if (this.d >= 17.0) {
                            n = 1.175f;
                        }
                        if (Speed.useTimer.M()) {
                            if (nextBoolean) {
                                Ta.M(1.3f);
                                n2 = n;
                                break Label_0752;
                            }
                            Ta.M(1.0f);
                        }
                    }
                    n2 = n;
                }
                u.M(n2, (Entity)Wrapper.mc.player);
                return;
            }
            if (Speed.A) {
                Wrapper.mc.player.motionY = -0.1;
                Speed.A = false;
            }
            HookTranslator.v20 = false;
            final double b = 0.0;
            this.d = 0.0;
            this.b = b;
            this.d();
        }
    }
    
    public Speed() {
        final double b = 0.0;
        final double d = 0.0;
        super(Category.MOVEMENT, "Speed", false, -1, "Move faster");
        this.d = d;
        this.b = b;
    }
}
