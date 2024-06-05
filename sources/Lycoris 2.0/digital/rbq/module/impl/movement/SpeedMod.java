/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.movement;

import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import digital.rbq.annotations.Label;
import digital.rbq.core.Autumn;
import digital.rbq.events.player.MotionUpdateEvent;
import digital.rbq.events.player.MoveEvent;
import digital.rbq.events.player.UpdateActionEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Bind;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.impl.movement.TargetStrafeMod;
import digital.rbq.module.option.impl.DoubleOption;
import digital.rbq.module.option.impl.EnumOption;
import digital.rbq.utils.MathUtils;
import digital.rbq.utils.MovementUtils;
import digital.rbq.utils.PlayerUtils;

@Label(value="Speed")
@Bind(value="V")
@Category(value=ModuleCategory.MOVEMENT)
public final class SpeedMod
extends Module {
    public final EnumOption<Mode> mode = new EnumOption<Mode>("Mode", Mode.HYPIXEL);
    public final DoubleOption bhopSpeed = new DoubleOption("Bhop Speed", 0.5, () -> this.mode.getValue() == Mode.BHOP, 0.1, 3.0, 0.05);
    private double moveSpeed;
    private double lastDist;
    private double y;
    private int stage;
    private int hops;
    private TargetStrafeMod targetStrafe;

    public SpeedMod() {
        this.setMode(this.mode);
        this.addOptions(this.mode, this.bhopSpeed);
    }

    @Override
    public void onEnabled() {
        this.y = 0.0;
        this.hops = 1;
        this.moveSpeed = MovementUtils.getBaseMoveSpeed();
        this.lastDist = 0.0;
        this.stage = 0;
        if (this.targetStrafe == null) {
            this.targetStrafe = Autumn.MANAGER_REGISTRY.moduleManager.getModuleOrNull(TargetStrafeMod.class);
        }
    }

    @Override
    public void onDisabled() {
        SpeedMod.mc.thePlayer.stepHeight = 0.625f;
        SpeedMod.mc.timer.timerSpeed = 1.0f;
    }

    @Listener(value=UpdateActionEvent.class)
    public final void onActionUpdate(UpdateActionEvent event) {
        if (event.isSneakState()) {
            event.setSneakState(false);
        }
    }

    @Listener(value=MoveEvent.class)
    public final void onMove(MoveEvent event) {
        EntityPlayerSP player = SpeedMod.mc.thePlayer;
        if (PlayerUtils.isInLiquid()) {
            return;
        }
        if (player.isMoving()) {
            block0 : switch ((Mode)((Object)this.mode.getValue())) {
                case NCP: {
                    switch (this.stage) {
                        case 2: {
                            if (!player.onGround || !player.isCollidedVertically) break block0;
                            event.y = player.motionY = MovementUtils.getJumpBoostModifier(0.42f);
                            this.moveSpeed *= 2.149;
                            SpeedMod.mc.timer.timerSpeed = 1.4f;
                            break block0;
                        }
                        case 3: {
                            double difference = 0.86 * (this.moveSpeed - MovementUtils.getBaseMoveSpeed());
                            this.moveSpeed = this.lastDist - difference;
                            break block0;
                        }
                    }
                    if (SpeedMod.mc.theWorld.getCollidingBoundingBoxes(player, player.getEntityBoundingBox().offset(0.0, player.motionY, 0.0)).size() > 0 || player.isCollidedVertically && player.onGround) {
                        this.stage = 1;
                    }
                    SpeedMod.mc.timer.timerSpeed = 1.0f;
                    break;
                }
                case HYPIXEL: {
                    switch (this.stage) {
                        case 2: {
                            if (!player.onGround || !player.isCollidedVertically) break;
                            SpeedMod.mc.timer.timerSpeed = 1.0f;
                            event.y = player.motionY = MovementUtils.getJumpBoostModifier(0.42f);
                            this.moveSpeed = MovementUtils.getBaseMoveSpeed() * 1.5;
                            break;
                        }
                        case 3: {
                            double difference = (double)0.62f * (this.lastDist - MovementUtils.getBaseMoveSpeed());
                            this.moveSpeed = this.lastDist + difference;
                            break;
                        }
                        default: {
                            if (SpeedMod.mc.theWorld.getCollidingBoundingBoxes(player, player.getEntityBoundingBox().offset(0.0, player.motionY, 0.0)).size() > 0 || player.isCollidedVertically && player.onGround) {
                                this.stage = 1;
                                SpeedMod.mc.timer.timerSpeed = 1.085f;
                                ++this.hops;
                            }
                            this.moveSpeed = this.lastDist - this.lastDist / 149.0;
                        }
                    }
                    this.moveSpeed = Math.max(this.moveSpeed, MovementUtils.getBaseMoveSpeed());
                    break;
                }
                case PACKETHOP: {
                    switch (this.stage) {
                        case 2: {
                            SpeedMod.mc.timer.timerSpeed = 2.0f;
                            this.moveSpeed = 0.0;
                            break block0;
                        }
                        case 3: {
                            SpeedMod.mc.timer.timerSpeed = 1.0f;
                            if (!player.onGround || !player.isCollidedVertically) break block0;
                            event.y = player.motionY = MovementUtils.getJumpBoostModifier(0.42f);
                            this.moveSpeed = MovementUtils.getBaseMoveSpeed() * 2.149;
                            break block0;
                        }
                        case 4: {
                            double difference = (double)0.56f * (this.lastDist - MovementUtils.getBaseMoveSpeed());
                            this.moveSpeed = this.lastDist - difference;
                            break block0;
                        }
                    }
                    if (SpeedMod.mc.theWorld.getCollidingBoundingBoxes(player, player.getEntityBoundingBox().offset(0.0, player.motionY, 0.0)).size() > 0 || player.isCollidedVertically && player.onGround) {
                        this.stage = 1;
                        SpeedMod.mc.timer.timerSpeed = 1.085f;
                    }
                    if (player.motionY < 0.0) {
                        player.motionY *= 1.1;
                    }
                    this.moveSpeed = this.lastDist - this.lastDist / 50.0;
                    break;
                }
                case MINEPLEX: {
                    switch (this.stage) {
                        case 2: {
                            if (player.onGround && player.isCollidedVertically) {
                                this.moveSpeed = 0.0;
                            }
                            SpeedMod.mc.timer.timerSpeed = 2.5f;
                            break;
                        }
                        case 3: {
                            SpeedMod.mc.timer.timerSpeed = 1.0f;
                            this.moveSpeed = Math.min(0.3 * (double)this.hops, 0.97);
                            break;
                        }
                        default: {
                            if (SpeedMod.mc.theWorld.getCollidingBoundingBoxes(player, player.getEntityBoundingBox().offset(0.0, player.motionY, 0.0)).size() > 0 || player.isCollidedVertically && player.onGround) {
                                this.stage = 1;
                                ++this.hops;
                            }
                            this.moveSpeed -= 0.01;
                        }
                    }
                    if (this.stage == 2) break;
                    this.moveSpeed = Math.max(this.moveSpeed, MovementUtils.getBaseMoveSpeed());
                    break;
                }
                case VHOP: {
                    double rounded = MathUtils.round(player.posY - (double)((int)player.posY), 3.0);
                    if (rounded == MathUtils.round(0.4, 3.0)) {
                        player.motionY = 0.31;
                        event.y = 0.31;
                    } else if (rounded == MathUtils.round(0.71, 3.0)) {
                        player.motionY = 0.04;
                        event.y = 0.04;
                    } else if (rounded == MathUtils.round(0.75, 3.0)) {
                        player.motionY = -0.2;
                        event.y = -0.2;
                    } else if (rounded == MathUtils.round(0.55, 3.0)) {
                        player.motionY = -0.14;
                        event.y = -0.14;
                    } else if (rounded == MathUtils.round(0.41, 3.0)) {
                        player.motionY = -0.2;
                        event.y = -0.2;
                    }
                    switch (this.stage) {
                        case 0: {
                            if (!player.onGround || !player.isCollidedVertically) break;
                            this.moveSpeed = MovementUtils.getBaseMoveSpeed() * 1.3;
                            break;
                        }
                        case 2: {
                            if (player.onGround && player.isCollidedVertically) {
                                event.y = player.motionY = MovementUtils.getJumpBoostModifier(0.40001);
                                this.moveSpeed *= 1.8;
                            }
                            SpeedMod.mc.timer.timerSpeed = 1.2f;
                            break;
                        }
                        case 3: {
                            SpeedMod.mc.timer.timerSpeed = 1.0f;
                            double difference = 0.72 * (this.lastDist - MovementUtils.getBaseMoveSpeed());
                            this.moveSpeed = this.lastDist - difference;
                            break;
                        }
                        default: {
                            if (SpeedMod.mc.theWorld.getCollidingBoundingBoxes(player, player.getEntityBoundingBox().offset(0.0, player.motionY, 0.0)).size() > 0 || player.isCollidedVertically && player.onGround) {
                                this.stage = 1;
                            }
                            this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                        }
                    }
                    this.moveSpeed = Math.max(this.moveSpeed, MovementUtils.getBaseMoveSpeed());
                    break;
                }
                case GROUND: {
                    if (this.canSpeed()) {
                        switch (this.stage) {
                            case 1: {
                                SpeedMod.mc.timer.timerSpeed = 1.0f;
                                this.y = MovementUtils.getJumpBoostModifier(0.40001);
                                this.moveSpeed = MovementUtils.getBaseMoveSpeed() * 2.149;
                                break block0;
                            }
                            case 2: {
                                this.y = MovementUtils.getJumpBoostModifier(0.381);
                                double difference = 0.66 * (this.lastDist - MovementUtils.getBaseMoveSpeed());
                                this.moveSpeed = this.lastDist - difference;
                                break block0;
                            }
                            case 3: {
                                this.y = MovementUtils.getJumpBoostModifier(0.22);
                                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                                break block0;
                            }
                            case 4: {
                                this.y = MovementUtils.getJumpBoostModifier(0.11);
                                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                                break block0;
                            }
                            case 5: {
                                SpeedMod.mc.timer.timerSpeed = 2.0f;
                                this.y = 0.0;
                                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                                this.stage = 0;
                            }
                        }
                        break;
                    }
                    this.y = 0.0;
                    this.moveSpeed = MovementUtils.getBaseMoveSpeed();
                    this.stage = 0;
                    SpeedMod.mc.timer.timerSpeed = 1.0f;
                    break;
                }
                case BHOP: {
                    if (player.onGround && player.isCollidedVertically) {
                        event.y = player.motionY = MovementUtils.getJumpBoostModifier(0.42f);
                        this.moveSpeed = (Double)this.bhopSpeed.getValue() * MovementUtils.getBaseMoveSpeed();
                    } else {
                        this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                    }
                    this.moveSpeed = Math.max(this.moveSpeed, MovementUtils.getBaseMoveSpeed());
                }
            }
            if (this.targetStrafe.canStrafe()) {
                this.targetStrafe.strafe(event, this.moveSpeed);
            } else {
                MovementUtils.setSpeed(event, this.moveSpeed);
            }
            ++this.stage;
        }
    }

    @Listener(value=MotionUpdateEvent.class)
    public final void onMotionUpdate(MotionUpdateEvent event) {
        if (event.isPre()) {
            if (PlayerUtils.isInLiquid()) {
                return;
            }
            EntityPlayerSP player = SpeedMod.mc.thePlayer;
            if (player.isMoving()) {
                if (this.mode.getValue() == Mode.HYPIXEL) {
                    if (player.isCollidedVertically) {
                        event.setPosY(SpeedMod.mc.thePlayer.posY + 7.435E-4);
                    }
                } else if (this.mode.getValue() == Mode.MINEPLEX) {
                    SpeedMod.mc.thePlayer.stepHeight = 0.0f;
                    if (SpeedMod.mc.thePlayer.isCollidedHorizontally) {
                        this.moveSpeed = 0.0;
                        this.hops = 1;
                    }
                    if (SpeedMod.mc.thePlayer.fallDistance < 8.0f) {
                        SpeedMod.mc.thePlayer.setPosition(SpeedMod.mc.thePlayer.posX, SpeedMod.mc.thePlayer.posY + 0.4, SpeedMod.mc.thePlayer.posZ);
                        SpeedMod.mc.thePlayer.motionY += 0.02;
                    } else {
                        this.moveSpeed = 0.0;
                        this.hops = 1;
                        SpeedMod.mc.thePlayer.motionY = -1.0;
                    }
                } else if (this.mode.getValue() == Mode.GROUND && this.canSpeed()) {
                    event.setOnGround(this.stage == 5);
                    event.setPosY(SpeedMod.mc.thePlayer.posY + this.y + 7.435E-4);
                }
            }
            double xDist = player.posX - player.prevPosX;
            double zDist = player.posZ - player.prevPosZ;
            this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
        }
    }

    private boolean canSpeed() {
        Block blockBelow = SpeedMod.mc.theWorld.getBlockState(new BlockPos(SpeedMod.mc.thePlayer.posX, SpeedMod.mc.thePlayer.posY - 1.0, SpeedMod.mc.thePlayer.posZ)).getBlock();
        return SpeedMod.mc.thePlayer.onGround && !SpeedMod.mc.gameSettings.keyBindJump.isPressed() && blockBelow != Blocks.stone_stairs && blockBelow != Blocks.oak_stairs && blockBelow != Blocks.sandstone_stairs && blockBelow != Blocks.nether_brick_stairs && blockBelow != Blocks.spruce_stairs && blockBelow != Blocks.stone_brick_stairs && blockBelow != Blocks.birch_stairs && blockBelow != Blocks.jungle_stairs && blockBelow != Blocks.acacia_stairs && blockBelow != Blocks.brick_stairs && blockBelow != Blocks.dark_oak_stairs && blockBelow != Blocks.quartz_stairs && blockBelow != Blocks.red_sandstone_stairs && SpeedMod.mc.theWorld.getBlockState(new BlockPos(SpeedMod.mc.thePlayer.posX, SpeedMod.mc.thePlayer.posY + 2.0, SpeedMod.mc.thePlayer.posZ)).getBlock() == Blocks.air;
    }

    private static enum Mode {
        HYPIXEL,
        MINEPLEX,
        VHOP,
        NCP,
        BHOP,
        PACKETHOP,
        GROUND;

    }
}

