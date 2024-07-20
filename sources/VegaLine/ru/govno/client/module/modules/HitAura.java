/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.vecmath.Vector2f;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Keyboard;
import ru.govno.client.Client;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventAction;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.event.events.EventReceivePacket;
import ru.govno.client.event.events.EventRotationJump;
import ru.govno.client.event.events.EventRotationStrafe;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.AirJump;
import ru.govno.client.module.modules.AntiBot;
import ru.govno.client.module.modules.Blink;
import ru.govno.client.module.modules.Criticals;
import ru.govno.client.module.modules.ElytraBoost;
import ru.govno.client.module.modules.Fly;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.modules.GameSyncTPS;
import ru.govno.client.module.modules.JesusSpeed;
import ru.govno.client.module.modules.MoveHelper;
import ru.govno.client.module.modules.PushAttack;
import ru.govno.client.module.modules.Speed;
import ru.govno.client.module.modules.TPInfluence;
import ru.govno.client.module.modules.TargetStrafe;
import ru.govno.client.module.modules.Timer;
import ru.govno.client.module.modules.WaterSpeed;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Combat.EntityUtil;
import ru.govno.client.utils.Combat.RotationUtil;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.CrystalField;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Movement.MoveMeHelp;
import ru.govno.client.utils.MusicHelper;

