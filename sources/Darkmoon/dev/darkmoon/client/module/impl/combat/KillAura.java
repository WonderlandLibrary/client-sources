package dev.darkmoon.client.module.impl.combat;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.player.EventMotion;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.impl.movement.ElytraFirework;
import dev.darkmoon.client.module.impl.movement.Strafe;
import dev.darkmoon.client.module.impl.player.NoClip;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.ModeSetting;
import dev.darkmoon.client.module.setting.impl.MultiBooleanSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.utility.math.AdvancedCast;
import dev.darkmoon.client.utility.math.RayCastUtility;
import dev.darkmoon.client.utility.math.RotationUtility;
import dev.darkmoon.client.utility.move.MovementUtility;
import dev.darkmoon.client.utility.player.InventoryUtility;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.*;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;
import java.util.*;

@ModuleAnnotation(name = "KillAura", category = Category.COMBAT)
public class KillAura extends Module {

    public static EntityLivingBase targetEntity;
    public static KillAura INSTANCE;
    private boolean targetVisible;
    public boolean thisContextRotatedBefore;
    public float prevAdditionYaw;
    public static Vector2f rotation = new Vector2f();
    float minCPS = 0.0f;
    public static NumberSetting range = new NumberSetting("Distance", 3.8F, 1.0F, 6.0F, 0.1F);
    public static NumberSetting rotateRange = new NumberSetting("Rotate-Distance", 0.5F, 0.0F, 2.0F, 0.1F);
    public MultiBooleanSetting targetSelectionSetting = new MultiBooleanSetting("Entities", Arrays.asList("Players", "Mobs", "Animals", "NPC"));
    public ModeSetting sortBy = new ModeSetting("Sort For:", "All", "All", "Distance", "Health", "Best Armor", "Worst Armor", "FOV");
    public BooleanSetting wallsBypass = new BooleanSetting("Through-Walls", true);
    public BooleanSetting resolve = new BooleanSetting("Resolve-Players", true);
    public BooleanSetting onlyCritical = new BooleanSetting("Only-Critical", true);
    public BooleanSetting waterCritical = new BooleanSetting("Water-Critical", true, () -> onlyCritical.get());
    public BooleanSetting breakShield = new BooleanSetting("Shield-Break", true);
    public BooleanSetting shieldDesync = new BooleanSetting("Shield-Desync", false);

    public KillAura() {
        INSTANCE = this;
    }

    @EventTarget
    public void onUpdateAura(EventUpdate eventUpdate) {
        if (resolve.get()) {
            this.resolvePlayers();
        }
        this.onAura();
        if (resolve.get()) {
            this.releaseResolver();
        }
    }

    @EventTarget
    public void onMotion(EventMotion eventMotion) {
        if (targetEntity != null) {
            mc.player.rotationYawHead = rotation.x;
            if (!DarkMoon.getInstance().getModuleManager().getModule(ElytraFirework.class).isEnabled()) {
                mc.player.renderYawOffset = rotation.x;
            }

            mc.player.rotationPitchHead = rotation.y;
            eventMotion.setYaw(rotation.x);
            eventMotion.setPitch(rotation.y);
        }

    }

    @Override
    public void onDisable() {
        targetEntity = null;
        if (mc.player != null) {
            rotation.x = mc.player.rotationYaw;
            rotation.y = mc.player.rotationPitch;
        }
        super.onDisable();
    }

    public void onAura() {
        if (this.shieldDesync.get() && mc.player.isHandActive() && mc.player.isActiveItemStackBlocking(4 + (new Random()).nextInt(4))) {
            mc.playerController.onStoppedUsingItem(mc.player);
        }

        if (this.minCPS > 0.0F) {
            --this.minCPS;
        }

        if (targetEntity != null && !this.isValidTarget(targetEntity)) {
            targetEntity = null;
        }

        if (targetEntity == null) {
            targetEntity = this.findTarget();
        }

        if (targetEntity == null) {
            rotation.x = mc.player.rotationYaw;
            rotation.y = mc.player.rotationPitch;
        } else {
            this.thisContextRotatedBefore = false;
            this.attackMethod(targetEntity);
            if (!this.thisContextRotatedBefore) {
                if (!mc.player.isElytraFlying()/* && !DarkMoon.getInstance().getModuleManager().getModule(ElytraFirework.class).isEnabled()*/) {
                    this.rotate(targetEntity, false);
                } else {
                    this.getVectorRotation(targetEntity, false);
                }
            }

        }
    }

