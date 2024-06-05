package club.mega.util;

import club.mega.Mega;
import club.mega.interfaces.MinecraftInterface;
import club.mega.module.impl.combat.KillAura;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class AuraUtil implements MinecraftInterface {

    private static float[] rotations;
    private static float[] prevRotations;
    private final ArrayList<EntityLivingBase> targets = new ArrayList<>();
    private static EntityLivingBase target;
    private final static KillAura killAura = Mega.INSTANCE.getModuleManager().getModule(KillAura.class);
    private static int targetIndex, finalCps = 0, blockTicks;
    private static final TimeUtil switchTimer = new TimeUtil();
    private static final TimeUtil attackTimer = new TimeUtil();
    private static boolean blocking;

    private static float range, preRange, rotationSpeed;

    public static void setTargets() {
        target = null;
        List<EntityLivingBase> livingEntities = getLivingEntities();

        if (killAura.priority.is("health"))
            livingEntities.sort(new HealthSorter());
        else
            livingEntities.sort(new DistanceSorter());
        if (livingEntities.size() > 0) {
            if (killAura.mode.is("single"))
                targetIndex = 0;
            else {
                if (switchTimer.hasTimePassed(killAura.switchDelay.getAsLong())) {
                    targetIndex++;
                    switchTimer.reset();
                }
                if (targetIndex >= livingEntities.size())
                    targetIndex = 0;
            }
            target = livingEntities.get(targetIndex);
        }
    }

    public static void attack() {
        block();
        int aps = (int) Math.round(RandomUtil.getRandomNumber(killAura.minAPS.getAsInt(),killAura.maxAPS.getAsInt()));

        if (finalCps < aps)
            finalCps += Math.round(RandomUtil.getRandomNumber(0.8D, 2D));
        if (finalCps <= aps)
            finalCps--;

        if (!attackTimer.hasTimePassed(1000 / aps))
            return;

        if (shouldPreSwing(range, preRange))
            MC.thePlayer.swingItem();

        if (canAttack(range))
        switch (killAura.attackMode.getCurrent())
        {
            case "Normal":
                MC.thePlayer.swingItem();
                if (killAura.keepSprint.get())
                    MC.getNetHandler().addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                else
                    MC.playerController.attackEntity(MC.thePlayer, target);
                break;
            case "Click":
                MC.clickMouse();
                if (killAura.keepSprint.get())
                    MC.thePlayer.setSprinting(true);
                break;
        }
        if (killAura.randomizeRange.get())
            range = (float) RandomUtil.getRandomNumber((killAura.range.getAsDouble() > 3.2 ? killAura.range.getAsDouble() - 0.3 : killAura.range.getAsDouble()), killAura.range.getAsDouble() + 1);

        if (killAura.randomizePreRange.get())
            preRange = (float) RandomUtil.getRandomNumber(killAura.preRange.getAsDouble(), killAura.preRange.getAsDouble() + 1);

        attackTimer.reset();
        unBlock();
    }

    private static boolean isValid(EntityLivingBase entityLivingBase) {
        return (killAura.attackDeath.get() || !entityLivingBase.isDead) &&
                ((!entityLivingBase.getName().toLowerCase().contains("shop") && !entityLivingBase.getName().toLowerCase().contains("upgrades")) || killAura.shopAttack.get()) &&
                (killAura.ignoreWalls.get() || RayCastUtil.canSeeEntity(entityLivingBase)) &&
                (entityLivingBase.getName() != MC.thePlayer.getName()) &&
                (MC.thePlayer.getDistanceToEntity(entityLivingBase) <= range + preRange) &&
                ((killAura.player.get() && entityLivingBase instanceof EntityPlayer) || (killAura.mobs.get() && (entityLivingBase instanceof EntityMob || entityLivingBase instanceof EntityAnimal)) || (killAura.villagers.get() && entityLivingBase instanceof EntityVillager));
    }

    public static void block() {
        if (MC.thePlayer.getDistanceToEntity(target) <= killAura.blockRange.getAsDouble() && MC.thePlayer.getHeldItem().getItem() instanceof ItemSword && killAura.autoBlock.get() && !blocking && RandomUtil.get(killAura.blockChance.getAsInt())) {
            blocking = true;
            blockTicks = 0;
            MC.gameSettings.keyBindUseItem.pressed = true;
        } else {
            forceUnblock();
        }
    }

    public static void unBlock() {
        if (MC.thePlayer.getDistanceToEntity(target) <= killAura.blockRange.getAsDouble() && MC.thePlayer.getHeldItem().getItem() instanceof ItemSword && killAura.autoBlock.get() && blocking && blockTicks >= Math.round(RandomUtil.getRandomNumber(killAura.minUnBlockTicks.getAsInt(), killAura.maxUnBlockTicks.getAsInt()))) {
            blocking = false;
            MC.gameSettings.keyBindUseItem.pressed = false;
        }
    }

    public static void forceUnblock() {
        if (blocking && MC.thePlayer.getHeldItem().getItem() instanceof ItemSword && killAura.autoBlock.get()) {
            MC.gameSettings.keyBindUseItem.pressed = false;
            blocking = false;
        }
    }

    public static float[] getRotations() {
        return rotations;
    }

    public static void setRotations(final float[] rotations) {
        AuraUtil.rotations = rotations;
    }

    public static float[] getPrevRotations() {
        return prevRotations;
    }

    public static void setPrevRotations(final float[] rotations) {
        AuraUtil.prevRotations = rotations;
    }

    public static EntityLivingBase getTarget() {
        return target;
    }

    public static void setTarget(final EntityLivingBase target) {
        AuraUtil.target = target;
    }

    public static void setRange(final float range) {
        AuraUtil.range = range;
    }

    public static float getRange() {
        return range;
    }

    public static void setPreRange(final float preRange) {
        AuraUtil.preRange = preRange;
    }

    public static float getPreRange() {
        return preRange;
    }

    public static void setRotationSpeed(final float rotationSpeed) {
        AuraUtil.rotationSpeed = rotationSpeed;
    }

    public static float getRotationSpeed() {
        return rotationSpeed;
    }

    public static void addBlockTick() {
        if (MC.thePlayer.getDistanceToEntity(target) <= killAura.blockRange.getAsDouble()) blockTicks++;
    }

    public static void reduceRotationSpeed(final float amount) {
        if (rotationSpeed >= 50)
            rotationSpeed -= amount;

    }

    public static void resetCps() {
        finalCps = 0;
    }

    private static boolean canAttack(final float range) {
        return (target != null && MC.thePlayer.getDistanceToEntity(target) <= range);
    }

    private static boolean shouldPreSwing(final float realRange, final float preRange) {
        return (target != null && MC.thePlayer.getDistanceToEntity(target) > realRange && MC.thePlayer.getDistanceToEntity(target) <= (realRange + preRange) - RandomUtil.getRandomNumber(0.2, 0.6)) && killAura.preSwing.get();
    }

    private static List<EntityLivingBase> getLivingEntities() {
        final List<EntityLivingBase> entities = new ArrayList<>();
        for (final Entity entity : MC.theWorld.loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                final EntityLivingBase e = (EntityLivingBase) entity;
                if (isValid((EntityLivingBase) entity) && e != MC.thePlayer){
                    entities.add(e);
                }
            }
        }
        return entities;
    }

    private static final class HealthSorter implements Comparator<EntityLivingBase> {
        public int compare(EntityLivingBase o1, EntityLivingBase o2) {
            return Double.compare(PlayerUtil.getEffectiveHealth(o1), PlayerUtil.getEffectiveHealth(o2));
        }
    }

    private static final class DistanceSorter implements Comparator<EntityLivingBase> {
        public int compare(EntityLivingBase o1, EntityLivingBase o2) {
            return Double.compare(MC.thePlayer.getDistanceToEntity(o1), MC.thePlayer.getDistanceToEntity(o2));
        }
    }


}
