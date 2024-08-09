package mathax.client.systems.modules.combat;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.entity.TargetUtils;
import mathax.client.utils.player.FindItemResult;
import mathax.client.utils.player.InvUtils;
import mathax.client.utils.player.Rotations;
import mathax.client.utils.player.SlotUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public class AutoEXP extends Module {
    private int repairingI;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // General

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("Which items to repair.")
        .defaultValue(Mode.Both)
        .build()
    );

    private final Setting<Boolean> replenish = sgGeneral.add(new BoolSetting.Builder()
        .name("replenish")
        .description("Automatically replenishes exp into a selected hotbar slot.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Integer> slot = sgGeneral.add(new IntSetting.Builder()
        .name("exp-slot")
        .description("The slot to replenish exp into.")
        .visible(replenish::get)
        .defaultValue(6)
        .range(1, 9)
        .sliderRange(1, 9)
        .build()
    );

    private final Setting<Integer> minThreshold = sgGeneral.add(new IntSetting.Builder()
        .name("min-threshold")
        .description("The minimum durability percentage that an item needs to fall to, to be repaired.")
        .defaultValue(30)
        .range(1, 100)
        .sliderRange(1, 100)
        .build()
    );

    private final Setting<Integer> maxThreshold = sgGeneral.add(new IntSetting.Builder()
        .name("max-threshold")
        .description("The maximum durability percentage to repair items to.")
        .defaultValue(80)
        .range(1, 100)
        .sliderRange(1, 100)
        .build()
    );

    private final Setting<Boolean> playerNearStop = sgGeneral.add(new BoolSetting.Builder()
        .name("player-near-stop")
        .description("Stops repairing when a player is near you.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> playerNearStopRange = sgGeneral.add(new DoubleSetting.Builder()
        .name("range")
        .description("Determines the range other players have to be in to stop repairing.")
        .defaultValue(3)
        .min(0)
        .sliderRange(0, 5)
        .visible(playerNearStop::get)
        .build()
    );

    public AutoEXP() {
        super(Categories.Combat, Items.EXPERIENCE_BOTTLE, "auto-exp", "Automatically repairs your armor and tools in pvp.");
    }

    @Override
    public boolean onActivate() {
        repairingI = -1;
        return false;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (playerNearStop.get() && TargetUtils.isPlayerNear(playerNearStopRange.get())) return;

        if (repairingI == -1) {
            if (mode.get() != Mode.Hands) {
                for (int i = 0; i < mc.player.getInventory().armor.size(); i++) {
                    if (needsRepair(mc.player.getInventory().armor.get(i), minThreshold.get())) {
                        repairingI = SlotUtils.ARMOR_START + i;
                        break;
                    }
                }
            }

            if (mode.get() != Mode.Armor && repairingI == -1) {
                for (Hand hand : Hand.values()) {
                    if (needsRepair(mc.player.getStackInHand(hand), minThreshold.get())) {
                        repairingI = hand == Hand.MAIN_HAND ? mc.player.getInventory().selectedSlot : SlotUtils.OFFHAND;
                        break;
                    }
                }
            }
        }

        if (repairingI != -1) {
            if (!needsRepair(mc.player.getInventory().getStack(repairingI), maxThreshold.get())) {
                repairingI = -1;
                return;
            }

            FindItemResult exp = InvUtils.find(Items.EXPERIENCE_BOTTLE);

            if (exp.found()) {
                if (!exp.isHotbar() && !exp.isOffhand()) {
                    if (!replenish.get()) return;
                    InvUtils.move().from(exp.slot()).toHotbar(slot.get() - 1);
                }

                Rotations.rotate(mc.player.getYaw(), 90, () -> {
                    if (exp.getHand() != null) mc.interactionManager.interactItem(mc.player, exp.getHand());
                    else {
                        InvUtils.swap(exp.slot(), true);
                        mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
                        InvUtils.swapBack();
                    }
                });
            }
        }
    }

    private boolean needsRepair(ItemStack itemStack, double threshold) {
        if (itemStack.isEmpty() || EnchantmentHelper.getLevel(Enchantments.MENDING, itemStack) < 1) return false;
        return (itemStack.getMaxDamage() - itemStack.getDamage()) / (double) itemStack.getMaxDamage() * 100 <= threshold;
    }

    public enum Mode {
        Armor("Armor"),
        Hands("Hands"),
        Both("Both");

        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
