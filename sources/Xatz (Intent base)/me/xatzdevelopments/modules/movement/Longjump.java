package me.xatzdevelopments.modules.movement;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventMotion;
import me.xatzdevelopments.events.listeners.EventReadPacket;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.util.MoveUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class Longjump extends Module
{
    public ModeSetting Mode;
    private int ticks;
    private int afterHurtTicks;
    private boolean hurted;
    private boolean jumped;
    private double OPosX;
    private double OPosY;
    private double OPosZ;
    private int offGroundTicks;
    boolean wasonground;
    private double onGroundTicks;
    
    public Longjump() {
        super("Longjump", 35, Category.MOVEMENT, null);
        this.Mode = new ModeSetting("Mode", "NCP", new String[] { "NCP", "AAC", "Mineplex", "Redesky" });
        this.ticks = 0;
        this.afterHurtTicks = 0;
        this.hurted = false;
        this.jumped = false;
        this.OPosX = 0.0;
        this.OPosY = 0.0;
        this.OPosZ = 0.0;
        this.offGroundTicks = 0;
        this.wasonground = false;
        this.onGroundTicks = 0.0;
        this.addSettings(this.Mode);
    }
    
    @Override
    public void onEnable() {
        this.ticks = 0;
        this.afterHurtTicks = 0;
        this.hurted = false;
        this.jumped = false;
        this.OPosX = this.mc.thePlayer.posX;
        this.OPosY = this.mc.thePlayer.posY;
        this.OPosZ = this.mc.thePlayer.posZ;
        this.offGroundTicks = 0;
        this.onGroundTicks = 0.0;
        this.wasonground = false;
    }
    
    @Override
    public void onDisable() {
        this.mc.thePlayer.capabilities.isFlying = false;
        if (this.Mode.getMode() != "Redesky") {
            MoveUtils.strafe(0.1f);
        }
        this.mc.thePlayer.speedInAir = 0.02f;
        this.mc.timer.timerSpeed = 1.0f;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventMotion && e.isPre()) {
            ++this.ticks;
            if (this.mc.thePlayer.ticksExisted == 1) {
                this.toggled = false;
                this.mc.timer.timerSpeed = 1.0f;
                MoveUtils.strafe(0.1);
            }
            if (this.mc.thePlayer.onGround) {
                this.offGroundTicks = 0;
                ++this.onGroundTicks;
                this.wasonground = true;
            }
            else {
                ++this.offGroundTicks;
                this.onGroundTicks = 0.0;
            }
            final String mode;
            switch (mode = this.Mode.getMode()) {
                case "Redesky": {
                    this.mc.timer.timerSpeed = 0.9f;
                    this.mc.thePlayer.speedInAir = 0.07f;
                    final EntityPlayerSP thePlayer = this.mc.thePlayer;
                    thePlayer.motionY += 0.05;
                    if (this.mc.thePlayer.onGround && MoveUtils.isMoving()) {
                        this.mc.thePlayer.jump(jumped);
                        this.mc.thePlayer.motionY = 0.49;
                        break;
                    }
                    break;
                }
                case "Mineplex": {
                    if (this.mc.thePlayer.fallDistance > 3.0f) {
                        return;
                    }
                    this.mc.thePlayer.speedInAir = 0.029f;
                    if (this.mc.thePlayer.onGround && MoveUtils.isMoving()) {
                        this.mc.thePlayer.jump(jumped);
                        MoveUtils.strafe(1.0E-4);
                        this.mc.thePlayer.motionY = 0.49;
                    }
                    else {
                        if (this.offGroundTicks == 1) {
                            MoveUtils.strafe(0.59);
                        }
                        if (this.mc.thePlayer.fallDistance < 0.0f) {
                            final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
                            thePlayer2.motionY += 0.039675;
                        }
                        else if (this.mc.thePlayer.fallDistance < 0.1) {
                            final EntityPlayerSP thePlayer3 = this.mc.thePlayer;
                            thePlayer3.motionY += 0.034;
                        }
                        else if (this.mc.thePlayer.fallDistance < 0.8) {
                            final EntityPlayerSP thePlayer4 = this.mc.thePlayer;
                            thePlayer4.motionY += 0.034;
                        }
                        else if (this.mc.thePlayer.fallDistance < 0.35) {
                            final EntityPlayerSP thePlayer5 = this.mc.thePlayer;
                            thePlayer5.motionY += 0.034;
                        }
                        else if (this.mc.thePlayer.fallDistance < 0.7) {
                            final EntityPlayerSP thePlayer6 = this.mc.thePlayer;
                            thePlayer6.motionY += 0.034;
                        }
                    }
                    MoveUtils.strafe();
                    if (!MoveUtils.isMoving()) {
                        this.mc.thePlayer.motionX = 0.0;
                        this.mc.thePlayer.motionZ = 0.0;
                        break;
                    }
                    break;
                }
                case "AAC": {
                    if (!this.mc.thePlayer.onGround) {
                        if (this.offGroundTicks <= 1) {
                            break;
                        }
                        if (this.mc.thePlayer.fallDistance == 0.0f) {
                            final EntityPlayerSP thePlayer7 = this.mc.thePlayer;
                            thePlayer7.motionY += 0.03;
                            break;
                        }
                        final EntityPlayerSP thePlayer8 = this.mc.thePlayer;
                        thePlayer8.motionY += 0.03;
                        this.mc.thePlayer.speedInAir = 0.033f;
                        break;
                    }
                    else {
                        if (MoveUtils.isMoving()) {
                            this.mc.thePlayer.jump(jumped);
                            break;
                        }
                        break;
                    }
                }
                case "NCP": {
                    this.mc.timer.timerSpeed = 0.5f;
                    if (this.mc.thePlayer.onGround && MoveUtils.isMoving()) {
                        this.mc.thePlayer.jump(jumped);
                    }
                    if (this.offGroundTicks == 1) {
                        MoveUtils.strafe(1.2);
                    }
                    this.mc.thePlayer.speedInAir = 0.0344f;
                    MoveUtils.strafe();
                    break;
                }
                default:
                    break;
            }
        }
        if (e instanceof EventReadPacket) {
            final String mode2;
            switch (mode2 = this.Mode.getMode()) {
                case "Cubecraft": {
                    if (e instanceof EventReadPacket && e.isPre() && ((EventReadPacket)e).getPacket() instanceof S12PacketEntityVelocity) {
                        e.setCancelled(true);
                        break;
                    }
                    break;
                }
                default:
                    break;
            }
        }
    }
}
