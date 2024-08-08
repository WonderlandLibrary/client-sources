package me.xatzdevelopments.modules.movement;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventStep;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.modules.Module.Category;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.util.MoveUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Step extends Module
{
    public ModeSetting Mode;
    private int ticks;
    private int offGroundTicks;
    private boolean jumped;
    int stepCounter;
    
    public Step() {
		super("Step", Keyboard.KEY_NONE, Category.MOVEMENT, "Speed jump/step onto a block.");
        this.Mode = new ModeSetting("Mode", "Vanilla", new String[] { "Vanilla", "NCP", "HiveAAC" });
        this.ticks = 0;
        this.offGroundTicks = 0;
        this.jumped = false;
        this.stepCounter = 0;
        this.addSettings(this.Mode);
    }
    
    @Override
    public void onEnable() {
        this.ticks = 0;
        this.offGroundTicks = 0;
        this.jumped = false;
        if (this.Mode.getMode() == "NCP") {
            Xatz.addChatMessage("Thank you yorrik100 for ncp step");
        }
    }
    
    @Override
    public void onDisable() {
        MoveUtils.strafe(0.2f);
        this.mc.thePlayer.speedInAir = 0.02f;
        this.mc.thePlayer.stepHeight = 0.5f;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate) {
            final String mode;
            switch (mode = this.Mode.getMode()) {
                case "HiveAAC": {
                    this.mc.thePlayer.stepHeight = 1.0f;
                    if (this.stepCounter > 0) {
                        ++this.stepCounter;
                    }
                    if (this.stepCounter > 2 || this.mc.thePlayer.isCollidedHorizontally) {
                        this.stepCounter = 0;
                        this.mc.timer.timerSpeed = 1.0f;
                        break;
                    }
                    break;
                }
                case "NCP": {
                    this.mc.thePlayer.stepHeight = 2.5f;
                    break;
                }
                case "Vanilla": {
                    this.mc.thePlayer.stepHeight = 1.0f;
                    break;
                }
                default:
                    break;
            }
        }
        if (e instanceof EventStep) {
            ++this.ticks;
            if (this.mc.thePlayer == null || this.mc.thePlayer.ticksExisted == 0) {
                return;
            }
            final double x = this.mc.thePlayer.posX;
            final double y = this.mc.thePlayer.posY;
            final double z = this.mc.thePlayer.posZ;
            if (this.mc.thePlayer.onGround) {
                this.offGroundTicks = 0;
            }
            else {
                ++this.offGroundTicks;
            }
            final String mode2;
            switch (mode2 = this.Mode.getMode()) {
                case "HiveAAC": {
                    final double rheight = this.mc.thePlayer.getEntityBoundingBox().minY - this.mc.thePlayer.posY;
                    if (rheight > 0.875) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.41999998688698, this.mc.thePlayer.posZ, false));
                        if (e.isPost()) {
                            this.mc.timer.timerSpeed = 0.4f;
                        }
                    }
                    this.stepCounter = 1;
                    break;
                }
                case "NCP": {
                    final double rheight = this.mc.thePlayer.getEntityBoundingBox().minY - this.mc.thePlayer.posY;
                    if (rheight > 2.019) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.425, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.821, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.699, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.599, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.022, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.372, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.652, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.869, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 2.019, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.919, this.mc.thePlayer.posZ, false));
                        final EntityPlayerSP thePlayer = this.mc.thePlayer;
                        final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
                        final double n = 0.0;
                        thePlayer2.motionZ = n;
                        thePlayer.motionX = n;
                        break;
                    }
                    if (rheight > 1.869) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.425, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.821, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.699, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.599, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.022, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.372, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.652, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.869, this.mc.thePlayer.posZ, false));
                        final EntityPlayerSP thePlayer3 = this.mc.thePlayer;
                        final EntityPlayerSP thePlayer4 = this.mc.thePlayer;
                        final double n2 = 0.0;
                        thePlayer4.motionZ = n2;
                        thePlayer3.motionX = n2;
                        break;
                    }
                    if (rheight > 1.5) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.425, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.821, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.699, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.599, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.022, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.372, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.652, this.mc.thePlayer.posZ, false));
                        final EntityPlayerSP thePlayer5 = this.mc.thePlayer;
                        final EntityPlayerSP thePlayer6 = this.mc.thePlayer;
                        final double n3 = 0.0;
                        thePlayer6.motionZ = n3;
                        thePlayer5.motionX = n3;
                        break;
                    }
                    if (rheight > 1.015) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.42, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.7532, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.01, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.093, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.015, this.mc.thePlayer.posZ, false));
                        final EntityPlayerSP thePlayer7 = this.mc.thePlayer;
                        final EntityPlayerSP thePlayer8 = this.mc.thePlayer;
                        final double n4 = 0.0;
                        thePlayer8.motionZ = n4;
                        thePlayer7.motionX = n4;
                        break;
                    }
                    if (rheight > 0.875) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.41999998688698, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.7531999805212, this.mc.thePlayer.posZ, false));
                        break;
                    }
                    if (rheight > 0.6) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.39, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.6938, this.mc.thePlayer.posZ, false));
                        break;
                    }
                    break;
                }
                case "Vanilla": {
                    this.mc.thePlayer.stepHeight = 1.0f;
                    break;
                }
                default:
                    break;
            }
        }
    }
}
