/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import mpp.venusfr.events.EventCalculateCooldown;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.ModeListSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.client.ClientUtil;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

@FunctionRegister(name="ItemsCooldown", type=Category.Player)
public class ItemCooldown
extends Function {
    public static final ModeListSetting items = new ModeListSetting("\u041f\u0440\u0435\u0434\u043c\u0435\u0442\u044b", new BooleanSetting("\u0413\u0435\u043f\u043b\u044b", true), new BooleanSetting("\u041f\u0435\u0440\u043a\u0438", true), new BooleanSetting("\u0425\u043e\u0440\u0443\u0441\u044b", true), new BooleanSetting("\u0427\u0430\u0440\u043a\u0438", false));
    static final SliderSetting gappleTime = new SliderSetting("\u041a\u0443\u043b\u0434\u0430\u0443\u043d \u0433\u0435\u043f\u043b\u0430", 4.5f, 1.0f, 10.0f, 0.05f).setVisible(ItemCooldown::lambda$static$0);
    static final SliderSetting pearlTime = new SliderSetting("\u041a\u0443\u043b\u0434\u0430\u0443\u043d \u043f\u0435\u0440\u043e\u043a", 14.05f, 1.0f, 15.0f, 0.05f).setVisible(ItemCooldown::lambda$static$1);
    static final SliderSetting horusTime = new SliderSetting("\u041a\u0443\u043b\u0434\u0430\u0443\u043d \u0445\u043e\u0440\u0443\u0441\u043e\u0432", 2.3f, 1.0f, 10.0f, 0.05f).setVisible(ItemCooldown::lambda$static$2);
    static final SliderSetting enchantmentGappleTime = new SliderSetting("\u041a\u0443\u043b\u0434\u0430\u0443\u043d \u0447\u0430\u0440\u043e\u043a", 4.5f, 1.0f, 10.0f, 0.05f).setVisible(ItemCooldown::lambda$static$3);
    private final BooleanSetting onlyPvP = new BooleanSetting("\u0422\u043e\u043b\u044c\u043a\u043e \u0432 PVP", true);
    public HashMap<Item, Long> lastUseItemTime = new HashMap();
    public boolean isCooldown;

    public ItemCooldown() {
        this.addSettings(items, gappleTime, pearlTime, horusTime, enchantmentGappleTime, this.onlyPvP);
    }

    @Subscribe
    public void onCalculateCooldown(EventCalculateCooldown eventCalculateCooldown) {
        this.applyGoldenAppleCooldown(eventCalculateCooldown);
    }

    private void applyGoldenAppleCooldown(EventCalculateCooldown eventCalculateCooldown) {
        ArrayList<Item> arrayList = new ArrayList<Item>();
        for (Map.Entry<Item, Long> entry : this.lastUseItemTime.entrySet()) {
            float f;
            ItemEnum itemEnum = ItemEnum.getItemEnum(entry.getKey());
            if (itemEnum == null || eventCalculateCooldown.getItemStack() != itemEnum.getItem() || !itemEnum.getActive().get().booleanValue() || this.isNotPvP()) continue;
            long l = System.currentTimeMillis() - entry.getValue();
            if ((float)l < (f = itemEnum.getTime().get().floatValue() * 1000.0f) && itemEnum.getActive().get().booleanValue()) {
                eventCalculateCooldown.setCooldown((float)l / f);
                this.isCooldown = true;
                continue;
            }
            this.isCooldown = false;
            arrayList.add(itemEnum.getItem());
        }
        arrayList.forEach(this.lastUseItemTime::remove);
    }

    public boolean isNotPvP() {
        return (Boolean)this.onlyPvP.get() != false && !ClientUtil.isPvP();
    }

    public boolean isCurrentItem(ItemEnum itemEnum) {
        if (!itemEnum.getActive().get().booleanValue()) {
            return true;
        }
        return itemEnum.getActive().get() != false && Arrays.stream(ItemEnum.values()).anyMatch(arg_0 -> ItemCooldown.lambda$isCurrentItem$4(itemEnum, arg_0));
    }

    private static boolean lambda$isCurrentItem$4(ItemEnum itemEnum, ItemEnum itemEnum2) {
        return itemEnum2 == itemEnum;
    }

    private static Boolean lambda$static$3() {
        return (Boolean)items.getValueByName("\u0427\u0430\u0440\u043a\u0438").get();
    }

    private static Boolean lambda$static$2() {
        return (Boolean)items.getValueByName("\u0425\u043e\u0440\u0443\u0441\u044b").get();
    }

    private static Boolean lambda$static$1() {
        return (Boolean)items.getValueByName("\u041f\u0435\u0440\u043a\u0438").get();
    }

    private static Boolean lambda$static$0() {
        return (Boolean)items.getValueByName("\u0413\u0435\u043f\u043b\u044b").get();
    }

    public static enum ItemEnum {
        CHORUS(Items.CHORUS_FRUIT, ItemEnum::lambda$static$0, horusTime::get),
        GOLDEN_APPLE(Items.GOLDEN_APPLE, ItemEnum::lambda$static$1, gappleTime::get),
        ENCHANTED_GOLDEN_APPLE(Items.ENCHANTED_GOLDEN_APPLE, ItemEnum::lambda$static$2, enchantmentGappleTime::get),
        ENDER_PEARL(Items.ENDER_PEARL, ItemEnum::lambda$static$3, pearlTime::get);

        private final Item item;
        private final Supplier<Boolean> active;
        private final Supplier<Float> time;

        private ItemEnum(Item item, Supplier<Boolean> supplier, Supplier<Float> supplier2) {
            this.item = item;
            this.active = supplier;
            this.time = supplier2;
        }

        public static ItemEnum getItemEnum(Item item) {
            return Arrays.stream(ItemEnum.values()).filter(arg_0 -> ItemEnum.lambda$getItemEnum$4(item, arg_0)).findFirst().orElse(null);
        }

        public Item getItem() {
            return this.item;
        }

        public Supplier<Boolean> getActive() {
            return this.active;
        }

        public Supplier<Float> getTime() {
            return this.time;
        }

        private static boolean lambda$getItemEnum$4(Item item, ItemEnum itemEnum) {
            return itemEnum.getItem() == item;
        }

        private static Boolean lambda$static$3() {
            return (Boolean)items.getValueByName("\u041f\u0435\u0440\u043a\u0438").get();
        }

        private static Boolean lambda$static$2() {
            return (Boolean)items.getValueByName("\u0427\u0430\u0440\u043a\u0438").get();
        }

        private static Boolean lambda$static$1() {
            return (Boolean)items.getValueByName("\u0413\u0435\u043f\u043b\u044b").get();
        }

        private static Boolean lambda$static$0() {
            return (Boolean)items.getValueByName("\u0425\u043e\u0440\u0443\u0441\u044b").get();
        }
    }
}

