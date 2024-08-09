package dev.excellent.client.module.impl.combat;

import dev.excellent.api.event.impl.player.HandInteractionEvent;
import dev.excellent.api.event.impl.player.ItemCooldownEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.util.player.PlayerUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.MultiBooleanValue;
import dev.excellent.impl.value.impl.NumberValue;
import lombok.Getter;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.*;
import java.util.function.Supplier;

@ModuleInfo(name = "Custom Cooldown", description = "Устанавливает задержку на использование предметов.", category = Category.COMBAT)
public class CustomCooldown extends Module {

    public static Singleton<CustomCooldown> singleton = Singleton.create(() -> Module.link(CustomCooldown.class));
    private final MultiBooleanValue items = new MultiBooleanValue("Предметы", this)
            .add(
                    new BooleanValue("Геплы", true),
                    new BooleanValue("Перки", true),
                    new BooleanValue("Хорусы", true),
                    new BooleanValue("Чарки", true)
            );
    private final NumberValue gappleTime = new NumberValue("Кулдаун гепла", this, 4.5F, 1.0F, 10.0F, 0.05F, () -> !items.isEnabled("Геплы"));
    private final NumberValue pearlTime = new NumberValue("Кулдаун перок", this, 14.05F, 1.0F, 15.0F, 0.05F, () -> !items.isEnabled("Перки"));
    private final NumberValue horusTime = new NumberValue("Кулдаун хорусов", this, 2.3F, 1.0F, 10.0F, 0.05F, () -> !items.isEnabled("Хорусы"));
    private final NumberValue enchantmentGappleTime = new NumberValue("Кулдаун чарок", this, 4.5F, 1.0F, 10.0F, 0.05F, () -> !items.isEnabled("Чарки"));
    private final BooleanValue onlyPvP = new BooleanValue("Только в PVP", this, false);
    public HashMap<Item, Long> lastUseItemTime = new HashMap<>();
    private final Listener<HandInteractionEvent> onHandInteractionEvent = event -> {
        ItemEnum itemEnum = ItemEnum.getItemEnum(event.getStack().getItem());
        if (itemEnum != null && isCurrentItem(itemEnum) && mc.player.getCooldownTracker().hasCooldown(itemEnum.getItem())) {
            event.cancel();
        }
    };
    private final Listener<ItemCooldownEvent> onItemCooldown = this::applyGoldenAppleCooldown;

    private void applyGoldenAppleCooldown(ItemCooldownEvent calcCooldown) {
        List<Item> itemsToRemove = new ArrayList<>();

        for (Map.Entry<Item, Long> entry : lastUseItemTime.entrySet()) {
            ItemEnum itemEnum = ItemEnum.getItemEnum(entry.getKey());

            if (itemEnum == null || calcCooldown.item != itemEnum.getItem() || !itemEnum.getActive().get() || isNotPvP()) {
                continue;
            }

            long time = System.currentTimeMillis() - entry.getValue();
            float timeSetting = itemEnum.getTime().get() * 1000.0F;

            if (time < timeSetting && itemEnum.getActive().get()) {
                calcCooldown.setCooldown(time / timeSetting);
            } else {
                itemsToRemove.add(itemEnum.getItem());
            }
        }

        itemsToRemove.forEach(lastUseItemTime::remove);
    }

    public boolean isNotPvP() {
        return onlyPvP.getValue() && !PlayerUtil.isPvp();
    }

    public boolean isCurrentItem(ItemEnum item) {
        if (!item.getActive().get()) {
            return false;
        }

        return item.getActive().get() && Arrays.stream(ItemEnum.values()).anyMatch(e -> e == item);
    }

    @Getter
    public enum ItemEnum {

        GOLDEN_APPLE(Items.GOLDEN_APPLE,
                () -> singleton.get().items.isEnabled("Геплы"),
                () -> singleton.get().gappleTime.getValue().floatValue()),
        ENDER_PEARL(Items.ENDER_PEARL,
                () -> singleton.get().items.isEnabled("Перки"),
                () -> singleton.get().pearlTime.getValue().floatValue()),
        CHORUS(Items.CHORUS_FRUIT,
                () -> singleton.get().items.isEnabled("Хорусы"),
                () -> singleton.get().horusTime.getValue().floatValue()),
        ENCHANTED_GOLDEN_APPLE(Items.ENCHANTED_GOLDEN_APPLE,
                () -> singleton.get().items.isEnabled("Чарки"),
                () -> singleton.get().enchantmentGappleTime.getValue().floatValue());

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

