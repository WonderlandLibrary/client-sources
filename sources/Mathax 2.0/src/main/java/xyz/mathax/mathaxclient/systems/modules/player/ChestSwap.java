package xyz.mathax.mathaxclient.systems.modules.player;

import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.EnumSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.config.Config;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class ChestSwap extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Chestplate> chestplateSetting = generalSettings.add(new EnumSetting.Builder<Chestplate>()
            .name("Chestplate")
            .description("Which type of chestplate to swap to.")
            .defaultValue(Chestplate.Prefer_Netherite)
            .build()
    );

    private final Setting<Boolean> stayOnSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Stay on")
            .description("Stay on and activates when you turn it off.")
            .defaultValue(false)
            .build()
    );

    public ChestSwap(Category category) {
        super(category, "Chest Swap", "Automatically swaps between a chestplate and an elytra.");
    }

    @Override
    public void onEnable() {
        swap();

        if (!stayOnSetting.get()) {
            forceToggle(false);
        }
    }

    @Override
    public void onDisable() {
        if (stayOnSetting.get()) {
            swap();
        }
    }

    public void swap() {
        Item currentItem = mc.player.getEquippedStack(EquipmentSlot.CHEST).getItem();
        if (currentItem == Items.ELYTRA) {
            equipChestplate();
        } else if (currentItem instanceof ArmorItem && ((ArmorItem) currentItem).getSlotType() == EquipmentSlot.CHEST) {
            equipElytra();
        } else {
            if (!equipChestplate()) {
                equipElytra();
            }
        }
    }

    private boolean equipChestplate() {
        int bestSlot = -1;
        boolean breakLoop = false;
        for (int i = 0; i < mc.player.getInventory().main.size(); i++) {
            Item item = mc.player.getInventory().main.get(i).getItem();
            switch (chestplateSetting.get()) {
                case Diamond:
                    if (item == Items.DIAMOND_CHESTPLATE) {
                        bestSlot = i;
                        breakLoop = true;
                    }

                    break;
                case Netherite:
                    if (item == Items.NETHERITE_CHESTPLATE) {
                        bestSlot = i;
                        breakLoop = true;
                    }

                    break;
                case Prefer_Diamond:
                    if (item == Items.DIAMOND_CHESTPLATE) {
                        bestSlot = i;
                        breakLoop = true;
                    } else if (item == Items.NETHERITE_CHESTPLATE) {
                        bestSlot = i;
                    }

                    break;
                case Prefer_Netherite:
                    if (item == Items.DIAMOND_CHESTPLATE) {
                        bestSlot = i;
                    } else if (item == Items.NETHERITE_CHESTPLATE) {
                        bestSlot = i;
                        breakLoop = true;
                    }

                    break;
            }

            if (breakLoop) {
                break;
            }
        }

        if (bestSlot != -1) {
            equip(bestSlot);
        }

        return bestSlot != -1;
    }

    private void equipElytra() {
        for (int i = 0; i < mc.player.getInventory().main.size(); i++) {
            Item item = mc.player.getInventory().main.get(i).getItem();
            if (item == Items.ELYTRA) {
                equip(i);
                break;
            }
        }
    }

    private void equip(int slot) {
        InvUtils.move().from(slot).toArmor(2);
    }

    @Override
    public void sendToggled() {
        if (stayOnSetting.get()) {
            super.sendToggled();
        } else if (Config.get().chatFeedbackSetting.get()) {
            info("Triggered (highlight)%s(default).", name);
        }
    }

    public enum Chestplate {
        Diamond("Diamond"),
        Netherite("Netherite"),
        Prefer_Diamond("Prefer Diamond"),
        Prefer_Netherite("Prefer Netherite");

        private final String name;

        Chestplate(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}