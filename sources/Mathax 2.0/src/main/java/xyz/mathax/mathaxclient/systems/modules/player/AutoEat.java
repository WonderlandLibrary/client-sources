package xyz.mathax.mathaxclient.systems.modules.player;

import baritone.api.BaritoneAPI;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.eventbus.EventPriority;
import xyz.mathax.mathaxclient.events.entity.player.ItemUseCrosshairTargetEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.combat.CrystalAura;
import xyz.mathax.mathaxclient.systems.modules.combat.KillAura;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.player.InvUtils;

import java.util.ArrayList;
import java.util.List;

public class AutoEat extends Module {
    private static final Class<? extends Module>[] AURAS = new Class[] {
            KillAura.class,
            CrystalAura.class/*,
            AnchorAura.class,
            BedAura.class*/
    };

    private final List<Class<? extends Module>> wasAura = new ArrayList<>();

    private boolean wasBaritone = false;
    public boolean eating;

    private int slot, prevSlot;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup hungerSettings = settings.createGroup("Hunger");

    // General

    private final Setting<List<Item>> blacklistSetting = generalSettings.add(new ItemListSetting.Builder()
            .name("Blacklist")
            .description("Which items to not eat.")
            .defaultValue(
                    Items.ENCHANTED_GOLDEN_APPLE,
                    Items.GOLDEN_APPLE,
                    Items.CHORUS_FRUIT,
                    Items.POISONOUS_POTATO,
                    Items.PUFFERFISH,
                    Items.CHICKEN,
                    Items.ROTTEN_FLESH,
                    Items.SPIDER_EYE,
                    Items.SUSPICIOUS_STEW
            )
            .filter(Item::isFood)
            .build()
    );

    private final Setting<Boolean> pauseAurasSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Pause auras")
            .description("Pauses all auras when eating.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> pauseBaritoneSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Pause Baritone")
            .description("Pause baritone when eating.")
            .defaultValue(true)
            .build()
    );

    // Hunger

    private final Setting<Integer> hungerThresholdSetting = hungerSettings.add(new IntSetting.Builder()
            .name("Hunger threshold")
            .description("The level of hunger you eat at.")
            .defaultValue(16)
            .range(1, 19)
            .sliderRange(1, 19)
            .build()
    );

    public AutoEat(Category category) {
        super(category, "Auto Eat", "Automatically eats food.");
    }

    @Override
    public void onDisable() {
        if (eating) {
            stopEating();
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onTick(TickEvent.Pre event) {
        if (Modules.get().get(AutoGap.class).isEating()) {
            return;
        }

        if (eating) {
            if (shouldEat()) {
                if (!mc.player.getInventory().getStack(slot).isFood()) {
                    int slot = findSlot();
                    if (slot == -1) {
                        stopEating();
                        return;
                    } else {
                        changeSlot(slot);
                    }
                }

                eat();
            } else {
                stopEating();
            }
        } else {
            if (shouldEat()) {
                slot = findSlot();
                if (slot != -1) {
                    startEating();
                }
            }
        }
    }

    @EventHandler
    private void onItemUseCrosshairTarget(ItemUseCrosshairTargetEvent event) {
        if (eating) {
            event.target = null;
        }
    }

    private void startEating() {
        prevSlot = mc.player.getInventory().selectedSlot;

        eat();

        wasAura.clear();
        if (pauseAurasSetting.get()) {
            for (Class<? extends Module> klass : AURAS) {
                Module module = Modules.get().get(klass);
                if (module.isEnabled()) {
                    wasAura.add(klass);
                    module.toggle();
                }
            }
        }

        if (pauseBaritoneSetting.get() && BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing() && !wasBaritone) {
            wasBaritone = true;
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("pause");
        }
    }

    private void eat() {
        changeSlot(slot);
        setPressed(true);

        if (!mc.player.isUsingItem()) {
            Utils.rightClick();
        }

        eating = true;
    }

    private void stopEating() {
        changeSlot(prevSlot);
        setPressed(false);

        eating = false;

        if (pauseAurasSetting.get()) {
            for (Class<? extends Module> klass : AURAS) {
                Module module = Modules.get().get(klass);
                if (wasAura.contains(klass) && !module.isEnabled()) {
                    module.toggle();
                }
            }
        }

        if (pauseBaritoneSetting.get() && wasBaritone) {
            wasBaritone = false;
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute("resume");
        }
    }

    private void setPressed(boolean pressed) {
        mc.options.useKey.setPressed(pressed);
    }

    private void changeSlot(int slot) {
        InvUtils.swap(slot, false);
        this.slot = slot;
    }

    private boolean shouldEat() {
        return mc.player.getHungerManager().getFoodLevel() <= hungerThresholdSetting.get();
    }

    private int findSlot() {
        int slot = -1;
        int bestHunger = -1;
        for (int i = 0; i < 9; i++) {
            Item item = mc.player.getInventory().getStack(i).getItem();
            if (!item.isFood()) {
                continue;
            }

            int hunger = item.getFoodComponent().getHunger();
            if (hunger > bestHunger) {
                if (blacklistSetting.get().contains(item)) {
                    continue;
                }

                slot = i;
                bestHunger = hunger;
            }
        }

        return slot;
    }
}