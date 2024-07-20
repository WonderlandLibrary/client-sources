/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemElytra;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.Criticals;
import ru.govno.client.module.modules.ElytraBoost;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.module.modules.NoClip;
import ru.govno.client.module.modules.Strafe;
import ru.govno.client.module.modules.TargetStrafe;
import ru.govno.client.module.modules.Timer;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Math.BlockUtils;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Movement.MoveMeHelp;
import ru.govno.client.utils.Movement.MovementHelper;

public class Speed
extends Module {
    public static Speed get;
    public Settings AntiCheat;
    public Settings SpeedF;
    public Settings OnlyMove;
    public Settings Bhop;
    public Settings BhopOnlyDamage;
    public Settings DamageBoost;
    public Settings IceSpeed;
    public Settings AirBoost;
    public Settings GroundBoost;
    public Settings Yport;
    public Settings SnowBoost;
    public Settings StrafeDamageHop;
    public Settings LongHop;
    public Settings OnGround;
    public Settings UseTimer;
    public Settings Caress;
    private boolean enabledWithModeVanillaAir;
    public static boolean snowGo;
    public static boolean snowGround;
    private final TimerHelper timer2 = new TimerHelper();
    public static float ncpSpeed;
    public static boolean iceGo;
    private final TimerHelper ncpIceTimer = new TimerHelper();
    private final TimerHelper forGuardianTimer = new TimerHelper();
    private final TimerHelper forRipServerTimer = new TimerHelper();

    public Speed() {
        super("Speed", 0, Module.Category.MOVEMENT);
        get = this;
        this.AntiCheat = new Settings("AntiCheat", "Matrix", (Module)this, new String[]{"Matrix", "AAC", "NCP", "Guardian", "RipServer", "Intave", "Vanilla", "Vulcan", "Strict", "Grim", "VanillaAir"});
        this.settings.add(this.AntiCheat);
        this.SpeedF = new Settings("Speed", 0.8f, 2.0f, 0.23f, this, () -> this.AntiCheat.currentMode.contains("Vanilla"));
        this.settings.add(this.SpeedF);
        this.OnlyMove = new Settings("OnlyMove", false, (Module)this, () -> this.AntiCheat.currentMode.equalsIgnoreCase("Vanilla"));
        this.settings.add(this.OnlyMove);
        this.Bhop = new Settings("Bhop", true, (Module)this, () -> this.AntiCheat.currentMode.equalsIgnoreCase("Matrix"));
        this.settings.add(this.Bhop);
        this.BhopOnlyDamage = new Settings("BhopOnlyDamage", false, (Module)this, () -> this.AntiCheat.currentMode.equalsIgnoreCase("Matrix") && this.Bhop.bValue);
        this.settings.add(this.BhopOnlyDamage);
        this.DamageBoost = new Settings("DamageBoost", true, (Module)this, () -> this.AntiCheat.currentMode.equalsIgnoreCase("Matrix") || this.AntiCheat.currentMode.equalsIgnoreCase("NCP"));
        this.settings.add(this.DamageBoost);
        this.IceSpeed = new Settings("IceSpeed", true, (Module)this, () -> this.AntiCheat.currentMode.equalsIgnoreCase("NCP"));
        this.settings.add(this.IceSpeed);
        this.AirBoost = new Settings("AirBoost", false, (Module)this, () -> this.AntiCheat.currentMode.equalsIgnoreCase("Matrix"));
        this.settings.add(this.AirBoost);
        this.GroundBoost = new Settings("GroundBoost", false, (Module)this, () -> this.AntiCheat.currentMode.equalsIgnoreCase("Matrix"));
        this.settings.add(this.GroundBoost);
        this.Yport = new Settings("Yport", false, (Module)this, () -> this.AntiCheat.currentMode.equalsIgnoreCase("Matrix"));
        this.settings.add(this.Yport);
        this.SnowBoost = new Settings("SnowBoost", false, (Module)this, () -> this.AntiCheat.currentMode.equalsIgnoreCase("Matrix"));
        this.settings.add(this.SnowBoost);
        this.StrafeDamageHop = new Settings("StrafeDamageHop", true, (Module)this, () -> this.AntiCheat.currentMode.equalsIgnoreCase("Matrix"));
        this.settings.add(this.StrafeDamageHop);
        this.LongHop = new Settings("LongHop", true, (Module)this, () -> this.AntiCheat.currentMode.equalsIgnoreCase("AAC"));
        this.settings.add(this.LongHop);
        this.OnGround = new Settings("OnGround", true, (Module)this, () -> this.AntiCheat.currentMode.equalsIgnoreCase("AAC"));
        this.settings.add(this.OnGround);
        this.UseTimer = new Settings("UseTimer", true, (Module)this, () -> this.AntiCheat.currentMode.equalsIgnoreCase("NCP"));
        this.settings.add(this.UseTimer);
        this.Caress = new Settings("Caress", false, (Module)this, () -> this.AntiCheat.currentMode.equalsIgnoreCase("Intave"));
        this.settings.add(this.Caress);
    }

    public static boolean posBlock(double x, double y, double z) {
        return Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.AIR && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.WATER && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.LAVA && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.BED && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.CAKE && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.TALLGRASS && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.STONE_BUTTON && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.WOODEN_BUTTON && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.FLOWER_POT && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.CHORUS_FLOWER && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.RED_FLOWER && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.YELLOW_FLOWER && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SAPLING && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.VINE && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.ACACIA_FENCE && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.ACACIA_FENCE_GATE && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.BIRCH_FENCE && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.BIRCH_FENCE_GATE && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DARK_OAK_FENCE && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DARK_OAK_FENCE_GATE && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.JUNGLE_FENCE && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.JUNGLE_FENCE_GATE && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.NETHER_BRICK_FENCE && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.OAK_FENCE && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.OAK_FENCE_GATE && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SPRUCE_FENCE && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SPRUCE_FENCE_GATE && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.ENCHANTING_TABLE && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.END_PORTAL_FRAME && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DOUBLE_PLANT && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.STANDING_SIGN && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.WALL_SIGN && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SKULL && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DAYLIGHT_DETECTOR && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DAYLIGHT_DETECTOR_INVERTED && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.PURPUR_SLAB && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.STONE_SLAB && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.WOODEN_SLAB && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.CARPET && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DEADBUSH && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.REDSTONE_WIRE && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.WALL_BANNER && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.REEDS && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.UNLIT_REDSTONE_TORCH && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.TORCH && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.REDSTONE_WIRE && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.WATERLILY && Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SNOW_LAYER;
    }

    public static boolean canMatrixBoost() {
        double x = Minecraft.player.posX;
        double y = Minecraft.player.posY;
        float ex = 0.39f;
        double z = Minecraft.player.posZ;
        if ((Speed.mc.world.getBlockState(new BlockPos(x, y - (double)ex, z)).getBlock() == Blocks.PURPUR_SLAB || Speed.mc.world.getBlockState(new BlockPos(x, y - (double)ex, z)).getBlock() == Blocks.STONE_SLAB2 || Speed.mc.world.getBlockState(new BlockPos(x, y - (double)ex, z)).getBlock() == Blocks.STONE_SLAB || Speed.mc.world.getBlockState(new BlockPos(x, y - (double)ex, z)).getBlock() == Blocks.WOODEN_SLAB) && Minecraft.player.posY + 0.5 >= (double)((int)Minecraft.player.posY)) {
            ex += 0.62f;
        }
        if ((double)Minecraft.player.fallDistance > 0.1 && !(Minecraft.player.inventory.armorItemInSlot(2).getItem() instanceof ItemElytra) && (Speed.posBlock(x, y - (double)ex, z) || Speed.posBlock(x - 0.3, y - (double)ex, z - 0.3) || Speed.posBlock(x + 0.3, y - (double)ex, z + 0.3) || Speed.posBlock(x - 0.3, y - (double)ex, z + 0.3) || Speed.posBlock(x + 0.3, y - (double)ex, z - 0.3) || Speed.posBlock(x + 0.3, y - (double)ex, z) || Speed.posBlock(x - 0.3, y - (double)ex, z) || Speed.posBlock(x, y - (double)ex, z - 0.3) || Speed.posBlock(x, y - (double)ex, z + 0.3))) {
            return MoveMeHelp.getSpeed() > MoveMeHelp.getSpeedByBPS(4.3);
        }
        return false;
    }

    @Override
    public void onMovement() {
        if (Minecraft.player == null || Speed.mc.world == null) {
            return;
        }
        try {
            this.speedMove(this.AntiCheat.currentMode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDisplayName() {
        return this.getDisplayByMode(this.AntiCheat.currentMode);
    }

    @Override
    public void onUpdate() {
        if (Minecraft.player == null || Speed.mc.world == null) {
            return;
        }
        try {
            this.speed(this.AntiCheat.currentMode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onToggled(boolean actived) {
        if (!actived) {
            this.onDisableSpeed();
        }
        if (actived && this.AntiCheat.currentMode.equalsIgnoreCase("VanillaAir")) {
            this.enabledWithModeVanillaAir = true;
        }
        super.onToggled(actived);
    }

    private void onDisableSpeed() {
        if (this.AntiCheat.currentMode.equalsIgnoreCase("NCP")) {
            this.forNCPoff(this.UseTimer.bValue);
        }
        if (this.AntiCheat.currentMode.equalsIgnoreCase("AAC") && Speed.mc.timer.speed == 1.2) {
            Speed.mc.timer.speed = 1.0;
        }
        if ((this.AntiCheat.currentMode.equalsIgnoreCase("Intave") || this.AntiCheat.currentMode.equalsIgnoreCase("Strict")) && Speed.mc.timer.speed != 1.0) {
            this.forIntaveOrStrictOff();
        }
        if (this.AntiCheat.currentMode.equalsIgnoreCase("Matrix")) {
            snowGo = false;
            snowGround = false;
        }
        if (this.AntiCheat.currentMode.equalsIgnoreCase("VanillaAir") || this.enabledWithModeVanillaAir) {
            this.forVanillaAirOff();
        }
    }

    private void speed(String antiCheat) {
        if (!(antiCheat != null && (MoveMeHelp.moveKeysPressed() || antiCheat.equalsIgnoreCase("NCP") && TargetStrafe.goStrafe()))) {
            return;
        }
        if (antiCheat.equalsIgnoreCase("Matrix")) {
            this.forMatrix(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ);
        }
        if (antiCheat.equalsIgnoreCase("AAC")) {
            this.forAAC();
        }
        if (antiCheat.equalsIgnoreCase("NCP")) {
            this.forNCP(this.UseTimer.bValue, this.DamageBoost.bValue, this.IceSpeed.bValue, Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ);
        }
        if (antiCheat.equalsIgnoreCase("Guardian")) {
            this.forGuardian();
        }
        if (antiCheat.equalsIgnoreCase("RipServer")) {
            this.forRipServer();
        }
        if (antiCheat.equalsIgnoreCase("Intave")) {
            this.forIntave(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ, this.Caress.bValue);
        }
        if (antiCheat.equalsIgnoreCase("Vulcan")) {
            this.forVulcan();
        }
        if (antiCheat.equalsIgnoreCase("Strict")) {
            this.forStrict();
        }
        if (antiCheat.equalsIgnoreCase("Grim")) {
            this.forGrim();
        }
        if (antiCheat.equalsIgnoreCase("VanillaAir")) {
            this.forVanillaAir();
        }
    }

    private void forVulcan() {
        PotionEffect active = Minecraft.player.getActivePotionEffect(MobEffects.SPEED);
        boolean isSpeed = active != null && active.getAmplifier() >= 0 && active.getDuration() > 9;
        boolean isSpeed2 = active != null && active.getAmplifier() >= 1 && active.getDuration() > 9;
        int ticks = Minecraft.player.ticksExisted;
        AxisAlignedBB bx = Minecraft.player.boundingBox;
        if (Minecraft.player.movementInput.jump) {
            if (Minecraft.player.onGround) {
                return;
            }
            if (Minecraft.player.fallDistance != 0.0f && (double)Minecraft.player.fallDistance < 0.5) {
                Minecraft.player.motionY -= (double)0.1f;
                Speed.mc.timer.field_194147_b = 0.1f;
            }
            if (!isSpeed && !isSpeed2) {
                MoveMeHelp.setSpeed(MathUtils.clamp(MoveMeHelp.getSpeed(), (double)0.29f, MoveMeHelp.getSpeedByBPS(5.25)));
                return;
            }
            MoveMeHelp.setSpeed(MathUtils.clamp(MoveMeHelp.getSpeed(), Minecraft.player.onGround ? (double)0.12f : (isSpeed ? (double)0.374f : (isSpeed2 ? (double)0.449f : (double)0.29f)), MoveMeHelp.getSpeed()));
        } else if (!Speed.mc.world.getCollisionBoxes(Minecraft.player, new AxisAlignedBB(bx.minX, bx.minY - (double)0.08f, bx.minZ, bx.maxX, bx.minY, bx.maxZ)).isEmpty() && Speed.mc.world.getCollisionBoxes(Minecraft.player, bx).isEmpty() && MoveMeHelp.getSpeed() > 0.1) {
            double speed;
            float time;
            if (Criticals.get.actived && Criticals.get.currentMode("Mode").equalsIgnoreCase("VanillaHop") && HitAura.TARGET != null && !Minecraft.player.isJumping() && !Minecraft.player.isInWater() && (time = HitAura.get.msCooldown() - (float)HitAura.cooldown.getTime()) < 300.0f) {
                return;
            }
            double d = isSpeed2 ? 0.4 : (speed = isSpeed ? 0.35 : 0.29);
            if (Minecraft.player.onGround && Minecraft.player.ticksExisted % 3 == 0) {
                Minecraft.player.motionY = 0.0391;
                double d2 = speed = isSpeed2 ? 0.59 : 0.49;
            }
            if (ticks % 3 == 2) {
                Speed.mc.timer.field_194147_b = 0.1f;
            }
            MoveMeHelp.setSpeed(speed);
        }
    }

    private void forStrict() {
        float f = Speed.mc.timer.field_194147_b = Minecraft.player.ticksExisted % 6 == 0 ? 1.3f : 0.0f;
        if (Minecraft.player.isJumping() && Entity.Getmotiony < 0.1 && Minecraft.player.fallDistance <= 1.0f) {
            Entity.motiony = Entity.Getmotiony - 0.079;
        }
    }

    private void speedMove(String antiCheat) {
        if (antiCheat == null) {
            return;
        }
        if (antiCheat.equalsIgnoreCase("Vanilla")) {
            this.forVanilla();
        }
        if (antiCheat.equalsIgnoreCase("RipServer")) {
            this.forRipServerMove();
        }
    }

    private void forVanilla() {
        MoveMeHelp.setMotionSpeed(true, this.OnlyMove.bValue, this.SpeedF.fValue * (Minecraft.player.ticksExisted % 2 == 0 ? 1.0f : 0.99f));
    }

    private void forIntave(double x, double y, double z, boolean caress) {
        double d = Speed.mc.timer.speed = Minecraft.player.fallDistance == 0.0f && !Minecraft.player.onGround ? (double)1.12f : (double)1.08f;
        if (!caress) {
            if ((double)Minecraft.player.fallDistance > 0.1) {
                Minecraft.player.jumpMovementFactor = (float)((double)Minecraft.player.jumpMovementFactor + 1.3E-4);
            }
            float ex = 0.38f;
            Minecraft.player.setSprinting(Minecraft.player.fallDistance != 0.0f);
            Minecraft.player.serverSprintState = Minecraft.player.isSprinting();
            if (Minecraft.player.serverSprintState) {
                for (int i = 0; i < 2; ++i) {
                    Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_SPRINTING));
                }
            }
            Minecraft.player.jumpTicks = 0;
            if (Minecraft.player.isCollidedVertically && MoveMeHelp.getSpeed() >= 0.4) {
                Minecraft.player.onGround = false;
            }
            if ((double)Minecraft.player.fallDistance > 0.1 && Speed.posBlock(x, y - (double)ex, z) && Minecraft.player.isJumping()) {
                Speed.mc.timer.speed = 1.5;
                Minecraft.player.fallDistance = 0.0f;
                Minecraft.player.fall(16.0f, 20.0f);
                Minecraft.player.onGround = true;
                Minecraft.player.motionY /= (double)1.002f;
                Minecraft.player.posY -= 0.0034;
            }
        }
        if (!(Minecraft.player.onGround && Minecraft.player.isJumping() || Minecraft.player.isMoving())) {
            Minecraft.player.multiplyMotionXZ(1.0013f);
        }
        if (Minecraft.player.onGround && !Minecraft.player.isJumping() && !Minecraft.player.isMoving() && !this.Caress.bValue) {
            MoveMeHelp.setSpeed(MoveMeHelp.getSpeed() * 1.016);
        }
    }

    private void forIntaveOrStrictOff() {
        if (Speed.mc.timer.speed != 1.0) {
            Speed.mc.timer.speed = 1.0;
        }
    }

    private void forVanillaAirOff() {
        Minecraft.player.speedInAir = 0.02f;
        this.enabledWithModeVanillaAir = false;
    }

    private void forMatrix(double x, double y, double z) {
        double bSpeed;
        boolean posed;
        float w = Minecraft.player.width / 2.0f - 0.025f;
        boolean bl = posed = Speed.posBlock(x, y - 1.0E-10, z) || Speed.posBlock(x + (double)w, y - 1.0E-10, z + (double)w) || Speed.posBlock(x - (double)w, y - 1.0E-10, z - (double)w) || Speed.posBlock(x + (double)w, y - 1.0E-10, z - (double)w) || Speed.posBlock(x - (double)w, y - 1.0E-10, z + (double)w) || Speed.posBlock(x + (double)w, y - 1.0E-10, z) || Speed.posBlock(x - (double)w, y - 1.0E-10, z) || Speed.posBlock(x, y - 1.0E-10, z + (double)w) || Speed.posBlock(x, y - 1.0E-10, z - (double)w);
        boolean yPort = !Minecraft.player.onGround && (double)Minecraft.player.fallDistance >= 0.068 && Speed.posBlock(x, y - (this.AirBoost.bValue ? 0.9 : 0.5), z) && this.Yport.bValue;
        boolean bHop = !(!this.Bhop.bValue || !EntityLivingBase.isMatrixDamaged && this.BhopOnlyDamage.bValue || !Minecraft.player.isJumping() || !Speed.canMatrixBoost() && !yPort || Minecraft.player.onGround || Minecraft.player.isSneaking());
        boolean dBoost = this.DamageBoost.bValue && EntityLivingBase.isMatrixDamaged && (Speed.canMatrixBoost() && bHop || Minecraft.player.onGround && Minecraft.player.isCollidedVertically && posed) && MoveMeHelp.getCuttingSpeed() < 1.2;
        boolean airBoost = Minecraft.player.fallDistance == 0.0f && Speed.posBlock(x, y - 1.0, z) && this.AirBoost.bValue && Minecraft.player.isJumping() && MoveMeHelp.isMoving() && !Minecraft.player.isSneaking() && (!Minecraft.player.isCollidedVertically || Minecraft.player.posY == (double)((int)Minecraft.player.posY) || Minecraft.player.ticksExisted % 2 != 0) && !EntityLivingBase.isMatrixDamaged;
        boolean gBoost = Minecraft.player.onGround && Minecraft.player.isCollidedVertically && !Minecraft.player.isJumping() && this.GroundBoost.bValue && Speed.posBlock(x, y - 1.0E-10, z);
        boolean snowBoost = this.SnowBoost.bValue;
        if (bHop && !dBoost) {
            bSpeed = MoveMeHelp.getSpeed() * 1.9987;
            MoveMeHelp.setSpeed(bSpeed);
            MoveMeHelp.setCuttingSpeed(bSpeed / (double)1.06f);
        }
        if (dBoost) {
            if (bHop) {
                bSpeed = MoveMeHelp.getSpeed() * (double)2.461f;
                MoveMeHelp.setSpeed(bSpeed);
                MoveMeHelp.setCuttingSpeed(bSpeed / (double)1.06f);
                if (Minecraft.player.stepHeight == 0.0f) {
                    Minecraft.player.stepHeight = 0.6f;
                }
            } else if (!NoClip.get.actived) {
                if (Minecraft.player.stepHeight == 0.6f) {
                    Minecraft.player.stepHeight = 0.0f;
                }
                float dir1 = (float)(-Math.sin(MovementHelper.getDirection())) * (Speed.mc.gameSettings.keyBindBack.isKeyDown() ? -1.0f : 1.0f);
                float dir2 = (float)Math.cos(MovementHelper.getDirection()) * (Speed.mc.gameSettings.keyBindBack.isKeyDown() ? -1.0f : 1.0f);
                if (MoveMeHelp.isMoving()) {
                    if (MoveMeHelp.getSpeed() < 0.08) {
                        MoveMeHelp.setSpeed(0.42);
                    } else {
                        Minecraft.player.addVelocity((double)dir1 * 9.8 / 25.0, 0.0, (double)dir2 * 9.8 / 25.0);
                        MoveMeHelp.setSpeed(MoveMeHelp.getSpeed());
                    }
                }
            }
        } else if (Minecraft.player.stepHeight == 0.0f) {
            Minecraft.player.stepHeight = 0.6f;
        }
        if (airBoost && !dBoost) {
            Minecraft.player.onGround = true;
        }
        if (gBoost && !dBoost && Minecraft.player.onGround) {
            Minecraft.player.motionY -= 1.0;
            if (!Minecraft.player.isJumping && Minecraft.player.ticksExisted % 3 == 0) {
                Minecraft.player.multiplyMotionXZ(1.35f);
                Minecraft.player.setPosition(x, y + 9.234E-7, z);
                Minecraft.player.posY -= 9.234E-7;
            }
        }
        if (yPort) {
            Minecraft.player.motionY = -0.22;
            Entity.motiony = -4.76;
        }
        if (snowBoost) {
            if (MoveMeHelp.getSpeed() < 0.36) {
                snowGround = false;
            }
            if (!(Speed.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SNOW_LAYER && Speed.mc.world.getBlockState(new BlockPos(x, y - 1.0, z)).getBlock() != Blocks.SNOW_LAYER || Speed.mc.world.getBlockState(new BlockPos(x, y - 1.0E-10, z)).getBlock() == Blocks.SNOW_LAYER && (!Minecraft.player.isJumping() || Minecraft.player.fallDistance == 0.0f && !Minecraft.player.onGround) || Minecraft.player.getItemInUseMaxCount() != 0 || Minecraft.player.isSneaking || snowGo && MoveMeHelp.getSpeed() > 0.4 && Minecraft.player.isJumping)) {
                if (!Minecraft.player.isJumping() && Minecraft.player.onGround) {
                    snowGround = true;
                } else if (MoveMeHelp.getSpeed() > 0.35 && snowGround) {
                    MoveMeHelp.setSpeed(0.35);
                }
                if (snowGround) {
                    MoveMeHelp.setCuttingSpeed(0.6205);
                    snowGo = true;
                } else if (Minecraft.player.onGround && MoveMeHelp.getSpeed() < 0.14) {
                    MoveMeHelp.setSpeed(0.18);
                }
            } else {
                snowGo = false;
            }
        }
    }

    private void forAAC() {
        boolean longHop = this.LongHop.bValue && (Minecraft.player.isJumping() || Minecraft.player.fallDistance != 0.0f);
        boolean onGround = this.OnGround.bValue && !Minecraft.player.isJumping() && Minecraft.player.onGround && Minecraft.player.isCollidedVertically && MoveMeHelp.getSpeed() < 0.9;
        Speed.mc.timer.speed = 1.2;
        if (longHop) {
            Minecraft.player.jumpMovementFactor = 0.17f;
            Minecraft.player.multiplyMotionXZ(1.005f);
        }
        if (onGround) {
            Minecraft.player.multiplyMotionXZ(1.212f);
        }
    }

    private void forNCP(boolean timer, boolean damageBoost, boolean iceSpeed, double x, double y, double z) {
        if (timer) {
            Timer.forceTimer(1.075f);
        }
        float speed = 0.0f;
        if (EntityLivingBase.isNcpDamaged && !Minecraft.player.onGround && !Minecraft.player.isInWeb && (MoveMeHelp.isMoving() || TargetStrafe.goStrafe())) {
            speed = 0.55f;
        }
        if (speed != 0.0f && !TargetStrafe.goStrafe()) {
            MoveMeHelp.setCuttingSpeed((double)speed / 1.06);
        }
        ncpSpeed = speed;
        if (iceSpeed) {
            if (Speed.mc.world.getBlockState(new BlockPos(x, y - 0.51, z)).getBlock() == Block.getBlockById(212) || Speed.mc.world.getBlockState(new BlockPos(x, y - 0.51, z)).getBlock() == Block.getBlockById(79) || Speed.mc.world.getBlockState(new BlockPos(x, y - 0.51, z)).getBlock() == Block.getBlockById(174) || Speed.mc.world.getBlockState(new BlockPos(x, y - 0.95, z)).getBlock() == Block.getBlockById(212) || Speed.mc.world.getBlockState(new BlockPos(x, y - 0.95, z)).getBlock() == Block.getBlockById(79) || Speed.mc.world.getBlockState(new BlockPos(x, y - 0.95, z)).getBlock() == Block.getBlockById(174)) {
                if (!BlockUtils.getBlockWithExpand(0.3, BlockUtils.getEntityBlockPos(Minecraft.player), Blocks.WATER) && !BlockUtils.getBlockWithExpand(0.3, BlockUtils.getEntityBlockPos(Minecraft.player), Blocks.LAVA)) {
                    this.ncpIceTimer.reset();
                    iceGo = true;
                }
            } else if (this.ncpIceTimer.hasReached(800.0)) {
                iceGo = false;
            }
        } else {
            iceGo = false;
        }
        if (iceSpeed && iceGo) {
            boolean isSpeedPot2;
            boolean bl = isSpeedPot2 = Minecraft.player.isPotionActive(MobEffects.SPEED) && Minecraft.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier() >= 1;
            if (Minecraft.player.isJumping) {
                if (Speed.mc.gameSettings.keyBindForward.isKeyDown()) {
                    MoveMeHelp.setSpeed(isSpeedPot2 ? 0.879 : 0.623);
                } else {
                    MoveMeHelp.setSpeed(isSpeedPot2 ? 0.91 : 0.63);
                }
            } else {
                MoveMeHelp.setSpeed(isSpeedPot2 ? 0.9685 : 0.687);
            }
        }
    }

    private void forNCPoff(boolean timer) {
        if (Minecraft.player.speedInAir == 0.06f || Minecraft.player.speedInAir == 0.05f) {
            Minecraft.player.speedInAir = 0.02f;
        }
        ncpSpeed = 0.0f;
        iceGo = false;
    }

    private void forGuardian() {
        if (Strafe.get.actived && Strafe.get.Mode.currentMode.equalsIgnoreCase("Matrix5") && Strafe.moves() || !EntityLivingBase.isSunRiseDamaged) {
            this.forGuardianTimer.reset();
            return;
        }
        if (MoveMeHelp.moveKeysPressed()) {
            double speed = MathUtils.clamp(MoveMeHelp.getSpeed(), 0.2499, 9.9);
            if (Minecraft.player.onGround && !Minecraft.player.isJumping()) {
                speed *= 1.8;
            } else if (Minecraft.player.isJumping()) {
                speed *= Speed.canMatrixBoost() ? 1.8 : 1.0;
            }
            speed = MathUtils.clamp(speed, 0.2499, 1.17455998);
            MoveMeHelp.setSpeed(speed);
            MoveMeHelp.setCuttingSpeed(speed / 1.06);
        }
    }

    private void forRipServer() {
        if (!(!this.forRipServerTimer.hasReached(10.0) || ElytraBoost.get.actived && (ElytraBoost.get.currentMode("Mode").equalsIgnoreCase("MatrixFly") || ElytraBoost.get.currentMode("Mode").equalsIgnoreCase("MatrixSpeed")) && ElytraBoost.canElytra())) {
            if (Minecraft.player.onGround && !Minecraft.player.isJumping()) {
                MoveMeHelp.setSpeed(MathUtils.clamp(MoveMeHelp.getSpeed() * (Minecraft.player.rayGround ? 1.8 : 0.8), 0.2, MoveMeHelp.w() && Minecraft.player.isSprinting() ? (double)1.7155f : (double)1.745f));
                Minecraft.player.rayGround = Minecraft.player.onGround;
            } else {
                Minecraft.player.serverSprintState = true;
                MoveMeHelp.setSpeed(MathUtils.clamp(MoveMeHelp.getSpeed() * (Minecraft.player.onGround || Minecraft.player.rayGround ? 1.0 : 1.2), 0.195, 1.823585033416748), 0.12f);
                Minecraft.player.rayGround = Minecraft.player.onGround;
            }
            this.forRipServerTimer.reset();
        }
    }

    private void forRipServerMove() {
        if (MoveMeHelp.isMoving()) {
            MoveMeHelp.setCuttingSpeed(MoveMeHelp.getCuttingSpeed() / (double)1.06f);
        }
    }

    private void forGrim() {
        if (Minecraft.player.onGround || Minecraft.player.motionY < 0.0 && !Minecraft.player.onGround) {
            Timer.forceTimer(Minecraft.player.isJumping() ? 1.02f : 1.015f);
        }
        Minecraft.player.rotationYaw = Minecraft.player.rotationYaw + (Minecraft.player.ticksExisted % 2 == 0 ? -0.25f : 0.25f);
        if (Minecraft.player.ticksExisted % 2 == 0 && Minecraft.player.fallDistance != 0.0f) {
            Minecraft.player.motionY -= (double)0.003f;
        }
        Minecraft.player.motionX = Minecraft.player.motionX * (Minecraft.player.onGround && !Minecraft.player.isJumping() ? (double)1.02844f : (double)1.002446f);
        Minecraft.player.motionZ = Minecraft.player.motionZ * (Minecraft.player.onGround && !Minecraft.player.isJumping() ? (double)1.02844f : (double)1.002446f);
    }

    private void forVanillaAir() {
        if (!Minecraft.player.isJumping) {
            Minecraft.player.onGround = false;
        }
        if (Minecraft.player.isJumping && Minecraft.player.onGround) {
            Minecraft.player.motionY = 0.42f;
        }
        Minecraft.player.speedInAir = this.SpeedF.fValue;
        if (!Minecraft.player.onGround) {
            Minecraft.player.motionX /= 5.0;
            Minecraft.player.motionZ /= 5.0;
        }
    }

    static {
        snowGo = false;
        snowGround = false;
        ncpSpeed = 0.0f;
    }
}

