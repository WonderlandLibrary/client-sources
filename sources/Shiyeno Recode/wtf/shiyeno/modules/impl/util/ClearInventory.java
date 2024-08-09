package wtf.shiyeno.modules.impl.util;

import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.util.misc.TimerUtil;

@FunctionAnnotation(
        name = "ClearInventory",
        type = Type.Util
)
public class ClearInventory extends Function {
    private final TimerUtil timerUtil = new TimerUtil();
    public BooleanOption actions = new BooleanOption("Подтвердить удаление", false);
    int i = 0;

    public ClearInventory() {
        this.addSettings(new Setting[]{this.actions});
    }

    protected void onEnable() {
        this.timerUtil.reset();
        super.onEnable();
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (!this.actions.get()) {
                this.toggle();
            }

            if (this.actions.get() && this.timerUtil.hasTimeElapsed(2000L)) {
                for(int i = 0; i < 36; ++i) {
                    if (mc.player.inventory.getStackInSlot(i).getItem() != Items.AIR) {
                        mc.playerController.windowClick(0, i < 9 ? i + 36 : i, 45, ClickType.SWAP, mc.player);
                    }

                    if (mc.player.inventory.getStackInSlot(40).getItem() != Items.AIR) {
                        mc.playerController.windowClick(0, 45, 45, ClickType.SWAP, mc.player);
                    }

                    if (((ItemStack)mc.player.inventory.armorInventory.get(3)).getItem() != Items.AIR) {
                        mc.playerController.windowClick(0, 5, 45, ClickType.SWAP, mc.player);
                    }

                    if (((ItemStack)mc.player.inventory.armorInventory.get(2)).getItem() != Items.AIR) {
                        mc.playerController.windowClick(0, 6, 45, ClickType.SWAP, mc.player);
                    }

                    if (((ItemStack)mc.player.inventory.armorInventory.get(1)).getItem() != Items.AIR) {
                        mc.playerController.windowClick(0, 7, 45, ClickType.SWAP, mc.player);
                    }

                    if (((ItemStack)mc.player.inventory.armorInventory.get(0)).getItem() != Items.AIR) {
                        mc.playerController.windowClick(0, 8, 45, ClickType.SWAP, mc.player);
                    }

                    if (this.timerUtil.hasTimeElapsed(3200L)) {
                        this.toggle();
                        this.timerUtil.reset();
                    }
                }
            }
        }
    }
}