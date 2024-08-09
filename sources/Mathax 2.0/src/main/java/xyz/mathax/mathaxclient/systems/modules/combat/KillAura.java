package xyz.mathax.mathaxclient.systems.modules.combat;

import baritone.api.BaritoneAPI;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.packets.PacketEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.systems.enemies.Enemies;
import xyz.mathax.mathaxclient.systems.friends.Friends;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.entity.EntityUtils;
import xyz.mathax.mathaxclient.utils.entity.SortPriority;
import xyz.mathax.mathaxclient.utils.entity.Target;
import xyz.mathax.mathaxclient.utils.entity.TargetUtils;
import xyz.mathax.mathaxclient.utils.player.FindItemResult;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;
import xyz.mathax.mathaxclient.utils.player.Rotations;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.world.GameMode;
import xyz.mathax.mathaxclient.settings.*;

import java.util.ArrayList;
import java.util.List;

public class KillAura extends Module {
    private final List<Entity> targets = new ArrayList<>();

    private int hitDelayTimer, switchTimer;

    private boolean wasPathing = false;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup targetingSettings = settings.createGroup("Targeting");
    private final SettingGroup delaySettings = settings.createGroup("Delay");

    // General

    private final Setting<Weapon> weaponSetting = generalSettings.add(new EnumSetting.Builder<Weapon>()
            .name("Weapon")
            .description("Only attacks an entity when a specified weapon is in your hand.")
            .defaultValue(Weapon.Both)
            .build()
    );

