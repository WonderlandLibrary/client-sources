package best.actinium.module.impl.combat;

import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.TickEvent;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.event.impl.network.PacketEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.BooleanProperty;
import best.actinium.property.impl.ModeProperty;
import best.actinium.property.impl.NumberProperty;
import best.actinium.util.IAccess;
import best.actinium.util.io.PacketUtil;
import best.actinium.util.render.ChatUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;

@ModuleInfo(
        name = "Velocity",
        description = "Prevents you from taking knockback.",
        category = ModuleCategory.COMBAT
)
public class VelocityModule extends Module {
    public ModeProperty mode = new ModeProperty("Mode", this, new String[] {"Normal","Grim","Reduce","Club","Watchdog"}, "Normal");
    private BooleanProperty viaVersion = new BooleanProperty("Via Version",this,true).
            setHidden(() -> !mode.is("Grim"));
    public NumberProperty horizontal = new NumberProperty("Horizontal", this, -100, 100, 100, 1);
    public NumberProperty vertical = new NumberProperty("Vertical", this, -100, 100, 100, 1);
    private int teleported, grimvelocity, test;

    @Callback
    public void onMotion(MotionEvent event) {
        if(event.getType() == EventType.POST) {
            return;
        }

        switch (mode.getMode()) {
            case "Grim":
                if (grimvelocity > 0) {
                    grimvelocity++;
                    if (grimvelocity == 2) {
                        if (viaVersion.isEnabled()) {
                            PacketUtil.sendSilent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.prevPosX, mc.thePlayer.prevPosY, mc.thePlayer.prevPosZ, mc.thePlayer.prevRotationYaw, mc.thePlayer.prevRotationPitch, mc.thePlayer.prevPosY % 0.015625 == 0));
                        } else {
                            mc.timer.timerSpeed = 0.305F;
                            PacketUtil.sendSilent(new C03PacketPlayer(mc.thePlayer.prevPosY % 0.015625 == 0));
                        }
                        PacketUtil.sendSilent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, new BlockPos(mc.thePlayer), EnumFacing.UP));
                    } else {
                        mc.timer.timerSpeed = 1F;
                        grimvelocity = 0;
                    }
                }
                break;
        }
    }

    @Callback
    public void onPacket(PacketEvent event) {
        this.setSuffix(mode.is("Normal") ? horizontal.getValue() + "% " + vertical.getValue() + "%" : mode.getMode());

        if (event.getType() == EventType.OUTGOING) {
            return;
        }

        if (event.getPacket() instanceof S12PacketEntityVelocity velocity) {
            switch (mode.getMode()) {
                case "Normal":
                    velocity.motionX *= horizontal.getValue() / 100;
                    velocity.motionY *= vertical.getValue() / 100;
                    velocity.motionZ *= horizontal.getValue() / 100;
                    break;
                case "Watchdog":
                   // test++;
                    //                    if(test == 3) {
                    //                        test = 0;
                    //                        event.setCancelled(true);
                    //                    }
                    //
                    //                    if(!IAccess.mc.thePlayer.onGround) {
                    //                        event.setCancelled(true);
                    //                    }
                    //
                    //                    if(IAccess.mc.thePlayer.onGround && test != 3) {
                    //                        velocity.motionX *= 0/100;
                    //                        velocity.motionZ *= 0/100;
                    //                    }
                    event.setCancelled(true);
                    break;
                case "Club":
                    if(mc.thePlayer.ticksExisted % 2 == 0) {
                        event.setCancelled(true);
                    }
                    break;
                case "Grim":
                    if (velocity.getEntityID() == mc.thePlayer.getEntityId() && teleported != mc.thePlayer.ticksExisted) {
                        event.setCancelled(true);
                        grimvelocity = 1;
                    }
                    break;
            }
        } else {
            if(event.getPacket() instanceof S08PacketPlayerPosLook && mode.is("Grim")) {
                teleported = mc.thePlayer.ticksExisted;
            }
        }
    }
}
