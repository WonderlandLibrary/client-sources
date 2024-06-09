package me.teus.eclipse.modules.impl.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.teus.eclipse.events.player.EventMotionUpdate;
import me.teus.eclipse.events.player.EventPreUpdate;
import me.teus.eclipse.events.world.EventTick;
import me.teus.eclipse.modules.Category;
import me.teus.eclipse.modules.Info;
import me.teus.eclipse.modules.Module;
import me.teus.eclipse.modules.value.impl.BooleanValue;
import me.teus.eclipse.modules.value.impl.ModeValue;
import me.teus.eclipse.modules.value.impl.NumberValue;
import me.teus.eclipse.utils.RotationUtils;
import me.teus.eclipse.utils.TimerUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import xyz.lemon.event.bus.Listener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Info(name = "KillAura", displayName = "KillAura", category = Category.COMBAT)
public class Aura extends Module {

    public ModeValue mode = new ModeValue("Mode", "CPS", "CPS", "HurtTime", "Tick");
    public ModeValue priority = new ModeValue("Priority", "Distance", "Distance", "Health", "HurtTime");
    public ModeValue autoblock = new ModeValue("Auto Block", "None", "None", "Fake", "Spam", "NCP", "AACv3", "Vanilla");

    public ModeValue rotation = new ModeValue("Aim", "Closest", "Closest", "Random", "NCP", "None");

    public NumberValue range = new NumberValue("Range", 3.0, 1.0, 6.0, 0.1);
    public NumberValue jitters = new NumberValue("Jitter Strength", 1.0, 0.5, 10.0, 0.1);
    public NumberValue wallRange = new NumberValue("Wall Range", 2.0, 1.0, 6.0, 0.1);
    public NumberValue blockRange = new NumberValue("Block Range", 3.0, 1.0, 10.0, 0.1);
    public NumberValue minCps = new NumberValue("Min CPS", 12, 1.0, 20.0, 0.1);
    public NumberValue maxCps = new NumberValue("Max CPS", 12, 1.0, 20.0, 0.1);

    public BooleanValue players = new BooleanValue("Players", true);

    public BooleanValue mobs = new BooleanValue("Mobs", false);
    public BooleanValue animals = new BooleanValue("Animals", false);
    public BooleanValue teams = new BooleanValue("Teams", false);

    public NumberValue angle = new NumberValue("Angle", 45.0, 1.0, 180.0, 1.0);
    public BooleanValue invisible = new BooleanValue("Invisibles", false);

    public static EntityLivingBase target = null;
    public final ArrayList<Entity> targets = new ArrayList<>(), bots = new ArrayList<>();

    public static float yaw, pitch, prevYaw, lastYaw, lastPitch;
    private double nextDelay = 0;
    public static boolean sprint, block;

    private TimerUtil timer = new TimerUtil();

    public Aura(){
        this.addValues(
                mode, priority, rotation, autoblock,
                minCps, maxCps, range, angle, wallRange, blockRange, jitters,
                players, mobs, animals, teams, invisible
        );
    }
    @Override
    public void onEnable() {
        yaw = mc.thePlayer.rotationYaw;
        pitch = mc.thePlayer.rotationPitch;
        targets.clear();
        target = null;
    }

    @Override
    public void onDisable() {
        block = false;
    }

    public Listener<EventTick> tickListener = event -> {
        findTargets();
    };


    public Listener<EventPreUpdate> updateListener = event -> {
        if(targets.isEmpty()){
            yaw = mc.thePlayer.rotationYaw;
            pitch = mc.thePlayer.rotationPitch;
            return;
        }
    };

    public float[] nextRotations = new float[]{};
    private float[] getRotations() {
        lastYaw = yaw;
        lastPitch = pitch;
        float[] rotations;
        float[] prevRots = {yaw, pitch};
        float[] centerRotations = RotationUtils.getNeededRotations(RotationUtils.getCenter(targets.get(0).getEntityBoundingBox()));
        switch(rotation.getMode()){
            case "Closest":
                nextRotations = RotationUtils.getNeededRotations(RotationUtils.getClosestPoint(mc.thePlayer.getPositionEyes(1.0f), targets.get(0).getEntityBoundingBox()));
                prevRots = new float[]{yaw, pitch};
                float[] rotOffset = new float[]{MathHelper.wrapAngleTo180_float(nextRotations[0] - prevRots[0]), MathHelper.wrapAngleTo180_float(nextRotations[1] - prevRots[1])};
                nextRotations[0] = (float) (yaw + rotOffset[0] * (angle.getValue() / angle.getMax()));
                break;
            case "Random":
                nextRotations = RotationUtils.getNeededRotations(RotationUtils.getRandomCenter(targets.get(0).getEntityBoundingBox()));
                prevRots = new float[]{yaw, pitch};
                rotOffset = new float[]{MathHelper.wrapAngleTo180_float(nextRotations[0] - prevRots[0]), MathHelper.wrapAngleTo180_float(nextRotations[1] - prevRots[1])};
                nextRotations[0] = (float) (yaw + rotOffset[0] * (angle.getValue() / angle.getMax()));
                break;
            case "None":
                return new float[]{mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch};
            case "NCP":
                return new float[] {RotationUtils.getNeededRotations(RotationUtils.getCenter(targets.get(0).getEntityBoundingBox()))[0],
                        Math.max(-90, Math.min(90, 45 + (mc.thePlayer.getDistanceToEntity(targets.get(0)) - 3.0f) * -80))};
        }

        rotations = nextRotations;

        return rotations;
    }

