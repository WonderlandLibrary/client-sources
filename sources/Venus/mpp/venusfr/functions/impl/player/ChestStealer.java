/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.Random;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.ModeListSetting;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.math.StopWatch;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

@FunctionRegister(name="ChestStealer", type=Category.Player)
public class ChestStealer
extends Function {
    private final ModeSetting mode = new ModeSetting("\u041c\u043e\u0434", "\u0423\u043c\u043d\u044b\u0439", "\u0423\u043c\u043d\u044b\u0439");
    private final BooleanSetting chestClose = new BooleanSetting("\u0417\u0430\u043a\u0440\u044b\u0432\u0430\u0442\u044c \u043f\u0440\u0438 \u043f\u043e\u043b\u043d\u043e\u043c", true);
    private final SliderSetting stealDelay = new SliderSetting("\u0417\u0430\u0434\u0435\u0440\u0436\u043a\u0430", 100.0f, 0.0f, 1000.0f, 1.0f);
    private final BooleanSetting filterLootToggle = new BooleanSetting("\u0424\u0438\u043b\u044c\u0442\u0440 \u043b\u0443\u0442\u0430", false).setVisible(this::lambda$new$0);
    private final ModeListSetting filterLoot = new ModeListSetting("\u041b\u0443\u0442", new BooleanSetting("\u0420\u0443\u0434\u044b", true), new BooleanSetting("\u0413\u043e\u043b\u043e\u0432\u044b", false), new BooleanSetting("\u041d\u0435\u0437\u0435\u0440\u0438\u0442\u043e\u0432\u044b\u0439 \u0441\u043b\u0438\u0442\u043e\u043a", false), new BooleanSetting("\u0417\u0430\u0447\u0430\u0440\u043e\u0432\u0430\u043d\u043d\u0430\u044f \u043a\u043d\u0438\u0433\u0430", false), new BooleanSetting("\u0422\u043e\u0442\u0435\u043c\u044b", false), new BooleanSetting("\u0417\u0435\u043b\u044c\u044f", false)).setVisible(this::lambda$new$1);
    private final SliderSetting itemLimit = new SliderSetting("\u041b\u0438\u043c\u0438\u0442 \u043a\u043e\u043b", 12.0f, 1.0f, 64.0f, 1.0f).setVisible(this::lambda$new$2);
    private final SliderSetting missPercent = new SliderSetting("\u041c\u0438\u0441\u0441\u0430\u0442\u044c", 50.0f, 0.0f, 100.0f, 1.0f).setVisible(this::lambda$new$3);
    private final StopWatch timerUtil = new StopWatch();

    public ChestStealer() {
        this.addSettings(this.mode, this.chestClose, this.stealDelay, this.filterLootToggle, this.filterLoot, this.itemLimit, this.missPercent);
    }

    private boolean filterItem(Item item) {
        if (!((Boolean)this.filterLootToggle.get()).booleanValue()) {
            return false;
        }
        boolean bl = (Boolean)this.filterLoot.get(0).get();
        boolean bl2 = (Boolean)this.filterLoot.get(1).get();
        boolean bl3 = (Boolean)this.filterLoot.get(2).get();
        boolean bl4 = (Boolean)this.filterLoot.get(3).get();
        boolean bl5 = (Boolean)this.filterLoot.get(4).get();
        boolean bl6 = (Boolean)this.filterLoot.get(5).get();
        if (bl && (item == Items.DIAMOND_ORE || item == Items.EMERALD_ORE || item == Items.IRON_ORE || item == Items.GOLD_ORE || item == Items.COAL_ORE)) {
            return false;
        }
        if (bl2 && item == Items.PLAYER_HEAD) {
            return false;
        }
        if (bl3 && item == Items.NETHERITE_INGOT) {
            return false;
        }
        if (bl4 && item == Items.ENCHANTED_BOOK) {
            return false;
        }
        if (bl5 && item == Items.TOTEM_OF_UNDYING) {
            return false;
        }
        return !bl6 || item != Items.POTION && item != Items.SPLASH_POTION;
    }

    @Subscribe
    public void onEvent(EventUpdate eventUpdate) {
        if (this.mode.is("\u0423\u043c\u043d\u044b\u0439") && ChestStealer.mc.player.openContainer instanceof ChestContainer) {
            int n;
            ChestContainer chestContainer = (ChestContainer)ChestStealer.mc.player.openContainer;
            IInventory iInventory = chestContainer.getLowerChestInventory();
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            for (n = 0; n < iInventory.getSizeInventory(); ++n) {
                if (iInventory.getStackInSlot(n).getItem() == Item.getItemById(0) || !((float)iInventory.getStackInSlot(n).getCount() <= ((Float)this.itemLimit.get()).floatValue()) || !this.filterItem(iInventory.getStackInSlot(n).getItem())) continue;
                arrayList.add(n);
            }
            if (!arrayList.isEmpty() && this.timerUtil.isReached(Math.round(((Float)this.stealDelay.get()).floatValue()))) {
                n = new Random().nextInt(arrayList.size());
                int n2 = (Integer)arrayList.get(n);
                Random random2 = new Random();
                if ((float)random2.nextInt(100) >= ((Float)this.missPercent.get()).floatValue()) {
                    ChestStealer.mc.playerController.windowClick(chestContainer.windowId, n2, 0, ClickType.QUICK_MOVE, ChestStealer.mc.player);
                }
                this.timerUtil.reset();
            }
            if (iInventory.isEmpty() && ((Boolean)this.chestClose.get()).booleanValue()) {
                ChestStealer.mc.player.closeScreen();
            }
        }
    }

    private Boolean lambda$new$3() {
        return this.mode.is("\u0423\u043c\u043d\u044b\u0439");
    }

    private Boolean lambda$new$2() {
        return this.mode.is("\u0423\u043c\u043d\u044b\u0439");
    }

    private Boolean lambda$new$1() {
        return this.mode.is("\u0423\u043c\u043d\u044b\u0439") && (Boolean)this.filterLootToggle.get() != false;
    }

    private Boolean lambda$new$0() {
        return this.mode.is("\u0423\u043c\u043d\u044b\u0439");
    }
}

