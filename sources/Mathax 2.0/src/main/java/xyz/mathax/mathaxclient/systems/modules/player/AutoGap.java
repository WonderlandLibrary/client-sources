package xyz.mathax.mathaxclient.systems.modules.player;

import baritone.api.BaritoneAPI;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.entity.player.ItemUseCrosshairTargetEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.IntSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.combat.CrystalAura;
import xyz.mathax.mathaxclient.systems.modules.combat.KillAura;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AutoGap extends Module {
    private static final Class<? extends Module>[] AURAS = new Class[] {
            KillAura.class,
            CrystalAura.class/*,
            AnchorAura.class,
            BedAura.class*/
    };

    private final List<Class<? extends Module>> wasAura = new ArrayList<>();

    private boolean requiresEGap;

    private boolean eating;
    private int slot, prevSlot;

    private boolean wasBaritone;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup potionsSettings = settings.createGroup("Potions");
    private final SettingGroup healthSettings = settings.createGroup("Health");

    // General

    private final Setting<Boolean> preferEGapSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Prefer EGap")
            .description("Prefers to eat E-Gap over Gaps if found.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> alwaysSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Always")
            .description("If it should always eat.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> pauseAuras = generalSettings.add(new BoolSetting.Builder()
            .name("Pause auras")
            .description("Pauses all auras when eating.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> pauseBaritone = generalSettings.add(new BoolSetting.Builder()
            .name("Pause baritone")
            .description("Pause baritone when eating.")
            .defaultValue(true)
            .build()
    );

    // Potions

    private final Setting<Boolean> potionsRegeneration = potionsSettings.add(new BoolSetting.Builder()
            .name("Potions regeneration")
            .description("If it should eat when Regeneration runs out.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> potionsFireResistance = potionsSettings.add(new BoolSetting.Builder()
            .name("Potions fire resistance")
            .description("If it should eat when Fire Resistance runs out. Requires E-Gaps.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> potionsResistance = potionsSettings.add(new BoolSetting.Builder()
            .name("Potions absorption")
            .description("If it should eat when Resistance runs out. Requires E-Gaps.")
            .defaultValue(false)
            .build()
    );

    // Health

    private final Setting<Boolean> healthEnabled = healthSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("If it should eat when health drops below threshold.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Integer> healthThreshold = healthSettings.add(new IntSetting.Builder()
            .name("Health threshold")
            .description("Health threshold to eat at. Includes absorption.")
            .defaultValue(20)
            .min(0)
            .sliderRange(0, 40)
            .build()
    );

    public AutoGap(Category category) {
        super(category, "Auto Gap", "Automatically eats Gaps or E-Gaps.");
    }

    @Override
    public void onDisable() {
        if (eating) {
            stopEating();
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (eating) {
            if (shouldEat()) {
                if (isNotGapOrEGap(mc.player.getInventory().getStack(slot))) {
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
        if (pauseAuras.get()) {
            for (Class<? extends Module> klass : AURAS) {
                Module module = Modules.get().get(klass);
                if (module.isEnabled()) {
                    wasAura.add(klass);
                    module.toggle();
                }
            }
        }

        wasBaritone = false;
        if (pauseBaritone.get() && BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()) {
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

        if (pauseAuras.get()) {
            for (Class<? extends Module> klass : AURAS) {
                Module module = Modules.get().get(klass);
                if (wasAura.contains(klass) && !module.isEnabled()) {
                    module.toggle();
                }
            }
        }

        if (pauseBaritone.get() && wasBaritone) {
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
        requiresEGap = false;

        if (alwaysSetting.get()) {
            return true;
        }

        if (shouldEatPotions()) {
            return true;
        }

        return shouldEatHealth();
    }

    private boolean shouldEatPotions() {
        Map<StatusEffect, StatusEffectInstance> effects = mc.player.getActiveStatusEffects();
        if (potionsRegeneration.get() && !effects.containsKey(StatusEffects.REGENERATION)) {
            return true;
        }

        if (potionsFireResistance.get() && !effects.containsKey(StatusEffects.FIRE_RESISTANCE)) {
            requiresEGap = true;
            return true;
        }

        if (potionsResistance.get() && !effects.containsKey(StatusEffects.RESISTANCE)) {
            requiresEGap = true;
            return true;
        }

        return false;
    }

    private boolean shouldEatHealth() {
        if (!healthEnabled.get()) {
            return false;
        }

        return PlayerUtils.getTotalHealth(true) < healthThreshold.get();
    }

    private int findSlot() {
        boolean preferEGap = preferEGapSetting.get();
        if (requiresEGap) {
            preferEGap = true;
        }

        int slot = -1;
        Item currentItem = null;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.isEmpty()) {
                continue;
            }

            if (isNotGapOrEGap(stack)) {
                continue;

            }

            Item item = stack.getItem();
            if (currentItem == null) {
                slot = i;
                currentItem = item;
            } else {
                if (currentItem == item) {
                    continue;
                }

                if (item == Items.ENCHANTED_GOLDEN_APPLE && preferEGap) {
                    slot = i;
                    currentItem = item;

                    break;
                } else if (item == Items.GOLDEN_APPLE && !preferEGap) {
                    slot = i;
                    currentItem = item;

                    break;
                }
            }
        }

        if (requiresEGap && currentItem != Items.ENCHANTED_GOLDEN_APPLE) {
            return -1;
        }

        return slot;
    }

    private boolean isNotGapOrEGap(ItemStack stack) {
        Item item = stack.getItem();
        return item != Items.GOLDEN_APPLE && item != Items.ENCHANTED_GOLDEN_APPLE;
    }

    public boolean isEating() {
        return isEnabled() && eating;
    }
}