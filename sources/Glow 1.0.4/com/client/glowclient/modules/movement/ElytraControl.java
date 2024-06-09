package com.client.glowclient.modules.movement;

import com.client.glowclient.modules.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import com.client.glowclient.events.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.relauncher.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.*;

public class ElytraControl extends ModuleContainer
{
    public static final NumberValue creSpeed;
    public static final NumberValue conSpeed;
    public static final nB mode;
    
    public ElytraControl() {
        super(Category.MOVEMENT, "ElytraControl", false, -1, "Control elytra with WASD");
    }
    
    @Override
    public String M() {
        return ElytraControl.mode.e();
    }
    
    @Override
    public void E() {
        if (ElytraControl.mode.e().equals("Creative") && Wrapper.mc.player != null) {
            Wrapper.mc.player.capabilities.isFlying = false;
            Ob.M().sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.mc.player, CPacketEntityAction$Action.START_FALL_FLYING));
            Wrapper.mc.player.capabilities.setFlySpeed(0.05f);
        }
    }
    
    private static void d() {
        if (Wrapper.mc.player != null && !Wrapper.mc.player.isElytraFlying()) {
            Ob.M().sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.mc.player, CPacketEntityAction$Action.START_FALL_FLYING));
        }
    }
    
    @SubscribeEvent
    public void A(final EventUpdate eventUpdate) {
        if (ElytraControl.mode.e().equals("Control") && Wrapper.mc.player.isElytraFlying()) {
            if (MinecraftHelper.mc.gameSettings.keyBindJump.isKeyDown()) {
                final EntityPlayerSP player = MinecraftHelper.getPlayer();
                player.motionY += 0.08;
            }
            else if (MinecraftHelper.mc.gameSettings.keyBindSneak.isKeyDown()) {
                final EntityPlayerSP player2 = MinecraftHelper.getPlayer();
                player2.motionY -= 0.04;
            }
            if (MinecraftHelper.mc.gameSettings.keyBindForward.isKeyDown()) {
                final float n = (float)Math.toRadians(MinecraftHelper.getPlayer().rotationYaw);
                final EntityPlayerSP player3 = MinecraftHelper.getPlayer();
                player3.motionX -= DegreeUtils.D(n) * ElytraControl.conSpeed.k();
                final EntityPlayerSP player4 = MinecraftHelper.getPlayer();
                player4.motionZ += DegreeUtils.M(n) * ElytraControl.conSpeed.k();
                return;
            }
            if (MinecraftHelper.mc.gameSettings.keyBindBack.isKeyDown()) {
                final float n2 = (float)Math.toRadians(MinecraftHelper.getPlayer().rotationYaw);
                final EntityPlayerSP player5 = MinecraftHelper.getPlayer();
                player5.motionX += DegreeUtils.D(n2) * 0.05f;
                final EntityPlayerSP player6 = MinecraftHelper.getPlayer();
                player6.motionZ -= DegreeUtils.M(n2) * 0.05f;
                return;
            }
            if (MinecraftHelper.mc.gameSettings.keyBindRight.isKeyDown()) {
                final float n3 = (float)Math.toRadians(MinecraftHelper.getPlayer().rotationYaw);
                final EntityPlayerSP player7 = MinecraftHelper.getPlayer();
                player7.motionX -= DegreeUtils.D(n3 - 80.1107f) * 0.08f;
                final EntityPlayerSP player8 = MinecraftHelper.getPlayer();
                player8.motionZ += DegreeUtils.M(n3 - 80.1107f) * 0.08f;
                return;
            }
            if (MinecraftHelper.mc.gameSettings.keyBindLeft.isKeyDown()) {
                final float n4 = (float)Math.toRadians(MinecraftHelper.getPlayer().rotationYaw);
                final EntityPlayerSP player9 = MinecraftHelper.getPlayer();
                player9.motionX -= DegreeUtils.D(n4 + 80.1107f) * 0.08f;
                final EntityPlayerSP player10 = MinecraftHelper.getPlayer();
                player10.motionZ += DegreeUtils.M(n4 + 80.1107f) * 0.08f;
            }
        }
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void D(final EventUpdate eventUpdate) {
        if (ElytraControl.mode.e().equals("Creative")) {
            if (Wrapper.mc.player.isElytraFlying()) {
                Wrapper.mc.player.capabilities.isFlying = true;
            }
            Wrapper.mc.player.capabilities.setFlySpeed((float)ElytraControl.creSpeed.k());
        }
    }
    
    static {
        final String s = "ElytraControl";
        final String s2 = "ConSpeed";
        final String s3 = "Control Speed";
        final double n = 0.05;
        conSpeed = ValueFactory.M(s, s2, s3, n, n, 0.0, 0.5);
        creSpeed = ValueFactory.M("ElytraControl", "CreSpeed", "Creative Speed", 0.15, 0.05, 0.0, 0.5);
        mode = ValueFactory.M("ElytraControl", "Mode", "Mode of the mod", "Control", "Creative", "Control");
    }
    
    @Override
    public void D() {
        if (ElytraControl.mode.e().equals("Creative")) {
            Wrapper.mc.addScheduledTask(tF::d);
        }
    }
}
