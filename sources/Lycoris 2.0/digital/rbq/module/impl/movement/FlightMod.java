/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.movement;

import java.util.ArrayDeque;
import java.util.ArrayList;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.Timer;
import digital.rbq.annotations.Label;
import digital.rbq.core.Autumn;
import digital.rbq.events.packet.ReceivePacketEvent;
import digital.rbq.events.packet.SendPacketEvent;
import digital.rbq.events.player.MotionUpdateEvent;
import digital.rbq.events.player.MoveEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Aliases;
import digital.rbq.module.annotations.Bind;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.impl.movement.TargetStrafeMod;
import digital.rbq.module.option.impl.BoolOption;
import digital.rbq.module.option.impl.DoubleOption;
import digital.rbq.module.option.impl.EnumOption;
import digital.rbq.notification.NotificationPublisher;
import digital.rbq.notification.NotificationType;
import digital.rbq.utils.MathUtils;
import digital.rbq.utils.MovementUtils;
import digital.rbq.utils.PlayerUtils;
import digital.rbq.utils.Stopwatch;
import digital.rbq.utils.pathfinding.CustomVec3;
import digital.rbq.utils.pathfinding.PathfindingUtils;

@Bind(value="F")
@Label(value="Flight")
@Category(value=ModuleCategory.MOVEMENT)
@Aliases(value={"flight", "fly"})
public final class FlightMod
extends Module {
    public final EnumOption<Mode> mode = new EnumOption<Mode>("Mode", Mode.WATCHDOG);
    public final BoolOption viewBobbing = new BoolOption("View Bobbing", true);
    public final BoolOption multiplier = new BoolOption("Multiplier", false, () -> this.mode.getValue() == Mode.WATCHDOG);
    public final DoubleOption multiplyspeed = new DoubleOption("Multiply Speed", 1.8, () -> this.mode.getValue() == Mode.WATCHDOG, 0.1, 3.0, 0.05);
    public final DoubleOption multiplytime = new DoubleOption("Multiply Time", 1190.0, () -> this.mode.getValue() == Mode.WATCHDOG, 100.0, 3000.0, 50.0);
    public final DoubleOption speed = new DoubleOption("Watchdog Speed", 1.0, () -> this.mode.getValue() == Mode.WATCHDOG, 0.5, 1.0, 0.05);
    public final DoubleOption vanillaSpeed = new DoubleOption("Vanilla Speed", 5.0, () -> this.mode.getValue() == Mode.VANILLA, 0.1, 7.0, 0.1);
    private final ArrayDeque<Packet<?>> arrayDeque = new ArrayDeque();
    private final Stopwatch stopwatch = new Stopwatch();
    private int ticks;
    private int stage;
    private CustomVec3 lastPos;
    private boolean tp;
    private double lastDist;
    private double moveSpeed;
    private TargetStrafeMod targetStrafe;
    private double y;

    public FlightMod() {
        this.setMode(this.mode);
        this.addOptions(this.mode, this.speed, this.vanillaSpeed, this.multiplyspeed, this.multiplytime, this.viewBobbing, this.multiplier);
    }

    public static FlightMod getInstance() {
        return Autumn.MANAGER_REGISTRY.moduleManager.getModuleOrNull(FlightMod.class);
    }

    @Listener(value=SendPacketEvent.class)
    public final void onSendPacket(SendPacketEvent event) {
        if (this.mode.getValue() == Mode.WATCHDOG && this.stage == 0) {
            event.setCancelled();
        }
        if (this.mode.getValue() == Mode.MINEPLEX && !this.stopwatch.elapsed(6100L)) {
            event.setCancelled();
        }
        if (this.mode.getValue() == Mode.DISABLER && event.getPacket() instanceof C03PacketPlayer && !this.tp) {
            event.setCancelled();
        }
    }

    @Listener(value=ReceivePacketEvent.class)
    public final void onReceivePacket(ReceivePacketEvent event) {
        if (this.mode.getValue() == Mode.MINEPLEX) {
            if ((event.getPacket() instanceof S02PacketChat || event.getPacket() instanceof S45PacketTitle) && !this.stopwatch.elapsed(6000L)) {
                event.setCancelled();
            }
        } else if (this.mode.getValue() == Mode.WATCHDOG) {
            if (event.getPacket() instanceof S08PacketPlayerPosLook) {
                NotificationPublisher.queue("Flag", "Disabled Flight due to flag.", NotificationType.INFO);
                this.toggle();
            }
        } else if (this.mode.getValue() == Mode.DISABLER && event.getPacket() instanceof S08PacketPlayerPosLook) {
            NotificationPublisher.queue("Teleported", "Teleported after lagback.", NotificationType.INFO);
            if (!this.tp) {
                this.tp = true;
            }
        }
    }

    private void mineplexDamage(EntityPlayerSP playerRef) {
        NetHandlerPlayClient netHandler = mc.getNetHandler();
        double offset = 0.0601f;
        for (int i = 0; i < 20; ++i) {
            int j = 0;
            while ((double)j < (double)PlayerUtils.getMaxFallDist() / (double)0.0601f + 1.0) {
                netHandler.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(FlightMod.mc.thePlayer.posX, FlightMod.mc.thePlayer.posY + (double)0.0601f, FlightMod.mc.thePlayer.posZ, false));
                netHandler.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(FlightMod.mc.thePlayer.posX, FlightMod.mc.thePlayer.posY + (double)5.0E-4f, FlightMod.mc.thePlayer.posZ, false));
                ++j;
            }
        }
        netHandler.addToSendQueueSilent(new C03PacketPlayer(true));
    }

    @Override
    public void onEnabled() {
        this.arrayDeque.clear();
        this.tp = false;
        this.lastPos = new CustomVec3(FlightMod.mc.thePlayer.posX, FlightMod.mc.thePlayer.posY, FlightMod.mc.thePlayer.posZ);
        EntityPlayerSP player = FlightMod.mc.thePlayer;
        if (this.mode.getValue() == Mode.MINEPLEX) {
            this.mineplexDamage(player);
        }
        if (this.mode.getValue() == Mode.DISABLER) {
            mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(FlightMod.mc.thePlayer.posX, FlightMod.mc.thePlayer.posY, FlightMod.mc.thePlayer.posZ, true));
            mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(FlightMod.mc.thePlayer.posX, FlightMod.mc.thePlayer.posY + 0.18, FlightMod.mc.thePlayer.posZ, true));
            mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(FlightMod.mc.thePlayer.posX, FlightMod.mc.thePlayer.posY + 0.08, FlightMod.mc.thePlayer.posZ, true));
        }
        this.stopwatch.reset();
        this.y = 0.0;
        this.lastDist = 0.0;
        this.moveSpeed = 0.0;
        this.stage = 0;
        this.ticks = 0;
        player.stepHeight = 0.0f;
        player.motionX = 0.0;
        player.motionZ = 0.0;
        if (this.targetStrafe == null) {
            this.targetStrafe = Autumn.MANAGER_REGISTRY.moduleManager.getModuleOrNull(TargetStrafeMod.class);
        }
    }

    @Override
    public void onDisabled() {
        EntityPlayerSP player = FlightMod.mc.thePlayer;
        FlightMod.mc.timer.timerSpeed = 1.0f;
        player.stepHeight = 0.625f;
        player.motionX = 0.0;
        player.motionZ = 0.0;
        if (this.mode.getValue() == Mode.WATCHDOG) {
            player.setPosition(player.posX, player.posY + this.y, player.posZ);
        }
    }

    @Listener(value=MoveEvent.class)
    public final void onMove(MoveEvent event) {
        EntityPlayerSP player = FlightMod.mc.thePlayer;
        GameSettings gameSettings = FlightMod.mc.gameSettings;
        switch ((Mode)((Object)this.mode.getValue())) {
            case WATCHDOG: {
                if (!player.isMoving()) break;
                switch (this.stage) {
                    case 0: {
                        if (!FlightMod.mc.thePlayer.onGround || !FlightMod.mc.thePlayer.isCollidedVertically) break;
                        PlayerUtils.damage();
                        this.moveSpeed = 0.5 * (Double)this.speed.getValue();
                        break;
                    }
                    case 1: {
                        if (FlightMod.mc.thePlayer.onGround && FlightMod.mc.thePlayer.isCollidedVertically) {
                            event.y = player.motionY = MovementUtils.getJumpBoostModifier(0.39999994);
                        }
                        this.moveSpeed *= 2.149;
                        break;
                    }
                    case 2: {
                        this.moveSpeed = 1.3 * (Double)this.speed.getValue();
                        break;
                    }
                    default: {
                        this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                    }
                }
                MovementUtils.setSpeed(event, Math.max(this.moveSpeed, MovementUtils.getBaseMoveSpeed()));
                ++this.stage;
                break;
            }
            case MINEPLEX: {
                player.motionY = 0.0;
                event.y = 0.0;
                if (this.stopwatch.elapsed(6100L)) {
                    MovementUtils.setSpeed(event, 6.0);
                    if (gameSettings.keyBindJump.isKeyDown()) {
                        player.motionY = 1.0;
                        event.y = 1.0;
                    }
                    if (gameSettings.keyBindSneak.isKeyDown()) {
                        player.motionY = -1.0;
                        event.y = -1.0;
                    }
                } else {
                    MovementUtils.setSpeed(event, 0.0);
                }
                if (!this.stopwatch.elapsed(7000L)) break;
                MovementUtils.setSpeed(event, 0.0);
                this.mineplexDamage(player);
                this.stopwatch.reset();
                break;
            }
            case VANILLA: {
                MovementUtils.setSpeed(event, ((Double)this.vanillaSpeed.getValue()).intValue());
                break;
            }
            case DISABLER: {
                FlightMod.mc.thePlayer.motionY = 0.0;
                event.y = 0.0;
                if (this.tp) {
                    if (gameSettings.keyBindJump.isKeyDown()) {
                        FlightMod.mc.thePlayer.motionY = 2.0;
                        event.y = 2.0;
                    } else if (gameSettings.keyBindSneak.isKeyDown()) {
                        FlightMod.mc.thePlayer.motionY = -2.0;
                        event.y = -2.0;
                    }
                    MovementUtils.setSpeed(event, 2.0);
                    break;
                }
                MovementUtils.setSpeed(event, 0.0);
                break;
            }
            case CUBECRAFT: {
                FlightMod.mc.timer.timerSpeed = 1.0f;
                MovementUtils.setSpeed(event, 0.0);
                if (this.ticks > 12) {
                    if (this.stage == 4) {
                        MovementUtils.setSpeed(event, 0.953532);
                        this.stage = 0;
                        break;
                    }
                    MovementUtils.setSpeed(event, 0.121984218421847);
                    break;
                }
                MovementUtils.setSpeed(event, 0.0);
            }
        }
    }

    @Listener(value=MotionUpdateEvent.class)
    public final void onMotionUpdate(MotionUpdateEvent event) {
        EntityPlayerSP player = FlightMod.mc.thePlayer;
        Timer timer = FlightMod.mc.timer;
        GameSettings gameSettings = FlightMod.mc.gameSettings;
        Stopwatch stopwatch = this.stopwatch;
        block0 : switch ((Mode)((Object)this.mode.getValue())) {
            case WATCHDOG: {
                if (this.multiplier.getValue().booleanValue()) {
                    FlightMod.mc.timer.timerSpeed = !stopwatch.elapsed(((Double)this.multiplytime.getValue()).longValue()) ? ((Double)this.multiplyspeed.getValue()).floatValue() : 1.0f;
                }
                if (event.isPre()) {
                    if (this.stage > 2) {
                        player.motionY = 0.0;
                    }
                    if (this.viewBobbing.getValue().booleanValue()) {
                        player.cameraYaw = 0.105f;
                    }
                    if (this.stage <= 2) break;
                    player.setPosition(player.posX, player.posY - 0.003, player.posZ);
                    ++this.ticks;
                    double offset = 3.25E-4;
                    switch (this.ticks) {
                        case 1: {
                            this.y *= (double)-0.95f;
                            break;
                        }
                        case 2: 
                        case 3: 
                        case 4: {
                            this.y += 3.25E-4;
                            break;
                        }
                        case 5: {
                            this.y += 5.0E-4;
                            this.ticks = 0;
                        }
                    }
                    event.setPosY(player.posY + this.y);
                    break;
                }
                if (this.stage <= 2) break;
                player.setPosition(player.posX, player.posY + 0.003, player.posZ);
                break;
            }
            case VANILLA: {
                if (!event.isPre()) break;
                player.motionY = 0.0;
                if (gameSettings.keyBindJump.isKeyDown()) {
                    player.motionY = 2.0;
                    break;
                }
                if (!gameSettings.keyBindSneak.isKeyDown()) break;
                player.motionY = -2.0;
                break;
            }
            case DISABLER: {
                break;
            }
            case MINEPLEX: {
                if (player.hurtTime == 9) {
                    stopwatch.reset();
                }
                if (!stopwatch.elapsed(6000L) || this.tp) break;
                NetHandlerPlayClient netHandler = mc.getNetHandler();
                netHandler.addToSendQueueSilent(new C0CPacketInput(0.0f, 0.0f, true, true));
                CustomVec3 lastPos = this.lastPos;
                ArrayList<CustomVec3> computePath = PathfindingUtils.computePath(new CustomVec3(player.posX, player.posY, player.posZ), lastPos);
                int computePathSize = computePath.size();
                for (int i = 0; i < computePathSize; ++i) {
                    CustomVec3 vec3 = computePath.get(i);
                    netHandler.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(vec3.getX(), vec3.getY(), vec3.getZ(), true));
                }
                NotificationPublisher.queue("Flight", "Exploit completed.", NotificationType.SUCCESS);
                FlightMod.mc.thePlayer.setPosition(lastPos.getX(), lastPos.getY(), lastPos.getZ());
                this.tp = true;
                break;
            }
            case CUBECRAFT: {
                if (!event.isPre()) break;
                timer.timerSpeed = 0.82f;
                ++this.ticks;
                float y = (float)Math.floor(player.posY);
                if (this.ticks == 1 && !player.onGround) {
                    this.toggle();
                }
                player.motionY = 0.0;
                if (this.ticks < 10) {
                    event.setPosY((double)y - 1.01);
                    player.motionY = -0.101;
                    break;
                }
                if (this.ticks <= 12) break;
                ++this.stage;
                switch (this.stage) {
                    case 1: {
                        event.setPosY((double)y + 0.381 + MathUtils.randomNumber(0.03, 0.05));
                        break block0;
                    }
                    case 2: {
                        event.setPosY((double)y + 0.355 + MathUtils.randomNumber(0.03, 0.05));
                        break block0;
                    }
                    case 3: {
                        event.setPosY((double)y + 0.325 + MathUtils.randomNumber(0.03, 0.05));
                        break block0;
                    }
                    case 4: {
                        event.setPosY((double)y + MathUtils.randomNumber(0.03, 0.05));
                    }
                }
            }
        }
        if (event.isPre()) {
            double xDif = player.posX - player.prevPosX;
            double zDif = player.posZ - player.prevPosZ;
            this.lastDist = Math.sqrt(xDif * xDif + zDif * zDif);
        }
    }

    public static enum Mode {
        WATCHDOG,
        DISABLER,
        CUBECRAFT,
        MINEPLEX,
        VANILLA;

    }
}

