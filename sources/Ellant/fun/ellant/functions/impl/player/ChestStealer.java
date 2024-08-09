package fun.ellant.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.BooleanSetting;
import fun.ellant.functions.settings.impl.ModeListSetting;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.functions.settings.impl.SliderSetting;
import fun.ellant.utils.math.StopWatch;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@FunctionRegister(name = "ChestStealer", type = Category.PLAYER, desc = "Пиздит ресы")
public class ChestStealer extends Function {
    private final ModeSetting mode = new ModeSetting("Мод", "Умный", "Умный");
    private final BooleanSetting chestClose = new BooleanSetting("Закрывать при полном инве", true);
    private final SliderSetting stealDelay = new SliderSetting("Задержка", 100, 0, 1000, 1);
    private final BooleanSetting filterLootToggle = new BooleanSetting("Фильтр лута", false).setVisible(() -> mode.is("Умный"));
    private final ModeListSetting filterLoot = new ModeListSetting("Лут",
            new BooleanSetting("Сферы", false),
            new BooleanSetting("Зачарованная книга", false),
            new BooleanSetting("Талисманы", false),
            new BooleanSetting("Зелья", false),
            new BooleanSetting("Остальные ресурсы", false),
            new BooleanSetting("Броня", false)
    ).setVisible(() -> mode.is("Умный") && filterLootToggle.get());
    private final SliderSetting itemLimit = new SliderSetting("Макс. ресов", 64, 1, 64, 1).setVisible(() -> mode.is("Умный"));
    private final SliderSetting missPercent = new SliderSetting("Миссать слотов", 50, 0, 100, 1).setVisible(() -> mode.is("Умный"));
    private final StopWatch timerUtil = new StopWatch();

    public ChestStealer() {
        addSettings(mode, chestClose, stealDelay, filterLootToggle, filterLoot, itemLimit, missPercent);
    }

