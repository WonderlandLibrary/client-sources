package mathax.client.systems.modules.combat;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import mathax.client.eventbus.EventHandler;
import mathax.client.events.packets.PacketEvent;
import mathax.client.events.render.Render3DEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.friends.Friends;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;

import mathax.client.utils.Utils;
import mathax.client.utils.Volcan.AnticheatUtils;
import mathax.client.utils.entity.EntityUtils;
import mathax.client.utils.entity.SortPriority;
import mathax.client.utils.entity.Target;
import mathax.client.utils.entity.TargetUtils;
import mathax.client.utils.misc.Vec3;
import mathax.client.utils.player.PlayerUtils;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.DoubleSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;



/* ----------------------------------------------------------------------------------------------------------------------------- */
/* Used some code from both the Envy AimAssist and TriggerBot (I don't know who the Envy devs skidded those from):                       */
/* https://github.com/Volcanware/Envy-Client/blob/1.19.3/src/main/java/mathax/client/systems/modules/combat/TriggerBot.java     */
/* https://github.com/Volcanware/Envy-Client/blob/1.19.3/src/main/java/mathax/client/systems/modules/ghost/AimAssist.java       */
/* Volcan helped me with the new Antibot system by giving me ideas on how to do it along with providing the code for the stop on Hitbox option:                                             */
/* https://github.com/Volcan4436
/* Lagoon for pointing out my lack of player clearing lol:                                                                                              */
/* https://github.com/lattiahirvio
/* ----------------------------------------------------------------------------------------------------------------------------- */

public class BidenAura extends Module {
    private final Vec3 vec3d1 = new Vec3();
    private Entity target;
    private long lastAttackTime = 0;
    private final Random random = new Random();
    private final Set<UUID> trackedPlayers = new HashSet<>();
    private final Map<String, PlayerEntity> validPlayers = new HashMap<>();
    private boolean justEnabled = false;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgSpeed = settings.createGroup("Aim");
    private final SettingGroup sgAttack = settings.createGroup("Attack Delay");
    private final SettingGroup sgBypass = settings.createGroup("Bypassing");

    // General
    private final Setting<Object2BooleanMap<EntityType<?>>> entities = sgGeneral.add(new EntityTypeListSetting.Builder()
        .name("entities")
        .description("Entities to target.")
        .defaultValue(Utils.asO2BMap(EntityType.PLAYER))
        .build()
    );

