package io.github.liticane.monoxide.module.impl.combat;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import io.github.liticane.monoxide.listener.event.minecraft.input.InputEvent;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.listener.event.minecraft.network.PacketEvent;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.SilentMoveEvent;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateMotionEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.math.time.TimeHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;

@ModuleData(name = "Velocity", description = "Modifies your velocity", category = ModuleCategory.COMBAT)
public class VelocityModule extends Module {
    private final ModeValue mode = new ModeValue("Mode", this, new String[] {"Intave Strong"});

    private boolean working = false, attacking = false;

    @Listen
    public void onUpdateMotionEvent(UpdateMotionEvent event) {
        switch (mode.getValue()) {
            case "Intave Strong": {
                if (event.getType() != UpdateMotionEvent.Type.MID)
                    break;

                if (working) {
                    if (mc.thePlayer.hurtTime != 0) {
                         mc.thePlayer.setSprinting(false);

                        if (!mc.thePlayer.onGround && !mc.thePlayer.isBurning()) {
                            mc.thePlayer.movementInput.jump = true;
                        }

                        if (mc.thePlayer.isSwingInProgress)
                            attacking = true;

                        if (mc.objectMouseOver.entityHit != null && attacking) {
                            mc.thePlayer.motionX *= 0.6;
                            mc.thePlayer.motionZ *= 0.6;
                        }

                        attacking = false;
                    }

                    working = false;
                }

                break;
            }
        }
    }

    @Listen
    public void onPacketEvent(PacketEvent event) {
        if (event.getType() == PacketEvent.Type.INCOMING) {
            if (event.getPacket() instanceof S12PacketEntityVelocity s12
                    && s12.getEntityID() == mc.thePlayer.getEntityID()) {
                working = true;
            }
        }
    }

}
