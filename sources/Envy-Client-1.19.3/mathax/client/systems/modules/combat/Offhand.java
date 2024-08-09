package mathax.client.systems.modules.combat;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.mathax.MouseButtonEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.EnumSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.systems.modules.Modules;
import mathax.client.utils.misc.input.KeyAction;
import mathax.client.utils.player.FindItemResult;
import mathax.client.utils.player.InvUtils;
import mathax.client.utils.player.PlayerUtils;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;

public class Offhand extends Module {
    private final AutoTotem autoTotem = Modules.get().get(AutoTotem.class);

    private Item currentItem;

    private boolean isClicking;
    private boolean sentMessage;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // General

    private final Setting<Item> item = sgGeneral.add(new EnumSetting.Builder<Item>()
        .name("item")
        .description("Which item to hold in your offhand.")
        .defaultValue(Item.Crystal)
        .build()
    );

    private final Setting<Boolean> hotbar = sgGeneral.add(new BoolSetting.Builder()
        .name("hotbar")
        .description("Whether to use items from your hotbar.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> toggleNotFound = sgGeneral.add(new BoolSetting.Builder()
        .name("toggle-not-found")
        .description("Toggles when you dont have the item you chose.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> rightClick = sgGeneral.add(new BoolSetting.Builder()
        .name("right-click")
        .description("Only holds the item in your offhand when you are holding right click.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> swordGap = sgGeneral.add(new BoolSetting.Builder()
        .name("sword-gap")
        .description("Holds an Enchanted Golden Apple when you are holding a sword.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> crystalCrystalAura = sgGeneral.add(new BoolSetting.Builder()
        .name("crystal-on-crystal-aura")
        .description("Holds a crystal when you have Crystal Aura enabled.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> crystalMine = sgGeneral.add(new BoolSetting.Builder()
        .name("crystal-on-mine")
        .description("Holds a crystal when you are mining.")
        .defaultValue(false)
        .build()
    );

    public Offhand() {
        super(Categories.Combat, Items.ENCHANTED_GOLDEN_APPLE, "offhand", "Allows you to hold specified items in your offhand.");
    }

    @Override
    public boolean onActivate() {
        sentMessage = false;
        isClicking = false;
        currentItem = item.get();
        return false;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (autoTotem.mode.get() == AutoTotem.Mode.Strict && autoTotem.isActive()) {
            info("(highlight)%s(default) does not work with (highlight)%s(default) set to (highlight)%s(default) mode, disabling...", title, autoTotem.title, "Strict");
            toggle();
            return;
        }

        if (autoTotem.mode.get() != AutoTotem.Mode.Strict && autoTotem.isActive() && PlayerUtils.getTotalHealth() - PlayerUtils.possibleHealthReductions(autoTotem.explosion.get(), autoTotem.fall.get()) <= autoTotem.health.get()) return;

        if ((mc.player.getMainHandStack().getItem() instanceof SwordItem || mc.player.getMainHandStack().getItem() instanceof AxeItem) && swordGap.get()) currentItem = Item.EGap;
        else if ((Modules.get().isActive(CrystalAura.class) && crystalCrystalAura.get()) || mc.interactionManager.isBreakingBlock() && crystalMine.get()) currentItem = Item.Crystal;
        else currentItem = item.get();

        if (mc.player.getOffHandStack().getItem() != currentItem.item) {
            FindItemResult item = InvUtils.find(itemStack -> itemStack.getItem() == currentItem.item, hotbar.get() ? 0 : 9, 35);
            if (!item.found()) {
                if (!sentMessage) {
                    warning("Chosen item not found" + (toggleNotFound.get() ? ", disabling..." : "."));
                    if (toggleNotFound.get()) {
                        toggle();
                        return;
                    }

                    sentMessage = true;
                }
            } else if ((isClicking || !rightClick.get()) && !autoTotem.isLocked() && !item.isOffhand()) {
                InvUtils.move().from(item.slot()).toOffhand();
                sentMessage = false;
            }
        }

        else if (!isClicking && rightClick.get()) {
            if (autoTotem.isActive()) {
                FindItemResult totem = InvUtils.find(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING, hotbar.get() ? 0 : 9, 35);
                if (totem.found() && !totem.isOffhand()) InvUtils.move().from(totem.slot()).toOffhand();
            } else {
                FindItemResult empty = InvUtils.find(ItemStack::isEmpty, hotbar.get() ? 0 : 9, 35);
                if (empty.found()) InvUtils.move().fromOffhand().to(empty.slot());
            }
        }
    }

    @EventHandler
    private void onMouseButton(MouseButtonEvent event) {
        isClicking = mc.currentScreen == null && !autoTotem.isLocked() && !usableItem() && !mc.player.isUsingItem() && event.action == KeyAction.Press && event.button == GLFW_MOUSE_BUTTON_RIGHT;
    }

    private boolean usableItem() {
        return mc.player.getMainHandStack().getItem() == Items.BOW || mc.player.getMainHandStack().getItem() == Items.TRIDENT || mc.player.getMainHandStack().getItem() == Items.CROSSBOW || mc.player.getMainHandStack().getItem().isFood();
    }

    @Override
    public String getInfoString() {
        return item.get().name();
    }

    public enum Item {
        EGap("EGap", Items.ENCHANTED_GOLDEN_APPLE),
        Gap("Gap", Items.GOLDEN_APPLE),
        Crystal("Crystal", Items.END_CRYSTAL),
        Shield("Shield", Items.SHIELD);

        private final String title;
        private final net.minecraft.item.Item item;

        Item(String title, net.minecraft.item.Item item) {
            this.title = title;
            this.item = item;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