    private final Setting<Boolean> randomizeAimSpeed = sgSpeed.add(new BoolSetting.Builder()
        .name("randomize-aim-speed")
        .description("Randomize the aim speed.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> jitterWhenAiming = sgBypass.add(new BoolSetting.Builder()
        .name("jitter-when-aiming")
        .description("Enable jitter when aiming.")
        .defaultValue(false)
        .build()
    );

    // Add new setting for autoBlock
    private final Setting<Boolean> autoBlock = sgGeneral.add(new BoolSetting.Builder()
        .name("auto-block")
        .description("Automatically block with a sword when aiming at a target.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> jitter = sgBypass.add(new BoolSetting.Builder()
        .name("jitter")
        .description("Enable jitter when attacking.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> maxPitchDeviation = sgBypass.add(new DoubleSetting.Builder()
        .name("max-pitch-deviation")
        .description("Maximum pitch deviation for jitter.")
        .defaultValue(0.069420)
        .min(0)
        .sliderRange(0, 1)
        .visible(jitter::get)
        .build()
    );

    private final Setting<Double> maxYawDeviation = sgBypass.add(new DoubleSetting.Builder()
        .name("max-yaw-deviation")
        .description("Maximum yaw deviation for jitter.")
        .defaultValue(0.069420)
        .min(0)
        .sliderRange(0, 1)
        .visible(jitter::get)
        .build()
    );

    private final Setting<Double> minPitchDeviation = sgBypass.add(new DoubleSetting.Builder()
        .name("min-pitch-deviation")
        .description("Minimum pitch deviation for jitter.")
        .defaultValue(0)
        .min(0)
        .sliderRange(0, 1)
        .visible(jitter::get)
        .build()
    );

    private final Setting<Double> minYawDeviation = sgBypass.add(new DoubleSetting.Builder()
        .name("min-yaw-deviation")
        .description("Minimum yaw deviation for jitter.")
        .defaultValue(0)
        .min(0)
        .sliderRange(0, 1)
        .visible(jitter::get)
        .build()
    );

    private final Setting<Double> minDelay = sgSpeed.add(new DoubleSetting.Builder()
        .name("min-aim-change-delay")
        .description("Minimum delay in milliseconds for aim speed randomization.")
        .defaultValue(0)
        .min(0)
        .sliderRange(0, 100)
        .visible(randomizeAimSpeed::get)
        .build()
    );

    private final Setting<Double> maxDelay = sgSpeed.add(new DoubleSetting.Builder()
        .name("max-aim-change-delay")
        .description("Maximum delay in milliseconds for aim speed randomization.")
        .defaultValue(5.0)
        .min(0)
        .sliderRange(0, 10)
        .visible(randomizeAimSpeed::get)
        .build()
    );

    private final Setting<Double> minAimSpeed = sgSpeed.add(new DoubleSetting.Builder()
        .name("min-aim-speed")
        .description("Minimum aim speed for randomization.")
        .defaultValue(0)
        .min(0)
        .sliderRange(0, 10)
        .visible(randomizeAimSpeed::get)
        .build()
    );

    private final Setting<Double> maxAimSpeed = sgSpeed.add(new DoubleSetting.Builder()
        .name("max-aim-speed")
        .description("Maximum aim speed for randomization.")
        .defaultValue(10.0)
        .min(0)
        .sliderRange(0, 10)
        .visible(randomizeAimSpeed::get)
        .build()
    );

    private final Setting<Double> range = sgGeneral.add(new DoubleSetting.Builder()
        .name("range")
        .description("The range at which an entity can be targeted.")
        .defaultValue(3)
        .min(0)
        .sliderRange(0, 10)
        .build()
    );

    private final SettingGroup sgAntiBot = settings.createGroup("Antibot");


// Move the chillOnHitbox setting definition above the chillAimSpeed setting
private final Setting<Boolean> chillOnHitbox = sgGeneral.add(new BoolSetting.Builder()
    .name("chill-on-hitbox")
    .description("Use a custom aim speed when the crosshair is on the hitbox of the target.")
    .defaultValue(false)
    .build()
);

private final Setting<Double> chillAimSpeed = sgGeneral.add(new DoubleSetting.Builder()
    .name("chill-aim-speed")
    .description("Custom aim speed when the crosshair is on the hitbox of the target.")
    .defaultValue(0.020)
    .min(0)
    .sliderRange(0, 0.050)
    .visible(chillOnHitbox::get)
    .build()
);

    private final Setting<Boolean> ignoreInvisible = sgBypass.add(new BoolSetting.Builder()
        .name("ignore-invisible")
        .description("Ignore invisible entities.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> ignoreWalls = sgBypass.add(new BoolSetting.Builder()
        .name("ignore-walls")
        .description("Whether or not to ignore aiming through walls.")
        .defaultValue(true)
        .build()
    );

    // Disable on RubberBand
    private final Setting<Boolean> disableOnRubberBand = sgBypass.add(new BoolSetting.Builder()
        .name("flag-toggle")
        .description("Disables the module on anticheat flag (may conflict with some servers).")
        .defaultValue(false)
        .build()
    );

    private final Setting<SortPriority> priority = sgGeneral.add(new EnumSetting.Builder<SortPriority>()
        .name("priority")
        .description("How to select target from entities in range.")
        .defaultValue(SortPriority.Lowest_Health)
        .build()
    );

    private final Setting<Target> bodyTarget = sgGeneral.add(new EnumSetting.Builder<Target>()
        .name("aim-target")
        .description("Which part of the entities body to aim at.")
        .defaultValue(Target.Head)
        .build()
    );

    // New AntiBot Setting
    private final Setting<Boolean> tablistCheck = sgAntiBot.add(new BoolSetting.Builder()
        .name("tablist-check")
        .description("Avoid targeting players not in the tab list.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> taggingCheck = sgAntiBot.add(new BoolSetting.Builder()
        .name("tagging-check")
        .description("Tag players as valid when entering render distance, avoid attacking untagged duplicates.")
        .defaultValue(true)
        .build()
    );

    // Aim Speed
    private final Setting<Boolean> instant = sgSpeed.add(new BoolSetting.Builder()
        .name("instant-look")
        .description("Instantly looks at the entity.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> speed = sgSpeed.add(new DoubleSetting.Builder()
        .name("speed")
        .description("How fast to aim at the entity.")
        .defaultValue(5)
        .min(0)
        .sliderRange(0, 10)
        .visible(() -> !instant.get())
        .visible(() -> !randomizeAimSpeed.get())
        .build()
    );

    // Attack Delay
    private final Setting<Double> minCPS = sgAttack.add(new DoubleSetting.Builder()
        .name("min-cps")
        .description("Minimum clicks per second.")
        .defaultValue(7)
        .min(1)
        .sliderRange(1, 20)
        .build()
    );



    private final Setting<Double> maxCPS = sgAttack.add(new DoubleSetting.Builder()
        .name("max-cps")
        .description("Maximum clicks per second.")
        .defaultValue(15)
        .min(1)
        .sliderRange(1, 20)
        .build()
    );

    public BidenAura() {
        super(Categories.Combat, Items.COMMAND_BLOCK, "biden-aura", "Does KillAura things but worse and for 1.8.9 servers by Biden");
    }

    @Override
    public boolean onActivate() {
        justEnabled = true;

        return true;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (justEnabled) {
            trackedPlayers.clear();
            validPlayers.clear();
            justEnabled = false;
        }

        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity) entity;
                UUID playerUUID = playerEntity.getUuid();
                String playerName = playerEntity.getName().getString();

                if (!trackedPlayers.contains(playerUUID)) {
                    trackedPlayers.add(playerUUID);
                    if (!validPlayers.containsKey(playerName) || validPlayers.get(playerName).age < playerEntity.age) {
                        validPlayers.put(playerName, playerEntity);
                    }
                }
            }
        }

        target = TargetUtils.get(entity -> {
            if (!entity.isAlive()) return false;
            if (mc.player.distanceTo(entity) >= range.get()) return false;
            if (!ignoreWalls.get() && !PlayerUtils.canSeeEntity(entity)) return false;
            if (ignoreInvisible.get() && entity.isInvisible()) return false;
            if (entity == mc.player || !entities.get().getBoolean(entity.getType())) return false;
            if (entity instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity) entity;
                if (!Friends.get().shouldAttack(playerEntity)) return false;
                if (tablistCheck.get() && !isInTabList(playerEntity)) return false;
                if (taggingCheck.get() && !isValidPlayer(playerEntity)) return false;
            }
            return true;
        }, priority.get());

        if (target != null) {
            aim(target, instant.get());
            attackIfNeeded(target);
        }

        // Remove players that have left render distance
        trackedPlayers.removeIf(uuid -> mc.world.getPlayerByUuid(uuid) == null);
    }

    @EventHandler
    private void onRender3D(Render3DEvent event) {
        if (target != null) aim(target, instant.get());
    }

    // Modify the aim method
    private void aim(Entity target, boolean instant) {
        double aimSpeed;
        if (chillOnHitbox.get() && isOverEntity(target)) {
            aimSpeed = chillAimSpeed.get();
        } else {
            aimSpeed = speed.get();
            if (randomizeAimSpeed.get()) {
                aimSpeed = minAimSpeed.get() + (maxAimSpeed.get() - minAimSpeed.get()) * random.nextDouble();
            }
        }

        if (autoBlock.get() && isOverEntity(target) && isHoldingSword()) {
            mc.options.useKey.setPressed(true);
        } else {
            mc.options.useKey.setPressed(false);
        }

        Vec3d targetPos = target.getPos();

        switch (bodyTarget.get()) {
            case Head -> targetPos = targetPos.add(0, target.getEyeHeight(target.getPose()), 0);
            case Body -> targetPos = targetPos.add(0, target.getEyeHeight(target.getPose()) / 2, 0);
        }

        double deltaX = targetPos.x - mc.player.getX();
        double deltaZ = targetPos.z - mc.player.getZ();
        double deltaY = targetPos.y - (mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()));

        double angle = Math.toDegrees(Math.atan2(deltaZ, deltaX)) - 90;
        double deltaAngle;
        double toRotate;

        if (jitterWhenAiming.get()) {
            float pitchDeviation = (float) (minPitchDeviation.get() + (maxPitchDeviation.get() - minPitchDeviation.get()) * random.nextDouble());
            float yawDeviation = (float) (minYawDeviation.get() + (maxYawDeviation.get() - minYawDeviation.get()) * random.nextDouble());

            if (random.nextBoolean()) {
                pitchDeviation = -pitchDeviation;
            }
            if (random.nextBoolean()) {
                yawDeviation = -yawDeviation;
            }

            if (mc.player.isOnGround() || random.nextDouble() < 0.5) {
                mc.player.setPitch(mc.player.getPitch() + pitchDeviation);
            }
            mc.player.setYaw(mc.player.getYaw() + yawDeviation);
        }

        if (instant) mc.player.setYaw((float) angle);
        else {
            deltaAngle = MathHelper.wrapDegrees(angle - mc.player.getYaw());
            toRotate = aimSpeed * (deltaAngle >= 0 ? 1 : -1);
            if ((toRotate >= 0 && toRotate > deltaAngle) || (toRotate < 0 && toRotate < deltaAngle)) toRotate = deltaAngle;
            mc.player.setYaw(mc.player.getYaw() + (float) toRotate);
        }

        double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        angle = -Math.toDegrees(Math.atan2(deltaY, horizontalDistance));

        if (instant) mc.player.setPitch((float) angle);
        else {
            deltaAngle = MathHelper.wrapDegrees(angle - mc.player.getPitch());
            toRotate = aimSpeed * (deltaAngle >= 0 ? 1 : -1);
            if ((toRotate >= 0 && toRotate > deltaAngle) || (toRotate < 0 && toRotate < deltaAngle)) toRotate = deltaAngle;
            mc.player.setPitch(mc.player.getPitch() + (float) toRotate);
        }
    }

    // Helper method to check if the player is holding a sword
    private boolean isHoldingSword() {
        ItemStack heldItem = mc.player.getMainHandStack();
        return heldItem.getItem() == Items.WOODEN_SWORD ||
            heldItem.getItem() == Items.STONE_SWORD ||
            heldItem.getItem() == Items.IRON_SWORD ||
            heldItem.getItem() == Items.DIAMOND_SWORD ||
            heldItem.getItem() == Items.GOLDEN_SWORD;
    }

    private void attackIfNeeded(Entity entity) {
        if (!isOverEntity(entity)) {
            return; // Only attack if the crosshair is over the target entity
        }

        long currentTime = System.currentTimeMillis();
        double minDelay = 590.0 / maxCPS.get();
        double maxDelay = 825.0 / minCPS.get();
        double randomDelay = minDelay + (maxDelay - minDelay) * random.nextDouble();

        if (currentTime - lastAttackTime >= randomDelay) {
            if (jitter.get()) {
                float pitchDeviation = (float) (minPitchDeviation.get() + (maxPitchDeviation.get() - minPitchDeviation.get()) * random.nextDouble());
                float yawDeviation = (float) (minYawDeviation.get() + (maxYawDeviation.get() - minYawDeviation.get()) * random.nextDouble());

                // Randomly decide if the deviation should be positive or negative
                if (random.nextBoolean()) {
                    pitchDeviation = -pitchDeviation;
                }
                if (random.nextBoolean()) {
                    yawDeviation = -yawDeviation;
                }

                mc.player.setPitch(mc.player.getPitch() + pitchDeviation);
                mc.player.setYaw(mc.player.getYaw() + yawDeviation);
            }

            mc.interactionManager.attackEntity(mc.player, entity);
            mc.player.swingHand(Hand.MAIN_HAND);
            lastAttackTime = currentTime;
        }
    }

    private boolean isInTabList(PlayerEntity player) {
        for (PlayerListEntry entry : mc.player.networkHandler.getPlayerList()) {
            if (entry.getProfile().getName().equals(player.getName().getString())) {
                return true;
            }
        }
        return false;
    }

    private boolean isOverEntity(Entity target) {
        if (mc.crosshairTarget == null) return false;
        if (mc.crosshairTarget.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) mc.crosshairTarget).getEntity();
            return entity.equals(target);
        }
        return false;
    }



    private boolean isValidPlayer(PlayerEntity player) {
        String playerName = player.getName().getString();
        PlayerEntity validPlayer = validPlayers.get(playerName);
        return validPlayer == null || validPlayer.equals(player) || player.age >= validPlayer.age;
    }

    @EventHandler
    private void onPacketReceive(PacketEvent.Receive event) {
        if (AnticheatUtils.getSetback(event) && disableOnRubberBand.get()) {
            info(Formatting.RED + "Setback Detected! Disabling");
            toggle();
        }
    }

    @Override
    public String getInfoString() {
        return EntityUtils.getName(target);
    }
}

