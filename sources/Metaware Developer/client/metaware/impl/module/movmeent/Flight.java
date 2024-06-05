package client.metaware.impl.module.movmeent;

import client.metaware.Metaware;
import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.Property;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.api.properties.property.impl.EnumProperty;
import client.metaware.impl.event.impl.network.PacketEvent;
import client.metaware.impl.event.impl.player.MovePlayerEvent;
import client.metaware.impl.event.impl.player.PlayerBoundingShitEvent;
import client.metaware.impl.event.impl.player.PlayerJumpEvent;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.module.combat.TargetStrafeV2;
import client.metaware.impl.utils.system.TimerUtil;
import client.metaware.impl.utils.util.PacketUtil;
import client.metaware.impl.utils.util.player.MovementUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Keyboard;

import java.util.ArrayDeque;

@ModuleInfo(name = "Flight", renderName = "Flight", category = Category.MOVEMENT, keybind = Keyboard.KEY_G)
public class Flight extends Module {

    public boolean done, reset, invert, damaged;
    private double lastDist;
    private double moveSpeed, yPos;
    private final ArrayDeque<Packet> packetQueue = new ArrayDeque<>();
    private double moveSpeedMotion = 1F;
    private int stage = 0, counter, ticks, damagedTicks;
    private final TimerUtil timer = new TimerUtil();

    public EnumProperty<Mode> mode = new EnumProperty<>("Mode", Mode.Verus);
    public EnumProperty<VerusMode> verusMode = new EnumProperty<>("Verus Mode", VerusMode.Verus1, () -> mode.getValue() == Mode.Verus);
    public DoubleProperty speed = new DoubleProperty("Speed", 1, 1.0f, 10f, 0.1f, () -> mode.getValue() == Mode.Vanilla || mode.getValue() == Mode.DamageFly);
    public EnumProperty<Verus3Mode> verus3Mode = new EnumProperty<>("Verus3 Mode", Verus3Mode.Slow, () -> mode.getValue() == Mode.Verus && verusMode.getValue() == VerusMode.Verus3);
    public Property<Boolean> flat = new Property<>("Flat", true, () -> mode.getValue() != Mode.Verus);

    public enum Mode{
        Verus, Vanilla, DamageFly, BedwarsPractice, Hypixel
    }

    public enum HypixelMode{
        Vroom, Infinite, AirWalk
    }

    public enum VerusMode{
        Verus1, Verus2, Verus3;
    }