    public void attackMethod(EntityLivingBase entity) {
        if (this.whenFalling() && this.minCPS == 0.0F) {
            if (this.getHitBox(entity, (double)range.get()) == null) {
                return;
            }

            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            if (!mc.player.isElytraFlying() /*&& !DarkMoon.getInstance().getModuleManager().getModule(ElytraFirework.class).isEnabled()*/) {
                this.rotate(entity, true);
            } else {
                this.getVectorRotation(entity, true);
            }

            if (AdvancedCast.instance.getMouseOver(entity, rotation.x, rotation.y, (double)range.get(), this.ignoreWalls()) == entity) {
                if (mc.player.isActiveItemStackBlocking()) {
                    mc.playerController.onStoppedUsingItem(mc.player);
                }

                this.minCPS = 10.0F;
                mc.playerController.attackEntity(mc.player, targetEntity);
                mc.player.swingArm(EnumHand.MAIN_HAND);
                mc.player.resetCooldown();
                this.breakShieldMethod((EntityPlayer)entity);
            }
        }

    }

    public void getVectorRotation(EntityLivingBase entity, boolean attackContext) {
        this.thisContextRotatedBefore = true;
        Vec3d vec = this.getHitBox(entity, (double) (rotateRange.get() + range.get()));
        if (vec == null) {
            vec = entity.getPositionEyes(1.0F);
        }

        double x = vec.x - mc.player.posX;
        double y = vec.y - mc.player.getPositionEyes(1.0F).y;
        double z = vec.z - mc.player.posZ;
        double dst = Math.sqrt(Math.pow(x, 2.0) + Math.pow(z, 2.0));
        float yawToTarget = (float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(z, x)) - 90.0);
        float pitchToTarget = (float) (-Math.toDegrees(Math.atan2(y, dst)));
        if (this.wallsBypass.get() && this.targetVisible && !attackContext) {
            yawToTarget = (float) ((double) yawToTarget + 20.0);
        }

        float yawToTarget2 = MathHelper.wrapDegrees(yawToTarget);
        float yawDelta = MathHelper.wrapDegrees(yawToTarget2 - rotation.x) / 1.0001F;
        int yawDeltaAbs = (int) Math.abs(yawDelta);
        float f2 = pitchToTarget / 1.0001F - rotation.y / 1.0001F;
        float pitchDeltaAbs = Math.abs(f2);
        float additionYaw = (float) Math.min(Math.max(yawDeltaAbs, 1), 80);
        float additionPitch = Math.max(attackContext && targetEntity != null ? pitchDeltaAbs : 1.0F, 2.0F);
        if (Math.abs(additionYaw - this.prevAdditionYaw) <= 3.0F) {
            additionYaw = this.prevAdditionYaw + 3.1F;
        }

