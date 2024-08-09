/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.combat;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.mixin.AccessorMinecraftClient;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.friends.Friends;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.movement.Flight;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static meteordevelopment.meteorclient.utils.player.MoveUtils.hasMovement;


public final class TriggerBot extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    // private final SettingGroup sgAntiKick = settings.createGroup("Weapon"); //Pog

   private final Setting<List<Item>> weapons = sgGeneral.add(new ItemListSetting.Builder()
        .name("Weapons")
        .description("The Weapons Triggerbot should use")
        .build()
    );

    private final Setting<Set<EntityType<?>>> entities = sgGeneral.add(new EntityTypeListSetting.Builder()
        .name("entities")
        .description("Entities to attack.")
        .onlyAttackable()
        .defaultValue(EntityType.PLAYER)
        .build()
    );

    private final Setting<Boolean> disableShields = sgGeneral.add(new BoolSetting.Builder()
        .name("ShieldDisable")
        .description("Disables the enemies' shields!")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> packet = sgGeneral.add(new BoolSetting.Builder()
        .name("packet")
        .description("Makes you sneak.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> teams = sgGeneral.add(new BoolSetting.Builder()
        .name("Teams")
        .description("Makes you not target teammates.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> autoCrit = sgGeneral.add(new BoolSetting.Builder()
        .name("autoCrit")
        .description("Makes you sneak.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> Block = sgGeneral.add(new BoolSetting.Builder()
        .name("Cancel hits while blocking!")
        .description("Makes you sneak.")
        .defaultValue(true)
        .build()
    );


    private final Setting<Double> Cooldown = sgGeneral.add(new DoubleSetting.Builder()
        .name("Cooldown")
        .description("Weapon Cooldown")
        .defaultValue(0.192)
        .min(0)
        .sliderMax(1)
        .build()
    );

    private final Setting<Double> ticksCooldown = sgGeneral.add(new DoubleSetting.Builder()
        .name("Ticks")
        .description("Tick cooldown")
        .defaultValue(11)
        .min(0)
        .sliderMax(20)
        .build()
    );

    private final Setting<Double> fallDistance = sgGeneral.add(new DoubleSetting.Builder()
        .name("fallDistance")
        .description("The max distance you gotta fall.")
        .defaultValue(0.25)
        .min(0)
        .sliderMax(1)
        .build()
    );

    private final Setting<Boolean> debug = sgGeneral.add(new BoolSetting.Builder()
        .name("debug!")
        .description("De-bugs.")
        .defaultValue(true)
        .build()
    );

    public TriggerBot() {
        super(Categories.Combat, "TriggerBot", "Triggers Bots");
    }

    private int delay = 0;
    private double hit_cooldown = Cooldown.get();
    public EntityHitResult entityResult;

    @EventHandler
    private void onTick(final TickEvent.Pre e) {

        if (mc.player == null || mc.world == null)
            return;

        if (debug.get() && mc.crosshairTarget instanceof EntityHitResult)
            ChatUtils.addMessage(Text.literal("The thing ur doing pointing at is " + mc.crosshairTarget));

        if (shouldSkipTick())
            return;

        if (itemInHand() && this.delay >= ticksCooldown.get() && shouldPerformAttack()) {
            doAttack();
            this.delay = 0;
        }
        this.delay++;
    }

    private void doAttack() {
        assert mc.player != null;
        Entity target = mc.targetedEntity;
        Predicate<ItemStack> predicate = stack -> stack.getItem() instanceof AxeItem;
        FindItemResult weaponResult = InvUtils.findInHotbar(predicate);

        assert target != null;
        if (shouldBreakShield((PlayerEntity) target) && disableShields.get()) {
            int lastSlot = mc.player.getInventory().selectedSlot;
            FindItemResult axeResult = InvUtils.findInHotbar(itemStack -> itemStack.getItem() instanceof AxeItem);
            if (axeResult.found()) weaponResult = axeResult;
            InvUtils.swap(weaponResult.slot(), false);
            ((AccessorMinecraftClient)mc).leftClick();
            ((AccessorMinecraftClient)mc).leftClick();
            InvUtils.swap(lastSlot, false);
            return;
        }

        if (!mc.player.isOnGround() && hasMovement() && packet.get()) {
                sendNoEvent(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.STOP_SPRINTING));
        }
        if (mc.player.getAttackCooldownProgress(0.5F) >= hit_cooldown) {
             if (shouldPerformAttack())
                ((AccessorMinecraftClient) mc).leftClick();
        } else if (!autoCrit.get())
            ((AccessorMinecraftClient)mc).leftClick();
        // GLFW.GLFW_MOUSE_BUTTON_LEFT
    }


    private boolean shouldSkipTick() {
        return mc.currentScreen != null
            || mc.player == null
            || (mc.player.isUsingItem() || mc.player.isBlocking() && Block.get());
    }

    private boolean shouldBreakShield(final PlayerEntity player) {
        return player.blockedByShield(mc.world.getDamageSources().playerAttack(mc.player)) && disableShields.get();
    }

    private boolean shouldPerformAttack() {
        if (mc.crosshairTarget instanceof EntityHitResult) {
            assert mc.player != null;
            entityResult = ((EntityHitResult) mc.crosshairTarget);
            return isValidTarget(entityResult.getEntity())
                && entityResult.getEntity().isAlive()
                && !entityResult.getEntity().isInvulnerable()
                && (mc.player.isOnGround() || mc.player.fallDistance >= fallDistance.get() || hasFlyUtilities())
                // && mc.player.getAttackCooldownProgress(0.5F) >= hit_cooldown
                //&& !isBoxItem(mc.player.getMainHandStack().getItem())
                ;
        }
        return false;
    }
    private boolean hasFlyUtilities () {
        assert mc.player != null;
        return mc.player.getAbilities().flying || Modules.get().isActive(Flight.class);
    }

    private boolean itemInHand() {
        assert mc.player != null;
        final Item item = mc.player.getMainHandStack().getItem();
        return weapons.get().contains(item);
    }

    private boolean isValidTarget(final Entity crossHairTarget){
        // if the crossHairTarget is yourself or if the crossHairTarget is a friend
        if (crossHairTarget instanceof PlayerEntity)
            if (Friends.get().isFriend((PlayerEntity) crossHairTarget)) return false;

        if (teams.get() && crossHairTarget.isTeammate(mc.player))
            return false;

        if (debug.get())
            System.out.println("the entity ist " + crossHairTarget);

        return entities.get().contains(crossHairTarget.getType());
        // return true;
    }
}
