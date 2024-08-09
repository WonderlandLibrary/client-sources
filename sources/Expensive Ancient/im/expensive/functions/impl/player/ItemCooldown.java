package im.expensive.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventCalculateCooldown;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.BooleanSetting;
import im.expensive.functions.settings.impl.ModeListSetting;
import im.expensive.functions.settings.impl.SliderSetting;
import im.expensive.utils.client.ClientUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.*;
import java.util.function.Supplier;

@FieldDefaults(level = AccessLevel.PRIVATE)
@FunctionRegister(name = "ItemsCooldown", type = Category.Player)
public class ItemCooldown extends Function {

    public static final ModeListSetting items = new ModeListSetting("Предметы",
            new BooleanSetting("Геплы", true),
            new BooleanSetting("Перки", true),
            new BooleanSetting("Хорусы", true),
            new BooleanSetting("Чарки", false));

    static final SliderSetting gappleTime = new SliderSetting("Кулдаун гепла", 4.5F, 1.0F, 10.0F, 0.05F)
            .setVisible(() -> items.getValueByName("Геплы").get());
    static final SliderSetting pearlTime = new SliderSetting("Кулдаун перок", 14.05F, 1.0F, 15.0F, 0.05F)
            .setVisible(() -> items.getValueByName("Перки").get());
    static final SliderSetting horusTime = new SliderSetting("Кулдаун хорусов", 2.3F, 1.0F, 10.0F, 0.05F)
            .setVisible(() -> items.getValueByName("Хорусы").get());
    static final SliderSetting enchantmentGappleTime = new SliderSetting("Кулдаун чарок", 4.5F, 1.0F, 10.0F, 0.05F)
            .setVisible(() -> items.getValueByName("Чарки").get());

    private final BooleanSetting onlyPvP = new BooleanSetting("Только в PVP", true);

    public ItemCooldown() {
        addSettings(items, gappleTime, pearlTime, horusTime, enchantmentGappleTime, onlyPvP);
    }

    public HashMap<Item, Long> lastUseItemTime = new HashMap<>();
    public boolean isCooldown;

    @Subscribe
    public void onCalculateCooldown(EventCalculateCooldown e) {
        applyGoldenAppleCooldown(e);
    }

    private void applyGoldenAppleCooldown(EventCalculateCooldown calcCooldown) {
        List<Item> itemsToRemove = new ArrayList<>();

        for (Map.Entry<Item, Long> entry : lastUseItemTime.entrySet()) {
            ItemEnum itemEnum = ItemEnum.getItemEnum(entry.getKey());

            if (itemEnum == null
                    || calcCooldown.getItemStack() != itemEnum.getItem()
                    || !itemEnum.getActive().get()
                    || isNotPvP()) {
                continue;
            }

            long time = System.currentTimeMillis() - entry.getValue();
            float timeSetting = itemEnum.getTime().get() * 1000.0F;

            if (time < timeSetting && itemEnum.getActive().get()) {
                calcCooldown.setCooldown(time / timeSetting);
                isCooldown = true;
            } else {
                isCooldown = false;
                itemsToRemove.add(itemEnum.getItem());
            }
        }

        itemsToRemove.forEach(lastUseItemTime::remove);
    }

    public boolean isNotPvP() {
        return onlyPvP.get() && !ClientUtil.isPvP();
    }


    public boolean isCurrentItem(ItemEnum item) {
        if (!item.getActive().get()) {
            return false;
        }

        return item.getActive().get() && Arrays.stream(ItemEnum.values()).anyMatch(e -> e == item);
    }

    @Getter
    public enum ItemEnum {
        CHORUS(Items.CHORUS_FRUIT,
                () -> items.getValueByName("Хорусы").get(),
                horusTime::get),
        GOLDEN_APPLE(Items.GOLDEN_APPLE,
                () -> items.getValueByName("Геплы").get(),
                gappleTime::get),
        ENCHANTED_GOLDEN_APPLE(Items.ENCHANTED_GOLDEN_APPLE,
                () -> items.getValueByName("Чарки").get(),
                enchantmentGappleTime::get),
        ENDER_PEARL(Items.ENDER_PEARL,
                () -> items.getValueByName("Перки").get(),
                pearlTime::get);

        private final Item item;
        private final Supplier<Boolean> active;
        private final Supplier<Float> time;


        ItemEnum(Item item, Supplier<Boolean> active, Supplier<Float> time) {
            this.item = item;
            this.active = active;
            this.time = time;
        }

        public static ItemEnum getItemEnum(Item item) {
            return Arrays.stream(ItemEnum.values())
                    .filter(e -> e.getItem() == item)
                    .findFirst()
                    .orElse(null);
        }
    }
}
