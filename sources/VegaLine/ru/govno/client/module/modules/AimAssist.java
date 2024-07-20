/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import javax.vecmath.Vector2f;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Mouse;
import ru.govno.client.Client;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.friendsystem.Friend;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.AirJump;
import ru.govno.client.module.modules.ElytraBoost;
import ru.govno.client.module.modules.Fly;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.module.modules.JesusSpeed;
import ru.govno.client.module.modules.TargetStrafe;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Combat.RotationUtil;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Movement.MoveMeHelp;
import ru.govno.client.utils.Wrapper;

public class AimAssist
extends Module {
    static EntityLivingBase target;
    float AnimYaw;
    float AnimPitch;
    Settings OnlyAttackKey;
    Settings AimBot;
    Settings Range;
    Settings Fov;
    Settings Speed;
    Settings AutoAttack;
    Settings HitsMode;
    Settings SmartCrits;
    Settings StopSprint;
    Settings Players;
    Settings Invis;
    Settings Walls;
    Settings Mobs;
    TimerHelper timerHelper = new TimerHelper();

    public AimAssist() {
        super("AimAssist", 0, Module.Category.COMBAT);
        this.OnlyAttackKey = new Settings("OnlyAttackKey", true, (Module)this, () -> this.AutoAttack.bValue || this.AimBot.bValue);
        this.settings.add(this.OnlyAttackKey);
        this.AimBot = new Settings("AimBot", true, (Module)this);
        this.settings.add(this.AimBot);
        this.Range = new Settings("Range", 4.0f, 6.0f, 1.0f, this, () -> this.AutoAttack.bValue || this.AimBot.bValue);
        this.settings.add(this.Range);
        this.Fov = new Settings("Fov", 90.0f, 360.0f, 40.0f, this, () -> this.AimBot.bValue);
        this.settings.add(this.Fov);
        this.Speed = new Settings("Speed", 3.0f, 7.0f, 0.5f, this, () -> this.AimBot.bValue);
        this.settings.add(this.Speed);
        this.AutoAttack = new Settings("AutoAttack", true, (Module)this);
        this.settings.add(this.AutoAttack);
        this.HitsMode = new Settings("HitsMode", "Click", this, new String[]{"Click", "Attack"}, () -> this.AutoAttack.bValue);
        this.settings.add(this.HitsMode);
        this.SmartCrits = new Settings("SmartCrits", true, (Module)this, () -> this.AutoAttack.bValue);
        this.settings.add(this.SmartCrits);
        this.StopSprint = new Settings("StopSprint", true, (Module)this, () -> this.AutoAttack.bValue);
        this.settings.add(this.StopSprint);
        this.Players = new Settings("Players", true, (Module)this, () -> this.AutoAttack.bValue || this.AimBot.bValue);
        this.settings.add(this.Players);
        this.Invis = new Settings("Invis", false, (Module)this, () -> this.AutoAttack.bValue || this.AimBot.bValue);
        this.settings.add(this.Invis);
        this.Walls = new Settings("Walls", false, (Module)this, () -> this.AutoAttack.bValue || this.AimBot.bValue);
        this.settings.add(this.Walls);
        this.Mobs = new Settings("Mobs", false, (Module)this, () -> this.AutoAttack.bValue || this.AimBot.bValue);
        this.settings.add(this.Mobs);
    }

    void hit(EntityLivingBase entityIn, boolean mouse, boolean stopSprint) {
        boolean saveCrit;
        boolean sprint = Minecraft.player.serverSprintState;
        boolean bl = saveCrit = this.canCrits() && sprint && stopSprint;
        if (saveCrit) {
            Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.STOP_SPRINTING));
        }
        if (mouse) {
            mc.clickMouse();
        } else {
            AimAssist.mc.playerController.attackEntity(Minecraft.player, entityIn);
            Minecraft.player.swingArm(EnumHand.MAIN_HAND);
        }
        if (saveCrit) {
            Minecraft.player.serverSprintState = sprint;
        }
    }

    public boolean lowHand() {
        Item item = Minecraft.player.getHeldItemMainhand().getItem();
        return !(item instanceof ItemSword) && !(item instanceof ItemTool);
    }

    public float msCooldown() {
        float handCooled = 5.5f;
        Item item = Minecraft.player.getHeldItemMainhand().getItem();
        if (this.lowHand()) {
            handCooled = 5.0f;
        }
        if (item instanceof ItemSword) {
            float f = handCooled = Wrapper.getPlayer().isPotionActive(Potion.getPotionById(3)) ? 4.5f : 5.1f;
        }
        if (item instanceof ItemAxe) {
            float f = handCooled = Wrapper.getPlayer().isPotionActive(Potion.getPotionById(3)) ? 9.0f : 11.0f;
        }
        if (item instanceof ItemPickaxe) {
            float f = handCooled = Wrapper.getPlayer().isPotionActive(Potion.getPotionById(3)) ? 5.5f : 7.0f;
        }
        if (item == Items.DIAMOND_SHOVEL || item == Items.IRON_SHOVEL || item == Items.GOLDEN_SHOVEL || item == Items.STONE_SHOVEL || item == Items.WOODEN_SHOVEL) {
            float f = handCooled = Wrapper.getPlayer().isPotionActive(Potion.getPotionById(3)) ? 7.0f : 9.0f;
        }
        if (item == Items.DIAMOND_HOE || item == Items.IRON_HOE || item == Items.STONE_HOE) {
            handCooled = 4.5f;
        }
        if (item == Items.GOLDEN_HOE || item == Items.WOODEN_HOE) {
            handCooled = Wrapper.getPlayer().isPotionActive(Potion.getPotionById(3)) ? 7.0f : 8.5f;
        }
        handCooled *= 100.0f;
        return (int)(handCooled += 10.0f);
    }

    boolean hasCooled() {
        return this.timerHelper.hasReached(this.msCooldown()) && Minecraft.player.getCooledAttackStrength(0.0f) != 0.0f;
    }

    public boolean isCritical() {
        double x = Minecraft.player.posX;
        double y = Minecraft.player.posY;
        double z = Minecraft.player.posZ;
        if (!MoveMeHelp.isBlockAboveHead() ? (double)Minecraft.player.fallDistance <= 0.08 : Minecraft.player.isCollidedVertically && !Minecraft.player.onGround && MoveMeHelp.isBlockAboveHead() && (double)Minecraft.player.fallDistance < 0.01) {
            return false;
        }
        if (MoveMeHelp.isBlockAboveHead() && (Minecraft.player.fallDistance != 0.0f || !Minecraft.player.isJumping())) {
            return false;
        }
        if (MoveMeHelp.isBlockAboveHead()) {
            return Minecraft.player.fallDistance != 0.0f || AimAssist.mc.world.getBlockState(new BlockPos(x, y - 0.2001, z)).getBlock() != Blocks.AIR && (!AirJump.get.actived || AimAssist.mc.world.getBlockState(new BlockPos(x, y - 0.2001, z)).getBlock() == Blocks.AIR || !Minecraft.player.isJumping());
        }
        return true;
    }

    public boolean getBlockWithExpand(float expand, double x, double y, double z, Block block) {
        return AimAssist.mc.world.getBlockState(new BlockPos(x, y + 0.03, z)).getBlock() == block || AimAssist.mc.world.getBlockState(new BlockPos(x + (double)expand, y, z + (double)expand)).getBlock() == block || AimAssist.mc.world.getBlockState(new BlockPos(x - (double)expand, y, z - (double)expand)).getBlock() == block || AimAssist.mc.world.getBlockState(new BlockPos(x + (double)expand, y, z - (double)expand)).getBlock() == block || AimAssist.mc.world.getBlockState(new BlockPos(x - (double)expand, y, z + (double)expand)).getBlock() == block || AimAssist.mc.world.getBlockState(new BlockPos(x + (double)expand, y, z)).getBlock() == block || AimAssist.mc.world.getBlockState(new BlockPos(x - (double)expand, y, z)).getBlock() == block || AimAssist.mc.world.getBlockState(new BlockPos(x, y, z + (double)expand)).getBlock() == block || AimAssist.mc.world.getBlockState(new BlockPos(x, y, z - (double)expand)).getBlock() == block;
    }

    public boolean canCrits() {
        double x = Minecraft.player.posX;
        double y = Minecraft.player.posY;
        double z = Minecraft.player.posZ;
        if (Minecraft.player.isInWeb && AimAssist.mc.world.getBlockState(new BlockPos(x, y + 0.1, z)).getBlock() != Blocks.AIR || Minecraft.player.isInWater() || Minecraft.player.isInLava()) {
            return false;
        }
        if (JesusSpeed.isSwimming || Minecraft.player.isElytraFlying()) {
            return false;
        }
        if (Fly.get.actived) {
            return false;
        }
        if (FreeCam.get.actived) {
            return false;
        }
        if (ElytraBoost.get.actived && ElytraBoost.get.Mode.currentMode.equalsIgnoreCase("Vanilla")) {
            return false;
        }
        if (this.getBlockWithExpand(0.3f, x, y + 0.2, z, Blocks.WEB) || this.getBlockWithExpand(0.3f, x, y, z, Blocks.WATER) || this.getBlockWithExpand(0.3f, x, y, z, Blocks.LAVA)) {
            return false;
        }
        if (!Minecraft.player.isJumping() && Minecraft.player.onGround) {
            return false;
        }
        return !Minecraft.player.isOnLadder();
    }

    Entity getMouseOverEntity() {
        EntityLivingBase base = MathUtils.getPointedEntity(new Vector2f(Minecraft.player.rotationYaw, Minecraft.player.rotationPitch), 100.0, 1.0f, false);
        if (base != null && base != Minecraft.player && base.isEntityAlive()) {
            return base;
        }
        return null;
    }

    @Override
    public void onUpdate() {
        target = this.getTarget(this.Range.fValue);
        if (target == null) {
            return;
        }
        if (this.AutoAttack.bValue && (Mouse.isButtonDown(0) || !this.OnlyAttackKey.bValue)) {
            AimAssist.mc.gameSettings.keyBindAttack.pressed = false;
            if (AimAssist.mc.objectMouseOver == null) {
                return;
            }
            if (this.getMouseOverEntity() == target || AimAssist.mc.objectMouseOver.entityHit == target || this.isValidEntity((EntityLivingBase)AimAssist.mc.pointedEntity)) {
                boolean hitting;
                boolean critted = this.isCritical() || !this.canCrits();
                boolean bl = hitting = this.hasCooled() && (critted || !this.SmartCrits.bValue);
                if (hitting) {
                    this.hit(target, this.HitsMode.currentMode.equalsIgnoreCase("Click"), this.StopSprint.bValue);
                    this.timerHelper.reset();
                }
            }
        }
        super.onUpdate();
    }

    @Override
    public void onMovement() {
        TargetStrafe.target = target = this.getTarget(this.Range.fValue);
        if (target == null) {
            return;
        }
        float f = this.faceTarget(target, 360.0f, 360.0f, false)[0];
        float f2 = this.faceTarget(target, 360.0f, 360.0f, false)[1];
        if ((!this.OnlyAttackKey.bValue || Mouse.isButtonDown(0)) && this.getMouseOverEntity() != target && this.AimBot.bValue && this.isInFOV(target, this.Fov.fValue)) {
            if (Minecraft.player.rotationYaw != f) {
                Minecraft.player.rotationYaw = MathUtils.lerp(Minecraft.player.rotationYaw, AimAssist.Rotation(target)[0], 0.05f * this.Speed.fValue);
            }
            if (this.isInFOVPitch(target, this.Fov.fValue)) {
                if (Minecraft.player.rotationPitch != f2) {
                    Minecraft.player.rotationPitch = MathUtils.lerp(Minecraft.player.rotationPitch, AimAssist.Rotation(target)[1], 0.025f * this.Speed.fValue);
                }
                Minecraft.player.rotationPitch = MathUtils.clamp(Minecraft.player.rotationPitch, -90.0f, 90.0f);
            }
        }
    }

    public EntityLivingBase getTarget(float range) {
        EntityLivingBase base = null;
        for (Object o : AimAssist.mc.world.loadedEntityList) {
            EntityLivingBase living;
            Entity entity = (Entity)o;
            if (!(entity instanceof EntityLivingBase) || !this.isValidEntity(living = (EntityLivingBase)entity) || living.getHealth() == 0.0f || !(Minecraft.player.getDistanceToEntity(living) <= range)) continue;
            range = Minecraft.player.getDistanceToEntity(living);
            base = living;
        }
        return base;
    }

    public boolean isValidEntity(EntityLivingBase player) {
        if (player == null || player.isDead) {
            return false;
        }
        boolean players = this.Players.bValue;
        boolean mobs = this.Mobs.bValue;
        boolean walls = this.Walls.bValue;
        if ((player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityBat || player instanceof EntityMagmaCube || player instanceof EntitySlime || player instanceof EntitySquid || player instanceof EntityVillager || player instanceof EntityDragon || player instanceof EntityShulker) && mobs && (Minecraft.player.canEntityBeSeen(player) || walls)) {
            return true;
        }
        if (player instanceof EntityOtherPlayerMP && player != FreeCam.fakePlayer && (Minecraft.player.canEntityBeSeen(player) || walls) && players) {
            for (Friend friend : Client.friendManager.getFriends()) {
                if (!player.getName().equals(friend.getName())) continue;
                return false;
            }
            return true;
        }
        if (player.isInvisible() && !this.Invis.bValue || HitAura.TARGET_ROTS != null) {
            return false;
        }
        return player != Minecraft.player;
    }

    public static float[] Rotation(Entity e) {
        double d = e.posX - Minecraft.player.posX;
        double d1 = e.posZ - Minecraft.player.posZ;
        if (e instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase)e;
        }
        EntityLivingBase entitylivingbase = (EntityLivingBase)e;
        float y = (float)(entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() - (double)(entitylivingbase.getEyeHeight() / 3.0f));
        double lastY = (double)y + (double)0.2f - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        double d2 = MathHelper.sqrt(d * d + d1 * d1);
        float yaw = (float)(Math.atan2(d1, d) * 180.0 / Math.PI - 92.0) + RandomUtils.nextFloat(1.0f, 7.0f);
        float pitch = (float)(-(Math.atan2(lastY, d2) * 210.0 / Math.PI)) + RandomUtils.nextFloat(1.0f, 7.0f);
        yaw = Minecraft.player.rotationYaw + RotationUtil.getSensitivity(MathHelper.wrapDegrees(yaw - Minecraft.player.rotationYaw));
        pitch = Minecraft.player.rotationPitch + RotationUtil.getSensitivity(MathHelper.wrapDegrees(pitch - Minecraft.player.rotationPitch));
        pitch = MathHelper.clamp(pitch, -88.5f, 89.9f);
        return new float[]{yaw, pitch};
    }

    public float[] faceTarget(Entity target, float p_706252, float p_706253, boolean miss) {
        double var7;
        double var4 = target.posX - Minecraft.player.posX;
        double var5 = target.posZ - Minecraft.player.posZ;
        if (target instanceof EntityLivingBase) {
            EntityLivingBase var6 = (EntityLivingBase)target;
            var7 = var6.posY + (double)var6.getEyeHeight() - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        } else {
            var7 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0 - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        }
        double var8 = MathHelper.sqrt(var4 * var4 + var5 * var5);
        float var9 = (float)(Math.atan2(var5, var4) * 180.0 / Math.PI) - 90.0f;
        float var10 = (float)(-(Math.atan2(var7 - (target instanceof EntityPlayer ? 0.25 : 0.0), var8) * 180.0 / Math.PI));
        float f = AimAssist.mc.gameSettings.mouseSensitivity * 0.9f + 0.2f;
        float gcd = f * f * f * 1.2f;
        float pitch = this.updateRotation(Minecraft.player.rotationPitch, var10, p_706253);
        float yaw = this.updateRotation(Minecraft.player.rotationYaw, var9, p_706252);
        yaw -= yaw % gcd;
        pitch -= pitch % gcd;
        return new float[]{yaw, pitch};
    }

    public float updateRotation(float current, float intended, float speed) {
        float f = MathHelper.wrapDegrees(intended - current);
        if (f > speed) {
            f = speed;
        }
        if (f < -speed) {
            f = -speed;
        }
        return current + f;
    }

    private boolean isInFOV(EntityLivingBase entity, double angle) {
        double angleDiff = AimAssist.getAngleDifference(Minecraft.player.rotationYaw, AimAssist.getRotations(entity.posX, entity.posY, entity.posZ)[0]);
        return angleDiff > 0.0 && angleDiff < (angle *= 0.5) || -angle < angleDiff && angleDiff < 0.0;
    }

    private boolean isInFOVPitch(EntityLivingBase entity, double angle) {
        double angleDiff = AimAssist.getAngleDifferencePitch(EventPlayerMotionUpdate.pitch, AimAssist.getRotations(entity.posX, entity.posY, entity.posZ)[1]);
        return angleDiff > 0.0 && angleDiff < angle || -angle < angleDiff && angleDiff < 0.0;
    }

    private static float getAngleDifference(float dir, float yaw) {
        float f = Math.abs(yaw - dir) % 360.0f;
        return f > 180.0f ? 360.0f - f : f;
    }

    private static float getAngleDifferencePitch(float dir, float pitch) {
        float f = Math.abs(pitch - dir) % 90.0f;
        return f > 0.0f ? 90.0f - f : f;
    }

    private static float[] getRotations(double x, double y, double z) {
        double diffX = x - Minecraft.player.posX;
        double diffY = (y + 0.5) / 2.0 - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
        double diffZ = z - Minecraft.player.posZ;
        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI)) + (float)(Minecraft.player.getDistanceToEntity(target) >= 3.0f ? 3 : 1);
        return new float[]{yaw, pitch};
    }
}

