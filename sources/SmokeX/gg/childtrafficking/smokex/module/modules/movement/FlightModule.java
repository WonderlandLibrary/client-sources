// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import gg.childtrafficking.smokex.utils.system.NetworkUtils;
import gg.childtrafficking.smokex.utils.player.MovementUtils;
import gg.childtrafficking.smokex.utils.system.StringUtils;
import java.util.ArrayList;
import java.util.LinkedList;
import gg.childtrafficking.smokex.event.events.network.EventReceivePacket;
import gg.childtrafficking.smokex.event.events.network.EventSendPacket;
import gg.childtrafficking.smokex.event.events.player.EventMove;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import net.minecraft.network.play.client.C03PacketPlayer;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import gg.childtrafficking.smokex.property.properties.EnumProperty;
import net.minecraft.network.Packet;
import java.util.List;
import gg.childtrafficking.smokex.utils.system.TimerUtil;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "Flight", renderName = "Flight", description = "Fly through the air.", aliases = { "fly" }, category = ModuleCategory.MOVEMENT)
public final class FlightModule extends Module
{
    private int stage;
    private double motion;
    private double lastDist;
    private final TimerUtil stopwatch;
    private final List<Packet<?>> packets;
    private final EnumProperty<Mode> modeEnumProperty;
    private final NumberProperty<Double> speedProperty;
    private double lastX;
    private double lastY;
    private double lastZ;
    private double x;
    private double y;
    private double z;
    private final List<C03PacketPlayer> penis;
    private EventListener<EventUpdate> eventUpdate;
    private EventListener<EventMove> eventMove;
    private EventListener<EventSendPacket> eventSendPacket;
    private final EventListener<EventReceivePacket> receivePacketEventListener;
    
