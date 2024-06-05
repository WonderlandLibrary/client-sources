// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.movement;

import net.minecraft.util.AxisAlignedBB;
import java.util.List;
import net.minecraft.client.entity.EntityPlayerSP;
import gg.childtrafficking.smokex.module.ModuleManager;
import gg.childtrafficking.smokex.module.modules.player.ScaffoldModule;
import gg.childtrafficking.smokex.utils.system.StringUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.entity.Entity;
import net.optifine.util.MathUtils;
import net.minecraft.potion.Potion;
import gg.childtrafficking.smokex.utils.player.MovementUtils;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.events.player.EventMove;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import gg.childtrafficking.smokex.property.properties.BooleanProperty;
import gg.childtrafficking.smokex.property.properties.EnumProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "Speed", renderName = "Speed", description = "Move faster than normal", aliases = {}, category = ModuleCategory.MOVEMENT)
public final class SpeedModule extends Module
{
    public final EnumProperty<Mode> modeEnumProperty;
    public final BooleanProperty slowProperty;
    private final BooleanProperty timerBoostProperty;
    private final BooleanProperty damageBoostProperty;
    private final NumberProperty<Float> timerSpeedProperty;
    public final BooleanProperty flagCheck;
    private final EnumProperty<WatchdogMode> watchdogModeEnumProperty;
    private double moveSpeed;
    private float cachedTimer;
    private int airTicks;
    private int stage;
    private float yaw;
    private int jumps;
    private final EventListener<EventMove> movePlayerEventCallback;
    private final EventListener<EventUpdate> eventUpdate;
    