public class HitAura
extends Module {
    private final TimerHelper timerHelper = new TimerHelper();
    public static EntityLivingBase TARGET;
    public static EntityLivingBase TARGET_ROTS;
    public static EntityLivingBase PREV_TARGET_ROTS;
    public static HitAura get;
    public Settings FieldOfView;
    public Settings AttackPlayers;
    public Settings AttackMobs;
    public Settings Rotation;
    public Settings ViewLock;
    public Settings LockRotateType;
    public Settings RenderRots;
    public Settings RaytraceRots;
    public Settings Sorting;
    public Settings Cooldown;
    public Settings MultyTargets;
    public Settings IgnoreWalls;
    public Settings WallsBypass;
    public Settings SmartRange;
    public Settings Range;
    public Settings PreRange;
    public Settings RangeMode;
    public Settings RotateMoveSide;
    public Settings CritsHelper;
    public Settings ShieldFixer;
    public Settings TripleHits;
    public Settings AutoSwitch;
    public Settings ShieldBreak;
    public Settings ShBreakType;
    public Settings SprintActions;
    public Settings CPSBypass;
    boolean hitR2Counted;
    int hitCounter;
    public static TimerHelper cooldown;
    public static TimerHelper cooldownAxe;
    public static TimerHelper shiedPressTimeTarget;
    private boolean hasBlocked;
    private boolean toCancelSprint;
    private int staredSlotOfWeapon = 0;
    private boolean postHit;
    private EntityLivingBase postHitTarget;
    private int hurtTimeRule = 10;
    private boolean tpHit;
    public float[] rotationsCombat = new float[]{0.0f, 0.0f};
    public float[] rotationsVisual = this.rotationsCombat;
    public boolean noRotateTick;
    int antiaimSide = 0;
    float preYaw = 0.0f;

    public HitAura() {
        super("HitAura", 0, Module.Category.COMBAT);
        this.FieldOfView = new Settings("FieldOfView", 180.0f, 180.0f, 5.0f, this, () -> this.AttackPlayers.getBool() || this.AttackMobs.getBool());
        this.settings.add(this.FieldOfView);
        this.AttackPlayers = new Settings("AttackPlayers", true, (Module)this);
        this.settings.add(this.AttackPlayers);
        this.AttackMobs = new Settings("AttackMobs", true, (Module)this);
        this.settings.add(this.AttackMobs);
        this.Rotation = new Settings("Rotation", "Matrix", this, new String[]{"None", "Matrix", "Vulcan", "Grim", "AAC&Vulcan"}, () -> this.AttackPlayers.getBool() || this.AttackMobs.getBool());
        this.settings.add(this.Rotation);
        this.ViewLock = new Settings("ViewLock", false, (Module)this, () -> !this.Rotation.currentMode.equalsIgnoreCase("None") && (this.AttackPlayers.getBool() || this.AttackMobs.getBool()));
        this.settings.add(this.ViewLock);
        this.LockRotateType = new Settings("LockRotateType", "VisualRotate", this, new String[]{"VisualRotate", "CombatRotate"}, () -> !this.Rotation.currentMode.equalsIgnoreCase("None") && this.ViewLock.bValue && (this.AttackPlayers.getBool() || this.AttackMobs.getBool()));
        this.settings.add(this.LockRotateType);
        this.RenderRots = new Settings("RenderRots", "Turn360Ap", this, new String[]{"None", "AtRots", "Reverse", "ReverseAp", "Turn360", "Turn360Ap", "Derp"}, () -> !this.Rotation.currentMode.equalsIgnoreCase("None") && (this.AttackPlayers.getBool() || this.AttackMobs.getBool()));
        this.settings.add(this.RenderRots);
        this.RaytraceRots = new Settings("RaytraceRots", false, (Module)this, () -> !this.Rotation.currentMode.equalsIgnoreCase("None") && (this.AttackPlayers.getBool() || this.AttackMobs.getBool()));
        this.settings.add(this.RaytraceRots);
        this.Sorting = new Settings("Sorting", "Distance", this, new String[]{"Distance", "Health", "Armor"}, () -> this.AttackPlayers.getBool() || this.AttackMobs.getBool());
        this.settings.add(this.Sorting);
        this.Cooldown = new Settings("Cooldown", "1.9+", this, new String[]{"1.9+", "1.8.9-"}, () -> this.AttackPlayers.bValue || this.AttackMobs.bValue);
        this.settings.add(this.Cooldown);
        this.MultyTargets = new Settings("MultyTargets", false, (Module)this, () -> this.Cooldown.currentMode.equalsIgnoreCase("1.8.9-") && (this.AttackPlayers.getBool() || this.AttackMobs.getBool()));
        this.settings.add(this.MultyTargets);
        this.IgnoreWalls = new Settings("IgnoreWalls", true, (Module)this, () -> this.AttackPlayers.getBool() || this.AttackMobs.getBool());
        this.settings.add(this.IgnoreWalls);
        this.WallsBypass = new Settings("WallsBypass", true, (Module)this, () -> this.IgnoreWalls.bValue && (this.AttackPlayers.getBool() || this.AttackMobs.getBool()));
        this.settings.add(this.WallsBypass);
        this.SmartRange = new Settings("SmartRange", false, (Module)this, () -> this.AttackPlayers.getBool() || this.AttackMobs.getBool());
        this.settings.add(this.SmartRange);
        this.Range = new Settings("Range", 3.4f, 6.0f, 1.0f, this, () -> !this.SmartRange.bValue && (this.AttackPlayers.getBool() || this.AttackMobs.getBool()));
        this.settings.add(this.Range);
        this.PreRange = new Settings("PreRange", 3.0f, 8.0f, 0.0f, this, () -> !this.SmartRange.bValue && (this.AttackPlayers.getBool() || this.AttackMobs.getBool()));
        this.settings.add(this.PreRange);
        this.RangeMode = new Settings("RangeMode", "Matrix", this, new String[]{"Matrix", "MatrixFullRage", "NCP", "Grim"}, () -> this.SmartRange.bValue && (this.AttackPlayers.getBool() || this.AttackMobs.getBool()));
        this.settings.add(this.RangeMode);
        this.RotateMoveSide = new Settings("RotateMoveSide", false, (Module)this, () -> !this.Rotation.currentMode.equalsIgnoreCase("None") && (this.AttackPlayers.getBool() || this.AttackMobs.getBool()));
        this.settings.add(this.RotateMoveSide);
        this.CritsHelper = new Settings("CritsHelper", "None", this, new String[]{"None", "Matrix", "NCP", "NCP+"}, () -> this.AttackPlayers.getBool() || this.AttackMobs.getBool());
        this.settings.add(this.CritsHelper);
        this.ShieldFixer = new Settings("ShieldFixer", true, (Module)this, () -> this.AttackPlayers.bValue || this.AttackMobs.bValue);
        this.settings.add(this.ShieldFixer);
        this.TripleHits = new Settings("TripleHits", false, (Module)this, () -> this.Cooldown.currentMode.equalsIgnoreCase("1.9+") && (this.AttackPlayers.getBool() || this.AttackMobs.getBool()));
        this.settings.add(this.TripleHits);
        this.AutoSwitch = new Settings("AutoSwitch", true, (Module)this, () -> this.AttackPlayers.getBool() || this.AttackMobs.getBool());
        this.settings.add(this.AutoSwitch);
        this.ShieldBreak = new Settings("ShieldBreak", true, (Module)this, () -> this.AttackPlayers.getBool() || this.AttackMobs.getBool());
        this.settings.add(this.ShieldBreak);
        this.ShBreakType = new Settings("ShBreakType", "FastHit", this, new String[]{"SlotedHit", "FastHit"}, () -> this.ShieldBreak.bValue && (this.AttackPlayers.getBool() || this.AttackMobs.getBool()));
        this.settings.add(this.ShBreakType);
        this.SprintActions = new Settings("SprintActions", "SendStop", this, new String[]{"None", "PreHitStop", "SendStop"}, () -> this.AttackPlayers.getBool() || this.AttackMobs.getBool());
        this.settings.add(this.SprintActions);
        this.CPSBypass = new Settings("CPSBypass", false, (Module)this, () -> this.AttackPlayers.getBool() || this.AttackMobs.getBool());
        this.settings.add(this.CPSBypass);
        get = this;
    }

    public static boolean isInFovOfYaw(float scope, Entity entity) {
        if (scope == 180.0f) {
            return true;
        }
        if (entity == null) {
            return false;
        }
        double diffZ = entity.posZ - Minecraft.player.posZ;
        double diffX = entity.posX - Minecraft.player.posX;
        float yaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
        return RotationUtil.angleDifference(yaw, Minecraft.player.rotationYaw) <= (double)scope;
    }

    public boolean getIsPostHitting() {
        return (this.Rotation.currentMode.equalsIgnoreCase("Grim") || this.Rotation.currentMode.equalsIgnoreCase("AAC&Vulcan")) && PREV_TARGET_ROTS != TARGET_ROTS;
    }

    public boolean onHitEntityClickedByPlayer(EntityLivingBase entityHitted) {
        if (!Panic.stop && get != null && HitAura.get.actived && TARGET_ROTS != null) {
            return this.canAddTarget(entityHitted, this.getAuraRange(entityHitted) + this.getAuraPreRange(), false, this.Rotation.currentMode.equalsIgnoreCase("None"), 10, 180.0f);
        }
        return false;
    }

    private int getWeaponSlot(Entity entity) {
        int bestSlot = -1;
        double maxDamage = 0.0;
        EnumCreatureAttribute creatureAttribute = EnumCreatureAttribute.UNDEFINED;
        if (EntityUtil.isLiving(entity)) {
            EntityLivingBase base = (EntityLivingBase)entity;
            creatureAttribute = base.getCreatureAttribute();
        }
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = Minecraft.player.inventory.getStackInSlot(i);
            double damage = 0.0;
            if (stack.isEmpty()) continue;
            if (stack.getItem() instanceof ItemTool && EntityUtil.getPickaxeAtHotbar() == -1 && EntityUtil.getAxeAtHotbar() == -1 && EntityUtil.getSwordAtHotbar() == -1) {
                damage = (double)((ItemTool)stack.getItem()).getDamageVsEntity() + (double)EnchantmentHelper.getModifierForCreature(stack, creatureAttribute);
            }
            if (stack.getItem() instanceof ItemPickaxe && EntityUtil.getAxeAtHotbar() == -1 && EntityUtil.getSwordAtHotbar() == -1) {
                damage = (double)((ItemPickaxe)stack.getItem()).getDamageVsEntity() + (double)EnchantmentHelper.getModifierForCreature(stack, creatureAttribute);
            }
            if (stack.getItem() instanceof ItemAxe && EntityUtil.getSwordAtHotbar() == -1) {
                damage = (double)((ItemAxe)stack.getItem()).getDamageVsEntity() + (double)EnchantmentHelper.getModifierForCreature(stack, creatureAttribute);
            }
            if (stack.getItem() instanceof ItemSword) {
                damage = (double)((ItemSword)stack.getItem()).getDamageTotal() + (double)EnchantmentHelper.getModifierForCreature(stack, creatureAttribute);
            }
            if (!(damage > maxDamage)) continue;
            maxDamage = damage;
            bestSlot = i;
        }
        return bestSlot;
    }

    private boolean isAutoSwitch(boolean isOnDisableTrigger) {
        boolean canSlotCap = TARGET != null && this.getWeaponSlot(TARGET) > -1 && this.getWeaponSlot(TARGET) < 9 && this.actived;
        return this.AutoSwitch.bValue && (isOnDisableTrigger && !canSlotCap || canSlotCap && !isOnDisableTrigger);
    }

    private void setWeaponSlot() {
        boolean flag1 = this.isAutoSwitch(true);
        boolean flag2 = this.isAutoSwitch(false);
        boolean reversed = flag1;
        if (!flag1 && !flag2) {
            return;
        }
        if (reversed) {
            if (this.staredSlotOfWeapon != -1) {
                if (Minecraft.player.inventory.currentItem != this.staredSlotOfWeapon) {
                    Minecraft.player.inventory.currentItem = this.staredSlotOfWeapon;
                    HitAura.mc.playerController.syncCurrentPlayItem();
                }
                this.staredSlotOfWeapon = -1;
            }
            return;
        }
        int weapon = this.getWeaponSlot(TARGET_ROTS);
        if (Minecraft.player.inventory.currentItem != weapon) {
            if (this.staredSlotOfWeapon == -1) {
                this.staredSlotOfWeapon = Minecraft.player.inventory.currentItem;
            }
            Minecraft.player.inventory.currentItem = weapon;
            HitAura.mc.playerController.syncCurrentPlayItem();
            cooldown.reset();
        }
    }

    private boolean canWallsTurnAwayCombatRotate(EntityLivingBase targetEntity) {
        double cooledDiff = MathUtils.getDifferenceOf(cooldown.getTime(), this.msCooldown());
        return this.WallsBypass.bValue && !HitAura.seenTargetEntity(false, targetEntity, false) && cooledDiff < 101.0 && targetEntity.entityBoxVec3dsAlternate(targetEntity, true).isEmpty();
    }

    void critsHelper() {
        String helpMode = this.CritsHelper.currentMode;
        if (helpMode.equalsIgnoreCase("None")) {
            return;
        }
        AxisAlignedBB box = Minecraft.player.boundingBox;
        switch (helpMode) {
            case "Matrix": {
                if (Minecraft.player.isJumping() && Entity.Getmotiony < -0.1 && (double)Minecraft.player.fallDistance > 0.5 && MoveMeHelp.getSpeed() < 0.12 && !TargetStrafe.goStrafe() && !this.hitR2Counted) {
                    boolean ext05TRUE = !HitAura.mc.world.getCollisionBoxes(Minecraft.player, box.offsetMinDown(0.5)).isEmpty();
                    boolean ext02FAlSE = HitAura.mc.world.getCollisionBoxes(Minecraft.player, box.offsetMinDown(0.2)).isEmpty();
                    if (!ext05TRUE || !ext02FAlSE) break;
                    Entity.motiony = -1.0;
                    break;
                }
                if (Minecraft.player.onGround || MoveMeHelp.isBlockAboveHeadSolo() || !(Minecraft.player.motionY > 0.06) && !((double)Minecraft.player.fallDistance > 0.1)) break;
                Minecraft.player.motionY = Minecraft.player.motionY - (Minecraft.player.motionY < 0.0 ? 0.0032 : 0.0011);
                if (!(Minecraft.player.fallDistance > 0.75f) || MoveMeHelp.getSpeed() == 0.0 || !(MoveMeHelp.getSpeed() < 0.23) || HitAura.mc.world.getCollisionBoxes(Minecraft.player, box.offsetMinDown(0.3)).isEmpty()) break;
                Minecraft.player.motionY -= 0.2;
                break;
            }
            case "NCP": {
                if (!Minecraft.player.isJumping() || Minecraft.player.fallDistance == 0.0f) break;
                boolean ext11TRUE = !HitAura.mc.world.getCollisionBoxes(Minecraft.player, box.offsetMinDown(1.1f)).isEmpty();
                boolean ext03FAlSE = HitAura.mc.world.getCollisionBoxes(Minecraft.player, box.offsetMinDown(0.3)).isEmpty();
                if (!ext11TRUE || !ext03FAlSE) break;
                Minecraft.player.motionY -= 0.078;
                break;
            }
            case "NCP+": {
                if (!Minecraft.player.isJumping() || !((double)Minecraft.player.fallDistance > 0.8)) break;
                boolean ext05TRUE = !HitAura.mc.world.getCollisionBoxes(Minecraft.player, box.offsetMinDown(0.3)).isEmpty();
                boolean ext03FAlSE = HitAura.mc.world.getCollisionBoxes(Minecraft.player, box.offsetMinDown(0.1)).isEmpty();
                if (!ext05TRUE || !ext03FAlSE) break;
                Timer.forceTimer(2.0f);
            }
        }
    }

    @Override
    public String getDisplayName() {
        return this.getName() + this.getSuff() + String.format("%.1f", this.getDistanceToTarget(TARGET, this.Rotation.currentMode.equalsIgnoreCase("None"))) + "/" + String.format("%.1f", Float.valueOf(this.getAuraRange(TARGET)));
    }

    private float[] rotations(EntityLivingBase entityTarget, boolean visual, String mode) {
        boolean turnAwayCombat = this.canWallsTurnAwayCombatRotate(entityTarget);
        return mode.equalsIgnoreCase("Matrix") || mode.equalsIgnoreCase("Grim") || mode.equalsIgnoreCase("AAC&Vulcan") || mode.equalsIgnoreCase("None") ? RotationUtil.getRots(entityTarget, visual && !mode.equalsIgnoreCase("Grim"), turnAwayCombat) : RotationUtil.getRots2(entityTarget, visual, turnAwayCombat);
    }

    public double getDistanceToTarget(EntityLivingBase target, boolean nonRotate) {
        if (target == null) {
            return 0.0;
        }
        if (Minecraft.player != null && target != null) {
            double sqrtDST;
            if (FreeCam.get.actived && FreeCam.fakePlayer != null) {
                return FreeCam.fakePlayer.getDistanceToEntity(target);
            }
            AxisAlignedBB aabb = target.getEntityBoundingBox();
            double d = sqrtDST = aabb == null ? (double)HitAura.getMe().getDistanceToEntity(target) : HitAura.getMe().getDistance(aabb.minX + (aabb.maxX - aabb.minX) / 2.0, aabb.minY, aabb.minZ + (aabb.maxZ - aabb.minZ) / 2.0);
            if (TPInfluence.get.forHitAuraRule(target)) {
                this.tpHit = true;
                return 0.001;
            }
            if (target == TARGET_ROTS) {
                float[] rot = RotationUtil.getRots(target, false, false);
                if (sqrtDST <= 4.1 && MathUtils.getPointedEntity(new Vector2f(rot[0], rot[1]), 180.0, 1.0f, false) != null) {
                    sqrtDST = Minecraft.player.getSmartDistanceToAABB(rot, target);
                }
            }
            return sqrtDST;
        }
        return 0.0;
    }

    public boolean canAddTarget(Entity entity, float range, boolean walls, boolean nonRotate, int hurtTimeRule, float scope) {
        if (entity != null && entity instanceof EntityLivingBase) {
            EntityLivingBase base = (EntityLivingBase)entity;
            if (!(base.hurtTime > hurtTimeRule || base.getEntityId() == 462462999 || Blink.isFakeEntity(base) || base instanceof EntityArmorStand || base instanceof EntityPlayerSP || base.isDead || base.getHealth() == 0.0f || Client.friendManager.isFriend(base.getName()) || !HitAura.isInFovOfYaw(scope, base))) {
                double distance = this.getDistanceToTarget(base, nonRotate);
                return distance <= (double)range && HitAura.seenTargetEntity(walls, base, distance > 8.0);
            }
        }
        return false;
    }

    private List<EntityLivingBase> getEntities(boolean players, boolean mobs, float range, boolean walls, boolean nonRotate, int hurtTimeRule, float scope) {
        return HitAura.mc.world.getLoadedEntityList().stream().filter(Objects::nonNull).map(Entity::getLivingBaseOf).filter(Objects::nonNull).filter(livingBase -> this.canAddTarget((Entity)livingBase, range, walls, nonRotate, hurtTimeRule, scope)).filter(livingBase -> players && livingBase instanceof EntityOtherPlayerMP || mobs && !(livingBase instanceof EntityPlayer)).filter(current -> !Client.friendManager.isFriend(current.getName()) && !AntiBot.entityIsBotAdded(current)).filter(base -> base.ticksExisted > 10).collect(Collectors.toList());
    }

    private float getEntityValueToSort(EntityLivingBase baseIn, String mode, boolean nonRotate) {
        if (baseIn != null) {
            switch (mode) {
                case "Distance": {
                    return (float)this.getDistanceToTarget(baseIn, nonRotate);
                }
                case "Health": {
                    return baseIn.getHealth();
                }
                case "Armor": {
                    return baseIn.getTotalArmorValue();
                }
            }
        }
        return 0.0f;
    }

    private List<EntityLivingBase> updateTargets(boolean players, boolean mobs, float range, boolean walls, String sort, boolean nonRotate, int hurtTimeRule, float scope) {
        List<EntityLivingBase> bases = this.getEntities(players, mobs, range, walls, nonRotate, hurtTimeRule, scope);
        if (bases.size() >= 2) {
            bases.sort(Comparator.comparingDouble(base -> base == null ? 0.0 : (double)this.getEntityValueToSort((EntityLivingBase)base, sort, nonRotate)));
        }
        return bases;
    }

    private boolean canRotate() {
        String mode = this.Rotation.currentMode;
        return TARGET_ROTS != null && (!mode.equalsIgnoreCase("None") || this.canWallsTurnAwayCombatRotate(TARGET_ROTS) && this.rotateCastedTo(TARGET_ROTS, 100.0f, true, new Vector2f(Minecraft.player.rotationYaw, Minecraft.player.rotationPitch)) && MathUtils.getPointedEntity(new Vector2f(Minecraft.player.rotationYaw, Minecraft.player.rotationPitch), 100.0, 1.0f, false) == TARGET_ROTS) && HitAura.getMe() == Minecraft.player && (!mode.equalsIgnoreCase("Grim") && !mode.equalsIgnoreCase("AAC&Vulcan") || cooldown.hasReached(MathUtils.clamp(this.msCooldown() - 50.0f, 0.0f, 5000.0f)) && (mode.equalsIgnoreCase("AAC&Vulcan") || this.noRotateTick || this.isCritical(true, this.getAuraUpdater()) || !this.canCrits(true))) && (!mode.equalsIgnoreCase("Grim") || HitAura.mc.pointedEntity != TARGET_ROTS && TARGET != null);
    }

    public float getAuraRange(EntityLivingBase entityIn) {
        if (!this.tpHit && this.SmartRange.bValue) {
            String rangeMode = this.RangeMode.currentMode;
            float range = 3.0f;
            if (rangeMode.equalsIgnoreCase("Matrix")) {
                range = 3.025f;
                if ((double)Minecraft.player.fallDistance > 3.8 && Minecraft.player.motionY < 0.7) {
                    range = 3.15f;
                }
                if (Fly.get.actived || MoveMeHelp.getSpeed() > (double)1.1f) {
                    range = 3.1f;
                }
                if (ElytraBoost.get.actived && Minecraft.player.isJumping() && ElytraBoost.get.Mode.currentMode.equalsIgnoreCase("MatrixSpeed2")) {
                    range = 3.1f;
                }
                if (TargetStrafe.goStrafe()) {
                    range = 3.25f;
                }
                if (entityIn != null && entityIn.getHealth() < 3.0f) {
                    range = 3.3f;
                }
                if (Minecraft.player.isInWater() || Minecraft.player.isInLava()) {
                    range = 2.8f;
                }
                if (Minecraft.player.isElytraFlying() && Minecraft.player.getTicksElytraFlying() > 1) {
                    range = 3.0f;
                }
                if (JesusSpeed.isJesused) {
                    range = 3.05f;
                }
            } else if (rangeMode.equalsIgnoreCase("MatrixFullRage")) {
                range = 5.7f;
                if (JesusSpeed.isJesused) {
                    range = 5.65f;
                }
                if (Minecraft.player.isInWater() || Minecraft.player.isInLava()) {
                    range = 5.2f;
                }
                if (TargetStrafe.goStrafe()) {
                    range = 5.5f;
                }
                if (ElytraBoost.get.actived && MoveMeHelp.isMoving() && ElytraBoost.canElytra()) {
                    range = Minecraft.player.isElytraFlying() ? 4.8f : 4.95f;
                }
            } else if (rangeMode.equalsIgnoreCase("NCP")) {
                range = 4.0f;
                if (Minecraft.player.isElytraFlying() && Minecraft.player.getTicksElytraFlying() > 1) {
                    range = 4.1f;
                }
                if (TargetStrafe.goStrafe()) {
                    range = 4.0f;
                }
                if (entityIn != null && entityIn.getHealth() < 6.0f) {
                    range = 4.05f;
                }
            } else if (rangeMode.equalsIgnoreCase("Grim")) {
                range = 3.1f;
                if (Minecraft.player.isElytraFlying() && Minecraft.player.getTicksElytraFlying() > 1) {
                    range = 3.3f;
                }
                if (entityIn != null && entityIn.getHealth() < 6.0f) {
                    range = 3.5f;
                }
            }
            return range;
        }
        return this.tpHit ? 120.0f : this.Range.fValue;
    }

    public float getAuraPreRange() {
        float pr;
        float preRange = pr = this.PreRange.fValue;
        if (!this.tpHit && this.SmartRange.bValue) {
            String rangeMode = this.RangeMode.currentMode;
            if (rangeMode.equalsIgnoreCase("Matrix") || rangeMode.equalsIgnoreCase("MatrixFullRage")) {
                preRange = 2.75f;
                if (Minecraft.player.isElytraFlying() && Minecraft.player.getTicksElytraFlying() > 1) {
                    preRange = 4.2f;
                }
                if (Minecraft.player.isInWater() || Minecraft.player.isInLava()) {
                    preRange = 1.4f;
                }
                if (TargetStrafe.goStrafe()) {
                    preRange = 2.1f;
                }
                if (ElytraBoost.get.actived && Minecraft.player.isJumping() && ElytraBoost.get.Mode.currentMode.equalsIgnoreCase("MatrixSpeed2")) {
                    preRange = 3.35f;
                }
                if (JesusSpeed.isJesused) {
                    preRange = 9.0f;
                }
            } else if (rangeMode.equalsIgnoreCase("NCP")) {
                preRange = 1.5f;
            } else if (rangeMode.equalsIgnoreCase("Grim")) {
                preRange = 0.2f;
                if (Minecraft.player.isElytraFlying() && Minecraft.player.getTicksElytraFlying() > 1) {
                    preRange = 3.0f;
                }
            }
        }
        return this.tpHit ? 0.0f : (pr > preRange ? pr : preRange);
    }

    static EntityPlayer getMe() {
        return FreeCam.fakePlayer != null ? FreeCam.fakePlayer : Minecraft.player;
    }

    static boolean lowHand() {
        Item item = Minecraft.player.getHeldItemMainhand().getItem();
        boolean lowHand = !(item instanceof ItemSword) && !(item instanceof ItemTool);
        return lowHand;
    }

    public float msCooldown() {
        float hc = 5.5f;
        if (this.Cooldown.currentMode.equalsIgnoreCase("1.9+")) {
            int haste = Minecraft.player.isPotionActive(Potion.getPotionById(3)) ? Minecraft.player.getActivePotionEffect(Potion.getPotionById(3)).getAmplifier() : -1;
            Item item = Minecraft.player.getHeldItemMainhand().getItem();
            if (HitAura.lowHand()) {
                hc = 4.5f;
            } else if (item instanceof ItemSword) {
                hc = haste >= 0 ? 4.5f : 5.0f;
            } else if (item instanceof ItemAxe) {
                hc = haste >= 0 ? 8.0f : 8.3f;
            } else if (item instanceof ItemPickaxe) {
                hc = haste >= 0 ? 5.5f : 7.0f;
            } else if (item == Items.DIAMOND_SHOVEL || item == Items.IRON_SHOVEL || item == Items.GOLDEN_SHOVEL || item == Items.STONE_SHOVEL || item == Items.WOODEN_SHOVEL) {
                hc = haste >= 0 ? 6.5f : 9.0f;
            } else if (item == Items.DIAMOND_HOE || item == Items.IRON_HOE || item == Items.STONE_HOE) {
                hc = 4.5f;
            } else if (item == Items.GOLDEN_HOE || item == Items.WOODEN_HOE) {
                hc = haste >= 0 ? 6.5f : 8.5f;
            }
            hc *= 105.0f;
            hc = (float)((double)hc / GameSyncTPS.getGameConpense(1.0, GameSyncTPS.instance.SyncPercent.fValue));
        } else {
            hc = (float)(50.0 + (this.CPSBypass.bValue ? 75.0 * Math.random() : 0.0));
        }
        return hc;
    }

    boolean isCritical(boolean critsSort, String updater) {
        double x = Minecraft.player.posX;
        double y = Minecraft.player.posY;
        double z = Minecraft.player.posZ;
        if (!MoveMeHelp.isBlockAboveHead()) {
            if (updater.equalsIgnoreCase("RenderUpdate") && !(Minecraft.player.lastTickPosY - Minecraft.player.posY > 0.1) && !((double)Minecraft.player.fallDistance > 0.1) && critsSort) {
                critsSort = false;
            }
            if (updater.equalsIgnoreCase("Default") && !(Minecraft.player.lastTickPosY - Minecraft.player.posY > 0.1) && !((double)Minecraft.player.fallDistance > 0.1) && critsSort) {
                critsSort = false;
            }
            if (updater.equalsIgnoreCase("MoveUpdate") && !(Minecraft.player.fallDistance > 0.0f) && !(Minecraft.player.posY - Minecraft.player.lastTickPosY < 0.0) && critsSort) {
                critsSort = false;
            }
        } else if (Minecraft.player.isCollidedVertically && !Minecraft.player.onGround && MoveMeHelp.isBlockAboveHead() && (double)Minecraft.player.fallDistance < 0.01 && critsSort) {
            critsSort = false;
        }
        if (MoveMeHelp.isBlockAboveHead() && (updater.equalsIgnoreCase("Default") ? Minecraft.player.motionY < 0.0 : Minecraft.player.isCollidedVertically && Minecraft.player.onGround || Entity.Getmotiony > 0.0) && critsSort) {
            critsSort = false;
        }
        if (MoveMeHelp.isBlockAboveHead() && Minecraft.player.fallDistance == 0.0f && critsSort && (HitAura.mc.world.getBlockState(new BlockPos(x, y - 0.2001, z)).getBlock() == Blocks.AIR || AirJump.get.actived && HitAura.mc.world.getBlockState(new BlockPos(x, y - 0.2001, z)).getBlock() != Blocks.AIR && Minecraft.player.isJumping())) {
            critsSort = false;
        }
        return critsSort;
    }

    boolean confirmCritsInWater(double x, double y, double z) {
        boolean confirm = false;
        if (WaterSpeed.get.actived && (WaterSpeed.get.Mode.currentMode.equalsIgnoreCase("Matrix") || WaterSpeed.get.Mode.currentMode.equalsIgnoreCase("Vulcan&NCP"))) {
            if (Entity.Getmotiony > 0.0 && HitAura.mc.world.getBlockState(new BlockPos(x, y - 0.3, z)).getBlock() == Blocks.WATER && !(HitAura.mc.world.getBlockState(new BlockPos(x, y - 1.4, z)) instanceof BlockLiquid) && HitAura.mc.world.getBlockState(new BlockPos(x, y + 1.0, z)).getBlock() == Blocks.AIR || Speed.posBlock(x, y - 1.0, z) && HitAura.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.WATER) {
                confirm = true;
            }
            if (HitAura.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.WATER && HitAura.mc.world.getBlockState(new BlockPos(x, y + 0.4, z)).getBlock() == Blocks.AIR) {
                confirm = true;
            }
        }
        if (HitAura.mc.world.getBlockState(new BlockPos(x, y + 0.3, z)).getBlock() == Blocks.WATER && HitAura.mc.world.getBlockState(new BlockPos(x, y + 0.9, z)).getBlock() == Blocks.AIR) {
            confirm = true;
        }
        return confirm;
    }

    boolean canCrits(boolean onlycritycals) {
        float ext;
        double x = Minecraft.player.posX;
        double y = Minecraft.player.posY;
        double z = Minecraft.player.posZ;
        float f = ext = Minecraft.player.isJumping() ? 0.04f : 0.0f;
        if (ElytraBoost.get.actived && ElytraBoost.get.Mode.currentMode.equalsIgnoreCase("NcpFly") && ElytraBoost.canElytra()) {
            onlycritycals = false;
        }
        if (Criticals.get.actived && !Criticals.get.currentMode("Mode").equalsIgnoreCase("VanillaHop") && !Minecraft.player.isJumping() && Minecraft.player.onGround) {
            onlycritycals = false;
        }
        if (Minecraft.player.isInWeb && HitAura.mc.world.getBlockState(new BlockPos(x, y + 0.01, z)).getBlock() != Blocks.AIR || Minecraft.player.isInWater() && !this.confirmCritsInWater(x, y, z) || Minecraft.player.isInLava()) {
            onlycritycals = false;
        }
        if (JesusSpeed.isSwimming || Minecraft.player.isElytraFlying() || JesusSpeed.isJesused) {
            onlycritycals = false;
        }
        if (Fly.get.actived) {
            onlycritycals = false;
        }
        if (FreeCam.get.actived) {
            onlycritycals = false;
        }
        if (Minecraft.player != null && Minecraft.player.isPotionActive(Potion.getPotionById(25))) {
            onlycritycals = false;
        }
        if (ElytraBoost.get.actived && ElytraBoost.get.Mode.currentMode.equalsIgnoreCase("Vanilla")) {
            onlycritycals = false;
        }
        if ((MoveHelper.getBlockWithExpand(Minecraft.player.width / 2.0f, x, y + (double)ext, z, Blocks.WEB) || MoveHelper.getBlockWithExpand(Minecraft.player.width / 2.0f, x, y + (double)ext, z, Blocks.WATER) || MoveHelper.getBlockWithExpand(Minecraft.player.width / 2.0f, x, y + (double)ext, z, Blocks.LAVA)) && !this.confirmCritsInWater(x, y, z)) {
            onlycritycals = false;
        }
        if (!Minecraft.player.isJumping() && Minecraft.player.onGround && (!Criticals.get.actived || Criticals.get.currentMode("Mode").equalsIgnoreCase("VanillaHop"))) {
            onlycritycals = false;
        }
        if (this.Cooldown.currentMode.equalsIgnoreCase("1.8.9-")) {
            onlycritycals = false;
        }
        if (Minecraft.player.isOnLadder() || Minecraft.player.isElytraFlying() || Minecraft.player.capabilities.isFlying || Minecraft.player.isRiding() || Minecraft.player.isSpectator()) {
            onlycritycals = false;
        }
        return onlycritycals;
    }

    private boolean stopOnEating() {
        return !PushAttack.get.actived && Minecraft.player.isEating();
    }

    private boolean stopOnCAura() {
        return CrystalField.get.actived && !CrystalField.getTargets().isEmpty() && CrystalField.get.AuraPause.bValue && (CrystalField.crystal != null || CrystalField.forCrystalPos != null || CrystalField.forObsidianPos != null);
    }

    @EventTarget
    public void forSprint(EventAction event) {
        if (!this.toCancelSprint) {
            return;
        }
        this.toCancelSprint = false;
        event.setSprintState(false);
    }

    private void updateHits(EntityLivingBase target) {
        ItemAxe AXE;
        EntityOtherPlayerMP MP;
        Item item;
        if (Minecraft.player.isBlocking() && Minecraft.player.getItemInUseMaxCount() > 2 && target instanceof EntityOtherPlayerMP && (item = (MP = (EntityOtherPlayerMP)target).getHeldItemMainhand().getItem()) instanceof ItemAxe && (AXE = (ItemAxe)item) != Items.WOODEN_AXE) {
            EnumHand activedHand = Minecraft.player.getActiveHand();
            HitAura.mc.playerController.onStoppedUsingItem(Minecraft.player);
            if (activedHand != null) {
                Minecraft.player.setActiveHand(activedHand);
            }
        }
        if (this.stopOnEating() || this.stopOnCAura()) {
            return;
        }
        if (this.RaytraceRots.bValue && !this.Rotation.currentMode.equalsIgnoreCase("None") && !this.rotateCastedTo(target, this.getAuraRange(target) + this.getAuraPreRange(), this.WallsBypass.bValue, this.getRotateOfFloats(this.rotationsCombat))) {
            return;
        }
        if (this.ShieldBreak.bValue && this.ShBreakType.currentMode.equalsIgnoreCase("SlotedHit")) {
            EntityOtherPlayerMP mp;
            int axe = this.getAxe(true);
            if (axe != -1 && target instanceof EntityOtherPlayerMP && (mp = (EntityOtherPlayerMP)target).isBlocking()) {
                this.hasBlocked = shiedPressTimeTarget.hasReached(50.0);
                if (axe != Minecraft.player.inventory.currentItem) {
                    this.staredSlotOfWeapon = Minecraft.player.inventory.currentItem;
                    Minecraft.player.inventory.currentItem = axe;
                    HitAura.mc.playerController.syncCurrentPlayItem();
                    cooldownAxe.reset();
                } else if (cooldownAxe.hasReached(150.0) && shiedPressTimeTarget.hasReached(300.0)) {
                    this.attack(target, false);
                    cooldownAxe.reset();
                }
                if (this.hasBlocked) {
                    return;
                }
            } else if (this.hasBlocked) {
                if (axe != -1) {
                    shiedPressTimeTarget.reset();
                    if (Minecraft.player.inventory.currentItem == axe && Minecraft.player.inventory.currentItem != this.staredSlotOfWeapon) {
                        Minecraft.player.inventory.currentItem = this.staredSlotOfWeapon;
                        HitAura.mc.playerController.syncCurrentPlayItem();
                    }
                    cooldown.reset();
                }
                this.hasBlocked = false;
            }
        }
        this.updatePostHitsMoment();
        float msCooldown = this.msCooldown();
        if (!this.toCancelSprint && this.SprintActions.currentMode.equalsIgnoreCase("PreHitStop") && cooldown.hasReached(MathUtils.clamp(msCooldown - 50.0f, 0.0f, 5000.0f)) && !cooldown.hasReached(MathUtils.clamp(msCooldown, 0.0f, 5000.0f)) && this.isCritical(true, this.getAuraUpdater())) {
            this.toCancelSprint = true;
        }
        if (!cooldown.hasReached(msCooldown) || !this.isCritical(true, this.getAuraUpdater()) && this.canCrits(true)) {
            return;
        }
        if (this.hitR2Counted) {
            this.hitR2Counted = false;
            return;
        }
        this.getAuraHit(target);
        cooldown.reset();
    }

    private int getAxe(boolean onlyHotbar) {
        for (int i = 0; i < (onlyHotbar ? 8 : 44); ++i) {
            ItemStack itemStack = Minecraft.player.inventory.getStackInSlot(i);
            if (!(itemStack.getItem() instanceof ItemAxe)) continue;
            return i;
        }
        return -1;
    }

    private void attack(EntityLivingBase target, boolean isNormalHit) {
        for (int i = 0; i < (isNormalHit && this.TripleHits.bValue && this.Cooldown.currentMode.equalsIgnoreCase("1.9+") ? 2 : 1); ++i) {
            HitAura.mc.playerController.attackEntity(Minecraft.player, target);
            Minecraft.player.swingArm();
        }
        this.antiaimSide = this.antiaimSide == 0 ? 1 : -this.antiaimSide;
        ++this.hitCounter;
        this.hitR2Counted = !this.hitR2Counted && this.CPSBypass.bValue && this.hitCounter % 4 == 0 || this.Rotation.currentMode.equalsIgnoreCase("AAC&Vulcan");
    }

    private Vector2f getRotateOfFloats(float[] rotate) {
        return new Vector2f(rotate[0], rotate[1]);
    }

    private boolean rotateCastedTo(EntityLivingBase base, float range, boolean onlyNoWalls, Vector2f rotate) {
        return base != null && (MathUtils.getPointedEntityAt(FreeCam.get.actived && FreeCam.fakePlayer != null ? FreeCam.fakePlayer : Minecraft.player, rotate, range, 1.0f, false) != null || !onlyNoWalls || !HitAura.seenTargetEntity(false, base, true));
    }

    private void preAttack(EntityLivingBase target) {
        if (this.tpHit) {
            TPInfluence.get.hitAuraTPPre(target);
        }
        if (!(!Minecraft.player.serverSprintState || this.SprintActions.currentMode.equalsIgnoreCase("None") || this.SprintActions.currentMode.equalsIgnoreCase("SendStop") && Minecraft.player.onGround && Minecraft.player.isCollidedVertically)) {
            Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.STOP_SPRINTING));
            Minecraft.player.serverSprintState = false;
            Minecraft.player.setSprinting(false);
        }
        if (this.ShieldFixer.bValue && Minecraft.player.getActiveItemStack() != null && Minecraft.player.getActiveItemStack().getItem() instanceof ItemShield) {
            Minecraft.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, target.getPosition(), target.getHorizontalFacing()));
            HitAura.mc.playerController.processRightClick(Minecraft.player, HitAura.mc.world, Minecraft.player.getActiveHand());
        }
    }

    private void postAttack(EntityLivingBase target) {
        EnumHand toActive;
        EntityOtherPlayerMP mp;
        int axe;
        if (HitAura.mc.gameSettings.keyBindForward.isKeyDown() && !Minecraft.player.isSprinting() && !Minecraft.player.serverSprintState && Minecraft.player.isHandActive()) {
            Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_SPRINTING));
            Minecraft.player.setSprinting(true);
        }
        if (this.ShieldBreak.bValue && this.ShBreakType.currentMode.equalsIgnoreCase("FastHit") && (axe = this.getAxe(false)) != -1 && target instanceof EntityOtherPlayerMP && (mp = (EntityOtherPlayerMP)target).isBlocking() && axe != Minecraft.player.inventory.currentItem) {
            if (axe < 9) {
                Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(axe));
                this.attack(target, false);
                Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(Minecraft.player.inventory.currentItem));
            } else {
                ItemStack stack = Minecraft.player.getHeldItemMainhand();
                HitAura.mc.playerController.windowClick(0, axe, Minecraft.player.inventory.currentItem, ClickType.SWAP, Minecraft.player);
                this.attack(target, false);
                HitAura.mc.playerController.windowClick(0, axe, Minecraft.player.inventory.currentItem, ClickType.SWAP, Minecraft.player);
                Minecraft.player.setHeldItem(EnumHand.MAIN_HAND, stack);
            }
        }
        if (this.tpHit) {
            TPInfluence.get.hitAuraTPPost(target);
        }
        if (this.ShieldFixer.bValue && Minecraft.player.getActiveItemStack() != null && Minecraft.player.getActiveItemStack().getItem() instanceof ItemShield && (toActive = Minecraft.player.getActiveHand()) != null) {
            Minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItem(Minecraft.player.getActiveHand()));
        }
        this.noRotateTick = this.rotateCastedTo(target, this.getAuraRange(TARGET_ROTS), this.WallsBypass.bValue, this.getRotateOfFloats(this.rotationsCombat)) || HitAura.getMe() != Minecraft.player;
    }

    private String getAuraUpdater() {
        double x = Minecraft.player.posX;
        double y = Minecraft.player.posY;
        double z = Minecraft.player.posZ;
        if (this.hitR2Counted) {
            return "Default";
        }
        if (JesusSpeed.isJesused || HitAura.mc.world != null && this.confirmCritsInWater(x, y, z) || MoveMeHelp.isBlockAboveHead()) {
            return "MoveUpdate";
        }
        if (MoveHelper.getBlockWithExpand(Minecraft.player.width / 2.0f, x, y - (double)0.05f, z, Blocks.WEB) && !Minecraft.player.isInWeb) {
            return "RenderUpdate";
        }
        if (MoveMeHelp.getCuttingSpeed() > 5.0 && !MoveMeHelp.isBlockAboveHead() && Minecraft.getDebugFPS() >= 160) {
            return "RenderUpdate";
        }
        if (Minecraft.player.isInWater() || Minecraft.player.isInLava() || Minecraft.player.isInWeb || Minecraft.player.isEntityInsideOpaqueBlock) {
            return "Default";
        }
        return "MoveUpdate";
    }

    private void getAuraHit(EntityLivingBase target) {
        if (this.getIsPostHitting()) {
            this.postHit = true;
            this.postHitTarget = target;
            return;
        }
        this.preAttack(target);
        this.attack(target, true);
        this.postAttack(target);
    }

    private void updatePostHitsMoment() {
        if (this.postHit) {
            if (this.postHitTarget != null && HitAura.mc.world.getLoadedEntityList().stream().anyMatch(e -> e == this.postHitTarget)) {
                this.preAttack(this.postHitTarget);
                this.attack(this.postHitTarget, true);
                this.postAttack(this.postHitTarget);
                this.postHitTarget = null;
            }
            this.postHit = false;
        }
    }

    private void playKillSound(String soundMode) {
        if (soundMode.equalsIgnoreCase("None")) {
            return;
        }
        MusicHelper.playSound(soundMode.toLowerCase() + ".wav");
    }

    private boolean hasPlayersInRange(float inRange, boolean playersWallsCheck, boolean nonRotate, int hurtTimeRule, float scope) {
        return HitAura.mc.world.playerEntities.stream().filter(player -> this.canAddTarget((Entity)player, inRange, playersWallsCheck, nonRotate, hurtTimeRule, scope)).filter(player -> !Client.friendManager.isFriend(player.getName())).filter(player -> this.getDistanceToTarget((EntityLivingBase)player, nonRotate) <= (double)inRange).findFirst().orElse(null) != null;
    }

    private void updateAura(String updater) {
        boolean canTarget;
        boolean is1_8_9MultyTargets;
        float range = this.getAuraRange(TARGET_ROTS);
        float rangepre = range + this.getAuraPreRange();
        float fov = this.FieldOfView.fValue;
        boolean walls = this.IgnoreWalls.bValue;
        boolean nonRotate = this.Rotation.currentMode.equalsIgnoreCase("None");
        boolean players = this.AttackPlayers.bValue;
        boolean mobs = this.AttackMobs.bValue && (!this.hasPlayersInRange(range, walls, nonRotate, 10, fov) || !players);
        String sort = this.Sorting.currentMode;
        List<EntityLivingBase> CURRENT_BASES = this.updateTargets(players, mobs, rangepre, walls, sort, nonRotate, this.hurtTimeRule, fov);
        boolean bl = is1_8_9MultyTargets = this.Cooldown.currentMode.equalsIgnoreCase("1.8.9-") && this.MultyTargets.bValue && CURRENT_BASES.size() > 1;
        if (is1_8_9MultyTargets) {
            int hurtTimeRule_2 = this.hurtTimeRule = MathUtils.clamp(10 - CURRENT_BASES.size(), 0, 10);
            CURRENT_BASES = CURRENT_BASES.stream().filter(base -> base.hurtTime <= hurtTimeRule_2).collect(Collectors.toList());
        } else {
            this.hurtTimeRule = 11;
        }
        boolean canTargetRots = !CURRENT_BASES.isEmpty() && CURRENT_BASES.get(0) != null;
        PREV_TARGET_ROTS = TARGET_ROTS;
        if (canTargetRots && (TARGET_ROTS == null || TARGET == null)) {
            TARGET_ROTS = CURRENT_BASES.get(0);
        }
        if (TARGET_ROTS != null && !this.canAddTarget(TARGET_ROTS, range, walls, nonRotate, this.hurtTimeRule, fov)) {
            TARGET_ROTS = null;
        }
        boolean bl2 = canTarget = TARGET_ROTS != null && this.canAddTarget(TARGET_ROTS, range, walls, nonRotate, this.hurtTimeRule, fov);
        if (canTarget && TARGET == null) {
            TARGET = TARGET_ROTS;
        }
        if (TARGET == null || TARGET != null && !this.canAddTarget(TARGET, range, walls, nonRotate, this.hurtTimeRule, fov)) {
            TARGET = null;
        }
        this.setWeaponSlot();
        this.tpHit = TPInfluence.get.forHitAuraRule(TARGET_ROTS);
        if (TARGET == null) {
            this.noRotateTick = false;
            return;
        }
        this.updateHits(TARGET);
    }

    void setupRotates(EntityLivingBase target, String mode) {
        if (target == null) {
            return;
        }
        if (!this.noRotateTick || mode.equalsIgnoreCase("AAC&Vulcan")) {
            this.rotationsCombat = this.rotations(target, true, mode);
        }
        this.noRotateTick = false;
        this.rotationsVisual = this.rotations(target, false, mode);
    }

    void rotate(EventPlayerMotionUpdate e, EntityLivingBase target) {
        String renderRotsMode;
        float yawFix = Minecraft.player.rotationYawHead;
        e.setYaw(this.rotationsCombat[0]);
        Minecraft.player.rotationYawHead = yawFix;
        float pitchFix = Minecraft.player.rotationPitchHead;
        e.setPitch(this.rotationsCombat[1]);
        Minecraft.player.rotationPitchHead = pitchFix;
        if (this.ViewLock.bValue && !this.Rotation.currentMode.equalsIgnoreCase("None")) {
            float[] lockedRotate = this.LockRotateType.currentMode.equalsIgnoreCase("VisualRotate") ? this.rotationsVisual : this.rotationsCombat;
            Minecraft.player.rotationYaw = lockedRotate[0];
            Minecraft.player.rotationPitch = lockedRotate[1];
        }
        if ((renderRotsMode = this.RenderRots.currentMode).equalsIgnoreCase("None")) {
            return;
        }
        float[] renderYP = this.rotationsVisual;
        switch (renderRotsMode) {
            case "AtRots": {
                renderYP = new float[]{this.rotationsVisual[0], this.rotationsVisual[1]};
                break;
            }
            case "Reverse": {
                renderYP = new float[]{this.rotationsVisual[0] + 180.0f, this.rotationsVisual[1]};
                break;
            }
            case "ReverseAp": {
                renderYP = new float[]{this.rotationsVisual[0] + 180.0f, -this.rotationsVisual[1]};
                break;
            }
            case "Turn360": {
                float msCooled = this.msCooldown();
                if (msCooled > 100.0f) {
                    float cooledPC = MathUtils.clamp(cooldown.getTime(), 0.0f, msCooled) / msCooled;
                    float addRadianYaw = MathUtils.wrapAngleTo180_float(360.0f * cooledPC * (float)this.antiaimSide);
                    renderYP = new float[]{this.rotationsVisual[0] + addRadianYaw, this.rotationsVisual[1]};
                    break;
                }
                renderYP = new float[]{this.rotationsVisual[0], this.rotationsVisual[1]};
                break;
            }
            case "Turn360Ap": {
                float msCooled = this.msCooldown();
                if (msCooled > 100.0f) {
                    float cooledPC = MathUtils.clamp(cooldown.getTime(), 0.0f, msCooled) / msCooled;
                    float addRadianYaw = MathUtils.wrapAngleTo180_float(360.0f * cooledPC * (float)this.antiaimSide);
                    float cooledRaze = ((double)cooledPC > 0.5 ? 1.0f - cooledPC : cooledPC) * 2.0f;
                    renderYP = new float[]{this.rotationsVisual[0] + addRadianYaw, this.rotationsVisual[1] - (this.rotationsVisual[1] - Minecraft.player.rotationPitch) * cooledRaze};
                    break;
                }
                renderYP = new float[]{this.rotationsVisual[0], this.rotationsVisual[1]};
                break;
            }
            case "Derp": {
                long millis = System.currentTimeMillis();
                float milPC$360 = millis % 360L;
                float milPC$45V = (float)(millis % 540L) / 3.0f;
                milPC$45V = ((milPC$45V > 90.0f ? 180.0f - milPC$45V : milPC$45V) - 45.0f) * 2.0f;
                renderYP = new float[]{milPC$360, milPC$45V};
            }
        }
        Minecraft.player.setHeadRotations(renderYP[0], renderYP[1]);
        Minecraft.player.renderYawOffset = renderYP[0];
    }

    @EventTarget
    public void onReceive(EventReceivePacket event) {
        SPacketPlayerPosLook packet;
        if (!this.actived || !this.canRotate()) {
            return;
        }
        Packet packet2 = event.getPacket();
        if (packet2 instanceof SPacketPlayerPosLook && (packet = (SPacketPlayerPosLook)packet2).getFlags().stream().anyMatch(flag -> flag.equals((Object)SPacketPlayerPosLook.EnumFlags.X_ROT) || flag.equals((Object)SPacketPlayerPosLook.EnumFlags.Y_ROT))) {
            float[] serverRot = new float[]{packet.yaw, packet.pitch};
            this.rotationsVisual = serverRot;
            this.rotationsCombat = serverRot;
            RotationUtil.Yaw = serverRot[0];
            RotationUtil.Pitch = serverRot[1];
            this.noRotateTick = true;
        }
    }

    private void updateBodyOffset(float meYaw) {
        double speed;
        float addRad;
        float offset = 65.0f;
        if (Minecraft.player.isSwingInProgress) {
            Minecraft.player.renderYawOffset += (meYaw - Minecraft.player.renderYawOffset) / 5.0f;
            offset = 10.0f;
        }
        Minecraft.player.renderYawOffset = (addRad = (float)((speed = MoveMeHelp.getCuttingSpeed()) > 0.05 ? MoveMeHelp.getMotionYaw() - (double)meYaw : 0.0)) + Minecraft.player.renderYawOffset < meYaw - offset ? meYaw - offset : (Minecraft.player.renderYawOffset > meYaw + offset ? meYaw + offset : Minecraft.player.renderYawOffset);
    }

    static boolean seenTargetEntity(boolean ignore, Entity targetIn, boolean deForce) {
        if (!ignore && HitAura.getMe() != null && targetIn != null) {
            double h = (double)targetIn.getEyeHeight() - 0.05;
            Vec3d seeTo = targetIn.getPositionVector().addVector(0.0, h, 0.0);
            boolean seen = HitAura.getMe().canEntityBeSeenVec3d(seeTo);
            if (!seen) {
                seen = HitAura.getMe().canEntityBeSeenVec3d(seeTo.addVector(0.0, -h / 4.0, 0.0));
            }
            if (!deForce) {
                if (!seen) {
                    seen = HitAura.getMe().canEntityBeSeenVec3d(seeTo.addVector(0.0, -h / 3.0, 0.0));
                }
                if (!seen) {
                    seen = HitAura.getMe().canEntityBeSeenVec3d(seeTo.addVector(0.0, -h / 1.5, 0.0));
                }
                if (!seen) {
                    seen = HitAura.getMe().canEntityBeSeenVec3d(seeTo.addVector(0.0, -h / 1.25, 0.0));
                }
                if (!seen) {
                    seen = HitAura.getMe().canEntityBeSeenVec3d(seeTo.addVector(0.0, -h / 1.1, 0.0));
                }
            }
            return seen;
        }
        return ignore;
    }

    @EventTarget
    public void onRotationStrafe(EventRotationStrafe event) {
        if (!this.actived || !this.RotateMoveSide.bValue || this.Rotation.currentMode.equalsIgnoreCase("None") || TARGET_ROTS == null || this.Rotation.currentMode.equalsIgnoreCase("None") || !this.canRotate()) {
            return;
        }
        event.setYaw(this.rotationsCombat[0] - (this.preYaw - this.rotationsCombat[0]));
        this.preYaw = this.rotationsCombat[0];
    }

    @EventTarget
    public void onRotationJump(EventRotationJump event) {
        if (!this.actived || !this.RotateMoveSide.bValue || this.Rotation.currentMode.equalsIgnoreCase("None") || TARGET_ROTS == null || this.Rotation.currentMode.equalsIgnoreCase("None") || !this.canRotate()) {
            return;
        }
        event.setYaw(this.rotationsCombat[0] - (this.preYaw - this.rotationsCombat[0]));
        this.preYaw = this.rotationsCombat[0];
    }

    @EventTarget
    public void onPlayerMotionUpdate(EventPlayerMotionUpdate e) {
        if (!this.actived || Minecraft.player == null) {
            return;
        }
        if (this.canRotate()) {
            this.rotate(e, TARGET_ROTS);
        }
    }

    void updateCriticalHopElement() {
        float time;
        if (Criticals.get.actived && Criticals.get.currentMode("Mode").equalsIgnoreCase("VanillaHop") && TARGET != null && !Minecraft.player.isJumping() && !Minecraft.player.isInWater() && Minecraft.player.onGround && (time = this.msCooldown() - (float)cooldown.getTime()) < 150.0f) {
            Minecraft.player.motionY = 0.05f;
            Minecraft.player.motionX = Entity.Getmotionx + (-0.1 + 0.2 * Math.random());
            Minecraft.player.motionZ = Entity.Getmotionz + (-0.1 + 0.2 * Math.random());
        }
    }

    void updateTrapdoorSneakElement() {
        if (Minecraft.player.isJumping() && MoveMeHelp.isBlockAboveHead()) {
            boolean sneak;
            float time = this.msCooldown() - (float)cooldown.getTime();
            boolean cooledPre = time <= 250.0f || time < 0.0f;
            HitAura.mc.gameSettings.keyBindSneak.pressed = sneak = Keyboard.isKeyDown(HitAura.mc.gameSettings.keyBindSneak.getKeyCode()) && HitAura.mc.currentScreen == null || TARGET != null && MoveMeHelp.trapdoorAdobedEntity(HitAura.getMe()) && cooledPre;
        }
    }

    @Override
    public void onUpdate() {
        if (!this.AttackPlayers.getBool() && !this.AttackMobs.getBool()) {
            this.toggle(false);
            Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7l" + this.getName() + "\u00a7r\u00a77]: \u00a77\u0432\u043a\u043b\u044e\u0447\u0438\u0442\u0435 \u0447\u0442\u043e-\u043d\u0438\u0431\u0443\u0434\u044c \u0432 \u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0430\u0445.", false);
        }
        if (Minecraft.player.jumpTicks == 4) {
            Minecraft.player.jumpTicks = 0;
        }
        if (TARGET_ROTS != null && TARGET != null) {
            this.critsHelper();
        }
        if (this.canRotate()) {
            this.setupRotates(TARGET_ROTS, this.Rotation.currentMode);
        }
        if (this.getAuraUpdater().equalsIgnoreCase("Default")) {
            this.updateAura(this.getAuraUpdater());
        }
        this.updateCriticalHopElement();
        this.updateTrapdoorSneakElement();
    }

    @Override
    public void onMovement() {
        if (this.getAuraUpdater().equalsIgnoreCase("MoveUpdate")) {
            this.updateAura(this.getAuraUpdater());
        }
    }

    @Override
    public void onRenderUpdate() {
        if (this.getAuraUpdater().equalsIgnoreCase("RenderUpdate")) {
            this.updateAura(this.getAuraUpdater());
        }
    }

    @Override
    public void onToggled(boolean actived) {
        if (!actived) {
            this.setWeaponSlot();
        }
        this.rotationsCombat = new float[]{Minecraft.player.rotationYaw, Minecraft.player.rotationPitch};
        this.rotationsVisual = this.rotationsCombat;
        RotationUtil.Yaw = this.rotationsVisual[0];
        RotationUtil.Pitch = this.rotationsVisual[1];
        TARGET = null;
        TARGET_ROTS = null;
        PREV_TARGET_ROTS = null;
        super.onToggled(actived);
    }

    static {
        cooldown = new TimerHelper();
        cooldownAxe = new TimerHelper();
        shiedPressTimeTarget = new TimerHelper();
    }
}

