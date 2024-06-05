/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 *  store.intent.intentguard.annotation.Native
 */
package cc.diablo.module.impl.movement;

import cc.diablo.event.impl.CollideEvent;
import cc.diablo.event.impl.MoveRawEvent;
import cc.diablo.event.impl.PacketEvent;
import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.helpers.HypixelHelper;
import cc.diablo.helpers.player.EntityHelper;
import cc.diablo.helpers.player.MoveUtils;
import cc.diablo.helpers.render.ChatHelper;
import cc.diablo.manager.module.ModuleManager;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.module.impl.combat.TargetStrafe;
import cc.diablo.setting.impl.BooleanSetting;
import cc.diablo.setting.impl.ModeSetting;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Timer;
import store.intent.intentguard.annotation.Native;

@Native
public class Fly
extends Module {
    public ModeSetting mode = new ModeSetting("Flight mode", "VerusDMG", "Vanilla", "VerusDMG", "Collide", "VerusDEV", "BlocksMC", "TP-Fly", "HypixelSlime", "MuffinIsDad", "Invaded", "MushMC", "Ghostly", "GhostlyInv", "Watchdog", "Gwen");
    public NumberSetting timer = new NumberSetting("Timer", 1.0, 0.1, 8.0, 0.1);
    public NumberSetting speed = new NumberSetting("Speed", 4.0, 0.5, 8.0, 0.1);
    public BooleanSetting viewbobbing = new BooleanSetting("View Bobbing", true);
    public BooleanSetting nomotion = new BooleanSetting("Stop Motion", false);
    public BooleanSetting distanceDisable = new BooleanSetting("Auto Disable after Distance", false);
    public NumberSetting distance = new NumberSetting("Distance", 12.0, 1.0, 180.0, 1.0);
    int state;
    double start_x;
    double start_y;
    double start_z;
    public boolean isChangedVelocity;
    boolean viewBobbing;

    public Fly() {
        super("Fly", "Allows Flight", 0, Category.Movement);
        this.addSettings(this.mode, this.timer, this.speed, this.viewbobbing, this.nomotion);
    }

    @Override
    public void onEnable() {
        String mode;
        double x = Fly.mc.thePlayer.posX;
        double y = Fly.mc.thePlayer.posY;
        double z = Fly.mc.thePlayer.posZ;
        if (this.viewbobbing.isChecked()) {
            this.viewBobbing = Fly.mc.gameSettings.viewBobbing;
            Fly.mc.gameSettings.viewBobbing = true;
        }
        this.isChangedVelocity = false;
        switch (mode = this.mode.getMode()) {
            case "Watchdog": {
                if (Fly.mc.thePlayer.onGround) {
                    Fly.mc.thePlayer.motionY = 0.05;
                } else {
                    ChatHelper.addChat("You Must Be On Ground!");
                    ModuleManager.getModule(Fly.class).setToggled(false);
                }
                this.state = 0;
                this.start_x = Fly.mc.thePlayer.posX;
                this.start_y = Fly.mc.thePlayer.posY;
                this.start_z = Fly.mc.thePlayer.posZ;
                break;
            }
            case "HypixelSlime": {
                HypixelHelper.slimeDisable();
                break;
            }
            case "BlocksMC": {
                break;
            }
            case "GhostlyInv": 
            case "Ghostly": 
            case "VerusDMG": 
            case "VerusDEV": {
                Fly.damageVerus();
            }
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        Fly.mc.gameSettings.viewBobbing = this.viewBobbing;
        if (this.nomotion.isChecked()) {
            EntityHelper.setMotion(0.0);
        }
        Timer.timerSpeed = 1.0f;
        super.onDisable();
    }

    @Subscribe
    public void onCollide(CollideEvent e) {
        String mode = this.mode.getMode();
        double x = e.getX();
        double y = e.getY();
        double z = e.getZ();
        switch (mode) {
            case "Watchdog": {
                switch (this.state) {
                    case 0: {
                        if (!(y >= this.start_y - 1.0)) break;
                        e.setBoundingBox(AxisAlignedBB.fromBounds(15.0, 1.0, 15.0, -15.0, -1.0, -15.0).offset(x, this.start_y + 1.0, z));
                        break;
                    }
                    case 1: {
                        e.setBoundingBox(AxisAlignedBB.fromBounds(15.0, 1.0, 15.0, -15.0, -1.0, -15.0).offset(x, y + 0.05, z));
                    }
                }
                break;
            }
            case "Collide": {
                if (!(y < Fly.mc.thePlayer.posY) || Fly.mc.gameSettings.keyBindSneak.isKeyDown()) break;
                e.setBoundingBox(AxisAlignedBB.fromBounds(15.0, 1.0, 15.0, -15.0, -1.0, -15.0).offset(x, y, z));
                break;
            }
            case "VerusDMG": 
            case "VerusDEV": 
            case "GhostlyInv": 
            case "BlocksMC": 
            case "Ghostly": {
                if (!(y < Fly.mc.thePlayer.posY)) break;
                e.setBoundingBox(AxisAlignedBB.fromBounds(15.0, 1.0, 15.0, -15.0, -1.0, -15.0).offset(x, y, z));
            }
        }
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        String mode = this.mode.getMode();
        this.setDisplayName("Fly\u00a77 " + this.mode.getMode());
        if (this.state == 1) {
            Timer.timerSpeed = (float)this.timer.getVal();
        }
        switch (mode) {
            case "Watchdog": {
                if (e.isPre() && this.state == 0) {
                    Fly.mc.thePlayer.posY = this.start_y;
                }
                if (this.state == 0) break;
                break;
            }
            case "Vanilla": {
                if (!e.isPre()) break;
                EntityHelper.setMotion(this.speed.getVal());
                if (MovementInput.jump && !ModuleManager.getModule(TargetStrafe.class).isToggled()) {
                    Fly.mc.thePlayer.motionY = 0.5;
                    break;
                }
                if (MovementInput.sneak && !ModuleManager.getModule(TargetStrafe.class).isToggled()) {
                    Fly.mc.thePlayer.motionY = -0.5;
                    break;
                }
                Fly.mc.thePlayer.motionY = 0.0;
                Fly.mc.thePlayer.motionY = Fly.mc.thePlayer.motionY - (Fly.mc.thePlayer.ticksExisted % 10 == 0 ? 0.08 : 0.0);
                break;
            }
            case "HypixelSlime": {
                if (!this.isChangedVelocity || !e.isPre()) break;
                EntityHelper.setMotion(this.speed.getVal());
                if (MovementInput.jump) {
                    Fly.mc.thePlayer.motionY = 0.75;
                    break;
                }
                if (MovementInput.sneak) {
                    Fly.mc.thePlayer.motionY = -0.75;
                    break;
                }
                Fly.mc.thePlayer.motionY = 0.0;
                Fly.mc.thePlayer.motionY = Fly.mc.thePlayer.motionY - (Fly.mc.thePlayer.ticksExisted % 10 == 0 ? 0.08 : 0.0);
                break;
            }
            case "TP-Fly": {
                Fly.mc.thePlayer.motionY = 0.0;
                Fly.mc.thePlayer.motionX = 0.0;
                Fly.mc.thePlayer.motionZ = 0.0;
                double value = MathHelper.getRandomDoubleInRange(new Random(), 4.42, 5.19);
                double x = -Math.sin(MoveUtils.getDirection()) * value;
                double z = Math.cos(MoveUtils.getDirection()) * value;
                if (Fly.mc.thePlayer.isMoving()) {
                    if ((double)Fly.mc.thePlayer.ticksExisted % this.speed.getVal() == 0.0) {
                        Fly.mc.thePlayer.setPosition(Fly.mc.thePlayer.posX + x, Fly.mc.thePlayer.posY - 1.75, Fly.mc.thePlayer.posZ + z);
                    } else {
                        Fly.mc.thePlayer.setPosition(Fly.mc.thePlayer.posX, Fly.mc.thePlayer.posY, Fly.mc.thePlayer.posZ);
                    }
                }
                Fly.mc.thePlayer.motionY = 0.0;
            }
            case "Invaded": {
                if (!this.isChangedVelocity) break;
                Timer.timerSpeed = 0.4f;
                EntityHelper.setMotion(this.speed.getVal());
                break;
            }
            case "MushMC": {
                Timer.timerSpeed = 0.85f;
                EntityHelper.setMotion(this.speed.getVal());
                Fly.mc.thePlayer.motionY = MovementInput.jump ? 0.75 : (MovementInput.sneak ? -0.75 : (Fly.mc.thePlayer.motionY = 0.0) - (Fly.mc.thePlayer.ticksExisted % 10 == 0 ? 0.08 : 0.0));
            }
        }
    }

    @Subscribe
    public void onPacket(PacketEvent event) {
        String mode;
        switch (mode = this.mode.getMode()) {
            case "Watchdog": {
                if (event.getPacket() instanceof S08PacketPlayerPosLook && this.state == 0) {
                    this.state = 1;
                }
                if (event.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition && this.state == 1) {
                    ((C03PacketPlayer.C04PacketPlayerPosition)event.getPacket()).onGround = false;
                }
                if (event.getPacket() instanceof C03PacketPlayer && this.state == 1) {
                    ((C03PacketPlayer)event.getPacket()).onGround = false;
                }
                if (event.getPacket() instanceof C03PacketPlayer.C05PacketPlayerLook && this.state == 1) {
                    ((C03PacketPlayer.C05PacketPlayerLook)event.getPacket()).onGround = false;
                }
                if (!(event.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook) || this.state != 1) break;
                ((C03PacketPlayer.C06PacketPlayerPosLook)event.getPacket()).onGround = false;
                break;
            }
            case "HypixelSlime": {
                if (!(event.getPacket() instanceof S12PacketEntityVelocity) || Fly.mc.thePlayer.getEntityId() != ((S12PacketEntityVelocity)event.getPacket()).getEntityID()) break;
                event.setCancelled(true);
                this.isChangedVelocity = true;
                break;
            }
            case "VerusDMG": {
                if (!(event.getPacket() instanceof S12PacketEntityVelocity) || Fly.mc.thePlayer.getEntityId() != ((S12PacketEntityVelocity)event.getPacket()).getEntityID()) break;
                Fly.mc.thePlayer.setPosition(Fly.mc.thePlayer.posX, Fly.mc.thePlayer.posY + 1.0, Fly.mc.thePlayer.posZ);
                this.isChangedVelocity = true;
                break;
            }
            case "Invaded": {
                if (!(event.getPacket() instanceof S12PacketEntityVelocity) || Fly.mc.thePlayer.getEntityId() != ((S12PacketEntityVelocity)event.getPacket()).getEntityID()) break;
                this.isChangedVelocity = true;
            }
        }
    }

    @Subscribe
    public void onMoveRaw(MoveRawEvent e) {
        switch (this.mode.getMode()) {
            case "Watchdog": {
                if (this.state != 0) break;
                e.setX(0.0);
                e.setZ(0.0);
                break;
            }
            case "BlocksMC": {
                if (!Fly.mc.thePlayer.isInLava() && !Fly.mc.thePlayer.isInWater() && !Fly.mc.thePlayer.isOnLadder() && Fly.mc.thePlayer.ridingEntity == null && Fly.mc.thePlayer.isMoving()) {
                    Timer.timerSpeed = 0.05f;
                    if (Fly.mc.thePlayer.hurtTime <= 16) {
                        EntityHelper.setMotion(this.speed.getVal());
                    }
                }
            }
            case "VerusDEV": 
            case "Ghostly": {
                if (Fly.mc.thePlayer.isInLava() || Fly.mc.thePlayer.isInWater() || Fly.mc.thePlayer.isOnLadder() || Fly.mc.thePlayer.ridingEntity != null) break;
                if (Fly.mc.thePlayer.isMoving()) {
                    Fly.mc.gameSettings.keyBindJump.pressed = false;
                    if (Fly.mc.thePlayer.onGround) {
                        if (Fly.mc.gameSettings.keyBindForward.isPressed()) {
                            Fly.mc.thePlayer.setSprinting(true);
                        }
                        Fly.mc.thePlayer.jump();
                        e.y = 0.42f;
                        Fly.mc.thePlayer.motionY = 0.0;
                    }
                    if (Fly.mc.thePlayer.hurtTime <= 16) {
                        EntityHelper.setMotion(this.speed.getVal());
                    }
                }
                Timer.timerSpeed = 0.45f;
                break;
            }
            case "GhostlyInv": {
                if (!Fly.mc.thePlayer.isInLava() && !Fly.mc.thePlayer.isInWater() && !Fly.mc.thePlayer.isOnLadder() && Fly.mc.thePlayer.ridingEntity == null && Fly.mc.thePlayer.isMoving()) {
                    Fly.mc.gameSettings.keyBindJump.pressed = false;
                    Fly.mc.thePlayer.motionY = MovementInput.jump ? 0.3 : (MovementInput.sneak ? -0.3 : (Fly.mc.thePlayer.motionY = 0.0) - (Fly.mc.thePlayer.ticksExisted % 10 == 0 ? 0.08 : 0.0));
                    EntityHelper.setMotion(this.speed.getVal());
                }
                if (Fly.mc.thePlayer.ticksExisted % 90 != 0) break;
                this.setToggled(false);
                break;
            }
            case "VerusDMG": {
                if (Fly.mc.thePlayer.isInLava() || Fly.mc.thePlayer.isInWater() || Fly.mc.thePlayer.isOnLadder() || Fly.mc.thePlayer.ridingEntity != null || !this.isChangedVelocity) break;
                if (Fly.mc.thePlayer.isMoving()) {
                    Fly.mc.gameSettings.keyBindJump.pressed = false;
                    if (Fly.mc.thePlayer.onGround && Fly.mc.gameSettings.keyBindForward.isPressed()) {
                        Fly.mc.thePlayer.setSprinting(true);
                    }
                    if (Fly.mc.thePlayer.hurtTime <= 16) {
                        EntityHelper.setMotion(Fly.mc.thePlayer.hurtTime < 7 ? this.speed.getVal() * 1.5 : this.speed.getVal());
                    }
                }
                Fly.mc.thePlayer.onGround = true;
                if (MovementInput.jump) {
                    Fly.mc.thePlayer.motionY = 0.75;
                    break;
                }
                if (!MovementInput.sneak) break;
                Fly.mc.thePlayer.motionY = -0.75;
            }
        }
    }

    public static void damageVerus() {
        mc.getNetHandler().getNetworkManager().sendPacket(new C0BPacketEntityAction(Fly.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
        double val1 = 0.0;
        for (int i = 0; i <= 6; ++i) {
            Fly.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Fly.mc.thePlayer.posX, Fly.mc.thePlayer.posY + (val1 += 0.5), Fly.mc.thePlayer.posZ, true));
        }
        double val2 = Fly.mc.thePlayer.posY + val1;
        ArrayList<Float> vals = new ArrayList<Float>();
        vals.add(Float.valueOf(0.0784f));
        vals.add(Float.valueOf(0.0784f));
        vals.add(Float.valueOf(0.23052737f));
        vals.add(Float.valueOf(0.30431682f));
        vals.add(Float.valueOf(0.37663049f));
        vals.add(Float.valueOf(0.4474979f));
        vals.add(Float.valueOf(0.5169479f));
        vals.add(Float.valueOf(0.585009f));
        vals.add(Float.valueOf(0.65170884f));
        vals.add(Float.valueOf(0.15372962f));
        Iterator iterator = vals.iterator();
        while (iterator.hasNext()) {
            float value = ((Float)iterator.next()).floatValue();
            val2 -= (double)value;
        }
        Fly.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Fly.mc.thePlayer.posX, val2, Fly.mc.thePlayer.posZ, false));
        Fly.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
        mc.getNetHandler().getNetworkManager().sendPacket(new C0BPacketEntityAction(Fly.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
        Fly.mc.thePlayer.jump();
    }
}

