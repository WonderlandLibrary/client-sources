/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.combat;

import meteordevelopment.meteorclient.events.meteor.MouseButtonEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.misc.input.KeyAction;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;

import java.util.List;

import static meteordevelopment.orbit.EventPriority.HIGHEST;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;

public final class Offhand extends Module {
    private final SettingGroup sgCombat = settings.createGroup("Combat");
    private final SettingGroup sgTotem = settings.createGroup("Totem");

    //Combat

    private final Setting<Integer> delayTicks = sgCombat.add(new IntSetting.Builder()
        .name("item-switch-delay")
        .description("The delay in ticks between slot movements.")
        .defaultValue(0)
        .min(0)
        .sliderMax(20)
        .build()
    );
    private final Setting<usableItem> preferreditem = sgCombat.add(new EnumSetting.Builder<usableItem>()
        .name("item")
        .description("Which item to hold in your offhand.")
        .defaultValue(usableItem.Crystal)
        .build()
    );

    private final Setting<Boolean> hotbar = sgCombat.add(new BoolSetting.Builder()
        .name("hotbar")
        .description("Whether to use items from your hotbar.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> rightgapple = sgCombat.add(new BoolSetting.Builder()
        .name("right-gapple")
        .description("Will switch to a gapple when holding right click.(DO NOT USE WITH POTION ON)")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> SwordGap = sgCombat.add(new BoolSetting.Builder()
        .name("sword-gapple")
        .description("Will switch to a gapple when holding a sword and right click.")
        .defaultValue(false)
        .visible(rightgapple::get)
        .build()
    );

    private final Setting<Boolean> alwaysSwordGap = sgCombat.add(new BoolSetting.Builder()
        .name("always-gap-on-sword")
        .description("Holds an Enchanted Golden Apple when you are holding a sword.")
        .defaultValue(false)
        .visible(() -> !rightgapple.get())
        .build()
    );

    private final Setting<Boolean> alwaysPot = sgCombat.add(new BoolSetting.Builder()
        .name("always-pot-on-sword")
        .description("Will switch to a potion when holding a sword")
        .defaultValue(false)
        .visible(() -> !rightgapple.get() && !alwaysSwordGap.get())
        .build()
    );

    private final Setting<Boolean> alwaysBeefSword = sgCombat.add(new BoolSetting.Builder()
        .name("steak-on-sword")
        .description("Will switch to a steak always")
        .defaultValue(false)
        .visible(() -> !rightgapple.get() && !alwaysSwordGap.get() && !alwaysPot.get())
        .build()
    );

    private final Setting<Boolean> alwaysBlock = sgCombat.add(new BoolSetting.Builder()
        .name("Always-Block")
        .description("Will switch to a block always")
        .defaultValue(false)
        .visible(() -> !rightgapple.get() && !alwaysSwordGap.get() && !alwaysPot.get())
        .build()
    );

    private final Setting<List<net.minecraft.item.Item>> blocks = sgCombat.add(new ItemListSetting.Builder()
        .name("Blocks")
        .description("The blocks... or items... you should have in the offhand")
        .visible(alwaysBlock::get)
        .build()
    );

    private final Setting<Boolean> alwaysBeef = sgCombat.add(new BoolSetting.Builder()
        .name("steak-on-sword")
        .description("Will switch to a steak always")
        .defaultValue(false)
        .visible(() -> !rightgapple.get() && !alwaysSwordGap.get() && !alwaysPot.get())
        .build()
    );
    private final Setting<Boolean> potionClick = sgCombat.add(new BoolSetting.Builder()
        .name("sword-pot")
        .description("Will switch to a potion when holding a sword and right click.")
        .defaultValue(false)
        .visible(() -> !rightgapple.get() && !alwaysPot.get() && !alwaysSwordGap.get() )
        .build()
    );

    //Totem

    private final Setting<Double> minHealth = sgTotem.add(new DoubleSetting.Builder()
        .name("min-health")
        .description("Will hold a totem when below this amount of health.")
        .defaultValue(10)
        .range(0,36)
        .sliderRange(0,36)
        .build()
    );

    private final Setting<Boolean> elytra = sgTotem.add(new BoolSetting.Builder()
        .name("elytra")
        .description("Will always hold a totem while flying with an elytra.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> falling = sgTotem.add(new BoolSetting.Builder()
        .name("falling")
        .description("Will hold a totem if fall damage could kill you.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> explosion = sgTotem.add(new BoolSetting.Builder()
        .name("explosion")
        .description("Will hold a totem when explosion damage could kill you.")
        .defaultValue(true)
        .build()
    );

    private boolean isClicking;
    private boolean sentMessage;

    private usableItem currentItem;
    public boolean locked;

    private int totems, ticks;
    private static net.minecraft.item.Item itemget;

    public Offhand() {
        super(Categories.Combat, "offhand", "Allows you to hold specified items in your offhand.");
    }

    @Override
    public void onActivate() {
        ticks = 0;
        sentMessage = false;
        isClicking = false;
        currentItem = preferreditem.get();
    }

    @EventHandler(priority = HIGHEST + 999)
    private void onTick(final TickEvent.Pre event) throws InterruptedException {
        FindItemResult result = InvUtils.find(Items.TOTEM_OF_UNDYING);
        totems = result.count();

        if (totems <= 0) locked = false;
        else if (ticks > delayTicks.get()) {
            boolean low = mc.player.getHealth() + mc.player.getAbsorptionAmount() - PlayerUtils.possibleHealthReductions(explosion.get(), falling.get()) <= minHealth.get();
            boolean ely = elytra.get() && mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA && mc.player.isFallFlying();
            FindItemResult item = InvUtils.find(itemStack -> itemStack.getItem() == currentItem.item, 0, 35);

            // Calculates Damage from Falling, Explosions + Elyta
            locked = (low || ely);

            if (locked && mc.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING) {
                InvUtils.move().from(result.slot()).toOffhand();
            }

            ticks = 0;
            return;
        }
        ticks++;

        AutoTotem autoTotem = Modules.get().get(AutoTotem.class);

        // Returns to the original Item
        currentItem = preferreditem.get();

        // Sword Gap & Right Gap
        if (rightgapple.get()) {
            if (!locked) {
                if (SwordGap.get() && mc.player.getMainHandStack().getItem() instanceof SwordItem) {
                    if (isClicking) {
                        currentItem = usableItem.EGap;
                    }
                }
                if (!SwordGap.get()) {
                    if (isClicking) {
                        currentItem = usableItem.EGap;
                    }
                }
            }
        }

        // always beef
        else if ((mc.player.getMainHandStack().getItem() instanceof SwordItem) && alwaysBeefSword.get()) currentItem = usableItem.Steak;
        else if (alwaysBeef.get()) currentItem = usableItem.Steak;
        else if (preferreditem.get().equals(usableItem.Blocks)) {
            for (int i = 0; i <= blocks.get().size(); i++) {
                Item itemget2 = blocks.get().get(i);
                if (itemget2 instanceof BlockItem)
                    itemget = itemget2;
            }
        }

        // Always Gap
        else if ((mc.player.getMainHandStack().getItem() instanceof SwordItem || mc.player.getMainHandStack().getItem() instanceof AxeItem) && alwaysSwordGap.get()) currentItem = usableItem.EGap;

            // Potion Click
        else if (potionClick.get()) {
            if (!locked) {
                if (mc.player.getMainHandStack().getItem() instanceof SwordItem) {
                    if (isClicking) {
                        currentItem = usableItem.Potion;
                    }
                }
            }
        }

        // Always Pot
        else if ((mc.player.getMainHandStack().getItem() instanceof SwordItem || mc.player.getMainHandStack().getItem() instanceof AxeItem) && alwaysPot.get()) currentItem = usableItem.Potion;


        else currentItem = preferreditem.get();

        // Checking offhand item
        if (mc.player.getOffHandStack().getItem() != currentItem.item) {
            if (ticks >= delayTicks.get()) {
                if (!locked) {
                    FindItemResult item = InvUtils.find(itemStack -> itemStack.getItem() == currentItem.item, hotbar.get() ? 0 : 9, 35);

                    if (itemget != null)
                        item = InvUtils.find(itemStack -> itemStack.getItem() == itemget, hotbar.get() ? 0 : 9, 35);

                    // No offhand item
                    if (!item.found()) {
                        if (!sentMessage) {
                            warning("Chosen item not found.");
                            sentMessage = true;
                        }
                    }

                    // Swap to offhand
                    else if ((isClicking || !autoTotem.isLocked() && !item.isOffhand())) {
                        InvUtils.move().from(item.slot()).toOffhand();
                        sentMessage = false;
                    }
                    ticks = 0;
                    return;
                }
                ticks++;
            }
        }
    }

    @EventHandler
    private void onMouseButton(final MouseButtonEvent event) {
        // Detects if the User is right-clicking
        isClicking = mc.currentScreen == null && !Modules.get().get(AutoTotem.class).isLocked() && !usableItem() && !mc.player.isUsingItem() && event.action == KeyAction.Press && event.button == GLFW_MOUSE_BUTTON_RIGHT;
    }

    private boolean usableItem() {
        // What counts as a Usable Item
        return mc.player.getMainHandStack().getItem() == Items.BOW
            || mc.player.getMainHandStack().getItem() == Items.TRIDENT
            || mc.player.getMainHandStack().getItem() == Items.CROSSBOW
            || mc.player.getMainHandStack().getItem().isFood();
    }

    @Override
    public String getInfoString() {
        return preferreditem.get().name();
    }

    public enum usableItem {
        // Items the module could put on your offhand
        Blocks(itemget),
        Steak(Items.COOKED_BEEF),
        EGap(Items.ENCHANTED_GOLDEN_APPLE),
        Gap(Items.GOLDEN_APPLE),
        Crystal(Items.END_CRYSTAL),
        Totem(Items.TOTEM_OF_UNDYING),
        Shield(Items.SHIELD),
        Potion(Items.POTION);
        net.minecraft.item.Item item;
        usableItem(net.minecraft.item.Item item) {
            this.item = item;
        }
        usableItem() {
        }

    }
}