    private boolean filterItem(Item item) {
        if (!filterLootToggle.get()) {
            return true;
        }

        boolean filterHeads = filterLoot.get(1).get();
        boolean filterEnchantedBooks = filterLoot.get(3).get();
        boolean filterTotems = filterLoot.get(4).get();
        boolean filterPotions = filterLoot.get(5).get();
        boolean filterostRes = filterLoot.get(6).get();
        boolean filterBronya = filterLoot.get(7).get();
        boolean filterFT = filterLoot.get(8).get();

        if (filterHeads && item == Items.PLAYER_HEAD) {
            return true;
        }

        if (filterEnchantedBooks && item == Items.ENCHANTED_BOOK) {
            return true;
        }

        if (filterBronya && (
                item == Items.DIAMOND_HELMET ||
                        item == Items.LEATHER_HELMET ||
                        item == Items.LEATHER_CHESTPLATE ||
                        item == Items.LEATHER_LEGGINGS ||
                        item == Items.LEATHER_BOOTS ||
                        item == Items.CHAINMAIL_HELMET ||
                        item == Items.CHAINMAIL_CHESTPLATE ||
                        item == Items.CHAINMAIL_LEGGINGS ||
                        item == Items.CHAINMAIL_BOOTS ||
                        item == Items.IRON_HELMET ||
                        item == Items.IRON_CHESTPLATE ||
                        item == Items.IRON_LEGGINGS ||
                        item == Items.IRON_BOOTS ||
                        item == Items.GOLDEN_HELMET ||
                        item == Items.GOLDEN_CHESTPLATE ||
                        item == Items.GOLDEN_LEGGINGS ||
                        item == Items.GOLDEN_BOOTS ||
                        item == Items.DIAMOND_HELMET ||
                        item == Items.DIAMOND_CHESTPLATE ||
                        item == Items.DIAMOND_LEGGINGS ||
                        item == Items.DIAMOND_BOOTS ||
                        item == Items.NETHERITE_HELMET ||
                        item == Items.NETHERITE_CHESTPLATE ||
                        item == Items.NETHERITE_LEGGINGS ||
                        item == Items.NETHERITE_BOOTS

        )) {
            return true;
        }


        if (filterFT && (
                item == Items.GUNPOWDER ||
                        item == Items.GUNPOWDER ||
                        item == Items.NAUTILUS_SHELL ||
                        item == Items.LIGHT_GRAY_DYE ||
                        item == Items.GRAY_DYE ||
                        item == Items.PHANTOM_MEMBRANE
        )) {
            return true;
        }

        if (filterTotems && item == Items.TOTEM_OF_UNDYING) {
            return true;
        }

        if (filterostRes && (
                item == Items.DIAMOND ||
                        item == Items.COAL ||
                        item == Items.COAL_BLOCK ||
                        item == Items.COAL_ORE ||
                        item == Items.DIAMOND ||
                        item == Items.DIAMOND_BLOCK ||
                        item == Items.DIAMOND_ORE ||
                        item == Items.EMERALD ||
                        item == Items.EMERALD_BLOCK ||
                        item == Items.EMERALD_ORE ||
                        item == Items.EXPERIENCE_BOTTLE ||
                        item == Items.GOLD_INGOT ||
                        item == Items.GOLD_BLOCK ||
                        item == Items.GOLD_NUGGET ||
                        item == Items.GOLD_ORE ||
                        item == Items.IRON_INGOT ||
                        item == Items.IRON_BLOCK ||
                        item == Items.IRON_NUGGET ||
                        item == Items.IRON_ORE ||
                        item == Items.LAPIS_LAZULI ||
                        item == Items.LAPIS_BLOCK ||
                        item == Items.LAPIS_ORE ||
                        item == Items.NETHERITE_INGOT ||
                        item == Items.NETHERITE_SCRAP ||
                        item == Items.QUARTZ ||
                        item == Items.QUARTZ_BLOCK ||
                        item == Items.NETHER_QUARTZ_ORE ||
                        item == Items.REDSTONE ||
                        item == Items.REDSTONE_BLOCK ||
                        item == Items.REDSTONE_ORE ||
                        item == Items.ANCIENT_DEBRIS ||
                        item == Items.OBSIDIAN ||
                        item == Items.CRYING_OBSIDIAN ||
                        item == Items.GLOWSTONE ||
                        item == Items.GLOWSTONE_DUST ||
                        item == Items.NETHER_STAR ||
                        item == Items.PRISMARINE ||
                        item == Items.PRISMARINE_CRYSTALS ||
                        item == Items.PRISMARINE_SHARD ||
                        item == Items.SEA_LANTERN ||
                        item == Items.SLIME_BALL ||
                        item == Items.SLIME_BLOCK ||
                        item == Items.BLAZE_ROD ||
                        item == Items.BLAZE_POWDER ||
                        item == Items.ENDER_PEARL ||
                        item == Items.ENDER_EYE ||
                        item == Items.GHAST_TEAR ||
                        item == Items.GUNPOWDER ||
                        item == Items.MAGMA_CREAM ||
                        item == Items.SHULKER_SHELL ||
                        item == Items.SUGAR ||
                        item == Items.BONE ||
                        item == Items.BONE_MEAL ||
                        item == Items.STRING ||
                        item == Items.SPIDER_EYE ||
                        item == Items.LEATHER ||
                        item == Items.FEATHER ||
                        item == Items.FLINT ||
                        item == Items.CLAY ||
                        item == Items.CLAY_BALL ||
                        item == Items.BRICK ||
                        item == Items.BRICKS ||
                        item == Items.PAPER ||
                        item == Items.BOOK ||
                        item == Items.MAP ||
                        item == Items.COMPASS ||
                        item == Items.CLOCK ||
                        item == Items.FISHING_ROD ||
                        item == Items.BOW ||
                        item == Items.ARROW ||
                        item == Items.SPECTRAL_ARROW ||
                        item == Items.TIPPED_ARROW ||
                        item == Items.TRIDENT ||
                        item == Items.SHIELD ||
                        item == Items.SHEARS ||
                        item == Items.BUCKET ||
                        item == Items.WATER_BUCKET ||
                        item == Items.LAVA_BUCKET ||
                        item == Items.MILK_BUCKET ||
                        item == Items.MINECART ||
                        item == Items.CHEST_MINECART ||
                        item == Items.FURNACE_MINECART ||
                        item == Items.HOPPER_MINECART ||
                        item == Items.TNT_MINECART ||
                        item == Items.CARROT_ON_A_STICK ||
                        item == Items.WARPED_FUNGUS_ON_A_STICK ||
                        item == Items.ELYTRA ||
                        item == Items.LEAD ||
                        item == Items.NAME_TAG ||
                        item == Items.SADDLE ||
                        item == Items.LINGERING_POTION ||
                        item == Items.BLACK_SHULKER_BOX ||
                        item == Items.BLUE_SHULKER_BOX ||
                        item == Items.BROWN_SHULKER_BOX ||
                        item == Items.CYAN_SHULKER_BOX ||
                        item == Items.GRAY_SHULKER_BOX ||
                        item == Items.GREEN_SHULKER_BOX ||
                        item == Items.LIGHT_BLUE_SHULKER_BOX ||
                        item == Items.LIGHT_GRAY_SHULKER_BOX ||
                        item == Items.LIME_SHULKER_BOX ||
                        item == Items.MAGENTA_SHULKER_BOX ||
                        item == Items.ORANGE_SHULKER_BOX ||
                        item == Items.PINK_SHULKER_BOX ||
                        item == Items.PURPLE_SHULKER_BOX ||
                        item == Items.RED_SHULKER_BOX ||
                        item == Items.WHITE_SHULKER_BOX ||
                        item == Items.YELLOW_SHULKER_BOX
        )) {
            return true;
        }

        if (filterPotions && (
                item == Items.POTION ||
                        item == Items.SPLASH_POTION
        )) {
            return true;
        }

        return false;
    }

    @Subscribe
    public void onEvent(final EventUpdate event) {
        if (mode.is("Умный")) {
            if (mc.player.openContainer instanceof ChestContainer) {
                ChestContainer container = (ChestContainer) mc.player.openContainer;
                IInventory inventory = container.getLowerChestInventory();
                List<Integer> validSlots = new ArrayList<>();

                for (int i = 0; i < inventory.getSizeInventory(); i++) {
                    if (inventory.getStackInSlot(i).getItem() != Item.getItemById(0)
                            && inventory.getStackInSlot(i).getCount() <= itemLimit.get()
                            && filterItem(inventory.getStackInSlot(i).getItem())) {
                        validSlots.add(i);
                    }
                }

                if (!validSlots.isEmpty() && timerUtil.isReached(Math.round(stealDelay.get()))) {
                    int randomIndex = new Random().nextInt(validSlots.size());
                    int slotToSteal = validSlots.get(randomIndex);

                    if (new Random().nextInt(100) >= missPercent.get()) {
                        mc.playerController.windowClick(container.windowId, slotToSteal, 0, ClickType.QUICK_MOVE, mc.player);
                    }

                    timerUtil.reset();
                }

                if (inventory.isEmpty() && chestClose.get()) {
                    mc.player.closeScreen();
                }
            }
        }
    }
}