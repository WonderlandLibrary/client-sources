package com.client.glowclient.modules.movement;

import com.client.glowclient.events.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import java.util.*;
import com.client.glowclient.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.modules.*;

public class IceSpeed extends ModuleContainer
{
    public static final NumberValue speed;
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        final double n = IceSpeed.speed.k() / 100.0;
        final BlockPos blockPos = new BlockPos(Wrapper.mc.player.posX, Wrapper.mc.player.posY - 0.6, Wrapper.mc.player.posZ);
        if (Wrapper.mc.world.getBlockState(blockPos).getBlock() == Blocks.ICE || Wrapper.mc.world.getBlockState(blockPos).getBlock() == Blocks.PACKED_ICE || Wrapper.mc.world.getBlockState(blockPos).getBlock() == Blocks.FROSTED_ICE) {
            if (Wrapper.mc.player.isSneaking() || (Wrapper.mc.player.moveForward == 0.0f && Wrapper.mc.player.moveStrafing == 0.0f)) {
                return;
            }
            if (Wrapper.mc.player.moveForward > 0.0f && !Wrapper.mc.player.collidedHorizontally) {
                Wrapper.mc.player.setSprinting(true);
            }
            final float n2 = (float)Math.toRadians(MinecraftHelper.getPlayer().rotationYaw);
            if (Wrapper.mc.player.onGround) {
                final Iterator<NF> iterator = ModuleManager.M().iterator();
                while (iterator.hasNext()) {
                    final Module module;
                    if ((module = (Module)iterator.next()) instanceof AutoWalk && !module.k() && !Wrapper.mc.gameSettings.keyBindLeft.isKeyDown() && !Wrapper.mc.gameSettings.keyBindRight.isKeyDown() && !Wrapper.mc.gameSettings.keyBindForward.isKeyDown() && !Wrapper.mc.gameSettings.keyBindBack.isKeyDown()) {
                        Wrapper.mc.player.motionX = 0.0;
                        Wrapper.mc.player.motionZ = 0.0;
                    }
                }
                if (Wrapper.mc.gameSettings.keyBindBack.isKeyDown() && Wrapper.mc.gameSettings.keyBindForward.isKeyDown()) {
                    Wrapper.mc.player.motionX = 0.0;
                    Wrapper.mc.player.motionZ = 0.0;
                    return;
                }
                if (Wrapper.mc.gameSettings.keyBindLeft.isKeyDown() && Wrapper.mc.gameSettings.keyBindRight.isKeyDown()) {
                    Wrapper.mc.player.motionX = 0.0;
                    Wrapper.mc.player.motionZ = 0.0;
                    return;
                }
                if (Wrapper.mc.gameSettings.keyBindRight.isKeyDown() && Wrapper.mc.gameSettings.keyBindForward.isKeyDown()) {
                    final EntityPlayerSP player = Wrapper.mc.player;
                    player.motionX -= DegreeUtils.D(n2 - 80.8961f) * n;
                    final EntityPlayerSP player2 = Wrapper.mc.player;
                    player2.motionZ += DegreeUtils.M(n2 - 80.8961f) * n;
                    return;
                }
                if (Wrapper.mc.gameSettings.keyBindLeft.isKeyDown() && Wrapper.mc.gameSettings.keyBindForward.isKeyDown()) {
                    final EntityPlayerSP player3 = Wrapper.mc.player;
                    player3.motionX -= DegreeUtils.D(n2 + 80.8961f) * n;
                    final EntityPlayerSP player4 = Wrapper.mc.player;
                    player4.motionZ += DegreeUtils.M(n2 + 80.8961f) * n;
                    return;
                }
                if (Wrapper.mc.gameSettings.keyBindLeft.isKeyDown() && Wrapper.mc.gameSettings.keyBindBack.isKeyDown()) {
                    final EntityPlayerSP player5 = Wrapper.mc.player;
                    player5.motionX -= DegreeUtils.D(n2 - 90.32081f) * n;
                    final EntityPlayerSP player6 = Wrapper.mc.player;
                    player6.motionZ += DegreeUtils.M(n2 - 90.32081f) * n;
                    return;
                }
                if (Wrapper.mc.gameSettings.keyBindRight.isKeyDown() && Wrapper.mc.gameSettings.keyBindBack.isKeyDown()) {
                    final EntityPlayerSP player7 = Wrapper.mc.player;
                    player7.motionX -= DegreeUtils.D(n2 + 90.32081f) * n;
                    final EntityPlayerSP player8 = Wrapper.mc.player;
                    player8.motionZ += DegreeUtils.M(n2 + 90.32081f) * n;
                    return;
                }
                if (Wrapper.mc.gameSettings.keyBindForward.isKeyDown()) {
                    final EntityPlayerSP player9 = Wrapper.mc.player;
                    player9.motionX -= DegreeUtils.D(n2) * n;
                    final EntityPlayerSP player10 = Wrapper.mc.player;
                    player10.motionZ += DegreeUtils.M(n2) * n;
                    return;
                }
                if (Wrapper.mc.gameSettings.keyBindBack.isKeyDown()) {
                    final EntityPlayerSP player11 = Wrapper.mc.player;
                    player11.motionX += DegreeUtils.D(n2) * n;
                    final EntityPlayerSP player12 = Wrapper.mc.player;
                    player12.motionZ -= DegreeUtils.M(n2) * n;
                    return;
                }
                if (Wrapper.mc.gameSettings.keyBindRight.isKeyDown()) {
                    final EntityPlayerSP player13 = Wrapper.mc.player;
                    player13.motionX -= DegreeUtils.D(n2 - 80.1107f) * n;
                    final EntityPlayerSP player14 = Wrapper.mc.player;
                    player14.motionZ += DegreeUtils.M(n2 - 80.1107f) * n;
                    return;
                }
                if (Wrapper.mc.gameSettings.keyBindLeft.isKeyDown()) {
                    final EntityPlayerSP player15 = Wrapper.mc.player;
                    player15.motionX -= DegreeUtils.D(n2 + 80.1107f) * n;
                    final EntityPlayerSP player16 = Wrapper.mc.player;
                    player16.motionZ += DegreeUtils.M(n2 + 80.1107f) * n;
                }
            }
        }
    }
    
    static {
        speed = ValueFactory.M("IceSpeed", "Speed", "Speed on ice", 4.65, 0.05, 0.0, 6.0);
    }
    
    @Override
    public String M() {
        return "";
    }
    
    public IceSpeed() {
        super(Category.MOVEMENT, "IceSpeed", false, -1, "Go faster on ice");
    }
}