    private final Setting<Boolean> autoSwitchSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Auto switch")
            .description("Switches to your selected weapon when attacking the target.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> onlyOnClickSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Only on click")
            .description("Only attacks when holding left click.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> onlyWhenLookSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Only when look")
            .description("Only attacks when you are looking at the entity.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> randomTeleportSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Random teleport")
            .description("Randomly teleport around the target.")
            .defaultValue(false)
            .visible(() -> !onlyWhenLookSetting.get())
            .build()
    );

    private final Setting<RotationMode> rotationSetting = generalSettings.add(new EnumSetting.Builder<RotationMode>()
            .name("Rotate")
            .description("Determines when you should rotate towards the target.")
            .defaultValue(RotationMode.Always)
            .build()
    );

    private final Setting<Double> hitChanceSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Hit chance")
            .description("The probability of your hits landing.")
            .defaultValue(100)
            .range(1, 100)
            .sliderRange(1, 100)
            .build()
    );

    private final Setting<Boolean> pauseOnCombatSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Pause on combat")
            .description("Freezes Baritone temporarily until you are finished attacking the entity.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> noRightClickSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Pause on use")
            .description("Does not attack if using an item.")
            .defaultValue(true)
            .build()
    );

    // Targeting

    private final Setting<Object2BooleanMap<EntityType<?>>> entitiesSetting = targetingSettings.add(new EntityTypeListSetting.Builder()
            .name("Entities")
            .description("Entities to attack.")
            .onlyAttackable()
            .build()
    );

    private final Setting<Double> rangeSetting = targetingSettings.add(new DoubleSetting.Builder()
            .name("Range")
            .description("The maximum range the entity can be to attack it.")
            .defaultValue(4.5)
            .min(0)
            .sliderRange(0, 6)
            .build()
    );

    private final Setting<Double> wallsRangeSetting = targetingSettings.add(new DoubleSetting.Builder()
            .name("Walls range")
            .description("The maximum range the entity can be attacked through walls.")
            .defaultValue(3.5)
            .min(0)
            .sliderRange(0, 6)
            .build()
    );

    private final Setting<SortPriority> prioritySetting = targetingSettings.add(new EnumSetting.Builder<SortPriority>()
            .name("Priority")
            .description("How to filter targets within range.")
            .defaultValue(SortPriority.Lowest_Health)
            .build()
    );

    private final Setting<Integer> maxTargetsSetting = targetingSettings.add(new IntSetting.Builder()
            .name("Max targets")
            .description("How many entities to target at once.")
            .defaultValue(1)
            .min(1)
            .sliderRange(1, 5)
            .build()
    );

    private final Setting<Boolean> babiesSetting = targetingSettings.add(new BoolSetting.Builder()
            .name("Babies")
            .description("Whether or not to attack baby variants of the entity.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> nametaggedSetting = targetingSettings.add(new BoolSetting.Builder()
            .name("Nametagged")
            .description("Whether or not to attack mobs with a name tag.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> passiveSetting = targetingSettings.add(new BoolSetting.Builder()
            .name("Passive")
            .description("Will attack sometimes passive mobs.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> tamedSetting = targetingSettings.add(new BoolSetting.Builder()
            .name("Tamed")
            .description("Will attack mobs you tamed.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> ignoreShieldSetting = targetingSettings.add(new BoolSetting.Builder()
            .name("Ignore shield")
            .description("Attack only if the blow is not blocked by a shield.")
            .defaultValue(true)
            .build()
    );

    // Delay

    private final Setting<Boolean> smartDelaySetting = delaySettings.add(new BoolSetting.Builder()
            .name("Smart delay")
            .description("Use the vanilla cooldown to attack entities.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Integer> hitDelaySetting = delaySettings.add(new IntSetting.Builder()
            .name("Hit delay")
            .description("How fast you hit the entity in ticks.")
            .defaultValue(0)
            .min(0)
            .sliderRange(0, 60)
            .visible(() -> !smartDelaySetting.get())
            .build()
    );

    private final Setting<Boolean> randomDelayEnabledSetting = delaySettings.add(new BoolSetting.Builder()
            .name("Random delay")
            .description("Add a random delay between hits to attempt to bypass anti-cheats.")
            .defaultValue(false)
            .visible(() -> !smartDelaySetting.get())
            .build()
    );

    private final Setting<Integer> randomDelayMaxSetting = delaySettings.add(new IntSetting.Builder()
            .name("Random delay max")
            .description("The maximum value for random delay.")
            .defaultValue(4)
            .min(0)
            .sliderRange(0, 20)
            .visible(() -> randomDelayEnabledSetting.get() && !smartDelaySetting.get())
            .build()
    );

    private final Setting<Integer> switchDelaySetting = delaySettings.add(new IntSetting.Builder()
            .name("Switch delay")
            .description("How many ticks to wait before hitting an entity after switching hotbar slots.")
            .defaultValue(0)
            .min(0)
            .range(0, 20)
            .build()
    );

    public KillAura(Category category) {
        super(category, "Kill Aura", "Attacks specified entities around you.");
    }

    @Override
    public void onDisable() {
        hitDelayTimer = 0;
        targets.clear();
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (!mc.player.isAlive() || PlayerUtils.getGameMode() == GameMode.SPECTATOR) {
            return;
        }

        TargetUtils.getList(targets, this::entityCheck, prioritySetting.get(), maxTargetsSetting.get());

        if (targets.isEmpty()) {
            if (wasPathing) {
                BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("resume");
                wasPathing = false;
            }

            return;
        }

        Entity primary = targets.get(0);
        if (rotationSetting.get() == RotationMode.Always) {
            rotate(primary, null);
        }

        if (onlyOnClickSetting.get() && !mc.options.attackKey.isPressed()) {
            return;
        }

        if (onlyWhenLookSetting.get()) {
            primary = mc.targetedEntity;
            if (primary == null) {
                return;
            }

            if (!entityCheck(primary)) {
                return;
            }

            targets.clear();
            targets.add(primary);
        }

        if (autoSwitchSetting.get()) {
            FindItemResult weaponResult = InvUtils.findInHotbar(itemStack -> {
                Item item = itemStack.getItem();
                return switch (weaponSetting.get()) {
                    case Axe -> item instanceof AxeItem;
                    case Sword -> item instanceof SwordItem;
                    case Both -> item instanceof AxeItem || item instanceof SwordItem;
                    default -> true;
                };
            });

            InvUtils.swap(weaponResult.slot(), false);
        }

        if (!itemInHand()) {
            return;
        }

        if (pauseOnCombatSetting.get() && BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing() && !wasPathing) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("pause");
            wasPathing = true;
        }

        if (delayCheck()) {
            targets.forEach(this::attack);
        }

        if (randomTeleportSetting.get() && !onlyWhenLookSetting.get()) {
            mc.player.setPosition(primary.getX() + randomOffset(), primary.getY(), primary.getZ() + randomOffset());
        }
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send event) {
        if (event.packet instanceof UpdateSelectedSlotC2SPacket) {
            switchTimer = switchDelaySetting.get();
        }
    }

    private double randomOffset() {
        return Math.random() * 4 - 2;
    }

    private boolean entityCheck(Entity entity) {
        if (entity.equals(mc.player) || entity.equals(mc.cameraEntity)) {
            return false;
        }

        if ((entity instanceof LivingEntity && ((LivingEntity) entity).isDead()) || !entity.isAlive()) {
            return false;
        }

        if (noRightClickSetting.get() && (mc.interactionManager.isBreakingBlock() || mc.player.isUsingItem())) {
            return false;
        }

        if (PlayerUtils.distanceTo(entity) > rangeSetting.get()) {
            return false;
        }

        if (!entitiesSetting.get().getBoolean(entity.getType())) {
            return false;
        }

        if (!nametaggedSetting.get() && entity.hasCustomName()) {
            return false;
        }

        if (!PlayerUtils.canSeeEntity(entity) && PlayerUtils.distanceTo(entity) > wallsRangeSetting.get()) {
            return false;
        }

        if (!tamedSetting.get()) {
            if (entity instanceof Tameable tameable && tameable.getOwnerUuid() != null && tameable.getOwnerUuid().equals(mc.player.getUuid())) {
                return false;
            }
        }

        if (!passiveSetting.get()) {
            if (entity instanceof EndermanEntity enderman && !enderman.isAngryAt(mc.player)) {
                return false;
            }

            if (entity instanceof ZombifiedPiglinEntity piglin && !piglin.isAngryAt(mc.player)) {
                return false;
            }

           if (entity instanceof WolfEntity wolf && !wolf.isAttacking()) {
               return false;
           }
        }

        if (entity instanceof PlayerEntity player) {
            if (player.isCreative()) {
                return false;
            }

            if (!Friends.get().shouldAttack(player) || !Enemies.get().shouldAttack(player)) {
                return false;
            }

            if (ignoreShieldSetting.get() && player.blockedByShield(DamageSource.player(mc.player))) {
                return false;
            }
        }

        return !(entity instanceof AnimalEntity animal) || babiesSetting.get() || !animal.isBaby();
    }

    private boolean delayCheck() {
        if (switchTimer > 0) {
            switchTimer--;
            return false;
        }

        if (smartDelaySetting.get()) {
            return mc.player.getAttackCooldownProgress(0.5f) >= 1;
        }

        if (hitDelayTimer > 0) {
            hitDelayTimer--;
            return false;
        } else {
            hitDelayTimer = hitDelaySetting.get();
            if (randomDelayEnabledSetting.get()) {
                hitDelayTimer += Math.round(Math.random() * randomDelayMaxSetting.get());
            }

            return true;
        }
    }

    private void attack(Entity target) {
        if (Math.random() > hitChanceSetting.get() / 100) {
            return;
        }

        if (rotationSetting.get() == RotationMode.On_Hit) {
            rotate(target, () -> hitEntity(target));
        } else {
            hitEntity(target);
        }
    }

    private void hitEntity(Entity target) {
        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
    }

    private void rotate(Entity target, Runnable callback) {
        Rotations.rotate(Rotations.getYaw(target), Rotations.getPitch(target, Target.Body), callback);
    }

    private boolean itemInHand() {
        return switch (weaponSetting.get()) {
            case Axe -> mc.player.getMainHandStack().getItem() instanceof AxeItem;
            case Sword -> mc.player.getMainHandStack().getItem() instanceof SwordItem;
            case Both -> mc.player.getMainHandStack().getItem() instanceof AxeItem || mc.player.getMainHandStack().getItem() instanceof SwordItem;
            default -> true;
        };
    }

    public Entity getTarget() {
        if (!targets.isEmpty()) {
            return targets.get(0);
        }

        return null;
    }

    @Override
    public String getInfoString() {
        if (!targets.isEmpty()) {
            return EntityUtils.getName(getTarget());
        }

        return null;
    }

    public enum Weapon {
        Sword("Sword"),
        Axe("Axe"),
        Both("Both"),
        Any("Any");

        private final String name;

        Weapon(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum RotationMode {
        Always("Always"),
        On_Hit("On Hit"),
        None("None");

        private final String name;

        RotationMode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}