    public enum Verus3Mode{
        Fast, Slow;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        done = false;
        //timer.reset();
        reset = false;
        yPos = mc.thePlayer.posY;
        counter = 0;
        stage = 0;
        packetQueue.clear();
        switch (mode.getValue()){
            case DamageFly:{
                double x = mc.thePlayer.posX, y = mc.thePlayer.posY, z = mc.thePlayer.posZ;
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 3.1, z, false)); // or mc.getNetHandler.getNetworkManager().sendPacket
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 1.0E-4, z, false));
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 1.0E-2, z, true));
                break;
            }
            case Verus:{
                break;
            }
        }
    }


    @EventHandler
    private final Listener<PacketEvent> packetEventListener = event -> {
        if(mc.thePlayer.ticksExisted <= 30) return;
        switch (mode.getValue()){
            case Hypixel:{

                break;
            }
        }
    };

    @EventHandler
    private Listener<MovePlayerEvent> eventListener = event -> {
        if(mc.thePlayer.ticksExisted <= 30) return;
        setSuffix(mode.getValue().toString());
        switch (mode.getValue()) {
            case Verus: {
                switch(verusMode.getValue()){
                    case Verus1: {
                        if (ticks % 10 == 0 && mc.thePlayer.onGround) {
                            MovementUtils.strafe(1f);
                            event.setY(mc.thePlayer.motionY = 0.42F);
                            ticks = 0;
                            event.setY(mc.thePlayer.motionY = 0.0);
                            mc.timer.timerSpeed = 4F;
                        } else {
                            if (mc.gameSettings.keyBindJump.isKeyDown() && ticks % 2 == 1) {
                                event.setY(mc.thePlayer.motionY = 0.5F);
                                MovementUtils.strafe(0.48f);
                                yPos += 0.5;
                                mc.timer.timerSpeed = 1f;
                                return;
                            }
                            mc.timer.timerSpeed = 1f;
                            if (mc.thePlayer.onGround) {
                                MovementUtils.strafe(0.8f);
                            } else {
                                MovementUtils.strafe(0.72f);
                            }
                        }
                        ticks++;
                        break;
                    }
                    case Verus2: {
                        if(!done) timer.reset();
                        if(!done && !timer.delay(250)) {
                            event.setX(0);
                            // event.setY(0);
                            event.setZ(0);
                            return;
                        }
                        if(mc.thePlayer.hurtTime == 0 && !reset) {
                            double x = mc.thePlayer.posX, y = mc.thePlayer.posY, z = mc.thePlayer.posZ;
                            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 3.1, z, false)); // or mc.getNetHandler.getNetworkManager().sendPacket
                            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 1.0E-4, z, false));
                            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 1.0E-2, z, true));
                            event.setX(0);
                            event.setZ(0);
                        }

                        if(!reset && mc.thePlayer.hurtTime >= 5){
                            reset = true;
                        }

                        if(reset) {
                            MovementUtils.setSpeed(event, 2.5);

                            if (mc.thePlayer.isCollided) {
                                mc.thePlayer.isCollided = false;
                            }

                            mc.thePlayer.cameraYaw = 0.09F;
                            mc.thePlayer.cameraPitch = 0.09F;

                            event.setY(mc.thePlayer.motionY = mc.thePlayer.movementInput.jump ? 0.42F : mc.thePlayer.movementInput.sneak ? -0.42F : -0.0625);

                            if (!mc.thePlayer.isMoving()) {
                                MovementUtils.setSpeed(event, 0);
                                mc.thePlayer.cameraYaw = 0F;
                                mc.thePlayer.cameraPitch = 0F;
                            }
                        }
                        break;
                    }
                    case Verus3:{
                        switch (verus3Mode.getValue()) {
                            case Slow:
                                if (this.mc.thePlayer.isMoving() && this.mc.thePlayer.onGround) {
                                    if (this.mc.thePlayer.ticksExisted % 2 == 0) {
                                        MovementUtils.setSpeed(event, 0.32F);
                                        break;
                                    }
                                    MovementUtils.setSpeed(event, 0.52F);
                                }
                                break;
                            case Fast:
                                if (this.mc.thePlayer.isMoving() && this.mc.thePlayer.onGround) {
                                    if (this.mc.thePlayer.ticksExisted % 2 == 0) {
                                        MovementUtils.setSpeed(event, 1.8F);
                                        break;
                                    }
                                    MovementUtils.setSpeed(event, 0.35F);
                                }
                                break;
                        }
                        break;
                    }
                }
                break;
            }
            case Vanilla:
            case DamageFly: {
                    TargetStrafeV2 ts = Metaware.INSTANCE.getModuleManager().getModuleByClass(TargetStrafeV2.class);
                    if (flat.getValue()) {
                        event.setY(mc.thePlayer.motionY = ts.shouldStrafe() ? 0 : mc.thePlayer.movementInput.jump ? 0.42f : mc.thePlayer.movementInput.sneak ? -0.42f : 0);
                    } else {
                        event.setY(mc.thePlayer.motionY = ts.shouldStrafe() ? mc.thePlayer.ticksExisted % 18 == 0 ? 0.0625 : -0.0625 : mc.thePlayer.movementInput.jump ? 0.42f : mc.thePlayer.movementInput.sneak ? -0.42f : -0.0625);
                    }
                   if(mc.thePlayer.isMoving()) {
                       MovementUtils.setSpeed(event, speed.getValue());
                   }else{
                       mc.thePlayer.motionX *= mc.thePlayer.motionZ *= 0;
                   }
                break;
            }
            case BedwarsPractice:{
                MovementUtils.setSpeed(event, 3.3);
                break;
            }
            case Hypixel: {
                MovementUtils.setSpeed(event, 0);
                break;
            }
        }
    };

    @EventHandler
    private final Listener<UpdatePlayerEvent> eventListener2 = event -> {
        if(mc.thePlayer == null || mc.theWorld == null) return;
        switch (mode.getValue()){
            case BedwarsPractice:{
                if(!event.isPre()) return;
                if(!mc.thePlayer.onGround){
                    PacketUtil.packetNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1.89 - Math.random() / 500, mc.thePlayer.posZ, false));
                    PacketUtil.packetNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                }
                mc.timer.timerSpeed = (float) (0.3 - Math.random() / 500);
                break;
            }
            case Vanilla:{
                int currentItem = mc.thePlayer.inventory.currentItem;


//                if(!mc.thePlayer.onGround){
//                    PacketUtil.packetNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
//                    PacketUtil.packetNoEvent(new C08PacketPlayerBlockPlacement(null));
//                }

                break;
            }
            case Hypixel:{
                mc.thePlayer.motionY = mc.thePlayer.ticksExisted % 4 == 0 ? 0.001 : -0.001;
                if(timer.delay(1150)){
                    mc.thePlayer.setPosition(mc.thePlayer.posX + MovementUtils.yawPos(7.99 + Math.random() / 500)[0], mc.thePlayer.posY, mc.thePlayer.posZ + MovementUtils.yawPos(7.99 + Math.random() / 500)[1]);
                    PacketUtil.packetNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1.7525 + Math.random() / 500, mc.thePlayer.posZ, true));
                    timer.reset();
                }
                break;
            }
        }
    };

    @EventHandler
    private Listener<PlayerBoundingShitEvent> eventUpdate = event -> {
        switch (mode.getValue()) {
            case Verus:{
                switch(verusMode.getValue()) {
                    case Verus3:
                    case Verus1: {
                        if (this.mc.thePlayer.isSneaking())
                            return;
                        if (event.getBlock() instanceof net.minecraft.block.BlockAir && event.getY() < this.mc.thePlayer.posY)
                            event.setBoundingBox(AxisAlignedBB.fromBounds(event.getX(), event.getY(), event.getZ(), event.getX() + 1.0D, yPos, event.getZ() + 1.0D));
                        break;
                    }
                }
                break;
            }
            case Hypixel:{
                break;
            }
        }

    };

    @EventHandler
    private Listener<PlayerJumpEvent> eventListener1 = event -> {
        event.setCancelled((mode.getValue() == Mode.Verus && verusMode.getValue() == VerusMode.Verus1) || (mode.getValue() == Mode.Hypixel));
    };

    @EventHandler
    private final Listener<PacketEvent> eventListener3 = event -> {

    };

}