    public FlightModule() {
        this.stopwatch = new TimerUtil();
        this.packets = new LinkedList<Packet<?>>();
        this.modeEnumProperty = new EnumProperty<Mode>("Mode", Mode.HOVER);
        this.speedProperty = new NumberProperty<Double>("Speed", 1.0, 1.0, 10.0, 0.1);
        this.penis = new ArrayList<C03PacketPlayer>();
        this.eventUpdate = (event -> {
            this.setSuffix(StringUtils.upperSnakeCaseToPascal(this.modeEnumProperty.getValueAsString()));
            switch (this.modeEnumProperty.getValue()) {
                case VANILLA: {
                    event.setOnGround(false);
                    break;
                }
                case WATCHDOG: {
                    if (event.isPre()) {
                        if (MovementUtils.isMoving()) {}
                        this.mc.thePlayer.motionY = 0.0;
                        final double[] gar1 = { 0.41999998688698, 0.7531999805212, 1.00133597911214, 1.16610926093821, 1.24918707874468, 1.24918707874468, 1.1707870772188 };
                        if (this.stage == 0) {
                            for (int i = 0; i < gar1.length; ++i) {
                                NetworkUtils.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.lastX, this.lastY + gar1[i], this.lastZ, false));
                            }
                            event.cancel();
                            System.out.println("Fapped at stage 0");
                            this.stage = 1;
                            break;
                        }
                        else if (this.stage == 1) {
                            final double[] gar2 = { 1.0155550727022, 0.78502770378923, 0.48071087633169, 0.10408037809304, -0.42 };
                            this.mc.timer.timerSpeed = 1.0f;
                            for (int j = 0; j < gar2.length; ++j) {
                                NetworkUtils.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.lastX, this.lastY + gar2[j], this.lastZ, false));
                            }
                            System.out.println("Fapped at stage 2");
                            this.stage = 2;
                            break;
                        }
                        else if (this.stage >= 3) {
                            event.setPosY(this.y);
                            this.stage = 999;
                            System.out.println(" fap " + this.stage);
                            MovementUtils.getXZ(MovementUtils.getBaseMoveSpeed() * 2.0);
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            if (event.isPre()) {
                final double xDif = this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX;
                final double zDif = this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ;
                this.lastDist = Math.sqrt(xDif * xDif + zDif * zDif);
            }
            return;
        });
        this.eventMove = (event -> {
            switch (this.modeEnumProperty.getValue()) {
                case WATCHDOG: {
                    MovementUtils.setSpeed(event, MovementUtils.getBaseMoveSpeed());
                    break;
                }
                case HOVER: {
                    switch (this.stage) {
                        case 0: {
                            final EntityPlayerSP thePlayer = this.mc.thePlayer;
                            final double motionY;
                            event.setY(thePlayer.motionY = motionY);
                            this.motion = MovementUtils.getBaseMoveSpeed() * 2.14;
                            break;
                        }
                        case 1: {
                            this.motion *= 0.57;
                            break;
                        }
                        case 2:
                        case 3: {
                            this.motion *= 0.94;
                            break;
                        }
                        case 4: {
                            final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
                            final double motionY2;
                            event.setY(thePlayer2.motionY = motionY2);
                            break;
                        }
                        default: {
                            final EntityPlayerSP thePlayer3 = this.mc.thePlayer;
                            final double motionY3;
                            event.setY(thePlayer3.motionY = motionY3);
                            this.motion *= 0.96;
                            break;
                        }
                    }
                    if (MovementUtils.isMoving()) {
                        MovementUtils.setSpeed(event, Math.max(MovementUtils.getBaseMoveSpeed(), this.motion));
                    }
                    else {
                        MovementUtils.setSpeed(event, Math.max(MovementUtils.getBaseMoveSpeed(), this.motion), 1.0f, 0.0f, this.mc.thePlayer.rotationYaw);
                    }
                    ++this.stage;
                    break;
                }
                case VANILLA: {
                    this.mc.thePlayer.cameraYaw = 0.1f;
                    this.motion -= this.motion / 159.0;
                    final EntityPlayerSP thePlayer4 = this.mc.thePlayer;
                    final double motionY4;
                    event.setY(thePlayer4.motionY = motionY4);
                    if (!MovementUtils.isMoving()) {
                        this.mc.thePlayer.motionX = 0.0;
                        this.mc.thePlayer.motionZ = 0.0;
                    }
                    if (this.mc.thePlayer.movementInput.jump) {
                        final EntityPlayerSP thePlayer5 = this.mc.thePlayer;
                        final double motionY5 = -this.motion;
                        event.setY(thePlayer5.motionY = motionY5);
                    }
                    if (this.mc.thePlayer.movementInput.sneak) {
                        final EntityPlayerSP thePlayer6 = this.mc.thePlayer;
                        final double motionY6 = -this.motion;
                        event.setY(thePlayer6.motionY = motionY6);
                    }
                    if (MovementUtils.isMoving()) {
                        MovementUtils.setSpeed(event, Math.max(MovementUtils.getBaseMoveSpeed(), this.motion));
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            return;
        });
        this.eventSendPacket = (event -> {
            switch (this.modeEnumProperty.getValue()) {
                case WATCHDOG: {
                    if (event.getPacket() instanceof C03PacketPlayer) {
                        event.cancel();
                        if (((C03PacketPlayer)event.getPacket()).isMoving()) {
                            this.packets.add(event.getPacket());
                        }
                        while (!this.packets.isEmpty() && this.stage >= 3) {
                            final C03PacketPlayer packetPlayer = this.packets.remove(0);
                            if (packetPlayer instanceof C03PacketPlayer.C04PacketPlayerPosition || packetPlayer instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
                                packetPlayer.y = this.y;
                            }
                            NetworkUtils.sendPacket(packetPlayer);
                        }
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            return;
        });
        this.receivePacketEventListener = (event -> {
            if (event.getPacket() instanceof S08PacketPlayerPosLook && this.stage == 2) {
                final S08PacketPlayerPosLook packetPlayerPosLook = (S08PacketPlayerPosLook)event.getPacket();
                this.y = packetPlayerPosLook.getY();
                this.mc.thePlayer.motionY = 0.10408037809304;
                NetworkUtils.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(packetPlayerPosLook.getX(), packetPlayerPosLook.getY(), packetPlayerPosLook.getZ(), false));
                this.stopwatch.reset();
                this.stage = 3;
                event.cancel();
            }
        });
    }
    
    @Override
    public void onEnable() {
        this.stage = 0;
        if (this.modeEnumProperty.getValue() == Mode.VANILLA) {
            this.motion = this.speedProperty.getValue();
        }
        this.y = this.mc.thePlayer.posY + this.mc.thePlayer.motionY;
        switch (this.modeEnumProperty.getValue()) {
            case WATCHDOG: {
                this.lastX = this.mc.thePlayer.posX;
                this.lastY = this.mc.thePlayer.posY;
                this.lastZ = this.mc.thePlayer.posZ;
                this.y = this.mc.thePlayer.posY;
                break;
            }
        }
        this.packets.clear();
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        switch (this.modeEnumProperty.getValue()) {
            case WATCHDOG: {
                while (!this.packets.isEmpty() && this.stage >= 3) {
                    final C03PacketPlayer packetPlayer = this.packets.remove(0);
                    if (packetPlayer instanceof C03PacketPlayer.C04PacketPlayerPosition || packetPlayer instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
                        packetPlayer.y = this.y;
                    }
                    NetworkUtils.sendPacket(packetPlayer);
                }
                break;
            }
        }
        this.stage = 0;
        super.onDisable();
    }
    
    private enum Mode
    {
        HOVER, 
        VANILLA, 
        WATCHDOG;
    }
}
