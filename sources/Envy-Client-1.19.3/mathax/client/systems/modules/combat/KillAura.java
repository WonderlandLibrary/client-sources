package mathax.client.systems.modules.combat;

import baritone.api.BaritoneAPI;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import mathax.client.eventbus.EventHandler;
import mathax.client.events.game.GameLeftEvent;
import mathax.client.events.packets.PacketEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.friends.Friends;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.entity.EntityUtils;
import mathax.client.utils.entity.SortPriority;
import mathax.client.utils.entity.Target;
import mathax.client.utils.entity.TargetUtils;
import mathax.client.utils.network.PacketUtils;
import mathax.client.utils.player.FindItemResult;
import mathax.client.utils.player.InvUtils;
import mathax.client.utils.player.PlayerUtils;
import mathax.client.utils.player.Rotations;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;

import java.util.ArrayList;
import java.util.List;

public class KillAura extends Module {
    private final List<Entity> targets = new ArrayList<>();

    private int hitDelayTimer, switchTimer;
    private short count = 0;

    private boolean wasPathing;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgTargeting = settings.createGroup("Targeting");
    private final SettingGroup sgDelay = settings.createGroup("Delay");

    // General

    private final Setting<Weapon> weapon = sgGeneral.add(new EnumSetting.Builder<Weapon>()
        .name("weapon")
        .description("Only attacks an entity when a specified item is in your hand.")
        .defaultValue(Weapon.Sword_and_Axe)
        .build()
    );

