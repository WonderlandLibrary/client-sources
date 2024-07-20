/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventAction;
import ru.govno.client.event.events.EventMove2;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.event.events.EventPostMove;
import ru.govno.client.event.events.EventReceivePacket;
import ru.govno.client.event.events.EventRotationJump;
import ru.govno.client.event.events.EventRotationStrafe;
import ru.govno.client.event.events.EventSprintBlock;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.AirJump;
import ru.govno.client.module.modules.Bypass;
import ru.govno.client.module.modules.ElytraBoost;
import ru.govno.client.module.modules.Fly;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.modules.HighJump;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.module.modules.JesusSpeed;
import ru.govno.client.module.modules.LongJump;
import ru.govno.client.module.modules.MoveHelper;
import ru.govno.client.module.modules.NoClip;
import ru.govno.client.module.modules.ScaffWalk;
import ru.govno.client.module.modules.Scaffold;
import ru.govno.client.module.modules.Speed;
import ru.govno.client.module.modules.TargetStrafe;
import ru.govno.client.module.modules.Timer;
import ru.govno.client.module.modules.WaterSpeed;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Movement.MatrixStrafeMovement;
import ru.govno.client.utils.Movement.MoveMeHelp;
import ru.govno.client.utils.Wrapper;

public class Strafe
extends Module {
    public static Strafe get;
    public Settings Mode;
    public Settings TimerBoost;
    public Settings DoElytraBoost;
    public Settings PullDown;
    public Settings DoNcpMin;
    public Settings HeadYawMoveDir;
    private final TimerHelper noCollideTime = new TimerHelper();
    float grimMoveYaw = -1.2312312E8f;
    float grimMoveYawPrev;
    public static boolean needSprintState;
    int stage;
    public static float speed;
    public static float curSpeed;
    boolean doUps = false;
    boolean m;
    boolean w;
    boolean a;
    boolean s;
    boolean d;
    boolean canGrBoost = false;
    double sp = 0.0;

    public Strafe() {
        super("Strafe", 0, Module.Category.MOVEMENT);
        this.Mode = new Settings("Mode", "Matrix", (Module)this, new String[]{"Matrix", "Matrix2", "Matrix3", "Matrix4", "Matrix5", "Guardian", "NCP", "NCP2", "Strict", "Grim"});
        this.settings.add(this.Mode);
        this.TimerBoost = new Settings("TimerBoost", true, (Module)this);
        this.settings.add(this.TimerBoost);
        this.DoElytraBoost = new Settings("DoElytraBoost", false, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("Matrix5"));
        this.settings.add(this.DoElytraBoost);
        this.PullDown = new Settings("PullDown", true, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("NCP") || this.Mode.currentMode.equalsIgnoreCase("NCP2"));
        this.settings.add(this.PullDown);
        this.DoNcpMin = new Settings("DoNcpMin", true, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("Strict"));
        this.settings.add(this.DoNcpMin);
        this.HeadYawMoveDir = new Settings("HeadYawMoveDir", true, (Module)this, () -> !this.Mode.currentMode.equalsIgnoreCase("Grim"));
        this.settings.add(this.HeadYawMoveDir);
        get = this;
    }

    @EventTarget
    public void onSilentSideStrafe(EventRotationStrafe event) {
        if (this.grimMoveYaw == -1.2312312E8f || this.grimMoveYaw != Minecraft.player.rotationYaw) {
            // empty if block
        }
    }

    @EventTarget
    public void onSilentSideJump(EventRotationJump event) {
        if (this.grimMoveYaw != -1.2312312E8f && this.grimMoveYaw != Minecraft.player.rotationYaw && MoveMeHelp.getSpeed() > 0.1) {
            event.setYaw(this.grimMoveYaw);
        }
    }

    @EventTarget
    public void onPlayerUpdate(EventPlayerMotionUpdate event) {
        if (this.Mode.currentMode.equalsIgnoreCase("Grim")) {
            if (MoveMeHelp.moveKeysPressed() && (!MoveMeHelp.w() || MoveMeHelp.a() || MoveMeHelp.s() || MoveMeHelp.d())) {
                this.grimMoveYawPrev = this.grimMoveYaw;
                this.grimMoveYaw = MoveMeHelp.moveYaw(Minecraft.player.rotationYaw);
                event.setYaw(this.grimMoveYaw);
                Minecraft.player.rotationYawHead = event.getYaw();
            } else if (this.grimMoveYaw != -1.2312312E8f) {
                this.grimMoveYaw = -1.2312312E8f;
            }
        } else {
            if (this.HeadYawMoveDir.bValue && MoveMeHelp.moveKeysPressed()) {
                Minecraft.player.renderYawOffset = Minecraft.player.rotationYawHead = MoveMeHelp.moveYaw(Minecraft.player.rotationYaw);
            }
            if (this.grimMoveYaw != -1.2312312E8f) {
                this.grimMoveYaw = -1.2312312E8f;
            }
        }
    }

    @EventTarget
    public void onSprintBlock(EventSprintBlock event) {
        if (this.Mode.currentMode.equalsIgnoreCase("Grim") && this.grimMoveYaw != Minecraft.player.rotationYaw && MoveMeHelp.getSpeed() > 0.1) {
            event.setCancelled(true);
        }
    }

    @EventTarget
    public void onPacket(EventReceivePacket event) {
        if (!Bypass.get.getIsStrafeHacked()) {
            return;
        }
        if ((this.Mode.currentMode.equalsIgnoreCase("Matrix5") || this.Mode.currentMode.equalsIgnoreCase("Strict")) && this.actived && event.getPacket() instanceof SPacketPlayerPosLook) {
            MatrixStrafeMovement.oldSpeed = 0.0;
            speed = 0.0f;
            this.doUps = true;
        }
    }

    @EventTarget
    public void onPostMove(EventPostMove post) {
        if (!Bypass.get.getIsStrafeHacked()) {
            return;
        }
        if (this.Mode.currentMode.equalsIgnoreCase("Matrix5") || this.Mode.currentMode.equalsIgnoreCase("Strict")) {
            MatrixStrafeMovement.postMove(post.getHorizontalMove());
        }
    }

    @EventTarget
    public void onAction(EventAction action) {
        if (!Bypass.get.getIsStrafeHacked()) {
            return;
        }
        if ((this.Mode.currentMode.equalsIgnoreCase("Matrix5") || this.Mode.currentMode.equalsIgnoreCase("Strict")) && Strafe.moves()) {
            MatrixStrafeMovement.actionEvent(action);
        }
    }

    static boolean moves() {
        float ex3 = 0.3f;
        return !(Minecraft.player.capabilities.isFlying || Minecraft.player.isLay || Minecraft.player.isSneaking() || Minecraft.player.isInLava() || Minecraft.player.isInWater() || Minecraft.player.isInWeb || NoClip.get.actived && !NoClip.get.Mode.currentMode.equalsIgnoreCase("Sunrise") || Speed.get.actived && Speed.get.AntiCheat.currentMode.equalsIgnoreCase("AAC") || Speed.get.actived && Speed.get.AntiCheat.currentMode.equalsIgnoreCase("Vanilla") || Speed.get.actived && Speed.get.AntiCheat.currentMode.equalsIgnoreCase("Matrix") && EntityLivingBase.isSunRiseDamaged && NoClip.get.actived || TargetStrafe.goStrafe() || WaterSpeed.get.actived && WaterSpeed.get.Mode.currentMode.equalsIgnoreCase("Matrix") && (Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ)).getBlock() == Blocks.WATER || Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ)).getBlock() == Blocks.LAVA) || ElytraBoost.get.actived && (ElytraBoost.get.Mode.currentMode.equalsIgnoreCase("MatrixFly") || ElytraBoost.get.Mode.currentMode.equalsIgnoreCase("MatrixSpeed")) || Scaffold.get.actived || ScaffWalk.get.actived || Fly.get.actived || Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.1, Minecraft.player.posZ)).getBlock() == Blocks.WATER && Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + 0.02001, Minecraft.player.posZ)).getBlock() != Blocks.WATER && JesusSpeed.get.actived && JesusSpeed.get.JesusMode.currentMode.equalsIgnoreCase("MatrixZoom2") && Minecraft.player.isCollidedHorizontally || Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ)).getBlock() == Blocks.WATER || Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ)).getBlock() == Blocks.LAVA || JesusSpeed.isJesused || Speed.snowGo && Speed.get.actived || LongJump.get.actived && !LongJump.get.Type.currentMode.equalsIgnoreCase("FlagBoost") && (LongJump.doSpeed || LongJump.doBow) || (double)Minecraft.player.speedInAir > 0.05 || Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ)).getBlock() == Blocks.SOUL_SAND || Speed.get.actived && Speed.get.AntiCheat.currentMode.equalsIgnoreCase("Vulcan") || (Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.3, Minecraft.player.posZ)).getBlock() == Blocks.WATER || Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.3, Minecraft.player.posZ)).getBlock() == Blocks.LAVA) && Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 1.1, Minecraft.player.posZ)) instanceof BlockLiquid || Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.2, Minecraft.player.posZ)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX - (double)ex3, Minecraft.player.posY - 0.2, Minecraft.player.posZ - (double)ex3)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX + (double)ex3, Minecraft.player.posY - 0.2, Minecraft.player.posZ + (double)ex3)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX - (double)ex3, Minecraft.player.posY - 0.2, Minecraft.player.posZ + (double)ex3)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX + (double)ex3, Minecraft.player.posY - 0.2, Minecraft.player.posZ - (double)ex3)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX - (double)ex3, Minecraft.player.posY - 0.2, Minecraft.player.posZ)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX + (double)ex3, Minecraft.player.posY - 0.2, Minecraft.player.posZ)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.2, Minecraft.player.posZ - (double)ex3)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.2, Minecraft.player.posZ + (double)ex3)).getBlock() == Blocks.WEB || JesusSpeed.get.actived && (Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.2, Minecraft.player.posZ)).getBlock() == Blocks.LAVA || Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.2, Minecraft.player.posZ)).getBlock() == Blocks.WATER) || Minecraft.player.isElytraFlying() && Minecraft.player.getTicksElytraFlying() > 0 || FreeCam.get.actived || Speed.get.actived && Speed.get.AntiCheat.currentMode.equalsIgnoreCase("RipServer") || HighJump.get.actived && (HighJump.toPlace || HighJump.get.GroundJump.bValue && HighJump.get.JumpMode.currentMode.equalsIgnoreCase("MatrixNew2")) || ElytraBoost.get.actived && ElytraBoost.get.Mode.currentMode.equalsIgnoreCase("MatrixFly3") && ElytraBoost.canElytra() || ElytraBoost.get.actived && ElytraBoost.get.Mode.currentMode.equalsIgnoreCase("NcpFly") && ElytraBoost.canElytra() || MoveHelper.holeTick || !Bypass.get.getIsStrafeHacked());
    }

    @Override
    public void onUpdate() {
        if (Minecraft.player.isCollidedHorizontally) {
            this.noCollideTime.reset();
        }
        if (!Bypass.get.getIsStrafeHacked()) {
            return;
        }
        if (Fly.get.actived && Fly.get.Mode.currentMode.equalsIgnoreCase("NCP")) {
            return;
        }
        if (this.Mode.currentMode.equalsIgnoreCase("Matrix5") || this.Mode.currentMode.equalsIgnoreCase("Strict")) {
            if (this.doUps) {
                if (speed == 0.0f) {
                    speed = curSpeed * 0.25f;
                }
                if (speed <= curSpeed) {
                    speed += curSpeed / 3.0f;
                }
            }
            if (!Strafe.moves()) {
                speed = 0.0f;
            }
        }
    }

    @EventTarget
    public void onMove(EventMove2 move) {
        if (!Bypass.get.getIsStrafeHacked()) {
            return;
        }
        if ((this.Mode.currentMode.equalsIgnoreCase("Matrix5") || this.Mode.currentMode.equalsIgnoreCase("Strict")) && this.actived) {
            boolean noSlow = MoveHelper.instance.NoSlowDown.bValue;
            String slowType = MoveHelper.instance.NoSlowMode.currentMode;
            double forward = MovementInput.field_192832_b;
            double strafe = MovementInput.moveStrafe;
            float yaw = Minecraft.player.rotationYaw - (Minecraft.player.lastReportedPreYaw - Minecraft.player.rotationYaw) * 2.5f;
            if (HitAura.get.RotateMoveSide.bValue && HitAura.TARGET != null) {
                yaw = HitAura.get.rotationsVisual[0];
            }
            if (forward == 0.0 && strafe == 0.0) {
                if (Strafe.moves()) {
                    move.motion().xCoord = 0.0;
                    move.motion().zCoord = 0.0;
                }
                if (!Strafe.moves()) {
                    speed = 0.0f;
                }
                if (Strafe.moves()) {
                    MatrixStrafeMovement.oldSpeed = 0.0;
                }
                this.doUps = true;
            } else if (Strafe.moves()) {
                AxisAlignedBB B;
                double ncpMin;
                boolean posed;
                boolean matrixSlow;
                boolean f = Strafe.mc.gameSettings.keyBindForward.isKeyDown();
                float rad = 45.0f;
                if (forward != 0.0) {
                    if (strafe > 0.0) {
                        yaw += forward > 0.0 ? -rad : rad;
                    } else if (strafe < 0.0) {
                        yaw += forward > 0.0 ? rad : -rad;
                    }
                    strafe = 0.0;
                    if (forward > 0.0) {
                        forward = 1.0;
                    } else if (forward < 0.0) {
                        forward = -1.0;
                    }
                }
                boolean elytra = ElytraBoost.get.actived && (ElytraBoost.get.Mode.currentMode.equalsIgnoreCase("MatrixSpeed2") && HitAura.cooldown.hasReached(150.0) || ElytraBoost.get.Mode.currentMode.equalsIgnoreCase("MatrixSpeed3") || ElytraBoost.get.Mode.currentMode.equalsIgnoreCase("VulcanSpeed")) && ElytraBoost.canElytra();
                boolean matrixSpeedDamageHop = this.noCollideTime.hasReached(150.0) && Speed.get.actived && Speed.get.AntiCheat.currentMode.equalsIgnoreCase("Matrix") && Speed.get.StrafeDamageHop.bValue && EntityLivingBase.isMatrixDamaged && Minecraft.player.isJumping();
                double speed = MatrixStrafeMovement.calculateSpeed(this.Mode.currentMode.equalsIgnoreCase("Strict") || matrixSpeedDamageHop, move, this.DoElytraBoost.bValue && ElytraBoost.get.actived && ElytraBoost.get.Mode.currentMode.equalsIgnoreCase("StrafeSync") && ElytraBoost.canElytra() || matrixSpeedDamageHop, matrixSpeedDamageHop ? (double)0.17f : (Minecraft.player.isHandActive() || Minecraft.player.isSneaking || !this.noCollideTime.hasReached(150.0) ? 0.04 : 0.17));
                boolean bl = matrixSlow = noSlow && (slowType.equalsIgnoreCase("MatrixOld") || slowType.equalsIgnoreCase("MatrixLatest") || slowType.equalsIgnoreCase("NCP+") && Minecraft.player.isHandActive() && Minecraft.player.getItemInUseMaxCount() > 0 && !Minecraft.player.isInWater() && !Minecraft.player.isInLava() && !Minecraft.player.isInWeb && !Minecraft.player.capabilities.isFlying && Minecraft.player.getTicksElytraFlying() <= 1 && MoveMeHelp.isMoving() && !EntityLivingBase.isNcpDamaged);
                double cur = Minecraft.player.isHandActive() && !Minecraft.player.isJumping() && Minecraft.player.onGround && matrixSlow ? MoveMeHelp.getSpeed() : ((Minecraft.player.onGround ? 0.2 : (f ? 0.248 : 0.2499)) - (Minecraft.player.isSprinting() ? -0.001 : 0.0)) * (double)(Strafe.speed / curSpeed);
                cur = 0.0;
                if (Strafe.mc.timer.speed != 1.0600013732910156 && this.TimerBoost.bValue && !this.Mode.currentMode.equalsIgnoreCase("Strict") && MoveMeHelp.getCuttingSpeed() < cur && MoveMeHelp.isMoving()) {
                    Strafe.mc.timer.speed = 1.0600013732910156;
                    Timer.forceTimer(1.0600014f);
                } else if (Strafe.mc.timer.speed == 1.0600013732910156) {
                    Strafe.mc.timer.speed = 1.0;
                }
                float w = Minecraft.player.width / 2.0f - 0.025f;
                double x = Minecraft.player.posX;
                double y = Minecraft.player.posY;
                double z = Minecraft.player.posZ;
                boolean bl2 = posed = !matrixSpeedDamageHop && (Speed.posBlock(x, y - 1.0E-10, z) || Speed.posBlock(x + (double)w, y - 1.0E-10, z + (double)w) || Speed.posBlock(x - (double)w, y - 1.0E-10, z - (double)w) || Speed.posBlock(x + (double)w, y - 1.0E-10, z - (double)w) || Speed.posBlock(x - (double)w, y - 1.0E-10, z + (double)w) || Speed.posBlock(x + (double)w, y - 1.0E-10, z) || Speed.posBlock(x - (double)w, y - 1.0E-10, z) || Speed.posBlock(x, y - 1.0E-10, z + (double)w) || Speed.posBlock(x, y - 1.0E-10, z - (double)w));
                if (!matrixSpeedDamageHop && Speed.get.actived && (Speed.get.AntiCheat.currentMode.equalsIgnoreCase("Guardian") && EntityLivingBase.isSunRiseDamaged || Speed.get.AntiCheat.currentMode.equalsIgnoreCase("Matrix") && Speed.get.DamageBoost.bValue && EntityLivingBase.isMatrixDamaged) && this.noCollideTime.hasReached(150.0)) {
                    if (Minecraft.player.onGround && posed && !NoClip.get.actived) {
                        speed *= speed < 0.62 ? 1.64 : 1.528;
                    } else if (Minecraft.player.isJumping() && Speed.get.actived && Speed.get.AntiCheat.currentMode.equalsIgnoreCase("Guardian")) {
                        speed *= Minecraft.player.fallDistance == 0.0f ? 1.001 : (Speed.canMatrixBoost() && !Minecraft.player.isHandActive() && (speed < 0.4 || (double)Minecraft.player.fallDistance > 0.65 && speed < 0.6) ? 1.9 : 1.0);
                    }
                    speed = MathUtils.clamp(speed, 0.2499 - (Minecraft.player.ticksExisted % 2 == 0 ? 5.0E-7 : 0.0), 1.3);
                }
                curSpeed = (float)speed;
                if (Strafe.speed >= (float)speed || (double)Strafe.speed > cur) {
                    Strafe.speed = (float)speed;
                    this.doUps = false;
                } else {
                    this.doUps = true;
                }
                double finalSpeed = Strafe.speed;
                if (Minecraft.player.isHandActive() && !Minecraft.player.isJumping() && Minecraft.player.onGround && matrixSlow) {
                    finalSpeed *= Minecraft.player.ticksExisted % 2 == 0 ? 0.45 : 0.62;
                } else if (!matrixSpeedDamageHop && Speed.canMatrixBoost() && Speed.get.actived && Speed.get.AntiCheat.currentMode.equalsIgnoreCase("Matrix") && Speed.get.Bhop.bValue && (EntityLivingBase.isMatrixDamaged || !Speed.get.BhopOnlyDamage.bValue) && !Minecraft.player.isSneaking() && this.noCollideTime.hasReached(150.0)) {
                    finalSpeed *= 1.953;
                }
                if (Speed.get.actived && Speed.get.AntiCheat.currentMode.equalsIgnoreCase("NCP") && (double)Speed.ncpSpeed > finalSpeed && Speed.get.DamageBoost.bValue) {
                    finalSpeed = Speed.ncpSpeed;
                }
                if (elytra && ElytraBoost.flSpeed > finalSpeed) {
                    finalSpeed = ElytraBoost.flSpeed;
                }
                if (Speed.iceGo) {
                    finalSpeed = (float)(Minecraft.player.isPotionActive(Potion.getPotionById(1)) ? 0.91 : 0.63) * 1.1f;
                }
                if (this.Mode.currentMode.equalsIgnoreCase("Strict") && this.DoNcpMin.bValue && !move.toGround() && !Minecraft.player.onGround && Wrapper.getPlayer().isPotionActive(Potion.getPotionById(1)) && finalSpeed < (ncpMin = this.ncpMin())) {
                    finalSpeed = ncpMin;
                }
                if (!move.toGround() && Minecraft.player.onGround && Minecraft.player.isJumping() && NoClip.get.actived && NoClip.get.Mode.currentMode.equalsIgnoreCase("Sunrise") && !Strafe.mc.world.getCollisionBoxes(Minecraft.player, B = Minecraft.player.boundingBox).isEmpty()) {
                    finalSpeed /= 1.3;
                }
                if (speed != 0.0) {
                    move.motion().xCoord = forward * finalSpeed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * finalSpeed * Math.sin(Math.toRadians(yaw + 90.0f));
                    move.motion().zCoord = forward * finalSpeed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * finalSpeed * Math.cos(Math.toRadians(yaw + 90.0f));
                    Minecraft.player.motionX = move.motion().xCoord;
                    Minecraft.player.motionZ = move.motion().zCoord;
                }
            } else {
                if (Minecraft.player.isHandActive() || Minecraft.player.isBlocking()) {
                    move.motion().xCoord /= 1.025;
                    move.motion().zCoord /= 1.025;
                }
                if (Strafe.mc.timer.speed == 1.0600013732910156) {
                    Strafe.mc.timer.speed = 1.0;
                }
            }
        }
    }

    double ncpMin() {
        double x = Minecraft.player.posX;
        double y = Minecraft.player.posY;
        double z = Minecraft.player.posZ;
        double ncpMin = 0.0;
        if (Strafe.mc.world.getBlockState(new BlockPos(x, y - (double)0.2f, z)).getBlock() != Blocks.WATER && Strafe.mc.world.getBlockState(new BlockPos(x, y - (double)0.2f, z)).getBlock() != Blocks.LAVA) {
            if (Minecraft.player.isJumping) {
                if (Minecraft.player.isMoving() || Strafe.mc.gameSettings.keyBindBack.isKeyDown()) {
                    if (Wrapper.getPlayer().isPotionActive(Potion.getPotionById(1))) {
                        ncpMin = MoveMeHelp.getSpeed() < 0.382 ? 0.382 : MoveMeHelp.getSpeed();
                    } else {
                        double d = ncpMin = MoveMeHelp.getSpeed() < 0.266 ? 0.266 : MoveMeHelp.getSpeed();
                    }
                }
                if (!Minecraft.player.isMoving() && Strafe.mc.gameSettings.keyBindForward.isKeyDown()) {
                    double d = MoveMeHelp.getSpeed();
                    double d2 = Wrapper.getPlayer().isPotionActive(Potion.getPotionById(1)) ? 0.395 : 0.265;
                    if (d < d2) {
                        ncpMin = Minecraft.player.onGround ? (Wrapper.getPlayer().isPotionActive(Potion.getPotionById(1)) ? 0.35 : 0.24) : (Wrapper.getPlayer().isPotionActive(Potion.getPotionById(1)) ? 0.385 : 0.26);
                    } else if (!Minecraft.player.onGround) {
                        ncpMin = MoveMeHelp.getSpeed();
                    }
                }
            } else if (Strafe.mc.world.getBlockState(new BlockPos(x, y - (double)0.2f, z)).getBlock() != Blocks.WATER && Strafe.mc.world.getBlockState(new BlockPos(x, y - (double)0.2f, z)).getBlock() != Blocks.LAVA) {
                ncpMin = Wrapper.getPlayer().isPotionActive(Potion.getPotionById(1)) ? (MoveMeHelp.getSpeed() <= (Minecraft.player.isSprinting() ? 0.238 : 0.279) ? (Minecraft.player.isSprinting() ? 0.238 : 0.279) : MoveMeHelp.getSpeed()) : (MoveMeHelp.getSpeed() <= (Minecraft.player.isSprinting() ? 0.165 : 0.1945) ? (Minecraft.player.isSprinting() ? 0.165 : 0.1945) : MoveMeHelp.getSpeed());
            }
        }
        return ncpMin;
    }

    @Override
    public void onMovement() {
        double speedWspr;
        float speed;
        if (Fly.get.actived && Fly.get.Mode.currentMode.equalsIgnoreCase("NCP")) {
            return;
        }
        double x = Minecraft.player.posX;
        double y = Minecraft.player.posY;
        double z = Minecraft.player.posZ;
        boolean move2 = true;
        boolean move = true;
        if (Speed.get.actived || TargetStrafe.goStrafe()) {
            move2 = false;
            move = false;
        }
        if (NoClip.get.actived) {
            move2 = false;
            move = false;
        }
        if (Minecraft.player.isInWater() || Minecraft.player.isInLava() || Minecraft.player.isInWeb || Minecraft.player.isSneaking()) {
            move2 = false;
            move = false;
        }
        if (MoveHelper.holeTick) {
            move2 = false;
            move = false;
        }
        if (WaterSpeed.get.actived && WaterSpeed.get.Mode.currentMode.equalsIgnoreCase("Matrix") && Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.4, Minecraft.player.posZ)).getBlock() == Blocks.WATER) {
            move = false;
        }
        if (ElytraBoost.get.actived || ScaffWalk.get.actived || Fly.get.actived) {
            move = false;
        }
        if ((this.Mode.currentMode.equalsIgnoreCase("Matrix3") || !Bypass.get.getIsStrafeHacked() && this.Mode.currentMode.equalsIgnoreCase("Matrix5")) && Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.1, Minecraft.player.posZ)).getBlock() == Blocks.WATER) {
            move = false;
        }
        if (Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.1, Minecraft.player.posZ)).getBlock() == Blocks.WATER && Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + 0.02001, Minecraft.player.posZ)).getBlock() != Blocks.WATER && JesusSpeed.get.actived && JesusSpeed.get.JesusMode.currentMode.equalsIgnoreCase("MatrixZoom2") && Minecraft.player.isCollidedHorizontally) {
            move = false;
        }
        if (Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.9, Minecraft.player.posZ)).getBlock() == Blocks.WATER) {
            move = false;
        }
        if (Strafe.mc.world.getBlockState(new BlockPos(x, y - 0.2, z)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(x - 1.0, y - 0.2, z)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(x, y - 1.0, z - 1.0)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(x - 1.0, y - 0.2, z - 1.0)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(x + 1.0, y - 0.2, z)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(x, y - 0.2, z + 1.0)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(x + 1.0, y - 0.2, z + 1.0)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(x + 1.0, y - 0.2, z - 1.0)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(x - 1.0, y - 0.2, z + 1.0)).getBlock() == Blocks.WEB) {
            move = false;
        }
        if (JesusSpeed.isSwimming || Speed.snowGo) {
            move = false;
        }
        if (Minecraft.player.isHandActive() || Minecraft.player.isSneaking()) {
            move = false;
        }
        if (!(Strafe.mc.gameSettings.keyBindForward.isKeyDown() || Strafe.mc.gameSettings.keyBindBack.isKeyDown() || GameSettings.keyBindLeft.isKeyDown() || GameSettings.keyBindRight.isKeyDown() || !this.Mode.currentMode.equalsIgnoreCase("Matrix3") && (Bypass.get.getIsStrafeHacked() || !this.Mode.currentMode.equalsIgnoreCase("Matrix5")))) {
            move = false;
        }
        if (MoveHelper.holeTick) {
            move2 = false;
            move = false;
        }
        if (this.Mode.currentMode.equalsIgnoreCase("NCP") && !Speed.iceGo && move2) {
            if (this.TimerBoost.bValue && Minecraft.player.fallDistance == 0.0f && HitAura.TARGET == null && !Minecraft.player.onGround && Minecraft.player.isJumping()) {
                Strafe.mc.timer.speed = 1.0900014638900757;
            } else if (Strafe.mc.timer.speed == 1.0900014638900757) {
                Strafe.mc.timer.speed = 1.0;
            }
            Timer.forceTimer(1.0900015f);
            MoveMeHelp.setCuttingSpeed(0.0);
            if (Strafe.mc.world.getBlockState(new BlockPos(x, y - (double)0.2f, z)).getBlock() != Blocks.WATER && Strafe.mc.world.getBlockState(new BlockPos(x, y - (double)0.2f, z)).getBlock() != Blocks.LAVA) {
                if (Minecraft.player.isJumping) {
                    if (Minecraft.player.isMoving() || Strafe.mc.gameSettings.keyBindBack.isKeyDown()) {
                        if (Wrapper.getPlayer().isPotionActive(Potion.getPotionById(1))) {
                            MoveMeHelp.setCuttingSpeed(MoveMeHelp.getSpeed() < 0.382 ? 0.382 : MoveMeHelp.getSpeed());
                        } else {
                            MoveMeHelp.setCuttingSpeed(MoveMeHelp.getSpeed() < 0.266 ? 0.266 : MoveMeHelp.getSpeed());
                        }
                    }
                    if (!Minecraft.player.isMoving() && Strafe.mc.gameSettings.keyBindForward.isKeyDown()) {
                        double d = MoveMeHelp.getSpeed();
                        double d2 = Wrapper.getPlayer().isPotionActive(Potion.getPotionById(1)) ? 0.395 : 0.265;
                        if (d < d2) {
                            if (Minecraft.player.onGround) {
                                MoveMeHelp.setCuttingSpeed(Wrapper.getPlayer().isPotionActive(Potion.getPotionById(1)) ? 0.35 : 0.24);
                            } else {
                                MoveMeHelp.setCuttingSpeed(Wrapper.getPlayer().isPotionActive(Potion.getPotionById(1)) ? 0.385 : 0.26);
                            }
                        } else if (!Minecraft.player.onGround) {
                            MoveMeHelp.setCuttingSpeed(MoveMeHelp.getSpeed());
                        }
                    }
                } else if (Strafe.mc.world.getBlockState(new BlockPos(x, y - (double)0.2f, z)).getBlock() != Blocks.WATER && Strafe.mc.world.getBlockState(new BlockPos(x, y - (double)0.2f, z)).getBlock() != Blocks.LAVA) {
                    if (Wrapper.getPlayer().isPotionActive(Potion.getPotionById(1))) {
                        MoveMeHelp.setCuttingSpeed(MoveMeHelp.getSpeed() <= (Minecraft.player.isSprinting() ? 0.238 : 0.279) ? (Minecraft.player.isSprinting() ? 0.238 : 0.279) : MoveMeHelp.getSpeed());
                    } else {
                        MoveMeHelp.setCuttingSpeed(MoveMeHelp.getSpeed() <= (Minecraft.player.isSprinting() ? 0.165 : 0.1945) ? (Minecraft.player.isSprinting() ? 0.165 : 0.1945) : MoveMeHelp.getSpeed());
                    }
                }
            }
            if (Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ)).getBlock() != Blocks.WATER && Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ)).getBlock() != Blocks.LAVA && !MoveMeHelp.isBlockAboveHead() && Minecraft.player.isJumping() && Minecraft.player.motionY < 0.0 && !((double)Minecraft.player.fallDistance >= 1.19) && !Minecraft.player.onGround && this.PullDown.bValue) {
                Entity.motiony = Minecraft.player.motionY - (double)(Minecraft.player.fallDistance < 1.0f ? 0.07549f : (Minecraft.player.fallDistance < 4.0f ? 0.0732f : 0.0f));
            }
        }
        if (this.Mode.currentMode.equalsIgnoreCase("NCP2") && !Speed.iceGo && move2) {
            boolean isSpeedPot;
            if (this.TimerBoost.bValue && Minecraft.player.fallDistance == 0.0f && HitAura.TARGET == null && !Minecraft.player.onGround && Minecraft.player.isJumping()) {
                Strafe.mc.timer.speed = 1.0900014638900757;
            } else if (Strafe.mc.timer.speed == 1.0900014638900757) {
                Strafe.mc.timer.speed = 1.0;
            }
            double speed2 = MathUtils.clamp(MoveMeHelp.getSpeed(), this.ncpMin(), MoveMeHelp.getSpeed());
            boolean isDiagonal = (Strafe.mc.gameSettings.keyBindForward.isKeyDown() || Strafe.mc.gameSettings.keyBindBack.isKeyDown()) && Minecraft.player.isMoving();
            boolean bl = isSpeedPot = Minecraft.player.isPotionActive(MobEffects.SPEED) && Minecraft.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier() > 0;
            Minecraft.player.speedInAir = 0.02f + (isSpeedPot ? (isDiagonal ? (float)(speed2 / 47.0) : (float)(speed2 / 40.0)) : (isDiagonal ? 0.001f : (float)(speed2 / 160.0)));
            MoveMeHelp.setSpeed(speed2);
            MoveMeHelp.setCuttingSpeed(speed2 / (double)1.06f);
            if (Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ)).getBlock() != Blocks.WATER && Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ)).getBlock() != Blocks.LAVA && !MoveMeHelp.isBlockAboveHead() && Minecraft.player.isJumping() && Minecraft.player.motionY < 0.0 && !((double)Minecraft.player.fallDistance >= 1.19)) {
                if (HitAura.TARGET_ROTS != null) {
                    return;
                }
                if (!Minecraft.player.onGround && this.PullDown.bValue) {
                    Entity.motiony = Minecraft.player.motionY - (double)(Minecraft.player.fallDistance < 1.0f ? 0.07549f : (Minecraft.player.fallDistance < 4.0f ? 0.0732f : 0.0f));
                }
            }
        }
        if ((this.Mode.currentMode.equalsIgnoreCase("Matrix3") && (!LongJump.get.actived || !LongJump.get.Type.currentMode.equalsIgnoreCase("InstantLong")) || !Bypass.get.getIsStrafeHacked() && this.Mode.currentMode.equalsIgnoreCase("Matrix5")) && move) {
            double valWerto;
            boolean gr;
            boolean bl = gr = Minecraft.player.onGround && Minecraft.player.isCollidedVertically && !Minecraft.player.isJumping();
            speed = gr ? (Minecraft.player.isMoving() && Strafe.mc.gameSettings.keyBindForward.isKeyDown() ? 0.2845f : (Strafe.mc.gameSettings.keyBindForward.isKeyDown() ? 0.2805f : 0.26f)) : ((float)MoveMeHelp.getSpeed() > 0.3f ? (float)MoveMeHelp.getSpeed() : 0.3f);
            double WsprNumb = 0.2499f - (Minecraft.player.ticksExisted % 2 == 0 ? 5.0E-4f : 0.0f);
            speedWspr = (float)(MoveMeHelp.getSpeed() > WsprNumb ? MoveMeHelp.getSpeed() : WsprNumb);
            double grP = MoveMeHelp.getSpeed();
            double d = valWerto = MathUtils.getDifferenceOf(Minecraft.player.rotationYaw, Minecraft.player.lastReportedYaw) > 2.0 ? 0.2305 : WsprNumb;
            if (MoveMeHelp.getSpeed() < valWerto && (!Minecraft.player.onGround || gr)) {
                MoveMeHelp.setSpeed(speedWspr);
                MoveMeHelp.setCuttingSpeed(speedWspr / 1.06);
            } else if (gr || Speed.canMatrixBoost()) {
                MoveMeHelp.setSpeed(grP);
                MoveMeHelp.setCuttingSpeed(grP / 1.06);
            }
        }
        if (this.Mode.currentMode.equalsIgnoreCase("Matrix4") && MoveMeHelp.isMoving() && move) {
            boolean gr;
            if (this.TimerBoost.bValue && Minecraft.player.fallDistance == 0.0f && HitAura.TARGET == null && !Minecraft.player.onGround && Minecraft.player.isJumping() && MoveMeHelp.getSpeed() < 0.195 && Strafe.mc.timer.speed == 1.0) {
                Strafe.mc.timer.speed = 1.0900014638900757;
            } else if (Strafe.mc.timer.speed == 1.0900014638900757) {
                Strafe.mc.timer.speed = 1.0;
            }
            boolean bl = gr = Minecraft.player.onGround && Minecraft.player.isCollidedVertically && !Minecraft.player.isJumping();
            speed = gr ? (Minecraft.player.isMoving() && Strafe.mc.gameSettings.keyBindForward.isKeyDown() ? 0.2845f : (Strafe.mc.gameSettings.keyBindForward.isKeyDown() ? 0.2805f : 0.26f)) : ((float)MoveMeHelp.getSpeed() > 0.3f ? (float)MoveMeHelp.getSpeed() : 0.3f);
            double WsprNumb = 0.2499f - (Minecraft.player.ticksExisted % 2 == 0 ? 1.0E-6f : 0.0f);
            speedWspr = (float)(MoveMeHelp.getSpeed() > WsprNumb ? MoveMeHelp.getSpeed() : WsprNumb);
            if (MoveMeHelp.getSpeed() < 0.23 && !Minecraft.player.onGround && !gr) {
                MoveMeHelp.setSpeed(speedWspr);
                MoveMeHelp.setCuttingSpeed(speedWspr / 1.06);
            } else if (Speed.canMatrixBoost() && !gr) {
                MoveMeHelp.setSpeed(speedWspr);
                MoveMeHelp.setCuttingSpeed(speedWspr / 1.06);
            }
            if (Minecraft.player.isJumping() && MoveMeHelp.getSpeed() < 0.27 && Minecraft.player.onGround && !MoveMeHelp.isBlockAboveHead()) {
                MoveMeHelp.setSpeed(0.3);
                MoveMeHelp.setCuttingSpeed(0.2830188679245283);
            }
        }
    }

    @Override
    public String getDisplayName() {
        return this.getDisplayByMode(this.Mode.currentMode);
    }

    @EventTarget
    public void onPlayerMotionUpdate(EventPlayerMotionUpdate e) {
        if (!this.actived) {
            return;
        }
        if (Fly.get.actived && Fly.get.Mode.currentMode.equalsIgnoreCase("NCP")) {
            return;
        }
        double x = Minecraft.player.posX;
        double y = Minecraft.player.posY;
        double z = Minecraft.player.posZ;
        boolean move = false;
        if (Strafe.mc.timer.speed == 1.100432987562) {
            Strafe.mc.timer.speed = 1.0;
        }
        boolean bl = move = Minecraft.player.isMoving() || Strafe.mc.gameSettings.keyBindForward.isKeyDown() || Strafe.mc.gameSettings.keyBindBack.isKeyDown();
        if (Speed.get.actived || TargetStrafe.goStrafe()) {
            move = false;
        }
        if (MoveHelper.holeTick) {
            move = false;
        }
        if (NoClip.get.actived) {
            // empty if block
        }
        if (Minecraft.player.isInWater() || Minecraft.player.isInLava() || Minecraft.player.isInWeb) {
            move = false;
        }
        if (Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ)).getBlock() == Blocks.WATER || Strafe.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 1.0, Minecraft.player.posZ)).getBlock() == Blocks.WATER) {
            move = false;
        }
        if (ElytraBoost.get.actived || ScaffWalk.get.actived) {
            move = false;
        }
        if (Strafe.mc.world.getBlockState(new BlockPos(x, y - 0.2, z)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(x - 1.0, y - 0.2, z)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(x, y - 1.0, z - 1.0)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(x - 1.0, y - 0.2, z - 1.0)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(x + 1.0, y - 0.2, z)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(x, y - 0.2, z + 1.0)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(x + 1.0, y - 0.2, z + 1.0)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(x + 1.0, y - 0.2, z - 1.0)).getBlock() == Blocks.WEB || Strafe.mc.world.getBlockState(new BlockPos(x - 1.0, y - 0.2, z + 1.0)).getBlock() == Blocks.WEB) {
            move = false;
        }
        if (this.Mode.currentMode.equalsIgnoreCase("Matrix") && move) {
            if (Minecraft.player.getItemInUseMaxCount() > 0 || Minecraft.player.isSneaking()) {
                if (Minecraft.player.onGround) {
                    MoveMeHelp.setSpeed(MoveMeHelp.getSpeed() * (double)1.0071f);
                }
                if (MoveMeHelp.getSpeed() < (double)0.2175f) {
                    MoveMeHelp.setSpeed(MoveMeHelp.getSpeed() * (double)1.0071f);
                }
            } else {
                if (MoveMeHelp.getSpeed() < (double)0.2205f || Minecraft.player.onGround) {
                    MoveMeHelp.setSpeed(MoveMeHelp.getSpeed());
                }
                if (Strafe.mc.timer.speed == 1.100432987562) {
                    Strafe.mc.timer.speed = 1.0;
                }
                if (!(AirJump.get.actived && AirJump.get.Mode.currentMode.equalsIgnoreCase("Matrix") || !(MoveMeHelp.getSpeed() < (double)0.1965f) || Minecraft.player.onGround)) {
                    if (this.TimerBoost.bValue && !Minecraft.player.onGround && MoveMeHelp.getSpeed() < (double)0.14f && !HitAura.get.actived) {
                        Strafe.mc.timer.speed = 1.100432987562;
                    }
                    MoveMeHelp.setSpeed(0.1965f);
                }
                if (Minecraft.player.onGround && Minecraft.player.isJumping() && !Minecraft.player.isSprinting() && !Strafe.mc.gameSettings.keyBindForward.isKeyDown() && Minecraft.player.motionY < 0.1 && Minecraft.player.fallDistance == 0.0f) {
                    MoveMeHelp.setSpeed(MoveMeHelp.getSpeed() * 1.4);
                } else if (!(Minecraft.player.fallDistance == 0.0f || Minecraft.player.onGround || Minecraft.player.isSprinting() || Strafe.mc.gameSettings.keyBindForward.isKeyDown())) {
                    MoveMeHelp.setSpeed(MoveMeHelp.getSpeed() * (double)1.0071f);
                }
            }
        }
        if (!(!this.Mode.currentMode.equalsIgnoreCase("Matrix2") || !move || Minecraft.player.isCollidedHorizontally || AirJump.get.actived && AirJump.get.Mode.currentMode.equalsIgnoreCase("Matrix") || !(MoveMeHelp.getSpeed() < (double)0.1965f) || Minecraft.player.onGround)) {
            if (this.TimerBoost.bValue && !Minecraft.player.onGround && MoveMeHelp.getSpeed() < (double)0.14f && !HitAura.get.actived) {
                Strafe.mc.timer.speed = 1.100432987562;
            }
            MoveMeHelp.setSpeed(0.1965f);
        }
        if (this.Mode.currentMode.equalsIgnoreCase("Matrix2") && move) {
            if (MoveMeHelp.getSpeed() < 0.22) {
                MoveMeHelp.setSpeed(MoveMeHelp.getSpeed());
            }
            if (MoveMeHelp.getSpeed() < 0.18 && !Minecraft.player.onGround) {
                MoveMeHelp.setSpeed(0.18);
            }
        }
        if (this.Mode.currentMode.equalsIgnoreCase("Guardian") && move && MoveMeHelp.moveKeysPressed()) {
            if (MoveMeHelp.getSpeed() != 0.0) {
                MoveMeHelp.setSpeed(MoveMeHelp.getSpeed());
            }
            if (this.TimerBoost.bValue && !HitAura.get.actived) {
                Strafe.mc.timer.speed = 1.100432987562;
            }
        }
    }

    static {
        curSpeed = 0.0f;
    }
}

