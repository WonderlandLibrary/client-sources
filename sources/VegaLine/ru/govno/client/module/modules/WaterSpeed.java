/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.BlockPos;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.JesusSpeed;
import ru.govno.client.module.modules.Speed;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.BlockUtils;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Movement.MoveMeHelp;
import ru.govno.client.utils.TPSDetect;
import ru.govno.client.utils.Wrapper;

public class WaterSpeed
extends Module {
    public static WaterSpeed get;
    public Settings Mode;
    public Settings PotionCheck;
    public Settings Speeds;
    public Settings MotionUp;
    public Settings MotionDown;
    public Settings SpeedUp;
    public Settings SpeedDown;
    public Settings SmoothOutput;
    public Settings MoveInLava;
    public Settings NoGravity;
    public Settings MultiplySpeed;
    public Settings Multiply;
    public Settings DamageBoost;
    public static boolean isFlowingWater;
    public static boolean halfBoost;
    public static double speedInWater;
    float moveYaw;
    protected static int ticksWaterMoving;

    public WaterSpeed() {
        super("WaterSpeed", 0, Module.Category.MOVEMENT);
        get = this;
        this.Mode = new Settings("Mode", "Matrix", (Module)this, new String[]{"Custom", "Matrix", "Vulcan&NCP"});
        this.settings.add(this.Mode);
        this.PotionCheck = new Settings("PotionCheck", true, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("Custom"));
        this.settings.add(this.PotionCheck);
        this.Speeds = new Settings("Speed", 0.45f, 1.0f, 0.0f, this, () -> this.Mode.currentMode.equalsIgnoreCase("Custom"));
        this.settings.add(this.Speeds);
        this.MotionUp = new Settings("MotionUp", true, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("Custom"));
        this.settings.add(this.MotionUp);
        this.MotionDown = new Settings("MotionDown", true, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("Custom"));
        this.settings.add(this.MotionDown);
        this.SpeedUp = new Settings("SpeedUp", 1.85f, 5.0f, 0.0f, this, () -> this.Mode.currentMode.equalsIgnoreCase("Custom"));
        this.settings.add(this.SpeedUp);
        this.SpeedDown = new Settings("SpeedDown", 2.0f, 5.0f, 0.0f, this, () -> this.Mode.currentMode.equalsIgnoreCase("Custom"));
        this.settings.add(this.SpeedDown);
        this.SmoothOutput = new Settings("SmoothOutput", true, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("Custom"));
        this.settings.add(this.SmoothOutput);
        this.MoveInLava = new Settings("MoveInLava", true, (Module)this);
        this.settings.add(this.MoveInLava);
        this.NoGravity = new Settings("NoGravity", true, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("Matrix") || this.Mode.currentMode.equalsIgnoreCase("Vulcan&NCP"));
        this.settings.add(this.NoGravity);
        this.MultiplySpeed = new Settings("MultiplySpeed", true, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("Matrix"));
        this.settings.add(this.MultiplySpeed);
        this.Multiply = new Settings("Multiply", 0.9f, 1.5f, 0.0f, this, () -> this.Mode.currentMode.equalsIgnoreCase("Matrix") && this.MultiplySpeed.bValue);
        this.settings.add(this.Multiply);
        this.DamageBoost = new Settings("DamageBoost", true, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("Matrix"));
        this.settings.add(this.DamageBoost);
    }

    @Override
    public void onMovement() {
        if (this.Mode.currentMode.equalsIgnoreCase("Matrix")) {
            boolean isInLiq;
            if (JesusSpeed.get.actived && JesusSpeed.get.JesusMode.currentMode.equalsIgnoreCase("Matrix7.0.2")) {
                return;
            }
            if (isFlowingWater) {
                BlockLiquid liq;
                Block block = WaterSpeed.mc.world.getBlockState(BlockUtils.getEntityBlockPos(Minecraft.player)).getBlock();
                isFlowingWater = block instanceof BlockLiquid && (liq = (BlockLiquid)block).getDepth(WaterSpeed.mc.world.getBlockState(BlockUtils.getEntityBlockPos(Minecraft.player))) > 1;
            }
            boolean conflictSpeedFlowingWater = false;
            double speed = 0.0;
            float yport = 0.0101f;
            int speedLVL = Minecraft.player.isPotionActive(Potion.getPotionById(1)) && Minecraft.player.getActivePotionEffect(Potion.getPotionById(1)).getDuration() > 7 ? Minecraft.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier() : -1;
            String ip = mc.isSingleplayer() || mc.getCurrentServerData() == null ? "" : WaterSpeed.mc.getCurrentServerData().serverIP;
            float mYaw = MoveMeHelp.moveYaw(Minecraft.player.rotationYaw) % 360.0f;
            this.moveYaw = mYaw * 1.01f;
            boolean bl = isInLiq = WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + 0.075, Minecraft.player.posZ)).getBlock() == Blocks.WATER || WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + 0.075, Minecraft.player.posZ)).getBlock() == Blocks.LAVA && this.MoveInLava.bValue;
            if ((Minecraft.player.isInWater() || this.MoveInLava.bValue && Minecraft.player.isInLava()) && (Minecraft.player == null || !Minecraft.player.capabilities.isFlying)) {
                boolean isShar = Minecraft.player.getHeldItemOffhand().getDisplayName().contains("\u0428\u0430\u0440 \u0441\u043a\u043e\u0440\u043e\u0441\u0442\u0438");
                boolean isShar2 = Minecraft.player.getHeldItemOffhand().getDisplayName().contains("\u0428\u0430\u0440 \u0441\u043a\u043e\u0440\u043e\u0441\u0442\u0438 2") || Minecraft.player.getHeldItemOffhand().getDisplayName().contains("\u0428\u0430\u0440 \u0441\u043a\u043e\u0440\u043e\u0441\u0442\u0438 3");
                boolean uppedWater = (WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ)).getBlock() == Blocks.WATER || WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ)).getBlock() == Blocks.LAVA && this.MoveInLava.bValue) && (WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + 1.1, Minecraft.player.posZ)).getBlock() == Blocks.WATER || WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + 1.1, Minecraft.player.posZ)).getBlock() == Blocks.LAVA && this.MoveInLava.bValue);
                boolean isWaterGround = Minecraft.player.onGround && Minecraft.player.isCollidedVertically;
                boolean antiYport = WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ)).getBlock() != Blocks.WATER && Minecraft.player.motionY < -0.1;
                boolean antiNoGravity = WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + 1.2, Minecraft.player.posZ)).getBlock() != Blocks.WATER && (!this.MoveInLava.bValue || WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + 1.2, Minecraft.player.posZ)).getBlock() != Blocks.LAVA) && (WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 1.0, Minecraft.player.posZ)).getBlock() != Blocks.WATER && (!this.MoveInLava.bValue || WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 1.0, Minecraft.player.posZ)).getBlock() != Blocks.LAVA) || Minecraft.player.isCollidedHorizontally && Minecraft.player.isJumping()) || Minecraft.player.isSneaking() || Minecraft.player.isJumping();
                Enchantment depth = Enchantments.DEPTH_STRIDER;
                int depthLvl = EnchantmentHelper.getEnchantmentLevel(depth, Minecraft.player.inventory.armorItemInSlot(0));
                Minecraft.player.serverSprintState = false;
                if (depthLvl > 0) {
                    double speedSP_GR_UP = 0.75;
                    double speedSP_NO_GR = 0.6;
                    double noSpeed_GR = 0.358;
                    double noSpeed_NO_GR = 0.2699;
                    speed = Minecraft.player.isPotionActive(Potion.getPotionById(1)) ? (isWaterGround ? (uppedWater ? speedSP_GR_UP : speedSP_NO_GR) : speedSP_NO_GR) : (isWaterGround ? noSpeed_GR : noSpeed_NO_GR);
                } else {
                    double appendSpeed;
                    boolean waterAdobe = WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (Minecraft.player.onGround ? 1.0 : 0.6), Minecraft.player.posZ)).getBlock() == Blocks.WATER || this.MoveInLava.bValue && WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (Minecraft.player.onGround ? 1.0 : 0.6), Minecraft.player.posZ)).getBlock() == Blocks.LAVA;
                    double d = appendSpeed = WaterSpeed.mc.gameSettings.keyBindJump.isKeyDown() && waterAdobe ? 0.03 : 0.0;
                    double d2 = Minecraft.player.onGround ? (waterAdobe || Minecraft.player.isJumping() ? (Minecraft.player.isJumping() ? 0.13 : 0.2199) : 0.1199) : (speed = waterAdobe ? 0.1199 : 0.075);
                }
                if (isShar && uppedWater) {
                    speed *= isShar2 ? 1.299 : 1.149;
                }
                double d = this.NoGravity.bValue && !isWaterGround && !antiNoGravity ? (double)(0.0101f * (float)(Minecraft.player.ticksExisted % 3 <= 1 ? 1 : -2)) : (Entity.motiony = Entity.Getmotiony);
                if (!WaterSpeed.mc.gameSettings.keyBindJump.isKeyDown() || !WaterSpeed.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    if (WaterSpeed.mc.gameSettings.keyBindJump.isKeyDown()) {
                        boolean upWater;
                        if (Minecraft.player.onGround && WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, (double)((int)Minecraft.player.posY + 1), Minecraft.player.posZ)).getBlock() != Blocks.WATER && WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, (double)((int)Minecraft.player.posY) + 0.99, Minecraft.player.posZ)).getBlock() == Blocks.WATER) {
                            halfBoost = true;
                        } else if (!Speed.posBlock(Minecraft.player.posX, Minecraft.player.posY - 0.45, Minecraft.player.posZ)) {
                            halfBoost = false;
                        }
                        if (halfBoost) {
                            Minecraft.player.motionY = Minecraft.player.onGround ? 0.4 : 0.16;
                        } else if (Entity.Getmotiony < 0.0) {
                            Minecraft.player.motionY -= (double)0.1f;
                        }
                        boolean waterAdobe = WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (Minecraft.player.onGround || Entity.Getmotiony < 0.0 ? 1.0 : 0.5), Minecraft.player.posZ)).getBlock() == Blocks.WATER || this.MoveInLava.bValue && WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (Minecraft.player.onGround || Entity.Getmotiony < 0.0 ? 1.0 : 0.5), Minecraft.player.posZ)).getBlock() == Blocks.LAVA;
                        boolean bl2 = upWater = WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + 0.6, Minecraft.player.posZ)).getBlock() == Blocks.WATER || this.MoveInLava.bValue && WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + 0.6, Minecraft.player.posZ)).getBlock() == Blocks.LAVA;
                        if (!(halfBoost || Minecraft.player.isJumping() && Speed.posBlock(Minecraft.player.posX, Minecraft.player.posY - 1.0, Minecraft.player.posZ) && !uppedWater)) {
                            Entity.motiony = 0.19;
                            Minecraft.player.motionY = 0.19;
                        }
                    } else if (WaterSpeed.mc.gameSettings.keyBindSneak.isKeyDown()) {
                        Entity.motiony = -0.4;
                    }
                } else {
                    double d3 = Minecraft.player.isCollidedHorizontally ? (uppedWater ? 0.19 : 0.55) : (Entity.motiony = (double)-0.0101f);
                }
                if (isFlowingWater && !conflictSpeedFlowingWater) {
                    double boostFlow;
                    isFlowingWater = false;
                    double d4 = boostFlow = uppedWater || isWaterGround ? 0.651 : 0.6;
                    if (speed < boostFlow) {
                        speed = boostFlow;
                    }
                }
                if (this.MultiplySpeed.bValue) {
                    speed *= (double)this.Multiply.fValue;
                }
                if (EntityLivingBase.isMatrixDamaged && !Minecraft.player.ticker.hasReached(1200.0) && this.DamageBoost.bValue) {
                    speed = depthLvl > 0 ? 1.7 : 1.0;
                }
                speedInWater = speed;
            } else if (!isInLiq) {
                speedInWater = 0.0;
            }
            double yawDiff = MathUtils.getDifferenceOf(Minecraft.player.PreYaw, MoveMeHelp.moveYaw(Minecraft.player.rotationYaw));
            if ((speedInWater *= 1.0 - yawDiff / 60.0 / 360.0) != 0.0) {
                MoveMeHelp.setMotionSpeed(true, true, speedInWater / 1.06, this.moveYaw);
                MoveMeHelp.setMotionSpeed(false, true, speedInWater, this.moveYaw);
            } else if (speedInWater == 0.0 && isInLiq) {
                MoveMeHelp.multiplySpeed(0.7f);
            }
        }
    }

    @Override
    public String getDisplayName() {
        return this.Mode.currentMode.equalsIgnoreCase("Custom") ? this.getDisplayByDouble(this.Speeds.fValue) : this.getDisplayByMode(this.Mode.currentMode);
    }

    public static double AIMoveWaterSpeedMultiply(double prevAI, EntityLivingBase base) {
        if (base instanceof EntityPlayerSP && WaterSpeed.get.actived && WaterSpeed.get.Mode.currentMode.equalsIgnoreCase("Vulcan&NCP") && !base.isSprinting()) {
            prevAI *= 1.300001;
        }
        return prevAI;
    }

    public static double getWaterSlowDownMultiply(double prevSlow, EntityLivingBase base) {
        if (base instanceof EntityPlayerSP && WaterSpeed.get.actived && WaterSpeed.get.Mode.currentMode.equalsIgnoreCase("Vulcan&NCP")) {
            int depthLvL = EnchantmentHelper.getEnchantmentLevel(Enchantments.DEPTH_STRIDER, Minecraft.player.inventory.armorItemInSlot(0));
            double curSpeed = MoveMeHelp.getSpeed();
            int speedLvL = base.isPotionActive(MobEffects.SPEED) ? base.getActivePotionEffect(MobEffects.SPEED).getAmplifier() + 1 : 0;
            float pcExt = (Minecraft.player.isMoving() && (MoveMeHelp.a() && !MoveMeHelp.d() || !MoveMeHelp.a() && MoveMeHelp.d()) ? 0.98f : 1.0f) * (ticksWaterMoving == -2 ? (speedLvL == 2 ? 1.05f : 1.0f) : MathUtils.clamp((float)ticksWaterMoving / 7.0f, 0.0f, 1.0f));
            pcExt = (float)((double)pcExt * (Minecraft.player.isJumping() && ticksWaterMoving > 2 ? 0.8 : 1.0));
            float plus = depthLvL == 3 ? (speedLvL == 0 ? 0.499f : (speedLvL == 1 ? 0.65f : 0.58f)) : (depthLvL == 2 ? (speedLvL == 0 ? 0.32f : 0.3f) : (depthLvL == 1 ? 0.3f : 0.0f));
            prevSlow *= (double)(ticksWaterMoving % 2 == 0 && ticksWaterMoving > -2 ? 1.0f : 1.0f + (plus *= (pcExt *= TPSDetect.getTPSServer() / 20.0f)));
        }
        return prevSlow;
    }

    private double minWaterSpeedNcp() {
        int speedLvL;
        double bps = 0.0;
        int depthLvL = EnchantmentHelper.getEnchantmentLevel(Enchantments.DEPTH_STRIDER, Minecraft.player.inventory.armorItemInSlot(0));
        int n = speedLvL = Minecraft.player.isPotionActive(MobEffects.SPEED) ? Minecraft.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier() + 1 : 0;
        if (depthLvL == 0) {
            bps = 1.0;
        }
        return MoveMeHelp.getSpeedByBPS(bps);
    }

    @Override
    public void onUpdate() {
        if (this.Mode.currentMode.equalsIgnoreCase("Vulcan&NCP")) {
            if (JesusSpeed.get.actived && JesusSpeed.get.JesusMode.currentMode.equalsIgnoreCase("Matrix7.0.2")) {
                return;
            }
            boolean legsInAir = WaterSpeed.mc.world.isAirBlock(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + 0.5, Minecraft.player.posZ)) && !Minecraft.player.capabilities.isFlying;
            boolean inWater = Minecraft.player.isInWater();
            boolean onWater = !Minecraft.player.capabilities.isFlying && WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + 0.2, Minecraft.player.posZ)).getBlock() instanceof BlockLiquid;
            double curSpeed = MoveMeHelp.getSpeed();
            int n = inWater && MoveMeHelp.isMoving() ? ticksWaterMoving + 1 : (ticksWaterMoving = Minecraft.player.isJumping() ? -3 : -1);
            if (onWater) {
                Minecraft.player.setSprinting(false);
                MoveMeHelp.setSpeed(MoveMeHelp.getSpeed(), 0.6f);
            }
            if (onWater) {
                boolean frictionStapple;
                int depthLvL = EnchantmentHelper.getEnchantmentLevel(Enchantments.DEPTH_STRIDER, Minecraft.player.inventory.armorItemInSlot(0));
                boolean bl = frictionStapple = depthLvL != 0 && legsInAir && ticksWaterMoving > 4;
                if ((depthLvL == 0 && legsInAir || frictionStapple) && Minecraft.player.isJumping()) {
                    Minecraft.player.multiplyMotionXZ(frictionStapple ? 0.68f : 0.95f);
                }
            }
            if (inWater) {
                double yPort;
                double d = this.NoGravity.bValue && !Minecraft.player.onGround && !legsInAir ? (Minecraft.player.ticksExisted % 2 == 0 ? 0.01 : -0.01) : (yPort = Minecraft.player.motionY);
                double d2 = Minecraft.player.isJumping() ? (Minecraft.player.isCollidedHorizontally && legsInAir ? 0.3 : ((double)Minecraft.player.fallDistance > 0.1 && Minecraft.player.motionY < 0.0 ? -0.06 : 0.09)) : (Minecraft.player.motionY = Minecraft.player.isSneaking() ? Minecraft.player.motionY - 0.15 : yPort);
            }
        }
        if (this.Mode.currentMode.equalsIgnoreCase("Custom")) {
            boolean gs;
            if (JesusSpeed.get.actived && JesusSpeed.get.JesusMode.currentMode.equalsIgnoreCase("Matrix7.0.2")) {
                return;
            }
            boolean bl = gs = Speed.get.actived && Speed.get.AntiCheat.currentMode.equalsIgnoreCase("Guardian");
            if (!(this.PotionCheck.bValue && !Wrapper.getPlayer().isPotionActive(Potion.getPotionById(1)) || !Minecraft.player.isInWater() && (!this.MoveInLava.bValue || !Minecraft.player.isInLava()) || gs || Minecraft.player.isJumping() && Minecraft.player.isCollidedHorizontally && WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + 1.0, Minecraft.player.posZ)).getBlock() == Blocks.AIR && this.SmoothOutput.bValue)) {
                if (this.MotionUp.bValue && Minecraft.player.isJumping()) {
                    Minecraft.player.motionY = this.SpeedUp.fValue / 5.0f * (float)(gs ? 3 : 1);
                }
                if (this.MotionDown.bValue && Minecraft.player.isSneaking()) {
                    Minecraft.player.motionY = -(this.SpeedDown.fValue / 5.0f * (float)(gs ? 3 : 1));
                }
                if (this.SmoothOutput.bValue && Minecraft.player.isCollidedHorizontally && WaterSpeed.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + 1.0, Minecraft.player.posZ)).getBlock() == Blocks.AIR) {
                    return;
                }
                if (Speed.get.actived && Speed.get.AntiCheat.currentMode.equalsIgnoreCase("Guardian")) {
                    if (!WaterSpeed.mc.gameSettings.keyBindSneak.isKeyDown() && !WaterSpeed.mc.gameSettings.keyBindJump.isKeyDown()) {
                        Minecraft.player.motionY = 0.0;
                    }
                    return;
                }
                MoveMeHelp.setSpeed(this.Speeds.fValue);
            }
        }
    }

    static {
        halfBoost = false;
        speedInWater = 0.0;
        ticksWaterMoving = 0;
    }
}