    private final Setting<Boolean> autoSwitch = sgGeneral.add(new BoolSetting.Builder()
        .name("auto-switch")
        .description("Switches to your selected weapon when attacking the target.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> onlyOnClick = sgGeneral.add(new BoolSetting.Builder()
        .name("only-on-click")
        .description("Only attacks when hold left click.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> onlyWhenLook = sgGeneral.add(new BoolSetting.Builder()
        .name("only-when-look")
        .description("Only attacks when you are looking at the entity.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> randomTeleport = sgGeneral.add(new BoolSetting.Builder()
        .name("random-teleport")
        .description("Randomly teleport around the target.")
        .defaultValue(false)
        .visible(() -> !onlyWhenLook.get())
        .build()
    );

    private final Setting<Boolean> ignoreShield = sgGeneral.add(new BoolSetting.Builder()
        .name("ignore-shield")
        .description("Attacks only if the blow is not blocked by a shield.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> ignoreinvisible = sgGeneral.add(new BoolSetting.Builder()
        .name("Ignore Invisible")
        .description("Attacks even if the target is invisible.")
        .defaultValue(true)
        .build()
    );


    private final Setting<Boolean> noRightClick = sgGeneral.add(new BoolSetting.Builder()
        .name("no-right-click")
        .description("Does not attack if the right mouse button is pressed (Using a shield, you eat food or drink a potion).")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> AutoSprint = sgGeneral.add(new BoolSetting.Builder()
        .name("Auto-Sprint")
        .description("Automatically sprints on attack.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> AttemptVelocity = sgGeneral.add(new BoolSetting.Builder()
        .name("Attempt Velocity")
        .description("Attempts to cancel velocity on attack || Good For Sumo Duels where KB is needed")
        .defaultValue(false)
        .build()
    );


    private final Setting<RotationMode> rotation = sgGeneral.add(new EnumSetting.Builder<RotationMode>()
        .name("rotate")
        .description("Determines when you should rotate towards the target.")
        .defaultValue(RotationMode.Always)
        .build()
    );

    private final Setting<Double> hitChance = sgGeneral.add(new DoubleSetting.Builder()
        .name("hit-chance")
        .description("The probability of your hits landing.")
        .defaultValue(100)
        .range(1, 100)
        .sliderRange(1, 100)
        .build()
    );

    private final Setting<Boolean> toggleOnDisconnect = sgGeneral.add(new BoolSetting.Builder()
        .name("disconnect-toggle")
        .description("Toggles the module on disconnect.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> pauseOnCombat = sgGeneral.add(new BoolSetting.Builder()
        .name("combat-pause")
        .description("Freezes Baritone temporarily until you are finished attacking the entity.")
        .defaultValue(true)
        .build()
    );

    // Targeting

    private final Setting<Object2BooleanMap<EntityType<?>>> entities = sgTargeting.add(new EntityTypeListSetting.Builder()
        .name("entities")
        .description("Entities to attack.")
        .defaultValue(EntityType.PLAYER)
        .onlyAttackable()
        .build()
    );

    public final Setting<Double> targetRange = sgTargeting.add(new DoubleSetting.Builder()
        .name("range")
        .description("The maximum range the entity can be to attack it.")
        .defaultValue(4.5)
        .min(0)
        .sliderRange(0, 6)
        .build()
    );

    private final Setting<Double> wallsRange = sgTargeting.add(new DoubleSetting.Builder()
        .name("walls-range")
        .description("The maximum range the entity can be attacked through walls.")
        .defaultValue(3.5)
        .min(0)
        .sliderRange(0, 6)
        .build()
    );

    private final Setting<Boolean> AirStrict = sgTargeting.add(new BoolSetting.Builder()
        .name("Air-Strict")
        .description("Only attacks if your on the ground. Helps with some weird Anticheat Setups")
        .defaultValue(false)
        .build()
    );

    private final Setting<SortPriority> priority = sgTargeting.add(new EnumSetting.Builder<SortPriority>()
        .name("priority")
        .description("How to filter targets within range.")
        .defaultValue(SortPriority.Lowest_Health)
        .build()
    );

    private final Setting<Integer> maxTargets = sgTargeting.add(new IntSetting.Builder()
        .name("max-targets")
        .description("How many entities to target at once.")
        .defaultValue(1)
        .min(1)
        .sliderRange(1, 5)
        .build()
    );

    private final Setting<Boolean> ignorePassive = sgGeneral.add(new BoolSetting.Builder()
        .name("ignore-passive")
        .description("Will only attack sometimes passive mobs if they are targeting you.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> ignoreTamed = sgGeneral.add(new BoolSetting.Builder()
        .name("ignore-tamed")
        .description("Will avoid attacking mobs you tamed.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> babies = sgTargeting.add(new BoolSetting.Builder()
        .name("babies")
        .description("Whether or not to attack baby variants of the entity.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> nametagged = sgTargeting.add(new BoolSetting.Builder()
        .name("nametagged")
        .description("Whether or not to attack mobs with a name tag.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> offground = sgTargeting.add(new BoolSetting.Builder()
        .name("Ignore Air")
        .description("Whether or not to attack mobs that are on the ground.")
        .defaultValue(false)
        .build()
    );

    //Shit Implimentation needs rework
    private final Setting<Boolean> Antibot = sgTargeting.add(new BoolSetting.Builder()
        .name("Anti-Bot || (Experimental)")
        .description("Does Not Attack if They have not existed for over 10 ticks || Shitty Implimentation")
        .defaultValue(false)
        .build()
    );

    // Delay

    private final Setting<Boolean> smartDelay = sgDelay.add(new BoolSetting.Builder()
        .name("smart-delay")
        .description("Uses the vanilla cooldown to attack entities.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Integer> hitDelay = sgDelay.add(new IntSetting.Builder()
        .name("hit-delay")
        .description("How fast you hit the entity in ticks.")
        .defaultValue(0)
        .min(0)
        .sliderRange(0, 60)
        .visible(() -> !smartDelay.get())
        .build()
    );

    private final Setting<Boolean> randomDelayEnabled = sgDelay.add(new BoolSetting.Builder()
        .name("random-delay-enabled")
        .description("Adds a random delay between hits to attempt to bypass anti-cheats.")
        .defaultValue(false)
        .visible(() -> !smartDelay.get())
        .build()
    );

    private final Setting<Integer> randomDelayMax = sgDelay.add(new IntSetting.Builder()
        .name("random-delay-max")
        .description("The maximum value for random delay.")
        .defaultValue(4)
        .min(0)
        .sliderRange(0, 20)
        .visible(() -> randomDelayEnabled.get() && !smartDelay.get())
        .build()
    );

    private final Setting<Integer> switchDelay = sgDelay.add(new IntSetting.Builder()
        .name("switch-delay")
        .description("How many ticks to wait before hitting an entity after switching hotbar slots.")
        .defaultValue(0)
        .min(0)
        .sliderRange(0, 40)
        .build()
    );

    private final Setting<Integer> speed = sgDelay.add(new IntSetting.Builder()
        .name("Spin Speed")
        .description("How fast you spin.")
        .defaultValue(25)
        .min(0)
        .sliderRange(0, 100)
        .build()
    );


    private final Setting<Boolean> autoBlock = sgGeneral.add(new BoolSetting.Builder()
        .name("AutoBlock")
        .description("1.8 module on 1.9+ wow")
        .defaultValue(false)
        .build()
    );

    private final Setting<BlockMode> blockMode = sgGeneral.add(new EnumSetting.Builder<BlockMode>()
        .name("Blocking Mode")
        .description(".")
        .defaultValue(BlockMode.Constant)
        .visible(autoBlock::get)
        .build()
    );

    public enum BlockMode {
        Constant,
        NotOnHit,
        Packet,
    }


    public KillAura() {
        super(Categories.Combat, Items.DIAMOND_SWORD, "Envy-kill-aura", "Attacks specified entities around you.");
    }

    @Override
    public void onDeactivate() {
        hitDelayTimer = 0;
        targets.clear();
    }

    @EventHandler
    private void onDisconnect(GameLeftEvent event) {
        if (toggleOnDisconnect.get()) toggle();
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (!mc.player.isAlive() || PlayerUtils.getGameMode() == GameMode.SPECTATOR) return;

        TargetUtils.getList(targets, this::entityCheck, priority.get(), maxTargets.get());

        if (targets.isEmpty()) {
            if (wasPathing) {
                BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("resume");
                wasPathing = false;
            }

            return;
        }
        if (autoBlock.get()) {
            if (BlockMode.Constant == blockMode.get()) {
                if (mc.player.getOffHandStack().getItem().equals(Items.SHIELD) && mc.player.handSwingProgress > 0) {
                    mc.options.useKey.setPressed(true);
                }
            }
            if (BlockMode.Packet == blockMode.get()) {
                if (mc.player.getOffHandStack().getItem().equals(Items.SHIELD) && mc.player.handSwingProgress > 0) {
                    //mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN)); not this

                }
            }
        }

        if (blockMode.get().equals(BlockMode.NotOnHit))
            mc.options.useKey.setPressed(false);

        if (pauseOnCombat.get() && BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing() && !wasPathing) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("pause");
            wasPathing = true;
        }

        Entity primary = targets.get(0);
        if (rotation.get() == RotationMode.Always) rotate(primary, null);

        if (onlyOnClick.get() && !mc.options.attackKey.isPressed()) return;
        if (onlyWhenLook.get()) {
            primary = mc.targetedEntity;

            if (primary == null) return;
            if (!entityCheck(primary)) return;

            targets.clear();
            targets.add(primary);
        }

        if (autoSwitch.get()) {
            FindItemResult weaponResult = InvUtils.findInHotbar(itemStack -> {
                Item item = itemStack.getItem();

                return switch (weapon.get()) {
                    case Sword -> item instanceof SwordItem;
                    case Axe -> item instanceof AxeItem;
                    case Hoe -> item instanceof HoeItem;
                    case Sword_and_Axe -> item instanceof SwordItem || item instanceof AxeItem;
                    case All_Three -> item instanceof SwordItem || item instanceof AxeItem || item instanceof HoeItem;
                    default -> true;
                };
            });

            InvUtils.swap(weaponResult.slot(), false);
        }

        if (!itemInHand()) return;
        if (delayCheck()) targets.forEach(this::attack);
        if (randomTeleport.get() && !onlyWhenLook.get())
            mc.player.setPosition(primary.getX() + targetRange.get(), primary.getY(), primary.getZ() + targetRange.get());
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send event) {
        if (event.packet instanceof UpdateSelectedSlotC2SPacket) switchTimer = switchDelay.get();
    }

    private double randomOffset() {
        return Math.random() * 4 - 2;
    }

    private boolean entityCheck(Entity entity) {
        if (entity.equals(mc.player) || entity.equals(mc.cameraEntity)) return false;
        if ((entity instanceof LivingEntity && ((LivingEntity) entity).isDead()) || !entity.isAlive()) return false;
        if (PlayerUtils.distanceTo(entity) > targetRange.get()) return false;
        if (!entities.get().getBoolean(entity.getType())) return false;
        if (noRightClick.get() && mc.options.useKey.isPressed()) return false;
        if (ignoreinvisible.get() && entity.isInvisible()) return false;
        if (Antibot.get() && entity instanceof PlayerEntity && entity.age < 10) return false;
        if (offground.get() && !entity.isOnGround()) return false;
        if (AirStrict.get() && !mc.player.isOnGround()) return false;

        if (rotation.get() == RotationMode.Spin) {
            count = (short) (count + speed.get());
            if (count > 180) count = (short) -180;
            Rotations.rotate(count, 0.0);
        }

        if (!nametagged.get() && entity.hasCustomName() && !(entity instanceof PlayerEntity)) return false;
        if (!PlayerUtils.canSeeEntity(entity) && PlayerUtils.distanceTo(entity) > wallsRange.get()) return false;
        if (ignoreTamed.get() && entity instanceof Tameable tameable && tameable.getOwnerUuid() != null && tameable.getOwnerUuid().equals(mc.player.getUuid()))
            return false;
        if (ignorePassive.get()) {
            if (entity instanceof EndermanEntity enderman && !enderman.isAngryAt(mc.player)) return false;
            if (entity instanceof ZombifiedPiglinEntity piglin && !piglin.isAngryAt(mc.player)) return false;
            if (entity instanceof WolfEntity wolf && !wolf.isAttacking()) return false;
        }
        if (entity instanceof PlayerEntity) {
            if (((PlayerEntity) entity).isCreative()) return false;
            if (!Friends.get().shouldAttack((PlayerEntity) entity)) return false;
            if (ignoreShield.get() && shieldCheck((PlayerEntity) entity)) return false;
        }
        return !(entity instanceof AnimalEntity) || babies.get() || !((AnimalEntity) entity).isBaby();
    }

    public boolean shieldCheck(PlayerEntity player) {
        if (player.isBlocking()) {
            Vec3d persistentProjectileEntity = mc.player.getPos();
            if (persistentProjectileEntity != null) {
                Vec3d vec3d = player.getRotationVec(1.0F);
                Vec3d vec3d2 = persistentProjectileEntity.relativize(player.getPos()).normalize();
                vec3d2 = new Vec3d(vec3d2.x, 0.0D, vec3d2.z);
                return vec3d2.dotProduct(vec3d) < 0.0D;
            }
        }

        return false;
    }

    private boolean delayCheck() {
        if (switchTimer > 0) {
            switchTimer--;
            return false;
        }

        if (smartDelay.get()) return mc.player.getAttackCooldownProgress(0.5f) >= 1;

        if (hitDelayTimer > 0) {
            hitDelayTimer--;
            return false;
        } else {
            hitDelayTimer = hitDelay.get();
            if (randomDelayEnabled.get()) hitDelayTimer += Math.round(Math.random() * randomDelayMax.get());
            return true;
        }
    }

    private void attack(Entity target) {
        if (Math.random() > hitChance.get() / 100) return;

        if (rotation.get() == RotationMode.On_Hit) rotate(target, () -> hitEntity(target));
        else hitEntity(target);

        if (AutoSprint.get()) {
            assert mc.player != null;
            mc.player.setSprinting(true);
        }
        if (AttemptVelocity.get()) {
            assert mc.player != null;
            mc.player.setVelocity(0, 0, 0);
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
        return switch (weapon.get()) {
            case Sword -> mc.player.getMainHandStack().getItem() instanceof SwordItem;
            case Axe -> mc.player.getMainHandStack().getItem() instanceof AxeItem;
            case Hoe -> mc.player.getMainHandStack().getItem() instanceof HoeItem;
            case Sword_and_Axe ->
                mc.player.getMainHandStack().getItem() instanceof SwordItem || mc.player.getMainHandStack().getItem() instanceof AxeItem;
            case All_Three ->
                mc.player.getMainHandStack().getItem() instanceof SwordItem || mc.player.getMainHandStack().getItem() instanceof AxeItem || mc.player.getMainHandStack().getItem() instanceof HoeItem;
            default -> true;
        };
    }

    public Entity getTarget() {
        if (!targets.isEmpty()) return targets.get(0);
        return null;
    }

    @Override
    public String getInfoString() {
        if (!targets.isEmpty()) EntityUtils.getName(getTarget());
        return null;
    }

    public enum Weapon {
        Sword("Sword"),
        Axe("Axe"),
        Hoe("Hoe"),
        Sword_and_Axe("Sword and Axe"),
        All_Three("All Three"),
        Any("Any");

        private final String title;

        Weapon(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    public enum RotationMode {
        Always("Always"),
        On_Hit("On Hit"),
        Spin("Spin"),
        None("None");

        private final String title;

        RotationMode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
