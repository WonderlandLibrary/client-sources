package com.polarware.module.impl.movement.flight;

import com.polarware.component.impl.player.RotationComponent;
import com.polarware.component.impl.player.rotationcomponent.MovementFix;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.motion.PreUpdateEvent;
import com.polarware.module.impl.movement.FlightModule;
import com.polarware.util.packet.PacketUtil;
import com.polarware.util.player.PlayerUtil;
import com.polarware.util.vector.Vector2f;
import com.polarware.value.Mode;
import com.polarware.value.impl.NumberValue;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * @author Blazer69
 * @since 31.07.2023
 */

public class PolarFlight extends Mode<FlightModule>  {

    public NumberValue speed = new NumberValue("Speed", this, 5.0f, 1.0f, 9.5f, 0.1f);
    public NumberValue height = new NumberValue("Height", this, 1.0f, 0.1f, 9.5f, 0.1f);

    public PolarFlight(String name, FlightModule parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        mc.timer.timerSpeed = 0.007f;
    }

    @Override
    public void onDisable() {
        mc.thePlayer.jump();
        //mc.thePlayer.setPosition(mc.thePlayer.posX + mc.thePlayer.getHorizontalFacing().getFrontOffsetX(), mc.thePlayer.posY, mc.thePlayer.posZ + mc.thePlayer.getHorizontalFacing().getFrontOffsetZ());
        mc.thePlayer.motionX = mc.thePlayer.getLookVec().xCoord * speed.getValue().floatValue();
        mc.thePlayer.motionZ = mc.thePlayer.getLookVec().zCoord * speed.getValue().floatValue();
        mc.thePlayer.motionY = height.getValue().floatValue();
    }
}