    public Listener<EventMotionUpdate> motionListener = event -> {
        switch(autoblock.getMode()){
            case "NCP":
                if((mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) && mc.theWorld.loadedEntityList.stream().anyMatch(e -> isValid(e, Math.max(this.range.getValue(), blockRange.getValue())))){
                    mc.gameSettings.keyBindUseItem.pressed = false;
                    if(event.isPre()){
                        if(!targets.isEmpty()){
                            MovingObjectPosition object = new MovingObjectPosition(targets.get(0));
                            //mc.playerController.isPlayerRightClickingOnEntity(mc.thePlayer, object.entityHit, object);
                            mc.playerController.interactWithEntitySendPacket(mc.thePlayer, object.entityHit);
                        }
                        mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
                    }
                }
                break;
            case "Fake":
                if ((mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) && mc.theWorld.loadedEntityList.stream().anyMatch(e -> isValid(e, Math.max(this.range.getValue(), blockRange.getValue())))) {
                    if(event.isPre())
                        block = true;
                }else{
                    block = false;
                }
                break;
            case "Vanilla":
                if(event.isPre()) {
                    if ((mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) && mc.theWorld.loadedEntityList.stream().anyMatch(e -> isValid(e, Math.max(this.range.getValue(), blockRange.getValue())))) {
                        mc.gameSettings.keyBindUseItem.pressed = block = true;
                    }else if(block){
                        mc.gameSettings.keyBindUseItem.pressed = block = false;
                    }
                }
                break;
            case "AACv3":
                if((mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) && mc.theWorld.loadedEntityList.stream().anyMatch(e -> isValid(e, Math.max(this.range.getValue(), blockRange.getValue())))){
                    mc.gameSettings.keyBindUseItem.pressed = false;
                    if(event.isPost()){
                        mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
                    }
                }
                break;
            case "Spam":
                if((mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) && mc.theWorld.loadedEntityList.stream().anyMatch(e -> isValid(e, Math.max(this.range.getValue(), blockRange.getValue())))){
                    if(event.isPost())
                        mc.rightClickMouse();
                }
                break;
        }
        if(minCps.getValue() >= maxCps.getValue())
            minCps.setValue(maxCps.getValue());
        if(!targets.isEmpty()) {
            target = (EntityLivingBase) targets.get(0);
            float[] rots = getRotations();
            mc.thePlayer.rotationYawHead = rots[0];
            event.setYaw(rots[0]);
            event.setPitch(rots[1]);

            if (timer.hasTimeElapsed((long) (1000 / maxCps.getValue()))) {
                attack(target);
                System.out.println("Attacked Motion");
                timer.reset();
            }

        }
    };

    public void attack(Entity entity) {
        mc.thePlayer.swingItem();
        mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
    }

    public void findTargets() {
        ArrayList<EntityLivingBase> finalEntities = mc.theWorld.loadedEntityList.stream().filter(this::isValid).map(e -> (EntityLivingBase) e).collect(Collectors.toCollection(ArrayList::new));
        targets.clear();
        switch(priority.getMode()) {
            case "Distance":
                finalEntities = finalEntities.stream().sorted(Comparator.comparingDouble(e -> e.getDistanceToEntity(mc.thePlayer))).collect(Collectors.toCollection(ArrayList::new));
                break;
            case "Health":
                finalEntities = finalEntities.stream().sorted(Comparator.comparingDouble(EntityLivingBase::getHealth)).collect(Collectors.toCollection(ArrayList::new));
                break;
            case "HurtTime":
                finalEntities = finalEntities.stream().sorted(Comparator.comparingInt(e -> e.hurtTime)).collect(Collectors.toCollection(ArrayList::new));
                break;
        }
        for (Entity entity : finalEntities) {
            finalEntities.size();
            if (targets.size() >= 1) break;
            targets.add(entity);
        }
    }

    public boolean isValid(Entity entity) {
        return isValid(entity, range.getValue());
    }

    public boolean isValid(Entity entity, double range) {
        return isValidTarget(entity) && entity != mc.thePlayer && mc.thePlayer.getDistanceToEntity(entity) < range && entity instanceof EntityLivingBase;
    }

    public boolean isValidTarget(Entity entity) {
        if (entity.getEntityId() < 0) return false;
        if (entity.ticksExisted < 5) return false;
        if (bots.contains(entity)) return false;
        if (entity instanceof EntityLivingBase && ((EntityLivingBase) entity).deathTime != 0) return false;
        if (teams.isEnabled() && entity.getDisplayName().getFormattedText().startsWith(Character.toString(ChatFormatting.PREFIX_CODE) + mc.thePlayer.getDisplayName().getFormattedText().charAt(mc.thePlayer.getDisplayName().getFormattedText().indexOf(ChatFormatting.PREFIX_CODE) + 1)))  return false;
        if (entity.getName().contains("[NPC]")) return false;
        if (entity.isInvisibleToPlayer(mc.thePlayer) && !invisible.isEnabled()) return false;
        return (entity instanceof EntityMob && mobs.isEnabled()) ||
                ((entity instanceof EntityAnimal || entity instanceof EntityVillager) && animals.isEnabled()) ||
                (entity instanceof EntityPlayer && players.isEnabled());
    }
}
