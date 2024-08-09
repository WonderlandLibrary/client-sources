/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.combat;

import baritone.api.BaritoneAPI;
import meteordevelopment.meteorclient.events.entity.player.MovementInputToVelocityEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.friends.Friends;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.misc.Disabler;
import meteordevelopment.meteorclient.systems.modules.movement.Blink;
import meteordevelopment.meteorclient.systems.modules.movement.StopMotion;
import meteordevelopment.meteorclient.utils.entity.EntityUtils;
import meteordevelopment.meteorclient.utils.entity.SortPriority;
import meteordevelopment.meteorclient.utils.entity.Target;
import meteordevelopment.meteorclient.utils.entity.TargetUtils;
import meteordevelopment.meteorclient.utils.entity.fakeplayer.FakePlayerEntity;
import meteordevelopment.meteorclient.utils.math.MathUtils;
import meteordevelopment.meteorclient.utils.other.TimerMS;
import meteordevelopment.meteorclient.utils.player.*;
import meteordevelopment.meteorclient.utils.world.TickRate;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

public final class Aura extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgTargeting = settings.createGroup("Targeting");
    private final SettingGroup sgReach = settings.createGroup("Reach");
    private final SettingGroup sgTiming = settings.createGroup("Timing");
    public static boolean fakeBlock;
    // General

    private final Setting<Weapon> weapon = sgGeneral.add(new EnumSetting.Builder<Weapon>()
        .name("weapon")
        .description("Only attacks an entity when a specified weapon is in your hand.")
        .defaultValue(Weapon.Both)
        .build()
    );

    private final Setting<List<Item>> weapons = sgGeneral.add(new ItemListSetting.Builder()
        .name("Weapons")
        .description("What counts as a weapon anyway?")
        .visible(() -> weapon.get().equals(Weapon.Choose))
        .build()
    );

    private final Setting<RotationMode> rotation = sgGeneral.add(new EnumSetting.Builder<RotationMode>()
        .name("rotate")
        .description("Determines what mode to use to rotate towards the target.")
        .defaultValue(RotationMode.Always)
        .build()
    );

    private final Setting<Boolean> moveCorrection = sgGeneral.add(new BoolSetting.Builder()
        .name("move-correction")
        .description("move-correction... Grim rots automatically do this.")
        .defaultValue(false)
        .visible(() -> !rotation.get().equals(RotationMode.GrimAlways))
        .build()
    );

    private final Setting<Target> rotatepart = sgGeneral.add(new EnumSetting.Builder<Target>()
        .name("rotatepart")
        .description("What part of the targets body to rotate to.")
        .defaultValue(Target.Head)
        .build()
    );

    private final Setting<ModuleInfoStringEnum> infoStringMode = sgGeneral.add(new EnumSetting.Builder<ModuleInfoStringEnum>()
        .name("info-string-mode")
        .description("What mode the info string should be")
        .defaultValue(ModuleInfoStringEnum.TargetMode)
        .build()
    );

    private final Setting<Boolean> fakeBlockSetting = sgGeneral.add(new BoolSetting.Builder()
        .name("fake-block")
        .description("Fake Block")
        .defaultValue(false)
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
        .description("Only attacks when holding left click.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> onlyOnLook = sgGeneral.add(new BoolSetting.Builder()
        .name("only-on-look")
        .description("Only attacks when looking at an entity.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> pauseOnCombat = sgGeneral.add(new BoolSetting.Builder()
        .name("pause-baritone")
        .description("Freezes Baritone temporarily until you are finished attacking the entity.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> wTapBool = sgGeneral.add(new BoolSetting.Builder()
        .name("wTap")
        .description("Makes you deal sprinthits if on ground!")
        .defaultValue(true)
        .build()
    );

    private final Setting<wTapMode> wTap = sgGeneral.add(new EnumSetting.Builder<wTapMode>()
        .name("wTap-mode")
        .description("WTap mode!")
        .defaultValue(wTapMode.Press)
        .visible(wTapBool::get)
        .build()
    );

    private final Setting<Integer> wtapTimer = sgGeneral.add(new IntSetting.Builder()
        .name("WTap Timer")
        .description("Wtap Timer! in Milliseconds of course.")
        .defaultValue(50)
        .min(0)
        .sliderMax(400)
        .visible(wTapBool::get)
        .build()
    );

    private final Setting<wTapTimingMode> wTapTiming = sgGeneral.add(new EnumSetting.Builder<wTapTimingMode>()
        .name("wTapTiming")
        .description("Pre or Post")
        .defaultValue(wTapTimingMode.Post)
        .visible(wTapBool::get)
        .build()
    );

    public final Setting<Boolean> keepsprint = sgGeneral.add(new BoolSetting.Builder()
        .name("keepsprint")
        .description("Keeps your sprint after attacking")
        .defaultValue(false)
        .build()
    );



    private final Setting<Double> critfall = sgGeneral.add(new DoubleSetting.Builder()
        .name("critical-falldistance")
        .description("The minimum falldistance for aura to calculate the hit as a critical hit.")
        .defaultValue(0.25)
        .min(0)
        .sliderMax(1)
        .build()
    );


    private final Setting<Boolean> autoCrit = sgGeneral.add(new BoolSetting.Builder()
        .name("auto-critical")
        .description("Crits for you automatically!")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> grimcrit = sgGeneral.add(new BoolSetting.Builder()
        .name("grim-critical")
        .description("Does crits on Grim...!")
        .defaultValue(false)
        .visible(autoCrit::get)
        .build()
    );

    private final Setting<Boolean> onlyCrit = sgGeneral.add(new BoolSetting.Builder()
        .name("only-crit")
        .description("Only hit the target if a crit is possible!")
        .defaultValue(false)
        .visible(autoCrit::get)
        .build()
    );
    private final Setting<Boolean> packet = sgGeneral.add(new BoolSetting.Builder()
        .name("packet")
        .description("Automatically stops sprinting on crit! Flags on Grim...")
        .defaultValue(false)
        .visible(autoCrit::get)
        .build()
    );

    private final Setting<Boolean> packet2 = sgGeneral.add(new BoolSetting.Builder()
        .name("post-packet")
        .description("Tries to fix flags on grim...")
        .defaultValue(false)
        .visible(() -> autoCrit.get() && packet.get())
        .build()
    );

    private final Setting<ShieldMode> shieldMode = sgGeneral.add(new EnumSetting.Builder<ShieldMode>()
        .name("shield-mode")
        .description("Will try and use an axe to break target shields.")
        .defaultValue(ShieldMode.Break)
        .visible(() -> autoSwitch.get() && weapon.get() != Weapon.Axe)
        .build()
    );

    // Targeting
    private final Setting<Boolean> noTeamBool = sgTargeting.add(new BoolSetting.Builder()
        .name("Teams")
        .description("should you attack teammates or nah.")
        .defaultValue(true)
        .build()
    );

    private final Setting<NoTeam> noteam = sgTargeting.add(new EnumSetting.Builder<NoTeam>()
        .name("no-team")
        .description("Only attacks a player when it isn't your team-mate.")
        .defaultValue(NoTeam.Normal)
        .visible(noTeamBool::get)
        .build()
    );

    private final Setting<Boolean> fovBool = sgTargeting.add(new BoolSetting.Builder()
        .name("FOV")
        .description("Only attacks entities in a specified FOV")
        .defaultValue(true)
        .build()
    );

    private final Setting<Integer> fov = sgTargeting.add(new IntSetting.Builder()
        .name("Degrees")
        .description("The amount of degrees within the entity needs to be before getting attacked.")
        .defaultValue(360)
        .min(1)
        .sliderRange(1, 360)
        .visible(() -> !onlyOnLook.get() && fovBool.get())
        .build()
    );

    private final Setting<Set<EntityType<?>>> entities = sgTargeting.add(new EntityTypeListSetting.Builder()
        .name("entities")
        .description("Entities to attack.")
        .onlyAttackable()
        .defaultValue(EntityType.PLAYER)
        .build()
    );

    private final Setting<SortPriority> priority = sgTargeting.add(new EnumSetting.Builder<SortPriority>()
        .name("priority")
        .description("How to filter targets within range.")
        .defaultValue(SortPriority.ClosestAngle)
        .build()
    );

    private final Setting<Integer> maxTargets = sgTargeting.add(new IntSetting.Builder()
        .name("max-targets")
        .description("How many entities to target at once.")
        .defaultValue(1)
        .min(1)
        .sliderRange(1, 5)
        .visible(() -> !onlyOnLook.get())
        .build()
    );


    private final Setting<Boolean> disableOnBlink = sgTargeting.add(new BoolSetting.Builder()
        .name("no-blink")
        .description("Disables ka on blink")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> disableOnStopMotion = sgTargeting.add(new BoolSetting.Builder()
        .name("no-stop-motion")
        .description("Disables on stop motion")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> ignoreFake = sgTargeting.add(new BoolSetting.Builder()
        .name("ignore-fake-player")
        .description("Does not attack fake players")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> ignoreBabies = sgTargeting.add(new BoolSetting.Builder()
        .name("ignore-babies")
        .description("Whether or not to attack baby variants of the entity.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> ignoreNamed = sgTargeting.add(new BoolSetting.Builder()
        .name("ignore-named")
        .description("Whether or not to attack mobs with a name.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> ignorePassive = sgTargeting.add(new BoolSetting.Builder()
        .name("ignore-passive")
        .description("Will only attack sometimes passive mobs if they are targeting you.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> ignoreTamed = sgTargeting.add(new BoolSetting.Builder()
        .name("ignore-tamed")
        .description("Will avoid attacking mobs you tamed.")
        .defaultValue(false)
        .build()
    );

    // Reach

    private final Setting<Double> range = sgReach.add(new DoubleSetting.Builder()
        .name("range")
        .description("The maximum range the entity can be to attack it.")
        .defaultValue(4.5)
        .min(0)
        .sliderMax(6)
        .build()
    );

    private final Setting<Double> wallsRange = sgReach.add(new DoubleSetting.Builder()
        .name("walls-range")
        .description("The maximum range the entity can be attacked through walls.")
        .defaultValue(3.0)
        .min(0)
        .sliderMax(6)
        .build()
    );
    private final Setting<Double> defaultRange = sgReach.add(new DoubleSetting.Builder()
        .name("default-range")
        .description("The default range in Minecraft... It's advised to set this to 3 or under")
        .defaultValue(3.0)
        .min(0)
        .sliderMax(6)
        .build()
    );

    private final Setting<Boolean> disablerRangeBool = sgReach.add(new BoolSetting.Builder()
        .name("disabler range")
        .description("Add reach on disabler!")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> disablerRange = sgReach.add(new DoubleSetting.Builder()
        .name("disabler-range")
        .description("The range that can be added by the disabler being on")
        .defaultValue(3.0)
        .min(0)
        .sliderMax(3)
        .visible(disablerRangeBool::get)
        .build()
    );

    private final Setting<Boolean> reachOnSprint = sgReach.add(new BoolSetting.Builder()
        .name("only-on-sprint")
        .description("Only reach when sprinting. useful for *certain* anticheats")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> reachOnMove = sgReach.add(new BoolSetting.Builder()
        .name("only-on-move")
        .description("Only reach when moving. Useful for *certain* anticheats")
        .defaultValue(true)
        .visible(() -> getSettingToggled(2))
        .build()
    );

    private final Setting<Boolean> reachOffMove = sgReach.add(new BoolSetting.Builder()
        .name("only-off-move")
        .description("Only reach when not moving. useful for *certain* anticheats")
        .defaultValue(true)
        .visible(() -> !reachOnMove.get())
        .build()
    );

    private final Setting<Boolean> reachCloseBool = sgReach.add(new BoolSetting.Builder()
        .name("only-on-close")
        .description("Only reach when the player has been getting closer to the target for the last 3 ticks. useful for *certain* anticheats")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> reachClose = sgReach.add(new DoubleSetting.Builder()
        .name("only-on-close-range")
        .description("The range that can be added by getting close to the enemy ig")
        .defaultValue(3.0)
        .min(0)
        .sliderMax(3)
        .visible(reachCloseBool::get)
        .build()
    );

    private final Setting<Boolean> reachOnGround = sgReach.add(new BoolSetting.Builder()
        .name("only-on-ground")
        .description("Only reach when on ground. useful for *certain* anticheat")
        .defaultValue(false)
        .visible(() -> getSettingToggled(1))
        .build()
    );

    private final Setting<Boolean> reachOnGround2 = sgReach.add(new BoolSetting.Builder()
        .name("only-off-ground")
        .description("Only reach when not on ground. useful for *certain* anticheats")
        .defaultValue(false)
        .visible(() -> !reachOnGround.get())
        .build()
    );


    // Timing

    private final Setting<Boolean> customDelay = sgTiming.add(new BoolSetting.Builder()
        .name("custom-delay")
        .description("Use a custom delay instead of the vanilla cooldown.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> charge = sgTiming.add(new DoubleSetting.Builder()
        .name("Charge")
        .description("The delay you have... in 1.9")
        .defaultValue(0.912)
        .min(0)
        .sliderMax(1)
        // .visible(customDelay::get)
        .build()
    );



    private final Setting<Boolean> pauseOnLag = sgTiming.add(new BoolSetting.Builder()
        .name("pause-on-lag")
        .description("Pauses if the server is lagging.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> pauseOnUse = sgTiming.add(new BoolSetting.Builder()
        .name("pause-on-use")
        .description("Does not attack while using an item.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> pauseOnCA = sgTiming.add(new BoolSetting.Builder()
        .name("pause-on-CA")
        .description("Does not attack while CA is placing.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> tpsSync = sgTiming.add(new BoolSetting.Builder()
        .name("TPS-sync")
        .description("Tries to sync attack delay with the server's TPS.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Integer> hitDelayMin = sgTiming.add(new IntSetting.Builder()
        .name("hit-delay-min")
        .description("How fast you hit the entity in ticks.")
        .defaultValue(11)
        .min(0)
        .sliderMax(60)
        .visible(customDelay::get)
        .build()
    );

    private final Setting<Integer> hitDelayMax = sgTiming.add(new IntSetting.Builder()
        .name("hit-delay-max")
        .description("How fast you hit the entity in ticks.")
        .defaultValue(11)
        .min(0)
        .sliderMax(60)
        .visible(customDelay::get)
        .build()
    );

    private final Setting<Integer> switchDelay = sgTiming.add(new IntSetting.Builder()
        .name("switch-delay")
        .description("How many ticks to wait before hitting an entity after switching hotbar slots.")
        .defaultValue(0)
        .min(0)
        .sliderMax(10)
        .build()
    );

    CrystalAura ca = Modules.get().get(CrystalAura.class);
    private final List<Entity> targets = new ArrayList<>();
    private int switchTimer, hitTimer;
    private boolean wasPathing = false;
    private final TimerMS timer = new TimerMS();
    private double tick1 = 0, tick2 = 0, tick3 = 0;
    private int ticks = 0;
    private boolean hasTicksPassed = false;

    public Aura() {
        super(Categories.Combat, "Aura", "Attacks specified entities around you.");
    }

    @Override
    public void onDeactivate() {
        //Rotations.rotate(mc.player.getYaw(), mc.player.getPitch());
        targets.clear();
        ticks = 0;
        tick1 = 0;
        tick2 = 0;
        tick3 = 0;
        hasTicksPassed = false;
        fakeBlock = false;
    }

    private boolean getSettingToggled(int settingNum) {
        if (settingNum == 1)
            return !reachOnGround2.get();
        if (settingNum == 2)
            return !reachOffMove.get();
        return false;
    }

    public double calculateRange() {

        if (isNull()) return 0;

        if (disablerRangeBool.get() && Modules.get().isActive(Disabler.class) && Modules.get().get(Disabler.class).isVulcanMode()) {
            return range.get() + disablerRange.get(); // Set the reach to reach + optional addition to reach when the disabler is on...
        }

        if (this.reachOnSprint.get() && !mc.player.isSprinting())
            return this.defaultRange.get(); // check if player is sprinting, and if not, set distance to the default range, 3.0 by defaukt

        if (this.reachOnMove.get() && !MoveUtils.hasMovement() && !this.reachOffMove.get())
            return this.defaultRange.get(); // if reachOnMove is true (a setting), player doesn't have movement, and reachOffMove is false, set reach to default

        if (this.reachOnGround.get() && !mc.player.isOnGround() && !this.reachOnGround2.get())
            return this.defaultRange.get(); // if player is not on ground, set to default range

        if (this.reachOnGround2.get() && mc.player.isOnGround() && !this.reachOnGround.get())
            return this.defaultRange.get(); // if player is on ground, set to default

        if (this.reachOffMove.get() && MoveUtils.hasMovement() && !this.reachOnMove.get())
            return this.defaultRange.get(); // if player is moving and allat, set to default range

        if (!(this.tick1 > this.tick2 && this.tick2 > this.tick3) && this.reachCloseBool.get())
            return this.range.get() + this.reachClose.get(); // if player has been moving closer to the enemy for 3 ticks, add a certain value to reach

        return this.range.get();
    }

    @EventHandler
    private void onTick(final TickEvent.Pre e) {
        if (!mc.player.isAlive() || PlayerUtils.getGameMode() == GameMode.SPECTATOR) return;
        if (pauseOnUse.get() && (mc.interactionManager.isBreakingBlock() || mc.player.isUsingItem())) return;
        if (onlyOnClick.get() && !mc.options.attackKey.isPressed()) return;
        if (TickRate.INSTANCE.getTimeSinceLastTick() >= 1f && pauseOnLag.get()) return;
        if (pauseOnCA.get() && ca.isActive() && ca.kaTimer > 0) return;
        if ((disableOnBlink.get() && Modules.get().isActive(Blink.class)) || (disableOnStopMotion.get() && Modules.get().isActive(StopMotion.class))) return;
        if (ticks > 3 && !hasTicksPassed) hasTicksPassed = true;

        if (onlyOnLook.get()) { // makes killaura act like triggerbot...
            Entity targeted = mc.targetedEntity;

            if (targeted == null) return;
            if (!entityCheck(targeted)) return;

            targets.clear();
            fakeBlock = false;
            targets.add(mc.targetedEntity);
            if (fakeBlockSetting.get() && !targets.isEmpty()) fakeBlock = true;
        } else {
            targets.clear();
            fakeBlock = false;
            TargetUtils.getList(targets, this::entityCheck, priority.get(), maxTargets.get());
            if (fakeBlockSetting.get() && !targets.isEmpty()) fakeBlock = true;
        }

        if (targets.isEmpty()) {
            if (wasPathing) {
                BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("resume");
                wasPathing = false;
            }
            return;
        }

        Entity primary = targets.get(0);

        if (autoSwitch.get()) {
            Predicate<ItemStack> predicate = switch (weapon.get()) {
                case Axe -> stack -> stack.getItem() instanceof AxeItem;
                case Sword -> stack -> stack.getItem() instanceof SwordItem;
                case Both -> stack -> stack.getItem() instanceof AxeItem || stack.getItem() instanceof SwordItem;
                case Choose -> stack -> weapons.get().contains(stack.getItem());
                default -> o -> true;
            };

            FindItemResult weaponResult = InvUtils.findInHotbar(predicate);

            if (shouldBreakShield()) {
                // TODO: dont do shit if there is no axe in hotbar
                int lastSlot = mc.player.getInventory().selectedSlot;
                FindItemResult axeResult = InvUtils.findInHotbar(itemStack -> itemStack.getItem() instanceof AxeItem);
                if (axeResult.found()) weaponResult = axeResult;
                InvUtils.swap(weaponResult.slot(), false);
                // break the enemy shield, attacks twice as 2 attacks -> 50% chance to break the shield,
                // as unenchanted axe has only a 25% chance to break the shield on low charge
                targets.forEach(this::attack);
                targets.forEach(this::attack);
                InvUtils.swap(lastSlot, false);
                return;
            }
            InvUtils.swap(weaponResult.slot(), false);
        }

        if (!itemInHand()) return;

        if (rotation.get() == RotationMode.Always)
            Rotations.rotate(Rotations.getYaw(primary), Rotations.getPitch(primary, rotatepart.get()));

        if (!moveCorrection.get() && rotation.get() == RotationMode.Vulcan) {
            // TRIED ADDING RANDOMISATION TO THE ROTS BUT DIDN'T WORK WITH GCD FIX...
            /*
            float randomrot = (float) (Math.floor(Math.random() * 10) + 1);
            float randomcomma = (float) (Math.floor(Math.random() * 10) + 1);
            float bettercomma = (randomcomma / 10);
            float betterrot = (randomrot + bettercomma);
            */

            SimpleOption<Double> mousesens = mc.options.getMouseSensitivity();
            double f = mousesens.getValue() * 0.6F + 0.2F;
            float gcd = (float) (f * f * f * 1.2);

            // Yaw
            float targetYaw = (float) Rotations.getYaw(primary);
            float curYaw = mc.player.getYaw();

            // Pitch
            float targetPitch = (float) Rotations.getPitch(primary, rotatepart.get());
            float curPitch = mc.player.getPitch();

            float deltaYaw = targetYaw - curYaw;
            float deltaPitch = targetPitch - curPitch;
            float fixedDeltaYaw = deltaYaw - (deltaYaw % gcd), fixedDeltaPitch = deltaPitch - (deltaPitch % gcd);
            float fixedYaw = curYaw + fixedDeltaYaw;
            float fixedPitch = curPitch + fixedDeltaPitch;


            Rotations.rotate(fixedYaw, fixedPitch);
        }

        if (pauseOnCombat.get() && BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing() && !wasPathing) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("pause");
            wasPathing = true;
        }

        // check distance to every target, and assign the distance to target to a value :)
        for (Entity currentTarget : targets) {
            if (currentTarget instanceof PlayerEntity && hasTicksPassed) {
                switch (ticks) {
                    case 1:
                        tick1 = currentTarget.distanceTo(mc.player);
                        break;

                    case 2:
                        tick2 = currentTarget.distanceTo(mc.player);
                        if (tick3 != 0)
                            tick1 = tick3;
                        break;

                    case 3:
                        tick3 = currentTarget.distanceTo(mc.player);
                        tick2 = tick1;
                        ticks = 0;
                        break;

                    default:
                        // Code for other cases, if needed
                        break;
                }
            }
        }

        //if (wTap.get().equals(wTapMode.Packet) && mc.player.isOnGround() && wTapPre.get().equals(wTapTimingMode.Pre))
        //    sendNoEvent(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING));

        // do WTap pre
        if (wTapTiming.get().equals(wTapTimingMode.Pre) && mc.player.isOnGround() && wTapBool.get())
            doWTap();

        // Attack players if packet is enabled, send "stop sprinting" packet...
        if ((!mc.player.isOnGround() && mc.player.fallDistance >= critfall.get() && delayCheck()) && packet.get() && autoCrit.get())
            sendNoEvent(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING));

        if (mc.player.isOnGround() && autoCrit.get() && grimcrit.get()) {
            sendNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY(), mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), mc.player.isOnGround()));
            sendNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY() + 0.02, mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), false));
            sendNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY() + 0.04, mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), false));
            sendNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY() + 0.06, mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), false));
            sendNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY() + 0.0625, mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), false));
            sendNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY() + 0.06, mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), false));
            sendNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY() + 0.04, mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), false));
            sendNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY() + 0.02, mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), false));
            sendNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY(), mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), mc.player.isOnGround()));
        }


        if ((checkCrit() && delayCheck() && autoCrit.get()) // autocrit
            || (onlyCrit.get() && checkCrit() && delayCheck()) // only crit
            || (!onlyCrit.get() && !autoCrit.get() && delayCheck()) // 1.8 ig, idrc, nullable broke this shit
            || (!onlyCrit.get() && delayCheck() && mc.player.isOnGround())) // if only crit is false, and player is on ground
                targets.forEach(this::attack); // hit person

        // Stop sprinting, hit, start sprinting again
        if ((!mc.player.isOnGround() && mc.player.fallDistance >= critfall.get() && delayCheck()) && packet.get() && autoCrit.get() && packet2.get())
            sendNoEvent(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING));

        // Do WTap post
        if (wTapTiming.get().equals(wTapTimingMode.Post) && mc.player.isOnGround() && wTapBool.get())
            doWTap();
        ticks++;
    }

    @EventHandler
    private void onSendPacket(final PacketEvent.Send event) {
        if (event.packet instanceof UpdateSelectedSlotC2SPacket) {
            switchTimer = switchDelay.get();
        }
        /* Lagooncode moved to PlayerEntityMixin
        if (event.packet instanceof ClientCommandC2SPacket && keepsprint.get() && mc.player.isOnGround()) {
            ClientCommandC2SPacket p = (ClientCommandC2SPacket) event.packet;

            if (p.getMode().equals(ClientCommandC2SPacket.Mode.STOP_SPRINTING) && MoveUtils.hasMovement())
                event.setCancelled(true);
        }
         */
    }

    private boolean shouldBreakShield() {
        for (Entity target : targets) {
            if (target instanceof PlayerEntity player && player.blockedByShield(mc.world.getDamageSources().playerAttack(mc.player)) && shieldMode.get() == ShieldMode.Break) {
                    return true;
            }
        }
        return false;
    }

    private boolean checkCrit() {
        // Return true if player is not on ground, has fallen less than 0.25 blocks or is flying (and can crit)
        return ((!mc.player.isOnGround() && mc.player.fallDistance >= critfall.get() && mc.player.getAttackCooldownProgress(0.5f) > 0.86) || PlayerUtils.hasFlyUtilities());
    }

    private void doWTap() {
        // Do WTap
        //if (wTap.get().equals(wTapMode.Packet) && mc.player.isOnGround())
        //    sendNoEvent(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING));

        // TODO: Add more wTap modes? and maybe crit modes?

        switch (wTap.get()) {
            case Packet -> sendNoEvent(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING));

            case packet2 -> {
                sendNoEvent(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING));
                if (timer.hasTimePassed(wtapTimer.get()))
                    sendNoEvent(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_SPRINTING));
                timer.reset();
            }

            case Press -> {
                mc.options.forwardKey.setPressed(false);
                if (timer.hasTimePassed(wtapTimer.get()))
                    mc.options.forwardKey.setPressed(true);
                timer.reset();
            }
            case Normal -> {
                mc.player.setSprinting(false);
                if (timer.hasTimePassed(wtapTimer.get()))
                    mc.player.setSprinting(true);
                timer.reset();
            }
            case ctrlPress -> {
                mc.options.sprintKey.setPressed(false);
                if (timer.hasTimePassed(wtapTimer.get()))
                    mc.options.sprintKey.setPressed(true);
                timer.reset();
            }
            case ShiftTap -> {
                mc.options.sneakKey.setPressed(true);
                if (timer.hasTimePassed(wtapTimer.get()))
                    mc.options.sneakKey.setPressed(false);
                timer.reset();
            }
            default -> {
            }
        }
    }

    private boolean entityCheck(final Entity entity) {
        if (entity.equals(mc.player) || entity.equals(mc.cameraEntity) || entity.getEntityName().equals(mc.player.getEntityName())) return false;
        if ((entity instanceof LivingEntity && ((LivingEntity) entity).isDead()) || !entity.isAlive()) return false;
        if (entity instanceof FakePlayerEntity && ignoreFake.get()) return false;

        Box hitbox = entity.getBoundingBox();
        if (!PlayerUtils.isWithin(
            MathHelper.clamp(mc.player.getX(), hitbox.minX, hitbox.maxX),
            MathHelper.clamp(mc.player.getY(), hitbox.minY, hitbox.maxY),
            MathHelper.clamp(mc.player.getZ(), hitbox.minZ, hitbox.maxZ),
            calculateRange()
        )) return false;

        if (!entities.get().contains(entity.getType())) return false;

        if (noTeamBool.get() && noteam.get().equals(NoTeam.Normal)) {
            //if (mc.player == null || mc.world == null || mc.getCurrentServerEntry() == null || mc.isInSingleplayer())
            //    return
            // Check if player (well, target) is a teammate
            if (entity.isTeammate(mc.player)) return false;
            // if that fails, get entity color and your own color, and compare them
            int enemyColor = entity.getTeamColorValue();
            int ownColor = mc.player.getTeamColorValue();
            if (enemyColor == ownColor) return false;
        }

        // cancer code... my linter is crying
        if (noTeamBool.get() && noteam.get().equals(NoTeam.Armor) && entity instanceof PlayerEntity e) {
            if (!e.getInventory().getArmorStack(2).isEmpty() && !mc.player.getInventory().getArmorStack(2).isEmpty()) {
                if (e.getInventory().getArmorStack(2).getNbt() != null && mc.player.getInventory().getArmorStack(2).getNbt() != null) {
                    if (Objects.requireNonNull(e.getInventory().getArmorStack(2).getNbt()).getCompound("display").getInt("color") == Objects.requireNonNull(mc.player.getInventory().getArmorStack(2).getNbt()).getCompound("display").getInt("color")) {
                        return false;
                    }
                }
            }
        }

        // Hey, rk3. You should be better at coding since lagoon thinks you are bad.
        if (noTeamBool.get() && noteam.get().equals(NoTeam.DisplayName)) {

            // getting the player display name
            String color1 = mc.player.getDisplayName().toString();

            // getting the entity display name
            String color2 = entity.getDisplayName().toString();

            // getting the first 3 characters of the player name color
            String playerNameChars = color1.substring(13, 22);

            // getting the first 3 characters of the entity name color
            String entityNameColor = color2.substring(13, 22);

            if (playerNameChars.equals(entityNameColor)) {
                return false;
            }
        }

        if (!MathUtils.isInFOV(entity, fov.get()))
            return false;

        if (ignoreNamed.get() && entity.hasCustomName()) return false;
        if (!PlayerUtils.canSeeEntity(entity) && !PlayerUtils.isWithin(entity, wallsRange.get())) return false;
        if (ignoreTamed.get()) {
            if (entity instanceof Tameable tameable
                && tameable.getOwnerUuid() != null
                && tameable.getOwnerUuid().equals(mc.player.getUuid())
            ) return false;
        }
        if (ignorePassive.get()) {
            if (entity instanceof EndermanEntity enderman && !enderman.isAngryAt(mc.player)) return false;
            if (entity instanceof ZombifiedPiglinEntity piglin && !piglin.isAngryAt(mc.player)) return false;
            if (entity instanceof WolfEntity wolf && !wolf.isAttacking()) return false;
            if (entity instanceof LlamaEntity llama && !llama.isAttacking()) return false;
        }
        if (entity instanceof PlayerEntity player) {
            if (player.isCreative()) return false;
            if (!Friends.get().shouldAttack(player)) return false;
            if (shieldMode.get() == ShieldMode.Ignore && player.blockedByShield(mc.world.getDamageSources().playerAttack(mc.player)))
                return false;
        }
        return !(entity instanceof AnimalEntity animal) || !ignoreBabies.get() || !animal.isBaby();
    }

    private boolean delayCheck() {
        if (switchTimer > 0) {
            switchTimer--;
            return false;
        }

        float delay = (customDelay.get()) ? (float) (Math.random() * (hitDelayMax.get() - hitDelayMin.get()) + hitDelayMin.get()) : 0.5f;
        if (tpsSync.get()) delay /= (TickRate.INSTANCE.getTickRate() / 20);

        if (customDelay.get()) {
            if (hitTimer < delay) {
                hitTimer++;
                return false;
            } else return true;
        } else return mc.player.getAttackCooldownProgress(delay) >= charge.get();
    }

    private void attack(final Entity target) {
        if (rotation.get() == RotationMode.OnHit)
            Rotations.rotate(Rotations.getYaw(target), Rotations.getPitch(target, rotatepart.get()));

        if (rotation.get() == RotationMode.Packet1)
            sendNoEvent(new PlayerMoveC2SPacket.LookAndOnGround((float) Rotations.getYaw(target), (float) Rotations.getPitch(target, rotatepart.get()), mc.player.isOnGround()));

        if (rotation.get() == RotationMode.Packet2)
            sendNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY(), mc.player.getZ(), (float) Rotations.getYaw(target), (float) Rotations.getPitch(target, rotatepart.get()), mc.player.isOnGround()));

        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);

        hitTimer = 0;

        // Post

        if (rotation.get() == RotationMode.Packet2)
            sendNoEvent(new PlayerMoveC2SPacket.Full(mc.player.getX(), mc.player.getY(), mc.player.getZ(), mc.player.getYaw(), mc.player.getPitch(), mc.player.isOnGround()));

        if (rotation.get() == RotationMode.Packet1)
            sendNoEvent(new PlayerMoveC2SPacket.LookAndOnGround(mc.player.getYaw(), mc.player.getPitch(), mc.player.isOnGround()));
    }

    private boolean itemInHand() {
        if (shouldBreakShield()) return mc.player.getMainHandStack().getItem() instanceof AxeItem;

        return switch (weapon.get()) {
            case Axe -> mc.player.getMainHandStack().getItem() instanceof AxeItem;
            case Sword -> mc.player.getMainHandStack().getItem() instanceof SwordItem;
            case Both -> mc.player.getMainHandStack().getItem() instanceof AxeItem || mc.player.getMainHandStack().getItem() instanceof SwordItem;
            case Choose -> weapons.get().contains(mc.player.getMainHandStack().getItem());
            default -> true;
        };
    }

    public Entity getTarget() {
        if (!targets.isEmpty()) return targets.get(0);
        return null;
    }

    @Override
    public String getInfoString() {
        if (!targets.isEmpty() && infoStringMode.get().equals(ModuleInfoStringEnum.Entity) ) return EntityUtils.getName(getTarget());
        if (infoStringMode.get().equals(ModuleInfoStringEnum.TargetMode)) return priority.get().toString();
        return null;
    }

    @EventHandler
    public void onMovementInputShit(final MovementInputToVelocityEvent e) {
        if (getTarget() != null && rotation.get() == RotationMode.GrimAlways) {
            e.yaw = (float) Rotations.getYaw(getTarget());
            Rotations.rotate(Rotations.getYaw(getTarget()), Rotations.getPitch(getTarget(), rotatepart.get()));
            //implement changing targets ig?
        }

        if (getTarget() != null && moveCorrection.get() && rotation.get() == RotationMode.Vulcan) {
            // TRIED ADDING RANDOMISATION TO THE ROTS BUT DIDN'T WORK WITH GCD FIX...
            /*
            float randomrot = (float) (Math.floor(Math.random() * 10) + 1);
            float randomcomma = (float) (Math.floor(Math.random() * 10) + 1);
            float bettercomma = (randomcomma / 10);
            float betterrot = (randomrot + bettercomma);
            */

            SimpleOption<Double> mousesens = mc.options.getMouseSensitivity();
            double f = mousesens.getValue() * 0.6F + 0.2F;
            float gcd = (float) (f * f * f * 1.2);

            // Yaw
            float targetYaw = (float) Rotations.getYaw(getTarget());
            float curYaw = mc.player.getYaw();

            // Pitch
            float targetPitch = (float) Rotations.getPitch(getTarget(), rotatepart.get());
            float curPitch = mc.player.getPitch();

            float deltaYaw = targetYaw - curYaw;
            float deltaPitch = targetPitch - curPitch;
            float fixedDeltaYaw = deltaYaw - (deltaYaw % gcd), fixedDeltaPitch = deltaPitch - (deltaPitch % gcd);
            float fixedYaw = curYaw + fixedDeltaYaw;
            float fixedPitch = curPitch + fixedDeltaPitch;


            Rotations.rotate(fixedYaw, fixedPitch);
            e.yaw = fixedYaw;
        }

        if (moveCorrection.get() && getTarget() != null && !rotation.get().equals(RotationMode.GrimAlways) && !rotation.get().equals(RotationMode.Vulcan)) {
            e.yaw = (float) Rotations.getYaw(getTarget());
        }
    }

    // Provide getters to shit here:

    public enum Weapon {
        Sword,
        Axe,
        Both,
        Choose,
        Any
    }

    public enum NoTeam {
        Normal,
        Armor,
        DisplayName,
        None
    }

    public enum RotationMode {
        Always,
        OnHit,
        GrimAlways,
        Vulcan,
        Packet1,
        Packet2,
        None
    }

    public enum ShieldMode {
        Ignore,
        Break,
        None
    }

    public enum wTapMode {
        Packet,
        packet2,
        Normal,
        Press,
        ctrlPress,
        ShiftTap,
        None
    }

    public enum wTapTimingMode {
        Pre,
        Post
    }

    public enum ModuleInfoStringEnum {
        Entity,
        TargetMode
    }
}
