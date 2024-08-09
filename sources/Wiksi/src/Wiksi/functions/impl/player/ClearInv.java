package src.Wiksi.functions.impl.player;
import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.BooleanSetting;
import src.Wiksi.utils.TimerUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@FunctionRegister(name = "ClearInv", type = Category.Player)
public class ClearInv extends Function {
    private final TimerUtil timerUtil = new TimerUtil();
    public BooleanSetting actions = new BooleanSetting("off после очистки", true);

    public ClearInv() {
        addSettings(this.actions);
    }

    public void onEnable() {
        this.timerUtil.reset();
        super.onEnable();
    }

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (e instanceof EventUpdate &&
                this.timerUtil.hasTimeElapsed(3000L))
            for (int i = 0; i < 36; i++) {
                if (mc.player.inventory.getStackInSlot(i).getItem() != Items.AIR)
                    mc.playerController.windowClick(0, (i < 9) ? (i + 36) : i, 45, ClickType.SWAP, (PlayerEntity) mc.player);
                if (mc.player.inventory.getStackInSlot(40).getItem() != Items.AIR)
                    mc.playerController.windowClick(0, 45, 45, ClickType.SWAP, (PlayerEntity) mc.player);
                if (((ItemStack) mc.player.inventory.armorInventory.get(3)).getItem() != Items.AIR)
                    mc.playerController.windowClick(0, 5, 45, ClickType.SWAP, (PlayerEntity) mc.player);
                if (((ItemStack) mc.player.inventory.armorInventory.get(2)).getItem() != Items.AIR)
                    mc.playerController.windowClick(0, 6, 45, ClickType.SWAP, (PlayerEntity) mc.player);
                if (((ItemStack) mc.player.inventory.armorInventory.get(1)).getItem() != Items.AIR)
                    mc.playerController.windowClick(0, 7, 45, ClickType.SWAP, (PlayerEntity) mc.player);
                if (((ItemStack) mc.player.inventory.armorInventory.get(0)).getItem() != Items.AIR)
                    mc.playerController.windowClick(0, 8, 45, ClickType.SWAP, (PlayerEntity) mc.player);
                if (this.timerUtil.hasTimeElapsed(3200L)) {
                    toggle();
                    this.timerUtil.reset();
                }
            }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
