package wtf.shiyeno.modules.impl.player;

import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.Item;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.util.misc.TimerUtil;

@FunctionAnnotation(
        name = "ChestStealer",
        type = Type.Player
)
public class ChestStealer extends Function {
    private final SliderSetting stealDelay = new SliderSetting("Скорость лутания", 100.0F, 0.0F, 1000.0F, 1.0F);
    private final TimerUtil timerUtil = new TimerUtil();
    public BooleanOption autocancel = new BooleanOption("Закрывать пустой сундук", true);

    public ChestStealer() {
        this.addSettings(new Setting[]{this.stealDelay, this.autocancel});
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate && mc.player.openContainer instanceof ChestContainer) {
            ChestContainer container = (ChestContainer)mc.player.openContainer;

            for(int index = 0; index < container.inventorySlots.size(); ++index) {
                if (container.getLowerChestInventory().getStackInSlot(index).getItem() != Item.getItemById(0) && this.timerUtil.hasTimeElapsed(this.stealDelay.getValue().longValue())) {
                    mc.playerController.windowClick(container.windowId, index, 0, ClickType.QUICK_MOVE, mc.player);
                    this.timerUtil.reset();
                } else if (this.autocancel.get() && container.getLowerChestInventory().isEmpty()) {
                    mc.player.closeScreen();
                }
            }
        }
    }
}