        float f3 = rotation.x + (yawDelta > 0.0F ? additionYaw : -additionYaw) * 1.0001F;
        float f4 = MathHelper.clamp(rotation.y + (f2 > 0.0F ? additionPitch : -additionPitch) * 1.0001F, -90.0F, 90.0F);
        rotation.x = f3;
        rotation.y = f4;
        this.prevAdditionYaw = additionYaw;
    }

    public void rotate(Entity base, boolean attackContext) {
        this.thisContextRotatedBefore = true;
        Vec3d bestHitbox = this.getHitBox(base, (double) (rotateRange.get() + range.get()));
        if (bestHitbox == null) {
            bestHitbox = base.getPositionEyes(1.0F);
        }

        float pitchToHead = 0.0F;
        EntityPlayerSP client = Minecraft.getMinecraft().player;
        double x = bestHitbox.x - client.posX;
        double y = base.getPositionEyes(1.0F).y - client.getPositionEyes(1.0F).y;
        double z = bestHitbox.z - client.posZ;
        double dst = Math.sqrt(Math.pow(x, 2.0) + Math.pow(z, 2.0));
        pitchToHead = (float) (-Math.toDegrees(Math.atan2(y, dst)));
        float sensitivity = 1.0001F;
        float yawToTarget = (float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(z, x)) - 90.0);
        float pitchToTarget = (float) (-Math.toDegrees(Math.atan2(y, dst)));
        float yawDelta = MathHelper.wrapDegrees(yawToTarget - rotation.x) / sensitivity;
        float pitchDelta = (pitchToTarget - rotation.y) / sensitivity;
        if (yawDelta > 180.0F) {
            yawDelta -= 180.0F;
        }

        int yawDeltaAbs = (int) Math.abs(yawDelta);
        if (yawDeltaAbs < 180) {
            float pitchDeltaAbs = Math.abs(pitchDelta);
            float additionYaw = (float) Math.min(Math.max(yawDeltaAbs, 1), 80);
            float additionPitch = Math.max(attackContext ? pitchDeltaAbs : 1.0F, 2.0F);
            if (Math.abs(additionYaw - this.prevAdditionYaw) <= 3.0F) {
                additionYaw = this.prevAdditionYaw + 3.1F;
            }

            float newYaw = rotation.x + (yawDelta > 0.0F ? additionYaw : -additionYaw) * sensitivity;
            float newPitch = MathHelper.clamp(rotation.y + (pitchDelta > 0.0F ? additionPitch : -additionPitch) * sensitivity, -90.0F, 90.0F);
            rotation.x = newYaw;
            rotation.y = newPitch;
            this.prevAdditionYaw = additionYaw;
        }
    }

    public boolean ignoreWalls() {
        BlockPos pos = new BlockPos(mc.player.lastReportedPosX, mc.player.lastReportedPosY, mc.player.lastReportedPosZ);
        return mc.world.getBlockState(pos).getMaterial() != Material.AIR ? true : this.wallsBypass.get();
    }

    public boolean whenFalling() {
        boolean critWater = this.waterCritical.get() && mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)).getBlock() instanceof BlockLiquid && mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 1.0, mc.player.posZ)).getBlock() instanceof BlockAir;
        boolean reason = mc.player.isPotionActive(MobEffects.BLINDNESS) || mc.player.isOnLadder() || mc.player.isInWater() && !critWater || mc.player.isInWeb || mc.player.capabilities.isFlying;
        if (mc.player.getCooledAttackStrength(1.5F) < 0.93F) {
            return false;
        } else if (this.onlyCritical.get() && !reason) {
            if (MovementUtility.isBlockAboveHead() && mc.player.onGround && mc.player.fallDistance > 0.0F) {
                return true;
            } else {
                return !mc.player.onGround && mc.player.fallDistance > 0.0F;
            }
        } else {
            return true;
        }
    }

    public void breakShieldMethod(EntityPlayer entity) {
        if (InventoryUtility.doesHotbarHaveAxe() && this.breakShield.get()) {
            int item = InventoryUtility.getAxe();
            if (entity != null && entity.getActiveItemStack().getItem() instanceof ItemShield) {
                mc.player.connection.sendPacket(new CPacketHeldItemChange(item));
                mc.playerController.attackEntity(mc.player, entity);
                mc.player.swingArm(EnumHand.MAIN_HAND);
                mc.player.resetCooldown();
                mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
            }
        }

    }

    public EntityLivingBase findTarget() {
        ArrayList<EntityLivingBase> entity = new ArrayList();
        Iterator var2 = mc.world.loadedEntityList.iterator();

        while(var2.hasNext()) {
            Entity player = (Entity)var2.next();
            if (player instanceof EntityLivingBase) {
                if (this.isValidTarget((EntityLivingBase)player)) {
                    entity.add((EntityLivingBase)player);
                } else {
                    entity.remove(player);
                }
            }
        }

        String mode = this.sortBy.get();
        if (mode.equals("All")) {
            entity.sort(Comparator.comparingDouble(EntityLivingBase::getTotalArmorValue)
                    .thenComparing((entity1, entity2) -> (int) (entity1.getHealth() - entity2.getHealth()))
                    .thenComparing(mc.player::getDistance));
        } else if (mode.equals("Distance")) {
            entity.sort(Comparator.comparingDouble(mc.player::getDistance));
        } else if (mode.equalsIgnoreCase("FOV")) {
            entity.sort(Comparator.comparingDouble(this::getFov));
        } else if (mode.equalsIgnoreCase("Health")) {
            entity.sort((entity1, entity2) -> (int) (entity1.getHealth() - entity2.getHealth()));
        } else if (mode.equalsIgnoreCase("Best Armor")) {
            entity.sort(Comparator.comparing(EntityLivingBase::getTotalArmorValue).reversed());
        } else if (mode.equalsIgnoreCase("Worst Armor")) {
            entity.sort(Comparator.comparing(EntityLivingBase::getTotalArmorValue));
        }

        return entity.isEmpty() ? null : (EntityLivingBase)entity.get(0);
    }

    public float getFov(EntityLivingBase entity) {
        double diffX = entity.posX - mc.player.posX;
        double diffZ = entity.posZ - mc.player.posZ;
        return (float)Math.abs(MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0 - (double)mc.player.rotationYaw));
    }

    public boolean isValidTarget(EntityLivingBase e) {
        if (e == mc.player) {
            return false;
        } else if (e.isDead) {
            return false;
        } else if (e.getHealth() <= 0.0F) {
            return false;
        } else if (e instanceof EntityArmorStand) {
            return false;
        } else if (DarkMoon.getInstance().getFriendManager().isFriend(e.getName())) {
            return false;
        } else if (e instanceof EntityPlayer && ((EntityPlayer)e).isBot && !this.targetSelectionSetting.get(3)) {
            return false;

        } else if (e instanceof EntityPlayer && AntiBot.bots.contains(e) && !this.targetSelectionSetting.get(3)) {
            return false;
        } else if (e instanceof EntityPlayer && !this.targetSelectionSetting.get(0)) {
            return false;
        } else if (e instanceof EntityMob && !this.targetSelectionSetting.get(1)) {
            return false;
        } else if ((e instanceof EntityAnimal || e instanceof EntityGolem || e instanceof EntitySquid || e instanceof EntityVillager) && !this.targetSelectionSetting.get(2)) {
            return false;
        } else if (!this.ignoreWalls()) {
            return this.getHitBox(e, (double)(range.get() + rotateRange.get())) != null;
        } else {
            return e.getDistance(mc.player) <= range.get() + rotateRange.get();
        }
    }

    public Vec3d getHitBox(Entity entity, double rotateDistance) {
        if (entity.getDistanceSq(entity) >= 36.0) {
            return null;
        } else {
            Vec3d head = findHitboxCoord(Hitbox.HEAD, entity);
            Vec3d chest = findHitboxCoord(Hitbox.CHEST, entity);
            Vec3d legs = findHitboxCoord(Hitbox.LEGS, entity);
            ArrayList<Vec3d> points = new ArrayList(Arrays.asList(head, chest, legs));
            points.removeIf((point) -> {
                this.targetVisible = !this.isHitBoxVisible(entity, point, rotateDistance);
                return this.targetVisible;
            });
            if (points.isEmpty()) {
                return null;
            } else {
                points.sort((d1, d2) -> {
                    Vector2f r1 = RotationUtility.getDeltaForCoord(rotation, d1);
                    Vector2f r2 = RotationUtility.getDeltaForCoord(rotation, d2);
                    float y1 = Math.abs(r1.y);
                    float y2 = Math.abs(r2.y);
                    return (int)((y1 - y2) * 1000.0F);
                });
                return (Vec3d)points.get(0);
            }
        }
    }

    public static Vec3d findHitboxCoord(Hitbox box, Entity target) {
        double yCoord = 0;
        switch (box) {
            case HEAD:
                yCoord = target.getEyeHeight();
                break;
            case CHEST:
                yCoord = target.getEyeHeight() / 2;
                break;
            case LEGS:
                yCoord = 0.05;
                break;
        }
        return target.getPositionVector().add(0, yCoord, 0);
    }

    public boolean isHitBoxVisible(Entity target, Vec3d vector, double dst) {
        return RayCastUtility.getPointedEntity(RotationUtility.getRotationForCoord(vector), dst, 1.0F, !this.ignoreWalls(), target) == target;
    }
    public void resolvePlayers() {
        Iterator var1 = mc.world.playerEntities.iterator();

        while(var1.hasNext()) {
            EntityPlayer player = (EntityPlayer)var1.next();
            if (player instanceof EntityOtherPlayerMP) {
                ((EntityOtherPlayerMP)player).resolve();
            }
        }

    }

    public void releaseResolver() {
        Iterator var1 = mc.world.playerEntities.iterator();

        while(var1.hasNext()) {
            EntityPlayer player = (EntityPlayer)var1.next();
            if (player instanceof EntityOtherPlayerMP) {
                ((EntityOtherPlayerMP)player).releaseResolver();
            }
        }

    }
    public enum Hitbox {
        HEAD, CHEST, LEGS
    }
}