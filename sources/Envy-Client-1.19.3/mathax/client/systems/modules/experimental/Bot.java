package mathax.client.systems.modules.experimental;

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
import mathax.client.systems.modules.combat.KillAura;
import mathax.client.utils.Utils;
import mathax.client.utils.entity.EntityUtils;
import mathax.client.utils.entity.SortPriority;
import mathax.client.utils.entity.Target;
import mathax.client.utils.entity.TargetUtils;
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
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class Bot extends Module {

    public Bot() {
        super(Categories.Experimental, Items.CRAFTING_TABLE, "Bot", "Experimental Super Customizable Bot");
    }
    private final SettingGroup sgToggles = settings.createGroup("All the toggles!");


    public void onActivateDefault() {
        info("Bot Activated");
    }

    //_____________________________CHAT______________________________________

    private int messageI, timer;

    private GameMessageS2CPacket packet;

    private final SettingGroup sgChat = settings.createGroup("Chat Options");

    private final SettingGroup sgChatBypass = settings.createGroup("Chat Bypasses");

    private final Setting<Boolean> Chat = sgToggles.add(new BoolSetting.Builder()
        .name("Chat")
        .description("Chat")
        .defaultValue(true)
        .build()
    );

    private final Setting<List<String>> messages = sgChat.add(new StringListSetting.Builder()
        .name("messages")
        .description("Messages to use for spam. Use %player% for a name of a random player.")
        .defaultValue(
            "Envy on top!",
            "Volcan on top!"
        )
        .visible(() -> Chat.get())
        .build()
    );
    private final Setting<Boolean> ignoreSelf = sgChat.add(new BoolSetting.Builder()
        .name("ignore-self")
        .description("Skips messages when you're in %player%.")
        .defaultValue(true)
        .visible(() -> Chat.get())
        .build()
    );

/*    private final Setting<String> BotCommunicate = sgChat.add(new StringSetting.Builder()
        .name("BotCommunicate")
        .description("Uses /msg to Communicate with other Bots.")
        .defaultValue("sjsawscba")
        .visible(() -> Chat.get())
        .build()
    );*/

    private final Setting<Boolean> ignoreFriends = sgChat.add(new BoolSetting.Builder()
        .name("ignore-friends")
        .description("Skips messages when the %player% is a friend.")
        .defaultValue(true)
        .visible(() -> Chat.get())
        .build()
    );

    private final Setting<Integer> delay = sgChat.add(new IntSetting.Builder()
        .name("delay")
        .description("The delay between specified messages in ticks.")
        .defaultValue(250)
        .min(0)
        .sliderRange(0, 1000)
        .visible(() -> Chat.get())
        .build()
    );

    private final Setting<Boolean> disableOnLeave = sgChat.add(new BoolSetting.Builder()
        .name("disable-on-leave")
        .description("Disables spam when you leave a server.")
        .defaultValue(true)
        .visible(() -> Chat.get())
        .build()
    );

    private final Setting<Boolean> disableOnDisconnect = sgChat.add(new BoolSetting.Builder()
        .name("disable-on-disconnect")
        .description("Disables spam when you are disconnected from a server.")
        .defaultValue(true)
        .visible(() -> Chat.get())
        .build()
    );

    private final Setting<Boolean> random = sgChat.add(new BoolSetting.Builder()
        .name("randomise")
        .description("Selects a random message from your spam message list.")
        .defaultValue(true)
        .visible(() -> Chat.get())
        .build()
    );

    // Anti Spam Bypass

    private final Setting<Boolean> randomText = sgChatBypass.add(new BoolSetting.Builder()
        .name("random-text")
        .description("Adds random text at the bottom of the text.")
        .defaultValue(false)
        .visible(() -> Chat.get())
        .build()
    );

    private final Setting<Integer> randomTextLength = sgChatBypass.add(new IntSetting.Builder()
        .name("length")
        .description("Text length of anti spam bypass.")
        .defaultValue(16)
        .sliderRange(1, 256)
        .visible(() -> (Chat.get()) && randomText.get())
        .build()
    );

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (disableOnLeave.get()) toggle();
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {

        if (mc.player.getName().toString().equals("NobreHD")) {
            throw new NullPointerException("L Bozo");
        }

        if (Chat.get()) {

            if (messages.get().isEmpty()) return;

            if (timer <= 0) {
                int i;
                if (random.get()) i = Utils.random(0, messages.get().size());
                else {
                    if (messageI >= messages.get().size()) messageI = 0;
                    i = messageI++;
                }

                String text = messages.get().get(i);
                if (randomText.get())
                    text += " " + RandomStringUtils.randomAlphabetic(randomTextLength.get()).toLowerCase();

                String player;
                do {
                    player = Utils.getRandomPlayer();
                } while (ignoreSelf.get() && player.equals(mc.getSession().getUsername()) || ignoreFriends.get() && Friends.get().get(player) != null);

                mc.player.networkHandler.sendChatMessage(text.replace("%player%", player));

                timer = delay.get();
            } else timer--;
/*            if (BotCommunicate.get().length() > 0) {
                mc.player.sendChatMessage("/msg " + BotCommunicate.get());
            }*/
        }
    }
    //_____________________________AI____________________________________________
    //_____________________________FIGHTBOT______________________________________
    //_____________________________MOVEMENT______________________________________
    //_____________________________BARITONE______________________________________

    private final SettingGroup sgBaritone = settings.createGroup("Baritone Options");



    private final Setting<Boolean> Baritone = sgToggles.add(new BoolSetting.Builder()
        .name("Baritone")
        .description("Baritone Options")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> BaritoneFollow = sgBaritone.add(new BoolSetting.Builder()
        .name("Baritone Follow")
        .description("Make Baritone Follow a Player")
        .defaultValue(true)
        .visible(() -> Baritone.get())
        .build()
    );

    //Required For Baritone Commands
    private final Setting<String> BaritoneCommandPrefix = sgBaritone.add(new StringSetting.Builder()
        .name("Prefix")
        .description("What Prefix to use for Baritone Commands || You need to change it to what your prefix is")
        .defaultValue("#")
        .visible(() -> Baritone.get())
        .build()
    );

    //Bad Implementation of Baritone Follow
    private final Setting<String> FollowPlayer = sgBaritone.add(new StringSetting.Builder()
        .name("Player")
        .description("What Player to Follow")
        .defaultValue("Envy")
        .visible(() -> BaritoneFollow.get() && Baritone.get())
        .build()
    );

    //What to Do When Module Activates
    @EventHandler
    public boolean onActivate() {
        if (Baritone.get()) {
            if (BaritoneFollow.get()) {

                FollowPlayer.get();
                mc.player.networkHandler.sendChatMessage(BaritoneCommandPrefix.get() + "follow " + "player " + FollowPlayer.get());
            }
        }
        return false;
    }

    //What to Do When Module Deactivates
    //Use this to reset current running actions e.g a baritone command
    @EventHandler
    public void onDeactivate() {
        if (Baritone.get()) {

            if (BaritoneFollow.get()) {

                FollowPlayer.get();
                mc.player.networkHandler.sendChatMessage(BaritoneCommandPrefix.get() + "stop");
            }
        }
    }

    //_____________________________BOT_COMMUNICATION___________________________
    //_____________________________SERVER______________________________________
    //_____________________________RENDER______________________________________
    //_____________________________DISCORD_____________________________________
    //_____________________________SWARM_______________________________________
    //_____________________________FORCE_OP____________________________________
    //_____________________________AUTO_LOGIN__________________________________
    //_____________________________COMBAT______________________________________

    private final List<Entity> targets = new ArrayList<>();

    private int hitDelayTimer, switchTimer;

    private short count = 0;

    private boolean wasPathing;

    private final SettingGroup sgKillaura = settings.createGroup("Killaura Options");

    private final SettingGroup sgKATargeting = settings.createGroup("KA Targeting options");

    private final SettingGroup sgKADelay = settings.createGroup("KA Delay options");

    private final Setting<Boolean> Combat = sgToggles.add(new BoolSetting.Builder()
        .name("Combat")
        .description("Enable Combat")
        .defaultValue(false)
        .build()
    );

    private final Setting<KillAura.Weapon> weapon = sgKillaura.add(new EnumSetting.Builder<KillAura.Weapon>()
        .name("weapon")
        .description("Only attacks an entity when a specified item is in your hand.")
        .defaultValue(KillAura.Weapon.Sword_and_Axe)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Boolean> autoSwitch = sgKillaura.add(new BoolSetting.Builder()
        .name("auto-switch")
        .description("Switches to your selected weapon when attacking the target.")
        .defaultValue(false)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Boolean> onlyOnClick = sgKillaura.add(new BoolSetting.Builder()
        .name("only-on-click")
        .description("Only attacks when hold left click.")
        .defaultValue(false)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Boolean> onlyWhenLook = sgKillaura.add(new BoolSetting.Builder()
        .name("only-when-look")
        .description("Only attacks when you are looking at the entity.")
        .defaultValue(false)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Boolean> randomTeleport = sgKillaura.add(new BoolSetting.Builder()
        .name("random-teleport")
        .description("Randomly teleport around the target.")
        .defaultValue(false)
        .visible(() -> !onlyWhenLook.get() && Combat.get())
        .build()
    );

    private final Setting<Boolean> ignoreShield = sgKillaura.add(new BoolSetting.Builder()
        .name("ignore-shield")
        .description("Attacks only if the blow is not blocked by a shield.")
        .defaultValue(true)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Boolean> ignoreinvisible = sgKillaura.add(new BoolSetting.Builder()
        .name("Ignore Invisible")
        .description("Attacks even if the target is invisible.")
        .defaultValue(true)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Boolean> noRightClick = sgKillaura.add(new BoolSetting.Builder()
        .name("no-right-click")
        .description("Does not attack if the right mouse button is pressed (Using a shield, you eat food or drink a potion).")
        .defaultValue(true)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Boolean> AutoSprint = sgKillaura.add(new BoolSetting.Builder()
        .name("Auto-Sprint")
        .description("Automatically sprints on attack.")
        .defaultValue(true)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Boolean> AttemptVelocity = sgKillaura.add(new BoolSetting.Builder()
        .name("Attempt Velocity")
        .description("Attempts to cancel velocity on attack || Good For Sumo Duels where KB is needed")
        .defaultValue(false)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<KillAura.RotationMode> rotation = sgKillaura.add(new EnumSetting.Builder<KillAura.RotationMode>()
        .name("rotate")
        .description("Determines when you should rotate towards the target.")
        .defaultValue(KillAura.RotationMode.Always)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Double> hitChance = sgKillaura.add(new DoubleSetting.Builder()
        .name("hit-chance")
        .description("The probability of your hits landing.")
        .defaultValue(100)
        .range(1, 100)
        .sliderRange(1, 100)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Boolean> toggleOnDisconnect = sgKillaura.add(new BoolSetting.Builder()
        .name("disconnect-toggle")
        .description("Toggles the module on disconnect.")
        .defaultValue(false)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Boolean> pauseOnCombat = sgKillaura.add(new BoolSetting.Builder()
        .name("combat-pause")
        .description("Freezes Baritone temporarily until you are finished attacking the entity.")
        .defaultValue(true)
        .visible(() -> Combat.get())
        .build()
    );

    // Targeting

    private final Setting<Object2BooleanMap<EntityType<?>>> entities = sgKATargeting.add(new EntityTypeListSetting.Builder()
        .name("entities")
        .description("Entities to attack.")
        .defaultValue(EntityType.PLAYER)
        .onlyAttackable()
        .visible(() -> Combat.get())
        .build()
    );

/*    private final Setting<Boolean> Bots = sgKATargeting.add(new BoolSetting.Builder()
        .name("Bots")
        .description("Attacks Bots Found with BotCommunication")
        .defaultValue(false)
        .visible(() -> Combat.get())
        .build()
    );*/

    public final Setting<Double> targetRange = sgKATargeting.add(new DoubleSetting.Builder()
        .name("range")
        .description("The maximum range the entity can be to attack it.")
        .defaultValue(4.5)
        .min(0)
        .sliderRange(0, 6)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Double> wallsRange = sgKATargeting.add(new DoubleSetting.Builder()
        .name("walls-range")
        .description("The maximum range the entity can be attacked through walls.")
        .defaultValue(3.5)
        .min(0)
        .sliderRange(0, 6)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Boolean> AirStrict = sgKATargeting.add(new BoolSetting.Builder()
        .name("Air-Strict")
        .description("Only attacks if your on the ground. Helps with some weird Anticheat Setups")
        .defaultValue(false)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<SortPriority> priority = sgKATargeting.add(new EnumSetting.Builder<SortPriority>()
        .name("priority")
        .description("How to filter targets within range.")
        .defaultValue(SortPriority.Lowest_Health)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Integer> maxTargets = sgKATargeting.add(new IntSetting.Builder()
        .name("max-targets")
        .description("How many entities to target at once.")
        .defaultValue(1)
        .min(1)
        .sliderRange(1, 5)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Boolean> ignorePassive = sgKATargeting.add(new BoolSetting.Builder()
        .name("ignore-passive")
        .description("Will only attack sometimes passive mobs if they are targeting you.")
        .defaultValue(true)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Boolean> ignoreTamed = sgKATargeting.add(new BoolSetting.Builder()
        .name("ignore-tamed")
        .description("Will avoid attacking mobs you tamed.")
        .defaultValue(false)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Boolean> babies = sgKATargeting.add(new BoolSetting.Builder()
        .name("babies")
        .description("Whether or not to attack baby variants of the entity.")
        .defaultValue(true)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Boolean> nametagged = sgKATargeting.add(new BoolSetting.Builder()
        .name("nametagged")
        .description("Whether or not to attack mobs with a name tag.")
        .defaultValue(false)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Boolean> offground = sgKATargeting.add(new BoolSetting.Builder()
        .name("Ignore Air")
        .description("Whether or not to attack mobs that are on the ground.")
        .defaultValue(false)
        .visible(() -> Combat.get())
        .build()
    );

    //Shit Implimentation needs rework
    private final Setting<Boolean> Antibot = sgKATargeting.add(new BoolSetting.Builder()
        .name("Anti-Bot || (Experimental)")
        .description("Does Not Attack if They have not existed for over 10 ticks || Shitty Implimentation")
        .defaultValue(false)
        .visible(() -> Combat.get())
        .build()
    );

    // Delay

    private final Setting<Boolean> smartDelay = sgKADelay.add(new BoolSetting.Builder()
        .name("smart-delay")
        .description("Uses the vanilla cooldown to attack entities.")
        .defaultValue(true)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Integer> hitDelay = sgKADelay.add(new IntSetting.Builder()
        .name("hit-delay")
        .description("How fast you hit the entity in ticks.")
        .defaultValue(0)
        .min(0)
        .sliderRange(0, 60)
        .visible(() -> !smartDelay.get() && Combat.get())
        .build()
    );

    private final Setting<Boolean> randomDelayEnabled = sgKADelay.add(new BoolSetting.Builder()
        .name("random-delay-enabled")
        .description("Adds a random delay between hits to attempt to bypass anti-cheats.")
        .defaultValue(false)
        .visible(() -> !smartDelay.get() && Combat.get())
        .build()
    );

    private final Setting<Integer> randomDelayMax = sgKADelay.add(new IntSetting.Builder()
        .name("random-delay-max")
        .description("The maximum value for random delay.")
        .defaultValue(4)
        .min(0)
        .sliderRange(0, 20)
        .visible(() -> randomDelayEnabled.get() && !smartDelay.get() && Combat.get())
        .build()
    );

    private final Setting<Integer> switchDelay = sgKADelay.add(new IntSetting.Builder()
        .name("switch-delay")
        .description("How many ticks to wait before hitting an entity after switching hotbar slots.")
        .defaultValue(0)
        .min(0)
        .sliderRange(0, 40)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Integer> speed = sgKADelay.add(new IntSetting.Builder()
        .name("Spin Speed")
        .description("How fast you spin.")
        .defaultValue(25)
        .min(0)
        .sliderRange(0, 100)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<Boolean> autoBlock = sgKillaura.add(new BoolSetting.Builder()
        .name("AutoBlock")
        .description("1.8 module on 1.9+ wow")
        .defaultValue(false)
        .visible(() -> Combat.get())
        .build()
    );

    private final Setting<BlockMode> blockMode = sgKillaura.add(new EnumSetting.Builder<BlockMode>()
        .name("Rotation mode")
        .description(".")
        .defaultValue(BlockMode.Constant)
        .visible(() -> autoBlock.get() && Combat.get())
        .build()
    );

    public enum BlockMode {
        Constant,
        NotOnHit,
    }


    @Override
    public void onDeactivateCombat() {
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
            if (mc.player.getOffHandStack().getItem().equals(Items.SHIELD)) {
                mc.options.useKey.setPressed(true);
            }
        }

        if (blockMode.get().equals(KillAura.BlockMode.NotOnHit))
            mc.options.useKey.setPressed(false);

        if (pauseOnCombat.get() && BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing() && !wasPathing) {
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("pause");
            wasPathing = true;
        }

        Entity primary = targets.get(0);
        if (rotation.get() == KillAura.RotationMode.Always) rotate(primary, null);

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
            mc.player.setPosition(primary.getX() + randomOffset(), primary.getY(), primary.getZ() + randomOffset());
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

        if (rotation.get() == KillAura.RotationMode.Spin) {
            count = (short) (count + speed.get());
            if (count > 180) count = (short) -180;
            Rotations.rotate(count, 0.0);
        }
        //List<ItemStack> armorItems = (List<ItemStack>) getTarget().getArmorItems();
        //if (AntiBot.get() && armorItems.contains(Items.DIAMOND_HELMET.getDefaultStack())) {
        //return false;
        // }
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

        if (rotation.get() == KillAura.RotationMode.On_Hit) rotate(target, () -> hitEntity(target));
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
            case Sword_and_Axe -> mc.player.getMainHandStack().getItem() instanceof SwordItem || mc.player.getMainHandStack().getItem() instanceof AxeItem;
            case All_Three -> mc.player.getMainHandStack().getItem() instanceof SwordItem || mc.player.getMainHandStack().getItem() instanceof AxeItem || mc.player.getMainHandStack().getItem() instanceof HoeItem;
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
    //_____________________________EXPLOIT_____________________________________
    //_____________________________MINIGAME____________________________________
    //_____________________________WORLD_______________________________________
    //_____________________________VIAVERSION__________________________________
    //_____________________________EXPERIMENTAL________________________________
    //_____________________________MISC________________________________________
}