    public SpeedModule() {
        this.modeEnumProperty = new EnumProperty<Mode>("Mode", Mode.WATCHDOG);
        this.slowProperty = new BooleanProperty("Pit", false);
        this.timerBoostProperty = new BooleanProperty("Timer Boost", true);
        this.damageBoostProperty = new BooleanProperty("Damage Boost", true);
        this.timerSpeedProperty = new NumberProperty<Float>("Timer Speed", 1.0f, 1.0f, 2.2f, 0.1f);
        this.flagCheck = new BooleanProperty("FlagCheck", "Flag Check", true);
        this.watchdogModeEnumProperty = new EnumProperty<WatchdogMode>("WatchdogMode", "Watchdog Mode", WatchdogMode.VHOP, () -> this.modeEnumProperty.getValue() == Mode.WATCHDOG);
        this.moveSpeed = MovementUtils.getBaseMoveSpeed();
        this.yaw = 0.0f;
        this.movePlayerEventCallback = (event -> {
            switch (this.modeEnumProperty.getValue()) {
                case WATCHDOG: {
                    switch (this.watchdogModeEnumProperty.getValue()) {
                        case BHOP: {
                            if (MovementUtils.isOnGround() && MovementUtils.isMoving()) {
                                final EntityPlayerSP thePlayer = this.mc.thePlayer;
                                final double motionY = MovementUtils.getBaseJumpMotion() * 0.9523;
                                event.setY(thePlayer.motionY = motionY);
                                this.stage = 0;
                            }
                            switch (this.stage) {
                                case 0: {
                                    this.mc.thePlayer.onGround = true;
                                    event.setY(event.getY() + 0.004 * Math.random());
                                    this.moveSpeed = MovementUtils.getBaseMoveSpeed() * 2.149;
                                    break;
                                }
                                case 1: {
                                    this.moveSpeed *= 0.58;
                                    break;
                                }
                                case 4: {
                                    if (!this.mc.thePlayer.isPotionActive(Potion.jump)) {
                                        this.mc.thePlayer.motionY = -0.12;
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                    break;
                                }
                                default: {
                                    this.moveSpeed *= 0.975;
                                    break;
                                }
                            }
                            ++this.stage;
                            if (this.mc.thePlayer.hurtTime > 5 && this.damageBoostProperty.getValue()) {
                                this.moveSpeed += this.moveSpeed / 45.0 * this.mc.thePlayer.hurtTime;
                            }
                            if (this.slowProperty.getValue()) {
                                if (MovementUtils.isOnGround()) {
                                    MovementUtils.setSpeed(event, Math.max(MovementUtils.getBaseMoveSpeed(), this.moveSpeed));
                                    break;
                                }
                                else {
                                    break;
                                }
                            }
                            else {
                                MovementUtils.setSpeed(event, Math.max(MovementUtils.getBaseMoveSpeed(), this.moveSpeed));
                                break;
                            }
                            break;
                        }
                        case VHOP: {
                            if (!this.mc.gameSettings.keyBindJump.isKeyDown()) {
                                if (MathUtils.roundToPlace(this.mc.thePlayer.posY - (int)this.mc.thePlayer.posY, 3) == MathUtils.roundToPlace(0.4, 3)) {
                                    final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
                                    final double motionY2;
                                    event.setY(thePlayer2.motionY = motionY2);
                                }
                                else if (MathUtils.roundToPlace(this.mc.thePlayer.posY - (int)this.mc.thePlayer.posY, 3) == MathUtils.roundToPlace(0.71, 3)) {
                                    final EntityPlayerSP thePlayer3 = this.mc.thePlayer;
                                    final double motionY3;
                                    event.setY(thePlayer3.motionY = motionY3);
                                }
                                else if (MathUtils.roundToPlace(this.mc.thePlayer.posY - (int)this.mc.thePlayer.posY, 3) == MathUtils.roundToPlace(0.75, 3)) {
                                    final EntityPlayerSP thePlayer4 = this.mc.thePlayer;
                                    final double motionY4;
                                    event.setY(thePlayer4.motionY = motionY4);
                                }
                                else if (MathUtils.roundToPlace(this.mc.thePlayer.posY - (int)this.mc.thePlayer.posY, 3) == MathUtils.roundToPlace(0.55, 3)) {
                                    final EntityPlayerSP thePlayer5 = this.mc.thePlayer;
                                    final double motionY5;
                                    event.setY(thePlayer5.motionY = motionY5);
                                }
                                else if (MathUtils.roundToPlace(this.mc.thePlayer.posY - (int)this.mc.thePlayer.posY, 3) == MathUtils.roundToPlace(0.41, 3)) {
                                    final EntityPlayerSP thePlayer6 = this.mc.thePlayer;
                                    final double motionY6;
                                    event.setY(thePlayer6.motionY = motionY6);
                                }
                            }
                            switch (this.stage) {
                                case 1: {
                                    this.mc.thePlayer.onGround = true;
                                    event.setY(event.getY() + 0.004 * Math.random());
                                    if (MovementUtils.isMoving()) {
                                        this.moveSpeed = 1.38 * MovementUtils.getBaseMoveSpeed2() - 0.01;
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                    break;
                                }
                                case 2: {
                                    if (MovementUtils.isMoving()) {
                                        if (!this.mc.gameSettings.keyBindJump.isKeyDown()) {
                                            final EntityPlayerSP thePlayer7 = this.mc.thePlayer;
                                            final double motionY7;
                                            event.setY(thePlayer7.motionY = motionY7);
                                        }
                                        this.moveSpeed *= 1.5;
                                        break;
                                    }
                                    else {
                                        break;
                                    }
                                    break;
                                }
                                case 3: {
                                    this.moveSpeed *= 0.6;
                                    break;
                                }
                                default: {
                                    final List<AxisAlignedBB> collide = this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.getEntityBoundingBox().offset(0.0, this.mc.thePlayer.motionY, 0.0));
                                    if ((!collide.isEmpty() || this.mc.thePlayer.isCollidedVertically) && this.stage > 0) {
                                        this.stage = 0;
                                    }
                                    this.moveSpeed *= 0.96;
                                    break;
                                }
                            }
                            MovementUtils.setSpeed(event, Math.max(this.moveSpeed, MovementUtils.getBaseMoveSpeed()));
                            if (MovementUtils.isMoving()) {
                                ++this.stage;
                                break;
                            }
                            else {
                                break;
                            }
                            break;
                        }
                        case GROUND: {
                            if (MovementUtils.isMoving() && MovementUtils.isOnGround() && !this.mc.thePlayer.isCollidedHorizontally) {
                                this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX + event.getX(), this.mc.thePlayer.posY, this.mc.thePlayer.posZ + event.getZ(), true));
                                event.setX(event.getX() * 2.0);
                                event.setZ(event.getZ() * 2.0);
                                MovementUtils.setSpeed(event, MovementUtils.getBaseMoveSpeed() * 2.0);
                                break;
                            }
                            else {
                                break;
                            }
                            break;
                        }
                        case SOFT: {
                            if (MovementUtils.isMoving()) {
                                if (MovementUtils.isOnGround()) {
                                    final EntityPlayerSP thePlayer8 = this.mc.thePlayer;
                                    MovementUtils.getBaseJumpMotion();
                                    final double motionY8;
                                    event.setY(thePlayer8.motionY = motionY8);
                                }
                                MovementUtils.setSpeed(event, MovementUtils.getBaseMoveSpeed() * 1.2);
                                break;
                            }
                            else {
                                break;
                            }
                            break;
                        }
                    }
                    break;
                }
                case HYPIXEL: {
                    if (MovementUtils.isOnGround() && MovementUtils.isMoving()) {
                        this.stage = 0;
                        final EntityPlayerSP thePlayer9 = this.mc.thePlayer;
                        MovementUtils.getBaseJumpMotion();
                        final double motionY9;
                        event.setY(thePlayer9.motionY = motionY9);
                    }
                    if (this.mc.thePlayer.isCollidedHorizontally || !MovementUtils.isMoving()) {
                        this.jumps = 0;
                    }
                    if (this.mc.thePlayer.isCollidedVertically && MovementUtils.isOnGround() && MovementUtils.isMoving()) {
                        this.mc.thePlayer.motionY = 0.42;
                        final double more = (this.jumps <= 0) ? 0.0 : (this.jumps / 10.0);
                        this.moveSpeed = 0.45 + MovementUtils.getSpeedAmplifier() * 0.1;
                        this.stage = 0;
                        if (this.jumps < 2) {
                            ++this.jumps;
                        }
                    }
                    else {
                        ++this.stage;
                        final double more2 = (this.jumps <= 0) ? 0.0 : (this.jumps / 10.0);
                        this.moveSpeed = 0.35 - this.stage / 200.0 + MovementUtils.getSpeedAmplifier() * 0.1;
                        if (this.mc.gameSettings.keyBindBack.isKeyDown()) {
                            this.moveSpeed -= 0.07;
                        }
                    }
                    MovementUtils.setMotion(this.moveSpeed = Math.max((this.stage < 4) ? this.moveSpeed : (this.moveSpeed - 0.11), 0.278));
                    break;
                }
            }
            return;
        });
        this.eventUpdate = (event -> {
            this.setSuffix(StringUtils.upperSnakeCaseToPascal(this.modeEnumProperty.getValue().toString()));
            final double pX = (float)this.mc.thePlayer.lastTickPosX;
            final double pZ = (float)this.mc.thePlayer.lastTickPosZ;
            final double eX = (float)this.mc.thePlayer.posX;
            final double eZ = (float)this.mc.thePlayer.posZ;
            final double dX = pX - eX;
            final double dZ = pZ - eZ;
            this.yaw = (float)(Math.toDegrees(Math.atan2(dZ, dX)) + 90.0);
            if (MovementUtils.isOnGround() && MovementUtils.isMoving() && this.modeEnumProperty.getValue() == Mode.HYPIXEL) {
                event.setPosY(event.getPosY() + 0.0);
                event.setOnGround(false);
            }
            if (!ModuleManager.getInstance(ScaffoldModule.class).isToggled()) {}
        });
    }
    
    @Override
    public void onEnable() {
        this.airTicks = 0;
        this.stage = 0;
        if (this.timerBoostProperty.getValue()) {
            this.mc.timer.timerSpeed = this.timerSpeedProperty.getValue();
        }
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.timer.timerSpeed = 1.0f;
    }
    
    @Override
    public void init() {
        this.setSuffix(StringUtils.upperSnakeCaseToPascal(this.modeEnumProperty.getValue().toString()));
    }
    
    private enum Mode
    {
        WATCHDOG, 
        HYPIXEL;
    }
    
    private enum WatchdogMode
    {
        SOFT, 
        BHOP, 
        VHOP, 
        GROUND;
    }
}
