package wtf.shiyeno.modules.impl.combat;

import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SkullItem;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.game.EventKey;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BindSetting;
import wtf.shiyeno.modules.settings.imp.ModeSetting;
import wtf.shiyeno.util.misc.TimerUtil;
import wtf.shiyeno.util.world.InventoryUtil;

@FunctionAnnotation(
        name = "AutoSwap",
        type = Type.Combat
)
public class AutoSwap extends Function {
    private final TimerUtil timerUtil = new TimerUtil();
    private BindSetting swap = new BindSetting("Кнопка свапа", 0);
    private final ModeSetting mode = new ModeSetting("Выбор первого свапа", "Тотем", new String[]{"Тотем", "Щит", "Геплы", "Шар"});
    private final ModeSetting mode1 = new ModeSetting("Выбор второго свапа", "Тотем", new String[]{"Тотем", "Щит", "Геплы", "Шар"});
    boolean swapped = true;
    boolean swapped1 = true;
    boolean restart = true;

    public AutoSwap() {
        this.addSettings(new Setting[]{this.swap, this.mode, this.mode1});
    }

    public void onEvent(Event event) {
        if (event instanceof EventKey e) {
            if (e.key == this.swap.getKey()) {
                if (this.restart) {
                    if (this.mode.is("Шар")) {
                        this.ballItem();
                    }

                    if (this.mode.is("Тотем")) {
                        this.swap(Items.TOTEM_OF_UNDYING);
                    }

                    if (this.mode.is("Щит")) {
                        this.swap(Items.SHIELD);
                    }

                    if (this.mode.is("Геплы")) {
                        this.swap(Items.GOLDEN_APPLE);
                    }

                    this.swapped = true;
                    this.restart = false;
                    return;
                }

                if (!this.restart) {
                    if (this.mode1.is("Шар")) {
                        this.ballItem();
                    }

                    if (this.mode1.is("Тотем")) {
                        this.swap(Items.TOTEM_OF_UNDYING);
                    }

                    if (this.mode1.is("Щит")) {
                        this.swap(Items.SHIELD);
                    }

                    if (this.mode1.is("Геплы")) {
                        this.swap(Items.GOLDEN_APPLE);
                    }

                    this.swapped = true;
                    this.restart = true;
                    return;
                }
            }
        }
    }

    public void swap(Item item) {
        if (this.swapped && mc.player.inventory.getStackInSlot(40).getItem() != item) {
            int slot = InventoryUtil.getItemSlot(item);
            if (slot != -1) {
                mc.playerController.windowClick(0, slot < 9 ? slot + 36 : slot, 40, ClickType.SWAP, mc.player);
                this.swapped = false;
            }
        }
    }

    public void ballItem() {
        for(int i = 0; i < 45; ++i) {
            if (this.swapped && mc.player.inventory.getStackInSlot(i).getItem() instanceof SkullItem && !(mc.player.inventory.getStackInSlot(40).getItem() instanceof SkullItem)) {
                mc.playerController.windowClick(0, i < 9 ? i + 36 : i, 40, ClickType.SWAP, mc.player);
                this.swapped = false;
            }
        }
    }

    public void onDisable() {
        this.swapped = true;
        this.restart = true;
        super.onDisable();
    }
}